package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_COMPAT;
import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_HARVEST;

public class AmethystMaterial extends Material {
	public AmethystMaterial() {
		super(
				"amethyst",
				"Amethyst",
				new Description("A purple gemstone that vibrates at a high frequency."),
				new ItemOrTag("forge", "gems/amethyst", true),
				null,
				2,
				MaterialType.GEM,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(255, 0, 255)),
				new MaterialStats(new HeadMaterialStats(1100, 6, HarvestLevels.NETHERITE, 8), new HandleMaterialStats(1.5f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("apocalypse"),
				750
		);
	}
}