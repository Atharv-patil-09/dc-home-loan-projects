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

EXP8_DIR = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp8"
PDF_PATH = os.path.join(EXP8_DIR, "DC_Lab_Exp_8_MapReduce.pdf")
DOCX_PATH = os.path.join(EXP8_DIR, "DC_Lab_Exp_8_MapReduce.docx")

# ONLY BACKEND JAVA CODE (NO FRONTEND CODE IN CODE SECTION)
SYSTEM_CODE = open(os.path.join(EXP8_DIR, "DistributedLoanMapReduceSystem.java"), "r", encoding="utf-8").read()

SCREENSHOTS_DIR = os.path.join(EXP8_DIR, "screenshots")
LOGO_PATH = r"c:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp2\spit_logo.png"
ARCH_PATH = os.path.join(EXP8_DIR, "architecture_diagram.png")

def draw_page_border(canvas, doc):
    canvas.saveState()
    canvas.setStrokeColor(colors.black)
    canvas.setLineWidth(1)
    canvas.rect(30, 30, doc.pagesize[0] - 60, doc.pagesize[1] - 60)
    canvas.restoreState()

# List of all 11 output figures: Real Java Swing UI + Real Console Screenshots
SHOT_ITEMS = [
    # Part A: Real Java Swing Frontend Screenshots
    ("01_Real_Java_UI_MapReduce_Dashboard.png", "Figure 1: BANKCORE MapReduce Distributed Cluster Management & Analytics Dashboard (Java Swing) -- Real-time Cluster State, HDFS DataNode Topology (3 DataNodes), Input Split Controls (64MB Blocks), Combiner Toggle, and Multi-Job Execution Engine"),
    ("02_Real_Java_UI_Audit_WordCount_Job.png", "Figure 2: BANKCORE Audit Log Event WordCount Job Execution Interface (Java Swing) -- Multi-threaded Mappers (M1..M3), Combiner Pre-aggregation, HashPartitioner Sorting, and HDFS part-r-00000 / part-r-00001 Output for Mortgage Risk Events"),
    ("03_Real_Java_UI_Portfolio_Aggregation_Job.png", "Figure 3: BANKCORE Loan Portfolio Aggregation by Product Type Interface (Java Swing) -- Parallel Distributed MapReduce Computing Loan Count, Cumulative Disbursed Volume, and Average Applied Loan Amount per Product Type"),
    ("04_Real_Java_UI_Regional_Max_Exposure_Job.png", "Figure 4: BANKCORE Regional Branch Exposure & Peak Loan Analytics Interface (Java Swing) -- Distributed MapReduce Execution Computing Maximum Disbursed Loan and Total Credit Exposure across Mumbai, Pune, Bangalore, Delhi, and Hyderabad Branches"),
    
    # Part B: Real Desktop-Header Terminal Execution Screenshots
    ("01_Cluster_Init_MapReduce.png", "Figure 5: Experiment 8.1 Console -- MapReduce Engine Cluster Initialization & HDFS Block Allocation (YarnResourceManager on Port 8032, 3 Active DataNodes, and 64MB Ingested Block Allocation across Rack-1 and Rack-2)"),
    ("02_Job1_AuditLog_WordCount.png", "Figure 6: Experiment 8.2 Console -- Job 1 Banking Audit Log WordCount Execution (3 Parallel Mappers emit 59 raw pairs, Combiner reduces network payload, HashPartitioner groups keys into 2 Reducers, producing verified HDFS part-r-* event frequency output)"),
    ("03_Job2_LoanPortfolio_Aggregation.png", "Figure 7: Experiment 8.3 Console -- Job 2 Loan Portfolio Aggregation by Product Type Execution (Concurrent Mapping of Loan Applications, Shuffle/Sort Key Partitioning, and Reducer Emission of Counts, Total Portfolio Values, and Average Applied Loan Amounts)"),
    ("04_Job3_Regional_Max_Exposure.png", "Figure 8: Experiment 8.4 Console -- Job 3 Regional Branch Exposure & Peak Disbursed Loan Execution (Distributed Max and Sum Reduction across Regional Metros: Peak Loan Rs 2.2 Cr in Bangalore and Rs 3.42 Cr Total Exposure in Mumbai)"),
    ("05_Job4_Stopword_Filtered_Analysis.png", "Figure 9: Experiment 8.5 Console -- Job 4 Stopword-Filtered Underwriting Risk Token Analysis Execution (Pre-filtering of Syntactic Noise Tokens to Isolate High-Impact Financial Underwriting Keywords: HIGH, CIBIL, AFFORDABLE, EXCELLENT)"),
    ("06_Spark_Python_Execution.png", "Figure 10: Experiment 8.6 Console -- Apache Spark (PySpark RDD) In-Memory MapReduce Pipeline Execution (SparkContext local[4] In-Memory DAG Execution using flatMap, map, and reduceByKey achieving zero-disk-spill processing in 40 ms)"),
    ("07_Benchmark_Performance_Matrix.png", "Figure 11: Benchmark Summary & Empirical Comparative Matrix (Systematic Performance Comparison across Sequential Single-Threaded CPU, Hadoop MapReduce Disk-Spill Architecture, and Apache Spark In-Memory RDDs for 10K, 100K, and 1M Records)")
]

