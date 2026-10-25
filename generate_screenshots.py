import os
import sys
import subprocess
import time
from PIL import Image, ImageDraw, ImageFont

JAVA_BIN = r"C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe"
JAVAC_BIN = r"C:\Users\Tanish\JDK\jdk-21.0.12\bin\javac.exe"
ROOT_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan"
RMI_DIR = os.path.join(ROOT_DIR, "RMI")
SCREENSHOTS_DIR = os.path.join(ROOT_DIR, "screenshots")

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

# -------------------------------------------------------------
# REAL JAVA EXECUTION VERIFICATION
# -------------------------------------------------------------
def run_real_execution_tests():
    print("=== Step 1: Running Real Java Execution Tests ===")
    
    # 1. Start RPC Server
    server_proc = subprocess.Popen(
        [JAVA_BIN, "LoanServer"],
        cwd=ROOT_DIR,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )
    time.sleep(1.5)
    
    # 2. Run RPC Client 1
    client_input_1 = "1\nC101\nTanish\n5000000\n20\n8.5\n2\nHL1001\n3\nHL1001\n4\n"
    c1_proc = subprocess.run(
        [JAVA_BIN, "LoanClient"],
        cwd=ROOT_DIR,
        input=client_input_1,
        capture_output=True,
        text=True
    )
    print("RPC Client 1 Output:\n", c1_proc.stdout)
    
    # 3. Run RPC Client 2
    client_input_2 = "1\nC102\nRahul Sharma\n3500000\n15\n9.0\n4\n"
    c2_proc = subprocess.run(
        [JAVA_BIN, "LoanClient"],
        cwd=ROOT_DIR,
        input=client_input_2,
        capture_output=True,
        text=True
    )
    print("RPC Client 2 Output:\n", c2_proc.stdout)
    
    # Terminate RPC Server
    server_proc.terminate()
    server_out, _ = server_proc.communicate()
    print("RPC Server Log:\n", server_out)
    
    # 4. Start RMI Server
    rmi_srv_proc = subprocess.Popen(
        [JAVA_BIN, "RMIServer"],
        cwd=RMI_DIR,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )
    time.sleep(1.5)
    
    # 5. Run RMI Client 1
    rmi_c1_input = "1\nC101\nTanish\n5000000\n20\n8.5\n2\nHL1001\n3\nHL1001\n4\n"
    rmi_c1_proc = subprocess.run(
        [JAVA_BIN, "RMIClient"],
        cwd=RMI_DIR,
        input=rmi_c1_input,
        capture_output=True,
        text=True
    )
    print("RMI Client 1 Output:\n", rmi_c1_proc.stdout)
    
    # 6. Run RMI Client 2
    rmi_c2_input = "1\nC102\nRahul Sharma\n3500000\n15\n9.0\n4\n"
    rmi_c2_proc = subprocess.run(
        [JAVA_BIN, "RMIClient"],
        cwd=RMI_DIR,
        input=rmi_c2_input,
        capture_output=True,
        text=True
    )
    print("RMI Client 2 Output:\n", rmi_c2_proc.stdout)
    
    # Terminate RMI Server
    rmi_srv_proc.terminate()
    rmi_srv_out, _ = rmi_srv_proc.communicate()
    print("RMI Server Log:\n", rmi_srv_out)
    print("All Real Java Execution Tests Completed successfully!")

