package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.Data
import com.github.ringoame196_s_mcPlugin.ImageRenderer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapView
import org.bukkit.util.BlockIterator

class ImgMapManager() {
    fun place(player: Player, url: String): Boolean {
        val location = acquisitionBlockBeforeLookingAt(player) ?: return true
        val itemFrame = summonItemFrame(location) ?: return false
        val mapItem = makeMap(location.world ?: return false, url)
        itemFrame.setItem(mapItem)
        return true
    }

    private fun summonItemFrame(location: Location): ItemFrame? {
        val world = location.world
        // 額縁召喚
        val itemFrame: ItemFrame? = world?.spawn(location, ItemFrame::class.java)
        itemFrame?.let {
            // it.isVisible = false // 可視化するかどうか
            it.isInvulnerable = true
            it.isCustomNameVisible = true
            it.setGravity(false)
            it.scoreboardTags.add(Data.IMG_ITEM_FRAME_TAG)
        }
        return itemFrame
    }

    private fun acquisitionBlockBeforeLookingAt(player: Player): Location? {
        val blockIterator = BlockIterator(player, 5) // 最大5ブロック先まで視線を追跡
        var lastBlock: org.bukkit.block.Block? = null

        while (blockIterator.hasNext()) {
            val currentBlock = blockIterator.next()
            if (!currentBlock.type.isAir) {
                return lastBlock?.location // 1個手前のブロックを返す
            }
            lastBlock = currentBlock
        }
        return null // 見ているブロックが空気なら null
    }

    private fun makeMap(world: World, url: String): ItemStack {
        val map = ItemStack(Material.FILLED_MAP)
        val meta = map.itemMeta as MapMeta
        var mapView = Bukkit.createMap(world)
        mapView = pasteMap(mapView)
        mapView = pasteImage(mapView, url)
        meta.setDisplayName("画像データ")
        meta.mapView = mapView
        map.itemMeta = meta
        return map
    }

    private fun pasteImage(mapView: MapView, url: String): MapView {
        val downLoadImage = imgMapManager.downloadImage(url)
        mapView.addRenderer(ImageRenderer(downLoadImage))
        return mapView
    }
    private fun pasteMap(mapView: MapView): MapView {
        mapView.renderers.clear()
        mapView.scale = MapView.Scale.FARTHEST
        return mapView
    }
}
