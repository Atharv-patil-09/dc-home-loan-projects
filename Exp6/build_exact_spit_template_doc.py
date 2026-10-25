import os
import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT

from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Image, Table, TableStyle, HRFlowable, KeepTogether
)
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle

EXP6_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp6"
PDF_PATH = os.path.join(EXP6_DIR, "DC_Lab_Exp_6_DataConsistency.pdf")
DOCX_PATH = os.path.join(EXP6_DIR, "DC_Lab_Exp_6_DataConsistency.docx")

# ONLY BACKEND JAVA CODE (NO FRONTEND CODE IN CODE SECTION)
SYSTEM_CODE = open(os.path.join(EXP6_DIR, "DistributedLoanConsistencySystem.java"), "r", encoding="utf-8").read()

SCREENSHOTS_DIR = os.path.join(EXP6_DIR, "screenshots")
LOGO_PATH = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp2\spit_logo.png"
ARCH_PATH = os.path.join(EXP6_DIR, "architecture_diagram.png")

def draw_page_border(canvas, doc):
    canvas.saveState()
    canvas.setStrokeColor(colors.black)
    canvas.setLineWidth(1)
    canvas.rect(30, 30, doc.pagesize[0] - 60, doc.pagesize[1] - 60)
    canvas.restoreState()

# List of all 11 output figures: Real Java Swing UI + Real Console Screenshots
SHOT_ITEMS = [
    # Part A: Real Java Swing Frontend Screenshots
    ("01_Real_Java_UI_Consistency_Dashboard.png", "Figure 1: BANKCORE Multi-Node Consistency & Health Dashboard (Java Swing) -- Real-time Node Telemetry (Node 1 Primary, Node 2 Fast Standby, Node 3 WAN Standby) and Synchronized Loan Ledger"),
    ("02_Real_Java_UI_Strong_Consistency_Submit.png", "Figure 2: BANKCORE Strong Consistency Application Interface (Java Swing) -- Synchronous Loan Submission for HL1001 (Gitesh Patil, Rs 62,00,000) with W=3 All-Node Confirmation (Latency: 2,496 ms)"),
    ("03_Real_Java_UI_Eventual_Stale_Read.png", "Figure 3: BANKCORE Eventual Consistency & Stale Read Monitor (Java Swing) -- Fast Write (5 ms) for HL1002 (Atharv Patil), Stale Read Detection on Delayed Node 3, and Queue Convergence"),
    ("04_Real_Java_UI_Quorum_Resolution.png", "Figure 4: BANKCORE Quorum Consistency & Conflict Resolution (Java Swing) -- Loan HL1001 EMI Payment (Rs 59,045), Fast Majority Write (1,012 ms), and R=2 Quorum Overlap Resolution to Version v3"),
    
    # Part B: Real Desktop-Header Terminal Execution Screenshots
    ("01_Strong_Consistency_W3.png", "Figure 5: Experiment 6.1 Console -- Strong Consistency Execution (W = 3, Latency = 2,496 ms, Synchronized SHA-256 Hash 7A8C23BE8F24 across All Replicas)"),
    ("02_Eventual_Consistency_Stale_Read.png", "Figure 6: Experiment 6.2 Console -- Eventual Consistency Stale Read Detection (W = 1, Latency = 5 ms, Immediate Read on Delayed Node 3 Returns Not Found)"),
    ("03_Eventual_Consistency_Convergence.png", "Figure 7: Experiment 6.3 Console -- Eventual Consistency Replica Convergence (Replication Queue Drained after 3,000 ms, All Nodes Reach Hash 719A65EE7038)"),
    ("04_Quorum_Consistency_W2_R2.png", "Figure 8: Experiment 6.4 Console -- Quorum Consistency Resolution (W = 2, R = 2, Latency = 1,012 ms, Quorum Read R=2 Resolves to Fresh Version v3)"),
    ("05_Update_Ordering_Sequence_Buffering.png", "Figure 9: Experiment 6.5 Console -- Update Ordering with Sequence Buffering (Out-of-order Packet #5 Buffered and Sequentially Drained upon Arrival of Packet #4)"),
    ("06_Primary_Crash_Durability_Loss.png", "Figure 10: Experiment 6.6 Console -- Primary Crash & Durability Data Loss (Catastrophic Failure of Primary in Eventual Mode Results in Loss of Unpropagated Writes HL1004 & HL1005)"),
    ("07_Replication_Model_Benchmark_Results.png", "Figure 11: Benchmark Summary & Empirical Replication Model Comparison Benchmark (Comprehensive Analysis of Latency, Stale Reads, Convergence, and Durability)")
]

