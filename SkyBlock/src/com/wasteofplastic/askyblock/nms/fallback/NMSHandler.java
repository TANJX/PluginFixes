/*******************************************************************************
 * This file is part of ASkyBlock.
 *
 *     ASkyBlock is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ASkyBlock is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ASkyBlock.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package com.wasteofplastic.askyblock.nms.fallback;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.wasteofplastic.askyblock.nms.NMSAbstraction;
import com.wasteofplastic.org.jnbt.CompoundTag;
import com.wasteofplastic.org.jnbt.StringTag;
import com.wasteofplastic.org.jnbt.Tag;

/**
 * @author ben
 *
 */
public class NMSHandler implements NMSAbstraction {

    /* (non-Javadoc)
     * @see com.wasteofplastic.askyblock.nms.NMSAbstraction#setBlockSuperFast(org.bukkit.block.Block, org.bukkit.Material)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void setBlockSuperFast(Block b, int blockId, byte data, boolean applyPhysics) {
        b.setTypeIdAndData(blockId, data, applyPhysics);
    }

    @Override
    public ItemStack setBook(Tag item) {
        Bukkit.getLogger().warning("Written books in schematics not supported with this version of server");
        return new ItemStack(Material.WRITTEN_BOOK);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setFlowerPotBlock(Block block, ItemStack itemStack) {
        block.setTypeIdAndData(itemStack.getTypeId(), itemStack.getData().getData(), false);

    }

    @Override
    public boolean isPotion(ItemStack item) {
        if (item.getType().equals(Material.POTION) && item.getDurability() != 0) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack setPotion(Material itemMaterial, Tag itemTags,
            ItemStack chestItem) {
        // Try some backwards compatibility with new 1.9 schematics
        Map<String,Tag> contents = (Map<String,Tag>) ((CompoundTag) itemTags).getValue().get("tag").getValue();
        StringTag stringTag = ((StringTag)contents.get("Potion"));
        if (stringTag != null) {
            String tag = stringTag.getValue().replace("minecraft:", "");
            PotionType type = null;
            boolean strong = tag.contains("strong");
            boolean _long = tag.contains("long");
            //Bukkit.getLogger().info("tag = " + tag);
            if(tag.equals("fire_resistance") || tag.equals("long_fire_resistance")){
                type = PotionType.FIRE_RESISTANCE;
            }else if(tag.equals("harming") || tag.equals("strong_harming")){
                type = PotionType.INSTANT_DAMAGE;
            }else if(tag.equals("healing") || tag.equals("strong_healing")){
                type = PotionType.INSTANT_HEAL;
            }else if(tag.equals("invisibility") || tag.equals("long_invisibility")){
                type = PotionType.INVISIBILITY;
            }else if(tag.equals("leaping") || tag.equals("long_leaping") || tag.equals("strong_leaping")){
                type = PotionType.JUMP;
            }else if(tag.equals("night_vision") || tag.equals("long_night_vision")){
                type = PotionType.NIGHT_VISION;
            }else if(tag.equals("poison") || tag.equals("long_poison") || tag.equals("strong_poison")){
                type = PotionType.POISON;
            }else if(tag.equals("regeneration") || tag.equals("long_regeneration") || tag.equals("strong_regeneration")){
                type = PotionType.REGEN;
            }else if(tag.equals("slowness") || tag.equals("long_slowness")){
                type = PotionType.SLOWNESS;
            }else if(tag.equals("swiftness") || tag.equals("long_swiftness") || tag.equals("strong_swiftness")){
                type = PotionType.SPEED;
            }else if(tag.equals("strength") || tag.equals("long_strength") || tag.equals("strong_strength")){
                type = PotionType.STRENGTH;
            }else if(tag.equals("water_breathing") || tag.equals("long_water_breathing")){
                type = PotionType.WATER_BREATHING;
            }else if(tag.equals("water")){
                type = PotionType.WATER;
            }else if(tag.equals("weakness") || tag.equals("long_weakness")){
                type = PotionType.WEAKNESS;
            }else{
                return chestItem;
            }
            Potion potion = new Potion(type);
            potion.setHasExtendedDuration(_long);
            potion.setLevel(strong ? 2 : 1);
            chestItem = potion.toItemStack(chestItem.getAmount());
        }

        return chestItem;
    }

    /* (non-Javadoc)
     * @see com.wasteofplastic.askyblock.nms.NMSAbstraction#getSpawnEgg(org.bukkit.entity.EntityType, int)
     */
    @Override
    public ItemStack getSpawnEgg(EntityType type, int amount) {
        SpawnEgg egg = new SpawnEgg(type);
        return egg.toItemStack(amount);
    }
}