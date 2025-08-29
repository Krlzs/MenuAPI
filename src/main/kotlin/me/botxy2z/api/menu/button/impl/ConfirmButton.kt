package me.botxy2z.api.menu.button.impl

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import me.botxy2z.api.utilities.ItemBuilder
import me.botxy2z.api.menu.Menu
import me.botxy2z.api.menu.button.Button
import me.botxy2z.api.menu.button.ButtonCallback

class ConfirmButton(
    private val confirm: Boolean,
    private val callback: ButtonCallback<Boolean>,
    private val closeAfterResponse: Boolean
) : Button() {

    override fun getButtonItem(player: Player): ItemStack {
        return ItemBuilder(Material.WOOL)
            .setName(if (confirm) "&a&lConfirm" else "&c&lCancel")
            .setDurability(if (confirm) 5 else 15)
            .build()
    }

    override fun clicked(player: Player, clickType: ClickType) {
        if (confirm) {
            playSound(player, Sound.LEVEL_UP, 5.0f, 3.0f)
        } else {
            playSound(player, Sound.DOOR_CLOSE, 5.0f, 3.0f)
        }

        if (closeAfterResponse) {
            Menu.currentlyOpenedMenus[player.uniqueId]?.setClosedByMenu(true)
            player.closeInventory()
        }

        callback.callback(confirm)
    }
}
