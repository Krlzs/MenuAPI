package me.botxy2z.api.menu

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class MenuUpdater : Runnable {
    override fun run() {
        Menu.currentlyOpenedMenus.forEach { (uuid, menu) ->
            val player = Bukkit.getPlayer(uuid)
            if (player != null && menu.isAutoUpdate()) {
                menu.openMenu(player)
            }
        }
    }
}
