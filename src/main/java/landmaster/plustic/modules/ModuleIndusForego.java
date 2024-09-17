package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import java.util.concurrent.*;

import com.buuz135.industrial.entity.EntityPinkSlime;
import landmaster.plustic.*;
import landmaster.plustic.api.*;
import landmaster.plustic.config.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.traits.TraitSlimey;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleIndusForego implements IModule {
	private static final CompletableFuture<?> regFut = new CompletableFuture<>();

	public static final TraitSlimey slimey_pink = new TraitSlimey("pink", EntityPinkSlime.class);
	
	@Override
	public void init() {
		if (Config.industrialForegoing && (Loader.isModLoaded("industrialforegoing"))) {
			Material pink_slime_mat = new Material("pink_slime", 0xFF84AD);
			
			PlusTiC.materials.put("pink_slime", pink_slime_mat);
			
			final CompletableFuture<?> mfrFut = regFut.thenRun(() -> {
				final Item pink_slime = Item.REGISTRY.getObject(new ResourceLocation("industrialforegoing:pink_slime"));
				
				pink_slime_mat.addTrait(slimey_pink);
				pink_slime_mat.addItem(pink_slime, 1, Material.VALUE_Ingot);
				pink_slime_mat.setCraftable(true);
				pink_slime_mat.setRepresentativeItem(pink_slime);
				PlusTiC.proxy.setRenderInfo(pink_slime_mat, 0xFF84AD);
				
				TinkerRegistry.addMaterialStats(pink_slime_mat,
						new HeadMaterialStats(1800, 3.77f, 1.80f, STONE),
						new HandleMaterialStats(2.7f, -729),
						new ExtraMaterialStats(243),
						new BowMaterialStats(1.2f, 0.8f, 0));
			});
			
			PlusTiC.materialIntegrationStages.put("pink_slime", mfrFut);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onItemReg(RegistryEvent.Register<Item> event) {
		regFut.complete(null);
	}
}
