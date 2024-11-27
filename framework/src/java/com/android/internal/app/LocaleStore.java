package com.android.internal.app;

/* loaded from: classes4.dex */
public class LocaleStore {
    private static final int TIER_LANGUAGE = 1;
    private static final int TIER_NUMBERING = 3;
    private static final int TIER_REGION = 2;
    private static final java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> sLocaleCache = new java.util.HashMap<>();
    private static final java.lang.String TAG = com.android.internal.app.LocaleStore.class.getSimpleName();
    private static boolean sFullyInitialized = false;

    public static class LocaleInfo implements java.io.Serializable {
        public static final int SUGGESTION_TYPE_CFG = 2;
        public static final int SUGGESTION_TYPE_CURRENT = 4;
        public static final int SUGGESTION_TYPE_IME_LANGUAGE = 32;
        public static final int SUGGESTION_TYPE_NONE = 0;
        public static final int SUGGESTION_TYPE_OTHER_APP_LANGUAGE = 16;
        public static final int SUGGESTION_TYPE_SIM = 1;
        public static final int SUGGESTION_TYPE_SYSTEM_AVAILABLE_LANGUAGE = 64;
        public static final int SUGGESTION_TYPE_SYSTEM_LANGUAGE = 8;
        private java.lang.String mFullCountryNameNative;
        private java.lang.String mFullNameNative;
        private boolean mHasNumberingSystems;
        private final java.lang.String mId;
        private boolean mIsChecked;
        private boolean mIsPseudo;
        private boolean mIsTranslated;
        private java.lang.String mLangScriptKey;
        private final java.util.Locale mLocale;
        private final java.util.Locale mParent;
        public int mSuggestionFlags;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SuggestionType {
        }

        private LocaleInfo(java.util.Locale locale) {
            this.mLocale = locale;
            this.mId = locale.toLanguageTag();
            this.mParent = getParent(locale);
            this.mHasNumberingSystems = false;
            this.mIsChecked = false;
            this.mSuggestionFlags = 0;
            this.mIsTranslated = false;
            this.mIsPseudo = false;
        }

        private LocaleInfo(java.lang.String str) {
            this(java.util.Locale.forLanguageTag(str));
        }

        private LocaleInfo(com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
            this.mLocale = localeInfo.getLocale();
            this.mId = localeInfo.getId();
            this.mParent = localeInfo.getParent();
            this.mHasNumberingSystems = localeInfo.mHasNumberingSystems;
            this.mIsChecked = localeInfo.getChecked();
            this.mSuggestionFlags = localeInfo.mSuggestionFlags;
            this.mIsTranslated = localeInfo.isTranslated();
            this.mIsPseudo = localeInfo.mIsPseudo;
        }

        private static java.util.Locale getParent(java.util.Locale locale) {
            if (locale.getCountry().isEmpty()) {
                return null;
            }
            return new java.util.Locale.Builder().setLocale(locale).setRegion("").setExtension('u', "").build();
        }

        public boolean hasNumberingSystems() {
            return this.mHasNumberingSystems;
        }

        public java.lang.String toString() {
            return this.mId;
        }

        public java.util.Locale getLocale() {
            return this.mLocale;
        }

        public java.util.Locale getParent() {
            return this.mParent;
        }

        public java.lang.String getId() {
            return this.mId;
        }

        public boolean isTranslated() {
            return this.mIsTranslated;
        }

        public void setTranslated(boolean z) {
            this.mIsTranslated = z;
        }

        public boolean isSuggested() {
            return this.mIsTranslated && this.mSuggestionFlags != 0;
        }

        public boolean isSuggestionOfType(int i) {
            return this.mIsTranslated && (this.mSuggestionFlags & i) == i;
        }

        public void extendSuggestionOfType(int i) {
            if (!this.mIsTranslated) {
                return;
            }
            this.mSuggestionFlags = i | this.mSuggestionFlags;
        }

        public java.lang.String getFullNameNative() {
            if (this.mFullNameNative == null) {
                java.util.Locale stripExtensions = this.mLocale.stripExtensions();
                this.mFullNameNative = com.android.internal.app.LocaleHelper.getDisplayName(stripExtensions, stripExtensions, true);
            }
            return this.mFullNameNative;
        }

        public java.lang.String getFullCountryNameNative() {
            if (this.mFullCountryNameNative == null) {
                this.mFullCountryNameNative = com.android.internal.app.LocaleHelper.getDisplayCountry(this.mLocale, this.mLocale);
            }
            return this.mFullCountryNameNative;
        }

