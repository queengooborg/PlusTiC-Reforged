package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Fluid;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialColors;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.awt.Color;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class BedrockMaterial extends Material {
	public BedrockMaterial() {
		super(
			"bedrock",
			"Bedrock",
			new MaterialId("minecraft", "bedrock"),
			new Description("-10% attack damage, +15% attack speed per level"),
			2,
			new String[]{"misc"},
			ORDER_HARVEST + ORDER_COMPAT,
			new MaterialColors(new Color(88, 88, 88)),
			null,
			Resources.getModifier("heavy_metal"),
			1000
		);
	}
}
