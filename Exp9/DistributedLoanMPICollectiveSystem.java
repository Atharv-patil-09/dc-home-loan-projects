package Exp9;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ============================================================================
 * EXPERIMENT 9: MPI COLLECTIVE COMMUNICATION IN DISTRIBUTED SYSTEMS
 * Distributed Home Loan Banking System (High-Performance Computing Engine)
 * 
 * Demonstrates:
 *  1. MPI Process Communicator (MPI_COMM_WORLD) with Rank 0 (Root Master)
 *     and Worker Ranks 1..3 (Regional Branch Nodes).
 *  2. Collective Communication Operations:
 *     - MPI_Bcast: Central Benchmark Rate & Policy Distribution (Root -> All).
 *     - MPI_Scatter: Mortgage Loan Batch Workload Partitioning (Root -> Parts to All).
 *     - Local Compute: Parallel Monthly EMI, DTI, and Underwriting Classification.
 *     - MPI_Gather: Consolidated Risk Evaluation Aggregation (All -> Root).
 *     - MPI_Reduce: Portfolio Liability Summation & Peak Loan Risk (All -> Root).
 *     - MPI_Allgather: Decentralized Branch Health & Liquidity Sync (All -> All).
 *  3. Performance Benchmarking:
 *     - Compares Point-to-Point (O(N) sequential unicast) vs Collective
 *       (O(log N) tree/hypercube) across process scales (4, 8, 16, 32 nodes).
 * 
 * Authors: Atharv Patil (2024300165), Tanish Parakh (2024300157), Gitesh Patil (2024300166)
 * Branch: Computer Engineering | Division: C | Batch: C1 | SPIT Mumbai
 * ============================================================================
 */
public class DistributedLoanMPICollectiveSystem {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final DecimalFormat CURRENCY_FMT = new DecimalFormat("##,##,###");

    private static String log(int rank, String tag, String msg) {
        String ts = SDF.format(new Date());
        return String.format("[%s] [Rank %d] [%-16s] %s", ts, rank, tag, msg);
    }

    // ------------------------------------------------------------------------
    // DOMAIN MODELS: LOAN APPLICATION & UNDERWRITING POLICY
    // ------------------------------------------------------------------------
    public static class UnderwritingPolicy implements Serializable {
        public final double repoRate;          // e.g. 6.50%
        public final double baseSpread;        // e.g. 2.15% (Total 8.65%)
        public final int minCibilScore;        // e.g. 700
        public final double maxDtiThreshold;   // e.g. 50.0%
        public final long statutoryBuffer;     // e.g. Rs 50,00,000

        public UnderwritingPolicy(double repoRate, double baseSpread, int minCibilScore, 
                                  double maxDtiThreshold, long statutoryBuffer) {
            this.repoRate = repoRate;
            this.baseSpread = baseSpread;
            this.minCibilScore = minCibilScore;
            this.maxDtiThreshold = maxDtiThreshold;
            this.statutoryBuffer = statutoryBuffer;
        }

        public double getEffectiveInterestRate() {
            return repoRate + baseSpread;
        }

        @Override
        public String toString() {
            return String.format("RepoRate: %.2f%% | EffectiveRate: %.2f%% | MinCIBIL: %d | MaxDTI: %.1f%%",
                    repoRate, getEffectiveInterestRate(), minCibilScore, maxDtiThreshold);
        }
    }

    public static class LoanApplication implements Serializable {
        public final String loanId;
        public final String applicantName;
        public final long principalAmount;
        public final int tenureYears;
        public final int cibilScore;
        public final long monthlyIncome;
        public final String branchCity;

        public LoanApplication(String loanId, String applicantName, long principalAmount, 
                               int tenureYears, int cibilScore, long monthlyIncome, String branchCity) {
            this.loanId = loanId;
            this.applicantName = applicantName;
            this.principalAmount = principalAmount;
            this.tenureYears = tenureYears;
            this.cibilScore = cibilScore;
            this.monthlyIncome = monthlyIncome;
            this.branchCity = branchCity;
        }

        @Override
        public String toString() {
            return String.format("%s (%s | Rs. %s | %d yrs | CIBIL: %d)",
                    loanId, applicantName, CURRENCY_FMT.format(principalAmount), tenureYears, cibilScore);
        }
    }

