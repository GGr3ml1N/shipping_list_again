package com.ggr3ml1n.shoppinglist.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.adapters.ShopListItemAdapter
import com.ggr3ml1n.shoppinglist.databinding.ActivityShopListBinding
import com.ggr3ml1n.shoppinglist.entities.ShopListItem
import com.ggr3ml1n.shoppinglist.entities.ShopListNameItem
import com.ggr3ml1n.shoppinglist.utils.Extension
import com.ggr3ml1n.shoppinglist.vm.MainViewModel

class ShopListActivity : AppCompatActivity() {

    private var _binding: ActivityShopListBinding? = null
    private val binding get() = _binding!!

    private lateinit var saveItem: MenuItem
    private var edText: EditText? = null
    private var adapter: ShopListItemAdapter? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    private var shopListName: ShopListNameItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observer()
        initRcView()
    }

    private fun init() {
        shopListName =
            Extension.getSerializable(intent, SHOP_LIST_NAME, ShopListNameItem::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)
        edText = newItem.actionView?.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_item) {
            addNewShopItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
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

    private fun addNewShopItem() {
        if (edText?.text.toString().isEmpty()) return
        val item = ShopListItem(
            null,
            edText?.text.toString(),
            null,
            false,
            shopListName?.id!!,
            0,
        )
        edText?.setText("")
        mainViewModel.insertShopListItem(item)
    }

    private fun observer() {
        mainViewModel.getAllItemsFromList(shopListName?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun initRcView() = with(binding) {
        adapter = ShopListItemAdapter(object: ShopListItemAdapter.Listener {
            override fun deleteItem(id: Int) {

            }

            override fun onClickItem(shopListName: ShopListNameItem) {

            }

            override fun editItem(shopListName: ShopListNameItem) {

            }

        })
        rcView.layoutManager = LinearLayoutManager(this@ShopListActivity)
        rcView.adapter = adapter
    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}