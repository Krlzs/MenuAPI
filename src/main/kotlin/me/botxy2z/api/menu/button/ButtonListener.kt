package me.botxy2z.api.menu.button

import me.botxy2z.api.ExamplePlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import me.botxy2z.api.menu.Menu

class ButtonListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onButtonPress(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val openMenu = Menu.currentlyOpenedMenus[player.uniqueId] ?: return

        if (event.slot != event.rawSlot && (event.click == ClickType.SHIFT_LEFT || event.click == ClickType.SHIFT_RIGHT)) {
            event.isCancelled = true
            return
        }

        val button = openMenu.getButtons()[event.slot]
        if (button != null) {
            val cancel = button.shouldCancel(player, event.click)
            if (!cancel && (event.click == ClickType.SHIFT_LEFT || event.click == ClickType.SHIFT_RIGHT)) {
                event.isCancelled = true
                event.currentItem?.let { player.inventory.addItem(it) }
            } else {
                event.isCancelled = cancel
            }

            button.clicked(player, event.click)
            button.clicked(player, event.slot, event.click, event.hotbarButton)

            val newMenu = Menu.currentlyOpenedMenus[player.uniqueId]
            if (newMenu != null && newMenu === openMenu && button.shouldUpdate(player, event.click)) {
                openMenu.setClosedByMenu(true)
                newMenu.openMenu(player)
            } else if (button.shouldUpdate(player, event.click)) {
                openMenu.setClosedByMenu(true)
                openMenu.openMenu(player)
            }

            if (event.isCancelled) {
                Bukkit.getScheduler().runTaskLater(ExamplePlugin.getPlugin(), Runnable {
                    player.updateInventory()
                }, 1L)
            }

        } else {
            if (event.currentItem != null || event.click == ClickType.SHIFT_LEFT || event.click == ClickType.SHIFT_RIGHT) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player ?: return
        val openMenu = Menu.currentlyOpenedMenus[player.uniqueId] ?: return
        openMenu.onClose(player)
        Menu.currentlyOpenedMenus.remove(player.uniqueId)
    }
}