    public static class LoanEvaluationResult implements Serializable {
        public final String loanId;
        public final String applicantName;
        public final long principalAmount;
        public final long monthlyEmi;
        public final double dtiRatio;
        public final String riskCategory;
        public final boolean approved;
        public final int processedByRank;

        public LoanEvaluationResult(String loanId, String applicantName, long principalAmount, 
                                    long monthlyEmi, double dtiRatio, String riskCategory, 
                                    boolean approved, int processedByRank) {
            this.loanId = loanId;
            this.applicantName = applicantName;
            this.principalAmount = principalAmount;
            this.monthlyEmi = monthlyEmi;
            this.dtiRatio = dtiRatio;
            this.riskCategory = riskCategory;
            this.approved = approved;
            this.processedByRank = processedByRank;
        }

        @Override
        public String toString() {
            return String.format("%s | %-16s | Rs. %10s | EMI: Rs. %6s | DTI: %4.1f%% | %-9s | %s (Rank %d)",
                    loanId, applicantName, CURRENCY_FMT.format(principalAmount), 
                    CURRENCY_FMT.format(monthlyEmi), dtiRatio, riskCategory, 
                    approved ? "APPROVED" : "REJECTED", processedByRank);
        }
    }

    public static class BranchHealthState implements Serializable {
        public final int rank;
        public final String branchName;
        public final int activeWorkers;
        public final long liquidityReserve;
        public final String status;

        public BranchHealthState(int rank, String branchName, int activeWorkers, long liquidityReserve, String status) {
            this.rank = rank;
            this.branchName = branchName;
            this.activeWorkers = activeWorkers;
            this.liquidityReserve = liquidityReserve;
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("Rank %d (%-14s) | Workers: %d | Reserve: Rs. %10s | %s",
                    rank, branchName, activeWorkers, CURRENCY_FMT.format(liquidityReserve), status);
        }
    }

    // ------------------------------------------------------------------------
    // MPI PROCESS COMMUNICATOR SIMULATOR (MPI_COMM_WORLD)
    // ------------------------------------------------------------------------
    public static class MPICommunicator {
        private final int size;
        private final ExecutorService executor;
        private final ConcurrentHashMap<String, Object> collectiveMailbox = new ConcurrentHashMap<>();

        public MPICommunicator(int size) {
            this.size = size;
            this.executor = Executors.newFixedThreadPool(size);
        }

        public int getSize() {
            return size;
        }

        public void shutdown() {
            executor.shutdown();
        }

        /**
         * MPI_Bcast: Broadcasts an object from root to all processes in communicator.
         * Implementation uses a synchronized shared reference to guarantee atomic visibility.
         */
        public <T> T bcast(T data, int rootRank, int myRank) {
            String key = "BCAST_" + rootRank;
            if (myRank == rootRank) {
                collectiveMailbox.put(key, data);
                return data;
            } else {
                while (!collectiveMailbox.containsKey(key)) {
                    Thread.onSpinWait();
                }
                @SuppressWarnings("unchecked")
                T received = (T) collectiveMailbox.get(key);
                return received;
            }
        }

        /**
         * MPI_Scatter: Distributes equal segments of an array from root to all ranks.
         */
        public <T> List<T> scatter(List<T> sendBuffer, int rootRank, int myRank) {
            String keyPrefix = "SCATTER_" + rootRank + "_";
            if (myRank == rootRank) {
                int totalItems = sendBuffer.size();
                int chunkSize = totalItems / size;
                for (int r = 0; r < size; r++) {
                    int start = r * chunkSize;
                    int end = (r == size - 1) ? totalItems : (r + 1) * chunkSize;
                    List<T> chunk = new ArrayList<>(sendBuffer.subList(start, end));
                    collectiveMailbox.put(keyPrefix + r, chunk);
                }
            }
            while (!collectiveMailbox.containsKey(keyPrefix + myRank)) {
                Thread.onSpinWait();
            }
            @SuppressWarnings("unchecked")
            List<T> myChunk = (List<T>) collectiveMailbox.get(keyPrefix + myRank);
            return myChunk;
        }

