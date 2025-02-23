package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.managers.ImgManager
import com.github.ringoame196_s_mcPlugin.managers.ImgMapManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class Command(private val plugin: Plugin) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) return false
        if (sender !is Player) {
            val message = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return true
        }
        val imgMapManager = ImgMapManager()
        val playerLocation = imgMapManager.acquisitionBlockBeforeLookingAt(sender)?.clone() ?: return true
        val url = args[0]
        val cutCount = args[1].toIntOrNull() ?: return false
        val imgManager = ImgManager(url, plugin)

        val rightDirection = imgMapManager.acquisitionRightDirection(sender)
        val cutImgList = imgManager.splitImage(cutCount)

        var i = 0
        var placeLocation = playerLocation

        for (cutImg in cutImgList) {
            if (placeLocation.block.type == Material.AIR && rightDirection.checkBlock(placeLocation)) {
                imgMapManager.place(placeLocation, cutImg)
            }
            rightDirection.addition(placeLocation, 1)
            i ++
            if (i == cutCount) {
                i = 0
                placeLocation.add(0.0, -1.0, 0.0)
                rightDirection.reset(placeLocation, cutCount)
            }
        }

        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf("[画像のURL]")
            2 -> mutableListOf("[横幅]")
            else -> mutableListOf()
        }
    }
}
