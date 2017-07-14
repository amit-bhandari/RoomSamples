package in.thetechguru.room.migrationtoroom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AB on 7/12/2017.
 */

@Entity
public class User  {

    @PrimaryKey
    private int uId;
    private String uName;
    private String uNumber;

    public User() {
    }

    @Ignore
    public User(int id, String name, String number){
        this.uId = id;
        this.uName = name;
        this.uNumber= number;
    };

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUNumber() {
        return uNumber;
    }

    public void setUNumber(String uNumber) {
        this.uNumber = uNumber;
    }
}
