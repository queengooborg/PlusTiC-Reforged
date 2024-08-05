package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialType;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class GeneratorMaterials extends AbstractMaterialDataProvider {
	public GeneratorMaterials(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void addMaterials() {
		for (Material material : Resources.MATERIALS) {
			addMaterial(material.resourceLocation, material.tier, material.order, false, material.color.base.getRGB(), false, material.condition);
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Materials";
	}
}
