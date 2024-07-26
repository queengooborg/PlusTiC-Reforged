package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Modifier;

public interface _Modifiers {
	Modifier[] modifiers = new Modifier[]{
		new HeavyModifier()
	};

	static Modifier get(String id) {
		for (Modifier modifier : modifiers) {
			if (modifier.id.equals(id)) {
				return modifier;
			}
		}
		return null;
	}
}
