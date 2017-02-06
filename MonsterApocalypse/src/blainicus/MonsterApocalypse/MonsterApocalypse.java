package blainicus.MonsterApocalypse;

import net.minecraft.server.v1_9_R2.EntityHuman;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

//import org.bukkit.craftbukkit.v1_4_R1.entity.CraftSkeleton;
//import org.bukkit.util.Vector;
//import com.nitnelave.CreeperHeal.CreeperHandler;
//import com.nitnelave.CreeperHeal.CreeperHeal;
//import com.sk89q.worldguard.protection.managers.RegionManager;
//import com.sk89q.worldguard.protection.ApplicableRegionSet;
//import com.sk89q.worldedit.Vector;
//import static com.sk89q.worldguard.bukkit.BukkitUtil.*;

public class MonsterApocalypse extends JavaPlugin implements Runnable {
    Location specialloc;
    public static final float DEGTORAD = 0.017453293F;
    private static final float RADTODEG = 57.29577951F;
    private final Logger log = Logger.getLogger("Minecraft");
    private Block spawn;
    public static double vectorfactor = 4.0;
    public static int blazehp;
    private int x;
    private int z;
    private int offset;
    int rotdeg;
    int distance;
    int temp;
    int trig;
    private int max;
    private int min;
    private int nmax;
    private int nmin;
    private int lightmin;
    private int lightmax;
    private int monstersperplayer;
    int ghastexplosionradius;
    int creeperexplosionradius;
    Random rand;
    Random spawnrand;
    private Random generalrand;
    private Player target;
    boolean potionhits;
    long nightspawnperiod;
    List<String> worldnames;
    private List<String> feetlist;
    boolean nightspawntoggle;
    private boolean toggledrop;
    private boolean togglebonussame;
    private boolean alwaysnight;
    private boolean toggleair;
    boolean togglehostiles;
    boolean togglepassives;
    boolean togglecustomspawn;
    private boolean togglebonusspawn;
    private boolean[] bonusenabled;
    private boolean[] naturalenabled;
    private double[] bonuschance;
    private double[] naturalchance;
    private List<String> dropsblaze;
    private List<String> dropsbat;
    private List<String> dropshorse;
    private List<String> dropswither;
    private List<String> dropswitherskeleton;
    private List<String> dropsmagmacube;
    private List<String> dropsdragon;
    private List<String> dropsocelot;
    private List<String> dropsgiant;
    private List<String> dropsirongolem;
    private List<String> dropscavespider;
    private List<String> dropschicken;
    private List<String> dropsmushroomcow;
    private List<String> dropswolf;
    private List<String> dropscow;
    private List<String> dropscreeper;
    private List<String> dropsenderman;
    private List<String> dropsghast;
    private List<String> dropspig;
    private List<String> dropspigzombie;
    private List<String> zombielist;
    private List<String> dropssheep;
    private List<String> dropssilverfish;
    private List<String> dropsskeleton;
    private List<String> dropsslime;
    private List<String> dropsspider;
    private List<String> dropssquid;
    private List<String> dropsvillager;
    private List<String> dropszombie;
    private boolean dropoverblaze;
    private boolean dropoverhorse;
    private boolean dropovermagmacube;
    private boolean dropoverbat;
    private boolean dropoverwither;
    private boolean dropoverwitherskeleton;
    private boolean dropoverdragon;
    private boolean dropovergiant;
    private boolean dropoverirongolem;
    private boolean dropoverocelot;
    private boolean dropovercavespider;
    private boolean dropoverchicken;
    private boolean dropovermushroomcow;
    private boolean dropoverwolf;
    private boolean dropovercow;
    private boolean dropovercreeper;
    private boolean dropoverenderman;
    private boolean dropoverghast;
    private boolean dropoverpig;
    private boolean dropoverpigzombie;
    private boolean dropoversheep;
    private boolean dropoversilverfish;
    private boolean dropoverskeleton;
    private boolean dropoverslime;
    private boolean dropoverspider;
    private boolean dropoversquid;
    private boolean dropovervillager;
    private boolean dropoverzombie;
    boolean togglesuperskeletons;
    private boolean toggletorch;
    private boolean togglemegaaggro;
    private boolean togglewallattack;
    boolean hphandledexternally;
    private double spawnchance;
    double nightspawnchance;
    double ghastmobspawnchance;
    private SpawnListener spawnListener;
    private List<EntitySpawn> bonustypes;
    private List<EntitySpawn> naturaltypes;
    double[] bonuschances, naturalchances;
    private LivingEntity creature;
    ArrayList<Mob> moblist;
    healthmanager healths;
    DropManager dropper;
    private long spawntime;
    private int superradius;
    private int nlightmin;
    private int nlightmax;
    //	arrowbuffer AB;
    private wallattacker waller;
    private boolean dontrun;
    private boolean drop;
    private boolean aggressivewolf;
    private boolean aggressivepig;
    private boolean megapig;
    private boolean wgbuild;
    boolean wgspawn;
    boolean wgattack;
    boolean wgexplosions;
    boolean changecombathp;
    boolean changecombatdmg;
    boolean changeexplosions;
    boolean changeadvanced;
    boolean changespawning;
    private boolean pigwalls;
    private boolean pillars;
    private boolean bridges;
    boolean checkaggro;
    private String pillarblock;
    private Material pillarmat;
    private int pillardelay;
    private int aggroy;
    private int nruns;
    private int nmobs;
    private boolean boneworld;
    private boolean Noneworld;
    private boolean togglecorpsedie;
    private boolean togglenaturalspawn;
    private String boneworldname;
    private String Noneworldname;
    private boolean toggleskeleton;
    boolean setoggle;
    boolean sefire;
    private int skeletonperiod;
    int skeletonradius;
    private int nymin;
    private int nymax;
    private double skeletonrange;
    private double skeletonchance;
    double serad;
    double sechance;
    private SpawnPoints spawnpointmanager;
    boolean togglespawnpoints;
    File confFile;
    FileConfiguration conf;
    private int skeletony;
    private double naturalsum;
    private double bonussum;
    private double nightmaremultiplier;
    private boolean nightmaretype;
    boolean checkspawnfeet;
    boolean invertspawnfeet;
    boolean checkblockspawn;
    boolean enderdamage;
    boolean enderblockdamage;
    private boolean newzombiemethod;
    private double skeletonspeed;
    private double zombiespeed;
    private double creeperspeed;
    SpawnBlockManager spawnfooter;
    private int naturaldistance;
    private int bonusdistance;
    private double naturalscaling;
    private double bonusscaling;
    private int mainspawnx;
    private int mainspawnz;
    private boolean sound;
    private boolean soundhit;
    private int aggroday;
    private int aggrodayup;
    private boolean splitaggro;
    private int sprintdistance;
    private boolean witherenable;
    private List<String> allmobnames;
    long damageperiod;
    private FileConfiguration maconfig;

    private class EntitySpawn {
        final EntityType type;
        final double chance;

        public EntitySpawn(EntityType e, double c) {
            type = e;
            chance = c;
        }
    }

    private class mobnameindex {

        public static final int
                Bat = 0,
                Blaze = 1,
                CaveSpider = 2,
                Chicken = 3,
                Creeper = 4,
                EnderDragon = 5,
                Enderman = 6,
                Ghast = 7,
                Giant = 8,
                Horse = 9,
                IronGolem = 10,
                MagmaCube = 11,
                MushroomCow = 12,
                Ocelot = 13,
                Pig = 14,
                PigZombie = 15,
                Sheep = 16,
                Silverfish = 17,
                Skeleton = 18,
                Slime = 19,
                Spider = 20,
                Squid = 21,
                Villager = 22,
                Wither = 23,
                WitherSkeleton = 24,
                Wolf = 25,
                Zombie = 26;
    }

    public void onEnable() {
        setupmobnamesandenum();
        bonusenabled = new boolean[allmobnames.size()];
        naturalenabled = new boolean[allmobnames.size()];
        bonuschance = new double[allmobnames.size()];
        naturalchance = new double[allmobnames.size()];
        hphandledexternally = false;
        waller = new wallattacker(this);
        spawnrand = new Random(System.currentTimeMillis());
        nightRun nightforcer = new nightRun(this);
        preparemoblist();
        DropListener dropListener = new DropListener(this);
        spawnListener = new SpawnListener(this);
        DamageListener damageListener = new DamageListener(this);
        ExplosionListener explosionListener = new ExplosionListener(this);

        spawnfooter = new SpawnBlockManager(this);
        try {
            maconfig = getConfig();
            worldnames = new ArrayList<>();
            dontrun = false;
            generalrand = new Random(System.currentTimeMillis() + 10);
//		if(getServer().getWorld(worldname)==null){System.out.println("Monster Apocalypse: Invalid world name. Plugin disabled.");
//		dontrun=true;
//    	maconfig.set("World", worldname);
            //   	saveConfig();
//		}
            try {
                worldnames = maconfig.getStringList("Worlds");
            } catch (NullPointerException e) {
                worldnames = new ArrayList<>();
                worldnames.add("world");
            }
            if (worldnames == null || worldnames.isEmpty()) {
                worldnames = new ArrayList<>();
                worldnames.add("world");
            }

            for (int h = 0; h < worldnames.size(); h++) {
                if (getServer().getWorld(worldnames.get(h)) == null) {
                    System.out.println("Monster Apocalypse: Invalid world name " + worldnames.get(h) + ". Plugin disabled.");
                    dontrun = true;
                    maconfig.set("Worlds", worldnames);
                    saveConfig();
                    break;
                }
            }
            confFile = new File(getDataFolder(), "spawnpoints.yml");
            conf = YamlConfiguration.loadConfiguration(confFile);
            boolean zombieworld = maconfig.getBoolean("Automatically over-write relevant parts of config to zombie apocalypse for next run?", false);
            alwaysnight = maconfig.getBoolean("Always night", false);
            nightmaremultiplier = maconfig.getDouble("Nightmare mode multiplier", 1.0d);
            long nightmareperiod = maconfig.getLong("Nightmare mode period", 1200000) / 50;
            nightmaretype = maconfig.getBoolean("Nightmare mode exponential?", true);
            checkaggro = maconfig.getBoolean("Check for the deaggro permission for entity attacks, shooting super arrows, and mega-aggro?", false);
            togglehostiles = maconfig.getBoolean("Enable natural hostile spawns?", true);
            togglepassives = maconfig.getBoolean("Enable natural passive spawns?", true);
            boolean events = maconfig.getBoolean("Use new event system?", true);
            togglespawnpoints = maconfig.getBoolean("Enable spawn points?", false);
            boolean fulldisable = maconfig.getBoolean("Allow this MA installation to register the spawnpoint command?", true);
            togglebonusspawn = maconfig.getBoolean("Enable bonus spawns?", false);
            togglenaturalspawn = maconfig.getBoolean("Enable naturalistic bonus spawns?", false);
            mainspawnx = maconfig.getInt("Main server spawn X (used for calculating wilderness spawn scaling)", 0);
            mainspawnz = maconfig.getInt("Main server spawn Z (used for calculating wilderness spawn scaling)", 0);
            int nhardcap = maconfig.getInt("Hard cap on all mobs spawning", 1000000);
            boneworld = maconfig.getBoolean("Only enable bonus spawns on one world?", false);
            boneworldname = maconfig.getString("Bonus spawn world name:", "world");
            Noneworld = maconfig.getBoolean("Only enable naturalistic bonus spawns on one selected world?", false);
            Noneworldname = maconfig.getString("Naturalistic bonus spawn world name", "world");
            wgspawn = maconfig.getBoolean("Enable worldguard spawn flag checks?", false);
            togglebonussame = maconfig.getBoolean("Spawn individual wave mobs in the same spot?", false);
            toggleair = maconfig.getBoolean("Enable bonus spawns midair? (Useful for Ghasts)", false);
            changecombathp = maconfig.getBoolean("Enable custom monster health?", false);
            changecombatdmg = maconfig.getBoolean("Enable custom monster damage?", false);
            wgattack = maconfig.getBoolean("Enable worldguard damage flag checks?", false);
            enderdamage = maconfig.getBoolean("Enable Ender Dragons destroying blocks?", false);
            enderblockdamage = maconfig.getBoolean("Enable Endermen picking blocks?", true);
            changeexplosions = maconfig.getBoolean("Enable custom monster explosion properties? (Normal Creeper/Ghast)", false);
            changespawning = maconfig.getBoolean("Enable spawning changes?", false);
            toggledrop = maconfig.getBoolean("Enable custom monster drops?", false);
            toggletorch = maconfig.getBoolean("Enable monsters destroying nearby torches?", false);
            togglemegaaggro = maconfig.getBoolean("Enable monsters attacking from a massive range?", false);
            sprintdistance = maconfig.getInt("Minimum distance sprint with their custom speed", 12);
            superradius = maconfig.getInt("Mega-aggro range", 160);
            aggroy = maconfig.getInt("Mega-aggro range Y", 80);
            splitaggro = maconfig.getBoolean("Use daytime mega-aggro range?", false);
            aggroday = maconfig.getInt("Daytime Mega-aggro range", 160);
            aggrodayup = maconfig.getInt("Daytime Mega-aggro range Y", 80);
            togglewallattack = maconfig.getBoolean("Enable Zombies attacking walls?", false);
            sound = maconfig.getBoolean("Enable Zombie wall destruction sounds?", true);
            soundhit = maconfig.getBoolean("Enable Zombie wall hit sounds?", true);
            pigwalls = maconfig.getBoolean("Enable PigZombies attacking walls?", false);
            drop = maconfig.getBoolean("Drop the block item when a Zombie destroys it?", true);
            wgbuild = maconfig.getBoolean("Enable worldguard 'enderman-grief' flag checks for all monster block operations?", false);
            pillars = maconfig.getBoolean("Enable monsters nerdpoling? (Building straight up)", false);
            bridges = maconfig.getBoolean("Enable monsters airbridging? (Building across gaps)", false);
            togglecorpsedie = maconfig.getBoolean("Kill the monster when it nerdpoles or airbridges?", false);
            pillardelay = maconfig.getInt("Minimum frustration period before nerdpoling or airbridging", 10);
            pillarblock = maconfig.getString("Block to use for monsters building", "DIRT");
            pillarmat = Material.getMaterial(pillarblock);
            aggressivewolf = maconfig.getBoolean("Make wolves aggressive?", false);
            aggressivepig = maconfig.getBoolean("Make zombie pigmen aggressive?", false);
            megapig = maconfig.getBoolean("Make zombie pigmen use mega-aggro?", false);
            wgexplosions = maconfig.getBoolean("Enable worldguard creeper explosion flag check for creepers, ghasts, and death explosions?", false);
            checkspawnfeet = maconfig.getBoolean("Check block spawn blacklist (deny spawn on these blocks)?", false);
            invertspawnfeet = maconfig.getBoolean("Invert block spawn list? (Make whitelist)", false);
            changeadvanced = maconfig.getBoolean("Enable all other monster properties?", false);
            potionhits = maconfig.getBoolean("Enable monster on hit potion effects?", false);
            loadbonusspawnconfigs();
            loadnaturalspawnconfigs();
            loadpropertyconfigs();
            loadspawnconfigs();
            loadnaturalisticlimits();
            loaddropconfigs();
            sumbonus();
            sumnatural();
            monstersperplayer = maconfig.getInt("Bonus monsters per player", 4);
//    	nightspawntoggle=maconfig.getBoolean("Spawn extra bonus monsters at night?", true);
//    	silverfishtoggle=maconfig.getBoolean("Spawn silverfish in Ghast explosions?", true);
//    	ghastradius=maconfig.getInt("Ghast explosion radius (Edit carefully)", 2);
            spawnchance = maconfig.getDouble("Percent chance of spawning a monster in bonus waves", 100.00d);
//    	nightspawnchance=maconfig.getDouble("Percent chance of spawning a monster in night bonus waves", 10.00d);
//    	ghastmobspawnchance=maconfig.getDouble("Percent chance of spawning a monster in a Ghast explosion", 33.00d);
            min = maconfig.getInt("Minimum bonus spawn distance", 24);
            max = maconfig.getInt("Maximum bonus spawn distance", 80);
            offset = maconfig.getInt("Bonus spawn Y offset", 0);
            lightmin = maconfig.getInt("Minimum light level to spawn", 0);
            lightmax = maconfig.getInt("Maximum light level to spawn", 7);
            long spawnperiod = (maconfig.getLong("Bonus spawn period", 60000)) / 50;
            nruns = maconfig.getInt("Naturalistic bonus spawn mob attempts per tick", 10);
            nmobs = maconfig.getInt("Number of mobs to spawn in a spot if found", 1);
            naturalscaling = maconfig.getDouble("Bonus distance max scale", 1.0d);
            naturaldistance = maconfig.getInt("Max bonus distance from spawn before max spawns", 3000);
            int ntick = maconfig.getInt("Tick period for bonus natural spawns, increase this to drastically slow spawning", 1);
            nmin = maconfig.getInt("Minimum naturalistic bonus spawn distance", 24);
            nmax = maconfig.getInt("Maximum naturalistic bonus spawn distance", 160);
            nlightmin = maconfig.getInt("Minimum light level to spawn naturalistic bonus", 0);
            nlightmax = maconfig.getInt("Maximum light level to spawn naturalistic bonus", 7);
            nymin = maconfig.getInt("Minimum Y for naturalistic bonus", 0);
            nymax = maconfig.getInt("Maximum Y for naturalistic bonus", 255);
            naturalscaling = maconfig.getDouble("Naturalistic distance max scale", 1.0d);
            naturaldistance = maconfig.getInt("Max natural distance from spawn before max spawns", 3000);
            creeperspeed = maconfig.getDouble("Advanced Monster Properties.Creeper.Mass aggro run speed", 1.0d);
            skeletonspeed = maconfig.getDouble("Advanced Monster Properties.Skeleton.Mass aggro run speed", 1.0d);
            zombiespeed = maconfig.getDouble("Advanced Monster Properties.Zombie.Mass aggro run speed", 1.0d);
            checkblockspawn = maconfig.getBoolean("Automatically remove mobs who spawn inside blocks?", false);
            damageperiod = maconfig.getLong("Action RPG mode! Milliseconds required between damage, vanilla=500", 500L);
//    	nightspawnperiod=(maconfig.getLong("Night bonus spawn period", 120000))/50;
            maconfig.set("Worlds", worldnames);
            maconfig.set("Automatically over-write relevant parts of config to zombie apocalypse for next run?", zombieworld);
            maconfig.set("Always night", alwaysnight);
            maconfig.set("Nightmare mode multiplier", nightmaremultiplier);
            maconfig.set("Nightmare mode period", nightmareperiod * 50);
            maconfig.set("Nightmare mode exponential?", nightmaretype);
            maconfig.set("Use new event system?", events);
            maconfig.set("Check for the deaggro permission for entity attacks, shooting super arrows, and mega-aggro?", checkaggro);
            maconfig.set("Enable natural hostile spawns?", togglehostiles);
            maconfig.set("Enable natural passive spawns?", togglepassives);
            maconfig.set("Enable spawn points?", togglespawnpoints);
            maconfig.set("Allow this MA installation to register the spawnpoint command?", fulldisable);
            maconfig.set("Enable bonus spawns?", togglebonusspawn);
            maconfig.set("Enable naturalistic bonus spawns?", togglenaturalspawn);
            maconfig.set("Hard cap on all mobs spawning", nhardcap);
            maconfig.set("Main server spawn X (used for calculating wilderness spawn scaling)", mainspawnx);
            maconfig.set("Main server spawn Z (used for calculating wilderness spawn scaling)", mainspawnz);
            maconfig.set("Only enable bonus spawns on one world?", boneworld);
            maconfig.set("Bonus spawn world name:", boneworldname);
            maconfig.set("Only enable naturalistic bonus spawns on one selected world?", Noneworld);
            maconfig.set("Naturalistic bonus spawn world name", Noneworldname);
            maconfig.set("Enable worldguard spawn flag checks?", wgspawn);
            maconfig.set("Spawn individual wave mobs in the same spot?", togglebonussame);
            maconfig.set("Enable bonus spawns midair? (Useful for Ghasts)", toggleair);
            maconfig.set("Enable custom monster health?", changecombathp);
            maconfig.set("Enable custom monster damage?", changecombatdmg);
            maconfig.set("Enable worldguard damage flag checks?", wgattack);
            maconfig.set("Enable Ender Dragons destroying blocks?", enderdamage);
            maconfig.set("Enable Endermen picking blocks?", enderblockdamage);
            maconfig.set("Enable custom monster explosion properties? (Normal Creeper/Ghast)", changeexplosions);
            maconfig.set("Enable spawning changes?", changespawning);
            maconfig.set("Enable custom monster drops?", toggledrop);
            maconfig.set("Enable monsters destroying nearby torches?", toggletorch);
            maconfig.set("Enable monsters attacking from a massive range?", togglemegaaggro);
            maconfig.set("Minimum distance sprint with their custom speed", sprintdistance);
            maconfig.set("Mega-aggro range", superradius);
            maconfig.set("Mega-aggro range Y", aggroy);
            maconfig.set("Use daytime mega-aggro range?", splitaggro);
            maconfig.set("Daytime Mega-aggro range", aggroday);
            maconfig.set("Daytime Mega-aggro range Y", aggrodayup);
            maconfig.set("Enable Zombies attacking walls?", togglewallattack);
            maconfig.set("Enable Zombie wall destruction sounds?", sound);
            maconfig.set("Enable Zombie wall hit sounds?", soundhit);
            maconfig.set("Enable PigZombies attacking walls?", pigwalls);
            maconfig.set("Drop the block item when a Zombie destroys it?", drop);
            maconfig.set("Enable worldguard 'enderman-grief' flag checks for all monster block operations?", wgbuild);
            maconfig.set("Enable monsters nerdpoling? (Building straight up)", pillars);
            maconfig.set("Enable monsters airbridging? (Building across gaps)", bridges);
            maconfig.set("Kill the monster when it nerdpoles or airbridges?", togglecorpsedie);
            maconfig.set("Minimum frustration period before nerdpoling or airbridging", pillardelay);
            maconfig.set("Block to use for monsters building", pillarblock);
            maconfig.set("Make wolves aggressive?", aggressivewolf);
            maconfig.set("Make zombie pigmen aggressive?", aggressivepig);
            maconfig.set("Make zombie pigmen use mega-aggro?", megapig);
            maconfig.set("Enable worldguard creeper explosion flag check for creepers, ghasts, and death explosions?", wgexplosions);
            maconfig.set("Check block spawn blacklist (deny spawn on these blocks)?", checkspawnfeet);
            maconfig.set("Invert block spawn list? (Make whitelist)", invertspawnfeet);
            maconfig.set("Enable monster on hit potion effects?", potionhits);
            maconfig.set("Enable all other monster properties?", changeadvanced);
            maconfig.set("Bonus monsters per player", monstersperplayer);
//    	maconfig.set("Spawn extra bonus monsters at night?", nightspawntoggle);
//    	maconfig.set("Spawn silverfish in Ghast explosions?", silverfishtoggle);
//    	maconfig.set("Ghast explosion radius (Edit carefully)", ghastradius);
            maconfig.set("Percent chance of spawning a monster in bonus waves", spawnchance);
            maconfig.set("Minimum light level to spawn", lightmin);
            maconfig.set("Maximum light level to spawn", lightmax);
//    	maconfig.set("Percent chance of spawning a monster in night bonus waves", nightspawnchance);
//    	maconfig.set("Percent chance of spawning a monster in a Ghast explosion", ghastmobspawnchance);
            maconfig.set("Minimum bonus spawn distance", min);
            maconfig.set("Maximum bonus spawn distance", max);
            maconfig.set("Bonus spawn Y offset", offset);
            maconfig.set("Bonus spawn period", spawnperiod * 50);
            maconfig.set("Number of mobs to spawn in a spot if found", nmobs);
            maconfig.set("Bonus distance max scale", naturalscaling);
            maconfig.set("Max bonus distance from spawn before max spawns", bonusdistance);
            maconfig.set("Naturalistic bonus spawn mob attempts per tick", nruns);
            maconfig.set("Tick period for bonus natural spawns, increase this to drastically slow spawning", ntick);
            maconfig.set("Minimum naturalistic bonus spawn distance", nmin);
            maconfig.set("Maximum naturalistic bonus spawn distance", nmax);
            maconfig.set("Minimum light level to spawn naturalistic bonus", nlightmin);
            maconfig.set("Maximum light level to spawn naturalistic bonus", nlightmax);
            maconfig.set("Minimum Y for naturalistic bonus", nymin);
            maconfig.set("Maximum Y for naturalistic bonus", nymax);
            maconfig.set("Naturalistic distance max scale", naturalscaling);
            maconfig.set("Max natural distance from spawn before max spawns", naturaldistance);

            log.info("Monster Apocalypse: Worldguard not found, disabling checks!");
            wgbuild = false;
            wgspawn = false;
            wgattack = false;
            wgexplosions = false;

            maconfig.set("Automatically remove mobs who spawn inside blocks?", checkblockspawn);
            maconfig.set("Action RPG mode! Milliseconds required between damage, vanilla=500", damageperiod);
//    	maconfig.set("Night bonus spawn period", nightspawnperiod*50);
            setbonusspawnconfigs();
            setnaturalspawnconfigs();
            setpropertyconfigs();
            setspawnconfigs();
            setdropconfigs();
            setspawnfooter();
            maconfig.set("Advanced Monster Properties.Creeper.Mass aggro run speed", creeperspeed);
            maconfig.set("Advanced Monster Properties.Skeleton.Mass aggro run speed", skeletonspeed);
            maconfig.set("Advanced Monster Properties.Zombie.Mass aggro run speed", zombiespeed);
            saveConfig();

            if (zombieworld) {
                loadzombieworld();
                maconfig.set("Worlds", worldnames);
                maconfig.set("Automatically over-write relevant parts of config to zombie apocalypse for next run?", zombieworld);
                maconfig.set("Always night", alwaysnight);
                maconfig.set("Nightmare mode multiplier", nightmaremultiplier);
                maconfig.set("Nightmare mode period", nightmareperiod * 50);
                maconfig.set("Nightmare mode exponential?", nightmaretype);
                maconfig.set("Use new event system?", events);
                maconfig.set("Check for the deaggro permission for entity attacks, shooting super arrows, and mega-aggro?", checkaggro);
                maconfig.set("Enable natural hostile spawns?", togglehostiles);
                maconfig.set("Enable natural passive spawns?", togglepassives);
                maconfig.set("Enable spawn points?", togglespawnpoints);
                maconfig.set("Allow this MA installation to register the spawnpoint command?", fulldisable);
                maconfig.set("Enable bonus spawns?", togglebonusspawn);
                maconfig.set("Enable naturalistic bonus spawns?", togglenaturalspawn);
                maconfig.set("Hard cap on all mobs spawning", nhardcap);
                maconfig.set("Main server spawn X (used for calculating wilderness spawn scaling)", mainspawnx);
                maconfig.set("Main server spawn Z (used for calculating wilderness spawn scaling)", mainspawnz);
                maconfig.set("Only enable bonus spawns on one world?", boneworld);
                maconfig.set("Bonus spawn world name:", boneworldname);
                maconfig.set("Only enable naturalistic bonus spawns on one selected world?", Noneworld);
                maconfig.set("Naturalistic bonus spawn world name", Noneworldname);
                maconfig.set("Enable worldguard spawn flag checks?", wgspawn);
                maconfig.set("Spawn individual wave mobs in the same spot?", togglebonussame);
                maconfig.set("Enable bonus spawns midair? (Useful for Ghasts)", toggleair);
                maconfig.set("Enable custom monster health?", changecombathp);
                maconfig.set("Enable custom monster damage?", changecombatdmg);
                maconfig.set("Enable worldguard damage flag checks?", wgattack);
                maconfig.set("Enable Ender Dragons destroying blocks?", enderdamage);
                maconfig.set("Enable Endermen picking blocks?", enderblockdamage);
                maconfig.set("Enable custom monster explosion properties? (Normal Creeper/Ghast)", changeexplosions);
                maconfig.set("Enable spawning changes?", changespawning);
                maconfig.set("Enable custom monster drops?", toggledrop);
                maconfig.set("Enable monsters destroying nearby torches?", toggletorch);
                maconfig.set("Enable monsters attacking from a massive range?", togglemegaaggro);
                maconfig.set("Minimum distance sprint with their custom speed", sprintdistance);
                maconfig.set("Mega-aggro range", superradius);
                maconfig.set("Mega-aggro range Y", aggroy);
                maconfig.set("Use daytime mega-aggro range?", splitaggro);
                maconfig.set("Daytime Mega-aggro range", aggroday);
                maconfig.set("Daytime Mega-aggro range Y", aggrodayup);
                maconfig.set("Enable Zombies attacking walls?", togglewallattack);
                maconfig.set("Enable Zombie wall destruction sounds?", sound);
                maconfig.set("Enable Zombie wall hit sounds?", soundhit);
                maconfig.set("Enable PigZombies attacking walls?", pigwalls);
                maconfig.set("Drop the block item when a Zombie destroys it?", drop);
                maconfig.set("Enable worldguard 'enderman-grief' flag checks for all monster block operations?", wgbuild);
                maconfig.set("Enable monsters nerdpoling? (Building straight up)", pillars);
                maconfig.set("Enable monsters airbridging? (Building across gaps)", bridges);
                maconfig.set("Kill the monster when it nerdpoles or airbridges?", togglecorpsedie);
                maconfig.set("Minimum frustration period before nerdpoling or airbridging", pillardelay);
                maconfig.set("Block to use for monsters building", pillarblock);
                maconfig.set("Make wolves aggressive?", aggressivewolf);
                maconfig.set("Make zombie pigmen aggressive?", aggressivepig);
                maconfig.set("Make zombie pigmen use mega-aggro?", megapig);
                maconfig.set("Enable worldguard creeper explosion flag check for creepers, ghasts, and death explosions?", wgexplosions);
                maconfig.set("Check block spawn blacklist (deny spawn on these blocks)?", checkspawnfeet);
                maconfig.set("Invert block spawn list? (Make whitelist)", invertspawnfeet);
                maconfig.set("Enable monster on hit potion effects?", potionhits);
                maconfig.set("Enable all other monster properties?", changeadvanced);
                maconfig.set("Bonus monsters per player", monstersperplayer);
//	    	maconfig.set("Spawn extra bonus monsters at night?", nightspawntoggle);
//	    	maconfig.set("Spawn silverfish in Ghast explosions?", silverfishtoggle);
//	    	maconfig.set("Ghast explosion radius (Edit carefully)", ghastradius);
                maconfig.set("Percent chance of spawning a monster in bonus waves", spawnchance);
                maconfig.set("Minimum light level to spawn", lightmin);
                maconfig.set("Maximum light level to spawn", lightmax);
//	    	maconfig.set("Percent chance of spawning a monster in night bonus waves", nightspawnchance);
//	    	maconfig.set("Percent chance of spawning a monster in a Ghast explosion", ghastmobspawnchance);
                maconfig.set("Minimum bonus spawn distance", min);
                maconfig.set("Maximum bonus spawn distance", max);
                maconfig.set("Bonus spawn Y offset", offset);
                maconfig.set("Bonus spawn period", spawnperiod * 50);
                maconfig.set("Number of mobs to spawn in a spot if found", nmobs);
                maconfig.set("Bonus distance max scale", naturalscaling);
                maconfig.set("Max bonus distance from spawn before max spawns", bonusdistance);
                maconfig.set("Naturalistic bonus spawn mob attempts per tick", nruns);
                maconfig.set("Tick period for bonus natural spawns, increase this to drastically slow spawning", ntick);
                maconfig.set("Minimum naturalistic bonus spawn distance", nmin);
                maconfig.set("Maximum naturalistic bonus spawn distance", nmax);
                maconfig.set("Minimum light level to spawn naturalistic bonus", nlightmin);
                maconfig.set("Maximum light level to spawn naturalistic bonus", nlightmax);
                maconfig.set("Minimum Y for naturalistic bonus", nymin);
                maconfig.set("Maximum Y for naturalistic bonus", nymax);
                maconfig.set("Naturalistic distance max scale", naturalscaling);
                maconfig.set("Max natural distance from spawn before max spawns", naturaldistance);
                log.info("Monster Apocalypse: Worldguard not found, disabling checks!");
                wgbuild = false;
                wgspawn = false;
                wgattack = false;
                wgexplosions = false;

                maconfig.set("Automatically remove mobs who spawn inside blocks?", checkblockspawn);
                maconfig.set("Action RPG mode! Milliseconds required between damage, vanilla=500", damageperiod);
//	    	maconfig.set("Night bonus spawn period", nightspawnperiod*50);
                setbonusspawnconfigs();
                setnaturalspawnconfigs();
                setpropertyconfigs();
                setspawnconfigs();
                setdropconfigs();
                setspawnfooter();
                maconfig.set("Advanced Monster Properties.Creeper.Mass aggro run speed", creeperspeed);
                maconfig.set("Advanced Monster Properties.Skeleton.Mass aggro run speed", skeletonspeed);
                maconfig.set("Advanced Monster Properties.Zombie.Mass aggro run speed", zombiespeed);
                saveConfig();
            }
        /*if(!togglepassives&&!togglehostiles){
            getServer().dispatchCommand(getServer().getConsoleSender(), "gamerule doMobSpawning false");
    		System.out.println("Monster Apocalypse: Gamerule doMobSpawning disabled because you have natural hostile and passive spawns disabled! This only occurs when Monster Apocalypse enables.");
    	}*/

            if (!dontrun) {
                PluginManager pm = this.getServer().getPluginManager();
                //if(events){
                pm.registerEvents(spawnListener, this);
                //}
                //else{
                //	pm.registerEvent(Event.Type.CREATURE_SPAWN, spawnListenerOld, Event.Priority.High, this);
                //}
                if (changecombathp) {
                    healths = new healthmanager(this);
                    healths.addall();
                    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, healths, 20 * 60 * 10, 20 * 60 * 10);
                }
                //if(events){
                pm.registerEvents(damageListener, this);
                //}else{
                //	pm.registerEvent(Event.Type.ENTITY_DAMAGE, damageListenerOld, Event.Priority.Highest, this);
                //	pm.registerEvent(Event.Type.ENTITY_COMBUST, damageListenerOld, Event.Priority.Highest, this);
                //	pm.registerEvent(Event.Type.ENTITY_DEATH, damageListenerOld, Event.Priority.Low, this);
                //}
                if (changeexplosions || changeadvanced || !enderdamage) {
                    ///	if(events){
                    pm.registerEvents(explosionListener, this);
                    //}
                    //else{
                    //	pm.registerEvent(Event.Type.EXPLOSION_PRIME, explosionListenerOld, Event.Priority.Highest, this);
                    //	pm.registerEvent(Event.Type.PROJECTILE_HIT, explosionListenerOld, Event.Priority.Highest, this);
                    //	}
                }

                if (toggledrop) {
                    dropper = new DropManager(this);
                    dropper.checkdrops();
                    //if(events){
                    pm.registerEvents(dropListener, this);
                    //}
                    //else{
                    //	pm.registerEvent(Event.Type.ENTITY_DEATH, dropListenerOld, Event.Priority.Highest, this);
                    //}
                }
                if (toggletorch || togglemegaaggro || this.pillars) {
                    torchdestroyer torchkiller = new torchdestroyer(this);
                    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, torchkiller, 1, 1);
                }
                this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new nightmaremode(this), 0, nightmareperiod);


