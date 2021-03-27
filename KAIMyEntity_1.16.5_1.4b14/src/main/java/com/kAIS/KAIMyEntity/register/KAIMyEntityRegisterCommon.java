package com.kAIS.KAIMyEntity.register;

import com.kAIS.KAIMyEntity.network.KAIMyEntityNetworkPack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class KAIMyEntityRegisterCommon
{
    public static void Regist()
    {
        channel = NetworkRegistry.newSimpleChannel(new ResourceLocation("kaimyentity", "network_pack"), () -> { return networkVersion; }, (version) -> { return version.equals(networkVersion); }, (version) -> { return version.equals(networkVersion); });
        channel.registerMessage(0, KAIMyEntityNetworkPack.class, KAIMyEntityNetworkPack::Pack, KAIMyEntityNetworkPack::new, KAIMyEntityNetworkPack::Do);
    }

    public static SimpleChannel channel;
    static String networkVersion = "1";
}
