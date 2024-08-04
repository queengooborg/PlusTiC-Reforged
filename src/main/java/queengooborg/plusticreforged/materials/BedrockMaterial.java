package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.MaterialColors;
import queengooborg.plusticreforged.api.MaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

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
			new String[]{"rock"},
			ORDER_HARVEST + ORDER_COMPAT,
			new MaterialColors(new Color(60, 60, 60)),
			new MaterialStats(new HeadMaterialStats(999999, 10, 9999, 99), new HandleMaterialStats(999999, 10, 10, 99), ExtraMaterialStats.DEFAULT),
			Resources.getModifier("heavy_metal"),
			1000
		);
	}
}
