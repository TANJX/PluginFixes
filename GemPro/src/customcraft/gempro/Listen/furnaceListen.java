//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.Listen;

import customcraft.gempro.EnumPack.Msg;
import customcraft.gempro.GemPro;
import customcraft.gempro.Handler.Util;
import customcraft.gempro.OBJ.GemOBJ;
import customcraft.gempro.OBJ.PotionOBJ;
import customcraft.gempro.OBJ.RPGItemOBJ;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class furnaceListen implements Listener {
    private static final HashMap<Block, furnaceListen.GemFurn> furnItemList = new HashMap();

    public furnaceListen() {
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        Furnace furn = (Furnace) e.getBlock().getState();
        if (this.canFurn(furn)) {
            e.setBurning(true);
            e.setBurnTime(240);
        } else {
            RPGItemOBJ rpgitem = Util.instance.itemToRPGItem(furn.getInventory().getSmelting());
            if (rpgitem != null) {
                e.setCancelled(true);
                e.setBurnTime(120000);
                e.setBurning(false);
            }
        }

    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onFurnaceSmelt(FurnaceSmeltEvent e) {
        furnaceListen.GemFurn gemFurn = furnItemList.remove(e.getBlock());
        if (gemFurn != null) {
            Furnace furn = (Furnace) e.getBlock().getState();
            ItemStack result = furn.getInventory().getSmelting();
            if (Util.equalItem(result, gemFurn.smelting)) {
                result = gemFurn.result;
                int rnt = Util.instance.rand(100);
                if (rnt >= GemPro.instance.socketChance) {
                    result = e.getSource();
                    if (GemPro.instance.socketFalseDisapper) {
                        result.setType(Material.AIR);
                    }
                }
            }

            e.setResult(result);
        }

    }

    @SuppressWarnings("deprecation")
    private boolean canFurn(Furnace furn) {
        ItemStack smelt = furn.getInventory().getSmelting().clone();
        ItemStack fuel = furn.getInventory().getFuel();
        if (smelt.getAmount() > 1) {
            return false;
        } else if (fuel != null && fuel.getType() != Material.AIR) {
            if (fuel.hasItemMeta() && fuel.getItemMeta().hasDisplayName()) {
                if (!fuel.getItemMeta().getLore().get(0).equals(fuel.getItemMeta().getDisplayName())) {
                    return false;
                } else {
                    GemOBJ gem = Util.instance.itemToGem(fuel);
                    if (gem != null) {
                        if (gem.type > 0) {
                            if (!GemPro.instance.canFurnList.get(gem.type.intValue() - 1).contains(smelt.getTypeId())) {
                                return false;
                            }
                        } else if (!GemPro.instance.canFurnList.get(0).contains(smelt.getTypeId()) && !GemPro.instance.canFurnList.get(1).contains(smelt.getTypeId())) {
                            return false;
                        }

                        if (gem.kind == 1) {
                            if (smelt.hasItemMeta() && (smelt.getItemMeta().hasEnchants() || gem.enchantmentEffect.isEmpty())) {
                                return false;
                            }

                            smelt.addUnsafeEnchantments(gem.enchantmentEffect);
                            this.furnaceMapHandler(furn.getBlock(), new furnaceListen.GemFurn(furn.getInventory().getSmelting(), smelt));
                            return true;
                        }

                        if (Util.instance.isRPGItem(smelt)) {
                            ItemMeta imSmelt = smelt.getItemMeta();
                            List lores = imSmelt.getLore();
                            if (lores.size() < 3) {
                                return false;
                            }

                            int socketRow;
                            if (((String) lores.get(2)).startsWith(Msg.socket.getText())) {
                                socketRow = 2;
                            } else {
                                Integer[] sockets = Util.instance.getLoreValue((String) lores.get(7));
                                socketRow = sockets != null && sockets.length != 0 ? sockets[0] : 0;

                                socketRow += 9;
                            }

                            int sockets1 = Util.instance.getLoreValue((String) lores.get(socketRow))[0];
                            if (sockets1 > 0) {
                                RPGItemOBJ rpgitem = Util.instance.itemToRPGItem(smelt);
                                if (gem.extraDamage != null) {
                                    rpgitem.extraDamage = gem.extraDamage;
                                }

                                if (gem.realDamage != null) {
                                    rpgitem.realDamage = gem.realDamage;
                                }

                                if (gem.critMultiple != null) {
                                    rpgitem.critMultiple = gem.critMultiple;
                                }

                                if (gem.ignoreAttack != null) {
                                    rpgitem.ignoreAttack = gem.ignoreAttack;
                                }

                                if (gem.extraDefense != null) {
                                    rpgitem.extraDefense = gem.extraDefense;
                                }

                                if (gem.suckBlood != null) {
                                    rpgitem.suckBlood = gem.suckBlood;
                                }

                                if (gem.potionEffect != null && !gem.potionEffect.isEmpty()) {
                                    if (rpgitem.potionEffect != null) {
                                        ArrayList poList = new ArrayList(gem.potionEffect);
                                        HashSet poNameList = new HashSet();
                                        Iterator i$ = gem.potionEffect.iterator();

                                        PotionOBJ potion;
                                        while (i$.hasNext()) {
                                            potion = (PotionOBJ) i$.next();
                                            poNameList.add(potion.target + potion.potionEffectName);
                                        }

                                        i$ = rpgitem.potionEffect.iterator();

                                        while (i$.hasNext()) {
                                            potion = (PotionOBJ) i$.next();
                                            if (!poNameList.contains(potion.target + potion.potionEffectName)) {
                                                poList.add(potion);
                                            }
                                        }

                                        rpgitem.potionEffect = new ArrayList(poList);
                                    } else {
                                        rpgitem.potionEffect = gem.potionEffect;
                                    }
                                }

                                rpgitem.socket = sockets1 - 1;
                                this.furnaceMapHandler(furn.getBlock(), new furnaceListen.GemFurn(furn.getInventory().getSmelting(), Util.instance.applyRPGLore(smelt, rpgitem)));
                                return true;
                            }
                        }
                    }

                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void furnaceMapHandler(final Block block, furnaceListen.GemFurn gemFurn) {
        furnItemList.put(block, gemFurn);
        GemPro.instance.getServer().getScheduler().scheduleSyncDelayedTask(GemPro.instance, new Runnable() {
            public void run() {
                furnaceListen.furnItemList.remove(block);
            }
        }, 220L);
    }

    class GemFurn {
        public final ItemStack smelting;
        public final ItemStack result;

        public GemFurn(ItemStack smelting, ItemStack result) {
            this.smelting = smelting;
            this.result = result;
        }
    }
}