                if (alwaysnight) {
                    for (String worldname : worldnames) {
                        this.getServer().getWorld(worldname).setTime(15600);
                    }

                    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, nightforcer, 6000, 6000);
                }
                if (togglewallattack) {
                    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, waller, 1, 1);
                }

                if (togglebonusspawn) {
                    boolean dontbonus = false;
                    if (boneworld) {
                        for (int i = 0; i < worldnames.size(); i++) {
                            String worldname = worldnames.get(i);
                            if (worldname.equals(boneworldname)) {
                                break;
                            }
                            if (i == worldnames.size()) {
                                log.info("Monster Apocalypse: Bonus world not found, disabling bonus spawns.");
                                dontbonus = true;
                            }
                        }
                    }
                    if (!dontbonus) {
                        //	setupbonustypes();
                        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this, spawnperiod, spawnperiod);
                    }

                }
                if (togglenaturalspawn) {
                    NaturalSpawner naturalz = new NaturalSpawner(this);
                    boolean dontnatural = false;
                    if (Noneworld) {
                        for (int i = 0; i < worldnames.size(); i++) {
                            String worldname = worldnames.get(i);
                            if (worldname.equals(Noneworldname)) {
                                break;
                            }
                            if (i == worldnames.size()) {
                                log.info("Monster Apocalypse: Natural bonus world not found, disabling natural bonus spawns.");
                                dontnatural = true;
                            }
                        }
                    }
                    if (!dontnatural) {
                        //setupnaturaltypes();
                        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, naturalz, ntick, ntick);
                    }

                }
                if (aggressivewolf || aggressivepig) {
                    NeutralAttacker neutraler = new NeutralAttacker(this);
                    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, neutraler, 1, 1);
                }
                if (changeadvanced) {
                    if (toggleskeleton) {
                        skeletons skeletoner = new skeletons(this);
                        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, skeletoner, skeletonperiod, skeletonperiod);
                    }
                }
                spawnpointmanager = new SpawnPoints(this);
                if (fulldisable) {
                    getCommand("monsterapocalypse").setExecutor(spawnpointmanager);
                }
                if (togglespawnpoints) {
                    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, spawnpointmanager, 20, 20);
                }
                log.info("Monster Apocalypse enabled.");
            }
        } catch (Exception e) {
            log.info("Monster Apocalypse: Config error, check your config font and format!\n");
            e.printStackTrace();
        }
