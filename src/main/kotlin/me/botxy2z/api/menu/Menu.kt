package me.botxy2z.api.menu

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import me.botxy2z.api.utilities.ItemBuilder
import me.botxy2z.api.menu.button.Button
import me.botxy2z.api.menu.button.impl.DisplayButton
import me.botxy2z.api.utilities.CC
import java.util.*
import kotlin.math.ceil

abstract class Menu {

    companion object {
        val currentlyOpenedMenus: MutableMap<UUID, Menu> = mutableMapOf()
    }

    private var buttons: MutableMap<Int, Button> = mutableMapOf()
    private var autoUpdate = false
    private var updateAfterClick = true
    private var closedByMenu = false
    private var placeholder = false
    private var placeholderButton: Button = DisplayButton(
        ItemBuilder(Material.STAINED_GLASS_PANE).setDurability(0).setName("&0&l").build(),
        true
    )

    private fun createItemStack(player: Player, button: Button): ItemStack {
        val itemStack = button.getButtonItem(player)
        if (itemStack.type != Material.SKULL_ITEM) {
            val meta = itemStack.itemMeta
            if (meta != null && meta.hasDisplayName()) {
                meta.setDisplayName(meta.displayName + "§b§c§d§e")
                itemStack.itemMeta = meta
            }
        }
        return itemStack
    }

    fun openMenu(player: Player) {
        buttons = getButtons(player).toMutableMap()
        val previousMenu = currentlyOpenedMenus[player.uniqueId]
        var inventory: Inventory? = null

        val size = if (getSize() == -1) size(buttons) else getSize()
        var update = false
        var title = CC.t(getTitle(player))
        if (title.length > 32) {
            title = title.substring(0, 32)
        }

        if (player.openInventory != null) {
            if (previousMenu == null) {
                player.closeInventory()
            } else {
                val previousSize = player.openInventory.topInventory.size
                if (previousSize == size && player.openInventory.topInventory.title == title) {
                    inventory = player.openInventory.topInventory
                    update = true
                } else {
                    previousMenu.closedByMenu = true
                    player.closeInventory()
                }
            }
        }

        if (inventory == null) {
            inventory = Bukkit.createInventory(player as InventoryHolder, size, title)
        }

        currentlyOpenedMenus[player.uniqueId] = this

        for ((slot, button) in buttons) {
            buttons[slot] = button
            inventory!!.setItem(slot, createItemStack(player, button))
        }

        if (placeholder) {
            for (slot in 0 until size) {
                if (buttons.containsKey(slot)) continue
                buttons[slot] = placeholderButton
                inventory!!.setItem(slot, placeholderButton.getButtonItem(player))
            }
        }

        if (update) {
            player.updateInventory()
        } else {
            player.openInventory(inventory)
        }

        onOpen(player)
        closedByMenu = false
    }

    fun size(buttons: Map<Int, Button>): Int {
        val highest = buttons.keys.maxOrNull() ?: 0
        return ceil((highest + 1) / 9.0).toInt() * 9
    }

    open fun getSize(): Int = -1

    fun getSlot(x: Int, y: Int): Int = 9 * y + x

    abstract fun getTitle(player: Player): String

    abstract fun getButtons(player: Player): Map<Int, Button>

    open fun onOpen(player: Player) {}

    open fun onClose(player: Player) {}

    fun getButtons(): Map<Int, Button> = buttons

    fun isAutoUpdate(): Boolean = autoUpdate

    fun isUpdateAfterClick(): Boolean = updateAfterClick

    fun isClosedByMenu(): Boolean = closedByMenu

    fun isPlaceholder(): Boolean = placeholder

    fun getPlaceholderButton(): Button = placeholderButton

    fun setButtons(buttons: Map<Int, Button>) {
        this.buttons = buttons.toMutableMap()
    }

    fun setAutoUpdate(autoUpdate: Boolean) {
        this.autoUpdate = autoUpdate
    }

    fun setUpdateAfterClick(updateAfterClick: Boolean) {
        this.updateAfterClick = updateAfterClick
    }

    fun setClosedByMenu(closedByMenu: Boolean) {
        this.closedByMenu = closedByMenu
    }

    fun setPlaceholder(placeholder: Boolean) {
        this.placeholder = placeholder
    }

    fun setPlaceholderButton(placeholderButton: Button) {
        this.placeholderButton = placeholderButton
    }
}
