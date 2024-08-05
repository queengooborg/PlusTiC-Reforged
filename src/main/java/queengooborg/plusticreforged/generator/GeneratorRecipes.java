package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialType;
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
		String castingDir = "smeltery/casting/metal/";
		String meltingDir = "smeltery/melting/metal/";
//		String alloyDir = "smeltery/alloys/";
		String materialDir = "tools/materials/";

		// Generate the recipes
		for (Material material : Resources.MATERIALS) {
			String namespace = material.item.getNamespace();
			Consumer<IFinishedRecipe> wrappedConsumer = namespace.equals("minecraft") || namespace.equals("tconstruct") ? consumer : withCondition(consumer, modLoaded(material.item.getNamespace()));

			if (material.type == MaterialType.METAL) {
				// Metals are pretty straightforward
				metalMelting(wrappedConsumer, material.moltenFluid.getFluid(), material.id, false, meltingDir, true);
				metalTagCasting(wrappedConsumer, material.moltenFluid.FLUID_OBJECT, material.id, castingDir, false);
			} else {
				// Other materials are a bit more complex
				int fluidValue = FluidValues.INGOT;

				if (material.type == MaterialType.STONE || material.type == MaterialType.WOOD) {
					fluidValue = FluidValues.METAL_BRICK;
					ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(material.item)).setFluidAndTime(material.moltenFluid.FLUID_OBJECT, true, fluidValue).build(wrappedConsumer, this.modResource(castingDir + material.id));
				} else if (material.type == MaterialType.GEM || material.type == MaterialType.POWDER) {
					fluidValue = FluidValues.GEM;
					// XXX Handle casting for gems and powders
				} else {
					// We should never reach this point yet
					log.warn("Unhandled material " + material.id + " of type: " + material.type);
				}
				MeltingRecipeBuilder.melting(ItemNameIngredient.from(material.item), material.moltenFluid.getFluid(), fluidValue, 1.0f).build(wrappedConsumer, modResource(meltingDir + material.id));
				materialMeltingCasting(wrappedConsumer, material.resourceLocation, material.moltenFluid.FLUID_OBJECT, false, FluidValues.INGOT * 2, materialDir);
			}
		}
	}
}
