package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class GeneratorMaterialTraits extends AbstractMaterialTraitDataProvider {
	public GeneratorMaterialTraits(DataGenerator gen, AbstractMaterialDataProvider materialData) {
		super(gen, materialData);
	}

	@Override
	protected void addMaterialTraits() {
		for (Material material : Resources.MATERIALS) {
			if (material.modifier == null) {
				noTraits(material.resourceLocation);
			} else {
				addDefaultTraits(material.resourceLocation, material.modifier);
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Traits";
	}
}
