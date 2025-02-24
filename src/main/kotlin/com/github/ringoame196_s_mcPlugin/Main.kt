package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.datas.Data
import com.github.ringoame196_s_mcPlugin.events.ItemFrameEvents
import com.github.ringoame196_s_mcPlugin.managers.ImgMapManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this

    override fun onEnable() {
        super.onEnable()
        if (!plugin.dataFolder.exists()) {
            // プラグインのフォルダー作成
            plugin.dataFolder.mkdirs()
        }
        saveResource("img.db", false) // db保存
        Data.DB_PATH = "${plugin.dataFolder.path}/img.db"

        server.pluginManager.registerEvents(ItemFrameEvents(), plugin)
        // コマンド
        val command = getCommand("mapimage")
        command!!.setExecutor(Command(plugin))

        // 画像を読み込む
        val imgMapManager = ImgMapManager()
        imgMapManager.loadImg(plugin)
    }
}
