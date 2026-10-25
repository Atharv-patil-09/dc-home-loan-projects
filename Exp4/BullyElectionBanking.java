import java.util.*;

public class BullyElectionBanking {

    // Process class representing a distributed banking node
    static class BankNode {
        int id;
        String name;
        boolean isAlive;
        boolean isCoordinator;

        BankNode(int id, String name) {
            this.id = id;
            this.name = name;
            this.isAlive = true;
            this.isCoordinator = false;
        }

        @Override
        public String toString() {
            return name + " (ID: " + id + ")";
        }
    }

    private static Map<Integer, BankNode> cluster = new HashMap<>();
    private static int coordinatorId = 4; // Initial highest ID process

    public static void main(String[] args) throws InterruptedException {
        System.out.println("==========================================================================");
        System.out.println(" EXPERIMENT 4: BULLY ELECTION ALGORITHM IN DISTRIBUTED BANKING SYSTEM");
        System.out.println("==========================================================================");

        // Initialize Banking Cluster Nodes
        cluster.put(1, new BankNode(1, "BankNode-1 [Branch Mumbai]"));
        cluster.put(2, new BankNode(2, "BankNode-2 [Branch Delhi]"));
        cluster.put(3, new BankNode(3, "BankNode-3 [Branch Bangalore]"));
        cluster.put(4, new BankNode(4, "BankNode-4 [Central Master Server]"));

        cluster.get(coordinatorId).isCoordinator = true;

        System.out.println("\n--- Step 1: Initial Cluster State ---");
        displayClusterState();
        System.out.println("Current Active Coordinator: " + cluster.get(coordinatorId));

        // --------------------------------------------------------------------------
        // SCENARIO 1: Coordinator (BankNode-4) Crashes
        // --------------------------------------------------------------------------
        System.out.println("\n--- Step 2: Central Master Coordinator (BankNode-4) Crashes ---");
        cluster.get(4).isAlive = false;
        cluster.get(4).isCoordinator = false;
        System.out.println("[CRASH EVENT] " + cluster.get(4).name + " has crashed/gone offline!");

        // Node 2 detects coordinator failure and initiates election
        System.out.println("\n--- Step 3: Node 2 Detects Failure & Initiates Bully Election ---");
        initiateElection(2);

        // --------------------------------------------------------------------------
        // SCENARIO 2: Higher Node (BankNode-4) Recovers & Claims Leadership ("Bully")
        // --------------------------------------------------------------------------
        System.out.println("\n--- Step 4: Crashed Node (BankNode-4) Recovers & Claims Leadership ---");
        System.out.println("[RECOVERY EVENT] " + cluster.get(4).name + " is coming back online...");
        cluster.get(4).isAlive = true;
        
        // Recovered node 4 initiates election to reclaim coordinator role
        initiateElection(4);

        System.out.println("\n==========================================================================");
        System.out.println(" FINAL CLUSTER STATE AFTER ELECTION & RECOVERY:");
        displayClusterState();
        System.out.println("==========================================================================");
    }

    private static void initiateElection(int initiatorId) {
        BankNode initiator = cluster.get(initiatorId);
        System.out.println("\n>>> [ELECTION STARTED] Initiated by " + initiator);

        boolean higherNodeResponded = false;

        // Send ELECTION message to all processes with higher IDs
        for (int id = initiatorId + 1; id <= 4; id++) {
            BankNode target = cluster.get(id);
            System.out.println("  [SEND ELECTION] " + initiator.name + " ---> ELECTION ---> " + target.name);
            
            if (target.isAlive) {
                System.out.println("  [RECEIVE OK]    " + target.name + " ---> OK / ANSWER ---> " + initiator.name);
                higherNodeResponded = true;
            } else {
                System.out.println("  [TIMEOUT]       No response from " + target.name + " (Node is DOWN)");
            }
        }

        if (!higherNodeResponded) {
            // No higher node responded, initiator becomes the new Coordinator
            setNewCoordinator(initiatorId);
        } else {
            // Higher nodes responded, pass election responsibility to the highest alive node
            for (int id = initiatorId + 1; id <= 4; id++) {
                if (cluster.get(id).isAlive) {
                    initiateElection(id);
                    break;
                }
            }
        }
    }

    private static void setNewCoordinator(int newCoordId) {
        coordinatorId = newCoordId;
        for (BankNode node : cluster.values()) {
            node.isCoordinator = (node.id == newCoordId);
        }

        BankNode newCoord = cluster.get(newCoordId);
        System.out.println("\n==========================================================================");
        System.out.println(" [ELECTION RESULT] " + newCoord.name + " WON THE ELECTION!");
        System.out.println("==========================================================================");

        // Broadcast COORDINATOR message to all lower active processes
        for (BankNode node : cluster.values()) {
            if (node.id != newCoordId && node.isAlive) {
                System.out.println("  [BROADCAST COORDINATOR] " + newCoord.name + " ---> COORDINATOR Announcement ---> " + node.name);
            }
        }
    }

    private static void displayClusterState() {
        for (BankNode node : cluster.values()) {
            String state = node.isAlive ? (node.isCoordinator ? "COORDINATOR (MASTER)" : "ACTIVE (SLAVE)") : "CRASHED (DOWN)";
            System.out.printf("  - %-42s : State = %s\n", node, state);
        }
    }
}
