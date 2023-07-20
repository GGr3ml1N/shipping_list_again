package com.ggr3ml1n.shoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.shoppinglist.db.MainDataBase
import com.ggr3ml1n.shoppinglist.entities.NoteItem
import com.ggr3ml1n.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDataBase) : ViewModel() {
    val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShoppingListName>> = dao.getAllShoppingListNames().asLiveData()

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShoppingListName(name: ShoppingListName) = viewModelScope.launch {
        dao.insertShoppingListName(name)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun updateShopListName(name: ShoppingListName) = viewModelScope.launch {
        dao.updateShopListName(name)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteShopListName(id: Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
    }

    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}