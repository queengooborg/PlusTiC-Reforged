package queengooborg.plusticreforged.api;

import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.Objects;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.*;

public class Material {
	public String id;
	public String name;
	public MaterialId item;
	public Description description = new Description();
	public int tier = 1;
	public MaterialType type;
	public int order = ORDER_HARVEST + ORDER_COMPAT;
	public MaterialColors color = new MaterialColors();
	public MaterialStats stats = new MaterialStats();
	public Modifier[] modifiers;
	public Fluid moltenFluid;

	public MaterialId resourceLocation;

	public Material(String id, String name, MaterialId item, Description description, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier modifier, int fluidTemperature) {
		this(id, name, item, description, tier, type, order, color, stats, new Modifier[]{modifier}, fluidTemperature);
	}

	public Material(String id, String name, MaterialId item, Description description, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier[] modifiers, int fluidTemperature) {
		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
		this.item = Objects.requireNonNull(item);
		if (description != null) this.description = description;
		this.tier = tier;
		this.order = order;
		if (type != null) this.type = type;
		if (color != null) this.color = color;
		if (stats != null) this.stats = stats;
		this.modifiers = modifiers;
		this.moltenFluid = new Fluid("molten_" + id, "Molten " + name, fluidTemperature, 15, 3000, 6000, this.color.base);

		this.resourceLocation = new MaterialId(ModInfo.MOD_ID, id);
	}
}