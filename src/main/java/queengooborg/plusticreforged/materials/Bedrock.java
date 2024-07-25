package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialColors;
import slimeknights.tconstruct.fluids.TinkerFluids;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class Bedrock extends Material {
	public Bedrock() {
		super(
			"bedrock",
			"Bedrock",
			"minecraft:bedrock",
			new Description("-10% attack damage, +15% attack speed per level"),
			2,
			"metal",
			ORDER_HARVEST + ORDER_COMPAT,
			new MaterialColors(0xE6B7BF),
			null,
			null,
			TinkerFluids.moltenAluminum
		);
	}
}