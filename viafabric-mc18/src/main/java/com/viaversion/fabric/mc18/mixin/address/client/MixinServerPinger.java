package com.viaversion.fabric.mc18.mixin.address.client;

import com.viaversion.fabric.common.VFAddressParser;
import net.minecraft.client.network.MultiplayerServerListPinger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Mixin(MultiplayerServerListPinger.class)
public class MixinServerPinger {
    @Redirect(method = "add", at = @At(value = "INVOKE",
            target = "Ljava/net/InetAddress;getByName(Ljava/lang/String;)Ljava/net/InetAddress;"))
    private InetAddress resolveViaFabricAddr(String address) throws UnknownHostException {
        VFAddressParser viaAddr = new VFAddressParser().parse(address);
        if (viaAddr.viaSuffix == null) {
            return InetAddress.getByName(address);
        }

        InetAddress resolved = InetAddress.getByName(viaAddr.realAddress);
        return InetAddress.getByAddress(resolved.getHostName() + "." + viaAddr.viaSuffix, resolved.getAddress());
    }
}
