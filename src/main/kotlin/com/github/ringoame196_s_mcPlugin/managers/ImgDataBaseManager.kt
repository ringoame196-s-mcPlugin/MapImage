package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.datas.Data
import com.github.ringoame196_s_mcPlugin.datas.ImgData

class ImgDataBaseManager {
    private val dataBaseManager = DataBaseManager(Data.DB_PATH)

    fun saveImg(imgData: ImgData) {
        val sql = "INSERT INTO ${Data.TABLE_NAME} (${Data.MAP_ID_KEY},${Data.GROUP_KEY},${Data.IMG_PATH_KEY},${Data.ITEM_FRAME_KEY}) VALUES (?,?,?,?)"
        dataBaseManager.executeUpdate(
            sql, mutableListOf(imgData.mapID, imgData.groupID, imgData.imgPath, imgData.itemFrameUUID)
        )
    }

    fun acquisitionGroup(itemFrameUUID: String): String {
        val sql = "SELECT ${Data.GROUP_KEY} FROM ${Data.TABLE_NAME} WHERE ${Data.ITEM_FRAME_KEY} = ?;"
        return dataBaseManager.acquisitionValue(sql, mutableListOf(itemFrameUUID), Data.GROUP_KEY).toString()
    }

    fun acquisitionGroupItemFrames(group: String): List<ImgData> {
        val sql = "SELECT * FROM ${Data.TABLE_NAME} WHERE ${Data.GROUP_KEY} = '$group';"
        return dataBaseManager.acquisitionImgDataValue(sql)
    }

    fun deleteGroupData(group: String, plugin: org.bukkit.plugin.Plugin): Boolean {
        val sql = "DELETE FROM ${Data.TABLE_NAME} WHERE ${Data.GROUP_KEY} = ?;"
        try {
            dataBaseManager.executeUpdate(sql, mutableListOf(group))
            return true
        } catch (e: Exception) {
            plugin.logger.info(e.message)
            return false
        }
    }
}
