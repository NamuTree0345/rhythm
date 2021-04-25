package xyz.namutree0345.rhythm.command

import com.destroystokyo.paper.block.TargetBlockInfo
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import xyz.namutree0345.rhythm.NoteType
import xyz.namutree0345.rhythm.Rhythm
import java.util.*
import kotlin.collections.HashMap

class Drop(private val plugin: Rhythm) : CommandExecutor {

    companion object {
        val notes = ArrayList<FallingBlock>()
        val notesIds = HashMap<UUID, Int>()

        fun getNoteMaterial(type: NoteType) : Material {
            return when(type) {
                NoteType.RED -> Material.RED_CONCRETE
                NoteType.BLUE -> Material.BLUE_CONCRETE
                NoteType.GREEN -> Material.GREEN_CONCRETE
                NoteType.YELLOW -> Material.YELLOW_CONCRETE
            }
        }
        fun getNoteByMaterial(type: Material) : NoteType {
            return when(type) {
                Material.RED_CONCRETE -> NoteType.RED
                Material.BLUE_CONCRETE -> NoteType.BLUE
                Material.GREEN_CONCRETE -> NoteType.GREEN
                Material.YELLOW_CONCRETE -> NoteType.YELLOW
                else -> NoteType.RED
            }
        }
        fun getSlotIndex(type: NoteType) : Int {
            return when(type) {
                NoteType.RED -> 3
                NoteType.BLUE -> 1
                NoteType.GREEN -> 2
                NoteType.YELLOW -> 0
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if (args.isNotEmpty()) {
                when (args[0]) {
                    "create" -> {
                        val f = sender.world.spawnFallingBlock(sender.getTargetBlock(4, TargetBlockInfo.FluidMode.NEVER)!!.location.toBlockLocation().add(0.0, 2.0, 0.0), getNoteMaterial(NoteType.valueOf(args[1])).createBlockData())
                        f.dropItem = false
                        f.setGravity(false)
                        f.location.direction = Vector(0, 0, 0)
                        notes.add(f)
                    }
                    "drop" -> {
                        for (note in notes) {
                            notesIds[note.uniqueId] = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                                note.velocity = Vector(0.05, 0.0, 0.0)
                                /*
                                note.teleport(note.location.let {
                                    it.x += 0.05
                                    it
                                })
                                 */
                                val newLoc = Location(note.world, note.location.x + 0.7, note.location.y, note.location.z)
                                if(newLoc.block.type == Material.BLACK_CONCRETE) {
                                    note.velocity = Vector(0.0, 0.0, 0.0)
                                    note.remove()
                                    cancelDroppingTask(note.uniqueId)
                                    notes.remove(note)
                                }
                            }, 0, 1)

                        }
                    }
                }
            }
        }
        return true
    }

    private fun cancelDroppingTask(uuid: UUID) {
        Bukkit.getScheduler().cancelTask(notesIds[uuid]!!)
    }

}