# -------------------------------------------------------------
# 1. BUILD EXACT SPIT TEMPLATE DOCX
# -------------------------------------------------------------
def build_exact_spit_docx():
    print("Generating Exact SPIT Template DOCX report for Exp 6...")
    doc = docx.Document()
    
    sections = doc.sections
    for section in sections:
        section.top_margin = Inches(0.5)
        section.bottom_margin = Inches(0.5)
        section.left_margin = Inches(0.5)
        section.right_margin = Inches(0.5)

    # Header Table: Logo + Text
    header_table = doc.add_table(rows=1, cols=2)
    header_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    header_cells = header_table.rows[0].cells
    
    p_logo = header_cells[0].paragraphs[0]
    if os.path.exists(LOGO_PATH):
        p_logo.add_run().add_picture(LOGO_PATH, width=Inches(1.2))
    p_logo.alignment = WD_ALIGN_PARAGRAPH.CENTER
    
    p_head = header_cells[1].paragraphs[0]
    p_head.alignment = WD_ALIGN_PARAGRAPH.CENTER
    r1 = p_head.add_run("Bhartiya Vidya Bhavans\n")
    r1.bold = True
    r1.font.size = Pt(13)
    r1.font.name = 'Arial'
    
    r2 = p_head.add_run("Sardar Patel Institute of Technology\n")
    r2.bold = True
    r2.font.size = Pt(13)
    r2.font.name = 'Arial'

    r3 = p_head.add_run("Munshi Nagar – Andheri West – 400058")
    r3.font.size = Pt(11)
    r3.font.name = 'Arial'

    p_div = doc.add_paragraph("—" * 65)
    p_div.alignment = WD_ALIGN_PARAGRAPH.CENTER
    
    # Metadata Table
    meta_table = doc.add_table(rows=1, cols=2)
    meta_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    hdr_cells = meta_table.rows[0].cells
    
    hdr_cells[0].paragraphs[0].add_run("Name: Atharv Patil (UID: 2024300165)\n          Tanish Parakh (UID: 2024300157)\n          Gitesh Patil (UID: 2024300166)\nBranch: C.E").bold = True
    hdr_cells[1].paragraphs[0].add_run("DIVISION: C\nBATCH: C1\nDATE: 28/09/2026").bold = True
    
    p_div2 = doc.add_paragraph("—" * 65)
    p_div2.alignment = WD_ALIGN_PARAGRAPH.CENTER

    # Main Template Table with specific 9 section rows
    table = doc.add_table(rows=9, cols=1)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    
    # Row 0: Experiment No.
    c0 = table.rows[0].cells[0]
    p0 = c0.paragraphs[0]
    r0 = p0.add_run("Experiment No.: 6")
    r0.bold = True
    r0.font.size = Pt(11)

    # Row 1: Aim
    c1 = table.rows[1].cells[0]
    p1 = c1.paragraphs[0]
    r1_title = p1.add_run("Aim : ")
    r1_title.bold = True
    r1_title.font.size = Pt(11)
    r1_body = p1.add_run(
        "To study how different replication strategies affect data consistency in the Distributed Home Loan Banking System, "
        "comparing Strong Consistency, Quorum Consistency, and Eventual Consistency, and analyzing write latency, stale reads, "
        "replica synchronization/convergence, ordering of updates with sequence buffering, behavior when replicas are delayed, "
        "and data durability during primary crash failures."
    )
    r1_body.font.size = Pt(10)

    # Row 2: Theory
    c2 = table.rows[2].cells[0]
    p2 = c2.paragraphs[0]
    r2_title = p2.add_run("Theory :\n")
    r2_title.bold = True
    r2_title.font.size = Pt(11)
    r2_body = p2.add_run(
        "In a Distributed Home Loan Banking System, data replication is the practice of maintaining redundant copies of core banking data "
        "— including home loan application records, customer KYC profiles, loan approval states, and EMI payment transaction ledgers — "
        "across geographically distributed server nodes. Replication is essential to guarantee high availability and fault tolerance. "
        "However, keeping multiple replicas synchronized across unreliable network links with variable latency introduces fundamental trade-offs between "
        "consistency, availability, and latency (governed by the CAP and PACELC theorems).\n\n"
        "1. Replication Strategies and Consistency Models:\n\n"
        "• Strong Consistency (Synchronous Master-Slave Replication, W = N):\n"
        "  Under Strong Consistency, every state-changing write (e.g., submitting a loan application or registering an EMI repayment) is executed on the primary coordinator "
        "  and synchronously replicated to all N replicas. The coordinator blocks and does not acknowledge the client until all N nodes confirm successful persistence. "
        "  This guarantees linearizability: any client reading from any node is guaranteed to observe the latest acknowledged write (0% stale reads). "
        "  The severe downside is write latency, which is strictly governed by the slowest replica on the highest-latency link (e.g., ~2496 ms across WAN).\n\n"
        "• Quorum Consistency (Tunable Leader-Follower Quorum, R + W > N):\n"
        "  In a cluster of N nodes, Quorum Consistency allows configuring a Write Quorum (W) and Read Quorum (R). As long as the Pigeonhole Principle "
        "  condition R + W > N is satisfied (e.g., in our N=3 cluster, W=2 and R=2 yielding 2 + 2 = 4 > 3), the read set and write set must overlap by at least one node. "
        "  By tagging each update with a monotonically increasing version number (v1, v2, v3) or timestamp, the client queries R replicas in parallel and selects the highest "
        "  version number, guaranteeing strong read consistency while allowing writes to commit as soon as a fast majority responds (~1012 ms), avoiding delays from slow WAN nodes.\n\n"
        "• Eventual Consistency (Asynchronous Replication, W = 1):\n"
        "  Under Eventual Consistency, the primary coordinator updates its local database and immediately acknowledges the client (latency ~5 ms). "
        "  Updates are placed into asynchronous background queues and forwarded lazily to replica nodes. While this maximizes write throughput and availability, "
        "  it introduces a window of vulnerability: clients querying a delayed replica before the queue drains will experience stale reads (missing or outdated loan balances). "
        "  However, if no new updates occur, all replicas eventually converge to an identical state.\n\n"
        "2. Summary Comparison of Consistency Models (Cluster Size N = 3):\n\n"
        "  +----------------------+--------------+-------------+---------------+-------------------+----------------------------+\n"
        "  | Replication Model    | Write Quorum | Read Quorum | Write Latency | Stale Read Window | Primary Crash Durability   |\n"
        "  +----------------------+--------------+-------------+---------------+-------------------+----------------------------+\n"
        "  | Strong Consistency   | W = 3 (All)  | R = 1       | High (~2500ms)| None (0%)         | Zero Data Loss             |\n"
        "  | Quorum Consistency   | W = 2 (Maj)  | R = 2 (Maj) | Med (~1000ms) | None (R+W > N)    | Zero Data Loss (Quorum Sur)|\n"
        "  | Eventual Consistency | W = 1 (Local)| R = 1       | Min (~5ms)    | Yes (~2400ms lag) | Potential Loss of In-Flight|\n"
        "  +----------------------+--------------+-------------+---------------+-------------------+----------------------------+\n\n"
        "3. Concrete Consistency Concepts Demonstrated in this Experiment:\n\n"
        "• Stale Reads: Occur when an asynchronous write has been acknowledged to the client, but a subsequent read request routes to a slow replica that has not yet applied the update.\n"
        "• Replica Convergence: The mathematical guarantee that when update propagation ceases, all replicas across the distributed banking network reach identical ledger states.\n"
        "• Update Ordering and Sequence Buffering: In an asynchronous network, packets can arrive out of order (e.g., Sequence #5 arrives before Sequence #4). "
        "  Replicas maintain a priority sequence buffer; out-of-order packets are held until missing predecessors arrive, preserving causal transaction ordering.\n"
        "• Multi-Node State Verification via SHA-256 Fingerprinting: To verify exact cluster consistency without transmitting entire database dumps, each node computes "
        "  a deterministic SHA-256 hash over its sorted loan ledger. Matching hashes across all nodes mathematically prove 100% convergence.\n"
        "• Replication Lag and Durability Loss: If the primary coordinator crashes while updates remain buffered in memory without having reached replicas, those writes "
        "  are permanently lost during failover, proving that asynchronous eventual consistency compromises durability (the D in ACID) in favor of write performance.\n\n"
        "4. Theoretical Trade-offs: CAP and PACELC Theorems:\n"
        "• CAP Theorem: A distributed system can simultaneously provide at most two of Consistency, Availability, and Partition Tolerance. In a banking network subject to WAN network partitions (P), "
        "  Strong Consistency chooses CP (sacrificing availability of slow/isolated nodes), while Eventual Consistency chooses AP (sacrificing consistency to maintain availability).\n"
        "• PACELC Theorem: If there is a Partition (P), trade off Availability (A) vs Consistency (C); Else (E), trade off Latency (L) vs Consistency (C). "
        "  Strong Consistency is PC/EC (high latency, guaranteed consistency), whereas Eventual Consistency is PA/EL (low latency, stale reads)."
    )
    r2_body.font.size = Pt(10)

    # Row 3: Problem-solving & Architecture
    c3 = table.rows[3].cells[0]
    p3 = c3.paragraphs[0]
    r3_title = p3.add_run("Problem-solving :\n")
    r3_title.bold = True
    r3_title.font.size = Pt(11)
    
    r3_desc = p3.add_run(
        "To evaluate and observe these consistency models, we designed and implemented a dedicated multi-node distributed benchmarking harness "
        "for the Home Loan Banking System in Java:\n"
        "1. Multi-Node Cluster Architecture (N = 3):\n"
        "   • Node 1 (BankNode-01): Primary Coordinator listening on port 7001 with 0ms local processing latency.\n"
        "   • Node 2 (BankNode-02): Fast Replica listening on port 7002 with a simulated network transmission delay of 1000ms.\n"
        "   • Node 3 (BankNode-03): Slow Replica listening on port 7003 with a simulated WAN transmission delay of 2400ms.\n"
        "2. Replication Controller & Coordination Pipeline:\n"
        "   • The ReplicationCoordinator accepts home loan transactions (SUBMIT, PAY_EMI), assigns a globally monotonic sequence number (#1, #2, #3, ...) "
        "     and increments the loan version number (v1, v2, v3).\n"
        "   • In Strong Mode (W=3), it dispatches parallel socket threads to all replicas and blocks using a CountDownLatch(3) until all confirm.\n"
        "   • In Quorum Mode (W=2, R=2), it awaits CountDownLatch(2), returning as soon as the local node and Node 2 confirm (~1012ms). Read queries query R=2 replicas and resolve to the max version.\n"
        "   • In Eventual Mode (W=1), it returns immediately upon local primary execution (5ms) and queues asynchronous tasks to background worker threads.\n"
        "3. Network Reordering & Sequence Buffer:\n"
        "   • Replicas track their next expected sequence number. Incoming packets with seq > expected are buffered in a TreeMap and drained sequentially once intermediate packets arrive.\n"
        "4. Durability & Crash Simulation:\n"
        "   • The harness injects catastrophic primary failure while unpropagated writes exist in flight, promotes Node 2, and audits the lost transactions.\n\n"
    )
    r3_desc.font.size = Pt(10)
    
    if os.path.exists(ARCH_PATH):
        p3_img = c3.add_paragraph()
        p3_img.alignment = WD_ALIGN_PARAGRAPH.CENTER
        p3_img.add_run().add_picture(ARCH_PATH, width=Inches(6.2))

    # Row 4: Code (ONLY Backend Java code, NO frontend)
    c4 = table.rows[4].cells[0]
    p4 = c4.paragraphs[0]
    r4_title = p4.add_run("Code :\n")
    r4_title.bold = True
    r4_title.font.size = Pt(11)

    r4_c1 = p4.add_run("1. DistributedLoanConsistencySystem.java (Core Distributed Replication & Consistency Engine)\n")
    r4_c1.bold = True
    r4_c1.font.color.rgb = RGBColor(0, 102, 0)

    p4_code1 = c4.add_paragraph()
    r4_code1_text = p4_code1.add_run(SYSTEM_CODE)
    r4_code1_text.font.name = 'Consolas'
    r4_code1_text.font.size = Pt(8.5)

    # Row 5: Output screenshot
    c5 = table.rows[5].cells[0]
    p5 = c5.paragraphs[0]
    r5_title = p5.add_run("Output screenshot :\n")
    r5_title.bold = True
    r5_title.font.size = Pt(11)

    for fname, caption in SHOT_ITEMS:
        img_path = os.path.join(SCREENSHOTS_DIR, fname)
        if os.path.exists(img_path):
            p_cap = c5.add_paragraph()
            p_cap.paragraph_format.keep_with_next = True
            r_cap = p_cap.add_run(caption)
            r_cap.bold = True
            r_cap.font.size = Pt(9.5)
            
            p_simg = c5.add_paragraph()
            p_simg.alignment = WD_ALIGN_PARAGRAPH.CENTER
            p_simg.add_run().add_picture(img_path, width=Inches(6.0))

    # Row 6: Conclusion
    c6 = table.rows[6].cells[0]
    p6 = c6.paragraphs[0]
    r6_title = p6.add_run("Conclusion:\n")
    r6_title.bold = True
    r6_title.font.size = Pt(11)
    
    r6_body = p6.add_run(
        "In this experiment, we conducted an in-depth empirical study of data consistency and replication strategies in our Distributed Home Loan Banking System. "
        "By implementing a multi-node cluster (N = 3) with configurable network latencies (Node 1 Primary 0ms, Node 2 Fast Replica 1000ms, Node 3 Slow WAN Replica 2400ms), "
        "we observed the following key results:\n\n"
        "1. Strong Consistency (W = 3) achieved absolute linearizability and zero stale reads with 100% cryptographic ledger agreement (SHA-256: 7A8C23BE8F24), "
        "   but incurred high client write latency (2496 ms) because the primary had to wait for the high-latency WAN replica.\n"
        "2. Eventual Consistency (W = 1) reduced write latency by over 99.8% down to 5 ms, but produced clear stale read anomalies on lagging replicas during in-flight replication. "
        "   We verified that given a quiet period (~3000 ms), the asynchronous queues fully drained and all replicas achieved convergence (SHA-256: 719A65EE7038).\n"
        "3. Quorum Consistency (W = 2, R = 2) struck an optimal balance: by satisfying R + W = 4 > 3, writes completed as soon as the fastest replica acknowledged (1012 ms), "
        "   while quorum reads successfully detected and discarded stale versions, guaranteeing fresh read linearizability.\n"
        "4. Sequence Buffering successfully mitigated out-of-order network delivery by holding dependent packets and preserving strict causal execution order.\n"
        "5. Durability Failure Testing demonstrated that asynchronous eventual consistency compromises durability under sudden crash failures, "
        "   resulting in lost loan applications (HL1004, HL1005) during failover.\n\n"
        "Architectural Takeaway: For core financial systems like home loan disbursements and EMI repayments, Quorum Consistency (W=2, R=2) or Strong Consistency (W=3) "
        "is mandatory to protect data integrity and avoid financial loss, whereas Eventual Consistency should be reserved for non-critical query telemetry or notification feeds."
    )
    r6_body.font.size = Pt(10)

    # Row 7 & 8 Notes
    c7 = table.rows[7].cells[0]
    p7 = c7.paragraphs[0]
    r7_text = p7.add_run("Attach research paper based on experiment performed into the classroom (Not for all the practicals)")
    r7_text.font.size = Pt(9.5)
    r7_text.italic = True

    c8 = table.rows[8].cells[0]
    p8 = c8.paragraphs[0]
    r8_text = p8.add_run("(Students are required to prepare one doc file for each experiment and then upload it in pdf form.)")
    r8_text.font.size = Pt(9.5)
    r8_text.italic = True

    doc.save(DOCX_PATH)
    print("Exact SPIT Template DOCX build complete for Exp 6:", DOCX_PATH)

