package xyz.namutree0345.rhythm

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import xyz.namutree0345.rhythm.command.Drop
import java.time.Duration

class EventHandler : Listener {

    @EventHandler
    fun onChangeItem(event: PlayerItemHeldEvent) {
        if(event.newSlot == 0 || event.newSlot == 1 || event.newSlot == 2 || event.newSlot == 3) {
            Drop.notes.forEach { it ->
                if(!it.isDead) {
                    if(event.newSlot == Drop.getSlotIndex(Drop.getNoteByMaterial(it.blockData.material))) {
                        val a = Location(it.world, -377.0, 63.0, 10.0)
                        println(a.x - it.location.x)
                        if (a.x - it.location.x <= 1.1) {
                            for (plr in Bukkit.getOnlinePlayers()) {
                                plr.showTitle(
                                    Title.title(
                                        Component.empty(), Component.text("PERFECT"), Title.Times.of(
                                            Duration.ZERO,
                                            Duration.ofSeconds(5),
                                            Duration.ZERO
                                        )
                                    )
                                )
                                it.remove()
                                return@forEach
                            }
                        } else if (a.x - it.location.x <= 1.2) {
                            for (plr in Bukkit.getOnlinePlayers()) {
                                plr.showTitle(
                                    Title.title(
                                        Component.empty(), Component.text("YEAH"), Title.Times.of(
                                            Duration.ZERO,
                                            Duration.ofSeconds(5),
                                            Duration.ZERO
                                        )
                                    )
                                )
                                it.remove()
                                return@forEach
                            }
                        } else if (a.x - it.location.x <= 1.3) {
                            for (plr in Bukkit.getOnlinePlayers()) {
                                plr.showTitle(
                                    Title.title(
                                        Component.empty(), Component.text("SO BAD"), Title.Times.of(
                                            Duration.ZERO,
                                            Duration.ofSeconds(5),
                                            Duration.ZERO
                                        )
                                    )
                                )
                                return@forEach
                            }
                        } else {
                            for (plr in Bukkit.getOnlinePlayers()) {
                                plr.showTitle(
                                    Title.title(
                                        Component.empty(), Component.text("BAD"), Title.Times.of(
                                            Duration.ZERO,
                                            Duration.ofSeconds(5),
                                            Duration.ZERO
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
            event.player.inventory.heldItemSlot = 4
        }
    }

}