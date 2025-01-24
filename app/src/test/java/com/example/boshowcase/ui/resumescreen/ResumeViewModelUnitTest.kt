package com.example.boshowcase.ui.resumescreen

import android.net.Uri
import com.example.boshowcase.TestCoroutineRule
import com.example.boshowcase.data.repository.ResumeRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ResumeViewModelUnitTest {

    @get:Rule
    val dispatcherRule = TestCoroutineRule()

    private lateinit var repository: ResumeRepository
    private lateinit var viewModel: ResumeViewModel

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        viewModel = ResumeViewModel(repository)
    }

    @Test
    fun `loadResumeUrl should fetch resume URL from repository`() = dispatcherRule.runTest {
        // Arrange
        val expectedUrl = "https://example.com/resume.pdf"
        coEvery { repository.getResumeUrl() } returns expectedUrl

        // Act
        viewModel.loadResumeUrl()
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(expectedUrl, viewModel.resumeUrl.first())
        coVerify { repository.getResumeUrl() }
    }

    @Test
    fun `uploadResume should save resume URL and update state`() = dispatcherRule.runTest {
        // Arrange
        val uri = mockk<Uri>()
        val expectedUrl = "https://example.com/resume.pdf"
        coEvery { repository.uploadResume(uri) } returns expectedUrl
        coEvery { repository.saveResumeUrl(expectedUrl) } just Runs

        // Act
        viewModel.uploadResume(uri)
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(expectedUrl, viewModel.resumeUrl.first())
        coVerifySequence {
            repository.uploadResume(uri)
            repository.saveResumeUrl(expectedUrl)
        }
    }

    @Test
    fun `loadResumeUrl should handle exception`() = dispatcherRule.runTest {
        // Arrange
        coEvery { repository.getResumeUrl() } throws Exception("Failed to fetch URL")

        // Act
        viewModel.loadResumeUrl()
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertNull(viewModel.resumeUrl.first())
        coVerify { repository.getResumeUrl() }
    }

    @Test
    fun `uploadResume should handle exception`() = dispatcherRule.runTest {
        // Arrange
        val uri = mockk<Uri>()
        coEvery { repository.uploadResume(uri) } throws Exception("Failed to upload resume")

        // Act
        viewModel.uploadResume(uri)
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertNull(viewModel.resumeUrl.first())
        coVerify { repository.uploadResume(uri) }
    }
}