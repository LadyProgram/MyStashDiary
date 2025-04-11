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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.ladyprogram.mystashdiary.R
import com.ladyprogram.mystashdiary.adapters.ElementAdapter
import com.ladyprogram.mystashdiary.data.Category
import com.ladyprogram.mystashdiary.data.Element
import com.ladyprogram.mystashdiary.data.ElementDAO
import com.ladyprogram.mystashdiary.data.State
import com.ladyprogram.mystashdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var elementDAO: ElementDAO
    lateinit var elementList: List<Element>
    lateinit var adapter: ElementAdapter

    var filterQuery = ""
    var filterState: State? = null
    var filterCategories: List<Category> = Category.entries


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

        binding.tabBar.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> filterState = null
                    1-> filterState = State.PLANNING
                    2 -> filterState = State.CONSUMING
                    3 -> filterState = State.COMPLETED
                }
                refreshData()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}

        })

        // Seleccionamos el tab consumiendo nada mas entrar
        //binding.tabBar.selectTab(binding.tabBar.getTabAt(2))

        binding.categoryFiltersChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            filterCategories = checkedIds.map {
                when(it) {
                    R.id.filterCategoryBook -> Category.BOOK
                    R.id.filterCategoryMovie -> Category.MOVIE
                    R.id.filterCategorySeries -> Category.SERIES
                    else -> Category.ANIME
                }
            }
            refreshData()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    fun refreshData() {
        if (filterState == null) {
            elementList = elementDAO.findAllByNameOrCreator(filterQuery)
        } else {
            elementList = elementDAO.findAllByNameOrCreatorAndStatusAndCategories(filterQuery, filterState!!, filterCategories)
        }
        adapter.updateItems(elementList)
    }


    fun editElement(position: Int) {
        val element = elementList[position]

        val intent = Intent(this, ElementActivity::class.java)
        intent.putExtra(ElementActivity.ELEMENT_ID, element.id)
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
                filterQuery = s
                refreshData()
                return true
            }
        })

        return true
    }

   /* @Composable
    fun Tab(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        text: (@Composable () -> Unit)? = null,
        icon: (@Composable () -> Unit)? = null,
        selectedContentColor: Color = LocalContentColor.current,
        unselectedContentColor: Color = selectedContentColor,
        interactionSource: MutableInteractionSource? = null
    ): Unit

    Tab(selected, onClick) {
        Column(
            Modifier.padding(10.dp).height(50.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier.size(10.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color =
                        if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.background
                    )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }*/
}
        
        
        
        
        
        
        // Ejemplo para transformar enums
        val categoryNumber = 0
        val category = Category.entries[categoryNumber]
        val newCategoryNumber = category.ordinal

        val stateNumber = 0
        val state = State.entries[stateNumber]
