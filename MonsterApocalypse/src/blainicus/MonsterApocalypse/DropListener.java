package blainicus.MonsterApocalypse;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class DropListener implements Listener {
    private final MonsterApocalypse plugin;

    public DropListener(MonsterApocalypse instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (e.getEntity().getWorld().getName().equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1) return;
        }
        Entity ent = e.getEntity();
        //entity not found by mobdrops
        if (plugin.getMobDrops(ent) == null) return;
//		EntityDamageEvent cause=(e.getEntity().getLastDamageCause());
//		if(cause.getDamage()==2000000){
//			e.getDrops().clear();
//			return;}
        if (plugin.getMobDropOverwrite(ent)) e.getDrops().clear();
        plugin.rand = new Random(System.currentTimeMillis());
        plugin.dropper.addDrops(ent, e.getDrops());
    }

}
