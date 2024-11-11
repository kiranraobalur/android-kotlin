package com.niveus.kiran.foodorderassignment.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niveus.kiran.foodorderassignment.R
import com.niveus.kiran.foodorderassignment.adapters.CountAddUpdateInterface
import com.niveus.kiran.foodorderassignment.adapters.CountDeleteInterface
import com.niveus.kiran.foodorderassignment.adapters.InsertToCartInterface
import com.niveus.kiran.foodorderassignment.adapters.OrderDetailsAdapter
import com.niveus.kiran.foodorderassignment.entities.Cart
import com.niveus.kiran.foodorderassignment.sharedpref.SharedPreferencesManager
import com.niveus.kiran.foodorderassignment.viewmodel.OderDetailsViewModel

class OrderDetailsActivity : AppCompatActivity(), InsertToCartInterface,
    CountAddUpdateInterface, CountDeleteInterface {

    lateinit var viewModels: OderDetailsViewModel
    lateinit var cartRecyclerView: RecyclerView
    lateinit var totalAmt:TextView
    lateinit var taxAmount:TextView
    lateinit var gTotal:TextView
    lateinit var tQuan:TextView
    lateinit var uName:TextView
    lateinit var placeOrder:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        uName = findViewById(R.id.titlename)
        placeOrder = findViewById(R.id.placeOrder)
        totalAmt = findViewById(R.id.aTotal)
        taxAmount = findViewById(R.id.tTotal)
        gTotal = findViewById(R.id.gTotal)
        tQuan = findViewById(R.id.tQuantity)
        cartRecyclerView = findViewById(R.id.recyclerViews)
        cartRecyclerView.isNestedScrollingEnabled = false

        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        if(sharedPreferencesManager.isLogin()!!){
            uName.text = "Hey! " + sharedPreferencesManager.getName() + ". This is your cart to Pleace Order"
        }

        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        val cAdapter = OrderDetailsAdapter(this, this, this, this, "")
        cartRecyclerView.adapter = cAdapter

        viewModels = OderDetailsViewModel(application)

        viewModels.allCart.observe(this, Observer {
            cAdapter.updateList(it)
            var amt = 0.00
            var tax = 0.00
            var grand = 0.00
            var tq = 0

            for (i in it){
                amt += (i.fPrice * i.fQuantity)
                tq += i.fQuantity
            }
            tax = ((amt/100) * 18)
            grand = amt + tax

            tQuan.text = tq.toString()
            totalAmt.text = amt.toFloat().toString()
            taxAmount.text = tax.toFloat().toString()
            gTotal.text = grand.toString()

        })
        placeOrder.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage("Your Order is placed. Thank You. ")
                .setTitle("Order status")
                .setPositiveButton("Done") { dialog, which ->
                    intent = Intent(this@OrderDetailsActivity,HomePageActivity::class.java)
                    startActivity(intent)
                    viewModels.nukeTable()
                }
            builder.setCancelable(true)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun insertFoodToCart(cart: Cart) {
        viewModels.insert(cart)
    }

    override fun onUpdateAddCount(cart: Cart) {
        viewModels.update(cart)
    }

    override fun onDeleteMinusClick(cart: Cart) {
        viewModels.delete(cart)
    }
}