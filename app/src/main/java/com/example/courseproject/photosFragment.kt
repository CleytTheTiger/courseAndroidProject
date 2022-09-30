package com.example.courseproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.courseproject.databinding.FragmentPhotosBinding
import org.json.JSONException

class photosFragment : Fragment() {

    lateinit var binding : FragmentPhotosBinding
    private val datamodel: customViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imagesList = mutableListOf<String>()
        val customAdapter = customPhotoAdapter(requireContext(),imagesList)
        binding.photosList.layoutManager = GridLayoutManager(requireContext(),2)
        binding.photosList.adapter = customAdapter
        val url = "https://openlibrary.org/authors/${datamodel.authorID.value}.json"
        val requestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                try {
                    //val obj: JSONObject = response
                    val images = response.getJSONArray("photos")
                    for (i in 0 until images.length()){
                        val photoId = images[i].toString()
                        Log.e("Photo ----- ",""+photoId)
                        imagesList.add(photoId)
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
        requestQueue.add(jsonObjectRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance() = photosFragment()
    }
}