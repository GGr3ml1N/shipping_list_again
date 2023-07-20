package com.ggr3ml1n.shoppinglist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.databinding.ActivityShopListBinding

class ShopListActivity : AppCompatActivity() {

    private var _binding: ActivityShopListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}