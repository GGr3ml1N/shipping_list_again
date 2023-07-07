package com.ggr3ml1n.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.ggr3ml1n.shoppinglist.activities.MainApp
import com.ggr3ml1n.shoppinglist.activities.NewNoteActivity
import com.ggr3ml1n.shoppinglist.databinding.FragmentNoteBinding
import com.ggr3ml1n.shoppinglist.vm.MainViewModel

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var editLauncher: ActivityResultLauncher<Intent>

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

    private fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode != Activity.RESULT_OK) {
                Log.d("MyLog", "title ${it.data?.getStringExtra(TITLE_KEY)}")
                Log.d("MyLog", "description ${it.data?.getStringExtra(DESC_KEY)}")
            }
        }
    }

    companion object {

        const val TITLE_KEY = "title_key"
        const val DESC_KEY = "desc_key"
         @JvmStatic
        fun newInstance() = NoteFragment()

    }
}