package landmaster.plustic.tools;

import appeng.api.config.*;
import appeng.api.implementations.items.*;
import landmaster.plustic.api.ModInfo;
import landmaster.plustic.api.Sounds;
import landmaster.plustic.api.event.PTEnergyDrain;
import landmaster.plustic.api.event.PTLaserAttack;
import landmaster.plustic.config.Config;
import landmaster.plustic.modules.ModuleTools;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.net.PacketLaserGunZapBlock;
import landmaster.plustic.tools.nbt.LaserNBT;
import landmaster.plustic.tools.nbt.ToolEnergyNBT;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.util.ClientUtils;
import landmaster.plustic.util.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.tools.*;

import java.util.*;

@net.minecraftforge.fml.common.Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyContainerItem", modid = "redstoneflux")
@net.minecraftforge.fml.common.Optional.Interface(iface = "appeng.api.implementations.items.IAEItemPowerStorage", modid = "appliedenergistics2")
public class ToolLaserGun extends TinkerToolCore implements cofh.redstoneflux.api.IEnergyContainerItem, IAEItemPowerStorage, IToggleTool<ToolLaserGun.Mode> {
	public static final String ATTACK_DURATION_TAG = "AttackDuration";
	public static final String MODE_TAG = "Mode";
	public static final String POS_LCOOL_TAG = "LockCooldown";
	public static final String ENERGY_NBT = "Energy";
	public static final ResourceLocation LASER_LOC = new ResourceLocation(ModInfo.MODID, "textures/effects/laserbeam.png");
	// to avoid errors with certain methods
	@SidedProxy(serverSide = "landmaster.plustic.tools.ToolLaserGun$Proxy", clientSide = "landmaster.plustic.tools.ToolLaserGun$ProxyClient")
	public static Proxy proxy;

	public ToolLaserGun() {
		super(PartMaterialType.handle(TinkerTools.toughToolRod),
				PartMaterialType.head(ModuleTools.pipe_piece),
				new PartMaterialType(ModuleTools.laser_medium, LaserMediumMaterialStats.TYPE),
				new PartMaterialType(ModuleTools.battery_cell, BatteryCellMaterialStats.TYPE));

		this.addCategory(Category.WEAPON);

		proxy.initEvents();

		this.setTranslationKey("laser_gun").setRegistryName("laser_gun");
	}

	private static float range(ItemStack is) {
		return (new LaserNBT(TagUtil.getToolTag(is))).range;
	}

	private static int getFullEnergy(ItemStack is) {
		return (new ToolEnergyNBT(TagUtil.getToolTag(is))).energy;
	}

	private static Optional<ItemStack> getActiveLaserGun(EntityLivingBase entity) {
		return Arrays.stream(EnumHand.values())
				.map(entity::getHeldItem)
				.filter(stack -> stack != null
						&& stack.getItem() instanceof ToolLaserGun
						&& TagUtil.getTagSafe(stack).getInteger(ATTACK_DURATION_TAG) > 0)
				.findFirst();
	}

	public static RayTraceResult trace(Mode mode, EntityPlayer entity, float range) {
		switch (mode) {
			case ATTACK:
				return EntityUtil.raytraceEntityPlayerLook(entity, range);
			case TOOL:
				return entity.getEntityWorld().rayTraceBlocks(entity.getPositionEyes(1), entity.getPositionEyes(1).add(entity.getLook(1).scale(range)));
			default:
				throw new RuntimeException("Bad mode, you copycat!");
		}
	}

	private int maxAttackDuration(ItemStack is) {
		return (int) (20 / ToolHelper.getActualAttackSpeed(is));
	}

	private int energyPerAttack(ItemStack is) {
		return Config.laser_energy;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!worldIn.isRemote) {
			NBTTagCompound nbt = TagUtil.getTagSafe(stack);
			int atkDur = nbt.getInteger(ATTACK_DURATION_TAG);
			--atkDur;
			if (atkDur < 0) atkDur = 0;
			nbt.setInteger(ATTACK_DURATION_TAG, atkDur);
			stack.setTagCompound(nbt);

			nbt.setInteger(POS_LCOOL_TAG, MathHelper.clamp(nbt.getInteger(POS_LCOOL_TAG) - 1, 0, Integer.MAX_VALUE));
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (this.isInCreativeTab(tab)) {
			this.addDefaultSubItems(list, null, null, TinkerMaterials.prismarine, TinkerMaterials.manyullyn);
		}
	}

