package com.ggr3ml1n.shoppinglist.utils

import android.content.Intent
import com.ggr3ml1n.shoppinglist.entities.ShopListItem

object ShareHelper {

    fun shareShopList(shopList: List<ShopListItem>, name: String) : Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList, name))
        }
        return intent
    }

    private fun makeShareText(shopList: List<ShopListItem>, name: String) : String{
        val sBuilder = StringBuilder()
        sBuilder.append("\"$name\"")
        sBuilder.append("\n")
        var counter = 1
        shopList.forEach{
            sBuilder.append("${counter++} - ${it.name} (${it.itemInfo ?: ""})")
            sBuilder.append("\n")
        }
        return sBuilder.toString()
    }

}