//    	this.getServer().getScheduler().scheduleSyncRepeatingTask(this, nightrunner, nightspawnperiod, nightspawnperiod);
    }

    private void setupmobnamesandenum() {
        allmobnames = new ArrayList<>();
        allmobnames.add("Bat");
        allmobnames.add("Blaze");
        allmobnames.add("CaveSpider");
        allmobnames.add("Chicken");
        allmobnames.add("Creeper");
        allmobnames.add("EnderDragon");
        allmobnames.add("Enderman");
        allmobnames.add("Ghast");
        allmobnames.add("Giant");
        allmobnames.add("Horse");
        allmobnames.add("IronGolem");
        allmobnames.add("MagmaCube");
        allmobnames.add("MushroomCow");
        allmobnames.add("Ocelot");
        allmobnames.add("Pig");
        allmobnames.add("PigZombie");
        allmobnames.add("Sheep");
        allmobnames.add("Silverfish");
        allmobnames.add("Skeleton");
        allmobnames.add("Slime");
        allmobnames.add("Spider");
        allmobnames.add("Squid");
        allmobnames.add("Villager");
        allmobnames.add("Wither");
        allmobnames.add("WitherSkeleton");
        allmobnames.add("Wolf");
        allmobnames.add("Zombie");
    }

    public void onDisable() {
        if (togglespawnpoints) {
            spawnpointmanager.save();
        }
        this.getServer().getScheduler().cancelTasks(this);

        log.info("Monster Apocalypse disabled.");
    }

    public boolean WGexplode(Location loc, float power, boolean fire) {
        if (wgexplosions && !getWGExplosion(loc)) {
            return false;
        } else {
            loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, fire, false);
            CraftWorld craftWorld = (CraftWorld) loc.getWorld();
            List<EntityHuman> players = new ArrayList<>();
            for (Player player : loc.getWorld().getPlayers()) {
                players.add(((EntityHuman) player));
            }
            CustomExp.createExplosion(craftWorld, players, loc.getX(), loc.getY(), loc.getZ(), power, fire, false);
//            loc.getWorld().playEffect(loc, Effect.EXPLOSION, 0, (int) power);
//            for (Entity entity : loc.getWorld().getEntities()) {
//                if (!(entity instanceof Item)) {
//                    if (entity.getLocation().distance(loc) <= power) {
//
//                    }
//                }
//            }
        }
        return true;
    }

    private void loadzombieworld() {
        //TODO: zombie world
        alwaysnight = true;
        togglehostiles = false;
        togglepassives = false;
        togglebonusspawn = false;
        togglenaturalspawn = true;
        changecombathp = true;
        changecombatdmg = true;
        changespawning = true;
        toggledrop = true;
        togglemegaaggro = true;
        sprintdistance = 5;
        superradius = 50;
        aggroy = 250;
        togglewallattack = true;
        changeadvanced = true;
        nruns = 7;
        nmax = 120;
        checkblockspawn = true;
        //damageperiod=150;

        pillars = true;
        bridges = true;
        togglecorpsedie = false;
        pillardelay = 10;
        pillarblock = "DIRT";
        pillarmat = Material.getMaterial(pillarblock);

        for (int i = 0; i < this.naturalenabled.length; i++) {
            naturalenabled[i] = false;
        }
        for (int i = 0; i < this.naturalchance.length; i++) {
            naturalchance[i] = 100;
        }
        naturalenabled[naturalenabled.length - 1] = true;
        naturalenabled[naturalenabled.length - 9] = true;
        naturalchance[naturalchance.length - 1] = 90;
        naturalchance[naturalchance.length - 9] = 10;
        naturaltypes = new ArrayList<>();
        for (int i = 0; i < this.naturalenabled.length; i++) {
            String name = allmobnames.get(i);
            if (naturalenabled[i]) {
                naturaltypes.add(new EntitySpawn(EntityType.fromName(name), naturalchance[i]));

            }
        }
        sumnatural();
        String Name = "Zombie";
        Mob m = null;
        for (Mob aMoblist1 : moblist) {
            if (aMoblist1.name.equalsIgnoreCase(Name)) {
                m = aMoblist1;
            }
        }
        m.health = 20;
        m.damage = 1;
        m.sunlightdamage = false;
        m.megaaggro = true;
        m.truedamage = 1;
        m.drowningimmune = true;
        zombiespeed = 1.8;
        m.pillar = true;
        m.bridge = true;

        zombieworldblocks();
        newzombiemethod = true;

        zombieworldzombiedrops();

        Name = "Skeleton";
        m = null;
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                m = aMoblist;
            }
        }

        m.megaaggro = true;
        m.health = 20;
        m.damage = 1;
        m.sunlightdamage = false;
        m.pillar = true;
        m.bridge = true;
        m.truedamage = 1;
        m.drowningimmune = true;
        m.standarditems = true;

        toggleskeleton = true;
        skeletonperiod = 4000;
        skeletonchance = 20;
        skeletonrange = 80;
        skeletony = 300;
        setoggle = true;
        serad = 2.5;
        sechance = 4;
        skeletonradius = 3;
        skeletonspeed = 1.8;

        zombieworldskeletondrops();

        checkspawnfeet = true;
        zombieworldblockspawn();
    }

    private void zombieworldblockspawn() {

        feetlist.clear();

        feetlist.add("WATER");
        feetlist.add("LEAVES");
        feetlist.add("ICE");
        feetlist.add("GLASS");
        feetlist.add("COBBLESTONE");
        feetlist.add("WOOD");

        spawnfooter.blocklist.clear();
        for (String aFeetlist : feetlist) {
            spawnfooter.addblock(aFeetlist);
        }
    }

    private void zombieworldskeletondrops() {
        dropsskeleton.clear();
        dropsskeleton.add("LEATHER:1:5.0");
        dropsskeleton.add("LEATHER:1:5.0");
        dropsskeleton.add("LEATHER:1:5.0");
        dropsskeleton.add("LEATHER:1:5.0");
        dropsskeleton.add("COOKED_FISH:1:5.0");
        dropsskeleton.add("COOKED_FISH:1:2.5");
        dropsskeleton.add("COOKED_FISH:1:5.0");
        dropsskeleton.add("COOKED_FISH:1:2.5");
        dropsskeleton.add("STRING:1:2.5");
        dropsskeleton.add("IRON_INGOT:1:1.5");
        dropsskeleton.add("IRON_INGOT:1:1.5");
        dropsskeleton.add("IRON_INGOT:1:1.5");
        dropsskeleton.add("IRON_INGOT:1:1.5");
        dropsskeleton.add("DIAMOND:1:0.01");
        dropsskeleton.add("DIAMOND:1:0.01");
        dropsskeleton.add("DIAMOND:1:0.01");
        dropsskeleton.add("DIAMOND:1:0.01");
        dropsskeleton.add("LEATHER:1:5.0");
        dropsskeleton.add("LEATHER:1:5.0");
        dropsskeleton.add("COOKED_FISH:1:3.5");
        dropsskeleton.add("STRING:1:2.5");
        dropsskeleton.add("IRON_INGOT:1:0.5");
        dropsskeleton.add("IRON_INGOT:1:0.5");
        dropsskeleton.add("DIAMOND:1:0.01");
        dropsskeleton.add("DIAMOND:1:0.01");
        dropsskeleton.add("GLOWSTONE:1:10.0");
        dropsskeleton.add("GLOWSTONE:1:10.0");
        dropsskeleton.add("GLOWSTONE:1:10.0");
        dropsskeleton.add("GLOWSTONE:1:10.0");
        dropsskeleton.add("ARROW:1:10.0");
    }

    private void zombieworldzombiedrops() {
        dropszombie.clear();
        dropszombie.add("LEATHER:1:5.0");
        dropszombie.add("LEATHER:1:5.0");
        dropszombie.add("LEATHER:1:5.0");
        dropszombie.add("LEATHER:1:5.0");
        dropszombie.add("COOKED_FISH:1:5.0");
        dropszombie.add("COOKED_FISH:1:2.5");
        dropszombie.add("STRING:1:2.5");
        dropszombie.add("IRON_INGOT:1:1.5");
        dropszombie.add("IRON_INGOT:1:1.5");
        dropszombie.add("IRON_INGOT:1:1.5");
        dropszombie.add("IRON_INGOT:1:1.5");
        dropszombie.add("IRON_INGOT:1:1.5");
        dropszombie.add("IRON_INGOT:1:1.5");
        dropszombie.add("DIAMOND:1:0.01");
        dropszombie.add("DIAMOND:1:0.01");
        dropszombie.add("DIAMOND:1:0.01");
        dropszombie.add("DIAMOND:1:0.01");
        dropszombie.add("GLOWSTONE:1:10.0");
        dropszombie.add("GLOWSTONE:1:10.0");
        dropszombie.add("GLOWSTONE:1:10.0");
        dropszombie.add("ARROW:1:15.0");
    }

    private void zombieworldblocks() {
        waller.wallmanager.blocklist.clear();
        zombielist.clear();
        zombielist.add("DIRT:10");
        zombielist.add("SNOW:30");
        zombielist.add("CLAY:30");
        zombielist.add("BRICK:90");
        zombielist.add("GRASS:30");
        zombielist.add("WOOD:30");
        zombielist.add("LOG:60");
        zombielist.add("WORKBENCH:60");
        zombielist.add("WOOL:30");
        zombielist.add("WOODEN_DOOR:30");
        zombielist.add("GLASS:15");
        zombielist.add("THIN_GLASS:15");
        zombielist.add("WOOD_DOOR:30");
        zombielist.add("IRON_DOOR:90");
        zombielist.add("LEAVES:2");
        zombielist.add("SAND:30");
        zombielist.add("OBSIDIAN:240");
        zombielist.add("PUMPKIN:30");
        zombielist.add("WOOD_STAIRS:30");
        zombielist.add("COBBLESTONE_STAIRS:60");
        zombielist.add("BRICK_STAIRS:60");
        zombielist.add("SMOOTH_STAIRS:60");
        zombielist.add("SMOOTH_BRICK:60");
        zombielist.add("CHEST:30");
        zombielist.add("FURNACE:60");
        zombielist.add("COBBLESTONE:60");
        zombielist.add("STONE:45");
        zombielist.add("GRAVEL:30");
        zombielist.add("IRON_ORE:60");
        zombielist.add("GOLD_ORE:60");
        zombielist.add("COAL_ORE:60");
        zombielist.add("IRON_BLOCK:180");
        zombielist.add("GOLD_BLOCK:120");
        zombielist.add("DIAMOND_BLOCK:240");
        zombielist.add("LAPIS_BLOCK:90");
        zombielist.add("LAPIS_ORE:60");
        zombielist.add("ICE:15");
        zombielist.add("STEP:60");
        zombielist.add("DOUBLE_STEP:60");
        zombielist.add("GLOWSTONE:30");
        zombielist.add("FENCE:60");
        zombielist.add("IRON_FENCE:120");
        zombielist.add("FENCE_GATE:30");
        zombielist.add("NETHER_BRICK:60");
        zombielist.add("NETHER_FENCE:60");
        zombielist.add("NETHER_BRICK_STAIRS:60");
        zombielist.add("NETHERRACK:60");
        zombielist.add("HUGE_MUSHROOM_1:30");
        zombielist.add("HUGE_MUSHROOM_2:30");
        zombielist.add("MELON_BLOCK:15");
        zombielist.add("CACTUS:15");
        zombielist.add("LADDER:15");
        zombielist.add("TRAP_DOOR:30");
        zombielist.add("JUKEBOX:30");
        for (String aZombielist : zombielist) {
            waller.wallmanager.stringadd(aZombielist);
        }
    }

    public void run() {
        for (String worldname : worldnames) {
            if (boneworld && !worldname.equals(boneworldname)) {
                continue;
            }
            World world = getServer().getWorld(worldname);
            boolean stopchecking = false;
            spawntime = System.currentTimeMillis();
            rand = new Random(spawntime);
            for (int n = 0; n < world.getPlayers().size(); n++) {
                target = world.getPlayers().get(n);
                if (target.getGameMode() == GameMode.CREATIVE) continue;
                int truecount = monstersperplayer + (int) Math.round(((bonusscaling - 1.0d) * Math.min((double) bonusdistance, (Math.round(getspawndis(target.getLocation())))) / (double) bonusdistance) * (monstersperplayer));
                for (int ghasts = 0; ghasts < truecount; ghasts++) {
                    if (rand.nextDouble() * 100d <= spawnchance) {
                        for (int reset = 0; reset < 40; reset++) {
                            if (togglebonussame) {
                                rand = new Random(spawntime);
                            }

                            int diff = max - min;
                            double t = 2 * Math.PI * rand.nextDouble();
                            double u = rand.nextDouble() + rand.nextDouble();
                            double r;
                            if (u > 1) {
                                r = 2 - u;
                            } else {
                                r = u + 0;
                            }
                            x = (int) Math.round(r * Math.cos(t) * (rand.nextInt(diff) + min));
                            z = (int) Math.round(r * Math.sin(t) * (rand.nextInt(diff) + min));
//    	rotdeg=rand.nextInt(89);
//    	distance=min+rand.nextInt(max-min+1);
//    	x=(int) Math.round(Math.floor(Math.cos((double)rotdeg)*distance));
//    	z=(int) Math.round(Math.floor(Math.sin((double)rotdeg)*distance));
//    	if(rand.nextBoolean()){x=(-x);}
//    	if(rand.nextBoolean()){z=(-z);}
//    	x = (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max-min+1)+min);
//    	temp = (rand.nextInt(max-min+1)+min);
                            //   	trig = (int) Math.sqrt((temp * temp) - (x * x));
                            //   	z = ((rand.nextBoolean() ? 1 : -1) * trig);

                            spawn = target.getLocation().getBlock().getRelative(x, offset, z);
                            if (!toggleair) {
                                if (!(spawn.getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) &&
                                        spawn.getRelative(BlockFace.UP).getType().equals(Material.AIR))) {
                                    for (int ydec = 1; ydec <= target.getLocation().getBlockY() + offset; ydec++) {
                                        if (spawn.getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) &&
                                                spawn.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                                            stopchecking = true;
                                            break;
                                        } else {
                                            spawn = target.getLocation().getBlock().getRelative(x, (offset - ydec), z);
                                        }
                                    }
                                    if (!stopchecking) {
                                        spawn = target.getLocation().getBlock().getRelative(x, offset, z);
                                        for (int yinc = 0; yinc <= 255 - target.getLocation().getBlockY() + offset; yinc++) {
                                            if (spawn.getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) &&
                                                    spawn.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                                                break;
                                            } else {
                                                spawn = target.getLocation().getBlock().getRelative(x, offset + yinc, z);
                                            }

                                        }
                                    }
                                }
                            }
                            if (spawn.getLocation().getBlock().getLightLevel() >= lightmin && spawn.getLocation().getBlock().getLightLevel() <= lightmax
                                    ) {
                                try {
                                    if (!checkplayersinbox(spawn.getLocation(), min)) {
                                        EntityType te = getbonusspawntype();
                                        if (te == EntityType.WITHER_SKULL) {
                                            te = EntityType.SKELETON;
                                            creature = (LivingEntity) world.spawnEntity(spawn.getLocation(), te);
                                            ((Skeleton) creature).setSkeletonType(SkeletonType.WITHER);
                                            creature.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                                        } else if (te == EntityType.SKELETON) {
                                            creature = (LivingEntity) world.spawnEntity(spawn.getLocation(), te);
                                            creature.getEquipment().setItemInHand(new ItemStack(Material.BOW));
                                        } else {
                                            creature = (LivingEntity) world.spawnEntity(spawn.getLocation(), te);
                                        }
                                        break;
                                    }
                                } catch (NullPointerException e) {
                                    return;
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private EntityType getbonusspawntype() {
        if (bonussum <= 101d) {
            //double dchance=rand.nextDouble()*100d;
            double tempsum = rand.nextDouble() * 100d;
            for (EntitySpawn bte : bonustypes) {
                if (tempsum < bte.chance) {
                    return bte.type;
                } else {
                    tempsum -= bte.chance;
                }
            }
        }


        //System.out.println("bonus sum: "+bonussum);
        try {
            return bonustypes.get(rand.nextInt(bonustypes.size() - 1)).type;
        } catch (Exception e) {
            System.out.println("Monster Apocalypse: Invalid bonus spawn proportion settings! You have not set any mobs to spawn, spawning chickens instead!");
        }
        return EntityType.CHICKEN;
    }

    private EntityType getnspawntype() {
        if (naturalsum <= 101d) {
            //double dchance=rand.nextDouble()*100d;
            double tempsum = rand.nextDouble() * 100d;
            for (EntitySpawn bte : naturaltypes) {
                if (tempsum < bte.chance) {
                    return bte.type;
                } else {
                    tempsum -= bte.chance;
                }
            }
        }


        //System.out.println("bonus sum: "+bonussum);
        try {
            return naturaltypes.get(rand.nextInt(naturaltypes.size() - 1)).type;
        } catch (Exception e) {
            System.out.println("Monster Apocalypse: Invalid naturalistic bonus spawn proportion settings! You have not set any mobs to spawn, spawning chickens instead!");
        }
        return EntityType.CHICKEN;
    }

    private void loadspawnconfigs() {
        spawnListener.spawnbat = maconfig.getBoolean("Natural.Bat.spawn", true);
        spawnListener.spawncreeper = maconfig.getBoolean("Natural.Creeper.spawn", true);
        spawnListener.spawnenderman = maconfig.getBoolean("Natural.Enderman.spawn", true);
        spawnListener.spawnskeleton = maconfig.getBoolean("Natural.Skeleton.spawn", true);
        spawnListener.spawnspider = maconfig.getBoolean("Natural.Spider.spawn", true);
        spawnListener.spawnzombie = maconfig.getBoolean("Natural.Zombie.spawn", true);
        spawnListener.spawnblaze = maconfig.getBoolean("Natural.Blaze.spawn", true);
        spawnListener.spawnhorse = maconfig.getBoolean("Natural.Horse.spawn", true);
        spawnListener.spawncavespider = maconfig.getBoolean("Natural.CaveSpider.spawn", true);
        spawnListener.spawnchicken = maconfig.getBoolean("Natural.Chicken.spawn", true);
        spawnListener.spawncow = maconfig.getBoolean("Natural.Cow.spawn", true);
        spawnListener.spawnghast = maconfig.getBoolean("Natural.Ghast.spawn", true);
        spawnListener.spawnirongolem = maconfig.getBoolean("Natural.IronGolem.spawn", true);
        spawnListener.spawnmagmacube = maconfig.getBoolean("Natural.MagmaCube.spawn", true);
        spawnListener.spawnmushroomcow = maconfig.getBoolean("Natural.MushroomCow.spawn", true);
        spawnListener.spawnocelot = maconfig.getBoolean("Natural.Ocelot.spawn", true);
        spawnListener.spawnpig = maconfig.getBoolean("Natural.Pig.spawn", true);
        spawnListener.spawnpigzombie = maconfig.getBoolean("Natural.PigZombie.spawn", true);
        spawnListener.spawnsheep = maconfig.getBoolean("Natural.Sheep.spawn", true);
        spawnListener.spawnsilverfish = maconfig.getBoolean("Natural.Silverfish.spawn", true);
        spawnListener.spawnslime = maconfig.getBoolean("Natural.Slime.spawn", true);
        spawnListener.spawnsquid = maconfig.getBoolean("Natural.Squid.spawn", true);
        spawnListener.spawnvillager = maconfig.getBoolean("Natural.Villager.spawn", true);
        spawnListener.spawnwither = maconfig.getBoolean("Natural.Wither.spawn", true);
        spawnListener.spawnwitherskeleton = maconfig.getBoolean("Natural.WitherSkeleton.spawn", true);
        spawnListener.spawnwolf = maconfig.getBoolean("Natural.Wolf.spawn", true);

        spawnListener.spawncountbat = maconfig.getInt("Natural.Bat.spawncount", 1);
        spawnListener.spawncountwither = maconfig.getInt("Natural.Wither.spawncount", 1);
        spawnListener.spawncountwitherskeleton = maconfig.getInt("Natural.WitherSkeleton.spawncount", 1);
        spawnListener.spawncountwolf = maconfig.getInt("Natural.Wolf.spawncount", 1);
        spawnListener.spawncountmagmacube = maconfig.getInt("Natural.MagmaCube.spawncount", 1);
        spawnListener.spawncountmushroomcow = maconfig.getInt("Natural.MushroomCow.spawncount", 1);
        spawnListener.spawncountblaze = maconfig.getInt("Natural.Blaze.spawncount", 1);
        spawnListener.spawncounthorse = maconfig.getInt("Natural.Horse.spawncount", 1);
        spawnListener.spawncountcavespider = maconfig.getInt("Natural.CaveSpider.spawncount", 1);
        spawnListener.spawncountchicken = maconfig.getInt("Natural.Chicken.spawncount", 1);
        spawnListener.spawncountcow = maconfig.getInt("Natural.Cow.spawncount", 1);
        spawnListener.spawncountcreeper = maconfig.getInt("Natural.Creeper.spawncount", 1);
        spawnListener.spawncountenderman = maconfig.getInt("Natural.Enderman.spawncount", 1);
        spawnListener.spawncountghast = maconfig.getInt("Natural.Ghast.spawncount", 1);
        spawnListener.spawncountirongolem = maconfig.getInt("Natural.IronGolem.spawncount", 1);
        spawnListener.spawncountocelot = maconfig.getInt("Natural.Ocelot.spawncount", 1);
        spawnListener.spawncountpig = maconfig.getInt("Natural.Pig.spawncount", 1);
        spawnListener.spawncountpigzombie = maconfig.getInt("Natural.PigZombie.spawncount", 1);
        spawnListener.spawncountsheep = maconfig.getInt("Natural.Sheep.spawncount", 1);
        spawnListener.spawncountsilverfish = maconfig.getInt("Natural.Silverfish.spawncount", 1);
        spawnListener.spawncountskeleton = maconfig.getInt("Natural.Skeleton.spawncount", 1);
        spawnListener.spawncountslime = maconfig.getInt("Natural.Slime.spawncount", 1);
        spawnListener.spawncountspider = maconfig.getInt("Natural.Spider.spawncount", 1);
        spawnListener.spawncountsquid = maconfig.getInt("Natural.Squid.spawncount", 1);
        spawnListener.spawncountvillager = maconfig.getInt("Natural.Villager.spawncount", 1);
        spawnListener.spawncountzombie = maconfig.getInt("Natural.Zombie.spawncount", 1);

        spawnListener.spawnchancebat = maconfig.getDouble("Natural.Bat.spawnchance", 100.0d);
        spawnListener.spawnchancewither = maconfig.getDouble("Natural.Wither.spawnchance", 100.0d);
        spawnListener.spawnchancewitherskeleton = maconfig.getDouble("Natural.WitherSkeleton.spawnchance", 100.0d);
        spawnListener.spawnchancewolf = maconfig.getDouble("Natural.Wolf.spawnchance", 100.0d);
        spawnListener.spawnchancemagmacube = maconfig.getDouble("Natural.MagmaCube.spawnchance", 100.0d);
        spawnListener.spawnchancemushroomcow = maconfig.getDouble("Natural.MushroomCow.spawnchance", 100.0d);
        spawnListener.spawnchanceblaze = maconfig.getDouble("Natural.Blaze.spawnchance", 100.0d);
        spawnListener.spawnchancehorse = maconfig.getDouble("Natural.Horse.spawnchance", 100.0d);
        spawnListener.spawnchancecavespider = maconfig.getDouble("Natural.CaveSpider.spawnchance", 100.0d);
        spawnListener.spawnchancechicken = maconfig.getDouble("Natural.Chicken.spawnchance", 100.0d);
        spawnListener.spawnchancecow = maconfig.getDouble("Natural.Cow.spawnchance", 100.0d);
        spawnListener.spawnchancecreeper = maconfig.getDouble("Natural.Creeper.spawnchance", 100.0d);
        spawnListener.spawnchanceenderman = maconfig.getDouble("Natural.Enderman.spawnchance", 100.0d);
        spawnListener.spawnchanceghast = maconfig.getDouble("Natural.Ghast.spawnchance", 100.0d);
        spawnListener.spawnchanceirongolem = maconfig.getDouble("Natural.IronGolem.spawnchance", 100.0d);
        spawnListener.spawnchanceocelot = maconfig.getDouble("Natural.Ocelot.spawnchance", 100.0d);
        spawnListener.spawnchancepig = maconfig.getDouble("Natural.Pig.spawnchance", 100.0d);
        spawnListener.spawnchancepigzombie = maconfig.getDouble("Natural.PigZombie.spawnchance", 100.0d);
        spawnListener.spawnchancesheep = maconfig.getDouble("Natural.Sheep.spawnchance", 100.0d);
        spawnListener.spawnchancesilverfish = maconfig.getDouble("Natural.Silverfish.spawnchance", 100.0d);
        spawnListener.spawnchanceskeleton = maconfig.getDouble("Natural.Skeleton.spawnchance", 100.0d);
        spawnListener.spawnchanceslime = maconfig.getDouble("Natural.Slime.spawnchance", 100.0d);
        spawnListener.spawnchancespider = maconfig.getDouble("Natural.Spider.spawnchance", 100.0d);
        spawnListener.spawnchancesquid = maconfig.getDouble("Natural.Squid.spawnchance", 100.0d);
        spawnListener.spawnchancevillager = maconfig.getDouble("Natural.Villager.spawnchance", 100.0d);
        spawnListener.spawnchancezombie = maconfig.getDouble("Natural.Zombie.spawnchance", 100.0d);

        spawnListener.spawnyminbat = maconfig.getInt("Natural.Bat.spawnymin", 1);
        spawnListener.spawnyminwither = maconfig.getInt("Natural.Wither.spawnymin", 1);
        spawnListener.spawnyminwitherskeleton = maconfig.getInt("Natural.WitherSkeleton.spawnymin", 1);
        spawnListener.spawnyminwolf = maconfig.getInt("Natural.Wolf.spawnymin", 1);
        spawnListener.spawnyminmushroomcow = maconfig.getInt("Natural.MushroomCow.spawnymin", 1);
        spawnListener.spawnyminmagmacube = maconfig.getInt("Natural.MagmaCube.spawnymin", 1);
        spawnListener.spawnyminblaze = maconfig.getInt("Natural.Blaze.spawnymin", 1);
        spawnListener.spawnyminhorse = maconfig.getInt("Natural.Horse.spawnymin", 1);
        spawnListener.spawnymincavespider = maconfig.getInt("Natural.CaveSpider.spawnymin", 1);
        spawnListener.spawnyminchicken = maconfig.getInt("Natural.Chicken.spawnymin", 1);
        spawnListener.spawnymincow = maconfig.getInt("Natural.Cow.spawnymin", 1);
        spawnListener.spawnymincreeper = maconfig.getInt("Natural.Creeper.spawnymin", 1);
        spawnListener.spawnyminenderman = maconfig.getInt("Natural.Enderman.spawnymin", 1);
        spawnListener.spawnyminghast = maconfig.getInt("Natural.Ghast.spawnymin", 1);
        spawnListener.spawnyminirongolem = maconfig.getInt("Natural.IronGolem.spawnymin", 1);
        spawnListener.spawnyminocelot = maconfig.getInt("Natural.Ocelot.spawnymin", 1);
        spawnListener.spawnyminpig = maconfig.getInt("Natural.Pig.spawnymin", 1);
        spawnListener.spawnyminpigzombie = maconfig.getInt("Natural.PigZombie.spawnymin", 1);
        spawnListener.spawnyminsheep = maconfig.getInt("Natural.Sheep.spawnymin", 1);
        spawnListener.spawnyminsilverfish = maconfig.getInt("Natural.Silverfish.spawnymin", 1);
        spawnListener.spawnyminskeleton = maconfig.getInt("Natural.Skeleton.spawnymin", 1);
        spawnListener.spawnyminslime = maconfig.getInt("Natural.Slime.spawnymin", 1);
        spawnListener.spawnyminspider = maconfig.getInt("Natural.Spider.spawnymin", 1);
        spawnListener.spawnyminsquid = maconfig.getInt("Natural.Squid.spawnymin", 1);
        spawnListener.spawnyminvillager = maconfig.getInt("Natural.Villager.spawnymin", 1);
        spawnListener.spawnyminzombie = maconfig.getInt("Natural.Zombie.spawnymin", 1);

        spawnListener.spawnymaxbat = maconfig.getInt("Natural.Bat.spawnymax", 255);
        spawnListener.spawnymaxwither = maconfig.getInt("Natural.Wither.spawnymax", 255);
        spawnListener.spawnymaxwitherskeleton = maconfig.getInt("Natural.WitherSkeleton.spawnymax", 255);
        spawnListener.spawnymaxwolf = maconfig.getInt("Natural.Wolf.spawnymax", 255);
        spawnListener.spawnymaxmushroomcow = maconfig.getInt("Natural.MushroomCow.spawnymax", 255);
        spawnListener.spawnymaxmagmacube = maconfig.getInt("Natural.MagmaCube.spawnymax", 255);
        spawnListener.spawnymaxblaze = maconfig.getInt("Natural.Blaze.spawnymax", 255);
        spawnListener.spawnymaxhorse = maconfig.getInt("Natural.Horse.spawnymax", 255);
        spawnListener.spawnymaxcavespider = maconfig.getInt("Natural.CaveSpider.spawnymax", 255);
        spawnListener.spawnymaxchicken = maconfig.getInt("Natural.Chicken.spawnymax", 255);
        spawnListener.spawnymaxcow = maconfig.getInt("Natural.Cow.spawnymax", 255);
        spawnListener.spawnymaxcreeper = maconfig.getInt("Natural.Creeper.spawnymax", 255);
        spawnListener.spawnymaxenderman = maconfig.getInt("Natural.Enderman.spawnymax", 255);
        spawnListener.spawnymaxghast = maconfig.getInt("Natural.Ghast.spawnymax", 255);
        spawnListener.spawnymaxirongolem = maconfig.getInt("Natural.IronGolem.spawnymax", 255);
        spawnListener.spawnymaxocelot = maconfig.getInt("Natural.Ocelot.spawnymax", 255);
        spawnListener.spawnymaxpig = maconfig.getInt("Natural.Pig.spawnymax", 255);
        spawnListener.spawnymaxpigzombie = maconfig.getInt("Natural.PigZombie.spawnymax", 255);
        spawnListener.spawnymaxsheep = maconfig.getInt("Natural.Sheep.spawnymax", 255);
        spawnListener.spawnymaxsilverfish = maconfig.getInt("Natural.Silverfish.spawnymax", 255);
        spawnListener.spawnymaxskeleton = maconfig.getInt("Natural.Skeleton.spawnymax", 255);
        spawnListener.spawnymaxslime = maconfig.getInt("Natural.Slime.spawnymax", 255);
        spawnListener.spawnymaxspider = maconfig.getInt("Natural.Spider.spawnymax", 255);
        spawnListener.spawnymaxsquid = maconfig.getInt("Natural.Squid.spawnymax", 255);
        spawnListener.spawnymaxvillager = maconfig.getInt("Natural.Villager.spawnymax", 255);
        spawnListener.spawnymaxzombie = maconfig.getInt("Natural.Zombie.spawnymax", 255);

        spawnListener.replacementbat = EntityType.fromName(maconfig.getString("Natural.Bat.replacement", "Bat"));
        spawnListener.replacementwither = EntityType.fromName(maconfig.getString("Natural.Wither.replacement", "Wither"));
        spawnListener.replacementwitherskeleton = EntityType.fromName(maconfig.getString("Natural.WitherSkeleton.replacement", "WitherSkull"));
        //	if(spawnListener.replacementwitherskeleton==EntityType.WITHER_SKULL){}
        spawnListener.replacementwolf = EntityType.fromName(maconfig.getString("Natural.Wolf.replacement", "Wolf"));
        spawnListener.replacementmagmacube = EntityType.fromName(maconfig.getString("Natural.MagmaCube.replacement", "LavaSlime"));
        spawnListener.replacementmushroomcow = EntityType.fromName(maconfig.getString("Natural.MushroomCow.replacement", "MushroomCow"));
        spawnListener.replacementblaze = EntityType.fromName(maconfig.getString("Natural.Blaze.replacement", "Blaze"));
        spawnListener.replacementhorse = EntityType.fromName(maconfig.getString("Natural.Horse.replacement", "Horse"));
        if (spawnListener.replacementhorse == null) {
            spawnListener.replacementhorse = EntityType.HORSE;
        }
        spawnListener.replacementcavespider = EntityType.fromName(maconfig.getString("Natural.CaveSpider.replacement", "CaveSpider"));
        spawnListener.replacementchicken = EntityType.fromName(maconfig.getString("Natural.Chicken.replacement", "Chicken"));
        spawnListener.replacementcow = EntityType.fromName(maconfig.getString("Natural.Cow.replacement", "Cow"));
        spawnListener.replacementcreeper = EntityType.fromName(maconfig.getString("Natural.Creeper.replacement", "Creeper"));
        spawnListener.replacementenderman = EntityType.fromName(maconfig.getString("Natural.Enderman.replacement", "Enderman"));
        spawnListener.replacementghast = EntityType.fromName(maconfig.getString("Natural.Ghast.replacement", "Ghast"));
        spawnListener.replacementirongolem = EntityType.fromName(maconfig.getString("Natural.IronGolem.replacement", "IronGolem"));
        if (maconfig.getString("Natural.IronGolem.replacement", "IronGolem") == "IronGolem") {
            spawnListener.replacementirongolem = EntityType.IRON_GOLEM;
        }
        spawnListener.replacementocelot = EntityType.fromName(maconfig.getString("Natural.Ocelot.replacement", "Ocelot"));
        if (maconfig.getString("Natural.Ocelot.replacement", "Ocelot") == "Ocelot") {
            spawnListener.replacementocelot = EntityType.OCELOT;
        }


        spawnListener.replacementpig = EntityType.fromName(maconfig.getString("Natural.Pig.replacement", "Pig"));
        spawnListener.replacementpigzombie = EntityType.fromName(maconfig.getString("Natural.PigZombie.replacement", "PigZombie"));
        spawnListener.replacementsheep = EntityType.fromName(maconfig.getString("Natural.Sheep.replacement", "Sheep"));
        spawnListener.replacementsilverfish = EntityType.fromName(maconfig.getString("Natural.Silverfish.replacement", "Silverfish"));
        spawnListener.replacementskeleton = EntityType.fromName(maconfig.getString("Natural.Skeleton.replacement", "Skeleton"));
        spawnListener.replacementslime = EntityType.fromName(maconfig.getString("Natural.Slime.replacement", "Slime"));
        spawnListener.replacementspider = EntityType.fromName(maconfig.getString("Natural.Spider.replacement", "Spider"));
        spawnListener.replacementsquid = EntityType.fromName(maconfig.getString("Natural.Squid.replacement", "Squid"));
        spawnListener.replacementvillager = EntityType.fromName(maconfig.getString("Natural.Villager.replacement", "Villager"));
        spawnListener.replacementzombie = EntityType.fromName(maconfig.getString("Natural.Zombie.replacement", "Zombie"));
    }

    private void setspawnconfigs() {
        try {
            maconfig.set("Natural.Creeper.spawn", spawnListener.spawncreeper);
            maconfig.set("Natural.Enderman.spawn", spawnListener.spawnenderman);
            maconfig.set("Natural.Skeleton.spawn", spawnListener.spawnskeleton);
            maconfig.set("Natural.Zombie.spawn", spawnListener.spawnzombie);
            maconfig.set("Natural.Bat.spawn", spawnListener.spawnbat);
            maconfig.set("Natural.Blaze.spawn", spawnListener.spawnblaze);
            maconfig.set("Natural.CaveSpider.spawn", spawnListener.spawncavespider);
            maconfig.set("Natural.Chicken.spawn", spawnListener.spawnchicken);
            maconfig.set("Natural.Cow.spawn", spawnListener.spawncow);
            maconfig.set("Natural.Ghast.spawn", spawnListener.spawnghast);
            maconfig.set("Natural.Horse.spawn", spawnListener.spawnhorse);
            maconfig.set("Natural.IronGolem.spawn", spawnListener.spawnirongolem);
            maconfig.set("Natural.Ocelot.spawn", spawnListener.spawnocelot);
            maconfig.set("Natural.MagmaCube.spawn", spawnListener.spawnmagmacube);
            maconfig.set("Natural.MushroomCow.spawn", spawnListener.spawnmushroomcow);
            maconfig.set("Natural.Pig.spawn", spawnListener.spawnpig);
            maconfig.set("Natural.PigZombie.spawn", spawnListener.spawnpigzombie);
            maconfig.set("Natural.Sheep.spawn", spawnListener.spawnsheep);
            maconfig.set("Natural.Silverfish.spawn", spawnListener.spawnsilverfish);
            maconfig.set("Natural.Slime.spawn", spawnListener.spawnslime);
            maconfig.set("Natural.Spider.spawn", spawnListener.spawnspider);
            maconfig.set("Natural.Squid.spawn", spawnListener.spawnsquid);
            maconfig.set("Natural.Villager.spawn", spawnListener.spawnvillager);
            maconfig.set("Natural.Wither.spawn", spawnListener.spawnwither);
            maconfig.set("Natural.WitherSkeleton.spawn", spawnListener.spawnwitherskeleton);
            maconfig.set("Natural.Wolf.spawn", spawnListener.spawnwolf);


            maconfig.set("Natural.Bat.spawncount", spawnListener.spawncountbat);
            maconfig.set("Natural.Blaze.spawncount", spawnListener.spawncountblaze);
            maconfig.set("Natural.CaveSpider.spawncount", spawnListener.spawncountcavespider);
            maconfig.set("Natural.Chicken.spawncount", spawnListener.spawncountchicken);
            maconfig.set("Natural.Cow.spawncount", spawnListener.spawncountcow);
            maconfig.set("Natural.Creeper.spawncount", spawnListener.spawncountcreeper);
            maconfig.set("Natural.Enderman.spawncount", spawnListener.spawncountenderman);
            maconfig.set("Natural.Ghast.spawncount", spawnListener.spawncountghast);
            maconfig.set("Natural.Horse.spawncount", spawnListener.spawncounthorse);
            maconfig.set("Natural.IronGolem.spawncount", spawnListener.spawncountirongolem);
            maconfig.set("Natural.MagmaCube.spawncount", spawnListener.spawncountmagmacube);
            maconfig.set("Natural.MushroomCow.spawncount", spawnListener.spawncountmushroomcow);
            maconfig.set("Natural.Ocelot.spawncount", spawnListener.spawncountocelot);
            maconfig.set("Natural.Pig.spawncount", spawnListener.spawncountpig);
            maconfig.set("Natural.PigZombie.spawncount", spawnListener.spawncountpigzombie);
            maconfig.set("Natural.Sheep.spawncount", spawnListener.spawncountsheep);
            maconfig.set("Natural.Silverfish.spawncount", spawnListener.spawncountsilverfish);
            maconfig.set("Natural.Skeleton.spawncount", spawnListener.spawncountskeleton);
            maconfig.set("Natural.Slime.spawncount", spawnListener.spawncountslime);
            maconfig.set("Natural.Spider.spawncount", spawnListener.spawncountspider);
            maconfig.set("Natural.Squid.spawncount", spawnListener.spawncountsquid);
            maconfig.set("Natural.Villager.spawncount", spawnListener.spawncountvillager);
            maconfig.set("Natural.Wither.spawncount", spawnListener.spawncountwither);
            maconfig.set("Natural.WitherSkeleton.spawncount", spawnListener.spawncountwitherskeleton);
            maconfig.set("Natural.Wolf.spawncount", spawnListener.spawncountwolf);
            maconfig.set("Natural.Zombie.spawncount", spawnListener.spawncountzombie);

            maconfig.set("Natural.Bat.spawnchance", spawnListener.spawnchancebat);
            maconfig.set("Natural.Blaze.spawnchance", spawnListener.spawnchanceblaze);
            maconfig.set("Natural.CaveSpider.spawnchance", spawnListener.spawnchancecavespider);
            maconfig.set("Natural.Chicken.spawnchance", spawnListener.spawnchancechicken);
            maconfig.set("Natural.Cow.spawnchance", spawnListener.spawnchancecow);
            maconfig.set("Natural.Creeper.spawnchance", spawnListener.spawnchancecreeper);
            maconfig.set("Natural.Enderman.spawnchance", spawnListener.spawnchanceenderman);
            maconfig.set("Natural.Ghast.spawnchance", spawnListener.spawnchanceghast);
            maconfig.set("Natural.Horse.spawnchance", spawnListener.spawnchancehorse);
            maconfig.set("Natural.IronGolem.spawnchance", spawnListener.spawnchanceirongolem);
            maconfig.set("Natural.MagmaCube.spawnchance", spawnListener.spawnchancemagmacube);
            maconfig.set("Natural.MushroomCow.spawnchance", spawnListener.spawnchancemushroomcow);
            maconfig.set("Natural.Ocelot.spawnchance", spawnListener.spawnchanceocelot);
            maconfig.set("Natural.Pig.spawnchance", spawnListener.spawnchancepig);
            maconfig.set("Natural.PigZombie.spawnchance", spawnListener.spawnchancepigzombie);
            maconfig.set("Natural.Sheep.spawnchance", spawnListener.spawnchancesheep);
            maconfig.set("Natural.Silverfish.spawnchance", spawnListener.spawnchancesilverfish);
            maconfig.set("Natural.Skeleton.spawnchance", spawnListener.spawnchanceskeleton);
            maconfig.set("Natural.Slime.spawnchance", spawnListener.spawnchanceslime);
            maconfig.set("Natural.Spider.spawnchance", spawnListener.spawnchancespider);
            maconfig.set("Natural.Squid.spawnchance", spawnListener.spawnchancesquid);
            maconfig.set("Natural.Villager.spawnchance", spawnListener.spawnchancevillager);
            maconfig.set("Natural.Wither.spawnchance", spawnListener.spawnchancewither);
            maconfig.set("Natural.WitherSkeleton.spawnchance", spawnListener.spawnchancewitherskeleton);
            maconfig.set("Natural.Wolf.spawnchance", spawnListener.spawnchancewolf);
            maconfig.set("Natural.Zombie.spawnchance", spawnListener.spawnchancezombie);

            maconfig.set("Natural.Bat.spawnymin", spawnListener.spawnyminbat);
            maconfig.set("Natural.Blaze.spawnymin", spawnListener.spawnyminblaze);
            maconfig.set("Natural.CaveSpider.spawnymin", spawnListener.spawnymincavespider);
            maconfig.set("Natural.Chicken.spawnymin", spawnListener.spawnyminchicken);
            maconfig.set("Natural.Cow.spawnymin", spawnListener.spawnymincow);
            maconfig.set("Natural.Creeper.spawnymin", spawnListener.spawnymincreeper);
            maconfig.set("Natural.Enderman.spawnymin", spawnListener.spawnyminenderman);
            maconfig.set("Natural.Ghast.spawnymin", spawnListener.spawnyminghast);
            maconfig.set("Natural.Horse.spawnymin", spawnListener.spawnyminhorse);
            maconfig.set("Natural.IronGolem.spawnymin", spawnListener.spawnyminirongolem);
            maconfig.set("Natural.MagmaCube.spawnymin", spawnListener.spawnyminmagmacube);
            maconfig.set("Natural.MushroomCow.spawnymin", spawnListener.spawnyminmushroomcow);
            maconfig.set("Natural.Ocelot.spawnymin", spawnListener.spawnyminocelot);
            maconfig.set("Natural.Pig.spawnymin", spawnListener.spawnyminpig);
            maconfig.set("Natural.PigZombie.spawnymin", spawnListener.spawnyminpigzombie);
            maconfig.set("Natural.Sheep.spawnymin", spawnListener.spawnyminsheep);
            maconfig.set("Natural.Silverfish.spawnymin", spawnListener.spawnyminsilverfish);
            maconfig.set("Natural.Skeleton.spawnymin", spawnListener.spawnyminskeleton);
            maconfig.set("Natural.Slime.spawnymin", spawnListener.spawnyminslime);
            maconfig.set("Natural.Spider.spawnymin", spawnListener.spawnyminspider);
            maconfig.set("Natural.Squid.spawnymin", spawnListener.spawnyminsquid);
            maconfig.set("Natural.Villager.spawnymin", spawnListener.spawnyminvillager);
            maconfig.set("Natural.Wither.spawnymin", spawnListener.spawnyminwither);
            maconfig.set("Natural.WitherSkeleton.spawnymin", spawnListener.spawnyminwitherskeleton);
            maconfig.set("Natural.Wolf.spawnymin", spawnListener.spawnyminwolf);
            maconfig.set("Natural.Zombie.spawnymin", spawnListener.spawnyminzombie);

            maconfig.set("Natural.Bat.spawnymax", spawnListener.spawnymaxbat);
            maconfig.set("Natural.Blaze.spawnymax", spawnListener.spawnymaxblaze);
            maconfig.set("Natural.CaveSpider.spawnymax", spawnListener.spawnymaxcavespider);
            maconfig.set("Natural.Chicken.spawnymax", spawnListener.spawnymaxchicken);
            maconfig.set("Natural.Cow.spawnymax", spawnListener.spawnymaxcow);
            maconfig.set("Natural.Creeper.spawnymax", spawnListener.spawnymaxcreeper);
            maconfig.set("Natural.Enderman.spawnymax", spawnListener.spawnymaxenderman);
            maconfig.set("Natural.Ghast.spawnymax", spawnListener.spawnymaxghast);
            maconfig.set("Natural.Horse.spawnymax", spawnListener.spawnymaxhorse);
            maconfig.set("Natural.IronGolem.spawnymax", spawnListener.spawnymaxirongolem);
            maconfig.set("Natural.MagmaCube.spawnymax", spawnListener.spawnymaxmagmacube);
            maconfig.set("Natural.MushroomCow.spawnymax", spawnListener.spawnymaxmushroomcow);
            maconfig.set("Natural.Ocelot.spawnymax", spawnListener.spawnymaxocelot);
            maconfig.set("Natural.Pig.spawnymax", spawnListener.spawnymaxpig);
            maconfig.set("Natural.PigZombie.spawnymax", spawnListener.spawnymaxpigzombie);
            maconfig.set("Natural.Sheep.spawnymax", spawnListener.spawnymaxsheep);
            maconfig.set("Natural.Silverfish.spawnymax", spawnListener.spawnymaxsilverfish);
            maconfig.set("Natural.Skeleton.spawnymax", spawnListener.spawnymaxskeleton);
            maconfig.set("Natural.Slime.spawnymax", spawnListener.spawnymaxslime);
            maconfig.set("Natural.Spider.spawnymax", spawnListener.spawnymaxspider);
            maconfig.set("Natural.Squid.spawnymax", spawnListener.spawnymaxsquid);
            maconfig.set("Natural.Villager.spawnymax", spawnListener.spawnymaxvillager);
            maconfig.set("Natural.Wither.spawnymax", spawnListener.spawnymaxwither);
            maconfig.set("Natural.WitherSkeleton.spawnymax", spawnListener.spawnymaxwitherskeleton);
            maconfig.set("Natural.Wolf.spawnymax", spawnListener.spawnymaxwolf);
            maconfig.set("Natural.Zombie.spawnymax", spawnListener.spawnymaxzombie);

            maconfig.set("Natural.Bat.replacement", (spawnListener.replacementbat.getName()));
            maconfig.set("Natural.Wolf.replacement", (spawnListener.replacementwolf.getName()));
            maconfig.set("Natural.Blaze.replacement", (spawnListener.replacementblaze.getName()));
            maconfig.set("Natural.CaveSpider.replacement", (spawnListener.replacementcavespider.getName()));
            maconfig.set("Natural.Chicken.replacement", (spawnListener.replacementchicken.getName()));
            maconfig.set("Natural.Cow.replacement", (spawnListener.replacementcow.getName()));
            maconfig.set("Natural.Creeper.replacement", (spawnListener.replacementcreeper.getName()));
            maconfig.set("Natural.Enderman.replacement", (spawnListener.replacementenderman.getName()));
            maconfig.set("Natural.Ghast.replacement", (spawnListener.replacementghast.getName()));
            if (spawnListener.replacementhorse.getName() == null) {
                maconfig.set("Natural.Horse.replacement", ("Horse"));
            } else {
                maconfig.set("Natural.Horse.replacement", (spawnListener.replacementhorse.getName()));
            }
            if (spawnListener.replacementirongolem == null) {
                System.out.println("potatoes");
            }
            maconfig.set("Natural.IronGolem.replacement", (spawnListener.replacementirongolem.getName()));
            maconfig.set("Natural.MagmaCube.replacement", (spawnListener.replacementmagmacube.getName()));
            maconfig.set("Natural.MushroomCow.replacement", (spawnListener.replacementmushroomcow.getName()));
            maconfig.set("Natural.Ocelot.replacement", (spawnListener.replacementocelot.getName()));
            maconfig.set("Natural.Pig.replacement", (spawnListener.replacementpig.getName()));
            maconfig.set("Natural.PigZombie.replacement", (spawnListener.replacementpigzombie.getName()));
            maconfig.set("Natural.Sheep.replacement", (spawnListener.replacementsheep.getName()));
            maconfig.set("Natural.Silverfish.replacement", (spawnListener.replacementsilverfish.getName()));
            maconfig.set("Natural.Skeleton.replacement", (spawnListener.replacementskeleton.getName()));
            maconfig.set("Natural.Slime.replacement", (spawnListener.replacementslime.getName()));
            maconfig.set("Natural.Spider.replacement", (spawnListener.replacementspider.getName()));
            maconfig.set("Natural.Squid.replacement", (spawnListener.replacementsquid.getName()));
            maconfig.set("Natural.Villager.replacement", (spawnListener.replacementvillager.getName()));
            try {
                maconfig.set("Natural.Wither.replacement", (spawnListener.replacementwither.getName()));
            } catch (Exception e) {
                maconfig.set("Natural.Wither.replacement", ("Wither"));
            }
            maconfig.set("Natural.WitherSkeleton.replacement", (spawnListener.replacementwitherskeleton.getName()));
            maconfig.set("Natural.Zombie.replacement", (spawnListener.replacementzombie.getName()));
        } catch (NullPointerException e) {
            System.out.println("Monster Apocalypse: Generic error in spawn configs, most likely caused by using the wrong craftbukkit version or user defined config error. Plugin disabled.");
            dontrun = true;
            e.printStackTrace();
        }
    }

    private void loadbonusspawnconfigs() {
        for (int i = 0; i < this.bonusenabled.length; i++) {
            bonusenabled[i] = maconfig.getBoolean("Bonus Spawns." + allmobnames.get(i) + ".spawn", false);
        }
        for (int i = 0; i < this.bonuschance.length; i++) {
            bonuschance[i] = maconfig.getDouble("Bonus Spawns." + allmobnames.get(i) + ".chance", 100.0);
        }
        bonustypes = new ArrayList<>();
        for (int i = 0; i < this.bonusenabled.length; i++) {
            String name = allmobnames.get(i);
            if (bonusenabled[i]) {
                if (name.equalsIgnoreCase("IronGolem")) {
                    bonustypes.add(new EntitySpawn(EntityType.IRON_GOLEM, bonuschance[i]));
                } else if (name.equalsIgnoreCase("Ocelot")) {
                    bonustypes.add(new EntitySpawn(EntityType.OCELOT, bonuschance[i]));
                } else if (name.equalsIgnoreCase("WitherSkeleton")) {
                    bonustypes.add(new EntitySpawn(EntityType.WITHER_SKULL, bonuschance[i]));

                } else {
                    bonustypes.add(new EntitySpawn(EntityType.fromName(name), bonuschance[i]));
                }
            }
        }
    }

    private void setbonusspawnconfigs() {
        for (int i = 0; i < this.bonusenabled.length; i++) {
            maconfig.set("Bonus Spawns." + allmobnames.get(i) + ".spawn", bonusenabled[i]);
        }
        for (int i = 0; i < this.bonuschance.length; i++) {
            maconfig.set("Bonus Spawns." + allmobnames.get(i) + ".chance", bonuschance[i]);
        }
    }

    private void sumbonus() {
        bonussum = 0;
        for (int i = 0; i < this.bonusenabled.length; i++) {
            if (bonuschance[i] < 0) bonuschance[i] = 0.0000001;
            if (bonusenabled[i]) bonussum += bonuschance[i];
        }
    }

    private void sumnatural() {
        naturalsum = 0;
        for (int i = 0; i < this.naturalenabled.length; i++) {
            if (naturalchance[i] < 0) naturalchance[i] = 0.0000001;
            if (naturalenabled[i]) naturalsum += naturalchance[i];
        }
    }

    private void loadnaturalspawnconfigs() {
        for (int i = 0; i < this.naturalenabled.length; i++) {
            naturalenabled[i] = maconfig.getBoolean("Naturalistic Bonus Spawns." + allmobnames.get(i) + ".spawn", false);
        }
        for (int i = 0; i < this.naturalchance.length; i++) {
            naturalchance[i] = maconfig.getDouble("Naturalistic Bonus Spawns." + allmobnames.get(i) + ".chance", 100.0);
        }
        naturaltypes = new ArrayList<>();
        for (int i = 0; i < this.naturalenabled.length; i++) {
            String name = allmobnames.get(i);
            if (naturalenabled[i]) {
                if (name.equalsIgnoreCase("IronGolem")) {
                    naturaltypes.add(new EntitySpawn(EntityType.IRON_GOLEM, naturalchance[i]));
                } else if (name.equalsIgnoreCase("Ocelot")) {
                    naturaltypes.add(new EntitySpawn(EntityType.OCELOT, naturalchance[i]));
                } else if (name.equalsIgnoreCase("WitherSkeleton")) {
                    naturaltypes.add(new EntitySpawn(EntityType.WITHER_SKULL, naturalchance[i]));

                } else {
                    naturaltypes.add(new EntitySpawn(EntityType.fromName(name), naturalchance[i]));
                }
            }
        }
    }

    private void setnaturalspawnconfigs() {
        for (int i = 0; i < this.naturalenabled.length; i++) {
            maconfig.set("Naturalistic Bonus Spawns." + allmobnames.get(i) + ".spawn", naturalenabled[i]);
        }
        for (int i = 0; i < this.naturalchance.length; i++) {
            maconfig.set("Naturalistic Bonus Spawns." + allmobnames.get(i) + ".chance", naturalchance[i]);
        }
    }

    private void loadpropertyconfigs() {
        Mob mob;
        for (Mob aMoblist : moblist) {
            mob = aMoblist;
            mob.health = maconfig.getInt("Monster Properties." + mob.name + ".health", mob.defaulthealth);
            mob.damage = maconfig.getInt("Monster Properties." + mob.name + ".damage", mob.defaultdamage);
            mob.truedamage = maconfig.getInt("Advanced Monster Properties." + mob.name + ".true damage (completely ignores armor)", 0);
            mob.deathexplode = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".explode on death", false);
            mob.deathfire = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".death explosion causes fire?", false);
            mob.deathradius = maconfig.getDouble("Advanced Monster Properties." + mob.name + ".death explosion radius", 1.0);
            mob.deathspawn = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".spawn new mob on death", false);
            mob.deathspawntype = EntityType.fromName(maconfig.getString("Advanced Monster Properties." + mob.name + ".death spawn type", mob.name));
            if (mob.deathspawntype == null) {
                if ("EnderDragon".equalsIgnoreCase(getConfig().getString("Advanced Monster Properties." + mob.name + ".death spawn type", mob.name)
                )) {
                    mob.deathspawntype = EntityType.ENDER_DRAGON;
                }
                if ("Ocelot".equalsIgnoreCase(getConfig().getString("Advanced Monster Properties." + mob.name + ".death spawn type", mob.name)
                )) {
                    mob.deathspawntype = EntityType.OCELOT;
                }
                if ("IronGolem".equalsIgnoreCase(getConfig().getString("Advanced Monster Properties." + mob.name + ".death spawn type", mob.name)
                )) {
                    mob.deathspawntype = EntityType.IRON_GOLEM;
                }
            }
            mob.deathcount = maconfig.getInt("Advanced Monster Properties." + mob.name + ".death spawn count", 1);
            mob.deathchance = maconfig.getDouble("Advanced Monster Properties." + mob.name + ".death spawn chance", 100d);
            loadimmunities(mob);
            if (mob.name.equalsIgnoreCase("Ghast") || mob.name.equalsIgnoreCase("Creeper")) {
                mob.standardradius = maconfig.getDouble("Monster Properties." + mob.name + ".standard explosion radius", mob.standardradius);
                mob.standardfire = maconfig.getBoolean("Monster Properties." + mob.name + ".standard fire", mob.standardfire);
            }
            if (mob.name.equalsIgnoreCase("Enderman") || mob.name.equalsIgnoreCase("Skeleton") || mob.name.equalsIgnoreCase("Zombie")) {
                mob.sunlightdamage = maconfig.getBoolean("Monster Properties." + mob.name + ".burns in sunlight", true);
            }

            if (mob.name.equalsIgnoreCase("Spider")
                    || mob.name.equalsIgnoreCase("CaveSpider")

                    ) {
                mob.megaaggro = maconfig.getBoolean("Monster Properties." + mob.name + ".uses mega-aggro", false);
                //	mob.pillar=maconfig.getBoolean("Advanced Monster Properties."+mob.name+".can nerdpole up", false);
                //	mob.bridge=maconfig.getBoolean("Advanced Monster Properties."+mob.name+".can make air bridges across", false);
            }
            if (mob.name.equalsIgnoreCase("Skeleton") || mob.name.equalsIgnoreCase("Zombie")
                    || mob.name.equalsIgnoreCase("WitherSkeleton")
                    || mob.name.equalsIgnoreCase("PigZombie")
                    || mob.name.equalsIgnoreCase("Creeper")
                    ) {
                mob.pillar = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".can nerdpole up", false);
                mob.bridge = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".can make air bridges across", false);
                mob.megaaggro = maconfig.getBoolean("Monster Properties." + mob.name + ".uses mega-aggro", true);
            }
            if (mob.name.equalsIgnoreCase("Skeleton") || mob.name.equalsIgnoreCase("PigZombie")
                    || mob.name.equalsIgnoreCase("WitherSkeleton")
                    ) {
                mob.standarditems = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".spawns with standard equipment", true);
            }
            if (mob.hostilemelee) {
                try {
                    mob.potionstrings = maconfig.getStringList("Advanced Monster Properties." + mob.name + ".on hit potion effects");
                } catch (NullPointerException e) {
                    mob.potionstrings = new ArrayList<>();
                    mob.potionstrings.add("EFFECT:durationticks(integer):amplifier(integer):chance(double)");
                    mob.potionstrings.add("Example: CONFUSION:10:1:56.7");
                }
                if (mob.potionstrings == null || mob.potionstrings.isEmpty()) {
                    mob.potionstrings = new ArrayList<>();
                    mob.potionstrings.add("EFFECT:durationticks(integer):amplifier(integer):chance(double)");
                    mob.potionstrings.add("Example: CONFUSION:10:1:56.7");
                }
                mob.compilePotions();
            }
        }
        witherenable = maconfig.getBoolean("Advanced Monster Properties.WitherSkeleton.Enable Wither Skeletons to fire Skeleton super arrows", false);
        toggleskeleton = maconfig.getBoolean("Advanced Monster Properties.Skeleton.Enable Super Arrows", false);
        skeletonperiod = maconfig.getInt("Advanced Monster Properties.Skeleton.Super Arrow period", 5000) / 50;
        skeletonchance = maconfig.getDouble("Advanced Monster Properties.Skeleton.Super Arrow chance", 100.0d);
        skeletonrange = maconfig.getDouble("Advanced Monster Properties.Skeleton.Super Arrow X/Z attack range", 20.0d);
        skeletonradius = maconfig.getInt("Advanced Monster Properties.Skeleton.Super Arrow Player Protection Radius", 0);
        skeletony = maconfig.getInt("Advanced Monster Properties.Skeleton.Super Arrow Y attack range", 80);
        setoggle = maconfig.getBoolean("Advanced Monster Properties.Skeleton.Make skeleton arrows explode?", false);
        serad = maconfig.getDouble("Advanced Monster Properties.Skeleton.Arrow explosion radius", 2.0d);
        sechance = maconfig.getDouble("Advanced Monster Properties.Skeleton.Arrow chance to explode on collision", 100.0d);
        sefire = maconfig.getBoolean("Advanced Monster Properties.Skeleton.Arrow explosion causes fire?", false);
        newzombiemethod = maconfig.getBoolean("Advanced Monster Properties.Zombie.Use the new zombie destruction method, with block hp?", true);
        try {
            zombielist = maconfig.getStringList("Advanced Monster Properties.Zombie.Attackable blocks");
        } catch (NullPointerException e) {
            zombielist = new ArrayList<>();
            zombielist.add("block:seconds");
        }
        if (zombielist == null || zombielist.isEmpty()) {
            zombielist = new ArrayList<>();
            zombielist.add("block:seconds");
        }
        for (String aZombielist : zombielist) {
            waller.wallmanager.stringadd(aZombielist);
        }
