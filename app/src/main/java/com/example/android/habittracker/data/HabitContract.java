package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Aga on 6/28/2017.
 */

public class HabitContract {

    // private constructor to avoid accidentally using it
    private HabitContract() {}

    public static final class HabitEntry implements BaseColumns {

        // Name of the database table
        public final static String TABLE_NAME = "habits";

        // Unique ID number
        public static final String _ID = BaseColumns._ID;
        // Name of the habit
        public static final String COLUMN_HABIT_NAME = "name";
        // Type of activity e.g. sport, dining, entertainment
        public static final String COLUMN_HABIT_TYPE = "type";

        // possible values of type
        public static final int HABIT_NOT_SPECIFIED = 0;
        public static final int HABIT_SPORT = 1;
        public static final int HABIT_ENTERTAINMENT = 2;
        public static final int HABIT_DINING = 3;




    }
}
