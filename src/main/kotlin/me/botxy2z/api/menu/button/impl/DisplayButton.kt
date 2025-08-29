package me.botxy2z.api.menu.button.impl

import org.bukkit.Material import org.bukkit.entity.Player import org.bukkit.event.inventory.ClickType import org.bukkit.inventory.ItemStack import me.botxy2z.api.menu.button.Button

class DisplayButton( private val itemStack: ItemStack, private val cancel: Boolean ) : Button() {

    override fun getButtonItem(player: Player): ItemStack { return itemStack ?: ItemStack(Material.AIR) }

    override fun shouldCancel(player: Player, clickType: ClickType): Boolean { return cancel } }