package in.thetechguru.room.roomsample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by AB on 7/12/2017.
 */

@Database (entities = {User.class},version = 1)
@TypeConverters({Converters.class})
public abstract class UserDB extends RoomDatabase {

    public abstract UserDAO userDAO();

}
