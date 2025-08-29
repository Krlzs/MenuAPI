package org.overpvp.mask.menu

import me.botxy2z.api.menu.Menu
import me.botxy2z.api.menu.button.Button
import me.botxy2z.api.utilities.CC
import me.botxy2z.api.utilities.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

object MenuExample : Menu() {

    override fun getTitle(player: Player): String = "&8Example Menu"

    override fun getSize(): Int {
        return 9 * 3
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[11] = object : Button() {
            override fun getButtonItem(player: Player) =
                ItemBuilder(Material.BLAZE_POWDER)
                    .setName("&aTest Button")
                    .setLore(
                        CC.t(
                            listOf(
                                "",
                                "&7line1",
                                ""
                            )
                        ))
                    .build()

            override fun clicked(player: Player, clickType: org.bukkit.event.inventory.ClickType) {
                // Here is the function when you click the button, for example an openMenu.
            }
        }

        buttons[15] = object : Button() {
            override fun getButtonItem(player: Player) =
                ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setName("&aTest Button 2")
                    .setLore(CC.t(listOf("&7test")))
                    .build()
        }

        return buttons
    }
}
