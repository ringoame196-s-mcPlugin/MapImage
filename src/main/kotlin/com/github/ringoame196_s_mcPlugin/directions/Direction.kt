package com.github.ringoame196_s_mcPlugin.directions

import org.bukkit.Location

interface Direction {
    fun addition(location: Location, changeValue: Int)
    fun reset(location: Location, changeValue: Int)
}
