package org.sherman.tony.nexttrain.adapters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.sherman.tony.nexttrain.data.*
import org.sherman.tony.nexttrain.models.StationList
import java.util.*
import kotlin.collections.ArrayList


class dBHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        //SQL - Structured Query Language
        db?.execSQL(CREATE_STATION_TABLE)
        loadDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        //create table again
        onCreate(db)
    }

    private fun loadDatabase(db: SQLiteDatabase?){
        //var db: SQLiteDatabase = writableDatabase
        var insertRecord: ContentValues = ContentValues()

        for (i in 0..STATION_LIST.size -1){
            insertRecord.put(KEY_STATION, STATION_LIST[i])
            insertRecord.put(KEY_RECENT,0L)
            insertRecord.put(KEY_FAVORITE,0)
            db!!.insert(TABLE_NAME, null, insertRecord)
        }
    }

    //CRUD
    fun createStation() {
        return // This is never going to be used
    }

    fun readStation() {
        return // I do not expect this to be used in the initial version
    }

    fun readAllStations(): ArrayList<StationList> {
            var db: SQLiteDatabase = readableDatabase
            var list: ArrayList<StationList> = ArrayList()
            var selectAll = "SELECT * FROM "+ TABLE_NAME
            var cursor: Cursor = db.rawQuery(selectAll, null)

            // loop through stations
            if(cursor.moveToFirst()) {
                do {
                    var station = StationList()

                    station.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    station.station = cursor.getString(cursor.getColumnIndex(KEY_STATION))
                    station.favorite = cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE))
                    station.recent = cursor.getLong(cursor.getColumnIndex(KEY_RECENT))

                    list.add(station)
                } while (cursor.moveToNext())
            }
            //db.close()
            return list
    }

    fun updateStation() {
        return // Not needed in the first version
    }

    fun deleteStation() {
        return // Not needed in the first version
    }

    fun getStatusCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM "+ TABLE_NAME
        var cursor: Cursor = db.rawQuery(countQuery, null)

        //db.close()
        return cursor.count
    }

    fun getRecord(stationName: String): StationList{
        var startList = StationList()

        var db: SQLiteDatabase = readableDatabase
        var query: String = "SELECT * FROM "+ TABLE_NAME+" WHERE "+ KEY_STATION+"='"+stationName+"'"
        var cursor: Cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        startList.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        startList.station = cursor.getString(cursor.getColumnIndex(KEY_STATION))
        startList.favorite = cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE))
        startList.recent = cursor.getLong(cursor.getColumnIndex(KEY_RECENT))

        return startList
    }

    fun touchRecord(stationName: String): StationList{
        var startList = StationList()

        // Get the record with the station name
        var db: SQLiteDatabase = writableDatabase
        var query: String = "SELECT * FROM "+ TABLE_NAME+" WHERE "+ KEY_STATION+"='"+stationName+"'"
        var cursor: Cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        startList.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        startList.station = cursor.getString(cursor.getColumnIndex(KEY_STATION))
        startList.favorite = cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE))
        startList.recent = cursor.getLong(cursor.getColumnIndex(KEY_RECENT))

        // Set recent to now
        var date: Calendar = Calendar.getInstance()
        startList.recent = date.timeInMillis

        // Now write back to the database
        var values: ContentValues = ContentValues()
        values.put(KEY_RECENT, startList.recent)
        values.put(KEY_FAVORITE,startList.favorite)
        values.put(KEY_STATION,startList.station)
        db.update(TABLE_NAME,values, KEY_ID+"=?", arrayOf(startList.id.toString()))

        return startList
    }

    fun getRecent(): ArrayList<StationList>{
        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<StationList> = ArrayList()

        // Calculate the time for 30 days ago
        val calendarOffset = -30 // 30 days ago
        var date: Calendar = Calendar.getInstance()
        date.add(Calendar.DATE, calendarOffset)
        val recentValue = date.timeInMillis

        // Generate the query
        val query = "SELECT * FROM "+ TABLE_NAME+" WHERE "+ KEY_RECENT+" >"+recentValue
        println("QUERY IS: $query")

        var cursor:Cursor = db.rawQuery(query,null)

        //Build list
        if(cursor.moveToFirst()){
            do {
                var station = StationList()

                station.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                station.station = cursor.getString(cursor.getColumnIndex(KEY_STATION))
                station.favorite = cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE))
                station.recent = cursor.getLong(cursor.getColumnIndex(KEY_RECENT))

                list.add(station)
            }while(cursor.moveToNext())
        }

        return list
    }
}