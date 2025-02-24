package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta

class MapManager {
    fun issueNewMap(): Int {
        val map = ItemStack(Material.FILLED_MAP)
        val meta = map.itemMeta as MapMeta
        val mapView = Bukkit.createMap(Bukkit.getWorlds().first()) // 最初のワールドを取得
        meta.mapView = mapView // マップを関連付ける
        map.itemMeta = meta // メタデータを更新
        return meta.mapId
    }

    fun acquisitionMap(mapID: Int): ItemStack? {
        val mapView = Bukkit.getMap(mapID) ?: return null // マップIDからMapViewを取得
        val mapItem = ItemStack(Material.FILLED_MAP) // マップアイテムを作成
        val meta = mapItem.itemMeta as? MapMeta ?: return null // MapMetaを取得

        meta.mapView = mapView // 取得したMapViewをセット
        mapItem.itemMeta = meta

        return mapItem // マップアイテムを返す
    }
}
