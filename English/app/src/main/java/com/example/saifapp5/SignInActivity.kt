package com.example.saifapp5

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {

    lateinit var mail: TextInputEditText
    lateinit var pass: TextInputEditText
    lateinit var remember: CheckBox
    val userService: UserService = UserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mail = findViewById(R.id.Mail)
        pass = findViewById(R.id.Password)
        remember = findViewById(R.id.checkBox)

        userService.Init(this)
        remember.isChecked = userService.rememberCredentials
        if (remember.isChecked) {
            mail.setText(userService.signinEmail)
            pass.setText(userService.signinPass)
        }
    }
    fun GoListen(view: View) {
        userService.rememberCredentials = remember.isChecked
        if (!remember.isChecked)
            userService.cleanLoginScreenData()

        val mailString = mail.text.toString().trim().lowercase()
        val passString = pass.text.toString().trim()

        var userCheckStatus = userService.credentialsStatus(mailString, passString)
        when (userCheckStatus) {
            UserCheckStatus.Empty
            -> Toast.makeText(this, "Введите Емайл и Пароль!", Toast.LENGTH_SHORT).show()

            UserCheckStatus.WrongEmail
            -> Toast.makeText(this, "E-mail введён не корректно!", Toast.LENGTH_SHORT).show()

            UserCheckStatus.WrongEmailOrPassword
            -> Toast.makeText(this, "Неверный Емайл или Пароль!", Toast.LENGTH_SHORT).show()

            UserCheckStatus.PasswordsNotEqual
            -> throw IllegalArgumentException("Ошибка Входа!")

            UserCheckStatus.OK -> {
                if (remember.isChecked)
                    userService.saveLoginScreenData(mailString, passString)

                goToListen()
            }
        }
    }

    fun goToListen() {
        val intent = Intent(this@SignInActivity, ListenActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun ForwardRegistration(view: View) {
        val intent = Intent(this@SignInActivity, RegistrActivity::class.java)
        startActivity(intent)
        finish()
    }
}