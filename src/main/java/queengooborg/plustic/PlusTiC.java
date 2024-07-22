package queengooborg.plustic;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.*;

import queengooborg.plustic.config.ModInfo;
import queengooborg.plustic.config.*;
import queengooborg.plustic.gui.handler.PTGuiHandler;
import queengooborg.plustic.modules.*;
import queengooborg.plustic.net.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import queengooborg.plustic.proxy.CommonProxy;
import slimeknights.tconstruct.library.materials.definition.Material;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModInfo.MODID)
public class PlusTiC {
	public static PlusTiC instance;

	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public static Config config;

	@SidedProxy(serverSide = "queengooborg.plustic.proxy.CommonProxy", clientSide = "queengooborg.plustic.proxy.ClientProxy")
	public static CommonProxy proxy;

	public static final Map<String, Material> materials = new LinkedHashMap<>();
	public static final Map<String, MaterialIntegration> materialIntegrations = new Object2ObjectOpenHashMap<>();

	public static final Map<String, CompletionStage<?>> materialIntegrationStages = new Object2ObjectOpenHashMap<>();

	public static final Map<String, String> materialOreDicts = new Object2ObjectOpenHashMap<>();

	public static final BowMaterialStats justWhy = new BowMaterialStats(0.2f, 0.4f, -1f);

	private static final String[] renamesToHandle = new String[] {
			"osmium", "titanium", "iridium", "enderium", "lumium", "platinum", "signalum", "invar" };

	public PlusTiC() {
		instance = this;

		Config.init();

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {

		proxy.initEntities();

		IModule.modules.addAll(Arrays.asList(
				new ModuleBase(),

				new ModuleGems(),
				new ModuleMekanism(),
				new ModuleBotania(),
				new ModuleArmorPlus(),
				new ModuleTF(),
				new ModuleDraconicEvolution(),
				new ModuleActAdd(),
				new ModulePsi(),
				new ModuleAvaritia(),
				new ModuleSurvivalist(),
				new ModuleProjectE(),
				new ModuleGemsPlus(),
				new ModuleAppEng2(),
				new ModuleIndusForego(),
				new ModuleAstralSorcery(),

				new ModuleTools(),
				new ModuleModifiers(),

				new ModuleMachines()
		));

		IModule.modules.forEach(IModule::init);

		preIntegrate();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new PTGuiHandler());
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// some example code to dispatch IMC to another mod
//		InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});

		InterModComms.sendTo("waila", "register", queengooborg.plustic.waila.PTWailaRegistrar.wailaCallback);
	}

	private void processIMC(final InterModProcessEvent event) {
		// some example code to receive and process InterModComms from other mods
//		LOGGER.info("Got IMC {}", event.getIMCStream().
//				map(m->m.getMessageSupplier().get()).
//				collect(Collectors.toList()));
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		// do something when the server starts
	}

	// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			// register a new block here
			LOGGER.info("HELLO from Register Block");
		}
	}


	@SubscribeEvent
	public static void missingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
		event.getMappings().forEach(mapping -> {
			for (String name : renamesToHandle) {
				if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_" + name))) {
					Optional.ofNullable(FluidRegistry.getFluid(name))
							.map(Fluid::getBlock)
							.ifPresent(mapping::remap);
				}
			}

			if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_fiery"))) {
				Optional.ofNullable(FluidRegistry.getFluid("fierymetal"))
						.map(Fluid::getBlock)
						.ifPresent(mapping::remap);
			}
		});
	}

	@SubscribeEvent
	public static void missingItemMappings(RegistryEvent.MissingMappings<Item> event) {
		event.getMappings().forEach(mapping -> {
			for (String name : renamesToHandle) {
				if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_" + name))) {
					Optional.ofNullable(FluidRegistry.getFluid(name))
							.map(Fluid::getBlock)
							.map(Item::getItemFromBlock)
							.ifPresent(mapping::remap);
				}

				if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_fiery"))) {
					Optional.ofNullable(FluidRegistry.getFluid("fierymetal"))
							.map(Fluid::getBlock)
							.map(Item::getItemFromBlock)
							.ifPresent(mapping::remap);
				}
			}
		});
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.initToolGuis();
		proxy.registerKeyBindings();
		PacketHandler.init();

		IModule.modules.forEach(IModule::init2);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		IModule.modules.forEach(IModule::init3);
	}

	private static void preIntegrate() {
		materials.forEach((k, v) -> {
			if (!materialIntegrations.containsKey(k)) {
				materialIntegrationStages.getOrDefault(k, CompletableFuture.completedFuture(null)).thenRun(() -> {
					MaterialIntegration mi;
					if (v.getRepresentativeItem().getItem() == Items.EMERALD) {
						mi = new MaterialIntegration(v, v.getFluid()).toolforge();
					} else if (v.getFluid() != null) {
						mi = new MaterialIntegration(v, v.getFluid(), StringUtils.capitalize(k)).toolforge();
					} else {
						mi = new MaterialIntegration(v);
					}
					if (materialOreDicts.containsKey(k)) {
						mi.representativeItem = materialOreDicts.get(k);
					}
					TinkerRegistry.integrate(mi).preInit();
					materialIntegrations.put(k, mi);
				});
			}
		});
	}
}