//		togglesuperskeletons=maconfig.getBoolean("Advanced Monster Properties.Skeleton.Nightmare aim rework", false);
    }

    private void setspawnfooter() {
        try {
            feetlist = maconfig.getStringList("Spawn Block Blacklist");
        } catch (NullPointerException e) {
            feetlist = new ArrayList<>();
            feetlist.add("block");
        }
        if (feetlist == null || feetlist.isEmpty()) {
            feetlist = new ArrayList<>();
            feetlist.add("block");
        }
        for (String aFeetlist : feetlist) {
            spawnfooter.addblock(aFeetlist);
        }
        maconfig.set("Spawn Block Blacklist", feetlist);
    }

    private void loadnaturalisticlimits() {
        Mob mob;
        for (Mob aMoblist : moblist) {
            mob = aMoblist;
            mob.ymin = maconfig.getInt("Natural." + mob.name + ".spawnymin", 0);
            mob.ymax = maconfig.getInt("Natural." + mob.name + ".spawnymax", 255);
        }
    }

    private void setpropertyconfigs() {
        Mob mob;
        for (Mob aMoblist : moblist) {
            mob = aMoblist;
            maconfig.set("Monster Properties." + mob.name + ".health", mob.health);
            maconfig.set("Monster Properties." + mob.name + ".damage", mob.damage);
            maconfig.set("Advanced Monster Properties." + mob.name + ".true damage (completely ignores armor)", mob.truedamage);
            maconfig.set("Advanced Monster Properties." + mob.name + ".explode on death", mob.deathexplode);
            maconfig.set("Advanced Monster Properties." + mob.name + ".death explosion causes fire?", mob.deathfire);
            maconfig.set("Advanced Monster Properties." + mob.name + ".death explosion radius", mob.deathradius);

            maconfig.set("Advanced Monster Properties." + mob.name + ".spawn new mob on death", mob.deathspawn);
            try {
                maconfig.set("Advanced Monster Properties." + mob.name + ".death spawn type", mob.deathspawntype.getName());
            } catch (NullPointerException e) {
            }
            maconfig.set("Advanced Monster Properties." + mob.name + ".death spawn count", mob.deathcount);
            maconfig.set("Advanced Monster Properties." + mob.name + ".death spawn chance", mob.deathchance);

            setimmunities(mob);
            if (mob.name == "Ghast" || mob.name == "Creeper") {
                maconfig.set("Monster Properties." + mob.name + ".standard explosion radius", mob.standardradius);
                maconfig.set("Monster Properties." + mob.name + ".standard fire", mob.standardfire);
            }
            if (mob.name == "Enderman" || mob.name == "Skeleton" || mob.name == "Zombie") {
                maconfig.set("Monster Properties." + mob.name + ".burns in sunlight", mob.sunlightdamage);
            }
            if (mob.name.equalsIgnoreCase("Spider")
                    || mob.name.equalsIgnoreCase("CaveSpider")
                    ) {
                maconfig.set("Monster Properties." + mob.name + ".uses mega-aggro", mob.megaaggro);
            }
            if (mob.name.equalsIgnoreCase("Skeleton") || mob.name.equalsIgnoreCase("Zombie")
                    || mob.name.equalsIgnoreCase("WitherSkeleton")
                    || mob.name.equalsIgnoreCase("PigZombie")
                    || mob.name.equalsIgnoreCase("Creeper")
                    ) {
                maconfig.set("Advanced Monster Properties." + mob.name + ".can nerdpole up", mob.pillar);
                maconfig.set("Advanced Monster Properties." + mob.name + ".can make air bridges across", mob.pillar);
                maconfig.set("Monster Properties." + mob.name + ".uses mega-aggro", mob.megaaggro);
            }
            if (mob.name.equalsIgnoreCase("Skeleton") || mob.name.equalsIgnoreCase("PigZombie")
                    || mob.name.equalsIgnoreCase("WitherSkeleton")
                    ) {
                maconfig.set("Advanced Monster Properties." + mob.name + ".spawns with standard equipment", mob.standarditems);
            }
            if (mob.hostilemelee) {
                maconfig.set("Advanced Monster Properties." + mob.name + ".on hit potion effects", mob.potionstrings);
            }
        }
        maconfig.set("Advanced Monster Properties.WitherSkeleton.Enable Wither Skeletons to fire Skeleton super arrows", witherenable);
        maconfig.set("Advanced Monster Properties.Skeleton.Enable Super Arrows", toggleskeleton);
        maconfig.set("Advanced Monster Properties.Skeleton.Super Arrow period", skeletonperiod * 50);
        maconfig.set("Advanced Monster Properties.Skeleton.Super Arrow chance", skeletonchance);
        maconfig.set("Advanced Monster Properties.Skeleton.Super Arrow X/Z attack range", skeletonrange);
        maconfig.set("Advanced Monster Properties.Skeleton.Super Arrow Y attack range", skeletony);
        maconfig.set("Advanced Monster Properties.Skeleton.Make skeleton arrows explode?", setoggle);
        maconfig.set("Advanced Monster Properties.Skeleton.Arrow explosion radius", serad);
        maconfig.set("Advanced Monster Properties.Skeleton.Super Arrow Player Protection Radius", skeletonradius);
        maconfig.set("Advanced Monster Properties.Skeleton.Arrow chance to explode on collision", sechance);
        maconfig.set("Advanced Monster Properties.Skeleton.Arrow explosion causes fire?", sefire);
        maconfig.set("Advanced Monster Properties.Zombie.Use the new zombie destruction method, with block hp?", newzombiemethod);
        maconfig.set("Advanced Monster Properties.Zombie.Attackable blocks", zombielist);
//		maconfig.set("Advanced Monster Properties.Skeleton.Nightmare aim rework", togglesuperskeletons);
    }

    private void loadimmunities(Mob mob) {
        mob.contactimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.cactus", false);
        mob.drowningimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.drowning", false);
        mob.attackimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.attacks", false);
        mob.explosionimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.explosions", false);
        mob.fallimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.falling", false);
        mob.fireimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.fire", false);
        mob.lavaimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.lava", false);
        mob.projectileimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.projectiles", false);
        mob.suffocationimmune = maconfig.getBoolean("Advanced Monster Properties." + mob.name + ".Immunities.suffocation", false);
    }

    private void setimmunities(Mob mob) {
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.cactus", mob.contactimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.drowning", mob.drowningimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.attacks", mob.attackimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.explosions", mob.explosionimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.falling", mob.fallimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.fire", mob.fireimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.lava", mob.lavaimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.projectiles", mob.projectileimmune);
        maconfig.set("Advanced Monster Properties." + mob.name + ".Immunities.cactus", mob.suffocationimmune);
    }

    private void loaddropconfigs() {
        try {
            dropsbat = maconfig.getStringList("Drops.Bat.drops");
        } catch (NullPointerException e) {
            dropsbat = new ArrayList<>();
            dropsbat.add("item:count:chance");
            dropsbat.add("item:count:chance:datavalue");
        }
        try {
            dropswither = maconfig.getStringList("Drops.Wither.drops");
        } catch (NullPointerException e) {
            dropswither = new ArrayList<>();
            dropswither.add("item:count:chance");
            dropswither.add("item:count:chance:datavalue");
        }
        try {
            dropswitherskeleton = maconfig.getStringList("Drops.WitherSkeleton.drops");
        } catch (NullPointerException e) {
            dropswitherskeleton = new ArrayList<>();
            dropswitherskeleton.add("item:count:chance");
            dropswitherskeleton.add("item:count:chance:datavalue");
        }
        try {
            dropscreeper = maconfig.getStringList("Drops.Creeper.drops");
        } catch (NullPointerException e) {
            dropscreeper = new ArrayList<>();
            dropscreeper.add("item:count:chance");
            dropscreeper.add("item:count:chance:datavalue");
        }
        try {
            dropsenderman = maconfig.getStringList("Drops.Enderman.drops");
        } catch (NullPointerException e) {
            dropsenderman = new ArrayList<>();
            dropsenderman.add("item:count:chance");
        }
        try {
            dropsskeleton = maconfig.getStringList("Drops.Skeleton.drops");
        } catch (NullPointerException e) {
            dropsskeleton = new ArrayList<>();
            dropsskeleton.add("item:count:chance");
        }
        try {
            dropsspider = maconfig.getStringList("Drops.Spider.drops");
        } catch (NullPointerException e) {
            dropsspider = new ArrayList<>();
            dropsspider.add("item:count:chance");
        }
        try {
            dropszombie = maconfig.getStringList("Drops.Zombie.drops");
        } catch (NullPointerException e) {
            dropszombie = new ArrayList<>();
            dropszombie.add("item:count:chance");
        }
        try {
            dropsblaze = maconfig.getStringList("Drops.Blaze.drops");
        } catch (NullPointerException e) {
            dropsblaze = new ArrayList<>();
            dropsblaze.add("item:count:chance");
        }
        try {
            dropscavespider = maconfig.getStringList("Drops.CaveSpider.drops");
        } catch (NullPointerException e) {


            dropscavespider = new ArrayList<>();
            dropscavespider.add("item:count:chance");
        }


        try {
            dropschicken = maconfig.getStringList("Drops.Chicken.drops");
        } catch (NullPointerException e) {
            dropschicken = new ArrayList<>();
            dropschicken.add("item:count:chance");
        }
        try {
            dropscow = maconfig.getStringList("Drops.Cow.drops");
        } catch (NullPointerException e) {
            dropscow = new ArrayList<>();
            dropscow.add("item:count:chance");
        }
        try {
            dropscow = maconfig.getStringList("Drops.Cow.drops");
        } catch (NullPointerException e) {
            dropscow = new ArrayList<>();
            dropscow.add("item:count:chance");
        }
        try {
            dropsghast = maconfig.getStringList("Drops.Ghast.drops");
        } catch (NullPointerException e) {
            dropsghast = new ArrayList<>();
            dropsghast.add("item:count:chance");
        }
        try {
            dropsmagmacube = maconfig.getStringList("Drops.MagmaCube.drops");
        } catch (NullPointerException e) {
            dropsmagmacube = new ArrayList<>();
            dropsmagmacube.add("item:count:chance");
        }
        try {
            dropsmushroomcow = maconfig.getStringList("Drops.MushroomCow.drops");
        } catch (NullPointerException e) {
            dropsmushroomcow = new ArrayList<>();
            dropsmushroomcow.add("item:count:chance");
        }
        try {
            dropspig = maconfig.getStringList("Drops.Pig.drops");
        } catch (NullPointerException e) {
            dropspig = new ArrayList<>();
            dropspig.add("item:count:chance");
        }
        try {
            dropshorse = maconfig.getStringList("Drops.Horse.drops");
        } catch (NullPointerException e) {
            dropshorse = new ArrayList<>();
            dropshorse.add("item:count:chance");
        }
        try {
            dropspigzombie = maconfig.getStringList("Drops.PigZombie.drops");
        } catch (NullPointerException e) {
            dropspigzombie = new ArrayList<>();
            dropspigzombie.add("item:count:chance");
        }
        try {
            dropssheep = maconfig.getStringList("Drops.Sheep.drops");
        } catch (NullPointerException e) {
            dropssheep = new ArrayList<>();
            dropssheep.add("item:count:chance");
        }
        try {
            dropssilverfish = maconfig.getStringList("Drops.Silverfish.drops");
        } catch (NullPointerException e) {
            dropssilverfish = new ArrayList<>();
            dropssilverfish.add("item:count:chance");
        }
        try {
            dropsslime = maconfig.getStringList("Drops.Slime.drops");
        } catch (NullPointerException e) {
            dropsslime = new ArrayList<>();
            dropsslime.add("item:count:chance");
        }
        try {
            dropssquid = maconfig.getStringList("Drops.Squid.drops");
        } catch (NullPointerException e) {
            dropssquid = new ArrayList<>();
            dropssquid.add("item:count:chance");
        }
        try {
            dropsvillager = maconfig.getStringList("Drops.Villager.drops");
        } catch (NullPointerException e) {
            dropsvillager = new ArrayList<>();
            dropsvillager.add("item:count:chance");
        }
        try {
            dropswolf = maconfig.getStringList("Drops.Wolf.drops");
        } catch (NullPointerException e) {
            dropswolf = new ArrayList<>();
            dropswolf.add("item:count:chance");
        }
        try {
            dropsgiant = maconfig.getStringList("Drops.Giant.drops");
        } catch (NullPointerException e) {
            dropsgiant = new ArrayList<>();
            dropsgiant.add("item:count:chance");
        }
        try {
            dropsdragon = maconfig.getStringList("Drops.EnderDragon.drops");
        } catch (NullPointerException e) {
            dropsdragon = new ArrayList<>();
            dropsdragon.add("item:count:chance");
        }
        try {
            dropsocelot = maconfig.getStringList("Drops.Ocelot.drops");
        } catch (NullPointerException e) {
            dropsocelot = new ArrayList<>();
            dropsocelot.add("item:count:chance");
        }
        try {
            dropsirongolem = maconfig.getStringList("Drops.IronGolem.drops");
        } catch (NullPointerException e) {
            dropsirongolem = new ArrayList<>();
            dropsirongolem.add("item:count:chance");
        }

        dropoverbat = maconfig.getBoolean("Drops.Bat.Overwrite?");
        dropovercreeper = maconfig.getBoolean("Drops.Creeper.Overwrite?");
        dropoverenderman = maconfig.getBoolean("Drops.Enderman.Overwrite?");
        dropoverskeleton = maconfig.getBoolean("Drops.Skeleton.Overwrite?");
        dropoverspider = maconfig.getBoolean("Drops.Spider.Overwrite?");
        dropoverzombie = maconfig.getBoolean("Drops.Zombie.Overwrite?");
        dropoverblaze = maconfig.getBoolean("Drops.Blaze.Overwrite?");
        dropovercavespider = maconfig.getBoolean("Drops.CaveSpider.Overwrite?");
        dropoverchicken = maconfig.getBoolean("Drops.Chicken.Overwrite?");
        dropovercow = maconfig.getBoolean("Drops.Cow.Overwrite?");
        dropoverghast = maconfig.getBoolean("Drops.Ghast.Overwrite?");
        dropoverhorse = maconfig.getBoolean("Drops.Horse.Overwrite?");
        dropovermagmacube = maconfig.getBoolean("Drops.MagmaCube.Overwrite?");
        dropovermushroomcow = maconfig.getBoolean("Drops.MushroomCow.Overwrite?");
        dropoverpig = maconfig.getBoolean("Drops.Pig.Overwrite?");
        dropoverpigzombie = maconfig.getBoolean("Drops.PigZombie.Overwrite?");
        dropoversheep = maconfig.getBoolean("Drops.Sheep.Overwrite?");
        dropoversilverfish = maconfig.getBoolean("Drops.Silverfish.Overwrite?");
        dropoverslime = maconfig.getBoolean("Drops.Slime.Overwrite?");
        dropoversquid = maconfig.getBoolean("Drops.Squid.Overwrite?");
        dropovervillager = maconfig.getBoolean("Drops.Villager.Overwrite?");
        dropoverwither = maconfig.getBoolean("Drops.Wither.Overwrite?");
        dropoverwitherskeleton = maconfig.getBoolean("Drops.WitherSkeleton.Overwrite?");
        dropoverwolf = maconfig.getBoolean("Drops.Wolf.Overwrite?");
        dropovergiant = maconfig.getBoolean("Drops.Giant.Overwrite?");
        dropoverocelot = maconfig.getBoolean("Drops.Ocelot.Overwrite?");
        dropoverirongolem = maconfig.getBoolean("Drops.IronGolem.Overwrite?");
        dropoverdragon = maconfig.getBoolean("Drops.EnderDragon.Overwrite?");

        if (dropsbat == null || dropsbat.isEmpty()) {
            dropsbat = new ArrayList<>();
            dropsbat.add("item:count:chance");
            dropsbat.add("item:count:chance");
        }

        if (dropscreeper == null || dropscreeper.isEmpty()) {
            dropscreeper = new ArrayList<>();
            dropscreeper.add("item:count:chance");
            dropscreeper.add("item:count:chance");
        }

        if (dropsenderman == null || dropsenderman.isEmpty()) {
            dropsenderman = new ArrayList<>();
            dropsenderman.add("item:count:chance");
        }

        if (dropsskeleton == null || dropsskeleton.isEmpty()) {
            dropsskeleton = new ArrayList<>();
            dropsskeleton.add("item:count:chance");
        }

        if (dropsspider == null || dropsspider.isEmpty()) {
            dropsspider = new ArrayList<>();
            dropsspider.add("item:count:chance");
        }

        if (dropszombie == null || dropszombie.isEmpty()) {
            dropszombie = new ArrayList<>();
            dropszombie.add("item:count:chance");
        }

        if (dropsblaze == null || dropsblaze.isEmpty()) {
            dropsblaze = new ArrayList<>();
            dropsblaze.add("item:count:chance");
        }

        if (dropscavespider == null || dropscavespider.isEmpty()) {
            dropscavespider = new ArrayList<>();
            dropscavespider.add("item:count:chance");
        }

        if (dropschicken == null || dropschicken.isEmpty()) {
            dropschicken = new ArrayList<>();
            dropschicken.add("item:count:chance");
        }

        if (dropscow == null || dropscow.isEmpty()) {
            dropscow = new ArrayList<>();
            dropscow.add("item:count:chance");
        }

        if (dropsghast == null || dropsghast.isEmpty()) {
            dropsghast = new ArrayList<>();
            dropsghast.add("item:count:chance");
        }

        if (dropshorse == null || dropshorse.isEmpty()) {
            dropshorse = new ArrayList<>();
            dropshorse.add("item:count:chance");
        }

        if (dropsmagmacube == null || dropsmagmacube.isEmpty()) {
            dropsmagmacube = new ArrayList<>();
            dropsmagmacube.add("item:count:chance");
        }

        if (dropsmushroomcow == null || dropsmushroomcow.isEmpty()) {
            dropsmushroomcow = new ArrayList<>();
            dropsmushroomcow.add("item:count:chance");
        }

        if (dropspig == null || dropspig.isEmpty()) {
            dropspig = new ArrayList<>();
            dropspig.add("item:count:chance");
        }

        if (dropspigzombie == null || dropspigzombie.isEmpty()) {
            dropspigzombie = new ArrayList<>();
            dropspigzombie.add("item:count:chance");
        }

        if (dropssheep == null || dropssheep.isEmpty()) {
            dropssheep = new ArrayList<>();
            dropssheep.add("item:count:chance");
        }

        if (dropssilverfish == null || dropssilverfish.isEmpty()) {
            dropssilverfish = new ArrayList<>();
            dropssilverfish.add("item:count:chance");
        }

        if (dropsslime == null || dropsslime.isEmpty()) {
            dropsslime = new ArrayList<>();
            dropsslime.add("item:count:chance");
        }

        if (dropssquid == null || dropssquid.isEmpty()) {
            dropssquid = new ArrayList<>();
            dropssquid.add("item:count:chance");
        }

        if (dropsvillager == null || dropsvillager.isEmpty()) {
            dropsvillager = new ArrayList<>();
            dropsvillager.add("item:count:chance");
        }

        if (dropswither == null || dropswither.isEmpty()) {
            dropswither = new ArrayList<>();
            dropswither.add("item:count:chance");
            dropswither.add("item:count:chance");
        }

        if (dropswitherskeleton == null || dropswitherskeleton.isEmpty()) {
            dropswitherskeleton = new ArrayList<>();
            dropswitherskeleton.add("item:count:chance");
            dropswitherskeleton.add("item:count:chance");
        }

        if (dropswolf == null || dropswolf.isEmpty()) {
            dropswolf = new ArrayList<>();
            dropswolf.add("item:count:chance");
        }

        if (dropsgiant == null || dropsgiant.isEmpty()) {
            dropsgiant = new ArrayList<>();
            dropsgiant.add("item:count:chance");
        }

        if (dropsocelot == null || dropsocelot.isEmpty()) {
            dropsocelot = new ArrayList<>();
            dropsocelot.add("item:count:chance");
        }

        if (dropsirongolem == null || dropsirongolem.isEmpty()) {
            dropsirongolem = new ArrayList<>();
            dropsirongolem.add("item:count:chance");
        }

        if (dropsdragon == null || dropsdragon.isEmpty()) {
            dropsdragon = new ArrayList<>();
            dropsdragon.add("item:count:chance");
        }

    }

    private void setdropconfigs() {
        maconfig.set("Drops.Bat.drops", dropsbat);
        maconfig.set("Drops.Creeper.drops", dropscreeper);
        maconfig.set("Drops.Enderman.drops", dropsenderman);
        maconfig.set("Drops.Skeleton.drops", dropsskeleton);
        maconfig.set("Drops.Spider.drops", dropsspider);
        maconfig.set("Drops.Zombie.drops", dropszombie);
        maconfig.set("Drops.Blaze.drops", dropsblaze);
        maconfig.set("Drops.CaveSpider.drops", dropscavespider);
        maconfig.set("Drops.Chicken.drops", dropschicken);
        maconfig.set("Drops.Cow.drops", dropscow);
        maconfig.set("Drops.EnderDragon.drops", dropsdragon);
        maconfig.set("Drops.Ghast.drops", dropsghast);
        maconfig.set("Drops.Giant.drops", dropsgiant);
        maconfig.set("Drops.Horse.drops", dropshorse);
        maconfig.set("Drops.IronGolem.drops", dropsirongolem);
        maconfig.set("Drops.MagmaCube.drops", dropsmagmacube);
        maconfig.set("Drops.MushroomCow.drops", dropsmushroomcow);
        maconfig.set("Drops.Ocelot.drops", dropsocelot);
        maconfig.set("Drops.Pig.drops", dropspig);
        maconfig.set("Drops.PigZombie.drops", dropspigzombie);
        maconfig.set("Drops.Sheep.drops", dropssheep);
        maconfig.set("Drops.Silverfish.drops", dropssilverfish);
        maconfig.set("Drops.Slime.drops", dropsslime);
        maconfig.set("Drops.Squid.drops", dropssquid);
        maconfig.set("Drops.Villager.drops", dropsvillager);
        maconfig.set("Drops.Wither.drops", dropswither);
        maconfig.set("Drops.WitherSkeleton.drops", dropswitherskeleton);
        maconfig.set("Drops.Wolf.drops", dropswolf);


        maconfig.set("Drops.Bat.Overwrite?", dropoverbat);
        maconfig.set("Drops.Creeper.Overwrite?", dropovercreeper);
        maconfig.set("Drops.Enderman.Overwrite?", dropoverenderman);
        maconfig.set("Drops.Skeleton.Overwrite?", dropoverskeleton);
        maconfig.set("Drops.Spider.Overwrite?", dropoverspider);
        maconfig.set("Drops.Zombie.Overwrite?", dropoverzombie);
        maconfig.set("Drops.Blaze.Overwrite?", dropoverblaze);
        maconfig.set("Drops.CaveSpider.Overwrite?", dropovercavespider);
        maconfig.set("Drops.Chicken.Overwrite?", dropoverchicken);
        maconfig.set("Drops.Cow.Overwrite?", dropovercow);
        maconfig.set("Drops.EnderDragon.Overwrite?", dropoverdragon);
        maconfig.set("Drops.Ghast.Overwrite?", dropoverghast);
        maconfig.set("Drops.Giant.Overwrite?", dropovergiant);
        maconfig.set("Drops.Horse.Overwrite?", dropoverhorse);
        maconfig.set("Drops.IronGolem.Overwrite?", dropoverirongolem);
        maconfig.set("Drops.MagmaCube.Overwrite?", dropovermagmacube);
        maconfig.set("Drops.MushroomCow.Overwrite?", dropovermushroomcow);
        maconfig.set("Drops.Ocelot.Overwrite?", dropoverocelot);
        maconfig.set("Drops.Pig.Overwrite?", dropoverpig);
        maconfig.set("Drops.PigZombie.Overwrite?", dropoverpigzombie);
        maconfig.set("Drops.Sheep.Overwrite?", dropoversheep);
        maconfig.set("Drops.Silverfish.Overwrite?", dropoversilverfish);
        maconfig.set("Drops.Slime.Overwrite?", dropoverslime);
        maconfig.set("Drops.Squid.Overwrite?", dropoversquid);
        maconfig.set("Drops.Villager.Overwrite?", dropovervillager);
        maconfig.set("Drops.Wither.Overwrite?", dropoverwither);
        maconfig.set("Drops.WitherSkeleton.Overwrite?", dropoverwitherskeleton);
        maconfig.set("Drops.Wolf.Overwrite?", dropoverwolf);

    }

    /*public boolean checkplayersinbox(LivingEntity critter, int size){
        double sizet=Math.sin(45)*size;
		List<Entity> L=critter.getNearbyEntities(sizet, sizet, sizet);
		Iterator<Entity> iter=L.iterator();
		while(iter.hasNext()){
			Entity entscan=iter.next();
			if(entscan instanceof Player && ((Player) entscan).isOnline()&& !((Player) entscan).isDead()){return true;}
		}
		return false;
	}*/
    private boolean checkplayersinbox(Location cl, int size) {
        double sizet = size;
        Player p;
        List<Entity> L = new ArrayList<>();
        for (int ij = 0; ij < cl.getWorld().getPlayers().size(); ij++) {
            p = cl.getWorld().getPlayers().get(ij);
            if (dis3(cl, p.getLocation(), sizet) < sizet)
                L.add(cl.getWorld().getPlayers().get(ij));
        }
        Iterator<Entity> iter = L.iterator();
        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead()) {
                return true;
            }
        }
        return false;
    }

    private double dis3(Location loc, Location loc2, double sizet) {
        double disx = Math.abs(loc.getX() - loc2.getX());
        double disy = Math.abs(loc.getY() - loc2.getY());
        double disz = Math.abs(loc.getZ() - loc2.getZ());
        if (disx > sizet) {
            return disx;
        }
        if (disy > sizet) {
            return disy;
        }
        if (disz > sizet) {
            return disz;
        }
        return (Math.max(disx, Math.max(disy, disz)));
    }

    private void preparemoblist() {
        moblist = new ArrayList<>();
        moblist.add(new Mob(("Bat"), 6, 0, 1.0d, false, false));
        moblist.add(new Mob(("Blaze"), 20, 5, 1.0d, false, false));
        moblist.add(new Mob(("CaveSpider"), 20, 2, 1.0d, false, true));
        moblist.add(new Mob(("Chicken"), 4, 0, 1.0d, false, false));
        moblist.add(new Mob(("Cow"), 10, 0, 1.0d, false, false));
        moblist.add(new Mob(("Creeper"), 20, 0, 3.0d, false, false));
        moblist.add(new Mob(("EnderDragon"), 200, 10, 1.0d, false, false));
        moblist.add(new Mob(("Enderman"), 40, 7, 1.0d, false, true));
        moblist.add(new Mob(("Ghast"), 10, 0, 1.0d, true, false));
        moblist.add(new Mob(("Giant"), 100, 17, 1.0d, false, true));
        moblist.add(new Mob(("Horse"), 15, 0, 1.0d, false, false));
        moblist.add(new Mob(("IronGolem"), 100, 14, 1.0d, false, true));
        moblist.add(new Mob(("MushroomCow"), 10, 0, 1.0d, false, false));
        moblist.add(new Mob(("Ocelot"), 10, 0, 1.0d, false, false));
        moblist.add(new Mob(("Pig"), 10, 0, 1.0d, false, false));
        moblist.add(new Mob(("PigZombie"), 20, 5, 1.0d, false, true));
        moblist.add(new Mob(("Sheep"), 8, 0, 1.0d, false, false));
        moblist.add(new Mob(("Silverfish"), 8, 1, 1.0d, false, true));
        moblist.add(new Mob(("Skeleton"), 20, 4, 1.0d, false, true));
        moblist.add(new Mob(("Spider"), 16, 2, 1.0d, false, true));
        moblist.add(new Mob(("Squid"), 10, 0, 1.0d, false, false));
        moblist.add(new Mob(("Villager"), 20, 0, 1.0d, false, false));
        moblist.add(new Mob(("TamedWolf"), 20, 4, 1.0d, false, true));
        moblist.add(new Mob(("Wither"), 300, 5, 1.0d, false, true));
        moblist.add(new Mob(("WitherSkeleton"), 20, 9, 1.0d, false, true));
        moblist.add(new Mob(("Wolf"), 8, 2, 1.0d, false, true));
        moblist.add(new Mob(("Zombie"), 20, 4, 1.0d, false, true));
    }

    public class Mob {
        final String name;
        int damage, truedamage;
        int health;
        double standardradius, deathradius;
        final int defaultdamage;
        final int defaulthealth;
        boolean sunlightdamage, standardfire, deathexplode, deathfire;
        boolean contactimmune, drowningimmune, attackimmune, explosionimmune,
                fallimmune, fireimmune, lavaimmune, projectileimmune, suffocationimmune;

        boolean deathspawn;
        int deathcount;
        double deathchance;
        EntityType deathspawntype;
        int ymin, ymax, lightmin, lightmax;
        boolean pillar = false;
        boolean bridge = false;
        boolean megaaggro = true;
        boolean standarditems = true;
        final boolean hostilemelee;
        public List<String> potionstrings;
        public List<PotionSet> potions;

        public Mob(String Name, int Dh, int Dd, double sr, boolean sf, boolean ip) {
            name = Name;
            defaultdamage = Dd;
            defaulthealth = Dh;
            standardradius = sr;
            standardfire = sf;
            hostilemelee = ip;
        }

        public void compilePotions() {
            potions = new ArrayList<>();
            for (String potionstring : potionstrings) {
                if (
                        potionstring.startsWith("Example")
                                ||
                                potionstring.startsWith("EFFECT")
                        ) {
                    continue;
                }
                try {
                    String[] params = potionstring.split(":");
                    if (params.length > 4) {
                        log.info("Monster Apocalypse: Invalid potion setting for mob " + name + ", " + potionstring);
                        continue;
                    }
                    PotionSet newpot = new PotionSet(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]), Double.parseDouble(params[3]));
                    potions.add(newpot);
                } catch (Exception e) {
                    log.info("Monster Apocalypse: Invalid potion setting for mob " + name + ", " + potionstring);
                    continue;
                }
            }
        }

        public void attemptPotions(LivingEntity ent) {
            for (PotionSet pot : potions) {
                if (generalrand.nextDouble() * 100 <= pot.chance) {
                    ent.addPotionEffect(new PotionEffect(pot.effect, pot.duration, pot.amplifier));
                }
            }
        }

        class PotionSet {
            public final PotionEffectType effect;
            public final int duration;
            public final int amplifier;
            public final double chance;

            public PotionSet(String eff, int d, int a, double c) {

                effect = PotionEffectType.getByName(eff);
                duration = d;
                amplifier = a;
                chance = c;
            }
        }
    }

    public void applyPotionEffects(LivingEntity ent, Entity mobi) {
        if (!(mobi instanceof LivingEntity)) {
            return;
        }
        LivingEntity attacker = ((LivingEntity) (mobi));
        String Name = getMobName(attacker);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                aMoblist.attemptPotions(ent);
            }
        }
    }

    public int getMobHealth(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.health;
            }
        }
        return -1;
    }

    public boolean getMobStandardItems(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.standarditems;
            }
        }
        return true;
    }

    private boolean getMobMegaAggro(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.megaaggro;
            }
        }
        return false;
    }

    public int getMobDamage(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.damage;
            }
        }
        return 0;
    }

    public int getSkeletonDamage() {
        String Name = "Skeleton";
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.damage;
            }
        }
        return 0;
    }

    public int getSkeletonTrueDamage() {
        String Name = "Skeleton";
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.truedamage;
            }
        }
        return 0;
    }

    public int getMobTrueDamage(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.truedamage;
            }
        }
        return 0;
    }

    public double getMobRadius(String Name) {
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.standardradius;
            }
        }
        return 0.0d;
    }

    public boolean getMobFireExplosion(String Name) {
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.standardfire;
            }
        }
        return false;
    }

    public boolean getMobSunlightCombustion(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.sunlightdamage;
            }
        }
        return false;
    }

    public boolean getMobDeathExplode(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.deathexplode;
            }
        }
        return false;
    }

    public double getMobDeathRadius(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.deathradius;
            }
        }
        return 0.0;
    }

    public boolean getMobDeathFire(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.deathfire;
            }
        }
        return false;
    }

    private boolean getMobPillar(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.pillar;
            }
        }
        return false;
    }

    private boolean getMobBridge(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.bridge;
            }
        }
        return false;
    }

    public boolean getMobImmunity(Entity ent, DamageCause dc) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {

                if (dc == DamageCause.CONTACT)
                    return aMoblist.contactimmune;
                if (dc == DamageCause.DROWNING)
                    return aMoblist.drowningimmune;
                if (dc == DamageCause.ENTITY_ATTACK)
                    return aMoblist.attackimmune;
                if (dc == DamageCause.ENTITY_EXPLOSION
                        || dc == DamageCause.BLOCK_EXPLOSION)
                    return aMoblist.explosionimmune;
                if (dc == DamageCause.FALL)
                    return aMoblist.fallimmune;
                if (dc == DamageCause.FIRE)
                    return aMoblist.fireimmune;
                if (dc == DamageCause.FIRE_TICK)
                    return aMoblist.fireimmune;
                if (dc == DamageCause.LAVA)
                    return aMoblist.lavaimmune;
                if (dc == DamageCause.PROJECTILE)
                    return aMoblist.projectileimmune;
                if (dc == DamageCause.SUFFOCATION)
                    return aMoblist.suffocationimmune;


            }
        }
        return false;
    }

    public boolean getMobDeathSpawn(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.deathspawn;
            }
        }
        return false;
    }

    public EntityType getMobDeathSpawnType(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                try {
                    return aMoblist.deathspawntype;
                } catch (NullPointerException e) {
                }
            }
        }
        return null;
    }

    public int getMobDeathCount(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.deathcount;
            }
        }
        return 0;
    }

    public double getMobDeathChance(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.deathchance;
            }
        }
        return 0;
    }

    private double getMobYMin(EntityType et) {
        String Name = et.getName();
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.ymin;
            }
        }
        return 0;
    }

    private double getMobYMax(EntityType et) {
        String Name = et.getName();
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.ymax;
            }
        }
        return 0;
    }

    public double getMobLightMin(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.lightmin;
            }
        }
        return 0;
    }

    public double getMobLightMax(Entity ent) {
        String Name = getMobName(ent);
        for (Mob aMoblist : moblist) {
            if (aMoblist.name.equalsIgnoreCase(Name)) {
                return aMoblist.lightmax;
            }
        }
        return 0;
    }

    public String getMobName(Entity ent) {
        if (ent instanceof PigZombie) {
            return "PigZombie";
        }
        if (ent instanceof Zombie) {
            return "Zombie";
        }
        if (ent instanceof Creeper) {
            return "Creeper";
        }
        if (ent instanceof Enderman) {
            return "Enderman";
        }
        if (ent instanceof Skeleton) {
            if (((Skeleton) ent).getSkeletonType() == SkeletonType.WITHER) {
                return "WitherSkeleton";
            }

            return "Skeleton";
        }
        if (ent instanceof Bat) {
            return "Bat";
        }
        if (ent instanceof Wither) {
            return "Wither";
        }
        if (ent instanceof CaveSpider) {
            return "CaveSpider";
        }
        if (ent instanceof Spider) {
            return "Spider";
        }
        if (ent instanceof Blaze) {
            return "Blaze";
        }
        if (ent instanceof Chicken) {
            return "Chicken";
        }
        if (ent instanceof Cow) {
            return "Cow";
        }
        if (ent instanceof Ghast) {
            return "Ghast";
        }
        if (ent instanceof Horse) {
            return "Horse";
        }
        if (ent instanceof MushroomCow) {
            return "MushroomCow";
        }
        if (ent instanceof Pig) {
            return "Pig";
        }
        if (ent instanceof Sheep) {
            return "Sheep";
        }
        if (ent instanceof Silverfish) {
            return "Silverfish";
        }
        if (ent instanceof MagmaCube) {
            return "MagmaCube";
        }
        if (ent instanceof Slime) {
            return "Slime";
        }
        if (ent instanceof Squid) {
            return "Squid";
        }
        if (ent instanceof Villager) {
            return "Villager";
        }
        if (ent instanceof Wolf) {
            if (((Wolf) ent).isTamed()) {
                return "TamedWolf";
            }
            return "Wolf";
        }
        if (ent instanceof EnderDragon) {
            return "EnderDragon";
        }
        if (ent instanceof Ocelot) {
            return "Ocelot";
        }
        if (ent instanceof Giant) {
            return "Giant";
        }
        if (ent instanceof IronGolem) {
            return "IronGolem";
        }

        return "";
    }

    public List<String> getMobDrops(Entity ent) {
        if (ent instanceof PigZombie) {
            return dropspigzombie;
        }
        if (ent instanceof Zombie) {
            return dropszombie;
        }
        if (ent instanceof Creeper) {
            return dropscreeper;
        }
        if (ent instanceof Enderman) {
            return dropsenderman;
        }
        if (ent instanceof Wither) {
            return dropswither;
        }
        if (ent instanceof Skeleton) {
            if (((Skeleton) ent).getSkeletonType() == SkeletonType.WITHER) {
                return dropswitherskeleton;
            }

            return dropsskeleton;
        }
        if (ent instanceof CaveSpider) {
            return dropscavespider;
        }
        if (ent instanceof Spider) {
            return dropsspider;
        }
        if (ent instanceof Bat) {
            return dropsbat;
        }
        if (ent instanceof Blaze) {
            return dropsblaze;
        }
        if (ent instanceof Chicken) {
            return dropschicken;
        }
        if (ent instanceof Cow) {
            return dropscow;
        }
        if (ent instanceof Ghast) {
            return dropsghast;
        }
        if (ent instanceof Horse) {
            return dropshorse;
        }
        if (ent instanceof MushroomCow) {
            return dropsmushroomcow;
        }
        if (ent instanceof Pig) {
            return dropspig;
        }
        if (ent instanceof Sheep) {
            return dropssheep;
        }
        if (ent instanceof Silverfish) {
            return dropssilverfish;
        }
        if (ent instanceof MagmaCube) {
            return dropsmagmacube;
        }
        if (ent instanceof Slime) {
            return dropsslime;
        }
        if (ent instanceof Squid) {
            return dropssquid;
        }
        if (ent instanceof Villager) {
            return dropsvillager;
        }
        if (ent instanceof Wolf) {
            return dropswolf;
        }
        if (ent instanceof Giant) {
            return dropsgiant;
        }
        if (ent instanceof IronGolem) {
            return dropsirongolem;
        }
        if (ent instanceof EnderDragon) {
            return dropsdragon;
        }
        if (ent instanceof Ocelot) {
            return dropsocelot;
        }

        return null;
    }

    public List<String> getMobDrops(String name) {
        if (name == "PigZombie") {
            return dropspigzombie;
        }
        if (name == "Zombie") {
            return dropszombie;
        }
        if (name == "Creeper") {
            return dropscreeper;
        }
        if (name == "Enderman") {
            return dropsenderman;
        }
        if (name == "Skeleton") {
            return dropsskeleton;
        }
        if (name == "WitherSkeleton") {
            return dropswitherskeleton;
        }
        if (name == "Wither") {
            return dropswither;
        }
        if (name == "Spider") {
            return dropsspider;
        }
        if (name == "Bat") {
            return dropsbat;
        }
        if (name == "Blaze") {
            return dropsblaze;
        }
        if (name == "CaveSpider") {
            return dropscavespider;
        }
        if (name == "Chicken") {
            return dropschicken;
        }
        if (name == "Cow") {
            return dropscow;
        }
        if (name == "Ghast") {
            return dropsghast;
        }
        if (name == "Horse") {
            return dropshorse;
        }
        if (name == "MagmaCube") {
            return dropsmagmacube;
        }
        if (name == "MushroomCow") {
            return dropsmushroomcow;
        }
        if (name == "Pig") {
            return dropspig;
        }
        if (name == "Sheep") {
            return dropssheep;
        }
        if (name == "Silverfish") {
            return dropssilverfish;
        }
        if (name == "Slime") {
            return dropsslime;
        }
        if (name == "Squid") {
            return dropssquid;
        }
        if (name == "Villager") {
            return dropsvillager;
        }
        if (name == "Wolf") {
            return dropswolf;
        }
        if (name == "Giant") {
            return dropsgiant;
        }
        if (name == "EnderDragon") {
            return dropsdragon;
        }
        if (name == "IronGolem") {
            return dropsirongolem;
        }
        if (name == "Ocelot") {
            return dropsocelot;
        }

        return null;
    }

    public boolean getMobDropOverwrite(Entity ent) {
        if (ent instanceof Zombie) {
            return dropoverzombie;
        }
        if (ent instanceof Creeper) {
            return dropovercreeper;
        }
        if (ent instanceof Enderman) {
            return dropoverenderman;
        }
        if (ent instanceof Skeleton) {
            if (((Skeleton) ent).getSkeletonType() == SkeletonType.WITHER) {
                return dropoverwitherskeleton;
            }

            return dropoverskeleton;
        }
        if (ent instanceof CaveSpider) {
            return dropovercavespider;
        }
        if (ent instanceof Spider) {
            return dropoverspider;
        }
        if (ent instanceof Bat) {
            return dropoverbat;
        }
        if (ent instanceof Blaze) {
            return dropoverblaze;
        }
        if (ent instanceof Chicken) {
            return dropoverchicken;
        }
        if (ent instanceof Cow) {
            return dropovercow;
        }
        if (ent instanceof Ghast) {
            return dropoverghast;
        }
        if (ent instanceof Horse) {
            return dropoverhorse;
        }
        if (ent instanceof MushroomCow) {
            return dropovermushroomcow;
        }
        if (ent instanceof Pig) {
            return dropoverpig;
        }
        if (ent instanceof PigZombie) {
            return dropoverpigzombie;
        }
        if (ent instanceof Sheep) {
            return dropoversheep;
        }
        if (ent instanceof Silverfish) {
            return dropoversilverfish;
        }
        if (ent instanceof Slime) {
            return dropoverslime;
        }
        if (ent instanceof Squid) {
            return dropoversquid;
        }
        if (ent instanceof Villager) {
            return dropovervillager;
        }
        if (ent instanceof Wither) {
            return dropoverwither;
        }
        if (ent instanceof Wolf) {
            return dropoverwolf;
        }
        if (ent instanceof EnderDragon) {
            return dropoverdragon;
        }
        if (ent instanceof Giant) {
            return dropovergiant;
        }
        if (ent instanceof IronGolem) {
            return dropoverirongolem;
        }
        if (ent instanceof Ocelot) {
            return dropoverocelot;
        }

        return false;
    }

    public class nightRun implements Runnable {
        final MonsterApocalypse plugin;

        public nightRun(MonsterApocalypse instance) {
            plugin = instance;
        }

        public void run() {
            for (String worldname : plugin.worldnames) {
                plugin.getServer().getWorld(worldname).setTime(15600);
            }
        }
    }

    public class torchdestroyer implements Runnable {
        final MonsterApocalypse plugin;
        final List<List<LivingEntity>> allentitylists = new ArrayList<>();
        int ticks = 0, n = 0;

        public torchdestroyer(MonsterApocalypse instance) {
            plugin = instance;
        }

        public void run() {
            if (ticks >= 20) {
                allentitylists.clear();
                ticks = 0;
                n = 0;
            }
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (allentitylists.size() <= i) {
                    allentitylists.add(plugin.getServer().getWorld(worldname).getLivingEntities());
                }
                List<LivingEntity> entitylist = allentitylists.get(i);
                if (allentitylists.size() < i || entitylist == null) {
                    allentitylists.add(plugin.getServer().getWorld(worldname).getLivingEntities());
                }
                entitylist = allentitylists.get(i);
                if (ticks < 19) {
                    for (; n < (int) Math.floor(entitylist.size() * ((ticks + 1) / 20d)); n++) {
                        Entity thismob = entitylist.get(n);
                        if (thismob.isDead()) continue;
                        if (thismob instanceof PigZombie && !megapig) continue;
                        if (!(thismob instanceof Spider || thismob instanceof Zombie || thismob instanceof CaveSpider || thismob instanceof Enderman ||
                                thismob instanceof Skeleton ||
                                thismob instanceof Creeper)) continue;
                        Location loc = thismob.getLocation();
//				aggrotorchesinradius(entitylist.get(n), torchradius);
                        waller.wallmanager.increasestilltime((LivingEntity) thismob);

                        boolean aggro = true;
                        try {
                            aggro = !tryairbridge(thismob, trypillar(thismob));
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        if (aggro) {
                            if (togglemegaaggro) {
                                megaaggro((Monster) thismob);
                            }
                        }
                        if (toggletorch) {
                            if (loc.getBlock().getLightLevel() < 11) continue;
                            destroytorchesnearby(loc);
                        }

                    }
                } else {
                    for (; n < entitylist.size(); n++) {
                        Entity thismob = entitylist.get(n);
                        if (thismob.isDead()) continue;
                        if (thismob instanceof PigZombie && !megapig) continue;
                        if (!(thismob instanceof Spider || thismob instanceof Zombie || thismob instanceof CaveSpider || thismob instanceof Enderman ||
                                thismob instanceof Skeleton ||
                                thismob instanceof Creeper)) continue;
                        Location loc = thismob.getLocation();
//					aggrotorchesinradius(entitylist.get(n), torchradius);
                        waller.wallmanager.increasestilltime((LivingEntity) thismob);

                        boolean aggro = true;
                        try {
                            aggro = !tryairbridge(thismob, trypillar(thismob));
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        if (aggro) {
                            if (togglemegaaggro) {
                                megaaggro((Monster) thismob);
                            }
                        }
                        if (toggletorch) {
                            if (loc.getBlock().getLightLevel() < 11) continue;
                            destroytorchesnearby(loc);
                        }
                    }
                }
            }
            ticks++;
        }

        public void destroytorchesnearby(Location loc) {
            for (int xs = -1; xs <= 1; xs++) {
                for (int ys = 0; ys < 2; ys++) {
                    for (int zs = -1; zs <= 1; zs++) {
                        Block block = loc.getBlock().getRelative(xs, ys, zs);
                        if (block.getType() == Material.TORCH) {
                            popblockflipper(block);
                        }
                    }
                }
            }
        }

        private boolean trypillar(Entity thismob) {
            boolean flag = false;
            boolean pillarz = pillars && getMobPillar(thismob);
            LivingEntity targetz = null;
            Location loc = thismob.getLocation();
            if (pillarz) {
                if (thismob instanceof Monster) {
                    if (thismob.isDead()) return flag;
                    //if((thismob instanceof Zombie)||(thismob instanceof PigZombie)){return;}
                    targetz = getnearestplayerLEy(thismob, splitaggro);
                    try {
                        plugin.waller.wallmanager.getmobloc((LivingEntity) thismob);
                    } catch (NullPointerException e) {
                        plugin.waller.wallmanager.addmob((LivingEntity) thismob);
                        return flag;
                    }
                    if (targetz == null) {
                        return flag;
                    }
                    if (targetz.getLocation().getBlockY() >= loc.getBlockY()) {
                        try {
                            flag = waller.attemptcorpsepileup(thismob, waller.wallmanager.getmobbelow((LivingEntity) thismob));


                            if (flag) {

                                //waller.wallmanager.resetmobstill((LivingEntity) thismob);
                            }
                            try {
                                Location loc2 = plugin.waller.wallmanager.getmobloc((LivingEntity) thismob);
                                if (loc2.getBlockX() != loc.getBlockX()
                                        ) {
                                    plugin.waller.wallmanager.resetmobstill((LivingEntity) thismob);
                                    plugin.waller.wallmanager.resetmobbelow((LivingEntity) thismob);
                                    return flag;
                                }
                                if (loc2.getBlockY() > loc.getBlockY() + 2
                                        ) {
                                    plugin.waller.wallmanager.resetmobstill((LivingEntity) thismob);
                                    plugin.waller.wallmanager.resetmobbelow((LivingEntity) thismob);
                                    return flag;
                                }
                                if (loc2.getBlockZ() != loc.getBlockZ()
                                        ) {
                                    plugin.waller.wallmanager.resetmobstill((LivingEntity) thismob);
                                    plugin.waller.wallmanager.resetmobbelow((LivingEntity) thismob);
                                    return flag;
                                }
                            } catch (NullPointerException e) {
                                plugin.waller.wallmanager.addmob((LivingEntity) thismob);
                                return flag;
                            }
                            return flag;
                        } catch (NullPointerException e2) {

                        }
                    }
                /*Location loc2=plugin.waller.wallmanager.getmobloc((LivingEntity) thismob);
                if(loc2.getBlockZ()!=loc.getBlockZ()
				)
				{plugin.waller.wallmanager.resetmobstill((LivingEntity) thismob);
				plugin.waller.wallmanager.resetmobbelow((LivingEntity) thismob);
				return;}
				if(loc2.getBlockX()!=loc.getBlockX()
				)
				{plugin.waller.wallmanager.resetmobstill((LivingEntity) thismob);
				plugin.waller.wallmanager.resetmobbelow((LivingEntity) thismob);
				return;}*/
                }

            }
            return flag;
        }

        private boolean tryairbridge(Entity thismob, boolean flag2) {
            boolean bridgez = bridges && getMobBridge(thismob);
            Location loc = thismob.getLocation();
            LivingEntity targetz = getnearestplayerLEy(thismob, splitaggro);
            boolean flag;
            if (targetz == null) {
                return flag2;
            }
            if (bridgez && !flag2) {
                if ((targetz.getLocation().getBlockY() > loc.getBlockY() - 1
                        ||
                        targetz.getLocation().getBlockY() < loc.getBlockY() + 1)
                        && ((
                        Math.abs(targetz.getLocation().getBlockY() - loc.getBlockY()) > 10
                ) || (
                        Math.abs(targetz.getLocation().getBlockX() - loc.getBlockX()) >= 2
                                &&
                                Math.abs(targetz.getLocation().getBlockZ() - loc.getBlockZ()) >= 2
                ))
                        ) {
                    if (!blockbelowair(loc.getBlock())) {
                        try {
                            flag = attemptairbridge(thismob, targetz, waller.wallmanager.getmobbelow((LivingEntity) thismob));


                            return flag;
                        } catch (NullPointerException e2) {

                        }
                    }
                }

            } else {
                return flag2;
            }
            return flag2;
        }

        private boolean attemptairbridge(Entity ent, LivingEntity targetp, int timez) {
            if (targetp == null) {
                return false;
            }
            boolean xside, zside;
            int horizflag, vertflag;
            horizflag = 0;
            vertflag = 0;
            xside = false;
            zside = false;
            int ax, ay, az, tx, ty, tz, bx, by, bz;
            bx = 0;
            by = 0;
            bz = 0;
            ax = ent.getLocation().getBlockX();
            ay = ent.getLocation().getBlockY();
            az = ent.getLocation().getBlockZ();
            tx = targetp.getLocation().getBlockX();
            ty = targetp.getLocation().getBlockY();
            tz = targetp.getLocation().getBlockZ();
            if (ax < tx) xside = true;
            if (az < tz) zside = true;
            if (Math.abs(ax - tx) < Math.abs(az - tz)) {
                horizflag = 0;
            }
            if (Math.abs(ax - tx) > Math.abs(az - tz)) {
                horizflag = 1;
            }
            if (Math.abs(ax - tx) == Math.abs(az - tz)) {
                horizflag = 2;
            }
            if (ay < ty - 2) {
                vertflag = 0;
            } else if (ay > ty) {
                vertflag = 1;
            } else {
                vertflag = 2;
            }
            if (horizflag == 1 || horizflag == 2) {
                bz = az;
                if (xside) {
                    bx = ax + 1;
                } else {
                    bx = ax - 1;
                }
            }
            if (horizflag == 0) {
                bx = ax;
                if (zside) {
                    bz = az + 1;
                } else {
                    bz = az - 1;
                }
            }
            if (vertflag == 0) {
                by = ay = 0;
            }
            if (vertflag == 1) {
                by = ay - 2;
            }
            if (vertflag == 2) {
                by = ay - 1;
            }
            //Entity target=getnearestplayerLE(ent,12);

            Location eloc = ent.getLocation();

            Location L = new Location(eloc.getWorld(), bx, by, bz);
            boolean flagreturn = false;
            Block bla = ent.getWorld().getBlockAt(L);
            if ((vertflag != 1) || blockaboveisair(bla)) {
                flagreturn = placeblockflipper(bla);
            }
            if (flagreturn) {
                if (togglecorpsedie) {
                    ent.remove();
                } else {
                    try {
                        if ((ent instanceof Zombie) && !(ent instanceof PigZombie)) {

                            livingEntityMoveTo((LivingEntity) ent, L.getX() + 0.5f, L.getY(), L.getZ() + 0.5f, 0.3f * 5);
                        } else if ((ent instanceof Creeper)) {

                            livingEntityMoveTo((LivingEntity) ent, L.getX() + 0.5f, L.getY(), L.getZ() + 0.5f, 0.3f * 5);
                        } else if ((ent instanceof Skeleton)) {

                            livingEntityMoveTo((LivingEntity) ent, L.getX() + 0.5f, L.getY(), L.getZ() + 0.5f, 0.3f * 5);
                        }

                    } catch (NullPointerException e2) {
                        return flagreturn;
                    }
                }
            }
            return flagreturn;


            //return false;
        }
    }

    public class skeletons implements Runnable {
        final MonsterApocalypse plugin;

        public skeletons(MonsterApocalypse instance) {
            plugin = instance;
        }

        public void run() {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                List<LivingEntity> entitylist = (plugin.getServer().getWorld(worldname).getLivingEntities());

                for (LivingEntity anEntitylist : entitylist) {
                    Entity thismob = anEntitylist;
                    if (thismob.isDead()) continue;
//				aggrotorchesinradius(entitylist.get(n), torchradius);
                    if (thismob instanceof Skeleton
                            && !((((Skeleton) thismob).getSkeletonType().getId() == 1)
                            || witherenable)
                            ) {
                        try {
                            shootplayers((Skeleton) thismob);
                        } catch (NullPointerException e) {
                        }
                    }
                }
            }
        }

        private void shootplayers(Skeleton ent) {
            shootplayer(ent, (getnearestplayerLEy(ent, plugin.skeletonrange, true, plugin.skeletony)));
        }

        @SuppressWarnings("deprecation")
        private void shootplayer(Skeleton sk, LivingEntity player) {
            if (spawnrand.nextDouble() * 100d > skeletonchance) {
                return;
            }
            Location sklocation = sk.getLocation().add(0, 0, 0);
            Location targetlocation = player.getLocation().add(0.0, 1.0, 0.0);
            Location arrowlocation = sk.getLocation().add(0.0, 0.0, 0.0);
//			for(int mo=(targetlocation.getBlockY()-arrowlocation.getBlockY()); mo>10; mo-=10){
//				targetlocation=targetlocation.add(0.0, 1.0, 0.0);
//			}
            double a = 0;
            double disx = Math.abs(targetlocation.getX() - arrowlocation.getX());
            double disz = Math.abs(targetlocation.getZ() - arrowlocation.getZ());
            double lengthxz = disz / Math.sin(Math.tan(disz / disx));
            double xzd = lengthxz / 20d;
//			if(lengthxz>10){
//				targetlocation=targetlocation.add(0.0, Math.pow(1.8, lengthxz/5.0d), 0.0);
//			}
            int yd = (int) Math.round(targetlocation.getY() - arrowlocation.getY());
            double ydmd = (yd / 20d) * 0.5d;
            if (yd > 30 || lengthxz > 20) targetlocation.setY(targetlocation.getY() + 1.0d);
//			targetlocation=targetlocation.add(0.0, Math.pow(1.18, yd), 0.0);
            Vector vector = targetlocation.subtract(arrowlocation).toVector().normalize().multiply(1.5d + ydmd + xzd);
            a = -atan(vector.getY() / length(vector.getX(), vector.getZ()));
//			double travelled = -Math.pow((0.0157091*(a)),2)+(1.26788*(a))+10.7178;
            double dx = vector.getX();
            double dz = vector.getZ();
            float yaw = 0;
            // Set yaw
            if (dx != 0) {
                // Set yaw start value based on dx
                if (dx < 0) {
                    yaw = 270;
                } else {
                    yaw = 90;
                }
                yaw -= atan(dz / dx);
            } else if (dz < 0) {
                yaw = 180;
            }

            arrowlocation.setYaw(-yaw);
//			int breaker=0;
//			while(travelled<lengthxz+1.0){
//				double y=targetlocation.getY()+0.3;
//				targetlocation.setY(y);
//				vector=targetlocation.subtract(arrowlocation).toVector();
//				a=(float) atan(vector.getY() / length(vector.getX(), vector.getZ()));
            //			travelled = -Math.pow((0.0157091*(a)),2)+(1.26788*(a))+10.7178;
            //			breaker++;
//				if(breaker>1000)break;
//			}
            arrowlocation.setPitch((float) a);


            sk.teleport(arrowlocation);
            // sk.launchProjectile(arg0);
            Arrow arrowzor = sk.launchProjectile(Arrow.class);
            arrowzor.setVelocity(vector);
            arrowzor.setShooter(sk);
            sk.teleport(sklocation);
//			sk.getWorld().spawnArrow(arrowlocation, vector, 0.6f, 12).setShooter(sk);
//			arrow.teleport(arrowlocation);
//			arrow.setVelocity(velocity);
        }

        public double lengthSquared(double... values) {
            double rval = 0;
            for (double value : values) {
                rval += value * value;
            }
            return rval;
        }

        public double length(double... values) {
            return Math.sqrt(lengthSquared(values));
        }

        public float atan(double value) {
            return RADTODEG * (float) Math.atan(value);
        }

//		public void aggrotorchesinradius(Entity ent, int radius){
//			for(int xs=0; xs<radius*2; xs++){
//				for(int ys=0; ys<3; ys++){
//					for(int zs=0; zs<radius*2; zs++){
//						Block block=ent.getLocation().getBlock().getRelative(-(radius)+xs, -(radius)+ys, -(radius)+zs);
//						if(block.getType()==Material.TORCH){
//							//creatureMoveTo(((Creature)ent), block.getX(), block.getY(), block.getZ());
//							torchfinder(ent, block.getX(), block.getY(), block.getZ());
//							return;
//						}
//					}}}
//		}
//	    private void torchfinder(Entity entity, int x, int y, int z){
//	    	Location targetlocation=new Location(plugin.getServer().getWorld(worldname), x, y, z);
////	    	entity.setVelocity(entity.getLocation().subtract(new Location(plugin.getServer().getWorld(worldname), x, y+0.75, z)).toVector().multiply(15.0));
//	    	entity.teleport(entity.getLocation().add(trigdis(20, entity.getLocation(), targetlocation)));
//
//	    }
    }

    public LivingEntity getnearestplayerLE(Entity ent, double size) {
        boolean canreturn = false;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();
        Location entloc = ent.getLocation();
        Location tarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        @SuppressWarnings("unused")
        Location closesttarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        LivingEntity closesttar = null;

        Location temploc;
        double locproduct, closestlocproduct;
        closestlocproduct = 100000d;

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead() && ((Player) entscan).getGameMode() == GameMode.SURVIVAL) {
                tarloc = entscan.getLocation();
                temploc = tarloc.subtract(entloc);
                locproduct = (Math.abs(temploc.getX()) + Math.abs(temploc.getZ()));
                if (locproduct < closestlocproduct) {
                    closesttar = (LivingEntity) entscan;
                    closesttarloc = tarloc;
                    closestlocproduct = locproduct;
                    canreturn = true;
                }
            }
        }
        if (canreturn) {
            if (Math.abs(closesttar.getLocation().getBlockX() - entloc.getBlockX()) <= size
                    && Math.abs(closesttar.getLocation().getBlockZ() - entloc.getBlockZ()) <= size
                    )
                return closesttar;
        }
        return null;
    }

    private int getnearestplayerLEcount(Entity ent, double size) {
        int returncount = 0;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead()) {
                double distancex = Math.abs(entscan.getLocation().getX() - ent.getLocation().getX());
                double distancez = Math.abs(entscan.getLocation().getZ() - ent.getLocation().getZ());
                if (distancex < size && distancez < size) {
                    returncount++;
                }
            }
        }
        return returncount;
    }

    public int getnearestplayerLEycount(Entity ent, double size) {
        int returncount = 0;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead()) {
                double distancex = Math.abs(entscan.getLocation().getX() - ent.getLocation().getX());
                double distancey = Math.abs(entscan.getLocation().getY() - ent.getLocation().getY());
                double distancez = Math.abs(entscan.getLocation().getZ() - ent.getLocation().getZ());
                if (distancex < size && distancez < size && distancey < size) {
                    returncount++;
                }
            }
        }
        return returncount;
    }

    private LivingEntity getnearestplayerLEy(Entity ent, boolean daycheck, double size) {
        int ylimit;
        if (daycheck) {
            if (timeisday(ent)) {
                ylimit = aggrodayup;
            } else {
                ylimit = aggroy;
            }
        } else {
            ylimit = aggroy;
        }
        boolean canreturn = false;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();
        Location entloc = ent.getLocation();
        Location tarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        @SuppressWarnings("unused")
        Location closesttarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        LivingEntity closesttar = null;

        Location temploc;
        double locproduct, closestlocproduct;
        closestlocproduct = 100000d;

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead() && ((Player) entscan).getGameMode() == GameMode.SURVIVAL
                    && (!checkaggro || !entscan.hasPermission("Monster Apocalypse.deaggro"))
                    ) {
                tarloc = entscan.getLocation();
                temploc = tarloc.subtract(entloc);
                locproduct = (Math.abs(temploc.getX()) + Math.abs(temploc.getZ()) + Math.abs(temploc.getY()));
                if (locproduct < closestlocproduct) {
                    closesttar = (LivingEntity) entscan;
                    closesttarloc = tarloc;
                    closestlocproduct = locproduct;
                    canreturn = true;
                }
            }
        }
        if (canreturn) {
            if (Math.abs(closesttar.getLocation().getBlockX() - entloc.getBlockX()) <= size
                    && Math.abs(closesttar.getLocation().getBlockZ() - entloc.getBlockZ()) <= size
                    && Math.abs(closesttar.getLocation().getBlockY() - entloc.getBlockY()) <= ylimit
                    )
                return closesttar;
        }
        return null;
    }

    private LivingEntity getnearestplayerLEy(Entity ent, boolean daycheck) {
        int ylimit;
        double size = 0;
        if (daycheck) {
            if (timeisday(ent)) {
                ylimit = aggrodayup;
                size = aggroday;
            } else {
                ylimit = aggroy;
                size = superradius;
            }
        } else {
            ylimit = aggroy;
            size = superradius;
        }
        boolean canreturn = false;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();
        Location entloc = ent.getLocation();
        Location tarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        @SuppressWarnings("unused")
        Location closesttarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        LivingEntity closesttar = null;

        Location temploc;
        double locproduct, closestlocproduct;
        closestlocproduct = 100000d;

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead() && ((Player) entscan).getGameMode() == GameMode.SURVIVAL
                    && (!checkaggro || !entscan.hasPermission("Monster Apocalypse.deaggro"))
                    ) {
                tarloc = entscan.getLocation();
                temploc = tarloc.subtract(entloc);
                locproduct = (Math.abs(temploc.getX()) + Math.abs(temploc.getZ()) + Math.abs(temploc.getY()));
                if (locproduct < closestlocproduct) {
                    closesttar = (LivingEntity) entscan;
                    closesttarloc = tarloc;
                    closestlocproduct = locproduct;
                    canreturn = true;
                }
            }
        }
        if (canreturn) {
            if (Math.abs(closesttar.getLocation().getBlockX() - entloc.getBlockX()) <= size
                    && Math.abs(closesttar.getLocation().getBlockZ() - entloc.getBlockZ()) <= size
                    && Math.abs(closesttar.getLocation().getBlockY() - entloc.getBlockY()) <= ylimit
                    )
                return closesttar;
        }
        return null;
    }

    private LivingEntity getnearestplayerLEy(Entity ent, double size, boolean daycheck, int ylimit) {
        boolean canreturn = false;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();
        Location entloc = ent.getLocation();
        Location tarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        @SuppressWarnings("unused")
        Location closesttarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        LivingEntity closesttar = null;

        Location temploc;
        double locproduct, closestlocproduct;
        closestlocproduct = 100000d;

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead() && ((Player) entscan).getGameMode() == GameMode.SURVIVAL
                    && (!checkaggro || !entscan.hasPermission("Monster Apocalypse.deaggro"))
                    ) {
                tarloc = entscan.getLocation();
                temploc = tarloc.subtract(entloc);
                locproduct = (Math.abs(temploc.getX()) + Math.abs(temploc.getZ()) + Math.abs(temploc.getY()));
                if (locproduct < closestlocproduct) {
                    closesttar = (LivingEntity) entscan;
                    closesttarloc = tarloc;
                    closestlocproduct = locproduct;
                    canreturn = true;
                }
            }
        }
        if (canreturn) {
            if (Math.abs(closesttar.getLocation().getBlockX() - entloc.getBlockX()) <= size
                    && Math.abs(closesttar.getLocation().getBlockZ() - entloc.getBlockZ()) <= size
                    && Math.abs(closesttar.getLocation().getBlockY() - entloc.getBlockY()) <= ylimit
                    )
                return closesttar;
        }
        return null;
    }

    private LivingEntity getnearestplayerLEy2(Entity ent, boolean daycheck) {
        double size;
        int ylimit = 300;
        if (daycheck) {
            if (timeisday(ent)) {
                size = aggroday;
            } else {
                size = superradius;
            }
        } else {
            size = superradius;
        }
        boolean canreturn = false;
        List<Player> L = ent.getWorld().getPlayers();
        Iterator<Player> iter = L.iterator();
        Location entloc = ent.getLocation();
        Location tarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        @SuppressWarnings("unused")
        Location closesttarloc = new Location(ent.getWorld(), 0d, 0d, 0d);
        LivingEntity closesttar = null;

        Location temploc;
        double locproduct, closestlocproduct;
        closestlocproduct = 100000d;

        while (iter.hasNext()) {
            Entity entscan = iter.next();
            if (entscan instanceof Player && ((Player) entscan).isOnline() && !entscan.isDead() && ((Player) entscan).getGameMode() == GameMode.SURVIVAL
                    && (!checkaggro || !entscan.hasPermission("Monster Apocalypse.deaggro"))
                    ) {
                tarloc = entscan.getLocation();
                temploc = tarloc.subtract(entloc);
                locproduct = (Math.abs(temploc.getX()) + Math.abs(temploc.getZ()) + Math.abs(temploc.getY()));
                if (locproduct < closestlocproduct) {
                    closesttar = (LivingEntity) entscan;
                    closesttarloc = tarloc;
                    closestlocproduct = locproduct;
                    canreturn = true;
                }
            }
        }
        if (canreturn) {
            if (Math.abs(closesttar.getLocation().getBlockX() - entloc.getBlockX()) <= size
                    && Math.abs(closesttar.getLocation().getBlockZ() - entloc.getBlockZ()) <= size
                    && Math.abs(closesttar.getLocation().getBlockY() - entloc.getBlockY()) <= ylimit
                    )
                return closesttar;
        }
        return null;
    }

    private void megaaggro(Monster thismob) {
        if (thismob instanceof Enderman) return;
        if (thismob instanceof Ghast) {
            LivingEntity targetplayer = null;
            try {
                targetplayer = getnearestplayerLEy(thismob, splitaggro);
                (thismob).setTarget(targetplayer);
            } catch (NullPointerException e) {
            }
            return;
        }
        if (!getMobMegaAggro(thismob)) {
            return;
        }
        LivingEntity targetplayer = null, tar = null;
        //	try{
        //if((thismob instanceof Slime)||(thismob instanceof Blaze)||(thismob instanceof Spider)||(thismob instanceof PigZombie))
        //targetplayer=getnearestplayerLEy(thismob, splitaggro, sprintdistance);
        //(thismob).setTarget(targetplayer);
        //}
        //catch(NullPointerException e){
        try {
            targetplayer = getnearestplayerLEy(thismob, splitaggro);
            if ((thismob instanceof Zombie) && !(thismob instanceof PigZombie)) {
                Location L = get9point(thismob, targetplayer, (float) zombiespeed);
                livingEntityMoveTo(thismob, L.getX(), L.getY(), L.getZ(), 1.2f * 0.25f * 5 * (float) zombiespeed);
            } else if ((thismob instanceof Creeper)) {
                Location L = get9point(thismob, targetplayer, (float) creeperspeed);
                livingEntityMoveTo(thismob, L.getX(), L.getY(), L.getZ(), 1.2f * 0.20f * 5 * (float) creeperspeed);
            } else if ((thismob instanceof Skeleton)) {
                Location L = get9point(thismob, targetplayer, (float) skeletonspeed);
                livingEntityMoveTo(thismob, L.getX(), L.getY(), L.getZ(), 1.2f * 0.20f * 5 * (float) skeletonspeed);
            } else {
                if (Math.abs(targetplayer.getLocation().getX() - thismob.getLocation().getX()) > 10
                        || Math.abs(targetplayer.getLocation().getZ() - thismob.getLocation().getZ()) > 10
                        ) {
                    tar = (LivingEntity) thismob.getWorld().spawnEntity(
                            get9point(thismob, targetplayer, 1), EntityType.SILVERFISH);
                    (thismob).setTarget(tar);
                    tar.remove();
                } else {
                    targetplayer = getnearestplayerLEy(thismob, splitaggro, 10);
                    (thismob).setTarget(targetplayer);
                }
            }

        } catch (NullPointerException e2) {

        }
        //}

    }

    /* private Location get9point(Monster mob, LivingEntity tar, double speed){
        Location L=tar.getLocation();
    	Location ML=mob.getLocation();
    	double disx=Math.abs(L.getX()-ML.getX());
    	double disz=Math.abs(L.getZ()-ML.getZ());
    	double disy=Math.abs(L.getY()-ML.getY());
    	if(disx<=15
    			&&disz<=15
    	){
    	if(disy<=15)
    	{mob.setTarget(tar);}

    	return trigdis(distance2d(disx, disz)-2, mob.getLocation(), L);
    	}
    	return trigdis(distance2d(disx, disz)-2, mob.getLocation(), L);
    }*/
    private Location get9point(Monster mob, LivingEntity tar, double speed) {
        Location L = tar.getLocation();
        Location ML = mob.getLocation();
        double disx = Math.abs(L.getX() - ML.getX());
        double disz = Math.abs(L.getZ() - ML.getZ());
        double disy = Math.abs(L.getY() - ML.getY());
        if (disx <= 10
                && disz <= 10
                ) {
            if (disy <= 10) {
                mob.setTarget(tar);
            }

            return trigdis(distance2d(disx, disz) - 2, mob.getLocation(), L);
        }
        return trigdis(9, mob.getLocation(), L);
    }

    private double distance2d(double p1, double p2) {
        return Math.hypot(p1, p2);
    }

    private Location trigdis(double distance2, Location o, Location t) {
        double xa = Math.atan(Math.abs(o.getX() - t.getX()) / Math.abs(o.getZ() - t.getZ()));
        double xdis = Math.sin(xa) * distance2;
        double zdis = Math.cos(xa) * distance2;
        if (t.getX() < o.getX()) xdis *= -1;
        if (t.getZ() < o.getZ()) zdis *= -1;


        //if(Math.abs(o.getX()-t.getX())<distance)xdis=-(o.getX()-t.getX());
        //if(Math.abs(o.getZ()-t.getZ())<distance)zdis=-(o.getZ()-t.getZ());


//    	double angle=Math.atan(-(o.getZ()-t.getZ()))/(-(o.getX()-t.getX()));
//    	xdis=Math.cos(angle)*distance;
        //   	zdis=Math.sin(angle)*distance;
        return (new Location(o.getWorld(), o.getX() + xdis, o.getY() + 1, o.getZ() + zdis));
    }

    private double getspawndis(Location o) {
        double xdis, zdis;
        xdis = 0;
        zdis = 0;
        xdis = Math.abs(o.getX() - mainspawnx);
        zdis = Math.abs(o.getZ() - mainspawnz);
//    	double angle=Math.atan(-(o.getZ()-t.getZ()))/(-(o.getX()-t.getX()));
//    	xdis=Math.cos(angle)*distance;
        //   	zdis=Math.sin(angle)*distance;
        return (Math.hypot(xdis, zdis));
    }

    private boolean livingEntityMoveTo(LivingEntity livingEntity, double x, double y, double z, float speed) {
        if (livingEntity instanceof Zombie) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftZombie) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof Skeleton) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftSkeleton) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof Creeper) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftCreeper) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof Spider) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftSpider) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof CaveSpider) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftCaveSpider) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof Silverfish) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftSilverfish) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof Enderman) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftEnderman) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof PigZombie) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftPigZombie) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        } else if (livingEntity instanceof Giant) {
            return ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftGiant) livingEntity).getHandle().getNavigation()
                    .a(x, y, z, speed);
        }

        return false;


    }

    //	      private void creatureMoveTo(Creature entity, int x, int y, int z) {
