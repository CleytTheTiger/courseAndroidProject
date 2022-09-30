package com.example.courseproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.courseproject.databinding.FragmentBooksBinding
import org.json.JSONException
import org.json.JSONObject

class booksFragment : Fragment() {

    lateinit var binding : FragmentBooksBinding
    private val datamodel: customViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val books = mutableListOf<String>()
        val customAdapter = books_adapter(books)
        binding.booksList.layoutManager = LinearLayoutManager(activity)
        binding.booksList.adapter = customAdapter
        val url = "https://openlibrary.org/authors/"+datamodel.authorID.value+"/works.json?limit=25"
        Log.e("URL ----- ",""+url)
        val requestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                try {
                    //val obj: JSONObject = response
                    val array = response.getJSONArray("entries")

                    for (i in 0 until array.length()){
                        val booksArr: JSONObject = array.getJSONObject(i)
                        val book = booksArr.getString("title")
                        Log.e("Name ----- ",""+book)
                        books.add(book)
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
                    //Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show()
                }
            }
        )
        //progressBar.visibility = View.GONE
        requestQueue.add(jsonObjectRequest)
    }


    companion object {
        @JvmStatic
        fun newInstance() = booksFragment()
    }
}