package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.materials._Materials;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class GeneratorMaterialTraits extends AbstractMaterialTraitDataProvider {
	public GeneratorMaterialTraits(DataGenerator gen, AbstractMaterialDataProvider materials) {
		super(gen, materials);
	}

	@Override
	protected void addMaterialTraits() {
		for (Material material : _Materials.materials) {
			for (RegistryObject<Modifier> trait : material.traits) {
				addDefaultTraits(material.resourceLocation, trait.get());
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Traits";
	}
}
