//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tigerhix.signs;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public Main() {
    }

    public void onEnable() {
        this.getCommand("signedit").setExecutor(new Commands(this));
        new Listeners(this);
    }
}
