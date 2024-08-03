package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;
import queengooborg.plusticreforged.materials._Materials;
import slimeknights.tconstruct.common.TinkerTags;

public class GeneratorFluidTags extends FluidTagsProvider {
	public GeneratorFluidTags(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ModInfo.MOD_ID, existingFileHelper);
	}

	public void addTags() {
		for (Material material : _Materials.materials) {
			tag(material.moltenFluid.getLocalTag()).add(material.moltenFluid.getFluid());
			tag(material.moltenFluid.getForgeTag()).add(material.moltenFluid.getFluid());
			tag(TinkerTags.Fluids.METAL_LIKE).addTag(material.moltenFluid.getForgeTag());
		}
	}
}
