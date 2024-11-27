package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class PreferenceGroupAdapter extends android.widget.BaseAdapter implements android.preference.Preference.OnPreferenceChangeInternalListener {
    private static final java.lang.String TAG = "PreferenceGroupAdapter";
    private static android.view.ViewGroup.LayoutParams sWrapperLayoutParams = new android.view.ViewGroup.LayoutParams(-1, -2);
    private android.graphics.drawable.Drawable mHighlightedDrawable;
    private android.preference.PreferenceGroup mPreferenceGroup;
    private java.util.ArrayList<android.preference.PreferenceGroupAdapter.PreferenceLayout> mPreferenceLayouts;
    private java.util.List<android.preference.Preference> mPreferenceList;
    private android.preference.PreferenceGroupAdapter.PreferenceLayout mTempPreferenceLayout = new android.preference.PreferenceGroupAdapter.PreferenceLayout();
    private boolean mHasReturnedViewTypeCount = false;
    private volatile boolean mIsSyncing = false;
    private android.os.Handler mHandler = new android.os.Handler();
    private java.lang.Runnable mSyncRunnable = new java.lang.Runnable() { // from class: android.preference.PreferenceGroupAdapter.1
        @Override // java.lang.Runnable
        public void run() {
            android.preference.PreferenceGroupAdapter.this.syncMyPreferences();
        }
    };
    private int mHighlightedPosition = -1;

    private static class PreferenceLayout implements java.lang.Comparable<android.preference.PreferenceGroupAdapter.PreferenceLayout> {
        private java.lang.String name;
        private int resId;
        private int widgetResId;

        private PreferenceLayout() {
        }

        @Override // java.lang.Comparable
        public int compareTo(android.preference.PreferenceGroupAdapter.PreferenceLayout preferenceLayout) {
            int compareTo = this.name.compareTo(preferenceLayout.name);
            if (compareTo == 0) {
                if (this.resId == preferenceLayout.resId) {
                    if (this.widgetResId == preferenceLayout.widgetResId) {
                        return 0;
                    }
                    return this.widgetResId - preferenceLayout.widgetResId;
                }
                return this.resId - preferenceLayout.resId;
            }
            return compareTo;
        }
    }

    public PreferenceGroupAdapter(android.preference.PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        this.mPreferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferenceList = new java.util.ArrayList();
        this.mPreferenceLayouts = new java.util.ArrayList<>();
        syncMyPreferences();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncMyPreferences() {
        synchronized (this) {
            if (this.mIsSyncing) {
                return;
            }
            this.mIsSyncing = true;
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mPreferenceList.size());
            flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
            this.mPreferenceList = arrayList;
            notifyDataSetChanged();
            synchronized (this) {
                this.mIsSyncing = false;
                notifyAll();
            }
        }
    }

    private void flattenPreferenceGroup(java.util.List<android.preference.Preference> list, android.preference.PreferenceGroup preferenceGroup) {
        preferenceGroup.sortPreferences();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            android.preference.Preference preference = preferenceGroup.getPreference(i);
            list.add(preference);
            if (!this.mHasReturnedViewTypeCount && preference.isRecycleEnabled()) {
                addPreferenceClassName(preference);
            }
            if (preference instanceof android.preference.PreferenceGroup) {
                android.preference.PreferenceGroup preferenceGroup2 = (android.preference.PreferenceGroup) preference;
                if (preferenceGroup2.isOnSameScreenAsChildren()) {
                    flattenPreferenceGroup(list, preferenceGroup2);
                }
            }
            preference.setOnPreferenceChangeInternalListener(this);
        }
    }

    private android.preference.PreferenceGroupAdapter.PreferenceLayout createPreferenceLayout(android.preference.Preference preference, android.preference.PreferenceGroupAdapter.PreferenceLayout preferenceLayout) {
        if (preferenceLayout == null) {
            preferenceLayout = new android.preference.PreferenceGroupAdapter.PreferenceLayout();
        }
        preferenceLayout.name = preference.getClass().getName();
        preferenceLayout.resId = preference.getLayoutResource();
        preferenceLayout.widgetResId = preference.getWidgetLayoutResource();
        return preferenceLayout;
    }

    private void addPreferenceClassName(android.preference.Preference preference) {
        android.preference.PreferenceGroupAdapter.PreferenceLayout createPreferenceLayout = createPreferenceLayout(preference, null);
        if (java.util.Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout) < 0) {
            this.mPreferenceLayouts.add((r0 * (-1)) - 1, createPreferenceLayout);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mPreferenceList.size();
    }

    @Override // android.widget.Adapter
    public android.preference.Preference getItem(int i) {
        if (i < 0 || i >= getCount()) {
            return null;
        }
        return this.mPreferenceList.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        if (i < 0 || i >= getCount()) {
            return Long.MIN_VALUE;
        }
        return getItem(i).getId();
    }

    public void setHighlighted(int i) {
        this.mHighlightedPosition = i;
    }

    public void setHighlightedDrawable(android.graphics.drawable.Drawable drawable) {
        this.mHighlightedDrawable = drawable;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        android.preference.Preference item = getItem(i);
        this.mTempPreferenceLayout = createPreferenceLayout(item, this.mTempPreferenceLayout);
        if (java.util.Collections.binarySearch(this.mPreferenceLayouts, this.mTempPreferenceLayout) < 0 || getItemViewType(i) == getHighlightItemViewType()) {
            view = null;
        }
        android.view.View view2 = item.getView(view, viewGroup);
        if (i == this.mHighlightedPosition && this.mHighlightedDrawable != null) {
            android.widget.FrameLayout frameLayout = new android.widget.FrameLayout(viewGroup.getContext());
            frameLayout.setLayoutParams(sWrapperLayoutParams);
            frameLayout.setBackgroundDrawable(this.mHighlightedDrawable);
            frameLayout.addView(view2);
            return frameLayout;
        }
        return view2;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        if (i < 0 || i >= getCount()) {
            return true;
        }
        return getItem(i).isSelectable();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceChange(android.preference.Preference preference) {
        notifyDataSetChanged();
    }

    @Override // android.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceHierarchyChange(android.preference.Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    private int getHighlightItemViewType() {
        return getViewTypeCount() - 1;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        if (i == this.mHighlightedPosition) {
            return getHighlightItemViewType();
        }
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        android.preference.Preference item = getItem(i);
        if (!item.isRecycleEnabled()) {
            return -1;
        }
        this.mTempPreferenceLayout = createPreferenceLayout(item, this.mTempPreferenceLayout);
        int binarySearch = java.util.Collections.binarySearch(this.mPreferenceLayouts, this.mTempPreferenceLayout);
        if (binarySearch < 0) {
            return -1;
        }
        return binarySearch;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        return java.lang.Math.max(1, this.mPreferenceLayouts.size()) + 1;
    }
}
