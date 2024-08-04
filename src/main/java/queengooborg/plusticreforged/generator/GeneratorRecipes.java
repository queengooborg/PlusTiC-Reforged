package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.data.material.MaterialRecipeProvider;

import java.util.function.Consumer;

public class GeneratorRecipes extends MaterialRecipeProvider implements IConditionBuilder, IMaterialRecipeHelper, IToolRecipeHelper, ISmelteryRecipeHelper, ICommonRecipeHelper {
	private static final Logger log = LogManager.getLogger(GeneratorRecipes.class);

	public GeneratorRecipes(DataGenerator gen) {
		super(gen);
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Recipes";
	}

	@Override
	public String getModId() {
		return ModInfo.MOD_ID;
	}

	public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		String castingFolder = "smeltery/casting/metal/";
		String meltingFolder = "smeltery/melting/metal/";
//		String alloyFolder = "smeltery/alloys/";
		String materialFolder = "tools/materials/";
//		String compositeFolder = "tools/parts/composite/";
//		String modifierFolder = "tools/modifiers/";
//		String salvageFolder = "tools/modifiers/salvage/";
//		String slotlessFolder = "tools/modifiers/slotless/";
//		String spillFolder = "tools/spilling/";
//		String toolFolder = "tools/building/";
//		String partFolder = "tools/parts/";
//		String castFolder = "smeltery/casts/";
//		String armorFolder = "armor/building/";
//		String armorRepairFolder = "armor/repair/";

		// Generate the recipes
		for (Material material : Resources.MATERIALS) {
			if (material.type[0].equals("metal")) {
				metalMelting(consumer, material.moltenFluid.getFluid(), material.id, false, meltingFolder, true);
				metalTagCasting(consumer, material.moltenFluid.FLUID_OBJECT, material.id, castingFolder, false);
			} else {
				MeltingRecipeBuilder.melting(ItemNameIngredient.from(material.item), material.moltenFluid.getFluid(), FluidValues.METAL_BLOCK, 1.0f).build(consumer, modResource(meltingFolder + material.id));
				ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(material.item)).setFluidAndTime(material.moltenFluid.FLUID_OBJECT, true, FluidValues.METAL_BLOCK).build(consumer, this.modResource(castingFolder + material.id));
//				tagCasting(consumer, material.moltenFluid.FLUID_OBJECT, FluidValues.METAL_BLOCK, TinkerSmeltery.plateCast, "plates/" + material.id, castingFolder + material.id + "/plate", false);
//				tagCasting(consumer, material.moltenFluid.FLUID_OBJECT, true, FluidValues.METAL_BLOCK * 4, TinkerSmeltery.gearCast, "gears/" + material.id, castingFolder + material.id + "/gear", false);
//				tagCasting(consumer, material.moltenFluid.FLUID_OBJECT, true, FluidValues.METAL_BLOCK / 3, TinkerSmeltery.coinCast, "coins/" + material.id, castingFolder + material.id + "/coin", false);
//				tagCasting(consumer, material.moltenFluid.FLUID_OBJECT, true, FluidValues.METAL_BLOCK / 2, TinkerSmeltery.rodCast, "rods/" + material.id, castingFolder + material.id + "/rod", false);
//				tagCasting(consumer, material.moltenFluid.FLUID_OBJECT, true, FluidValues.METAL_BLOCK / 2, TinkerSmeltery.wireCast, "wires/" + material.id, castingFolder + material.id + "/wire", false);
			}
		}
	}
}
