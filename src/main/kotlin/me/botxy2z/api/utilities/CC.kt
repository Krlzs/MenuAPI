package me.botxy2z.api.utilities

import org.bukkit.ChatColor

object CC {

    private val replacer: (String) -> String = { ChatColor.translateAlternateColorCodes('&', it) }

    val LINE: String = t("&7&m-------------------------")

    fun t(text: String): String {
        return replacer(text)
    }

    fun t(list: List<String>): List<String> {
        return list.map(replacer)
    }
}
