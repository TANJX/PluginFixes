//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.doodcraft.oshcon.bukkit.enderpads.util;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockHelper {
    public BlockHelper() {
    }

    public static boolean isDirectional(Material material) {
        BlockHelper.directionalBlocks[] var1 = BlockHelper.directionalBlocks.values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            BlockHelper.directionalBlocks value = var1[var3];
            if (material.toString().equals(value.name())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isDual(Material material) {
        BlockHelper.dualBlocks[] var1 = BlockHelper.dualBlocks.values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            BlockHelper.dualBlocks value = var1[var3];
            if (material.toString().equals(value.name())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isVariant(Material material) {
        BlockHelper.variantBlocks[] var1 = BlockHelper.variantBlocks.values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            BlockHelper.variantBlocks value = var1[var3];
            if (material.toString().equals(value.name())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPhysicsBlock(Material material) {
        return material.hasGravity();
    }

    public static String fixDual(Block block) {
        if (isDual(block.getType())) {
            if (block.getType() == Material.GLOWING_REDSTONE_ORE) {
                return "REDSTONE_ORE";
            }

            if (block.getType() == Material.BURNING_FURNACE) {
                return "FURNACE";
            }

            if (block.getType() == Material.REDSTONE_LAMP_ON) {
                return "REDSTONE_LAMP_OFF";
            }
        }

        return block.getType().toString();
    }

    public static byte fixVariant(Block block) {
        if (isVariant(block.getType())) {
            if (block.getType() == Material.QUARTZ_BLOCK) {
                if (block.getData() == 0) {
                    return 0;
                }

                if (block.getData() == 1) {
                    return 1;
                }

                if (block.getData() >= 2) {
                    return 2;
                }

                return 0;
            }

            if (block.getType() == Material.LOG) {
                return fixLog(block);
            }

            if (block.getType() == Material.LOG_2) {
                return fixLog(block);
            }

            if (block.getType() == Material.HUGE_MUSHROOM_1) {
                return 0;
            }

            if (block.getType() == Material.HUGE_MUSHROOM_2) {
                return 0;
            }

            if (block.getType() == Material.SPONGE) {
                return 0;
            }
        }

        return isDirectional(block.getType()) ? 0 : block.getData();
    }

    public static byte fixLog(Block block) {
        byte data = block.getData();
        if (block.getType().equals(Material.LOG)) {
            label72:
            {
                if (data != 0 && data != 4 && data != 8) {
                    if (data != 1 && data != 5 && data != 9) {
                        if (data != 2 && data != 6 && data != 10) {
                            if (data != 3 && data != 7 && data != 11) {
                                break label72;
                            }

                            return 3;
                        }

                        return 2;
                    }

                    return 1;
                }

                return 0;
            }
        }

        if (block.getType().equals(Material.LOG_2)) {
            if (data == 0 || data == 4 || data == 8) {
                return 0;
            }

            if (data == 1 || data == 5 || data == 9) {
                return 1;
            }
        }

        return block.getData();
    }

    public static enum variantBlocks {
        SPONGE,
        QUARTZ_BLOCK,
        LOG,
        LOG_2,
        HUGE_MUSHROOM_1,
        HUGE_MUSHROOM_2;

        private variantBlocks() {
        }
    }

    public static enum dualBlocks {
        GLOWING_REDSTONE_ORE,
        REDSTONE_ORE,
        REDSTONE_LAMP_ON,
        REDSTONE_LAMP_OFF,
        FURNACE,
        BURNING_FURNACE;

        private dualBlocks() {
        }
    }

    public static enum directionalBlocks {
        BONE_BLOCK,
        PUMPKIN,
        JACK_O_LANTERN,
        DISPENSER,
        DROPPER,
        HAY_BLOCK,
        COMMAND,
        OBSERVER,
        STRUCTURE_BLOCK,
        PURPUR_PILLAR,
        WHITE_GLAZED_TERRACOTTA,
        ORANGE_GLAZED_TERRACOTTA,
        MAGENTA_GLAZED_TERRACOTTA,
        LIGHT_BLUE_GLAZED_TERRACOTTA,
        YELLOW_GLAZED_TERRACOTTA,
        LIME_GLAZED_TERRACOTTA,
        PINK_GLAZED_TERRACOTTA,
        GRAY_GLAZED_TERRACOTTA,
        SILVER_GLAZED_TERRACOTTA,
        CYAN_GLAZED_TERRACOTTA,
        PURPLE_GLAZED_TERRACOTTA,
        BLUE_GLAZED_TERRACOTTA,
        BROWN_GLAZED_TERRACOTTA,
        GREEN_GLAZED_TERRACOTTA,
        RED_GLAZED_TERRACOTTA,
        BLACK_GLAZED_TERRACOTTA,
        FURNACE,
        BURNING_FURNACE,
        QUARTZ_BLOCK,
        LOG,
        LOG_2,
        HUGE_MUSHROOM_1,
        HUGE_MUSHROOM_2;

        private directionalBlocks() {
        }
    }
}
