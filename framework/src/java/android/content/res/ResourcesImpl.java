package android.content.res;

/* loaded from: classes.dex */
public class ResourcesImpl {
    private static final boolean DEBUG_CONFIG = false;
    private static final boolean DEBUG_LOAD = false;
    private static final int ID_OTHER = 16777220;
    static final java.lang.String TAG = "Resources";
    private static final boolean TRACE_FOR_MISS_PRELOAD = false;
    private static final boolean TRACE_FOR_PRELOAD = false;
    private static final int XML_BLOCK_CACHE_SIZE = 4;
    private static boolean sPreloaded;
    private static final libcore.util.NativeAllocationRegistry sThemeRegistry;
    final android.content.res.AssetManager mAssets;
    private final android.view.DisplayAdjustments mDisplayAdjustments;
    private android.icu.text.PluralRules mPluralRule;
    private boolean mPreloading;
    private static final java.lang.Object sSync = new java.lang.Object();
    private static final android.util.LongSparseArray<android.graphics.drawable.Drawable.ConstantState> sPreloadedColorDrawables = new android.util.LongSparseArray<>();
    private static final android.util.LongSparseArray<android.content.res.ConstantState<android.content.res.ComplexColor>> sPreloadedComplexColors = new android.util.LongSparseArray<>();
    private static final android.util.LongSparseArray<android.graphics.drawable.Drawable.ConstantState>[] sPreloadedDrawables = new android.util.LongSparseArray[2];
    private final java.lang.Object mAccessLock = new java.lang.Object();
    private final android.content.res.Configuration mTmpConfig = new android.content.res.Configuration();
    private final android.content.res.DrawableCache mDrawableCache = new android.content.res.DrawableCache();
    private final android.content.res.DrawableCache mColorDrawableCache = new android.content.res.DrawableCache();
    private final android.content.res.ConfigurationBoundResourceCache<android.content.res.ComplexColor> mComplexColorCache = new android.content.res.ConfigurationBoundResourceCache<>();
    private final android.content.res.ConfigurationBoundResourceCache<android.animation.Animator> mAnimatorCache = new android.content.res.ConfigurationBoundResourceCache<>();
    private final android.content.res.ConfigurationBoundResourceCache<android.animation.StateListAnimator> mStateListAnimatorCache = new android.content.res.ConfigurationBoundResourceCache<>();
    private final java.lang.ThreadLocal<android.content.res.ResourcesImpl.LookupStack> mLookupStack = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.content.res.ResourcesImpl$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return android.content.res.ResourcesImpl.lambda$new$0();
        }
    });
    private int mLastCachedXmlBlockIndex = -1;
    private final int[] mCachedXmlBlockCookies = new int[4];
    private final java.lang.String[] mCachedXmlBlockFiles = new java.lang.String[4];
    private final android.content.res.XmlBlock[] mCachedXmlBlocks = new android.content.res.XmlBlock[4];
    private final android.util.DisplayMetrics mMetrics = new android.util.DisplayMetrics();
    private final android.content.res.Configuration mConfiguration = new android.content.res.Configuration();

    static {
        sPreloadedDrawables[0] = new android.util.LongSparseArray<>();
        sPreloadedDrawables[1] = new android.util.LongSparseArray<>();
        sThemeRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.content.res.ResourcesImpl.class.getClassLoader(), android.content.res.AssetManager.getThemeFreeFunction());
    }

    static /* synthetic */ android.content.res.ResourcesImpl.LookupStack lambda$new$0() {
        return new android.content.res.ResourcesImpl.LookupStack();
    }

    static void resetDrawableStateCache() {
        synchronized (sSync) {
            sPreloadedDrawables[0].clear();
            sPreloadedDrawables[1].clear();
            sPreloadedColorDrawables.clear();
            sPreloadedComplexColors.clear();
            sPreloaded = false;
        }
    }

    public ResourcesImpl(android.content.res.AssetManager assetManager, android.util.DisplayMetrics displayMetrics, android.content.res.Configuration configuration, android.view.DisplayAdjustments displayAdjustments) {
        this.mAssets = assetManager;
        this.mMetrics.setToDefaults();
        this.mDisplayAdjustments = displayAdjustments;
        this.mConfiguration.setToDefaults();
        updateConfigurationImpl(configuration, displayMetrics, displayAdjustments.getCompatibilityInfo(), true);
    }

    public android.view.DisplayAdjustments getDisplayAdjustments() {
        return this.mDisplayAdjustments;
    }

    public android.content.res.AssetManager getAssets() {
        return this.mAssets;
    }

    android.util.DisplayMetrics getDisplayMetrics() {
        return this.mMetrics;
    }

    android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    android.content.res.Configuration[] getSizeConfigurations() {
        return this.mAssets.getSizeConfigurations();
    }

    android.content.res.Configuration[] getSizeAndUiModeConfigurations() {
        return this.mAssets.getSizeAndUiModeConfigurations();
    }

    android.content.res.CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    private android.icu.text.PluralRules getPluralRule() {
        android.icu.text.PluralRules pluralRules;
        synchronized (sSync) {
            if (this.mPluralRule == null) {
                this.mPluralRule = android.icu.text.PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
            }
            pluralRules = this.mPluralRule;
        }
        return pluralRules;
    }

    void getValue(int i, android.util.TypedValue typedValue, boolean z) throws android.content.res.Resources.NotFoundException {
        if (this.mAssets.getResourceValue(i, 0, typedValue, z)) {
        } else {
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i));
        }
    }

    void getValueForDensity(int i, int i2, android.util.TypedValue typedValue, boolean z) throws android.content.res.Resources.NotFoundException {
        if (this.mAssets.getResourceValue(i, i2, typedValue, z)) {
        } else {
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i));
        }
    }

    void getValue(java.lang.String str, android.util.TypedValue typedValue, boolean z) throws android.content.res.Resources.NotFoundException {
        int identifier = getIdentifier(str, "string", null);
        if (identifier != 0) {
            getValue(identifier, typedValue, z);
            return;
        }
        throw new android.content.res.Resources.NotFoundException("String resource name " + str);
    }

    private static boolean isIntLike(java.lang.String str) {
        if (str.isEmpty() || str.length() > 10) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return false;
            }
        }
        return true;
    }

    int getIdentifier(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (str == null) {
            throw new java.lang.NullPointerException("name is null");
        }
        if (isIntLike(str)) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.Exception e) {
            }
        }
        return this.mAssets.getResourceIdentifier(str, str2, str3);
    }

    java.lang.String getResourceName(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.String resourceName = this.mAssets.getResourceName(i);
        if (resourceName != null) {
            return resourceName;
        }
        throw new android.content.res.Resources.NotFoundException("Unable to find resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    java.lang.String getResourcePackageName(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.String resourcePackageName = this.mAssets.getResourcePackageName(i);
        if (resourcePackageName != null) {
            return resourcePackageName;
        }
        throw new android.content.res.Resources.NotFoundException("Unable to find resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    java.lang.String getResourceTypeName(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.String resourceTypeName = this.mAssets.getResourceTypeName(i);
        if (resourceTypeName != null) {
            return resourceTypeName;
        }
        throw new android.content.res.Resources.NotFoundException("Unable to find resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    java.lang.String getResourceEntryName(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.String resourceEntryName = this.mAssets.getResourceEntryName(i);
        if (resourceEntryName != null) {
            return resourceEntryName;
        }
        throw new android.content.res.Resources.NotFoundException("Unable to find resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    java.lang.String getLastResourceResolution() throws android.content.res.Resources.NotFoundException {
        java.lang.String lastResourceResolution = this.mAssets.getLastResourceResolution();
        if (lastResourceResolution != null) {
            return lastResourceResolution;
        }
        throw new android.content.res.Resources.NotFoundException("Associated AssetManager hasn't resolved a resource");
    }

    java.lang.CharSequence getQuantityText(int i, int i2) throws android.content.res.Resources.NotFoundException {
        android.icu.text.PluralRules pluralRule = getPluralRule();
        double d = i2;
        java.lang.CharSequence resourceBagText = this.mAssets.getResourceBagText(i, attrForQuantityCode(pluralRule.select(d)));
        if (resourceBagText != null) {
            return resourceBagText;
        }
        java.lang.CharSequence resourceBagText2 = this.mAssets.getResourceBagText(i, ID_OTHER);
        if (resourceBagText2 != null) {
            return resourceBagText2;
        }
        throw new android.content.res.Resources.NotFoundException("Plural resource ID #0x" + java.lang.Integer.toHexString(i) + " quantity=" + i2 + " item=" + pluralRule.select(d));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int attrForQuantityCode(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 101272:
                if (str.equals("few")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 110182:
                if (str.equals("one")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 115276:
                if (str.equals("two")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3343967:
                if (str.equals("many")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3735208:
                if (str.equals("zero")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 16777221;
            case 1:
                return 16777222;
            case 2:
                return 16777223;
            case 3:
                return 16777224;
            case 4:
                return 16777225;
            default:
                return ID_OTHER;
        }
    }

    android.content.res.AssetFileDescriptor openRawResourceFd(int i, android.util.TypedValue typedValue) throws android.content.res.Resources.NotFoundException {
        getValue(i, typedValue, true);
        try {
            return this.mAssets.openNonAssetFd(typedValue.assetCookie, typedValue.string.toString());
        } catch (java.lang.Exception e) {
            throw new android.content.res.Resources.NotFoundException("File " + typedValue.string.toString() + " from resource ID #0x" + java.lang.Integer.toHexString(i), e);
        }
    }

    java.io.InputStream openRawResource(int i, android.util.TypedValue typedValue) throws android.content.res.Resources.NotFoundException {
        getValue(i, typedValue, true);
        try {
            return this.mAssets.openNonAsset(typedValue.assetCookie, typedValue.string.toString(), 2);
        } catch (java.lang.Exception e) {
            android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("File " + (typedValue.string == null ? "(null)" : typedValue.string.toString()) + " from resource ID #0x" + java.lang.Integer.toHexString(i));
            notFoundException.initCause(e);
            throw notFoundException;
        }
    }

    android.content.res.ConfigurationBoundResourceCache<android.animation.Animator> getAnimatorCache() {
        return this.mAnimatorCache;
    }

    android.content.res.ConfigurationBoundResourceCache<android.animation.StateListAnimator> getStateListAnimatorCache() {
        return this.mStateListAnimatorCache;
    }

    public void updateConfiguration(android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo) {
        updateConfigurationImpl(configuration, displayMetrics, compatibilityInfo, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00e9 A[Catch: all -> 0x0018, TryCatch #1 {all -> 0x0018, blocks: (B:90:0x0012, B:7:0x001d, B:8:0x0022, B:10:0x003d, B:11:0x0046, B:13:0x0057, B:15:0x005d, B:17:0x0063, B:19:0x006a, B:20:0x0080, B:22:0x0083, B:24:0x0092, B:26:0x00e9, B:28:0x00ef, B:30:0x00f5, B:31:0x00fc, B:33:0x0102, B:36:0x0129, B:38:0x012f, B:39:0x0144, B:41:0x0153, B:42:0x015a, B:44:0x0173, B:45:0x018c, B:47:0x0192, B:50:0x01a3, B:51:0x021c, B:71:0x019d, B:72:0x0180, B:74:0x0115, B:76:0x00a1, B:78:0x00ad, B:82:0x00bc, B:84:0x00c3, B:86:0x00d9), top: B:89:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x012f A[Catch: all -> 0x0018, TryCatch #1 {all -> 0x0018, blocks: (B:90:0x0012, B:7:0x001d, B:8:0x0022, B:10:0x003d, B:11:0x0046, B:13:0x0057, B:15:0x005d, B:17:0x0063, B:19:0x006a, B:20:0x0080, B:22:0x0083, B:24:0x0092, B:26:0x00e9, B:28:0x00ef, B:30:0x00f5, B:31:0x00fc, B:33:0x0102, B:36:0x0129, B:38:0x012f, B:39:0x0144, B:41:0x0153, B:42:0x015a, B:44:0x0173, B:45:0x018c, B:47:0x0192, B:50:0x01a3, B:51:0x021c, B:71:0x019d, B:72:0x0180, B:74:0x0115, B:76:0x00a1, B:78:0x00ad, B:82:0x00bc, B:84:0x00c3, B:86:0x00d9), top: B:89:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0153 A[Catch: all -> 0x0018, TryCatch #1 {all -> 0x0018, blocks: (B:90:0x0012, B:7:0x001d, B:8:0x0022, B:10:0x003d, B:11:0x0046, B:13:0x0057, B:15:0x005d, B:17:0x0063, B:19:0x006a, B:20:0x0080, B:22:0x0083, B:24:0x0092, B:26:0x00e9, B:28:0x00ef, B:30:0x00f5, B:31:0x00fc, B:33:0x0102, B:36:0x0129, B:38:0x012f, B:39:0x0144, B:41:0x0153, B:42:0x015a, B:44:0x0173, B:45:0x018c, B:47:0x0192, B:50:0x01a3, B:51:0x021c, B:71:0x019d, B:72:0x0180, B:74:0x0115, B:76:0x00a1, B:78:0x00ad, B:82:0x00bc, B:84:0x00c3, B:86:0x00d9), top: B:89:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0173 A[Catch: all -> 0x0018, TryCatch #1 {all -> 0x0018, blocks: (B:90:0x0012, B:7:0x001d, B:8:0x0022, B:10:0x003d, B:11:0x0046, B:13:0x0057, B:15:0x005d, B:17:0x0063, B:19:0x006a, B:20:0x0080, B:22:0x0083, B:24:0x0092, B:26:0x00e9, B:28:0x00ef, B:30:0x00f5, B:31:0x00fc, B:33:0x0102, B:36:0x0129, B:38:0x012f, B:39:0x0144, B:41:0x0153, B:42:0x015a, B:44:0x0173, B:45:0x018c, B:47:0x0192, B:50:0x01a3, B:51:0x021c, B:71:0x019d, B:72:0x0180, B:74:0x0115, B:76:0x00a1, B:78:0x00ad, B:82:0x00bc, B:84:0x00c3, B:86:0x00d9), top: B:89:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0180 A[Catch: all -> 0x0018, TryCatch #1 {all -> 0x0018, blocks: (B:90:0x0012, B:7:0x001d, B:8:0x0022, B:10:0x003d, B:11:0x0046, B:13:0x0057, B:15:0x005d, B:17:0x0063, B:19:0x006a, B:20:0x0080, B:22:0x0083, B:24:0x0092, B:26:0x00e9, B:28:0x00ef, B:30:0x00f5, B:31:0x00fc, B:33:0x0102, B:36:0x0129, B:38:0x012f, B:39:0x0144, B:41:0x0153, B:42:0x015a, B:44:0x0173, B:45:0x018c, B:47:0x0192, B:50:0x01a3, B:51:0x021c, B:71:0x019d, B:72:0x0180, B:74:0x0115, B:76:0x00a1, B:78:0x00ad, B:82:0x00bc, B:84:0x00c3, B:86:0x00d9), top: B:89:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0128  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateConfigurationImpl(android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo, boolean z) {
        java.lang.String str;
        java.lang.String[] strArr;
        int i;
        int i2;
        int i3;
        java.util.Locale firstMatchWithEnglishSupported;
        android.os.Trace.traceBegin(8192L, "ResourcesImpl#updateConfiguration");
        try {
            synchronized (this.mAccessLock) {
                if (compatibilityInfo != null) {
                    try {
                        this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (displayMetrics != null) {
                    this.mMetrics.setTo(displayMetrics);
                }
                this.mDisplayAdjustments.getCompatibilityInfo().applyToDisplayMetrics(this.mMetrics);
                int calcConfigChanges = calcConfigChanges(configuration);
                android.os.LocaleList locales = this.mConfiguration.getLocales();
                if (locales.isEmpty()) {
                    locales = android.os.LocaleList.getDefault();
                    this.mConfiguration.setLocales(locales);
                }
                android.app.LocaleConfig localeConfig = android.app.ResourcesManager.getInstance().getLocaleConfig();
                java.lang.String[] strArr2 = null;
                if ((calcConfigChanges & 4) != 0 && locales.size() > 1) {
                    if (android.content.res.Flags.defaultLocale() && localeConfig.getDefaultLocale() != null) {
                        java.util.Locale[] intersection = locales.getIntersection(localeConfig.getSupportedLocales());
                        this.mConfiguration.setLocales(new android.os.LocaleList(intersection));
                        strArr2 = new java.lang.String[intersection.length];
                        for (int i4 = 0; i4 < intersection.length; i4++) {
                            strArr2[i4] = adjustLanguageTag(intersection[i4].toLanguageTag());
                        }
                        str = adjustLanguageTag(localeConfig.getDefaultLocale().toLanguageTag());
                    } else {
                        java.lang.String[] nonSystemLocales = this.mAssets.getNonSystemLocales();
                        if (android.os.LocaleList.isPseudoLocalesOnly(nonSystemLocales)) {
                            nonSystemLocales = this.mAssets.getLocales();
                            if (android.os.LocaleList.isPseudoLocalesOnly(nonSystemLocales)) {
                                nonSystemLocales = null;
                            }
                        }
                        if (nonSystemLocales != null && (firstMatchWithEnglishSupported = locales.getFirstMatchWithEnglishSupported(nonSystemLocales)) != null) {
                            java.lang.String[] strArr3 = {adjustLanguageTag(firstMatchWithEnglishSupported.toLanguageTag())};
                            if (!firstMatchWithEnglishSupported.equals(locales.get(0))) {
                                this.mConfiguration.setLocales(new android.os.LocaleList(firstMatchWithEnglishSupported, locales));
                            }
                            str = null;
                            strArr2 = strArr3;
                        }
                    }
                    if (strArr2 == null) {
                        strArr = strArr2;
                    } else if (android.content.res.Flags.defaultLocale() && localeConfig.getDefaultLocale() != null) {
                        java.lang.String[] strArr4 = new java.lang.String[locales.size()];
                        for (int i5 = 0; i5 < locales.size(); i5++) {
                            strArr4[i5] = adjustLanguageTag(locales.get(i5).toLanguageTag());
                        }
                        strArr = strArr4;
                    } else {
                        strArr = new java.lang.String[]{adjustLanguageTag(locales.get(0).toLanguageTag())};
                    }
                    if (this.mConfiguration.densityDpi != 0) {
                        this.mMetrics.densityDpi = this.mConfiguration.densityDpi;
                        this.mMetrics.density = this.mConfiguration.densityDpi * 0.00625f;
                    }
                    this.mMetrics.scaledDensity = this.mMetrics.density * (this.mConfiguration.fontScale == 0.0f ? this.mConfiguration.fontScale : 1.0f);
                    this.mMetrics.fontScaleConverter = android.content.res.FontScaleConverterFactory.forScale(this.mConfiguration.fontScale);
                    if (this.mMetrics.widthPixels < this.mMetrics.heightPixels) {
                        i = this.mMetrics.widthPixels;
                        i2 = this.mMetrics.heightPixels;
                    } else {
                        i = this.mMetrics.heightPixels;
                        i2 = this.mMetrics.widthPixels;
                    }
                    if (this.mConfiguration.keyboardHidden != 1 && this.mConfiguration.hardKeyboardHidden == 2) {
                        i3 = 3;
                    } else {
                        i3 = this.mConfiguration.keyboardHidden;
                    }
                    this.mAssets.setConfigurationInternal(this.mConfiguration.mcc, this.mConfiguration.mnc, str, strArr, this.mConfiguration.orientation, this.mConfiguration.touchscreen, this.mConfiguration.densityDpi, this.mConfiguration.keyboard, i3, this.mConfiguration.navigation, i, i2, this.mConfiguration.smallestScreenWidthDp, this.mConfiguration.screenWidthDp, this.mConfiguration.screenHeightDp, this.mConfiguration.screenLayout, this.mConfiguration.uiMode, this.mConfiguration.colorMode, this.mConfiguration.getGrammaticalGender(), android.os.Build.VERSION.RESOURCES_SDK_INT, z);
                    this.mDrawableCache.onConfigurationChange(calcConfigChanges);
                    this.mColorDrawableCache.onConfigurationChange(calcConfigChanges);
                    this.mComplexColorCache.onConfigurationChange(calcConfigChanges);
                    this.mAnimatorCache.onConfigurationChange(calcConfigChanges);
                    this.mStateListAnimatorCache.onConfigurationChange(calcConfigChanges);
                    flushLayoutCache();
                }
                str = null;
                if (strArr2 == null) {
                }
                if (this.mConfiguration.densityDpi != 0) {
                }
                this.mMetrics.scaledDensity = this.mMetrics.density * (this.mConfiguration.fontScale == 0.0f ? this.mConfiguration.fontScale : 1.0f);
                this.mMetrics.fontScaleConverter = android.content.res.FontScaleConverterFactory.forScale(this.mConfiguration.fontScale);
                if (this.mMetrics.widthPixels < this.mMetrics.heightPixels) {
                }
                if (this.mConfiguration.keyboardHidden != 1) {
                }
                i3 = this.mConfiguration.keyboardHidden;
                this.mAssets.setConfigurationInternal(this.mConfiguration.mcc, this.mConfiguration.mnc, str, strArr, this.mConfiguration.orientation, this.mConfiguration.touchscreen, this.mConfiguration.densityDpi, this.mConfiguration.keyboard, i3, this.mConfiguration.navigation, i, i2, this.mConfiguration.smallestScreenWidthDp, this.mConfiguration.screenWidthDp, this.mConfiguration.screenHeightDp, this.mConfiguration.screenLayout, this.mConfiguration.uiMode, this.mConfiguration.colorMode, this.mConfiguration.getGrammaticalGender(), android.os.Build.VERSION.RESOURCES_SDK_INT, z);
                this.mDrawableCache.onConfigurationChange(calcConfigChanges);
                this.mColorDrawableCache.onConfigurationChange(calcConfigChanges);
                this.mComplexColorCache.onConfigurationChange(calcConfigChanges);
                this.mAnimatorCache.onConfigurationChange(calcConfigChanges);
                this.mStateListAnimatorCache.onConfigurationChange(calcConfigChanges);
                flushLayoutCache();
            }
            synchronized (sSync) {
                if (this.mPluralRule != null) {
                    this.mPluralRule = android.icu.text.PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
                }
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public int calcConfigChanges(android.content.res.Configuration configuration) {
        if (configuration == null) {
            return -1;
        }
        this.mTmpConfig.setTo(configuration);
        int i = configuration.densityDpi;
        if (i == 0) {
            i = this.mMetrics.noncompatDensityDpi;
        }
        this.mDisplayAdjustments.getCompatibilityInfo().applyToConfiguration(i, this.mTmpConfig);
        if (this.mTmpConfig.getLocales().isEmpty()) {
            this.mTmpConfig.setLocales(android.os.LocaleList.getDefault());
        }
        return this.mConfiguration.updateFrom(this.mTmpConfig);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:
    
        if (r4.equals("id") != false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.lang.String adjustLanguageTag(java.lang.String str) {
        java.lang.String substring;
        int indexOf = str.indexOf(45);
        char c = 0;
        if (indexOf == -1) {
            substring = "";
        } else {
            java.lang.String substring2 = str.substring(0, indexOf);
            substring = str.substring(indexOf);
            str = substring2;
        }
        switch (str.hashCode()) {
            case 3325:
                if (str.equals("he")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3355:
                break;
            case 3856:
                if (str.equals("yi")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                str = "in";
                break;
            case 1:
                str = "ji";
                break;
            case 2:
                str = "iw";
                break;
        }
        return str + substring;
    }

    public void flushLayoutCache() {
        synchronized (this.mCachedXmlBlocks) {
            java.util.Arrays.fill(this.mCachedXmlBlockCookies, 0);
            java.util.Arrays.fill(this.mCachedXmlBlockFiles, (java.lang.Object) null);
            android.content.res.XmlBlock[] xmlBlockArr = this.mCachedXmlBlocks;
            for (int i = 0; i < 4; i++) {
                android.content.res.XmlBlock xmlBlock = xmlBlockArr[i];
                if (xmlBlock != null) {
                    xmlBlock.close();
                }
            }
            java.util.Arrays.fill(xmlBlockArr, (java.lang.Object) null);
        }
    }

    public void clearAllCaches() {
        synchronized (this.mAccessLock) {
            this.mDrawableCache.clear();
            this.mColorDrawableCache.clear();
            this.mComplexColorCache.clear();
            this.mAnimatorCache.clear();
            this.mStateListAnimatorCache.clear();
            flushLayoutCache();
        }
    }

    android.graphics.drawable.Drawable loadDrawable(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, int i2, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        java.lang.String str;
        long j;
        android.content.res.DrawableCache drawableCache;
        boolean z;
        android.graphics.drawable.Drawable.ConstantState constantState;
        android.graphics.drawable.Drawable loadDrawableForCookie;
        boolean z2;
        android.graphics.drawable.Drawable drawable;
        android.graphics.drawable.Drawable.ConstantState constantState2;
        android.graphics.drawable.Drawable drawableCache2;
        boolean z3 = i2 == 0 || typedValue.density == this.mMetrics.densityDpi;
        if (i2 > 0 && typedValue.density > 0 && typedValue.density != 65535) {
            if (typedValue.density != i2) {
                typedValue.density = (typedValue.density * this.mMetrics.densityDpi) / i2;
            } else {
                typedValue.density = this.mMetrics.densityDpi;
            }
        }
        try {
            if (typedValue.type >= 28 && typedValue.type <= 31) {
                android.content.res.DrawableCache drawableCache3 = this.mColorDrawableCache;
                j = typedValue.data;
                drawableCache = drawableCache3;
                z = true;
            } else {
                j = (typedValue.assetCookie << 32) | typedValue.data;
                drawableCache = this.mDrawableCache;
                z = false;
            }
            int generation = drawableCache.getGeneration();
            if (!this.mPreloading && z3 && (drawableCache2 = drawableCache.getInstance(j, resources, theme)) != null) {
                drawableCache2.setChangingConfigurations(typedValue.changingConfigurations);
                return drawableCache2;
            }
            if (!z) {
                constantState = sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].get(j);
            } else {
                constantState = sPreloadedColorDrawables.get(j);
            }
            if (constantState != null) {
                loadDrawableForCookie = constantState.newDrawable(resources);
            } else if (z) {
                loadDrawableForCookie = new android.graphics.drawable.ColorDrawable(typedValue.data);
            } else {
                loadDrawableForCookie = loadDrawableForCookie(resources, typedValue, i, i2);
            }
            if (!(loadDrawableForCookie instanceof android.graphics.drawable.DrawableContainer)) {
                z2 = false;
            } else {
                z2 = true;
            }
            boolean z4 = loadDrawableForCookie != null && loadDrawableForCookie.canApplyTheme();
            if (z4 && theme != null) {
                android.graphics.drawable.Drawable mutate = loadDrawableForCookie.mutate();
                mutate.applyTheme(theme);
                mutate.clearMutated();
                drawable = mutate;
            } else {
                drawable = loadDrawableForCookie;
            }
            if (drawable != null) {
                drawable.setChangingConfigurations(typedValue.changingConfigurations);
                if (z3) {
                    cacheDrawable(typedValue, z, drawableCache, theme, z4, j, drawable, generation);
                    if (z2 && (constantState2 = drawable.getConstantState()) != null) {
                        return constantState2.newDrawable(resources);
                    }
                    return drawable;
                }
                return drawable;
            }
            return drawable;
        } catch (java.lang.Exception e) {
            try {
                str = getResourceName(i);
            } catch (android.content.res.Resources.NotFoundException e2) {
                str = "(missing name)";
            }
            android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("Drawable " + str + " with resource ID #0x" + java.lang.Integer.toHexString(i), e);
            notFoundException.setStackTrace(new java.lang.StackTraceElement[0]);
            throw notFoundException;
        }
    }

    private void cacheDrawable(android.util.TypedValue typedValue, boolean z, android.content.res.DrawableCache drawableCache, android.content.res.Resources.Theme theme, boolean z2, long j, android.graphics.drawable.Drawable drawable, int i) {
        android.graphics.drawable.Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return;
        }
        if (this.mPreloading) {
            int changingConfigurations = constantState.getChangingConfigurations();
            if (z) {
                if (verifyPreloadConfig(changingConfigurations, 0, typedValue.resourceId, "drawable")) {
                    sPreloadedColorDrawables.put(j, constantState);
                    return;
                }
                return;
            } else {
                if (verifyPreloadConfig(changingConfigurations, 8192, typedValue.resourceId, "drawable")) {
                    if ((changingConfigurations & 8192) != 0) {
                        sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].put(j, constantState);
                        return;
                    } else {
                        sPreloadedDrawables[0].put(j, constantState);
                        sPreloadedDrawables[1].put(j, constantState);
                        return;
                    }
                }
                return;
            }
        }
        synchronized (this.mAccessLock) {
            drawableCache.put(j, theme, constantState, i, z2);
        }
    }

    private boolean verifyPreloadConfig(int i, int i2, int i3, java.lang.String str) {
        java.lang.String str2;
        if ((i & (-1073745921) & (~i2)) != 0) {
            try {
                str2 = getResourceName(i3);
            } catch (android.content.res.Resources.NotFoundException e) {
                str2 = "?";
            }
            android.util.Log.w(TAG, "Preloaded " + str + " resource #0x" + java.lang.Integer.toHexString(i3) + " (" + str2 + ") that varies with configuration!!");
            return false;
        }
        return true;
    }

    private android.graphics.drawable.Drawable decodeImageDrawable(android.content.res.AssetManager.AssetInputStream assetInputStream, android.content.res.Resources resources, android.util.TypedValue typedValue) {
        try {
            return android.graphics.ImageDecoder.decodeDrawable(new android.graphics.ImageDecoder.AssetInputStreamSource(assetInputStream, resources, typedValue), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.content.res.ResourcesImpl$$ExternalSyntheticLambda1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private android.graphics.drawable.Drawable decodeImageDrawable(java.io.FileInputStream fileInputStream, android.content.res.Resources resources) {
        try {
            return android.graphics.ImageDecoder.decodeDrawable(android.graphics.ImageDecoder.createSource(resources, fileInputStream), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.content.res.ResourcesImpl$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private android.graphics.drawable.Drawable loadDrawableForCookie(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, int i2) {
        android.graphics.drawable.Drawable decodeImageDrawable;
        if (typedValue.string == null) {
            throw new android.content.res.Resources.NotFoundException("Resource \"" + getResourceName(i) + "\" (" + java.lang.Integer.toHexString(i) + ") is not a Drawable (color or path): " + typedValue);
        }
        java.lang.String charSequence = typedValue.string.toString();
        android.os.Trace.traceBegin(8192L, charSequence);
        android.content.res.ResourcesImpl.LookupStack lookupStack = this.mLookupStack.get();
        try {
            if (lookupStack.contains(i)) {
                throw new java.lang.Exception("Recursive reference in drawable");
            }
            lookupStack.push(i);
            try {
                if (charSequence.endsWith(".xml")) {
                    java.lang.String resourceTypeName = getResourceTypeName(i);
                    if (resourceTypeName != null && resourceTypeName.equals("color")) {
                        decodeImageDrawable = loadColorOrXmlDrawable(resources, typedValue, i, i2, charSequence);
                    } else {
                        decodeImageDrawable = loadXmlDrawable(resources, typedValue, i, i2, charSequence);
                    }
                } else if (charSequence.startsWith("frro://")) {
                    android.net.Uri parse = android.net.Uri.parse(charSequence);
                    decodeImageDrawable = decodeImageDrawable(new android.content.res.AssetFileDescriptor(android.os.ParcelFileDescriptor.open(new java.io.File('/' + parse.getHost() + parse.getPath()), 268435456), java.lang.Long.parseLong(parse.getQueryParameter(android.provider.CallLog.Calls.OFFSET_PARAM_KEY)), java.lang.Long.parseLong(parse.getQueryParameter("size"))).createInputStream(), resources);
                } else {
                    decodeImageDrawable = decodeImageDrawable((android.content.res.AssetManager.AssetInputStream) this.mAssets.openNonAsset(typedValue.assetCookie, charSequence, 2), resources, typedValue);
                }
                android.os.Trace.traceEnd(8192L);
                return decodeImageDrawable;
            } finally {
                lookupStack.pop();
            }
        } catch (java.lang.Exception | java.lang.StackOverflowError e) {
            android.os.Trace.traceEnd(8192L);
            android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("File " + charSequence + " from drawable resource ID #0x" + java.lang.Integer.toHexString(i));
            notFoundException.initCause(e);
            throw notFoundException;
        }
    }

    private android.graphics.drawable.Drawable loadColorOrXmlDrawable(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, int i2, java.lang.String str) {
        try {
            return new android.graphics.drawable.ColorStateListDrawable(loadColorStateList(resources, typedValue, i, null));
        } catch (android.content.res.Resources.NotFoundException e) {
            try {
                return loadXmlDrawable(resources, typedValue, i, i2, str);
            } catch (java.lang.Exception e2) {
                throw e;
            }
        }
    }

    private android.graphics.drawable.Drawable loadXmlDrawable(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, int i2, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.XmlResourceParser loadXmlResourceParser = loadXmlResourceParser(str, i, typedValue.assetCookie, "drawable");
        try {
            android.graphics.drawable.Drawable createFromXmlForDensity = android.graphics.drawable.Drawable.createFromXmlForDensity(resources, loadXmlResourceParser, i2, null);
            if (loadXmlResourceParser != null) {
                loadXmlResourceParser.close();
            }
            return createFromXmlForDensity;
        } catch (java.lang.Throwable th) {
            if (loadXmlResourceParser != null) {
                try {
                    loadXmlResourceParser.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public android.graphics.Typeface loadFont(android.content.res.Resources resources, android.util.TypedValue typedValue, int i) {
        if (typedValue.string == null) {
            throw new android.content.res.Resources.NotFoundException("Resource \"" + getResourceName(i) + "\" (" + java.lang.Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        java.lang.String charSequence = typedValue.string.toString();
        if (!charSequence.startsWith("res/")) {
            return null;
        }
        android.graphics.Typeface findFromCache = android.graphics.Typeface.findFromCache(this.mAssets, charSequence);
        if (findFromCache != null) {
            return findFromCache;
        }
        android.os.Trace.traceBegin(8192L, charSequence);
        try {
            try {
                if (!charSequence.endsWith("xml")) {
                    return new android.graphics.Typeface.Builder(this.mAssets, charSequence, false, typedValue.assetCookie).build();
                }
                android.content.res.FontResourcesParser.FamilyResourceEntry parse = android.content.res.FontResourcesParser.parse(loadXmlResourceParser(charSequence, i, typedValue.assetCookie, android.content.Context.FONT_SERVICE), resources);
                if (parse == null) {
                    return null;
                }
                return android.graphics.Typeface.createFromResources(parse, this.mAssets, charSequence);
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Failed to read xml resource " + charSequence, e);
                return null;
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.util.Log.e(TAG, "Failed to parse xml resource " + charSequence, e2);
                return null;
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private android.content.res.ComplexColor loadComplexColorFromName(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.TypedValue typedValue, int i) {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        android.content.res.ConfigurationBoundResourceCache<android.content.res.ComplexColor> configurationBoundResourceCache = this.mComplexColorCache;
        android.content.res.ComplexColor configurationBoundResourceCache2 = configurationBoundResourceCache.getInstance(j, resources, theme);
        if (configurationBoundResourceCache2 != null) {
            return configurationBoundResourceCache2;
        }
        int generation = configurationBoundResourceCache.getGeneration();
        android.content.res.ConstantState<android.content.res.ComplexColor> constantState = sPreloadedComplexColors.get(j);
        if (constantState != null) {
            configurationBoundResourceCache2 = constantState.newInstance2(resources, theme);
        }
        if (configurationBoundResourceCache2 == null) {
            configurationBoundResourceCache2 = loadComplexColorForCookie(resources, typedValue, i, theme);
        }
        if (configurationBoundResourceCache2 != null) {
            configurationBoundResourceCache2.setBaseChangingConfigurations(typedValue.changingConfigurations);
            if (this.mPreloading) {
                if (verifyPreloadConfig(configurationBoundResourceCache2.getChangingConfigurations(), 0, typedValue.resourceId, "color")) {
                    sPreloadedComplexColors.put(j, configurationBoundResourceCache2.getConstantState());
                }
            } else {
                configurationBoundResourceCache.put(j, theme, configurationBoundResourceCache2.getConstantState(), generation);
            }
        }
        return configurationBoundResourceCache2;
    }

    android.content.res.ComplexColor loadComplexColor(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, android.content.res.Resources.Theme theme) {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getColorStateListFromInt(typedValue, j);
        }
        java.lang.String charSequence = typedValue.string.toString();
        if (charSequence.endsWith(".xml")) {
            try {
                return loadComplexColorFromName(resources, theme, typedValue, i);
            } catch (java.lang.Exception e) {
                android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("File " + charSequence + " from complex color resource ID #0x" + java.lang.Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        throw new android.content.res.Resources.NotFoundException("File " + charSequence + " from drawable resource ID #0x" + java.lang.Integer.toHexString(i) + ": .xml extension required");
    }

    android.content.res.ColorStateList loadColorStateList(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getColorStateListFromInt(typedValue, j);
        }
        android.content.res.ComplexColor loadComplexColorFromName = loadComplexColorFromName(resources, theme, typedValue, i);
        if (loadComplexColorFromName != null && (loadComplexColorFromName instanceof android.content.res.ColorStateList)) {
            return (android.content.res.ColorStateList) loadComplexColorFromName;
        }
        throw new android.content.res.Resources.NotFoundException("Can't find ColorStateList from drawable resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    private android.content.res.ColorStateList getColorStateListFromInt(android.util.TypedValue typedValue, long j) {
        android.content.res.ConstantState<android.content.res.ComplexColor> constantState = sPreloadedComplexColors.get(j);
        if (constantState != null) {
            return (android.content.res.ColorStateList) constantState.newInstance2();
        }
        android.content.res.ColorStateList valueOf = android.content.res.ColorStateList.valueOf(typedValue.data);
        if (this.mPreloading && verifyPreloadConfig(typedValue.changingConfigurations, 0, typedValue.resourceId, "color")) {
            sPreloadedComplexColors.put(j, valueOf.getConstantState());
        }
        return valueOf;
    }

    private android.content.res.ComplexColor loadComplexColorForCookie(android.content.res.Resources resources, android.util.TypedValue typedValue, int i, android.content.res.Resources.Theme theme) {
        int next;
        android.content.res.ComplexColor complexColor;
        if (typedValue.string == null) {
            throw new java.lang.UnsupportedOperationException("Can't convert to ComplexColor: type=0x" + typedValue.type);
        }
        java.lang.String charSequence = typedValue.string.toString();
        android.os.Trace.traceBegin(8192L, charSequence);
        if (charSequence.endsWith(".xml")) {
            try {
                android.content.res.XmlResourceParser loadXmlResourceParser = loadXmlResourceParser(charSequence, i, typedValue.assetCookie, "ComplexColor");
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlResourceParser);
                do {
                    next = loadXmlResourceParser.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
                }
                java.lang.String name = loadXmlResourceParser.getName();
                if (name.equals("gradient")) {
                    complexColor = android.content.res.GradientColor.createFromXmlInner(resources, loadXmlResourceParser, asAttributeSet, theme);
                } else if (!name.equals("selector")) {
                    complexColor = null;
                } else {
                    complexColor = android.content.res.ColorStateList.createFromXmlInner(resources, loadXmlResourceParser, asAttributeSet, theme);
                }
                loadXmlResourceParser.close();
                android.os.Trace.traceEnd(8192L);
                return complexColor;
            } catch (java.lang.Exception e) {
                android.os.Trace.traceEnd(8192L);
                android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("File " + charSequence + " from ComplexColor resource ID #0x" + java.lang.Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        android.os.Trace.traceEnd(8192L);
        throw new android.content.res.Resources.NotFoundException("File " + charSequence + " from drawable resource ID #0x" + java.lang.Integer.toHexString(i) + ": .xml extension required");
    }

    android.content.res.XmlResourceParser loadXmlResourceParser(java.lang.String str, int i, int i2, java.lang.String str2) throws android.content.res.Resources.NotFoundException {
        if (i != 0) {
            try {
                synchronized (this.mCachedXmlBlocks) {
                    int[] iArr = this.mCachedXmlBlockCookies;
                    java.lang.String[] strArr = this.mCachedXmlBlockFiles;
                    android.content.res.XmlBlock[] xmlBlockArr = this.mCachedXmlBlocks;
                    int length = strArr.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        if (iArr[i3] == i2 && strArr[i3] != null && strArr[i3].equals(str)) {
                            return xmlBlockArr[i3].newParser(i);
                        }
                    }
                    android.content.res.XmlBlock openXmlBlockAsset = this.mAssets.openXmlBlockAsset(i2, str);
                    if (openXmlBlockAsset != null) {
                        int i4 = (this.mLastCachedXmlBlockIndex + 1) % length;
                        this.mLastCachedXmlBlockIndex = i4;
                        android.content.res.XmlBlock xmlBlock = xmlBlockArr[i4];
                        if (xmlBlock != null) {
                            xmlBlock.close();
                        }
                        iArr[i4] = i2;
                        strArr[i4] = str;
                        xmlBlockArr[i4] = openXmlBlockAsset;
                        return openXmlBlockAsset.newParser(i);
                    }
                }
            } catch (java.lang.Exception e) {
                android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("File " + str + " from xml type " + str2 + " resource ID #0x" + java.lang.Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        throw new android.content.res.Resources.NotFoundException("File " + str + " from xml type " + str2 + " resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    public final void startPreloading() {
        synchronized (sSync) {
            if (sPreloaded) {
                throw new java.lang.IllegalStateException("Resources already preloaded");
            }
            sPreloaded = true;
            this.mPreloading = true;
            this.mConfiguration.densityDpi = android.util.DisplayMetrics.DENSITY_DEVICE;
            updateConfiguration(null, null, null);
        }
    }

    void finishPreloading() {
        if (this.mPreloading) {
            this.mPreloading = false;
            flushLayoutCache();
        }
    }

    static int getAttributeSetSourceResId(android.util.AttributeSet attributeSet) {
        if (attributeSet == null || !(attributeSet instanceof android.content.res.XmlBlock.Parser)) {
            return 0;
        }
        return ((android.content.res.XmlBlock.Parser) attributeSet).getSourceResId();
    }

    android.util.LongSparseArray<android.graphics.drawable.Drawable.ConstantState> getPreloadedDrawables() {
        return sPreloadedDrawables[0];
    }

    android.content.res.ResourcesImpl.ThemeImpl newThemeImpl() {
        return new android.content.res.ResourcesImpl.ThemeImpl();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "class=" + getClass());
        printWriter.println(str + "assets");
        this.mAssets.dump(printWriter, str + "  ");
    }

    public class ThemeImpl {
        private android.content.res.AssetManager mAssets;
        private final long mTheme;
        private final android.content.res.Resources.ThemeKey mKey = new android.content.res.Resources.ThemeKey();
        private int mThemeResId = 0;

        ThemeImpl() {
            this.mAssets = android.content.res.ResourcesImpl.this.mAssets;
            this.mTheme = this.mAssets.createTheme();
            android.content.res.ResourcesImpl.sThemeRegistry.registerNativeAllocation(this, this.mTheme);
        }

        protected void finalize() throws java.lang.Throwable {
            super.finalize();
            this.mAssets.releaseTheme(this.mTheme);
        }

        android.content.res.Resources.ThemeKey getKey() {
            return this.mKey;
        }

        long getNativeTheme() {
            return this.mTheme;
        }

        int getAppliedStyleResId() {
            return this.mThemeResId;
        }

        int getParentThemeIdentifier(int i) {
            if (i > 0) {
                return this.mAssets.getParentThemeIdentifier(i);
            }
            return 0;
        }

        void applyStyle(int i, boolean z) {
            this.mAssets.applyStyleToTheme(this.mTheme, i, z);
            this.mThemeResId = i;
            this.mKey.append(i, z);
        }

        void setTo(android.content.res.ResourcesImpl.ThemeImpl themeImpl) {
            this.mAssets.setThemeTo(this.mTheme, themeImpl.mAssets, themeImpl.mTheme);
            this.mThemeResId = themeImpl.mThemeResId;
            this.mKey.setTo(themeImpl.getKey());
        }

        android.content.res.TypedArray obtainStyledAttributes(android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet, int[] iArr, int i, int i2) {
            android.content.res.TypedArray obtain = android.content.res.TypedArray.obtain(theme.getResources(), iArr.length);
            android.content.res.XmlBlock.Parser parser = (android.content.res.XmlBlock.Parser) attributeSet;
            this.mAssets.applyStyle(this.mTheme, i, i2, parser, iArr, obtain.mDataAddress, obtain.mIndicesAddress);
            obtain.mTheme = theme;
            obtain.mXml = parser;
            return obtain;
        }

        android.content.res.TypedArray resolveAttributes(android.content.res.Resources.Theme theme, int[] iArr, int[] iArr2) {
            int length = iArr2.length;
            if (iArr == null || length != iArr.length) {
                throw new java.lang.IllegalArgumentException("Base attribute values must the same length as attrs");
            }
            android.content.res.TypedArray obtain = android.content.res.TypedArray.obtain(theme.getResources(), length);
            this.mAssets.resolveAttrs(this.mTheme, 0, 0, iArr, iArr2, obtain.mData, obtain.mIndices);
            obtain.mTheme = theme;
            obtain.mXml = null;
            return obtain;
        }

        boolean resolveAttribute(int i, android.util.TypedValue typedValue, boolean z) {
            return this.mAssets.getThemeValue(this.mTheme, i, typedValue, z);
        }

        int[] getAllAttributes() {
            return this.mAssets.getStyleAttributes(getAppliedStyleResId());
        }

        int getChangingConfigurations() {
            return android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(android.content.res.AssetManager.nativeThemeGetChangingConfigurations(this.mTheme));
        }

        public void dump(int i, java.lang.String str, java.lang.String str2) {
            this.mAssets.dumpTheme(this.mTheme, i, str, str2);
        }

        java.lang.String[] getTheme() {
            int i = this.mKey.mCount;
            int i2 = i * 2;
            java.lang.String[] strArr = new java.lang.String[i2];
            int i3 = i - 1;
            int i4 = 0;
            while (i4 < i2) {
                int i5 = this.mKey.mResId[i3];
                boolean z = this.mKey.mForce[i3];
                try {
                    strArr[i4] = android.content.res.ResourcesImpl.this.getResourceName(i5);
                } catch (android.content.res.Resources.NotFoundException e) {
                    strArr[i4] = java.lang.Integer.toHexString(i4);
                }
                strArr[i4 + 1] = z ? "forced" : "not forced";
                i4 += 2;
                i3--;
            }
            return strArr;
        }

        void rebase() {
            rebase(this.mAssets);
        }

        void rebase(android.content.res.AssetManager assetManager) {
            this.mAssets = this.mAssets.rebaseTheme(this.mTheme, assetManager, this.mKey.mResId, this.mKey.mForce, this.mKey.mCount);
        }

        public int[] getAttributeResolutionStack(int i, int i2, int i3) {
            return this.mAssets.getAttributeResolutionStack(this.mTheme, i, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LookupStack {
        private int[] mIds;
        private int mSize;

        private LookupStack() {
            this.mIds = new int[4];
            this.mSize = 0;
        }

        public void push(int i) {
            this.mIds = com.android.internal.util.GrowingArrayUtils.append(this.mIds, this.mSize, i);
            this.mSize++;
        }

        public boolean contains(int i) {
            for (int i2 = 0; i2 < this.mSize; i2++) {
                if (this.mIds[i2] == i) {
                    return true;
                }
            }
            return false;
        }

        public void pop() {
            this.mSize--;
        }
    }
}
