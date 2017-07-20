package io.loyloy.nicky;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class TagAPIListener implements Listener {
    @EventHandler
    public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
        Nick nick = new Nick(event.getNamedPlayer());

        if (nick.get() != null) {
            event.setTag(nick.get());
        }
    }
}
