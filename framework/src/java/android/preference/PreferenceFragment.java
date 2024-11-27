package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceFragment extends android.app.Fragment implements android.preference.PreferenceManager.OnPreferenceTreeClickListener {
    private static final int FIRST_REQUEST_CODE = 100;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final java.lang.String PREFERENCES_TAG = "android:preferences";
    private boolean mHavePrefs;
    private boolean mInitDone;
    private android.widget.ListView mList;
    private android.preference.PreferenceManager mPreferenceManager;
    private int mLayoutResId = com.android.internal.R.layout.preference_list_fragment;
    private android.os.Handler mHandler = new android.os.Handler() { // from class: android.preference.PreferenceFragment.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.preference.PreferenceFragment.this.bindPreferences();
                    break;
            }
        }
    };
    private final java.lang.Runnable mRequestFocus = new java.lang.Runnable() { // from class: android.preference.PreferenceFragment.2
        @Override // java.lang.Runnable
        public void run() {
            android.preference.PreferenceFragment.this.mList.focusableViewAvailable(android.preference.PreferenceFragment.this.mList);
        }
    };
    private android.view.View.OnKeyListener mListOnKeyListener = new android.view.View.OnKeyListener() { // from class: android.preference.PreferenceFragment.3
        @Override // android.view.View.OnKeyListener
        public boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
            java.lang.Object selectedItem = android.preference.PreferenceFragment.this.mList.getSelectedItem();
            if (selectedItem instanceof android.preference.Preference) {
                return ((android.preference.Preference) selectedItem).onKey(android.preference.PreferenceFragment.this.mList.getSelectedView(), i, keyEvent);
            }
            return false;
        }
    };

    @java.lang.Deprecated
    public interface OnPreferenceStartFragmentCallback {
        boolean onPreferenceStartFragment(android.preference.PreferenceFragment preferenceFragment, android.preference.Preference preference);
    }

    @Override // android.app.Fragment
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mPreferenceManager = new android.preference.PreferenceManager(getActivity(), 100);
        this.mPreferenceManager.setFragment(this);
    }

    @Override // android.app.Fragment
    public android.view.View onCreateView(android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup, android.os.Bundle bundle) {
        android.content.res.TypedArray obtainStyledAttributes = getActivity().obtainStyledAttributes(null, com.android.internal.R.styleable.PreferenceFragment, 16844038, 0);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(0, this.mLayoutResId);
        obtainStyledAttributes.recycle();
        return layoutInflater.inflate(this.mLayoutResId, viewGroup, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(android.view.View view, android.os.Bundle bundle) {
        super.onViewCreated(view, bundle);
        android.content.res.TypedArray obtainStyledAttributes = getActivity().obtainStyledAttributes(null, com.android.internal.R.styleable.PreferenceFragment, 16844038, 0);
        android.widget.ListView listView = (android.widget.ListView) view.findViewById(16908298);
        if (listView != null && obtainStyledAttributes.hasValueOrEmpty(1)) {
            listView.setDivider(obtainStyledAttributes.getDrawable(1));
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.app.Fragment
    public void onActivityCreated(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        android.preference.PreferenceScreen preferenceScreen;
        super.onActivityCreated(bundle);
        if (this.mHavePrefs) {
            bindPreferences();
        }
        this.mInitDone = true;
        if (bundle != null && (bundle2 = bundle.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(bundle2);
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.mPreferenceManager.dispatchActivityStop();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(null);
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        if (this.mList != null) {
            this.mList.setOnKeyListener(null);
        }
        this.mList = null;
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mHandler.removeMessages(1);
        super.onDestroyView();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mPreferenceManager.dispatchActivityDestroy();
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        android.preference.PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            android.os.Bundle bundle2 = new android.os.Bundle();
            preferenceScreen.saveHierarchyState(bundle2);
            bundle.putBundle(PREFERENCES_TAG, bundle2);
        }
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, android.content.Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mPreferenceManager.dispatchActivityResult(i, i2, intent);
    }

    public android.preference.PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public void setPreferenceScreen(android.preference.PreferenceScreen preferenceScreen) {
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            onUnbindPreferences();
            this.mHavePrefs = true;
            if (this.mInitDone) {
                postBindPreferences();
            }
        }
    }

    public android.preference.PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceManager.getPreferenceScreen();
    }

    public void addPreferencesFromIntent(android.content.Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    public void addPreferencesFromResource(int i) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(getActivity(), i, getPreferenceScreen()));
    }

    @Override // android.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(android.preference.PreferenceScreen preferenceScreen, android.preference.Preference preference) {
        if (preference.getFragment() != null && (getActivity() instanceof android.preference.PreferenceFragment.OnPreferenceStartFragmentCallback)) {
            return ((android.preference.PreferenceFragment.OnPreferenceStartFragmentCallback) getActivity()).onPreferenceStartFragment(this, preference);
        }
        return false;
    }

    public android.preference.Preference findPreference(java.lang.CharSequence charSequence) {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(charSequence);
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            throw new java.lang.RuntimeException("This should be called after super.onCreate.");
        }
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPreferences() {
        android.preference.PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            android.view.View view = getView();
            if (view != null) {
                android.view.View findViewById = view.findViewById(16908310);
                if (findViewById instanceof android.widget.TextView) {
                    java.lang.CharSequence title = preferenceScreen.getTitle();
                    if (android.text.TextUtils.isEmpty(title)) {
                        findViewById.setVisibility(8);
                    } else {
                        ((android.widget.TextView) findViewById).lambda$setTextAsync$0(title);
                        findViewById.setVisibility(0);
                    }
                }
            }
            preferenceScreen.bind(getListView());
        }
        onBindPreferences();
    }

    protected void onBindPreferences() {
    }

    protected void onUnbindPreferences() {
    }

    public android.widget.ListView getListView() {
        ensureList();
        return this.mList;
    }

    public boolean hasListView() {
        if (this.mList != null) {
            return true;
        }
        android.view.View view = getView();
        if (view == null) {
            return false;
        }
        android.view.View findViewById = view.findViewById(16908298);
        if (!(findViewById instanceof android.widget.ListView)) {
            return false;
        }
        this.mList = (android.widget.ListView) findViewById;
        return this.mList != null;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        android.view.View view = getView();
        if (view == null) {
            throw new java.lang.IllegalStateException("Content view not yet created");
        }
        android.view.View findViewById = view.findViewById(16908298);
        if (!(findViewById instanceof android.widget.ListView)) {
            throw new java.lang.RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
        }
        this.mList = (android.widget.ListView) findViewById;
        if (this.mList == null) {
            throw new java.lang.RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
        }
        this.mList.setOnKeyListener(this.mListOnKeyListener);
        this.mHandler.post(this.mRequestFocus);
    }
}
