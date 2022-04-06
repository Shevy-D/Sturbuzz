package com.shevy.sturbuzz

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast

class DrinkCategoryActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    private lateinit var cursor: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_category)

        val listDrinks = findViewById<View>(R.id.list_drinks) as ListView
        val sturbuzzDatabaseHelper: SQLiteOpenHelper = SturbuzzDatabaseHelper(this)
        try {
            db = sturbuzzDatabaseHelper.readableDatabase
            cursor = db.query("DRINK",
                arrayOf("_id","NAME"),null, null, null, null, null)

            val listAdapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                arrayOf("NAME"),
                intArrayOf(android.R.id.text1),
                0)
            listDrinks.adapter = listAdapter

        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT)
                .show()
        }

        val itemClickListener =
            OnItemClickListener { _, _, _, id -> //Pass the drink the user clicks on to DrinkActivity
                val intent = Intent(
                    this@DrinkCategoryActivity,
                    DrinkActivity::class.java
                )
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
                startActivity(intent)
            }

        listDrinks.onItemClickListener = itemClickListener
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor.close()
        db.close()
    }
}