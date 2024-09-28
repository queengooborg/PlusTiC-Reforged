package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.api.ModInfo;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.traits.Anticorrosion;
import landmaster.plustic.traits.BrownMagic;
import landmaster.plustic.traits.Light;
import landmaster.plustic.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleGalacticraft implements IModule {
	@GameRegistry.ObjectHolder(value = "galacticraftplanets:item_basic_mars")
	public static final Item item_basic_mars = null;

	@ObjectHolder(value = "galacticraftplanets:item_basic_asteroids")
	public static final Item item_basic_asteroids = null;

	private static Material titanium;
	private static Material desh;

	public void init() {
		if (Config.galacticraft && Loader.isModLoaded("galacticraftcore")) {
			if (Loader.isModLoaded("galacticraftplanets")) {

				desh = new Material("desh", 0x161616);
				titanium = new Material("titanium", TextFormatting.WHITE);

				desh.addTrait(alien);
				desh.addTrait(enderference);
				desh.addTrait(BrownMagic.brownmagic);

				titanium.addTrait(Light.light);
				titanium.addTrait(Anticorrosion.anticorrosion, HEAD);

				desh.setCraftable(false).setCastable(true);
				titanium.setCraftable(false).setCastable(true);

				desh.addItem("ingotDesh", 1, Material.VALUE_Ingot);
				titanium.addItem("ingotTitanium", 1, Material.VALUE_Ingot);

				PlusTiC.proxy.setRenderInfo(desh, 0x161616);
				PlusTiC.proxy.setRenderInfo(titanium, 0xDCE1EA);

				FluidMolten deshFluid = Utils.fluidMetal("desh", 0x161616);
				FluidMolten titaniumFluid = Utils.fluidMetal("titanium", 0xDCE1EA);

				deshFluid.setTemperature(821); // {821,823,827,829} are quadruplet primes
				titaniumFluid.setTemperature(790);

				Utils.initFluidMetal(deshFluid);
				Utils.initFluidMetal(titaniumFluid);

				desh.setFluid(deshFluid);
				titanium.setFluid(titaniumFluid);


				TinkerRegistry.addMaterialStats(desh,
						new HeadMaterialStats(1729/* 1729 is a taxicab number */, (float) (Math.E * Math.PI), 8, COBALT),
						new HandleMaterialStats((float) (Math.sqrt(5) + 1) / 2/* the golden ratio */, 0),
						new ExtraMaterialStats(257),
						new BowMaterialStats(1 / (float) Math.E, (float) Math.E, 12));

				TinkerRegistry.addMaterialStats(titanium,
						new HeadMaterialStats(560, 6, 6, OBSIDIAN),
						new HandleMaterialStats(1.4f, 0),
						new ExtraMaterialStats(40),
						new BowMaterialStats(1.15f, 1.3f, 6.6f),
						new FletchingMaterialStats(1.0f, 1.3f));

				PlusTiC.materials.put("desh", desh);
				PlusTiC.materials.put("titanium", titanium);
			}
		}
	}

	public void init2() {
		if (Config.galacticraft && Loader.isModLoaded("galacticraftcore")) {
			if (Loader.isModLoaded("galacticraftplanets")) {
				TinkerRegistry.registerMelting(new ItemStack(item_basic_mars), FluidRegistry.getFluid("desh"), Material.VALUE_Ingot);
				OreDictionary.registerOre("ingotDesh", new ItemStack(item_basic_mars, 1, 2)); // see init3
				TinkerRegistry.registerMelting(new ItemStack(item_basic_asteroids), FluidRegistry.getFluid("titanium"), Material.VALUE_Ingot);
				OreDictionary.registerOre("ingotTitanuim", new ItemStack(item_basic_asteroids, 1, 0)); // see init3
			}
		}
	}

	public void init3() {
		if (Config.galacticraft && Loader.isModLoaded("galacticraftcore")) {
			if (Loader.isModLoaded("galacticraftplanets")) {
				// Seriously? Registering oredicts *this* late? -_-
				Utils.setDispItem(desh, "ingotDesh");
				Utils.setDispItem(titanium, "ingotTitanium");
			}
		}
	}
}
