package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.datas.Data
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.event.player.PlayerInteractEntityEvent

class ItemFrameEvents : Listener {
    @EventHandler
    fun onBreak(e: HangingBreakEvent) {
        if (isImgItemFrame(e.entity)) e.isCancelled = true
    }

    @EventHandler
    fun onRightClick(e: PlayerInteractEntityEvent) {
        if (isImgItemFrame(e.rightClicked)) e.isCancelled = true
    }

    @EventHandler
    fun onLeftClick(e: EntityDamageEvent) {
        if (isImgItemFrame(e.entity)) e.isCancelled = true
    }

    private fun isImgItemFrame(entity: Entity): Boolean {
        return entity.scoreboardTags.contains(Data.IMG_ITEM_FRAME_TAG)
    }
}
