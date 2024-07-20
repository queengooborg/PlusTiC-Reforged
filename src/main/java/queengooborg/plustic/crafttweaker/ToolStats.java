package queengooborg.plustic.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import queengooborg.plustic.tools.stats.BatteryCellMaterialStats;
import queengooborg.plustic.tools.stats.LaserMediumMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.plustic.toolstats")
@ZenRegister
@ModOnly("modtweaker")
public class ToolStats {
	/*
	static IAction action;
	static IBracketHandler bhandler;
	*/
	
	@ZenMethod("energy")
	public static int getEnergy(Material mat) {
		BatteryCellMaterialStats stats = mat.getStatsOrUnknown(BatteryCellMaterialStats.TYPE);
		return stats.energy;
	}
	
	@ZenMethod("energy")
	public static void setEnergy(Material mat, int energy) {
		CraftTweakerAPI.apply(new SetEnergyAction(mat, energy));
	}
	
	@ZenMethod("laserPower")
	public static float getLaserPower(Material mat) {
		LaserMediumMaterialStats stats = mat.getStatsOrUnknown(LaserMediumMaterialStats.TYPE);
		return stats.power;
	}
	
	@ZenMethod("laserPower")
	public static void setLaserPower(Material mat, float power) {
		CraftTweakerAPI.apply(new SetLaserPowerAction(mat, power));
	}
	
	@ZenMethod("laserRange")
	public static float getLaserRange(Material mat) {
		LaserMediumMaterialStats stats = mat.getStatsOrUnknown(LaserMediumMaterialStats.TYPE);
		return stats.range;
	}
	
	@ZenMethod("laserRange")
	public static void setLaserRange(Material mat, float range) {
		CraftTweakerAPI.apply(new SetLaserRangeAction(mat, range));
	}
}
