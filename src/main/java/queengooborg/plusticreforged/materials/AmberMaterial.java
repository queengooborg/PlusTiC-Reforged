package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_COMPAT;
import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_HARVEST;

public class AmberMaterial extends Material {
	public AmberMaterial() {
		super(
				"amber",
				"Amber",
				new Description("A golden gemstone that carries the energy of the sun...and sometimes a trapped bug."),
				new ItemOrTag("forge", "gems/amber", true),
				null,
				2,
				MaterialType.GEM,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(255, 208, 0)),
				new MaterialStats(new HeadMaterialStats(730, 4.6f, HarvestLevels.NETHERITE, 5.7f), new HandleMaterialStats(1, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("thundering"), // new Modifier[]{Resources.getModifier("thundering"), Resources.getModifier("shocking")} // "shocking" was originally in Tinker's Construct, but isn't anymore, so we need to reimplement it
				750
		);
	}
}
