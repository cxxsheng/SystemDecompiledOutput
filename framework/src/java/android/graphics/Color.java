package android.graphics;

/* loaded from: classes.dex */
public class Color {
    public static final int BLACK = -16777216;
    public static final int BLUE = -16776961;
    public static final int CYAN = -16711681;
    public static final int DKGRAY = -12303292;
    public static final int GRAY = -7829368;
    public static final int GREEN = -16711936;
    public static final int LTGRAY = -3355444;
    public static final int MAGENTA = -65281;
    public static final int RED = -65536;
    public static final int TRANSPARENT = 0;
    public static final int WHITE = -1;
    public static final int YELLOW = -256;
    private static final java.util.HashMap<java.lang.String, java.lang.Integer> sColorNameMap = new java.util.HashMap<>();
    private final android.graphics.ColorSpace mColorSpace;
    private final float[] mComponents;

    private static native int nativeHSVToColor(int i, float[] fArr);

    private static native void nativeRGBToHSV(int i, int i2, int i3, float[] fArr);

    public Color() {
        this.mComponents = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        this.mColorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
    }

    private Color(float f, float f2, float f3, float f4) {
        this(f, f2, f3, f4, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    private Color(float f, float f2, float f3, float f4, android.graphics.ColorSpace colorSpace) {
        this.mComponents = new float[]{f, f2, f3, f4};
        this.mColorSpace = colorSpace;
    }

    private Color(float[] fArr, android.graphics.ColorSpace colorSpace) {
        this.mComponents = fArr;
        this.mColorSpace = colorSpace;
    }

    public android.graphics.ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    public android.graphics.ColorSpace.Model getModel() {
        return this.mColorSpace.getModel();
    }

    public boolean isWideGamut() {
        return getColorSpace().isWideGamut();
    }

    public boolean isSrgb() {
        return getColorSpace().isSrgb();
    }

    public int getComponentCount() {
        return this.mColorSpace.getComponentCount() + 1;
    }

    public long pack() {
        return pack(this.mComponents[0], this.mComponents[1], this.mComponents[2], this.mComponents[3], this.mColorSpace);
    }

    public android.graphics.Color convert(android.graphics.ColorSpace colorSpace) {
        android.graphics.ColorSpace.Connector connect = android.graphics.ColorSpace.connect(this.mColorSpace, colorSpace);
        float[] fArr = {this.mComponents[0], this.mComponents[1], this.mComponents[2], this.mComponents[3]};
        connect.transform(fArr);
        return new android.graphics.Color(fArr, colorSpace);
    }

    public int toArgb() {
        if (this.mColorSpace.isSrgb()) {
            return (((int) ((this.mComponents[3] * 255.0f) + 0.5f)) << 24) | (((int) ((this.mComponents[0] * 255.0f) + 0.5f)) << 16) | (((int) ((this.mComponents[1] * 255.0f) + 0.5f)) << 8) | ((int) ((this.mComponents[2] * 255.0f) + 0.5f));
        }
        float[] fArr = {this.mComponents[0], this.mComponents[1], this.mComponents[2], this.mComponents[3]};
        android.graphics.ColorSpace.connect(this.mColorSpace).transform(fArr);
        return (((int) ((fArr[3] * 255.0f) + 0.5f)) << 24) | (((int) ((fArr[0] * 255.0f) + 0.5f)) << 16) | (((int) ((fArr[1] * 255.0f) + 0.5f)) << 8) | ((int) ((fArr[2] * 255.0f) + 0.5f));
    }

    public float red() {
        return this.mComponents[0];
    }

    public float green() {
        return this.mComponents[1];
    }

    public float blue() {
        return this.mComponents[2];
    }

    public float alpha() {
        return this.mComponents[this.mComponents.length - 1];
    }

    public float[] getComponents() {
        return java.util.Arrays.copyOf(this.mComponents, this.mComponents.length);
    }

    public float[] getComponents(float[] fArr) {
        if (fArr == null) {
            return java.util.Arrays.copyOf(this.mComponents, this.mComponents.length);
        }
        if (fArr.length < this.mComponents.length) {
            throw new java.lang.IllegalArgumentException("The specified array's length must be at least " + this.mComponents.length);
        }
        java.lang.System.arraycopy(this.mComponents, 0, fArr, 0, this.mComponents.length);
        return fArr;
    }

    public float getComponent(int i) {
        return this.mComponents[i];
    }

    public float luminance() {
        if (this.mColorSpace.getModel() != android.graphics.ColorSpace.Model.RGB) {
            throw new java.lang.IllegalArgumentException("The specified color must be encoded in an RGB color space. The supplied color space is " + this.mColorSpace.getModel());
        }
        java.util.function.DoubleUnaryOperator eotf = ((android.graphics.ColorSpace.Rgb) this.mColorSpace).getEotf();
        return saturate((float) ((eotf.applyAsDouble(this.mComponents[0]) * 0.2126d) + (eotf.applyAsDouble(this.mComponents[1]) * 0.7152d) + (eotf.applyAsDouble(this.mComponents[2]) * 0.0722d)));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.Color color = (android.graphics.Color) obj;
        if (!java.util.Arrays.equals(this.mComponents, color.mComponents)) {
            return false;
        }
        return this.mColorSpace.equals(color.mColorSpace);
    }

    public int hashCode() {
        return (java.util.Arrays.hashCode(this.mComponents) * 31) + this.mColorSpace.hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Color(");
        for (float f : this.mComponents) {
            sb.append(f).append(", ");
        }
        sb.append(this.mColorSpace.getName());
        sb.append(')');
        return sb.toString();
    }

    public static android.graphics.ColorSpace colorSpace(long j) {
        return android.graphics.ColorSpace.get((int) (j & 63));
    }

    public static float red(long j) {
        return (63 & j) == 0 ? ((j >> 48) & 255) / 255.0f : android.util.Half.toFloat((short) ((j >> 48) & 65535));
    }

    public static float green(long j) {
        return (63 & j) == 0 ? ((j >> 40) & 255) / 255.0f : android.util.Half.toFloat((short) ((j >> 32) & 65535));
    }

    public static float blue(long j) {
        return (63 & j) == 0 ? ((j >> 32) & 255) / 255.0f : android.util.Half.toFloat((short) ((j >> 16) & 65535));
    }

    public static float alpha(long j) {
        return (63 & j) == 0 ? ((j >> 56) & 255) / 255.0f : ((j >> 6) & 1023) / 1023.0f;
    }

    public static boolean isSrgb(long j) {
        return colorSpace(j).isSrgb();
    }

    public static boolean isWideGamut(long j) {
        return colorSpace(j).isWideGamut();
    }

    public static boolean isInColorSpace(long j, android.graphics.ColorSpace colorSpace) {
        return ((int) (j & 63)) == colorSpace.getId();
    }

    public static int toArgb(long j) {
        if ((63 & j) == 0) {
            return (int) (j >> 32);
        }
        float red = red(j);
        float green = green(j);
        float blue = blue(j);
        float alpha = alpha(j);
        float[] transform = android.graphics.ColorSpace.connect(colorSpace(j)).transform(red, green, blue);
        return ((int) ((transform[2] * 255.0f) + 0.5f)) | (((int) ((alpha * 255.0f) + 0.5f)) << 24) | (((int) ((transform[0] * 255.0f) + 0.5f)) << 16) | (((int) ((transform[1] * 255.0f) + 0.5f)) << 8);
    }

    public static android.graphics.Color valueOf(int i) {
        return new android.graphics.Color(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    public static android.graphics.Color valueOf(long j) {
        return new android.graphics.Color(red(j), green(j), blue(j), alpha(j), colorSpace(j));
    }

    public static android.graphics.Color valueOf(float f, float f2, float f3) {
        return new android.graphics.Color(f, f2, f3, 1.0f);
    }

    public static android.graphics.Color valueOf(float f, float f2, float f3, float f4) {
        return new android.graphics.Color(saturate(f), saturate(f2), saturate(f3), saturate(f4));
    }

    public static android.graphics.Color valueOf(float f, float f2, float f3, float f4, android.graphics.ColorSpace colorSpace) {
        if (colorSpace.getComponentCount() > 3) {
            throw new java.lang.IllegalArgumentException("The specified color space must use a color model with at most 3 color components");
        }
        return new android.graphics.Color(f, f2, f3, f4, colorSpace);
    }

    public static android.graphics.Color valueOf(float[] fArr, android.graphics.ColorSpace colorSpace) {
        if (fArr.length < colorSpace.getComponentCount() + 1) {
            throw new java.lang.IllegalArgumentException("Received a component array of length " + fArr.length + " but the color model requires " + (colorSpace.getComponentCount() + 1) + " (including alpha)");
        }
        return new android.graphics.Color(java.util.Arrays.copyOf(fArr, colorSpace.getComponentCount() + 1), colorSpace);
    }

    public static long pack(int i) {
        return (i & 4294967295L) << 32;
    }

    public static long pack(float f, float f2, float f3) {
        return pack(f, f2, f3, 1.0f, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    public static long pack(float f, float f2, float f3, float f4) {
        return pack(f, f2, f3, f4, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    public static long pack(float f, float f2, float f3, float f4, android.graphics.ColorSpace colorSpace) {
        if (colorSpace.isSrgb()) {
            return (((((((int) ((f * 255.0f) + 0.5f)) << 16) | (((int) ((f4 * 255.0f) + 0.5f)) << 24)) | (((int) ((f2 * 255.0f) + 0.5f)) << 8)) | ((int) ((f3 * 255.0f) + 0.5f))) & 4294967295L) << 32;
        }
        int id = colorSpace.getId();
        if (id == -1) {
            throw new java.lang.IllegalArgumentException("Unknown color space, please use a color space returned by ColorSpace.get()");
        }
        if (colorSpace.getComponentCount() > 3) {
            throw new java.lang.IllegalArgumentException("The color space must use a color model with at most 3 components");
        }
        return ((android.util.Half.toHalf(f2) & 65535) << 32) | ((android.util.Half.toHalf(f) & 65535) << 48) | ((android.util.Half.toHalf(f3) & 65535) << 16) | ((((int) ((java.lang.Math.max(0.0f, java.lang.Math.min(f4, 1.0f)) * 1023.0f) + 0.5f)) & 1023) << 6) | (id & 63);
    }

    public static long convert(int i, android.graphics.ColorSpace colorSpace) {
        return convert(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB), colorSpace);
    }

    public static long convert(long j, android.graphics.ColorSpace colorSpace) {
        return convert(red(j), green(j), blue(j), alpha(j), colorSpace(j), colorSpace);
    }

    public static long convert(float f, float f2, float f3, float f4, android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace colorSpace2) {
        float[] transform = android.graphics.ColorSpace.connect(colorSpace, colorSpace2).transform(f, f2, f3);
        return pack(transform[0], transform[1], transform[2], f4, colorSpace2);
    }

    public static long convert(long j, android.graphics.ColorSpace.Connector connector) {
        return convert(red(j), green(j), blue(j), alpha(j), connector);
    }

    public static long convert(float f, float f2, float f3, float f4, android.graphics.ColorSpace.Connector connector) {
        float[] transform = connector.transform(f, f2, f3);
        return pack(transform[0], transform[1], transform[2], f4, connector.getDestination());
    }

    public static float luminance(long j) {
        android.graphics.ColorSpace colorSpace = colorSpace(j);
        if (colorSpace.getModel() != android.graphics.ColorSpace.Model.RGB) {
            throw new java.lang.IllegalArgumentException("The specified color must be encoded in an RGB color space. The supplied color space is " + colorSpace.getModel());
        }
        java.util.function.DoubleUnaryOperator eotf = ((android.graphics.ColorSpace.Rgb) colorSpace).getEotf();
        return saturate((float) ((eotf.applyAsDouble(red(j)) * 0.2126d) + (eotf.applyAsDouble(green(j)) * 0.7152d) + (eotf.applyAsDouble(blue(j)) * 0.0722d)));
    }

    private static float saturate(float f) {
        float f2 = 0.0f;
        if (f > 0.0f) {
            f2 = 1.0f;
            if (f < 1.0f) {
                return f;
            }
        }
        return f2;
    }

    public static int alpha(int i) {
        return i >>> 24;
    }

    public static int red(int i) {
        return (i >> 16) & 255;
    }

    public static int green(int i) {
        return (i >> 8) & 255;
    }

    public static int blue(int i) {
        return i & 255;
    }

    public static int rgb(int i, int i2, int i3) {
        return (i << 16) | (-16777216) | (i2 << 8) | i3;
    }

    public static int rgb(float f, float f2, float f3) {
        return (((int) ((f * 255.0f) + 0.5f)) << 16) | (-16777216) | (((int) ((f2 * 255.0f) + 0.5f)) << 8) | ((int) ((f3 * 255.0f) + 0.5f));
    }

    public static int argb(int i, int i2, int i3, int i4) {
        return (i << 24) | (i2 << 16) | (i3 << 8) | i4;
    }

    public static int argb(float f, float f2, float f3, float f4) {
        return (((int) ((f * 255.0f) + 0.5f)) << 24) | (((int) ((f2 * 255.0f) + 0.5f)) << 16) | (((int) ((f3 * 255.0f) + 0.5f)) << 8) | ((int) ((f4 * 255.0f) + 0.5f));
    }

    public static float luminance(int i) {
        java.util.function.DoubleUnaryOperator eotf = ((android.graphics.ColorSpace.Rgb) android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB)).getEotf();
        return (float) ((eotf.applyAsDouble(red(i) / 255.0d) * 0.2126d) + (eotf.applyAsDouble(green(i) / 255.0d) * 0.7152d) + (eotf.applyAsDouble(blue(i) / 255.0d) * 0.0722d));
    }

    public static int parseColor(java.lang.String str) {
        if (str.charAt(0) == '#') {
            long parseLong = java.lang.Long.parseLong(str.substring(1), 16);
            if (str.length() == 7) {
                parseLong |= -16777216;
            } else if (str.length() != 9) {
                throw new java.lang.IllegalArgumentException("Unknown color");
            }
            return (int) parseLong;
        }
        java.lang.Integer num = sColorNameMap.get(str.toLowerCase(java.util.Locale.ROOT));
        if (num != null) {
            return num.intValue();
        }
        throw new java.lang.IllegalArgumentException("Unknown color");
    }

    public static void RGBToHSV(int i, int i2, int i3, float[] fArr) {
        if (fArr.length < 3) {
            throw new java.lang.RuntimeException("3 components required for hsv");
        }
        nativeRGBToHSV(i, i2, i3, fArr);
    }

    public static void colorToHSV(int i, float[] fArr) {
        RGBToHSV((i >> 16) & 255, (i >> 8) & 255, i & 255, fArr);
    }

    public static int HSVToColor(float[] fArr) {
        return HSVToColor(255, fArr);
    }

    public static int HSVToColor(int i, float[] fArr) {
        if (fArr.length < 3) {
            throw new java.lang.RuntimeException("3 components required for hsv");
        }
        return nativeHSVToColor(i, fArr);
    }

    static {
        sColorNameMap.put("black", -16777216);
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap = sColorNameMap;
        java.lang.Integer valueOf = java.lang.Integer.valueOf(DKGRAY);
        hashMap.put("darkgray", valueOf);
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap2 = sColorNameMap;
        java.lang.Integer valueOf2 = java.lang.Integer.valueOf(GRAY);
        hashMap2.put("gray", valueOf2);
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap3 = sColorNameMap;
        java.lang.Integer valueOf3 = java.lang.Integer.valueOf(LTGRAY);
        hashMap3.put("lightgray", valueOf3);
        sColorNameMap.put("white", -1);
        sColorNameMap.put("red", -65536);
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap4 = sColorNameMap;
        java.lang.Integer valueOf4 = java.lang.Integer.valueOf(GREEN);
        hashMap4.put("green", valueOf4);
        sColorNameMap.put("blue", -16776961);
        sColorNameMap.put("yellow", -256);
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap5 = sColorNameMap;
        java.lang.Integer valueOf5 = java.lang.Integer.valueOf(CYAN);
        hashMap5.put("cyan", valueOf5);
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap6 = sColorNameMap;
        java.lang.Integer valueOf6 = java.lang.Integer.valueOf(MAGENTA);
        hashMap6.put("magenta", valueOf6);
        sColorNameMap.put(android.hardware.Camera.Parameters.EFFECT_AQUA, valueOf5);
        sColorNameMap.put("fuchsia", valueOf6);
        sColorNameMap.put("darkgrey", valueOf);
        sColorNameMap.put("grey", valueOf2);
        sColorNameMap.put("lightgrey", valueOf3);
        sColorNameMap.put("lime", valueOf4);
        sColorNameMap.put("maroon", -8388608);
        sColorNameMap.put("navy", -16777088);
        sColorNameMap.put("olive", -8355840);
        sColorNameMap.put("purple", -8388480);
        sColorNameMap.put("silver", -4144960);
        sColorNameMap.put("teal", -16744320);
    }
}
