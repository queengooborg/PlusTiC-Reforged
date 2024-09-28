package queengooborg.plusticreforged.api;

import net.minecraft.util.ResourceLocation;

public class Item implements Ingredient {
	public ResourceLocation location;

	public Item(ResourceLocation location) {
		this.location = location;
	}

	public Item(String namespace, String path) {
		this(new ResourceLocation(namespace, path));
	}
}
