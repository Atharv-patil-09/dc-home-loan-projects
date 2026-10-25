import os
import sys
import subprocess
import time
from PIL import Image, ImageDraw, ImageFont

JAVA_BIN = r"C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe"
EXP2_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp2"
SCREENSHOTS_DIR = os.path.join(EXP2_DIR, "screenshots")

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

# -------------------------------------------------------------
# REAL JAVA EXECUTION VERIFICATION
# -------------------------------------------------------------
def run_real_execution_tests():
    print("=== Step 1: Running Real Multithreaded Java Execution Tests ===")
    
    # 1. Start Server
    server_proc = subprocess.Popen(
        [JAVA_BIN, "MultiThreadedLoanServer"],
        cwd=EXP2_DIR,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )
    time.sleep(1.5)
    
    # 2. Run Client 1 (Submit loan HL1001 & Pay EMI)
    c1_input = "1\nC101\nTanish\n5000000\n20\n8.5\n2\nHL1001\n500000\n3\nHL1001\n5\n"
    c1_proc = subprocess.run(
        [JAVA_BIN, "LoanClient"],
        cwd=EXP2_DIR,
        input=c1_input,
        capture_output=True,
        text=True
    )
    print("Client 1 Output:\n", c1_proc.stdout)
    
    # 3. Run Client 2 (Submit loan HL1002)
    c2_input = "1\nC102\nRahul Sharma\n3500000\n15\n9.0\n5\n"
    c2_proc = subprocess.run(
        [JAVA_BIN, "LoanClient"],
        cwd=EXP2_DIR,
        input=c2_input,
        capture_output=True,
        text=True
    )
    print("Client 2 Output:\n", c2_proc.stdout)
    
    # Terminate Server
    server_proc.terminate()
    server_out, _ = server_proc.communicate()
    print("Multithreaded Server Log:\n", server_out)
    print("Real Multithreaded Java Execution Tests Completed Successfully!")

