package io.github.blockneko11.nekocore.util.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

public final class PosUtil {
    public static BlockPos floored(Vec3d pos) {
        return BlockPos.ofFloored(pos);
    }

    public static Vec3d centered(BlockPos pos) {
        return pos.toCenterPos();
    }

    public static ChunkPos chunked(BlockPos pos) {
        return new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);
    }

    private PosUtil() {
    }
}
