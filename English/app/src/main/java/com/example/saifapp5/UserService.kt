package com.example.saifapp5

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import java.util.regex.Pattern

class UserService {

    val pattern = ("[+-z0-9]{1,100}" + "@" + "[0-9a-z]{1,6}" + "\\." + "[a-z]{1,5}")

    fun isEmailValid(text: String): Boolean {
        return Pattern.compile(pattern).matcher(text).matches()
    }

    fun Init(context: Activity) {
        pref = context.getSharedPreferences("TABLEE", AppCompatActivity.MODE_PRIVATE)
    }
    private var pref: SharedPreferences? = null

    private val preff: SharedPreferences
        get() {
            if (pref == null) {
                throw Exception("Ошибка установки регистраций")
            }
            return pref as SharedPreferences;
        }

    var rememberCredentials: Boolean
        get() {
            return preff.getBoolean(TAG_DOREMEMBER,  false)
        }
        set(value) {
            var editor = preff.edit()
            editor.putBoolean(TAG_DOREMEMBER, value)
            editor.apply()
        }

    val signinEmail: String
        get() {
            return preff.getString(TAG_SIGNEMAIL, "")!!
        }
    val signinPass: String
        get() {
            return preff.getString(TAG_SIGNPASS, "")!!
        }

    private var currentUser: String
        get() {
            return preff.getString(TAG_CURRENTUSER, "")!!
        }
        set(value) {
            var editor = preff.edit()
            editor.putString(TAG_CURRENTUSER, value)
            editor.apply()
        }
    val userFullName: String
        get() {
            if (userLastName == "")
                return userName
            if (userName == "")
                return userLastName
            return "$userName $userLastName"
        }

    val userName
        get() = preff.getString("[$currentUser]$TAG_NAME", "")!!

    val userLastName
        get() = preff.getString("[$currentUser]$TAG_LASTNAME", "")!!

    private val TAG_DOREMEMBER = "signinDoRemember"

    private val TAG_SIGNEMAIL = "signinEmail"
    private val TAG_SIGNPASS = "signinPass"

    private val TAG_CURRENTUSER = "userEmail"
    private val TAG_PASS = "userPass"
    private val TAG_NAME = "userName"
    private val TAG_LASTNAME = "userLastName"

    fun saveUser(mail: String, pass: String, name: String, lastname: String) {
        var editor = preff.edit()
        editor.putString("[$mail]$TAG_PASS", pass)
        editor.putString("[$mail]$TAG_NAME", name)
        editor.putString("[$mail]$TAG_LASTNAME", lastname)
        editor.apply()
    }

    fun saveLoginScreenData(mail: String, pass: String) {
        var editor = preff.edit()
        editor.putString(TAG_SIGNEMAIL, mail)
        editor.putString(TAG_SIGNPASS, pass)
        editor.apply()
    }

    fun cleanLoginScreenData() {
        var editor = preff.edit()
        editor.remove(TAG_SIGNEMAIL)
        editor.remove(TAG_SIGNPASS)
        editor.apply()
    }

    fun checkAuth(mail: String, pass: String): Boolean {
        if (preff.getString("[$mail]$TAG_PASS", null) == pass) {
            currentUser = mail
            return true
        }
        var editor = preff.edit()
        editor.remove(TAG_CURRENTUSER)
        editor.apply()
        return false
    }

    fun credentialsStatus(mail: String, pass: String, repass: String? = null): UserCheckStatus {
        if (mail.isNullOrEmpty() || pass.isNullOrEmpty())
            return UserCheckStatus.Empty

        if (!isEmailValid(mail))
            return UserCheckStatus.WrongEmail

        if (repass == null && !checkAuth(mail, pass))
            return UserCheckStatus.WrongEmailOrPassword

        if (repass != null && pass != repass)
            return UserCheckStatus.PasswordsNotEqual

        return UserCheckStatus.OK
    }
}