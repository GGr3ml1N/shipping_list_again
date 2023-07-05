package com.ggr3ml1n.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavListener()
    }

    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings -> {
                    Log.d("MyLog", "Settings!")
                }
                R.id.new_item -> {
                    Log.d("MyLog", "New!")
                }
                R.id.notes -> {
                    Log.d("MyLog", "Notes")
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "List")
                }
            }
            true
        }
    }
}