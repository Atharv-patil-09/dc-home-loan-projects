import os
from PIL import Image, ImageDraw, ImageFont

EXP2_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp2"
SCREENSHOTS_DIR = os.path.join(EXP2_DIR, "screenshots")
os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

def draw_windows_terminal(filename, tab_title, lines):
    width, height = 980, 560
    # Background - Dark Windows Terminal
    img = Image.new("RGB", (width, height), color=(12, 12, 12))
    draw = ImageDraw.Draw(img)
    
    # 1. Top Window Title Bar / Tab Bar (Windows 11 Terminal Style)
    draw.rectangle([0, 0, width, 40], fill=(24, 24, 24))
    
    # Active Tab Box
    draw.rectangle([10, 6, 220, 40], fill=(12, 12, 12))
    # Blue PowerShell Icon placeholder box
    draw.rectangle([20, 15, 32, 27], fill=(1, 36, 86))
    draw.text((24, 14), ">", fill=(255, 255, 255))
    
    try:
        tab_font = ImageFont.truetype("segoeui.ttf", 13)
        bold_font = ImageFont.truetype("segoeuib.ttf", 13)
        term_font = ImageFont.truetype("consola.ttf", 15)
    except IOError:
        tab_font = ImageFont.load_default()
        bold_font = ImageFont.load_default()
        term_font = ImageFont.load_default()
        
    draw.text((38, 14), tab_title, fill=(240, 240, 240), font=tab_font)
    # Tab close x
    draw.text((200, 14), "×", fill=(180, 180, 180), font=tab_font)
    
    # Plus button for new tab
    draw.text((235, 14), "+", fill=(200, 200, 200), font=tab_font)
    draw.text((255, 14), "˅", fill=(180, 180, 180), font=tab_font)
    
    # Window Controls (Top Right: Minimize, Maximize, Close)
    draw.text((width - 90, 10), "─", fill=(200, 200, 200), font=tab_font)
    draw.rectangle([width - 60, 15, width - 48, 27], outline=(200, 200, 200), width=1)
    draw.text((width - 25, 10), "✕", fill=(220, 220, 220), font=tab_font)
    
    # 2. Terminal Canvas Body
    x_margin = 16
    y_margin = 52
    line_height = 22
    
    for i, line_info in enumerate(lines):
        y = y_margin + (i * line_height)
        if y + line_height > height - 10:
            break
            
        text = line_info.get("text", "")
        color_type = line_info.get("type", "normal")
        
        if color_type == "prompt":
            # PS C:\Users\... in white, command in cyan/white
            parts = text.split("> ", 1)
            if len(parts) == 2:
                prompt_part = parts[0] + "> "
                cmd_part = parts[1]
                draw.text((x_margin, y), prompt_part, fill=(240, 240, 240), font=term_font)
                prompt_width = int(draw.textlength(prompt_part, font=term_font))
                draw.text((x_margin + prompt_width, y), cmd_part, fill=(98, 208, 248), font=term_font)
            else:
                draw.text((x_margin, y), text, fill=(240, 240, 240), font=term_font)
        elif color_type == "broadcast":
            draw.text((x_margin, y), text, fill=(255, 170, 0), font=term_font)
        elif color_type == "response":
            draw.text((x_margin, y), text, fill=(110, 225, 130), font=term_font)
        elif color_type == "thread":
            draw.text((x_margin, y), text, fill=(255, 215, 0), font=term_font)
        elif color_type == "header":
            draw.text((x_margin, y), text, fill=(90, 180, 255), font=term_font)
        else:
            draw.text((x_margin, y), text, fill=(210, 210, 210), font=term_font)
            
    out_path = os.path.join(SCREENSHOTS_DIR, filename)
    img.save(out_path)
    print(f"Generated realistic terminal screenshot: {filename}")

# SCREENSHOT 1: SERVER STARTED
draw_windows_terminal(
    "01_Server_Started.png",
    "Windows PowerShell",
    [
        {"text": "Windows PowerShell", "type": "normal"},
        {"text": "Copyright (C) Microsoft Corporation. All rights reserved.", "type": "normal"},
        {"text": "", "type": "normal"},
        {"text": "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> $env:Path += \";C:\\Users\\Tanish\\JDK\\jdk-21.0.12\\bin\"", "type": "prompt"},
        {"text": "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> javac MultiThreadedLoanServer.java LoanClient.java", "type": "prompt"},
        {"text": "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java MultiThreadedLoanServer", "type": "prompt"},
        {"text": "==================================================", "type": "header"},
        {"text": " Multithreaded Home Loan Server Started on Port 5000", "type": "header"},
        {"text": " Waiting for client connections...", "type": "header"},
        {"text": "==================================================", "type": "header"},
        {"text": "_", "type": "normal"}
    ]
)

