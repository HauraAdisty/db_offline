package com.haura.db_offline

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.haura.db_offline.helper.DBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var btnSumbit: Button
    private lateinit var btnShowData: Button
    private lateinit var enterAge: EditText
    private lateinit var enterName: EditText
    private lateinit var name: TextView
    private lateinit var age: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnSumbit = findViewById(R.id.btnSubmit)
        btnShowData = findViewById(R.id.btnShowData)
        enterAge = findViewById(R.id.etUmur)
        enterName = findViewById(R.id.etNama)
        name = findViewById(R.id.Name)
        age = findViewById(R.id.Age)

        btnSumbit.setOnClickListener {
            val db = DBHelper(this, null)
            val nameInput = enterName.text.toString()
            val ageInput = enterAge.text.toString()

            db.addName(nameInput, ageInput)

            Toast.makeText(this, "$nameInput added to database", Toast.LENGTH_LONG).show()

            enterName.text.clear()
            enterAge.text.clear()
        }

        btnShowData.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getName()

            val nameBuilder = StringBuilder()
            val ageBuilder = StringBuilder()

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COl))
                    val age = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AGE_COL))

                    nameBuilder.append(name).append("\n")
                    ageBuilder.append(age).append("\n")
                } while (cursor.moveToNext())

                name.text = nameBuilder.toString()
                age.text = ageBuilder.toString()
            }

            cursor?.close()

        }
    }
}