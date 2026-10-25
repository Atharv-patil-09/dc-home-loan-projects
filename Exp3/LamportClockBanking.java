import java.util.*;

public class LamportClockBanking {

    // Process representing a distributed banking node (e.g., Branch Server)
    static class BankingProcess {
        String name;
        int clock;

        BankingProcess(String name) {
            this.name = name;
            this.clock = 0;
        }

        // Internal event execution
        public int executeInternalEvent(String eventDescription) {
            clock++;
            System.out.printf("[%s] Local Event: '%s' | Logical Timestamp: L(%s) = %d\n", 
                name, eventDescription, name, clock);
            return clock;
        }

        // Sending event: increments local clock and returns message timestamp
        public int sendMessage(String message, BankingProcess receiver) {
            clock++;
            int sendTimestamp = clock;
            System.out.printf("[%s] SEND Message: '%s' to %s | Sent Timestamp: %d\n", 
                name, message, receiver.name, sendTimestamp);
            return sendTimestamp;
        }

        // Receiving event: updates clock to max(local, msgTimestamp) + 1
        public void receiveMessage(String message, String senderName, int msgTimestamp) {
            int oldClock = clock;
            clock = Math.max(clock, msgTimestamp) + 1;
            System.out.printf("[%s] RECEIVE Message: '%s' from %s | Old Clock: %d | Msg Timestamp: %d | New Synchronized L(%s) = %d\n", 
                name, message, senderName, oldClock, msgTimestamp, name, clock);
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" EXPERIMENT 3: LAMPORT'S LOGICAL CLOCK IN DISTRIBUTED BANKING SYSTEM");
        System.out.println("==========================================================================");

        BankingProcess branch1 = new BankingProcess("Branch-Mumbai");
        BankingProcess branch2 = new BankingProcess("Branch-Delhi");
        BankingProcess centralServer = new BankingProcess("Central-Server");

        System.out.println("\n--- Step 1: Local Events at Branch-Mumbai ---");
        branch1.executeInternalEvent("Customer C101 Fills Home Loan Form");
        branch1.executeInternalEvent("Document Verification Complete");

        System.out.println("\n--- Step 2: Branch-Mumbai Submits Loan Application to Central Server ---");
        int t1 = branch1.sendMessage("SUBMIT_LOAN|C101|Tanish|5000000|20|8.5", centralServer);
        centralServer.receiveMessage("SUBMIT_LOAN|C101|Tanish|5000000|20|8.5", branch1.name, t1);

        System.out.println("\n--- Step 3: Central Server Processes Application & Credit Check ---");
        centralServer.executeInternalEvent("Credit Score Rating Check for C101");
        centralServer.executeInternalEvent("Loan Sanction Approval (HL1001)");

        System.out.println("\n--- Step 4: Branch-Delhi Local Transaction & Query to Central Server ---");
        branch2.executeInternalEvent("Customer C102 Applies for Home Loan");
        int t2 = branch2.sendMessage("QUERY_STATUS|HL1001", centralServer);
        centralServer.receiveMessage("QUERY_STATUS|HL1001", branch2.name, t2);

        System.out.println("\n--- Step 5: Central Server Sends Loan Approval Response to Branch-Mumbai ---");
        int t3 = centralServer.sendMessage("LOAN_APPROVED|HL1001|UNDER_REVIEW", branch1);
        branch1.receiveMessage("LOAN_APPROVED|HL1001|UNDER_REVIEW", centralServer.name, t3);

        System.out.println("\n==========================================================================");
        System.out.println(" FINAL SYNCHRONIZED LOGICAL CLOCK STATE:");
        System.out.println(" Branch-Mumbai Clock   : " + branch1.clock);
        System.out.println(" Branch-Delhi Clock    : " + branch2.clock);
        System.out.println(" Central-Server Clock  : " + centralServer.clock);
        System.out.println("==========================================================================");
    }
}
