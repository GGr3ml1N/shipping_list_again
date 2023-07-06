package com.ggr3ml1n.shoppinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.R

object FragmentManager {
    var currentFrag: BaseFragment? = null

    fun setFragment(activity: AppCompatActivity, newFrag: BaseFragment) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }
}