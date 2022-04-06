package com.shevy.sturbuzz

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class DrinkActivity : AppCompatActivity() {
    companion object {
        val EXTRA_DRINKID = "drinkId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)

        val drinkId = intent.extras!![EXTRA_DRINKID] as Int

        val starbuzzDatabaseHelper: SQLiteOpenHelper = SturbuzzDatabaseHelper(this)
        try {
            val db = starbuzzDatabaseHelper.readableDatabase
            val cursor = db.query(
                "DRINK", arrayOf("NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"),
                "_id = ?", arrayOf(Integer.toString(drinkId)),
                null, null, null
            )

            if (cursor.moveToFirst()) {
                val nameText = cursor.getString(0)
                val descriptionText = cursor.getString(1)
                val photoId = cursor.getInt(2)
                val isFavorite = cursor.getInt(3) == 1

                val name = findViewById<View>(R.id.name) as TextView
                name.text = nameText

                val description = findViewById<View>(R.id.description) as TextView
                description.text = descriptionText

                val photo = findViewById<View>(R.id.photo) as ImageView
                photo.setImageResource(photoId)
                photo.contentDescription = nameText

                val favorite = findViewById<View>(R.id.favorite) as CheckBox
                favorite.isChecked = isFavorite
            }
        } catch (e: SQLiteException) {
            val toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun onFavoriteClicked(view: View?) {
        val drinkId = intent.extras!![EXTRA_DRINKID] as Int
        UpdateDrinkTask().execute(drinkId)
    }

    private inner class UpdateDrinkTask :
        AsyncTask<Int, Void, Boolean>() {
        private var drinkValues: ContentValues? = null
        override fun onPreExecute() {
            val favorite = findViewById<View>(R.id.favorite) as CheckBox
            drinkValues = ContentValues()
            drinkValues!!.put("FAVORITE", favorite.isChecked)
        }

        override fun doInBackground(vararg drinks: Int?): Boolean {
            val drinkId = drinks[0]
            val sturbuzzDatabaseHelper: SQLiteOpenHelper =
                SturbuzzDatabaseHelper(this@DrinkActivity)
            return try {
                val db = sturbuzzDatabaseHelper.writableDatabase
                db.update(
                    "DRINK", drinkValues,
                    "_id = ?", arrayOf(drinkId.toString())
                )
                db.close()
                true
            } catch (e: SQLiteException) {
                false
            }
        }

        override fun onPostExecute(success: Boolean) {
            if (!success) {
                Toast.makeText(
                    this@DrinkActivity,
                    "Database unavailable",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
