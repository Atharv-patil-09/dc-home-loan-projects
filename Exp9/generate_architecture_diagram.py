import os
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.patches as patches

EXP9_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp9"
OUT_PATH = os.path.join(EXP9_DIR, "architecture_diagram.png")

def generate_diagram():
    fig, ax = plt.subplots(figsize=(16.0, 9.2), dpi=300)
    ax.set_xlim(0, 16.0)
    ax.set_ylim(0, 9.2)
    ax.axis('off')

    # Background canvas
    fig.patch.set_facecolor('#F8FAFC')
    ax.set_facecolor('#F8FAFC')

    # Title block
    ax.text(8.0, 8.8, "Distributed Home Loan Banking System: MPI Collective Communication Architecture", 
            ha='center', va='center', fontsize=14.5, fontweight='bold', color='#0F172A', fontname='DejaVu Sans')
    ax.text(8.0, 8.4, "MPI_COMM_WORLD • MPI_Bcast (Tree) • MPI_Scatter • Parallel Worker Ranks • MPI_Gather • MPI_Reduce", 
            ha='center', va='center', fontsize=10, fontstyle='italic', color='#475569', fontname='DejaVu Sans')

    # -------------------------------------------------------------
    # 1. ROOT MASTER AUTHORITY (RANK 0) - Left (x=0.6 to 3.8)
    # -------------------------------------------------------------
    root_box = patches.FancyBboxPatch((0.6, 1.8), 3.2, 6.2, boxstyle="round,pad=0.12,rounding_size=0.15",
                                     facecolor="#EFF6FF", edgecolor="#2563EB", linewidth=1.8)
    ax.add_patch(root_box)
    ax.text(2.2, 7.6, "Root Master Authority", ha='center', va='center', fontsize=11, fontweight='bold', color='#1E40AF')
    ax.text(2.2, 7.3, "Process Rank 0 (HQ Mumbai)", ha='center', va='center', fontsize=8.5, color='#1E3A8A')
    ax.plot([0.8, 3.6], [7.1, 7.1], color='#93C5FD', lw=1)

    # Sub-block 1: Policy Repository
    p_box = patches.FancyBboxPatch((0.8, 5.3), 2.8, 1.5, boxstyle="round,pad=0.08,rounding_size=0.08",
                                  facecolor="#DBEAFE", edgecolor="#3B82F6", linewidth=1.2)
    ax.add_patch(p_box)
    ax.text(2.2, 6.45, "Underwriting Policy", ha='center', va='center', fontsize=9, fontweight='bold', color='#1D4ED8')
    ax.text(2.2, 5.9, "• Repo Rate: 6.50% | Spread: 2.15%\n• Effective ROI: 8.65% p.a.\n• Min CIBIL: 700 | Max DTI: 50%", 
            ha='center', va='center', fontsize=7.5, color='#1E293B', linespacing=1.2)

    # Sub-block 2: Master Workload Queue
    w_box = patches.FancyBboxPatch((0.8, 3.4), 2.8, 1.6, boxstyle="round,pad=0.08,rounding_size=0.08",
                                  facecolor="#DBEAFE", edgecolor="#3B82F6", linewidth=1.2)
    ax.add_patch(w_box)
    ax.text(2.2, 4.65, "Master Loan Batch", ha='center', va='center', fontsize=9, fontweight='bold', color='#1D4ED8')
    ax.text(2.2, 4.05, "8 Loan Applications (Rs 5.12 Cr)\nChunk 0: HL3001, HL3002\nChunk 1: HL3003, HL3004\nChunk 2: HL3005, HL3006\nChunk 3: HL3007, HL3008", 
            ha='center', va='center', fontsize=7.5, color='#1E293B', linespacing=1.2)

    # Sub-block 3: Final Consolidated Ledger
    g_box = patches.FancyBboxPatch((0.8, 2.05), 2.8, 1.1, boxstyle="round,pad=0.08,rounding_size=0.08",
                                  facecolor="#DCFCE7", edgecolor="#16A34A", linewidth=1.2)
    ax.add_patch(g_box)
    ax.text(2.2, 2.8, "Gathered Approval Register", ha='center', va='center', fontsize=8.5, fontweight='bold', color='#15803D')
    ax.text(2.2, 2.4, "Consolidated Underwriting Ledger\nMPI_SUM Exposure: Rs 5.12 Cr", ha='center', va='center', fontsize=7.2, color='#14532D')

    # -------------------------------------------------------------
    # 2. COLLECTIVE OPERATIONS BUS - Middle (x=4.4 to 6.2)
    # -------------------------------------------------------------
    coll_box = patches.FancyBboxPatch((4.4, 1.8), 2.0, 6.2, boxstyle="round,pad=0.12,rounding_size=0.15",
                                      facecolor="#FAF5FF", edgecolor="#9333EA", linewidth=1.8)
    ax.add_patch(coll_box)
    ax.text(5.4, 7.6, "MPI Collectives", ha='center', va='center', fontsize=11, fontweight='bold', color='#6B21A8')
    ax.text(5.4, 7.3, "MPI_COMM_WORLD", ha='center', va='center', fontsize=8.5, color='#581C87')
    ax.plot([4.6, 6.2], [7.1, 7.1], color='#D8B4FE', lw=1)

    ops = [
        ("1. MPI_Bcast", "Binomial Tree O(log N)\nBroadcast Policy to All", "#9333EA", 5.9),
        ("2. MPI_Scatter", "Workload Partitioning\nEqual Chunks to Ranks", "#2563EB", 4.5),
        ("3. MPI_Gather", "Gather Evaluations\nLocal Records -> Root", "#16A34A", 3.1),
        ("4. MPI_Reduce", "Parallel Reduction\nMPI_SUM & MPI_MAX", "#D97706", 2.0)
    ]
    for name, desc, col, y in ops:
        op_patch = patches.FancyBboxPatch((4.6, y - 0.4), 1.6, 0.9, boxstyle="round,pad=0.06,rounding_size=0.06",
                                         facecolor="#F3E8FF", edgecolor=col, linewidth=1.2)
        ax.add_patch(op_patch)
        ax.text(5.4, y + 0.25, name, ha='center', va='center', fontsize=8.5, fontweight='bold', color=col)
        ax.text(5.4, y - 0.1, desc, ha='center', va='center', fontsize=6.8, color='#3B0764', linespacing=1.1)

    # -------------------------------------------------------------
    # 3. DISTRIBUTED WORKER PROCESSES - Right (x=7.0 to 11.2)
    # -------------------------------------------------------------
    workers_box = patches.FancyBboxPatch((7.0, 1.4), 4.4, 6.6, boxstyle="round,pad=0.12,rounding_size=0.15",
                                         facecolor="#F0FDF4", edgecolor="#16A34A", linewidth=2.0)
    ax.add_patch(workers_box)
    ax.text(9.2, 7.6, "Distributed MPI Process Ranks", ha='center', va='center', fontsize=11, fontweight='bold', color='#15803D')
    ax.text(9.2, 7.3, "Concurrent Execution on Regional Branch Nodes", ha='center', va='center', fontsize=8.5, color='#166534')
    ax.plot([7.2, 11.2], [7.1, 7.1], color='#86EFAC', lw=1)

    ranks_info = [
        ("Rank 0 (Master Node)", "Local Partition: HL3001, HL3002 (Rs 1.20 Cr)\nComputed EMI: Rs 1,10,511 • Status: APPROVED", "#16A34A", 5.9),
        ("Rank 1 (Pune Branch)", "Local Partition: HL3003, HL3004 (Rs 2.20 Cr)\nComputed EMI: Rs 1,81,549 • Status: APPROVED", "#2563EB", 4.6),
        ("Rank 2 (Bangalore Branch)", "Local Partition: HL3005, HL3006 (Rs 1.52 Cr)\nComputed EMI: Rs 1,43,815 • Status: 1 REJECTED", "#9333EA", 3.3),
        ("Rank 3 (Delhi NCR Hub)", "Local Partition: HL3007, HL3008 (Rs 1.10 Cr)\nComputed EMI: Rs 96,508 • Status: APPROVED", "#D97706", 2.0)
    ]

    for title, detail, col, y in ranks_info:
        rbox = patches.FancyBboxPatch((7.2, y - 0.45), 4.0, 1.05, boxstyle="round,pad=0.06,rounding_size=0.06",
                                      facecolor="#FFFFFF", edgecolor=col, linewidth=1.4)
        ax.add_patch(rbox)
        ax.text(7.4, y + 0.35, title, ha='left', va='center', fontsize=8.5, fontweight='bold', color=col)
        ax.text(7.4, y - 0.05, detail, ha='left', va='center', fontsize=7.2, color='#1E293B', linespacing=1.2)

    # -------------------------------------------------------------
    # 4. PARALLEL REDUCTION & CONVERGENCE - Far Right (x=12.0 to 15.4)
    # -------------------------------------------------------------
    red_box = patches.FancyBboxPatch((12.0, 1.8), 3.4, 6.2, boxstyle="round,pad=0.12,rounding_size=0.15",
                                     facecolor="#FFFBEB", edgecolor="#D97706", linewidth=1.8)
    ax.add_patch(red_box)
    ax.text(13.7, 7.6, "Global Convergence", ha='center', va='center', fontsize=11, fontweight='bold', color='#B45309')
    ax.text(13.7, 7.3, "MPI_Reduce & Allgather", ha='center', va='center', fontsize=8.5, color='#92400E')
    ax.plot([12.2, 15.2], [7.1, 7.1], color='#FCD34D', lw=1)

    # Reducer 1: SUM
    r1 = patches.FancyBboxPatch((12.2, 5.2), 3.0, 1.5, boxstyle="round,pad=0.08,rounding_size=0.08",
                                facecolor="#FEF3C7", edgecolor="#F59E0B", linewidth=1.2)
    ax.add_patch(r1)
    ax.text(13.7, 6.35, "MPI_SUM Reduction", ha='center', va='center', fontsize=9, fontweight='bold', color='#B45309')
    ax.text(13.7, 5.75, "Total Disbursed Capital: Rs 5.12 Cr\nSum across Ranks 0..3\nZero Cross-talk Race Conditions", 
            ha='center', va='center', fontsize=7.5, color='#78350F', linespacing=1.2)

    # Reducer 2: MAX
    r2 = patches.FancyBboxPatch((12.2, 3.4), 3.0, 1.5, boxstyle="round,pad=0.08,rounding_size=0.08",
                                facecolor="#FEF3C7", edgecolor="#F59E0B", linewidth=1.2)
    ax.add_patch(r2)
    ax.text(13.7, 4.55, "MPI_MAX Reduction", ha='center', va='center', fontsize=9, fontweight='bold', color='#B45309')
    ax.text(13.7, 3.95, "Peak Single Loan: Rs 1.85 Cr\nContributed by Rank 1 (Rohan Mehta)\nAudit Flag: Tier-1 Metro Exposure", 
            ha='center', va='center', fontsize=7.5, color='#78350F', linespacing=1.2)

    # Reducer 3: ALLGATHER
    r3 = patches.FancyBboxPatch((12.2, 2.0), 3.0, 1.1, boxstyle="round,pad=0.08,rounding_size=0.08",
                                facecolor="#FEF3C7", edgecolor="#F59E0B", linewidth=1.2)
    ax.add_patch(r3)
    ax.text(13.7, 2.7, "MPI_Allgather Sync", ha='center', va='center', fontsize=8.5, fontweight='bold', color='#B45309')
    ax.text(13.7, 2.3, "Branch Health & Liquidity Sync\nAll ranks share uniform cluster state", 
            ha='center', va='center', fontsize=7.2, color='#78350F')

    # -------------------------------------------------------------
    # ARROWS & DATA FLOW
    # -------------------------------------------------------------
    arrow_props = dict(arrowstyle="->", lw=1.6, mutation_scale=14, color="#475569")
    bcast_arrow = dict(arrowstyle="->", lw=1.8, mutation_scale=15, color="#9333EA")
    scatter_arrow = dict(arrowstyle="->", lw=1.8, mutation_scale=15, color="#2563EB")
    gather_arrow = dict(arrowstyle="->", lw=1.8, mutation_scale=15, color="#16A34A")
    reduce_arrow = dict(arrowstyle="->", lw=1.8, mutation_scale=15, color="#D97706")

    # Root -> Collectives
    ax.annotate("", xy=(4.4, 6.0), xytext=(3.6, 6.0), arrowprops=bcast_arrow)
    ax.annotate("", xy=(4.4, 4.5), xytext=(3.6, 4.5), arrowprops=scatter_arrow)

    # Collectives -> Workers
    ax.annotate("", xy=(7.0, 5.9), xytext=(6.2, 5.9), arrowprops=bcast_arrow)
    ax.annotate("", xy=(7.0, 4.6), xytext=(6.2, 4.6), arrowprops=scatter_arrow)

    # Workers -> Gather
    ax.annotate("", xy=(6.2, 3.1), xytext=(7.0, 3.1), arrowprops=gather_arrow)
    ax.annotate("", xy=(3.6, 2.6), xytext=(4.4, 3.1), arrowprops=gather_arrow)

    # Workers -> Reduce
    ax.annotate("", xy=(12.0, 5.8), xytext=(11.2, 5.2), arrowprops=reduce_arrow)
    ax.annotate("", xy=(12.0, 4.0), xytext=(11.2, 4.0), arrowprops=reduce_arrow)

    plt.tight_layout()
    plt.savefig(OUT_PATH, format='png', dpi=300, facecolor=fig.get_facecolor(), edgecolor='none')
    plt.close()
    print("MPI Collective Architecture diagram generated successfully:", OUT_PATH)

if __name__ == "__main__":
    generate_diagram()
