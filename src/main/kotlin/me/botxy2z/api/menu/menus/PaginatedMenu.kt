package me.botxy2z.api.menu.menus

import org.bukkit.entity.Player
import me.botxy2z.api.menu.Menu
import me.botxy2z.api.menu.button.Button
import kotlin.math.ceil
import java.util.concurrent.atomic.AtomicInteger

abstract class PaginatedMenu : Menu() {

    private var page = 1

    override fun getTitle(player: Player): String {
        return "${getPrePaginatedTitle(player)} &r($page/${getPages(player)})"
    }

    fun modPage(player: Player, mod: Int) {
        page += mod
        setButtons(emptyMap())
        openMenu(player)
    }

    fun getPages(player: Player): Int {
        val count = getAllPagesButtons(player).size
        return if (count == 0) 1 else ceil(count.toDouble() / getMaxItemsPerPage(player).toDouble()).toInt()
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val minIndex = ((page - 1) * getMaxItemsPerPage(player)).toInt()
        var maxIndex = (page * getMaxItemsPerPage(player)).toInt() + 4
        val buttons = mutableMapOf<Int, Button>()
        val index = AtomicInteger(0)
        val numberToAdd = page * 10 + 1

        for ((_, value) in getAllPagesButtons(player)) {
            var ind = index.getAndIncrement()
            if (ind < minIndex || ind >= maxIndex) continue
            ind -= (getMaxItemsPerPage(player) * (page - 1)).toInt() - 10
            if (ind == 16) {
                index.set(if (page == 1) 9 else 9 + numberToAdd)
            }
            if (ind == 25) {
                index.set(if (page == 1) 18 else 18 + numberToAdd)
            }
            buttons[ind] = value
        }

        getGlobalButtons(player)?.forEach { (key, button) ->
            buttons[key] = button
        }

        return buttons
    }

    open fun getMaxItemsPerPage(player: Player): Int = 21

    open fun getGlobalButtons(player: Player): Map<Int, Button>? = null

    abstract fun getPrePaginatedTitle(player: Player): String

    abstract fun getAllPagesButtons(player: Player): Map<Int, Button>

    fun getPage(): Int = page
}
