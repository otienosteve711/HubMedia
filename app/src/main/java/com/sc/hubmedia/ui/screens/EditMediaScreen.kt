package com.sc.hubmedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sc.hubmedia.model.MediaItem
import com.sc.hubmedia.ui.theme.MediaHubTheme
import com.sc.hubmedia.viewmodel.MediaState
import com.sc.hubmedia.viewmodel.MediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMediaScreen(
    navController  : NavController,
    mediaId        : String,
    mediaViewModel : MediaViewModel = viewModel()
) {
    // resolve item from shared loaded lists
    // no new Firestore fetch needed
    val allMedia    by mediaViewModel.allMedia.collectAsState()
    val publicMedia by mediaViewModel.publicMedia.collectAsState()
    val myMedia     by mediaViewModel.myMedia.collectAsState()

    val item = (allMedia + publicMedia + myMedia)
        .distinctBy { it.id }
        .find { it.id == mediaId }
        ?: MediaItem()

    // editable fields — keyed on item.id so they
    // reset correctly if mediaId changes
    var title       by remember(item.id) { mutableStateOf(item.title) }
    var description by remember(item.id) { mutableStateOf(item.description) }
    var isPublic    by remember(item.id) { mutableStateOf(item.isPublic) }

    val mediaState   by mediaViewModel.mediaState.collectAsState()
    val isLoading    = mediaState is MediaState.Loading
    val errorMessage = (mediaState as? MediaState.Error)?.message

    // go back after successful update
    LaunchedEffect(mediaState) {
        if (mediaState is MediaState.Success) {
            mediaViewModel.clearState()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Media") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // error banner
            errorMessage?.let {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Error, null,
                            tint = MaterialTheme.colorScheme.onError)
                        Spacer(Modifier.width(8.dp))
                        Text(it, color = MaterialTheme.colorScheme.onError)
                    }
                }
            }

            // category is read-only — changing it after
            // upload doesn't reclassify the actual file
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Category, null,
                        tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "Category",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                        Text(
                            item.category,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // title field
            OutlinedTextField(
                value          = title,
                onValueChange  = { title = it },
                label          = { Text("Title") },
                leadingIcon    = { Icon(Icons.Default.Title, null) },
                singleLine     = true,
                modifier       = Modifier.fillMaxWidth(),
                shape          = RoundedCornerShape(12.dp)
            )

            // description field
            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text("Description") },
                leadingIcon   = { Icon(Icons.Default.Description, null) },
                minLines      = 3,
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp)
            )

            // visibility toggle
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (isPublic) Icons.Default.Public
                        else Icons.Default.Lock,
                        null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            if (isPublic) "Public" else "Private",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            if (isPublic) "Visible to all users"
                            else "Only visible to you",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    }
                    Switch(
                        checked         = isPublic,
                        onCheckedChange = { isPublic = it }
                    )
                }
            }

            // save button
            Button(
                onClick = {
                    mediaViewModel.updateMedia(
                        mediaId     = mediaId,
                        title       = title,
                        description = description,
                        isPublic    = isPublic
                    )
                },
                enabled  = title.isNotBlank() &&
                        description.isNotBlank() &&
                        !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier    = Modifier.size(20.dp),
                        color       = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.Save, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Save Changes")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditMediaScreenPreview() {
    MediaHubTheme {
        EditMediaScreen(rememberNavController(), "1")
    }
}