package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.api.ModInfo;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.util.Utils;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID, value = Side.CLIENT)
public class ModuleFuture implements IModule {

	public void init() {
		final boolean isFutureLoaded = Loader.isModLoaded("futuremc");

		if (Config.future && isFutureLoaded) {
			Material netherite = new Material("netherite", TextFormatting.DARK_GRAY);
			Utils.setDispItem(netherite, "ingotNetherite");
			PlusTiC.proxy.setRenderInfo(netherite, 0x4C4143);

			FluidMolten netheriteFluid = Utils.fluidMetal("netherite", 0x8C8183);
			netheriteFluid.setTemperature(900);
			Utils.initFluidMetal(netheriteFluid);
			netherite.setFluid(netheriteFluid);

			netherite.addTrait(dense);
			netherite.addTrait(duritos);
			netherite.addItem("ingotNetherite", 1, Material.VALUE_Ingot);
			netherite.setCraftable(false).setCastable(true);

			TinkerRegistry.addMaterialStats(netherite,
					new HeadMaterialStats(2800, 8, 12, COBALT),
					new HandleMaterialStats(1.6f, 100),
					new ExtraMaterialStats(180),
					PlusTiC.justWhy,
					new LaserMediumMaterialStats(7.0f, 55));

			PlusTiC.materials.put("netherite", netherite);
		}
	}
}

