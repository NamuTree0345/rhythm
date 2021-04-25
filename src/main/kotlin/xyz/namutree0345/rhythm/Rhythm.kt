package xyz.namutree0345.rhythm

import org.bukkit.plugin.java.JavaPlugin
import xyz.namutree0345.rhythm.command.Drop

class Rhythm : JavaPlugin() {

    override fun onEnable() {
        super.onEnable()
        server.pluginManager.registerEvents(EventHandler(), this)
        getCommand("note")?.setExecutor(Drop(this))
        server.scheduler.scheduleSyncRepeatingTask(this, {
            for (note in Drop.notes) {
                note.ticksLived = 1
            }
        }, 0L, 1L)
    }

}