package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Item;
import queengooborg.plusticreforged.api.ItemTag;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialType;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.mantle.recipe.ItemOutput;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
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
		String castingDir = "smeltery/casting/";
		String meltingDir = "smeltery/melting/";
//		String alloyDir = "smeltery/alloys/";
		String materialDir = "tools/materials/";

		// Generate the recipes
		for (Material material : Resources.MATERIALS) {
			log.info("Adding recipes for material {}", material.id);

			Consumer<IFinishedRecipe> wrappedConsumer = material.condition == null ? consumer : withCondition(consumer, material.condition);

			if (material.ingredient instanceof Item) {
				Item ingredient = (Item) material.ingredient;
				boolean isTag = ingredient instanceof ItemTag;

				ITag<net.minecraft.item.Item> tag = getTag(ingredient.location.getNamespace(), ingredient.location.getPath());

				ItemOutput output = isTag ? ItemNameOutput.fromTag(tag, 1) : ItemNameOutput.fromName(ingredient.location);
				Ingredient input = isTag ? Ingredient.of(tag) : ItemNameIngredient.from(ingredient.location);

				if (material.type == MaterialType.METAL) {
					// Metals are pretty straightforward
					metalMelting(wrappedConsumer, material.moltenFluid.getFluid(), material.id, false, meltingDir + "metal/", true);
					metalTagCasting(wrappedConsumer, material.moltenFluid.FLUID_OBJECT, material.id, castingDir + "metal/", false);
				} else {
					// Other materials are a bit more complex
					int fluidValue = FluidValues.INGOT;

					if (material.type == MaterialType.STONE || material.type == MaterialType.WOOD) {
						fluidValue = FluidValues.METAL_BRICK;
						ItemCastingRecipeBuilder.basinRecipe(output).setFluidAndTime(material.moltenFluid.FLUID_OBJECT, true, fluidValue).build(wrappedConsumer, this.modResource(castingDir + material.id));
					} else if (material.type == MaterialType.GEM || material.type == MaterialType.POWDER) {
						fluidValue = FluidValues.GEM;
						castingWithCast(wrappedConsumer, material.moltenFluid.FLUID_OBJECT, fluidValue, TinkerSmeltery.gemCast, output, castingDir + (material.type == MaterialType.GEM ? "gem/" : "powder/") + material.id);

						// XXX Create casting recipe for gem blocks

						//    ItemCastingRecipeBuilder.basinRecipe(Blocks.EMERALD_BLOCK)
						//                            .setFluidAndTime(TinkerFluids.moltenEmerald, false, FluidValues.GEM_BLOCK)
						//                            .build(consumer, modResource(folder + "emerald/block"));
						//					ResourceLocation gemBlock = new ResourceLocation(material.item.getNamespace(), material.item.getPath() + "_block");
						// // XXX Make sure the gem block exists

						// From Tinkers' Construct:
						// ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(gemBlock)).setFluidAndTime(material.moltenFluid.FLUID_OBJECT, true, fluidValue).build(wrappedConsumer, this.modResource(castingDir + material.id));
					} else {
						// We should never reach this point yet
						log.warn("Unhandled material " + material.id + " of type: " + material.type);
					}

					MeltingRecipeBuilder.melting(input, material.moltenFluid.getFluid(), fluidValue, 1.0f).build(wrappedConsumer, modResource(meltingDir + material.id));
					materialMeltingCasting(wrappedConsumer, material.resourceLocation, material.moltenFluid.FLUID_OBJECT, false, FluidValues.INGOT * 2, materialDir);
				}
			}
		}
	}
}
