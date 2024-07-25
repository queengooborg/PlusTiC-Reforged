package queengooborg.plusticreforged.api;

import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.modifiers.Modifier;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class Material {
	public String id;
	public String name;
	public String item;
	public Description description;
	public int tier = 1;
	public String[] type = {"metal"};
	public int order = ORDER_HARVEST + ORDER_COMPAT;
	public MaterialColors color;
	public MaterialStats stats;
	public Modifier[] traits;
	public FluidObject<?> moltenFluid;

	public Material(String id, String name, String item, Description description, int tier, String type, int order, MaterialColors color, MaterialStats stats, Modifier[] traits, FluidObject<?> moltenFluid) {
		this(id, name, item, description, tier, new String[]{type}, order, color, stats, traits, moltenFluid);
	}

	public Material(String id, String name, String item, Description description, int tier, String[] type, int order, MaterialColors color, MaterialStats stats, Modifier[] traits, FluidObject<?> moltenFluid) {
		this.id = id;
		this.name = name;
		this.item = item;
		this.description = description;
		this.tier = tier;
		this.order = order;
		this.type = type;
		this.color = color;
		this.stats = stats;
		this.traits = traits;
		this.moltenFluid = moltenFluid;
	}
}

// // Example: Iron
// public class extends AbstractMaterialDataProvider: addMaterial(MaterialIds.iron,        2, ORDER_GENERAL, false, 0xD8D8D8);
// public class extends AbstractMaterialRenderInfoProvider: buildRenderInfo(MaterialIds.iron).color(0xD8D8D8).fallbacks("metal");
// public class extends AbstractMaterialSpriteProvider: buildMaterial(MaterialIds.iron)
//      .meleeHarvest()
//      .statType(TinkerPartSpriteProvider.PLATE)
//      .fallbacks("metal")
//      .colorMapper(
//          GreyToColorMapping.builderFromBlack()
//          .addARGB(63, 0xFF353535)
//          .addARGB(102, 0xFF5E5E5E)
//          .addARGB(140, 0xFF828282)
//          .addARGB(178, 0xFFA8A8A8)
//          .addARGB(216, 0xFFD8D8D8)
//          .addARGB(255, 0xFFFFFFFF)
//          .build()
//      );
// public class extends AbstractMaterialTraitDataProvider: addDefaultTraits(MaterialIds.iron, TinkerModifiers.sturdy.get());
// public class extends AbstractMaterialStatsDataProvider: addMaterialStats(MaterialIds.iron,
//                     new HeadMaterialStats(250, 6f, IRON, 2f),
//                     HandleMaterialStats.DEFAULT.withDurability(1.10f),
//                     ExtraMaterialStats.DEFAULT);
// public class extends BaseRecipeProvider implements IMaterialRecipeHelper: metalMaterialRecipe(consumer, MaterialIds.iron, folder, "iron", false);
// public class extends BaseRecipeProvider implements IMaterialRecipeHelper: materialMeltingCasting(consumer, MaterialIds.iron, TinkerFluids.moltenIron, true,folder);
// public class extends LanguageProvider: addMaterial(MaterialIds.iron, "Iron", "flavor", "description");

// // Example: Seared Bricks
// public class extends BaseRecipeProvider implements IMaterialRecipeHelper: materialRecipe(consumer, MaterialIds.searedStone, Ingredient.fromItems(TinkerSmeltery.searedBrick), 1, 2, folder + "seared_stone/brick");
// public class extends BaseRecipeProvider implements IMaterialRecipeHelper: materialMeltingCasting(consumer, MaterialIds.searedStone, TinkerFluids.searedStone, false,FluidValues.INGOT * 2, folder);
