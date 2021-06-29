package com.shubhamvashishth.sudokusolver.algo

import android.content.Context
import com.google.gson.Gson

import android.content.SharedPreferences
import java.lang.reflect.Type
import android.content.Context.MODE_PRIVATE
import com.shubhamvashishth.sudokusolver.boardview.useClass
import com.google.gson.reflect.TypeToken
import com.shubhamvashishth.sudokusolver.boardview.Cell



fun saveObjectToSharedPreference(
    context: Context,
    preferenceFileName: String?,
    serializedObjectKey: String?,
    `object`: Any?
) {
    val mPrefs: SharedPreferences = context.getSharedPreferences(preferenceFileName,0)
    val prefsEditor: SharedPreferences.Editor = mPrefs.edit()
    val gson = Gson()
    val json = gson.toJson(`object`)
    prefsEditor.putString(serializedObjectKey, json)
    prefsEditor.commit()

}


fun getSavedObjectFromPreference(context: Context,
preferenceFileName: String?,
serializedObjectKey: String?,
`object`: Any
){
    val mPrefs: SharedPreferences = context.getSharedPreferences(preferenceFileName,0)
    val gson = Gson()
    var json = mPrefs.getString(serializedObjectKey, "")
    val type = object : TypeToken<List<Cell?>?>() {}.type

    useClass.sugrid = gson.fromJson(json, type)

    json = mPrefs.getString("time", "")
    useClass.timeins= gson.fromJson(json, useClass.timeins.javaClass )

    json = mPrefs.getString("qgrid", "")
    useClass.qgrid=gson.fromJson(json, type)

    json = mPrefs.getString("ugrid", "")
    useClass.ugrid= gson.fromJson(json, type)




}

