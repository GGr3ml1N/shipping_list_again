package com.ggr3ml1n.shoppinglist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ggr3ml1n.shoppinglist.activities.MainApp
import com.ggr3ml1n.shoppinglist.databinding.FragmentNoteBinding
import com.ggr3ml1n.shoppinglist.vm.MainViewModel

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()

    }
}