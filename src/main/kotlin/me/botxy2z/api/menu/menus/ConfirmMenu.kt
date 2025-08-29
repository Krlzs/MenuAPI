package me.botxy2z.api.menu.menus

import org.bukkit.entity.Player
import me.botxy2z.api.menu.Menu
import me.botxy2z.api.menu.button.Button
import me.botxy2z.api.menu.button.ButtonCallback
import me.botxy2z.api.menu.button.impl.ConfirmButton

class ConfirmMenu(
    private val title: String,
    private val response: ButtonCallback<Boolean>,
    private val closeAfterResponse: Boolean,
    private val centerButton: Button? = null
) : Menu() {

    override fun getTitle(player: Player): String {
        return title
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        buttons[getSlot(1, 0)] = ConfirmButton(true, response, closeAfterResponse)
        buttons[getSlot(7, 0)] = ConfirmButton(false, response, closeAfterResponse)
        centerButton?.let { buttons[getSlot(4, 0)] = it }
        return buttons
    }
}
