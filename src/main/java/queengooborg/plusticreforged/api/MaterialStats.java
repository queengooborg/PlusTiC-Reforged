package queengooborg.plusticreforged.api;

import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class MaterialStats {
	HeadMaterialStats head = HeadMaterialStats.DEFAULT;
	HandleMaterialStats handle = HandleMaterialStats.DEFAULT;
	ExtraMaterialStats extra = ExtraMaterialStats.DEFAULT;

	public MaterialStats() {}

	public MaterialStats(HeadMaterialStats head, HandleMaterialStats handle, ExtraMaterialStats extra) {
		this.head = head;
		this.handle = handle;
		this.extra = extra;
	}
}