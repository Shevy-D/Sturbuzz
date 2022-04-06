package com.shevy.sturbuzz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class DrinkActivity : AppCompatActivity() {
    companion object {
        val EXTRA_DRINKID = "drinkId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)

        val drinkId = intent.extras!![EXTRA_DRINKID] as Int
        val drink = Drink.drinks[drinkId]

        val name = findViewById<View>(R.id.name) as TextView
        name.text = drink.name

        val description = findViewById<View>(R.id.description) as TextView
        description.text = drink.description

        val photo = findViewById<View>(R.id.photo) as ImageView
        photo.setImageResource(drink.imageResourceId)
        photo.contentDescription = drink.name
    }
}