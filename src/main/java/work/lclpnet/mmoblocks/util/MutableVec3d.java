package work.lclpnet.mmoblocks.util;

import net.minecraft.util.math.Vec3d;

public class MutableVec3d {
    public double x, y, z;

    public MutableVec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MutableVec3d(Vec3d vec) {
        this(vec.x, vec.y, vec.z);
    }
}
