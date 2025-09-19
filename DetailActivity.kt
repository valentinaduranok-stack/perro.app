package com.example.dogapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import android.widget.Button


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgDogDetail = findViewById<ImageView>(R.id.imgDogDetail)
        val tvBreed = findViewById<TextView>(R.id.tvBreed)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val tvWeight = findViewById<TextView>(R.id.tvWeight)
        val tvHeight = findViewById<TextView>(R.id.tvHeight)
        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // vuelve a MainActivity
        }

        // Get data from intent
        val imageUrl = intent.getStringExtra("imageUrl")
        val breed = intent.getStringExtra("breed")
        val age = intent.getStringExtra("age")
        val weight = intent.getStringExtra("weight")
        val height = intent.getStringExtra("height")

        // Load image
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(imgDogDetail)

        // Set text
        tvBreed.text = "Raza: $breed"
        tvAge.text = "Edad: $age"
        tvWeight.text = "Peso: $weight"
        tvHeight.text = "Altura: $height"

    }
}
