package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceGroup extends android.preference.Preference implements android.preference.GenericInflater.Parent<android.preference.Preference> {
    private boolean mAttachedToActivity;
    private int mCurrentPreferenceOrder;
    private boolean mOrderingAsAdded;
    private java.util.List<android.preference.Preference> mPreferenceList;

    public PreferenceGroup(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOrderingAsAdded = true;
        this.mCurrentPreferenceOrder = 0;
        this.mAttachedToActivity = false;
        this.mPreferenceList = new java.util.ArrayList();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PreferenceGroup, i, i2);
        this.mOrderingAsAdded = obtainStyledAttributes.getBoolean(0, this.mOrderingAsAdded);
        obtainStyledAttributes.recycle();
    }

    public PreferenceGroup(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceGroup(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setOrderingAsAdded(boolean z) {
        this.mOrderingAsAdded = z;
    }

    public boolean isOrderingAsAdded() {
        return this.mOrderingAsAdded;
    }

    @Override // android.preference.GenericInflater.Parent
    public void addItemFromInflater(android.preference.Preference preference) {
        addPreference(preference);
    }

    public int getPreferenceCount() {
        return this.mPreferenceList.size();
    }

    public android.preference.Preference getPreference(int i) {
        return this.mPreferenceList.get(i);
    }

    public boolean addPreference(android.preference.Preference preference) {
        if (this.mPreferenceList.contains(preference)) {
            return true;
        }
        if (preference.getOrder() == Integer.MAX_VALUE) {
            if (this.mOrderingAsAdded) {
                int i = this.mCurrentPreferenceOrder;
                this.mCurrentPreferenceOrder = i + 1;
                preference.setOrder(i);
            }
            if (preference instanceof android.preference.PreferenceGroup) {
                ((android.preference.PreferenceGroup) preference).setOrderingAsAdded(this.mOrderingAsAdded);
            }
        }
        if (!onPrepareAddPreference(preference)) {
            return false;
        }
        synchronized (this) {
            int binarySearch = java.util.Collections.binarySearch(this.mPreferenceList, preference);
            if (binarySearch < 0) {
                binarySearch = (binarySearch * (-1)) - 1;
            }
            this.mPreferenceList.add(binarySearch, preference);
        }
        preference.onAttachedToHierarchy(getPreferenceManager());
        preference.assignParent(this);
        if (this.mAttachedToActivity) {
            preference.onAttachedToActivity();
        }
        notifyHierarchyChanged();
        return true;
    }

    public boolean removePreference(android.preference.Preference preference) {
        boolean removePreferenceInt = removePreferenceInt(preference);
        notifyHierarchyChanged();
        return removePreferenceInt;
    }

    private boolean removePreferenceInt(android.preference.Preference preference) {
        boolean remove;
        synchronized (this) {
            preference.onPrepareForRemoval();
            if (preference.getParent() == this) {
                preference.assignParent(null);
            }
            remove = this.mPreferenceList.remove(preference);
        }
        return remove;
    }

    public void removeAll() {
        synchronized (this) {
            java.util.List<android.preference.Preference> list = this.mPreferenceList;
            for (int size = list.size() - 1; size >= 0; size--) {
                removePreferenceInt(list.get(0));
            }
        }
        notifyHierarchyChanged();
    }

    protected boolean onPrepareAddPreference(android.preference.Preference preference) {
        preference.onParentChanged(this, shouldDisableDependents());
        return true;
    }

    public android.preference.Preference findPreference(java.lang.CharSequence charSequence) {
        android.preference.Preference findPreference;
        if (android.text.TextUtils.equals(getKey(), charSequence)) {
            return this;
        }
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            android.preference.Preference preference = getPreference(i);
            java.lang.String key = preference.getKey();
            if (key != null && key.equals(charSequence)) {
                return preference;
            }
            if ((preference instanceof android.preference.PreferenceGroup) && (findPreference = ((android.preference.PreferenceGroup) preference).findPreference(charSequence)) != null) {
                return findPreference;
            }
        }
        return null;
    }

    protected boolean isOnSameScreenAsChildren() {
        return true;
    }

    @Override // android.preference.Preference
    protected void onAttachedToActivity() {
        super.onAttachedToActivity();
        this.mAttachedToActivity = true;
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onAttachedToActivity();
        }
    }

    @Override // android.preference.Preference
    protected void onPrepareForRemoval() {
        super.onPrepareForRemoval();
        this.mAttachedToActivity = false;
    }

    @Override // android.preference.Preference
    public void notifyDependencyChange(boolean z) {
        super.notifyDependencyChange(z);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onParentChanged(this, z);
        }
    }

    void sortPreferences() {
        synchronized (this) {
            java.util.Collections.sort(this.mPreferenceList);
        }
    }

    @Override // android.preference.Preference
    protected void dispatchSaveInstanceState(android.os.Bundle bundle) {
        super.dispatchSaveInstanceState(bundle);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).dispatchSaveInstanceState(bundle);
        }
    }

    @Override // android.preference.Preference
    protected void dispatchRestoreInstanceState(android.os.Bundle bundle) {
        super.dispatchRestoreInstanceState(bundle);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).dispatchRestoreInstanceState(bundle);
        }
    }
}
