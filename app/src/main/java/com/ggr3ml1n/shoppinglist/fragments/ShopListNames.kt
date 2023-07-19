package com.ggr3ml1n.shoppinglist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.databinding.FragmentShopListNamesBinding
import com.ggr3ml1n.shoppinglist.dialogs.NewListDialog


class ShopListNames : BaseFragment() {
    private var _binding: FragmentShopListNamesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity
        ) { Log.d("MyLog", "Name: $it") }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopListNames()
    }
}