        java.lang.String getFullCountryNameInUiLanguage() {
            return com.android.internal.app.LocaleHelper.getDisplayCountry(this.mLocale);
        }

        public java.lang.String getFullNameInUiLanguage() {
            return com.android.internal.app.LocaleHelper.getDisplayName(this.mLocale.stripExtensions(), true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getLangScriptKey() {
            java.lang.String languageTag;
            if (this.mLangScriptKey == null) {
                java.util.Locale parent = getParent(com.android.internal.app.LocaleHelper.addLikelySubtags(new java.util.Locale.Builder().setLocale(this.mLocale).setExtension('u', "").build()));
                if (parent == null) {
                    languageTag = this.mLocale.toLanguageTag();
                } else {
                    languageTag = parent.toLanguageTag();
                }
                this.mLangScriptKey = languageTag;
            }
            return this.mLangScriptKey;
        }

        java.lang.String getLabel(boolean z) {
            if (z) {
                return getFullCountryNameNative();
            }
            return getFullNameNative();
        }

        java.lang.String getNumberingSystem() {
            return com.android.internal.app.LocaleHelper.getDisplayNumberingSystemKeyValue(this.mLocale, this.mLocale);
        }

        java.lang.String getContentDescription(boolean z) {
            if (z) {
                return getFullCountryNameInUiLanguage();
            }
            return getFullNameInUiLanguage();
        }

        public boolean getChecked() {
            return this.mIsChecked;
        }

        public void setChecked(boolean z) {
            this.mIsChecked = z;
        }

        public boolean isAppCurrentLocale() {
            return (this.mSuggestionFlags & 4) > 0;
        }

        public boolean isSystemLocale() {
            return (this.mSuggestionFlags & 8) > 0;
        }

        public boolean isInCurrentSystemLocales() {
            return (this.mSuggestionFlags & 64) > 0;
        }
    }

    private static java.util.Set<java.lang.String> getSimCountries(android.content.Context context) {
        java.util.HashSet hashSet = new java.util.HashSet();
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class);
        if (telephonyManager != null) {
            java.lang.String upperCase = telephonyManager.getSimCountryIso().toUpperCase(java.util.Locale.US);
            if (!upperCase.isEmpty()) {
                hashSet.add(upperCase);
            }
            java.lang.String upperCase2 = telephonyManager.getNetworkCountryIso().toUpperCase(java.util.Locale.US);
            if (!upperCase2.isEmpty()) {
                hashSet.add(upperCase2);
            }
        }
        return hashSet;
    }

    public static void updateSimCountries(android.content.Context context) {
        java.util.Set<java.lang.String> simCountries = getSimCountries(context);
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo : sLocaleCache.values()) {
            if (simCountries.contains(localeInfo.getLocale().getCountry())) {
                localeInfo.mSuggestionFlags |= 1;
            }
        }
    }

    public static com.android.internal.app.LocaleStore.LocaleInfo getAppActivatedLocaleInfo(android.content.Context context, java.lang.String str, boolean z) {
        android.os.LocaleList applicationLocales;
        if (str == null) {
            return null;
        }
        android.app.LocaleManager localeManager = (android.app.LocaleManager) context.getSystemService(android.app.LocaleManager.class);
        if (localeManager == null) {
            applicationLocales = null;
        } else {
            try {
                applicationLocales = localeManager.getApplicationLocales(str);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.d(TAG, "IllegalArgumentException ", e);
            }
        }
        java.util.Locale locale = applicationLocales == null ? null : applicationLocales.get(0);
        if (locale != null) {
            com.android.internal.app.LocaleStore.LocaleInfo localeInfo = new com.android.internal.app.LocaleStore.LocaleInfo(getLocaleInfo(locale, sLocaleCache));
            if (z) {
                localeInfo.mSuggestionFlags |= 4;
            } else {
                localeInfo.mSuggestionFlags |= 16;
            }
            return localeInfo;
        }
        return null;
    }

