package queengooborg.plustic.api;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.lwjgl.opengl.*;

import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.objects.*;
import queengooborg.plustic.*;
import queengooborg.plustic.net.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.utils.*;

/**
 * 
 * Class for toggleable modifiers
 * @author Landmaster
 *
 */
public class Toggle {
	private static final Set<String> toggleable = new HashSet<>();
	private static final Map<String, String> toggleableAlias = new HashMap<>();
	
	static {
		MinecraftForge.EVENT_BUS.register(Toggle.class);
	}

	public static void addToggleable(String trait) {
		toggleable.add(trait);
	}
	public static void addToggleableAlias(String trait, String alias) {
		toggleableAlias.put(alias, trait);
	}
	public static String unwrapAlias(String alias) {
		String res = alias;
		while (toggleableAlias.containsKey(res)) {
			res = toggleableAlias.get(alias);
		}
		return res;
	}
	
	public static class Gui extends GuiScreen {
		public static final int OPTIONS_PER_PAGE = 6;
		
		private final ResourceLocation background = new ResourceLocation(ModInfo.MODID, "textures/gui/toggle.png");
		
		private int page;
		private EntityPlayer player;
		private List<String> identifiers;
		private BooleanList enableds;
		
		private int xSize = 176;
		private int ySize = 128;
		private int guiLeft, guiTop;
		
		public Gui(EntityPlayer player) {
			this.player = player;
			page = 0;
			Object2BooleanMap<String> temp = new Object2BooleanLinkedOpenHashMap<>();
			NBTTagCompound nbt = TagUtil.getTagSafe(this.player.getHeldItemMainhand());
			NBTTagList traits = TagUtil.getTraitsTagList(nbt);
			for (int i=0; i<traits.tagCount(); ++i) {
				String identifier = unwrapAlias(traits.getStringTagAt(i));
				if (toggleable.contains(identifier)) {
					temp.put(identifier, getToggleState(nbt, identifier));
				}
			}
			identifiers = new ArrayList<>(temp.keySet());
			enableds = new BooleanArrayList(temp.values());
		}
		
		@Override
		public void initGui() {
			super.initGui();
			guiLeft = (width - xSize) / 2;
	        guiTop = (height - ySize) / 2;
		}
		
		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			drawDefaultBackground();
			
			super.drawScreen(mouseX, mouseY, partialTicks);
			
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableAlpha();
			GL11.glEnable(GL11.GL_BLEND);
			
			// draw background proper
			mc.renderEngine.bindTexture(background);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			// draw header
			fontRenderer.drawString(I18n.format("gui.header.toggle"), guiLeft+5, guiTop+5, 0xFFFFFF);
			
			// draw list background
			mc.renderEngine.bindTexture(background);
			for (int i=0; i<Math.min(OPTIONS_PER_PAGE, identifiers.size() - page*OPTIONS_PER_PAGE); ++i) {
				drawTexturedModalRect(guiLeft+7, guiTop+18*(i+1), 0, 128, 152, 16);
			}
			
			// draw arrows
			mc.renderEngine.bindTexture(background);
			if (page > 0) drawTexturedModalRect(guiLeft+160, guiTop+18, 176, 12, 12, 21);
			if (page < identifiers.size() / OPTIONS_PER_PAGE - (identifiers.size() % OPTIONS_PER_PAGE == 0 ? 1 : 0)) drawTexturedModalRect(guiLeft+160, guiTop+100, 176+12, 12, 12, 21);
			
			// draw items
			for (int i=page*OPTIONS_PER_PAGE; i<Math.min((page+1)*OPTIONS_PER_PAGE, identifiers.size()); ++i) {
				String identifier = identifiers.get(i);
				boolean enabled = enableds.get(i);
				String locName = I18n.format("gui.toggle.tool", I18n.format("modifier."+identifier+".name"));
				fontRenderer.drawString(locName, guiLeft+10, guiTop+18*(i%OPTIONS_PER_PAGE+1)+3, 0xFFFFFF);
				mc.renderEngine.bindTexture(background);
				drawTexturedModalRect(guiLeft+130, guiTop+18*(i%OPTIONS_PER_PAGE+1)+1,176+(enabled ? 0 : 12), 0, 12, 12);
				if (isPointInRegion(7, 18*(i%OPTIONS_PER_PAGE+1), 158, 16, mouseX, mouseY)) {
					drawHoveringText(Arrays.asList(I18n.format("tooltip.plustic.toggle.info"),
							I18n.format("tooltip.plustic.toggle.state."+enabled, locName)
							), mouseX, mouseY);
				}
			}
		}
		
