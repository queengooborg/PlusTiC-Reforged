package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.materials._Materials;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class GeneratorMaterialTraits extends AbstractMaterialTraitDataProvider {
	public GeneratorMaterialTraits(DataGenerator gen, AbstractMaterialDataProvider materialData) {
		super(gen, materialData);
	}

	@Override
	protected void addMaterialTraits() {
		for (Material material : _Materials.materials) {
			if (material.traits == null) {
				noTraits(material.resourceLocation);
			} else {
				addDefaultTraits(material.resourceLocation, material.traits);
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Traits";
	}
}
