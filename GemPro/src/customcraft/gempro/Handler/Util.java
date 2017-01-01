//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.Handler;

import customcraft.gempro.GemPro;
import customcraft.gempro.EnumPack.ConfKey;
import customcraft.gempro.EnumPack.Msg;
import customcraft.gempro.OBJ.EnchantmentEntry;
import customcraft.gempro.OBJ.GemOBJ;
import customcraft.gempro.OBJ.PotionOBJ;
import customcraft.gempro.OBJ.RPGItemOBJ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
	public static Util instance;

	public Util() {
		instance = this;
	}

	public GemOBJ getGemFromName(String gemName) {
		List gems = Arrays.asList(GemPro.instance.gemList.values().toArray());
		Iterator i$ = gems.iterator();

		GemOBJ gem;
		do {
			if(!i$.hasNext()) {
				return null;
			}

			Object o = i$.next();
			gem = (GemOBJ)o;
		} while(!gem.gemName.equalsIgnoreCase(gemName));

		return gem;
	}

	public GemOBJ randomGem() {
		List gems = Arrays.asList(GemPro.instance.gemList.values().toArray());
		return (GemOBJ)gems.get(this.rand(gems.size()));
	}

	public ItemStack spawnGem(GemOBJ gem) {
		ItemStack item = this.randomTexture();
		ArrayList lores = new ArrayList();
		lores.add(Msg.colorGemName.getText() + gem.gemName);
		String firstLine = gem.kind == 0?Msg.generalGem.getText():Msg.enchantGem.getText();
		switch(gem.type) {
			case 0:
				firstLine = firstLine + Msg.generalType.getText();
				break;
			case 1:
				firstLine = firstLine + Msg.weaponType.getText();
				break;
			case 2:
				firstLine = firstLine + Msg.armorType.getText();
		}

		lores.add(firstLine);
		if(gem.kind == 0) {
			String tempList = Msg.extraDamage.getText();
			Integer[] im;
			Integer[] newEnchMap;
			if(gem.extraDamage != null) {
				im = this.getLoreValue(gem.extraDamage);
				newEnchMap = this.getLoreValue(GemPro.instance.generalGems.getString(gem.gemName + "." + Msg.max.getText() + ConfKey.extraDamage.getText()));
				if(newEnchMap != null) {
					im = this.newValue(im, newEnchMap);
				}

				tempList = tempList + this.loreValueToString(im);
			} else {
				tempList = tempList + Msg.empty.getText();
			}

			lores.add(tempList);
			tempList = Msg.realDamage.getText();
			if(gem.realDamage != null) {
				im = this.getLoreValue(gem.realDamage);
				newEnchMap = this.getLoreValue(GemPro.instance.generalGems.getString(gem.gemName + "." + Msg.max.getText() + ConfKey.realDamage.getText()));
				if(newEnchMap != null) {
					im = this.newValue(im, newEnchMap);
				}

				tempList = tempList + this.loreValueToString(im);
			} else {
				tempList = tempList + Msg.empty.getText();
			}

			lores.add(tempList);
			tempList = Msg.critMultiple.getText();
			if(gem.critMultiple != null) {
				im = this.getLoreValue(gem.critMultiple);
				newEnchMap = this.getLoreValue(GemPro.instance.generalGems.getString(gem.gemName + "." + Msg.max.getText() + ConfKey.critMultiple.getText()));
				if(newEnchMap != null) {
					im = this.newValue(im, newEnchMap);
				}

				tempList = tempList + this.loreValueToString(im);
			} else {
				tempList = tempList + Msg.empty.getText();
			}

			lores.add(tempList);
			tempList = Msg.ignoreAttack.getText();
			if(gem.ignoreAttack != null) {
				im = this.getLoreValue(gem.ignoreAttack);
				newEnchMap = this.getLoreValue(GemPro.instance.generalGems.getString(gem.gemName + "." + Msg.max.getText() + ConfKey.ignoreAttack.getText()));
				if(newEnchMap != null) {
					im = this.newValue(im, newEnchMap);
				}

				tempList = tempList + this.loreValueToString(im);
			} else {
				tempList = tempList + Msg.empty.getText();
			}

			lores.add(tempList);
			tempList = Msg.extraDefense.getText();
			if(gem.extraDefense != null) {
				im = this.getLoreValue(gem.extraDefense);
				newEnchMap = this.getLoreValue(GemPro.instance.generalGems.getString(gem.gemName + "." + Msg.max.getText() + ConfKey.extraDefense.getText()));
				if(newEnchMap != null) {
					im = this.newValue(im, newEnchMap);
				}

				tempList = tempList + this.loreValueToString(im);
			} else {
				tempList = tempList + Msg.empty.getText();
			}

			lores.add(tempList);
			tempList = Msg.suckBlood.getText();
			if(gem.suckBlood != null) {
				im = this.getLoreValue(gem.suckBlood);
				newEnchMap = this.getLoreValue(GemPro.instance.generalGems.getString(gem.gemName + "." + Msg.max.getText() + ConfKey.suckBlood.getText()));
				if(newEnchMap != null) {
					im = this.newValue(im, newEnchMap);
				}

				tempList = tempList + this.loreValueToString(im);
			} else {
				tempList = tempList + Msg.empty.getText();
			}

			lores.add(tempList);
			if(gem.potionEffect != null && !gem.potionEffect.isEmpty()) {
				List i$ = GemPro.instance.generalGems.getStringList(gem.gemName + "." + ConfKey.potionEffect.getText());
				lores.add(Msg.potionEffect.getText());

				PotionOBJ ench;
				int chance;
				int newLevel1;
				int duration1;
				for(Iterator row = gem.potionEffect.iterator(); row.hasNext(); lores.add(Msg.potionPrefix.getText() + ench.target + ench.potionEffectName + ":" + Msg.potionLevel.getText() + newLevel1 + ":" + Msg.potionDuration.getText() + duration1 + ":" + Msg.chance.getText() + chance)) {
					ench = (PotionOBJ)row.next();
					String oldLevel = null;

					for (Object anI$ : i$) {
						String duration = (String) anI$;
						if (duration.startsWith(Msg.max.getText() + ench.target + ench.potionEffectName)) {
							oldLevel = duration.replace(Msg.max.getText(), "");
						}
					}

					newLevel1 = ench.level;
					duration1 = ench.duration;
					chance = ench.chance;
					if(oldLevel != null) {
						PotionOBJ maxPot = GemPro.instance.getPotionOBJFromStandardString(oldLevel);
						if(maxPot != null) {
							Integer[] minPotValue = new Integer[]{ench.level, ench.duration, ench.chance};
							Integer[] maxPotValue = new Integer[]{maxPot.level, maxPot.duration, maxPot.chance};
							Integer[] value = this.newValue(minPotValue, maxPotValue);
							newLevel1 = value[0];
							duration1 = value[1];
							chance = value[2];
						}
					}
				}
			} else {
				lores.add(Msg.potionEffect.getText() + Msg.empty.getText());
			}
		}

		ItemMeta im1 = item.getItemMeta();
		im1.setLore(lores);
		im1.setDisplayName(Msg.colorGemName.getText() + gem.gemName);
		item.setItemMeta(im1);
		if(gem.enchantmentEffect != null) {
			HashMap newEnchMap1 = new HashMap(gem.enchantmentEffect);
			ArrayList tempList1 = new ArrayList(GemPro.instance.enchantGems.getStringList(gem.gemName + ".附魔"));

			for (Object aTempList1 : tempList1) {
				String row1 = (String) aTempList1;
				if (row1.startsWith(Msg.max.getText())) {
					row1 = row1.replace(Msg.max.getText(), "");
					EnchantmentEntry ench1 = GemPro.instance.getEnchantmentFromStandardString(row1);
					if (ench1 != null && gem.enchantmentEffect.containsKey(ench1.enchantment)) {
						Integer[] oldLevel1 = new Integer[]{gem.enchantmentEffect.get(ench1.enchantment)};
						Integer[] newLevel2 = new Integer[]{ench1.level};
						newLevel2 = this.newValue(oldLevel1, newLevel2);
						newEnchMap1.put(ench1.enchantment, newLevel2[0]);
					}
				}
			}

			item.addUnsafeEnchantments(newEnchMap1);
		}

		return item;
	}

	private Integer[] stringArrayToIntegerArray(String... str) {
		Integer[] array = new Integer[str.length];

		for(int x = 0; x < array.length; ++x) {
			array[x] = this.getNumber(str[x]);
		}

		return array;
	}

	private String loreValueToString(Integer[] value) {
		return value.length == 1?Msg.chance.getText() + value[0]:Msg.chance.getText() + value[0] + Msg.effect.getText() + value[1] + "-" + value[2];
	}

	private Integer[] newValue(Integer[] min, Integer[] max) {
		for(int index = 0; index < min.length; ++index) {
			Integer[] value = new Integer[]{min[index], max[index]};
			value = this.sortNumber(value);
			min[index] = this.rand(value[1] - value[0] + 1) + value[0];
		}

		return min;
	}

	private Integer[] sortNumber(Integer[] array) {
		if(array[0] <= array[1]) {
			return array;
		} else {
			Util.Swap swap = new Util.Swap(array[0], array[1]);
			array[0] = (Integer)swap.getA();
			array[1] = (Integer)swap.getB();
			return array;
		}
	}

	private ItemStack randomTexture() {
		return new ItemStack(GemPro.instance.gemTexture.get(this.rand(GemPro.instance.gemTexture.size())));
	}

	public int rand(int max) {
		boolean plus = max >= 0;
		int num = (new Random()).nextInt(Math.abs(max));
		return plus?num:-num;
	}

	@SuppressWarnings("deprecation")
	public ItemStack validItem(int id, short data) {
		return Material.getMaterial(id) != null && data >= 0?new ItemStack(Material.getMaterial(id), 1, data):null;
	}

	public ItemStack validItem(String id, short data) {
		return Material.matchMaterial(id) != null && data >= 0?new ItemStack(Material.matchMaterial(id), 1, data):null;
	}

	public String colorCut(String text) {
		return ChatColor.stripColor(text);
	}

	public Integer getNumber(String string) {
		Integer number;
		try {
			number = Integer.valueOf(string);
		} catch (NumberFormatException var4) {
			number = null;
		}

		return number;
	}

	public Integer getPlusNumber(String string) {
		Integer number = this.getNumber(string);
		if(number != null && number < 0) {
			number = null;
		}

		return number;
	}

	public Double getDoubleNumber(String string) {
		Double number;
		try {
			number = Double.valueOf(string);
		} catch (NumberFormatException var4) {
			number = null;
		}

		return number;
	}

	public Double getDoublePlusNumber(String string) {
		Double number = this.getDoubleNumber(string);
		if(number != null && number < 0.0D) {
			number = null;
		}

		return number;
	}

	private void setLore(ItemStack item, int row, String text) {
		ItemMeta im = item.getItemMeta();
		List lores = im.getLore();
		lores.set(row, text);
		im.setLore(lores);
		item.setItemMeta(im);
	}

	public void setLores(ItemStack item, List<String> lores) {
		ItemMeta im = item.getItemMeta();
		im.setLore(lores);
		item.setItemMeta(im);
	}

	private void addLores(ItemStack item, int index, String... lore) {
		Object lores;
		lores = item.hasItemMeta() && item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList();

		((List)lores).addAll(index, Arrays.asList(lore));
		this.setLores(item, (List)lores);
	}

	private void removeLore(ItemStack item, int index) {
		if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
			List lores = item.getItemMeta().getLore();
			if(index + 1 > lores.size() || index < 0) {
				return;
			}

			lores.remove(index);
			this.setLores(item, lores);
		}

	}

	public GemOBJ itemToGem(ItemStack item) {
		if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
			List lores = item.getItemMeta().getLore();
			if(lores.size() < 2) {
				return null;
			} else {
				String gemName = (String)lores.get(0);
				String lore = (String)lores.get(1);
				Integer kind;
				if(lore.startsWith(Msg.generalGem.getText())) {
					kind = 0;
					if(lores.size() < 9) {
						return null;
					}
				} else {
					if(!lore.startsWith(Msg.enchantGem.getText())) {
						return null;
					}

					kind = 1;
				}

				lore = this.removeLorePrefix(lore);
				Integer type;
				if(lore.equals(Msg.generalType.getText())) {
					type = 0;
				} else if(lore.equals(Msg.weaponType.getText())) {
					type = 1;
				} else {
					type = 2;
				}

				String extraDamage;
				String realDamage;
				String critMultiple;
				String ignoreAttack;
				String extraDefense;
				String suckBlood;
				ArrayList potionEffect;
				Map enchantmentEffect;
				if(kind == 0) {
					extraDamage = this.removeLorePrefixAndDesc((String)lores.get(2));
					realDamage = this.removeLorePrefixAndDesc((String)lores.get(3));
					critMultiple = this.removeLorePrefixAndDesc((String)lores.get(4));
					ignoreAttack = this.removeLorePrefixAndDesc((String)lores.get(5));
					extraDefense = this.removeLorePrefixAndDesc((String)lores.get(6));
					suckBlood = this.removeLorePrefixAndDesc((String)lores.get(7));
					enchantmentEffect = null;
					if(lores.size() == 9) {
						potionEffect = null;
					} else {
						potionEffect = new ArrayList();

						for(int x = 9; x < lores.size(); ++x) {
							if(((String)lores.get(x)).startsWith(Msg.potionPrefix.getText())) {
								String target = "";
								String string = ((String)lores.get(x)).replace(Msg.potionPrefix.getText(), "");
								if(string.startsWith("-")) {
									target = "-";
									string = string.replaceFirst(target, "");
								}

								string = string.replace(Msg.potionLevel.getText(), "").replace(Msg.potionDuration.getText(), "").replace(Msg.chance.getText(), "");
								String[] str = string.split(":");
								potionEffect.add(new PotionOBJ(str[0], target, this.getPlusNumber(str[1]), this.getPlusNumber(str[2]), this.getPlusNumber(str[3])));
							}
						}
					}
				} else {
					suckBlood = null;
					extraDefense = null;
					ignoreAttack = null;
					critMultiple = null;
					realDamage = null;
					extraDamage = null;
					gemName = null;
					potionEffect = null;
					enchantmentEffect = item.getItemMeta().hasEnchants() ? item.getItemMeta().getEnchants() : null;
				}

				return new GemOBJ(gemName, kind, extraDamage, realDamage, critMultiple, ignoreAttack, extraDefense, suckBlood, potionEffect, enchantmentEffect, type);
			}
		} else {
			return null;
		}
	}

	public static boolean itemEquals(ItemStack stack1, ItemStack stack2) {
		return (stack1 != null && stack2 != null) && (stack1 == stack2 || stack1.getType() == stack2.getType() && stack1.getDurability() == stack2.getDurability() && stack1.hasItemMeta() == stack2.hasItemMeta() && (!stack1.hasItemMeta() || Bukkit.getItemFactory().equals(stack1.getItemMeta(), stack2.getItemMeta())));
	}

	public RPGItemOBJ itemToRPGItem(ItemStack item) {
		if(this.isRPGItem(item)) {
			ArrayList potionEffect = new ArrayList();
			Object enchantmentEffect = new HashMap();
			ArrayList desc = new ArrayList();
			List lores = item.getItemMeta().getLore();
			if(!item.getItemMeta().hasEnchants()) {
				enchantmentEffect = item.getEnchantments();
			}

			String extraDamage;
			String realDamage;
			String critMultiple;
			String ignoreAttack;
			String extraDefense;
			String suckBlood;
			Integer RPGLoreCount;
			if(lores.size() > 3) {
				extraDamage = this.removeLorePrefixAndDesc((String)lores.get(1));
				realDamage = this.removeLorePrefixAndDesc((String)lores.get(2));
				critMultiple = this.removeLorePrefixAndDesc((String)lores.get(3));
				ignoreAttack = this.removeLorePrefixAndDesc((String)lores.get(4));
				extraDefense = this.removeLorePrefixAndDesc((String)lores.get(5));
				suckBlood = this.removeLorePrefixAndDesc((String)lores.get(6));
				Integer x = this.getNumber(this.removeLorePrefixAndDesc((String)lores.get(7)));
				if(x == null) {
					x = 0;
				}

				for(int x1 = 0; x1 < x; ++x1) {
					String lore = ((String)lores.get(x1 + 8)).replace(Msg.potionPrefix.getText(), "");
					String target = "";
					if(lore.startsWith("-")) {
						target = "-";
						lore = lore.replaceFirst(target, "");
					}

					lore = lore.replace(Msg.potionLevel.getText(), "").replaceAll(Msg.potionDuration.getText(), "").replaceAll(Msg.chance.getText(), "");

					try {
						String[] e = lore.split(":");
						potionEffect.add(new PotionOBJ(e[0], target, this.getNumber(e[1]), this.getNumber(e[2]), this.getNumber(e[3])));
					} catch (Exception ignored) {
					}
				}

				RPGLoreCount = 8 + x;
			} else {
				suckBlood = null;
				extraDefense = null;
				ignoreAttack = null;
				critMultiple = null;
				realDamage = null;
				extraDamage = null;
				RPGLoreCount = 1;
			}

			Integer punch = this.getNumber(this.removeLorePrefix((String)lores.get(RPGLoreCount)));
			RPGLoreCount = RPGLoreCount + 1;
			Integer socket = this.getNumber(this.removeLorePrefix((String)lores.get(RPGLoreCount)));
			RPGLoreCount = RPGLoreCount + 1;

			for(int var21 = RPGLoreCount; var21 < lores.size(); ++var21) {
				desc.add(lores.get(var21));
			}

			return new RPGItemOBJ(extraDamage, realDamage, critMultiple, ignoreAttack, extraDefense, suckBlood, potionEffect, punch, socket, desc, (Map)enchantmentEffect);
		} else {
			return null;
		}
	}

	private String removeLorePrefix(String lore) {
		if(lore != null && lore.contains(":")) {
			String str;
			try {
				str = lore.split(": §3")[1];
			} catch (ArrayIndexOutOfBoundsException var6) {
				try {
					str = lore.split(": §c")[1];
				} catch (ArrayIndexOutOfBoundsException var5) {
					str = lore.split(": ")[1];
				}
			}

			if(str.equals(Msg.empty.getText())) {
				str = null;
			}

			return str;
		} else {
			return lore;
		}
	}

	private String removeLorePrefixAndDesc(String lore) {
		String str = this.removeLorePrefix(lore);
		if(str != null) {
			str = str.replace(Msg.chance.getText(), "").replace(Msg.effect.getText(), "~");
		}

		return str;
	}

	public Integer[] getLoreValue(String lore) {
		String str = this.removeLorePrefix(lore);
		if(str == null) {
			return null;
		} else {
			str = this.replaceLoreDescFormat(str);
			if(str.contains("~")) {
				String[] array2 = str.split("~");
				String[] part2 = array2[1].split("-");
				Integer[] array1 = new Integer[]{this.getPlusNumber(array2[0]), this.getNumber(part2[0]), this.getNumber(part2[1])};
				return array1;
			} else {
				Integer[] array = new Integer[]{this.getPlusNumber(str)};
				return array;
			}
		}
	}

	private String replaceLoreDescFormat(String lore) {
		return lore == null?null:lore.replace(Msg.chance.getText(), "").replace(Msg.effect.getText(), "~");
	}

	public boolean isRPGItem(ItemStack item) {
		return item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().get(0).equals(Msg.rpgItemLore.getText());
	}

	private boolean isProcessed(ItemStack item) {
		return item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().get(0).equals(Msg.processedItemLore.getText());
	}

	public static boolean isGemTexture(Material item) {
		if(item == null) {
			return false;
		} else {
			Iterator i$ = GemPro.instance.gemTexture.iterator();

			ItemStack gem;
			do {
				if(!i$.hasNext()) {
					return false;
				}

				gem = (ItemStack)i$.next();
			} while(item != gem.getType());

			return true;
		}
	}

	public static boolean isGemTexture(ItemStack item) {
		Iterator i$ = GemPro.instance.gemTexture.iterator();

		ItemStack gem;
		do {
			if(!i$.hasNext()) {
				return false;
			}

			gem = (ItemStack)i$.next();
			System.out.println(gem.getType().name() + ":" + gem.getDurability() + " - " + item.getType().name() + ":" + item.getDurability());
		} while(!equalItem(gem, item));

		return true;
	}

	public static boolean equalItem(ItemStack stack1, ItemStack stack2) {
		return (stack1 != null && stack2 != null) && (stack1 == stack2 || stack1.getType() == stack2.getType() && stack1.getDurability() == stack2.getDurability() && stack1.hasItemMeta() == stack2.hasItemMeta() && (!stack1.hasItemMeta() || Bukkit.getItemFactory().equals(stack1.getItemMeta(), stack2.getItemMeta())));
	}

	public ItemStack applyRPGLore(ItemStack item, RPGItemOBJ rpgitem) {
		ItemMeta im = item.getItemMeta();
		ArrayList lores = new ArrayList();
		lores.add(Msg.rpgItemLore.getText());
		String extraDamage = Msg.extraDamage.getText();
		String realDamage = Msg.realDamage.getText();
		String critMultiple = Msg.critMultiple.getText();
		String ignoreAttack = Msg.ignoreAttack.getText();
		String extraDefense = Msg.extraDefense.getText();
		String suckBlood = Msg.suckBlood.getText();
		extraDamage = rpgitem.extraDamage != null ? extraDamage + Msg.chance.getText() + rpgitem.extraDamage.replaceFirst("~", Msg.effect.getText()) : extraDamage + Msg.empty.getText();

		realDamage = rpgitem.realDamage != null ? realDamage + Msg.chance.getText() + rpgitem.realDamage.replaceFirst("~", Msg.effect.getText()) : realDamage + Msg.empty.getText();

		critMultiple = rpgitem.critMultiple != null ? critMultiple + Msg.chance.getText() + rpgitem.critMultiple.replaceFirst("~", Msg.effect.getText()) : critMultiple + Msg.empty.getText();

		ignoreAttack = rpgitem.ignoreAttack != null ? ignoreAttack + Msg.chance.getText() + rpgitem.ignoreAttack.replaceFirst("~", Msg.effect.getText()) : ignoreAttack + Msg.empty.getText();

		extraDefense = rpgitem.extraDefense != null ? extraDefense + Msg.chance.getText() + rpgitem.extraDefense.replaceFirst("~", Msg.effect.getText()) : extraDefense + Msg.empty.getText();

		suckBlood = rpgitem.suckBlood != null ? suckBlood + Msg.chance.getText() + rpgitem.suckBlood.replaceFirst("~", Msg.effect.getText()) : suckBlood + Msg.empty.getText();

		lores.add(extraDamage);
		lores.add(realDamage);
		lores.add(critMultiple);
		lores.add(ignoreAttack);
		lores.add(extraDefense);
		lores.add(suckBlood);
		String potionLore = Msg.potionEffect.getText();
		if(rpgitem.potionEffect != null && !rpgitem.potionEffect.isEmpty()) {
			potionLore = potionLore + rpgitem.potionEffect.size();
			lores.add(potionLore);

			for(int x = 0; x < rpgitem.potionEffect.size(); ++x) {
				lores.add(Msg.potionPrefix.getText() + rpgitem.potionEffect.get(x).target + rpgitem.potionEffect.get(x).potionEffectName + ":" + Msg.potionLevel.getText() + rpgitem.potionEffect.get(x).level + ":" + Msg.potionDuration.getText() + rpgitem.potionEffect.get(x).duration + ":" + Msg.chance.getText() + rpgitem.potionEffect.get(x).chance);
			}
		} else {
			potionLore = potionLore + Msg.empty.getText();
			lores.add(potionLore);
		}

		lores.add(Msg.punch.getText() + rpgitem.punch);
		lores.add(Msg.socket.getText() + rpgitem.socket);
		lores.addAll(rpgitem.desc);
		im.setLore(lores);
		item.setItemMeta(im);
		return item;
	}

	public void info(String message) {
		this.info(Bukkit.getConsoleSender(), message);
	}

	private void info(CommandSender sender, String message) {
		sender.sendMessage(Msg.pluginPrefix.getText() + message);
	}

	public boolean punchItem(Player player, ItemStack item) {
		RPGItemOBJ rpgitem = this.itemToRPGItem(item);
		if(rpgitem != null && rpgitem.punch >= GemPro.instance.punchMaxAmount) {
			player.sendMessage(Msg.punchReachLimit.getTextAppendPrefix() + GemPro.instance.punchMaxAmount);
			return false;
		} else {
			this.punchItem(player, item, rpgitem);
			return true;
		}
	}

	private void punchItem(Player player, ItemStack item, RPGItemOBJ rpgitem) {
		if(!this.isProcessed(item) && !this.isRPGItem(item)) {
			if(this.hasMoney(player, GemPro.instance.processedNeedMoney)) {
				if(this.hasItem(player.getInventory(), GemPro.instance.processedNeedItem, GemPro.instance.processedNeedItemAmount)) {
					if(this.payMoney(player, GemPro.instance.processedNeedMoney) && this.payItem(player.getInventory(), GemPro.instance.processedNeedItem, GemPro.instance.processedNeedItemAmount)) {
						if(GemPro.instance.processedChance > instance.rand(100)) {
							this.addLores(item, 0, Msg.processedItemLore.getText());
							player.sendMessage(Msg.processedSuccess.getTextAppendPrefix());
						} else {
							if(GemPro.instance.processedFaildDisappear) {
								item.setType(Material.AIR);
							}

							player.sendMessage(Msg.processedFaild.getTextAppendPrefix());
						}
					}
				} else {
					player.sendMessage(Msg.noEnoughItem.getTextAppendPrefix());
				}
			}
		} else if(this.hasMoney(player, GemPro.instance.punchNeedMoney)) {
			if(this.hasItem(player.getInventory(), GemPro.instance.punchNeedItem, GemPro.instance.punchNeedItemAmount)) {
				if(this.payMoney(player, GemPro.instance.punchNeedMoney) && this.payItem(player.getInventory(), GemPro.instance.punchNeedItem, GemPro.instance.punchNeedItemAmount)) {
					if(GemPro.instance.punchChance > instance.rand(100)) {
						if(rpgitem != null) {
							this.setLore(item, 7 + rpgitem.potionEffect.size() + 1, Msg.punch.getText() + (rpgitem.punch + 1));
							this.setLore(item, 7 + rpgitem.potionEffect.size() + 2, Msg.socket.getText() + (rpgitem.socket + 1));
						} else {
							this.removeLore(item, 0);
							GemPro.instance.soulboundItem(player, item);
							Object lores = new ArrayList();
							if(item.getItemMeta().hasLore()) {
								lores = item.getItemMeta().getLore();
							}

							Object enchantmentEffect = new HashMap();
							if(item.getItemMeta().hasEnchants()) {
								enchantmentEffect = item.getEnchantments();
							}

							this.applyRPGLore(item, new RPGItemOBJ(null, null, null, null, null, null, null, 1, 1, (List)lores, (Map)enchantmentEffect));
						}

						player.sendMessage(Msg.punchSuccess.getTextAppendPrefix());
					} else {
						if(GemPro.instance.punchFaildDisappear) {
							item.setType(Material.AIR);
						}

						player.sendMessage(Msg.punchFaild.getTextAppendPrefix());
					}
				}
			} else {
				player.sendMessage(Msg.noEnoughItem.getTextAppendPrefix());
			}
		}

	}

	@SuppressWarnings("deprecation")
	private boolean payMoney(Player player, double money) {
		if(GemPro.instance.economy != null && money != 0.0D) {
			if(!GemPro.instance.economy.hasAccount(player.getName())) {
				player.sendMessage(Msg.noBank.getTextAppendPrefix());
			} else {
				if(GemPro.instance.economy.getBalance(player.getName()) >= money) {
					GemPro.instance.economy.withdrawPlayer(player.getName(), money);
					player.sendMessage(Msg.paid.getTextAppendPrefix() + GemPro.instance.economy.format(money));
					return true;
				}

				player.sendMessage(Msg.noEnoughMoney.getTextAppendPrefix() + GemPro.instance.economy.format(money));
			}

			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	private boolean hasMoney(Player player, double money) {
		if(GemPro.instance.economy != null && money != 0.0D) {
			if(!GemPro.instance.economy.hasAccount(player.getName())) {
				player.sendMessage(Msg.noBank.getTextAppendPrefix());
			} else {
				if(GemPro.instance.economy.getBalance(player.getName()) >= money) {
					return true;
				}

				player.sendMessage(Msg.noEnoughMoney.getTextAppendPrefix() + GemPro.instance.economy.format(money));
			}

			return false;
		} else {
			return true;
		}
	}

	private boolean payItem(Inventory inv, ItemStack needItem, int needAmount) {
		if(needItem == null) {
			return true;
		} else {
			int index = inv.first(needItem.getType());
			if(index != -1) {
				int amount = inv.getItem(index).getAmount();
				if(amount > needAmount) {
					inv.getItem(index).setAmount(amount - needAmount);
				} else {
					if(amount != needAmount) {
						return false;
					}

					inv.setItem(index, null);
				}

				return true;
			} else {
				return false;
			}
		}
	}

	private boolean hasItem(Inventory inv, ItemStack needItem, int needAmount) {
		if(needItem == null) {
			return true;
		} else {
			int index = inv.first(needItem.getType());
			if(index != -1) {
				int amount = inv.getItem(index).getAmount();
				if(amount >= needAmount) {
					return true;
				}
			}

			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public boolean canPunch(ItemStack item) {
		return item.getAmount() == 1 && (GemPro.instance.punchItemList.contains(item.getTypeId() + ":" + item.getDurability()) || GemPro.instance.punchItemList.contains(item.getTypeId() + ""));
	}

	public boolean hasPerm(CommandSender sender, String perm) {
		if(sender.hasPermission("gempro." + perm)) {
			return true;
		} else {
			sender.sendMessage(Msg.noPerm.getTextAppendPrefix());
			return false;
		}
	}

	class Swap<T> {
		private final T a;
		private T b;

		public Swap(T var1, T a) {
			this.a = a;
			this.b = b;
		}

		public T getA() {
			return this.b;
		}

		public T getB() {
			return this.a;
		}
	}
}
