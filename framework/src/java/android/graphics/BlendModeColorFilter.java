package android.graphics;

/* loaded from: classes.dex */
public final class BlendModeColorFilter extends android.graphics.ColorFilter {
    final int mColor;
    private final android.graphics.BlendMode mMode;

    private static native long native_CreateBlendModeFilter(int i, int i2);

    public BlendModeColorFilter(int i, android.graphics.BlendMode blendMode) {
        this.mColor = i;
        this.mMode = blendMode;
    }

    public int getColor() {
        return this.mColor;
    }

    public android.graphics.BlendMode getMode() {
        return this.mMode;
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return native_CreateBlendModeFilter(this.mColor, this.mMode.getXfermode().porterDuffMode);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && ((android.graphics.BlendModeColorFilter) obj).mMode == this.mMode) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.mMode.hashCode() * 31) + this.mColor;
    }
}
