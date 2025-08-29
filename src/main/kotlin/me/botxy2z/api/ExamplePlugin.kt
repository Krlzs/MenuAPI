package me.botxy2z.api

import me.botxy2z.api.example.ExampleCommand
import me.botxy2z.api.menu.button.ButtonListener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class ExamplePlugin : JavaPlugin() {

    companion object {
        lateinit var instance: ExamplePlugin
        fun getPlugin(): ExamplePlugin = instance
    }

    override fun onEnable() {
        instance = this
        getCommand("example")?.setExecutor(ExampleCommand())
        server.pluginManager.registerEvents(ButtonListener(), this) // In case it allows you to take items from the inventory, this method will make you no longer able to.
    }

    override fun onDisable() {}
}
