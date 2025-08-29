package me.botxy2z.api.example

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.overpvp.mask.menu.MenuExample

class ExampleCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return false
        MenuExample.openMenu(sender)
        return true
    }
}
