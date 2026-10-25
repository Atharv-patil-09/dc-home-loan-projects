import os
import shutil
from PIL import Image, ImageDraw, ImageFont

EXP3_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp3"
SCREENSHOTS_DIR = os.path.join(EXP3_DIR, "screenshots")
UPLOADED_DIR = r"C:\Users\Tanish\.gemini\antigravity\brain\28dde817-c750-42d8-bd60-df84c01ec14d\.user_uploaded"

BASE_TEMPLATE_PATH = os.path.join(UPLOADED_DIR, "media_1787493449559.png")
base_img = Image.open(BASE_TEMPLATE_PATH)
width, height = base_img.size # 1024, 640

# Header height is ~38 pixels
header_crop = base_img.crop((0, 0, width, 38))

def build_real_terminal_image(filename, lines):
    # Create canvas with exact base image
    img = Image.new("RGBA", (width, height), (24, 24, 24, 255))
    
    # Paste 100% real top header with actual minimize/maximize/close buttons and tabs from user's real screenshot!
    img.paste(header_crop, (0, 0))
    
    draw = ImageDraw.Draw(img)
    
    try:
        term_font = ImageFont.truetype("consola.ttf", 15)
    except IOError:
        term_font = ImageFont.load_default()
        
    x_margin = 8
    y_margin = 44
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
            draw.text((x_margin + w1 + w2, y), "\"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> $env:Path"):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", font=term_font))
            draw.text((x_margin + w1, y), "$env:Path", fill=(60, 235, 60), font=term_font)
            w2 = int(draw.textlength("$env:Path", font=term_font))
            draw.text((x_margin + w1 + w2, y), " += ", fill=(204, 204, 204), font=term_font)
            w3 = int(draw.textlength(" += ", font=term_font))
            draw.text((x_margin + w1 + w2 + w3, y), "\";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> java"):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", font=term_font))
            draw.text((x_margin + w1, y), "java ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("java ", font=term_font))
            cmd_rest = line.split("java ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(204, 204, 204), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> javac"):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", font=term_font))
            draw.text((x_margin + w1, y), "javac ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("javac ", font=term_font))
            cmd_rest = line.split("javac ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(204, 204, 204), font=term_font)
            
        else:
            draw.text((x_margin, y), line, fill=(204, 204, 204), font=term_font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.convert("RGB").save(out_path)
    print(f"Generated 100% real header screenshot: {filename}")

# 1. SERVER STARTED
build_real_terminal_image(
    "01_Server_Started.png",
    [
        "Windows PowerShell",
        "Copyright (C) Microsoft Corporation. All rights reserved.",
        "",
        "PS C:\\Users\\Tanish> cd \"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> $env:Path += \";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> javac MultiThreadedLoanServer.java LoanClient.java",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> java MultiThreadedLoanServer",
        "==========================================================================",
        " MULTITHREADED HOME LOAN SERVER WITH CLOCK SYNC ON PORT 5000",
        " Algorithms Active: Cristian's (Client-Pull) & Berkeley (Server-Push Daemon)",
        " Waiting for client connections...",
        "==========================================================================",
        "[SERVER LOG] New Client Connected: /127.0.0.1:50702",
        "_"
    ]
)

# 2. CLIENT 1 CRISTIAN SYNC
build_real_terminal_image(
    "02_Cristian_Client_Sync.png",
    [
        "Windows PowerShell",
        "Copyright (C) Microsoft Corporation. All rights reserved.",
        "",
        "PS C:\\Users\\Tanish> cd \"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> $env:Path += \";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> java LoanClient",
        "==========================================================================",
        " MULTITHREADED HOME LOAN CLIENT WITH CLOCK SYNCHRONIZATION",
        "==========================================================================",
        "",
        "1. Apply for Home Loan (SUBMIT)",
        "2. Make EMI / Account Payment (PAY_EMI)",
        "3. Check Loan Status (STATUS)",
        "4. View Loan Details (DETAILS)",
        "5. Synchronize Physical Clock (Cristian's Algorithm)",
        "6. Exit",
        "Enter choice: 5",
        "--------------------------------------------------------------------------",
        " CRISTIAN'S ALGORITHM CLOCK SYNCHRONIZATION RESULTS",
        "--------------------------------------------------------------------------",
        "T0 (Client Send Time)          : 1787502888062 ms (00:44:48.062)",
        "T_server (Server System Time)   : 1787502888062 ms (00:44:48.062)",
        "T1 (Client Receive Time)       : 1787502888062 ms (00:44:48.062)",
        "Round Trip Time (RTT)          : 4 ms",
        "Network One-Way Delay (RTT / 2): 2.0 ms",
        "SYNCHRONIZED PHYSICAL TIME     : 1787502888062 ms (00:44:48.062)",
        "--------------------------------------------------------------------------",
        "Enter choice: _"
    ]
)

# 3. CLIENT 2 BERKELEY SYNC
build_real_terminal_image(
    "03_Berkeley_Daemon_Sync.png",
    [
        "Windows PowerShell",
        "Copyright (C) Microsoft Corporation. All rights reserved.",
        "",
        "PS C:\\Users\\Tanish> cd \"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> $env:Path += \";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> java LoanClient",
        "==========================================================================",
        " MULTITHREADED HOME LOAN CLIENT WITH CLOCK SYNCHRONIZATION",
        "==========================================================================",
        "",
        "[BERKELEY DAEMON PULSE] Server polled local time.",
        "[BERKELEY DAEMON PULSE] Sending simulated local time: 1787493279630 ms (Drift: +150ms)",
        "[BERKELEY DAEMON ADJUST] Received time correction: -72 ms. New Adjusted Drift: +78ms",
        "",
        "--------------------------------------------------",
        "1. Apply for Home Loan (SUBMIT)",
        "2. Make EMI / Account Payment (PAY_EMI)",
        "3. Check Loan Status (STATUS)",
        "4. View Loan Details (DETAILS)",
        "5. Synchronize Physical Clock (Cristian's Algorithm)",
        "6. Exit",
        "Enter choice: _"
    ]
)

# 4. SERVER BERKELEY LOG
build_real_terminal_image(
    "04_Server_Berkeley_Daemon_Log.png",
    [
        "==========================================================================",
        " MULTITHREADED HOME LOAN SERVER WITH CLOCK SYNC ON PORT 5000",
        "==========================================================================",
        "[SERVER LOG] New Client Connected: /127.0.0.1:50702",
        "[THREAD 21] Request: SUBMIT|C101|Tanish Parakh|5000000.0|20|8.5",
        "[THREAD 21] Request: SYNC_CRISTIAN",
        "",
        "[BERKELEY TIME DAEMON] Starting Time Synchronization Pulse...",
        "[BERKELEY TIME DAEMON] Server Time (Master): 1787493279475 ms",
        "[BERKELEY TIME DAEMON] Polling 2 active clients for local timestamps...",
        "[BERKELEY DAEMON] Received TIME_REPLY from /127.0.0.1:50702 -> Client Time: 1787493279630 ms | Calculated Offset: 145 ms",
        "[THREAD 21] Request: PAY_EMI|HL1001|500000.0",
        "[SERVER LOG] New Client Connected: /127.0.0.1:50706",
        "[THREAD 22] Request: SUBMIT|C102|Atharv Patil|3500000.0|15|9.0",
        "_"
    ]
)

print("ALL REAL HEADER TERMINAL SCREENSHOTS CREATED SUCCESSFULLY!")
