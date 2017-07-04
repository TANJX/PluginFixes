package info.TrenTech.EasyKits.SQL;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public final class SerializeItemStack {

    private static final String sp1 = "分隔符一";
    private static final String sp2 = "分隔符二";

    public static String invToStringBlob(ItemStack[] inv) {
        System.out.println(inv.length);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] != null) {
                result.append(i).append(sp1).append(itemToStringBlob(inv[i])).append(sp2);
            }
        }
        return result.toString();
    }

    public static ItemStack[] stringBlobToInv(String stringblob) {
        String[] itemAndIndexs = stringblob.split(sp2);
        ItemStack[] result = new ItemStack[Integer.parseInt(itemAndIndexs[itemAndIndexs.length - 1].split(sp1)[0]) + 1];
        for (String itemAndIndex : itemAndIndexs) {
            String[] split = itemAndIndex.split(sp1);
            int i = Integer.parseInt(split[0]);
            ItemStack item = stringBlobToItem(split[1]);
            result[i] = item;
        }
        return result;
    }

    private static String itemToStringBlob(ItemStack itemStack) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", itemStack);
        return config.saveToString();
    }

    private static ItemStack stringBlobToItem(String stringBlob) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(stringBlob);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("i");
    }
}
