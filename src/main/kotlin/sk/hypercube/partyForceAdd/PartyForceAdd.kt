package sk.hypercube.partyForceAdd

import com.alessiodp.parties.api.Parties
import com.alessiodp.parties.api.interfaces.PartiesAPI
import com.alessiodp.parties.api.interfaces.Party
import com.alessiodp.parties.api.interfaces.PartyPlayer
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.Command
import java.util.UUID

class PartyForceAdd : Plugin() {

    private val prefix: String = "§8[§9PartyForceAdd§8] §r"
    private val version: String = "v1.0"

    override fun onEnable() {
        // Plugin startup logic
        val PartiesAPI: PartiesAPI = Parties.getApi()
        proxy.logger.info(prefix + "Enabling PartyForceAdd " + version + " by peetfoxx")
        ProxyServer.getInstance().pluginManager.registerCommand(this, ForceAddPartyCommand(PartiesAPI))



    }

    override fun onDisable() {
        // Plugin shutdown logic
        proxy.logger.info(prefix + "Disabling PartyForceAdd " + version + " by peetfoxx")
    }

    @Suppress("DEPRECATION")
    inner class ForceAddPartyCommand(private val partiesAPI: PartiesAPI) : Command("partyadd") {
        override fun execute(sender: CommandSender, args: Array<String>) {
            if (args.size != 2) {
                sender.sendMessage("$prefix§cUsage: /partyadd <player> <party>")
                return
        }
            val playerName = args[0]
            val partyName = args[1]

            val targetPlayer: ProxiedPlayer? = ProxyServer.getInstance().getPlayer(playerName)
            if (targetPlayer == null) {
                sender.sendMessage("$prefix§cPlayer not found.")
                return
            }

            val partiesPlayer: PartyPlayer? = partiesAPI.getPartyPlayer(targetPlayer.uniqueId)
            if (partiesPlayer == null) {
                sender.sendMessage("$prefix§cPlayer is not known in the Parties system.")
                return
            }
            val targetParty: Party? = partiesAPI.getParty(partyName)
            if (targetParty == null) {
                sender.sendMessage("$prefix§cParty not found")
                return
            }
            if (partiesPlayer.isInParty) {
                val currentPartyId: UUID? = partiesPlayer.partyId
                if (currentPartyId != null) {
                    val currentParty: Party? = partiesAPI.getParty(currentPartyId)
                    if (currentParty != null) {
                        val leftParty = currentParty.removeMember(partiesPlayer)
                        if (leftParty) {
                            sender.sendMessage("$prefix§ePlayer removed from their current party")
                        } else {
                            sender.sendMessage("$prefix§cError in removing player from their party")
                            return
                        }
                    }
                }
            }
            val success: Boolean = targetParty.addMember(partiesPlayer)
            if (success) {
                sender.sendMessage("$prefix§aSuccessfully added ${targetPlayer.name} to party $partyName")
            } else {
                sender.sendMessage("$prefix§cFailed to add ${targetPlayer.name} to party $partyName")
            }
        }
    }
}
