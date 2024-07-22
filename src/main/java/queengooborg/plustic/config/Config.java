package queengooborg.plustic.config;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import it.unimi.dsi.fastutil.ints.*;
//import queengooborg.plustic.traits.*;
//import net.minecraft.item.*;
//import net.minecraft.util.*;
//import net.minecraft.util.text.translation.*;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import slimeknights.tconstruct.world.TinkerStructures;
//import net.minecraftforge.fml.loading.FMLPaths;
//import net.minecraftforge.fml.common.event.*;
//import net.minecraftforge.oredict.OreDictionary;
//import slimeknights.tconstruct.library.materials.*;
//import slimeknights.tconstruct.library.smeltery.*;

public class Config {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static class Module {
		public static ForgeConfigSpec.BooleanValue base;
		public static ForgeConfigSpec.BooleanValue biomesOPlenty;
		public static ForgeConfigSpec.BooleanValue projectRed;
		public static ForgeConfigSpec.BooleanValue mekanism;
		public static ForgeConfigSpec.BooleanValue botania;
		public static ForgeConfigSpec.BooleanValue armorPlus;
		public static ForgeConfigSpec.BooleanValue thermalFoundation;
		public static ForgeConfigSpec.BooleanValue draconicEvolution;
		public static ForgeConfigSpec.BooleanValue actuallyAdditions;
		public static ForgeConfigSpec.BooleanValue psi;
		public static ForgeConfigSpec.BooleanValue avaritia;
		public static ForgeConfigSpec.BooleanValue industrialForegoing;
		public static ForgeConfigSpec.BooleanValue survivalist;
		public static ForgeConfigSpec.BooleanValue projectE;
		public static ForgeConfigSpec.BooleanValue gemsplusplus;
		public static ForgeConfigSpec.BooleanValue appEng2;
		public static ForgeConfigSpec.BooleanValue machines;
		public static ForgeConfigSpec.BooleanValue astralSorcery;
		public static ForgeConfigSpec.BooleanValue aoa;
	}

	public static class Tools {
		public static class Katana {
			public static ForgeConfigSpec.BooleanValue enable;
			public static ForgeConfigSpec.DoubleValue comboMultiplier;
			public static ForgeConfigSpec.BooleanValue boostOnMobsOnly;
			public static ForgeConfigSpec.BooleanValue smoothProgression;
		}

		public static class LaserGun {
			public static ForgeConfigSpec.BooleanValue enable;
			public static ForgeConfigSpec.IntValue energy;
		}
	}

	public static class Tweaks {
//		public static ForgeConfigSpec.ConfigValue<String[]> trashItems;
//		public static ForgeConfigSpec.ConfigValue<String[]> forceLoadTraits;
//		public static ForgeConfigSpec.ConfigValue<String[]> fruitSaladItems;

		public static class Centrifuge {
			//			public static ForgeConfigSpec.ConfigValue<String[]> blacklist;
			public static ForgeConfigSpec.IntValue energyPerMB;
		}

		public static class Botanical {
			public static ForgeConfigSpec.ConfigValue<IntArrayList> botanAmount;
		}

		public static class ThermalFoundation {
			public static ForgeConfigSpec.BooleanValue pyrotheumSmelt;
			public static ForgeConfigSpec.BooleanValue extraMelt;
		}
	}

