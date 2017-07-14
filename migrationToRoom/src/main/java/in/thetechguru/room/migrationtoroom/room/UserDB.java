package in.thetechguru.room.migrationtoroom.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import in.thetechguru.room.migrationtoroom.User;

/**
 * Created by AB on 7/12/2017.
 */

@Database (entities = {User.class},version = 2)
public abstract class UserDB extends RoomDatabase {

    public abstract UserDAO userDAO();

}
