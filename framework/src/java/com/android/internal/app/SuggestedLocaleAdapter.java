package com.android.internal.app;

/* loaded from: classes4.dex */
public class SuggestedLocaleAdapter extends android.widget.BaseAdapter implements android.widget.Filterable {
    protected static final int APP_LANGUAGE_PICKER_TYPE_COUNT = 5;
    protected static final int MIN_REGIONS_FOR_SUGGESTIONS = 6;
    protected static final int SYSTEM_LANGUAGE_TYPE_COUNT = 3;
    protected static final int SYSTEM_LANGUAGE_WITHOUT_HEADER_TYPE_COUNT = 1;
    protected static final int TYPE_CURRENT_LOCALE = 4;
    protected static final int TYPE_HEADER_ALL_OTHERS = 1;
    protected static final int TYPE_HEADER_SUGGESTED = 0;
    protected static final int TYPE_LOCALE = 2;
    protected static final int TYPE_SYSTEM_LANGUAGE_FOR_APP_LANGUAGE_PICKER = 3;
    protected android.content.Context mContextOverride;
    protected final boolean mCountryMode;
    protected java.util.Locale mDisplayLocale;
    private boolean mHasSpecificAppPackageName;
    protected android.view.LayoutInflater mInflater;
    protected boolean mIsNumberingMode;
    protected java.util.ArrayList<com.android.internal.app.LocaleStore.LocaleInfo> mLocaleOptions;
    protected java.util.ArrayList<com.android.internal.app.LocaleStore.LocaleInfo> mOriginalLocaleOptions;
    protected int mSuggestionCount;

    public SuggestedLocaleAdapter(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set, boolean z) {
        this(set, z, false);
    }

    public SuggestedLocaleAdapter(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set, boolean z, boolean z2) {
        this.mDisplayLocale = null;
        this.mContextOverride = null;
        this.mCountryMode = z;
        this.mLocaleOptions = new java.util.ArrayList<>(set.size());
        this.mHasSpecificAppPackageName = z2;
        for (com.android.internal.app.LocaleStore.LocaleInfo localeInfo : set) {
            if (localeInfo.isSuggested()) {
                this.mSuggestionCount++;
            }
            this.mLocaleOptions.add(localeInfo);
        }
    }

    public void setNumberingSystemMode(boolean z) {
        this.mIsNumberingMode = z;
    }

    public boolean getIsForNumberingSystem() {
        return this.mIsNumberingMode;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return getItemViewType(i) == 2 || getItemViewType(i) == 3 || getItemViewType(i) == 4;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        if (!showHeaders()) {
            com.android.internal.app.LocaleStore.LocaleInfo localeInfo = (com.android.internal.app.LocaleStore.LocaleInfo) getItem(i);
            if (localeInfo.isSystemLocale()) {
                return 3;
            }
            return localeInfo.isAppCurrentLocale() ? 4 : 2;
        }
        if (i == 0) {
            return 0;
        }
        if (i == this.mSuggestionCount + 1) {
            return 1;
        }
        com.android.internal.app.LocaleStore.LocaleInfo localeInfo2 = (com.android.internal.app.LocaleStore.LocaleInfo) getItem(i);
        if (localeInfo2 == null) {
            throw new java.lang.NullPointerException("Non header locale cannot be null");
        }
        if (localeInfo2.isSystemLocale()) {
            return 3;
        }
        return localeInfo2.isAppCurrentLocale() ? 4 : 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (this.mHasSpecificAppPackageName && showHeaders()) {
            return 5;
        }
        if (showHeaders()) {
            return 3;
        }
        return 1;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (showHeaders()) {
            return this.mLocaleOptions.size() + 2;
        }
        return this.mLocaleOptions.size();
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        int i2;
        if (isHeaderPosition(i)) {
            return null;
        }
        if (!showHeaders()) {
            i2 = 0;
        } else {
            i2 = i > this.mSuggestionCount ? -2 : -1;
        }
        return this.mLocaleOptions.get(i + i2);
    }

