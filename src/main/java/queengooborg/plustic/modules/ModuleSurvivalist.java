package queengooborg.plustic.modules;

import queengooborg.plustic.config.*;

public class ModuleSurvivalist implements IModule {
	public void init() {
		if (Config.survivalist && Loader.isModLoaded("survivalist")) {
			// add melting recipes
			TinkerRegistry.registerMelting("rockOreIron", TinkerFluids.iron, Material.VALUE_Nugget * 2);
			TinkerRegistry.registerMelting("rockOreGold", TinkerFluids.gold, Material.VALUE_Nugget * 2);
			TinkerRegistry.registerMelting("rockOreCopper", TinkerFluids.copper, Material.VALUE_Nugget * 2);
			TinkerRegistry.registerMelting("rockOreTin", TinkerFluids.tin, Material.VALUE_Nugget * 2);
			TinkerRegistry.registerMelting("rockOreLead", TinkerFluids.lead, Material.VALUE_Nugget * 2);
			TinkerRegistry.registerMelting("rockOreSilver", TinkerFluids.silver, Material.VALUE_Nugget * 2);
		}
	}
}
