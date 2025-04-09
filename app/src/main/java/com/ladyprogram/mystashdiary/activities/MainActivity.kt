package com.ladyprogram.mystashdiary.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladyprogram.mystashdiary.R
import com.ladyprogram.mystashdiary.adapters.ElementAdapter
import com.ladyprogram.mystashdiary.data.Category
import com.ladyprogram.mystashdiary.data.Element
import com.ladyprogram.mystashdiary.data.ElementDAO
import com.ladyprogram.mystashdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var elementDAO: ElementDAO
    lateinit var elementList: List<Element>
    lateinit var adapter: ElementAdapter
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //setContentView(R.layout.activity_main) RECORDAR BORRAR ESTE CÓDIGO CUANDO USAMOS BINDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "My Stash Diary"

        elementDAO = ElementDAO(this)

        //elementList = elementDAO.findAll()

        adapter = ElementAdapter(emptyList(),::editElement, ::deleteElement)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addElementButton.setOnClickListener{
            val intent = Intent(this,ElementActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    fun refreshData() {
        elementList = elementDAO.findAll()
        adapter.updateItems(elementList)
    }
    

    fun editElement(position: Int) {
        val element = elementList[position]

        val intent = Intent(this, ElementActivity::class.java)
        intent.putExtra(ElementActivity.TASK_ID,element.id)
        startActivity(intent)
    }

    fun deleteElement(position: Int) {
        val element = elementList[position]

        AlertDialog.Builder(this)
            .setTitle("Delete element")
            .setMessage("Are you sure you want to delete this element?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                elementDAO.delete(element)
                refreshData()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setCancelable(false) //Si se pulsa fuera del diálogo no se cierra el mensaje
            .show()
    }
}


        
        
        
        
        
        
        // Ejemplo para transformar enums
        val categoryNumber = 0
        val category = Category.entries[categoryNumber]
        val newCategoryNumber = category.ordinal
