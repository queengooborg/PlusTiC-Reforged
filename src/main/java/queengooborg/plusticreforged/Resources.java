package queengooborg.plusticreforged;

import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.modifiers.*;
import queengooborg.plusticreforged.materials.*;

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
	};

	public static final Material[] MATERIALS = new Material[]{
			new BedrockMaterial(),

			// ModuleGems + ModuleGemsPlus
			new RubyMaterial(),
			new SapphireMaterial(),
			new PeridotMaterial(),
			new JadeMaterial(),
			new MalachiteMaterial(),
			new TopazMaterial(),
			new AmethystMaterial(),
			new PhoenixiteMaterial(),
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
