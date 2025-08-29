package me.botxy2z.api.example

import me.botxy2z.api.menu.button.Button
import me.botxy2z.api.utilities.CC
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import me.botxy2z.api.utilities.ItemBuilder

class ButtonExample(private val itemStack: ItemStack) : Button() {

    override fun clicked(player: Player, clickType: ClickType) {
        player.sendMessage(CC.t("&etest"))
    }

    override fun getButtonItem(player: Player): ItemStack {
        return itemStack
    }
}
