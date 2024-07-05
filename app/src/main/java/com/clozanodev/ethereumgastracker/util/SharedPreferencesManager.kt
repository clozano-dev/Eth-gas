package com.clozanodev.ethereumgastracker.util

import android.content.Context
import androidx.core.content.edit

object SharedPreferencesManager {
    private const val PREFS_NAME = "gas_prefs"
    private const val PREFS_KEY_LOWER_LIMIT = "lower_limit"
    private const val PREFS_KEY_UPPER_LIMIT = "upper_limit"

    fun saveUpperLimit(context: Context, upperLimit: Int){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit{
            putInt(PREFS_KEY_UPPER_LIMIT, upperLimit)
        }
    }

    fun saveLowerLimit(context: Context, lowerLimit: Int){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit{
            putInt(PREFS_KEY_LOWER_LIMIT, lowerLimit)
        }
    }

    fun getLowerLimit(context: Context): Int{
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(PREFS_KEY_LOWER_LIMIT, 0)
    }

    fun getUpperLimit(context: Context): Int{
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(PREFS_KEY_UPPER_LIMIT, 0)
    }

    fun clearUpperLimit(context: Context){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit{
            remove(PREFS_KEY_UPPER_LIMIT)
        }
    }

    fun clearLowerLimit(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            remove(PREFS_KEY_LOWER_LIMIT)
        }
    }
}