package android.graphics;

/* loaded from: classes.dex */
public class PorterDuff {

    public enum Mode {
        CLEAR(0),
        SRC(1),
        DST(2),
        SRC_OVER(3),
        DST_OVER(4),
        SRC_IN(5),
        DST_IN(6),
        SRC_OUT(7),
        DST_OUT(8),
        SRC_ATOP(9),
        DST_ATOP(10),
        XOR(11),
        DARKEN(16),
        LIGHTEN(17),
        MULTIPLY(13),
        SCREEN(14),
        ADD(12),
        OVERLAY(15);

        public final int nativeInt;

        Mode(int i) {
            this.nativeInt = i;
        }
    }

    public static int modeToInt(android.graphics.PorterDuff.Mode mode) {
        return mode.nativeInt;
    }

    public static android.graphics.PorterDuff.Mode intToMode(int i) {
        switch (i) {
            case 1:
                return android.graphics.PorterDuff.Mode.SRC;
            case 2:
                return android.graphics.PorterDuff.Mode.DST;
            case 3:
                return android.graphics.PorterDuff.Mode.SRC_OVER;
            case 4:
                return android.graphics.PorterDuff.Mode.DST_OVER;
            case 5:
                return android.graphics.PorterDuff.Mode.SRC_IN;
            case 6:
                return android.graphics.PorterDuff.Mode.DST_IN;
            case 7:
                return android.graphics.PorterDuff.Mode.SRC_OUT;
            case 8:
                return android.graphics.PorterDuff.Mode.DST_OUT;
            case 9:
                return android.graphics.PorterDuff.Mode.SRC_ATOP;
            case 10:
                return android.graphics.PorterDuff.Mode.DST_ATOP;
            case 11:
                return android.graphics.PorterDuff.Mode.XOR;
            case 12:
                return android.graphics.PorterDuff.Mode.ADD;
            case 13:
                return android.graphics.PorterDuff.Mode.MULTIPLY;
            case 14:
                return android.graphics.PorterDuff.Mode.SCREEN;
            case 15:
                return android.graphics.PorterDuff.Mode.OVERLAY;
            case 16:
                return android.graphics.PorterDuff.Mode.DARKEN;
            case 17:
                return android.graphics.PorterDuff.Mode.LIGHTEN;
            default:
                return android.graphics.PorterDuff.Mode.CLEAR;
        }
    }
}