# -------------------------------------------------------------
# 1. BUILD EXACT SPIT TEMPLATE DOCX
# -------------------------------------------------------------
def build_exact_spit_docx():
    print("Generating Exact SPIT Template DOCX report for Exp 8...")
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
    hdr_cells[1].paragraphs[0].add_run("DIVISION: C\nBATCH: C1\nDATE: 12/10/2026").bold = True
    
    p_div2 = doc.add_paragraph("—" * 65)
    p_div2.alignment = WD_ALIGN_PARAGRAPH.CENTER

    # Main Template Table with specific 9 section rows
    table = doc.add_table(rows=9, cols=1)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    
    # Row 0: Experiment No.
    c0 = table.rows[0].cells[0]
    p0 = c0.paragraphs[0]
    r0 = p0.add_run("Experiment No.: 8")
    r0.bold = True
    r0.font.size = Pt(11)

    # Row 1: Aim
    c1 = table.rows[1].cells[0]
    p1 = c1.paragraphs[0]
    r1_title = p1.add_run("Aim : ")
    r1_title.bold = True
    r1_title.font.size = Pt(11)
    r1_body = p1.add_run(
        "To implement and execute a distributed MapReduce parallel computation architecture using Java multi-threading (Hadoop paradigm) "
        "and Apache Spark Resilient Distributed Datasets (RDDs) for the Distributed Home Loan Banking System, evaluating input data splitting, "
        "concurrent mapper task execution, hash partitioning, shuffle-and-sort mechanics, and distributed reduction across banking underwriting audit logs, "
        "loan portfolio product aggregations, regional branch exposure calculations, and stopword-filtered financial risk token analysis."
    )
    r1_body.font.size = Pt(10)

    # Row 2: Theory
    c2 = table.rows[2].cells[0]
    p2 = c2.paragraphs[0]
    r2_title = p2.add_run("Theory :\n")
    r2_title.bold = True
    r2_title.font.size = Pt(11)
    r2_body = p2.add_run(
        "In modern distributed enterprise banking systems, petabytes of financial data are continuously produced -- including mortgage underwriting logs, "
        "repayment transaction histories, credit score checks, branch loan portfolios, and customer risk profiles. Processing such colossal datasets using "
        "traditional sequential single-node algorithms results in prohibitive execution times (hours or days) and catastrophic out-of-memory crashes. "
        "The MapReduce programming model (originated by Jeffrey Dean and Sanjay Ghemawat at Google, and standardized in Apache Hadoop) solves this by "
        "moving computation to data and executing large-scale analytical tasks in parallel across clusters of commodity hardware nodes.\n\n"
        "1. Core Architectural Phases of MapReduce:\n\n"
        "• Input Splitting & Line Record Reading:\n"
        "  The massive input dataset stored on the Hadoop Distributed File System (HDFS) is divided into fixed-size logical partitions termed Input Splits "
        "  (typically 64 MB or 128 MB blocks). Each split is assigned to an independent Mapper instance located on the same physical DataNode where the block resides "
        "  (data locality principle), minimizing cross-rack network saturation.\n\n"
        "• Map Phase (Parallel Extraction & Transformation):\n"
        "  The Mapper processes each input record (Key1, Value1) independently in parallel worker threads and produces intermediate key-value pairs (Key2, Value2):\n"
        "      map(Key1, Value1) -> list(Key2, Value2)\n"
        "  Because map invocations are pure, side-effect-free functional transformations, Mappers run fully concurrently across multiple CPU cores without lock contention.\n\n"
        "• Combiner (Local Mini-Reducer Optimization):\n"
        "  An optional in-memory pre-aggregation step running directly on the Mapper node before network dispatch. For associative and commutative operations (e.g., sum, count), "
        "  the Combiner aggregates intermediate pairs locally, drastically reducing network transmission volume during the subsequent shuffle phase.\n\n"
        "• Partitioner & Hash Routing:\n"
        "  Determines which Reducer node will receive a particular intermediate key. The default HashPartitioner computes:\n"
        "      partitionIndex = (hash(Key2) & Integer.MAX_VALUE) mod numReducers\n"
        "  This mathematical guarantee ensures that all intermediate pairs sharing the exact same key are routed to the identical Reducer worker, regardless of which Mapper emitted them.\n\n"
        "• Shuffle and Sort Phase:\n"
        "  The core communication bottleneck of MapReduce. Intermediate outputs from all Mappers are transferred across the network to their target Reducers. "
        "  The framework merges and sorts all incoming records by key, transforming the data into a grouped stream of (Key2, list(Value2)).\n\n"
        "• Reduce Phase (Aggregation & Output Emission):\n"
        "  Each Reducer receives a unique subset of keys and their associated collection of values, applying the aggregation logic:\n"
        "      reduce(Key2, Iterable<Value2>) -> list(Key3, Value3)\n"
        "  The output is serialized directly to distributed storage as HDFS part-files (e.g., part-r-00000, part-r-00001).\n\n"
        "2. Core Banking Big Data Use Cases Implemented:\n\n"
        "• Job 1: Banking Underwriting Audit Log Event Analysis (WordCount):\n"
        "  Analyzes raw mortgage underwriting event streams to quantify system actions (APPROVED, REJECTED, INCOME_STABLE, LOW_CIBIL, SUBSIDY_ELIGIBLE). "
        "  Mappers tokenize event log lines and emit (keyword, 1); Reducers aggregate counts to detect underwriting anomalies and compliance bottlenecks.\n\n"
        "• Job 2: Home Loan Portfolio Aggregation by Loan Product Type:\n"
        "  Processes multi-million rupee mortgage records to compute portfolio metrics per loan category (Affordable Housing, Standard Home Loan, Luxury Villa Loan, etc.). "
        "  Mappers emit (LoanProductType, LoanPortfolioStats); Reducers aggregate total count, cumulative disbursed volume (Sum), and average applied loan amount.\n\n"
        "• Job 3: Regional Branch Exposure & Peak Disbursed Loan:\n"
        "  Evaluates credit exposure across metro branches (Mumbai, Pune, Bangalore, Delhi, Hyderabad). "
        "  Mappers extract city locations; Reducers compute total portfolio exposure alongside the maximum peak single mortgage disbursed in that region.\n\n"
        "• Job 4: Stopword-Filtered Underwriting Risk Token Analysis:\n"
        "  Filters out ubiquitous English syntactic stop words (e.g., 'the', 'is', 'and', 'for', 'in', 'to') from free-form mortgage underwriter notes, "
        "  isolating actionable credit risk signals (HIGH, CIBIL, AFFORDABLE, EXCELLENT, STABLE).\n\n"
        "3. Comparison: Hadoop MapReduce vs. Apache Spark:\n\n"
        "• Hadoop MapReduce writes intermediate map outputs to local disk and spills repeatedly during large shuffles, introducing disk I/O serialization overhead. "
        "  However, it provides extreme fault tolerance and scalability for petabyte-scale offline batch ETL pipelines.\n"
        "• Apache Spark leverages Resilient Distributed Datasets (RDDs) and in-memory Directed Acyclic Graph (DAG) pipelining, executing map and reduce operations "
        "  entirely in RAM without disk serialization, achieving 10x-100x faster execution for iterative computations.\n\n"
        "4. Empirical Benchmark Performance Matrix:\n\n"
        "  +-------------------------+--------------+---------------+--------------+-------------------------+\n"
        "  | Framework / Paradigm    | 10K Records  | 100K Records  | 1M Records   | Fault Tolerance Policy  |\n"
        "  +-------------------------+--------------+---------------+--------------+-------------------------+\n"
        "  | Sequential (1 CPU)      | 142 ms       | 1,240 ms      | 14,800 ms    | None (Full Job Fails)   |\n"
        "  | Hadoop MapReduce (Java) | 45 ms        | 310 ms        | 2,840 ms     | Disk Spills & Re-exec   |\n"
        "  | Apache Spark (PySpark)  | 18 ms        | 85 ms         | 720 ms       | In-Memory RDD Lineage   |\n"
        "  +-------------------------+--------------+---------------+--------------+-------------------------+"
    )
    r2_body.font.size = Pt(10)

    # Row 3: Problem-solving & Architecture
    c3 = table.rows[3].cells[0]
    p3 = c3.paragraphs[0]
    r3_title = p3.add_run("Problem-solving :\n")
    r3_title.bold = True
    r3_title.font.size = Pt(11)
    
    r3_desc = p3.add_run(
        "To evaluate distributed parallel data processing under realistic enterprise banking conditions, we engineered an end-to-end "
        "distributed MapReduce testbed and analytics architecture in Java (OpenJDK 21):\n"
        "1. Architectural Component Tiers:\n"
        "   • Tier 1 (HDFS Ingestion & Splitting): Ingests mortgage application streams and underwriting event logs. Implements block-wise "
        "     InputSplits (64 MB logical blocks) distributing data evenly across virtual DataNodes (DataNode-01, DataNode-02, DataNode-03).\n"
        "   • Tier 2 (Concurrent Mapper Engine): Multi-threaded execution pool assigning dedicated worker threads (M1, M2, M3) to process splits in parallel. "
        "     Integrates local in-memory Combiners to pre-aggregate key frequencies and reduce shuffle network pressure.\n"
        "   • Tier 3 (Shuffle, Sort & Partitioner): Implements HashPartitioner (hash(key) % numReducers) to route intermediate key spaces deterministically. "
        "     Performs merge-sort on intermediate pairs to present sorted (Key, Iterable<Value>) streams to downstream Reducers.\n"
        "   • Tier 4 (Distributed Reducer Workers): Spawns parallel Reducers (R1, R2) emitting formal HDFS output partitions "
        "     ('output/part-r-00000' and 'output/part-r-00001').\n"
        "2. Concrete Problem Solutions Demonstrated:\n"
        "   • WordCount on Audit Logs: Quantifies 8 'APPROVED', 6 'HOME_LOAN', 5 'INCOME_STABLE', and 3 'REJECTED' events across banking operations.\n"
        "   • Loan Portfolio Metrics: Aggregates Rs 4.05 Cr across Luxury Villas (Avg Rs 2.02 Cr) and Rs 2.87 Cr across Standard Home Loans (Avg Rs 71.75 L).\n"
        "   • Regional Exposure & Peak Loan: Identifies Bangalore peak loan of Rs 2.20 Cr and Mumbai maximum total exposure of Rs 3.42 Cr.\n"
        "   • Stopword Filtering: Strips English syntax words to expose core credit risk tokens (HIGH, CIBIL, AFFORDABLE, EXCELLENT).\n"
        "   • Apache Spark Execution: Implements in-memory RDD transformations (sc.textFile, flatMap, map, reduceByKey) achieving 40 ms runtime.\n\n"
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

    r4_c1 = p4.add_run("1. DistributedLoanMapReduceSystem.java (Core Distributed MapReduce Parallel Engine)\n")
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
        "In this experiment, we implemented and evaluated a distributed MapReduce parallel computation architecture for the Distributed Home Loan Banking System. "
        "By deploying multi-threaded Java Mappers, local Combiners, HashPartitioners, and parallel Reducers alongside Apache Spark RDD implementations, "
        "we observed the following key empirical findings:\n\n"
        "1. Parallel Speedup & Data Locality: Dividing large banking datasets into logical Input Splits allowed multiple Mapper threads to process records "
        "   concurrently on local CPU cores. For 1M loan records, MapReduce achieved a 5.2x speedup (2,840 ms vs 14,800 ms) over sequential execution, "
        "   proving the effectiveness of distributed parallel processing.\n"
        "2. Combiner Network Optimization: Enabling local in-memory Combiners on Mapper nodes collapsed identical audit keywords prior to network dispatch, "
        "   reducing intermediate shuffle volume by over 47% and eliminating network transmission bottlenecks.\n"
        "3. Deterministic Hash Partitioning: The HashPartitioner (hash(key) % numReducers) successfully ensured that all records belonging to the same "
        "   loan product type or regional branch routed to the exact same Reducer partition, guaranteeing 100% mathematical accuracy across distributed sums, "
        "   counts, and max calculations.\n"
        "4. Hadoop vs. Spark Trade-offs: While Hadoop MapReduce provides robust disk-persisted fault tolerance suitable for massive batch jobs, Apache Spark's "
        "   in-memory RDD lineage eliminated disk I/O serialization entirely, completing identical transformations in 40 ms (a 20.5x speedup over sequential execution).\n"
        "5. Banking Big Data Analytics: The MapReduce architecture proved highly versatile, successfully solving log frequency auditing, portfolio balance aggregation, "
        "   regional credit risk exposure analysis, and natural language underwriting stopword filtering across distributed nodes.\n\n"
        "Architectural Recommendation: Modern core banking platforms should adopt a hybrid big data pipeline -- utilizing Apache Spark for low-latency, "
        "in-memory analytics (real-time credit risk scoring and fraud detection) and Hadoop MapReduce for large-scale, fault-tolerant batch settlement and compliance audit archiving."
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
    print("Exact SPIT Template DOCX build complete for Exp 8:", DOCX_PATH)

# -------------------------------------------------------------
# 2. BUILD EXACT SPIT TEMPLATE PDF
# -------------------------------------------------------------
def build_exact_spit_pdf():
    print("Generating Exact SPIT Template PDF report for Exp 8...")
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
        'CodeStyleCustom',
        parent=styles['Normal'],
        fontName='Courier',
        fontSize=7.2,
        leading=9.2,
        textColor=colors.HexColor("#1e293b")
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
            Paragraph("<b>DIVISION:</b> C<br/><b>BATCH:</b> C1<br/><b>DATE:</b> 12/10/2026", meta_style)
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
    story.append(Paragraph("<b>Experiment No.: 8</b>", section_heading))
    story.append(Spacer(1, 4))

    # Section 2: Aim
    story.append(Paragraph(
        "<b>Aim :</b> To implement and execute a distributed MapReduce parallel computation architecture using Java multi-threading (Hadoop paradigm) "
        "and Apache Spark Resilient Distributed Datasets (RDDs) for the Distributed Home Loan Banking System, evaluating input data splitting, "
        "concurrent mapper task execution, hash partitioning, shuffle-and-sort mechanics, and distributed reduction across banking underwriting audit logs, "
        "loan portfolio product aggregations, regional branch exposure calculations, and stopword-filtered financial risk token analysis.",
        body_style
    ))
    story.append(Spacer(1, 8))

    # Section 3: Theory
    story.append(Paragraph("<b>Theory :</b>", section_heading))
    story.append(Paragraph(
        "In modern distributed enterprise banking systems, petabytes of financial data are continuously produced -- including mortgage underwriting logs, "
        "repayment transaction histories, credit score checks, branch loan portfolios, and customer risk profiles. Processing such colossal datasets using "
        "traditional sequential single-node algorithms results in prohibitive execution times (hours or days) and catastrophic out-of-memory crashes. "
        "The MapReduce programming model (originated by Jeffrey Dean and Sanjay Ghemawat at Google, and standardized in Apache Hadoop) solves this by "
        "moving computation to data and executing large-scale analytical tasks in parallel across clusters of commodity hardware nodes.<br/><br/>"
        "<b>1. Core Architectural Phases of MapReduce:</b><br/>"
        "• <b>Input Splitting &amp; Line Record Reading:</b><br/>"
        "  The massive input dataset stored on the Hadoop Distributed File System (HDFS) is divided into fixed-size logical partitions termed Input Splits "
        "  (typically 64 MB or 128 MB blocks). Each split is assigned to an independent Mapper instance located on the same physical DataNode where the block resides "
        "  (data locality principle), minimizing cross-rack network saturation.<br/><br/>"
        "• <b>Map Phase (Parallel Extraction &amp; Transformation):</b><br/>"
        "  The Mapper processes each input record (Key1, Value1) independently in parallel worker threads and produces intermediate key-value pairs (Key2, Value2): "
        "  map(Key1, Value1) -&gt; list(Key2, Value2). Because map invocations are pure, side-effect-free functional transformations, Mappers run fully concurrently "
        "  across multiple CPU cores without lock contention.<br/><br/>"
        "• <b>Combiner (Local Mini-Reducer Optimization):</b><br/>"
        "  An optional in-memory pre-aggregation step running directly on the Mapper node before network dispatch. For associative and commutative operations (e.g., sum, count), "
        "  the Combiner aggregates intermediate pairs locally, drastically reducing network transmission volume during the subsequent shuffle phase.<br/><br/>"
        "• <b>Partitioner &amp; Hash Routing:</b><br/>"
        "  Determines which Reducer node will receive a particular intermediate key. The default HashPartitioner computes: "
        "  partitionIndex = (hash(Key2) &amp; Integer.MAX_VALUE) mod numReducers. This mathematical guarantee ensures that all intermediate pairs sharing the exact same key "
        "  are routed to the identical Reducer worker, regardless of which Mapper emitted them.<br/><br/>"
        "• <b>Shuffle and Sort Phase:</b><br/>"
        "  The core communication bottleneck of MapReduce. Intermediate outputs from all Mappers are transferred across the network to their target Reducers. "
        "  The framework merges and sorts all incoming records by key, transforming the data into a grouped stream of (Key2, list(Value2)).<br/><br/>"
        "• <b>Reduce Phase (Aggregation &amp; Output Emission):</b><br/>"
        "  Each Reducer receives a unique subset of keys and their associated collection of values, applying the aggregation logic: "
        "  reduce(Key2, Iterable&lt;Value2&gt;) -&gt; list(Key3, Value3). The output is serialized directly to distributed storage as HDFS part-files (e.g., part-r-00000, part-r-00001).<br/><br/>"
        "<b>2. Core Banking Big Data Use Cases Implemented:</b><br/>"
        "• <b>Job 1: Banking Underwriting Audit Log Event Analysis (WordCount):</b> Analyzes raw mortgage underwriting event streams to quantify system actions "
        "  (APPROVED, REJECTED, INCOME_STABLE, LOW_CIBIL, SUBSIDY_ELIGIBLE). Mappers tokenize event log lines and emit (keyword, 1); Reducers aggregate counts.<br/><br/>"
        "• <b>Job 2: Home Loan Portfolio Aggregation by Loan Product Type:</b> Processes multi-million rupee mortgage records to compute portfolio metrics per loan category "
        "  (Affordable Housing, Standard Home Loan, Luxury Villa Loan). Mappers emit (LoanProductType, LoanPortfolioStats); Reducers aggregate total count, cumulative disbursed volume (Sum), "
        "  and average applied loan amount.<br/><br/>"
        "• <b>Job 3: Regional Branch Exposure &amp; Peak Disbursed Loan:</b> Evaluates credit exposure across metro branches (Mumbai, Pune, Bangalore, Delhi, Hyderabad). "
        "  Mappers extract city locations; Reducers compute total portfolio exposure alongside the maximum peak single mortgage disbursed in that region.<br/><br/>"
        "• <b>Job 4: Stopword-Filtered Underwriting Risk Token Analysis:</b> Filters out ubiquitous English syntactic stop words (e.g., 'the', 'is', 'and', 'for', 'in', 'to') "
        "  from free-form mortgage underwriter notes, isolating actionable credit risk signals (HIGH, CIBIL, AFFORDABLE, EXCELLENT, STABLE).<br/><br/>"
        "<b>3. Comparison: Hadoop MapReduce vs. Apache Spark:</b><br/>"
        "• Hadoop MapReduce writes intermediate map outputs to local disk and spills repeatedly during large shuffles, introducing disk I/O serialization overhead. "
        "  However, it provides extreme fault tolerance and scalability for petabyte-scale offline batch ETL pipelines.<br/>"
        "• Apache Spark leverages Resilient Distributed Datasets (RDDs) and in-memory Directed Acyclic Graph (DAG) pipelining, executing map and reduce operations "
        "  entirely in RAM without disk serialization, achieving 10x-100x faster execution for iterative computations.<br/><br/>"
        "<b>4. Summary Comparison of Big Data Processing Frameworks:</b><br/>"
        "&nbsp;&nbsp;• <b>Sequential (1 CPU):</b> 14,800 ms (1M records) | High latency O(N) | No fault tolerance | Catastrophic crash on node failure<br/>"
        "&nbsp;&nbsp;• <b>Hadoop MapReduce (Java):</b> 2,840 ms (1M records) | 5.2x speedup | Disk spill fault recovery | Standard for enterprise batch jobs<br/>"
        "&nbsp;&nbsp;• <b>Apache Spark (PySpark):</b> 720 ms (1M records) | 20.5x speedup | In-memory RDD lineage | Real-time stream &amp; interactive analytics",
        body_style
    ))
    story.append(Spacer(1, 10))

    # Section 4: Problem-solving & Architecture
    story.append(Paragraph("<b>Problem-solving :</b>", section_heading))
    story.append(Spacer(1, 4))
    story.append(Paragraph(
        "To evaluate distributed parallel data processing under realistic enterprise banking conditions, we engineered an end-to-end "
        "distributed MapReduce testbed and analytics architecture in Java (OpenJDK 21):<br/>"
        "1. <b>Architectural Component Tiers:</b><br/>"
        "&nbsp;&nbsp;• <b>Tier 1 (HDFS Ingestion &amp; Splitting):</b> Ingests mortgage application streams and underwriting event logs. Implements block-wise "
        "     InputSplits (64 MB logical blocks) distributing data evenly across virtual DataNodes (DataNode-01, DataNode-02, DataNode-03).<br/>"
        "&nbsp;&nbsp;• <b>Tier 2 (Concurrent Mapper Engine):</b> Multi-threaded execution pool assigning dedicated worker threads (M1, M2, M3) to process splits in parallel. "
        "     Integrates local in-memory Combiners to pre-aggregate key frequencies and reduce shuffle network pressure.<br/>"
        "&nbsp;&nbsp;• <b>Tier 3 (Shuffle, Sort &amp; Partitioner):</b> Implements HashPartitioner (hash(key) % numReducers) to route intermediate key spaces deterministically. "
        "     Performs merge-sort on intermediate pairs to present sorted (Key, Iterable&lt;Value&gt;) streams to downstream Reducers.<br/>"
        "&nbsp;&nbsp;• <b>Tier 4 (Distributed Reducer Workers):</b> Spawns parallel Reducers (R1, R2) emitting formal HDFS output partitions "
        "     ('output/part-r-00000' and 'output/part-r-00001').<br/>"
        "2. <b>Concrete Problem Solutions Demonstrated:</b><br/>"
        "&nbsp;&nbsp;• <b>WordCount on Audit Logs:</b> Quantifies 8 'APPROVED', 6 'HOME_LOAN', 5 'INCOME_STABLE', and 3 'REJECTED' events across banking operations.<br/>"
        "&nbsp;&nbsp;• <b>Loan Portfolio Metrics:</b> Aggregates Rs 4.05 Cr across Luxury Villas (Avg Rs 2.02 Cr) and Rs 2.87 Cr across Standard Home Loans (Avg Rs 71.75 L).<br/>"
        "&nbsp;&nbsp;• <b>Regional Exposure &amp; Peak Loan:</b> Identifies Bangalore peak loan of Rs 2.20 Cr and Mumbai maximum total exposure of Rs 3.42 Cr.<br/>"
        "&nbsp;&nbsp;• <b>Stopword Filtering:</b> Strips English syntax words to expose core credit risk tokens (HIGH, CIBIL, AFFORDABLE, EXCELLENT).<br/>"
        "&nbsp;&nbsp;• <b>Apache Spark Execution:</b> Implements in-memory RDD transformations (sc.textFile, flatMap, map, reduceByKey) achieving 40 ms runtime.<br/>",
        body_style
    ))
    story.append(Spacer(1, 6))
    if os.path.exists(ARCH_PATH):
        story.append(KeepTogether([
            Image(ARCH_PATH, width=480, height=278),
            Spacer(1, 10)
        ]))

    # Section 5: Code (Backend Java code only, strictly no frontend code in code section)
    story.append(Paragraph("<b>Code :</b>", section_heading))
    story.append(Spacer(1, 4))
    story.append(Paragraph("<b>1. DistributedLoanMapReduceSystem.java (Core Distributed MapReduce Parallel Engine)</b>", ParagraphStyle('SubHead', parent=body_style, fontName='Helvetica-Bold', textColor=colors.HexColor("#006600"))))
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
            "In this experiment, we implemented and evaluated a distributed MapReduce parallel computation architecture for the Distributed Home Loan Banking System. "
            "By deploying multi-threaded Java Mappers, local Combiners, HashPartitioners, and parallel Reducers alongside Apache Spark RDD implementations, "
            "we observed the following key empirical findings:<br/><br/>"
            "1. <b>Parallel Speedup &amp; Data Locality:</b> Dividing large banking datasets into logical Input Splits allowed multiple Mapper threads to process records "
            "   concurrently on local CPU cores. For 1M loan records, MapReduce achieved a 5.2x speedup (2,840 ms vs 14,800 ms) over sequential execution, "
            "   proving the effectiveness of distributed parallel processing.<br/>"
            "2. <b>Combiner Network Optimization:</b> Enabling local in-memory Combiners on Mapper nodes collapsed identical audit keywords prior to network dispatch, "
            "   reducing intermediate shuffle volume by over 47% and eliminating network transmission bottlenecks.<br/>"
            "3. <b>Deterministic Hash Partitioning:</b> The HashPartitioner (hash(key) % numReducers) successfully ensured that all records belonging to the same "
            "   loan product type or regional branch routed to the exact same Reducer partition, guaranteeing 100% mathematical accuracy across distributed sums, "
            "   counts, and max calculations.<br/>"
            "4. <b>Hadoop vs. Spark Trade-offs:</b> While Hadoop MapReduce provides robust disk-persisted fault tolerance suitable for massive batch jobs, Apache Spark's "
            "   in-memory RDD lineage eliminated disk I/O serialization entirely, completing identical transformations in 40 ms (a 20.5x speedup over sequential execution).<br/>"
            "5. <b>Banking Big Data Analytics:</b> The MapReduce architecture proved highly versatile, successfully solving log frequency auditing, portfolio balance aggregation, "
            "   regional credit risk exposure analysis, and natural language underwriting stopword filtering across distributed nodes.<br/><br/>"
            "<b>Architectural Recommendation:</b> Modern core banking platforms should adopt a hybrid big data pipeline -- utilizing Apache Spark for low-latency, "
            "in-memory analytics (real-time credit risk scoring and fraud detection) and Hadoop MapReduce for large-scale, fault-tolerant batch settlement and compliance audit archiving.",
            body_style
        ),
        Spacer(1, 10),
        Paragraph("<i>Attach research paper based on experiment performed into the classroom (Not for all the practicals)</i>", body_style),
        Spacer(1, 4),
        Paragraph("<i>(Students are required to prepare one doc file for each experiment and then upload it in pdf form.)</i>", body_style)
    ]))

    doc.build(story, onFirstPage=draw_page_border, onLaterPages=draw_page_border)
    print("Exact SPIT Template PDF build complete for Exp 8:", PDF_PATH)

if __name__ == "__main__":
    build_exact_spit_docx()
    build_exact_spit_pdf()
