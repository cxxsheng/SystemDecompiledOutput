package android.graphics;

/* loaded from: classes.dex */
public enum BlendMode {
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
    PLUS(12),
    MODULATE(13),
    SCREEN(14),
    OVERLAY(15),
    DARKEN(16),
    LIGHTEN(17),
    COLOR_DODGE(18),
    COLOR_BURN(19),
    HARD_LIGHT(20),
    SOFT_LIGHT(21),
    DIFFERENCE(22),
    EXCLUSION(23),
    MULTIPLY(24),
    HUE(25),
    SATURATION(26),
    COLOR(27),
    LUMINOSITY(28);

    private static final android.graphics.BlendMode[] BLEND_MODES = values();
    private final android.graphics.Xfermode mXfermode = new android.graphics.Xfermode();

    public static android.graphics.BlendMode fromValue(int i) {
        for (android.graphics.BlendMode blendMode : BLEND_MODES) {
            if (blendMode.mXfermode.porterDuffMode == i) {
                return blendMode;
            }
        }
        return null;
    }

    public static int toValue(android.graphics.BlendMode blendMode) {
        return blendMode.getXfermode().porterDuffMode;
    }

    public static android.graphics.PorterDuff.Mode blendModeToPorterDuffMode(android.graphics.BlendMode blendMode) {
        if (blendMode == null) {
            return null;
        }
        switch (blendMode) {
            case CLEAR:
                return android.graphics.PorterDuff.Mode.CLEAR;
            case SRC:
                return android.graphics.PorterDuff.Mode.SRC;
            case DST:
                return android.graphics.PorterDuff.Mode.DST;
            case SRC_OVER:
                return android.graphics.PorterDuff.Mode.SRC_OVER;
            case DST_OVER:
                return android.graphics.PorterDuff.Mode.DST_OVER;
            case SRC_IN:
                return android.graphics.PorterDuff.Mode.SRC_IN;
            case DST_IN:
                return android.graphics.PorterDuff.Mode.DST_IN;
            case SRC_OUT:
                return android.graphics.PorterDuff.Mode.SRC_OUT;
            case DST_OUT:
                return android.graphics.PorterDuff.Mode.DST_OUT;
            case SRC_ATOP:
                return android.graphics.PorterDuff.Mode.SRC_ATOP;
            case DST_ATOP:
                return android.graphics.PorterDuff.Mode.DST_ATOP;
            case XOR:
                return android.graphics.PorterDuff.Mode.XOR;
            case DARKEN:
                return android.graphics.PorterDuff.Mode.DARKEN;
            case LIGHTEN:
                return android.graphics.PorterDuff.Mode.LIGHTEN;
            case MODULATE:
                return android.graphics.PorterDuff.Mode.MULTIPLY;
            case SCREEN:
                return android.graphics.PorterDuff.Mode.SCREEN;
            case PLUS:
                return android.graphics.PorterDuff.Mode.ADD;
            case OVERLAY:
                return android.graphics.PorterDuff.Mode.OVERLAY;
            default:
                return null;
        }
    }

    BlendMode(int i) {
        this.mXfermode.porterDuffMode = i;
    }

    public android.graphics.Xfermode getXfermode() {
        return this.mXfermode;
    }
}
