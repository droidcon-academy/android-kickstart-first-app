package com.droidcon.quicknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.droidcon.quicknotes.ui.EditNoteScreen
import com.droidcon.quicknotes.ui.HomeScreen
import com.droidcon.quicknotes.ui.theme.QuickNotesTheme
import com.droidcon.quicknotes.viewmodel.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val notesViewModel: NotesViewModel = viewModel()

                    val notes by notesViewModel.notes.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        // Home screen with note list
                        composable("home") {
                            HomeScreen(
                                notes = notes,
                                onAddNote = { content ->
                                    notesViewModel.addNote(content)
                                },
                                onDeleteNote = { id ->
                                    notesViewModel.deleteNote(id)
                                },
                                onNoteClick = { noteId ->
                                    navController.navigate("edit/$noteId")
                                }
                            )
                        }

                        // Edit note screen
                        composable(
                            route = "edit/{noteId}",
                            arguments = listOf(
                                navArgument("noteId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""

                            val noteContent = notesViewModel.getNoteById(noteId)?.content ?: ""

                            EditNoteScreen(
                                noteId = noteId,
                                initialContent = noteContent,
                                onUpdateNote = { id, content ->
                                    notesViewModel.updateNote(id, content)
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}