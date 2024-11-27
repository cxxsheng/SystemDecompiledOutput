package com.android.server.vr;

/* loaded from: classes2.dex */
public class EnabledComponentsObserver implements com.android.server.vr.SettingsObserver.SettingChangeListener {
    public static final int DISABLED = -1;
    private static final java.lang.String ENABLED_SERVICES_SEPARATOR = ":";
    public static final int NOT_INSTALLED = -2;
    public static final int NO_ERROR = 0;
    private static final java.lang.String TAG = com.android.server.vr.EnabledComponentsObserver.class.getSimpleName();
    private final android.content.Context mContext;
    private final java.lang.Object mLock;
    private final java.lang.String mServiceName;
    private final java.lang.String mServicePermission;
    private final java.lang.String mSettingName;
    private final android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> mInstalledSet = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> mEnabledSet = new android.util.SparseArray<>();
    private final java.util.Set<com.android.server.vr.EnabledComponentsObserver.EnabledComponentChangeListener> mEnabledComponentListeners = new android.util.ArraySet();

    public interface EnabledComponentChangeListener {
        void onEnabledComponentChanged();
    }

    private EnabledComponentsObserver(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull java.util.Collection<com.android.server.vr.EnabledComponentsObserver.EnabledComponentChangeListener> collection) {
        this.mLock = obj;
        this.mContext = context;
        this.mSettingName = str;
        this.mServiceName = str3;
        this.mServicePermission = str2;
        this.mEnabledComponentListeners.addAll(collection);
    }

    public static com.android.server.vr.EnabledComponentsObserver build(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull java.util.Collection<com.android.server.vr.EnabledComponentsObserver.EnabledComponentChangeListener> collection) {
        com.android.server.vr.SettingsObserver build = com.android.server.vr.SettingsObserver.build(context, handler, str);
        com.android.server.vr.EnabledComponentsObserver enabledComponentsObserver = new com.android.server.vr.EnabledComponentsObserver(context, str, str2, str3, obj, collection);
        new com.android.internal.content.PackageMonitor() { // from class: com.android.server.vr.EnabledComponentsObserver.1
            public void onSomePackagesChanged() {
                com.android.server.vr.EnabledComponentsObserver.this.onPackagesChanged();
            }

            public void onPackageDisappeared(java.lang.String str4, int i) {
                com.android.server.vr.EnabledComponentsObserver.this.onPackagesChanged();
            }

            public void onPackageModified(java.lang.String str4) {
                com.android.server.vr.EnabledComponentsObserver.this.onPackagesChanged();
            }

            public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
                com.android.server.vr.EnabledComponentsObserver.this.onPackagesChanged();
                return super.onHandleForceStop(intent, strArr, i, z);
            }
        }.register(context, looper, android.os.UserHandle.ALL, true);
        build.addListener(enabledComponentsObserver);
        return enabledComponentsObserver;
    }

    public void onPackagesChanged() {
        rebuildAll();
    }

    @Override // com.android.server.vr.SettingsObserver.SettingChangeListener
    public void onSettingChanged() {
        rebuildAll();
    }

    @Override // com.android.server.vr.SettingsObserver.SettingChangeListener
    public void onSettingRestored(java.lang.String str, java.lang.String str2, int i) {
        rebuildAll();
    }

    public void onUsersChanged() {
        rebuildAll();
    }

    public void rebuildAll() {
        synchronized (this.mLock) {
            try {
                this.mInstalledSet.clear();
                this.mEnabledSet.clear();
                for (int i : getCurrentProfileIds()) {
                    android.util.ArraySet<android.content.ComponentName> loadComponentNamesForUser = loadComponentNamesForUser(i);
                    android.util.ArraySet<android.content.ComponentName> loadComponentNamesFromSetting = loadComponentNamesFromSetting(this.mSettingName, i);
                    loadComponentNamesFromSetting.retainAll(loadComponentNamesForUser);
                    this.mInstalledSet.put(i, loadComponentNamesForUser);
                    this.mEnabledSet.put(i, loadComponentNamesFromSetting);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        sendSettingChanged();
    }

    public int isValid(android.content.ComponentName componentName, int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<android.content.ComponentName> arraySet = this.mInstalledSet.get(i);
                if (arraySet == null || !arraySet.contains(componentName)) {
                    return -2;
                }
                android.util.ArraySet<android.content.ComponentName> arraySet2 = this.mEnabledSet.get(i);
                return (arraySet2 == null || !arraySet2.contains(componentName)) ? -1 : 0;
            } finally {
            }
        }
    }

    public android.util.ArraySet<android.content.ComponentName> getInstalled(int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<android.content.ComponentName> arraySet = this.mInstalledSet.get(i);
                if (arraySet != null) {
                    return arraySet;
                }
                return new android.util.ArraySet<>();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.util.ArraySet<android.content.ComponentName> getEnabled(int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<android.content.ComponentName> arraySet = this.mEnabledSet.get(i);
                if (arraySet != null) {
                    return arraySet;
                }
                return new android.util.ArraySet<>();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int[] getCurrentProfileIds() {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService("user");
        if (userManager == null) {
            return null;
        }
        return userManager.getEnabledProfileIds(android.app.ActivityManager.getCurrentUser());
    }

    public static android.util.ArraySet<android.content.ComponentName> loadComponentNames(android.content.pm.PackageManager packageManager, int i, java.lang.String str, java.lang.String str2) {
        android.util.ArraySet<android.content.ComponentName> arraySet = new android.util.ArraySet<>();
        java.util.List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(new android.content.Intent(str), 786564, i);
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
                android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (!str2.equals(serviceInfo.permission)) {
                    android.util.Slog.w(TAG, "Skipping service " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name + ": it does not require the permission " + str2);
                } else {
                    arraySet.add(componentName);
                }
            }
        }
        return arraySet;
    }

    private android.util.ArraySet<android.content.ComponentName> loadComponentNamesForUser(int i) {
        return loadComponentNames(this.mContext.getPackageManager(), i, this.mServiceName, this.mServicePermission);
    }

    private android.util.ArraySet<android.content.ComponentName> loadComponentNamesFromSetting(java.lang.String str, int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            return new android.util.ArraySet<>();
        }
        java.lang.String[] split = stringForUser.split(ENABLED_SERVICES_SEPARATOR);
        android.util.ArraySet<android.content.ComponentName> arraySet = new android.util.ArraySet<>(split.length);
        for (java.lang.String str2 : split) {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str2);
            if (unflattenFromString != null) {
                arraySet.add(unflattenFromString);
            }
        }
        return arraySet;
    }

    private void sendSettingChanged() {
        java.util.Iterator<com.android.server.vr.EnabledComponentsObserver.EnabledComponentChangeListener> it = this.mEnabledComponentListeners.iterator();
        while (it.hasNext()) {
            it.next().onEnabledComponentChanged();
        }
    }
}
