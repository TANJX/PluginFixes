package blainicus.MonsterApocalypse;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosionListener implements Listener {
    private final MonsterApocalypse plugin;
    double x, y, z, xdis, ydis, zdis;
    boolean xflag, yflag, zflag;

    public ExplosionListener(MonsterApocalypse instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (plugin.changeexplosions) {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (event.getEntity().getWorld().getName().equals(worldname)) break;
                if (i == plugin.worldnames.size() - 1) return;
            }
            Entity exploder = event.getEntity();
            if (plugin.wgexplosions && !plugin.getWGExplosion(exploder)) {
                event.setCancelled(true);
            }
            if (exploder instanceof Creeper) {
                event.setRadius((float) plugin.getMobRadius("Creeper"));
                event.setFire(plugin.getMobFireExplosion("Creeper"));
            }
            if (exploder instanceof Fireball) {
                event.setRadius((float) plugin.getMobRadius("Ghast"));
                event.setFire(plugin.getMobFireExplosion("Ghast"));
                if (plugin.wgexplosions && !plugin.getWGExplosion(exploder)) {
                    event.setCancelled(true);
                    exploder.remove();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplosion(EntityExplodeEvent event) {
        try {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (event.getEntity().getWorld().getName().equals(worldname)) break;
                if (i == plugin.worldnames.size() - 1) return;
            }
            Entity exploder = event.getEntity();
            if (plugin.wgexplosions && !plugin.getWGExplosion(exploder.getLocation())) {
                event.setCancelled(true);
            }
            if (exploder instanceof EnderDragon) {
                if (!plugin.enderdamage) {
                    event.setCancelled(true);
                }
            }
        } catch (NullPointerException e) {

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityPickup(EntityChangeBlockEvent event) {
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (event.getEntity().getWorld().getName().equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1) return;
        }
        Entity exploder = event.getEntity();
        if (exploder instanceof Enderman) {
            if (!plugin.enderblockdamage) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHit(ProjectileHitEvent e) {
        if (plugin.changeadvanced) {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (e.getEntity().getWorld().getName().equals(worldname)) break;
                if (i == plugin.worldnames.size() - 1) return;
            }
            Entity ent = e.getEntity();
            if (ent instanceof Arrow) {
                if (((Arrow) ent).getShooter() instanceof Skeleton) {
                    if (plugin.setoggle) {
                        if (plugin.spawnrand.nextDouble() * 100d <= plugin.sechance) {
                            if (plugin.skeletonradius <= 0 || plugin.getnearestplayerLEycount(ent, plugin.skeletonradius) <= 0) {
                                if (plugin.WGexplode(ent.getLocation(), (float) plugin.serad, plugin.sefire)) {
                                    ent.remove();
                                    return;
                                }
                            }
                        }
                    }
                    //TODO: THIS IS WHERE IT REALLY GOES
                    //blockbreaking(ent);

                }
                // player damage
                //blockbreaking(ent);
            }
        }
    }
/*	private void breakleft(Entity ent){
        plugin.waller.attemptpopblock(ent.getLocation().getBlockX()-1, ent.getLocation().getBlockY(), ent.getLocation().getBlockZ(), plugin.sbbdamage, ent.getLocation().getWorld());
	}
	private void breakright(Entity ent){
		plugin.waller.attemptpopblock(ent.getLocation().getBlockX()+1, ent.getLocation().getBlockY(), ent.getLocation().getBlockZ(), plugin.sbbdamage, ent.getLocation().getWorld());
	}
	private void breaktop(Entity ent){
		plugin.waller.attemptpopblock(ent.getLocation().getBlockX(), ent.getLocation().getBlockY()+1, ent.getLocation().getBlockZ(), plugin.sbbdamage, ent.getLocation().getWorld());
	}
	private void breakbot(Entity ent){
		plugin.waller.attemptpopblock(ent.getLocation().getBlockX(), ent.getLocation().getBlockY()-1, ent.getLocation().getBlockZ(), plugin.sbbdamage, ent.getLocation().getWorld());
	}
	private void breakfront(Entity ent){
		plugin.waller.attemptpopblock(ent.getLocation().getBlockX(), ent.getLocation().getBlockY(), ent.getLocation().getBlockZ()+1, plugin.sbbdamage, ent.getLocation().getWorld());
	}
	private void breakback(Entity ent){
		plugin.waller.attemptpopblock(ent.getLocation().getBlockX(), ent.getLocation().getBlockY(), ent.getLocation().getBlockZ()-1, plugin.sbbdamage, ent.getLocation().getWorld());
	}
	private void blockbreaking(Entity ent){
		if(!plugin.sbbenable||plugin.spawnrand.nextDouble()*100d>=plugin.sbbchance){
			return;
		}
		x=ent.getLocation().getX();
		y=ent.getLocation().getY();
		z=ent.getLocation().getZ();
		if(x>=0){
			if(x%1.0d>0.85d){
				xdis=1-x%1.0d;
				xflag=true;
			}else if(x%1.0d<0.15d){
				xdis=x%1.0d;
				xflag=false;
			}
		}else{
			if(x%1.0d>-0.15d){
				xdis=-x%1.0d;
				xflag=true;
			}else if(x%1.0d<-0.85d){
				xdis=1+x%1.0d;
				xflag=false;
			}
		}
		if(y>=0){
			if(y%1.0d>0.85d){
				ydis=1-y%1.0d;
				yflag=true;
			}else if(y%1.0d<0.15d){
				ydis=y%1.0d;
				yflag=false;
			}
		}else{
			if(y%1.0d>-0.15d){
				ydis=-y%1.0d;
				yflag=true;
			}else if(y%1.0d<-0.85d){
				ydis=1+y%1.0d;
				yflag=false;
			}
		}
		if(z>=0){
			if(z%1.0d>0.85d){
				zdis=1-z%1.0d;
				zflag=true;
			}else if(z%1.0d<0.15d){
				zdis=z%1.0d;
				zflag=false;
			}
		}else{
			if(z%1.0d>-0.15d){
				zdis=-z%1.0d;
				zflag=true;
			}else if(z%1.0d<-0.85d){
				zdis=1+z%1.0d;
				zflag=false;
			}
		}
		if(xdis<ydis){
			if(xdis<zdis){
				//if(xflag){
					breakright(ent);
					//}
				//else{
					breakleft(ent);
				//}
			}else{
			breakfront(ent);
			//}
			breakback(ent);
			//}
			}
		}
		else{
			if(ydis<zdis){
				//f(yflag){
					breaktop(ent);
					//}
				//else{
					breakbot(ent);
					//}
			}else{
			//	if(zflag){
					breakfront(ent);
				//	}
				//else{
					breakback(ent);
				//	}
			}
		}
	}*/
}
