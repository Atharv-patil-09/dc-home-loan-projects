import os
import subprocess
import time

CHROME_BIN = r"C:\Program Files\Google\Chrome\Application\chrome.exe"
FRONTEND_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\frontend"
SCREENSHOTS_DIR = os.path.join(FRONTEND_DIR, "screenshots")
os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

HTML_BASE = "file:///" + os.path.join(FRONTEND_DIR, "index.html").replace("\\", "/")

views = [
    ("01_Frontend_Dashboard.png", "#dashboard"),
    ("02_Frontend_Loans_Table.png", "#loans"),
    ("03_Frontend_Loan_Details.png", "#loan-details"),
    ("04_Frontend_Replication_Exp5.png", "#replication"),
    ("05_Frontend_Bully_Election_Exp4.png", "#election"),
    ("06_Frontend_Clock_Sync_Exp3.png", "#clocksync"),
    ("07_Frontend_Monitoring_Exp2.png", "#monitoring"),
    ("08_Frontend_Cluster_Exp1.png", "#cluster"),
]

def capture_all():
    print("=== Capturing High-Resolution Frontend Web Screenshots via Chrome Headless ===")
    for filename, hash_route in views:
        target_url = HTML_BASE + hash_route
        out_file = os.path.join(SCREENSHOTS_DIR, filename)
        
        # Temporary small script to activate the specific tab before screenshot
        cmd = [
            CHROME_BIN,
            "--headless=new",
            "--disable-gpu",
            "--window-size=1280,720",
            f"--screenshot={out_file}",
            target_url
        ]
        res = subprocess.run(cmd, capture_output=True, text=True)
        print(f"Captured {filename} for route {hash_route} (Returncode: {res.returncode})")

if __name__ == "__main__":
    capture_all()
    print("All frontend screenshots saved to:", SCREENSHOTS_DIR)
