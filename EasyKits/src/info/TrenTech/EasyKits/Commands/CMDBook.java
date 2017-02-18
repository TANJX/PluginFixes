package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Book;
import info.TrenTech.EasyKits.Utils.Notifications;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDBook {

    public static void execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Notifications notify = new Notifications("Not-Player", null, sender.getName(), 0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }

        if (!sender.hasPermission("EasyKits.cmd.book")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }

        Player player = (Player) sender;
        ItemStack book = Book.getBook("EasyKits", "List of Kits!");
        player.getInventory().addItem(book);
    }

}
