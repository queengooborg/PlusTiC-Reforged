package queengooborg.plusticreforged.modifiers;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.config.ModInfo;

import java.util.HashMap;
import java.util.Map;

public interface _Modifiers {
	Modifier[] modifierObjects = {
			new HeavyModifier()
	};

	Map<String, RegistryObject<slimeknights.tconstruct.library.modifiers.Modifier>> modifiers = new HashMap<String, RegistryObject<slimeknights.tconstruct.library.modifiers.Modifier>>();

	DeferredRegister<slimeknights.tconstruct.library.modifiers.Modifier> MODIFIERS = DeferredRegister.create(slimeknights.tconstruct.library.modifiers.Modifier.class, ModInfo.MOD_ID);

	static void registerModifiers() {
		for (Modifier modifier : modifierObjects) {
			modifiers.put(modifier.id, MODIFIERS.register(modifier.id, () -> modifier));
		}
	}
}
