package com.github.ringoame196_s_mcPlugin.directions

import org.bukkit.Location

class South : Direction {
    override fun addition(location: Location, changeValue: Int) {
        location.add(0.0, 0.0, changeValue.toDouble())
    }

    override fun reset(location: Location, changeValue: Int) {
        location.add(0.0, 0.0, -changeValue.toDouble())
    }
}
