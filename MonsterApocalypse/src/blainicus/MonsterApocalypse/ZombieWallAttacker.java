package blainicus.MonsterApocalypse;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ZombieWallAttacker implements Runnable {
    final List<BlockPair> blocklist;
    private final List<RealBlockPair> resetmap;
    private final Map<LivingEntity, Integer> timemap;
    private final Map<LivingEntity, Integer> timebelowmap;
    private final Map<LivingEntity, Location> locationmap;
    private final Map<Block, Integer> destructionmap;
    private String blockstring;

    public ZombieWallAttacker(MonsterApocalypse instance) {
        MonsterApocalypse plugin = instance;
        blocklist = new ArrayList<>();
        timemap = new HashMap<>();
        timebelowmap = new HashMap<>();
        locationmap = new HashMap<>();
        //if(plugin.newzombiemethod){
        destructionmap = new HashMap<>();
        resetmap = new ArrayList<>();
        //}
    }

    public void run() {
        cleanup();
    }

    private void cleanup() {
        Object[] keys = timemap.keySet().toArray();
        for (Object key2 : keys) {
            try {
                if (((LivingEntity) key2).isDead()) {
                    timemap.remove(key2);
                }
            } catch (Exception e) {
                timemap.remove(key2);
            }
        }
        keys = null;
        keys = timebelowmap.keySet().toArray();
        for (Object key1 : keys) {
            try {
                if (((LivingEntity) key1).isDead()) {
                    timebelowmap.remove(key1);
                }
            } catch (Exception e) {
                timebelowmap.remove(key1);
            }
        }
        keys = null;
        keys = locationmap.keySet().toArray();
        for (Object key : keys) {
            try {
                if (((LivingEntity) key).isDead()) {
                    locationmap.remove(key);
                }
            } catch (Exception e) {
                locationmap.remove(key);
            }
        }
        keys = null;
    }

    public int getmobstill(LivingEntity ent) {
        try {
            return timemap.get(ent);
        } catch (NullPointerException e) {
            addmob(ent);
            return timemap.get(ent);
        }
    }

    public int getmobbelow(LivingEntity ent) {
        try {
            return timebelowmap.get(ent);
        } catch (NullPointerException e) {
            addmob(ent);
            return timebelowmap.get(ent);
        }
    }

    public void addmob(LivingEntity ent) {
        timemap.put(ent, 0);
        if (timebelowmap.get(ent) == null) {
            timebelowmap.put(ent, 0);
        }
        setmobloc(ent);
    }

    public void resetmobstill(LivingEntity ent) {
        timemap.remove(ent);
        locationmap.remove(ent);
    }

    public void resetmobbelow(LivingEntity ent) {
        timebelowmap.remove(ent);
    }

    public void increasestilltime(LivingEntity ent) {
        if (timemap.get(ent) == null) {
            timemap.put(ent, 4);
        } else {
            int time2 = timemap.get(ent);
            timemap.put(ent, time2 + 4);
        }
        if (timebelowmap.get(ent) == null) {
            timebelowmap.put(ent, 1);
        } else {
            int time2 = timebelowmap.get(ent);
            timebelowmap.put(ent, time2 + 1);
        }
    }

    public Location getmobloc(LivingEntity ent) {
        return locationmap.get(ent);
    }

    private void setmobloc(LivingEntity ent) {
        Location roundedloc = new Location(ent.getWorld(), 0, 0, 0);
        Location entloc = ent.getLocation();
        roundedloc.setX(Math.round(entloc.getBlockX() + 0));
        roundedloc.setY(Math.round(entloc.getBlockY() + 0));
        roundedloc.setZ(Math.round(entloc.getBlockZ() + 0));
        locationmap.put(ent, roundedloc);
    }

    public boolean sameloc(LivingEntity ent) {
        Location roundedloc = new Location(ent.getWorld(), 0, 0, 0);
        Location entloc = ent.getLocation();
        roundedloc.setX(entloc.getBlockX());
        roundedloc.setY(entloc.getBlockY());
        roundedloc.setZ(entloc.getBlockZ());
        if (roundedloc.getBlockX() == locationmap.get(ent).getBlockX()
                &&
                roundedloc.getBlockY() == locationmap.get(ent).getBlockY()
                &&
                roundedloc.getBlockZ() == locationmap.get(ent).getBlockZ()
                ) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public void stringadd(String s) {
        try {
            if (s.startsWith("block")) return;
            int secondindex = s.indexOf(":") + 1;
            Material mat;
            try {
                mat = Material.getMaterial(Integer.parseInt(s.substring(0, secondindex - 1)));
            } catch (NumberFormatException e) {
                mat = Material.getMaterial(s.substring(0, secondindex - 1).toUpperCase());
            }
            int seconds = Integer.parseInt(s.substring(secondindex));
            addblock(mat, seconds);
        } catch (Exception e) {
            System.out.println("Monster Apocalypse: Invalid time setting " + blockstring + ". Please use a valid CraftBukkit enum.");
            return;
        }
    }

    private void addblock(Material m, int s) {
        //will bug please test
        blocklist.add(new BlockPair(m, s));
    }

    public int getblockseconds(Material b) {
        for (BlockPair aBlocklist : blocklist) {
            if (aBlocklist.getblock() == b) {
                return aBlocklist.getseconds();
            }
        }
        return -1;
    }

    public void checkblocks() {
        for (BlockPair aBlocklist : blocklist) {
            try {
                aBlocklist.getseconds();
            } catch (NullPointerException e) {
                System.out.println("Monster Apocalypse: Invalid time setting " + blockstring + ". Please use a valid CraftBukkit enum.");
                return;
            }

        }
    }

    public int getblockstate(Block b, int time) {
        Material oldmat = b.getType();
        if (oldmat == Material.AIR || oldmat == Material.LAVA || oldmat == Material.WATER) return 0;
        int test = getblockseconds(oldmat);
        if (test < 0) return 0;
        int timeoffset = 0;
        try {
            timeoffset += destructionmap.get(b);
        } catch (Exception e) {
        }
        destructionmap.put(b, time + timeoffset);
        update(b, 0);
        if (
                time + timeoffset
                        >=
                        test) {
            destructionmap.remove(b);
            removeblock(b);
            return 2;
        }
        return 1;

    }

    private void update(Block b, int time) {
        removeblock(b);
        resetmap.add(new RealBlockPair(b, time));
    }

    private void removeblock(Block b) {
        for (int i = 0; i < resetmap.size(); i++) {
            if (resetmap.get(i).getblock().equals(b)) {
                resetmap.remove(i);
                return;
            }
        }
    }

    public void resettimes() {
        //int maxtime=plugin.breakreset;
        for (int i = 0; i < resetmap.size(); i++) {
            resetmap.get(i).addseconds(4);
            int maxtime = 12000;
            if (resetmap.get(i).getseconds() > maxtime) {
                destructionmap.remove(resetmap.get(i).getblock());
                resetmap.remove(i);
            }
        }

    }

    public class BlockPair {
        final Material block;
        final int seconds;

        public BlockPair(Material m, int s) {
            block = m;
            seconds = s;
        }

        public int getseconds() {
            return seconds;
        }

        public Material getblock() {
            return block;
        }
    }

    public class RealBlockPair {
        final Block block;
        int seconds;

        public RealBlockPair(Block m, int s) {
            block = m;
            seconds = s;
        }

        public int getseconds() {
            return seconds;
        }

        public Block getblock() {
            return block;
        }

        public void addseconds(int amount) {
            seconds += amount;
        }
    }
}
