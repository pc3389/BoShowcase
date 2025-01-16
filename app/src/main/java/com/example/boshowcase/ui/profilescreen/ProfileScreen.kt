package com.example.boshowcase.ui.profilescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.boshowcase.R
import com.example.boshowcase.ui.model.Profile
import com.example.boshowcase.ui.repository.ProfileRepository

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = ProfileViewModel(ProfileRepository())) {
    val profile by viewModel.profile.collectAsState()

    if (profile != null) {
        ProfileContent(profile!!)
    } else {
        // Loading or error state can be handled here
        Text("Loading...")
    }
}

@Composable
fun ProfileContent(profile: Profile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.ic_profile_placeholder), // Replace with actual image
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )

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
            Text(text = skill, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(4.dp))
        }

        // Experience Section
        Text(
            text = "Experience",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
        profile.experiences.forEach { experience ->
            Text(
                text = experience,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    ProfileScreen(ProfileViewModel(ProfileRepository()))
}
