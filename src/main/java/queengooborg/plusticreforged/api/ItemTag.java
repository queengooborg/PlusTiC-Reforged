package queengooborg.plusticreforged.api;

import net.minecraft.util.ResourceLocation;

public class ItemTag extends Item {
	public ItemTag(ResourceLocation location) {
		super(location);
	}

	public ItemTag(String namespace, String path) {
		super(namespace, path);
	}
}