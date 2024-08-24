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

public class PhoenixiteMaterial extends Material {
	public PhoenixiteMaterial() {
		super(
				"phoenixite",
				"Phoenixite",
				new Description("A fiery gemstone that is said to be reborn from its ashes."),
				new ItemOrTag("forge", "gems/phoenixite", true),
				null,
				2,
				MaterialType.GEM,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(255, 69, 17)),
				new MaterialStats(new HeadMaterialStats(1300, 9, HarvestLevels.NETHERITE, 5), new HandleMaterialStats(1.4f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("getlucky"),
				750
		);
	}
}
