package com.github.ringoame196_s_mcPlugin.directions

import org.bukkit.Location

class East : Direction {
    override fun addition(location: Location, changeValue: Int) {
        location.add(changeValue.toDouble(), 0.0, 0.0)
    }

    override fun reset(location: Location, changeValue: Int) {
        location.add(-changeValue.toDouble(), 0.0, 0.0)
    }
}
