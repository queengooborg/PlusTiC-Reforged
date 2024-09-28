package queengooborg.plusticreforged.api;

import net.minecraft.util.ResourceLocation;

public class ItemOrTag implements Ingredient {
	public ResourceLocation location;
	public boolean isTag;

	public ItemOrTag(ResourceLocation location, boolean isTag) {
		this.location = location;
		this.isTag = isTag;
	}

	public ItemOrTag(String namespace, String path, boolean isTag) {
		this(new ResourceLocation(namespace, path), isTag);
	}
}
