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

public class TanzaniteMaterial extends Material {
	public TanzaniteMaterial() {
		super(
				"tanzanite",
				"Tanzanite",
				new Description("Also known as blue zoisite, this gemstone is said to help with spiritual awareness and psychic insight."),
				new ItemOrTag("forge", "gems/tanzanite", true),
				null,
				2,
				MaterialType.GEM,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(98, 0, 255)),
				new MaterialStats(new HeadMaterialStats(650, 3, HarvestLevels.NETHERITE, 7), new HandleMaterialStats(0.7f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				new Modifier[]{}, // Resources.getModifier("freezing"), // XXX Comes from an older version of Tinkers' Construct
				750
		);
	}
}
