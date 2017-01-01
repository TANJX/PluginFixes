//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.EnumPack;

public enum ConfKey {
	extraDamage("附加伤害"),
	realDamage("直接伤害"),
	critMultiple("暴击"),
	ignoreAttack("闪避"),
	extraDefense("附加防御"),
	suckBlood("吸血"),
	type("类型"),
	potionEffect("药水效果");

	private final String text;

	ConfKey(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
