package com.ggr3ml1n.shoppinglist.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.ggr3ml1n.shoppinglist.databinding.EditListItemDialogBinding
import com.ggr3ml1n.shoppinglist.entities.ShopListItem

object EditListItemDialog {
    fun showDialog(context: Context, item: ShopListItem, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            edName.setText(item.name)
            edInfo.setText(item.itemInfo)
            bUpdate.setOnClickListener {
                if (edName.text.toString().isNotEmpty()) {
                    val itemInfo = edInfo.text.toString().ifEmpty { null }
                    listener.onClick(item.copy(name = edName.text.toString(), itemInfo = itemInfo))
                }
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }
    fun interface Listener {
        fun onClick(item: ShopListItem)
    }
}