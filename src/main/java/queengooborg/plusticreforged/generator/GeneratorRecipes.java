package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.tools.data.material.MaterialRecipeProvider;

import java.util.function.Consumer;

public class GeneratorRecipes extends MaterialRecipeProvider implements IConditionBuilder, IMaterialRecipeHelper, IToolRecipeHelper, ISmelteryRecipeHelper, ICommonRecipeHelper {
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

		// Generate the recipes
		for (Material material : Resources.MATERIALS) {
			if (material.type[0].equals("metal")) {
				metalMelting(consumer, material.moltenFluid.getFluid(), material.id, false, meltingFolder, true);
				metalTagCasting(consumer, material.moltenFluid.FLUID_OBJECT, material.id, castingFolder, false);
			} else {
				MeltingRecipeBuilder.melting(ItemNameIngredient.from(material.item), material.moltenFluid.getFluid(), FluidValues.METAL_BRICK, 1.0f).build(consumer, modResource(meltingFolder + material.id));
				materialMeltingCasting(consumer, material.resourceLocation, material.moltenFluid.FLUID_OBJECT, false, FluidValues.INGOT * 2, materialFolder + material.id);
				ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(material.item)).setFluidAndTime(material.moltenFluid.FLUID_OBJECT, true, FluidValues.METAL_BRICK).build(consumer, this.modResource(castingFolder + material.id));
			}
		}
	}
}
