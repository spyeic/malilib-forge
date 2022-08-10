package fi.dy.masa.malilib.util;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class EntityUtils
{
    /**
     * Returns the camera entity, if it's not null, otherwise returns the client player entity.
     * @return
     */
    @Nullable
    public static Entity getCameraEntity()
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        Entity entity = mc.getCameraEntity();

        if (entity == null)
        {
            entity = mc.player;
        }

        return entity;
    }
}
