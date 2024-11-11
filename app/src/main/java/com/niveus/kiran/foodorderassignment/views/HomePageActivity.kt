package com.niveus.kiran.foodorderassignment.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niveus.kiran.foodorderassignment.R
import com.niveus.kiran.foodorderassignment.adapters.FoodAdapter
import com.niveus.kiran.foodorderassignment.adapters.FoodCountAddUpdateInterface
import com.niveus.kiran.foodorderassignment.adapters.FoodCountDeleteInterface
import com.niveus.kiran.foodorderassignment.adapters.FoodInsertToCartInterface
import com.niveus.kiran.foodorderassignment.entities.Cart
import com.niveus.kiran.foodorderassignment.entities.Food
import com.niveus.kiran.foodorderassignment.sharedpref.SharedPreferencesManager
import com.niveus.kiran.foodorderassignment.viewmodel.FoodViewModel

class HomePageActivity : AppCompatActivity(), FoodInsertToCartInterface, FoodCountAddUpdateInterface, FoodCountDeleteInterface {


    lateinit var viewModel:FoodViewModel
    lateinit var fooRV: RecyclerView
    lateinit var checkout: Button
    lateinit var username: TextView
    lateinit var logOut: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.username)
        logOut = findViewById(R.id.logOutBtn)
        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        if(sharedPreferencesManager.isLogin()!!){
           username.text ="Hello.." + sharedPreferencesManager.getName()
        }
        logOut.setOnClickListener{
            sharedPreferencesManager.clearSession()
            intent = Intent(this@HomePageActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        checkout = findViewById(R.id.checkOut)
        fooRV = findViewById(R.id.recyclerView)
        fooRV.isNestedScrollingEnabled = false


        fooRV.layoutManager = LinearLayoutManager(this)

        val fAdapter = FoodAdapter(this,this,this,this)
        fooRV.adapter = fAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FoodViewModel::class.java]


        viewModel.allFood.observe(this, Observer{
            list -> list?.let{
                if(it.isEmpty()|| it.equals(null)){
                    viewModel.insert(Food("Idly", "BreakFast", 50.0))
                    viewModel.insert(Food("vada", "BreakFast", 30.0))
                    viewModel.insert(Food("SouthIndian", "Meal", 150.0))
                    viewModel.insert(Food("NorthIndian", "Meal", 180.0))
                    viewModel.insert(Food("Gobi", "Starter", 60.0))
                    viewModel.insert(Food("Mushroom", "Starter", 80.0))
                    viewModel.insert(Food("IceScream", "Desert", 70.0))
                    viewModel.insert(Food("Pan", "Break", 20.0))
                    viewModel.insert(Food("Faluda", "Break", 40.0))
                }
            else {
                    fAdapter.updateList(it)
                    Log.d("Collections", it.size.toString())
            }
        }
        })
        checkout.setOnClickListener {
            intent = Intent(this@HomePageActivity,OrderDetailsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun insertFoodToCart(cart: Cart) {
        //viewModel.insert(Cart(cart.fName,cart.fCategory,cart.fQuantity,cart.fPrice))
        viewModel.insert(cart)
    }

    override fun onUpdateAddCount(cart: Cart) {
        viewModel.update(cart)
    }

    override fun onDeleteMinusClick(cart: Cart) {
        viewModel.delete(cart)
    }
}