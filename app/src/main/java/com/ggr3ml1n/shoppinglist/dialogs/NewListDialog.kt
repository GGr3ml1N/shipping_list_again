package com.ggr3ml1n.shoppinglist.dialogs

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.ggr3ml1n.shoppinglist.databinding.NewListDialogBinding

object NewListDialog {
    fun showDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                } else {
                    dialog?.dismiss()
                }
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }
    fun interface Listener {
        fun onClick(name: String)
    }
}