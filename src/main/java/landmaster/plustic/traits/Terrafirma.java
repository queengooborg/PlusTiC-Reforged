package landmaster.plustic.traits;

import java.util.*;

import com.google.common.collect.*;

import c4.conarm.lib.armor.*;
import c4.conarm.lib.traits.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.traits.*;

@net.minecraftforge.fml.common.Optional.Interface(iface = "c4.conarm.lib.traits.IArmorTrait", modid = "conarm")
@net.minecraftforge.fml.common.Optional.Interface(iface = "c4.conarm.lib.traits.IArmorAbility", modid = "conarm")
public class Terrafirma extends AbstractTraitLeveled implements IArmorTrait, IArmorAbility {
	public static final List<Terrafirma> terrafirma = ImmutableList.of(new Terrafirma(1), new Terrafirma(2));

	public Terrafirma(int levels) {
		super("terrafirma", 0x00FF00, 3, levels);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && entity instanceof EntityLivingBase && random.nextFloat() < 0.05) {
			((EntityLivingBase) entity).heal(levels / 3.0f);
		}
	}

	@Override
	public boolean disableRendering(ItemStack arg0, EntityLivingBase arg1) {
		return false;
	}

	@Override
	public ArmorModifications getModifications(EntityPlayer arg0, ArmorModifications arg1, ItemStack arg2,
	                                           DamageSource arg3, double arg4, int arg5) {
		return arg1;
	}

	@Override
	public void onAbilityTick(int arg0, World arg1, EntityPlayer arg2) {
		if (random.nextFloat() < 0.05) {
			arg2.heal(arg0 / 6.0f);
		}
	}

	@Override
	public int onArmorDamage(ItemStack arg0, DamageSource arg1, int arg2, int arg3, EntityPlayer arg4, int arg5) {
		return arg3;
	}

	@Override
	public void onArmorEquipped(ItemStack arg0, EntityPlayer arg1, int arg2) {
		// nothing
	}

	@Override
	public int onArmorHeal(ItemStack arg0, DamageSource arg1, int arg2, int arg3, EntityPlayer arg4, int arg5) {
		return arg3;
	}

	@Override
	public void onArmorRemoved(ItemStack arg0, EntityPlayer arg1, int arg2) {
		// nothing
	}

	@Override
	public float onDamaged(ItemStack arg0, EntityPlayer arg1, DamageSource arg2, float arg3, float arg4,
	                       LivingDamageEvent arg5) {
		return arg4;
	}

	@Override
	public void onFalling(ItemStack arg0, EntityPlayer arg1, LivingFallEvent arg2) {
		// nothing
	}

	@Override
	public float onHeal(ItemStack arg0, EntityPlayer arg1, float arg2, float arg3, LivingHealEvent arg4) {
		return arg3;
	}

	@Override
	public float onHurt(ItemStack arg0, EntityPlayer arg1, DamageSource arg2, float arg3, float arg4,
	                    LivingHurtEvent arg5) {
		return arg4;
	}

	@Override
	public void onItemPickup(ItemStack arg0, EntityItem arg1, EntityItemPickupEvent arg2) {
		// nothing
	}

	@Override
	public void onJumping(ItemStack arg0, EntityPlayer arg1, LivingEvent.LivingJumpEvent arg2) {
		// nothing
	}

	@Override
	public void onKnockback(ItemStack arg0, EntityPlayer arg1, LivingKnockBackEvent arg2) {
		// nothing
	}

	@Override
	public int getAbilityLevel(ModifierNBT arg0) {
		return arg0.level;
	}
}