	@Override
	protected LaserNBT buildTagData(List<Material> materials) {
		LaserNBT nbt = new LaserNBT();
		nbt.head(materials.get(1).getStatsOrUnknown(MaterialTypes.HEAD));
		nbt.handle(materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE));
		nbt.laserMedium(materials.get(2).getStatsOrUnknown(LaserMediumMaterialStats.TYPE));
		nbt.batteryCell(materials.get(3).getStatsOrUnknown(BatteryCellMaterialStats.TYPE));
		return nbt;
	}

	@Override
	public float damagePotential() {
		return 1.0f;
	}

	@Override
	public double attackSpeed() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		Optional.ofNullable(IToggleTool.getMode(stack, Mode.class))
				.ifPresent(mode -> tooltip.add(I18n.format("msg.plustic.tool_mode", I18n.format(mode.getUnlocName()))));

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public List<String> getInformation(ItemStack stack, boolean detailed) {
		List<String> list = new ArrayList<>();

		TooltipBuilder info = new TooltipBuilder(stack);

		info.addDurability(!detailed);
		// for energy stored
		info.add(String.format(TextFormatting.AQUA + "%s RF / %s RF", this.getEnergyStored(stack), this.getMaxEnergyStored(stack)));

		info.addAttack();

		if (ToolHelper.getFreeModifiers(stack) > 0) {
			info.addFreeModifiers();
		}

		if (detailed) {
			info.addModifierInfo();
		}

		list.addAll(info.getTooltip());

		return list;
	}

	/**
	 * <strong>This is the real laser attack.</strong>
	 * {@inheritDoc}
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		return this._rightClick(playerIn.getHeldItem(hand), worldIn, playerIn, hand);
	}

	protected ActionResult<ItemStack> _rightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (worldIn.isRemote) return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

		NBTTagCompound nbt = TagUtil.getTagSafe(itemStackIn);

		ActionResult<ItemStack> res = new ActionResult<>(EnumActionResult.PASS, itemStackIn);

		if (IToggleTool.getMode(itemStackIn, Mode.class) == Mode.ATTACK) {
			res = Optional.ofNullable(Utils.raytraceEntityPlayerLookWithPred(playerIn, range(itemStackIn), ent -> !(ent instanceof IEntityMultiPart)))
					.map(rtr -> rtr.entityHit)
					.map(ent -> {
						PTEnergyDrain eevent = new PTEnergyDrain(itemStackIn, playerIn, this.energyPerAttack(itemStackIn)); // event
						MinecraftForge.EVENT_BUS.post(eevent);
						int energyTaken = eevent.energyDrained; // grab event result

						if (this.extractEnergy(itemStackIn, energyTaken, true) >= energyTaken
								&& nbt.getInteger(ATTACK_DURATION_TAG) <= 0) { // able to attack?
							if (hand == EnumHand.OFF_HAND) {
								unequip(playerIn, EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.MAINHAND);
								equip(playerIn, EntityEquipmentSlot.OFFHAND, EntityEquipmentSlot.MAINHAND);
							}
							boolean didAttack = ToolHelper.attackEntity(itemStackIn, this, playerIn, ent);
							if (hand == EnumHand.OFF_HAND) {
								unequip(playerIn, EntityEquipmentSlot.OFFHAND, EntityEquipmentSlot.MAINHAND);
								equip(playerIn, EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.MAINHAND);
							}
							PTLaserAttack aevent = new PTLaserAttack(playerIn, ent, itemStackIn, didAttack);
							MinecraftForge.EVENT_BUS.post(aevent);
							if (didAttack) { // try attacking
								this.extractEnergy(itemStackIn, energyTaken, false); // if success, use energy
								nbt.setInteger(ATTACK_DURATION_TAG, this.maxAttackDuration(itemStackIn));
								itemStackIn.setTagCompound(nbt);
								Sounds.playSoundToAll(playerIn, Sounds.LASER_BEAM, 1.0f, 1.0f);
								return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
							}
						}
						return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
					}).orElse(new ActionResult<>(EnumActionResult.FAIL, itemStackIn));
		}

		return res;
	}

	@Override
	public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
		if (player instanceof EntityPlayer) {
			return entity.attackEntityFrom(new LaserDamageSource("player", player, stack), damage);
		}
		return entity.attackEntityFrom(new LaserDamageSource("mob", player, stack), damage);
	}

	private void unequip(EntityLivingBase entity, EntityEquipmentSlot slot, EntityEquipmentSlot functionalSlot) {
		ItemStack stack = entity.getItemStackFromSlot(slot);
		if (!stack.isEmpty()) {
			entity.getAttributeMap().removeAttributeModifiers(stack.getAttributeModifiers(functionalSlot));
		}
	}

	private void equip(EntityLivingBase entity, EntityEquipmentSlot slot, EntityEquipmentSlot functionalSlot) {
		ItemStack stack = entity.getItemStackFromSlot(slot);
		if (!stack.isEmpty()) {
			entity.getAttributeMap().applyAttributeModifiers(stack.getAttributeModifiers(functionalSlot));
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) return EnumActionResult.PASS;

		final ItemStack stack = player.getHeldItem(hand);

		if (IToggleTool.getMode(stack, Mode.class) == Mode.TOOL) {
			final NBTTagCompound nbt = stack.getTagCompound();

			if (nbt.getInteger(POS_LCOOL_TAG) > 0) return EnumActionResult.FAIL;

			ItemStack smeltingRes = ItemStack.EMPTY;

			final IBlockState state = worldIn.getBlockState(pos);

			if (!(smeltingRes = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state))).copy()).isEmpty()) {
				PTEnergyDrain eevent = new PTEnergyDrain(stack, player, this.energyPerAttack(stack)); // event
				MinecraftForge.EVENT_BUS.post(eevent);
				int energyTaken = eevent.energyDrained; // grab event result

				if (this.extractEnergy(stack, energyTaken, true) >= energyTaken
						&& worldIn.destroyBlock(pos, false)) {
					this.extractEnergy(stack, energyTaken, false);
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, smeltingRes));
					nbt.setInteger(POS_LCOOL_TAG, MathHelper.ceil(220 / ToolHelper.getActualMiningSpeed(stack)));
					if (player instanceof EntityPlayerMP) {
						PacketHandler.INSTANCE.sendTo(new PacketLaserGunZapBlock(new Vec3d(hitX, hitY, hitZ), EntityPlayer.getUUID(player.getGameProfile())), (EntityPlayerMP) player);
					}
					return EnumActionResult.SUCCESS;
				}
			}
		}

		return EnumActionResult.PASS;
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return (int) this._receiveEnergy(container, maxReceive, simulate);
	}

	protected double _receiveEnergy(ItemStack container, double maxReceive, boolean simulate) {
		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		double energy = container.getTagCompound().getDouble(ENERGY_NBT);
		double energyReceived = Math.min(getFullEnergy(container) - energy, Math.min(getFullEnergy(container), maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.getTagCompound().setDouble(ENERGY_NBT, energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return (int) this._extractEnergy(container, maxExtract, simulate);
	}

	protected double _extractEnergy(ItemStack container, double maxExtract, boolean simulate) {
		if (container.getTagCompound() == null || !container.getTagCompound().hasKey(ENERGY_NBT)) {
			return 0;
		}
		double energy = container.getTagCompound().getDouble(ENERGY_NBT);
		double energyExtracted = Math.min(energy, Math.min(getFullEnergy(container), maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.getTagCompound().setDouble(ENERGY_NBT, energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container.getTagCompound() == null || !container.getTagCompound().hasKey(ENERGY_NBT)) {
			return 0;
		}
		return container.getTagCompound().getInteger(ENERGY_NBT);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return getFullEnergy(container);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack is, NBTTagCompound capNbt) {
		return new Provider(is);
	}

	@Override
	public Class<Mode> clazz() {
		return Mode.class;
	}

	@Override
	public String getTag() {
		return MODE_TAG;
	}

	@Override
	@net.minecraftforge.fml.common.Optional.Method(modid = "appliedenergistics2")
	public double extractAEPower(ItemStack arg0, double arg1, Actionable arg2) {
		return PowerUnits.RF.convertTo(PowerUnits.AE, this._extractEnergy(arg0, PowerUnits.AE.convertTo(PowerUnits.RF, arg1), arg2 == Actionable.SIMULATE));
	}

	@Override
	public double getAECurrentPower(ItemStack arg0) {
		if (arg0.getTagCompound() == null || !arg0.getTagCompound().hasKey(ENERGY_NBT)) {
			return 0;
		}
		return PowerUnits.RF.convertTo(PowerUnits.AE, arg0.getTagCompound().getDouble(ENERGY_NBT));
	}

	@Override
	public double getAEMaxPower(ItemStack arg0) {
		return PowerUnits.RF.convertTo(PowerUnits.AE, this.getMaxEnergyStored(arg0));
	}

	@Override
	@net.minecraftforge.fml.common.Optional.Method(modid = "appliedenergistics2")
	public AccessRestriction getPowerFlow(ItemStack arg0) {
		return AccessRestriction.READ_WRITE;
	}

	@Override
	@net.minecraftforge.fml.common.Optional.Method(modid = "appliedenergistics2")
	public double injectAEPower(ItemStack arg0, double arg1, Actionable arg2) {
		// Whereas RF returns energy able to be stored, AE2 returns energy **unable** to be stored.
		return arg1 - PowerUnits.RF.convertTo(PowerUnits.AE, this._receiveEnergy(arg0, PowerUnits.AE.convertTo(PowerUnits.RF, arg1), arg2 == Actionable.SIMULATE));
	}

	public enum Mode implements IEnumL10n {
		ATTACK, TOOL;

		@Override
		public String getUnlocName() {
			return "mode.laser_gun." + name().toLowerCase(Locale.US);
		}
	}

	public static class LaserDamageSource extends EntityDamageSource {
		private final ItemStack stack;

		public LaserDamageSource(String damageTypeIn, Entity damageSourceEntityIn, ItemStack stack) {
			super(damageTypeIn, damageSourceEntityIn);
			this.stack = stack;
		}

		public ItemStack getStack() {
			return stack;
		}

	}

	public static class Proxy {
		public void initEvents() {
			MinecraftForge.EVENT_BUS.register(Proxy.class);
		}

		public void addToZapBlockRendering(EntityPlayer shooter, Vec3d target) {
		}
	}

	public static class ProxyClient extends Proxy {
		private static final Map<EntityPlayer, Vec3d> zapBlockRend = new WeakHashMap<>();

		@SubscribeEvent
		public static void renderBeam(RenderWorldLastEvent event) { // for this player
			Optional.ofNullable(Minecraft.getMinecraft().player)
					.ifPresent(ProxyClient::doRenderBeam);
		}

		@SubscribeEvent
		public static void renderBeam(RenderPlayerEvent.Pre event) { // for other players
			if (!event.getEntityPlayer().equals(Minecraft.getMinecraft().player)) { // exclude this player
				doRenderBeam(event.getEntityPlayer());
			}
		}

		@SubscribeEvent
		public static void renderBeam(RenderLivingEvent.Pre<?> event) { // for other entities
			if (!(event.getEntity() instanceof EntityPlayer)) { // exclude players
				doRenderBeam(event.getEntity());
			}
		}

		public static void doRenderBeam(EntityLivingBase shooter) {
			getActiveLaserGun(shooter)
					.ifPresent(stack -> {
						GlStateManager.depthMask(false);
						GlStateManager.enableBlend();
						GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

						GlStateManager.pushMatrix();

						EntityPlayer player = Minecraft.getMinecraft().player;

						float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
						double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
						double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
						double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

						Vec3d vec = new Vec3d(doubleX, doubleY + player.getEyeHeight(), doubleZ);
						Vec3d vec0 = shooter.getPositionVector().add(0, shooter.getEyeHeight() + 0.2, 0);
						Vec3d vec1 = vec0;

						switch (IToggleTool.getMode(stack, Mode.class)) {
							case ATTACK:
								vec1 = Optional.ofNullable(EntityUtil.raytraceEntityPlayerLook(player, range(stack)))
										.map(rtr -> rtr.hitVec)
										.orElse(vec1);
								break;
							case TOOL:
								if (zapBlockRend.containsKey(shooter)) {
									vec1 = zapBlockRend.get(shooter);
									zapBlockRend.remove(shooter);
								}
								break;
							default:
								break;
						}

						GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

						Tessellator tessellator = Tessellator.getInstance();
						BufferBuilder buffer = tessellator.getBuffer();

						Minecraft.getMinecraft().renderEngine.bindTexture(LASER_LOC);

						buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

						ClientUtils.drawBeam(vec0, vec1, vec, 0.13f);

						tessellator.draw();

						GlStateManager.popMatrix();

						GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					});
		}

		public void addToZapBlockRendering(EntityPlayer shooter, Vec3d target) {
			zapBlockRend.put(shooter, target);
		}

		@Override
		public void initEvents() {
			super.initEvents();
			MinecraftForge.EVENT_BUS.register(ProxyClient.class);
		}
	}

	private class Energy implements IEnergyStorage {
		ItemStack is;

		public Energy(ItemStack is) {
			this.is = is;
		}

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			return ToolLaserGun.this.receiveEnergy(is, maxReceive, simulate);
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			return ToolLaserGun.this.extractEnergy(is, maxExtract, simulate);
		}

		@Override
		public int getEnergyStored() {
			return ToolLaserGun.this.getEnergyStored(is);
		}

		@Override
		public int getMaxEnergyStored() {
			return ToolLaserGun.this.getMaxEnergyStored(is);
		}

		@Override
		public boolean canExtract() {
			return true;
		}

		@Override
		public boolean canReceive() {
			return true;
		}
	}

	private class Provider implements ICapabilityProvider {
		Energy energy;

		public Provider(ItemStack is) {
			energy = new Energy(is);
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == CapabilityEnergy.ENERGY;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability == CapabilityEnergy.ENERGY) {
				return (T) energy;
			}
			return null;
		}
	}
}
