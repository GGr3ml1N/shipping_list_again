package com.ggr3ml1n.shoppinglist.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.NewListDialogBinding

object NewListDialog {
    fun showDialog(context: Context,  name: String, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            if (name.isNotEmpty()) {
                tvTitle.text = context.getString(R.string.update_list_name)
                bCreate.text = context.getString(R.string.update)
            }
            edNewListName.setText(name)
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                }
                dialog?.dismiss()
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