		@Override
		public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			for (int i=0; i<Math.min(OPTIONS_PER_PAGE, identifiers.size() - page*OPTIONS_PER_PAGE); ++i) {
				if (isPointInRegion(7, 18*(i+1), 152, 16, mouseX, mouseY)) {
					String identifier = identifiers.get(page*OPTIONS_PER_PAGE+i);
					return;
				}
			}
			if (isPointInRegion(160, 18, 12, 21, mouseX, mouseY)) {
				page = Math.max(page-1, 0);
			} else if (isPointInRegion(160, 100, 12, 21, mouseX, mouseY)) {
				page = Math.min(page+1, identifiers.size() / OPTIONS_PER_PAGE - (identifiers.size() % OPTIONS_PER_PAGE == 0 ? 1 : 0));
			}
		}
		
		protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
	        int i = this.guiLeft;
	        int j = this.guiTop;
	        pointX = pointX - i;
	        pointY = pointY - j;
	        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
		}
		
		public void update(String identifier, boolean value) {
			int ind = identifiers.indexOf(identifier);
			if (ind>=0) enableds.set(ind, value);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void testAndToggle(InputEvent.KeyInputEvent event) {
		if (PlusTiC.proxy.isControlPressed("toggle_gui")) {
			PacketHandler.INSTANCE.sendToServer(new PacketRequestToggleGui());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void tooltip(ItemTooltipEvent event) {
		if (event.getItemStack() == null
				|| !(event.getItemStack().getItem() instanceof ToolCore))
			return;
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		NBTTagList traits = TagUtil.getTraitsTagList(nbt);
		NBTTagCompound toggle = TagUtil.getTagSafe(nbt, "PlusTiC_toggle");
		for (int i=0; i<traits.tagCount(); ++i) {
			String identifier = traits.getStringTagAt(i);
			if (toggleable.contains(identifier)) {
				boolean enabled = !toggle.hasKey(identifier);
				event.getToolTip().add(I18n.format("tooltip.plustic.toggle.state."+enabled,
									TinkerRegistry.getModifier(identifier).getLocalizedName()));
			}
		}
	}
	
	public static boolean canToggle(ItemStack is) {
		return canToggle(TagUtil.getTagSafe(is));
	}
	
	public static boolean canToggle(NBTTagCompound nbt) {
		NBTTagList traits = TagUtil.getTraitsTagList(nbt);
		for (int i=0; i<traits.tagCount(); ++i) {
			if (toggleable.contains(traits.getStringTagAt(i))) return true;
		}
		return false;
	}
	
	/**
	 * Check whether a modifier/trait is enabled.
	 * @param is the itemstack
	 * @param identifier the identifier for the modifier/trait
	 * @return whether the modifier/trait is enabled
	 */
	public static boolean getToggleState(ItemStack is, String identifier) {
		NBTTagCompound nbt = TagUtil.getTagSafe(is);
		return getToggleState(nbt, identifier);
	}
	
	/**
	 * Check whether a modifier/trait is enabled.
	 * @see #getToggleState(ItemStack, String)
	 */
	public static boolean getToggleState(NBTTagCompound nbt, String identifier) {
		NBTTagCompound toggle = TagUtil.getTagSafe(nbt, "PlusTiC_toggle");
		return (!toggleable.contains(identifier) || !toggle.hasKey(identifier));
	}
	
	/**
	 * Set whether the modifier/trait is enabled.
	 */
	public static void setToggleState(NBTTagCompound nbt, String identifier, boolean value) {
		if (!toggleable.contains(identifier)) return;
		NBTTagCompound toggle = TagUtil.getTagSafe(nbt, "PlusTiC_toggle");
		if (value) {
			toggle.removeTag(identifier);
		} else {
			toggle.setBoolean(identifier, false);
		}
		nbt.setTag("PlusTiC_toggle", toggle);
	}
}
