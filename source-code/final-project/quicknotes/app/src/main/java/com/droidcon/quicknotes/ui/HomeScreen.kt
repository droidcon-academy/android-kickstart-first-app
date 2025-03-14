package com.droidcon.quicknotes.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.droidcon.quicknotes.viewmodel.NotesViewModel

@Composable
fun HomeScreen(
    viewModel: NotesViewModel,
    onNavigateToEdit: (String) -> Unit
) {
    val notes by viewModel.notes.collectAsState()

    var newNoteText by remember { mutableStateOf("") }

    val context = LocalContext.current
    
}