    private boolean isHeaderPosition(int i) {
        return showHeaders() && (i == 0 || i == this.mSuggestionCount + 1);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setDisplayLocale(android.content.Context context, java.util.Locale locale) {
        if (locale == null) {
            this.mDisplayLocale = null;
            this.mContextOverride = null;
        } else if (!locale.equals(this.mDisplayLocale)) {
            this.mDisplayLocale = locale;
            android.content.res.Configuration configuration = new android.content.res.Configuration();
            configuration.setLocale(locale);
            this.mContextOverride = context.createConfigurationContext(configuration);
        }
    }

    protected void setTextTo(android.widget.TextView textView, int i) {
        if (this.mContextOverride == null) {
            textView.setText(i);
        } else {
            textView.lambda$setTextAsync$0(this.mContextOverride.getText(i));
        }
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        android.widget.TextView textView;
        if (view == null && this.mInflater == null) {
            this.mInflater = android.view.LayoutInflater.from(viewGroup.getContext());
        }
        int itemViewType = getItemViewType(i);
        android.view.View newViewIfNeeded = getNewViewIfNeeded(view, viewGroup, itemViewType, i);
        switch (itemViewType) {
            case 0:
            case 1:
                android.widget.TextView textView2 = (android.widget.TextView) newViewIfNeeded;
                if (itemViewType == 0) {
                    if (this.mCountryMode && !this.mIsNumberingMode) {
                        setTextTo(textView2, com.android.internal.R.string.language_picker_regions_section_suggested);
                    } else {
                        setTextTo(textView2, com.android.internal.R.string.language_picker_section_suggested);
                    }
                } else if (this.mCountryMode && !this.mIsNumberingMode) {
                    setTextTo(textView2, com.android.internal.R.string.region_picker_section_all);
                } else {
                    setTextTo(textView2, com.android.internal.R.string.language_picker_section_all);
                }
                textView2.setTextLocale(this.mDisplayLocale != null ? this.mDisplayLocale : java.util.Locale.getDefault());
                return newViewIfNeeded;
            case 2:
            default:
                updateTextView(newViewIfNeeded, (android.widget.TextView) newViewIfNeeded.findViewById(com.android.internal.R.id.locale), i);
                return newViewIfNeeded;
            case 3:
                com.android.internal.app.LocaleStore.LocaleInfo localeInfo = (com.android.internal.app.LocaleStore.LocaleInfo) getItem(i);
                if (localeInfo == null) {
                    throw new java.lang.NullPointerException("Non header locale cannot be null.");
                }
                if (localeInfo.isAppCurrentLocale()) {
                    textView = (android.widget.TextView) newViewIfNeeded.findViewById(com.android.internal.R.id.language_picker_item);
                } else {
                    textView = (android.widget.TextView) newViewIfNeeded.findViewById(com.android.internal.R.id.locale);
                }
                textView.setText(com.android.internal.R.string.system_locale_title);
                return newViewIfNeeded;
            case 4:
                updateTextView(newViewIfNeeded, (android.widget.TextView) newViewIfNeeded.findViewById(com.android.internal.R.id.language_picker_item), i);
                return newViewIfNeeded;
        }
    }

    private android.view.View getNewViewIfNeeded(android.view.View view, android.view.ViewGroup viewGroup, int i, int i2) {
        switch (i) {
            case 0:
            case 1:
                if (!((view instanceof android.widget.TextView) && view.findViewById(com.android.internal.R.id.language_picker_header) != null)) {
                    return this.mInflater.inflate(com.android.internal.R.layout.language_picker_section_header, viewGroup, false);
                }
                return view;
            case 2:
            default:
                if (!((view instanceof android.widget.TextView) && view.findViewById(com.android.internal.R.id.locale) != null)) {
                    return this.mInflater.inflate(com.android.internal.R.layout.language_picker_item, viewGroup, false);
                }
                return view;
            case 3:
                if (((com.android.internal.app.LocaleStore.LocaleInfo) getItem(i2)).isAppCurrentLocale()) {
                    if (!((view instanceof android.widget.LinearLayout) && view.findViewById(com.android.internal.R.id.language_picker_item) != null)) {
                        return this.mInflater.inflate(com.android.internal.R.layout.app_language_picker_current_locale_item, viewGroup, false);
                    }
                    return view;
                }
                if (!((view instanceof android.widget.TextView) && view.findViewById(com.android.internal.R.id.locale) != null)) {
                    return this.mInflater.inflate(com.android.internal.R.layout.language_picker_item, viewGroup, false);
                }
                return view;
            case 4:
                if (!((view instanceof android.widget.LinearLayout) && view.findViewById(com.android.internal.R.id.language_picker_item) != null)) {
                    return this.mInflater.inflate(com.android.internal.R.layout.app_language_picker_current_locale_item, viewGroup, false);
                }
                return view;
        }
    }

    protected boolean showHeaders() {
        return ((this.mCountryMode && this.mLocaleOptions.size() < 6) || this.mSuggestionCount == 0 || this.mSuggestionCount == this.mLocaleOptions.size()) ? false : true;
    }

    public void sort(com.android.internal.app.LocaleHelper.LocaleInfoComparator localeInfoComparator) {
        java.util.Collections.sort(this.mLocaleOptions, localeInfoComparator);
    }

    class FilterByNativeAndUiNames extends android.widget.Filter {
        FilterByNativeAndUiNames() {
        }

        @Override // android.widget.Filter
        protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence charSequence) {
            android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
            if (com.android.internal.app.SuggestedLocaleAdapter.this.mOriginalLocaleOptions == null) {
                com.android.internal.app.SuggestedLocaleAdapter.this.mOriginalLocaleOptions = new java.util.ArrayList<>(com.android.internal.app.SuggestedLocaleAdapter.this.mLocaleOptions);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(com.android.internal.app.SuggestedLocaleAdapter.this.mOriginalLocaleOptions);
            if (charSequence == null || charSequence.length() == 0) {
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
            } else {
                java.util.Locale locale = java.util.Locale.getDefault();
                java.lang.String normalizeForSearch = com.android.internal.app.LocaleHelper.normalizeForSearch(charSequence.toString(), locale);
                int size = arrayList.size();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (int i = 0; i < size; i++) {
                    com.android.internal.app.LocaleStore.LocaleInfo localeInfo = (com.android.internal.app.LocaleStore.LocaleInfo) arrayList.get(i);
                    java.lang.String normalizeForSearch2 = com.android.internal.app.LocaleHelper.normalizeForSearch(localeInfo.getFullNameInUiLanguage(), locale);
                    if (wordMatches(com.android.internal.app.LocaleHelper.normalizeForSearch(localeInfo.getFullNameNative(), locale), normalizeForSearch) || wordMatches(normalizeForSearch2, normalizeForSearch)) {
                        arrayList2.add(localeInfo);
                    }
                }
                filterResults.values = arrayList2;
                filterResults.count = arrayList2.size();
            }
            return filterResults;
        }

        boolean wordMatches(java.lang.String str, java.lang.String str2) {
            if (str.startsWith(str2)) {
                return true;
            }
            for (java.lang.String str3 : str.split(" ")) {
                if (str3.startsWith(str2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.widget.Filter
        protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
            com.android.internal.app.SuggestedLocaleAdapter.this.mLocaleOptions = (java.util.ArrayList) filterResults.values;
            com.android.internal.app.SuggestedLocaleAdapter.this.mSuggestionCount = 0;
            java.util.Iterator<com.android.internal.app.LocaleStore.LocaleInfo> it = com.android.internal.app.SuggestedLocaleAdapter.this.mLocaleOptions.iterator();
            while (it.hasNext()) {
                if (it.next().isSuggested()) {
                    com.android.internal.app.SuggestedLocaleAdapter.this.mSuggestionCount++;
                }
            }
            if (filterResults.count > 0) {
                com.android.internal.app.SuggestedLocaleAdapter.this.notifyDataSetChanged();
            } else {
                com.android.internal.app.SuggestedLocaleAdapter.this.notifyDataSetInvalidated();
            }
        }
    }

    @Override // android.widget.Filterable
    public android.widget.Filter getFilter() {
        return new com.android.internal.app.SuggestedLocaleAdapter.FilterByNativeAndUiNames();
    }

    private void updateTextView(android.view.View view, android.widget.TextView textView, int i) {
        int i2;
        com.android.internal.app.LocaleStore.LocaleInfo localeInfo = (com.android.internal.app.LocaleStore.LocaleInfo) getItem(i);
        textView.lambda$setTextAsync$0(this.mIsNumberingMode ? localeInfo.getNumberingSystem() : localeInfo.getLabel(this.mCountryMode));
        textView.setTextLocale(localeInfo.getLocale());
        textView.setContentDescription(this.mIsNumberingMode ? localeInfo.getNumberingSystem() : localeInfo.getContentDescription(this.mCountryMode));
        if (this.mCountryMode) {
            int layoutDirectionFromLocale = android.text.TextUtils.getLayoutDirectionFromLocale(localeInfo.getParent());
            view.setLayoutDirection(layoutDirectionFromLocale);
            if (layoutDirectionFromLocale == 1) {
                i2 = 4;
            } else {
                i2 = 3;
            }
            textView.setTextDirection(i2);
        }
    }
}
