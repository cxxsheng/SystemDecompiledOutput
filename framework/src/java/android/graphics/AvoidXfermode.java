package android.graphics;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class AvoidXfermode extends android.graphics.Xfermode {

    public enum Mode {
        AVOID(0),
        TARGET(1);

        final int nativeInt;

        Mode(int i) {
            this.nativeInt = i;
        }
    }

    public AvoidXfermode(int i, int i2, android.graphics.AvoidXfermode.Mode mode) {
        if (i2 < 0 || i2 > 255) {
            throw new java.lang.IllegalArgumentException("tolerance must be 0..255");
        }
    }
}
