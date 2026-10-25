import os
from PIL import Image

FRONTEND_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\frontend"
SCREENSHOTS_DIR = os.path.join(FRONTEND_DIR, "screenshots")
COMBINED_DIR = os.path.join(FRONTEND_DIR, "combined_screenshots")
os.makedirs(COMBINED_DIR, exist_ok=True)

def combine_vertically(top_img_path, bottom_img_path, out_filename):
    if not os.path.exists(top_img_path) or not os.path.exists(bottom_img_path):
        print(f"Skipping {out_filename} (Missing inputs)")
        return
        
    img_top = Image.open(top_img_path).convert("RGB")
    img_bottom = Image.open(bottom_img_path).convert("RGB")
    
    # Target uniform width (1200px)
    target_width = 1200
    
    w1, h1 = img_top.size
    h1_scaled = int(h1 * (target_width / w1))
    img_top_scaled = img_top.resize((target_width, h1_scaled), Image.Resampling.LANCZOS)
    
    w2, h2 = img_bottom.size
    h2_scaled = int(h2 * (target_width / w2))
    img_bottom_scaled = img_bottom.resize((target_width, h2_scaled), Image.Resampling.LANCZOS)
    
    combined = Image.new("RGB", (target_width, h1_scaled + h2_scaled + 20), (240, 243, 246))
    combined.paste(img_top_scaled, (0, 0))
    combined.paste(img_bottom_scaled, (0, h1_scaled + 20))
    
    out_path = os.path.join(COMBINED_DIR, out_filename)
    combined.save(out_path)
    print(f"Created combined UI + Terminal screenshot: {out_filename}")

if __name__ == "__main__":
    # Exp 5: Replication UI + Terminal
    exp5_term = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp5\screenshots\03_Primary_Crash_Failover_Promotion.png"
    exp5_ui = os.path.join(SCREENSHOTS_DIR, "04_Frontend_Replication_Exp5.png")
    combine_vertically(exp5_ui, exp5_term, "Exp5_Combined_UI_and_Terminal.png")
    
    # Exp 4: Election UI + Terminal
    exp4_term = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp4\screenshots\03_Election_Initiation_P3_Wins.png"
    exp4_ui = os.path.join(SCREENSHOTS_DIR, "05_Frontend_Bully_Election_Exp4.png")
    combine_vertically(exp4_ui, exp4_term, "Exp4_Combined_UI_and_Terminal.png")
    
    # Exp 3: Clock Sync UI + Terminal
    exp3_term = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp3\screenshots\02_Cristian_Client_Sync.png"
    exp3_ui = os.path.join(SCREENSHOTS_DIR, "06_Frontend_Clock_Sync_Exp3.png")
    combine_vertically(exp3_ui, exp3_term, "Exp3_Combined_UI_and_Terminal.png")
    
    # Exp 2: Loans List UI + Terminal
    exp2_term = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp2\screenshots\02_Client1_Submit_Loan.png"
    exp2_ui = os.path.join(SCREENSHOTS_DIR, "02_Frontend_Loans_Table.png")
    combine_vertically(exp2_ui, exp2_term, "Exp2_Combined_UI_and_Terminal.png")
