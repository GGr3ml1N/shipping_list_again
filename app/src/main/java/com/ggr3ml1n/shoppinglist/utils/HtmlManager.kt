package com.ggr3ml1n.shoppinglist.utils

import android.text.Html
import android.text.Spanned

object HtmlManager {
    fun getFromHtml(text: String): Spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)

    fun toHtml(text: Spanned): String = Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
}