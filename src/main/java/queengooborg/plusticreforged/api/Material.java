package queengooborg.plusticreforged.api;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
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
	public ICondition condition;

	public MaterialId resourceLocation;

	public Material(String id, String name, Description description, MaterialId item, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier modifier, int fluidTemperature) {
		this(id, name, description, item, condition, tier, type, order, color, stats, new Modifier[]{modifier}, fluidTemperature);
	}

	public Material(String id, String name, MaterialId item, Description description, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier modifier, Fluid fluid) {
		this(id, name, description, item, condition, tier, type, order, color, stats, new Modifier[]{modifier}, fluid);
	}

	public Material(String id, String name, Description description, MaterialId item, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier[] modifiers, int fluidTemperature) {
		this(id, name, description, item, condition, tier, type, order, color, stats, modifiers, new Fluid("molten_" + id, name, fluidTemperature, 15, 3000, 6000, color.base));
	}

	public Material(String id, String name, Description description, MaterialId item, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier[] modifiers, Fluid fluid) {

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
		this.moltenFluid = Objects.requireNonNull(fluid);

		if (type == MaterialType.METAL) {
			ICondition[] conditions = new ICondition[]{ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS, new NotCondition(new TagEmptyCondition("forge", "ingots/" + item.getPath()))};
			if (condition != null) {
				// We have to...create an entirely new array to add an element? Ugh, that's annoying...
				ICondition[] newConditions = new ICondition[conditions.length + 1];
				System.arraycopy(conditions, 0, newConditions, 0, conditions.length);
				newConditions[conditions.length] = condition;
				conditions = newConditions;
			}
			this.condition = new OrCondition(conditions);
		} else if (condition != null) {
			this.condition = condition;
		}

		this.resourceLocation = new MaterialId(ModInfo.MOD_ID, id);
	}
}