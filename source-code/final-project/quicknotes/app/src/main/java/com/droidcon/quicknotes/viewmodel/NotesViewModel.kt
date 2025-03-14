package com.droidcon.quicknotes.viewmodel

import androidx.lifecycle.ViewModel
import com.droidcon.quicknotes.data.Note
import com.droidcon.quicknotes.data.NoteRepository
import kotlinx.coroutines.flow.StateFlow

class NotesViewModel(
    private val repository: NoteRepository = NoteRepository()
): ViewModel() {

    val notes: StateFlow<List<Note>> = repository.notes

    fun addNote(content: String) {
        if (content.isNotBlank()) {
            repository.addNote(content)
        }
    }

    fun deleteNote(id: String) {
        repository.deleteNote(id)
    }

    fun updateNote(id: String, content: String) {
        if (content.isNotBlank()) {
            repository.updateNote(id, content)
        }
    }

    fun getNoteById(id: String) : Note? {
        return repository.getNoteById(id)
    }
}