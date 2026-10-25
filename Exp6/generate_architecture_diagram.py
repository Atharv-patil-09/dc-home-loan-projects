import os
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.patches as patches

EXP6_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp6"
OUT_PATH = os.path.join(EXP6_DIR, "architecture_diagram.png")

def generate_diagram():
    fig, ax = plt.subplots(figsize=(15, 8.5), dpi=300)
    ax.set_xlim(0, 15)
    ax.set_ylim(0, 8.5)
    ax.axis('off')

    # Background canvas
    fig.patch.set_facecolor('#F8FAFC')
    ax.set_facecolor('#F8FAFC')

    # Title block
    ax.text(7.5, 8.1, "Distributed Home Loan System: Data Consistency & Multi-Node Replication Architecture", 
            ha='center', va='center', fontsize=14, fontweight='bold', color='#0F172A', fontname='DejaVu Sans')
    ax.text(7.5, 7.7, "N = 3 Cluster | Primary Coordinator, Fast Replica (1000ms), High-Latency WAN Replica (2400ms) | CAP & PACELC Trade-offs", 
            ha='center', va='center', fontsize=9.5, fontstyle='italic', color='#475569', fontname='DejaVu Sans')

    # Component 1: Client / Loan Portal
    client_box = patches.FancyBboxPatch((0.6, 3.2), 2.7, 3.8, boxstyle="round,pad=0.12,rounding_size=0.15",
                                        facecolor="#EFF6FF", edgecolor="#2563EB", linewidth=1.8)
    ax.add_patch(client_box)
    ax.text(1.95, 6.6, "Home Loan Client", ha='center', va='center', fontsize=11.5, fontweight='bold', color='#1E40AF')
    ax.text(1.95, 6.25, "(Customer / Web Portal)", ha='center', va='center', fontsize=9, color='#1E3A8A')
    ax.plot([0.8, 3.1], [6.0, 6.0], color='#93C5FD', lw=1)
    ax.text(1.95, 4.8, "Client Operations:\n• Submit Loan (HL1001)\n• Pay EMI (v1 -> v2)\n• Read Status (HL1002)\n• Quorum Audit (R=2)\n• SHA-256 Verification", 
            ha='center', va='center', fontsize=8.5, color='#1E293B', linespacing=1.4)

    # Component 2: Primary Coordinator (Node 1)
    n1_box = patches.FancyBboxPatch((5.4, 2.6), 4.0, 4.4, boxstyle="round,pad=0.12,rounding_size=0.15",
                                    facecolor="#F0FDF4", edgecolor="#16A34A", linewidth=2.0)
    ax.add_patch(n1_box)
    ax.text(7.4, 6.6, "Node 1: Primary Coordinator", ha='center', va='center', fontsize=11.5, fontweight='bold', color='#15803D')
    ax.text(7.4, 6.25, "Port: 7001 | Local Latency: 0ms", ha='center', va='center', fontsize=9, color='#166534')
    ax.plot([5.6, 9.2], [6.0, 6.0], color='#86EFAC', lw=1)
    ax.text(7.4, 4.6, "Replication & Consistency Engine:\n• Write Quorum Controller (W=1, 2, 3)\n• Read Quorum Coordinator (R=1, 2)\n• Monotonic Sequence Generator (#1..#5)\n• Version Vector Generator (v1, v2, v3)\n• In-Memory Active Loan Ledger\n• SHA-256 Ledger State Fingerprinting", 
            ha='center', va='center', fontsize=8.5, color='#1E293B', linespacing=1.4)

    # Component 3: Replica 1 (Node 2 - Fast)
    n2_box = patches.FancyBboxPatch((11.4, 4.7), 3.2, 2.3, boxstyle="round,pad=0.12,rounding_size=0.15",
                                    facecolor="#FAF5FF", edgecolor="#9333EA", linewidth=1.8)
    ax.add_patch(n2_box)
    ax.text(13.0, 6.65, "Node 2: Backup Replica", ha='center', va='center', fontsize=10.5, fontweight='bold', color='#7E22CE')
    ax.text(13.0, 6.35, "Port: 7002 | Sim Delay: 1000ms", ha='center', va='center', fontsize=8.5, color='#6B21A8')
    ax.plot([11.6, 14.4], [6.15, 6.15], color='#D8B4FE', lw=1)
    ax.text(13.0, 5.4, "• Priority Sequence Buffer\n• Replicated Ledger Mirror\n• Fast Quorum Partner (W=2)\n• Auto-Promotion Standby", 
            ha='center', va='center', fontsize=8, color='#1E293B', linespacing=1.35)

    # Component 4: Replica 2 (Node 3 - Slow)
    n3_box = patches.FancyBboxPatch((11.4, 1.8), 3.2, 2.3, boxstyle="round,pad=0.12,rounding_size=0.15",
                                    facecolor="#FFFBEB", edgecolor="#D97706", linewidth=1.8)
    ax.add_patch(n3_box)
    ax.text(13.0, 3.75, "Node 3: Backup Replica", ha='center', va='center', fontsize=10.5, fontweight='bold', color='#B45309')
    ax.text(13.0, 3.45, "Port: 7003 | Sim Delay: 2400ms (WAN)", ha='center', va='center', fontsize=8.5, color='#92400E')
    ax.plot([11.6, 14.4], [3.25, 3.25], color='#FDE68A', lw=1)
    ax.text(13.0, 2.5, "• High-Latency Link Simulation\n• Stale Read Source in Eventual\n• Asynchronous Drain Target\n• Eventual Hash Synchronization", 
            ha='center', va='center', fontsize=8, color='#1E293B', linespacing=1.35)

    # Arrows between Client and Node 1
    # 1. Write Request
    ax.annotate("", xy=(5.4, 5.5), xytext=(3.3, 5.5),
                arrowprops=dict(arrowstyle="->", color="#2563EB", lw=2.2))
    ax.text(4.35, 5.7, "1. Write Req", ha='center', va='bottom', fontsize=8.5, fontweight='bold', color='#2563EB')
    
    # 4. ACK / Response
    ax.annotate("", xy=(3.3, 4.3), xytext=(5.4, 4.3),
                arrowprops=dict(arrowstyle="->", color="#16A34A", lw=2.2))
    ax.text(4.35, 3.95, "4. Client ACK", ha='center', va='bottom', fontsize=8.5, fontweight='bold', color='#16A34A')

    # Arrows between Node 1 and Node 2
    # 2a. Replicate
    ax.annotate("", xy=(11.4, 5.8), xytext=(9.4, 5.8),
                arrowprops=dict(arrowstyle="->", color="#7E22CE", lw=2.0))
    ax.text(10.4, 6.0, "2a. Replicate (Seq #)\nFast (1000ms)", ha='center', va='bottom', fontsize=8, fontweight='bold', color='#7E22CE')

    # 3a. ACK
    ax.annotate("", xy=(9.4, 5.2), xytext=(11.4, 5.2),
                arrowprops=dict(arrowstyle="->", color="#7E22CE", lw=1.5, ls="--"))
    ax.text(10.4, 4.85, "3a. Node ACK", ha='center', va='top', fontsize=7.5, color='#7E22CE')

    # Arrows between Node 1 and Node 3
    # 2b. Replicate
    ax.annotate("", xy=(11.4, 3.1), xytext=(9.4, 3.6),
                arrowprops=dict(arrowstyle="->", color="#D97706", lw=2.0))
    ax.text(10.4, 3.65, "2b. Replicate (Seq #)\nSlow (2400ms)", ha='center', va='bottom', fontsize=8, fontweight='bold', color='#D97706')

    # 3b. ACK
    ax.annotate("", xy=(9.4, 3.1), xytext=(11.4, 2.5),
                arrowprops=dict(arrowstyle="->", color="#D97706", lw=1.5, ls="--"))
    ax.text(10.4, 2.55, "3b. Node ACK (Delayed)", ha='center', va='top', fontsize=7.5, color='#D97706')

    # Stale read direct arrow: Client -> Node 3
    ax.annotate("", xy=(11.4, 2.0), xytext=(3.3, 3.5),
                arrowprops=dict(arrowstyle="->", color="#DC2626", lw=1.8, ls="dotted"))
    ax.text(6.8, 1.85, "Stale Read Path (R=1 in Eventual Mode: Queries Lagging Node 3 -> Returns Missing / Old v1)", 
            ha='center', va='center', fontsize=8.0, fontweight='bold', color='#DC2626',
            bbox=dict(boxstyle="round,pad=0.25", facecolor="#FEE2E2", edgecolor="#DC2626", lw=0.9))

    # Bottom Summary Box: Consistency Formulas & Rules
    summary_box = patches.FancyBboxPatch((0.6, 0.2), 14.0, 0.9, boxstyle="round,pad=0.08,rounding_size=0.1",
                                         facecolor="#F1F5F9", edgecolor="#64748B", linewidth=1.2)
    ax.add_patch(summary_box)
    ax.text(7.5, 0.75, "REPLICATION MODELS & QUORUM RULES APPLIED (Cluster Size N = 3)", 
            ha='center', va='center', fontsize=9.8, fontweight='bold', color='#0F172A')
    ax.text(7.5, 0.45, "• Strong: W = 3, R = 1 (Wait all: ~2496ms, 0% Stale)  |  • Quorum: W = 2, R = 2 (R + W = 4 > 3: ~1012ms, Fresh Quorum Read Guarantee)\n• Eventual: W = 1, R = 1 (Async Queue: ~5ms, Temporary Stale Reads until Queue Drains)  |  • State Validation: Cryptographic SHA-256 Hash Auditing", 
            ha='center', va='center', fontsize=8.0, color='#334155')

    plt.tight_layout()
    plt.savefig(OUT_PATH, dpi=300, bbox_inches='tight')
    plt.close()
    print("Perfect architecture diagram successfully generated at:", OUT_PATH)

if __name__ == "__main__":
    generate_diagram()
