package com.cookandroid.todolist.category.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cookandroid.todolist.R
import com.cookandroid.todolist.category.db.CategoryAdapter
import com.cookandroid.todolist.category.db.CategoryDatabase
import com.cookandroid.todolist.category.db.CategoryViewModel
import com.cookandroid.todolist.databinding.ActivityModiRomoveBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Modi_Romove : AppCompatActivity() {

    private lateinit var binding : ActivityModiRomoveBinding

    lateinit var viewmodel : CategoryViewModel

    var cateDB = CategoryDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_modi_romove)

        //뷰모델
        viewmodel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewmodel.getData()

        val position = intent.getIntExtra("position", 0)

        viewmodel.categoryList.observe(this, Observer {
            val categoryAdapter = CategoryAdapter(it)

            val cateItemName = categoryAdapter.getCateItem(position)
            binding.categoryTitle.setText(cateItemName)
        })

        binding.update.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                val result = cateDB.categoryDao().getAllData()
                val categoryEntity = result[position]
                categoryEntity.cateName = binding.categoryTitle.text.toString()

                cateDB.categoryDao().update(categoryEntity)

            }

            val intent = Intent(this, Content_Category::class.java)
            startActivity(intent)

        }

        binding.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val result = cateDB.categoryDao().getAllData()
                val categoryEntity = result[position]

                cateDB.categoryDao().delete(categoryEntity)

            }

            val intent = Intent(this, Content_Category::class.java)
            startActivity(intent)

        }


    }
}