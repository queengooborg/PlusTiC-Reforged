package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import java.util.function.Consumer;

public class GeneratorRecipes extends RecipeProvider implements IConditionBuilder, IMaterialRecipeHelper, IToolRecipeHelper, ISmelteryRecipeHelper, ICommonRecipeHelper {
	private static final Logger log = LogManager.getLogger(GeneratorRecipes.class);

	public GeneratorRecipes(DataGenerator gen) {
		super(gen);
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
//				metalTagCasting(consumer, material.moltenFluid.FLUID_OBJECT, material.id, castingFolder, false);
				metalMelting(consumer, material.moltenFluid.getFluid(), material.id, false, meltingFolder, true);
			} else {
				MeltingRecipeBuilder.melting(ItemNameIngredient.from(material.item), material.moltenFluid.getFluid(), FluidValues.INGOT, 1.0f).build(consumer, modResource(meltingFolder + material.id));
//				tagCasting(consumer, material.moltenFluid.FLUID_OBJECT, FluidValues.INGOT, material.id, castingFolder, false);
			}

			materialMeltingCasting(consumer, material.item, material.moltenFluid.FLUID_OBJECT, materialFolder);
		}
	}
}
