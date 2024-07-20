package queengooborg.plustic.crafttweaker;

import crafttweaker.IAction;
import queengooborg.plustic.tools.stats.LaserMediumMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetLaserRangeAction implements IAction {
	private final Material mat;
	private final float range;
	
	public SetLaserRangeAction(Material mat, float range) {
		this.mat = mat;
		this.range = range;
	}
	
	@Override
	public void apply() {
		LaserMediumMaterialStats oldStats = mat.getStatsOrUnknown(LaserMediumMaterialStats.TYPE);
		LaserMediumMaterialStats newStats = new LaserMediumMaterialStats(oldStats.power, this.range);
		mat.addStats(newStats);
	}
	
	@Override
	public String describe() {
		return "Setting laser range of " + mat.getLocalizedName() + " to " + range;
	}
}
