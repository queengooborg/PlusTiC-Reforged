package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class SapphireMaterial extends Material {
	public SapphireMaterial() {
		super(
			"sapphire",
			"Sapphire",
			new Description("A blue gemstone that is said to bring good luck to its owner."),
			new ItemOrTag("forge", "gems/sapphire", true),
			null,
			2,
			MaterialType.GEM,
			ORDER_HARVEST + ORDER_COMPAT,
			new MaterialColors(new Color(1, 1, 200)),
			new MaterialStats(new HeadMaterialStats(700, 5, HarvestLevels.NETHERITE, 6.4f), new HandleMaterialStats(100, 5, 5, 6.4f), ExtraMaterialStats.DEFAULT),
			new Modifier[]{}, // (Modifier) TinkerModifiers.aquaaffinity.get(),
			750
		);
	}
}
