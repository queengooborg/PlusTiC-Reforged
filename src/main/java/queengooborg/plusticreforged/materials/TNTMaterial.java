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

public class TNTMaterial extends Material {
	public TNTMaterial() {
		super(
				"tnt",
				"TNT",
				new Description("A band once wrote a song about it, but I don't remember the lyrics...", ""),
				new ItemOrTag("minecraft", "tnt", false),
				null,
				4,
				MaterialType.STONE,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(255, 79, 79)),
				new MaterialStats(HeadMaterialStats.DEFAULT, HandleMaterialStats.DEFAULT, ExtraMaterialStats.DEFAULT),
				Resources.getModifier("explosive"),
				300
		);
	}
}
