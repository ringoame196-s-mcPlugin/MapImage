package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.managers.ImgManager
import com.github.ringoame196_s_mcPlugin.managers.ImgMapManager
import com.github.ringoame196_s_mcPlugin.managers.MapManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.net.MalformedURLException
import java.net.URL

class Command(private val plugin: Plugin) : CommandExecutor, TabCompleter {
    private val mapManager = MapManager()
    private val imgMapManager = ImgMapManager()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            val message = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return true
        }
        if (args.isEmpty()) return false

        val subCommand = args[0]

        return when (subCommand) {
            CommandConst.MAKE_COMMAND -> makeCommand(sender, args)
            CommandConst.DELETE_COMMAND -> deleteCommand(sender)
            else -> {
                val message = "${ChatColor.RED}コマンド構文が間違っています"
                sender.sendMessage(message)
                return true
            }
        }
    }

    private fun makeCommand(sender: Player, args: Array<out String>): Boolean {
        if (args.size < 3) return false
        val playerLocation = imgMapManager.acquisitionBlockBeforeLookingAt(sender)?.clone() ?: return true
        val url = try { URL(args[1]) } catch (e: MalformedURLException) { return false }
        val cutCount = args[2].toIntOrNull() ?: return false
        val imgManager = ImgManager(url, plugin)

        val rightDirection = imgMapManager.acquisitionRightDirection(sender)
        val cutImgList = imgManager.splitImage(cutCount)

        var i = 0
        var c = 0
        var placeLocation = playerLocation

        for (cutImg in cutImgList) {
            if (placeLocation.block.type == Material.AIR && rightDirection.checkBlock(placeLocation)) {
                val mapID = mapManager.issueNewMap()
                val itemFrame = imgMapManager.summonItemFrame(placeLocation, mapID)
                imgMapManager.setImg(cutImg, mapID)
                imgManager.saveImg(cutImg, c.toString())
                imgManager.saveImgInfoDB(mapID, c.toString(), itemFrame?.uniqueId.toString())
                c ++
            }
            rightDirection.addition(placeLocation, 1)
            i ++
            if (i == cutCount) {
                i = 0
                placeLocation.add(0.0, -1.0, 0.0)
                rightDirection.reset(placeLocation, cutCount)
            }
        }
        val message = "${ChatColor.GOLD}${c}枚の画像貼り付け完了"
        val sound = Sound.BLOCK_ANVIL_USE
        sender.sendMessage(message)
        sender.playSound(sender, sound, 1f, 1f)

        return true
    }

    private fun deleteCommand(sender: Player): Boolean {
        val itemFrame = imgMapManager.acquisitionItemFrame(sender)
        if (itemFrame == null) {
            val message = "${ChatColor.RED}額縁の取得に失敗しました"
            sender.sendMessage(message)
            return true
        }
        val run = imgMapManager.delete(itemFrame.uniqueId.toString(), plugin)
        val message = if (run) "${ChatColor.RED}画像削除が正常に完了しました" else "${ChatColor.DARK_RED}画像削除中にエラーが発生しました"
        val sound = Sound.BLOCK_ANVIL_USE
        sender.sendMessage(message)
        sender.playSound(sender, sound, 1f, 1f)
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(CommandConst.MAKE_COMMAND, CommandConst.DELETE_COMMAND)
            2 -> when (args[0]) {
                CommandConst.MAKE_COMMAND -> mutableListOf("[画像のURL]")
                else -> mutableListOf()
            }
            3 -> when (args[0]) {
                CommandConst.MAKE_COMMAND -> mutableListOf("[横幅]")
                else -> mutableListOf()
            }
            else -> mutableListOf()
        }
    }
}
