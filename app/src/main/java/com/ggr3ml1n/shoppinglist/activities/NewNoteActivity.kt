package com.ggr3ml1n.shoppinglist.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ActivityNewNoteBinding

class NewNoteActivity : AppCompatActivity() {

    private var _binding: ActivityNewNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save -> {
                finish()
            }
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}