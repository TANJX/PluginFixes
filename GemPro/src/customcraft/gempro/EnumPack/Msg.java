//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.EnumPack;

public enum Msg {
	extraDamage("§e附加伤害§7: §3"),
	realDamage("§e直接伤害§7: §3"),
	critMultiple("§e暴击§7: §3"),
	ignoreAttack("§e闪避§7: §3"),
	extraDefense("§e附加防御§7: §3"),
	suckBlood("§e吸血§7: §3"),
	empty("无"),
	potionEffect("§e药水效果§7: §3"),
	potionDuration("持续"),
	potionLevel("等级"),
	chance("几率%"),
	effect("§9效果影响§a"),
	enchantGem("§6§l附魔宝石§7: §c"),
	generalGem("§6§l镶嵌宝石§7: §c"),
	weaponType("武器/工具"),
	armorType("盔甲"),
	generalType("通用"),
	colorGemName("§b§l"),
	potionPrefix("   §8*§5 "),
	onConsole("§c控制台不能这么做"),
	noPerm("§c你没有权限这么做"),
	notFoundPlayer("§c未找到此玩家"),
	notFoundGem("§c未找到此宝石"),
	sendGemToInv("宝石入库, 注意查收~"),
	sendGemToPlayer("成功发送§9<n>§a个§6<gem>§a给玩家§3"),
	noItemInHand("§c你手上没有拿着任何物品"),
	rpgItemLore("§eo§6§l⊙§b§lRPG武器§6§l⊙§eo"),
	processedItemLore("§eo§6§l⊙§9§l已加工§6§l⊙§eo"),
	punch("§6§l打孔数量§7: §3"),
	socket("§6§l凹槽§7: §3"),
	noBank("你没有银行账户"),
	pluginPrefix("§a"),
	punchReachLimit("打孔数目不得超过 §6"),
	noEnoughMoney("你的资金不足§6 "),
	paid("你支付了§6 "),
	cantPunch("此物品无法加工"),
	processedFaild("加工失败"),
	processedSuccess("加工成功, 现在可以进行打孔了~"),
	punchFaild("打孔失败"),
	punchSuccess("打孔成功, 可以进行镶嵌了哦~"),
	noEnoughItem("你缺少必须的物品或数量不足"),
	invalidNumber("无效数字"),
	loadSuccess("全部配置加载完毕"),
	max("最大");

	private final String text;

	Msg(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String getTextAppendPrefix() {
		return pluginPrefix.getText() + this.getText();
	}
}
