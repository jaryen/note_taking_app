package com.example.note_taking_app

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an initial list of notes
        val noteList = mutableListOf(
                Note("Example note 1", false),
                Note("Example note 2", false),
                Note("Example note 3", false),
        )

        // Fill recycler view with above list
        val adapter = NoteAdapter(noteList)
        val noteRecyclerView = findViewById<RecyclerView>(R.id.note_recycler_view)

        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAddNote).setOnClickListener {
            val title = findViewById<EditText>(R.id.new_note).text.toString()
            val note = Note(title, false)
            adapter.addNote(note)
            findViewById<EditText>(R.id.new_note).getText().clear()
            findViewById<EditText>(R.id.new_note).onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
        findViewById<Button>(R.id.btnDelNote).setOnClickListener {
            adapter.deleteNote()
        }

        // Define sensor manager and get list of sensors
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val listAccel = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER)

        val sensorListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

            override fun onSensorChanged(sensorEvent: SensorEvent?) {
                val values = sensorEvent?.values
                val x = values?.get(0)
                // var y = values?.get(1)
                val z = values?.get(2)

                // If acceleration > 5 in x and z axes,
                // delete the checked notes.
                if (x != null && z != null) {
                    if (x > 5 || z > 5) {
                        adapter.deleteNote()
                    }
                }
                // Displays current acceleration
                findViewById<TextView>(R.id.textView).setText("X=$x\nZ=$z\n")
            }
        }
        sensorManager.registerListener(sensorListener, listAccel.get(0), SensorManager.SENSOR_DELAY_NORMAL)
    }
}
