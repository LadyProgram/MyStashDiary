package com.ladyprogram.mystashdiary.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladyprogram.mystashdiary.R
import com.ladyprogram.mystashdiary.adapters.ElementAdapter
import com.ladyprogram.mystashdiary.data.Category
import com.ladyprogram.mystashdiary.data.Element
import com.ladyprogram.mystashdiary.data.ElementDAO
import com.ladyprogram.mystashdiary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        adapter = ElementAdapter(emptyList(), ::editElement, ::deleteElement)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addElementButton.setOnClickListener {
            val intent = Intent(this, ElementActivity::class.java)
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
        intent.putExtra(ElementActivity.TASK_ID, element.id)
        startActivity(intent)

        supportActionBar?.title = "My Stash Diary"
    }

    fun deleteElement(position: Int) {
        val element = elementList[position]

        AlertDialog.Builder(this)
            .setTitle(R.string.delete_element)
            .setMessage(R.string.delete_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                elementDAO.delete(element)
                refreshData()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setCancelable(false) //Si se pulsa fuera del diálogo no se cierra el mensaje
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Log.i("MENU", "He pulsado Enter")
                return false
            }


            override fun onQueryTextChange(s: String): Boolean {
                //Log.i("MENU", s)

                elementList = elementDAO.findAllByNameOrCreator(s)
                adapter.updateItems(elementList)
                return true
            }
        })

        return true
    }
}
        
        
        
        
        
        
        // Ejemplo para transformar enums
        val categoryNumber = 0
        val category = Category.entries[categoryNumber]
        val newCategoryNumber = category.ordinal
