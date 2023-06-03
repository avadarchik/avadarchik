package com.example.saifapp5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

class RegistrActivity : AppCompatActivity() {

    lateinit var name: TextInputEditText
    lateinit var mail: TextInputEditText
    lateinit var pass: TextInputEditText
    lateinit var repass: TextInputEditText
    lateinit var lname: TextInputEditText

    private val userService: UserService = UserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)
        name = findViewById(R.id.etName)
        mail = findViewById(R.id.etMail)
        pass = findViewById(R.id.etPassword)
        repass = findViewById(R.id.etConfirmPassword)
        lname = findViewById(R.id.etName)

        userService.Init(this)
    }
    fun ButRegistr(view: View) {
        val nameString = name.text.toString().trim().lowercase()
        val mailString = mail.text.toString().trim()
        val passString = pass.text.toString().trim()
        val repassString = repass.text.toString().trim()
        val lnameString = name.text.toString().trim()

        if (nameString.isNullOrBlank())
            Toast.makeText(this, "Заполните пустые поля!", Toast.LENGTH_SHORT).show()

        var userCheckStatus = userService.credentialsStatus(mailString, passString, repassString)
        when (userCheckStatus) {
            UserCheckStatus.Empty
            -> Toast.makeText(this, "Заполните пустые поля!", Toast.LENGTH_SHORT).show()

            UserCheckStatus.WrongEmail
            -> Toast.makeText(this, "Емайл введен некорректно!", Toast.LENGTH_SHORT).show()

            UserCheckStatus.PasswordsNotEqual
            -> Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show()

            UserCheckStatus.WrongEmailOrPassword
            -> throw IllegalArgumentException("Ошибка регистраций!")

            UserCheckStatus.OK -> {
                userService.saveUser(mailString, passString, nameString, lnameString)
                Toast.makeText(this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegistrActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun BackToSignIn(view: View) {
        val intent = Intent(this@RegistrActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}