package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class PreferenceManager {
    public static final java.lang.String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    public static final java.lang.String METADATA_KEY_PREFERENCES = "android.preference";
    private static final int STORAGE_CREDENTIAL_PROTECTED = 2;
    private static final int STORAGE_DEFAULT = 0;
    private static final int STORAGE_DEVICE_PROTECTED = 1;
    private static final java.lang.String TAG = "PreferenceManager";
    private android.app.Activity mActivity;
    private java.util.List<android.preference.PreferenceManager.OnActivityDestroyListener> mActivityDestroyListeners;
    private java.util.List<android.preference.PreferenceManager.OnActivityResultListener> mActivityResultListeners;
    private java.util.List<android.preference.PreferenceManager.OnActivityStopListener> mActivityStopListeners;
    private android.content.Context mContext;
    private android.content.SharedPreferences.Editor mEditor;
    private android.preference.PreferenceFragment mFragment;
    private int mNextRequestCode;
    private boolean mNoCommit;
    private android.preference.PreferenceManager.OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    private android.preference.PreferenceDataStore mPreferenceDataStore;
    private android.preference.PreferenceScreen mPreferenceScreen;
    private java.util.List<android.content.DialogInterface> mPreferencesScreens;
    private android.content.SharedPreferences mSharedPreferences;
    private int mSharedPreferencesMode;
    private java.lang.String mSharedPreferencesName;
    private long mNextId = 0;
    private int mStorage = 0;

    @java.lang.Deprecated
    public interface OnActivityDestroyListener {
        void onActivityDestroy();
    }

    @java.lang.Deprecated
    public interface OnActivityResultListener {
        boolean onActivityResult(int i, int i2, android.content.Intent intent);
    }

    @java.lang.Deprecated
    public interface OnActivityStopListener {
        void onActivityStop();
    }

    @java.lang.Deprecated
    public interface OnPreferenceTreeClickListener {
        boolean onPreferenceTreeClick(android.preference.PreferenceScreen preferenceScreen, android.preference.Preference preference);
    }

    public PreferenceManager(android.app.Activity activity, int i) {
        this.mActivity = activity;
        this.mNextRequestCode = i;
        init(activity);
    }

    PreferenceManager(android.content.Context context) {
        init(context);
    }

    private void init(android.content.Context context) {
        this.mContext = context;
        setSharedPreferencesName(getDefaultSharedPreferencesName(context));
    }

    void setFragment(android.preference.PreferenceFragment preferenceFragment) {
        this.mFragment = preferenceFragment;
    }

    android.preference.PreferenceFragment getFragment() {
        return this.mFragment;
    }

    public void setPreferenceDataStore(android.preference.PreferenceDataStore preferenceDataStore) {
        this.mPreferenceDataStore = preferenceDataStore;
    }

    public android.preference.PreferenceDataStore getPreferenceDataStore() {
        return this.mPreferenceDataStore;
    }

    private java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent) {
        return this.mContext.getPackageManager().queryIntentActivities(intent, 128);
    }

    android.preference.PreferenceScreen inflateFromIntent(android.content.Intent intent, android.preference.PreferenceScreen preferenceScreen) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(intent);
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int size = queryIntentActivities.size() - 1; size >= 0; size--) {
            android.content.pm.ActivityInfo activityInfo = queryIntentActivities.get(size).activityInfo;
            android.os.Bundle bundle = activityInfo.metaData;
            if (bundle != null && bundle.containsKey(METADATA_KEY_PREFERENCES)) {
                java.lang.String str = activityInfo.packageName + ":" + activityInfo.metaData.getInt(METADATA_KEY_PREFERENCES);
                if (!hashSet.contains(str)) {
                    hashSet.add(str);
                    try {
                        android.content.Context createPackageContext = this.mContext.createPackageContext(activityInfo.packageName, 0);
                        android.preference.PreferenceInflater preferenceInflater = new android.preference.PreferenceInflater(createPackageContext, this);
                        android.content.res.XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(createPackageContext.getPackageManager(), METADATA_KEY_PREFERENCES);
                        preferenceScreen = (android.preference.PreferenceScreen) preferenceInflater.inflate((org.xmlpull.v1.XmlPullParser) loadXmlMetaData, (android.content.res.XmlResourceParser) preferenceScreen, true);
                        loadXmlMetaData.close();
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Log.w(TAG, "Could not create context for " + activityInfo.packageName + ": " + android.util.Log.getStackTraceString(e));
                    }
                }
            }
        }
        preferenceScreen.onAttachedToHierarchy(this);
        return preferenceScreen;
    }

    public android.preference.PreferenceScreen inflateFromResource(android.content.Context context, int i, android.preference.PreferenceScreen preferenceScreen) {
        setNoCommit(true);
        android.preference.PreferenceScreen preferenceScreen2 = (android.preference.PreferenceScreen) new android.preference.PreferenceInflater(context, this).inflate(i, (int) preferenceScreen, true);
        preferenceScreen2.onAttachedToHierarchy(this);
        setNoCommit(false);
        return preferenceScreen2;
    }

    public android.preference.PreferenceScreen createPreferenceScreen(android.content.Context context) {
        android.preference.PreferenceScreen preferenceScreen = new android.preference.PreferenceScreen(context, null);
        preferenceScreen.onAttachedToHierarchy(this);
        return preferenceScreen;
    }

    long getNextId() {
        long j;
        synchronized (this) {
            j = this.mNextId;
            this.mNextId = 1 + j;
        }
        return j;
    }

    public java.lang.String getSharedPreferencesName() {
        return this.mSharedPreferencesName;
    }

    public void setSharedPreferencesName(java.lang.String str) {
        this.mSharedPreferencesName = str;
        this.mSharedPreferences = null;
    }

    public int getSharedPreferencesMode() {
        return this.mSharedPreferencesMode;
    }

    public void setSharedPreferencesMode(int i) {
        this.mSharedPreferencesMode = i;
        this.mSharedPreferences = null;
    }

    public void setStorageDefault() {
        this.mStorage = 0;
        this.mSharedPreferences = null;
    }

    public void setStorageDeviceProtected() {
        this.mStorage = 1;
        this.mSharedPreferences = null;
    }

    @android.annotation.SystemApi
    public void setStorageCredentialProtected() {
        this.mStorage = 2;
        this.mSharedPreferences = null;
    }

    public boolean isStorageDefault() {
        return this.mStorage == 0;
    }

    public boolean isStorageDeviceProtected() {
        return this.mStorage == 1;
    }

    @android.annotation.SystemApi
    public boolean isStorageCredentialProtected() {
        return this.mStorage == 2;
    }

    public android.content.SharedPreferences getSharedPreferences() {
        android.content.Context createDeviceProtectedStorageContext;
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (this.mSharedPreferences == null) {
            switch (this.mStorage) {
                case 1:
                    createDeviceProtectedStorageContext = this.mContext.createDeviceProtectedStorageContext();
                    break;
                case 2:
                    createDeviceProtectedStorageContext = this.mContext.createCredentialProtectedStorageContext();
                    break;
                default:
                    createDeviceProtectedStorageContext = this.mContext;
                    break;
            }
            this.mSharedPreferences = createDeviceProtectedStorageContext.getSharedPreferences(this.mSharedPreferencesName, this.mSharedPreferencesMode);
        }
        return this.mSharedPreferences;
    }

    public static android.content.SharedPreferences getDefaultSharedPreferences(android.content.Context context) {
        return context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode());
    }

    public static java.lang.String getDefaultSharedPreferencesName(android.content.Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return 0;
    }

    android.preference.PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceScreen;
    }

    boolean setPreferences(android.preference.PreferenceScreen preferenceScreen) {
        if (preferenceScreen != this.mPreferenceScreen) {
            this.mPreferenceScreen = preferenceScreen;
            return true;
        }
        return false;
    }

    public android.preference.Preference findPreference(java.lang.CharSequence charSequence) {
        if (this.mPreferenceScreen == null) {
            return null;
        }
        return this.mPreferenceScreen.findPreference(charSequence);
    }

    public static void setDefaultValues(android.content.Context context, int i, boolean z) {
        setDefaultValues(context, getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode(), i, z);
    }

    public static void setDefaultValues(android.content.Context context, java.lang.String str, int i, int i2, boolean z) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_HAS_SET_DEFAULT_VALUES, 0);
        if (z || !sharedPreferences.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {
            android.preference.PreferenceManager preferenceManager = new android.preference.PreferenceManager(context);
            preferenceManager.setSharedPreferencesName(str);
            preferenceManager.setSharedPreferencesMode(i);
            preferenceManager.inflateFromResource(context, i2, null);
            android.content.SharedPreferences.Editor putBoolean = sharedPreferences.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true);
            try {
                putBoolean.apply();
            } catch (java.lang.AbstractMethodError e) {
                putBoolean.commit();
            }
        }
    }

    android.content.SharedPreferences.Editor getEditor() {
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (this.mNoCommit) {
            if (this.mEditor == null) {
                this.mEditor = getSharedPreferences().edit();
            }
            return this.mEditor;
        }
        return getSharedPreferences().edit();
    }

    boolean shouldCommit() {
        return !this.mNoCommit;
    }

    private void setNoCommit(boolean z) {
        if (!z && this.mEditor != null) {
            try {
                this.mEditor.apply();
            } catch (java.lang.AbstractMethodError e) {
                this.mEditor.commit();
            }
        }
        this.mNoCommit = z;
    }

    android.app.Activity getActivity() {
        return this.mActivity;
    }

    android.content.Context getContext() {
        return this.mContext;
    }

    void registerOnActivityResultListener(android.preference.PreferenceManager.OnActivityResultListener onActivityResultListener) {
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                this.mActivityResultListeners = new java.util.ArrayList();
            }
            if (!this.mActivityResultListeners.contains(onActivityResultListener)) {
                this.mActivityResultListeners.add(onActivityResultListener);
            }
        }
    }

    void unregisterOnActivityResultListener(android.preference.PreferenceManager.OnActivityResultListener onActivityResultListener) {
        synchronized (this) {
            if (this.mActivityResultListeners != null) {
                this.mActivityResultListeners.remove(onActivityResultListener);
            }
        }
    }

    void dispatchActivityResult(int i, int i2, android.content.Intent intent) {
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mActivityResultListeners);
            int size = arrayList.size();
            for (int i3 = 0; i3 < size && !((android.preference.PreferenceManager.OnActivityResultListener) arrayList.get(i3)).onActivityResult(i, i2, intent); i3++) {
            }
        }
    }

    public void registerOnActivityStopListener(android.preference.PreferenceManager.OnActivityStopListener onActivityStopListener) {
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                this.mActivityStopListeners = new java.util.ArrayList();
            }
            if (!this.mActivityStopListeners.contains(onActivityStopListener)) {
                this.mActivityStopListeners.add(onActivityStopListener);
            }
        }
    }

    public void unregisterOnActivityStopListener(android.preference.PreferenceManager.OnActivityStopListener onActivityStopListener) {
        synchronized (this) {
            if (this.mActivityStopListeners != null) {
                this.mActivityStopListeners.remove(onActivityStopListener);
            }
        }
    }

    void dispatchActivityStop() {
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mActivityStopListeners);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((android.preference.PreferenceManager.OnActivityStopListener) arrayList.get(i)).onActivityStop();
            }
        }
    }

    void registerOnActivityDestroyListener(android.preference.PreferenceManager.OnActivityDestroyListener onActivityDestroyListener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners == null) {
                this.mActivityDestroyListeners = new java.util.ArrayList();
            }
            if (!this.mActivityDestroyListeners.contains(onActivityDestroyListener)) {
                this.mActivityDestroyListeners.add(onActivityDestroyListener);
            }
        }
    }

    void unregisterOnActivityDestroyListener(android.preference.PreferenceManager.OnActivityDestroyListener onActivityDestroyListener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners != null) {
                this.mActivityDestroyListeners.remove(onActivityDestroyListener);
            }
        }
    }

    void dispatchActivityDestroy() {
        java.util.ArrayList arrayList;
        synchronized (this) {
            if (this.mActivityDestroyListeners == null) {
                arrayList = null;
            } else {
                arrayList = new java.util.ArrayList(this.mActivityDestroyListeners);
            }
        }
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((android.preference.PreferenceManager.OnActivityDestroyListener) arrayList.get(i)).onActivityDestroy();
            }
        }
        dismissAllScreens();
    }

    int getNextRequestCode() {
        int i;
        synchronized (this) {
            i = this.mNextRequestCode;
            this.mNextRequestCode = i + 1;
        }
        return i;
    }

    void addPreferencesScreen(android.content.DialogInterface dialogInterface) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                this.mPreferencesScreens = new java.util.ArrayList();
            }
            this.mPreferencesScreens.add(dialogInterface);
        }
    }

    void removePreferencesScreen(android.content.DialogInterface dialogInterface) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                return;
            }
            this.mPreferencesScreens.remove(dialogInterface);
        }
    }

    void dispatchNewIntent(android.content.Intent intent) {
        dismissAllScreens();
    }

    private void dismissAllScreens() {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mPreferencesScreens);
            this.mPreferencesScreens.clear();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((android.content.DialogInterface) arrayList.get(size)).dismiss();
            }
        }
    }

    void setOnPreferenceTreeClickListener(android.preference.PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener) {
        this.mOnPreferenceTreeClickListener = onPreferenceTreeClickListener;
    }

    android.preference.PreferenceManager.OnPreferenceTreeClickListener getOnPreferenceTreeClickListener() {
        return this.mOnPreferenceTreeClickListener;
    }
}
