package xyz.tildejustin.stater.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import xyz.tildejustin.stater.StateOutputHelper;
import xyz.tildejustin.stater.Stater;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @ModifyConstant(method = "prepareWorlds", constant = @Constant(longValue = 1000L))
    private long modifyLoggingInterval(long interval) {
        return Stater.log_interval;
    }

    @ModifyArg(method = "prepareWorlds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;logProgress(Ljava/lang/String;I)V"),
            index = 1
    )
    private int outputGenerationState(int worldProgress) {
        StateOutputHelper.outputState("generating," + worldProgress);
        return worldProgress;
    }
}
