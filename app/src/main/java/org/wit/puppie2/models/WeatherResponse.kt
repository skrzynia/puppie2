package org.wit.puppie2.models

import com.squareup.moshi.Json
data class WeatherResponse(@Json(name="visibility") val visibility:String){
}
