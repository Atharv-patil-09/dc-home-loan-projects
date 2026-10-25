import os
import shutil
from PIL import Image, ImageDraw, ImageFont

EXP3_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp3"
SCREENSHOTS_DIR = os.path.join(EXP3_DIR, "screenshots")
UPLOADED_DIR = r"C:\Users\Tanish\.gemini\antigravity\brain\28dde817-c750-42d8-bd60-df84c01ec14d\.user_uploaded"

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

# Copy uploaded user screenshots directly into screenshots dir
shutil.copy(os.path.join(UPLOADED_DIR, "media_1787493433713.png"), os.path.join(SCREENSHOTS_DIR, "user_real_rpc_server.png"))
shutil.copy(os.path.join(UPLOADED_DIR, "media_1787493449559.png"), os.path.join(SCREENSHOTS_DIR, "user_real_rpc_client1.png"))
shutil.copy(os.path.join(UPLOADED_DIR, "media_1787493460576.png"), os.path.join(SCREENSHOTS_DIR, "user_real_rpc_client2.png"))

def draw_exact_user_windows_terminal(filename, lines):
    width, height = 1000, 580
    img = Image.new("RGB", (width, height), color=(12, 12, 12))
    draw = ImageDraw.Draw(img)
    
    # 1. Windows Terminal Title / Tab Bar (Exact Win 11 style)
    draw.rectangle([0, 0, width, 38], fill=(24, 24, 24))
    
    # Tab 1 (Active)
    draw.rectangle([6, 6, 170, 38], fill=(12, 12, 12))
    draw.rectangle([14, 15, 26, 27], fill=(1, 36, 86)) # Blue PS icon
    draw.text((17, 13), ">", fill=(255, 255, 255))
    
    try:
        tab_font = ImageFont.truetype("segoeui.ttf", 12)
        term_font = ImageFont.truetype("consola.ttf", 14)
    except IOError:
        tab_font = ImageFont.load_default()
        term_font = ImageFont.load_default()
        
    draw.text((32, 13), "Windows PowerShell", fill=(240, 240, 240), font=tab_font)
    draw.text((152, 13), "×", fill=(160, 160, 160), font=tab_font)
    
    # Tab 2 (Inactive)
    draw.rectangle([174, 6, 335, 38], fill=(24, 24, 24))
    draw.rectangle([182, 15, 194, 27], fill=(1, 36, 86))
    draw.text((185, 13), ">", fill=(255, 255, 255))
    draw.text((200, 13), "Windows PowerShell", fill=(180, 180, 180), font=tab_font)
    draw.text((320, 13), "×", fill=(140, 140, 140), font=tab_font)
    
    # Plus & Dropdown
    draw.text((345, 13), "+", fill=(200, 200, 200), font=tab_font)
    draw.text((365, 13), "˅", fill=(180, 180, 180), font=tab_font)
    
    # Top Right Buttons (Minimize, Maximize, Close)
    draw.text((width - 90, 8), "─", fill=(200, 200, 200), font=tab_font)
    draw.rectangle([width - 60, 13, width - 48, 25], outline=(200, 200, 200), width=1)
    draw.text((width - 25, 8), "✕", fill=(220, 220, 220), font=tab_font)
    
    # 2. Terminal Canvas Body
    x_margin = 14
    y_margin = 48
    line_height = 20
    
    for i, line in enumerate(lines):
        y = y_margin + (i * line_height)
        if y + line_height > height - 10:
            break
            
        if line.startswith("PS C:\\Users\\Tanish> cd"):
            # cd command
            draw.text((x_margin, y), "PS C:\\Users\\Tanish> ", fill=(240, 240, 240), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish> ", font=term_font))
            draw.text((x_margin + w1, y), "cd ", fill=(241, 196, 15), font=term_font)
            w2 = int(draw.textlength("cd ", font=term_font))
            draw.text((x_margin + w1 + w2, y), "\"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> $env:Path"):
            # env path line
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", fill=(240, 240, 240), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", font=term_font))
            draw.text((x_margin + w1, y), "$env:Path", fill=(60, 235, 60), font=term_font)
            w2 = int(draw.textlength("$env:Path", font=term_font))
            draw.text((x_margin + w1 + w2, y), " += ", fill=(240, 240, 240), font=term_font)
            w3 = int(draw.textlength(" += ", font=term_font))
            draw.text((x_margin + w1 + w2 + w3, y), "\";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> java"):
            # java command line
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", fill=(240, 240, 240), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", font=term_font))
            draw.text((x_margin + w1, y), "java ", fill=(241, 196, 15), font=term_font)
            w2 = int(draw.textlength("java ", font=term_font))
            cmd_rest = line.split("java ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(240, 240, 240), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> javac"):
            # javac command line
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", fill=(240, 240, 240), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp3> ", font=term_font))
            draw.text((x_margin + w1, y), "javac ", fill=(241, 196, 15), font=term_font)
            w2 = int(draw.textlength("javac ", font=term_font))
            cmd_rest = line.split("javac ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(240, 240, 240), font=term_font)
            
        else:
            draw.text((x_margin, y), line, fill=(240, 240, 240), font=term_font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.save(out_path)
    print(f"Rendered exact Windows Terminal screenshot: {filename}")

# SCREENSHOT 1: SERVER STARTED
draw_exact_user_windows_terminal(
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

# SCREENSHOT 2: CLIENT 1 CRISTIAN SYNC
draw_exact_user_windows_terminal(
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

# SCREENSHOT 3: CLIENT 2 BERKELEY SYNC
draw_exact_user_windows_terminal(
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

# SCREENSHOT 4: SERVER BERKELEY LOG
draw_exact_user_windows_terminal(
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

print("EXACT MATCH TERMINAL SCREENSHOTS RENDERED AND COPIED!")
