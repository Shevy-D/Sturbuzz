package com.shevy.sturbuzz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView

class DrinkCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_category)

        val listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Drink.drinks
        )

        val listDrinks = findViewById<View>(R.id.list_drinks) as ListView
        listDrinks.adapter = listAdapter

        val itemClickListener =
            OnItemClickListener { listDrinks, itemView, position, id -> //Pass the drink the user clicks on to DrinkActivity
                val intent = Intent(
                    this@DrinkCategoryActivity,
                    DrinkActivity::class.java
                )
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
                startActivity(intent)
            }

        listDrinks.onItemClickListener = itemClickListener
    }
}