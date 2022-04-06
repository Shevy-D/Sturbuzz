package com.shevy.sturbuzz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class SturbuzzDatabaseHelper internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

/*  private val DB_NAME = "starbuzz"
    private val DB_VERSION = 1*/

    override fun onCreate(db: SQLiteDatabase) {
        updateMyDatabase(db, 0, DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        updateMyDatabase(db, oldVersion, newVersion)
    }

    private fun updateMyDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            db.execSQL(
                "CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME TEXT, "
                        + "DESCRIPTION TEXT, "
                        + "IMAGE_RESOURCE_ID INTEGER);"
            )
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte)
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino)
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter)
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;")
        }
    }

    companion object {
        private const val DB_NAME = "starbuzz"
        private const val DB_VERSION = 2

        private fun insertDrink(
            db: SQLiteDatabase, name: String, description: String,
            resourceId: Int
        ) {
            val drinkValues = ContentValues()
            drinkValues.put("NAME", name)
            drinkValues.put("DESCRIPTION", description)
            drinkValues.put("IMAGE_RESOURCE_ID", resourceId)
            db.insert("DRINK", null, drinkValues)
        }
    }
}


/*class StarbuzzDatabaseHelper internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val DB_NAME = "starbuzz" // the name of our database
    private val DB_VERSION = 2 // the version of the database

    override fun onCreate(db: SQLiteDatabase) {
        updateMyDatabase(db, 0, DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        updateMyDatabase(db, oldVersion, newVersion)
    }

    private fun updateMyDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            db.execSQL(
                "CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME TEXT, "
                        + "DESCRIPTION TEXT, "
                        + "IMAGE_RESOURCE_ID INTEGER);"
            )
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte)
            insertDrink(
                db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                R.drawable.cappuccino
            )
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter)
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;")
        }
    }

    companion object {
        private const val DB_NAME = "starbuzz" // the name of our database
        private const val DB_VERSION = 2 // the version of the database
        private fun insertDrink(
            db: SQLiteDatabase, name: String, description: String,
            resourceId: Int
        ) {
            val drinkValues = ContentValues()
            drinkValues.put("NAME", name)
            drinkValues.put("DESCRIPTION", description)
            drinkValues.put("IMAGE_RESOURCE_ID", resourceId)
            db.insert("DRINK", null, drinkValues)
        }
    }
}*/