    public static java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> transformImeLanguageTagToLocaleInfo(java.util.List<android.view.inputmethod.InputMethodSubtype> list) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.HashSet hashSet2 = new java.util.HashSet();
        java.util.Iterator<android.view.inputmethod.InputMethodSubtype> it = list.iterator();
        while (it.hasNext()) {
            java.lang.String languageTag = it.next().getLanguageTag();
            if (!hashSet2.contains(languageTag)) {
                hashSet2.add(languageTag);
                com.android.internal.app.LocaleStore.LocaleInfo localeInfo = new com.android.internal.app.LocaleStore.LocaleInfo(getLocaleInfo(java.util.Locale.forLanguageTag(languageTag), sLocaleCache));
                localeInfo.mSuggestionFlags |= 32;
                hashSet.add(localeInfo);
            }
        }
        return hashSet;
    }

    public static java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSystemCurrentLocales() {
        java.util.HashSet hashSet = new java.util.HashSet();
        android.os.LocaleList localeList = android.os.LocaleList.getDefault();
        for (int i = 0; i < localeList.size(); i++) {
            com.android.internal.app.LocaleStore.LocaleInfo localeInfo = new com.android.internal.app.LocaleStore.LocaleInfo(getLocaleInfo(getLocaleWithOnlyNumberingSystem(localeList.get(i)), sLocaleCache));
            localeInfo.mSuggestionFlags |= 64;
            hashSet.add(localeInfo);
        }
        return hashSet;
    }

    public static com.android.internal.app.LocaleStore.LocaleInfo getSystemDefaultLocaleInfo(boolean z) {
        com.android.internal.app.LocaleStore.LocaleInfo localeInfo = new com.android.internal.app.LocaleStore.LocaleInfo("");
        localeInfo.mSuggestionFlags |= 8;
        if (z) {
            localeInfo.mSuggestionFlags |= 4;
        }
        localeInfo.mIsTranslated = true;
        return localeInfo;
    }

    private static void addSuggestedLocalesForRegion(java.util.Locale locale) {
        if (locale == null) {
            return;
        }
        java.lang.String country = locale.getCountry();
        if (country.isEmpty()) {
            return;
        }
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo : sLocaleCache.values()) {
            if (country.equals(localeInfo.getLocale().getCountry())) {
                localeInfo.mSuggestionFlags |= 1;
            }
        }
    }

    public static void fillCache(android.content.Context context) {
        com.android.internal.app.LocaleStore.LocaleInfo localeInfo;
        if (sFullyInitialized) {
            return;
        }
        java.util.Set<java.lang.String> simCountries = getSimCountries(context);
        boolean z = android.provider.Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) != 0;
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str : com.android.internal.app.LocalePicker.getSupportedLocales(context)) {
            if (java.util.Locale.forLanguageTag(str).getUnicodeLocaleType("nu") != null) {
                hashSet.add(java.util.Locale.forLanguageTag(str));
            }
        }
        java.lang.String[] supportedLocales = com.android.internal.app.LocalePicker.getSupportedLocales(context);
        int length = supportedLocales.length;
        int i = 0;
        while (true) {
            if (i < length) {
                java.lang.String str2 = supportedLocales[i];
                if (str2.isEmpty()) {
                    throw new java.util.IllformedLocaleException("Bad locale entry in locale_config.xml");
                }
                final com.android.internal.app.LocaleStore.LocaleInfo localeInfo2 = new com.android.internal.app.LocaleStore.LocaleInfo(str2);
                if (android.os.LocaleList.isPseudoLocale(localeInfo2.getLocale())) {
                    if (!z) {
                        i++;
                    } else {
                        localeInfo2.setTranslated(true);
                        localeInfo2.mIsPseudo = true;
                        localeInfo2.mSuggestionFlags |= 1;
                    }
                }
                if (simCountries.contains(localeInfo2.getLocale().getCountry())) {
                    localeInfo2.mSuggestionFlags |= 1;
                }
                hashSet.forEach(new java.util.function.Consumer() { // from class: com.android.internal.app.LocaleStore$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.internal.app.LocaleStore.lambda$fillCache$0(com.android.internal.app.LocaleStore.LocaleInfo.this, (java.util.Locale) obj);
                    }
                });
                sLocaleCache.put(localeInfo2.getId(), localeInfo2);
                java.util.Locale parent = localeInfo2.getParent();
                if (parent != null) {
                    java.lang.String languageTag = parent.toLanguageTag();
                    if (!sLocaleCache.containsKey(languageTag)) {
                        sLocaleCache.put(languageTag, new com.android.internal.app.LocaleStore.LocaleInfo(parent));
                    }
                }
                i++;
            } else {
                java.util.HashSet hashSet2 = new java.util.HashSet();
                for (java.lang.String str3 : com.android.internal.app.LocalePicker.getSystemAssetLocales()) {
                    com.android.internal.app.LocaleStore.LocaleInfo localeInfo3 = new com.android.internal.app.LocaleStore.LocaleInfo(str3);
                    java.lang.String country = localeInfo3.getLocale().getCountry();
                    if (!country.isEmpty()) {
                        if (sLocaleCache.containsKey(localeInfo3.getId())) {
                            localeInfo = sLocaleCache.get(localeInfo3.getId());
                        } else {
                            java.lang.String str4 = localeInfo3.getLangScriptKey() + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + country;
                            if (!sLocaleCache.containsKey(str4)) {
                                localeInfo = null;
                            } else {
                                localeInfo = sLocaleCache.get(str4);
                            }
                        }
                        if (localeInfo != null) {
                            localeInfo.mSuggestionFlags |= 2;
                        }
                    }
                    hashSet2.add(localeInfo3.getLangScriptKey());
                }
                for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo4 : sLocaleCache.values()) {
                    localeInfo4.setTranslated(hashSet2.contains(localeInfo4.getLangScriptKey()));
                }
                addSuggestedLocalesForRegion(java.util.Locale.getDefault());
                sFullyInitialized = true;
                return;
            }
        }
    }

    static /* synthetic */ void lambda$fillCache$0(com.android.internal.app.LocaleStore.LocaleInfo localeInfo, java.util.Locale locale) {
        if (localeInfo.getLocale().stripExtensions().equals(locale.stripExtensions())) {
            localeInfo.mHasNumberingSystems = true;
        }
    }

    private static boolean isShallIgnore(java.util.Set<java.lang.String> set, final com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z) {
        if (set.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.app.LocaleStore$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean equals;
                equals = java.util.Locale.forLanguageTag((java.lang.String) obj).stripExtensions().equals(com.android.internal.app.LocaleStore.LocaleInfo.this.getLocale().stripExtensions());
                return equals;
            }
        })) {
            return true;
        }
        if (localeInfo.mIsPseudo) {
            return false;
        }
        return (z && !localeInfo.isTranslated()) || localeInfo.getParent() == null;
    }

    private static int getLocaleTier(com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
        if (localeInfo == null) {
            return 1;
        }
        if (localeInfo.getLocale().getCountry().isEmpty()) {
            return 2;
        }
        return 3;
    }

    public static java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getLevelLocales(android.content.Context context, java.util.Set<java.lang.String> set, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z) {
        return getLevelLocales(context, set, localeInfo, z, null);
    }

    public static java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getLevelLocales(android.content.Context context, java.util.Set<java.lang.String> set, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, android.os.LocaleList localeList) {
        java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> convertExplicitLocales;
        if (context != null) {
            fillCache(context);
        }
        if (localeList == null) {
            convertExplicitLocales = sLocaleCache;
        } else {
            convertExplicitLocales = convertExplicitLocales(localeList, sLocaleCache.values());
        }
        return getTierLocales(set, localeInfo, z, convertExplicitLocales);
    }

    private static java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getTierLocales(java.util.Set<java.lang.String> set, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> hashMap) {
        java.lang.String id = localeInfo != null ? localeInfo.getId() : null;
        java.util.HashSet hashSet = new java.util.HashSet();
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo2 : hashMap.values()) {
            if (!isShallIgnore(set, localeInfo2, z)) {
                switch (getLocaleTier(localeInfo)) {
                    case 1:
                        if (localeInfo2.isSuggestionOfType(1)) {
                            hashSet.add(localeInfo2);
                            break;
                        } else {
                            java.util.Locale parent = localeInfo2.getParent();
                            com.android.internal.app.LocaleStore.LocaleInfo localeInfo3 = getLocaleInfo(parent, hashMap);
                            addLocaleInfoToMap(parent, localeInfo3, hashMap);
                            hashSet.add(localeInfo3);
                            break;
                        }
                    case 2:
                        if (id.equals(localeInfo2.getParent().toLanguageTag())) {
                            java.util.Locale stripExtensions = localeInfo2.getLocale().stripExtensions();
                            com.android.internal.app.LocaleStore.LocaleInfo localeInfo4 = getLocaleInfo(stripExtensions, hashMap);
                            addLocaleInfoToMap(stripExtensions, localeInfo4, hashMap);
                            hashSet.add(localeInfo4);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (localeInfo.getLocale().stripExtensions().equals(localeInfo2.getLocale().stripExtensions())) {
                            hashSet.add(localeInfo2);
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        return hashSet;
    }

    public static java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> convertExplicitLocales(android.os.LocaleList localeList, java.util.Collection<com.android.internal.app.LocaleStore.LocaleInfo> collection) {
        android.os.LocaleList matchLocaleFromSupportedLocaleList = matchLocaleFromSupportedLocaleList(localeList, collection);
        java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> hashMap = new java.util.HashMap<>();
        for (int i = 0; i < matchLocaleFromSupportedLocaleList.size(); i++) {
            java.util.Locale locale = matchLocaleFromSupportedLocaleList.get(i);
            if (locale.toString().isEmpty()) {
                throw new java.util.IllformedLocaleException("Bad locale entry");
            }
            com.android.internal.app.LocaleStore.LocaleInfo localeInfo = new com.android.internal.app.LocaleStore.LocaleInfo(locale);
            if (!hashMap.containsKey(localeInfo.getId())) {
                hashMap.put(localeInfo.getId(), localeInfo);
                java.util.Locale parent = localeInfo.getParent();
                if (parent != null) {
                    java.lang.String languageTag = parent.toLanguageTag();
                    if (!hashMap.containsKey(languageTag)) {
                        hashMap.put(languageTag, new com.android.internal.app.LocaleStore.LocaleInfo(parent));
                    }
                }
            }
        }
        return hashMap;
    }

    private static android.os.LocaleList matchLocaleFromSupportedLocaleList(android.os.LocaleList localeList, java.util.Collection<com.android.internal.app.LocaleStore.LocaleInfo> collection) {
        if (collection == null) {
            return localeList;
        }
        java.util.Locale[] localeArr = new java.util.Locale[localeList.size()];
        for (int i = 0; i < localeList.size(); i++) {
            java.util.Locale locale = localeList.get(i);
            if (!android.text.TextUtils.isEmpty(locale.getCountry())) {
                java.util.Iterator<com.android.internal.app.LocaleStore.LocaleInfo> it = collection.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    com.android.internal.app.LocaleStore.LocaleInfo next = it.next();
                    if (android.os.LocaleList.matchesLanguageAndScript(locale, next.getLocale()) && android.text.TextUtils.equals(locale.getCountry(), next.getLocale().getCountry())) {
                        localeArr[i] = next.getLocale();
                        break;
                    }
                }
            }
            if (localeArr[i] == null) {
                localeArr[i] = locale;
            }
        }
        return new android.os.LocaleList(localeArr);
    }

    public static com.android.internal.app.LocaleStore.LocaleInfo getLocaleInfo(java.util.Locale locale) {
        com.android.internal.app.LocaleStore.LocaleInfo localeInfo = getLocaleInfo(locale, sLocaleCache);
        addLocaleInfoToMap(locale, localeInfo, sLocaleCache);
        return localeInfo;
    }

    private static com.android.internal.app.LocaleStore.LocaleInfo getLocaleInfo(java.util.Locale locale, java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> hashMap) {
        java.lang.String languageTag = locale.toLanguageTag();
        if (!hashMap.containsKey(languageTag)) {
            java.util.Locale localeWithOnlyNumberingSystem = getLocaleWithOnlyNumberingSystem(locale);
            if (hashMap.containsKey(localeWithOnlyNumberingSystem.toLanguageTag())) {
                com.android.internal.app.LocaleStore.LocaleInfo localeInfo = new com.android.internal.app.LocaleStore.LocaleInfo(locale);
                com.android.internal.app.LocaleStore.LocaleInfo localeInfo2 = hashMap.get(localeWithOnlyNumberingSystem.toLanguageTag());
                localeInfo.mIsPseudo = localeInfo2.mIsPseudo;
                localeInfo.mIsTranslated = localeInfo2.mIsTranslated;
                localeInfo.mHasNumberingSystems = localeInfo2.mHasNumberingSystems;
                localeInfo.mSuggestionFlags = localeInfo2.mSuggestionFlags;
                return localeInfo;
            }
            return new com.android.internal.app.LocaleStore.LocaleInfo(locale);
        }
        return hashMap.get(languageTag);
    }

    private static java.util.Locale getLocaleWithOnlyNumberingSystem(java.util.Locale locale) {
        return new java.util.Locale.Builder().setLocale(locale.stripExtensions()).setUnicodeLocaleKeyword("nu", locale.getUnicodeLocaleType("nu")).build();
    }

    private static void addLocaleInfoToMap(java.util.Locale locale, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, java.util.HashMap<java.lang.String, com.android.internal.app.LocaleStore.LocaleInfo> hashMap) {
        if (!hashMap.containsKey(locale.toLanguageTag()) && !hashMap.containsKey(getLocaleWithOnlyNumberingSystem(locale).toLanguageTag())) {
            hashMap.put(locale.toLanguageTag(), localeInfo);
        }
    }

    public static com.android.internal.app.LocaleStore.LocaleInfo fromLocale(java.util.Locale locale) {
        return new com.android.internal.app.LocaleStore.LocaleInfo(locale);
    }
}
