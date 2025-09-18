Simple plugin primarily for Bungeecord which hooks into the plugin Parties by AlessioDP

# Usage:
```
- /partyadd <player> <party> - Forces the player to join specified party
```
On Bukkit/Paper servers, this action can be performed by using /sudo plugins,
however using Parties on a proxy does not support executing Party commands this
way, that's why I made my own solution.

The code might be a bit messy, as it's my first Bungee plugin, but it has been tested and
it does the job and doesn't seem to cause any issues.


# Installation:
## Bukkit/Paper:
1. Download [PartyForceAdd for Bukkit](out/artifacts/PartyForceAdd-Bukkit.jar)
2. Place the .jar in your /plugins/ directory
3. Restart the server
4. Try executing the command /partyadd ...

## Bungeecord:
1. Download [PartyForceAdd for Bungee](out/artifacts/party-force-add.jar)
2. Place the .jar in your /plugins/ directory ONLY on the Proxy
3. Restart the server
4. Try executing the command /partyadd ...

