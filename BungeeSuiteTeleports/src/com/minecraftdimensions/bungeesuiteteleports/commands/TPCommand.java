package com.minecraftdimensions.bungeesuiteteleports.commands;


import com.minecraftdimensions.bungeesuiteteleports.managers.TeleportsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        if ( !( sender instanceof Player ) ) {
            if ( args.length == 2 ) {
                Bukkit.getPlayer( args[0] ).teleport( Bukkit.getPlayer( args[1] ) );
            } else if ( args.length == 4 ) {
                Player p = Bukkit.getPlayer( args[0] );
                p.teleport( new Location( p.getWorld(), Double.parseDouble( args[1] ), Double.parseDouble( args[2] ), Double.parseDouble( args[3] ) ) );
            }
        }
        if ( args.length == 1 ) {
            TeleportsManager.teleportToPlayer( sender, sender.getName(), args[0] );
            return true;
        } else if ( args.length == 2 ) {
            TeleportsManager.teleportToPlayer( sender, args[0], args[1] );
            return true;
        }
        if ( args.length == 3 ) {
            String x = args[0];
            String y = args[1];
            String z = args[2];
            String loc = ( ( Player ) sender ).getWorld().getName() + "~!~" + x + "~!~" + y + "~!~" + z;
            TeleportsManager.teleportToLocation( sender.getName(), loc );
            return true;
        }
        if ( args.length == 5 ) {
            if ( Bukkit.getPlayer( args[0] ) != null ) {
                String x = args[1];
                String y = args[2];
                String z = args[3];
                String loc = args[4] + "~!~" + x + "~!~" + y + "~!~" + z;
                TeleportsManager.teleportToLocation( args[0], loc );
            }
            return true;
        }

        return false;
    }

}
