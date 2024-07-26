package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.materials._Materials;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import java.util.Objects;

public class GeneratorMaterials extends AbstractMaterialDataProvider {
	public GeneratorMaterials(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void addMaterials() {
		for (Material material : _Materials.materials) {
			if (Objects.equals(material.type[0], "metal")) {
				addCompatMetalMaterial(material.resourceLocation, material.tier, material.order, material.color.base);
			} else {
				addMaterial(material.resourceLocation, material.tier, material.order, true, material.color.base);
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Materials";
	}
}
