package queengooborg.plusticreforged.materials;

import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.library.modifiers.Modifier;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class RubyMaterial extends Material {
	public RubyMaterial() {
		super(
				"ruby",
				"Ruby",
				new Description("A red gemstone that is said to bring good luck to its owner."),
				new ItemOrTag("forge", "gems/ruby", true),
				null,
				2,
				MaterialType.GEM,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(200, 1, 1)),
				new MaterialStats(new HeadMaterialStats(660, 4.6f, HarvestLevels.NETHERITE, 6.4f), new HandleMaterialStats(100, 4.6f, 5, 6.4f), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("bloodymary"), // new Modifier[]{Resources.getModifier("bloodymary"), (Modifier) TinkerModifiers.sharpness.get()},
				750
		);
	}
}
