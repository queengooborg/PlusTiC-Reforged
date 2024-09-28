package landmaster.plustic.waila;

import landmaster.plustic.tile.TECentrifugeCore;
import landmaster.plustic.tile.TECentrifugeTank;
import mcp.mobius.waila.api.*;
import slimeknights.tconstruct.plugin.waila.*;

public class PTWailaRegistrar {
	public static void wailaCallback(IWailaRegistrar registrar) {
		TankDataProvider tankDataProvider = new TankDataProvider();
		registrar.registerBodyProvider(tankDataProvider, TECentrifugeTank.class);
		registrar.registerBodyProvider(tankDataProvider, TECentrifugeCore.class);
	}
}
