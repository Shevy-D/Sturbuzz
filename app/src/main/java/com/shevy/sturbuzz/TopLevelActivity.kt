package com.shevy.sturbuzz

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener

class TopLevelActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    private lateinit var favoritesCursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_level)
        setupOptionsListView()
        setupFavoritesListView()

        val itemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                val intent = Intent(this@TopLevelActivity, DrinkCategoryActivity::class.java)
                startActivity(intent)
            }
        }

        val listView = findViewById<View>(R.id.list_options) as ListView
        listView.onItemClickListener = itemClickListener
    }

    private fun setupOptionsListView() {
        val itemClickListener =
            OnItemClickListener { _, _, position, _ ->
                if (position == 0) {
                    val intent = Intent(
                        this@TopLevelActivity,
                        DrinkCategoryActivity::class.java
                    )
                    startActivity(intent)
                }
            }
        val listView = findViewById<View>(R.id.list_options) as ListView
        listView.onItemClickListener = itemClickListener
    }

    private fun setupFavoritesListView() {
        val listFavorites = findViewById<View>(R.id.list_favorites) as ListView
        try {
            val sturbuzzDatabaseHelper: SQLiteOpenHelper = SturbuzzDatabaseHelper(this)
            db = sturbuzzDatabaseHelper.readableDatabase
            favoritesCursor = db.query("DRINK",
                arrayOf("_id", "NAME"),
                "FAVORITE = 1",
                null, null, null, null
            )
            val favoriteAdapter = SimpleCursorAdapter(
                this@TopLevelActivity,
                android.R.layout.simple_list_item_1,
                favoritesCursor,
                arrayOf("NAME"),
                intArrayOf(android.R.id.text1),
                0)
            listFavorites.adapter = favoriteAdapter
        } catch (e: SQLiteException) {
            val toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }

        listFavorites.onItemClickListener =
            OnItemClickListener { _, _, _, id ->
                val intent = Intent(this@TopLevelActivity, DrinkActivity::class.java)
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
                startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val newCursor = db.query("DRINK",
            arrayOf("_id", "NAME"),
            "FAVORITE = 1",
            null, null, null, null)
        val listFavorites = findViewById<ListView>(R.id.list_favorites)
        val adapter = listFavorites.adapter as CursorAdapter
        adapter.changeCursor(newCursor)
        favoritesCursor = newCursor
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesCursor.close()
        db.close()
    }
}