# -------------------------------------------------------------
# 2. BUILD EXACT SPIT TEMPLATE PDF
# -------------------------------------------------------------
def build_exact_spit_pdf():
    print("Generating Exact SPIT Template PDF report for Exp 6...")
    doc = SimpleDocTemplate(
        PDF_PATH,
        pagesize=letter,
        rightMargin=45, leftMargin=45, topMargin=45, bottomMargin=45
    )
    
    styles = getSampleStyleSheet()
    
    title_style = ParagraphStyle(
        'HeaderTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=13,
        leading=16,
        alignment=1,
        textColor=colors.HexColor("#000000")
    )
    
    meta_style = ParagraphStyle(
        'MetaText',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.5,
        leading=13.5
    )

    section_heading = ParagraphStyle(
        'SecHeading',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=11,
        leading=15,
        textColor=colors.HexColor("#000000"),
        spaceBefore=6,
        spaceAfter=4
    )

    body_style = ParagraphStyle(
        'BodyTextCustom',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.5,
        leading=13.5,
        textColor=colors.HexColor("#111111")
    )

    code_style = ParagraphStyle(
        'CodeStyle',
        parent=styles['Normal'],
        fontName='Courier',
        fontSize=7.5,
        leading=9.5,
        textColor=colors.HexColor("#002B36")
    )

    story = []

    # Header Table
    logo_img = Image(LOGO_PATH, width=70, height=70) if os.path.exists(LOGO_PATH) else Paragraph("", body_style)
    text_p = Paragraph("<b>Bhartiya Vidya Bhavans</b><br/><b>Sardar Patel Institute of Technology</b><br/>Munshi Nagar -- Andheri West -- 400058", title_style)
    
    head_table = Table([[logo_img, text_p]], colWidths=[90, 430])
    head_table.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('ALIGN', (0,0), (0,0), 'CENTER'),
        ('ALIGN', (1,0), (1,0), 'CENTER'),
    ]))
    story.append(head_table)
    story.append(Spacer(1, 4))
    story.append(HRFlowable(width="100%", thickness=1, color=colors.black, spaceBefore=2, spaceAfter=6))

    # Metadata Block Table
    meta_table_data = [
        [
            Paragraph("<b>Name:</b> Atharv Patil (2024300165)<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Tanish Parakh (2024300157)</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Gitesh Patil (2024300166)</b><br/><b>Branch:</b> C.E", meta_style),
            Paragraph("<b>DIVISION:</b> C<br/><b>BATCH:</b> C1<br/><b>DATE:</b> 28/09/2026", meta_style)
        ]
    ]
    t_meta = Table(meta_table_data, colWidths=[340, 180])
    t_meta.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(t_meta)
    story.append(HRFlowable(width="100%", thickness=1, color=colors.black, spaceBefore=4, spaceAfter=8))

    # Section 1: Experiment No.
    story.append(Paragraph("<b>Experiment No.: 6</b>", section_heading))
    story.append(Spacer(1, 4))

    # Section 2: Aim
    story.append(Paragraph(
        "<b>Aim :</b> To study how different replication strategies affect data consistency in the Distributed Home Loan Banking System, "
        "comparing Strong Consistency, Quorum Consistency, and Eventual Consistency, and analyzing write latency, stale reads, "
        "replica synchronization/convergence, ordering of updates with sequence buffering, behavior when replicas are delayed, "
        "and data durability during primary crash failures.",
        body_style
    ))
    story.append(Spacer(1, 8))

    # Section 3: Theory
    story.append(Paragraph("<b>Theory :</b>", section_heading))
    story.append(Paragraph(
        "In a Distributed Home Loan Banking System, data replication is the practice of maintaining redundant copies of core banking data "
        "-- including home loan application records, customer KYC profiles, loan approval states, and EMI payment transaction ledgers -- "
        "across geographically distributed server nodes. Replication is essential to guarantee high availability and fault tolerance. "
        "However, keeping multiple replicas synchronized across unreliable network links with variable latency introduces fundamental trade-offs between "
        "consistency, availability, and latency (governed by the CAP and PACELC theorems).<br/><br/>"
        "<b>1. Replication Strategies and Consistency Models:</b><br/>"
        "• <b>Strong Consistency (Synchronous Master-Slave Replication, W = N):</b><br/>"
        "  Under Strong Consistency, every state-changing write (e.g., submitting a loan application or registering an EMI repayment) is executed on the primary coordinator "
        "  and synchronously replicated to all N replicas. The coordinator blocks and does not acknowledge the client until all N nodes confirm successful persistence. "
        "  This guarantees linearizability: any client reading from any node is guaranteed to observe the latest acknowledged write (0% stale reads). "
        "  The severe downside is write latency, which is strictly governed by the slowest replica on the highest-latency link (~2496 ms across WAN).<br/><br/>"
        "• <b>Quorum Consistency (Tunable Leader-Follower Quorum, R + W &gt; N):</b><br/>"
        "  In a cluster of N nodes, Quorum Consistency allows configuring a Write Quorum (W) and Read Quorum (R). As long as the Pigeonhole Principle "
        "  condition R + W &gt; N is satisfied (e.g., in our N=3 cluster, W=2 and R=2 yielding 2 + 2 = 4 &gt; 3), the read set and write set must overlap by at least one node. "
        "  By tagging each update with a monotonically increasing version number (v1, v2, v3) or timestamp, the client queries R replicas in parallel and selects the highest "
        "  version number, guaranteeing strong read consistency while allowing writes to commit as soon as a fast majority responds (~1012 ms), avoiding delays from slow WAN nodes.<br/><br/>"
        "• <b>Eventual Consistency (Asynchronous Replication, W = 1):</b><br/>"
        "  Under Eventual Consistency, the primary coordinator updates its local database and immediately acknowledges the client (latency ~5 ms). "
        "  Updates are placed into asynchronous background queues and forwarded lazily to replica nodes. While this maximizes write throughput and availability, "
        "  it introduces a window of vulnerability: clients querying a delayed replica before the queue drains will experience stale reads (missing or outdated loan balances). "
        "  However, if no new updates occur, all replicas eventually converge to an identical state.<br/><br/>"
        "<b>2. Summary Comparison of Consistency Models (Cluster Size N = 3):</b><br/>"
        "&nbsp;&nbsp;• <b>Strong:</b> W = 3, R = 1 | High Latency (~2500ms) | 0% Stale Reads | Zero Data Loss<br/>"
        "&nbsp;&nbsp;• <b>Quorum:</b> W = 2, R = 2 | Medium Latency (~1000ms) | 0% Stale Reads (R+W&gt;N) | Zero Data Loss<br/>"
        "&nbsp;&nbsp;• <b>Eventual:</b> W = 1, R = 1 | Minimal Latency (~5ms) | Stale Read Window (~2400ms) | Risk of Lost Writes on Crash<br/><br/>"
        "<b>3. Concrete Consistency Concepts Demonstrated:</b><br/>"
        "• <b>Stale Reads:</b> Reading from a replica before asynchronous replication completes yields outdated or missing records.<br/>"
        "• <b>Replica Convergence:</b> Replicas reconcile to identical states once background queues drain.<br/>"
        "• <b>Update Ordering &amp; Sequence Buffering:</b> Monotonic sequence tags (#1..#5) allow replicas to buffer out-of-order packets and apply them sequentially.<br/>"
        "• <b>Multi-Node State Verification via SHA-256 Fingerprinting:</b> Computing cryptographic hashes of the entire loan ledger across nodes proves 100% convergence.<br/>"
        "• <b>Replication Lag &amp; Durability Loss:</b> Unpropagated writes in eventual mode are permanently lost if the primary crashes before replication completes.<br/><br/>"
        "<b>4. Theoretical Trade-offs: CAP and PACELC Theorems:</b><br/>"
        "• <b>CAP Theorem:</b> Strong Consistency selects CP (Consistency + Partition Tolerance) sacrificing availability of lagging nodes, whereas Eventual Consistency selects AP (Availability + Partition Tolerance) sacrificing consistency.<br/>"
        "• <b>PACELC Theorem:</b> Under normal execution (Else), Strong Consistency trades higher Latency for Consistency (PC/EC), while Eventual Consistency trades Consistency for lower Latency (PA/EL).",
        body_style
    ))
    story.append(Spacer(1, 10))

    # Section 4: Problem-solving & Architecture
    story.append(Paragraph("<b>Problem-solving :</b>", section_heading))
    story.append(Spacer(1, 4))
    story.append(Paragraph(
        "To evaluate and observe these consistency models, we designed and implemented a dedicated multi-node distributed benchmarking harness "
        "for the Home Loan Banking System in Java:<br/>"
        "1. <b>Multi-Node Cluster Architecture (N = 3):</b><br/>"
        "&nbsp;&nbsp;• <b>Node 1 (BankNode-01):</b> Primary Coordinator listening on port 7001 with 0ms local processing latency.<br/>"
        "&nbsp;&nbsp;• <b>Node 2 (BankNode-02):</b> Fast Replica listening on port 7002 with a simulated network transmission delay of 1000ms.<br/>"
        "&nbsp;&nbsp;• <b>Node 3 (BankNode-03):</b> Slow Replica listening on port 7003 with a simulated WAN transmission delay of 2400ms.<br/>"
        "2. <b>Replication Controller &amp; Coordination Pipeline:</b><br/>"
        "&nbsp;&nbsp;• The ReplicationCoordinator accepts home loan transactions (SUBMIT, PAY_EMI), assigns a globally monotonic sequence number (#1, #2, #3, ...) "
        "and increments the loan version number (v1, v2, v3).<br/>"
        "&nbsp;&nbsp;• In Strong Mode (W=3), it dispatches parallel socket threads to all replicas and blocks using a CountDownLatch(3) until all confirm.<br/>"
        "&nbsp;&nbsp;• In Quorum Mode (W=2, R=2), it awaits CountDownLatch(2), returning as soon as the local node and Node 2 confirm (~1012ms). Read queries query R=2 replicas and resolve to the max version.<br/>"
        "&nbsp;&nbsp;• In Eventual Mode (W=1), it returns immediately upon local primary execution (5ms) and queues asynchronous tasks to background worker threads.<br/>"
        "3. <b>Network Reordering &amp; Sequence Buffer:</b><br/>"
        "&nbsp;&nbsp;• Replicas track their next expected sequence number. Incoming packets with seq &gt; expected are buffered in a TreeMap and drained sequentially once intermediate packets arrive.<br/>"
        "4. <b>Durability &amp; Crash Simulation:</b><br/>"
        "&nbsp;&nbsp;• The harness injects catastrophic primary failure while unpropagated writes exist in flight, promotes Node 2, and audits the lost transactions.<br/>",
        body_style
    ))
    story.append(Spacer(1, 6))
    if os.path.exists(ARCH_PATH):
        story.append(KeepTogether([
            Image(ARCH_PATH, width=480, height=272),
            Spacer(1, 10)
        ]))

    # Section 5: Code (Backend Java code only, strictly no frontend code in code section)
    story.append(Paragraph("<b>Code :</b>", section_heading))
    story.append(Spacer(1, 4))
    story.append(Paragraph("<b>1. DistributedLoanConsistencySystem.java (Core Distributed Replication &amp; Consistency Engine)</b>", ParagraphStyle('SubHead', parent=body_style, fontName='Helvetica-Bold', textColor=colors.HexColor("#006600"))))
    story.append(Spacer(1, 4))

    def create_code_paragraphs(code_text):
        lines = code_text.splitlines()
        flowables = []
        chunk = []
        for line in lines:
            chunk.append(line)
            if len(chunk) >= 40:
                chunk_str = "\n".join(chunk).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br/>").replace(" ", "&nbsp;")
                flowables.append(Paragraph(chunk_str, code_style))
                flowables.append(Spacer(1, 4))
                chunk = []
        if chunk:
            chunk_str = "\n".join(chunk).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br/>").replace(" ", "&nbsp;")
            flowables.append(Paragraph(chunk_str, code_style))
        return flowables

    story.extend(create_code_paragraphs(SYSTEM_CODE))
    story.append(Spacer(1, 12))

    # Section 6: Output screenshot (Includes both Real Java GUI and Real Console Outputs)
    story.append(Paragraph("<b>Output screenshot :</b>", section_heading))
    story.append(Spacer(1, 6))

    for fname, caption in SHOT_ITEMS:
        img_p = os.path.join(SCREENSHOTS_DIR, fname)
        if os.path.exists(img_p):
            fig_elements = [
                Paragraph(f"<b>{caption}</b>", body_style),
                Spacer(1, 4),
                Image(img_p, width=460, height=230),
                Spacer(1, 10)
            ]
            story.append(KeepTogether(fig_elements))

    # Section 7: Conclusion
    story.append(KeepTogether([
        Paragraph("<b>Conclusion:</b> (It should include what the student has learned/ understood from the experiment).", section_heading),
        Spacer(1, 4),
        Paragraph(
            "In this experiment, we conducted an in-depth empirical study of data consistency and replication strategies in our Distributed Home Loan Banking System. "
            "By implementing a multi-node cluster (N = 3) with configurable network latencies (Node 1 Primary 0ms, Node 2 Fast Replica 1000ms, Node 3 Slow WAN Replica 2400ms), "
            "we observed the following key results:<br/><br/>"
            "1. <b>Strong Consistency (W = 3)</b> achieved absolute linearizability and zero stale reads with 100% cryptographic ledger agreement (SHA-256: 7A8C23BE8F24), "
            "   but incurred high client write latency (2496 ms) because the primary had to wait for the high-latency WAN replica.<br/>"
            "2. <b>Eventual Consistency (W = 1)</b> reduced write latency by over 99.8% down to 5 ms, but produced clear stale read anomalies on lagging replicas during in-flight replication. "
            "   We verified that given a quiet period (~3000 ms), the asynchronous queues fully drained and all replicas achieved convergence (SHA-256: 719A65EE7038).<br/>"
            "3. <b>Quorum Consistency (W = 2, R = 2)</b> struck an optimal balance: by satisfying R + W = 4 &gt; 3, writes completed as soon as the fastest replica acknowledged (1012 ms), "
            "   while quorum reads successfully detected and discarded stale versions, guaranteeing fresh read linearizability.<br/>"
            "4. <b>Sequence Buffering</b> successfully mitigated out-of-order network delivery by holding dependent packets and preserving strict causal execution order.<br/>"
            "5. <b>Durability Failure Testing</b> demonstrated that asynchronous eventual consistency compromises durability under sudden crash failures, "
            "   resulting in lost loan applications (HL1004, HL1005) during failover.<br/><br/>"
            "<b>Architectural Takeaway:</b> For core financial systems like home loan disbursements and EMI repayments, Quorum Consistency (W=2, R=2) or Strong Consistency (W=3) "
            "is mandatory to protect data integrity and avoid financial loss, whereas Eventual Consistency should be reserved for non-critical query telemetry or notification feeds.",
            body_style
        ),
        Spacer(1, 10),
        Paragraph("<i>Attach research paper based on experiment performed into the classroom (Not for all the practicals)</i>", body_style),
        Spacer(1, 4),
        Paragraph("<i>(Students are required to prepare one doc file for each experiment and then upload it in pdf form.)</i>", body_style)
    ]))

    doc.build(story, onFirstPage=draw_page_border, onLaterPages=draw_page_border)
    print("Exact SPIT Template PDF build complete for Exp 6:", PDF_PATH)

if __name__ == "__main__":
    build_exact_spit_docx()
    build_exact_spit_pdf()
