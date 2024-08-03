package queengooborg.plusticreforged;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import queengooborg.plusticreforged.config.ModInfo;
import queengooborg.plusticreforged.generator.*;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModInfo.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlusTiCReforged {
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public PlusTiCReforged() {
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
		// some preinit code
//		LOGGER.info("HELLO FROM PREINIT");
//		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
//        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// some example code to dispatch IMC to another mod
//        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
	}

	private void processIMC(final InterModProcessEvent event) {
		// some example code to receive and process InterModComms from other mods
//		LOGGER.info("Got IMC {}", event.getIMCStream().
//				map(m -> m.getMessageSupplier().get()).
//				collect(Collectors.toList()));
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		// do something when the server starts
//		LOGGER.info("HELLO from server starting");
	}

	// Gather data to be generated
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();

		if (event.includeClient()) {
			// Generate client-side data

			// Generate language file
			gen.addProvider(new GeneratorLang(gen));

			// Generate models and textures
			GeneratorMaterialTextures materialSprites = new GeneratorMaterialTextures();
			gen.addProvider(new GeneratorRenderInfo(gen, materialSprites));
			// Generate Tinkers' parts with our materials
			// XXX The following causes: Caused by: java.lang.IllegalStateException: Missing sprite at tconstruct:item/tool/parts/large_plate.png, cannot generate textures
//			gen.addProvider(new MaterialPartTextureGenerator(gen, event.getExistingFileHelper(), new TinkerPartSpriteProvider(), materialSprites));
		}

		if (event.includeServer()) {
			// Generate server-side data

			// Generate material information
			AbstractMaterialDataProvider materialData = new GeneratorMaterials(gen);
			gen.addProvider(materialData);
			gen.addProvider(new GeneratorMaterialStats(gen, materialData));
			gen.addProvider(new GeneratorMaterialTraits(gen, materialData));

			// Generate tags
			gen.addProvider(new GeneratorFluidTags(gen, event.getExistingFileHelper()));
		}
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
}
