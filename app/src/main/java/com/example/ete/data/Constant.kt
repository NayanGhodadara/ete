package com.example.ete.data

object Constant {

    //Preference key
    object PrefsKeys {
        const val FIREBASE_MESSAGE_TOKEN: String = "firebaseMessageToken"
        const val USER_DATA: String = "userData"
        const val AUTH_DATA: String = "authData"
    }

    //Api key
    object ApiObject {
        const val PLATFORM = "platform"
        const val VERSION = "version"
        const val ANDROID = "Android"
    }

    //Force update key
    object ForceUpdate {
        const val UP_TO_DATE: Int = 0
        const val FORCE_UPDATE: Int = 1
        const val OPTIONAL_UPDATE: Int = 2
    }
}