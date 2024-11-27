package com.android.internal.app;

/* loaded from: classes4.dex */
public class LocalePickerWithRegion extends android.app.ListFragment implements android.widget.SearchView.OnQueryTextListener {
    private static final java.lang.String PARENT_FRAGMENT_NAME = "localeListEditor";
    private static final java.lang.String TAG = com.android.internal.app.LocalePickerWithRegion.class.getSimpleName();
    private com.android.internal.app.SuggestedLocaleAdapter mAdapter;
    private com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener mListener;
    private java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> mLocaleList;
    private com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase mLocalePickerCollector;
    private android.view.MenuItem.OnActionExpandListener mOnActionExpandListener;
    private com.android.internal.app.LocaleStore.LocaleInfo mParentLocale;
    private boolean mTranslatedOnly = false;
    private android.widget.SearchView mSearchView = null;
    private java.lang.CharSequence mPreviousSearch = null;
    private boolean mPreviousSearchHadFocus = false;
    private int mFirstVisiblePosition = 0;
    private int mTopDistance = 0;
    private java.lang.CharSequence mTitle = null;
    private boolean mIsNumberingSystem = false;

    interface LocaleCollectorBase {
        java.util.HashSet<java.lang.String> getIgnoredLocaleList(boolean z);

        java.util.Set<com.android.internal.app.LocaleStore.LocaleInfo> getSupportedLocaleList(com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2);

        boolean hasSpecificPackageName();
    }

    public interface LocaleSelectedListener {
        void onLocaleSelected(com.android.internal.app.LocaleStore.LocaleInfo localeInfo);
    }

    private static com.android.internal.app.LocalePickerWithRegion createNumberingSystemPicker(com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener localeSelectedListener, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, android.view.MenuItem.OnActionExpandListener onActionExpandListener, com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase localeCollectorBase) {
        com.android.internal.app.LocalePickerWithRegion localePickerWithRegion = new com.android.internal.app.LocalePickerWithRegion();
        localePickerWithRegion.setOnActionExpandListener(onActionExpandListener);
        localePickerWithRegion.setIsNumberingSystem(true);
        if (localePickerWithRegion.setListener(localeSelectedListener, localeInfo, z, localeCollectorBase)) {
            return localePickerWithRegion;
        }
        return null;
    }

    private static com.android.internal.app.LocalePickerWithRegion createCountryPicker(com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener localeSelectedListener, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, android.view.MenuItem.OnActionExpandListener onActionExpandListener, com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase localeCollectorBase) {
        com.android.internal.app.LocalePickerWithRegion localePickerWithRegion = new com.android.internal.app.LocalePickerWithRegion();
        localePickerWithRegion.setOnActionExpandListener(onActionExpandListener);
        if (localePickerWithRegion.setListener(localeSelectedListener, localeInfo, z, localeCollectorBase)) {
            return localePickerWithRegion;
        }
        return null;
    }

    public static com.android.internal.app.LocalePickerWithRegion createLanguagePicker(android.content.Context context, com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener localeSelectedListener, boolean z) {
        return createLanguagePicker(context, localeSelectedListener, z, null, null, null);
    }

    public static com.android.internal.app.LocalePickerWithRegion createLanguagePicker(android.content.Context context, com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener localeSelectedListener, boolean z, android.os.LocaleList localeList) {
        return createLanguagePicker(context, localeSelectedListener, z, localeList, null, null);
    }

    public static com.android.internal.app.LocalePickerWithRegion createLanguagePicker(android.content.Context context, com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener localeSelectedListener, boolean z, android.os.LocaleList localeList, java.lang.String str, android.view.MenuItem.OnActionExpandListener onActionExpandListener) {
        com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase appLocaleCollector;
        if (android.text.TextUtils.isEmpty(str)) {
            appLocaleCollector = new com.android.internal.app.SystemLocaleCollector(context, localeList);
        } else {
            appLocaleCollector = new com.android.internal.app.AppLocaleCollector(context, str);
        }
        com.android.internal.app.LocalePickerWithRegion localePickerWithRegion = new com.android.internal.app.LocalePickerWithRegion();
        localePickerWithRegion.setOnActionExpandListener(onActionExpandListener);
        localePickerWithRegion.setListener(localeSelectedListener, null, z, appLocaleCollector);
        return localePickerWithRegion;
    }

    private void setIsNumberingSystem(boolean z) {
        this.mIsNumberingSystem = z;
    }

