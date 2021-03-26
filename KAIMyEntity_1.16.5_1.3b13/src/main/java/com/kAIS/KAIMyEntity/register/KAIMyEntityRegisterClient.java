package com.kAIS.KAIMyEntity.register;

import com.kAIS.KAIMyEntity.KAIMyEntity;
import com.kAIS.KAIMyEntity.network.KAIMyEntityNetworkPack;
import com.kAIS.KAIMyEntity.renderer.KAIMyEntityRenderFactory;
import com.kAIS.KAIMyEntity.renderer.KAIMyEntityRendererPlayer;
import com.kAIS.KAIMyEntity.renderer.MMDModelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.io.File;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KAIMyEntityRegisterClient
{
    public static void Regist()
    {
        ClientRegistry.registerKeyBinding(keyResetPhysics);
        ClientRegistry.registerKeyBinding(keyReloadModels);
        ClientRegistry.registerKeyBinding(keyCustomAnim1);
        ClientRegistry.registerKeyBinding(keyCustomAnim2);
        ClientRegistry.registerKeyBinding(keyCustomAnim3);
        ClientRegistry.registerKeyBinding(keyCustomAnim4);

        File modelDir = new File(Minecraft.getInstance().gameDir, "KAIMyEntity");
        File[] allDir = modelDir.listFiles();
        if (allDir != null)
        {
            for (File i : allDir)
            {
                if (!i.getName().equals("EntityPlayer"))
                {
                    String mcEntityName = i.getName().replace('.', ':');
                    try
                    {
                        RenderingRegistry.registerEntityRenderingHandler(EntityType.byKey(mcEntityName).get(), new KAIMyEntityRenderFactory<>(mcEntityName));
                    }
                    catch (Exception e)
                    {
                        KAIMyEntity.logger.info(String.format("Cannot regist entity renderer: %s", mcEntityName));
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event)
    {
        //Renderer Create time: When 3rd view. If use shader then when world entered.
        //Renderer Render time: WHen 3rd view.

        if (event.getEntity() == null)
            return;
        if (KAIMyEntityRendererPlayer.GetInst() == null)
        {
            KAIMyEntityRendererPlayer.Init(event.getRenderer().getRenderManager());
        }
        event.setCanceled(true);

        float f = event.getEntity().prevRotationYaw + (event.getEntity().rotationYaw - event.getEntity().prevRotationYaw) * event.getPartialRenderTick();
        KAIMyEntityRendererPlayer.GetInst().render((PlayerEntity)event.getEntity(), f, event.getPartialRenderTick(), event.getMatrixStack(), event.getBuffers(), event.getLight());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event)
    {
        if (keyCustomAnim1.isKeyDown())
        {
            if (KAIMyEntityRendererPlayer.GetInst() != null)
            {
                KAIMyEntityRendererPlayer.GetInst().CustomAnim(Minecraft.getInstance().player, "1");
                KAIMyEntityRegisterCommon.channel.sendToServer(new KAIMyEntityNetworkPack(1, Minecraft.getInstance().player.getUniqueID(), 1));
            }
        }
        if (keyReloadModels.isKeyDown())
        {
            MMDModelManager.ReloadModel();
        }
        if (keyCustomAnim2.isKeyDown())
        {
            if (KAIMyEntityRendererPlayer.GetInst() != null)
            {
                KAIMyEntityRendererPlayer.GetInst().CustomAnim(Minecraft.getInstance().player, "2");
                KAIMyEntityRegisterCommon.channel.sendToServer(new KAIMyEntityNetworkPack(1, Minecraft.getInstance().player.getUniqueID(), 2));
            }
        }
        if (keyCustomAnim3.isKeyDown())
        {
            if (KAIMyEntityRendererPlayer.GetInst() != null)
            {
                KAIMyEntityRendererPlayer.GetInst().CustomAnim(Minecraft.getInstance().player, "3");
                KAIMyEntityRegisterCommon.channel.sendToServer(new KAIMyEntityNetworkPack(1, Minecraft.getInstance().player.getUniqueID(), 3));
            }
        }
        if (keyCustomAnim4.isKeyDown())
        {
            if (KAIMyEntityRendererPlayer.GetInst() != null)
            {
                KAIMyEntityRendererPlayer.GetInst().CustomAnim(Minecraft.getInstance().player, "4");
                KAIMyEntityRegisterCommon.channel.sendToServer(new KAIMyEntityNetworkPack(1, Minecraft.getInstance().player.getUniqueID(), 4));
            }
        }
        if (keyResetPhysics.isKeyDown())
        {
            if (KAIMyEntityRendererPlayer.GetInst() != null)
            {
                KAIMyEntityRendererPlayer.GetInst().ResetPhysics(Minecraft.getInstance().player);
                KAIMyEntityRegisterCommon.channel.sendToServer(new KAIMyEntityNetworkPack(2, Minecraft.getInstance().player.getUniqueID(), 0));
            }
        }
    }

    static KeyBinding keyResetPhysics = new KeyBinding("key.resetPhysics", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_K, "key.title");
    static KeyBinding keyReloadModels = new KeyBinding("key.reloadModels", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_KP_1, "key.title");
    static KeyBinding keyCustomAnim1 = new KeyBinding("key.customAnim1", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.title");
    static KeyBinding keyCustomAnim2 = new KeyBinding("key.customAnim2", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.title");
    static KeyBinding keyCustomAnim3 = new KeyBinding("key.customAnim3", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_N, "key.title");
    static KeyBinding keyCustomAnim4 = new KeyBinding("key.customAnim4", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_M, "key.title");
}
