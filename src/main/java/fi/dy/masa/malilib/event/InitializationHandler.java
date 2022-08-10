package fi.dy.masa.malilib.event;

import java.util.ArrayList;
import java.util.List;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.interfaces.IInitializationDispatcher;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;

public class InitializationHandler implements IInitializationDispatcher
{
    private static final InitializationHandler INSTANCE = new InitializationHandler();

    private final List<IInitializationHandler> handlers = new ArrayList<>();

    public static IInitializationDispatcher getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerInitializationHandler(IInitializationHandler handler)
    {
        if (this.handlers.contains(handler) == false)
        {
            this.handlers.add(handler);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onGameInitDone()
    {
        if (this.handlers.isEmpty() == false)
        {
            for (IInitializationHandler handler : this.handlers)
            {
                handler.registerModHandlers();
            }
        }

        ((ConfigManager) ConfigManager.getInstance()).loadAllConfigs();
        InputEventHandler.getKeybindManager().updateUsedKeys();
    }
}
