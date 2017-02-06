package blainicus.MonsterApocalypse;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

class DropManager {
    private final List<ItemStack> drops;
    private List<String> droplist;
    private final MonsterApocalypse plugin;
    private String itemstring;
    private int countindex;
    private int chanceindex;
    private double chance;
    private int count;
    private Material mat;

    public DropManager(MonsterApocalypse instance) {
        plugin = instance;
        drops = new ArrayList<>();
        droplist = new ArrayList<>();
    }

    @SuppressWarnings("deprecation")
    private void getDrops(Entity ent) {
        drops.clear();
        droplist = plugin.getMobDrops(ent);
        for (String aDroplist : droplist) {
            itemstring = aDroplist;
            if (itemstring.startsWith("item")) continue;
            countindex = itemstring.indexOf(":") + 1;
            chanceindex = itemstring.indexOf(":", countindex + 1) + 1;
            try {
                mat = Material.getMaterial(Integer.parseInt(itemstring.substring(0, countindex - 1)));
            } catch (NumberFormatException epz) {
                mat = Material.getMaterial(itemstring.substring(0, countindex - 1));
            }
            count = Integer.parseInt(itemstring.substring(countindex, chanceindex - 1));
            chance = Double.parseDouble(itemstring.substring(chanceindex));
            if (plugin.spawnrand.nextDouble() <= chance / 100d) {
                drops.add(new ItemStack(mat, count));
            }
        }
    }

    public void addDrops(Entity ent, List<ItemStack> list) {
        try {
            getDrops(ent);
        } catch (NullPointerException e) {
            System.out.println("Monster Apocalypse: Invalid drop setting " + itemstring + " for entity " + ent.getClass().getSimpleName() + ". Please use a valid CraftBukkit enum.");
            return;
        }
        for (ItemStack drop : drops) {
            try {
                list.add(drop);
            } catch (UnsupportedOperationException e) {
            }
        }
    }

    public void checkdrops() {
        for (int n = 0; n < plugin.moblist.size(); n++) {
            if (plugin.moblist.get(n).name == "TamedWolf") {
                continue;
            }
            try {
                getDrops(plugin.moblist.get(n).name);
            } catch (NullPointerException e) {
                System.out.println("Monster Apocalypse: Invalid drop setting " + itemstring + " for entity " + plugin.moblist.get(n).name + ". Please use a valid CraftBukkit enum.");
                return;
            }

        }
    }

    @SuppressWarnings("deprecation")
    private void getDrops(String name) {
        drops.clear();
        droplist = plugin.getMobDrops(name);
        for (String aDroplist : droplist) {
            try {
                itemstring = aDroplist;
                if (itemstring.startsWith("item")) continue;
                countindex = itemstring.indexOf(":") + 1;
                chanceindex = itemstring.indexOf(":", countindex + 1) + 1;
                int dataindex = itemstring.indexOf(":", chanceindex + 1) + 1;
                if (dataindex == 0) {
                    mat = Material.getMaterial(itemstring.substring(0, countindex - 1));
                    count = Integer.parseInt(itemstring.substring(countindex, chanceindex - 1));
                    chance = Double.parseDouble(itemstring.substring(chanceindex));
                    if (plugin.spawnrand.nextDouble() <= chance / 100d) {
                        drops.add(new ItemStack(mat, count));
                    }
                } else {
                    mat = Material.getMaterial(itemstring.substring(0, countindex - 1));
                    count = Integer.parseInt(itemstring.substring(countindex, chanceindex - 1));
                    chance = Double.parseDouble(itemstring.substring(chanceindex, dataindex - 1));
                    //				System.out.println(chanceindex+" "+dataindex);
                    byte data = Byte.parseByte(itemstring.substring(dataindex));
                    if (plugin.spawnrand.nextDouble() <= chance / 100d) {
                        drops.add(new ItemStack(mat, count, (short) 0, data));
                    }
                }
            } catch (Exception e) {
                System.out.println("Monster Apocalypse: Error adding drop to drop list of " + name + ", check your drops for errors.");
            }
        }
    }
}