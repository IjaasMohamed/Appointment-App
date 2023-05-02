package com.example.appointment.database

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
}