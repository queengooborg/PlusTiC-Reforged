package queengooborg.plusticreforged;

import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.materials.*;
import queengooborg.plusticreforged.modifiers.*;

public class Resources {
	public static final Modifier[] MODIFIERS = new Modifier[]{
			new HeavyModifier(),
			new InvulnerableModifier(),
			new BloodyMaryModifier(),
			new NaturesBlessingModifier(),
			new JadedModifier(),
			new NaturesWrathModifier(),
			new NaturesPowerModifier(),
			new ApocalypseModifier(),
			new GetLuckyModifier(),
			new ExplosiveModifier(),
			new DarkTravelerModifier(),
			new IgnobleModifier(),
			new UnstableMatterModifier(),
			new PsicologicalModifier(),
			new GlobalModifier(),
			new PortlyModifier(),
	};

	public static final Material[] MATERIALS = new Material[]{
			// ModuleBase + New
			new BedrockMaterial(),
			new TNTMaterial(),

			// ModuleGems + ModuleGemsPlus
			new RubyMaterial(),
			new SapphireMaterial(),
			new PeridotMaterial(),
			new JadeMaterial(),
			new MalachiteMaterial(),
			new TopazMaterial(),
			new AmethystMaterial(),
			new PhoenixiteMaterial(),

			// ModuleProjectE
			new DarkMatterMaterial(),
			new RedMatterMaterial(),

			// ModulePsi
			new PsimetalMaterial(),
			new PsigemMaterial(),

			// ModuleDraconicEvolution -- unavailable in MC 1.19
			// XXX Original used three of four cores, but we should use the ingots instead

			// Available and maintained:
			// ModuleAppEng2
			// ModuleArmorPlus
			// ModuleBotania
			// ModuleIndusForego
			// ModuleIndusForegoStuff
			// ModuleThermalFoundation
	};

	public static Modifier getModifier(String id) {
		for (Modifier modifier : MODIFIERS) {
			if (modifier.id.equals(id)) {
				return modifier;
			}
		}
		return null;
	}

	public static Material getMaterial(String id) {
		for (Material material : MATERIALS) {
			if (material.id.equals(id)) {
				return material;
			}
		}
		return null;
	}
}
