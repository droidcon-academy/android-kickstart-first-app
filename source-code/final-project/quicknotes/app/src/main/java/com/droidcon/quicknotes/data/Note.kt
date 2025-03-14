package com.droidcon.quicknotes.data

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val content: String
)