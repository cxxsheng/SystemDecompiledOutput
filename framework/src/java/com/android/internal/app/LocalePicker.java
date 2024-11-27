package com.android.internal.app;

/* loaded from: classes4.dex */
public class LocalePicker extends android.app.ListFragment {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "LocalePicker";
    private static final java.lang.String[] pseudoLocales = {"en-XA", "ar-XB"};
    com.android.internal.app.LocalePicker.LocaleSelectionListener mListener;

    public interface LocaleSelectionListener {
        void onLocaleSelected(java.util.Locale locale);
    }

    public static class LocaleInfo implements java.lang.Comparable<com.android.internal.app.LocalePicker.LocaleInfo> {
        static final java.text.Collator sCollator = java.text.Collator.getInstance();
        java.lang.String label;
        final java.util.Locale locale;

        public LocaleInfo(java.lang.String str, java.util.Locale locale) {
            this.label = str;
            this.locale = locale;
        }

        public java.lang.String getLabel() {
            return this.label;
        }

        public java.util.Locale getLocale() {
            return this.locale;
        }

        public java.lang.String toString() {
            return this.label;
        }

        @Override // java.lang.Comparable
        public int compareTo(com.android.internal.app.LocalePicker.LocaleInfo localeInfo) {
            return sCollator.compare(this.label, localeInfo.label);
        }
    }

    public static java.lang.String[] getSystemAssetLocales() {
        return android.content.res.Resources.getSystem().getAssets().getLocales();
    }

    public static java.lang.String[] getSupportedLocales(android.content.Context context) {
        if (context == null) {
            return new java.lang.String[0];
        }
        java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.supported_locales);
        java.util.function.Predicate<java.lang.String> localeFilter = getLocaleFilter();
        if (localeFilter == null) {
            return stringArray;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(stringArray.length);
        for (java.lang.String str : stringArray) {
            if (localeFilter.test(str)) {
                arrayList.add(str);
            }
        }
        int size = arrayList.size();
        if (size == stringArray.length) {
            return stringArray;
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[size]);
    }

