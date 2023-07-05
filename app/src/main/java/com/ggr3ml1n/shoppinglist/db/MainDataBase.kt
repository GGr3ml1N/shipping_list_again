package com.ggr3ml1n.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ggr3ml1n.shoppinglist.entities.LibraryItem
import com.ggr3ml1n.shoppinglist.entities.NoteItem
import com.ggr3ml1n.shoppinglist.entities.ShoppingListNames
import com.ggr3ml1n.shoppinglist.entities.ShoppingListItems

@Database(entities = [LibraryItem::class, NoteItem::class, ShoppingListNames::class, ShoppingListItems::class], version = 1 )
abstract class MainDataBase() : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}