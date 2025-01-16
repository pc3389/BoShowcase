package com.example.boshowcase.ui.projectdetailscreen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.boshowcase.ui.model.Project

/**
 * Project Detail Screen to show each project details.
 *
 * @param project project to show details on this screen
 */
@Composable
fun ProjectDetailsScreen(project: Project) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = project.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = project.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Technologies: ${project.technologies.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "GitHub: ${project.githubLink}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable {
                try {
                    // Open the GitHub link in a browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(project.githubLink))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Show a Toast if the link cannot be opened
                    Toast.makeText(context, "Unable to open link", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}