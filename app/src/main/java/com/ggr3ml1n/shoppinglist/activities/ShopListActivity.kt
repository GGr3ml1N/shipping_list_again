package com.ggr3ml1n.shoppinglist.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ActivityShopListBinding
import com.ggr3ml1n.shoppinglist.entities.ShopListNameItem
import com.ggr3ml1n.shoppinglist.utils.Extension
import com.ggr3ml1n.shoppinglist.vm.MainViewModel

class ShopListActivity : AppCompatActivity() {

    private var _binding: ActivityShopListBinding? = null
    private val binding get() = _binding!!

    private lateinit var saveItem: MenuItem

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    private var shopListName: ShopListNameItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        shopListName = Extension.getSerializable(intent, SHOP_LIST_NAME, ShopListNameItem::class.java)
        binding.tvTest.text = shopListName?.name
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }
        }
    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}