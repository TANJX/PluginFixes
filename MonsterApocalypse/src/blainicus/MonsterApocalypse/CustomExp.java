package blainicus.MonsterApocalypse;
/*
 * Created by Mars Tan on 2/6/2017.
 * Copyright ISOTOPE Studio
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.craftbukkit.v1_9_R2.event.CraftEventFactory;

import javax.annotation.Nullable;
import java.util.*;

public class CustomExp extends Explosion {

    private final boolean a;
    private final boolean b;
    private final Random c = new Random();
    private final World world;
    private final double posX;
    private final double posY;
    private final double posZ;
    public final Entity source;
    private final float size;
    private final List<BlockPosition> blocks = Lists.newArrayList();
    private final Map<EntityHuman, Vec3D> k = Maps.newHashMap();
    public boolean wasCanceled = false;

    public CustomExp(World world, Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        super(world, entity, d0, d1, d2, f, flag, flag1);
        this.world = world;
        this.source = entity;
        this.size = (float) Math.max((double) f, 0.0D);
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
        this.a = flag;
        this.b = flag1;
    }

    @Override
    public void a() {
        if (this.size >= 0.1F) {
            HashSet hashset = Sets.newHashSet();

            int i;
            int j;
            for (int f3 = 0; f3 < 16; ++f3) {
                for (i = 0; i < 16; ++i) {
                    for (j = 0; j < 16; ++j) {
                        if (f3 == 0 || f3 == 15 || i == 0 || i == 15 || j == 0 || j == 15) {
                            double d0 = (double) ((float) f3 / 15.0F * 2.0F - 1.0F);
                            double d1 = (double) ((float) i / 15.0F * 2.0F - 1.0F);
                            double d2 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 /= d3;
                            d1 /= d3;
                            d2 /= d3;
                            float f = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
                            double d4 = this.posX;
                            double d5 = this.posY;

                            for (double d6 = this.posZ; f > 0.0F; f -= 0.22500001F) {
                                BlockPosition blockposition = new BlockPosition(d4, d5, d6);
                                IBlockData iblockdata = this.world.getType(blockposition);
                                if (iblockdata.getMaterial() != Material.AIR) {
                                    float f2 = this.source != null ? this.source.a(this, this.world, blockposition, iblockdata) : iblockdata.getBlock().a((Entity) null);
                                    f -= (f2 + 0.3F) * 0.3F;
                                }

                                if (f > 0.0F && (this.source == null || this.source.a(this, this.world, blockposition, iblockdata, f)) && blockposition.getY() < 256 && blockposition.getY() >= 0) {
                                    hashset.add(blockposition);
                                }

                                d4 += d0 * 0.30000001192092896D;
                                d5 += d1 * 0.30000001192092896D;
                                d6 += d2 * 0.30000001192092896D;
                            }
                        }
                    }
                }
            }

            this.blocks.addAll(hashset);
            float var49 = this.size * 2.0F;
            i = MathHelper.floor(this.posX - (double) var49 - 1.0D);
            j = MathHelper.floor(this.posX + (double) var49 + 1.0D);
            int l = MathHelper.floor(this.posY - (double) var49 - 1.0D);
            int i1 = MathHelper.floor(this.posY + (double) var49 + 1.0D);
            int j1 = MathHelper.floor(this.posZ - (double) var49 - 1.0D);
            int k1 = MathHelper.floor(this.posZ + (double) var49 + 1.0D);
            List list = this.world.getEntities(this.source, new AxisAlignedBB((double) i, (double) l, (double) j1, (double) j, (double) i1, (double) k1));
            Vec3D vec3d = new Vec3D(this.posX, this.posY, this.posZ);

            for (int l1 = 0; l1 < list.size(); ++l1) {
                Entity entity = (Entity) list.get(l1);
                if (!(entity instanceof EntityItem))
                    if (!entity.br()) {
                        double d7 = entity.f(this.posX, this.posY, this.posZ) / (double) var49;
                        if (d7 <= 1.0D) {
                            double d8 = entity.locX - this.posX;
                            double d9 = entity.locY + (double) entity.getHeadHeight() - this.posY;
                            double d10 = entity.locZ - this.posZ;
                            double d11 = (double) MathHelper.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
                            if (d11 != 0.0D) {
                                d8 /= d11;
                                d9 /= d11;
                                d10 /= d11;
                                double d12 = (double) this.world.a(vec3d, entity.getBoundingBox());
                                double d13 = (1.0D - d7) * d12;
                                CraftEventFactory.entityDamage = this.source;
                                entity.forceExplosionKnockback = false;
                                boolean wasDamaged = entity.damageEntity(DamageSource.explosion(this), (float) ((int) ((d13 * d13 + d13) / 2.0D * 7.0D * (double) var49 + 1.0D)));
                                CraftEventFactory.entityDamage = null;
                                if (wasDamaged || entity instanceof EntityTNTPrimed || entity instanceof EntityFallingBlock || entity.forceExplosionKnockback) {
                                    double d14 = 1.0D;
                                    if (entity instanceof EntityLiving) {
                                        d14 = EnchantmentProtection.a((EntityLiving) entity, d13);
                                    }

                                    entity.motX += d8 * d14;
                                    entity.motY += d9 * d14;
                                    entity.motZ += d10 * d14;
                                    if (entity instanceof EntityHuman) {
                                        EntityHuman entityhuman = (EntityHuman) entity;
                                        if (!entityhuman.isSpectator() && (!entityhuman.l_() || !entityhuman.abilities.isFlying)) {
                                            this.k.put(entityhuman, new Vec3D(d8 * d13, d9 * d13, d10 * d13));
                                        }
                                    }
                                }
                            }
                        }
                    }
            }

        }
    }

    public static Explosion createExplosion(net.minecraft.server.v1_9_R2.World world, @Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        Explosion explosion = new Explosion(world, entity, d0, d1, d2, f, flag, flag1);
        explosion.a();
        explosion.a(true);
        return explosion;
    }

    public static Explosion createExplosion(World world, List<EntityHuman> players, @Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        Explosion explosion = new Explosion(world, entity, d0, d1, d2, f, flag, flag1);
        explosion.a();
        explosion.a(true);
        if (explosion.wasCanceled) {
            return explosion;
        } else {
            if (!flag1) {
                explosion.clearBlocks();
            }

            Iterator iterator = players.iterator();

            while (iterator.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator.next();
                if (entityhuman.e(d0, d1, d2) < 4096.0D) {
                    ((EntityPlayer) entityhuman).playerConnection.sendPacket(new PacketPlayOutExplosion(d0, d1, d2, f, explosion.getBlocks(), (Vec3D) explosion.b().get(entityhuman)));
                }
            }

            return explosion;
        }
    }
}
