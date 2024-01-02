package org.wit.puppie2.utilities

import java.security.MessageDigest

class Helpers {

    @OptIn(ExperimentalStdlibApi::class)
    fun getHashedString(email:String) : String{
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(email.toByteArray())
        return digest.toHexString()
    }
}