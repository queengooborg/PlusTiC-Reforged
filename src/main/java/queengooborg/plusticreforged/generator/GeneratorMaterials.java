package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import java.util.Objects;

public class GeneratorMaterials extends AbstractMaterialDataProvider {
	public GeneratorMaterials(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void addMaterials() {
		for (Material material : Resources.MATERIALS) {
			if (Objects.equals(material.type[0], "metal")) {
				addCompatMetalMaterial(material.resourceLocation, material.tier, material.order, material.color.base.getRGB());
			} else {
				addMaterial(material.resourceLocation, material.tier, material.order, false, material.color.base.getRGB());
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Materials";
	}
}
