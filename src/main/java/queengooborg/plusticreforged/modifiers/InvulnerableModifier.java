package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;

public class InvulnerableModifier extends Modifier {
	public InvulnerableModifier() {
		super("invulnerable", "Invulnerable", new Description("You could only obtain these tools through Creative or cheats -- so if you're gonna cheat, you might as well commit, right?", "The tool takes no damage"), 0x555555);
		this.usable = true;
	}

	@Override
	public int onDamageTool(IModifierToolStack toolStack, int level, int amount, @Nullable LivingEntity holder) {
		// Prevents the tool from taking damage
		return 0;
	}
}
