package com.example.dummyjsonexercise.models

import org.json.JSONArray
import org.json.JSONObject

data class Recipe (
    var id : Int? = null,
    var name : String? = null,
    var ingredients : List<String>? = null,
    var instructions : List<String>? = null,
    var prepTimeMinutes : Int? = null,
    var cookTimeMinutes : Int? = null,
    var servings : Int? = null,
    var difficulty : String? = null,
    var cuisine : String? = null,
    var caloriesPerServing : Int? = null,
    var tags : List<String>? = null,
    var userId : Int? = null,
    var image : String? = null,
    var rating : Double? = null,
    var reviewCount: Int? = null,
    var mealType: List<String>? = null
){
    companion object{
        fun fromJson(jsonObject: JSONObject): Recipe {
            return Recipe(
                id = jsonObject.optInt("id"),
                name = jsonObject.optString("name"),
                ingredients = jsonArrayToList(jsonObject.optJSONArray("ingredients")),
                instructions = jsonArrayToList(jsonObject.optJSONArray("instructions")),
                prepTimeMinutes = jsonObject.optInt("prepTimeMinutes"),
                cookTimeMinutes = jsonObject.optInt("cookTimeMinutes"),
                servings = jsonObject.optInt("servings"),
                difficulty = jsonObject.optString("difficulty"),
                cuisine = jsonObject.optString("cuisine"),
                caloriesPerServing = jsonObject.optInt("caloriesPerServing"),
                tags = jsonArrayToList(jsonObject.optJSONArray("tags")),
                userId = jsonObject.optInt("userId"),
                image = jsonObject.optString("image"),
                rating = jsonObject.optDouble("rating"),
                reviewCount = jsonObject.optInt("reviewCount"),
                mealType = jsonArrayToList(jsonObject.optJSONArray("mealType"))
            )
        }

        private fun jsonArrayToList(jsonArray: JSONArray?) : List<String>? {
            if (jsonArray == null) return null
            val list = mutableListOf<String>()
            for (i in 0 until jsonArray.length()){
                list.add(jsonArray.getString(i))
            }
            return list
        }
    }
}