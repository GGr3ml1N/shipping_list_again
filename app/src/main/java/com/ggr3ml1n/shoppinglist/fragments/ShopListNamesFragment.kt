package com.ggr3ml1n.shoppinglist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.ggr3ml1n.shoppinglist.activities.MainApp
import com.ggr3ml1n.shoppinglist.databinding.FragmentShopListNamesBinding
import com.ggr3ml1n.shoppinglist.dialogs.NewListDialog
import com.ggr3ml1n.shoppinglist.entities.ShoppingListName
import com.ggr3ml1n.shoppinglist.utils.TimeManager
import com.ggr3ml1n.shoppinglist.vm.MainViewModel


class ShopListNamesFragment : BaseFragment() {
    private var _binding: FragmentShopListNamesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClickNew() {
        NewListDialog.showDialog(
            activity as AppCompatActivity
        ) {
            val shoppingListName = ShoppingListName(
                null, it, TimeManager.getCurrentTime(), 0, 0, ""
            )
            mainViewModel.insertShoppingListName(shoppingListName)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }
}