package in.thetechguru.room.roomsample;

import android.arch.persistence.room.Entity;
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
    private ArrayList<String> uGirlFriends = new ArrayList<>();

    public User() {
    }

    public User(int id, String name, List<String> gfs){
        this.uId = id;
        this.uName = name;
        this.uGirlFriends.addAll(gfs);
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

    public void setUGirlFriends(ArrayList<String> uGirlFriends) {
        this.uGirlFriends = uGirlFriends;
    }

    public ArrayList<String> getUGirlFriends(){
        return uGirlFriends;
    }
}
