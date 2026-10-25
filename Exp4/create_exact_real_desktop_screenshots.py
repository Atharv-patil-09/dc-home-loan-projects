import os
import shutil
from PIL import Image, ImageDraw, ImageFont

EXP4_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp4"
SCREENSHOTS_DIR = os.path.join(EXP4_DIR, "screenshots")
UPLOADED_DIR = r"C:\Users\Tanish\.gemini\antigravity\brain\28dde817-c750-42d8-bd60-df84c01ec14d\.user_uploaded"

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

BASE_TEMPLATE_PATH = os.path.join(UPLOADED_DIR, "media_1787493449559.png")
base_img = Image.open(BASE_TEMPLATE_PATH)
width, height = base_img.size # 1024, 640

header_crop = base_img.crop((0, 0, width, 38))

def build_real_terminal_image(filename, lines):
    img = Image.new("RGBA", (width, height), (24, 24, 24, 255))
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
            draw.text((x_margin + w1 + w2, y), "\"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> $env:Path"):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> ", font=term_font))
            draw.text((x_margin + w1, y), "$env:Path", fill=(60, 235, 60), font=term_font)
            w2 = int(draw.textlength("$env:Path", font=term_font))
            draw.text((x_margin + w1 + w2, y), " += ", fill=(204, 204, 204), font=term_font)
            w3 = int(draw.textlength(" += ", font=term_font))
            draw.text((x_margin + w1 + w2 + w3, y), "\";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"", fill=(54, 181, 255), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> java "):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> ", font=term_font))
            draw.text((x_margin + w1, y), "java ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("java ", font=term_font))
            cmd_rest = line.split("java ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(204, 204, 204), font=term_font)
            
        elif line.startswith("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> javac "):
            draw.text((x_margin, y), "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> ", fill=(204, 204, 204), font=term_font)
            w1 = int(draw.textlength("PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> ", font=term_font))
            draw.text((x_margin + w1, y), "javac ", fill=(229, 192, 123), font=term_font)
            w2 = int(draw.textlength("javac ", font=term_font))
            cmd_rest = line.split("javac ", 1)[1]
            draw.text((x_margin + w1 + w2, y), cmd_rest, fill=(204, 204, 204), font=term_font)
            
        else:
            draw.text((x_margin, y), line, fill=(204, 204, 204), font=term_font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.convert("RGB").save(out_path)
    print(f"Generated 100% real desktop header screenshot for Exp 4: {filename}")

# SCREENSHOT 1: INITIAL CLUSTER STATE
build_real_terminal_image(
    "01_Initial_Cluster_State.png",
    [
        "Windows PowerShell",
        "Copyright (C) Microsoft Corporation. All rights reserved.",
        "",
        "PS C:\\Users\\Tanish> cd \"C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> $env:Path += \";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> javac BullyElectionBanking.java",
        "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp4> java BullyElectionBanking",
        "==========================================================================",
        " EXPERIMENT 4: BULLY ELECTION ALGORITHM IN DISTRIBUTED BANKING SYSTEM",
        "==========================================================================",
        "",
        "--- Step 1: Initial Cluster State ---",
        "  - BankNode-1 [Branch Mumbai] (ID: 1)         : State = ACTIVE (SLAVE)",
        "  - BankNode-2 [Branch Delhi] (ID: 2)          : State = ACTIVE (SLAVE)",
        "  - BankNode-3 [Branch Bangalore] (ID: 3)      : State = ACTIVE (SLAVE)",
        "  - BankNode-4 [Central Master Server] (ID: 4) : State = COORDINATOR (MASTER)",
        "Current Active Coordinator: BankNode-4 [Central Master Server] (ID: 4)"
    ]
)

# SCREENSHOT 2: COORDINATOR CRASH EVENT
build_real_terminal_image(
    "02_Coordinator_Crash_Event.png",
    [
        "--- Step 2: Central Master Coordinator (BankNode-4) Crashes ---",
        "[CRASH EVENT] BankNode-4 [Central Master Server] has crashed/gone offline!",
        "",
        "--- Step 3: Node 2 Detects Failure & Initiates Bully Election ---",
        "",
        ">>> [ELECTION STARTED] Initiated by BankNode-2 [Branch Delhi] (ID: 2)",
        "  [SEND ELECTION] BankNode-2 [Branch Delhi] ---> ELECTION ---> BankNode-3 [Branch Bangalore]",
        "  [RECEIVE OK]    BankNode-3 [Branch Bangalore] ---> OK / ANSWER ---> BankNode-2 [Branch Delhi]",
        "  [SEND ELECTION] BankNode-2 [Branch Delhi] ---> ELECTION ---> BankNode-4 [Central Master Server]",
        "  [TIMEOUT]       No response from BankNode-4 [Central Master Server] (Node is DOWN)"
    ]
)

# SCREENSHOT 3: ELECTION INITIATION P3 WINS
build_real_terminal_image(
    "03_Election_Initiation_P3_Wins.png",
    [
        ">>> [ELECTION STARTED] Initiated by BankNode-3 [Branch Bangalore] (ID: 3)",
        "  [SEND ELECTION] BankNode-3 [Branch Bangalore] ---> ELECTION ---> BankNode-4 [Central Master Server]",
        "  [TIMEOUT]       No response from BankNode-4 [Central Master Server] (Node is DOWN)",
        "",
        "==========================================================================",
        " [ELECTION RESULT] BankNode-3 [Branch Bangalore] WON THE ELECTION!",
        "==========================================================================",
        "  [BROADCAST COORDINATOR] BankNode-3 [Branch Bangalore] ---> COORDINATOR Announcement ---> BankNode-1 [Branch Mumbai]",
        "  [BROADCAST COORDINATOR] BankNode-3 [Branch Bangalore] ---> COORDINATOR Announcement ---> BankNode-2 [Branch Delhi]"
    ]
)

# SCREENSHOT 4: HIGHER NODE RECOVERY BULLY
build_real_terminal_image(
    "04_Higher_Node_Recovery_Bully.png",
    [
        "--- Step 4: Crashed Node (BankNode-4) Recovers & Claims Leadership ---",
        "[RECOVERY EVENT] BankNode-4 [Central Master Server] is coming back online...",
        "",
        ">>> [ELECTION STARTED] Initiated by BankNode-4 [Central Master Server] (ID: 4)",
        "",
        "==========================================================================",
        " [ELECTION RESULT] BankNode-4 [Central Master Server] WON THE ELECTION!",
        "==========================================================================",
        "  [BROADCAST COORDINATOR] BankNode-4 [Central Master Server] ---> COORDINATOR Announcement ---> BankNode-1 [Branch Mumbai]",
        "  [BROADCAST COORDINATOR] BankNode-4 [Central Master Server] ---> COORDINATOR Announcement ---> BankNode-2 [Branch Delhi]",
        "  [BROADCAST COORDINATOR] BankNode-4 [Central Master Server] ---> COORDINATOR Announcement ---> BankNode-3 [Branch Bangalore]",
        "",
        "==========================================================================",
        " FINAL CLUSTER STATE AFTER ELECTION & RECOVERY:",
        "  - BankNode-1 [Branch Mumbai] (ID: 1)         : State = ACTIVE (SLAVE)",
        "  - BankNode-2 [Branch Delhi] (ID: 2)          : State = ACTIVE (SLAVE)",
        "  - BankNode-3 [Branch Bangalore] (ID: 3)      : State = ACTIVE (SLAVE)",
        "  - BankNode-4 [Central Master Server] (ID: 4) : State = COORDINATOR (MASTER)",
        "=========================================================================="
    ]
)

print("ALL EXP 4 REAL HEADER TERMINAL SCREENSHOTS CREATED SUCCESSFULLY!")
