"""
============================================================================
EXPERIMENT 9: MPI COLLECTIVE COMMUNICATION (Python mpi4py implementation)
Distributed Home Loan Banking System -- High Performance Computing
============================================================================
"""
import sys

try:
    from mpi4py import MPI
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
except ImportError:
    # Pure Python simulation fallback if mpi4py binary is not linked
    class DummyComm:
        def Get_rank(self): return 0
        def Get_size(self): return 4
        def bcast(self, data, root=0): return data
        def scatter(self, sendbuf, root=0): return sendbuf[0] if sendbuf else None
        def gather(self, sendobj, root=0): return [sendobj, sendobj + 11, sendobj + 22, sendobj + 33]
        def reduce(self, sendobj, op=None, root=0): return sendobj * 4
    comm = DummyComm()
    rank = 0
    size = 4

# ---------- 1. BROADCAST (comm.bcast) ----------
# Central Master (Rank 0) broadcasts the benchmark Repo Rate & underwriting policy
if rank == 0:
    policy_data = {
        "repo_rate": 6.50,
        "effective_roi": 8.65,
        "min_cibil": 700,
        "max_dti": 50.0
    }
else:
    policy_data = None

policy_data = comm.bcast(policy_data, root=0)
print(f"[Rank {rank}] Received broadcast policy: Repo Rate = {policy_data['repo_rate']}% | Effective ROI = {policy_data['effective_roi']}%")

# ---------- 2. SCATTER (comm.scatter) ----------
# Central Master scatters loan application batches to worker nodes
if rank == 0:
    # 4 chunks of loan applications for 4 ranks
    scatter_batches = [
        {"loans": ["HL3001", "HL3002"], "branch": "Mumbai HQ", "principal": 12000000},
        {"loans": ["HL3003", "HL3004"], "branch": "Pune Branch", "principal": 22000000},
        {"loans": ["HL3005", "HL3006"], "branch": "Bangalore Branch", "principal": 15200000},
        {"loans": ["HL3007", "HL3008"], "branch": "Delhi Branch", "principal": 11000000}
    ]
else:
    scatter_batches = None

my_batch = comm.scatter(scatter_batches, root=0)
print(f"[Rank {rank}] Received scatter batch: {my_batch['branch']} -> {my_batch['loans']} (Principal: Rs {my_batch['principal']:,})")

# ---------- 3. LOCAL PARALLEL COMPUTATION ----------
# Compute monthly EMI liability and approval status locally
monthly_rate = (policy_data['effective_roi'] / 100.0) / 12.0
num_months = 240 # 20 years
factor = (1 + monthly_rate) ** num_months
computed_emi = round((my_batch['principal'] * monthly_rate * factor) / (factor - 1))
print(f"[Rank {rank}] Local Computation: Computed Total Monthly EMI = Rs {computed_emi:,}")

# ---------- 4. GATHER (comm.gather) ----------
# Gather all evaluated EMI schedules back to Root Master
gathered_results = comm.gather({"rank": rank, "branch": my_batch['branch'], "emi": computed_emi}, root=0)

if rank == 0:
    print(f"\n[Rank 0] Gathered data from all processes:")
    for res in gathered_results:
        print(f"  • Rank {res['rank']}: {res['branch']} -> Combined Monthly EMI = Rs {res['emi']:,}")
