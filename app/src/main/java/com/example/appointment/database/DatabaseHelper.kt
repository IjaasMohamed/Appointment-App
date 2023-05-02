package com.example.appointment.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appointment.model.AppointmentListModel

class DatabaseHelper (context : Context) : SQLiteOpenHelper (context , DB_NAME, null, DB_VERSION ) {

    companion object { //way to initialize a variable while creating a class
        private val DB_NAME = "appointment"
        private val DB_VERSION = 1
        private val TABLE_NAME = "appointmentlist"
        private val ID = "id"
        private val APPOINTMENT_NAME = "appointmentname"
        private val APPOINTMENT_DETAILS = "appointmentdetails"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY, $APPOINTMENT_NAME TEXT, $APPOINTMENT_DETAILS TEXT )"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    @SuppressLint("Range")
    fun getAllAppointment () : List <AppointmentListModel> {
        val appointmentlist = ArrayList<AppointmentListModel> ()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null) // data is held by cursor
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val appointments = AppointmentListModel()
                    appointments.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    appointments.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
                    appointments.details = cursor.getString(cursor.getColumnIndex(APPOINTMENT_DETAILS))
                    appointmentlist.add(appointments)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return appointmentlist
    }

    //insert
        fun addAppointment(appointments : AppointmentListModel) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(APPOINTMENT_NAME, appointments.name)
        values.put(APPOINTMENT_DETAILS,appointments.details)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success" ) != -1 )
    }
    //select
    @SuppressLint("Range")
    fun getAppointment(_id : Int) : AppointmentListModel{
        val appointments = AppointmentListModel() // object of task list model
        val db = writableDatabase // object of database
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        appointments.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        appointments.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
        appointments.details = cursor.getString(cursor.getColumnIndex(APPOINTMENT_DETAILS))
        cursor.close()
        return appointments
    }
    //delete
    fun deleteAppointment(_id : Int) : Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?" + arrayOf(_id.toString())).toLong()
        return Integer.parseInt("$_success") != -1
    }
    //update
    fun updateAppointment(appointments: AppointmentListModel) : Boolean{
        val db = this.writableDatabase //object creation of database
        val values = ContentValues()
        values.put(APPOINTMENT_NAME, appointments.name)
        values.put(APPOINTMENT_DETAILS, appointments.details)
        val _success = db.update(TABLE_NAME, values, ID + "=?" + arrayOf(appointments.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}