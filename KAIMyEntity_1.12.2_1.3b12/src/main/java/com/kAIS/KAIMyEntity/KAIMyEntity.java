package com.kAIS.KAIMyEntity;

import com.kAIS.KAIMyEntity.register.KAIMyEntityRegisterClient;
import com.kAIS.KAIMyEntity.register.KAIMyEntityRegisterCommon;
import com.kAIS.KAIMyEntity.renderer.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = KAIMyEntity.MODID, name = KAIMyEntity.NAME, version = KAIMyEntity.VERSION, guiFactory = "com.kAIS.KAIMyEntity.config.KAIMyEntityConfigGUIFactory")
public class KAIMyEntity
{
    public static final String MODID = "kaimyentity";
    public static final String NAME = "KAIMyEntity";
    public static final String VERSION = "1.3b12";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception
    {
        logger = event.getModLog();
        logger.info("KAIMyEntity preInit begin...");

        KAIMyEntityRegisterCommon.Regist();

        if (event.getSide() == Side.CLIENT)
        {
            if (!NativeFunc.GetInst().GetVersion().equals("1.1b4"))
            {
                throw new Exception("Cannot load native library: KAIMyEntitySaba. Version Error. Use the match version of KAIMyEntitySaba.dll.");
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

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
