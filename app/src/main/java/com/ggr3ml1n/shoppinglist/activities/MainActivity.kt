package com.ggr3ml1n.shoppinglist.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ActivityMainBinding
import com.ggr3ml1n.shoppinglist.dialogs.NewListDialog
import com.ggr3ml1n.shoppinglist.fragments.FragmentManager
import com.ggr3ml1n.shoppinglist.fragments.NoteFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val listener = NewListDialog.Listener { Log.d("MyLog", "Name: $it") }
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
                    //FragmentManager.currentFrag?.onClickNew()
                    NewListDialog.showDialog(this, listener)
                }
                R.id.notes -> {
                    FragmentManager.setFragment(this, NoteFragment.newInstance())
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "List")
                }
            }
            true
        }
    }
}