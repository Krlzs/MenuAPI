package me.botxy2z.api.menu.button

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

abstract class Button {

    companion object {
        fun fromItem(item: ItemStack): Button {
            return object : Button() {
                override fun getButtonItem(player: Player): ItemStack = item
            }
        }

        fun fromItem(item: ItemStack, consumer: ((Player) -> Unit)?): Button {
            return object : Button() {
                override fun getButtonItem(player: Player): ItemStack = item

                override fun clicked(player: Player, clickType: ClickType) {
                    consumer?.invoke(player)
                }
            }
        }

        fun playFail(player: Player) {
            player.playSound(player.location, Sound.DIG_GRASS, 20f, 0.1f)
        }

        fun playSuccess(player: Player) {
            player.playSound(player.location, Sound.NOTE_PIANO, 20f, 15f)
        }

        fun playNeutral(player: Player) {
            player.playSound(player.location, Sound.CLICK, 20f, 1f)
        }
    }

    fun playSound(player: Player, sound: Sound, volume: Float, pitch: Float) {
        player.playSound(player.location, sound, volume, pitch)
    }

    abstract fun getButtonItem(player: Player): ItemStack

    open fun clicked(player: Player, clickType: ClickType) {}

    open fun clicked(player: Player, slot: Int, clickType: ClickType, hotbarSlot: Int) {}

    open fun shouldCancel(player: Player, clickType: ClickType): Boolean = true

    open fun shouldUpdate(player: Player, clickType: ClickType): Boolean = false
}
