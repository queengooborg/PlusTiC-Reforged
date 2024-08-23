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

public class PeridotMaterial extends Material {
	public PeridotMaterial() {
		super(
			"peridot",
			"Peridot",
			new Description("A green gemstone that vibrates with the energy of sunshine.", ""),
			new ItemOrTag("forge", "gems/peridot", true),
			null,
			2,
			MaterialType.GEM,
			ORDER_HARVEST + ORDER_COMPAT,
			new MaterialColors(new Color(1, 200, 1)),
			new MaterialStats(new HeadMaterialStats(640, 4, HarvestLevels.NETHERITE, 6.1f), new HandleMaterialStats(-30, 4, 5, 1.3f), ExtraMaterialStats.DEFAULT),
			Resources.getModifier("naturesblessing"),
			750
		);
	}
}
