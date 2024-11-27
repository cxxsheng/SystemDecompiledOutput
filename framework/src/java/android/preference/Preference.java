package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Preference implements java.lang.Comparable<android.preference.Preference> {
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    private boolean mBaseMethodCalled;
    private android.content.Context mContext;
    private java.lang.Object mDefaultValue;
    private java.lang.String mDependencyKey;
    private boolean mDependencyMet;
    private java.util.List<android.preference.Preference> mDependents;
    private boolean mEnabled;
    private android.os.Bundle mExtras;
    private java.lang.String mFragment;
    private boolean mHasSingleLineTitleAttr;
    private android.graphics.drawable.Drawable mIcon;
    private int mIconResId;
    private boolean mIconSpaceReserved;
    private long mId;
    private android.content.Intent mIntent;
    private java.lang.String mKey;
    private int mLayoutResId;
    private android.preference.Preference.OnPreferenceChangeInternalListener mListener;
    private android.preference.Preference.OnPreferenceChangeListener mOnChangeListener;
    private android.preference.Preference.OnPreferenceClickListener mOnClickListener;
    private int mOrder;
    private boolean mParentDependencyMet;
    private android.preference.PreferenceGroup mParentGroup;
    private boolean mPersistent;
    private android.preference.PreferenceDataStore mPreferenceDataStore;
    private android.preference.PreferenceManager mPreferenceManager;
    private boolean mRecycleEnabled;
    private boolean mRequiresKey;
    private boolean mSelectable;
    private boolean mShouldDisableView;
    private boolean mSingleLineTitle;
    private java.lang.CharSequence mSummary;
    private java.lang.CharSequence mTitle;
    private int mTitleRes;
    private int mWidgetLayoutResId;

    interface OnPreferenceChangeInternalListener {
        void onPreferenceChange(android.preference.Preference preference);

        void onPreferenceHierarchyChange(android.preference.Preference preference);
    }

    @java.lang.Deprecated
    public interface OnPreferenceChangeListener {
        boolean onPreferenceChange(android.preference.Preference preference, java.lang.Object obj);
    }

    @java.lang.Deprecated
    public interface OnPreferenceClickListener {
        boolean onPreferenceClick(android.preference.Preference preference);
    }

    public Preference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this.mOrder = Integer.MAX_VALUE;
        this.mEnabled = true;
        this.mSelectable = true;
        this.mPersistent = true;
        this.mDependencyMet = true;
        this.mParentDependencyMet = true;
        this.mRecycleEnabled = true;
        this.mSingleLineTitle = true;
        this.mShouldDisableView = true;
        this.mLayoutResId = com.android.internal.R.layout.preference;
        this.mContext = context;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Preference, i, i2);
        for (int indexCount = obtainStyledAttributes.getIndexCount() - 1; indexCount >= 0; indexCount--) {
            int index = obtainStyledAttributes.getIndex(indexCount);
            switch (index) {
                case 0:
                    this.mIconResId = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 1:
                    this.mPersistent = obtainStyledAttributes.getBoolean(index, this.mPersistent);
                    break;
                case 2:
                    this.mEnabled = obtainStyledAttributes.getBoolean(index, true);
                    break;
                case 3:
                    this.mLayoutResId = obtainStyledAttributes.getResourceId(index, this.mLayoutResId);
                    break;
                case 4:
                    this.mTitleRes = obtainStyledAttributes.getResourceId(index, 0);
                    this.mTitle = obtainStyledAttributes.getText(index);
                    break;
                case 5:
                    this.mSelectable = obtainStyledAttributes.getBoolean(index, true);
                    break;
                case 6:
                    this.mKey = obtainStyledAttributes.getString(index);
                    break;
                case 7:
                    this.mSummary = obtainStyledAttributes.getText(index);
                    break;
                case 8:
                    this.mOrder = obtainStyledAttributes.getInt(index, this.mOrder);
                    break;
                case 9:
                    this.mWidgetLayoutResId = obtainStyledAttributes.getResourceId(index, this.mWidgetLayoutResId);
                    break;
                case 10:
                    this.mDependencyKey = obtainStyledAttributes.getString(index);
                    break;
                case 11:
                    this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, index);
                    break;
                case 12:
                    this.mShouldDisableView = obtainStyledAttributes.getBoolean(index, this.mShouldDisableView);
                    break;
                case 13:
                    this.mFragment = obtainStyledAttributes.getString(index);
                    break;
                case 14:
                    this.mRecycleEnabled = obtainStyledAttributes.getBoolean(index, this.mRecycleEnabled);
                    break;
                case 15:
                    this.mSingleLineTitle = obtainStyledAttributes.getBoolean(index, this.mSingleLineTitle);
                    this.mHasSingleLineTitleAttr = true;
                    break;
                case 16:
                    this.mIconSpaceReserved = obtainStyledAttributes.getBoolean(index, this.mIconSpaceReserved);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
    }

    public Preference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Preference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842894);
    }

    public Preference(android.content.Context context) {
        this(context, null);
    }

    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return null;
    }

    public void setIntent(android.content.Intent intent) {
        this.mIntent = intent;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public void setFragment(java.lang.String str) {
        this.mFragment = str;
    }

    public java.lang.String getFragment() {
        return this.mFragment;
    }

    public void setPreferenceDataStore(android.preference.PreferenceDataStore preferenceDataStore) {
        this.mPreferenceDataStore = preferenceDataStore;
    }

    public android.preference.PreferenceDataStore getPreferenceDataStore() {
        if (this.mPreferenceDataStore != null) {
            return this.mPreferenceDataStore;
        }
        if (this.mPreferenceManager != null) {
            return this.mPreferenceManager.getPreferenceDataStore();
        }
        return null;
    }

    public android.os.Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        return this.mExtras;
    }

    public android.os.Bundle peekExtras() {
        return this.mExtras;
    }

    public void setLayoutResource(int i) {
        if (i != this.mLayoutResId) {
            this.mRecycleEnabled = false;
        }
        this.mLayoutResId = i;
    }

    public int getLayoutResource() {
        return this.mLayoutResId;
    }

    public void setWidgetLayoutResource(int i) {
        if (i != this.mWidgetLayoutResId) {
            this.mRecycleEnabled = false;
        }
        this.mWidgetLayoutResId = i;
    }

    public int getWidgetLayoutResource() {
        return this.mWidgetLayoutResId;
    }

    public android.view.View getView(android.view.View view, android.view.ViewGroup viewGroup) {
        if (view == null) {
            view = onCreateView(viewGroup);
        }
        onBindView(view);
        return view;
    }

    protected android.view.View onCreateView(android.view.ViewGroup viewGroup) {
        android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        android.view.View inflate = layoutInflater.inflate(this.mLayoutResId, viewGroup, false);
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) inflate.findViewById(16908312);
        if (viewGroup2 != null) {
            if (this.mWidgetLayoutResId != 0) {
                layoutInflater.inflate(this.mWidgetLayoutResId, viewGroup2);
            } else {
                viewGroup2.setVisibility(8);
            }
        }
        return inflate;
    }

    protected void onBindView(android.view.View view) {
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(16908310);
        if (textView != null) {
            java.lang.CharSequence title = getTitle();
            if (!android.text.TextUtils.isEmpty(title)) {
                textView.lambda$setTextAsync$0(title);
                textView.setVisibility(0);
                if (this.mHasSingleLineTitleAttr) {
                    textView.setSingleLine(this.mSingleLineTitle);
                }
            } else {
                textView.setVisibility(8);
            }
        }
        android.widget.TextView textView2 = (android.widget.TextView) view.findViewById(16908304);
        if (textView2 != null) {
            java.lang.CharSequence summary = getSummary();
            if (!android.text.TextUtils.isEmpty(summary)) {
                textView2.lambda$setTextAsync$0(summary);
                textView2.setVisibility(0);
            } else {
                textView2.setVisibility(8);
            }
        }
        android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(16908294);
        if (imageView != null) {
            if (this.mIconResId != 0 || this.mIcon != null) {
                if (this.mIcon == null) {
                    this.mIcon = getContext().getDrawable(this.mIconResId);
                }
                if (this.mIcon != null) {
                    imageView.setImageDrawable(this.mIcon);
                }
            }
            if (this.mIcon != null) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(this.mIconSpaceReserved ? 4 : 8);
            }
        }
        android.view.View findViewById = view.findViewById(16908350);
        if (findViewById != null) {
            if (this.mIcon != null) {
                findViewById.setVisibility(0);
            } else {
                findViewById.setVisibility(this.mIconSpaceReserved ? 4 : 8);
            }
        }
        if (this.mShouldDisableView) {
            setEnabledStateOnViews(view, isEnabled());
        }
    }

    private void setEnabledStateOnViews(android.view.View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                setEnabledStateOnViews(viewGroup.getChildAt(childCount), z);
            }
        }
    }

    public void setOrder(int i) {
        if (i != this.mOrder) {
            this.mOrder = i;
            notifyHierarchyChanged();
        }
    }

    public int getOrder() {
        return this.mOrder;
    }

    public void setTitle(java.lang.CharSequence charSequence) {
        if ((charSequence == null && this.mTitle != null) || (charSequence != null && !charSequence.equals(this.mTitle))) {
            this.mTitleRes = 0;
            this.mTitle = charSequence;
            notifyChanged();
        }
    }

    public void setTitle(int i) {
        setTitle(this.mContext.getString(i));
        this.mTitleRes = i;
    }

    public int getTitleRes() {
        return this.mTitleRes;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public void setIcon(android.graphics.drawable.Drawable drawable) {
        if ((drawable == null && this.mIcon != null) || (drawable != null && this.mIcon != drawable)) {
            this.mIcon = drawable;
            notifyChanged();
        }
    }

    public void setIcon(int i) {
        if (this.mIconResId != i) {
            this.mIconResId = i;
            setIcon(this.mContext.getDrawable(i));
        }
    }

    public android.graphics.drawable.Drawable getIcon() {
        if (this.mIcon == null && this.mIconResId != 0) {
            this.mIcon = getContext().getDrawable(this.mIconResId);
        }
        return this.mIcon;
    }

    public java.lang.CharSequence getSummary() {
        return this.mSummary;
    }

    public void setSummary(java.lang.CharSequence charSequence) {
        if ((charSequence == null && this.mSummary != null) || (charSequence != null && !charSequence.equals(this.mSummary))) {
            this.mSummary = charSequence;
            notifyChanged();
        }
    }

    public void setSummary(int i) {
        setSummary(this.mContext.getString(i));
    }

    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public boolean isEnabled() {
        return this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
    }

    public void setSelectable(boolean z) {
        if (this.mSelectable != z) {
            this.mSelectable = z;
            notifyChanged();
        }
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    public void setShouldDisableView(boolean z) {
        this.mShouldDisableView = z;
        notifyChanged();
    }

    public boolean getShouldDisableView() {
        return this.mShouldDisableView;
    }

    public void setRecycleEnabled(boolean z) {
        this.mRecycleEnabled = z;
        notifyChanged();
    }

    public boolean isRecycleEnabled() {
        return this.mRecycleEnabled;
    }

    public void setSingleLineTitle(boolean z) {
        this.mHasSingleLineTitleAttr = true;
        this.mSingleLineTitle = z;
        notifyChanged();
    }

    public boolean isSingleLineTitle() {
        return this.mSingleLineTitle;
    }

    public void setIconSpaceReserved(boolean z) {
        this.mIconSpaceReserved = z;
        notifyChanged();
    }

    public boolean isIconSpaceReserved() {
        return this.mIconSpaceReserved;
    }

    long getId() {
        return this.mId;
    }

    protected void onClick() {
    }

    public void setKey(java.lang.String str) {
        this.mKey = str;
        if (this.mRequiresKey && !hasKey()) {
            requireKey();
        }
    }

    public java.lang.String getKey() {
        return this.mKey;
    }

    void requireKey() {
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Preference does not have a key assigned.");
        }
        this.mRequiresKey = true;
    }

    public boolean hasKey() {
        return !android.text.TextUtils.isEmpty(this.mKey);
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    protected boolean shouldPersist() {
        return this.mPreferenceManager != null && isPersistent() && hasKey();
    }

    public void setPersistent(boolean z) {
        this.mPersistent = z;
    }

    protected boolean callChangeListener(java.lang.Object obj) {
        return this.mOnChangeListener == null || this.mOnChangeListener.onPreferenceChange(this, obj);
    }

    public void setOnPreferenceChangeListener(android.preference.Preference.OnPreferenceChangeListener onPreferenceChangeListener) {
        this.mOnChangeListener = onPreferenceChangeListener;
    }

    public android.preference.Preference.OnPreferenceChangeListener getOnPreferenceChangeListener() {
        return this.mOnChangeListener;
    }

    public void setOnPreferenceClickListener(android.preference.Preference.OnPreferenceClickListener onPreferenceClickListener) {
        this.mOnClickListener = onPreferenceClickListener;
    }

    public android.preference.Preference.OnPreferenceClickListener getOnPreferenceClickListener() {
        return this.mOnClickListener;
    }

    public void performClick(android.preference.PreferenceScreen preferenceScreen) {
        if (!isEnabled()) {
            return;
        }
        onClick();
        if (this.mOnClickListener != null && this.mOnClickListener.onPreferenceClick(this)) {
            return;
        }
        android.preference.PreferenceManager preferenceManager = getPreferenceManager();
        if (preferenceManager != null) {
            android.preference.PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener = preferenceManager.getOnPreferenceTreeClickListener();
            if (preferenceScreen != null && onPreferenceTreeClickListener != null && onPreferenceTreeClickListener.onPreferenceTreeClick(preferenceScreen, this)) {
                return;
            }
        }
        if (this.mIntent != null) {
            getContext().startActivity(this.mIntent);
        }
    }

    public boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public android.content.SharedPreferences getSharedPreferences() {
        if (this.mPreferenceManager == null || getPreferenceDataStore() != null) {
            return null;
        }
        return this.mPreferenceManager.getSharedPreferences();
    }

    public android.content.SharedPreferences.Editor getEditor() {
        if (this.mPreferenceManager == null || getPreferenceDataStore() != null) {
            return null;
        }
        return this.mPreferenceManager.getEditor();
    }

    public boolean shouldCommit() {
        if (this.mPreferenceManager == null) {
            return false;
        }
        return this.mPreferenceManager.shouldCommit();
    }

    @Override // java.lang.Comparable
    public int compareTo(android.preference.Preference preference) {
        if (this.mOrder != preference.mOrder) {
            return this.mOrder - preference.mOrder;
        }
        if (this.mTitle == preference.mTitle) {
            return 0;
        }
        if (this.mTitle == null) {
            return 1;
        }
        if (preference.mTitle == null) {
            return -1;
        }
        return com.android.internal.util.CharSequences.compareToIgnoreCase(this.mTitle, preference.mTitle);
    }

    final void setOnPreferenceChangeInternalListener(android.preference.Preference.OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
        this.mListener = onPreferenceChangeInternalListener;
    }

    protected void notifyChanged() {
        if (this.mListener != null) {
            this.mListener.onPreferenceChange(this);
        }
    }

    protected void notifyHierarchyChanged() {
        if (this.mListener != null) {
            this.mListener.onPreferenceHierarchyChange(this);
        }
    }

    public android.preference.PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    protected void onAttachedToHierarchy(android.preference.PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        this.mId = preferenceManager.getNextId();
        dispatchSetInitialValue();
    }

    protected void onAttachedToActivity() {
        registerDependency();
    }

    void assignParent(android.preference.PreferenceGroup preferenceGroup) {
        this.mParentGroup = preferenceGroup;
    }

    private void registerDependency() {
        if (android.text.TextUtils.isEmpty(this.mDependencyKey)) {
            return;
        }
        android.preference.Preference findPreferenceInHierarchy = findPreferenceInHierarchy(this.mDependencyKey);
        if (findPreferenceInHierarchy != null) {
            findPreferenceInHierarchy.registerDependent(this);
            return;
        }
        throw new java.lang.IllegalStateException("Dependency \"" + this.mDependencyKey + "\" not found for preference \"" + this.mKey + "\" (title: \"" + ((java.lang.Object) this.mTitle) + "\"");
    }

    private void unregisterDependency() {
        android.preference.Preference findPreferenceInHierarchy;
        if (this.mDependencyKey != null && (findPreferenceInHierarchy = findPreferenceInHierarchy(this.mDependencyKey)) != null) {
            findPreferenceInHierarchy.unregisterDependent(this);
        }
    }

    protected android.preference.Preference findPreferenceInHierarchy(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str) || this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(str);
    }

    private void registerDependent(android.preference.Preference preference) {
        if (this.mDependents == null) {
            this.mDependents = new java.util.ArrayList();
        }
        this.mDependents.add(preference);
        preference.onDependencyChanged(this, shouldDisableDependents());
    }

    private void unregisterDependent(android.preference.Preference preference) {
        if (this.mDependents != null) {
            this.mDependents.remove(preference);
        }
    }

    public void notifyDependencyChange(boolean z) {
        java.util.List<android.preference.Preference> list = this.mDependents;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).onDependencyChanged(this, z);
        }
    }

    public void onDependencyChanged(android.preference.Preference preference, boolean z) {
        if (this.mDependencyMet == z) {
            this.mDependencyMet = !z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void onParentChanged(android.preference.Preference preference, boolean z) {
        if (this.mParentDependencyMet == z) {
            this.mParentDependencyMet = !z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public boolean shouldDisableDependents() {
        return !isEnabled();
    }

    public void setDependency(java.lang.String str) {
        unregisterDependency();
        this.mDependencyKey = str;
        registerDependency();
    }

    public java.lang.String getDependency() {
        return this.mDependencyKey;
    }

    public android.preference.PreferenceGroup getParent() {
        return this.mParentGroup;
    }

    protected void onPrepareForRemoval() {
        unregisterDependency();
    }

    public void setDefaultValue(java.lang.Object obj) {
        this.mDefaultValue = obj;
    }

    private void dispatchSetInitialValue() {
        if (getPreferenceDataStore() != null) {
            onSetInitialValue(true, this.mDefaultValue);
            return;
        }
        if (!shouldPersist() || !getSharedPreferences().contains(this.mKey)) {
            if (this.mDefaultValue != null) {
                onSetInitialValue(false, this.mDefaultValue);
                return;
            }
            return;
        }
        onSetInitialValue(true, null);
    }

    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
    }

    private void tryCommit(android.content.SharedPreferences.Editor editor) {
        if (this.mPreferenceManager.shouldCommit()) {
            try {
                editor.apply();
            } catch (java.lang.AbstractMethodError e) {
                editor.commit();
            }
        }
    }

    protected boolean persistString(java.lang.String str) {
        if (!shouldPersist()) {
            return false;
        }
        if (android.text.TextUtils.equals(str, getPersistedString(null))) {
            return true;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            preferenceDataStore.putString(this.mKey, str);
        } else {
            android.content.SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putString(this.mKey, str);
            tryCommit(editor);
        }
        return true;
    }

    protected java.lang.String getPersistedString(java.lang.String str) {
        if (!shouldPersist()) {
            return str;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getString(this.mKey, str);
        }
        return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, str);
    }

    public boolean persistStringSet(java.util.Set<java.lang.String> set) {
        if (!shouldPersist()) {
            return false;
        }
        if (set.equals(getPersistedStringSet(null))) {
            return true;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            preferenceDataStore.putStringSet(this.mKey, set);
        } else {
            android.content.SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putStringSet(this.mKey, set);
            tryCommit(editor);
        }
        return true;
    }

    public java.util.Set<java.lang.String> getPersistedStringSet(java.util.Set<java.lang.String> set) {
        if (!shouldPersist()) {
            return set;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getStringSet(this.mKey, set);
        }
        return this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, set);
    }

    protected boolean persistInt(int i) {
        if (!shouldPersist()) {
            return false;
        }
        if (i == getPersistedInt(~i)) {
            return true;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            preferenceDataStore.putInt(this.mKey, i);
        } else {
            android.content.SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putInt(this.mKey, i);
            tryCommit(editor);
        }
        return true;
    }

    protected int getPersistedInt(int i) {
        if (!shouldPersist()) {
            return i;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getInt(this.mKey, i);
        }
        return this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, i);
    }

    protected boolean persistFloat(float f) {
        if (!shouldPersist()) {
            return false;
        }
        if (f == getPersistedFloat(Float.NaN)) {
            return true;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            preferenceDataStore.putFloat(this.mKey, f);
        } else {
            android.content.SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putFloat(this.mKey, f);
            tryCommit(editor);
        }
        return true;
    }

    protected float getPersistedFloat(float f) {
        if (!shouldPersist()) {
            return f;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getFloat(this.mKey, f);
        }
        return this.mPreferenceManager.getSharedPreferences().getFloat(this.mKey, f);
    }

    protected boolean persistLong(long j) {
        if (!shouldPersist()) {
            return false;
        }
        if (j == getPersistedLong(~j)) {
            return true;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            preferenceDataStore.putLong(this.mKey, j);
        } else {
            android.content.SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putLong(this.mKey, j);
            tryCommit(editor);
        }
        return true;
    }

    protected long getPersistedLong(long j) {
        if (!shouldPersist()) {
            return j;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getLong(this.mKey, j);
        }
        return this.mPreferenceManager.getSharedPreferences().getLong(this.mKey, j);
    }

    protected boolean persistBoolean(boolean z) {
        if (!shouldPersist()) {
            return false;
        }
        if (z == getPersistedBoolean(!z)) {
            return true;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            preferenceDataStore.putBoolean(this.mKey, z);
        } else {
            android.content.SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putBoolean(this.mKey, z);
            tryCommit(editor);
        }
        return true;
    }

    protected boolean getPersistedBoolean(boolean z) {
        if (!shouldPersist()) {
            return z;
        }
        android.preference.PreferenceDataStore preferenceDataStore = getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getBoolean(this.mKey, z);
        }
        return this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, z);
    }

    public java.lang.String toString() {
        return getFilterableStringBuilder().toString();
    }

    java.lang.StringBuilder getFilterableStringBuilder() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.CharSequence title = getTitle();
        if (!android.text.TextUtils.isEmpty(title)) {
            sb.append(title).append(' ');
        }
        java.lang.CharSequence summary = getSummary();
        if (!android.text.TextUtils.isEmpty(summary)) {
            sb.append(summary).append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb;
    }

    public void saveHierarchyState(android.os.Bundle bundle) {
        dispatchSaveInstanceState(bundle);
    }

    void dispatchSaveInstanceState(android.os.Bundle bundle) {
        if (hasKey()) {
            this.mBaseMethodCalled = false;
            android.os.Parcelable onSaveInstanceState = onSaveInstanceState();
            if (!this.mBaseMethodCalled) {
                throw new java.lang.IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
            if (onSaveInstanceState != null) {
                bundle.putParcelable(this.mKey, onSaveInstanceState);
            }
        }
    }

    protected android.os.Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return android.preference.Preference.BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(android.os.Bundle bundle) {
        dispatchRestoreInstanceState(bundle);
    }

    void dispatchRestoreInstanceState(android.os.Bundle bundle) {
        android.os.Parcelable parcelable;
        if (hasKey() && (parcelable = bundle.getParcelable(this.mKey)) != null) {
            this.mBaseMethodCalled = false;
            onRestoreInstanceState(parcelable);
            if (!this.mBaseMethodCalled) {
                throw new java.lang.IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        this.mBaseMethodCalled = true;
        if (parcelable != android.preference.Preference.BaseSavedState.EMPTY_STATE && parcelable != null) {
            throw new java.lang.IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    @java.lang.Deprecated
    public static class BaseSavedState extends android.view.AbsSavedState {
        public static final android.os.Parcelable.Creator<android.preference.Preference.BaseSavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.Preference.BaseSavedState>() { // from class: android.preference.Preference.BaseSavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.Preference.BaseSavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.Preference.BaseSavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.Preference.BaseSavedState[] newArray(int i) {
                return new android.preference.Preference.BaseSavedState[i];
            }
        };

        public BaseSavedState(android.os.Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
