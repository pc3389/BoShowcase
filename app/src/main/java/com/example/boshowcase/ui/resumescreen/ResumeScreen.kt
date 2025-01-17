package com.example.boshowcase.ui.resumescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.boshowcase.R
import com.example.boshowcase.ui.model.Certification
import com.example.boshowcase.ui.model.Education
import com.example.boshowcase.ui.model.Experience
import com.example.boshowcase.ui.model.Resume
import com.example.boshowcase.ui.openCustomTab
import com.example.boshowcase.ui.repository.ResumeRepository

/**
 * Resume Screen to show Resume.
 *
 * @param viewModel View Model for resume screen
 */
@Composable
fun ResumeScreen(viewModel: ResumeViewModel = ResumeViewModel(ResumeRepository())) {
    val resume by viewModel.resume.collectAsState()

    if (resume != null) {
        ResumeContent(resume!!)
    } else {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ResumeContent(resume: Resume) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Section(title = "Experience") {
            resume.experiences.forEach { experience ->
                ExperienceCard(experience)
            }
        }

        Section(title = "Education") {
            resume.education.forEach { education ->
                EducationCard(education)
            }
        }

        Section(title = "Certifications") {
            resume.certifications.forEach { certification ->
                CertificationCard(certification)
            }
        }

        // LinkedIn Profile
        LinkedInLink(resume.linkedIn)
    }
}

@Composable
fun Section(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        content()
    }
}

@Composable
fun ExperienceCard(experience: Experience) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = experience.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = experience.company, style = MaterialTheme.typography.bodyMedium)
            Text(text = experience.duration, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = experience.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun EducationCard(education: Education) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = education.degree, style = MaterialTheme.typography.headlineSmall)
            Text(text = education.institution, style = MaterialTheme.typography.bodyMedium)
            Text(text = education.year, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun CertificationCard(certification: Certification) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = certification.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = certification.issuer, style = MaterialTheme.typography.bodyMedium)
            Text(text = certification.year, style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
fun LinkedInLink(link: String) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                openCustomTab(context, link)
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_linkedin), // Add LinkedIn icon in your resources
            contentDescription = "LinkedIn Profile",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "View LinkedIn Profile",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}