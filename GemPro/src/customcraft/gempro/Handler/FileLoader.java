//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.Handler;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FileLoader extends YamlConfiguration {
	private static final Logger LOGGER = Logger.getLogger("Minecraft");
	private final File configFile;
	private String templateName = null;
	private Class<?> resourceClass = FileLoader.class;
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
	private final AtomicInteger pendingDiskWrites = new AtomicInteger(0);
	private final byte[] bytebuffer = new byte[1024];

	public FileLoader(File configFile) {
		this.configFile = configFile.getAbsoluteFile();
	}

	public synchronized void load() {
		if(this.pendingDiskWrites.get() != 0) {
			LOGGER.log(Level.INFO, "File " + this.configFile + " not read, because it\'s not yet written to disk.");
		} else {
			if(!this.configFile.getParentFile().exists() && !this.configFile.getParentFile().mkdirs()) {
				LOGGER.log(Level.SEVERE, "无法创建配置: " + this.configFile);
			}

			FileInputStream ex;
			if(this.configFile.exists() && this.configFile.length() != 0L) {
				try {
					ex = new FileInputStream(this.configFile);

					try {
						if(ex.read() == 0) {
							ex.close();
							this.configFile.delete();
						}
					} catch (IOException var30) {
						LOGGER.log(Level.SEVERE, null, var30);
					} finally {
						try {
							ex.close();
						} catch (IOException var29) {
							LOGGER.log(Level.SEVERE, null, var29);
						}

					}
				} catch (FileNotFoundException var32) {
					LOGGER.log(Level.SEVERE, null, var32);
				}
			}

			if(!this.configFile.exists()) {
				if(this.templateName == null) {
					return;
				}

				LOGGER.log(Level.INFO, "从模板创建配置" + this.configFile.toString());
				this.createFromTemplate();
			}

			try {
				ex = new FileInputStream(this.configFile);

				try {
					long broken1 = this.configFile.length();
					if(broken1 > 2147483647L) {
						throw new InvalidConfigurationException("File too big");
					}

					ByteBuffer buffer;
					int length;
					for(buffer = ByteBuffer.allocate((int)broken1); (length = ex.read(this.bytebuffer)) != -1; buffer.put(this.bytebuffer, 0, length)) {
						if(length > buffer.remaining()) {
							ByteBuffer data = ByteBuffer.allocate(buffer.capacity() + length - buffer.remaining());
							int decoder = buffer.position();
							buffer.rewind();
							data.put(buffer);
							data.position(decoder);
							buffer = data;
						}
					}

					buffer.rewind();
					CharBuffer data1 = CharBuffer.allocate(buffer.capacity());
					CharsetDecoder decoder1 = UTF8.newDecoder();
					CoderResult result = decoder1.decode(buffer, data1, true);
					if(result.isError()) {
						buffer.rewind();
						data1.clear();
						LOGGER.log(Level.INFO, "File " + this.configFile.getAbsolutePath() + " is not utf-8 encoded, trying " + Charset.defaultCharset().displayName());
						decoder1 = Charset.defaultCharset().newDecoder();
						result = decoder1.decode(buffer, data1, true);
						if(result.isError()) {
							throw new InvalidConfigurationException("Invalid Characters in file " + this.configFile.getAbsolutePath());
						}

						decoder1.flush(data1);
					} else {
						decoder1.flush(data1);
					}

					int end = data1.position();
					data1.rewind();
					super.loadFromString(data1.subSequence(0, end).toString());
				} finally {
					ex.close();
				}
			} catch (IOException var34) {
				LOGGER.log(Level.SEVERE, var34.getMessage(), var34);
			} catch (InvalidConfigurationException var35) {
				File broken = new File(this.configFile.getAbsolutePath() + ".broken." + System.currentTimeMillis());
				this.configFile.renameTo(broken);
				LOGGER.log(Level.SEVERE, "The file " + this.configFile.toString() + " is broken, it has been renamed to " + broken.toString(), var35.getCause());
			}

		}
	}

	private void createFromTemplate() {
		InputStream istr = null;
		FileOutputStream ostr = null;

		try {
			istr = this.resourceClass.getResourceAsStream(this.templateName);
			if(istr == null) {
				LOGGER.log(Level.SEVERE, "无法找到模板" + this.templateName);
				return;
			}

			ostr = new FileOutputStream(this.configFile);
			byte[] ex = new byte[1024];
			boolean length = false;

			for(int length1 = istr.read(ex); length1 > 0; length1 = istr.read(ex)) {
				ostr.write(ex, 0, length1);
			}
		} catch (IOException var19) {
			LOGGER.log(Level.SEVERE, "无法写入配置" + this.configFile, var19);
		} finally {
			try {
				if(istr != null) {
					istr.close();
				}
			} catch (IOException var18) {
				Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, var18);
			}

			try {
				if(ostr != null) {
					ostr.close();
				}
			} catch (IOException var17) {
				LOGGER.log(Level.SEVERE, "无法关闭配置" + this.configFile, var17);
			}

		}

	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public File getFile() {
		return this.configFile;
	}

	public void setTemplateName(String templateName, Class<?> resClass) {
		this.templateName = templateName;
		this.resourceClass = resClass;
	}

	public void save() {
		try {
			this.save(this.configFile);
		} catch (IOException var2) {
			LOGGER.log(Level.SEVERE, var2.getMessage(), var2);
		}

	}

	public void saveWithError() throws IOException {
		this.save(this.configFile);
	}

	public synchronized void save(File file) throws IOException {
		this.delayedSave(file);
	}

	public synchronized void forceSave() {
		try {
			Future ex = this.delayedSave(this.configFile);
			if(ex != null) {
				ex.get();
			}
		} catch (InterruptedException var2) {
			LOGGER.log(Level.SEVERE, var2.getMessage(), var2);
		} catch (ExecutionException var3) {
			LOGGER.log(Level.SEVERE, var3.getMessage(), var3);
		}

	}

	private Future<?> delayedSave(File file) {
		if(file == null) {
			throw new IllegalArgumentException("File cannot be null");
		} else {
			String data = this.saveToString();
			if(data.length() == 0) {
				return null;
			} else {
				this.pendingDiskWrites.incrementAndGet();
				Future future = EXECUTOR_SERVICE.submit(new FileLoader.WriteRunner(this.configFile, data, this.pendingDiskWrites));
				return future;
			}
		}
	}

	public boolean hasProperty(String path) {
		return this.isSet(path);
	}

	public Location getLocation(String path, Server server) throws Exception {
		String worldName = this.getString((path == null?"":path + ".") + "world");
		if(worldName != null && !worldName.isEmpty()) {
			World world = server.getWorld(worldName);
			if(world == null) {
				throw new Exception(worldName);
			} else {
				return new Location(world, this.getDouble((path == null?"":path + ".") + "x", 0.0D), this.getDouble((path == null?"":path + ".") + "y", 0.0D), this.getDouble((path == null?"":path + ".") + "z", 0.0D), (float)this.getDouble((path == null?"":path + ".") + "yaw", 0.0D), (float)this.getDouble((path == null?"":path + ".") + "pitch", 0.0D));
			}
		} else {
			return null;
		}
	}

	public void setProperty(String path, Location loc) {
		this.set((path == null?"":path + ".") + "world", loc.getWorld().getName());
		this.set((path == null?"":path + ".") + "x", loc.getX());
		this.set((path == null?"":path + ".") + "y", loc.getY());
		this.set((path == null?"":path + ".") + "z", loc.getZ());
		this.set((path == null?"":path + ".") + "yaw", loc.getYaw());
		this.set((path == null?"":path + ".") + "pitch", loc.getPitch());
	}

	public ItemStack getItemStack(String path) {
		ItemStack stack = new ItemStack(Material.valueOf(this.getString(path + ".type", "AIR")), this.getInt(path + ".amount", 1), (short)this.getInt(path + ".damage", 0));
		ConfigurationSection enchants = this.getConfigurationSection(path + ".enchant");
		if(enchants != null) {

			for (Object enchant : enchants.getKeys(false)) {
				Enchantment enchantment = Enchantment.getByName(enchant.toUpperCase(Locale.ENGLISH));
				if (enchantment != null) {
					int level = this.getInt(path + ".enchant." + enchant, enchantment.getStartLevel());
					stack.addUnsafeEnchantment(enchantment, level);
				}
			}
		}

		return stack;
	}

	public void setProperty(String path, ItemStack stack) {
		HashMap map = new HashMap();
		map.put("type", stack.getType().toString());
		map.put("amount", stack.getAmount());
		map.put("damage", stack.getDurability());
		Map enchantments = stack.getEnchantments();
		if(!enchantments.isEmpty()) {
			HashMap enchant = new HashMap();

			for (Object o : enchantments.entrySet()) {
				Entry entry = (Entry) o;
				enchant.put(((Enchantment) entry.getKey()).getName().toLowerCase(Locale.ENGLISH), entry.getValue());
			}

			map.put("enchant", enchant);
		}

		this.set(path, map);
	}

	public void setProperty(String path, List object) {
		this.set(path, new ArrayList(object));
	}

	public void setProperty(String path, Map object) {
		this.set(path, new LinkedHashMap(object));
	}

	public Object getProperty(String path) {
		return this.get(path);
	}

	public void setProperty(String path, BigDecimal bigDecimal) {
		this.set(path, bigDecimal.toString());
	}

	public void setProperty(String path, Object object) {
		this.set(path, object);
	}

	public void removeProperty(String path) {
		this.set(path, null);
	}

	public synchronized Object get(String path) {
		return super.get(path);
	}

	public synchronized Object get(String path, Object def) {
		return super.get(path, def);
	}

	public synchronized BigDecimal getBigDecimal(String path, BigDecimal def) {
		String input = super.getString(path);
		return toBigDecimal(input, def);
	}

	private static BigDecimal toBigDecimal(String input, BigDecimal def) {
		if(input != null && !input.isEmpty()) {
			try {
				return new BigDecimal(input, MathContext.DECIMAL128);
			} catch (NumberFormatException var3) {
				return def;
			} catch (ArithmeticException var4) {
				return def;
			}
		} else {
			return def;
		}
	}

	public synchronized boolean getBoolean(String path) {
		return super.getBoolean(path);
	}

	public synchronized boolean getBoolean(String path, boolean def) {
		return super.getBoolean(path, def);
	}

	public synchronized List<Boolean> getBooleanList(String path) {
		return super.getBooleanList(path);
	}

	public synchronized List<Byte> getByteList(String path) {
		return super.getByteList(path);
	}

	public synchronized List<Character> getCharacterList(String path) {
		return super.getCharacterList(path);
	}

	public synchronized ConfigurationSection getConfigurationSection(String path) {
		return super.getConfigurationSection(path);
	}

	public synchronized double getDouble(String path) {
		return super.getDouble(path);
	}

	public synchronized double getDouble(String path, double def) {
		return super.getDouble(path, def);
	}

	public synchronized List<Double> getDoubleList(String path) {
		return super.getDoubleList(path);
	}

	public synchronized List<Float> getFloatList(String path) {
		return super.getFloatList(path);
	}

	public synchronized int getInt(String path) {
		return super.getInt(path);
	}

	public synchronized int getInt(String path, int def) {
		return super.getInt(path, def);
	}

	public synchronized List<Integer> getIntegerList(String path) {
		return super.getIntegerList(path);
	}

	public synchronized ItemStack getItemStack(String path, ItemStack def) {
		return super.getItemStack(path, def);
	}

	public synchronized Set<String> getKeys(boolean deep) {
		return super.getKeys(deep);
	}

	public synchronized List<?> getList(String path) {
		return super.getList(path);
	}

	public synchronized List<?> getList(String path, List<?> def) {
		return super.getList(path, def);
	}

	public synchronized long getLong(String path) {
		return super.getLong(path);
	}

	public synchronized long getLong(String path, long def) {
		return super.getLong(path, def);
	}

	public synchronized List<Long> getLongList(String path) {
		return super.getLongList(path);
	}

	public synchronized Map<String, Object> getMap() {
		return this.map;
	}

	public synchronized List<Map<?, ?>> getMapList(String path) {
		return super.getMapList(path);
	}

	public synchronized OfflinePlayer getOfflinePlayer(String path) {
		return super.getOfflinePlayer(path);
	}

	public synchronized OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
		return super.getOfflinePlayer(path, def);
	}

	public synchronized List<Short> getShortList(String path) {
		return super.getShortList(path);
	}

	public synchronized String getString(String path) {
		return super.getString(path);
	}

	public synchronized String getString(String path, String def) {
		return super.getString(path, def);
	}

	public synchronized List<String> getStringList(String path) {
		return super.getStringList(path);
	}

	public synchronized Map<String, Object> getValues(boolean deep) {
		return super.getValues(deep);
	}

	public synchronized Vector getVector(String path) {
		return super.getVector(path);
	}

	public synchronized Vector getVector(String path, Vector def) {
		return super.getVector(path, def);
	}

	public synchronized boolean isBoolean(String path) {
		return super.isBoolean(path);
	}

	public synchronized boolean isConfigurationSection(String path) {
		return super.isConfigurationSection(path);
	}

	public synchronized boolean isDouble(String path) {
		return super.isDouble(path);
	}

	public synchronized boolean isInt(String path) {
		return super.isInt(path);
	}

	public synchronized boolean isItemStack(String path) {
		return super.isItemStack(path);
	}

	public synchronized boolean isList(String path) {
		return super.isList(path);
	}

	public synchronized boolean isLong(String path) {
		return super.isLong(path);
	}

	public synchronized boolean isOfflinePlayer(String path) {
		return super.isOfflinePlayer(path);
	}

	public synchronized boolean isSet(String path) {
		return super.isSet(path);
	}

	public synchronized boolean isString(String path) {
		return super.isString(path);
	}

	public synchronized boolean isVector(String path) {
		return super.isVector(path);
	}

	public synchronized void set(String path, Object value) {
		super.set(path, value);
	}

	private static class WriteRunner implements Runnable {
		private final File configFile;
		private final String data;
		private final AtomicInteger pendingDiskWrites;

		private WriteRunner(File configFile, String data, AtomicInteger pendingDiskWrites) {
			this.configFile = configFile;
			this.data = data;
			this.pendingDiskWrites = pendingDiskWrites;
		}

		public void run() {
			File var1 = this.configFile;
			synchronized(this.configFile) {
				if(this.pendingDiskWrites.get() > 1) {
					this.pendingDiskWrites.decrementAndGet();
				} else {
					try {
						Files.createParentDirs(this.configFile);
						if(!this.configFile.exists()) {
							try {
								FileLoader.LOGGER.log(Level.INFO, "创建空配置" + this.configFile);
								if(!this.configFile.createNewFile()) {
									FileLoader.LOGGER.log(Level.SEVERE, "无法创建配置" + this.configFile);
									return;
								}
							} catch (IOException var28) {
								FileLoader.LOGGER.log(Level.SEVERE, "无法创建配置" + this.configFile, var28);
								return;
							}
						}

						FileOutputStream e = new FileOutputStream(this.configFile);

						try {
							OutputStreamWriter writer = new OutputStreamWriter(e, FileLoader.UTF8);

							try {
								writer.write(this.data);
							} finally {
								writer.close();
							}
						} finally {
							e.close();
						}
					} catch (IOException var29) {
						FileLoader.LOGGER.log(Level.SEVERE, var29.getMessage(), var29);
					} finally {
						this.pendingDiskWrites.decrementAndGet();
					}

				}
			}
		}
	}
}