//	      ((org.bukkit.craftbukkit.entity.CraftCreature)entity).getHandle().setPathEntity(new net.minecraft.server.PathEntity(new net.minecraft.server.PathPoint[] {new net.minecraft.server.PathPoint(x, y, z)}));
//	      }
    public class wallattacker implements Runnable {
        final MonsterApocalypse plugin;
        final ZombieWallAttacker wallmanager;
        final List<List<LivingEntity>> allentitylists = new ArrayList<>();
        int ticks = 0,
                n = 0;

        public wallattacker(MonsterApocalypse instance) {
            plugin = instance;
            wallmanager = new ZombieWallAttacker(plugin);
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, wallmanager, 20 * 60 * 10, 20 * 60 * 10);
        }

        public void run() {
            if (ticks >= 4) {
                allentitylists.clear();
                ticks = 0;
                n = 0;
            }
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (allentitylists.size() <= i) {
                    allentitylists.add(plugin.getServer().getWorld(worldname).getLivingEntities());
                }
                List<LivingEntity> entitylist = allentitylists.get(i);
                if (allentitylists.size() < i || entitylist == null) {
                    allentitylists.add(plugin.getServer().getWorld(worldname).getLivingEntities());
                }
                entitylist = allentitylists.get(i);
                Boolean flag;
                if (newzombiemethod) {
                    wallmanager.resettimes();
                }
                if (ticks < 3) {
                    for (; n < (int) Math.floor(entitylist.size() * ((ticks + 1) / 4d)); n++) {
                        flag = false;
                        Entity thismob = entitylist.get(n);
                        if (!(thismob instanceof Zombie) || thismob.isDead() || ((thismob instanceof PigZombie) && !pigwalls))
                            continue;
                        LivingEntity targetz = getnearestplayerLEy(thismob, splitaggro);
                        Location loc = thismob.getLocation();
                        try {
                            if (wallmanager.getmobloc((LivingEntity) thismob).getBlockX() > loc.getBlockX() + 1 ||
                                    wallmanager.getmobloc((LivingEntity) thismob).getBlockX() < loc.getBlockX() - 1
                                    ) {
                                wallmanager.resetmobstill((LivingEntity) thismob);
                                wallmanager.resetmobbelow((LivingEntity) thismob);
                                continue;
                            }

                            if (wallmanager.getmobloc((LivingEntity) thismob).getBlockZ() > loc.getBlockZ() + 1 ||
                                    wallmanager.getmobloc((LivingEntity) thismob).getBlockZ() < loc.getBlockZ() - 1
                                    ) {
                                wallmanager.resetmobstill((LivingEntity) thismob);
                                wallmanager.resetmobbelow((LivingEntity) thismob);
                                continue;
                            }
                        } catch (NullPointerException e) {
                            wallmanager.addmob((LivingEntity) thismob);
                            continue;
                        }
                        if (targetz == null) {
                            continue;
                        }
                /*Location tarloc=null;
                try{
				tarloc=targetz.getLocation();}
				catch(NullPointerException e){wallmanager.resetmobstill((LivingEntity) thismob);}
				Location entloc=thismob.getLocation();
				try{
				int absx=Math.abs(tarloc.getBlockX()-entloc.getBlockX());
				int absz=Math.abs(tarloc.getBlockZ()-entloc.getBlockZ());
				if(!(absx<sprintdistance && absz<sprintdistance)){
					wallmanager.resetmobstill((LivingEntity) thismob);
				}}	catch(NullPointerException e){wallmanager.resetmobstill((LivingEntity) thismob);}*/
                        if (thismob instanceof Zombie) {
                            if (targetz instanceof Player) {


                                try {
                                    flag = zombieattackwall(thismob, targetz, wallmanager.getmobstill((LivingEntity) thismob));
//					wallmanager.increasestilltime((LivingEntity) thismob);
                    /*if(monsterteamwork){
                        List<Entity> L=thismob.getNearbyEntities(1, 1, 1);
						Iterator<Entity> iter=L.iterator();
						wallmanager.increasestilltime((LivingEntity) thismob);
						while(iter.hasNext()){
							Entity scanmob= iter.next();
							if(scanmob instanceof Zombie && !(scanmob instanceof PigZombie) && !scanmob.isDead()){
								int progress=wallmanager.getmobstill((LivingEntity) scanmob)+1;
								progress-=1;
								for(int h=0; h<progress; h++){
						wallmanager.increasestilltime((LivingEntity) thismob);}
							}}
					}else{*/
                                    //if(!getMobPillar(thismob)){
                                    //	wallmanager.increasestilltime((LivingEntity) thismob);
                                    //}
                                    //}
                                    if (flag) {
                        /*if(monsterteamwork){
							List<Entity> L=thismob.getNearbyEntities(1, 1, 1);
							Iterator<Entity> iter=L.iterator();
							wallmanager.resetmobstill((LivingEntity) thismob);
							while(iter.hasNext()){
								Entity scanmob= iter.next();
								if(scanmob instanceof Zombie && !(scanmob instanceof PigZombie) && !scanmob.isDead()){
									wallmanager.resetmobstill((LivingEntity) scanmob);
								}}
						}else{*/
                                        wallmanager.resetmobstill((LivingEntity) thismob);
                                    }
                                    //}
                                    continue;
                                } catch (NullPointerException e2) {

                                }
                            } else {
                                wallmanager.resetmobstill((LivingEntity) thismob);
                                wallmanager.resetmobbelow((LivingEntity) thismob);
                            }
                        }
                    }
                } else {
                    for (; n < entitylist.size(); n++) {
                        flag = false;
                        Entity thismob = entitylist.get(n);
                        if (!(thismob instanceof Zombie) || thismob.isDead() || ((thismob instanceof PigZombie) && !pigwalls))
                            continue;
                        LivingEntity targetz = getnearestplayerLEy(thismob, splitaggro);
                        Location loc = thismob.getLocation();
                        try {
                            if (wallmanager.getmobloc((LivingEntity) thismob).getBlockX() > loc.getBlockX() + 1 ||
                                    wallmanager.getmobloc((LivingEntity) thismob).getBlockX() < loc.getBlockX() - 1
                                    ) {
                                wallmanager.resetmobstill((LivingEntity) thismob);
                                continue;
                            }

                            if (wallmanager.getmobloc((LivingEntity) thismob).getBlockZ() > loc.getBlockZ() + 1 ||
                                    wallmanager.getmobloc((LivingEntity) thismob).getBlockZ() < loc.getBlockZ() - 1
                                    ) {
                                wallmanager.resetmobstill((LivingEntity) thismob);
                                continue;
                            }
                        } catch (NullPointerException e) {
                            wallmanager.addmob((LivingEntity) thismob);
                            continue;
                        }
                        if (targetz == null) {
                            continue;
                        }
					/*
					Location tarloc=null;
					try{
					tarloc=targetz.getLocation();}
					catch(NullPointerException e){wallmanager.resetmobstill((LivingEntity) thismob);}
					Location entloc=thismob.getLocation();
					try{
					int absx=Math.abs(tarloc.getBlockX()-entloc.getBlockX());
					int absz=Math.abs(tarloc.getBlockZ()-entloc.getBlockZ());
					if(!(absx<sprintdistance && absz<sprintdistance)){
						wallmanager.resetmobstill((LivingEntity) thismob);
					}}	catch(NullPointerException e){wallmanager.resetmobstill((LivingEntity) thismob);}
					*/
                        if (thismob instanceof Zombie) {
                            if (targetz instanceof Player) {


                                try {
                                    flag = zombieattackwall(thismob, targetz, wallmanager.getmobstill((LivingEntity) thismob));
//						wallmanager.increasestilltime((LivingEntity) thismob);
						/*if(monsterteamwork){
							List<Entity> L=thismob.getNearbyEntities(1, 1, 1);
							Iterator<Entity> iter=L.iterator();
							wallmanager.increasestilltime((LivingEntity) thismob);
							while(iter.hasNext()){
								Entity scanmob= iter.next();
								if(scanmob instanceof Zombie && !(scanmob instanceof PigZombie) && !scanmob.isDead()){
									int progress=wallmanager.getmobstill((LivingEntity) scanmob)+1;
									progress-=1;
									for(int h=0; h<progress; h++){
							wallmanager.increasestilltime((LivingEntity) thismob);}
								}}
						}else{*/
                                    //	if(!getMobPillar(thismob)){
                                    //	wallmanager.increasestilltime((LivingEntity) thismob);
                                    //}
                                    //}
                                    if (flag) {
							/*if(monsterteamwork){
								List<Entity> L=thismob.getNearbyEntities(1, 1, 1);
								Iterator<Entity> iter=L.iterator();
								wallmanager.resetmobstill((LivingEntity) thismob);
								while(iter.hasNext()){
									Entity scanmob= iter.next();
									if(scanmob instanceof Zombie && !(scanmob instanceof PigZombie) && !scanmob.isDead()){
										wallmanager.resetmobstill((LivingEntity) scanmob);
									}}
							}else{*/
                                        wallmanager.resetmobstill((LivingEntity) thismob);
                                    }
                                    //}
                                    continue;
                                } catch (NullPointerException e2) {

                                }
                            } else {
                                wallmanager.resetmobstill((LivingEntity) thismob);
                                wallmanager.resetmobbelow((LivingEntity) thismob);
                            }
                        }

                    }
                }
            }
            ticks++;
        }

        private boolean zombieattackwall(Entity ent, Entity targetp, int time) {
            boolean xside, zside;
            int horizflag, vertflag;
            horizflag = 0;
            vertflag = 0;
            xside = false;
            zside = false;
            int ax, ay, az, tx, ty, tz, bx, by, bz;
            bx = 0;
            by = 0;
            bz = 0;
            ax = ent.getLocation().getBlockX();
            ay = ent.getLocation().getBlockY();
            az = ent.getLocation().getBlockZ();
            tx = targetp.getLocation().getBlockX();
            ty = targetp.getLocation().getBlockY();
            tz = targetp.getLocation().getBlockZ();
            if (ax < tx) xside = true;
            if (az < tz) zside = true;
            if (Math.abs(ax - tx) < Math.abs(az - tz)) {
                horizflag = 0;
            }
            if (Math.abs(ax - tx) > Math.abs(az - tz)) {
                horizflag = 1;
            }
            if (Math.abs(ax - tx) == Math.abs(az - tz)) {
                horizflag = 2;
            }
            if (ay < ty) {
                vertflag = 0;
            }
            if (ay > ty) {
                vertflag = 1;
            }
            if (ay == ty) {
                vertflag = 2;
            }
            if (horizflag == 1 || horizflag == 2) {
                bz = az;
                if (xside) {
                    bx = ax + 1;
                } else {
                    bx = ax - 1;
                }
            }
            if (horizflag == 0) {
                bx = ax;
                if (zside) {
                    bz = az + 1;
                } else {
                    bz = az - 1;
                }
            }
            if (vertflag == 0) {
                by = ay + 1;
            }
            if (vertflag == 1) {
                by = ay - 1;
            }
            if (vertflag == 2) {
                by = ay + 0;
            }
            if (ax == tx && az == tz && ay > ty) {
                wallmanager.resetmobbelow((LivingEntity) ent);
                if (attemptpopblock(ax, ay - 1, az, time, ent.getWorld())) {
                    return true;
                } else {
                    return false;
                }
            } else if (ax == tx && az == tz && ay < ty - 2) {
                if (attemptpopblock(ax, ay + 2, az, time, ent.getWorld())) {
                    return true;
                } else {
                    //attemptcorpsepileup(ent, time);
                    return false;
                }
            } else {
                if (vertflag == 1) {
                    wallmanager.resetmobbelow((LivingEntity) ent);
                    if (!attemptpopblock(bx, by + 2, bz, time, ent.getWorld())) {
                        if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                            if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {

                                {
                                    if (horizflag == 0) {
                                        horizflag = 1;
                                    } else {
                                        horizflag = 0;
                                    }
                                }
                                if (horizflag == 1 || horizflag == 2) {
                                    bz = az;
                                    if (xside) {
                                        bx = ax + 1;
                                    } else {
                                        bx = ax - 1;
                                    }
                                }
                                if (horizflag == 0 || horizflag == 2) {
                                    bx = ax;
                                    if (zside) {
                                        bz = az + 1;
                                    } else {
                                        bz = az - 1;
                                    }
                                }
                                if (!attemptpopblock(bx, by + 2, bz, time, ent.getWorld())) {
                                    if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                                        if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {

                                            bx = ax;
                                            bz = az;
                                            if (xside) {
                                                ++bx;
                                            } else {
                                                --bx;
                                            }
                                            if (zside) {
                                                ++bz;
                                            } else {
                                                --bz;
                                            }
                                            if (!attemptpopblock(bx, by + 2, bz, time, ent.getWorld())) {
                                                if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                                                    if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {
                                                    } else {
                                                        return true;
                                                    }
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true;
                                            }
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true;
                                    }
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else if (vertflag == 0) {
                    if (!attemptpopblock(bx, by + 2, bz, time, ent.getWorld())) {
                        if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                            if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {
                                if (!attemptpopblock(ax, ay + 2, az, time, ent.getWorld())) {

                                    {
                                        if (horizflag == 0) {
                                            horizflag = 1;
                                        } else {
                                            horizflag = 0;
                                        }
                                    }
                                    if (horizflag == 1 || horizflag == 2) {
                                        bz = az;
                                        if (xside) {
                                            bx = ax + 1;
                                        } else {
                                            bx = ax - 1;
                                        }
                                    }
                                    if (horizflag == 0 || horizflag == 2) {
                                        bx = ax;
                                        if (zside) {
                                            bz = az + 1;
                                        } else {
                                            bz = az - 1;
                                        }
                                    }
                                    if (!attemptpopblock(bx, by + 2, bz, time, ent.getWorld())) {
                                        if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                                            if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {

                                                bx = ax;
                                                bz = az;
                                                if (xside) {
                                                    ++bx;
                                                } else {
                                                    --bx;
                                                }
                                                if (zside) {
                                                    ++bz;
                                                } else {
                                                    --bz;
                                                }
                                                if (!attemptpopblock(bx, by + 2, bz, time, ent.getWorld())) {
                                                    if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                                                        if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {
                                                            //	attemptcorpsepile(ent, time);
                                                        } else {
                                                            return true;
                                                        }
                                                    } else {
                                                        return true;
                                                    }
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true;
                                            }
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true;
                                    }
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    wallmanager.resetmobbelow((LivingEntity) ent);
                    if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                        if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {

                            {
                                if (horizflag == 0) {
                                    horizflag = 1;
                                } else {
                                    horizflag = 0;
                                }
                            }
                            if (horizflag == 1 || horizflag == 2) {
                                bz = az;
                                if (xside) {
                                    bx = ax + 1;
                                } else {
                                    bx = ax - 1;
                                }
                            }
                            if (horizflag == 0 || horizflag == 2) {
                                bx = ax;
                                if (zside) {
                                    bz = az + 1;
                                } else {
                                    bz = az - 1;
                                }
                            }
                            if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                                if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {

                                    bx = ax;
                                    bz = az;
                                    if (xside) {
                                        ++bx;
                                    } else {
                                        --bx;
                                    }
                                    if (zside) {
                                        ++bz;
                                    } else {
                                        --bz;
                                    }
                                    if (!attemptpopblock(bx, by + 1, bz, time, ent.getWorld())) {
                                        if (!attemptpopblock(bx, by, bz, time, ent.getWorld())) {
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true;
                                    }
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean attemptpopblock(int x, int y, int z, int time, World world) {
            if (!newzombiemethod) {
                int test = wallmanager.getblockseconds(world.getBlockAt(x, y, z).getType());
                if (
                        time
                                >=
                                test
                                && test >= 0
                        ) {
                    if (sound) {
                        world.playEffect(world.getBlockAt(x, y, z).getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 0);
                    }
                    return popblockflipper(world.getBlockAt(x, y, z));
                }

            } else {
                if (time >= 2) {
                    int test = wallmanager.getblockstate(world.getBlockAt(x, y, z), 1);
                    if (
                            test == 1
                            ) {
                        if (soundhit) {
                            world.playEffect(world.getBlockAt(x, y, z).getLocation(), Effect.ZOMBIE_CHEW_WOODEN_DOOR, 0);
                        }
                        return true;
                    } else if (test == 2) {
                        if (sound) {
                            world.playEffect(world.getBlockAt(x, y, z).getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 0);
                        }
                        return popblockflipper(world.getBlockAt(x, y, z));
                    }
                }
            }
            return false;
        }

        @SuppressWarnings("unused")
        private boolean attemptcorpsepile(Entity ent, int timez) {

            //Entity target=getnearestplayerLE(ent,12);
            if (!pillars) return false;
            Entity target = getnearestplayerLEy2(ent, true);
            if (target == null) {
                return false;
            }
            if (!hasdig(ent, target, timez)) return false;


            if (wallmanager.getmobbelow((LivingEntity) ent) >= pillardelay) {
                if (togglecorpsedie) {
                    ent.remove();
                } else {
                    ent.teleport(new Location(ent.getWorld(), ent.getLocation().getX(), ent.getLocation().getY() + 1.1, ent.getLocation().getZ()));
                    wallmanager.resetmobbelow((LivingEntity) ent);
                }
//				((LivingEntity)ent).damage(2000000);
//				ent.setLastDamageCause(new EntityDamageEvent(ent, DamageCause.FALL, 2000000));
                boolean flag2 = placeblockflipper(ent.getWorld().getBlockAt(ent.getLocation()));
                if (flag2) {
                    if (togglecorpsedie) {
                        ent.remove();
                    }
                }
                return flag2;
            }
            return false;
        }

        private boolean attemptcorpsepileup(Entity ent, int timez) {

            //Entity target=getnearestplayerLE(ent,12);
            if (!pillars) return false;
            Entity target = getnearestplayerLEy2(ent, true);
            if (target == null) {
                return false;
            }
            if (hasdigup(ent, timez)) return false;

            if (wallmanager.getmobbelow((LivingEntity) ent) >= pillardelay) {
                ent.teleport(new Location(ent.getWorld(), ent.getLocation().getX(), ent.getLocation().getY() + 1.1, ent.getLocation().getZ()));
                //wallmanager.resetmobbelow((LivingEntity) ent);

                Location eloc = ent.getLocation();
                boolean flag2 = placeblockflipper(ent.getWorld().getBlockAt(new Location(eloc.getWorld(), eloc.getBlockX(), eloc.getBlockY() - 1, eloc.getBlockZ())));
                if (flag2) {
                    if (togglecorpsedie) {
                        ent.remove();
                    }
                }
                return flag2;
            }
            return false;
        }

        private boolean hasdig(Entity ent, Entity targetp, int time) {
            boolean xside, zside;
            int horizflag;
            horizflag = 0;
            xside = false;
            zside = false;
            int ax, ay, az, tx, tz, bx, by, bz;
            bx = 0;
            by = 0;
            bz = 0;
            ax = ent.getLocation().getBlockX();
            ay = ent.getLocation().getBlockY();
            az = ent.getLocation().getBlockZ();
            tx = targetp.getLocation().getBlockX();
            tz = targetp.getLocation().getBlockZ();
            if (ax < tx) xside = true;
            if (az < tz) zside = true;
            if (Math.abs(ax - tx) < Math.abs(az - tz)) {
                horizflag = 0;
            }
            if (Math.abs(ax - tx) > Math.abs(az - tz)) {
                horizflag = 1;
            }
            if (Math.abs(ax - tx) == Math.abs(az - tz)) {
                horizflag = 2;
            }
            if (horizflag == 1 || horizflag == 2) {
                bz = az;
                if (xside) {
                    bx = ax + 1;
                } else {
                    bx = ax - 1;
                }
            }
            if (horizflag == 0) {
                bx = ax;
                if (zside) {
                    bz = az + 1;
                } else {
                    bz = az - 1;
                }
            }
            if (!getdiggable(bx, by + 2, bz, time, ent.getWorld())) {
                if (!getdiggable(bx, by + 1, bz, time, ent.getWorld())) {
                    if (!getdiggable(bx, by, bz, time, ent.getWorld())) {
                        if (!getdiggable(ax, ay + 2, az, time, ent.getWorld())) {

                            {
                                if (horizflag == 0) {
                                    horizflag = 1;
                                } else {
                                    horizflag = 0;
                                }
                            }
                            if (horizflag == 1 || horizflag == 2) {
                                bz = az;
                                if (xside) {
                                    bx = ax + 1;
                                } else {
                                    bx = ax - 1;
                                }
                            }
                            if (horizflag == 0 || horizflag == 2) {
                                bx = ax;
                                if (zside) {
                                    bz = az + 1;
                                } else {
                                    bz = az - 1;
                                }
                            }
                            if (!getdiggable(bx, by + 2, bz, time, ent.getWorld())) {
                                if (!getdiggable(bx, by + 1, bz, time, ent.getWorld())) {
                                    if (!getdiggable(bx, by, bz, time, ent.getWorld())) {

                                        bx = ax;
                                        bz = az;
                                        if (xside) {
                                            ++bx;
                                        } else {
                                            --bx;
                                        }
                                        if (zside) {
                                            ++bz;
                                        } else {
                                            --bz;
                                        }
                                        if (!getdiggable(bx, by + 2, bz, time, ent.getWorld())) {
                                            if (!getdiggable(bx, by + 1, bz, time, ent.getWorld())) {
                                                if (!getdiggable(bx, by, bz, time, ent.getWorld())) {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true;
                                            }
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true;
                                    }
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

        private boolean hasdigup(Entity ent, int time) {
            Location loc = ent.getLocation();
            if (getdiggable(loc.getBlockX(), loc.getBlockY() + 2, loc.getBlockZ(), time, ent.getWorld())) {
                return true;
            } else {

                return false;

            }
        }

        private boolean getdiggable(int x, int y, int z, int time, World world) {
            int test = wallmanager.getblockseconds(world.getBlockAt(x, y, z).getType());
            if (
                    test >= 0
                    ) {
                return cpopblockflipper(world.getBlockAt(x, y, z));
            }
            return false;
        }
    }

    private boolean placeblockflipper(Block b) {
        if (wgbuild) {
            return placeblockwg(b);
        } else {
            return placeblock(b);
        }
    }

    private boolean popblockflipper(Block b) {
        if (wgbuild) {
            return popblockwg(b);
        } else {
            return popblock(b);
        }
    }

    private boolean popblock(Block b) {
        Material oldmat = b.getType();
        short data = b.getData();
        boolean canpop = false;
        if (!(oldmat == Material.AIR || oldmat == Material.LAVA || oldmat == Material.WATER || oldmat == pillarmat)) {
            canpop = true;
        }
        if (oldmat == Material.AIR) {
            return false;
        }
        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId());
        //b.setType(Material.AIR);

        if (drop) {
            if (canpop) b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(oldmat, 1, data));
        }
        return true;
    }

    private boolean popblockwg(Block b) {
        if (getAllowPop(b)) {
            Material oldmat = b.getType();
            short data = b.getData();
            boolean canpop = false;
            if (!(oldmat == Material.AIR || oldmat == Material.LAVA || oldmat == Material.WATER || oldmat == pillarmat)) {
                canpop = true;
            }
            if (oldmat == Material.AIR) {
                return false;
            }
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId());
            //b.setType(Material.AIR);
//            recordCH(b);
            if (drop) {
                if (canpop) b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(oldmat, 1, data));
            }
            return true;
        }
        return false;
    }

    private boolean cpopblockflipper(Block b) {
        if (wgbuild) {
            return cpopblockwg(b);
        } else {
            return cpopblock(b);
        }
    }

    private boolean cpopblock(Block b) {
        Material oldmat = b.getType();
        if (oldmat == Material.AIR) {
            return false;
        }
        return true;
    }

    private boolean cpopblockwg(Block b) {
        if (getAllowPop(b)) {
            Material oldmat = b.getType();
            if (oldmat == Material.AIR) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean blockaboveisair(Block b) {
        Material oldmat = b.getRelative(BlockFace.UP).getType();
        if (!(oldmat == Material.AIR || oldmat == Material.LONG_GRASS || oldmat == Material.RED_ROSE || oldmat == Material.YELLOW_FLOWER)) {
            return false;
        }
        //	b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, pillarmat.getId());
        //recordCH(b);
        //	b.setType(pillarmat);
        //if(canpop)b.getWorld().dropItemNaturally(b.getLocation().add(0.0d, 1.0d, 0.0d),new ItemStack(oldmat, 1));
        return true;
    }

    private boolean blockbelowair(Block b) {
        Material oldmat = b.getRelative(BlockFace.DOWN).getType();
        if ((oldmat == Material.AIR || oldmat == Material.LONG_GRASS || oldmat == Material.RED_ROSE || oldmat == Material.YELLOW_FLOWER)) {
            return true;
        }
        //	b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, pillarmat.getId());
        //recordCH(b);
        //	b.setType(pillarmat);
        //if(canpop)b.getWorld().dropItemNaturally(b.getLocation().add(0.0d, 1.0d, 0.0d),new ItemStack(oldmat, 1));
        return false;
    }

    private boolean placeblock(Block b) {
        Material oldmat = b.getType();
        if (!(oldmat == Material.AIR || oldmat == Material.LAVA || oldmat == Material.WATER || oldmat == Material.STATIONARY_LAVA || oldmat == Material.STATIONARY_WATER || oldmat == Material.LONG_GRASS || oldmat == Material.RED_ROSE || oldmat == Material.YELLOW_FLOWER)) {
            return false;
        }
        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, pillarmat.getId());

        b.setType(pillarmat);
        //if(canpop)b.getWorld().dropItemNaturally(b.getLocation().add(0.0d, 1.0d, 0.0d),new ItemStack(oldmat, 1));
        return true;
    }

    private boolean placeblockwg(Block b) {
        if (getAllowPop(b)) {
            Material oldmat = b.getType();
            if (!(oldmat == Material.AIR || oldmat == Material.LAVA || oldmat == Material.WATER || oldmat == Material.STATIONARY_LAVA || oldmat == Material.STATIONARY_WATER || oldmat == Material.LONG_GRASS || oldmat == Material.RED_ROSE || oldmat == Material.YELLOW_FLOWER)) {
                return false;
            }
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, pillarmat.getId());

            b.setType(pillarmat);
            //if(canpop)b.getWorld().dropItemNaturally(b.getLocation().add(0.0d, 1.0d, 0.0d),new ItemStack(oldmat, 1));
            return true;
        }
        return false;
    }

    public healthmanager getHealthManager() {
        return healths;
    }

    private boolean getAllowPop(Block b) {
        return true;
    }

    boolean getWGSpawnable(Entity ent) {
        return true;
    }

    boolean getWGMobAttack(Entity ent) {
        return true;
    }

    boolean getWGExplosion(Entity ent) {
        return true;
    }

    boolean getWGExplosion(Location L) {
        return true;
    }

    private class NeutralAttacker implements Runnable {
        final MonsterApocalypse plugin;
        final List<List<LivingEntity>> allentitylists = new ArrayList<>();
        int ticks = 0, n = 0;

        public NeutralAttacker(MonsterApocalypse instance) {
            plugin = instance;
        }

        public void run() {
            if (ticks > 19) {
                allentitylists.clear();
                ticks = 0;
                n = 0;
            }
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (allentitylists.size() <= i) {
                    allentitylists.add(plugin.getServer().getWorld(worldname).getLivingEntities());
                }
                List<LivingEntity> entitylist = allentitylists.get(i);
                if (allentitylists.size() < i || entitylist == null) {
                    allentitylists.add(plugin.getServer().getWorld(worldname).getLivingEntities());
                }
                entitylist = allentitylists.get(i);
                if (ticks < 19)
                    for (; n < (int) Math.floor(entitylist.size() * ((ticks + 1) / 20d)); n++) {
                        Entity thismob = entitylist.get(n);
                        if (thismob.isDead()) continue;
                        if (!(thismob instanceof Wolf || thismob instanceof PigZombie)) continue;
                        if (thismob instanceof Wolf && plugin.aggressivewolf) {
                            ((Wolf) thismob).setAngry(true);
                            LivingEntity tar = plugin.getnearestplayerLEy(thismob, false, 10);
                            if ((((Wolf) thismob).getTarget() == null || !((Wolf) thismob).getTarget().equals(tar))) {
                                ((Wolf) thismob).setTarget(tar);
                                ((Wolf) thismob).damage(0, tar);
                            }
                        }
                        if (thismob instanceof PigZombie && plugin.aggressivepig)
                            ((PigZombie) thismob).setTarget(getnearestplayerLEy(thismob, splitaggro, 10));
                    }
                else {
                    for (; n < entitylist.size(); n++) {
                        Entity thismob = entitylist.get(n);
                        if (thismob.isDead()) continue;
                        if (!(thismob instanceof Wolf || thismob instanceof PigZombie)) continue;
                        if (thismob instanceof Wolf && plugin.aggressivewolf) {
                            ((Wolf) thismob).setAngry(true);
                            LivingEntity tar = plugin.getnearestplayerLEy(thismob, false, 10);
                            if ((((Wolf) thismob).getTarget() == null || !((Wolf) thismob).getTarget().equals(tar))) {
                                ((Wolf) thismob).setTarget(tar);
                                ((Wolf) thismob).damage(0, tar);
                            }
                        }
                        if (thismob instanceof PigZombie && plugin.aggressivepig)
                            ((PigZombie) thismob).setTarget(getnearestplayerLEy(thismob, splitaggro, 10));
                    }
                }
            }
            ticks++;
        }
    }

    private class NaturalSpawner implements Runnable {
        public NaturalSpawner(MonsterApocalypse instance) {
        }

        public void run() {
            for (String worldname : worldnames) {
                if (Noneworld && !worldname.equals(Noneworldname)) {
                    continue;
                }
                //if(plugin.getServer().getWorld(worldnames.get(i)).getEntities().size()>=nhardcap){continue;}
                World world = getServer().getWorld(worldname);
                spawntime = System.currentTimeMillis();
                rand = new Random(spawntime);
                for (int n = 0; n < world.getPlayers().size(); n++) {
                    target = world.getPlayers().get(n);
                    if (target.getGameMode() == GameMode.CREATIVE) continue;
                    int nearbyplayers = getnearestplayerLEcount(target, (int) Math.round((double) nmax * 2d / 3d));
                    if (nearbyplayers <= 0) nearbyplayers = 1;
                    long runtimes = Math.round(((double) nruns) / ((double) nearbyplayers));
                    if (runtimes <= 0) runtimes = 1;
                    int truecount = (int) ((int) runtimes + Math.round(((naturalscaling - 1.0d) * (Math.min(naturaldistance, Math.round(getspawndis(target.getLocation()))) / (double) naturaldistance) * (runtimes))));
                    for (long reset = 0; reset < truecount; reset++) {

                        int diff = nmax - nmin;
                        double t = 2 * Math.PI * rand.nextDouble();
                        double u = rand.nextDouble() + rand.nextDouble();
                        double r;
                        if (u > 1) {
                            r = 2 - u;
                        } else {
                            r = u + 0;
                        }
                        x = (int) Math.round(r * Math.cos(t) * (rand.nextInt(diff) + nmin));
                        z = (int) Math.round(r * Math.sin(t) * (rand.nextInt(diff) + nmin));
                        int noffset = rand.nextInt(255);
                        if (noffset == 0) noffset = 1;
                        boolean dobreak = false;
                        //	EntityType spawntype=getnspawntype();
                        if (spawn.getLocation().getBlock().getY() >= nymin
                                && spawn.getLocation().getBlock().getY() <= nymax) {
                            if (spawn.getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) &&
                                    spawn.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                                if (spawn.getLocation().getBlock().getLightLevel() >= nlightmin && spawn.getLocation().getBlock().getLightLevel() <= nlightmax
                                        ) {
                                    for (int death = 0; death < nmobs; death++) {
                                        try {
                                            if (!checkplayersinbox(spawn.getLocation(), nmin)) {
                                                EntityType te = getnspawntype();
                                                if (spawn.getLocation().getBlock().getY() >= getMobYMin(te)
                                                        && spawn.getLocation().getBlock().getY() <= getMobYMax(te)) {

                                                    if (te == EntityType.WITHER_SKULL) {
                                                        te = EntityType.SKELETON;
                                                        creature = (LivingEntity) world.spawnEntity(spawn.getLocation(), te);
                                                        ((Skeleton) creature).setSkeletonType(SkeletonType.WITHER);
                                                        creature.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                                                    } else if (te == EntityType.SKELETON) {
                                                        creature = (LivingEntity) world.spawnEntity(spawn.getLocation(), te);
                                                        creature.getEquipment().setItemInHand(new ItemStack(Material.BOW));
                                                    } else {
                                                        creature = (LivingEntity) world.spawnEntity(spawn.getLocation(), te);
                                                    }
                                                }
                                                dobreak = true;
                                            }
                                        } catch (NullPointerException e) {
                                            return;
                                        }
                                    }
                                    if (dobreak) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public class nightmaremode implements Runnable {
        final MonsterApocalypse plugin;
        final double originalbonus;
        final double originalnaturalistic;
        double currentnatural;
        double currentbonus;

        public nightmaremode(MonsterApocalypse m) {
            plugin = m;
            originalbonus = monstersperplayer;
            originalnaturalistic = nruns;
            currentnatural = originalnaturalistic;
            currentbonus = originalbonus;
        }

        public void run() {
            if (nightmaretype) {
                currentnatural *= nightmaremultiplier;
                currentbonus *= nightmaremultiplier;
            } else {
                currentnatural += (originalnaturalistic * nightmaremultiplier);
                currentbonus += (originalbonus * nightmaremultiplier);
            }

            nruns = (int) Math.round(currentnatural);
            monstersperplayer = (int) Math.round(currentbonus);
        }

    }

    private boolean timeisday(Entity ent) {
        return (ent.getWorld().getTime() < 13800);
    }
}
