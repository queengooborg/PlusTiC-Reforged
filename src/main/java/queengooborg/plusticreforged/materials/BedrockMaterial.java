package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Fluid;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialColors;
import slimeknights.tconstruct.fluids.TinkerFluids;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class BedrockMaterial extends Material {
	public BedrockMaterial() {
		super(
			"bedrock",
			"Bedrock",
			"minecraft:bedrock",
			new Description("-10% attack damage, +15% attack speed per level"),
			2,
			new String[]{"misc"},
			ORDER_HARVEST + ORDER_COMPAT,
			new MaterialColors(0xE6B7BF),
			null,
			Resources.getModifier("heavy_metal"),
			new Fluid("molten_bedrock", "Molten Bedrock", 1600, 15, 3000, 6000)
		);
	}
}
