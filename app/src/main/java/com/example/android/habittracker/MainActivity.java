package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    // Database helper
    private HabitDbHelper habitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        habitDbHelper = new HabitDbHelper(this);

        long insertResult;

        // insert some data rows into the database
        insertResult = insertHabit("jogging", HabitEntry.HABIT_SPORT);
        Log.v("MainActivity", "insertHabit() returned " + insertResult);
        insertResult = insertHabit("watching movies", HabitEntry.HABIT_ENTERTAINMENT);
        Log.v("MainActivity", "insertHabit() returned " + insertResult);

        // test for bad method arguments
        insertResult = insertHabit("watching movies", 1000);
        Log.v("MainActivity", "insertHabit() returned " + insertResult);

        // list all rows
        selectHabits();
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
            values.put(HabitEntry.COLUMN_HABIT_TYPE, habitType);
            return db.insert(HabitEntry.TABLE_NAME, null, values);
        }
    }

    public void selectHabits() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_TYPE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                HabitEntry._ID + " ASC";

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,     // The table to query
                projection,                // The columns to return
                null,                      // The columns for the WHERE clause
                null,                      // The values for the WHERE clause
                null,                      // don't group the rows
                null,                      // don't filter by row groups
                sortOrder                  // The sort order
        );

        try {
            Log.v("HabitTracker", "Number of rows in database table: " + cursor.getCount() + "\n\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int typeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TYPE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentType = cursor.getInt(typeColumnIndex);

                Log.v("HabitTracker", currentId + " - " + currentName + " - " + currentType + " - ");
            }
        } finally {
            cursor.close();
        }
    }
}
