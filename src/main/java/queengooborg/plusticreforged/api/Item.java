package queengooborg.plusticreforged.api;

import net.minecraft.util.ResourceLocation;

public class Item implements Ingredient {
	public ResourceLocation location;
	public boolean isTag;

	public Item(ResourceLocation location, boolean isTag) {
		this.location = location;
		this.isTag = isTag;
	}

	public Item(String namespace, String path, boolean isTag) {
		this(new ResourceLocation(namespace, path), isTag);
	}
}