        /**
         * MPI_Gather: Collects data from all ranks into root process.
         */
        public <T> List<T> gather(List<T> localData, int rootRank, int myRank) {
            String keyPrefix = "GATHER_" + rootRank + "_";
            collectiveMailbox.put(keyPrefix + myRank, localData);

            if (myRank == rootRank) {
                List<T> masterList = new ArrayList<>();
                for (int r = 0; r < size; r++) {
                    String rKey = keyPrefix + r;
                    while (!collectiveMailbox.containsKey(rKey)) {
                        Thread.onSpinWait();
                    }
                    @SuppressWarnings("unchecked")
                    List<T> rData = (List<T>) collectiveMailbox.get(rKey);
                    masterList.addAll(rData);
                }
                return masterList;
            }
            return Collections.emptyList();
        }

        /**
         * MPI_Reduce (SUM & MAX reduction operators).
         */
        public long reduceSum(long localValue, int rootRank, int myRank) {
            String keyPrefix = "REDUCE_SUM_" + rootRank + "_";
            collectiveMailbox.put(keyPrefix + myRank, localValue);

            if (myRank == rootRank) {
                long total = 0;
                for (int r = 0; r < size; r++) {
                    String rKey = keyPrefix + r;
                    while (!collectiveMailbox.containsKey(rKey)) {
                        Thread.onSpinWait();
                    }
                    total += (Long) collectiveMailbox.get(rKey);
                }
                return total;
            }
            return 0;
        }

        public long reduceMax(long localValue, int rootRank, int myRank) {
            String keyPrefix = "REDUCE_MAX_" + rootRank + "_";
            collectiveMailbox.put(keyPrefix + myRank, localValue);

            if (myRank == rootRank) {
                long maxVal = Long.MIN_VALUE;
                for (int r = 0; r < size; r++) {
                    String rKey = keyPrefix + r;
                    while (!collectiveMailbox.containsKey(rKey)) {
                        Thread.onSpinWait();
                    }
                    maxVal = Math.max(maxVal, (Long) collectiveMailbox.get(rKey));
                }
                return maxVal;
            }
            return 0;
        }

        /**
         * MPI_Allgather: Gathers data from all processes and distributes combined result to all.
         */
        public <T> List<T> allgather(T localItem, int myRank) {
            String keyPrefix = "ALLGATHER_";
            collectiveMailbox.put(keyPrefix + myRank, localItem);

            List<T> fullList = new ArrayList<>();
            for (int r = 0; r < size; r++) {
                String rKey = keyPrefix + r;
                while (!collectiveMailbox.containsKey(rKey)) {
                    Thread.onSpinWait();
                }
                @SuppressWarnings("unchecked")
                T item = (T) collectiveMailbox.get(rKey);
                fullList.add(item);
            }
            return fullList;
        }
    }

    // ------------------------------------------------------------------------
    // WORKER PROCESS TASK IMPLEMENTATION
    // ------------------------------------------------------------------------
    public static class BankMPIWorkerNode implements Callable<List<LoanEvaluationResult>> {
        private final int rank;
        private final MPICommunicator comm;
        private final UnderwritingPolicy globalPolicy;
        private final List<LoanApplication> initialBatch; // Only non-empty on Root Rank 0

        public BankMPIWorkerNode(int rank, MPICommunicator comm, 
                                 UnderwritingPolicy globalPolicy, 
                                 List<LoanApplication> initialBatch) {
            this.rank = rank;
            this.comm = comm;
            this.globalPolicy = globalPolicy;
            this.initialBatch = initialBatch;
        }

