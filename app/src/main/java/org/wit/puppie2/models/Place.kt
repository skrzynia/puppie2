package org.wit.puppie2.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import java.net.Inet4Address
import java.util.Date


data class Place (val name:String? = null, val address:String? = null, val lat:String? = null, val lon:String? = null, val pickedImage:String? = null, val createdAt: String = Date().toString(), val user:String? = null)
{

}