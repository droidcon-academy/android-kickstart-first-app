package com.droidcon.quicknotes.util

import android.content.Context
import android.content.Intent


fun shareNote(context: Context, noteContent: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, noteContent)
        type = "text/plain"
    }

    val chooserIntent = Intent.createChooser(shareIntent, "Share Note")
    context.startActivity(chooserIntent)
}