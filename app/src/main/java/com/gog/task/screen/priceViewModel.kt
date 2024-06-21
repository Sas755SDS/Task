package com.gog.task.screen

import Product
import Specification
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val _specifications = MutableStateFlow<Product?>(null)
    val specifications: StateFlow<Product?> = _specifications.asStateFlow()

    fun loadSpecifications(jsonString: String) {
        viewModelScope.launch {
            val gson = Gson()
            val data: JsonObject? = try {

                gson.fromJson(jsonString, JsonObject::class.java)
            } catch (e: Exception) {
                // Handle parsing errors gracefully (e.g., log the error)
                null
            }
            _specifications.value = gson.fromJson(jsonString, Product::class.java)
            Log.d("TAG", "loadSpecifications:${_specifications.value} ")
//            val specs = data?.let { jsonObject ->
//                val specificationsJson = jsonObject.getAsJsonArray("specifications")
//                specificationsJson?.mapNotNull { specElement ->
//                    val specString = specElement.toString()
//                    val gson = Gson()
//                    gson.fromJson(specString, Specification::class.java)
//                }
//            }
//
//          = specs
        }
    }
}