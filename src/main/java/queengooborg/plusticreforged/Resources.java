package queengooborg.plusticreforged;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.config.ModInfo;
import queengooborg.plusticreforged.modifiers.*;
import queengooborg.plusticreforged.materials.*;

public class Resources {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ModInfo.MOD_ID);

	public static final Modifier[] MODIFIERS = new Modifier[]{
		new HeavyModifier()
	};

	public static final Material[] MATERIALS = new Material[]{
		new BedrockMaterial()
	};

	public static Modifier getModifier(String id) {
		for (Modifier modifier : MODIFIERS) {
			if (modifier.id.equals(id)) {
				return modifier;
			}
		}
		return null;
	}
}