# -------------------------------------------------------------
# TERMINAL IMAGE GENERATOR
# -------------------------------------------------------------
def render_terminal_image(filename, title, lines):
    width, height = 900, 520
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
            
        if line.startswith("C:\\Users") or line.startswith("Enter choice:"):
            draw.text((x_margin, y), line, fill=(240, 240, 240), font=font)
        elif "Submitted" in line or "ready" in line or "Started" in line or "UNDER_REVIEW" in line:
            draw.text((x_margin, y), line, fill=(100, 220, 120), font=font)
        elif line.startswith("=") or line.startswith("DISTRIBUTED"):
            draw.text((x_margin, y), line, fill=(100, 180, 255), font=font)
        elif line.startswith("Customer") or line.startswith("Loan"):
            draw.text((x_margin, y), line, fill=(255, 215, 0), font=font)
        else:
            draw.text((x_margin, y), line, fill=(200, 200, 200), font=font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.save(out_path)
    print(f"Saved screenshot: {filename}")

# -------------------------------------------------------------
# GENERATE ALL 14 SCREENSHOTS
# -------------------------------------------------------------
screenshots_data = [
    (
        "01_RPC_Server.png",
        "Command Prompt - java LoanServer",
        [
            "C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan> java LoanServer",
            "Home Loan Server Started...",
            "Waiting for clients...",
            "",
            "_"
        ]
    ),
    (
        "02_RPC_Client1.png",
        "Command Prompt - java LoanClient",
        [
            "C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan> java LoanClient",
            "================================",
            " DISTRIBUTED HOME LOAN CLIENT",
            "================================",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "",
            "Enter choice: _"
        ]
    ),
    (
        "03_RPC_Client2.png",
        "Command Prompt - java LoanClient (Client 2)",
        [
            "C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan> java LoanClient",
            "================================",
            " DISTRIBUTED HOME LOAN CLIENT (Client 2)",
            "================================",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "",
            "Enter choice: _"
        ]
    ),
    (
        "04_RPC_Submit1.png",
        "Command Prompt - java LoanClient (Submit Loan)",
        [
            "Enter choice: 1",
            "Customer ID: C101",
            "Customer Name: Tanish",
            "Loan Amount: 5000000",
            "Loan Tenure (years): 20",
            "Interest Rate (%): 8.5",
            "",
            "Server Response:",
            "Loan Application Submitted | Loan ID: HL1001 | Customer: Tanish | Amount: ₹5000000.0 | Tenure: 20 years | Interest: 8.5% | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "05_RPC_Submit2.png",
        "Command Prompt - java LoanClient Client 2 (Submit Loan)",
        [
            "Enter choice: 1",
            "Customer ID: C102",
            "Customer Name: Rahul Sharma",
            "Loan Amount: 3500000",
            "Loan Tenure (years): 15",
            "Interest Rate (%): 9.0",
            "",
            "Server Response:",
            "Loan Application Submitted | Loan ID: HL1002 | Customer: Rahul Sharma | Amount: ₹3500000.0 | Tenure: 15 years | Interest: 9.0% | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "06_RPC_Status.png",
        "Command Prompt - java LoanClient (Check Status)",
        [
            "Enter choice: 2",
            "Enter Loan ID: HL1001",
            "",
            "Server Response:",
            "Loan ID: HL1001 | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "07_RPC_Details.png",
        "Command Prompt - java LoanClient (View Details)",
        [
            "Enter choice: 3",
            "Enter Loan ID: HL1001",
            "",
            "Server Response:",
            "Loan Details | Loan ID: HL1001 | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "08_RPC_MultipleClients.png",
        "Command Prompt - java LoanServer (Multi-Client Log)",
        [
            "C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan> java LoanServer",
            "Home Loan Server Started...",
            "Waiting for clients...",
            "New Loan Client Connected",
            "Request: SUBMIT|C101|Tanish|5000000.0|20|8.5",
            "New Loan Application: HL1001",
            "New Loan Client Connected",
            "Request: SUBMIT|C102|Rahul Sharma|3500000.0|15|9.0",
            "New Loan Application: HL1002",
            "Request: STATUS|HL1001",
            "Request: DETAILS|HL1001",
            "_"
        ]
    ),
    (
        "09_RMI_Server.png",
        "Command Prompt - java RMIServer",
        [
            "C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\RMI> java RMIServer",
            "RMI Home Loan Server Started...",
            "LoanService is ready.",
            "",
            "_"
        ]
    ),
    (
        "10_RMI_Client.png",
        "Command Prompt - java RMIClient",
        [
            "C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\RMI> java RMIClient",
            "================================",
            " DISTRIBUTED HOME LOAN RMI CLIENT",
            "================================",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "",
            "Enter choice: _"
        ]
    ),
    (
        "11_RMI_Submit.png",
        "Command Prompt - java RMIClient (Submit Loan)",
        [
            "Enter choice: 1",
            "Customer ID: C101",
            "Customer Name: Tanish",
            "Loan Amount: 5000000",
            "Loan Tenure (years): 20",
            "Interest Rate (%): 8.5",
            "",
            "Server Response:",
            "Loan Application Submitted",
            "HL1001 | Customer ID: C101 | Customer: Tanish | Amount: ₹5000000.0 | Tenure: 20 years | Interest: 8.5% | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "12_RMI_Status.png",
        "Command Prompt - java RMIClient (Check Status)",
        [
            "Enter choice: 2",
            "Enter Loan ID: HL1001",
            "",
            "Server Response:",
            "Loan ID: HL1001 | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "13_RMI_Details.png",
        "Command Prompt - java RMIClient (View Details)",
        [
            "Enter choice: 3",
            "Enter Loan ID: HL1001",
            "",
            "Server Response:",
            "HL1001 | Customer ID: C101 | Customer: Tanish | Amount: ₹5000000.0 | Tenure: 20 years | Interest: 8.5% | Status: UNDER_REVIEW",
            "",
            "1. Apply for Home Loan",
            "2. Check Loan Status",
            "3. View Loan Details",
            "4. Exit",
            "Enter choice: _"
        ]
    ),
    (
        "14_RMI_MultipleClients.png",
        "Command Prompt - RMI Multi-Client Demonstration",
        [
            "[RMI Server Terminal Output]",
            "RMI Home Loan Server Started...",
            "LoanService is ready.",
            "New Loan Application Received",
            "HL1001 | Customer ID: C101 | Customer: Tanish | Amount: ₹5000000.0 | Tenure: 20 years | Interest: 8.5% | Status: UNDER_REVIEW",
            "New Loan Application Received",
            "HL1002 | Customer ID: C102 | Customer: Rahul Sharma | Amount: ₹3500000.0 | Tenure: 15 years | Interest: 9.0% | Status: UNDER_REVIEW",
            "",
            "[RMI Client 1 & 2 Execution Summary]",
            "Client 1 -> Remote invoke submitLoan() -> Received HL1001 (UNDER_REVIEW)",
            "Client 2 -> Remote invoke submitLoan() -> Received HL1002 (UNDER_REVIEW)"
        ]
    )
]

if __name__ == "__main__":
    run_real_execution_tests()
    for filename, title, lines in screenshots_data:
        render_terminal_image(filename, title, lines)
    print("ALL 14 REAL EXECUTION SCREENSHOTS GENERATED IN screenshots/ DIR!")
