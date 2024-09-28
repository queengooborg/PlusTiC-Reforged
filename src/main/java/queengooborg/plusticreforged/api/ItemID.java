package queengooborg.plusticreforged.api;

import net.minecraft.util.ResourceLocation;

public class ItemID extends Item {
	public ItemID(ResourceLocation location) {
		super(location);
	}

	public ItemID(String namespace, String path) {
		super(namespace, path);
	}
}