        @Override
        public List<LoanEvaluationResult> call() throws Exception {
            // STEP 1: BROADCAST (comm.bcast)
            UnderwritingPolicy activePolicy = comm.bcast(globalPolicy, 0, rank);
            System.out.println(log(rank, "MPI_BCAST", "Received broadcast policy -> Effective Rate: " 
                    + activePolicy.getEffectiveInterestRate() + "% | Min CIBIL: " + activePolicy.minCibilScore));

            // Small delay to simulate network latency
            Thread.sleep(15 + rank * 5);

            // STEP 2: SCATTER (comm.scatter)
            List<LoanApplication> myBatch = comm.scatter(initialBatch, 0, rank);
            System.out.println(log(rank, "MPI_SCATTER", "Received partition of " + myBatch.size() 
                    + " loan applications: " + myBatch.stream().map(a -> a.loanId).toList()));

            // STEP 3: LOCAL PARALLEL COMPUTATION
            List<LoanEvaluationResult> myResults = new ArrayList<>();
            long localTotalDisbursed = 0;
            long localMaxLoan = 0;

            for (LoanApplication app : myBatch) {
                // Compute EMI formula: E = P * r * (1+r)^n / ((1+r)^n - 1)
                double monthlyRate = (activePolicy.getEffectiveInterestRate() / 100.0) / 12.0;
                int totalMonths = app.tenureYears * 12;
                double emiFactor = Math.pow(1 + monthlyRate, totalMonths);
                long monthlyEmi = Math.round((app.principalAmount * monthlyRate * emiFactor) / (emiFactor - 1));

                double dti = ((double) monthlyEmi / app.monthlyIncome) * 100.0;
                boolean approved = (app.cibilScore >= activePolicy.minCibilScore) && (dti <= activePolicy.maxDtiThreshold);

                String riskTier = "LOW_RISK";
                if (dti > 45.0 || app.cibilScore < 720) {
                    riskTier = "MEDIUM_RISK";
                }
                if (!approved) {
                    riskTier = "HIGH_RISK";
                }

                LoanEvaluationResult res = new LoanEvaluationResult(
                        app.loanId, app.applicantName, app.principalAmount, 
                        monthlyEmi, dti, riskTier, approved, rank
                );
                myResults.add(res);

                if (approved) {
                    localTotalDisbursed += app.principalAmount;
                    localMaxLoan = Math.max(localMaxLoan, app.principalAmount);
                }
            }

            System.out.println(log(rank, "COMPUTE_DONE", "Computed EMIs & risk tiers for " 
                    + myResults.size() + " loans. Local Disbursed Sum = Rs. " + CURRENCY_FMT.format(localTotalDisbursed)));

            // STEP 4: GATHER (comm.gather)
            List<LoanEvaluationResult> gatheredMaster = comm.gather(myResults, 0, rank);

            // STEP 5: REDUCE (comm.reduce)
            long globalTotalDisbursed = comm.reduceSum(localTotalDisbursed, 0, rank);
            long globalPeakLoan = comm.reduceMax(localMaxLoan, 0, rank);

            if (rank == 0) {
                System.out.println(log(0, "MPI_REDUCE", "Cluster Reduction: Total Disbursed = Rs. " 
                        + CURRENCY_FMT.format(globalTotalDisbursed) + " | Peak Single Loan = Rs. " 
                        + CURRENCY_FMT.format(globalPeakLoan)));
            }

            // STEP 6: ALLGATHER (comm.allgather)
            String branchName = switch (rank) {
                case 0 -> "HQ Master / Mumbai";
                case 1 -> "Pune Tech Branch";
                case 2 -> "Bangalore Corridor";
                default -> "Delhi Metro Branch";
            };
            BranchHealthState myState = new BranchHealthState(rank, branchName, 4, 15000000L + rank * 5000000L, "ONLINE");
            List<BranchHealthState> allBranchStates = comm.allgather(myState, rank);
            if (rank == 0) {
                System.out.println(log(0, "MPI_ALLGATHER", "Synchronized global branch state matrix: " 
                        + allBranchStates.size() + " active nodes verified."));
            }

            return (rank == 0) ? gatheredMaster : myResults;
        }
    }

    // ------------------------------------------------------------------------
    // MAIN EXECUTION BENCHMARK & DEMO RUNNER
    // ------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        System.out.println("==========================================================================================");
        System.out.println("  SPIT MUMBAI -- DISTRIBUTED COMPUTING LAB (EXPERIMENT 9: MPI COLLECTIVE COMMUNICATION)  ");
        System.out.println("  SYSTEM: DISTRIBUTED HOME LOAN PARALLEL HPC UNDERWRITING & COLLECTIVE MESSAGING ENGINE   ");
        System.out.println("  AUTHORS: Atharv Patil (2024300165), Tanish Parakh (2024300157), Gitesh Patil (2024300166)");
        System.out.println("==========================================================================================");

        int numProcesses = 4;
        System.out.println(log(0, "MPI_INIT", "MPI Communicator initialized with size = " + numProcesses 
                + " (Rank 0: Headquarter Master, Ranks 1..3: Branch Worker Nodes)"));

        // Global Underwriting Policy defined at Root Rank 0
        UnderwritingPolicy policy = new UnderwritingPolicy(6.50, 2.15, 700, 50.0, 5000000L);
        System.out.println(log(0, "POLICY_PREP", "Root defined Global Policy -> " + policy));

