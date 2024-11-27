package android.graphics.fonts;

/* loaded from: classes.dex */
public final class FontStyle {
    public static final int FONT_SLANT_ITALIC = 1;
    public static final int FONT_SLANT_UPRIGHT = 0;
    public static final int FONT_WEIGHT_BLACK = 900;
    public static final int FONT_WEIGHT_BOLD = 700;
    public static final int FONT_WEIGHT_EXTRA_BOLD = 800;
    public static final int FONT_WEIGHT_EXTRA_LIGHT = 200;
    public static final int FONT_WEIGHT_LIGHT = 300;
    public static final int FONT_WEIGHT_MAX = 1000;
    public static final int FONT_WEIGHT_MEDIUM = 500;
    public static final int FONT_WEIGHT_MIN = 1;
    public static final int FONT_WEIGHT_NORMAL = 400;
    public static final int FONT_WEIGHT_SEMI_BOLD = 600;
    public static final int FONT_WEIGHT_THIN = 100;
    public static final int FONT_WEIGHT_UNSPECIFIED = -1;
    private static final java.lang.String TAG = "FontStyle";
    private final int mSlant;
    private final int mWeight;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FontSlant {
    }

    public FontStyle() {
        this.mWeight = 400;
        this.mSlant = 0;
    }

    public FontStyle(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgument(1 <= i && i <= 1000, "weight value must be [1, 1000]");
        com.android.internal.util.Preconditions.checkArgument(i2 == 0 || i2 == 1, "slant value must be FONT_SLANT_UPRIGHT or FONT_SLANT_UPRIGHT");
        this.mWeight = i;
        this.mSlant = i2;
    }

    public int getWeight() {
        return this.mWeight;
    }

    public int getSlant() {
        return this.mSlant;
    }

    public int getMatchScore(android.graphics.fonts.FontStyle fontStyle) {
        return (java.lang.Math.abs(getWeight() - fontStyle.getWeight()) / 100) + (getSlant() == fontStyle.getSlant() ? 0 : 2);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof android.graphics.fonts.FontStyle)) {
            return false;
        }
        android.graphics.fonts.FontStyle fontStyle = (android.graphics.fonts.FontStyle) obj;
        if (fontStyle.mWeight == this.mWeight && fontStyle.mSlant == this.mSlant) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mWeight), java.lang.Integer.valueOf(this.mSlant));
    }

    public java.lang.String toString() {
        return "FontStyle { weight=" + this.mWeight + ", slant=" + this.mSlant + "}";
    }
}
