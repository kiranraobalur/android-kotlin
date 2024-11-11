package com.niveus.kiran.foodorderassignment.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.niveus.kiran.foodorderassignment.R
import com.niveus.kiran.foodorderassignment.databinding.ActivityRegistrationBinding
import com.niveus.kiran.foodorderassignment.entities.User
import com.niveus.kiran.foodorderassignment.viewmodel.LoginViewModel
import java.security.MessageDigest
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[LoginViewModel::class.java]

        binding.registrationBtn.setOnClickListener{
            val name = binding.editTextName.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val cPassword = binding.editTextTextPassword2.text.toString()
            val getHash = getHashForString(this,password)

            Log.d("insert"," Added")

            if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(password.isNotEmpty()){
                    if(cPassword.isNotEmpty()){
                        if(cPassword == password){
                            viewModel.insertUser(User(name,email,password))
                        }
                        else {
                            Toast.makeText(this,"Password and Confirm password should be same", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(this,"Please Enter Confirm Password", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(this,"Please Enter Password", Toast.LENGTH_LONG).show()
                }

            }
            else{
            Toast.makeText(this,"Please Enter valid Email ID", Toast.LENGTH_LONG).show()
        }
        }

        binding.toLogin.setOnClickListener {
            intent = Intent(this@RegistrationActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        return passwordREGEX.matcher(password).matches()
    }

    fun hashAndSavePasswordHash(context: Context, clearPassword: String) {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(clearPassword.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedPassword = sb.toString()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("password_hash", hashedPassword)
        editor.apply()
    }

    fun getSavedPasswordHash(context: Context): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return if (sharedPref.contains("name"))
            sharedPref.getString("name", "")!!
        else
            ""
    }

    fun getHashForString(context: Context, stringToHash: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(stringToHash.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedString = sb.toString()
        return hashedString
    }
}