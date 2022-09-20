package hasan.gurgur.roomdbexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "first_name") val fistName : String?,
    @ColumnInfo(name = "last_name") val lastName : String?,
    @ColumnInfo(name = "tckn_no") val tcknNo : Long?
)
