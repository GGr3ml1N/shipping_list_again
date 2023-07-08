package com.ggr3ml1n.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggr3ml1n.shoppinglist.activities.MainApp
import com.ggr3ml1n.shoppinglist.activities.NewNoteActivity
import com.ggr3ml1n.shoppinglist.adapters.NoteAdapter
import com.ggr3ml1n.shoppinglist.databinding.FragmentNoteBinding
import com.ggr3ml1n.shoppinglist.entities.NoteItem
import com.ggr3ml1n.shoppinglist.vm.MainViewModel

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                mainViewModel.insertNote(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.data?.getSerializableExtra(NEW_NOTE_KEY, NoteItem::class.java)!!
                    } else {
                        it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem
                    }
                )
            }
        }
    }

    private fun initRcView() = with(binding) {
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        val listener = NoteAdapter.Listener { mainViewModel.deleteNote(it) }
        adapter = NoteAdapter(listener)
        rcViewNote.adapter = adapter
    }

    private fun observer() {
        mainViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {

        const val NEW_NOTE_KEY = "title_key"

        @JvmStatic
        fun newInstance() = NoteFragment()

    }
}