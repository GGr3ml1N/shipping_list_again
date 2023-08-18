package com.ggr3ml1n.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ggr3ml1n.shoppinglist.entities.LibraryItem
import com.ggr3ml1n.shoppinglist.entities.NoteItem
import com.ggr3ml1n.shoppinglist.entities.ShopListNameItem
import com.ggr3ml1n.shoppinglist.entities.ShopListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>

    @Query("SELECT * FROM shop_list_item WHERE list_id LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShopListItem>>

    @Query("SELECT * FROM library WHERE name LIKE :name")
    suspend fun getAllLibraryItems(name: String): List<LibraryItem>

    @Query ("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Query ("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShopListName(id: Int)

    @Query("DELETE FROM shop_list_item WHERE list_id LIKE :listId")
    suspend fun deleteShopListItemsByListId(listId: Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertShopListName(name: ShopListNameItem)

    @Insert
    suspend fun insertItem(shopListItem: ShopListItem)

    @Insert
    suspend fun insertLibraryItem(libraryItem: LibraryItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateShopListName(name: ShopListNameItem)

    @Update
    suspend fun updateItem(shopListItem: ShopListItem)
}