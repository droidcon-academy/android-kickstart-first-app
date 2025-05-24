package com.droidcon.quicknotes.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droidcon.quicknotes.data.Note
import com.droidcon.quicknotes.util.shareNote
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    notes: List<Note>,
    onAddNote: (String) -> Unit,
    onDeleteNote: (String) -> Unit,
    onNoteClick: (String) -> Unit
) {
    var newNoteText by remember { mutableStateOf("") }

    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }


    Scaffold(
        snackbarHost = {SnackbarHost(snackBarHostState)}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 4.dp
            ) {
                Text(
                    text = "QuickNotes",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newNoteText,
                    onValueChange = { newNoteText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    label = { Text("New note") },
                    maxLines = 3
                )

                Button(
                    onClick = {
                        if (newNoteText.isNotBlank()) {
                            if (notes.none { it.content == newNoteText }) {
                                onAddNote(newNoteText)
                                newNoteText = ""
                            } else {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar("This note already exists.")
                                }
                            }
                        }
                    }

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add note"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = notes,
                    key = { note -> note.id }
                ) { note ->
                    NoteItem(
                        note = note,
                        onNoteClick = { clickedNote ->
                            onNoteClick(clickedNote.id)
                        },
                        onDeleteClick = {
                            noteToDelete = note
                            showDeleteConfirmation = true
                        },
                        onShareClick = { noteToShare ->
                            shareNote(context, noteToShare.content)
                        }
                    )
                }
            }

            if (showDeleteConfirmation && noteToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    confirmButton = {
                        Button(onClick = {
                            onDeleteNote(noteToDelete!!.id)
                            showDeleteConfirmation = false
                            noteToDelete = null
                        }) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            showDeleteConfirmation = false
                            noteToDelete = null
                        }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Delete Note") },
                    text = { Text("Are you sure you want to delete this note?") }
                )
            }
        }
    }
}