package com.android.internal.app;

/* loaded from: classes4.dex */
public class BilingualSuggestedLocaleAdapter extends com.android.internal.app.SuggestedLocaleAdapter {
    private final java.util.Locale mSecondaryLocale;
    private final int mSecondaryLocaleTextDir;
    private com.android.internal.app.LocaleStore.LocaleInfo mSelectedLocaleInfo;
    private final boolean mShowSelection;

    public BilingualSuggestedLocaleAdapter(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set, boolean z, java.util.Locale locale) {
        this(set, z, locale, false);
    }

    public BilingualSuggestedLocaleAdapter(java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> set, boolean z, java.util.Locale locale, boolean z2) {
        super(set, z);
        this.mSecondaryLocale = locale;
        if (android.text.TextUtils.getLayoutDirectionFromLocale(locale) == 1) {
            this.mSecondaryLocaleTextDir = 4;
        } else {
            this.mSecondaryLocaleTextDir = 3;
        }
        this.mShowSelection = z2;
    }

    @Override // com.android.internal.app.SuggestedLocaleAdapter, android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        if (view == null && this.mInflater == null) {
            this.mInflater = android.view.LayoutInflater.from(viewGroup.getContext());
        }
        int itemViewType = getItemViewType(i);
        switch (itemViewType) {
            case 0:
            case 1:
                if (!(view instanceof android.widget.TextView)) {
                    view = this.mInflater.inflate(com.android.internal.R.layout.language_picker_bilingual_section_header, viewGroup, false);
                }
                android.widget.TextView textView = (android.widget.TextView) view;
                if (itemViewType == 0) {
                    setHeaderText(textView, com.android.internal.R.string.language_picker_section_suggested_bilingual, com.android.internal.R.string.region_picker_section_suggested_bilingual);
                } else {
                    setHeaderText(textView, com.android.internal.R.string.language_picker_section_all, com.android.internal.R.string.region_picker_section_all);
                }
                return view;
            default:
                if (!(view instanceof android.view.ViewGroup)) {
                    view = this.mInflater.inflate(com.android.internal.R.layout.language_picker_bilingual_item, viewGroup, false);
                }
                com.android.internal.app.LocaleStore.LocaleInfo localeInfo = (com.android.internal.app.LocaleStore.LocaleInfo) getItem(i);
                if (this.mShowSelection) {
                    setItemState(isSelectedLocaleInfo(localeInfo), view);
                }
                setLocaleToListItem(view, localeInfo);
                return view;
        }
    }

    public void setSelectedLocaleInfo(com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
        this.mSelectedLocaleInfo = localeInfo;
        notifyDataSetChanged();
    }

    public com.android.internal.app.LocaleStore.LocaleInfo getSelectedLocaleInfo() {
        return this.mSelectedLocaleInfo;
    }

    private boolean isSelectedLocaleInfo(com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
        return (localeInfo == null || this.mSelectedLocaleInfo == null || !localeInfo.getId().equals(this.mSelectedLocaleInfo.getId())) ? false : true;
    }

    private void setItemState(boolean z, android.view.View view) {
        android.widget.RelativeLayout relativeLayout = (android.widget.RelativeLayout) view;
        android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.indicator);
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(com.android.internal.R.id.locale_native);
        android.widget.TextView textView2 = (android.widget.TextView) view.findViewById(com.android.internal.R.id.locale_secondary);
        if (imageView == null || textView == null || textView2 == null) {
            return;
        }
        textView.setSelected(z);
        textView2.setSelected(z);
        if (z) {
            relativeLayout.setBackgroundResource(com.android.internal.R.drawable.language_picker_item_bg_selected);
            imageView.setVisibility(0);
        } else {
            relativeLayout.setBackgroundResource(0);
            imageView.setVisibility(8);
        }
    }

    private void setHeaderText(android.widget.TextView textView, int i, int i2) {
        if (this.mCountryMode) {
            setTextTo(textView, i2);
        } else {
            setTextTo(textView, i);
        }
    }

    private void setLocaleToListItem(android.view.View view, com.android.internal.app.LocaleStore.LocaleInfo localeInfo) {
        int i;
        if (localeInfo == null) {
            throw new java.lang.NullPointerException("Cannot set locale, locale info is null.");
        }
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(com.android.internal.R.id.locale_native);
        textView.lambda$setTextAsync$0(localeInfo.getLabel(this.mCountryMode));
        textView.setTextLocale(localeInfo.getLocale());
        textView.setContentDescription(localeInfo.getContentDescription(this.mCountryMode));
        android.widget.TextView textView2 = (android.widget.TextView) view.findViewById(com.android.internal.R.id.locale_secondary);
        textView2.lambda$setTextAsync$0(localeInfo.getLocale().getDisplayLanguage(this.mSecondaryLocale));
        textView2.setTextDirection(this.mSecondaryLocaleTextDir);
        if (this.mCountryMode) {
            int layoutDirectionFromLocale = android.text.TextUtils.getLayoutDirectionFromLocale(localeInfo.getParent());
            view.setLayoutDirection(layoutDirectionFromLocale);
            if (layoutDirectionFromLocale == 1) {
                i = 4;
            } else {
                i = 3;
            }
            textView.setTextDirection(i);
        }
    }
}
