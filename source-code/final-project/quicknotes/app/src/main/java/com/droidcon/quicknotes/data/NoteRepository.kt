package com.droidcon.quicknotes.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())

    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(content: String): Note {
        val newNote = Note(content = content)
        _notes.value += newNote
        return  newNote
    }

    fun deleteNote(id: String) {
        _notes.value = _notes.value.filter { it.id != id }
    }

    fun updateNote(id: String, content: String) {
        _notes.value = _notes.value.map {
            if (it.id == id) it.copy(content = content) else it
        }
    }

    fun getNoteById(id: String) : Note? {
        return _notes.value.find { it.id == id }
    }
}