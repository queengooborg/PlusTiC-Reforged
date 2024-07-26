package queengooborg.plusticreforged.api;

import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Objects;

public abstract class Modifier extends slimeknights.tconstruct.library.modifiers.Modifier {
	public String id;
	public String name;
	public Description description = new Description();

	public Modifier(String id, String name, Description description, int color) {
		super(color);

		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
		if (description != null) this.description = description;
	}
}
