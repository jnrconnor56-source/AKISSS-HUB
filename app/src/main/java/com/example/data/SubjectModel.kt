package com.example.data

import androidx.compose.runtime.Immutable

@Immutable
enum class CourseTrack(val displayName: String) {
    ALL("All Subjects"),
    CORE("Core Subjects"),
    GENERAL_SCIENCE("General Science"),
    GENERAL_ARTS("General Arts"),
    BUSINESS("Business"),
    VISUAL_ARTS("Visual Arts"),
    HOME_ECONOMICS("Home Economics"),
    AGRICULTURE("Agricultural Science")
}

@Immutable
data class SubjectItem(
    val id: String,
    val title: String,
    val shortName: String,
    val year: Int, // 1, 2, or 3
    val courseTrack: CourseTrack = CourseTrack.CORE,
    val curriculumCode: String,
    val description: String,
    val fileSizeFormatted: String,
    val downloadUrl: String,
    val fileName: String,
    val topics: List<String>,
    val wassceFormat: String,
    val recommendedTextbooks: List<String>,
    val pagesCount: Int
)

@Immutable
enum class DownloadState {
    READY,
    DOWNLOADING,
    COMPLETED,
    FAILED
}

object CurriculumRepository {
    val subjects: List<SubjectItem> = listOf(
        // =========================================================================
        // CORE SUBJECTS (COMPULSORY FOR ALL SHS STUDENTS)
        // =========================================================================

        // Year 1 Core
        SubjectItem(
            id = "math_y1",
            title = "Core Mathematics",
            shortName = "Mathematics",
            year = 1,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-MATH01",
            description = "Fundamental Senior High School mathematics covering real number systems, algebra, geometry, mensuration, and linear equations.",
            fileSizeFormatted = "4.6 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Mathematics_LM_Y1-FINAL-LEARNERS-VERSION.pdf",
            fileName = "GES_SHS1_Core_Mathematics_Syllabus.pdf",
            topics = listOf(
                "Sets and Operations on Sets",
                "Real Number Systems & Surds",
                "Algebraic Expressions & Factorization",
                "Linear Equations and Inequalities in One Variable",
                "Ratio, Proportion, Rates and Percentages",
                "Plane Geometry I: Angles and Triangles",
                "Mensuration I: Perimeter and Area of Plane Figures",
                "Data Collection and Basic Frequency Tables"
            ),
            wassceFormat = "WASSCE Foundation: Builds foundational algebra, geometry, and numeracy skills essential for Paper 1 (50 Objective Questions) and Paper 2 Section A.",
            recommendedTextbooks = listOf(
                "Aki-Ola Core Mathematics for Senior High Schools",
                "Ministry of Education Teaching Syllabus for Core Mathematics (SHS 1-3)",
                "Approach Core Mathematics by K. A. Danso"
            ),
            pagesCount = 98
        ),
        SubjectItem(
            id = "eng_y1",
            title = "English Language",
            shortName = "English",
            year = 1,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-ENG01",
            description = "Comprehensive English language course covering grammar, composition writing, reading comprehension, and phonetics.",
            fileSizeFormatted = "5.2 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/English-Language_LM_Year-1_-LV.pdf",
            fileName = "GES_SHS1_English_Language_Syllabus.pdf",
            topics = listOf(
                "Parts of Speech & Grammatical Functions",
                "Sentence Structure: Simple, Compound & Complex Sentences",
                "Composition: Narrative and Descriptive Essays",
                "Summary Writing Fundamentals",
                "Reading Comprehension Strategies",
                "Phonetics: Vowels, Diphthongs & Consonant Sounds",
                "Introduction to African and Non-African Prose & Poetry"
            ),
            wassceFormat = "WASSCE Focus: Prepares students for Paper 1 (Oral English), Paper 2 (Essay Writing, Comprehension, and Summary), and Paper 3 (Lexis and Structure).",
            recommendedTextbooks = listOf(
                "Aki-Ola English for Senior High Schools",
                "Senior High School English by A. A. Agbetsoamedo",
                "WASSCE Prescribed Literature Texts (WAEC Anthology)"
            ),
            pagesCount = 112
        ),
        SubjectItem(
            id = "sci_y1",
            title = "General / Integrated Science",
            shortName = "Science",
            year = 1,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-SCI01",
            description = "Core integrated science bridging biology, chemistry, physics, and agricultural science fundamentals.",
            fileSizeFormatted = "6.1 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Science_LM_Y1_Online_LV.pdf",
            fileName = "GES_SHS1_Integrated_Science_Syllabus.pdf",
            topics = listOf(
                "Introduction to Science, Safety and Laboratory Apparatus",
                "Measurement of Physical Quantities",
                "Diversity of Matter: Elements, Compounds & Mixtures",
                "Atomic Structure and the Periodic Table",
                "Cells, Tissues and Organization of Living Organisms",
                "Breeding and Reproduction in Plants and Animals",
                "Forces, Motion and Simple Machines",
                "Soil Science and Crop Production Basics"
            ),
            wassceFormat = "WASSCE Structure: Targets Paper 1 (50 Objective Items), Paper 2 (Theory - Biology, Chemistry, Physics, Agriculture), and Paper 3 (Practical Alternative).",
            recommendedTextbooks = listOf(
                "Aki-Ola Integrated Science for SHS 1-3",
                "Excellence in Integrated Science by J. K. Baah",
                "Official GES Integrated Science Syllabus"
            ),
            pagesCount = 134
        ),
        SubjectItem(
            id = "soc_y1",
            title = "Social Studies",
            shortName = "Social Studies",
            year = 1,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-SOC01",
            description = "Examines social relationships, personal development, cultural heritage, and national identity in Ghana.",
            fileSizeFormatted = "4.1 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Social-Studies-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Social_Studies_Syllabus.pdf",
            topics = listOf(
                "Self-Identity, Self-Esteem and Capabilities",
                "Adolescent Reproductive Health & Responsible Living",
                "The Institution of Marriage and Family Life in Ghana",
                "Socialization and Our Cultural Heritage",
                "Our Physical Environment and Environmental Degradation",
                "Ghana as a Nation: Evolution and Integration",
                "The 1992 Constitution: Rights and Responsibilities"
            ),
            wassceFormat = "WASSCE Scheme: Covers Paper 1 (Objective Questions) and Paper 2 (Sections A, B, and C: Environment, Governance, and Socio-Economic Development).",
            recommendedTextbooks = listOf(
                "Aki-Ola Social Studies for Senior High Schools",
                "Social Studies for West Africa by Kwaku Appiah",
                "NaCCA Approved SHS Social Studies Syllabus"
            ),
            pagesCount = 88
        ),

        // Year 2 Core
        SubjectItem(
            id = "math_y2",
            title = "Core Mathematics",
            shortName = "Mathematics",
            year = 2,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-MATH02",
            description = "Intermediate SHS mathematics covering quadratic equations, plane geometry, trigonometry, and statistical distributions.",
            fileSizeFormatted = "5.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Mathematics_LM_Y2-FINAL-LEARNERS-VERSION.pdf",
            fileName = "GES_SHS2_Core_Mathematics_Syllabus.pdf",
            topics = listOf(
                "Quadratic Equations, Functions and Parabolic Graphs",
                "Simultaneous Linear and Non-Linear Equations",
                "Plane Geometry II: Circle Theorems and Tangents",
                "Trigonometry: Sine, Cosine and Tangent Ratios",
                "Angles of Elevation and Depression, Bearings",
                "Statistics: Mean, Median, Mode, Quartiles and Cumulative Frequency (Ogive)",
                "Probability: Simple and Compound Events",
                "Variation: Direct, Inverse, Joint and Partial"
            ),
            wassceFormat = "WASSCE Core: Heavily tested in Paper 2 Section A (Compulsory 5 Questions) and Section B (Advanced Problem Solving).",
            recommendedTextbooks = listOf(
                "Aki-Ola Core Mathematics SHS 1-3",
                "Comprehensive Core Mathematics by Isaac Mensah",
                "Past WASSCE Questions & Answers (2015-2025)"
            ),
            pagesCount = 118
        ),
        SubjectItem(
            id = "eng_y2",
            title = "English Language",
            shortName = "English",
            year = 2,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-ENG02",
            description = "Advanced writing techniques, argumentative discourse, grammatical concord, idiomatic expressions, and literary appreciation.",
            fileSizeFormatted = "5.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/English-Language_LM_Year-2_-LV.pdf",
            fileName = "GES_SHS2_English_Language_Syllabus.pdf",
            topics = listOf(
                "Argumentative and Expository Essay Formats",
                "Formal Letter Writing, Minutes and Memoranda",
                "Complex Grammatical Structures and Concord Rules",
                "Advanced Summary Skills: Condensation & Paraphrasing",
                "Reading Comprehension: Tone, Mood and Inference",
                "Oral English: Word Stress, Sentence Intonation and Rhyme",
                "Critical Appreciation of African Drama and Poetry"
            ),
            wassceFormat = "WASSCE Key: Direct preparation for 50-mark continuous writing (Paper 2) and Lexis and Structure nuances (Paper 3).",
            recommendedTextbooks = listOf(
                "Aki-Ola English for SHS (Updated Edition)",
                "Mastering WASSCE English by Dr. Kofi Owusu",
                "Selected African Poems and Plays for WAEC"
            ),
            pagesCount = 126
        ),
        SubjectItem(
            id = "sci_y2",
            title = "General / Integrated Science",
            shortName = "Science",
            year = 2,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-SCI02",
            description = "Deeper study of organic chemistry, human body systems, electronics, electromagnetism, and environmental conservation.",
            fileSizeFormatted = "6.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Science_LM_Y2_Online_LV.pdf",
            fileName = "GES_SHS2_Integrated_Science_Syllabus.pdf",
            topics = listOf(
                "Energy Transformation and Conservation of Energy",
                "Organic Chemistry: Hydrocarbons, Alkanes, Alkenes & Alkanols",
                "Acids, Bases and Salts: Neutralization and pH Scale",
                "Human Body Systems: Circulatory, Respiratory & Excretory",
                "Genetics and Heredity: Mendelian Inheritance",
                "Basic Electronics: P-N Junction Diodes, Transistors",
                "Magnetism and Electromagnetism in Appliances",
                "Farm Animal Production and Disease Control"
            ),
            wassceFormat = "WASSCE Intensive: Key concepts appearing annually in Theory (Paper 2) and Practical Alternative (Paper 3).",
            recommendedTextbooks = listOf(
                "Aki-Ola Integrated Science Complete Course",
                "Integrated Science for Senior High Schools by G. K. Mensah",
                "WAEC Syllabus & Past Practical Guides"
            ),
            pagesCount = 142
        ),
        SubjectItem(
            id = "soc_y2",
            title = "Social Studies",
            shortName = "Social Studies",
            year = 2,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-SOC02",
            description = "Examines national economic development, human resource management, population issues, and science in society.",
            fileSizeFormatted = "4.3 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Social-Studies-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Social_Studies_Syllabus.pdf",
            topics = listOf(
                "The Role of Science and Technology in National Development",
                "Human Resource Development in Ghana",
                "Youth and Discipline in Nation Building",
                "Managing Ghana's Natural and Financial Resources",
                "Population Growth and Urbanization Challenges",
                "Financial Security and Investment Strategies for Citizens",
                "Democratic Governance and the Rule of Law"
            ),
            wassceFormat = "WASSCE Scheme: Essential for Section B (Governance, Politics and Stability) and Section C (Social and Economic Development).",
            recommendedTextbooks = listOf(
                "Aki-Ola Social Studies for SHS",
                "Gateway to Social Studies by Emmanuel Addo",
                "Ghana National Development Planning Commission Digest"
            ),
            pagesCount = 94
        ),

        // Year 3 Core
        SubjectItem(
            id = "math_y3",
            title = "Core Mathematics",
            shortName = "Mathematics",
            year = 3,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-MATH03",
            description = "Senior final-year mathematics covering vectors, transformations, matrices, coordinate geometry, and commercial mathematics.",
            fileSizeFormatted = "5.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Mathematics_LM_Y3-FINAL-LEARNERS-VERSION.pdf",
            fileName = "GES_SHS3_Core_Mathematics_Syllabus.pdf",
            topics = listOf(
                "Rigid Motion and Geometric Transformations: Reflection, Rotation, Translation",
                "Vectors in Two Dimensions: Addition, Scalar Multiplication and Magnitude",
                "Coordinate Geometry: Gradient, Midpoint and Equation of a Straight Line",
                "Matrices and Determinants (2x2 Matrix Operations)",
                "Commercial Arithmetic: Depreciation, Compound Interest, Taxes and Hire Purchase",
                "Latitude and Longitude: Earth as a Sphere, Distance along Great/Small Circles",
                "Mensuration II: Frustum of Cones and Pyramids",
                "Comprehensive WASSCE Past Question Synthesis"
            ),
            wassceFormat = "WASSCE Final Prep: Directly addresses Paper 2 Section B 12-mark high-yield problem solving questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Core Mathematics SHS 1-3 (All-in-One Master)",
                "WAEC Past Questions and Marking Schemes (2010-2025)",
                "Core Mathematics for West Africa by J. B. Channon"
            ),
            pagesCount = 124
        ),
        SubjectItem(
            id = "eng_y3",
            title = "English Language",
            shortName = "English",
            year = 3,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-ENG03",
            description = "Mastery of WASSCE Paper 1, Paper 2, and Paper 3 examination techniques, advanced literary analysis, and essay synthesis.",
            fileSizeFormatted = "5.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/English-Language_LM_Year-3_-LV.pdf",
            fileName = "GES_SHS3_English_Language_Syllabus.pdf",
            topics = listOf(
                "Speech Writing, Public Speaking and Debate Formats",
                "Feature Articles and Letters to the Editor for National Press",
                "Mastery of Summary: Answering Exact Word Counts and Central Points",
                "Complex Comprehension: Metaphoric Language, Irony and Figures of Speech",
                "Synonyms, Antonyms, Idioms and Common Errors in English",
                "Oral English Examination Drills: Phonetic Symbols and Stress Patterns",
                "Comprehensive Review of Prescribed WASSCE Literature Dramas and Poetry"
            ),
            wassceFormat = "WASSCE Final Drills: Complete coverage for WAEC Papers 1, 2, and 3 scoring guidelines and chief examiners' reports.",
            recommendedTextbooks = listOf(
                "Aki-Ola English for Senior High Schools (Revision Guide)",
                "WAEC Chief Examiner's Report & Model Answers (English)",
                "Senior High School English Grammar & Composition by K. B. Mensah"
            ),
            pagesCount = 138
        ),
        SubjectItem(
            id = "sci_y3",
            title = "General / Integrated Science",
            shortName = "Science",
            year = 3,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-SCI03",
            description = "Advanced integrated science covering biotechnology, radioactivity, electrical power transmission, and environmental sustainability.",
            fileSizeFormatted = "6.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Science_LM_Y3_Online_LV.pdf",
            fileName = "GES_SHS3_Integrated_Science_Syllabus.pdf",
            topics = listOf(
                "Radioactivity and Nuclear Energy: Alpha, Beta, Gamma Radiations",
                "Electrical Power Generation, Transmission and Safe Domestic Wiring",
                "Applied Ecology: Ecosystems, Food Webs and Biogeochemical Cycles",
                "Environmental Biotechnology and Genetic Engineering",
                "Industrial Chemistry: Petroleum Refining, Plastics and Polymers",
                "Acids, Bases, and Neutralization in Industry and Agriculture",
                "Food Processing, Preservation and Nutrition Security in Ghana",
                "WASSCE Science Practical Identification and Calculations"
            ),
            wassceFormat = "WASSCE Master Course: Covers all components for obtaining Grade A1 in WASSCE Integrated Science.",
            recommendedTextbooks = listOf(
                "Aki-Ola Integrated Science Revision Companion",
                "Model Questions and Answers in Integrated Science for WASSCE",
                "Practical Integrated Science Guide by E. A. Asante"
            ),
            pagesCount = 150
        ),
        SubjectItem(
            id = "soc_y3",
            title = "Social Studies",
            shortName = "Social Studies",
            year = 3,
            courseTrack = CourseTrack.CORE,
            curriculumCode = "GES/NaCCA/SHS-CR-SOC03",
            description = "Critical review of international relations, peace-building, regional integration (AU, ECOWAS), and civic responsibilities.",
            fileSizeFormatted = "4.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Social-Studies-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Social_Studies_Syllabus.pdf",
            topics = listOf(
                "International Relations and Global Issues: UN, Commonwealth, AU and ECOWAS",
                "Conflict Prevention, Management and Resolution in Communities",
                "Promoting Sustainable Peace and National Security",
                "Civic Responsibilities and Rights of the Ghanaian Citizen",
                "The Role of the Judiciary and Legal Aid in Ghana",
                "Social and Economic Problems in Developing Nations",
                "Comprehensive WASSCE Social Studies Essay Mastery"
            ),
            wassceFormat = "WASSCE Target: Critical essay formulation strategies for high scoring in Paper 2 sections A, B, and C.",
            recommendedTextbooks = listOf(
                "Aki-Ola Social Studies Master Revision",
                "WASSCE Social Studies Past Questions with Detailed Solutions",
                "Contemporary Social Studies for West Africa by Dr. Samuel Quaye"
            ),
            pagesCount = 98
        ),