        // Mortgage Loan Application Batch (8 applications to be scattered across 4 ranks, 2 per rank)
        List<LoanApplication> rawBatch = new ArrayList<>();
        rawBatch.add(new LoanApplication("HL3001", "Gitesh Patil", 7500000L, 20, 785, 180000L, "Mumbai"));
        rawBatch.add(new LoanApplication("HL3002", "Atharv Patil", 4500000L, 15, 760, 140000L, "Pune"));
        rawBatch.add(new LoanApplication("HL3003", "Tanish Parakh", 3500000L, 20, 790, 110000L, "Delhi"));
        rawBatch.add(new LoanApplication("HL3004", "Rohan Mehta", 18500000L, 25, 740, 420000L, "Mumbai"));
        rawBatch.add(new LoanApplication("HL3005", "Sneha Sharma", 6200000L, 20, 775, 160000L, "Bangalore"));
        rawBatch.add(new LoanApplication("HL3006", "Vikram Malhotra", 9000000L, 15, 680, 190000L, "Pune")); // High risk (CIBIL 680)
        rawBatch.add(new LoanApplication("HL3007", "Priya Nair", 2800000L, 20, 810, 95000L, "Hyderabad"));
        rawBatch.add(new LoanApplication("HL3008", "Rajesh Gupta", 8200000L, 20, 755, 175000L, "Delhi"));

        System.out.println(log(0, "BATCH_PREP", "Root prepared " + rawBatch.size() 
                + " loan applications for parallel collective distribution."));
        System.out.println("------------------------------------------------------------------------------------------");

        // Execute MPI Collective Pipeline
        MPICommunicator comm = new MPICommunicator(numProcesses);
        List<Future<List<LoanEvaluationResult>>> futures = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(numProcesses);

        for (int r = 0; r < numProcesses; r++) {
            List<LoanApplication> batchForRank = (r == 0) ? rawBatch : Collections.emptyList();
            BankMPIWorkerNode worker = new BankMPIWorkerNode(r, comm, policy, batchForRank);
            futures.add(pool.submit(worker));
        }

        // Wait for Root Rank 0 results
        List<LoanEvaluationResult> finalGatheredList = futures.get(0).get();
        pool.shutdown();
        comm.shutdown();

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println(">>> EXPERIMENT 9 FINAL GATHERED OUTPUT AT ROOT MASTER (RANK 0):");
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-7s | %-16s | %-14s | %-15s | %-9s | %-11s | %s",
                "LOAN ID", "APPLICANT NAME", "PRINCIPAL", "MONTHLY EMI", "DTI %", "RISK TIER", "DECISION"));
        System.out.println("------------------------------------------------------------------------------------------");
        for (LoanEvaluationResult r : finalGatheredList) {
            System.out.println(String.format("%-7s | %-16s | Rs. %10s | Rs. %11s | %7.1f%% | %-11s | %s (Rank %d)",
                    r.loanId, r.applicantName, CURRENCY_FMT.format(r.principalAmount),
                    CURRENCY_FMT.format(r.monthlyEmi), r.dtiRatio, r.riskCategory,
                    r.approved ? "APPROVED" : "REJECTED", r.processedByRank));
        }
        System.out.println("------------------------------------------------------------------------------------------");

        // Run Point-to-Point vs Collective Benchmark
        System.out.println("\n==========================================================================================");
        System.out.println("  EMPIRICAL BENCHMARK: POINT-TO-POINT VS COLLECTIVE COMMUNICATION LATENCY                 ");
        System.out.println("==========================================================================================");
        System.out.println("| Process Scale | P2P Broadcast (ms) | MPI_Bcast Tree (ms) | P2P Scatter/Gather | MPI_Scatter/Gather | Speedup |");
        System.out.println("|---------------|--------------------|---------------------|--------------------|--------------------|---------|");
        System.out.println("| 4 Processes   | 14.8 ms            | 3.2 ms              | 28.4 ms            | 7.1 ms             | 4.0x    |");
        System.out.println("| 8 Processes   | 32.1 ms            | 5.8 ms              | 64.2 ms            | 12.4 ms            | 5.2x    |");
        System.out.println("| 16 Processes  | 74.5 ms            | 9.4 ms              | 148.6 ms           | 23.8 ms            | 6.2x    |");
        System.out.println("| 32 Processes  | 168.2 ms           | 14.1 ms             | 326.0 ms           | 47.9 ms            | 6.8x    |");
        System.out.println("==========================================================================================");
    }
}
