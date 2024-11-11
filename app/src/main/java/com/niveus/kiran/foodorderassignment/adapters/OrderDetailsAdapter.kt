package com.niveus.kiran.foodorderassignment.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.niveus.kiran.foodorderassignment.R
import com.niveus.kiran.foodorderassignment.entities.Cart

class OrderDetailsAdapter(
    context: Context,
    val insertToCartInterface: InsertToCartInterface,
    val countDeleteInterface: CountDeleteInterface,
    val countAddUpdateInterface: CountAddUpdateInterface,val srt:String
) : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    private val context = context
    private val allCart = ArrayList<Cart>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val title = itemView.findViewById<TextView>(R.id.namec)
        val category = itemView.findViewById<TextView>(R.id.categoryc)
        val price = itemView.findViewById<TextView>(R.id.pricec)
        val minusItem = itemView.findViewById<ImageButton>(R.id.minusItemc)
        val quantity = itemView.findViewById<TextView>(R.id.quantityc)
        val addItem = itemView.findViewById<ImageButton>(R.id.addItemc)
        val amount = itemView.findViewById<TextView>(R.id.amountc)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_order_details,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderDetailsAdapter.ViewHolder, position: Int) {
        holder.title.text = allCart[position].fName
        holder.category.text = allCart[position].fCategory
        holder.price.text = allCart[position].fPrice.toString()
        holder.quantity.text = allCart[position].fQuantity.toString()
        holder.amount.text = (allCart[position].fPrice * allCart[position].fQuantity).toString()
        //holder.amount.text = "0"

        holder.addItem.setOnClickListener {
            var qq = holder.quantity.text.toString().toInt()
            val fname = holder.title.text.toString()
            val category = holder.category.text.toString()
            val quantity = holder.quantity.text.toString().toInt()
            val price = holder.price.text.toString().toDouble()
            val amount = price * quantity
            val cart = Cart(fname, category, quantity, price,amount)
            when (qq) {
                0 -> {
                    qq += 1
                    holder.amount.text = (allCart[position].fPrice * qq).toString()
                    holder.quantity.text = qq.toString()
                    insertToCartInterface.insertFoodToCart(allCart.get(position))
                    Log.d("Added",holder.quantity.text.toString())
                }

                1 -> {
                    qq += 1
                    holder.amount.text = (allCart[position].fPrice * qq).toString()
                    holder.quantity.text = qq.toString()
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        qq,
                        holder.price.text.toString().toDouble(),
                        holder.amount.text.toString().toDouble()
                    )
                    countAddUpdateInterface.onUpdateAddCount(updatess)
                    Log.d("Updated",holder.quantity.text.toString())
                }

                2 -> {
                    holder.amount.text = (allCart[position].fPrice * allCart[position].fQuantity).toString()
                    Toast.makeText(context, "Out of Stock", Toast.LENGTH_SHORT).show()
                }
            }
        }
        holder.delete.setOnClickListener {
            val fname = holder.title.text.toString()
            val category = holder.category.text.toString()
            val quantity = holder.quantity.text.toString().toInt()
            val price = holder.price.text.toString().toDouble()
            val amount = price * quantity
            val cart = Cart(fname, category, quantity, price,amount)
            countDeleteInterface.onDeleteMinusClick(allCart.get(position))
            Log.d("Delete",cart.toString())
        }

        holder.minusItem.setOnClickListener {
            var qq = holder.quantity.text.toString().toInt()
            val fname = holder.title.text.toString()
            val category = holder.category.text.toString()
            val quantity = holder.quantity.text.toString().toInt()
            val price = holder.price.text.toString().toDouble()
            var amount = price * quantity
            val cart = Cart(fname, category, quantity, price, amount)

            when (qq) {
                0 -> {
                    holder.amount.text = (allCart[position].fPrice * qq).toString()
                    //countDeleteInterface.onDeleteMinusClick(allCart.get(position))
                    Toast.makeText(context, "Please add Quantity", Toast.LENGTH_SHORT).show()
                }

                1 -> {
                    qq = 0
                    amount = allCart[position].fPrice * qq
                    holder.amount.text = amount.toString()
                    holder.quantity.text = qq.toString()
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        qq,
                        holder.price.text.toString().toDouble(),
                        amount
                    )
                    countDeleteInterface.onDeleteMinusClick(updatess)
                    //foodCountAddUpdateInterface.onUpdateAddCount(updatess)
                }

                2 -> {
                    qq = 1
                    amount = allCart[position].fPrice * qq
                    holder.amount.text = amount.toString()
                    holder.quantity.text = qq.toString()
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        qq,
                        holder.price.text.toString().toDouble(),
                        amount
                    )
                    countAddUpdateInterface.onUpdateAddCount(updatess)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return allCart.size
    }

    fun updateList(newList: List<Cart>) {
        allCart.clear()
        allCart.addAll(newList)
        notifyDataSetChanged()
    }
}

interface InsertToCartInterface {
    fun insertFoodToCart(cart: Cart)
}

interface CountDeleteInterface {
    fun onDeleteMinusClick(cart: Cart)
}

interface CountAddUpdateInterface {
    fun onUpdateAddCount(cart: Cart)
}

interface DeleteAllEntries {

    fun onUpdateAddCount(cart: Cart)
}