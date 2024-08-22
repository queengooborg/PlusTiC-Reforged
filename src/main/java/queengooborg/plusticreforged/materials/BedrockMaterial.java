package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class BedrockMaterial extends Material {
	public BedrockMaterial() {
		super(
				"bedrock",
				"Bedrock",
				new Description("You could only obtain these tools through Creative or cheats -- so if you're gonna cheat, you might as well commit, right?", "The tool takes no damage"),
				new ItemOrTag("minecraft", "bedrock", false),
				null,
				2,
				MaterialType.STONE,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(60, 60, 60)),
				new MaterialStats(new HeadMaterialStats(99999999, 10, HarvestLevels.NETHERITE, 99), new HandleMaterialStats(9999999, 10, 10, 99), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("invulnerable"),
				1000
		);
	}
}
