package queengooborg.plustic.traits;

import java.util.*;

import com.google.common.collect.*;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import slimeknights.tconstruct.library.traits.*;

public class Terrafirma extends AbstractTraitLeveled {
	public static final List<Terrafirma> terrafirma = ImmutableList.of(new Terrafirma(1), new Terrafirma(2));
	
	public Terrafirma(int levels) {
		super("terrafirma", 0x00FF00, 3, levels);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && entity instanceof EntityLivingBase && random.nextFloat() < 0.05) {
			((EntityLivingBase)entity).heal(levels/3.0f);
		}
	}
}