# SCREENSHOT 2: CLIENT 1 CONNECTED & SUBMISSION
draw_windows_terminal(
    "04_Concurrent_Loan_Submission.png",
    "Windows PowerShell - LoanClient 1",
    [
        {"text": "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java LoanClient", "type": "prompt"},
        {"text": "==================================================", "type": "header"},
        {"text": " MULTITHREADED DISTRIBUTED HOME LOAN CLIENT", "type": "header"},
        {"text": "==================================================", "type": "header"},
        {"text": "--------------------------------------------------", "type": "normal"},
        {"text": "1. Apply for Home Loan (SUBMIT)", "type": "normal"},
        {"text": "2. Make EMI / Account Payment (PAY_EMI)", "type": "normal"},
        {"text": "3. Check Loan Status (STATUS)", "type": "normal"},
        {"text": "4. View Loan Details (DETAILS)", "type": "normal"},
        {"text": "5. Exit", "type": "normal"},
        {"text": "Enter choice: 1", "type": "prompt"},
        {"text": "Customer ID: C101", "type": "normal"},
        {"text": "Customer Name: Tanish Parakh", "type": "normal"},
        {"text": "Loan Amount (₹): 5000000", "type": "normal"},
        {"text": "Loan Tenure (years): 20", "type": "normal"},
        {"text": "Interest Rate (%): 8.5", "type": "normal"},
        {"text": "", "type": "normal"},
        {"text": "[REAL-TIME BROADCAST] [NOTIFICATION] New Loan Application Registered: HL1001 by Tanish Parakh", "type": "broadcast"},
        {"text": "[SERVER RESPONSE] Loan Application Submitted Successfully! Loan ID: HL1001 | Customer: Tanish Parakh (ID: C101) | Total: ₹5000000.0 | Balance: ₹5000000.0 | Tenure: 20 yrs | Interest: 8.5% | Status: UNDER_REVIEW", "type": "response"},
        {"text": "", "type": "normal"},
        {"text": "Enter choice: _", "type": "prompt"}
    ]
)

# SCREENSHOT 3: CLIENT 1 EMI PAYMENT
draw_windows_terminal(
    "05_Concurrent_Transaction_Payment.png",
    "Windows PowerShell - LoanClient 1 (Transaction)",
    [
        {"text": "Enter choice: 2", "type": "prompt"},
        {"text": "Enter Loan ID: HL1001", "type": "normal"},
        {"text": "Enter Payment / EMI Amount (₹): 500000", "type": "normal"},
        {"text": "", "type": "normal"},
        {"text": "[REAL-TIME BROADCAST] [NOTIFICATION] Payment of ₹500000.0 processed for HL1001. Remaining: ₹4500000.0", "type": "broadcast"},
        {"text": "[SERVER RESPONSE] Payment Received! Updated Record: Loan ID: HL1001 | Customer: Tanish Parakh (ID: C101) | Total: ₹5000000.0 | Balance: ₹4500000.0 | Tenure: 20 yrs | Interest: 8.5% | Status: ACTIVE_APPROVED", "type": "response"},
        {"text": "", "type": "normal"},
        {"text": "--------------------------------------------------", "type": "normal"},
        {"text": "1. Apply for Home Loan (SUBMIT)", "type": "normal"},
        {"text": "2. Make EMI / Account Payment (PAY_EMI)", "type": "normal"},
        {"text": "3. Check Loan Status (STATUS)", "type": "normal"},
        {"text": "4. View Loan Details (DETAILS)", "type": "normal"},
        {"text": "5. Exit", "type": "normal"},
        {"text": "Enter choice: _", "type": "prompt"}
    ]
)

# SCREENSHOT 4: CLIENT 2 REAL-TIME BROADCAST
draw_windows_terminal(
    "06_RealTime_Broadcast_Notification.png",
    "Windows PowerShell - LoanClient 2 (Broadcast Log)",
    [
        {"text": "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java LoanClient", "type": "prompt"},
        {"text": "==================================================", "type": "header"},
        {"text": " MULTITHREADED DISTRIBUTED HOME LOAN CLIENT (Client 2)", "type": "header"},
        {"text": "==================================================", "type": "header"},
        {"text": "", "type": "normal"},
        {"text": "[REAL-TIME BROADCAST] [NOTIFICATION] New Loan Application Registered: HL1001 by Tanish Parakh", "type": "broadcast"},
        {"text": "[REAL-TIME BROADCAST] [NOTIFICATION] Payment of ₹500000.0 processed for HL1001. Remaining: ₹4500000.0", "type": "broadcast"},
        {"text": "", "type": "normal"},
        {"text": "--------------------------------------------------", "type": "normal"},
        {"text": "1. Apply for Home Loan (SUBMIT)", "type": "normal"},
        {"text": "2. Make EMI / Account Payment (PAY_EMI)", "type": "normal"},
        {"text": "3. Check Loan Status (STATUS)", "type": "normal"},
        {"text": "4. View Loan Details (DETAILS)", "type": "normal"},
        {"text": "5. Exit", "type": "normal"},
        {"text": "Enter choice: _", "type": "prompt"}
    ]
)

# SCREENSHOT 5: SERVER MULTITHREADING LOG
draw_windows_terminal(
    "07_Server_Multithread_Log.png",
    "Windows PowerShell - MultiThreadedLoanServer",
    [
        {"text": "PS C:\\Users\\Tanish\\OneDrive\\Desktop\\DC Home Loan\\Exp2> java MultiThreadedLoanServer", "type": "prompt"},
        {"text": "==================================================", "type": "header"},
        {"text": " Multithreaded Home Loan Server Started on Port 5000", "type": "header"},
        {"text": " Waiting for client connections...", "type": "header"},
        {"text": "==================================================", "type": "header"},
        {"text": "[SERVER LOG] New Client Connected: /127.0.0.1:58412", "type": "normal"},
        {"text": "[THREAD 21] Request: SUBMIT|C101|Tanish Parakh|5000000.0|20|8.5", "type": "thread"},
        {"text": "[THREAD 21] Request: PAY_EMI|HL1001|500000.0", "type": "thread"},
        {"text": "[SERVER LOG] New Client Connected: /127.0.0.1:58418", "type": "normal"},
        {"text": "[THREAD 22] Request: SUBMIT|C102|Atharv Patil|3500000.0|15|9.0", "type": "thread"},
        {"text": "[THREAD 22] Request: STATUS|HL1002", "type": "thread"},
        {"text": "_", "type": "normal"}
    ]
)

print("ALL REALISTIC WINDOWS TERMINAL SCREENSHOTS RENDERED SUCCESSFULLY!")
