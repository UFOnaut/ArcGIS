package com.ufonaut.test.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {
    fun showPlaceDetailsDialog(context: Context, title: String, position: String) {
        AlertDialog.Builder(context)
            .setMessage(position)
            .setTitle(title)
            .setPositiveButton("Ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.show()
    }
}