	static {
		// Modules
		BUILDER.comment("Enable or disable compatibility with other mods").push("modules");
		Module.base = BUILDER.comment("Add features to vanilla Tinkers Construct").define("base", true);
		Module.biomesOPlenty = BUILDER.comment("Integrate with Biomes o' Plenty").define("biomesOPlenty", true);
		Module.projectRed = BUILDER.comment("Integrate with Project Red-Core").define("projectRed", true);
		Module.mekanism = BUILDER.comment("Integrate with Mekanism").define("mekanism", true);
		Module.botania = BUILDER.comment("Integrate with Botania").define("botania", true);
		Module.armorPlus = BUILDER.comment("Integrate with ArmorPlus").define("armorPlus", true);
		Module.thermalFoundation = BUILDER.comment("Integrate with Thermal Foundation").define("thermalFoundation", true);
		Module.draconicEvolution = BUILDER.comment("Integrate with Draconic Evolution").define("draconicEvolution", true);
		Module.actuallyAdditions = BUILDER.comment("Integrate with Actually Additions").define("actuallyAdditions", true);
		Module.psi = BUILDER.comment("Integrate with Psi").define("psi", true);
		Module.avaritia = BUILDER.comment("Integrate with Avaritia").define("avaritia", true);
		Module.industrialForegoing = BUILDER.comment("Integrate with Industrial Foregoing").define("industrialForegoing", true);
		Module.survivalist = BUILDER.comment("Integrate with Survivalist").define("survivalist", true);
		Module.projectE = BUILDER.comment("Integrate with ProjectE").define("projectE", true);
		Module.gemsplusplus = BUILDER.comment("Integrate with Gems++").define("gemsplusplus", true);
		Module.appEng2 = BUILDER.comment("Integrate with Applied Energistics 2").define("appEng2", true);
		Module.machines = BUILDER.comment("Enable the machines from this mod (Centrifuge, etc.)").define("machines", true);
		Module.astralSorcery = BUILDER.comment("Integrate with Astral Sorcery").define("astralSorcery", true);
		Module.aoa = BUILDER.comment("Integrate with AoA").define("aoa", true);
		BUILDER.pop();

		// Tools
		BUILDER.comment("Configuration for the new tools").push("tools");

		BUILDER.comment("Configuration for the Katana").push("katana");
		Tools.Katana.enable = BUILDER.comment("Enable Katana").define("enable", true);
		Tools.Katana.comboMultiplier = BUILDER.comment("Multiplier for Katana's combo value").defineInRange("comboMultiplier", 1.25f, 0, Float.MAX_VALUE);
		Tools.Katana.boostOnMobsOnly = BUILDER.comment("Katana boosts only when killing mobs (false = boost on any impact)").define("boostOnMobsOnly", true);
		Tools.Katana.smoothProgression = BUILDER.comment("Should boosted damage of Katana change smoothly from material to material?").define("smoothProgression", false);
		BUILDER.pop();

		BUILDER.comment("Configuration for the laser gun").push("laserGun");
		Tools.LaserGun.enable = BUILDER.comment("Enable Laser Gun").define("enable", true);
		Tools.LaserGun.energy = BUILDER.comment("How much energy is used, by default, per laser attack").defineInRange("energy", 100, 0, Integer.MAX_VALUE);
		BUILDER.pop();

		BUILDER.pop();

		// Tweaks
		BUILDER.comment("Configuration for various tweaks").push("tweaks");

//		Tweaks.trashItems = BUILDER.comment("Objects that the Trash modifier will generate; enter in the format \"weight|modid:name;meta\" (leave meta blank for zero metadata)")
//				.define("trashItems", new String[] {"20|coal", "5|slime_ball", "10|saddle", "5|tconstruct:edible;1", "1|emerald", "3|melon"});
//		Tweaks.forceLoadTraits = BUILDER.comment("Force-load these traits (as a fully-qualified class name; e.g. queengooborg.plustic.traits.Global) without the required mods themselves being loaded")
//				.define("forceLoadTraits", new String[0]);
//		Tweaks.fruitSaladItems = BUILDER.comment("Items that can be used in Fruit Salad").define("fruitSaladItems", new String[] {"minecraft:apple", "minecraft:golden_apple", "minecraft:melon", "minecraft:chorus_fruit"});

		BUILDER.comment("Configuration for the centrifuge").push("centrifuge");
//		Tweaks.Centrifuge.blacklist = BUILDER.comment("Blacklist for centrifuge recipes (enter in format 'inputFluid:outputFluid1;outputFluid2;outputFluid3')").define("blacklist", new String[0]);
		Tweaks.Centrifuge.energyPerMB = BUILDER.comment("Energy consumed by centrifuge per millibucket").defineInRange("energyPerMB", 5, 0, Integer.MAX_VALUE);
		BUILDER.pop();

		BUILDER.comment("Configuration for Thermal Foundation compatibility").push("ThermalFoundation");
		Tweaks.ThermalFoundation.pyrotheumSmelt = BUILDER.comment("Use Pyrotheum as smeltery fuel").define("pyrotheumSmelt", true);
		Tweaks.ThermalFoundation.extraMelt = BUILDER.comment("Add smeltery recipes for Redstone, Glowstone, and Ender pearl").define("extraMelt", true);
		BUILDER.pop();

		BUILDER.comment("Configuration for Botanical compatibility").push("Botanical");
//		Tweaks.Botanical.botanAmount = BUILDER.comment("Modifier values for Botanical, specifying the amount of modifiers added to the tool for each level, in increasing order of level (defaults will be extrapolated if some left blank)").define("botanAmount", new IntArrayList(Botanical.MAX_LEVELS));
		BUILDER.pop();

		BUILDER.pop();

		SPEC = BUILDER.build();
	}


	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC, "plustic.toml");

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(Config::configChanged);
	}

	private static void configChanged(ModConfig.Reloading event) {
		ModConfig config = event.getConfig();
		if (config.getModId().equals(ModInfo.MODID)) {
			ForgeConfigSpec spec = config.getSpec();
			if (spec == Config.SPEC) {
				TinkerStructures.addStructureSeparation();
			}
		}
	}

	// XXX Restructure from here on

