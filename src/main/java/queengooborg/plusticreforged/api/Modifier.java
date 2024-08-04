package queengooborg.plusticreforged.api;

import net.minecraft.util.ResourceLocation;
import queengooborg.plusticreforged.Registries;
import queengooborg.plusticreforged.config.ModInfo;

import java.util.Objects;

public abstract class Modifier extends slimeknights.tconstruct.library.modifiers.Modifier {
	public String id;
	public String name;
	public Description description = new Description();

	public ResourceLocation resourceLocation;

	public Modifier(String id, String name, Description description, int color) {
		super(color);

		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
		if (description != null) this.description = description;

		this.resourceLocation = new ResourceLocation(ModInfo.MOD_ID, id);
		this.setRegistryName(this.resourceLocation);

//		Registries.MODIFIERS.register(id, () -> this);
	}
}
