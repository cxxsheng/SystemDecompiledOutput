package com.android.internal.app;

/* loaded from: classes4.dex */
public class AppLocaleCollector implements com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase {
    private static final boolean ENABLED = true;
    private static final java.lang.String PROP_APP_LANGUAGE_SUGGESTION = "android.app.language.suggestion.enhanced";
    private static final java.lang.String TAG = com.android.internal.app.AppLocaleCollector.class.getSimpleName();
    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> mAllAppActiveLocales;
    private com.android.internal.app.LocaleStore.LocaleInfo mAppCurrentLocale;
    private final java.lang.String mAppPackageName;
    private final android.content.Context mContext;
    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> mImeLocales;

    public AppLocaleCollector(android.content.Context context, java.lang.String str) {
        this.mContext = context;
        this.mAppPackageName = str;
    }

    public com.android.internal.app.LocaleStore.LocaleInfo getAppCurrentLocale() {
        return com.android.internal.app.LocaleStore.getAppActivatedLocaleInfo(this.mContext, this.mAppPackageName, true);
    }

    public java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getAllAppActiveLocales() {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.app.LocaleManager localeManager = (android.app.LocaleManager) this.mContext.getSystemService(android.app.LocaleManager.class);
        final java.util.HashSet hashSet = new java.util.HashSet();
        if (packageManager != null && localeManager != null) {
            java.util.HashMap hashMap = new java.util.HashMap();
            java.util.Iterator<android.content.pm.ApplicationInfo> it = packageManager.getInstalledApplications(android.content.pm.PackageManager.ApplicationInfoFlags.of(0L)).iterator();
            while (it.hasNext()) {
                com.android.internal.app.LocaleStore.LocaleInfo appActivatedLocaleInfo = com.android.internal.app.LocaleStore.getAppActivatedLocaleInfo(this.mContext, it.next().packageName, false);
                if (appActivatedLocaleInfo != null && appActivatedLocaleInfo.getLocale().getCountry().length() > 0) {
                    hashMap.put(appActivatedLocaleInfo.getId(), appActivatedLocaleInfo);
                }
            }
            hashMap.forEach(new java.util.function.BiConsumer() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    hashSet.add((com.android.internal.app.LocaleStore.LocaleInfo) obj2);
                }
            });
        }
        return hashSet;
    }

    public java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getActiveImeLocales() {
        java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set;
        android.view.inputmethod.InputMethodInfo activeIme;
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) this.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null && (activeIme = getActiveIme(inputMethodManager)) != null) {
            set = com.android.internal.app.LocaleStore.transformImeLanguageTagToLocaleInfo(inputMethodManager.getEnabledInputMethodSubtypeList(activeIme, true));
        } else {
            set = null;
        }
        if (set == null) {
            return java.util.Set.of();
        }
        return (java.util.Set) set.stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.app.AppLocaleCollector.lambda$getActiveImeLocales$1((com.android.internal.app.LocaleStore.LocaleInfo) obj);
            }
        }).collect(java.util.stream.Collectors.toSet());
    }

    static /* synthetic */ boolean lambda$getActiveImeLocales$1(com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
        return localeInfo.getLocale().getCountry().length() > 0;
    }

    private android.view.inputmethod.InputMethodInfo getActiveIme(android.view.inputmethod.InputMethodManager inputMethodManager) {
        java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = inputMethodManager.getEnabledInputMethodList();
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.DEFAULT_INPUT_METHOD, this.mContext.getUserId());
        android.view.inputmethod.InputMethodInfo inputMethodInfo = null;
        if (enabledInputMethodList != null && stringForUser != null) {
            for (android.view.inputmethod.InputMethodInfo inputMethodInfo2 : enabledInputMethodList) {
                if (inputMethodInfo2.getId().equals(stringForUser)) {
                    inputMethodInfo = inputMethodInfo2;
                }
            }
        }
        return inputMethodInfo;
    }

    public com.android.internal.app.AppLocaleStore.AppLocaleResult getAppSupportedLocales() {
        return com.android.internal.app.AppLocaleStore.getAppSupportedLocales(this.mContext, this.mAppPackageName);
    }

    public java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSystemSupportedLocale(java.util.Set<java.lang.String> set, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z) {
        return com.android.internal.app.LocaleStore.getLevelLocales(this.mContext, set, localeInfo, z);
    }

    public java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSystemCurrentLocales() {
        return (java.util.Set) com.android.internal.app.LocaleStore.getSystemCurrentLocales().stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.app.AppLocaleCollector.lambda$getSystemCurrentLocales$2((com.android.internal.app.LocaleStore.LocaleInfo) obj);
            }
        }).collect(java.util.stream.Collectors.toSet());
    }

    static /* synthetic */ boolean lambda$getSystemCurrentLocales$2(com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
        return localeInfo.getLocale().getCountry().length() > 0;
    }

    @Override // com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase
    public java.util.HashSet<java.lang.String> getIgnoredLocaleList(boolean z) {
        final java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet<>();
        if (this.mAppCurrentLocale != null) {
            hashSet.add(this.mAppCurrentLocale.getLocale().toLanguageTag());
        }
        if (android.os.SystemProperties.getBoolean(PROP_APP_LANGUAGE_SUGGESTION, true)) {
            this.mAllAppActiveLocales.forEach(new java.util.function.Consumer() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    hashSet.add(((com.android.internal.app.LocaleStore.LocaleInfo) obj).getLocale().toLanguageTag());
                }
            });
            this.mImeLocales.forEach(new java.util.function.Consumer() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    hashSet.add(((com.android.internal.app.LocaleStore.LocaleInfo) obj).getLocale().toLanguageTag());
                }
            });
        }
        android.os.LocaleList localeList = android.os.LocaleList.getDefault();
        for (int i = 0; i < localeList.size(); i++) {
            hashSet.add(localeList.get(i).toLanguageTag());
        }
        return hashSet;
    }

    @Override // com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase
    public java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSupportedLocaleList(com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2) {
        java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> systemSupportedLocale;
        if (this.mAppCurrentLocale == null) {
            this.mAppCurrentLocale = getAppCurrentLocale();
        }
        if (this.mAllAppActiveLocales == null) {
            this.mAllAppActiveLocales = getAllAppActiveLocales();
        }
        if (this.mImeLocales == null) {
            this.mImeLocales = getActiveImeLocales();
        }
        com.android.internal.app.AppLocaleStore.AppLocaleResult appSupportedLocales = getAppSupportedLocales();
        java.util.HashSet<java.lang.String> ignoredLocaleList = getIgnoredLocaleList(z);
        java.util.HashSet hashSet = new java.util.HashSet();
        boolean z3 = appSupportedLocales.mLocaleStatus == com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_LOCAL_CONFIG || appSupportedLocales.mLocaleStatus == com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_ASSET;
        java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set = null;
        if (z2) {
            systemSupportedLocale = getSystemSupportedLocale(ignoredLocaleList, localeInfo, z);
        } else {
            systemSupportedLocale = getSystemSupportedLocale(ignoredLocaleList, null, z);
        }
        if (this.mAppCurrentLocale != null && !z2) {
            hashSet.add(this.mAppCurrentLocale);
        }
        if (!z2) {
            for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo2 : filterSupportedLocales(getSystemCurrentLocales(), appSupportedLocales.mAppSupportedLocales)) {
                boolean z4 = this.mAppCurrentLocale != null && localeInfo2.getLocale().equals(this.mAppCurrentLocale.getLocale());
                boolean addSystemSuggestionFlag = addSystemSuggestionFlag(localeInfo2, this.mAllAppActiveLocales);
                boolean addSystemSuggestionFlag2 = addSystemSuggestionFlag(localeInfo2, this.mImeLocales);
                if (!z4 && !addSystemSuggestionFlag && !addSystemSuggestionFlag2) {
                    hashSet.add(localeInfo2);
                }
            }
        }
        if (z3) {
            hashSet.addAll(filterSupportedLocales(systemSupportedLocale, appSupportedLocales.mAppSupportedLocales));
            set = getSuggestedLocales(hashSet);
        }
        if (!z2 && android.os.SystemProperties.getBoolean(PROP_APP_LANGUAGE_SUGGESTION, true)) {
            java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> filterSupportedLocales = filterSupportedLocales(this.mAllAppActiveLocales, appSupportedLocales.mAppSupportedLocales);
            if (set != null) {
                filterSupportedLocales = addImeSuggestionFlag(filterSameLanguageAndCountry(filterSupportedLocales, set));
            }
            hashSet.addAll(filterSupportedLocales);
            set.addAll(filterSupportedLocales);
            java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> filterSupportedLocales2 = filterSupportedLocales(this.mImeLocales, appSupportedLocales.mAppSupportedLocales);
            if (set != null) {
                filterSupportedLocales2 = filterSameLanguageAndCountry(filterSupportedLocales2, set);
            }
            hashSet.addAll(filterSupportedLocales2);
            set.addAll(filterSupportedLocales2);
        }
        if (!z2 && z3) {
            hashSet.add(com.android.internal.app.LocaleStore.getSystemDefaultLocaleInfo(this.mAppCurrentLocale == null));
        }
        if (android.os.Build.isDebuggable()) {
            android.util.Log.d(TAG, "App locale list: " + hashSet);
        }
        return hashSet;
    }

    @Override // com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase
    public boolean hasSpecificPackageName() {
        return true;
    }

    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSuggestedLocales(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set) {
        return (java.util.Set) set.stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isSuggested;
                isSuggested = ((com.android.internal.app.LocaleStore.LocaleInfo) obj).isSuggested();
                return isSuggested;
            }
        }).collect(java.util.stream.Collectors.toSet());
    }

    private boolean addSystemSuggestionFlag(com.android.internal.app.LocaleStore.LocaleInfo localeInfo, java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set) {
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo2 : set) {
            if (localeInfo2.getLocale().equals(localeInfo.getLocale())) {
                localeInfo2.extendSuggestionOfType(64);
                return true;
            }
        }
        return false;
    }

    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> addImeSuggestionFlag(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set) {
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo : set) {
            java.util.Iterator<com.android.internal.app.LocaleStore.LocaleInfo> it = this.mImeLocales.iterator();
            while (it.hasNext()) {
                if (it.next().getLocale().equals(localeInfo.getLocale())) {
                    localeInfo.extendSuggestionOfType(32);
                }
            }
        }
        return set;
    }

    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> filterSameLanguageAndCountry(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set, java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set2) {
        boolean z;
        java.util.HashSet hashSet = new java.util.HashSet(set.size());
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo : set) {
            java.util.Locale locale = localeInfo.getLocale();
            java.util.Iterator<com.android.internal.app.LocaleStore.LocaleInfo> it = set2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                java.util.Locale locale2 = it.next().getLocale();
                if (locale.getLanguage().equals(locale2.getLanguage()) && locale.getCountry().equals(locale2.getCountry())) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                hashSet.add(localeInfo);
            }
        }
        return hashSet;
    }

    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> filterSupportedLocales(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set, java.util.HashSet<java.util.Locale> hashSet) {
        java.util.HashSet hashSet2 = new java.util.HashSet();
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo : set) {
            if (hashSet.contains(localeInfo.getLocale())) {
                hashSet2.add(localeInfo);
            } else {
                java.util.Iterator<java.util.Locale> it = hashSet.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (android.os.LocaleList.matchesLanguageAndScript(localeInfo.getLocale(), it.next())) {
                            hashSet2.add(localeInfo);
                            break;
                        }
                    }
                }
            }
        }
        return hashSet2;
    }
}
