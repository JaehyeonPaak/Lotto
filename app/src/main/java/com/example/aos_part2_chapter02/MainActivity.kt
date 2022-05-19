package com.example.aos_part2_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.contains
import androidx.core.view.isVisible
import androidx.core.view.size

class MainActivity : AppCompatActivity() {

    private val numberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val addNumberButton : Button by lazy {
        findViewById<Button>(R.id.addNumberButton)
    }

    private val resetButton : Button by lazy {
        findViewById<Button>(R.id.resetButton)
    }
    private val runButton : Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private var didRun = false

    private val numberTextViewList : List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.numberTextView1),
            findViewById(R.id.numberTextView2),
            findViewById(R.id.numberTextView3),
            findViewById(R.id.numberTextView4),
            findViewById(R.id.numberTextView5),
            findViewById(R.id.numberTextView6)
        )
    }

    private var numberPickerList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initAddNumberButton()
        initResetButton()
        initRunButton()
    }

    private fun initAddNumberButton() {
        addNumberButton.setOnClickListener {
            //if auto creation is already done...
            if(didRun) {
                Toast.makeText(this, "Try after reset the numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //process of duplicate values...
            if(numberPickerList.contains(numberPicker.value)) {
                Toast.makeText(this, "There is a duplicate value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //if the size of numberPickerList is greater than 5...
            if(numberPickerList.size > 4) {
                Toast.makeText(this, "You cannot add more", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var index = numberPickerList.size
            numberTextViewList[index].text = numberPicker.value.toString()
            numberTextViewList[index].isVisible = true

            numberPickerList.add(numberPicker.value)
            Log.d("List size", index.toString())
        }
    }

    private fun initResetButton() {
        resetButton.setOnClickListener {
            didRun = false
            numberPickerList.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber() //list that contains Int elements...
            Log.d("Random Number List", list.toString())
            didRun = true
            list.forEachIndexed { index, i ->
                val textView = numberTextViewList[index]
                textView.text = i.toString()
                textView.isVisible = true
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberlist = mutableListOf<Int>()
        if(numberPickerList.size > 5) {
            numberPickerList.clear()
        }
        for (i in 1..45) {
            if(numberPickerList.contains(i)) {
                continue
            }
            numberlist.add(i)
        }
        numberlist.shuffle()
        return numberPickerList.toList() + numberlist.subList(0, 6 - numberPickerList.size).sorted()
    }

}
