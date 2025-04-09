package com.ladyprogram.mystashdiary.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ladyprogram.mystashdiary.R
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


       val id = intent.getLongExtra(TASK_ID, -1L)
        //val categoryId = intent.getLongExtra(CATEGORY_ID, -1L)

        elementDAO = ElementDAO(this)

        if (id != -1L) {
            element = elementDAO.findById(id)!!
            binding.nameEditText.setText(element.name)
        } else {
            element = Element(-1L, "","")
            supportActionBar?.title = "Crear tarea"
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            element.name = name

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