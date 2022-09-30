package com.example.courseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    //private val datamodel: customViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val names = mutableListOf<String>()
        val dates = mutableListOf<String>()
        val keys = mutableListOf<String>()
        val customAdapter = CustomRecyclerAdapter(names,dates)
        val listView = findViewById<RecyclerView>(R.id.authors_list)
        val button = findViewById<Button>(R.id.search_button)
        val progressBar = findViewById<ProgressBar>(R.id.main_progressbar)
        val editText = findViewById<EditText>(R.id.search_text)
        progressBar.visibility = View.INVISIBLE
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = customAdapter

        customAdapter.setOnItemClickListener(object : CustomRecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                intent.putExtra("key", keys[position])
                startActivity(intent)
                //Toast.makeText(this@MainActivity,""+keys[position],Toast.LENGTH_SHORT).show()
            }

        })

        button.setOnClickListener {
            names.clear()
            dates.clear()
            keys.clear()

            progressBar.visibility = View.VISIBLE

            var url = "https://openlibrary.org/search/authors.json"
            if (editText.text.isNotEmpty()){
                val words = editText.text.split(" ")
                url += "?q="
                for(i in words) {
                    url += "$i+"
                }
                url = url.dropLast(1)
            }
            Log.e("URL ----- ",""+url)
            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null, { response ->
                    try {
                        //val obj: JSONObject = response
                        val array = response.getJSONArray("docs")

                        for (i in 0 until array.length()){
                            val author: JSONObject = array.getJSONObject(i)
                            val name = author.getString("name")
                            val date = author.getString("birth_date")
                            val key = author.getString("key")
                            names.add(name)
                            dates.add(date)
                            keys.add(key)
                            Log.e("Name ----- ",""+name)
                            Log.e("Date ----- ",""+date)
                            Log.e("Key ----- ",""+key)
                            customAdapter.notifyDataSetChanged()
                        }

                        customAdapter.notifyDataSetChanged()

                    }
                    catch (e: JSONException){

                    }
                },
                { error: VolleyError? ->
                    if (error != null) {
                        Log.e("Ошибка ----- ",""+error)
                        Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show()
                    }
                }
            )
            progressBar.visibility = View.INVISIBLE
            requestQueue.add(jsonObjectRequest)
        }
    }
}