package com.example.appointment.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context : Context) : SQLiteOpenHelper (context , DB_NAME, null, DB_VERSION ) {

    companion object { //way to initialize a variable while creating a class
        private val DB_NAME = "appointment"
        private val DB_VERSION = 1
        private val TABLE_NAME = "appointmentlist"
        private val ID = "id"
        private val APPOINTMENT_NAME = "appointmentname"
        private val APPOINTMENT_DETAILS = "appointmentdetails"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY, $APPOINTMENT_NAME TEXT, $APPOINTMENT_DETAILS TEXT )"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}