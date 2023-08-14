package com.ggr3ml1n.shoppinglist.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.ggr3ml1n.shoppinglist.dialogs.EditListItemDialog
import com.ggr3ml1n.shoppinglist.entities.ShopListItem
import com.ggr3ml1n.shoppinglist.entities.ShopListNameItem
import com.ggr3ml1n.shoppinglist.utils.Extension
import com.ggr3ml1n.shoppinglist.utils.ShareHelper
import com.ggr3ml1n.shoppinglist.vm.MainViewModel

class ShopListActivity : AppCompatActivity() {

    private var _binding: ActivityShopListBinding? = null
    private val binding get() = _binding!!

    private lateinit var saveItem: MenuItem
    private var edTitle: EditText? = null
    private var adapter: ShopListItemAdapter? = null
    private var textWatcher: TextWatcher? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    private var shopListNameItem: ShopListNameItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observer()
        initRcView()
    }

    private fun init() {
        shopListNameItem =
            Extension.getSerializable(intent, SHOP_LIST_NAME, ShopListNameItem::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)
        edTitle = newItem.actionView?.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        textWatcher = textWatcher()
        return true
    }

    private fun textWatcher(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("MyLog", "On text changed: $s")
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_item -> addNewShopItem()
            R.id.delete_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!, true)
                finish()
            }
            R.id.clear_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!, false)
            }
            R.id.share_list -> {
                startActivity(Intent.createChooser(ShareHelper.shareShopList(adapter?.currentList!!, shopListNameItem?.name!!), "Share by"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveItem.isVisible = true
                edTitle?.addTextChangedListener(textWatcher)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveItem.isVisible = false
                edTitle?.removeTextChangedListener(textWatcher)
                invalidateOptionsMenu()
                return true
            }
        }
    }

    private fun addNewShopItem() {
        if (edTitle?.text.toString().isEmpty()) return
        val item = ShopListItem(
            null,
            edTitle?.text.toString(),
            null,
            false,
            shopListNameItem?.id!!,
            0,
        )
        edTitle?.setText("")
        mainViewModel.insertShopListItem(item)
    }

    private fun observer() {
        mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun initRcView() = with(binding) {
        adapter = ShopListItemAdapter { shopListName, state ->
            when(state) {
                ShopListItemAdapter.CHECKED_BOX -> mainViewModel.updateShopListItem(shopListName)
                ShopListItemAdapter.EDIT -> EditListItemDialog.showDialog(this@ShopListActivity, shopListName) {
                    mainViewModel.updateShopListItem(it)
                }
            }

        }
        rcView.layoutManager = LinearLayoutManager(this@ShopListActivity)
        rcView.adapter = adapter
    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}