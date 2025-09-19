package com.example.dogapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var btnGetDog: Button
    private lateinit var imgDog: ImageView
    private lateinit var progressBar: ProgressBar
    private var currentImageUrl: String? = null

    // Predefined lists for random dog data
    private val breeds = listOf("Dachshund", "Beagle", "Bulldog", "Poodle", "Labrador")
    private val heights = listOf("20 cm", "25 cm", "30 cm", "35 cm", "40 cm")
    private val weights = listOf("5 kg", "8 kg", "10 kg", "12 kg", "15 kg")
    private val ages = listOf("1 year", "2 years", "3 years", "4 years", "5 years")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetDog = findViewById(R.id.btnGetDog)
        imgDog = findViewById(R.id.imgDog)
        progressBar = findViewById(R.id.progressBar)

        // Fetch a new dog when button is clicked
        btnGetDog.setOnClickListener {
            fetchDogImage()
        }

        // Open DetailActivity when image is clicked
        imgDog.setOnClickListener {
            currentImageUrl?.let { url ->
                val intent = Intent(this, DetailActivity::class.java)

                // Randomly select dog data
                intent.putExtra("imageUrl", url)
                intent.putExtra("breed", breeds.random())
                intent.putExtra("height", heights.random())
                intent.putExtra("weight", weights.random())
                intent.putExtra("age", ages.random())

                startActivity(intent)
            }
        }
    }

    private fun fetchDogImage() {
        progressBar.visibility = View.VISIBLE
        imgDog.visibility = View.GONE

        thread {
            try {
                val apiResponse = URL("https://dog.ceo/api/breeds/image/random").readText()
                val json = JSONObject(apiResponse)
                val imageUrl = json.getString("message")
                currentImageUrl = imageUrl

                runOnUiThread {
                    Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(imgDog)

                    progressBar.visibility = View.GONE
                    imgDog.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    imgDog.visibility = View.VISIBLE
                }
            }
        }
    }
}
