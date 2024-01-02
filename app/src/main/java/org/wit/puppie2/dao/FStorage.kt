package org.wit.puppie2.dao

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.wit.puppie2.models.Person
import org.wit.puppie2.models.Place
import timber.log.Timber.Forest.i

class FStorage {

//    private var database: DatabaseReference
      private var database: FirebaseFirestore

    init {
//        database = Firebase.database("https://puppie2-default-rtdb.firebaseio.com/").reference
        database = Firebase.firestore
        i("database =  ${database}")
    }



    fun writeNewUser(personId:String, person:Person){
        val temp = Person(email= person.email, myPlace = listOf())
        i("PERSON VALUE = ${temp}")
        database.collection("people").document(personId).set(temp)
        i("child = ${database}}")

    }

//    val personListener = object :ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            val person = snapshot.getValue<Person>()
//            i("RETRIEVED PERSON = ${person.toString()}")
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            TODO("Not yet implemented")
//        }
//    }

    fun getPerson(personId: String){
        i("PERSON ID = $personId")
        database.collection("people").document(personId).get().addOnSuccessListener {
            document ->
            if (document != null){
                i("DOCUMENT FOUND = ${document.data}")
            } else
            {
                i("DOCUMENT NOT FOUND")
            }
        }.addOnFailureListener {
            e -> i("FAILURE = ${e}")
        }

    }

    fun writeNewPlace(placeId:String, place:Place){
        val temp = Place(name = place.name, address = place.address, lat = place.lat, lon = place.lon, pickedImage = place.pickedImage, user =place.user)
        i("PLACE VALUE = $temp")
        database.collection("places").document(placeId).set(temp)
        i("ADDED PLACE = ${database.collection("places").document(placeId).get()}}")



    }

    fun getAllPlaces(): MutableList<Place> {
        val list = mutableListOf<Place>()
        database.collection("places").get().addOnSuccessListener {
            documents -> for (doc in documents) {
                i("PLACE IN LIST = ${doc.data.get("name")}")
                list.add(
                    Place(
                    name = doc.data.get("name").toString(),
                        address = doc.data.get("address").toString(),
                        lat = doc.data.get("lat").toString(),
                        lon = doc.data.get("lon").toString(),
                        createdAt = doc.data.get("createdAt").toString(),
                        pickedImage = doc.data.get("pickedImage").toString())
                )

            }
        }.addOnFailureListener{
            i("FAILURE IN GET ALL PLACES = $it")
        }

        return list
    }




    fun redundantGetPlaces() {
//        var tempPlace: Place? = null
//        val placesRef = database.child("places")
//        val placeListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.children.forEach {
//                    i("${it.child("name").value}")
//                    tempPlace = Place(
//                        name = it.child("name").value.toString(),
//                        address = it.child("address").value.toString(),
//                        lat = it.child("lat").value.toString(),
//                        lon = it.child("lon").value.toString(),
//                        createdAt = it.child("createdAt").value.toString(),
//                        pickedImage = it.child("pickedImage").value.toString()
//                    )
//                    i("TempPLACE = $tempPlace")
//
//
//                }
//
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        }
//        placesRef.addValueEventListener(placeListener)

    }

}
