package com.ggr3ml1n.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item")
data class ShopListItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "item_info")
    val itemInfo: String?,

    @ColumnInfo(name = "item_checked")
    val itemChecked: Boolean = false,

    @ColumnInfo(name = "list_id")
    val listId: Int,

    @ColumnInfo(name = "item_type")
    val itemType: Int = 0,
)
