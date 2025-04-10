package com.ladyprogram.mystashdiary.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ladyprogram.mystashdiary.R
import com.ladyprogram.mystashdiary.data.Category
import com.ladyprogram.mystashdiary.data.Element
import com.ladyprogram.mystashdiary.data.ElementDAO
import com.ladyprogram.mystashdiary.databinding.ActivityElementBinding


class ElementActivity : AppCompatActivity() {

    companion object {
        const val TASK_ID = "TASK_ID"
    }

    lateinit var binding: ActivityElementBinding
    lateinit var elementDAO: ElementDAO
    lateinit var element: Element
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityElementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //setContentView(R.layout.activity_element)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Create Spinner for Category choices
        val categories = Category.entries.map { getString(it.title) }.toMutableList().apply {
            add(0, getString(R.string.element_category_select))
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.setAdapter(adapter)



       val id = intent.getLongExtra(TASK_ID, -1L)
        //val categoryId = intent.getLongExtra(CATEGORY_ID, -1L)

        elementDAO = ElementDAO(this)

        if (id != -1L) {
            element = elementDAO.findById(id)!!
            binding.nameEditText.setText(element.name)
            binding.creatorEditText.setText(element.creator)
            binding.categorySpinner.setSelection(element.category.ordinal + 1)
        } else {
            element = Element(-1L, "","", Category.BOOK)
            supportActionBar?.title = "Crear elemento"
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val creator = binding.creatorEditText.text.toString()
            val category = binding.categorySpinner.selectedItemPosition

            if (category == 0) {
                // Selecciona una categoria
                return@setOnClickListener
            }

            element.name = name
            element.creator = creator
            element.category = Category.entries[category - 1]

            if (element.id != -1L) {
                elementDAO.update(element)
            } else {
                elementDAO.insert(element)
            }
            //val element = Element(-1L, title)


            finish()
        }
    }
}