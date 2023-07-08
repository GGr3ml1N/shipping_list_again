package com.ggr3ml1n.shoppinglist.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ActivityNewNoteBinding
import com.ggr3ml1n.shoppinglist.entities.NoteItem
import com.ggr3ml1n.shoppinglist.fragments.NoteFragment
import java.util.Locale

class NewNoteActivity : AppCompatActivity() {

    private var _binding: ActivityNewNoteBinding? = null
    private val binding get() = _binding!!

    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getNote() {
        note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY, NoteItem::class.java)
        } else {
            intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as NoteItem
        }
        fillNote()
    }

    private fun fillNote() = with(binding) {
        if (note != null) {
            edTitle.setText(note?.title)
            edDescription.setText(note?.content)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                setMainResult()
            }

            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem? = if (note == null) {
            createNewNote()
        } else {
            editState = "update"
            updateNote()
        }
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateNote(): NoteItem? = with(binding) {
        return note?.copy(
            title = edTitle.text.toString(),
            content = edDescription.text.toString()
        )
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime(),
            ""
        )
    }

    private fun getCurrentTime(): String {
        val formater = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formater.format(Calendar.getInstance().time)
    }

    private fun actionBarSettings() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}