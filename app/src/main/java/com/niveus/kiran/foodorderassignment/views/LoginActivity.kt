package com.niveus.kiran.foodorderassignment.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Visibility
import com.niveus.kiran.foodorderassignment.R
import com.niveus.kiran.foodorderassignment.databinding.ActivityLoginBinding
import com.niveus.kiran.foodorderassignment.entities.User
import com.niveus.kiran.foodorderassignment.sharedpref.SharedPreferencesManager
import com.niveus.kiran.foodorderassignment.viewmodel.LoginViewModel
import java.security.MessageDigest
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        if(sharedPreferencesManager.isLogin()!!){
            intent = Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(intent)
        }
        binding.progressBar.visibility = View.GONE

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[LoginViewModel::class.java]

        binding.loginBtn.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.emailAddress.text.toString()
            viewModel.get(email)

            val pass = binding.password.text.toString()
            //val email = binding.emailAddress.text.toString()
            val check = isValidPasswordFormat("S0lapr123$")
            if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(pass.isNotEmpty()){
                    viewModel.listUser?.observe(this, Observer {
                            list -> list?.let{
                        binding.progressBar.visibility = View.GONE
                        Log.d("Collections", it.userName)
                        if(pass == it.password && email == it.emailId){
                            sharedPreferencesManager.saveSpBoolean("spIsLogin",true)
                            sharedPreferencesManager.saveSpEmail("spEmail",it.emailId)
                            sharedPreferencesManager.saveSpName("spName",it.userName)
                            intent = Intent(this@LoginActivity,HomePageActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this,"Email Id or Password is wrong", Toast.LENGTH_LONG).show()
                        }

                    }
                    })
                }
                else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this,"Please Enter Password", Toast.LENGTH_LONG).show()
                }

            }
            else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this,"Please Enter valid Email ID", Toast.LENGTH_LONG).show()
            }
        }

        binding.toRegister.setOnClickListener {
            intent = Intent(this@LoginActivity,RegistrationActivity::class.java)
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