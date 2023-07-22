package com.ggr3ml1n.shoppinglist.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ActivityNewNoteBinding
import com.ggr3ml1n.shoppinglist.entities.NoteItem
import com.ggr3ml1n.shoppinglist.fragments.NoteFragment
import com.ggr3ml1n.shoppinglist.utils.Extension
import com.ggr3ml1n.shoppinglist.utils.HtmlManager
import com.ggr3ml1n.shoppinglist.utils.MyTouchListener
import com.ggr3ml1n.shoppinglist.utils.TimeManager

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
        init()
        onClickColorPicker()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getNote() {
        note = Extension.getSerializable(intent, NoteFragment.NEW_NOTE_KEY, NoteItem::class.java)
        fillNote()
    }

    private fun fillNote() = with(binding) {
        if (note != null) {
            edTitle.setText(note?.title)
            edDescription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                setMainResult()
            }

            R.id.bold -> {
                setBoldForSelectedText()
            }

            R.id.color -> {
                if (binding.colorPicker.isShown) {
                    closeColorPicker()
                } else {
                    openColorPicker()
                }
            }

            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edDescription.selectionStart
        val endPos = edDescription.selectionEnd
        val styles = edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null

        if (styles.isNotEmpty()) {
            edDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }

        edDescription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDescription.text.trim()
        edDescription.setSelection(startPos)
    }

    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        val startPos = edDescription.selectionStart
        val endPos = edDescription.selectionEnd

        val styles = edDescription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)

        if (styles.isNotEmpty()) edDescription.text.removeSpan(styles[0])

        edDescription.text.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this@NewNoteActivity,
                    colorId
                )
            ), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        edDescription.text.trim()
        edDescription.setSelection(startPos)
    }

    private fun onClickColorPicker() = with(binding) {
        ibRed.setOnClickListener { setColorForSelectedText(R.color.picker_red) }
        ibGreen.setOnClickListener { setColorForSelectedText(R.color.picker_green) }
        ibBlue.setOnClickListener { setColorForSelectedText(R.color.picker_blue) }
        ibBlack.setOnClickListener { setColorForSelectedText(R.color.picker_black) }
        ibYellow.setOnClickListener { setColorForSelectedText(R.color.picker_yellow) }
        ibOrange.setOnClickListener { setColorForSelectedText(R.color.picker_orange) }
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
            content = HtmlManager.toHtml(edDescription.text)
        )
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.toHtml(binding.edDescription.text),
            TimeManager.getCurrentTime(),
            ""
        )
    }


    private fun actionBarSettings() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }
}