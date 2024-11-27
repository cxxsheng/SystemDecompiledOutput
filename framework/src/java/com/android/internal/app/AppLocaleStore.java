package com.android.internal.app;

/* loaded from: classes4.dex */
public class AppLocaleStore {
    private static final java.lang.String TAG = com.android.internal.app.AppLocaleStore.class.getSimpleName();

    public static com.android.internal.app.AppLocaleStore.AppLocaleResult getAppSupportedLocales(android.content.Context context, java.lang.String str) {
        android.app.LocaleConfig localeConfig;
        com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus localeStatus = com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.UNKNOWN_FAILURE;
        java.util.HashSet<java.util.Locale> hashSet = new java.util.HashSet<>();
        java.util.HashSet<java.util.Locale> assetLocales = getAssetLocales(context, str);
        try {
            localeConfig = new android.app.LocaleConfig(context.createPackageContext(str, 0));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Can not found the package name : " + str + " / " + e);
            localeConfig = null;
        }
        if (localeConfig != null) {
            if (localeConfig.getStatus() == 0) {
                android.os.LocaleList supportedLocales = localeConfig.getSupportedLocales();
                boolean z = !hasInstallerInfo(context, str) && isSystemApp(context, str);
                android.util.Log.d(TAG, "filterNonMatchingLocale. , shouldFilterNotMatchingLocale: " + z + ", assetLocale size: " + assetLocales.size() + ", packageLocaleList size: " + supportedLocales.size());
                for (int i = 0; i < supportedLocales.size(); i++) {
                    hashSet.add(supportedLocales.get(i));
                }
                if (z) {
                    hashSet = filterNotMatchingLocale(hashSet, assetLocales);
                }
                localeStatus = hashSet.size() > 0 ? com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_LOCAL_CONFIG : com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.NO_SUPPORTED_LANGUAGE_IN_APP;
            } else if (localeConfig.getStatus() == 1) {
                if (assetLocales.size() > 0) {
                    localeStatus = com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_ASSET;
                    hashSet = assetLocales;
                } else {
                    localeStatus = com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus.ASSET_LOCALE_IS_EMPTY;
                }
            }
        }
        android.util.Log.d(TAG, "getAppSupportedLocales(). package: " + str + ", status: " + localeStatus + ", appSupportedLocales:" + hashSet.size());
        return new com.android.internal.app.AppLocaleStore.AppLocaleResult(localeStatus, hashSet);
    }

    private static java.util.HashSet<java.util.Locale> getAssetLocales(android.content.Context context, java.lang.String str) {
        java.util.HashSet<java.util.Locale> hashSet = new java.util.HashSet<>();
        try {
            android.content.pm.PackageManager packageManager = context.getPackageManager();
            java.lang.String[] nonSystemLocales = packageManager.getResourcesForApplication(packageManager.getPackageInfo(str, 131072).applicationInfo).getAssets().getNonSystemLocales();
            if (nonSystemLocales == null) {
                android.util.Log.i(TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + str + "] locales are null.");
            } else if (nonSystemLocales.length <= 0) {
                android.util.Log.i(TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + str + "] locales length is 0.");
            } else {
                for (java.lang.String str2 : nonSystemLocales) {
                    hashSet.add(java.util.Locale.forLanguageTag(str2));
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Can not found the package name : " + str + " / " + e);
        }
        return hashSet;
    }

    private static java.util.HashSet<java.util.Locale> filterNotMatchingLocale(java.util.HashSet<java.util.Locale> hashSet, final java.util.HashSet<java.util.Locale> hashSet2) {
        return (java.util.HashSet) hashSet.stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.app.AppLocaleStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean matchLanguageInSet;
                matchLanguageInSet = com.android.internal.app.AppLocaleStore.matchLanguageInSet((java.util.Locale) obj, hashSet2);
                return matchLanguageInSet;
            }
        }).collect(java.util.stream.Collectors.toCollection(new java.util.function.Supplier() { // from class: com.android.internal.app.AppLocaleStore$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return new java.util.HashSet();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean matchLanguageInSet(java.util.Locale locale, java.util.HashSet<java.util.Locale> hashSet) {
        if (hashSet.contains(locale)) {
            return true;
        }
        java.util.Iterator<java.util.Locale> it = hashSet.iterator();
        while (it.hasNext()) {
            if (android.os.LocaleList.matchesLanguageAndScript(it.next(), locale)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasInstallerInfo(android.content.Context context, java.lang.String str) {
        try {
            return context.getPackageManager().getInstallSourceInfo(str) != null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Installer info not found for: " + str);
            return false;
        }
    }

    private static boolean isSystemApp(android.content.Context context, java.lang.String str) {
        try {
            return context.getPackageManager().getApplicationInfoAsUser(str, 0, context.getUserId()).isSystemApp();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Application info not found for: " + str);
            return false;
        }
    }

    public static class AppLocaleResult {
        public java.util.HashSet<java.util.Locale> mAppSupportedLocales;
        com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus mLocaleStatus;

        public enum LocaleStatus {
            UNKNOWN_FAILURE,
            NO_SUPPORTED_LANGUAGE_IN_APP,
            ASSET_LOCALE_IS_EMPTY,
            GET_SUPPORTED_LANGUAGE_FROM_LOCAL_CONFIG,
            GET_SUPPORTED_LANGUAGE_FROM_ASSET
        }

        public AppLocaleResult(com.android.internal.app.AppLocaleStore.AppLocaleResult.LocaleStatus localeStatus, java.util.HashSet<java.util.Locale> hashSet) {
            this.mLocaleStatus = localeStatus;
            this.mAppSupportedLocales = hashSet;
        }
    }
}
