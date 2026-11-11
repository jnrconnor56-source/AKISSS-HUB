# 🎓 AKISSS Student Hub (Ghana GES SHS Curriculum Center)

![Android APK](https://img.shields.io/badge/Android-APK_v1.0-1e3a8a?style=for-the-badge&logo=android)
![GES SHS Curriculum](https://img.shields.io/badge/GES_NaCCA-SHS_Core_%26_Electives-D97706?style=for-the-badge)
![Build Status](https://img.shields.io/badge/Build-Passing-059669?style=for-the-badge)

**AKISSS Student Hub** is a native Android application built specifically for Senior High School (SHS) students in Ghana (Akim Swedru Senior High School / GES Curriculum). It provides instant access to official teaching syllabuses, unit topics, WASSCE examination tips, and native PDF downloads for all **Core Subjects** and **Elective Program Tracks** across **Years 1, 2, and 3**.

---

## 📱 Download Ready APK File

You can download and install the pre-compiled Android APK file directly from this GitHub repository:

### ⬇️ Direct Download Link
* **[Download `akisss-student-hub-v1.0.apk`](./akisss-student-hub-v1.0.apk)** (Size: ~23 MB, Android 7.0+ / API 24+)

### 📲 How to Install on Android Device:
1. Tap the **`akisss-student-hub-v1.0.apk`** link above on your Android phone or tablet.
2. Tap **Download** or **Download raw file**.
3. Once downloaded, open your phone's **Downloads** or **File Manager** app.
4. Tap `akisss-student-hub-v1.0.apk`.
5. If prompted, enable **"Install from unknown sources"** for your file browser.
6. Tap **Install** and open **AKISSS Student Hub**!

---

## ✨ Features & Curriculum Coverage

### 📚 1. All SHS Years Supported
- **Year 1 (SHS 1)**
- **Year 2 (SHS 2)**
- **Year 3 (SHS 3)**

### 🏛️ 2. Core & Elective Program Tracks (87 Syllabuses)
- **Compulsory Core Subjects**: Core Mathematics, English Language, General / Integrated Science, Social Studies
- **General Science Track**: Elective Mathematics (Further Math), Elective Physics, Elective Chemistry, Elective Biology
- **General Arts Track**: Economics, Geography, Government, Literature-in-English
- **Business Track**: Financial Accounting, Business Management, Elective ICT
- **Visual Arts Track**: General Knowledge in Art (GKA), Graphic Design
- **Home Economics Track**: Food & Nutrition, Management in Living
- **Agricultural Science Track**: General Agriculture

### ⚡ 3. Native Android Download Manager
- Integrated with Android's system `DownloadManager` service (`getSystemService(Context.DOWNLOAD_SERVICE)`).
- Downloads official curriculum PDFs directly from `curriculumresources.edu.gh` into your device's public `/Downloads` folder.
- Real-time download progress toasts and system notification bar alerts.
- One-tap **Open PDF** via Android `FileProvider` (`androidx.core.content.FileProvider`).

### 🎨 4. Modern Material 3 Swedru High School Theme
- Akim Swedru Senior High School school crest and primary blue theme (`#1E3A8A`).
- Program track filter chips and search bar for instant keyword queries.
- Detailed syllabus modals showing unit topics, WAEC exam paper formats, and recommended textbooks (Aki-Ola, Nelkon, Raymond Chang, Frank Wood).

---

## 🛠️ How to Build from Source (Android Studio)

If you are a developer and want to inspect, modify, or compile the source code yourself:

### Prerequisites:
- **Android Studio** (Ladybug / Jellyfish or newer recommended)
- **JDK 11** or **JDK 17**
- **Android SDK 34/36**

### Steps:
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/akisss-student-hub.git
   cd akisss-student-hub
   ```

2. **Open in Android Studio**:
   - Launch Android Studio and select **Open**.
   - Navigate to the cloned `akisss-student-hub` folder and click **OK**.

3. **Build APK via Gradle Command Line**:
   ```bash
   # Build Debug APK
   gradle assembleDebug

   # The compiled APK will be located at:
   # app/build/outputs/apk/debug/app-debug.apk
   ```

---

## 📂 Repository Structure

```
├── akisss-student-hub-v1.0.apk  <-- Compiled Android APK File (Direct Download)
├── app/
│   ├── build.gradle.kts          <-- App module dependencies & signing configs
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/
│           │   ├── MainActivity.kt
│           │   ├── data/SubjectModel.kt
│           │   ├── download/CurriculumDownloadManager.kt
│           │   └── ui/
│           │       ├── AkisssMainScreen.kt
│           │       ├── AkisssViewModel.kt
│           │       └── components/
│           └── res/               <-- XML layouts, drawables, colors, strings
├── gradle/libs.versions.toml     <-- Version catalog
├── settings.gradle.kts
└── README.md
```

---

## 📄 License & Credits
Built for Senior High School students in Ghana. All curriculum syllabuses are property of the **Ghana Education Service (GES)** and **NaCCA**.
