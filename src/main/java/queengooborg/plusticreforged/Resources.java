package queengooborg.plusticreforged;

import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.modifiers.*;
import queengooborg.plusticreforged.materials.*;

public class Resources {
	public static final Modifier[] MODIFIERS = new Modifier[]{
			new HeavyModifier(),
			new InvulnerableModifier(),
			new BloodyMaryModifier()
	};

	public static final Material[] MATERIALS = new Material[]{
			new BedrockMaterial(),
			new RubyMaterial(),
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