//	private static class TrashThing {
//		public final int weight;
//		public final ItemStack stack;
//
//		public TrashThing(int weight, ItemStack stack) {
//			this.weight = weight;
//			this.stack = stack;
//		}
//	}
//
//	private static final List<TrashThing> trashThings = new ArrayList<>();
//
//	private static int trashThingsSum = 0;
//	public static @javax.annotation.Nullable ItemStack fetchThing(Random random) {
//		if (trashThingsSum <= 0) {
//			trashThingsSum = trashThings.stream().mapToInt(t -> t.weight).sum();
//		}
//		int rval = random.nextInt(trashThingsSum);
//		ItemStack thing = ItemStack.EMPTY;
//		for (TrashThing entry: trashThings) {
//			rval -= entry.weight;
//			thing = entry.stack;
//			if (rval < 0) break;
//		}
//		return thing;
//	}
//
//	public static List<ItemStack> fruitStacks = new ArrayList<>();
//	public static IntSet fruitOreDicts;
//	public static boolean isFruit(ItemStack stack) {
//		for (int id: OreDictionary.getOreIDs(stack)) {
//			if (fruitOreDicts.contains(id)) {
//				return true;
//			}
//		}
//		for (ItemStack fruit: fruitStacks) {
//			if (OreDictionary.itemMatches(fruit, stack, false)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public void init1() {
//		// Trash
//		{
//			int meta = 0;
//			int weight = 0;
//			for (int i=0; i<trash_things_arr.length; ++i) {
//				String[] trash_wi = trash_things_arr[i].split("\\|");
//				try {
//					weight = Integer.parseInt(trash_wi[0]);
//					if (weight < 0) {
//						throw new IllegalArgumentException("Weight must not be negative");
//					}
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				}
//				String[] loc_meta = trash_wi[1].split(";");
//				if (loc_meta.length > 1) {
//					try {
//						meta = Integer.parseInt(loc_meta[1]);
//					} catch (NumberFormatException e) {
//						e.printStackTrace();
//					}
//				}
//				Item it = Item.REGISTRY.getObject(new ResourceLocation(loc_meta[0]));
//				if (it != null && weight > 0) {
//					trashThings.add(new TrashThing(weight, new ItemStack(it, 1, meta)));
//				}
//			}
//		}
//
//		// Fruit salad
//		{
//			int meta = 0;
//			for (int i=0; i<fruitStacksArr.length; ++i) {
//				String[] loc_meta = fruitStacksArr[i].split(";");
//				if (loc_meta.length > 1) {
//					try {
//						meta = Integer.parseInt(loc_meta[1]);
//					} catch (NumberFormatException e) {
//						e.printStackTrace();
//					}
//				}
//				Item it = Item.REGISTRY.getObject(new ResourceLocation(loc_meta[0]));
//				if (it != null) {
//					fruitStacks.add(new ItemStack(it, 1, meta));
//				}
//			}
//		}
//
//		// Centrifuge
//		for (String blacklistEntry: centrifugeBlacklist) {
//			String[] separateInOut = blacklistEntry.split(":");
//			blacklistedForCentrifuge.add(Pair.of(separateInOut[0], new HashSet<>(Arrays.asList(separateInOut[1].split(";")))));
//		}
//
//		for (String traitLoadEntry: forceLoadTraits) {
//			try {
//				Class.forName(traitLoadEntry);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private static final MethodHandle injectHandle;
//	static {
//		try {
//			Method temp = LanguageMap.class.getDeclaredMethod("inject", LanguageMap.class, InputStream.class);
//			temp.setAccessible(true);
//			injectHandle = MethodHandles.lookup().unreflect(temp);
//		} catch (Throwable e) {
//			Throwables.throwIfUnchecked(e);
//			throw new RuntimeException(e);
//		}
//	}
//
//	private static final LanguageMap englishMap = new LanguageMap();
//	static {
//		try {
//			final String[] langFiles = new String[] { "/assets/plustic/lang/en_us.lang", "/assets/plustic/lang/en_US.lang" };
//			for (String langFile: langFiles) {
//				try (InputStream inS = Config.class.getResourceAsStream(langFile)) {
//					injectHandle.invokeExact(englishMap, inS);
//				}
//			}
//		} catch (Throwable e) {
//			Throwables.throwIfUnchecked(e);
//			throw new RuntimeException(e);
//		}
//	}
//
//	public void init2(Map<String, Material> materials) {
//		this.addCustomCategoryComment("materials", "Materials will only appear here when their respective modules are loaded.");
//
//		for (final Iterator<Material> it = materials.values().iterator(); it.hasNext(); ) {
//			final Material mat = it.next();
//			final String matName = englishMap.translateKey(String.format(Material.LOC_Name, mat.getIdentifier()));
//			if (!this.getBoolean(String.format("Enable material %s", mat.getIdentifier()),
//					"materials", true, String.format("Set to false to prevent %s from being loaded", matName))) {
//				it.remove(); // delete the disabled material
//			}
//		}
//	}
//
//	public static boolean isCentrifugeRecipeValid(AlloyRecipe recipe) {
//		Pair<String, Set<String>> pairToCheck = Pair.of(
//				FluidRegistry.getFluidName(recipe.getResult()),
//				recipe.getFluids().stream()
//				.map(FluidRegistry::getFluidName)
//				.collect(Collectors.toSet()));
//		return !blacklistedForCentrifuge.contains(pairToCheck);
//	}
}