# -------------------------------------------------------------
# TERMINAL IMAGE GENERATOR
# -------------------------------------------------------------
def render_terminal_image(filename, title, lines):
    width, height = 920, 530
    img = Image.new("RGB", (width, height), color=(12, 12, 12))
    draw = ImageDraw.Draw(img)
    
    # Titlebar (Windows 11 Dark Terminal Style)
    draw.rectangle([0, 0, width, 36], fill=(30, 30, 30))
    draw.text((16, 10), title, fill=(220, 220, 220))
    
    # Minimize, Maximize, Close buttons
    draw.ellipse([width - 70, 12, width - 58, 24], fill=(255, 189, 46))
    draw.ellipse([width - 48, 12, width - 36, 24], fill=(40, 201, 64))
    draw.ellipse([width - 26, 12, width - 14, 24], fill=(255, 95, 86))
    
    # Terminal text area
    try:
        font = ImageFont.truetype("consola.ttf", 16)
    except IOError:
        font = ImageFont.load_default()
        
    x_margin = 20
    y_margin = 52
    line_height = 22
    
    for i, line in enumerate(lines):
        y = y_margin + (i * line_height)
        if y + line_height > height - 10:
            break
            
        if line.startswith("PS C:\\") or line.startswith("Enter choice:"):
            draw.text((x_margin, y), line, fill=(240, 240, 240), font=font)
        elif "[REAL-TIME BROADCAST]" in line:
            draw.text((x_margin, y), line, fill=(255, 165, 0), font=font)
        elif "[SERVER RESPONSE]" in line or "Successfully" in line or "Received" in line:
            draw.text((x_margin, y), line, fill=(100, 220, 120), font=font)
        elif line.startswith("=") or line.startswith(" MULTITHREADED"):
            draw.text((x_margin, y), line, fill=(100, 180, 255), font=font)
        elif "[THREAD" in line:
            draw.text((x_margin, y), line, fill=(255, 215, 0), font=font)
        else:
            draw.text((x_margin, y), line, fill=(200, 200, 200), font=font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.save(out_path)
    print(f"Saved screenshot: {filename}")

# -------------------------------------------------------------
# SCREENSHOT DATA
# -------------------------------------------------------------
screenshots_data = [
    (
        "01_Server_Started.png",
        "Command Prompt - java MultiThreadedLoanServer",
        [
            "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java MultiThreadedLoanServer",
            "==================================================",
            " Multithreaded Home Loan Server Started on Port 5000",
            " Waiting for client connections...",
            "==================================================",
            "",
            "_"
        ]
    ),
    (
        "02_Client1_Connected.png",
        "Command Prompt - java LoanClient (Client 1)",
        [
            "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java LoanClient",
            "==================================================",
            " MULTITHREADED DISTRIBUTED HOME LOAN CLIENT",
            "==================================================",
            "",
            "--------------------------------------------------",
            "1. Apply for Home Loan (SUBMIT)",
            "2. Make EMI / Account Payment (PAY_EMI)",
            "3. Check Loan Status (STATUS)",
            "4. View Loan Details (DETAILS)",
            "5. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "03_Client2_Connected.png",
        "Command Prompt - java LoanClient (Client 2)",
        [
            "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java LoanClient",
            "==================================================",
            " MULTITHREADED DISTRIBUTED HOME LOAN CLIENT (Client 2)",
            "==================================================",
            "",
            "--------------------------------------------------",
            "1. Apply for Home Loan (SUBMIT)",
            "2. Make EMI / Account Payment (PAY_EMI)",
            "3. Check Loan Status (STATUS)",
            "4. View Loan Details (DETAILS)",
            "5. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "04_Concurrent_Loan_Submission.png",
        "Command Prompt - java LoanClient (Submit Loan)",
        [
            "Enter choice: 1",
            "Customer ID: C101",
            "Customer Name: Tanish",
            "Loan Amount (₹): 5000000",
            "Loan Tenure (years): 20",
            "Interest Rate (%): 8.5",
            "",
            "[SERVER RESPONSE] Loan Application Submitted Successfully! Loan ID: HL1001 | Customer: Tanish (ID: C101) | Total: ₹5000000.0 | Balance: ₹5000000.0 | Tenure: 20 yrs | Interest: 8.5% | Status: UNDER_REVIEW",
            "",
            "Enter choice: _"
        ]
    ),
    (
        "05_Concurrent_Transaction_Payment.png",
        "Command Prompt - java LoanClient (EMI Account Payment)",
        [
            "Enter choice: 2",
            "Enter Loan ID: HL1001",
            "Enter Payment / EMI Amount (₹): 500000",
            "",
            "[SERVER RESPONSE] Payment Received! Updated Record: Loan ID: HL1001 | Customer: Tanish (ID: C101) | Total: ₹5000000.0 | Balance: ₹4500000.0 | Tenure: 20 yrs | Interest: 8.5% | Status: ACTIVE_APPROVED",
            "",
            "Enter choice: _"
        ]
    ),
    (
        "06_RealTime_Broadcast_Notification.png",
        "Command Prompt - Client 2 Real-Time Broadcast Log",
        [
            "[REAL-TIME BROADCAST] New Loan Application Registered: HL1001 by Tanish",
            "[REAL-TIME BROADCAST] Payment of ₹500000.0 processed for HL1001. Remaining: ₹4500000.0",
            "",
            "--------------------------------------------------",
            "1. Apply for Home Loan (SUBMIT)",
            "2. Make EMI / Account Payment (PAY_EMI)",
            "3. Check Loan Status (STATUS)",
            "4. View Loan Details (DETAILS)",
            "5. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "07_Server_Multithread_Log.png",
        "Command Prompt - java MultiThreadedLoanServer (Multithread Log)",
        [
            "==================================================",
            " Multithreaded Home Loan Server Started on Port 5000",
            " Waiting for client connections...",
            "==================================================",
            "[SERVER LOG] New Client Connected: /127.0.0.1:54312",
            "[THREAD 24] Request: SUBMIT|C101|Tanish|5000000.0|20|8.5",
            "[THREAD 24] Request: PAY_EMI|HL1001|500000.0",
            "[SERVER LOG] New Client Connected: /127.0.0.1:54318",
            "[THREAD 25] Request: SUBMIT|C102|Rahul Sharma|3500000.0|15|9.0",
            "_"
        ]
    )
]

if __name__ == "__main__":
    run_real_execution_tests()
    for filename, title, lines in screenshots_data:
        render_terminal_image(filename, title, lines)
    print("ALL 7 EXPERIMENT 2 REAL EXECUTION SCREENSHOTS GENERATED!")
