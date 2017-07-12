package in.thetechguru.room.roomsample;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by AB on 7/12/2017.
 */

@Dao
public interface UserDAO {

    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM User")
    List<User> getUsers();

   // @Query("SELECT * FROM User")

}
