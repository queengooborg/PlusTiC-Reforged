package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.config.ModInfo;

public class GeneratorLang extends LanguageProvider {
	public GeneratorLang(DataGenerator gen) {
		super(gen, ModInfo.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		for (Material material : Resources.MATERIALS) {
			add(String.format("material.%s.%s", ModInfo.MOD_ID, material.id), material.name);
			if (!material.description.flavor.isEmpty()) {
				add(String.format("material.%s.%s.flavor", ModInfo.MOD_ID, material.id), material.description.flavor);
			}
			if (!material.description.traits.isEmpty()) {
				add(String.format("material.%s.%s.encyclopedia", ModInfo.MOD_ID, material.id), material.description.traits);
			}
		}

		for (Modifier modifier : Resources.MODIFIERS) {
			add("modifier.plustic." + modifier.id, modifier.name);
			if (!modifier.description.flavor.isEmpty()) {
				add(String.format("modifier.%s.%s.flavor", ModInfo.MOD_ID, modifier.id), modifier.description.flavor);
			}
			if (!modifier.description.traits.isEmpty()) {
				add(String.format("modifier.%s.%s.description", ModInfo.MOD_ID, modifier.id), modifier.description.traits);
			}
		}
	}
}