    private boolean setListener(com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener localeSelectedListener, com.android.internal.app.LocaleStore.LocaleInfo localeInfo, boolean z, com.android.internal.app.LocalePickerWithRegion.LocaleCollectorBase localeCollectorBase) {
        boolean z2;
        this.mParentLocale = localeInfo;
        this.mListener = localeSelectedListener;
        this.mTranslatedOnly = z;
        this.mLocalePickerCollector = localeCollectorBase;
        setRetainInstance(true);
        if (localeInfo != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mLocaleList = localeCollectorBase.getSupportedLocaleList(localeInfo, z, z2);
        if (localeInfo == null || localeSelectedListener == null || this.mLocaleList.size() != 1) {
            return true;
        }
        localeSelectedListener.onLocaleSelected(this.mLocaleList.iterator().next());
        return false;
    }

    private void returnToParentFrame() {
        getFragmentManager().popBackStack(PARENT_FRAGMENT_NAME, 1);
    }

    @Override // android.app.Fragment
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        if (this.mLocaleList == null) {
            returnToParentFrame();
            return;
        }
        this.mTitle = getActivity().getTitle();
        boolean z = this.mParentLocale != null;
        java.util.Locale locale = z ? this.mParentLocale.getLocale() : java.util.Locale.getDefault();
        this.mAdapter = new com.android.internal.app.SuggestedLocaleAdapter(this.mLocaleList, z, this.mLocalePickerCollector != null && this.mLocalePickerCollector.hasSpecificPackageName());
        this.mAdapter.setNumberingSystemMode(this.mIsNumberingSystem);
        this.mAdapter.sort(new com.android.internal.app.LocaleHelper.LocaleInfoComparator(locale, z));
        setListAdapter(this.mAdapter);
    }

    @Override // android.app.ListFragment, android.app.Fragment
    public void onViewCreated(android.view.View view, android.os.Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().setNestedScrollingEnabled(true);
        getListView().setDivider(null);
    }

    @Override // android.app.Fragment
    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mParentLocale != null) {
            getActivity().setTitle(this.mParentLocale.getFullNameNative());
        } else {
            getActivity().setTitle(this.mTitle);
        }
        getListView().requestFocus();
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mSearchView != null) {
            this.mPreviousSearchHadFocus = this.mSearchView.hasFocus();
            this.mPreviousSearch = this.mSearchView.getQuery();
        } else {
            this.mPreviousSearchHadFocus = false;
            this.mPreviousSearch = null;
        }
        android.widget.ListView listView = getListView();
        android.view.View childAt = listView.getChildAt(0);
        this.mFirstVisiblePosition = listView.getFirstVisiblePosition();
        this.mTopDistance = childAt != null ? childAt.getTop() - listView.getPaddingTop() : 0;
    }

    @Override // android.app.ListFragment
    public void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
        com.android.internal.app.LocalePickerWithRegion createCountryPicker;
        com.android.internal.app.LocaleStore.LocaleInfo localeInfo = (com.android.internal.app.LocaleStore.LocaleInfo) listView.getAdapter().getItem(i);
        boolean isSystemLocale = localeInfo.isSystemLocale();
        boolean z = localeInfo.getParent() != null;
        boolean hasNumberingSystems = localeInfo.hasNumberingSystems();
        if (isSystemLocale || localeInfo.isSuggested() || ((z && !hasNumberingSystems) || this.mIsNumberingSystem)) {
            if (this.mListener != null) {
                this.mListener.onLocaleSelected(localeInfo);
            }
            returnToParentFrame();
            return;
        }
        if (hasNumberingSystems) {
            createCountryPicker = createNumberingSystemPicker(this.mListener, localeInfo, this.mTranslatedOnly, this.mOnActionExpandListener, this.mLocalePickerCollector);
        } else {
            createCountryPicker = createCountryPicker(this.mListener, localeInfo, this.mTranslatedOnly, this.mOnActionExpandListener, this.mLocalePickerCollector);
        }
        if (createCountryPicker != null) {
            getFragmentManager().beginTransaction().setTransition(4097).replace(getId(), createCountryPicker).addToBackStack(null).commit();
        } else {
            returnToParentFrame();
        }
    }

    @Override // android.app.Fragment
    public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater menuInflater) {
        if (this.mParentLocale == null) {
            menuInflater.inflate(com.android.internal.R.menu.language_selection_list, menu);
            android.view.MenuItem findItem = menu.findItem(com.android.internal.R.id.locale_search_menu);
            if (this.mOnActionExpandListener != null) {
                findItem.setOnActionExpandListener(this.mOnActionExpandListener);
            }
            this.mSearchView = (android.widget.SearchView) findItem.getActionView();
            this.mSearchView.setQueryHint(getText(com.android.internal.R.string.search_language_hint));
            this.mSearchView.setOnQueryTextListener(this);
            if (!android.text.TextUtils.isEmpty(this.mPreviousSearch)) {
                findItem.expandActionView();
                this.mSearchView.setIconified(false);
                this.mSearchView.setActivated(true);
                if (this.mPreviousSearchHadFocus) {
                    this.mSearchView.requestFocus();
                }
                this.mSearchView.setQuery(this.mPreviousSearch, true);
            } else {
                this.mSearchView.setQuery(null, false);
            }
            getListView().setSelectionFromTop(this.mFirstVisiblePosition, this.mTopDistance);
        }
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(java.lang.String str) {
        return false;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(java.lang.String str) {
        if (this.mAdapter != null) {
            this.mAdapter.getFilter().filter(str);
            return false;
        }
        return false;
    }

    public void setOnActionExpandListener(android.view.MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
    }
}
