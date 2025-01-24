package com.example.boshowcase.ui.portfolioscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.boshowcase.data.repository.FirestoreManager
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.data.repository.PortfolioRepository

/**
 * Portfolio Screen
 *
 * @param viewModel Portfolio View Model
 * @param onProjectClick Handling onClick when each item is clicked
 */
@Composable
fun PortfolioScreen(
    viewModel: PortfolioViewModel,
    onProjectClick: (Project) -> Unit
) {
    val projects by viewModel.projects.collectAsState()

    // Load projects when the screen is opened
    LaunchedEffect(Unit) {
        viewModel.loadProjects()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Portfolio Title
        Text(
            text = "Portfolio",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Add Project Button
        Button(
            onClick = {
                // Example project to add
                // TODO replace with actual input in a dialog
                val newProject = Project(
                    id = System.currentTimeMillis().toString(), // Unique ID
                    title = "New Project",
                    description = "This is a sample project.",
                    technologies = listOf("Kotlin", "Compose"),
                    githubLink = "https://example.com/project1.jpg"
                )
                viewModel.saveProject(newProject)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Project")
        }

        Spacer(modifier = Modifier.height(16.dp))

        projects?.let{
            // LazyColumn for Project List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(it) { project ->
                    ProjectCard(
                        project = project,
                        onClick = { onProjectClick(project) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}