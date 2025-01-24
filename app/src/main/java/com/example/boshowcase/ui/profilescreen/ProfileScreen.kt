package com.example.boshowcase.ui.profilescreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.boshowcase.data.repository.FirestoreManager
import com.example.boshowcase.ui.model.Profile
import com.example.boshowcase.data.repository.ProfileRepository

/**
 * Profile Screen.
 *
 * @param viewModel View Model for Profile Screen
 */
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val profile by viewModel.profile.collectAsState()

    // Local states for editable fields
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var title by remember { mutableStateOf(profile?.title ?: "") }
    var bio by remember { mutableStateOf(profile?.bio ?: "") }
    var skills by remember { mutableStateOf(profile?.skills?.joinToString(", ") ?: "") }
    var imageUrl by remember { mutableStateOf(profile?.imageUrl ?: "") }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Update the image URL state
            imageUrl = it.toString()
        }
    }


    // Load profile data when the screen is opened
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isEditing = !isEditing },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                    contentDescription = if (isEditing) "Save Profile" else "Edit Profile",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(enabled = isEditing) {
                        imagePickerLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    Text(
                        text = profile?.name?.firstOrNull()?.toString() ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            if (isEditing) {
                // Editable Fields
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                TextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text("Skills (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Display Fields
                ProfileContent(profile ?: Profile())
            }

            if (isEditing) {
                Button(
                    onClick = {
                        val updatedProfile = Profile(
                            name = name,
                            title = title,
                            bio = bio,
                            skills = skills.split(",").map { it.trim() }
                        )
                        viewModel.saveProfile(updatedProfile)
                        isEditing = false
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}

@Composable
fun ProfileContent(profile: Profile) {
    // Name and Title
    Text(
        text = profile.name,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(top = 16.dp)
    )
    Text(
        text = profile.title,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 8.dp)
    )

    // Bio
    Text(
        text = profile.bio,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 16.dp),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )

    // Skills Section
    Text(
        text = "Skills",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
    )
    profile.skills.forEach { skill ->
        Text(
            text = skill,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
fun Preview() {
    ProfileScreen(ProfileViewModel(ProfileRepository(FirestoreManager())))
}