    private static java.util.function.Predicate<java.lang.String> getLocaleFilter() {
        try {
            return (java.util.function.Predicate) android.sysprop.LocalizationProperties.locale_filter().map(new java.util.function.Function() { // from class: com.android.internal.app.LocalePicker$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.function.Predicate asPredicate;
                    asPredicate = java.util.regex.Pattern.compile((java.lang.String) obj).asPredicate();
                    return asPredicate;
                }
            }).orElse(null);
        } catch (java.lang.SecurityException e) {
            android.util.Log.e(TAG, "Failed to read locale filter.", e);
            return null;
        } catch (java.util.regex.PatternSyntaxException e2) {
            android.util.Log.e(TAG, "Bad locale filter format (\"" + e2.getPattern() + "\"), skipping.");
            return null;
        }
    }

    public static java.util.List<com.android.internal.app.LocalePicker.LocaleInfo> getAllAssetLocales(android.content.Context context, boolean z) {
        android.content.res.Resources resources = context.getResources();
        java.lang.String[] systemAssetLocales = getSystemAssetLocales();
        java.util.ArrayList arrayList = new java.util.ArrayList(systemAssetLocales.length);
        java.util.Collections.addAll(arrayList, systemAssetLocales);
        java.util.Collections.sort(arrayList);
        java.lang.String[] stringArray = resources.getStringArray(com.android.internal.R.array.special_locale_codes);
        java.lang.String[] stringArray2 = resources.getStringArray(com.android.internal.R.array.special_locale_names);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(arrayList.size());
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            java.util.Locale forLanguageTag = java.util.Locale.forLanguageTag(((java.lang.String) it.next()).replace('_', '-'));
            if (forLanguageTag != null && !"und".equals(forLanguageTag.getLanguage()) && !forLanguageTag.getLanguage().isEmpty() && !forLanguageTag.getCountry().isEmpty() && (z || !android.os.LocaleList.isPseudoLocale(forLanguageTag))) {
                if (arrayList2.isEmpty()) {
                    arrayList2.add(new com.android.internal.app.LocalePicker.LocaleInfo(toTitleCase(forLanguageTag.getDisplayLanguage(forLanguageTag)), forLanguageTag));
                } else {
                    com.android.internal.app.LocalePicker.LocaleInfo localeInfo = (com.android.internal.app.LocalePicker.LocaleInfo) arrayList2.get(arrayList2.size() - 1);
                    if (localeInfo.locale.getLanguage().equals(forLanguageTag.getLanguage()) && !localeInfo.locale.getLanguage().equals("zz")) {
                        localeInfo.label = toTitleCase(getDisplayName(localeInfo.locale, stringArray, stringArray2));
                        arrayList2.add(new com.android.internal.app.LocalePicker.LocaleInfo(toTitleCase(getDisplayName(forLanguageTag, stringArray, stringArray2)), forLanguageTag));
                    } else {
                        arrayList2.add(new com.android.internal.app.LocalePicker.LocaleInfo(toTitleCase(forLanguageTag.getDisplayLanguage(forLanguageTag)), forLanguageTag));
                    }
                }
            }
        }
        java.util.Collections.sort(arrayList2);
        return arrayList2;
    }

    public static android.widget.ArrayAdapter<com.android.internal.app.LocalePicker.LocaleInfo> constructAdapter(android.content.Context context) {
        return constructAdapter(context, com.android.internal.R.layout.locale_picker_item, com.android.internal.R.id.locale);
    }

    public static android.widget.ArrayAdapter<com.android.internal.app.LocalePicker.LocaleInfo> constructAdapter(android.content.Context context, final int i, final int i2) {
        java.util.List<com.android.internal.app.LocalePicker.LocaleInfo> allAssetLocales = getAllAssetLocales(context, android.provider.Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) != 0);
        final android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        return new android.widget.ArrayAdapter<com.android.internal.app.LocalePicker.LocaleInfo>(context, i, i2, allAssetLocales) { // from class: com.android.internal.app.LocalePicker.1
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public android.view.View getView(int i3, android.view.View view, android.view.ViewGroup viewGroup) {
                android.widget.TextView textView;
                if (view == null) {
                    view = layoutInflater.inflate(i, viewGroup, false);
                    textView = (android.widget.TextView) view.findViewById(i2);
                    view.setTag(textView);
                } else {
                    textView = (android.widget.TextView) view.getTag();
                }
                com.android.internal.app.LocalePicker.LocaleInfo item = getItem(i3);
                textView.lambda$setTextAsync$0(item.toString());
                textView.setTextLocale(item.getLocale());
                return view;
            }
        };
    }

    private static java.lang.String toTitleCase(java.lang.String str) {
        if (str.length() == 0) {
            return str;
        }
        return java.lang.Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static java.lang.String getDisplayName(java.util.Locale locale, java.lang.String[] strArr, java.lang.String[] strArr2) {
        java.lang.String obj = locale.toString();
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(obj)) {
                return strArr2[i];
            }
        }
        return locale.getDisplayName(locale);
    }

    @Override // android.app.Fragment
    public void onActivityCreated(android.os.Bundle bundle) {
        super.onActivityCreated(bundle);
        setListAdapter(constructAdapter(getActivity()));
    }

    public void setLocaleSelectionListener(com.android.internal.app.LocalePicker.LocaleSelectionListener localeSelectionListener) {
        this.mListener = localeSelectionListener;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        getListView().requestFocus();
    }

    @Override // android.app.ListFragment
    public void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
        if (this.mListener != null) {
            this.mListener.onLocaleSelected(((com.android.internal.app.LocalePicker.LocaleInfo) getListAdapter().getItem(i)).locale);
        }
    }

    public static void updateLocale(java.util.Locale locale) {
        updateLocales(new android.os.LocaleList(locale));
    }

    public static void updateLocales(android.os.LocaleList localeList) {
        if (localeList != null) {
            localeList = removeExcludedLocales(localeList);
        }
        try {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            android.content.res.Configuration configuration = new android.content.res.Configuration();
            configuration.setLocales(localeList);
            configuration.userSetLocale = true;
            service.updatePersistentConfigurationWithAttribution(configuration, android.app.ActivityThread.currentOpPackageName(), null);
            android.app.backup.BackupManager.dataChanged("com.android.providers.settings");
        } catch (android.os.RemoteException e) {
        }
    }

    private static android.os.LocaleList removeExcludedLocales(android.os.LocaleList localeList) {
        java.util.function.Predicate<java.lang.String> localeFilter = getLocaleFilter();
        if (localeFilter == null) {
            return localeList;
        }
        int size = localeList.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            java.util.Locale locale = localeList.get(i);
            if (localeFilter.test(locale.toString())) {
                arrayList.add(locale);
            }
        }
        if (size == arrayList.size()) {
            return localeList;
        }
        return new android.os.LocaleList((java.util.Locale[]) arrayList.toArray(new java.util.Locale[0]));
    }

    public static android.os.LocaleList getLocales() {
        try {
            return android.app.ActivityManager.getService().getConfiguration().getLocales();
        } catch (android.os.RemoteException e) {
            return android.os.LocaleList.getDefault();
        }
    }
}
