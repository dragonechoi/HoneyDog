package com.cys.honeydog.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.cys.honeydog.adapters.SearchFragmentAdapter
import com.cys.honeydog.databinding.FragmentSearchMainBinding
import com.cys.honeydog.network.AniMalHospital
import com.cys.honeydog.network.RetRofitHospital
import com.cys.honeydog.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SearchMainFragment : Fragment() {
    private lateinit var Binding: FragmentSearchMainBinding
    var item: MutableList<AniMalHospital> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentSearchMainBinding.inflate(inflater, container, false)

        return Binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        Binding.recyclerSearch.adapter = SearchFragmentAdapter(requireContext(), item)
        Binding.searchBtn.setOnClickListener { searchDataHospital() }

    }

    fun searchDataHospital() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 1)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)

        val call: Call<RetRofitHospital> =
            retrofitService.searchDataByJson(Binding.etSearch.text.toString())

        call.enqueue(object : Callback<RetRofitHospital> {
            override fun onResponse(
                call: Call<RetRofitHospital>,
                response: Response<RetRofitHospital>
            ) {
                val naverSearch: RetRofitHospital? = response.body()

                Binding.recyclerSearch.adapter =
                    SearchFragmentAdapter(requireContext(), naverSearch!!.items)

            }

            override fun onFailure(call: Call<RetRofitHospital>, t: Throwable) {
                Toast.makeText(requireContext(), "검색에 실패 했습니다 ", Toast.LENGTH_SHORT).show()
            }
        })

    }

}


