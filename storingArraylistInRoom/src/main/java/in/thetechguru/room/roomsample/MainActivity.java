package in.thetechguru.room.roomsample;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create db
        final UserDB userDB = Room.databaseBuilder(this
                , UserDB.class
                , "MyDB").build();

        //insert user without pets
        final ArrayList<String> pets = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                userDB.userDAO().insertUser(new User(1,"Amit", pets));
            }
        });

        //for making changes to UI from db thread
        final Handler mHandler = new Handler();

        //for displaying the name
        final TextView mNameFromDB = (TextView) findViewById(R.id.message);

        //ttaking  input
        final EditText mInputName = (EditText) findViewById(R.id.inputName);
        Button mAdd = (Button) findViewById(R.id.addName);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mInputName.getText().toString();
                if(name.equals("")) return;
                mInputName.setText("");
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        //update the user and insert into db
                        //as id is primary key, record will be replaced
                        pets.add(name);
                        userDB.userDAO().insertUser(new User(1,"Amit", pets));
                        final ArrayList<String> petsSoFar = userDB.userDAO().getUsers().get(0).getUPets();
                        Log.v("Data",petsSoFar.get(petsSoFar.size()-1));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mNameFromDB.append("\n" + petsSoFar.get(petsSoFar.size()-1));
                            }
                        });
                    }
                });
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
