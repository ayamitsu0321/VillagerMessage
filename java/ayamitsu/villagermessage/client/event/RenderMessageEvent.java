package ayamitsu.villagermessage.client.event;

import ayamitsu.villagermessage.client.message.Messages;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by ayamitsu0321 on 2016/03/15.
 */
@SideOnly(Side.CLIENT)
public class RenderMessageEvent {

    @SubscribeEvent
    public void postRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        Messages.renderer.updatePost(event);
    }

}
