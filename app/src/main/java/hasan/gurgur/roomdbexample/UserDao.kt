package hasan.gurgur.roomdbexample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
 interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAll(): List<User>

    @Query("SELECT * FROM user_table WHERE tckn_no LIKE :roll LIMIT 1 ")
    fun findByRoll(roll: Long): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

}