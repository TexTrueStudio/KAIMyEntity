package com.kAIS.KAIMyEntity;

import com.kAIS.KAIMyEntity.config.KAIMyEntityConfig;
import com.kAIS.KAIMyEntity.register.KAIMyEntityRegisterClient;
import com.kAIS.KAIMyEntity.register.KAIMyEntityRegisterCommon;
import com.kAIS.KAIMyEntity.renderer.MMDAnimManager;
import com.kAIS.KAIMyEntity.renderer.MMDModelManager;
import com.kAIS.KAIMyEntity.renderer.MMDTextureManager;
import com.kAIS.KAIMyEntity.renderer.RenderTimer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("kaimyentity")
public class KAIMyEntity
{
    public static Logger logger = LogManager.getLogger();

    public KAIMyEntity()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, KAIMyEntityConfig.config);
    }

    public void preInit(FMLCommonSetupEvent event)
    {
        logger.info("KAIMyEntity preInit begin...");

        KAIMyEntityRegisterCommon.Regist();

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            if (!NativeFunc.GetInst().GetVersion().equals("1.1b4"))
            {
                logger.info("Cannot load native library: KAIMyEntitySaba. Version Error.");
                event.setCanceled(true);
            }
            else
            {
                logger.info("Renderer mode: OpenGL.");
                RenderTimer.Init();
                MMDModelManager.Init();
                MMDTextureManager.Init();
                MMDAnimManager.Init();
                KAIMyEntityRegisterClient.Regist();
            }
        }
        else
        {
            logger.info("KAIMyEntity running on server.");
        }

        logger.info("KAIMyEntity preInit successful.");
    }
}
