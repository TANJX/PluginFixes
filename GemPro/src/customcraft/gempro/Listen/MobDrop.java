//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.Listen;

import customcraft.gempro.GemPro;
import customcraft.gempro.Handler.Util;
import customcraft.gempro.OBJ.GemOBJ;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class MobDrop implements Listener {
    private static final String mobSpawnReason = "mobSpawnReason";

    public MobDrop() {
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onMobSpawn(CreatureSpawnEvent e) {
        switch (e.getSpawnReason().ordinal()) {
            case 1:
                e.getEntity().setMetadata("mobSpawnReason", new MobDrop.MobMetadata(GemPro.instance, e.getSpawnReason()));
                return;
            default:
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeathEvent(EntityDeathEvent e) {
        if (e.getEntity() instanceof Animals || e.getEntity() instanceof Monster) {
            if (e.getEntity().hasMetadata("mobSpawnReason") && e.getEntity().getMetadata("mobSpawnReason").equals(SpawnReason.SPAWNER)) {
                return;
            }

            if (GemPro.instance.dropList.containsKey(e.getEntityType().toString())) {
                Location loc = e.getEntity().getLocation();
                Set dropGems = ((HashMap) GemPro.instance.dropList.get(e.getEntityType().toString())).entrySet();

                for (Object dropGem : dropGems) {
                    Entry gem = (Entry) dropGem;
                    if ((double) (Util.instance.rand(1000000000) + 1) <= (Double) gem.getValue() * 1.0E9D) {
                        loc.getWorld().dropItemNaturally(loc, Util.instance.spawnGem((GemOBJ) gem.getKey()));
                    }
                }
            }
        }

    }

    class MobMetadata implements MetadataValue {
        Object value;
        final Plugin p;

        public MobMetadata(Plugin p, Object o) {
            this.p = p;
            this.value = o;
        }

        public boolean equals(Object obj) {
            return this.value.equals(obj);
        }

        public void set(Object o) {
            this.value = o;
        }

        public int hashCode() {
            byte hash = 3;
            int hash1 = 53 * hash + Objects.hashCode(this.value);
            return hash1;
        }

        public boolean asBoolean() {
            throw new NullPointerException();
        }

        public byte asByte() {
            throw new NullPointerException();
        }

        public double asDouble() {
            throw new NullPointerException();
        }

        public float asFloat() {
            throw new NullPointerException();
        }

        public int asInt() {
            throw new NullPointerException();
        }

        public long asLong() {
            throw new NullPointerException();
        }

        public short asShort() {
            throw new NullPointerException();
        }

        public String asString() {
            throw new NullPointerException();
        }

        public Plugin getOwningPlugin() {
            return this.p;
        }

        public void invalidate() {
        }

        public Object value() {
            return this.value;
        }
    }
}
