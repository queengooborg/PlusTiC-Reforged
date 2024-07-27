package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import landmaster.plustic.*;
import landmaster.plustic.api.*;
import landmaster.plustic.config.*;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.tools.stats.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.*;

import java.util.Map;

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

