package com.github.ringoame196_s_mcPlugin.directions

import org.bukkit.Location
import org.bukkit.Material

class South : Direction {
    override fun addition(location: Location, changeValue: Int) {
        location.add(0.0, 0.0, changeValue.toDouble())
    }

    override fun reset(location: Location, changeValue: Int) {
        location.add(0.0, 0.0, -changeValue.toDouble())
    }

    override fun checkBlock(location: Location): Boolean {
        return location.clone().add(1.0, 0.0, 0.0).block.type != Material.AIR
    }
}
