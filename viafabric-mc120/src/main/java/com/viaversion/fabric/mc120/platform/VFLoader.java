package com.viaversion.fabric.mc120.platform;

import com.viaversion.fabric.mc120.providers.VFHandItemProvider;
import com.viaversion.fabric.mc120.providers.FabricVersionProvider;
import com.viaversion.fabric.mc120.providers.VFPlayerAbilitiesProvider;
import com.viaversion.fabric.mc120.providers.VFPlayerLookTargetProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PlayerLookTargetProvider;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.provider.PlayerAbilitiesProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;

public class VFLoader implements ViaPlatformLoader {
    @Override
    public void load() {
        Via.getManager().getProviders().use(VersionProvider.class, new FabricVersionProvider());

        if (Via.getPlatform().getConf().isItemCache()) {
            VFHandItemProvider handProvider = new VFHandItemProvider();
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                handProvider.registerClientTick();
            }
            Via.getManager().getProviders().use(HandItemProvider.class, handProvider);
        }

        Via.getManager().getProviders().use(PlayerAbilitiesProvider.class, new VFPlayerAbilitiesProvider());
        Via.getManager().getProviders().use(PlayerLookTargetProvider.class, new VFPlayerLookTargetProvider());
    }

    @Override
    public void unload() {
        // Nothing to do
    }
}
