package com.example

import com.example.data.CourseTrack
import com.example.data.CurriculumRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testCurriculumSubjectsIntegrity() {
        // Verify exactly 4 core subjects per year for Year 1, 2, and 3
        val coreYear1 = CurriculumRepository.getCoreSubjectsForYear(1)
        val coreYear2 = CurriculumRepository.getCoreSubjectsForYear(2)
        val coreYear3 = CurriculumRepository.getCoreSubjectsForYear(3)

        assertEquals("Year 1 must have 4 core subjects", 4, coreYear1.size)
        assertEquals("Year 2 must have 4 core subjects", 4, coreYear2.size)
        assertEquals("Year 3 must have 4 core subjects", 4, coreYear3.size)

        // Verify each year contains the 4 essential GES SHS Core subjects
        val requiredSubjectPrefixes = listOf("math", "eng", "sci", "soc")
        for (year in listOf(coreYear1, coreYear2, coreYear3)) {
            for (prefix in requiredSubjectPrefixes) {
                assertTrue("Missing core subject with prefix $prefix", year.any { it.id.startsWith(prefix) })
            }
        }

        // Verify Elective Programs exist for General Science, General Arts, Business, Visual Arts, Home Economics, Agriculture
        val scienceElectives = CurriculumRepository.getSubjectsByTrack(1, CourseTrack.GENERAL_SCIENCE)
        val artsElectives = CurriculumRepository.getSubjectsByTrack(1, CourseTrack.GENERAL_ARTS)
        val businessElectives = CurriculumRepository.getSubjectsByTrack(1, CourseTrack.BUSINESS)
        val visualArtsElectives = CurriculumRepository.getSubjectsByTrack(1, CourseTrack.VISUAL_ARTS)
        val homeEconElectives = CurriculumRepository.getSubjectsByTrack(1, CourseTrack.HOME_ECONOMICS)
        val agricElectives = CurriculumRepository.getSubjectsByTrack(1, CourseTrack.AGRICULTURE)

        assertTrue("General Science electives must be present", scienceElectives.isNotEmpty())
        assertTrue("General Arts electives must be present", artsElectives.isNotEmpty())
        assertTrue("Business electives must be present", businessElectives.isNotEmpty())
        assertTrue("Visual Arts electives must be present", visualArtsElectives.isNotEmpty())
        assertTrue("Home Economics electives must be present", homeEconElectives.isNotEmpty())
        assertTrue("Agricultural Science electives must be present", agricElectives.isNotEmpty())

        // Verify all subjects point to curriculumresources.edu.gh and have valid PDFs
        for (subject in CurriculumRepository.subjects) {
            assertTrue(
                "Subject ${subject.id} download URL must point to curriculumresources.edu.gh",
                subject.downloadUrl.contains("curriculumresources.edu.gh")
            )
            assertTrue(
                "Subject ${subject.id} must specify a .pdf file name",
                subject.fileName.endsWith(".pdf")
            )
            assertTrue(
                "Subject ${subject.id} must have syllabus topics listed",
                subject.topics.isNotEmpty()
            )
            assertNotNull(subject.curriculumCode)
        }
    }
}
