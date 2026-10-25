import os
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.patches as patches

EXP8_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp8"
OUT_PATH = os.path.join(EXP8_DIR, "architecture_diagram.png")

def generate_diagram():
    fig, ax = plt.subplots(figsize=(16.0, 9.2), dpi=300)
    ax.set_xlim(0, 16.0)
    ax.set_ylim(0, 9.2)
    ax.axis('off')

    # Background canvas
    fig.patch.set_facecolor('#F8FAFC')
    ax.set_facecolor('#F8FAFC')

    # Title block
    ax.text(8.0, 8.8, "Distributed Home Loan Banking System: MapReduce Parallel Processing Architecture", 
            ha='center', va='center', fontsize=14.5, fontweight='bold', color='#0F172A', fontname='DejaVu Sans')
    ax.text(8.0, 8.4, "HDFS Ingestion • Concurrent Mapping (M1..M3) • Hash Shuffle & Sort • Distributed Reduction (R1..R2) • Output part-r-*", 
            ha='center', va='center', fontsize=10, fontstyle='italic', color='#475569', fontname='DejaVu Sans')

    # -------------------------------------------------------------
    # 1. INPUT DATASET TIER (Left, x=0.5 to 2.8)
    # -------------------------------------------------------------
    ds_box = patches.FancyBboxPatch((0.5, 2.0), 2.3, 5.8, boxstyle="round,pad=0.12,rounding_size=0.15",
                                    facecolor="#EFF6FF", edgecolor="#2563EB", linewidth=1.8)
    ax.add_patch(ds_box)
    ax.text(1.65, 7.45, "HDFS Ingestion Tier", ha='center', va='center', fontsize=11, fontweight='bold', color='#1E40AF')
    ax.text(1.65, 7.15, "Raw Banking Big Data", ha='center', va='center', fontsize=8.5, color='#1E3A8A')
    ax.plot([0.7, 2.6], [6.95, 6.95], color='#93C5FD', lw=1)

    # Sub-blocks for Splits
    for i, (name, records) in enumerate([
        ("Split 0 (0-64MB)", "Records: HL1001..HL1004\nMumbai, Pune Apps"),
        ("Split 1 (64-128MB)", "Records: HL1005..HL1007\nBangalore, Hyd Apps"),
        ("Split 2 (128-192MB)", "Records: HL1008..HL1012\nDelhi, NCR Apps")
    ]):
        y = 5.6 - (i * 1.6)
        sbox = patches.FancyBboxPatch((0.7, y), 1.9, 1.25, boxstyle="round,pad=0.08,rounding_size=0.08",
                                     facecolor="#DBEAFE", edgecolor="#3B82F6", linewidth=1.2)
        ax.add_patch(sbox)
        ax.text(1.65, y + 0.95, name, ha='center', va='center', fontsize=8.5, fontweight='bold', color='#1D4ED8')
        ax.text(1.65, y + 0.45, records, ha='center', va='center', fontsize=7.5, color='#1E293B', linespacing=1.2)

    # -------------------------------------------------------------
    # 2. MAP PHASE TIER (x=3.6 to 6.3)
    # -------------------------------------------------------------
    map_box = patches.FancyBboxPatch((3.5, 1.6), 2.8, 6.2, boxstyle="round,pad=0.12,rounding_size=0.15",
                                     facecolor="#F0FDF4", edgecolor="#16A34A", linewidth=2.0)
    ax.add_patch(map_box)
    ax.text(4.9, 7.45, "Map Phase (Mappers)", ha='center', va='center', fontsize=11, fontweight='bold', color='#15803D')
    ax.text(4.9, 7.15, "Concurrent Worker Threads", ha='center', va='center', fontsize=8.5, color='#166534')
    ax.plot([3.7, 6.1], [6.95, 6.95], color='#86EFAC', lw=1)

    for i in range(3):
        y = 5.4 - (i * 1.7)
        mbox = patches.FancyBboxPatch((3.7, y), 2.4, 1.35, boxstyle="round,pad=0.08,rounding_size=0.08",
                                     facecolor="#DCFCE7", edgecolor="#22C55E", linewidth=1.2)
        ax.add_patch(mbox)
        ax.text(4.9, y + 1.05, f"Mapper-{i+1} Worker", ha='center', va='center', fontsize=9, fontweight='bold', color='#166534')
        ax.text(4.9, y + 0.55, f"Tokenizes & Emits Pairs:\n(LoanType, Amount)\n(Word, 1) | (City, Amount)", 
                ha='center', va='center', fontsize=7.2, color='#1E293B', linespacing=1.2)

    # -------------------------------------------------------------
    # 3. SHUFFLE & SORT / PARTITION TIER (x=7.1 to 10.1)
    # -------------------------------------------------------------
    shuf_box = patches.FancyBboxPatch((7.0, 1.6), 3.0, 6.2, boxstyle="round,pad=0.12,rounding_size=0.15",
                                      facecolor="#FAF5FF", edgecolor="#9333EA", linewidth=2.0)
    ax.add_patch(shuf_box)
    ax.text(8.5, 7.45, "Shuffle & Sort Engine", ha='center', va='center', fontsize=11, fontweight='bold', color='#7E22CE')
    ax.text(8.5, 7.15, "Hash Partition & Key Sort", ha='center', va='center', fontsize=8.5, color='#6B21A8')
    ax.plot([7.2, 9.8], [6.95, 6.95], color='#D8B4FE', lw=1)

    # Partitioner detail box
    p_box = patches.FancyBboxPatch((7.2, 4.4), 2.6, 2.3, boxstyle="round,pad=0.08,rounding_size=0.08",
                                   facecolor="#F3E8FF", edgecolor="#A855F7", linewidth=1.2)
    ax.add_patch(p_box)
    ax.text(8.5, 6.35, "Hash Partitioner", ha='center', va='center', fontsize=9, fontweight='bold', color='#6B21A8')
    ax.text(8.5, 5.35, "Partition Rule:\nhash(Key) % numReducers\n\nPartition 0: Standard, Afford\nPartition 1: Luxury, Express", 
            ha='center', va='center', fontsize=7.5, color='#3B0764', linespacing=1.25)

    # Combiner detail box
    c_box = patches.FancyBboxPatch((7.2, 2.0), 2.6, 2.0, boxstyle="round,pad=0.08,rounding_size=0.08",
                                   facecolor="#F3E8FF", edgecolor="#A855F7", linewidth=1.2)
    ax.add_patch(c_box)
    ax.text(8.5, 3.65, "In-Memory Combiner", ha='center', va='center', fontsize=9, fontweight='bold', color='#6B21A8')
    ax.text(8.5, 2.8, "Pre-aggregates map output\nlocally before network\ntransmission to reduce I/O", 
            ha='center', va='center', fontsize=7.2, color='#3B0764', linespacing=1.2)

    # -------------------------------------------------------------
    # 4. REDUCE PHASE TIER (x=10.9 to 13.3)
    # -------------------------------------------------------------
    red_box = patches.FancyBboxPatch((10.8, 2.0), 2.5, 5.8, boxstyle="round,pad=0.12,rounding_size=0.15",
                                     facecolor="#FFFBEB", edgecolor="#D97706", linewidth=2.0)
    ax.add_patch(red_box)
    ax.text(12.05, 7.45, "Reduce Phase", ha='center', va='center', fontsize=11, fontweight='bold', color='#B45309')
    ax.text(12.05, 7.15, "Aggregation Workers", ha='center', va='center', fontsize=8.5, color='#92400E')
    ax.plot([11.0, 13.1], [6.95, 6.95], color='#FDE68A', lw=1)

    for i, (rname, keys) in enumerate([
        ("Reducer-01", "Processes Partition 0:\n• Standard Home Loan\n• Affordable Housing\n• Mumbai, Pune Totals"),
        ("Reducer-02", "Processes Partition 1:\n• Luxury Villa Loan\n• Express Home Loan\n• Bangalore, Delhi Totals")
    ]):
        y = 4.8 - (i * 2.3)
        rbox = patches.FancyBboxPatch((11.0, y), 2.1, 1.85, boxstyle="round,pad=0.08,rounding_size=0.08",
                                     facecolor="#FEF3C7", edgecolor="#F59E0B", linewidth=1.2)
        ax.add_patch(rbox)
        ax.text(12.05, y + 1.55, rname, ha='center', va='center', fontsize=9, fontweight='bold', color='#92400E')
        ax.text(12.05, y + 0.8, keys, ha='center', va='center', fontsize=7.2, color='#1E293B', linespacing=1.2)

    # -------------------------------------------------------------
    # 5. OUTPUT TIER (x=14.0 to 15.6)
    # -------------------------------------------------------------
    out_box = patches.FancyBboxPatch((13.9, 2.0), 1.7, 5.8, boxstyle="round,pad=0.12,rounding_size=0.15",
                                     facecolor="#FEF2F2", edgecolor="#DC2626", linewidth=1.8)
    ax.add_patch(out_box)
    ax.text(14.75, 7.45, "HDFS Output", ha='center', va='center', fontsize=10, fontweight='bold', color='#991B1B')
    ax.text(14.75, 7.15, "Analytics Files", ha='center', va='center', fontsize=8, color='#7F1D1D')
    ax.plot([14.1, 15.4], [6.95, 6.95], color='#FECACA', lw=1)

    for i, fname in enumerate(["part-r-00000", "part-r-00001"]):
        y = 4.8 - (i * 2.3)
        fbox = patches.FancyBboxPatch((14.1, y), 1.3, 1.85, boxstyle="round,pad=0.08,rounding_size=0.08",
                                     facecolor="#FEE2E2", edgecolor="#EF4444", linewidth=1.2)
        ax.add_patch(fbox)
        ax.text(14.75, y + 1.5, fname, ha='center', va='center', fontsize=8, fontweight='bold', color='#991B1B')
        ax.text(14.75, y + 0.75, "Aggregated\nLoan Portfolio\n& Audit Counts", 
                ha='center', va='center', fontsize=6.8, color='#1E293B', linespacing=1.15)

    # -------------------------------------------------------------
    # 6. CONNECTING ARROWS
    # -------------------------------------------------------------
    # Splits -> Mappers
    for i in range(3):
        y_src = 6.2 - (i * 1.6)
        y_dst = 6.1 - (i * 1.7)
        ax.annotate("", xy=(3.7, y_dst), xytext=(2.6, y_src),
                    arrowprops=dict(arrowstyle="->", color="#2563EB", lw=1.6))

    # Mappers -> Shuffle
    for i in range(3):
        y_src = 6.1 - (i * 1.7)
        ax.annotate("", xy=(7.2, 5.5), xytext=(6.1, y_src),
                    arrowprops=dict(arrowstyle="->", color="#16A34A", lw=1.4))

    # Shuffle -> Reducers
    ax.annotate("", xy=(11.0, 5.7), xytext=(9.8, 5.6),
                arrowprops=dict(arrowstyle="->", color="#9333EA", lw=1.8))
    ax.text(10.4, 6.0, "Partition 0", ha='center', va='bottom', fontsize=7.2, fontweight='bold', color='#7E22CE')

    ax.annotate("", xy=(11.0, 3.4), xytext=(9.8, 4.6),
                arrowprops=dict(arrowstyle="->", color="#9333EA", lw=1.8))
    ax.text(10.4, 3.6, "Partition 1", ha='center', va='bottom', fontsize=7.2, fontweight='bold', color='#7E22CE')

    # Reducers -> HDFS Output
    ax.annotate("", xy=(14.1, 5.7), xytext=(13.1, 5.7),
                arrowprops=dict(arrowstyle="->", color="#D97706", lw=1.6))
    ax.annotate("", xy=(14.1, 3.4), xytext=(13.1, 3.4),
                arrowprops=dict(arrowstyle="->", color="#D97706", lw=1.6))

    plt.tight_layout()
    plt.savefig(OUT_PATH, dpi=300, bbox_inches='tight')
    plt.close()
    print(f"MapReduce Architecture diagram generated: {OUT_PATH}")

if __name__ == '__main__':
    generate_diagram()
