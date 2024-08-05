package queengooborg.plusticreforged.generator;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;

import java.util.function.Supplier;

public class GeneratorBlockStates extends BlockStateProvider {
	public GeneratorBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ModInfo.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		for (Material material : Resources.MATERIALS) {
			if (material.moltenFluid == null) {
				continue;
			}

			// Register blockstates for fluids
			Supplier<FlowingFluidBlock> fluid = material.moltenFluid.FLUID_BLOCK;
			ResourceLocation name = fluid.get().getRegistryName();
			simpleBlock(fluid.get(), models().cubeAll(name.getPath(), new ResourceLocation("tconstruct", "block/fluid/molten/still")));
		}
	}
}