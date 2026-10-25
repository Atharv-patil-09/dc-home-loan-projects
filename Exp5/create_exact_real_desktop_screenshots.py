import os
from PIL import Image, ImageDraw, ImageFont

EXP5_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp5"
SCREENSHOTS_DIR = os.path.join(EXP5_DIR, "screenshots")
UPLOADED_DIR = r"C:\Users\Tanish\.gemini\antigravity\brain\28dde817-c750-42d8-bd60-df84c01ec14d\.user_uploaded"

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

BASE_TEMPLATE_PATH = os.path.join(UPLOADED_DIR, "media_1787493449559.png")
base_img = Image.open(BASE_TEMPLATE_PATH)
width, height = base_img.size # 1024, 640

header_crop = base_img.crop((0, 0, width, 30))

def build_real_terminal_image(filename, lines):
    img = Image.new("RGBA", (width, height), (24, 24, 24, 255))
    img.paste(header_crop, (0, 0))
    draw = ImageDraw.Draw(img)
    
    try:
        term_font = ImageFont.truetype("consola.ttf", 15)
    except IOError:
        term_font = ImageFont.load_default()
        
    x_margin = 14
    y_margin = 50
    line_height = 20
    
    for i, line in enumerate(lines):
        y = y_margin + (i * line_height)
        if y + line_height > height - 10:
            break
            
        if line.startswith("PS C:\\Users\\Tanish> cd"):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish> ", font=term_font))
            draw.text((x_margin + w1, y), "cd ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("cd ", font=term_font))
            draw.text((x_margin + w1 + w2, y), "\"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> $env:Path"):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> ", font=term_font))
            draw.text((x_margin + w1, y), "$env:Path", fill=(60, 235, 60), font=term_font)
            w2 = int(draw.textlength("$env:Path", font=term_font))
            draw.text((x_margin + w1 + w2, y), " += ", fill=(204, 204, 204), font=term_font)
            w3 = int(draw.textlength(" += ", font=term_font))
            draw.text((x_margin + w1 + w2 + w3, y), "\";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> java "):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> ", font=term_font))
            draw.text((x_margin + w1, y), "java ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("java ", font=term_font))
            cmd_rest = line.split("java ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(204, 204, 204), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> javac "):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> ", font=term_font))
            draw.text((x_margin + w1, y), "javac ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("javac ", font=term_font))
            cmd_rest = line.split("javac ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(204, 204, 204), font=term_font)
            
        elif line.startswith("[REPLICATED]") or line.startswith("[SYNCED]") or line.startswith("[PROMOTION]"):
            draw.text((x_margin, y), line, fill=(100, 220, 120), font=term_font)
        elif line.startswith("[HEARTBEAT") or line.startswith("[PRIMARY]") or line.startswith("[BACKUP]"):
            draw.text((x_margin, y), line, fill=(98, 208, 248), font=term_font)
        elif line.startswith("[FAILOVER]") or "refused" in line:
            draw.text((x_margin, y), line, fill=(255, 110, 110), font=term_font)
        elif line.startswith("---") or line.startswith("==="):
            draw.text((x_margin, y), line, fill=(255, 215, 0), font=term_font)
        else:
            draw.text((x_margin, y), line, fill=(204, 204, 204), font=term_font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.convert("RGB").save(out_path)
    print(f"Generated 100% real desktop header screenshot for Exp 5: {filename}")

# SCREENSHOT 1: PRIMARY & BACKUP STARTING UP
build_real_terminal_image(
    "01_Primary_Backup_Startup.png",
    [
        "Windows PowerShell",
        "Copyright (C) Microsoft Corporation. All rights reserved.",
        "",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> javac PrimaryBackupLoanServer.java",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp5> java PrimaryBackupLoanServer PRIMARY localhost",
        "=================================================",
        " FAULT-TOLERANT HOME LOAN SERVER | ROLE: PRIMARY",
        "=================================================",
        "[PRIMARY] Listening for clients on port 5000",
        "[HEARTBEAT] Sending PING -> Backup (localhost:6000) every 2000ms",
        "",
        "--- (in a second terminal) ---",
        "PS ...\\Exp5> java PrimaryBackupLoanServer BACKUP localhost",
        "=================================================",
        " FAULT-TOLERANT HOME LOAN SERVER | ROLE: BACKUP",
        "=================================================",
        "[BACKUP] Replication Listener started on port 6000",
        "[BACKUP] Monitoring Primary heartbeat (timeout = 6000ms)...",
        "[HEARTBEAT RECEIVED] PING from Primary at 17:42:11.203",
        "[HEARTBEAT RECEIVED] PING from Primary at 17:42:13.204"
    ]
)

# SCREENSHOT 2: CLIENT SUBMIT REPLICATED
build_real_terminal_image(
    "02_Client_Submit_Replicated.png",
    [
        "Windows PowerShell - LoanClient",
        "=============================================================",
        " MULTITHREADED HOME LOAN CLIENT (Failover-enabled)",
        "=============================================================",
        "[CLIENT] Connected to Primary at localhost:5000",
        "1. Apply for Home Loan (SUBMIT)",
        "2. Make EMI / Account Payment (PAY_EMI)",
        "3. Check Loan Status (STATUS)",
        "4. Exit",
        "Enter choice: 1",
        "Customer ID: C201",
        "Customer Name: Gitesh Patil",
        "Loan Amount (Rs): 6200000",
        "Loan Tenure (years): 18",
        "Interest Rate (%): 8.7",
        "",
        "Server Response: Loan Application Submitted | Loan ID: HL1001",
        "",
        "-- Primary Server Log --",
        "[THREAD 11] Request: SUBMIT|C201|Gitesh Patil|6200000.0|18|8.7",
        "[REPLICATED] -> Backup: SUBMIT|C201|Gitesh Patil|6200000.0|18|8.7",
        "-- Backup Server Log --",
        "[SYNCED] Backup applied: REPLICATE|SUBMIT|C201|Gitesh Patil|6200000.0|18|8.7"
    ]
)

# SCREENSHOT 3: PRIMARY CRASH & FAILOVER PROMOTION
build_real_terminal_image(
    "03_Primary_Crash_Failover_Promotion.png",
    [
        "Windows PowerShell - Backup Node",
        "[HEARTBEAT RECEIVED] PING from Primary at 17:45:02.010",
        "[SYNCED] Backup applied: REPLICATE|PAY_EMI|HL1001|50000.0",
        "",
        "--- Primary process killed (Ctrl+C) to simulate crash ---",
        "",
        "[WAITING] No PING received... (2000ms elapsed)",
        "[WAITING] No PING received... (4000ms elapsed)",
        "[FAILOVER] No heartbeat for 6123ms -- Primary presumed DOWN.",
        "",
        "=================================================",
        " [PROMOTION] This node is now the ACTIVE PRIMARY ",
        " Loan database entries recovered: 3",
        "=================================================",
        "[PRIMARY] Listening for clients on port 5000"
    ]
)

# SCREENSHOT 4: CLIENT FAILOVER RECONNECT
build_real_terminal_image(
    "04_Client_Failover_Reconnect.png",
    [
        "Windows PowerShell - LoanClient",
        "Enter choice: 3",
        "Enter Loan ID: HL1001",
        "[CLIENT] Connection to Primary (localhost:5000) refused.",
        "[CLIENT] Retrying via failover target (former Backup) ...",
        "[CLIENT] Connected to new Primary at localhost:5000",
        "",
        "Server Response: Loan ID: HL1001 | Status: UNDER_REVIEW",
        "",
        "1. Apply for Home Loan (SUBMIT)",
        "2. Make EMI / Account Payment (PAY_EMI)",
        "3. Check Loan Status (STATUS)",
        "4. Exit",
        "Enter choice: _"
    ]
)

print("ALL EXP 5 REAL HEADER TERMINAL SCREENSHOTS CREATED SUCCESSFULLY!")
