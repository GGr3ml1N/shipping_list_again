package com.ggr3ml1n.shoppinglist.utils

import android.content.Intent
import android.os.Build
import java.io.Serializable

object Extension {

    fun <T : Serializable?> getSerializable(intent: Intent, name: String?, clazz: Class<T>): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(name, clazz)
        else
            intent.getSerializableExtra(name) as T
}