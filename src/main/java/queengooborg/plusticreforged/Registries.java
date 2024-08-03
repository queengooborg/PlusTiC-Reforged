package queengooborg.plusticreforged;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import queengooborg.plusticreforged.config.ModInfo;

public class Registries {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ModInfo.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModInfo.MOD_ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModInfo.MOD_ID);
	public static final DeferredRegister<slimeknights.tconstruct.library.modifiers.Modifier> MODIFIERS = DeferredRegister.create(slimeknights.tconstruct.library.modifiers.Modifier.class, ModInfo.MOD_ID);
}
