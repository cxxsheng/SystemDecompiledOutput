package android.graphics;

/* loaded from: classes.dex */
public class PorterDuffColorFilter extends android.graphics.ColorFilter {
    private int mColor;
    private android.graphics.PorterDuff.Mode mMode;

    private static native long native_CreateBlendModeFilter(int i, int i2);

    public PorterDuffColorFilter(int i, android.graphics.PorterDuff.Mode mode) {
        this.mColor = i;
        this.mMode = mode;
    }

    public int getColor() {
        return this.mColor;
    }

    public android.graphics.PorterDuff.Mode getMode() {
        return this.mMode;
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return native_CreateBlendModeFilter(this.mColor, this.mMode.nativeInt);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.PorterDuffColorFilter porterDuffColorFilter = (android.graphics.PorterDuffColorFilter) obj;
        if (this.mColor == porterDuffColorFilter.mColor && this.mMode.nativeInt == porterDuffColorFilter.mMode.nativeInt) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.mMode.hashCode() * 31) + this.mColor;
    }
}
