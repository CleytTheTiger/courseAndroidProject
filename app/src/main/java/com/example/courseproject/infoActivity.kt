package com.example.courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.courseproject.databinding.ActivityInfoBinding
import com.squareup.picasso.Picasso
import org.json.JSONException

class InfoActivity : AppCompatActivity() {

    private val datamodel: customViewModel by viewModels()
    private lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        binding.authorName.setLines(3)
        binding.authorName.maxLines = 3
        binding.progressBarInfo.visibility = View.VISIBLE
        setContentView(binding.root)
        val key : String = intent.extras?.getString("key").toString()
        datamodel.authorID.value = key
        val authName = findViewById<TextView>(R.id.author_name)
        val authDesc = findViewById<TextView>(R.id.author_desc)
        val authPhoto = findViewById<ImageView>(R.id.author_photo)

        authName.textAlignment = View.TEXT_ALIGNMENT_CENTER
        authDesc.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val url = "https://openlibrary.org/authors/${key}.json"
        var imageID : String
        var imageURL = mutableListOf<String>()
        Log.e("URL ----- ",""+url)
        val requestQueue = Volley.newRequestQueue(this@InfoActivity)
        binding.booksButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.place_for_fragments, booksFragment.newInstance())
                .commit()
        }
        binding.photosButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.place_for_fragments, photosFragment.newInstance())
                .commit()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                try {
                    val images = response.getJSONArray("photos")
                    val name = response.getString("name")
                    val desc = response.getString("bio")
                    imageID = images[0].toString()
                    imageURL.add("https://covers.openlibrary.org/a/id/$imageID.jpg")
                    authName.text = name
                    authDesc.text = desc
                    Picasso.with(this@InfoActivity)
                        .load(imageURL[0])
                        .placeholder(androidx.appcompat.R.drawable.abc_btn_colored_material)
                        .error(R.drawable.ic_launcher_background)
                        .into(authPhoto)
                    Log.e("Name ----- ",""+name)
                    Log.e("Bio ----- ",""+desc)
                    Log.e("Image ----- ",""+imageID)
                }
                catch (e: JSONException){

                }
            },
            { error: VolleyError? ->
                if (error != null) {
                    Log.e("Ошибка ----- ",""+error)
                    Toast.makeText(this@InfoActivity,""+error, Toast.LENGTH_SHORT).show()
                }
            }
        )
        requestQueue.add(jsonObjectRequest)

        binding.progressBarInfo.visibility = View.INVISIBLE
    }
}