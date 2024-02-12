package com.tes.global.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.tes.global.model.response.ProfileMeResponse
import com.tuyenmonkey.mkloader.BuildConfig

class SessionManager(context: Context) {

    private val PRIVATE_MODE = 0

    private val PREF_NAME = BuildConfig.APPLICATION_ID
    private val PREF_IS_LOGIN = "is_login"
    private val PREF_DATA_MEMBER = "data_member"
    private val PREF_PASSWORD = "password"

    private val TOKEN = "token"

    lateinit var pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    fun isLogin(bol: Boolean) {
        editor.putBoolean(PREF_IS_LOGIN, bol).apply()
    }

    fun isLogin(): Boolean {
        return pref.getBoolean(PREF_IS_LOGIN, false)
    }

    var dataUser: ProfileMeResponse.Data?
        get() {
            return get(PREF_DATA_MEMBER)
        }
        set(data) {
            put(data, PREF_DATA_MEMBER)
        }

    fun savePassword(pass: String) {
        editor.putString(PREF_PASSWORD, pass).apply()
    }

    fun getPassword(): String {
        return pref.getString(PREF_PASSWORD, "")!!
    }

    var token: String
        get(){
            return pref.getString(TOKEN, "")!!
        }
        set(value){
            editor.putString(TOKEN, value).apply()
        }

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        pref.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = pref.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }
}