package blainicus.MonsterApocalypse;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

class SpawnBlockManager {
    final List<Material> blocklist;
    private final MonsterApocalypse plugin;
    String itemstring;
    Material mat;

    public SpawnBlockManager(MonsterApocalypse instance) {
        plugin = instance;
        blocklist = new ArrayList<>();
    }

    @SuppressWarnings("deprecation")
    public void addblock(String m) {

        if (Material.getMaterial(m) != null) {
            try {
                blocklist.add(Material.getMaterial(Integer.parseInt(m)));
            } catch (NumberFormatException e) {
                blocklist.add(Material.getMaterial(m.toUpperCase()));
            }
        }
    }

    public boolean getAllowSpawn(Material m) {
        if (!plugin.checkspawnfeet) {
            return true;
        }
        for (Material aBlocklist : blocklist) {
            if (aBlocklist == m) {
                if (plugin.invertspawnfeet) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (plugin.invertspawnfeet) {
            return false;
        }
        return true;
    }
}
