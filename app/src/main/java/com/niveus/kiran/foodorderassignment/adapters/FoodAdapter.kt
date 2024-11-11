package com.niveus.kiran.foodorderassignment.adapters

import android.content.Context
import android.provider.DocumentsContract.Root
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
import com.niveus.kiran.foodorderassignment.entities.Food

class FoodAdapter(
    context: Context,
    val foodInsertToCartInterface: FoodInsertToCartInterface,
    val foodCountDeleteInterface: FoodCountDeleteInterface,
    val foodCountAddUpdateInterface: FoodCountAddUpdateInterface
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private val context = context
    private val allFood = ArrayList<Food>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val title = itemView.findViewById<TextView>(R.id.name)
        val category = itemView.findViewById<TextView>(R.id.category)
        val price = itemView.findViewById<TextView>(R.id.price)
        val minusItem = itemView.findViewById<ImageButton>(R.id.minusItem)
        val quantity = itemView.findViewById<TextView>(R.id.quantity)
        val addItem = itemView.findViewById<ImageButton>(R.id.addItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.food_item_list,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {
        holder.title.text = allFood[position].fName
        holder.category.text = allFood[position].fCategory
        holder.price.text = allFood[position].fPrice.toString()
        holder.quantity.text = "0"
        val id = allFood[position].id

        holder.addItem.setOnClickListener {
            var qq = holder.quantity.text.toString().toInt()
            val fname = holder.title.text.toString()
            val category = holder.category.text.toString()
            val quantity = holder.quantity.text.toString().toInt()
            val price = holder.price.text.toString().toDouble()
            var amount = (price * quantity)
            val cart = Cart(fname, category, quantity, price,amount)
            when (qq) {
                0 -> {
                    qq += 1
                    holder.quantity.text = qq.toString()
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        holder.quantity.text.toString().toInt(),
                        holder.price.text.toString().toDouble(),
                        amount
                    )
                    updatess.id = allFood[position].id
                    foodInsertToCartInterface.insertFoodToCart(updatess)
                }

                1 -> {
                    qq += 1
                    holder.quantity.text = qq.toString()
                    amount = holder.quantity.text.toString().toInt() * holder.price.text.toString().toDouble()
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        holder.quantity.text.toString().toInt(),
                        holder.price.text.toString().toDouble(),
                        amount
                    )
                    updatess.id = allFood[position].id
                    foodCountAddUpdateInterface.onUpdateAddCount(updatess)
                }

                2 -> {
                    Toast.makeText(context, "Out of Stock", Toast.LENGTH_SHORT).show()
                }
            }
        }

        holder.minusItem.setOnClickListener {
            var qq = holder.quantity.text.toString().toInt()
            val fname = holder.title.text.toString()
            val category = holder.category.text.toString()
            val quantity = holder.quantity.text.toString().toInt()
            val price = holder.price.text.toString().toDouble()
            var amount = holder.quantity.text.toString().toInt() * holder.price.text.toString().toDouble()
            val cart = Cart(fname, category, quantity, price,amount)

            when (qq) {
                0 -> {
                    Toast.makeText(context, "Please add Quantity", Toast.LENGTH_SHORT).show()
                }

                1 -> {
                    qq = 0
                    holder.quantity.text = qq.toString()
                    amount = price * qq
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        qq,
                        holder.price.text.toString().toDouble(),amount
                    )
                    updatess.id = allFood[position].id
                    foodCountDeleteInterface.onDeleteMinusClick(updatess)

                }

                2 -> {
                    qq = 1
                    holder.quantity.text = qq.toString()
                    amount = price * qq
                    val updatess = Cart(
                        holder.title.text.toString(),
                        holder.category.text.toString(),
                        holder.quantity.text.toString().toInt(),
                        holder.price.text.toString().toDouble(),amount
                    )
                    updatess.id = allFood[position].id
                    foodCountAddUpdateInterface.onUpdateAddCount(updatess)
                }

            }
        }
    }

        override fun getItemCount(): Int {
            return allFood.size
        }

        fun updateList(newList: List<Food>) {
            allFood.clear()
            allFood.addAll(newList)
            notifyDataSetChanged()
        }
    }

    interface FoodInsertToCartInterface {
        fun insertFoodToCart(cart: Cart)
    }

    interface FoodCountDeleteInterface {
        fun onDeleteMinusClick(cart: Cart)
    }

    interface FoodCountAddUpdateInterface {
        fun onUpdateAddCount(cart: Cart)
    }