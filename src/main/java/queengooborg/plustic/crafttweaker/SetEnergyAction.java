package queengooborg.plustic.crafttweaker;

import crafttweaker.IAction;
import queengooborg.plustic.tools.stats.BatteryCellMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetEnergyAction implements IAction {
	private final Material mat;
	private final int energy;

	public SetEnergyAction(Material mat, int energy) {
		this.mat = mat;
		this.energy = energy;
	}

	@Override
	public void apply() {
		//BatteryCellMaterialStats oldStats = mat.getStatsOrUnknown(BatteryCellMaterialStats.TYPE);
		BatteryCellMaterialStats newStats = new BatteryCellMaterialStats(this.energy);
		mat.addStats(newStats);
	}

	@Override
	public String describe() {
		return "Setting battery cell energy capacity of " + mat.getLocalizedName() + " to " + energy;
	}

}
