package com.shevy.sturbuzz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView

class TopLevelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_level)

        val itemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                val intent = Intent(this@TopLevelActivity, DrinkCategoryActivity::class.java)
                startActivity(intent)
            }
        }

        val listView = findViewById<View>(R.id.list_options) as ListView
        listView.onItemClickListener = itemClickListener
    }
}