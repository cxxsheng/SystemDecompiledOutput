package android.graphics;

/* loaded from: classes.dex */
public class Typeface {
    public static final int BOLD = 1;
    public static final int BOLD_ITALIC = 3;
    public static final java.lang.String DEFAULT_FAMILY = "sans-serif";
    public static final boolean ENABLE_LAZY_TYPEFACE_INITIALIZATION = true;
    public static final int ITALIC = 2;
    public static final int NORMAL = 0;
    public static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final int STYLE_ITALIC = 1;
    public static final int STYLE_MASK = 3;
    private static final int STYLE_NORMAL = 0;
    static android.graphics.Typeface sDefaultTypeface;
    static android.graphics.Typeface[] sDefaults;
    private final java.lang.Runnable mCleaner;
    private final int mStyle;
    private int[] mSupportedAxes;
    private final java.lang.String mSystemFontFamilyName;
    private final int mWeight;
    public final long native_instance;
    private static java.lang.String TAG = "Typeface";
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Typeface.class.getClassLoader(), nativeGetReleaseFunc());
    public static final android.graphics.Typeface DEFAULT = null;
    public static final android.graphics.Typeface DEFAULT_BOLD = null;
    public static final android.graphics.Typeface SANS_SERIF = null;
    public static final android.graphics.Typeface SERIF = null;
    public static final android.graphics.Typeface MONOSPACE = null;
    private static final android.util.LongSparseArray<android.util.SparseArray<android.graphics.Typeface>> sStyledTypefaceCache = new android.util.LongSparseArray<>(3);
    private static final java.lang.Object sStyledCacheLock = new java.lang.Object();
    private static final android.util.LongSparseArray<android.util.SparseArray<android.graphics.Typeface>> sWeightTypefaceCache = new android.util.LongSparseArray<>(3);
    private static final java.lang.Object sWeightCacheLock = new java.lang.Object();
    private static final android.util.LruCache<java.lang.String, android.graphics.Typeface> sDynamicTypefaceCache = new android.util.LruCache<>(16);
    private static final java.lang.Object sDynamicCacheLock = new java.lang.Object();
    static final java.util.Map<java.lang.String, android.graphics.Typeface> sSystemFontMap = new android.util.ArrayMap();
    static java.nio.ByteBuffer sSystemFontMapBuffer = null;
    static android.os.SharedMemory sSystemFontMapSharedMemory = null;
    private static final java.lang.Object SYSTEM_FONT_MAP_LOCK = new java.lang.Object();

    @java.lang.Deprecated
    static final java.util.Map<java.lang.String, android.graphics.FontFamily[]> sSystemFallbackMap = java.util.Collections.emptyMap();
    private static final int[] EMPTY_AXES = new int[0];

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Style {
    }

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeAddFontCollections(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateFromArray(long[] jArr, long j, int i, int i2);

    private static native long nativeCreateFromTypeface(long j, int i);

    private static native long nativeCreateFromTypefaceWithExactStyle(long j, int i, boolean z);

    private static native long nativeCreateFromTypefaceWithVariation(long j, java.util.List<android.graphics.fonts.FontVariationAxis> list);

    private static native long nativeCreateWeightAlias(long j, int i);

    private static native void nativeForceSetStaticFinalField(java.lang.String str, android.graphics.Typeface typeface);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nativeGetReleaseFunc();

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetStyle(long j);

    private static native int[] nativeGetSupportedAxes(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetWeight(long j);

    private static native long[] nativeReadTypefaces(java.nio.ByteBuffer byteBuffer, int i);

    private static native void nativeRegisterGenericFamily(java.lang.String str, long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeRegisterLocaleList(java.lang.String str);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetDefault(long j);

    private static native void nativeWarmUpCache(java.lang.String str);

    private static native int nativeWriteTypefaces(java.nio.ByteBuffer byteBuffer, int i, long[] jArr);

    static {
        preloadFontFile("/system/fonts/Roboto-Regular.ttf");
        preloadFontFile("/system/fonts/RobotoStatic-Regular.ttf");
        java.lang.String script = android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLanguageTag(android.os.SystemProperties.get("persist.sys.locale", "en-US"))).getScript();
        android.text.FontConfig systemPreinstalledFontConfigFromLegacyXml = android.graphics.fonts.SystemFonts.getSystemPreinstalledFontConfigFromLegacyXml();
        for (int i = 0; i < systemPreinstalledFontConfigFromLegacyXml.getFontFamilies().size(); i++) {
            android.text.FontConfig.FontFamily fontFamily = systemPreinstalledFontConfigFromLegacyXml.getFontFamilies().get(i);
            if (!fontFamily.getLocaleList().isEmpty()) {
                nativeRegisterLocaleList(fontFamily.getLocaleList().toLanguageTags());
            }
            boolean z = false;
            for (int i2 = 0; i2 < fontFamily.getLocaleList().size() && !(z = android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLocale(fontFamily.getLocaleList().get(i2))).getScript().equals(script)); i2++) {
            }
            if (z) {
                for (int i3 = 0; i3 < fontFamily.getFontList().size(); i3++) {
                    preloadFontFile(fontFamily.getFontList().get(i3).getFile().getAbsolutePath());
                }
            }
        }
    }

    public static android.os.SharedMemory getSystemFontMapSharedMemory() {
        java.util.Objects.requireNonNull(sSystemFontMapSharedMemory);
        return sSystemFontMapSharedMemory;
    }

    private static void setDefault(android.graphics.Typeface typeface) {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            sDefaultTypeface = typeface;
            nativeSetDefault(typeface.native_instance);
        }
    }

    private static android.graphics.Typeface getDefault() {
        android.graphics.Typeface typeface;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            typeface = sDefaultTypeface;
        }
        return typeface;
    }

    public int getWeight() {
        return this.mWeight;
    }

    public int getStyle() {
        return this.mStyle;
    }

    public final boolean isBold() {
        return (this.mStyle & 1) != 0;
    }

    public final boolean isItalic() {
        return (this.mStyle & 2) != 0;
    }

    public final java.lang.String getSystemFontFamilyName() {
        return this.mSystemFontFamilyName;
    }

    private static boolean hasFontFamily(java.lang.String str) {
        boolean containsKey;
        java.util.Objects.requireNonNull(str, "familyName cannot be null");
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            containsKey = sSystemFontMap.containsKey(str);
        }
        return containsKey;
    }

    public static android.graphics.Typeface createFromResources(android.content.res.FontResourcesParser.FamilyResourceEntry familyResourceEntry, android.content.res.AssetManager assetManager, java.lang.String str) {
        android.graphics.Typeface typeface;
        android.graphics.fonts.FontFamily.Builder builder;
        int i;
        if (familyResourceEntry instanceof android.content.res.FontResourcesParser.ProviderResourceEntry) {
            android.content.res.FontResourcesParser.ProviderResourceEntry providerResourceEntry = (android.content.res.FontResourcesParser.ProviderResourceEntry) familyResourceEntry;
            java.lang.String systemFontFamilyName = providerResourceEntry.getSystemFontFamilyName();
            if (systemFontFamilyName != null && hasFontFamily(systemFontFamilyName)) {
                return create(systemFontFamilyName, 0);
            }
            java.util.List<java.util.List<java.lang.String>> certs = providerResourceEntry.getCerts();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (certs != null) {
                for (int i2 = 0; i2 < certs.size(); i2++) {
                    java.util.List<java.lang.String> list = certs.get(i2);
                    java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        arrayList2.add(android.util.Base64.decode(list.get(i3), 0));
                    }
                    arrayList.add(arrayList2);
                }
            }
            android.graphics.Typeface fontSync = android.provider.FontsContract.getFontSync(new android.provider.FontRequest(providerResourceEntry.getAuthority(), providerResourceEntry.getPackage(), providerResourceEntry.getQuery(), arrayList));
            return fontSync == null ? DEFAULT : fontSync;
        }
        android.graphics.Typeface findFromCache = findFromCache(assetManager, str);
        if (findFromCache != null) {
            return findFromCache;
        }
        try {
            android.content.res.FontResourcesParser.FontFileResourceEntry[] entries = ((android.content.res.FontResourcesParser.FontFamilyFilesResourceEntry) familyResourceEntry).getEntries();
            int length = entries.length;
            builder = null;
            int i4 = 0;
            while (true) {
                i = 1;
                if (i4 >= length) {
                    break;
                }
                android.content.res.FontResourcesParser.FontFileResourceEntry fontFileResourceEntry = entries[i4];
                android.graphics.fonts.Font.Builder fontVariationSettings = new android.graphics.fonts.Font.Builder(assetManager, fontFileResourceEntry.getFileName(), false, -1).setTtcIndex(fontFileResourceEntry.getTtcIndex()).setFontVariationSettings(fontFileResourceEntry.getVariationSettings());
                if (fontFileResourceEntry.getWeight() != -1) {
                    fontVariationSettings.setWeight(fontFileResourceEntry.getWeight());
                }
                if (fontFileResourceEntry.getItalic() != -1) {
                    if (fontFileResourceEntry.getItalic() != 1) {
                        i = 0;
                    }
                    fontVariationSettings.setSlant(i);
                }
                if (builder == null) {
                    builder = new android.graphics.fonts.FontFamily.Builder(fontVariationSettings.build());
                } else {
                    builder.addFont(fontVariationSettings.build());
                }
                i4++;
            }
        } catch (java.io.IOException e) {
            typeface = DEFAULT;
        } catch (java.lang.IllegalArgumentException e2) {
            return null;
        }
        if (builder == null) {
            return DEFAULT;
        }
        android.graphics.fonts.FontFamily build = builder.build();
        android.graphics.fonts.FontStyle fontStyle = new android.graphics.fonts.FontStyle(400, 0);
        android.graphics.fonts.Font font = build.getFont(0);
        int matchScore = fontStyle.getMatchScore(font.getStyle());
        while (i < build.getSize()) {
            android.graphics.fonts.Font font2 = build.getFont(i);
            int matchScore2 = fontStyle.getMatchScore(font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
            i++;
        }
        typeface = new android.graphics.Typeface.CustomFallbackBuilder(build).setStyle(font.getStyle()).build();
        synchronized (sDynamicCacheLock) {
            sDynamicTypefaceCache.put(android.graphics.Typeface.Builder.createAssetUid(assetManager, str, 0, null, -1, -1, DEFAULT_FAMILY), typeface);
        }
        return typeface;
    }

    public static android.graphics.Typeface findFromCache(android.content.res.AssetManager assetManager, java.lang.String str) {
        synchronized (sDynamicCacheLock) {
            android.graphics.Typeface typeface = sDynamicTypefaceCache.get(android.graphics.Typeface.Builder.createAssetUid(assetManager, str, 0, null, -1, -1, DEFAULT_FAMILY));
            if (typeface != null) {
                return typeface;
            }
            return null;
        }
    }

    public static final class Builder {
        public static final int BOLD_WEIGHT = 700;
        public static final int NORMAL_WEIGHT = 400;
        private final android.content.res.AssetManager mAssetManager;
        private java.lang.String mFallbackFamilyName;
        private final android.graphics.fonts.Font.Builder mFontBuilder;
        private int mItalic;
        private final java.lang.String mPath;
        private int mWeight;

        public Builder(java.io.File file) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new android.graphics.fonts.Font.Builder(file);
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(java.io.FileDescriptor fileDescriptor) {
            android.graphics.fonts.Font.Builder builder;
            this.mWeight = -1;
            this.mItalic = -1;
            try {
                builder = new android.graphics.fonts.Font.Builder(android.os.ParcelFileDescriptor.dup(fileDescriptor));
            } catch (java.io.IOException e) {
                builder = null;
            }
            this.mFontBuilder = builder;
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(java.lang.String str) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new android.graphics.fonts.Font.Builder(new java.io.File(str));
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(android.content.res.AssetManager assetManager, java.lang.String str) {
            this(assetManager, str, true, 0);
        }

        public Builder(android.content.res.AssetManager assetManager, java.lang.String str, boolean z, int i) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new android.graphics.fonts.Font.Builder(assetManager, str, z, i);
            this.mAssetManager = assetManager;
            this.mPath = str;
        }

        public android.graphics.Typeface.Builder setWeight(int i) {
            this.mWeight = i;
            this.mFontBuilder.setWeight(i);
            return this;
        }

        public android.graphics.Typeface.Builder setItalic(boolean z) {
            this.mItalic = z ? 1 : 0;
            this.mFontBuilder.setSlant(this.mItalic);
            return this;
        }

        public android.graphics.Typeface.Builder setTtcIndex(int i) {
            this.mFontBuilder.setTtcIndex(i);
            return this;
        }

        public android.graphics.Typeface.Builder setFontVariationSettings(java.lang.String str) {
            this.mFontBuilder.setFontVariationSettings(str);
            return this;
        }

        public android.graphics.Typeface.Builder setFontVariationSettings(android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr) {
            this.mFontBuilder.setFontVariationSettings(fontVariationAxisArr);
            return this;
        }

        public android.graphics.Typeface.Builder setFallback(java.lang.String str) {
            this.mFallbackFamilyName = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String createAssetUid(android.content.res.AssetManager assetManager, java.lang.String str, int i, android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr, int i2, int i3, java.lang.String str2) {
            android.util.SparseArray<java.lang.String> assignedPackageIdentifiers = assetManager.getAssignedPackageIdentifiers();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int size = assignedPackageIdentifiers.size();
            for (int i4 = 0; i4 < size; i4++) {
                sb.append(assignedPackageIdentifiers.valueAt(i4));
                sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            sb.append(str);
            sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            sb.append(java.lang.Integer.toString(i));
            sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            sb.append(java.lang.Integer.toString(i2));
            sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            sb.append(java.lang.Integer.toString(i3));
            sb.append("--");
            sb.append(str2);
            sb.append("--");
            if (fontVariationAxisArr != null) {
                for (android.graphics.fonts.FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                    sb.append(fontVariationAxis.getTag());
                    sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    sb.append(java.lang.Float.toString(fontVariationAxis.getStyleValue()));
                }
            }
            return sb.toString();
        }

        private android.graphics.Typeface resolveFallbackTypeface() {
            if (this.mFallbackFamilyName == null) {
                return null;
            }
            android.graphics.Typeface systemDefaultTypeface = android.graphics.Typeface.getSystemDefaultTypeface(this.mFallbackFamilyName);
            if (this.mWeight == -1 && this.mItalic == -1) {
                return systemDefaultTypeface;
            }
            int i = this.mWeight == -1 ? systemDefaultTypeface.mWeight : this.mWeight;
            boolean z = false;
            if (this.mItalic != -1 ? this.mItalic == 1 : (systemDefaultTypeface.mStyle & 2) != 0) {
                z = true;
            }
            return android.graphics.Typeface.createWeightStyle(systemDefaultTypeface, i, z);
        }

        public android.graphics.Typeface build() {
            java.lang.String createAssetUid;
            if (this.mFontBuilder == null) {
                return resolveFallbackTypeface();
            }
            try {
                android.graphics.fonts.Font build = this.mFontBuilder.build();
                if (this.mAssetManager == null) {
                    createAssetUid = null;
                } else {
                    createAssetUid = createAssetUid(this.mAssetManager, this.mPath, build.getTtcIndex(), build.getAxes(), this.mWeight, this.mItalic, this.mFallbackFamilyName == null ? android.graphics.Typeface.DEFAULT_FAMILY : this.mFallbackFamilyName);
                }
                if (createAssetUid != null) {
                    synchronized (android.graphics.Typeface.sDynamicCacheLock) {
                        android.graphics.Typeface typeface = (android.graphics.Typeface) android.graphics.Typeface.sDynamicTypefaceCache.get(createAssetUid);
                        if (typeface != null) {
                            return typeface;
                        }
                    }
                }
                android.graphics.Typeface.CustomFallbackBuilder style = new android.graphics.Typeface.CustomFallbackBuilder(new android.graphics.fonts.FontFamily.Builder(build).build()).setStyle(new android.graphics.fonts.FontStyle(this.mWeight == -1 ? build.getStyle().getWeight() : this.mWeight, this.mItalic == -1 ? build.getStyle().getSlant() : this.mItalic));
                if (this.mFallbackFamilyName != null) {
                    style.setSystemFallback(this.mFallbackFamilyName);
                }
                android.graphics.Typeface build2 = style.build();
                if (createAssetUid != null) {
                    synchronized (android.graphics.Typeface.sDynamicCacheLock) {
                        android.graphics.Typeface.sDynamicTypefaceCache.put(createAssetUid, build2);
                    }
                }
                return build2;
            } catch (java.io.IOException | java.lang.IllegalArgumentException e) {
                return resolveFallbackTypeface();
            }
        }
    }

    public static final class CustomFallbackBuilder {
        private static final int MAX_CUSTOM_FALLBACK = 64;
        private android.graphics.fonts.FontStyle mStyle;
        private final java.util.ArrayList<android.graphics.fonts.FontFamily> mFamilies = new java.util.ArrayList<>();
        private java.lang.String mFallbackName = null;

        public static int getMaxCustomFallbackCount() {
            return 64;
        }

        public CustomFallbackBuilder(android.graphics.fonts.FontFamily fontFamily) {
            com.android.internal.util.Preconditions.checkNotNull(fontFamily);
            this.mFamilies.add(fontFamily);
        }

        public android.graphics.Typeface.CustomFallbackBuilder setSystemFallback(java.lang.String str) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            this.mFallbackName = str;
            return this;
        }

        public android.graphics.Typeface.CustomFallbackBuilder setStyle(android.graphics.fonts.FontStyle fontStyle) {
            this.mStyle = fontStyle;
            return this;
        }

        public android.graphics.Typeface.CustomFallbackBuilder addCustomFallback(android.graphics.fonts.FontFamily fontFamily) {
            com.android.internal.util.Preconditions.checkNotNull(fontFamily);
            com.android.internal.util.Preconditions.checkArgument(this.mFamilies.size() < getMaxCustomFallbackCount(), "Custom fallback limit exceeded(%d)", java.lang.Integer.valueOf(getMaxCustomFallbackCount()));
            this.mFamilies.add(fontFamily);
            return this;
        }

        public android.graphics.Typeface build() {
            int size = this.mFamilies.size();
            android.graphics.Typeface systemDefaultTypeface = android.graphics.Typeface.getSystemDefaultTypeface(this.mFallbackName);
            long[] jArr = new long[size];
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                jArr[i2] = this.mFamilies.get(i2).getNativePtr();
            }
            int weight = this.mStyle == null ? 400 : this.mStyle.getWeight();
            if (this.mStyle != null && this.mStyle.getSlant() != 0) {
                i = 1;
            }
            return new android.graphics.Typeface(android.graphics.Typeface.nativeCreateFromArray(jArr, systemDefaultTypeface.native_instance, weight, i), null);
        }
    }

    public static android.graphics.Typeface create(java.lang.String str, int i) {
        return create(getSystemDefaultTypeface(str), i);
    }

    public static android.graphics.Typeface create(android.graphics.Typeface typeface, int i) {
        if ((i & (-4)) != 0) {
            i = 0;
        }
        if (typeface == null) {
            typeface = getDefault();
        }
        if (typeface.mStyle == i) {
            return typeface;
        }
        long j = typeface.native_instance;
        synchronized (sStyledCacheLock) {
            android.util.SparseArray<android.graphics.Typeface> sparseArray = sStyledTypefaceCache.get(j);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>(4);
                sStyledTypefaceCache.put(j, sparseArray);
            } else {
                android.graphics.Typeface typeface2 = sparseArray.get(i);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            android.graphics.Typeface typeface3 = new android.graphics.Typeface(nativeCreateFromTypeface(j, i), typeface.getSystemFontFamilyName());
            sparseArray.put(i, typeface3);
            return typeface3;
        }
    }

    public static android.graphics.Typeface create(android.graphics.Typeface typeface, int i, boolean z) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 1000, "weight");
        if (typeface == null) {
            typeface = getDefault();
        }
        return createWeightStyle(typeface, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Typeface createWeightStyle(android.graphics.Typeface typeface, int i, boolean z) {
        int i2 = (i << 1) | (z ? 1 : 0);
        synchronized (sWeightCacheLock) {
            android.util.SparseArray<android.graphics.Typeface> sparseArray = sWeightTypefaceCache.get(typeface.native_instance);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>(4);
                sWeightTypefaceCache.put(typeface.native_instance, sparseArray);
            } else {
                android.graphics.Typeface typeface2 = sparseArray.get(i2);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            android.graphics.Typeface typeface3 = new android.graphics.Typeface(nativeCreateFromTypefaceWithExactStyle(typeface.native_instance, i, z), typeface.getSystemFontFamilyName());
            sparseArray.put(i2, typeface3);
            return typeface3;
        }
    }

    public static android.graphics.Typeface createFromTypefaceWithVariation(android.graphics.Typeface typeface, java.util.List<android.graphics.fonts.FontVariationAxis> list) {
        if (typeface == null) {
            typeface = DEFAULT;
        }
        return new android.graphics.Typeface(nativeCreateFromTypefaceWithVariation(typeface.native_instance, list), typeface.getSystemFontFamilyName());
    }

    public static android.graphics.Typeface defaultFromStyle(int i) {
        android.graphics.Typeface typeface;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            typeface = sDefaults[i];
        }
        return typeface;
    }

    public static android.graphics.Typeface createFromAsset(android.content.res.AssetManager assetManager, java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(assetManager);
        android.graphics.Typeface build = new android.graphics.Typeface.Builder(assetManager, str).build();
        if (build != null) {
            return build;
        }
        try {
            java.io.InputStream open = assetManager.open(str);
            if (open != null) {
                open.close();
            }
            return DEFAULT;
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Font asset not found " + str);
        }
    }

    private static java.lang.String createProviderUid(java.lang.String str, java.lang.String str2) {
        return "provider:" + str + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str2;
    }

    public static android.graphics.Typeface createFromFile(java.io.File file) {
        android.graphics.Typeface build = new android.graphics.Typeface.Builder(file).build();
        if (build != null) {
            return build;
        }
        if (!file.exists()) {
            throw new java.lang.RuntimeException("Font asset not found " + file.getAbsolutePath());
        }
        return DEFAULT;
    }

    public static android.graphics.Typeface createFromFile(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        return createFromFile(new java.io.File(str));
    }

    @java.lang.Deprecated
    private static android.graphics.Typeface createFromFamilies(android.graphics.FontFamily[] fontFamilyArr) {
        long[] jArr = new long[fontFamilyArr.length];
        for (int i = 0; i < fontFamilyArr.length; i++) {
            jArr[i] = fontFamilyArr[i].mNativePtr;
        }
        return new android.graphics.Typeface(nativeCreateFromArray(jArr, 0L, -1, -1), null);
    }

    private static android.graphics.Typeface createFromFamilies(java.lang.String str, android.graphics.fonts.FontFamily[] fontFamilyArr) {
        long[] jArr = new long[fontFamilyArr.length];
        for (int i = 0; i < fontFamilyArr.length; i++) {
            jArr[i] = fontFamilyArr[i].getNativePtr();
        }
        return new android.graphics.Typeface(nativeCreateFromArray(jArr, 0L, -1, -1), str);
    }

    @java.lang.Deprecated
    private static android.graphics.Typeface createFromFamiliesWithDefault(android.graphics.FontFamily[] fontFamilyArr, int i, int i2) {
        return createFromFamiliesWithDefault(fontFamilyArr, DEFAULT_FAMILY, i, i2);
    }

    @java.lang.Deprecated
    private static android.graphics.Typeface createFromFamiliesWithDefault(android.graphics.FontFamily[] fontFamilyArr, java.lang.String str, int i, int i2) {
        android.graphics.Typeface systemDefaultTypeface = getSystemDefaultTypeface(str);
        long[] jArr = new long[fontFamilyArr.length];
        for (int i3 = 0; i3 < fontFamilyArr.length; i3++) {
            jArr[i3] = fontFamilyArr[i3].mNativePtr;
        }
        return new android.graphics.Typeface(nativeCreateFromArray(jArr, systemDefaultTypeface.native_instance, i, i2), null);
    }

    private Typeface(long j) {
        this(j, null);
    }

    private Typeface(long j, java.lang.String str) {
        if (j == 0) {
            throw new java.lang.RuntimeException("native typeface cannot be made");
        }
        this.native_instance = j;
        this.mCleaner = sRegistry.registerNativeAllocation(this, this.native_instance);
        this.mStyle = nativeGetStyle(j);
        this.mWeight = nativeGetWeight(j);
        this.mSystemFontFamilyName = str;
    }

    public void releaseNativeObjectForTest() {
        this.mCleaner.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Typeface getSystemDefaultTypeface(java.lang.String str) {
        android.graphics.Typeface typeface = sSystemFontMap.get(str);
        return typeface == null ? DEFAULT : typeface;
    }

    public static void initSystemDefaultTypefaces(java.util.Map<java.lang.String, android.graphics.fonts.FontFamily[]> map, java.util.List<android.text.FontConfig.Alias> list, java.util.Map<java.lang.String, android.graphics.Typeface> map2) {
        android.graphics.Typeface typeface;
        for (java.util.Map.Entry<java.lang.String, android.graphics.fonts.FontFamily[]> entry : map.entrySet()) {
            map2.put(entry.getKey(), createFromFamilies(entry.getKey(), entry.getValue()));
        }
        for (int i = 0; i < list.size(); i++) {
            android.text.FontConfig.Alias alias = list.get(i);
            if (!map2.containsKey(alias.getName()) && (typeface = map2.get(alias.getOriginal())) != null) {
                int weight = alias.getWeight();
                if (weight != 400) {
                    typeface = new android.graphics.Typeface(nativeCreateWeightAlias(typeface.native_instance, weight), alias.getName());
                }
                map2.put(alias.getName(), typeface);
            }
        }
    }

    private static void registerGenericFamilyNative(java.lang.String str, android.graphics.Typeface typeface) {
        if (typeface != null) {
            nativeRegisterGenericFamily(str, typeface.native_instance);
        }
    }

    public static android.os.SharedMemory serializeFontMap(java.util.Map<java.lang.String, android.graphics.Typeface> map) throws java.io.IOException, android.system.ErrnoException {
        long[] jArr = new long[map.size()];
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        int i = 0;
        for (java.util.Map.Entry<java.lang.String, android.graphics.Typeface> entry : map.entrySet()) {
            jArr[i] = entry.getValue().native_instance;
            writeString(byteArrayOutputStream, entry.getKey());
            i++;
        }
        int nativeWriteTypefaces = nativeWriteTypefaces(null, 4, jArr);
        android.os.SharedMemory create = android.os.SharedMemory.create("fontMap", nativeWriteTypefaces + 4 + byteArrayOutputStream.size());
        java.nio.ByteBuffer order = create.mapReadWrite().order(java.nio.ByteOrder.BIG_ENDIAN);
        try {
            order.putInt(nativeWriteTypefaces);
            int nativeWriteTypefaces2 = nativeWriteTypefaces(order, order.position(), jArr);
            if (nativeWriteTypefaces2 != nativeWriteTypefaces) {
                throw new java.io.IOException(java.lang.String.format("Unexpected bytes written: %d, expected: %d", java.lang.Integer.valueOf(nativeWriteTypefaces2), java.lang.Integer.valueOf(nativeWriteTypefaces)));
            }
            order.position(order.position() + nativeWriteTypefaces2);
            order.put(byteArrayOutputStream.toByteArray());
            android.os.SharedMemory.unmap(order);
            create.setProtect(android.system.OsConstants.PROT_READ);
            return create;
        } catch (java.lang.Throwable th) {
            android.os.SharedMemory.unmap(order);
            throw th;
        }
    }

    public static long[] deserializeFontMap(java.nio.ByteBuffer byteBuffer, java.util.Map<java.lang.String, android.graphics.Typeface> map) throws java.io.IOException {
        int i = byteBuffer.getInt();
        long[] nativeReadTypefaces = nativeReadTypefaces(byteBuffer, byteBuffer.position());
        if (nativeReadTypefaces == null) {
            throw new java.io.IOException("Could not read typefaces");
        }
        map.clear();
        byteBuffer.position(byteBuffer.position() + i);
        for (long j : nativeReadTypefaces) {
            java.lang.String readString = readString(byteBuffer);
            map.put(readString, new android.graphics.Typeface(j, readString));
        }
        return nativeReadTypefaces;
    }

    private static java.lang.String readString(java.nio.ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.getInt()];
        byteBuffer.get(bArr);
        return new java.lang.String(bArr);
    }

    private static void writeString(java.io.ByteArrayOutputStream byteArrayOutputStream, java.lang.String str) throws java.io.IOException {
        byte[] bytes = str.getBytes();
        writeInt(byteArrayOutputStream, bytes.length);
        byteArrayOutputStream.write(bytes);
    }

    private static void writeInt(java.io.ByteArrayOutputStream byteArrayOutputStream, int i) {
        byteArrayOutputStream.write((i >> 24) & 255);
        byteArrayOutputStream.write((i >> 16) & 255);
        byteArrayOutputStream.write((i >> 8) & 255);
        byteArrayOutputStream.write(i & 255);
    }

    public static java.util.Map<java.lang.String, android.graphics.Typeface> getSystemFontMap() {
        java.util.Map<java.lang.String, android.graphics.Typeface> map;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            map = sSystemFontMap;
        }
        return map;
    }

    public static void setSystemFontMap(android.os.SharedMemory sharedMemory) throws java.io.IOException, android.system.ErrnoException {
        if (sSystemFontMapBuffer != null) {
            if (sharedMemory == null || sharedMemory == sSystemFontMapSharedMemory) {
                return;
            } else {
                throw new java.lang.UnsupportedOperationException("Once set, buffer-based system font map cannot be updated");
            }
        }
        sSystemFontMapSharedMemory = sharedMemory;
        android.os.Trace.traceBegin(2L, "setSystemFontMap");
        try {
            if (sharedMemory == null) {
                loadPreinstalledSystemFontMap();
                return;
            }
            sSystemFontMapBuffer = sharedMemory.mapReadOnly().order(java.nio.ByteOrder.BIG_ENDIAN);
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            for (long j : deserializeFontMap(sSystemFontMapBuffer, arrayMap)) {
                nativeAddFontCollections(j);
            }
            setSystemFontMap(arrayMap);
        } finally {
            android.os.Trace.traceEnd(2L);
        }
    }

    public static void setSystemFontMap(java.util.Map<java.lang.String, android.graphics.Typeface> map) {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            sSystemFontMap.clear();
            sSystemFontMap.putAll(map);
            if (sSystemFontMap.containsKey(DEFAULT_FAMILY)) {
                setDefault(sSystemFontMap.get(DEFAULT_FAMILY));
            }
            nativeForceSetStaticFinalField("DEFAULT", create(sDefaultTypeface, 0));
            nativeForceSetStaticFinalField("DEFAULT_BOLD", create(sDefaultTypeface, 1));
            nativeForceSetStaticFinalField("SANS_SERIF", create(DEFAULT_FAMILY, 0));
            nativeForceSetStaticFinalField("SERIF", create("serif", 0));
            nativeForceSetStaticFinalField("MONOSPACE", create("monospace", 0));
            sDefaults = new android.graphics.Typeface[]{DEFAULT, DEFAULT_BOLD, create((java.lang.String) null, 2), create((java.lang.String) null, 3)};
            java.lang.String[] strArr = {"serif", DEFAULT_FAMILY, "cursive", "fantasy", "monospace", "system-ui"};
            for (int i = 0; i < 6; i++) {
                java.lang.String str = strArr[i];
                registerGenericFamilyNative(str, map.get(str));
            }
        }
    }

    public static android.util.Pair<java.util.List<android.graphics.Typeface>, java.util.List<android.graphics.Typeface>> changeDefaultFontForTest(java.util.List<android.graphics.Typeface> list, java.util.List<android.graphics.Typeface> list2) {
        android.util.Pair<java.util.List<android.graphics.Typeface>, java.util.List<android.graphics.Typeface>> pair;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            java.util.List asList = java.util.Arrays.asList(sDefaults);
            sDefaults = (android.graphics.Typeface[]) list.toArray(new android.graphics.Typeface[4]);
            setDefault(list.get(0));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(sSystemFontMap.get(DEFAULT_FAMILY));
            sSystemFontMap.put(DEFAULT_FAMILY, list2.get(0));
            arrayList.add(sSystemFontMap.get("serif"));
            sSystemFontMap.put("serif", list2.get(1));
            arrayList.add(sSystemFontMap.get("monospace"));
            sSystemFontMap.put("monospace", list2.get(2));
            pair = new android.util.Pair<>(asList, arrayList);
        }
        return pair;
    }

    private static void preloadFontFile(java.lang.String str) {
        java.io.File file = new java.io.File(str);
        if (file.exists()) {
            android.util.Log.i(TAG, "Preloading " + file.getAbsolutePath());
            nativeWarmUpCache(str);
        }
    }

    public static void destroySystemFontMap() {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            java.util.Iterator<android.graphics.Typeface> it = sSystemFontMap.values().iterator();
            while (it.hasNext()) {
                it.next().releaseNativeObjectForTest();
            }
            sSystemFontMap.clear();
            if (sSystemFontMapBuffer != null) {
                android.os.SharedMemory.unmap(sSystemFontMapBuffer);
            }
            sSystemFontMapBuffer = null;
            sSystemFontMapSharedMemory = null;
            synchronized (sStyledCacheLock) {
                destroyTypefaceCacheLocked(sStyledTypefaceCache);
            }
            synchronized (sWeightCacheLock) {
                destroyTypefaceCacheLocked(sWeightTypefaceCache);
            }
        }
    }

    private static void destroyTypefaceCacheLocked(android.util.LongSparseArray<android.util.SparseArray<android.graphics.Typeface>> longSparseArray) {
        for (int i = 0; i < longSparseArray.size(); i++) {
            android.util.SparseArray<android.graphics.Typeface> valueAt = longSparseArray.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                valueAt.valueAt(i2).releaseNativeObjectForTest();
            }
        }
        longSparseArray.clear();
    }

    public static void loadPreinstalledSystemFontMap() {
        android.text.FontConfig systemPreinstalledFontConfig = android.graphics.fonts.SystemFonts.getSystemPreinstalledFontConfig();
        setSystemFontMap(android.graphics.fonts.SystemFonts.buildSystemTypefaces(systemPreinstalledFontConfig, android.graphics.fonts.SystemFonts.buildSystemFallback(systemPreinstalledFontConfig)));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.Typeface typeface = (android.graphics.Typeface) obj;
        if (this.mStyle == typeface.mStyle && this.native_instance == typeface.native_instance) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((527 + ((int) (this.native_instance ^ (this.native_instance >>> 32)))) * 31) + this.mStyle;
    }

    public boolean isSupportedAxes(int i) {
        synchronized (this) {
            if (this.mSupportedAxes == null) {
                this.mSupportedAxes = nativeGetSupportedAxes(this.native_instance);
                if (this.mSupportedAxes == null) {
                    this.mSupportedAxes = EMPTY_AXES;
                }
            }
        }
        return java.util.Arrays.binarySearch(this.mSupportedAxes, i) >= 0;
    }
}
