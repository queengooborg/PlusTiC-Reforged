package queengooborg.plusticreforged.generator;

import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.materials._Materials;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;

public class GeneratorMaterialTextures extends AbstractMaterialSpriteProvider {
	@Override
	protected void addAllMaterials() {
		for (Material material : _Materials.materials) {
			buildMaterial(material.resourceLocation)
				.meleeHarvest()
				.fallbacks(material.type)
				.colorMapper(GreyToColorMapping
					.builderFromBlack()
					.addARGB(63, material.color.f63)
					.addARGB(102, material.color.f102)
					.addARGB(140, material.color.f140)
					.addARGB(178, material.color.f178)
					.addARGB(216, material.color.f216)
					.addARGB(255, material.color.f255)
					.build()
				);
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Textures";
	}
}
