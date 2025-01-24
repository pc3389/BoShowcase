package com.example.boshowcase.ui.resumescreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.boshowcase.data.openCustomTab

/**
 * Resume Screen to show Resume.
 *
 * @param viewModel View Model for resume screen
 */
@Composable
fun ResumeScreen(viewModel: ResumeViewModel) {
    // State to track the resume URL from ViewModel
    val resumeUrl by viewModel.resumeUrl.collectAsState()

    // File picker for selecting the resume (PDF)
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadResume(it) }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Upload Your Resume",
            style = MaterialTheme.typography.headlineSmall
        )

        // Upload Button
        Button(onClick = { filePickerLauncher.launch("application/pdf") }) {
            Text("Upload Resume")
        }

        // Display Resume URL
        if (resumeUrl != null) {
            Text(
                text = "Resume URL: $resumeUrl",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Text(
                text = "No resume uploaded yet.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        val context = LocalContext.current

        // Open Resume Button (if a URL exists)
        resumeUrl?.let { url ->
            Button(
                onClick = {
                    openCustomTab(context = context, url = url)
                }
            ) {
                Text("View Resume")
            }
        }
    }
}