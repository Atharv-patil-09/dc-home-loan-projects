import os
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.patches as patches

EXP7_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp7"
OUT_PATH = os.path.join(EXP7_DIR, "architecture_diagram.png")

def generate_diagram():
    fig, ax = plt.subplots(figsize=(15.5, 9.0), dpi=300)
    ax.set_xlim(0, 15.5)
    ax.set_ylim(0, 9.0)
    ax.axis('off')

    # Background canvas
    fig.patch.set_facecolor('#F8FAFC')
    ax.set_facecolor('#F8FAFC')

    # Title block
    ax.text(7.75, 8.6, "Distributed Home Loan Banking System: Load Balancing & Concurrency Control Architecture", 
            ha='center', va='center', fontsize=14.5, fontweight='bold', color='#0F172A', fontname='DejaVu Sans')
    ax.text(7.75, 8.2, "Adaptive Routing (RR, WRR, LC) • Token Bucket Rate Limiting (HTTP 429) • Cluster Saturation & Load Shedding (HTTP 503)", 
            ha='center', va='center', fontsize=10, fontstyle='italic', color='#475569', fontname='DejaVu Sans')

    # -------------------------------------------------------------
    # 1. CLIENT TIER (Left)
    # -------------------------------------------------------------
    client_box = patches.FancyBboxPatch((0.5, 2.5), 2.8, 5.2, boxstyle="round,pad=0.12,rounding_size=0.15",
                                        facecolor="#EFF6FF", edgecolor="#2563EB", linewidth=1.8)
    ax.add_patch(client_box)
    ax.text(1.9, 7.35, "Client Ingestion Tier", ha='center', va='center', fontsize=11.5, fontweight='bold', color='#1E40AF')
    ax.text(1.9, 7.05, "(Borrowers & Automated Portals)", ha='center', va='center', fontsize=8.5, color='#1E3A8A')
    ax.plot([0.7, 3.1], [6.85, 6.85], color='#93C5FD', lw=1)

    ax.text(1.9, 5.3, "Concurrent Traffic Sources:\n\n"
                      "• Retail Portal (HL-7001..7006)\n"
                      "  Normal Home Loan Apps\n\n"
                      "• High-Value Commercial\n"
                      "  Appraisal (HL-9001, 1800ms)\n\n"
                      "• AutoScript Client (192.168.1.105)\n"
                      "  Rapid Burst (10 req / 100ms)\n\n"
                      "• Festive Surge Influx\n"
                      "  22 Concurrent Applications",
            ha='center', va='center', fontsize=8, color='#1E293B', linespacing=1.35)

    # -------------------------------------------------------------
    # 2. LOAD BALANCER & GATEWAY TIER (Center)
    # -------------------------------------------------------------
    lb_box = patches.FancyBboxPatch((4.2, 1.6), 5.6, 6.1, boxstyle="round,pad=0.15,rounding_size=0.18",
                                   facecolor="#F0FDF4", edgecolor="#16A34A", linewidth=2.2)
    ax.add_patch(lb_box)
    ax.text(7.0, 7.35, "Central Banking Load Balancer & Gateway", ha='center', va='center', fontsize=12, fontweight='bold', color='#15803D')
    ax.text(7.0, 7.05, "Port: 8000 | Reverse Proxy & Traffic Orchestrator", ha='center', va='center', fontsize=9, color='#166534')
    ax.plot([4.4, 9.6], [6.85, 6.85], color='#86EFAC', lw=1.2)

    # Sub-modules inside Load Balancer
    # Module A: Token Bucket Rate Limiter
    rl_sub = patches.FancyBboxPatch((4.5, 5.2), 5.0, 1.45, boxstyle="round,pad=0.08,rounding_size=0.1",
                                    facecolor="#FEF2F2", edgecolor="#DC2626", linewidth=1.4)
    ax.add_patch(rl_sub)
    ax.text(7.0, 6.35, "Token Bucket Rate Limiter (DDoS / Burst Guard)", ha='center', va='center', fontsize=9.5, fontweight='bold', color='#991B1B')
    ax.text(7.0, 5.75, "Burst Capacity = 5 Tokens | Refill Rate = 2.0 tokens/sec\nExceeded Limit -> HTTP 429 TOO_MANY_REQUESTS Throttling", 
            ha='center', va='center', fontsize=8, color='#7F1D1D', linespacing=1.25)

    # Module B: Adaptive Scheduling Engine
    sch_sub = patches.FancyBboxPatch((4.5, 3.4), 5.0, 1.6, boxstyle="round,pad=0.08,rounding_size=0.1",
                                     facecolor="#F8FAFC", edgecolor="#475569", linewidth=1.4)
    ax.add_patch(sch_sub)
    ax.text(7.0, 4.65, "Multi-Algorithm Routing Dispatcher", ha='center', va='center', fontsize=9.5, fontweight='bold', color='#0F172A')
    ax.text(7.0, 3.95, "• Round Robin: Sequential (idx % N)\n"
                       "• Weighted Round Robin: Proportional by node specs (3:2:1)\n"
                       "• Least Connections: min(active_conns) for heavy loan isolation\n"
                       "• IP Hash: Client session sticky affinity", 
            ha='center', va='center', fontsize=7.8, color='#334155', linespacing=1.25)

    # Module C: Capacity Guard & Bounded Buffer Queue
    cg_sub = patches.FancyBboxPatch((4.5, 1.9), 5.0, 1.3, boxstyle="round,pad=0.08,rounding_size=0.1",
                                    facecolor="#FFFBEB", edgecolor="#D97706", linewidth=1.4)
    ax.add_patch(cg_sub)
    ax.text(7.0, 2.85, "Cluster Capacity Guard & Load Shedding Engine", ha='center', va='center', fontsize=9.5, fontweight='bold', color='#92400E')
    ax.text(7.0, 2.3, "Cluster Max Concurrency = 12 Slots | Bounded FIFO Queue = 5 Slots\nBuffer Overflow -> HTTP 503 SERVICE_UNAVAILABLE Load Shedding", 
            ha='center', va='center', fontsize=8, color='#78350F', linespacing=1.25)

    # -------------------------------------------------------------
    # 3. BACKEND BANKING SERVER NODES TIER (Right)
    # -------------------------------------------------------------
    # Node 1
    n1_box = patches.FancyBboxPatch((10.8, 5.6), 4.2, 2.1, boxstyle="round,pad=0.1,rounding_size=0.12",
                                    facecolor="#FAF5FF", edgecolor="#9333EA", linewidth=1.8)
    ax.add_patch(n1_box)
    ax.text(12.9, 7.35, "BankServer-01 (High-Performance)", ha='center', va='center', fontsize=10.5, fontweight='bold', color='#6B21A8')
    ax.text(12.9, 7.05, "Port: 8001 | Weight: 3 | Max Concurrency: 4", ha='center', va='center', fontsize=8.5, color='#7E22CE')
    ax.plot([11.0, 14.8], [6.85, 6.85], color='#D8B4FE', lw=1)
    ax.text(12.9, 6.25, "• Core High-Throughput Processing Engine\n"
                        "• Processes 50% of Weighted Traffic (3/6)\n"
                        "• Hardware Concurrency Limit: 4 Active Conns\n"
                        "• Handles Heavy Commercial Loans (HL-9001)", 
            ha='center', va='center', fontsize=7.5, color='#1E293B', linespacing=1.25)

    # Node 2
    n2_box = patches.FancyBboxPatch((10.8, 3.2), 4.2, 2.1, boxstyle="round,pad=0.1,rounding_size=0.12",
                                    facecolor="#EFF6FF", edgecolor="#2563EB", linewidth=1.8)
    ax.add_patch(n2_box)
    ax.text(12.9, 4.95, "BankServer-02 (Mid-Tier Regional)", ha='center', va='center', fontsize=10.5, fontweight='bold', color='#1E40AF')
    ax.text(12.9, 4.65, "Port: 8002 | Weight: 2 | Max Concurrency: 4", ha='center', va='center', fontsize=8.5, color='#2563EB')
    ax.plot([11.0, 14.8], [4.45, 4.45], color='#93C5FD', lw=1)
    ax.text(12.9, 3.85, "• Regional Processing Workhorse\n"
                        "• Processes 33.3% of Weighted Traffic (2/6)\n"
                        "• Hardware Concurrency Limit: 4 Active Conns\n"
                        "• Handles Secondary Appraisals (HL-9002)", 
            ha='center', va='center', fontsize=7.5, color='#1E293B', linespacing=1.25)

    # Node 3
    n3_box = patches.FancyBboxPatch((10.8, 0.8), 4.2, 2.1, boxstyle="round,pad=0.1,rounding_size=0.12",
                                    facecolor="#FEF3C7", edgecolor="#D97706", linewidth=1.8)
    ax.add_patch(n3_box)
    ax.text(12.9, 2.55, "BankServer-03 (Edge Branch Server)", ha='center', va='center', fontsize=10.5, fontweight='bold', color='#B45309')
    ax.text(12.9, 2.25, "Port: 8003 | Weight: 1 | Max Concurrency: 4", ha='center', va='center', fontsize=8.5, color='#D97706')
    ax.plot([11.0, 14.8], [2.05, 2.05], color='#FDE68A', lw=1)
    ax.text(12.9, 1.45, "• Edge Absorber & Standby Node\n"
                        "• Processes 16.7% of Weighted Traffic (1/6)\n"
                        "• Least Connections Rapid Offloader (FAST-01..05)\n"
                        "• Heartbeat Health Checked (Ping every 500ms)", 
            ha='center', va='center', fontsize=7.5, color='#1E293B', linespacing=1.25)

    # -------------------------------------------------------------
    # 4. ARROWS & INTERACTIONS
    # -------------------------------------------------------------
    # Clients -> Load Balancer
    ax.annotate("", xy=(4.2, 5.5), xytext=(3.3, 5.5),
                arrowprops=dict(arrowstyle="->", color="#2563EB", lw=2.4))
    ax.text(3.75, 5.75, "Loan Requests\n(Port 8000)", ha='center', va='bottom', fontsize=8, fontweight='bold', color='#2563EB')

    # Load Balancer -> Client (Throttled 429 / 503 Rejections)
    ax.annotate("", xy=(3.3, 4.2), xytext=(4.2, 4.2),
                arrowprops=dict(arrowstyle="->", color="#DC2626", lw=2.0, ls="--"))
    ax.text(3.75, 3.85, "HTTP 429 / 503\nRejections", ha='center', va='top', fontsize=7.5, fontweight='bold', color='#DC2626')

    # Load Balancer -> Node 1
    ax.annotate("", xy=(10.8, 6.7), xytext=(9.8, 5.8),
                arrowprops=dict(arrowstyle="->", color="#9333EA", lw=2.0))
    ax.text(10.3, 6.5, "Weight 3\n(50%)", ha='center', va='bottom', fontsize=7.5, fontweight='bold', color='#9333EA')

    # Load Balancer -> Node 2
    ax.annotate("", xy=(10.8, 4.3), xytext=(9.8, 4.3),
                arrowprops=dict(arrowstyle="->", color="#2563EB", lw=2.0))
    ax.text(10.3, 4.5, "Weight 2\n(33%)", ha='center', va='bottom', fontsize=7.5, fontweight='bold', color='#2563EB')

    # Load Balancer -> Node 3
    ax.annotate("", xy=(10.8, 1.9), xytext=(9.8, 2.8),
                arrowprops=dict(arrowstyle="->", color="#D97706", lw=2.0))
    ax.text(10.3, 2.1, "Weight 1\n(17%)", ha='center', va='top', fontsize=7.5, fontweight='bold', color='#D97706')

    # Health Check Ping loop on Node 3
    ax.annotate("", xy=(9.8, 2.1), xytext=(10.8, 1.2),
                arrowprops=dict(arrowstyle="->", color="#16A34A", lw=1.2, ls=":"))
    ax.text(10.3, 1.3, "Health Check / Ping", ha='center', va='top', fontsize=6.8, color='#16A34A')

    plt.tight_layout()
    plt.savefig(OUT_PATH, dpi=300, bbox_inches='tight')
    plt.close()
    print(f"Architecture diagram generated: {OUT_PATH}")

if __name__ == '__main__':
    generate_diagram()
