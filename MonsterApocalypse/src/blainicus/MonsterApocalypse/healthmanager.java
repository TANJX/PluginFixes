package blainicus.MonsterApocalypse;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class healthmanager implements Runnable {
    private final Map<LivingEntity, Double> healthmap;
    private final Map<LivingEntity, Long> timemap;
    private final MonsterApocalypse plugin;

    public healthmanager(MonsterApocalypse instance) {
        plugin = instance;
        healthmap = new HashMap<>();
        timemap = new HashMap<>();
    }

    public void run() {
        cleanup();
    }

    private void cleanup() {
        Object[] keys = healthmap.keySet().toArray();
        for (Object key : keys) {
            try {
                if (((LivingEntity) key).isDead()) {
                    healthmap.remove(key);
                }
            } catch (Exception e) {
                healthmap.remove(key);
            }
        }
        Object[] keyz = timemap.keySet().toArray();
        for (Object aKeyz : keyz) {
            try {
                if (((LivingEntity) aKeyz).isDead()) {
                    healthmap.remove(aKeyz);
                }
            } catch (Exception e) {
                healthmap.remove(aKeyz);
            }
        }
    }

    public void addmob(LivingEntity ent) {
        if (plugin.getMobName(ent) == "" || ent.isDead()) {
            return;
        }
        healthmap.put(ent, (double) plugin.getMobHealth(ent));
        timemap.put(ent, (long) 0);
    }

    public void removemob(Entity ent) {
        if (plugin.getMobName(ent) == "" || ent.isDead()) {
            return;
        }
        healthmap.remove(ent);
        timemap.remove(ent);
    }

    public double damagemob(LivingEntity ent, double amount) {
        if (plugin.getMobName(ent) == "" || ent.isDead()) {
            return 0;
        }
        try {
            double neww = healthmap.get(ent) - amount;
            healthmap.put(ent, neww);
            timemap.put(ent, System.currentTimeMillis());
            return neww;
        } catch (NullPointerException e) {
            addmob(ent);
            double neww = healthmap.get(ent) - amount;
            healthmap.put(ent, neww);
            timemap.put(ent, System.currentTimeMillis());
            return neww;
        }
    }

    public double getmobhp(LivingEntity ent) {
        if (plugin.getMobName(ent) == "" || ent.isDead()) {
            return 0;
        }
        try {
            double neww = healthmap.get(ent);
            return neww;
        } catch (NullPointerException e) {
            addmob(ent);
            double neww = healthmap.get(ent);
            return neww;
        }
    }

    public void setmobhp(LivingEntity ent, double newvalue) {
        if (plugin.getMobName(ent) == "" || ent.isDead()) {
            return;
        }
        try {
            healthmap.put(ent, newvalue);
        } catch (NullPointerException e) {
            addmob(ent);
        }
    }

    public long getLastDamageTime(LivingEntity ent) {
        long li = 0;
        try {
            li = timemap.get(ent);
        } catch (NullPointerException e) {
            li = 0;
        }
        return li;
    }

    void addall() {
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            List<Entity> L = plugin.getServer().getWorld(worldname).getEntities();
            Iterator<Entity> iter = L.iterator();
            while (iter.hasNext()) {
                Entity entscan = iter.next();
                if (plugin.getMobName(entscan) != "" && !entscan.isDead()) {
                    addmob((LivingEntity) entscan);
                }
            }
        }
    }
}
