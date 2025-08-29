package me.botxy2z.api.utilities

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta

class ItemBuilder {

    private val itemStack: ItemStack
    private val itemMeta: ItemMeta

    constructor(itemStack: ItemStack) {
        this.itemStack = itemStack
        this.itemMeta = itemStack.itemMeta ?: throw IllegalStateException("ItemStack has no ItemMeta")
    }

    constructor(material: Material, amount: Int = 1, durability: Short = 0) {
        this.itemStack = ItemStack(material, amount, durability.toShort())
        this.itemMeta = this.itemStack.itemMeta ?: throw IllegalStateException("Could not create ItemMeta for $material")
    }

    fun setDurability(durability: Int): ItemBuilder {
        itemStack.durability = durability.toShort()
        return this
    }

    fun setAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    fun type(material: Material): ItemBuilder {
        itemStack.type = material
        return this
    }

    fun addAmount(amount: Int): ItemBuilder {
        itemStack.amount += amount
        return this
    }

    fun setName(name: String): ItemBuilder {
        itemMeta.setDisplayName(CC.t(name))
        return this
    }

    fun setSkullOwner(owner: String): ItemBuilder {
        (itemMeta as? SkullMeta)?.owner = owner
        return this
    }

    fun setLore(lore: List<String>?): ItemBuilder {
        if (lore != null) {
            itemMeta.lore = CC.t(lore)
        }
        return this
    }

    fun color(color: Color): ItemBuilder {
        if (itemStack.type.name.contains("LEATHER")) {
            (itemMeta as? LeatherArmorMeta)?.setColor(color)
                ?: throw IllegalArgumentException("ItemMeta is not LeatherArmorMeta")
        } else {
            throw IllegalArgumentException("color() only applicable for leather armor")
        }
        return this
    }

    fun addLoreLine(line: String): ItemBuilder {
        val lore = itemMeta.lore?.toMutableList() ?: mutableListOf()
        lore.add(CC.t(line))
        itemMeta.lore = lore
        return this
    }

    fun addStoredEnchantment(enchant: Enchantment, level: Int): ItemBuilder {
        (itemMeta as? EnchantmentStorageMeta)?.addStoredEnchant(enchant, level, false)
        return this
    }

    fun addEnchantment(enchant: Enchantment, level: Int): ItemBuilder {
        itemStack.addEnchantment(enchant, level)
        return this
    }

    fun addUnsafeEnchantment(enchant: Enchantment, level: Int): ItemBuilder {
        itemStack.addUnsafeEnchantment(enchant, level)
        return this
    }

    fun addFakeGlow(): ItemBuilder {
        return this
    }

    fun getItemMeta(): ItemMeta = itemMeta

    fun name(name: String): ItemBuilder = setName(name)

    fun lore(vararg lore: String): ItemBuilder {
        itemMeta.lore = CC.t(lore.toList())
        return this
    }

    fun lore(lore: List<String>): ItemBuilder {
        itemMeta.lore = CC.t(lore)
        return this
    }

    fun data(dur: Int): ItemBuilder = durability(dur)

    fun durability(durability: Int): ItemBuilder = setDurability(durability)

    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}