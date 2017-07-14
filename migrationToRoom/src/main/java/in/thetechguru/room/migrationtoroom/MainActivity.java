package in.thetechguru.room.migrationtoroom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
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

import in.thetechguru.room.migrationtoroom.room.UserDB;
import in.thetechguru.room.migrationtoroom.sqlitehlper.UserDbHelper;

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


        //add entry to db created by sqlite helper
        UserDbHelper userDbHelper = new UserDbHelper(this);
        userDbHelper.addUser(new User(1,"Hulk","11-445-9999"));
        userDbHelper.addUser(new User(2,"Dominic","11-445-9999"));
        userDbHelper.addUser(new User(3,"Thor","11-445-9999"));
        userDbHelper.addUser(new User(4,"Batwoman","11-445-9999"));
        userDbHelper.addUser(new User(5,"Wonderman","11-445-9999"));


        User extractedUser = userDbHelper.getUser(1);
        Log.v("Extracted user",extractedUser.getUName() + " : " + extractedUser.getUNumber());


        final Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                // Since we didn't alter the table, there's nothing else to do here.
                int version = database.getVersion();
                Log.v("Old version",version+"");
            }
        };


        //create db
        final UserDB userDB = Room.databaseBuilder(this
                , UserDB.class
                , "userDB")
                .addMigrations(MIGRATION_1_2)
                .build();




        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                userDB.userDAO().insertUser(new User(6,"Amit", "888888888"));
                String s = userDB.userDAO().getUsers().get(0).getUName();
                Log.v("new version",s+"");
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

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mNameFromDB.append("\n");
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
