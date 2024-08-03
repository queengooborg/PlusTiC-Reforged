package queengooborg.plusticreforged.api;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.mantle.registration.object.FluidObject;

import java.util.function.Supplier;

public class Fluid {
	public final String id;
	public final String name;
	public final int temperature;
	public final int light;
	public final int density;
	public final int viscosity;

	public final Supplier<ForgeFlowingFluid.Source> FLUID;
	public final Supplier<ForgeFlowingFluid.Flowing> FLUID_FLOWING;
	public final FluidObject<ForgeFlowingFluid> FLUID_OBJECT;
	public final ForgeFlowingFluid.Properties FLUID_PROPERTIES;
	public final Supplier<FlowingFluidBlock> FLUID_BLOCK;
	public final Supplier<BucketItem> FLUID_BUCKET;
	public final ResourceLocation TEXTURE_STILL;
	public final ResourceLocation TEXTURE_FLOWING;

	public Fluid(String id, String name, int temperature, int light, int density, int viscosity) {
		this.id = id;
		this.name = name;
		this.temperature = temperature;
		this.light = light;
		this.density = density;
		this.viscosity = viscosity;

		FLUID = Resources.FLUIDS.register(id, () -> new ForgeFlowingFluid.Source(getFluidProperties()));
		FLUID_FLOWING = Resources.FLUIDS.register("flowing_" + id, () -> new ForgeFlowingFluid.Flowing(getFluidProperties()));

		TEXTURE_STILL = new ResourceLocation(ModInfo.MOD_ID, "block/fluid/" + id + "_still");
		TEXTURE_FLOWING = new ResourceLocation(ModInfo.MOD_ID, "block/fluid/" + id + "_flow");

		FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(FLUID, FLUID_FLOWING, FluidAttributes.builder(TEXTURE_STILL, TEXTURE_FLOWING).overlay(TEXTURE_STILL).luminosity(light).density(density).viscosity(viscosity).temperature(temperature).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA));

		FLUID_BLOCK = () -> new FlowingFluidBlock(FLUID, Block.Properties.of(net.minecraft.block.material.Material.LAVA).lightLevel((state) -> { return light; }).randomTicks().strength(100.0F).noDrops());
		FLUID_BUCKET = () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ItemGroup.TAB_MISC));

		FLUID_PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK).explosionResistance(1000F).tickRate(9);

		FLUID_OBJECT = new FluidObject<>(new ResourceLocation(ModInfo.MOD_ID, id), id, FLUID, FLUID_FLOWING, FLUID_BLOCK);
	}

	public ForgeFlowingFluid.Properties getFluidProperties() {
		return FLUID_PROPERTIES;
	}

	public ForgeFlowingFluid getFluid() {
		return FLUID.get();
	}

	public ITag.INamedTag<net.minecraft.fluid.Fluid> getLocalTag() {
		return FLUID_OBJECT.getLocalTag();
	}

	public ITag.INamedTag<net.minecraft.fluid.Fluid> getForgeTag() {
		return FLUID_OBJECT.getForgeTag();
	}
}