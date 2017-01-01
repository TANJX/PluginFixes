//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.Listen;

import customcraft.gempro.GemPro;
import customcraft.gempro.EnumPack.Msg;
import customcraft.gempro.Handler.Util;
import customcraft.gempro.OBJ.GemOBJ;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Cmd implements CommandExecutor {
	public Cmd() {
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1) {
			return false;
		} else {
			if(args[0].equalsIgnoreCase("reload")) {
				if(Util.instance.hasPerm(sender, "reload")) {
					GemPro.instance.loadFiles();
					sender.sendMessage(Msg.loadSuccess.getTextAppendPrefix());
				}
			} else {
				Player player;
				Integer item;
				ItemStack item1;
				if(args[0].equalsIgnoreCase("give")) {
					if(Util.instance.hasPerm(sender, "give")) {
						if(args.length <= 1) {
							return false;
						}

						player = GemPro.instance.getServer().getPlayerExact(args[1]);
						if(player == null) {
							sender.sendMessage(Msg.notFoundPlayer.getTextAppendPrefix());
						} else {
							item = 1;
							item1 = null;
							GemOBJ item4;
							if(args.length > 2) {
								item4 = Util.instance.getGemFromName(args[2]);
								if(item4 == null) {
									sender.sendMessage(Msg.notFoundGem.getTextAppendPrefix());
									return true;
								}

								if(args.length > 3) {
									item = Util.instance.getNumber(args[3]);
									if(item == null || item < 1) {
										sender.sendMessage(Msg.invalidNumber.getTextAppendPrefix());
										return true;
									}
								}
							} else {
								item4 = Util.instance.randomGem();
							}

							ItemStack item2 = Util.instance.spawnGem(item4);
							item2.setAmount(item);
							player.getInventory().addItem(item2);
							sender.sendMessage(Msg.sendGemToPlayer.getTextAppendPrefix().replace("<n>", item + "").replace("<gem>", item4.gemName) + player.getName());
							player.sendMessage(Msg.sendGemToInv.getTextAppendPrefix());
						}
					}
				} else if(args[0].equalsIgnoreCase("gemlist")) {
					if(Util.instance.hasPerm(sender, "gemlist")) {
						sender.sendMessage("§6镶嵌宝石: \n§3" + Arrays.asList(new Set[]{GemPro.instance.generalGems.getKeys(false)}).toString());
						sender.sendMessage("§6附魔宝石: \n§3" + Arrays.asList(new Set[]{GemPro.instance.enchantGems.getKeys(false)}).toString());
					}
				} else {
					if(!(sender instanceof Player)) {
						sender.sendMessage(Msg.onConsole.getTextAppendPrefix());
						return true;
					}

					player = (Player)sender;
					if(args[0].equalsIgnoreCase("spawn")) {
						if(Util.instance.hasPerm(sender, "spawn")) {
							item = 1;
							if(args.length > 1) {
								item = Util.instance.getNumber(args[1]);
								if(item == null || item < 1) {
									sender.sendMessage(Msg.invalidNumber.getTextAppendPrefix());
									return true;
								}
							}

							item1 = Util.instance.spawnGem(Util.instance.randomGem());
							item1.setAmount(item);
							player.getInventory().addItem(item1);
							sender.sendMessage(Msg.sendGemToInv.getTextAppendPrefix());
						}
					} else {
						ItemStack item3;
						if(args[0].equalsIgnoreCase("clear")) {
							if(Util.instance.hasPerm(sender, "clear")) {
								item3 = player.getItemInHand();
								if(item3 != null && item3.getType() != Material.AIR) {
									Util.instance.setLores(item3, null);
								} else {
									sender.sendMessage(Msg.noItemInHand.getTextAppendPrefix());
								}
							}
						} else if(args[0].equalsIgnoreCase("clearall")) {
							if(Util.instance.hasPerm(sender, "clearall")) {
								item3 = player.getItemInHand();
								if(item3 != null && item3.getType() != Material.AIR) {
									item3.setItemMeta(null);
								} else {
									sender.sendMessage(Msg.noItemInHand.getTextAppendPrefix());
								}
							}
						} else {
							if(!args[0].equalsIgnoreCase("punch")) {
								return false;
							}

							if(Util.instance.hasPerm(sender, "punch")) {
								if(player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
									if(Util.instance.canPunch(player.getItemInHand())) {
										Util.instance.punchItem(player, player.getItemInHand());
									} else {
										sender.sendMessage(Msg.cantPunch.getTextAppendPrefix());
									}
								} else {
									sender.sendMessage(Msg.noItemInHand.getTextAppendPrefix());
								}
							}
						}
					}
				}
			}

			return true;
		}
	}
}
