package ayamitsu.villagermessage.client.event;

import ayamitsu.villagermessage.client.message.MessageLoader;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.io.IOException;

/**
 * Created by ayamitsu0321 on 2016/03/17.
 */
public class LanguageReloadListener implements IResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        try {
            MessageLoader.loadMessage(FMLClientHandler.instance().getCurrentLanguage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
