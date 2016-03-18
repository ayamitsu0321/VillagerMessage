package ayamitsu.villagermessage.client.message;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

/**
 * Created by ayamitsu0321 on 2016/03/17.
 */
public final class MessageRenderer {

    private static EntityVillager villager = new EntityVillager(null, 0);
    private Message currentMessage;
    private static int FONT_MULTI_WIDTH = 9;
    private Random random = new Random();
    private int currentValue = 0;
    private int intervalRandom = 600;
    private int showDurationChar = 6;
    private int showDurationBase = 100;
    private int showDuration = 0;
    private boolean show = false;

    public void updatePre(RenderGameOverlayEvent.Post event) {

    }

    public void updatePost(RenderGameOverlayEvent.Post event) {
        int width = event.resolution.getScaledWidth();
        int height = event.resolution.getScaledHeight();

        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            if (!FMLClientHandler.instance().getClient().isGamePaused()) {
                currentValue++;

                if (show) {
                    if (currentValue > showDuration) {
                        this.setCurrentMessage(null);
                    }
                } else {
                    if (currentValue > intervalRandom) {
                        this.setCurrentMessage(MessageLoader.getCurrentList().random.get(random.nextInt(MessageLoader.getCurrentList().random.size())));
                    }
                }
            }

            this.renderVillagerMessage(FMLClientHandler.instance().getClient(), width, height, event.partialTicks);
        }
    }

    /**
     * not used. clientでもeventが扱えれば出番はあったのだが...
     */
    public void setCurrentMessageByCategory(String category) {
        try {
            Field f = MessageList.class.getDeclaredField(category);
            List<Message> list = (List<Message>)f.get(MessageLoader.getCurrentList());
            this.setCurrentMessage(list.get(random.nextInt(list.size())));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentMessage(Message message) {
        this.currentMessage = message;
        show = message != null;
        showDuration = message == null ? 0 : this.showDurationBase + (message.text.length() * showDurationChar);
        currentValue = 0;
    }

    private void renderVillagerMessage(Minecraft mc, int width, int height, float partialTicks) {
        this.renderVillager(mc, width, height, partialTicks);
        this.renderMessage(mc, width, height, partialTicks);
    }

    private void renderVillager(Minecraft mc, int width, int height, float partialTicks) {
        if (villager.worldObj == null) villager.setWorld(FMLClientHandler.instance().getWorldClient());

        GuiInventory.drawEntityOnScreen(30, 70, 30, 0F, 0F, villager);
    }

    private void renderMessage(Minecraft mc, int width, int height, float partialTicks) {
        if (currentMessage != null) this.drawText(mc, 60, 36, currentMessage);
    }

    private void drawText(Minecraft mc, int x, int y, Message message) {
        if (message.vertical) {
            this.drawTextVertical(mc, x, y, message.text);
        } else {
            this.drawTextHorizontal(mc, x, y, message.text);
        }
    }

    private void drawTextHorizontal(Minecraft mc, int x, int y, String text) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0F, 0F, 50.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0F, 0.0F, 0.0F);

        //render start
        int topPos = y - ((mc.fontRendererObj.listFormattedStringToWidth(text, mc.fontRendererObj.getStringWidth(text)).size() * mc.fontRendererObj.FONT_HEIGHT) / 2);
        mc.fontRendererObj.drawSplitString(text, x, topPos, mc.fontRendererObj.getStringWidth(text), 0xFFFFFF);
        //render end

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    private void drawTextVertical(Minecraft mc, int x, int y, String text) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0F, 0F, 50.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0F, 0.0F, 0.0F);

        //render start
        List<String> list = mc.fontRendererObj.listFormattedStringToWidth(text, mc.fontRendererObj.getStringWidth(text));
        int line = list.size();
        String maxLengthStr = list.stream().max((o1, o2) -> o2.length() - o1.length()).get();
        int topPos = y - ((maxLengthStr.length() * mc.fontRendererObj.FONT_HEIGHT) / 2);
        int i = 0;


        // 左から右に描画
        for (String txt : (List<String>)Lists.reverse(list)) {
            int j = 0;

            for (char c : txt.toCharArray()) {
                mc.fontRendererObj.drawStringWithShadow(String.valueOf(c), x + (i * FONT_MULTI_WIDTH), topPos + (j * mc.fontRendererObj.FONT_HEIGHT), 0xFFFFFF);
                j++;
            }

            i++;
        }
        //render end

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}
