package com.android.internal.app;

/* loaded from: classes4.dex */
class SystemLocaleCollector implements com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase {
    private final android.content.Context mContext;
    private android.os.LocaleList mExplicitLocales;

    SystemLocaleCollector(android.content.Context context) {
        this(context, null);
    }

    SystemLocaleCollector(android.content.Context context, android.os.LocaleList localeList) {
        this.mContext = context;
        this.mExplicitLocales = localeList;
    }

    @Override // com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase
    public java.util.HashSet<java.lang.String> getIgnoredLocaleList(boolean z) {
        java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet<>();
        if (!z) {
            java.util.Collections.addAll(hashSet, com.android.internal.app.LocalePicker.getLocales().toLanguageTags().split(","));
        }
        return hashSet;
    }

    @Override // com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase
    public java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSupportedLocaleList(com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2) {
        java.util.HashSet<java.lang.String> ignoredLocaleList = getIgnoredLocaleList(z);
        if (z2) {
            return com.android.internal.app.LocaleStore.getLevelLocales(this.mContext, ignoredLocaleList, localeInfo, z, this.mExplicitLocales);
        }
        return com.android.internal.app.LocaleStore.getLevelLocales(this.mContext, ignoredLocaleList, null, z, this.mExplicitLocales);
    }

    @Override // com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase
    public boolean hasSpecificPackageName() {
        return false;
    }
}