        // =========================================================================
        // ELECTIVE PROGRAM 1: GENERAL SCIENCE TRACK
        // =========================================================================

        // Elective Physics
        SubjectItem(
            id = "phys_y1",
            title = "Elective Physics (SHS 1)",
            shortName = "Physics",
            year = 1,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-PHYS01",
            description = "Fundamental physical principles including kinematics, dynamics, scalar/vector quantities, and properties of matter.",
            fileSizeFormatted = "5.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Physics-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Elective_Physics_Syllabus.pdf",
            topics = listOf(
                "Physical Quantities, Units and Measurement Errors",
                "Kinematics: Uniformly Accelerated Rectilinear Motion",
                "Newton's Laws of Motion and Momentum Conservation",
                "Work, Energy, Power and Mechanical Advantage",
                "Density, Pressure in Fluids and Archimedes' Principle",
                "Thermal Properties of Matter and Heat Transfer Mechanisms"
            ),
            wassceFormat = "WASSCE Physics: Prepares students for Paper 1 (50 Objectives), Paper 2 (Theory), and Paper 3 (Laboratory Practical Physics).",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Physics for SHS 1-3",
                "Advanced Level Physics by Nelkon and Parker",
                "GES Elective Physics Syllabus & Practical Manual"
            ),
            pagesCount = 135
        ),
        SubjectItem(
            id = "phys_y2",
            title = "Elective Physics (SHS 2)",
            shortName = "Physics",
            year = 2,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-PHYS02",
            description = "Waves, optics, electric fields, direct current circuits, and magnetic effects of electric currents.",
            fileSizeFormatted = "6.2 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Physics-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Elective_Physics_Syllabus.pdf",
            topics = listOf(
                "Simple Harmonic Motion (SHM) and Pendulum Oscillations",
                "Wave Motion, Sound Waves and Resonance",
                "Geometrical Optics: Reflection, Refraction, Lenses & Optical Instruments",
                "Electrostatics: Coulomb's Law and Electric Potential",
                "Electric Current, Resistance, Ohm's Law and Kirchhoff's Laws",
                "Magnetic Fields and Electromagnetic Induction"
            ),
            wassceFormat = "WASSCE Focus: Heavy emphasis on circuit diagrams, wave calculations, and optics practical experiments.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Physics Master Series",
                "Calculation in Physics for Senior Secondary Schools by Solomon",
                "WAEC Past Physics Practical Worksheets"
            ),
            pagesCount = 148
        ),
        SubjectItem(
            id = "phys_y3",
            title = "Elective Physics (SHS 3)",
            shortName = "Physics",
            year = 3,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-PHYS03",
            description = "Atomic and nuclear physics, quantum phenomena, semiconductors, and modern electronics.",
            fileSizeFormatted = "6.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Physics-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Elective_Physics_Syllabus.pdf",
            topics = listOf(
                "Photoelectric Effect and Wave-Particle Duality",
                "Atomic Structure, Bohr Model and Line Spectra",
                "Radioactivity, Half-Life and Binding Energy",
                "Semiconductor Physics, Diodes and Rectification",
                "Analogue and Digital Electronics: Logic Gates",
                "Comprehensive WASSCE Physics Practical Drills"
            ),
            wassceFormat = "WASSCE Mastery: Guarantees top performance in WAEC Physics Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Physics Final Revision",
                "WAEC Chief Examiner Physics Reports (2012-2025)",
                "College Physics by Sears and Zemansky"
            ),
            pagesCount = 160
        ),

        // Elective Chemistry
        SubjectItem(
            id = "chem_y1",
            title = "Elective Chemistry (SHS 1)",
            shortName = "Chemistry",
            year = 1,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-CHEM01",
            description = "Atomic structure, chemical bonding, mole concept, stoichiometry, and states of matter.",
            fileSizeFormatted = "5.6 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Chemistry-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Elective_Chemistry_Syllabus.pdf",
            topics = listOf(
                "Introduction to Chemistry & Laboratory Safety",
                "Atomic Structure and Electronic Configurations",
                "Periodic Table Trends and Chemical Periodicity",
                "Chemical Bonding: Ionic, Covalent and Metallic Bonds",
                "The Mole Concept and Stoichiometric Calculations",
                "Gases and Gas Laws: Boyle's, Charles' and Ideal Gas Law"
            ),
            wassceFormat = "WASSCE Foundation: Core stoichiometry and volumetric analysis calculations.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Chemistry for SHS 1-3",
                "General Chemistry by Raymond Chang",
                "GES Elective Chemistry Syllabus"
            ),
            pagesCount = 130
        ),
        SubjectItem(
            id = "chem_y2",
            title = "Elective Chemistry (SHS 2)",
            shortName = "Chemistry",
            year = 2,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-CHEM02",
            description = "Energetics, reaction kinetics, chemical equilibrium, redox reactions, and introductory organic chemistry.",
            fileSizeFormatted = "6.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Chemistry-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Elective_Chemistry_Syllabus.pdf",
            topics = listOf(
                "Thermochemistry: Enthalpy Changes and Hess's Law",
                "Reaction Rates and Activation Energy Factors",
                "Chemical Equilibrium and Le Chatelier's Principle",
                "Redox Reactions and Electrochemical Cells",
                "Organic Chemistry: Functional Groups, Alkanes, Alkenes & Alkynes",
                "Volumetric Analysis (Titration Calculations)"
            ),
            wassceFormat = "WASSCE Focus: Directly tested in Paper 3 Quantitative (Titration) and Qualitative Analysis.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Chemistry Master Guide",
                "Practical Chemistry for WASSCE by Dr. E. K. Owusu",
                "New School Chemistry by Osei Yaw Ababio"
            ),
            pagesCount = 144
        ),
        SubjectItem(
            id = "chem_y3",
            title = "Elective Chemistry (SHS 3)",
            shortName = "Chemistry",
            year = 3,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-CHEM03",
            description = "Advanced organic synthesis, polymers, transition metal chemistry, and environmental chemistry.",
            fileSizeFormatted = "6.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Chemistry-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Elective_Chemistry_Syllabus.pdf",
            topics = listOf(
                "Aromatic Hydrocarbons: Benzene Structure & Reactions",
                "Alkanols, Alkanoic Acids, Esters and Fats/Oils",
                "Synthetic Polymers: Addition and Condensation Polymerization",
                "Chemistry of Transition Elements & Complex Ions",
                "Qualitative Analysis: Cation and Anion Tests",
                "Comprehensive WASSCE Qualitative Chemistry Practical Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Complete mastery for WAEC Chemistry Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Chemistry Revision",
                "WASSCE Chemistry Past Questions & Detailed Marking Scheme",
                "Ababio Chemistry Revision Companion"
            ),
            pagesCount = 152
        ),

        // Elective Biology
        SubjectItem(
            id = "bio_y1",
            title = "Elective Biology (SHS 1)",
            shortName = "Biology",
            year = 1,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-BIO01",
            description = "Cellular biology, biological molecules, classification of living organisms, and plant anatomy.",
            fileSizeFormatted = "5.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Biology-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Elective_Biology_Syllabus.pdf",
            topics = listOf(
                "Cell Structure, Microscopy and Ultrastructure",
                "Biological Molecules: Carbohydrates, Lipids & Proteins",
                "Enzyme Structure, Kinetics and Inhibition",
                "Systematics and Classification of Living Organisms",
                "Plant Morphology, Anatomy and Water Relations",
                "Nutrition in Plants (Photosynthesis) and Animals"
            ),
            wassceFormat = "WASSCE Foundation: Essential for Paper 1 (Objective) and Paper 3 Practical Specimen Identification.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Biology for SHS 1-3",
                "Modern Biology for Senior Secondary Schools by S. T. Ramalingam",
                "GES Elective Biology Syllabus"
            ),
            pagesCount = 128
        ),
        SubjectItem(
            id = "bio_y2",
            title = "Elective Biology (SHS 2)",
            shortName = "Biology",
            year = 2,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-BIO02",
            description = "Transport in living organisms, gaseous exchange, excretion, homeostasis, nervous co-ordination, and ecology.",
            fileSizeFormatted = "5.9 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Biology-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Elective_Biology_Syllabus.pdf",
            topics = listOf(
                "Transport Systems in Plants and Mammals",
                "Gaseous Exchange and Cellular Respiration",
                "Excretion and Homeostasis (Kidney and Liver Function)",
                "Nervous and Hormonal Co-ordination in Animals",
                "Support and Locomotion in Animals",
                "Ecology: Ecosystem Dynamics and Population Studies"
            ),
            wassceFormat = "WASSCE Focus: Heavy representation in Paper 2 essay sections and practical dissection/specimen questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Biology Master Companion",
                "Biological Science by Green, Stout and Taylor",
                "WAEC Biology Specimen Identification Guide"
            ),
            pagesCount = 140
        ),
        SubjectItem(
            id = "bio_y3",
            title = "Elective Biology (SHS 3)",
            shortName = "Biology",
            year = 3,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-BIO03",
            description = "Genetics, molecular biology, evolution, reproduction, and biotechnology applications.",
            fileSizeFormatted = "6.3 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Elective-Biology-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Elective_Biology_Syllabus.pdf",
            topics = listOf(
                "Reproduction in Plants and Mammals",
                "Mendelian Inheritance and Gene Interaction",
                "DNA, RNA, Protein Synthesis and Genetic Code",
                "Variation, Mutation and Natural Selection",
                "Evolutionary Theories and Evidence",
                "Biotechnology: Genetic Engineering and Tissue Culture"
            ),
            wassceFormat = "WASSCE Master: Prepares students for Grade A1 in WAEC Elective Biology.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Biology Final Revision Guide",
                "WASSCE Past Questions with Marking Schemes (Biology)",
                "Senior Secondary Biology by Idodo Umeh"
            ),
            pagesCount = 146
        ),

        // Elective Mathematics (Further Math)
        SubjectItem(
            id = "emath_y1",
            title = "Elective Mathematics (SHS 1)",
            shortName = "Elective Math",
            year = 1,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-EMATH01",
            description = "Advanced algebra, set theory, functions, polynomials, and introductory coordinate geometry.",
            fileSizeFormatted = "5.2 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Additional-Maths-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Elective_Mathematics_Syllabus.pdf",
            topics = listOf(
                "Sets, Binary Operations and Algebraic Structures",
                "Functions, Mapping and Inverse Functions",
                "Polynomials: Remainder and Factor Theorems",
                "Quadratic Functions, Equations and Inequalities",
                "Indices, Logarithms and Exponential Functions",
                "Coordinate Geometry of the Straight Line and Circle"
            ),
            wassceFormat = "WASSCE Elective: High-level mathematical foundation for WAEC Further Mathematics Papers 1 & 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Mathematics for SHS 1-3",
                "Additional Mathematics by Pure and Applied by J. F. Talbert",
                "GES Elective Mathematics Syllabus"
            ),
            pagesCount = 120
        ),
        SubjectItem(
            id = "emath_y2",
            title = "Elective Mathematics (SHS 2)",
            shortName = "Elective Math",
            year = 2,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-EMATH02",
            description = "Differential and integral calculus, trigonometry, vectors in 3D, and mechanics.",
            fileSizeFormatted = "5.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Additional-Maths-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Elective_Mathematics_Syllabus.pdf",
            topics = listOf(
                "Trigonometric Identities, Equations and Compound Angles",
                "Differential Calculus: Limits, Differentiation from First Principles",
                "Applications of Differentiation: Tangents, Normals & Maxima/Minima",
                "Integral Calculus: Definite/Indefinite Integrals and Area Under Curves",
                "Vectors in 3-Dimensional Space",
                "Mechanics: Kinematics of Motion in a Straight Line and Projectiles"
            ),
            wassceFormat = "WASSCE Core Focus: Covers 60% of Section B 12-mark calculus and mechanics questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Mathematics Master Series",
                "Pure Mathematics for Advanced Level by Bunday and Mulholland",
                "Past WASSCE Elective Mathematics Worked Solutions"
            ),
            pagesCount = 138
        ),
        SubjectItem(
            id = "emath_y3",
            title = "Elective Mathematics (SHS 3)",
            shortName = "Elective Math",
            year = 3,
            courseTrack = CourseTrack.GENERAL_SCIENCE,
            curriculumCode = "GES/NaCCA/SHS-EL-EMATH03",
            description = "Advanced mechanics, statistics, probability distributions, matrices, and linear transformation.",
            fileSizeFormatted = "6.1 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Additional-Maths-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Elective_Mathematics_Syllabus.pdf",
            topics = listOf(
                "Mechanics: Statics, Equilibrium of Rigid Bodies & Moments",
                "Probability Distributions: Binomial and Normal Distributions",
                "Statistics: Variance, Correlation and Linear Regression",
                "Matrices, Linear Transformations and Eigenvalues",
                "Differential Equations of First Order",
                "Comprehensive WASSCE Elective Math Revision Drills"
            ),
            wassceFormat = "WASSCE Mastery: Complete preparation for WASSCE Elective Mathematics Paper 1 and Paper 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective Mathematics Final Revision",
                "WAEC Chief Examiner Reports for Elective Mathematics",
                "Advanced Mathematics by L. Bostock and S. Chandler"
            ),
            pagesCount = 145
        ),

        // =========================================================================
        // ELECTIVE PROGRAM 2: GENERAL ARTS TRACK
        // =========================================================================

        // Economics
        SubjectItem(
            id = "econ_y1",
            title = "Economics (SHS 1)",
            shortName = "Economics",
            year = 1,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-ECON01",
            description = "Basic economic concepts, price theory, demand and supply, elasticities, and production.",
            fileSizeFormatted = "4.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Economics-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Economics_Syllabus.pdf",
            topics = listOf(
                "Introduction to Economics: Scarcity, Choice and Scale of Preference",
                "Economic Systems: Capitalist, Socialist and Mixed Economies",
                "Demand and Supply Theory, Equilibrium Price Determination",
                "Elasticity of Demand and Supply",
                "Theory of Production, Division of Labour and Scale of Production",
                "Business Organizations: Sole Proprietorship, Partnerships, Companies"
            ),
            wassceFormat = "WASSCE Foundation: Focuses on microeconomics data response questions in Paper 2 Section A.",
            recommendedTextbooks = listOf(
                "Aki-Ola Economics for Senior High Schools",
                "Comprehensive Economics for Senior Secondary Schools by N. A. Coleman",
                "GES Economics Teaching Syllabus"
            ),
            pagesCount = 110
        ),
        SubjectItem(
            id = "econ_y2",
            title = "Economics (SHS 2)",
            shortName = "Economics",
            year = 2,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-ECON02",
            description = "Market structures, national income accounting, money and banking, and public finance.",
            fileSizeFormatted = "5.3 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Economics-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Economics_Syllabus.pdf",
            topics = listOf(
                "Market Structures: Perfect Competition, Monopoly, Oligopoly",
                "National Income Accounting: GDP, GNP, NNP and Per Capita Income",
                "Money and Banking: Central Bank, Commercial Banks & Monetary Policy",
                "Public Finance: Taxation, Government Expenditure and Budgeting",
                "Inflation: Causes, Effects and Control Measures",
                "Unemployment: Types and Solutions in Developing Nations"
            ),
            wassceFormat = "WASSCE Focus: Heavy concentration in macroeconomics analytical essays and calculations.",
            recommendedTextbooks = listOf(
                "Aki-Ola Economics Master Guide",
                "Economics for West Africa by K. D. Amoah",
                "Bank of Ghana & Ministry of Finance Economic Reports"
            ),
            pagesCount = 125
        ),
        SubjectItem(
            id = "econ_y3",
            title = "Economics (SHS 3)",
            shortName = "Economics",
            year = 3,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-ECON03",
            description = "International trade, balance of payments, economic integration (ECOWAS, AfCFTA), and economic planning.",
            fileSizeFormatted = "5.7 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Economics-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Economics_Syllabus.pdf",
            topics = listOf(
                "International Trade: Comparative Advantage and Protectionism",
                "Balance of Payments and Foreign Exchange Rates",
                "International Economic Organizations: IMF, World Bank, WTO, AfCFTA",
                "Economic Integration in West Africa: ECOWAS Achievements",
                "Economic Planning and Development Strategies in Ghana",
                "WASSCE Economics Data Response & Essay Revision Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees grade A1 in WASSCE Economics Papers 1 and 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Economics Revision Guide",
                "WASSCE Past Economics Questions & Detailed Solutions",
                "Textbook of Economics by Hanson"
            ),
            pagesCount = 132
        ),

        // Geography
        SubjectItem(
            id = "geog_y1",
            title = "Geography (SHS 1)",
            shortName = "Geography",
            year = 1,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GEOG01",
            description = "Earth as a planet, map reading fundamentals, landforms, rocks, and weathering.",
            fileSizeFormatted = "5.1 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Geography-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Geography_Syllabus.pdf",
            topics = listOf(
                "The Earth and Solar System: Latitude, Longitude and Time",
                "Map Reading and Interpretation: Scales, Contours and Grid References",
                "The Earth's Crust: Igneous, Sedimentary and Metamorphic Rocks",
                "Internal Processes: Vulcanicity, Earthquakes and Plate Tectonics",
                "Weathering and Mass Movement",
                "Rivers and Fluvial Landforms: Upper, Middle and Lower Courses"
            ),
            wassceFormat = "WASSCE Foundation: Direct preparation for Paper 1 (Practical Geography & Map Work).",
            recommendedTextbooks = listOf(
                "Aki-Ola Geography for Senior High Schools",
                "Certificate Physical and Human Geography by Goh Cheng Leong",
                "GES Geography Syllabus"
            ),
            pagesCount = 118
        ),
        SubjectItem(
            id = "geog_y2",
            title = "Geography (SHS 2)",
            shortName = "Geography",
            year = 2,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GEOG02",
            description = "Climatology, vegetation zones, soil formation, regional geography of West Africa, and settlement geography.",
            fileSizeFormatted = "5.6 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Geography-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Geography_Syllabus.pdf",
            topics = listOf(
                "Weather and Climate: Temperature, Pressure, Winds & Rainfall",
                "Major Climatic and Natural Vegetation Zones of the World",
                "Soil Formation, Profiles and Soil Erosion Control",
                "Regional Geography of West Africa: Relief, Climate and Vegetation",
                "Settlement Geography: Rural and Urban Settlement Patterns",
                "Population Geography: Growth, Distribution and Migration"
            ),
            wassceFormat = "WASSCE Focus: Heavy emphasis in Paper 2 (Physical & Regional Geography of West Africa).",
            recommendedTextbooks = listOf(
                "Aki-Ola Geography Master Series",
                "A Geography of West Africa by H. R. Jarrett",
                "WAEC Practical Map Reading Manual"
            ),
            pagesCount = 134
        ),
        SubjectItem(
            id = "geog_y3",
            title = "Geography (SHS 3)",
            shortName = "Geography",
            year = 3,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GEOG03",
            description = "Geography of Ghana: Relief, drainage, agriculture, mining, industrialization, and environmental management.",
            fileSizeFormatted = "6.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Geography-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Geography_Syllabus.pdf",
            topics = listOf(
                "Geography of Ghana: Physical Setting, Relief and Drainage",
                "Agriculture in Ghana: Food and Cash Crop Production",
                "Mining Industry in Ghana: Gold, Bauxite, Manganese & Crude Oil",
                "Power and Energy Resources: Akosombo Hydroelectric Power",
                "Manufacturing Industries and Tourism in Ghana",
                "Environmental Issues: Deforestation, Galamsey & Coastal Erosion",
                "Comprehensive Map Reading & Statistical Mapping Mastery"
            ),
            wassceFormat = "WASSCE Final Prep: Complete coverage for WAEC Geography Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola Geography of Ghana & West Africa",
                "WASSCE Geography Past Questions & Topo Maps",
                "Geography of Ghana by E. A. Boateng"
            ),
            pagesCount = 142
        ),

        // Government
        SubjectItem(
            id = "gov_y1",
            title = "Government (SHS 1)",
            shortName = "Government",
            year = 1,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GOV01",
            description = "Concepts of state, sovereignty, rule of law, constitutions, organs of government, and political ideologies.",
            fileSizeFormatted = "4.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Government-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Government_Syllabus.pdf",
            topics = listOf(
                "Meaning, Scope and Importance of Government",
                "Basic Concepts: State, Nation, Sovereignty, Power and Authority",
                "Rule of Law, Constitutionalism and Fundamental Human Rights",
                "Types of Constitutions: Written, Unwritten, Rigid, Flexible",
                "Organs of Government: Legislature, Executive and Judiciary",
                "Political Ideologies: Democracy, Capitalism, Socialism, Fascism"
            ),
            wassceFormat = "WASSCE Foundation: Essential for Paper 1 (Objectives) and Paper 2 Section A (Political Theory).",
            recommendedTextbooks = listOf(
                "Aki-Ola Government for Senior High Schools",
                "Government for Senior Secondary Schools by B. O. Asare",
                "GES Government Syllabus"
            ),
            pagesCount = 104
        ),
        SubjectItem(
            id = "gov_y2",
            title = "Government (SHS 2)",
            shortName = "Government",
            year = 2,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GOV02",
            description = "Political development in West Africa, pre-colonial systems, colonial administration, and constitutional developments in Ghana.",
            fileSizeFormatted = "5.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Government-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Government_Syllabus.pdf",
            topics = listOf(
                "Pre-Colonial Political Systems in Ghana (Akan, Northern States)",
                "Colonial Rule in West Africa: British Indirect Rule vs French Assimilation",
                "Constitutional Developments in Ghana (1925 Clifford to 1957 Independence)",
                "The Nationalist Movement and Independence Struggle in Ghana",
                "Political Parties, Electoral Systems and Franchise",
                "Public Opinion, Pressure Groups and Civil Society"
            ),
            wassceFormat = "WASSCE Focus: Heavy concentration in Section B (Political History of Ghana & West Africa).",
            recommendedTextbooks = listOf(
                "Aki-Ola Government Master Edition",
                "Constitutional History of Ghana by F. K. Drah",
                "Government of Ghana by K. B. S. Asante"
            ),
            pagesCount = 118
        ),
        SubjectItem(
            id = "gov_y3",
            title = "Government (SHS 3)",
            shortName = "Government",
            year = 3,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GOV03",
            description = "Post-independence Ghana constitutions (1960, 1969, 1979, 1992), local government, foreign policy, and international organizations.",
            fileSizeFormatted = "5.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Government-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Government_Syllabus.pdf",
            topics = listOf(
                "Post-Independence Constitutions: The 1992 Fourth Republican Constitution",
                "Local Government and Decentralization in Ghana (District Assemblies)",
                "Ghana's Foreign Policy and Non-Aligned Movement",
                "International & Regional Bodies: Commonwealth, AU, ECOWAS, UNO",
                "Civil Service, Public Corporations and Public Complaints (Ombudsman)",
                "WASSCE Government Essay Formulation & Past Question Drills"
            ),
            wassceFormat = "WASSCE Master: Guarantees grade A1 in WASSCE Government Papers 1 and 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Government Final Revision Guide",
                "The 1992 Constitution of the Republic of Ghana",
                "WASSCE Past Government Questions and Marking Schemes"
            ),
            pagesCount = 126
        ),

        // Literature-in-English
        SubjectItem(
            id = "lit_y1",
            title = "Literature-in-English (SHS 1)",
            shortName = "Literature",
            year = 1,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-LIT01",
            description = "Literary genres, literary devices, poetic techniques, and analysis of prescribed African prose and drama.",
            fileSizeFormatted = "4.2 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Literature-in-English-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Literature_In_English_Syllabus.pdf",
            topics = listOf(
                "Introduction to Literary Genres: Drama, Prose and Poetry",
                "Literary Terms & Devices: Metaphor, Simile, Irony, Personification, Alliteration",
                "Elements of Drama: Plot, Theme, Characterization, Conflict, Staging",
                "Elements of Prose: Point of View, Narrative Technique, Setting",
                "Analysis of Prescribed African Drama Texts (WAEC Anthology)",
                "Analysis of Selected African Poetry"
            ),
            wassceFormat = "WASSCE Foundation: Direct preparation for WAEC Paper 1 (General Literary Principles).",
            recommendedTextbooks = listOf(
                "Aki-Ola Literature-in-English for Senior High Schools",
                "WAEC Prescribed African & Non-African Literature Texts (2021-2025)",
                "Unlocking Literature-in-English by K. E. Baidoo"
            ),
            pagesCount = 96
        ),
        SubjectItem(
            id = "lit_y2",
            title = "Literature-in-English (SHS 2)",
            shortName = "Literature",
            year = 2,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-LIT02",
            description = "In-depth study of prescribed Non-African prose, Shakespearean drama, and Non-African poetry.",
            fileSizeFormatted = "4.7 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Literature-in-English-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Literature_In_English_Syllabus.pdf",
            topics = listOf(
                "Analysis of Prescribed Non-African Drama (Shakespearean Text)",
                "Analysis of Prescribed Non-African Prose Novels",
                "Detailed Study of Selected Non-African Poems",
                "Themes, Character Analysis and Dramatic Techniques",
                "Contextual and Structural Passages Analysis",
                "Essay Writing on Literary Characters and Themes"
            ),
            wassceFormat = "WASSCE Focus: Heavy emphasis in Paper 2 (Prescribed African & Non-African Drama/Prose).",
            recommendedTextbooks = listOf(
                "Aki-Ola Literature Companion Guide",
                "WAEC Recommended Shakespeare Plays with Notes",
                "Critical Notes on WASSCE Literature Texts by Dr. Owusu"
            ),
            pagesCount = 110
        ),
        SubjectItem(
            id = "lit_y3",
            title = "Literature-in-English (SHS 3)",
            shortName = "Literature",
            year = 3,
            courseTrack = CourseTrack.GENERAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-LIT03",
            description = "Comprehensive review of all WASSCE prescribed texts, unseen prose and poetry, and literary essay mastery.",
            fileSizeFormatted = "5.1 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Literature-in-English-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Literature_In_English_Syllabus.pdf",
            topics = listOf(
                "Unseen Prose and Poetry Analysis Techniques",
                "Comparative Analysis of African and Non-African Themes",
                "Comprehensive Review of 12 Prescribed WASSCE Poems",
                "Mastery of Context Questions and Model Essay Answers",
                "WAEC Scoring Guidelines & Chief Examiner Literary Insights",
                "Full Mock Drills for WAEC Literature Papers 1, 2, and 3"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees Grade A1 across all three WAEC Literature Papers.",
            recommendedTextbooks = listOf(
                "Aki-Ola Literature Final Revision Master",
                "WASSCE Past Literature Questions and Model Essays",
                "Complete Guide to WASSCE Literature by E. A. Tagoe"
            ),
            pagesCount = 118
        ),

        // =========================================================================
        // ELECTIVE PROGRAM 3: BUSINESS TRACK
        // =========================================================================

        // Financial Accounting
        SubjectItem(
            id = "fa_y1",
            title = "Financial Accounting (SHS 1)",
            shortName = "Fin. Accounting",
            year = 1,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-FA01",
            description = "Bookkeeping principles, double-entry system, trial balance, and preparation of final accounts for sole traders.",
            fileSizeFormatted = "5.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Accounting_LM_Y1_Online_LV.pdf",
            fileName = "GES_SHS1_Financial_Accounting_Syllabus.pdf",
            topics = listOf(
                "Introduction to Accounting & Accounting Equation",
                "Books of Original Entry: Daybooks, Journals and Cash Books",
                "The Ledger and Double-Entry System",
                "The Trial Balance and Extraction of Balances",
                "Final Accounts of Sole Traders: Trading, Profit & Loss Account and Balance Sheet",
                "Adjustments to Final Accounts: Accruals, Prepayments, Bad Debts & Provisions"
            ),
            wassceFormat = "WASSCE Foundation: Foundation for WAEC Financial Accounting Paper 1 and Paper 2 ledger posting questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Financial Accounting for SHS 1-3",
                "Business Accounting 1 by Frank Wood and Alan Sangster",
                "GES Financial Accounting Syllabus"
            ),
            pagesCount = 120
        ),
        SubjectItem(
            id = "fa_y2",
            title = "Financial Accounting (SHS 2)",
            shortName = "Fin. Accounting",
            year = 2,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-FA02",
            description = "Bank reconciliation, control accounts, correction of errors, partnership accounts, and non-profit organization accounts.",
            fileSizeFormatted = "5.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Accounting_LM_Y2_Online_LV.pdf",
            fileName = "GES_SHS2_Financial_Accounting_Syllabus.pdf",
            topics = listOf(
                "Bank Reconciliation Statements",
                "Control Accounts: Sales and Purchases Ledgers",
                "Correction of Errors and Suspense Accounts",
                "Partnership Accounts: Admission, Retirement and Goodwill",
                "Accounts of Non-Profit Making Organizations (Clubs & Societies)",
                "Single Entry and Incomplete Records"
            ),
            wassceFormat = "WASSCE Focus: Directly addresses Paper 2 compulsory partnership and bank reconciliation questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Financial Accounting Master Guide",
                "Financial Accounting for West Africa by Isaac Mensah",
                "WAEC Past Accounting Worked Solutions"
            ),
            pagesCount = 136
        ),
        SubjectItem(
            id = "fa_y3",
            title = "Financial Accounting (SHS 3)",
            shortName = "Fin. Accounting",
            year = 3,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-FA03",
            description = "Company accounts, manufacturing accounts, departmental accounts, ratio analysis, and auditing basics.",
            fileSizeFormatted = "5.9 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Accounting_LM_Y3_Online_LV.pdf",
            fileName = "GES_SHS3_Financial_Accounting_Syllabus.pdf",
            topics = listOf(
                "Limited Liability Company Accounts: Shares, Debentures and Dividends",
                "Manufacturing Accounts: Direct/Indirect Costs and Production Profit",
                "Departmental and Branch Accounts",
                "Interpretation of Accounts: Financial Ratio Analysis",
                "Introduction to Auditing and Public Sector Accounting",
                "Comprehensive WASSCE Accounting Revision Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Complete mastery for WAEC Financial Accounting Paper 1 and Paper 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Financial Accounting Final Revision",
                "WASSCE Accounting Past Questions & Marking Schemes",
                "Frank Wood Business Accounting 2"
            ),
            pagesCount = 144
        ),

        // Business Management
        SubjectItem(
            id = "bm_y1",
            title = "Business Management (SHS 1)",
            shortName = "Business Mgmt",
            year = 1,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-BM01",
            description = "Foundations of business environment, business ownership forms, functions of management, and organizational structure.",
            fileSizeFormatted = "4.6 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Business-Management-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Business_Management_Syllabus.pdf",
            topics = listOf(
                "Nature and Purpose of Business Environment",
                "Forms of Business Ownership: Sole Trader, Partnership, Companies, State Enterprises",
                "Functions of Management: Planning, Organizing, Directing & Controlling",
                "Organizational Structure and Organizational Charts",
                "Communication in Business: Internal and External Channels",
                "Office Management and Business Records"
            ),
            wassceFormat = "WASSCE Foundation: Essential for Paper 1 (Objectives) and Paper 2 Section A (General Management Theory).",
            recommendedTextbooks = listOf(
                "Aki-Ola Business Management for Senior High Schools",
                "Business Management for West Africa by O. A. Lartey",
                "GES Business Management Syllabus"
            ),
            pagesCount = 108
        ),
        SubjectItem(
            id = "bm_y2",
            title = "Business Management (SHS 2)",
            shortName = "Business Mgmt",
            year = 2,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-BM02",
            description = "Human resource management, marketing management, production management, and financial management.",
            fileSizeFormatted = "5.1 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Business-Management-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Business_Management_Syllabus.pdf",
            topics = listOf(
                "Human Resource Management: Recruitment, Selection, Training & Motivation",
                "Marketing Management: The 4 Ps (Product, Price, Place, Promotion)",
                "Production Management: Plant Location, Layout & Quality Control",
                "Financial Management: Sources of Short-term and Long-term Capital",
                "Business Law: Law of Contract, Sale of Goods and Agency",
                "Consumer Protection and Business Ethics"
            ),
            wassceFormat = "WASSCE Focus: Heavy emphasis in Paper 2 Section B case studies and functional area questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Business Management Master Series",
                "Modern Business Management by S. K. Ampah",
                "WAEC Past Business Management Questions"
            ),
            pagesCount = 122
        ),
        SubjectItem(
            id = "bm_y3",
            title = "Business Management (SHS 3)",
            shortName = "Business Mgmt",
            year = 3,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-BM03",
            description = "International business, risk management, insurance, entrepreneurship, and strategic planning.",
            fileSizeFormatted = "5.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Business-Management-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Business_Management_Syllabus.pdf",
            topics = listOf(
                "International Business and Foreign Trade Procedures",
                "Risk Management and Insurance Principles (Indemnity, Utmost Good Faith)",
                "Entrepreneurship and Small Business Start-up in Ghana",
                "Business Plan Formulation and Project Appraisal",
                "Social Responsibility of Business to Stakeholders",
                "WASSCE Business Management Case Study & Essay Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Complete preparation for WAEC Business Management Papers 1 and 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Business Management Final Revision",
                "WASSCE Business Management Past Questions & Marking Schemes",
                "Entrepreneurship & Business Management by K. B. Appiah"
            ),
            pagesCount = 128
        ),

        // Information & Communications Technology (ICT)
        SubjectItem(
            id = "ict_y1",
            title = "Elective ICT (SHS 1)",
            shortName = "Elective ICT",
            year = 1,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-ICT01",
            description = "Computer hardware architecture, operating systems, word processing, spreadsheets, and information security.",
            fileSizeFormatted = "5.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/ICT-YEAR-1-LV-ONLINE.pdf",
            fileName = "GES_SHS1_Elective_ICT_Syllabus.pdf",
            topics = listOf(
                "Computer Architecture: CPU, Memory, Input/Output Devices",
                "System Software and Operating Systems (Windows/Linux)",
                "Advanced Word Processing: Formatting, Mail Merge & Tables",
                "Spreadsheet Processing: Formulas, Functions (VLOOKUP) & Charts",
                "Data Representation: Binary, Octal & Hexadecimal Number Systems",
                "Information Security, Viruses, Malware & Cyber Ethics"
            ),
            wassceFormat = "WASSCE Foundation: Direct preparation for WAEC ICT Paper 1 (Objectives) and Paper 3 (Practical Computer Test).",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective ICT for Senior High Schools",
                "Computer Studies for Senior Secondary Schools by O. A. Adedapo",
                "GES Elective ICT Syllabus"
            ),
            pagesCount = 130
        ),
        SubjectItem(
            id = "ict_y2",
            title = "Elective ICT (SHS 2)",
            shortName = "Elective ICT",
            year = 2,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-ICT02",
            description = "Database management systems (DBMS), computer networking, web design (HTML/CSS), and algorithm design.",
            fileSizeFormatted = "6.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/ICT-YEAR-2-LV-ONLINE.pdf",
            fileName = "GES_SHS2_Elective_ICT_Syllabus.pdf",
            topics = listOf(
                "Database Management Systems (MS Access / MySQL): Tables, Queries & Forms",
                "Computer Networking: LAN, WAN, Topologies & TCP/IP Protocols",
                "Web Development: HTML5 Structural Tags and CSS Styling",
                "Algorithms and Flowcharts: Sequential, Selection & Iterative Logic",
                "System Analysis and Software Development Life Cycle (SDLC)",
                "Internet Technologies, E-Commerce and Digital Payment Systems"
            ),
            wassceFormat = "WASSCE Focus: Heavy emphasis in Paper 3 practical MS Access and HTML coding tests.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective ICT Master Companion",
                "WAEC ICT Practical Laboratory Guide",
                "Web Design and Database Fundamentals by K. Mensah"
            ),
            pagesCount = 145
        ),
        SubjectItem(
            id = "ict_y3",
            title = "Elective ICT (SHS 3)",
            shortName = "Elective ICT",
            year = 3,
            courseTrack = CourseTrack.BUSINESS,
            curriculumCode = "GES/NaCCA/SHS-EL-ICT03",
            description = "Computer programming (VB.NET / Python fundamentals), data structures, technology impact, and WASSCE practical drills.",
            fileSizeFormatted = "6.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/ICT-YEAR-3-LV-ONLINE.pdf",
            fileName = "GES_SHS3_Elective_ICT_Syllabus.pdf",
            topics = listOf(
                "Introduction to Computer Programming: Variables, Control Structures",
                "Arrays and Basic Data Structures",
                "Event-Driven Programming and GUI Application Design",
                "Social, Legal and Economic Impacts of Computing",
                "Emerging Technologies: Artificial Intelligence, Cloud Computing & IoT",
                "WASSCE ICT Practical Hands-on Examination Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees Grade A1 in WAEC ICT Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola Elective ICT Final Revision",
                "WASSCE ICT Past Questions & Practical Solutions",
                "Programming & Database Systems for SHS by E. K. Danso"
            ),
            pagesCount = 155
        ),

        // =========================================================================
        // ELECTIVE PROGRAM 4: VISUAL ARTS TRACK
        // =========================================================================

        // General Knowledge in Art (GKA)
        SubjectItem(
            id = "gka_y1",
            title = "General Knowledge in Art (SHS 1)",
            shortName = "GKA",
            year = 1,
            courseTrack = CourseTrack.VISUAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GKA01",
            description = "Art history, Ghanaian traditional arts, elements and principles of design, and basic studio safety.",
            fileSizeFormatted = "4.5 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Knowledge-in-Art-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_General_Knowledge_In_Art_Syllabus.pdf",
            topics = listOf(
                "Definition, Scope and Functions of Art in Society",
                "Elements of Art: Line, Shape, Color, Texture, Value, Space",
                "Principles of Design: Balance, Rhythm, Contrast, Harmony, Unity",
                "Prehistoric Art and Ancient Egyptian Art",
                "Traditional Ghanaian Arts: Kente, Adinkra Symbols, Pottery & Carving",
                "Basic Art Tools, Materials and Studio Management"
            ),
            wassceFormat = "WASSCE Foundation: Foundation for WAEC GKA Paper 1 (Objectives) and Paper 2 (Theory).",
            recommendedTextbooks = listOf(
                "Aki-Ola General Knowledge in Art for SHS 1-3",
                "General Knowledge in Art for Senior Secondary Schools by K. A. Sekyi",
                "GES GKA Teaching Syllabus"
            ),
            pagesCount = 102
        ),
        SubjectItem(
            id = "gka_y2",
            title = "General Knowledge in Art (SHS 2)",
            shortName = "GKA",
            year = 2,
            courseTrack = CourseTrack.VISUAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GKA02",
            description = "African art history, Renaissance art, art appreciation, criticism, and museum studies.",
            fileSizeFormatted = "5.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Knowledge-in-Art-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_General_Knowledge_In_Art_Syllabus.pdf",
            topics = listOf(
                "Traditional African Art: Nok, Benin Bronzes, Asante Goldweights",
                "European Art History: Renaissance, Impressionism & Cubism",
                "Art Appreciation and Art Criticism Methodologies",
                "Exhibition, Museum and Gallery Studies in Ghana",
                "Copyright Laws and Intellectual Property in Art",
                "Color Theory, Pigments and Color Chemistry"
            ),
            wassceFormat = "WASSCE Focus: Direct preparation for WAEC GKA essay questions and art critique.",
            recommendedTextbooks = listOf(
                "Aki-Ola GKA Master Guide",
                "African Art History by Frank Willett",
                "WAEC GKA Past Questions & Model Answers"
            ),
            pagesCount = 116
        ),
        SubjectItem(
            id = "gka_y3",
            title = "General Knowledge in Art (SHS 3)",
            shortName = "GKA",
            year = 3,
            courseTrack = CourseTrack.VISUAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GKA03",
            description = "Contemporary Ghanaian artists, art entrepreneurship, portfolio creation, and WASSCE practical project drills.",
            fileSizeFormatted = "5.3 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Knowledge-in-Art-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_General_Knowledge_In_Art_Syllabus.pdf",
            topics = listOf(
                "Contemporary Ghanaian Visual Artists and Pioneers (El Anatsui, Ablade Glover)",
                "Art Entrepreneurship and Marketing Art Products",
                "Portfolio Preparation and Exhibition Curation",
                "Art in National Development and Tourism Promotion",
                "WASSCE GKA Theory and Lettering/Design Practical Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees Grade A1 in WAEC GKA Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola GKA Final Revision Guide",
                "WASSCE GKA Past Questions & Marking Schemes",
                "Contemporary Art in Ghana by Dr. Seth Asare"
            ),
            pagesCount = 124
        ),

        // Graphic Design
        SubjectItem(
            id = "gd_y1",
            title = "Graphic Design (SHS 1)",
            shortName = "Graphic Design",
            year = 1,
            courseTrack = CourseTrack.VISUAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GD01",
            description = "Lettering techniques, calligraphy, layout design, poster design, and color application in graphics.",
            fileSizeFormatted = "4.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Graphic-Design-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Graphic_Design_Syllabus.pdf",
            topics = listOf(
                "Introduction to Graphic Design & Visual Communication",
                "Lettering Styles: Roman, Gothic, Italic and Calligraphy",
                "Principles of Layout and Composition in Graphics",
                "Color Application and Color Schemes in Poster Design",
                "Paper Types, Sizes and Graphic Design Equipment",
                "Designing Simple Book Covers and Greeting Cards"
            ),
            wassceFormat = "WASSCE Foundation: Essential for WAEC Graphic Design Paper 2 Practical Project Execution.",
            recommendedTextbooks = listOf(
                "Aki-Ola Graphic Design for SHS 1-3",
                "Graphic Design Principles for West Africa by E. K. Agyeman",
                "GES Graphic Design Syllabus"
            ),
            pagesCount = 106
        ),
        SubjectItem(
            id = "gd_y2",
            title = "Graphic Design (SHS 2)",
            shortName = "Graphic Design",
            year = 2,
            courseTrack = CourseTrack.VISUAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GD02",
            description = "Corporate identity design (logos, letterheads), package design, printmaking, and digital graphic software.",
            fileSizeFormatted = "5.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Graphic-Design-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Graphic_Design_Syllabus.pdf",
            topics = listOf(
                "Corporate Identity Design: Logos, Letterheads, Business Cards",
                "Package Design: Cartons, Labels and Mock-ups",
                "Printmaking Techniques: Screen Printing, Relief Printing, Monotype",
                "Illustration Techniques for Children's Books and Advertising",
                "Digital Graphic Software: CorelDraw, Adobe Photoshop Basics",
                "Book Binding and Finishing Methods"
            ),
            wassceFormat = "WASSCE Focus: Covers WAEC 5-day timed practical examination requirements.",
            recommendedTextbooks = listOf(
                "Aki-Ola Graphic Design Master Series",
                "Printmaking and Graphic Art by J. O. Mensah",
                "WAEC Graphic Design Past Practical Projects"
            ),
            pagesCount = 122
        ),
        SubjectItem(
            id = "gd_y3",
            title = "Graphic Design (SHS 3)",
            shortName = "Graphic Design",
            year = 3,
            courseTrack = CourseTrack.VISUAL_ARTS,
            curriculumCode = "GES/NaCCA/SHS-EL-GD03",
            description = "Advertising design campaigns, digital publishing, portfolio review, and WASSCE timed practical drills.",
            fileSizeFormatted = "5.8 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Graphic-Design-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Graphic_Design_Syllabus.pdf",
            topics = listOf(
                "Advertising Campaigns: Billboards, Brochures, Flyers, Digital Banners",
                "Typography and Desktop Publishing Mastery",
                "Cost Estimation and Pricing Graphic Design Services",
                "Professional Portfolio Assembly and Studio Practice",
                "Comprehensive WASSCE Graphic Design Timed Practical Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees top practical grades in WAEC Graphic Design.",
            recommendedTextbooks = listOf(
                "Aki-Ola Graphic Design Final Revision Guide",
                "WASSCE Past Graphic Design Exam Papers",
                "Professional Graphic Design Practice by K. B. Osei"
            ),
            pagesCount = 130
        ),

        // =========================================================================
        // ELECTIVE PROGRAM 5: HOME ECONOMICS TRACK
        // =========================================================================

        // Food and Nutrition
        SubjectItem(
            id = "fn_y1",
            title = "Food & Nutrition (SHS 1)",
            shortName = "Food & Nutrition",
            year = 1,
            courseTrack = CourseTrack.HOME_ECONOMICS,
            curriculumCode = "GES/NaCCA/SHS-EL-FN01",
            description = "Nutrients, balanced diets, kitchen equipment, food hygiene, and basic culinary methods.",
            fileSizeFormatted = "4.7 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Food-and-Nutrition-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Food_And_Nutrition_Syllabus.pdf",
            topics = listOf(
                "Introduction to Food & Nutrition & Kitchen Safety",
                "Macro and Micro Nutrients: Functions, Sources & Deficiency Diseases",
                "Meal Planning for Family Members across Life Stages",
                "Basic Cooking Methods: Boiling, Steaming, Roasting, Frying, Baking",
                "Kitchen Layout, Hygiene and Food Safety Practices",
                "Selection, Storage and Preservation of Perishable Foods"
            ),
            wassceFormat = "WASSCE Foundation: Foundation for WAEC Food & Nutrition Paper 1 and Paper 3 Practical Cooking Exam.",
            recommendedTextbooks = listOf(
                "Aki-Ola Food & Nutrition for Senior High Schools",
                "Food and Nutrition for West Africa by E. P. Quaye",
                "GES Food & Nutrition Syllabus"
            ),
            pagesCount = 110
        ),
        SubjectItem(
            id = "fn_y2",
            title = "Food & Nutrition (SHS 2)",
            shortName = "Food & Nutrition",
            year = 2,
            courseTrack = CourseTrack.HOME_ECONOMICS,
            curriculumCode = "GES/NaCCA/SHS-EL-FN02",
            description = "Experimental cookery, special diets, food preservation techniques, catering, and meal service.",
            fileSizeFormatted = "5.2 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Food-and-Nutrition-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Food_And_Nutrition_Syllabus.pdf",
            topics = listOf(
                "Diets for Special Groups: Diabetics, Hypertensives, Convalescents & Elderly",
                "Experimental Cookery: Raising Agents, Emulsions and Flour Mixtures",
                "Food Spoilage and Food Preservation Methods (Canning, Drying, Freezing)",
                "Catering, Table Setting and Service Styles (Buffet, Formal, Family)",
                "Traditional Ghanaian Dishes: Preparation and Nutritional Value",
                "Costing Meals and Recipe Modification"
            ),
            wassceFormat = "WASSCE Focus: Direct preparation for WAEC 3-hour practical examination and recipe drafting.",
            recommendedTextbooks = listOf(
                "Aki-Ola Food & Nutrition Master Guide",
                "Practical Cookery for Senior High Schools by A. A. Mensah",
                "WAEC Food & Nutrition Practical Worksheets"
            ),
            pagesCount = 124
        ),
        SubjectItem(
            id = "fn_y3",
            title = "Food & Nutrition (SHS 3)",
            shortName = "Food & Nutrition",
            year = 3,
            courseTrack = CourseTrack.HOME_ECONOMICS,
            curriculumCode = "GES/NaCCA/SHS-EL-FN03",
            description = "Large-scale catering, consumer education, food laws, and WASSCE practical examination drills.",
            fileSizeFormatted = "5.6 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Food-and-Nutrition-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Food_And_Nutrition_Syllabus.pdf",
            topics = listOf(
                "Large-Scale Catering and Quantity Food Preparation",
                "Food Laws, Standards and Consumer Protection Agencies (FDA Ghana)",
                "Rechaufé (Leftover) Cookery and Food Waste Reduction",
                "Nutritional Problems in Developing Nations (Kwashiorkor, Marasmus)",
                "Comprehensive WASSCE Food & Nutrition Practical Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees Grade A1 in WAEC Food & Nutrition Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola Food & Nutrition Final Revision Guide",
                "WASSCE Past Food & Nutrition Exam Questions & Marking Schemes",
                "Manual of Nutrition (Ministry of Agriculture, UK/Ghana Edition)"
            ),
            pagesCount = 132
        ),

        // Management in Living
        SubjectItem(
            id = "mil_y1",
            title = "Management in Living (SHS 1)",
            shortName = "Mgmt in Living",
            year = 1,
            courseTrack = CourseTrack.HOME_ECONOMICS,
            curriculumCode = "GES/NaCCA/SHS-EL-MIL01",
            description = "Family life, values, goals, resource management, housing, and interior decoration basics.",
            fileSizeFormatted = "4.4 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Management-in-Living-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_Management_In_Living_Syllabus.pdf",
            topics = listOf(
                "Concept and Scope of Management in Living",
                "Family Life, Family Types and Functions",
                "Values, Goals and Standards in Family Management",
                "Management of Family Resources: Time, Energy and Money",
                "Housing the Family: Selection, Acquisition & Maintenance",
                "Interior Decoration: Color Schemes, Furniture & Accessories"
            ),
            wassceFormat = "WASSCE Foundation: Essential for WAEC Management in Living Paper 1 and Paper 2 essay questions.",
            recommendedTextbooks = listOf(
                "Aki-Ola Management in Living for SHS 1-3",
                "Home Management for Senior Secondary Schools by E. O. Addo",
                "GES Management in Living Teaching Syllabus"
            ),
            pagesCount = 102
        ),
        SubjectItem(
            id = "mil_y2",
            title = "Management in Living (SHS 2)",
            shortName = "Mgmt in Living",
            year = 2,
            courseTrack = CourseTrack.HOME_ECONOMICS,
            curriculumCode = "GES/NaCCA/SHS-EL-MIL02",
            description = "Consumer education, household budgeting, work simplification, child development, and sanitation.",
            fileSizeFormatted = "4.9 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Management-in-Living-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_Management_In_Living_Syllabus.pdf",
            topics = listOf(
                "Consumer Education: Rights, Responsibilities and Wise Shopping",
                "Family Budgeting, Savings and Financial Security",
                "Work Simplification Techniques and Energy Conservation",
                "Child Development: Physical, Emotional & Social Growth",
                "Environmental Sanitation, Waste Management & Pest Control",
                "Care of Household Textiles and Laundry Equipment"
            ),
            wassceFormat = "WASSCE Focus: Heavy concentration on family budgeting and resource management case studies.",
            recommendedTextbooks = listOf(
                "Aki-Ola Management in Living Master Edition",
                "Consumer Education and Living by K. B. Appiah",
                "WAEC Past Management in Living Papers"
            ),
            pagesCount = 114
        ),
        SubjectItem(
            id = "mil_y3",
            title = "Management in Living (SHS 3)",
            shortName = "Mgmt in Living",
            year = 3,
            courseTrack = CourseTrack.HOME_ECONOMICS,
            curriculumCode = "GES/NaCCA/SHS-EL-MIL03",
            description = "Marriage laws, family crisis management, elderly care, community development, and WASSCE essay drills.",
            fileSizeFormatted = "5.2 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/Management-in-Living-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_Management_In_Living_Syllabus.pdf",
            topics = listOf(
                "Marriage Laws, Customary Marriage & Ordinance Marriage in Ghana",
                "Family Crisis Management: Bereavement, Divorce, Unemployment",
                "Care of the Elderly and Special Needs Family Members",
                "Community Development Projects and Women Empowerment",
                "WASSCE Management in Living Essay Formulation & Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees Grade A1 in WAEC Management in Living Papers 1 and 2.",
            recommendedTextbooks = listOf(
                "Aki-Ola Management in Living Final Revision",
                "WASSCE Management in Living Past Questions & Marking Schemes",
                "Family & Living Studies by Dr. Grace Mensah"
            ),
            pagesCount = 120
        ),

        // =========================================================================
        // ELECTIVE PROGRAM 6: AGRICULTURAL SCIENCE TRACK
        // =========================================================================

        // General Agriculture
        SubjectItem(
            id = "agric_y1",
            title = "General Agriculture (SHS 1)",
            shortName = "Agriculture",
            year = 1,
            courseTrack = CourseTrack.AGRICULTURE,
            curriculumCode = "GES/NaCCA/SHS-EL-AGRIC01",
            description = "Role of agriculture, land tenure systems, farm mechanization, soil science, and crop production fundamentals.",
            fileSizeFormatted = "5.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Agriculture-LM-Y1-LV.pdf",
            fileName = "GES_SHS1_General_Agriculture_Syllabus.pdf",
            topics = listOf(
                "Importance of Agriculture to National Economy in Ghana",
                "Land Tenure Systems and Land Use Ownership Laws",
                "Farm Mechanization: Implements, Tools and Maintenance",
                "Soil Formation, Soil Profile and Physical/Chemical Properties",
                "Soil Fertility, Organic Manures and Inorganic Fertilizers",
                "Classification of Crops: Cereals, Legumes, Roots & Tubers"
            ),
            wassceFormat = "WASSCE Foundation: Foundation for WAEC General Agriculture Paper 1, Paper 2, and Paper 3 Practical Identification.",
            recommendedTextbooks = listOf(
                "Aki-Ola General Agriculture for SHS 1-3",
                "General Agriculture for West Africa by Q. B. O. Anthonio",
                "GES General Agriculture Syllabus"
            ),
            pagesCount = 118
        ),
        SubjectItem(
            id = "agric_y2",
            title = "General Agriculture (SHS 2)",
            shortName = "Agriculture",
            year = 2,
            courseTrack = CourseTrack.AGRICULTURE,
            curriculumCode = "GES/NaCCA/SHS-EL-AGRIC02",
            description = "Crop protection, weed control, livestock anatomy, animal nutrition, and agricultural economics.",
            fileSizeFormatted = "5.6 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Agriculture-LM-Y2-LV.pdf",
            fileName = "GES_SHS2_General_Agriculture_Syllabus.pdf",
            topics = listOf(
                "Crop Protection: Pests, Diseases and Integrated Pest Management (IPM)",
                "Weeds and Weed Control Methods (Chemical, Biological, Mechanical)",
                "Anatomy and Physiology of Farm Animals (Ruminants vs Non-Ruminants)",
                "Animal Nutrition: Feed Ingredients, Rations & Feed Formulation",
                "Agricultural Economics: Demand, Supply and Price Elasticities for Farm Produce",
                "Farm Accounting, Budgeting and Financial Statements"
            ),
            wassceFormat = "WASSCE Focus: Heavy representation in WAEC Paper 3 practical specimen identification and calculations.",
            recommendedTextbooks = listOf(
                "Aki-Ola General Agriculture Master Companion",
                "Agricultural Science for Senior Secondary Schools by O. A. Iwena",
                "WAEC Agriculture Practical Specimen Manual"
            ),
            pagesCount = 132
        ),
        SubjectItem(
            id = "agric_y3",
            title = "General Agriculture (SHS 3)",
            shortName = "Agriculture",
            year = 3,
            courseTrack = CourseTrack.AGRICULTURE,
            curriculumCode = "GES/NaCCA/SHS-EL-AGRIC03",
            description = "Animal breeding, pasture management, agricultural extension, agribusiness, and WASSCE practical drills.",
            fileSizeFormatted = "6.0 MB",
            downloadUrl = "https://curriculumresources.edu.gh/wp-content/uploads/2026/09/General-Agriculture-LM-Y3-LV.pdf",
            fileName = "GES_SHS3_General_Agriculture_Syllabus.pdf",
            topics = listOf(
                "Animal Breeding, Genetics and Artificial Insemination",
                "Pasture and Forage Crop Management",
                "Agricultural Extension Services and Technology Transfer",
                "Agribusiness, Marketing Channels and Export Crops (Cocoa, Cashew)",
                "Fisheries and Forestry Management in Ghana",
                "Comprehensive WASSCE General Agriculture Practical Drills"
            ),
            wassceFormat = "WASSCE Final Prep: Guarantees Grade A1 in WAEC General Agriculture Papers 1, 2, and 3.",
            recommendedTextbooks = listOf(
                "Aki-Ola General Agriculture Final Revision Guide",
                "WASSCE Past General Agriculture Questions & Marking Schemes",
                "Senior Secondary Agriculture by Dr. K. A. Kwakye"
            ),
            pagesCount = 140
        )
    )

    fun getSubjectsForYear(year: Int): List<SubjectItem> {
        return subjects.filter { it.year == year }
    }

    fun getCoreSubjectsForYear(year: Int): List<SubjectItem> {
        return subjects.filter { it.year == year && it.courseTrack == CourseTrack.CORE }
    }

    fun getSubjectsByTrack(year: Int, track: CourseTrack): List<SubjectItem> {
        if (track == CourseTrack.ALL) {
            return subjects.filter { it.year == year }
        }
        return subjects.filter { it.year == year && it.courseTrack == track }
    }
}
