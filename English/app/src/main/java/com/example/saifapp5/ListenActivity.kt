package com.example.saifapp5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListenActivity : AppCompatActivity() {
    lateinit var name: TextView
    var userService = UserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listen)
        userService.Init(this)
        name = findViewById(R.id.NameListen)
        val alpabet_res:RecyclerView=findViewById((R.id.rec_view))
        alpabet_res.adapter = AlphabetAdapter(this, AlpabetList().list)
        name.setText(userService.userLastName)
    }

    fun GoListen(view: View) {
        val intent = Intent(this@ListenActivity, ListenTestActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun ForwardRegistration(view: View) {
        val intent = Intent(this@ListenActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}