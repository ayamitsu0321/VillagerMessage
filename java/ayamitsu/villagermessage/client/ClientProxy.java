package ayamitsu.villagermessage.client;

import ayamitsu.villagermessage.AbstractProxy;
import ayamitsu.villagermessage.client.event.LanguageReloadListener;
import ayamitsu.villagermessage.client.event.RenderMessageEvent;
import ayamitsu.villagermessage.client.message.MessageLoader;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.io.IOException;

/**
 * Created by ayamitsu0321 on 2016/03/15.
 */
public class ClientProxy extends AbstractProxy {

    public void init() {
        ((SimpleReloadableResourceManager)FMLClientHandler.instance().getClient().getResourceManager()).registerReloadListener(new LanguageReloadListener());
        MinecraftForge.EVENT_BUS.register(new RenderMessageEvent());

        try {
            MessageLoader.loadMessage(FMLClientHandler.instance().getClient().getLanguageManager().getCurrentLanguage().getLanguageCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
