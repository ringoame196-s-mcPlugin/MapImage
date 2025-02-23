package com.github.ringoame196_s_mcPlugin.directions

import org.bukkit.Location
import org.bukkit.Material

class East : Direction {
    override fun addition(location: Location, changeValue: Int) {
        location.add(changeValue.toDouble(), 0.0, 0.0)
    }

    override fun reset(location: Location, changeValue: Int) {
        location.add(-changeValue.toDouble(), 0.0, 0.0)
    }

    override fun checkBlock(location: Location): Boolean {
        return location.clone().add(0.0.toDouble(), 0.0, -1.0).block.type != Material.AIR
    }
}
