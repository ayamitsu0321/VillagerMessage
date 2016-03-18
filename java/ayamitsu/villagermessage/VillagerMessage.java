package ayamitsu.villagermessage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = VillagerMessage.MODID,
        name = VillagerMessage.NAME,
        version = VillagerMessage.VERSION
)
public class VillagerMessage {

    public static final String MODID = "villagermessage";
    public static final String NAME = "VillagerMessage";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static VillagerMessage instance;

    @SidedProxy(clientSide = "ayamitsu.villagermessage.client.ClientProxy", serverSide = "ayamitsu.villagermessage.server.ServerProxy")
    public static AbstractProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}