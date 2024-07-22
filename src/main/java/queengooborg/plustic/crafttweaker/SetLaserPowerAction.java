package queengooborg.plustic.crafttweaker;

import crafttweaker.IAction;
import queengooborg.plustic.tools.stats.LaserMediumMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetLaserPowerAction implements IAction {
	private final Material mat;
	private final float power;

	public SetLaserPowerAction(Material mat, float power) {
		this.mat = mat;
		this.power = power;
	}

	@Override
	public void apply() {
		LaserMediumMaterialStats oldStats = mat.getStatsOrUnknown(LaserMediumMaterialStats.TYPE);
		LaserMediumMaterialStats newStats = new LaserMediumMaterialStats(this.power, oldStats.range);
		mat.addStats(newStats);
	}

	@Override
	public String describe() {
		return "Setting laser power of " + mat.getLocalizedName() + " to " + power;
	}
}
