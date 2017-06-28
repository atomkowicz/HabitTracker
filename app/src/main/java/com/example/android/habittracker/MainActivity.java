package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    // Database helper
    private HabitDbHelper habitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        habitDbHelper = new HabitDbHelper(this);
    }

    private long insertHabit(String habitName, int habitType) {

        // Open database
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();

        if (habitType < 0 || habitType > 3) {
            Log.v("MainActivity", "insertHabit - bad function argument: habitType");
            return -1;
        } else if (isEmpty(habitName)) {
            Log.v("MainActivity", "insertHabit - empty function argument: habitName");
            return -1;
        } else {
            ContentValues values = new ContentValues();
            values.put(HabitEntry.COLUMN_HABIT_NAME, habitName.trim());
            values.put(HabitEntry.COLUMN_HABIT_TYPE, habitName);
            return db.insert(HabitEntry.TABLE_NAME, null, values);
        }
    }

    private void selectHabits() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
