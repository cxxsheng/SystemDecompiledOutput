package android.graphics.fonts;

/* loaded from: classes.dex */
public final class FontFamily {
    private static final java.lang.String TAG = "FontFamily";
    private final long mNativePtr;

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetFont(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetFontSize(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nGetLangTags(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetVariant(long j);

    public static final class Builder {
        private static final int TAG_ital = 1769234796;
        private static final int TAG_wght = 2003265652;
        public static final int VARIABLE_FONT_FAMILY_TYPE_NONE = 0;
        public static final int VARIABLE_FONT_FAMILY_TYPE_SINGLE_FONT_WGHT_ITAL = 2;
        public static final int VARIABLE_FONT_FAMILY_TYPE_SINGLE_FONT_WGHT_ONLY = 1;
        public static final int VARIABLE_FONT_FAMILY_TYPE_TWO_FONTS_WGHT = 3;
        public static final int VARIABLE_FONT_FAMILY_TYPE_UNKNOWN = -1;
        private static final libcore.util.NativeAllocationRegistry sFamilyRegistory = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.fonts.FontFamily.class.getClassLoader(), nGetReleaseNativeFamily());
        private final java.util.ArrayList<android.graphics.fonts.Font> mFonts = new java.util.ArrayList<>();
        private final android.util.SparseIntArray mStyles = new android.util.SparseIntArray(4);

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface VariableFontFamilyType {
        }

        @dalvik.annotation.optimization.CriticalNative
        private static native void nAddFont(long j, long j2);

        private static native long nBuild(long j, java.lang.String str, int i, boolean z, boolean z2, int i2);

        @dalvik.annotation.optimization.CriticalNative
        private static native long nGetReleaseNativeFamily();

        private static native long nInitBuilder();

        public Builder(android.graphics.fonts.Font font) {
            com.android.internal.util.Preconditions.checkNotNull(font, "font can not be null");
            this.mStyles.append(makeStyleIdentifier(font), 0);
            this.mFonts.add(font);
        }

        public android.graphics.fonts.FontFamily.Builder addFont(android.graphics.fonts.Font font) {
            com.android.internal.util.Preconditions.checkNotNull(font, "font can not be null");
            int makeStyleIdentifier = makeStyleIdentifier(font);
            if (this.mStyles.indexOfKey(makeStyleIdentifier) >= 0) {
                throw new java.lang.IllegalArgumentException(font + " has already been added");
            }
            this.mStyles.append(makeStyleIdentifier, 0);
            this.mFonts.add(font);
            return this;
        }

        public android.graphics.fonts.FontFamily buildVariableFamily() {
            int analyzeAndResolveVariableType = analyzeAndResolveVariableType(this.mFonts);
            if (analyzeAndResolveVariableType == -1) {
                return null;
            }
            return build("", 0, true, false, analyzeAndResolveVariableType);
        }

        public android.graphics.fonts.FontFamily build() {
            return build("", 0, true, false, 0);
        }

        public android.graphics.fonts.FontFamily build(java.lang.String str, int i, boolean z, boolean z2, int i2) {
            long nInitBuilder = nInitBuilder();
            for (int i3 = 0; i3 < this.mFonts.size(); i3++) {
                nAddFont(nInitBuilder, this.mFonts.get(i3).getNativePtr());
            }
            long nBuild = nBuild(nInitBuilder, str, i, z, z2, i2);
            android.graphics.fonts.FontFamily fontFamily = new android.graphics.fonts.FontFamily(nBuild);
            sFamilyRegistory.registerNativeAllocation(fontFamily, nBuild);
            return fontFamily;
        }

        private static int makeStyleIdentifier(android.graphics.fonts.Font font) {
            return (font.getStyle().getSlant() << 16) | font.getStyle().getWeight();
        }

        public static int analyzeAndResolveVariableType(java.util.ArrayList<android.graphics.fonts.Font> arrayList) {
            if (arrayList.size() > 2) {
                return -1;
            }
            if (arrayList.size() == 1) {
                android.graphics.fonts.Font font = arrayList.get(0);
                java.util.Set<java.lang.Integer> supportedAxes = android.graphics.fonts.FontFileUtil.getSupportedAxes(font.getBuffer(), font.getTtcIndex());
                if (supportedAxes.contains(java.lang.Integer.valueOf(TAG_wght))) {
                    return supportedAxes.contains(java.lang.Integer.valueOf(TAG_ital)) ? 2 : 1;
                }
                return -1;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                android.graphics.fonts.Font font2 = arrayList.get(i);
                if (!android.graphics.fonts.FontFileUtil.getSupportedAxes(font2.getBuffer(), font2.getTtcIndex()).contains(java.lang.Integer.valueOf(TAG_wght))) {
                    return -1;
                }
            }
            boolean z = arrayList.get(0).getStyle().getSlant() == 1;
            if (z == (arrayList.get(1).getStyle().getSlant() == 1)) {
                return -1;
            }
            if (z) {
                android.graphics.fonts.Font font3 = arrayList.get(0);
                arrayList.set(0, arrayList.get(1));
                arrayList.set(1, font3);
                return 3;
            }
            return 3;
        }
    }

    public FontFamily(long j) {
        this.mNativePtr = j;
    }

    public java.lang.String getLangTags() {
        return nGetLangTags(this.mNativePtr);
    }

    public int getVariant() {
        return nGetVariant(this.mNativePtr);
    }

    public android.graphics.fonts.Font getFont(int i) {
        if (i < 0 || getSize() <= i) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return new android.graphics.fonts.Font(nGetFont(this.mNativePtr, i));
    }

    public int getSize() {
        return nGetFontSize(this.mNativePtr);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }
}
