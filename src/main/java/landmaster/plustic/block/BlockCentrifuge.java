package landmaster.plustic.block;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.gui.handler.PTGuiHandler;
import landmaster.plustic.tile.TECentrifugeCore;
import landmaster.plustic.tile.TECentrifugeTank;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.*;

import java.util.List;

public class BlockCentrifuge extends Block implements IMetaBlockName {
	public static final PropertyBool CORE = PropertyBool.create("core");

	public BlockCentrifuge() {
		super(Material.ROCK);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(10F);
		this.setResistance(80F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(blockState.getBaseState().withProperty(CORE, false));
		this.setTranslationKey("centrifuge").setRegistryName("centrifuge");
		this.setCreativeTab(TinkerRegistry.tabSmeltery);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CORE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(CORE, meta != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CORE) ? 1 : 0;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
		list.add(new ItemStack(this, 1, 1));
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return stack.getMetadata() != 0 ? "core" : "tank";
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, getMetaFromState(state));
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return state.getValue(CORE) ? new TECentrifugeCore() : new TECentrifugeTank();
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		// adapted from Tinker's TileTank code
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
			return false;
		}
		IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler)) {
			return true;
		}

		if (state.getValue(CORE) && !(Block.getBlockFromItem(heldItem.getItem()) instanceof BlockCentrifuge)) {
			if (!worldIn.isRemote) {
				playerIn.openGui(PlusTiC.INSTANCE, PTGuiHandler.CENTRIFUGE_CORE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}

		return FluidUtil.getFluidHandler(heldItem) != null;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		for (ItemStack drop : drops) {
			if (Block.getBlockFromItem(drop.getItem()) instanceof BlockCentrifuge) {
				drop.setTagCompound(new NBTTagCompound());
				drop.getTagCompound().setTag("BlockEntityTag", world.getTileEntity(pos).serializeNBT());
			}
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (willHarvest) return true; // see BlockFlowerPot.java
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
		super.harvestBlock(world, player, pos, state, te, tool);
		world.setBlockToAir(pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		NBTTagCompound blockEntityTag = stack.getSubCompound("BlockEntityTag");
		if (blockEntityTag != null && blockEntityTag.hasKey("Tank", 10)) {
			FluidStack fs = FluidStack.loadFluidStackFromNBT(blockEntityTag.getCompoundTag("Tank"));
			tooltip.add(I18n.format("tooltip.plustic.centrifuge.fluid_info", fs.getLocalizedName(), fs.amount));
		}
	}
}
