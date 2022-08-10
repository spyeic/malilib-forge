package fi.dy.masa.malilib;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MaLiLibReference.MOD_ID)
public class MaLiLib
{
    public static final Logger logger = LogManager.getLogger(MaLiLibReference.MOD_ID);


    public MaLiLib() {
        InitializationHandler.getInstance().registerInitializationHandler(new MaLiLibInitHandler());

        // Register config GUI
        ModLoadingContext.get().registerExtensionPoint(
                ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory((minecraftClient, screen) -> {
                    MaLiLibConfigGui gui = new MaLiLibConfigGui();
                    gui.setParent(screen);
                    return gui;
                })
        );
    }
}
