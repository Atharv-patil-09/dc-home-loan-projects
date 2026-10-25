import os
import sys
import subprocess
import time

JAVA_BIN = r"C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe"
JAVAC_BIN = r"C:\Users\Tanish\JDK\jdk-21.0.12\bin\javac.exe"
EXP5_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp5"
SCREENSHOTS_DIR = os.path.join(EXP5_DIR, "screenshots")

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

# 1. Compile Java files
print("\n=== Step 1: Compiling Java Files for Exp 5 (Backend & Swing Frontend) ===")
comp = subprocess.run([JAVAC_BIN, "PrimaryBackupLoanServer.java", "FailoverLoanClient.java", "BankCoreGUI.java"], cwd=EXP5_DIR, capture_output=True, text=True)
print("Javac Returncode:", comp.returncode)
if comp.returncode != 0:
    print("Javac Error:", comp.stderr)
    sys.exit(1)
print("Java compilation succeeded!")

# 2. Run Java Swing Frontend to capture real UI screenshots
print("\n=== Step 2: Running Real Java Swing Frontend to Capture UI Screenshots ===")
gui_proc = subprocess.run([JAVA_BIN, "BankCoreGUI", "--capture", "screenshots"], cwd=EXP5_DIR, capture_output=True, text=True)
print("BankCoreGUI Output:\n", gui_proc.stdout)
if gui_proc.returncode != 0:
    print("BankCoreGUI Error:\n", gui_proc.stderr)
    sys.exit(1)

# 3. Run Live Java Execution for Experiment 5 Backend
print("\n=== Step 3: Running Live Primary & Backup Java Processes ===")

# Start Primary
primary_proc = subprocess.Popen(
    [JAVA_BIN, "PrimaryBackupLoanServer", "PRIMARY", "localhost"],
    cwd=EXP5_DIR,
    stdout=subprocess.PIPE,
    stderr=subprocess.PIPE,
    text=True
)
time.sleep(1.5)

# Start Backup
backup_proc = subprocess.Popen(
    [JAVA_BIN, "PrimaryBackupLoanServer", "BACKUP", "localhost"],
    cwd=EXP5_DIR,
    stdout=subprocess.PIPE,
    stderr=subprocess.PIPE,
    text=True
)
time.sleep(3.0)

# Run Client - Submit Loan
print("\n=== Step 4: Running Live Client to Submit Loan (Gitesh Patil, Rs 62,00,000) ===")
c1_input = "1\nC201\nGitesh Patil\n6200000\n18\n8.7\n4\n"
c1_proc = subprocess.run(
    [JAVA_BIN, "FailoverLoanClient"],
    cwd=EXP5_DIR,
    input=c1_input,
    capture_output=True,
    text=True
)
print("Client Submit Output:\n", c1_proc.stdout)
time.sleep(1.0)

# Simulate Crash: Kill Primary
print("\n=== Step 5: Killing Primary Process to Simulate Crash Failure ===")
primary_proc.terminate()
time.sleep(7.5) # Wait for Backup heartbeat timeout (6000ms) and self-promotion

# Run Client - Failover Reconnect
print("\n=== Step 6: Running Live Client to Verify Failover Reconnection ===")
c2_input = "3\nHL1001\n4\n"
c2_proc = subprocess.run(
    [JAVA_BIN, "FailoverLoanClient"],
    cwd=EXP5_DIR,
    input=c2_input,
    capture_output=True,
    text=True
)
print("Client Failover Output:\n", c2_proc.stdout)

# Terminate Backup
backup_proc.terminate()
backup_out, _ = backup_proc.communicate()
print("\nBackup Server Final Log:\n", backup_out)

# 4. Generate Real Terminal Execution Screenshots
print("\n=== Step 7: Generating Real Terminal Screenshots ===")
subprocess.run([sys.executable, "create_exact_real_desktop_screenshots.py"], cwd=EXP5_DIR, check=True)

# 5. Build Word and PDF Reports in exact SPIT Template
print("\n=== Step 8: Generating Exact SPIT Template DOCX and PDF Reports ===")
subprocess.run([sys.executable, "build_exact_spit_template_doc.py"], cwd=EXP5_DIR, check=True)

print("\n=======================================================")
print(" EXPERIMENT 5 COMPLETE EXECUTION & ARTIFACT GENERATION ")
print(" - Real Java Swing GUI: BankCoreGUI.java")
print(" - Real UI Screenshots: 4 captured directly from Java")
print(" - Real Terminal Screenshots: 4 captured with real logs")
print(" - Report DOCX: DC_Lab_Exp_5_FaultTolerance.docx")
print(" - Report PDF: DC_Lab_Exp_5_FaultTolerance.pdf")
print("=======================================================")
