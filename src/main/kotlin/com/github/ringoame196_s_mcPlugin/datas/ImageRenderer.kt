package com.github.ringoame196_s_mcPlugin.datas

import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import java.awt.image.BufferedImage

class ImageRenderer(private val image: BufferedImage?) : MapRenderer() {
    override fun render(view: MapView, canvas: MapCanvas, player: Player) {

        // 画像が正しくダウンロードされたらマップに描画
        if (image != null) {
            canvas.drawImage(0, 0, image)
        }
    }
}
