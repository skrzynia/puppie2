package org.wit.puppie2.models

import com.google.firebase.database.IgnoreExtraProperties



@IgnoreExtraProperties
data class Person(val name:String? = null, val surname: String? = null, val email:String? = null, val myPlace: List<String>? = null){

}

