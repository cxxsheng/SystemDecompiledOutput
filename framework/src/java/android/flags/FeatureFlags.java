package android.flags;

/* loaded from: classes.dex */
public class FeatureFlags {
    private static final java.lang.String TAG = "FeatureFlags";
    private static android.flags.FeatureFlags sInstance;
    private static final java.lang.Object sInstanceLock = new java.lang.Object();
    private final java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Boolean>> mBooleanOverrides;
    private final java.util.Set<android.flags.Flag<?>> mDirtyFlags;
    private android.flags.IFeatureFlags mIFeatureFlags;
    private final android.flags.IFeatureFlagsCallback mIFeatureFlagsCallback;
    private final java.util.Set<android.flags.Flag<?>> mKnownFlags;
    private final java.util.Set<android.flags.FeatureFlags.ChangeListener> mListeners;

    public interface ChangeListener {
        void onFlagChanged(android.flags.DynamicFlag<?> dynamicFlag);
    }

    public static android.flags.FeatureFlags getInstance() {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new android.flags.FeatureFlags();
            }
        }
        return sInstance;
    }

    public static void setInstance(android.flags.FeatureFlags featureFlags) {
        synchronized (sInstanceLock) {
            sInstance = featureFlags;
        }
    }

    private FeatureFlags() {
        this(null);
    }

    public FeatureFlags(android.flags.IFeatureFlags iFeatureFlags) {
        this.mKnownFlags = new android.util.ArraySet();
        this.mDirtyFlags = new android.util.ArraySet();
        this.mBooleanOverrides = new java.util.HashMap();
        this.mListeners = new java.util.HashSet();
        this.mIFeatureFlagsCallback = new android.flags.IFeatureFlagsCallback.Stub() { // from class: android.flags.FeatureFlags.1
            @Override // android.flags.IFeatureFlagsCallback
            public void onFlagChange(android.flags.SyncableFlag syncableFlag) {
                for (android.flags.Flag flag : android.flags.FeatureFlags.this.mKnownFlags) {
                    if (android.flags.FeatureFlags.flagEqualsSyncableFlag(flag, syncableFlag)) {
                        if (flag instanceof android.flags.DynamicFlag) {
                            if (flag instanceof android.flags.DynamicBooleanFlag) {
                                java.lang.String value = syncableFlag.getValue();
                                if (value == null) {
                                    value = ((android.flags.DynamicBooleanFlag) flag).getDefault().toString();
                                }
                                android.flags.FeatureFlags.this.addBooleanOverride(syncableFlag.getNamespace(), syncableFlag.getName(), value);
                            }
                            android.flags.FeatureFlags.this.onFlagChange((android.flags.DynamicFlag) flag);
                            return;
                        }
                        return;
                    }
                }
            }
        };
        this.mIFeatureFlags = iFeatureFlags;
        if (this.mIFeatureFlags != null) {
            try {
                this.mIFeatureFlags.registerCallback(this.mIFeatureFlagsCallback);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Could not register callbacks!", e);
            }
        }
    }

    public static android.flags.BooleanFlag booleanFlag(java.lang.String str, java.lang.String str2, boolean z) {
        return (android.flags.BooleanFlag) getInstance().addFlag(new android.flags.BooleanFlag(str, str2, z));
    }

    public static android.flags.FusedOffFlag fusedOffFlag(java.lang.String str, java.lang.String str2) {
        return (android.flags.FusedOffFlag) getInstance().addFlag(new android.flags.FusedOffFlag(str, str2));
    }

    public static android.flags.FusedOnFlag fusedOnFlag(java.lang.String str, java.lang.String str2) {
        return (android.flags.FusedOnFlag) getInstance().addFlag(new android.flags.FusedOnFlag(str, str2));
    }

    public static android.flags.DynamicBooleanFlag dynamicBooleanFlag(java.lang.String str, java.lang.String str2, boolean z) {
        return (android.flags.DynamicBooleanFlag) getInstance().addFlag(new android.flags.DynamicBooleanFlag(str, str2, z));
    }

    public void addChangeListener(android.flags.FeatureFlags.ChangeListener changeListener) {
        this.mListeners.add(changeListener);
    }

    public void removeChangeListener(android.flags.FeatureFlags.ChangeListener changeListener) {
        this.mListeners.remove(changeListener);
    }

    protected void onFlagChange(android.flags.DynamicFlag<?> dynamicFlag) {
        java.util.Iterator<android.flags.FeatureFlags.ChangeListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onFlagChanged(dynamicFlag);
        }
    }

    public boolean isEnabled(android.flags.BooleanFlag booleanFlag) {
        return getBooleanInternal(booleanFlag);
    }

    public boolean isEnabled(android.flags.FusedOffFlag fusedOffFlag) {
        return false;
    }

    public boolean isEnabled(android.flags.FusedOnFlag fusedOnFlag) {
        return true;
    }

    public boolean isCurrentlyEnabled(android.flags.DynamicBooleanFlag dynamicBooleanFlag) {
        return getBooleanInternal(dynamicBooleanFlag);
    }

    private boolean getBooleanInternal(android.flags.Flag<java.lang.Boolean> flag) {
        java.lang.Boolean bool;
        sync();
        java.util.Map<java.lang.String, java.lang.Boolean> map = this.mBooleanOverrides.get(flag.getNamespace());
        if (map == null) {
            bool = null;
        } else {
            bool = map.get(flag.getName());
        }
        if (bool == null) {
            throw new java.lang.IllegalStateException("Boolean flag being read but was not synced: " + flag);
        }
        return bool.booleanValue();
    }

    private <T extends android.flags.Flag<?>> T addFlag(T t) {
        synchronized (android.flags.FeatureFlags.class) {
            this.mDirtyFlags.add(t);
            this.mKnownFlags.add(t);
        }
        return t;
    }

    public void sync() {
        synchronized (android.flags.FeatureFlags.class) {
            if (this.mDirtyFlags.isEmpty()) {
                return;
            }
            syncInternal(this.mDirtyFlags);
            this.mDirtyFlags.clear();
        }
    }

    protected void syncInternal(java.util.Set<android.flags.Flag<?>> set) {
        boolean z;
        android.flags.IFeatureFlags bind = bind();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.flags.Flag<?>> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(flagToSyncableFlag(it.next()));
        }
        java.util.List<android.flags.SyncableFlag> of = java.util.List.of();
        try {
            of = bind.syncFlags(arrayList);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        for (android.flags.Flag<?> flag : set) {
            java.util.Iterator<android.flags.SyncableFlag> it2 = of.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                android.flags.SyncableFlag next = it2.next();
                if (flagEqualsSyncableFlag(flag, next)) {
                    if ((flag instanceof android.flags.BooleanFlag) || (flag instanceof android.flags.DynamicBooleanFlag)) {
                        addBooleanOverride(next.getNamespace(), next.getName(), next.getValue());
                    }
                    z = true;
                }
            }
            if (!z && (flag instanceof android.flags.BooleanFlag)) {
                addBooleanOverride(flag.getNamespace(), flag.getName(), ((android.flags.BooleanFlag) flag).getDefault().booleanValue() ? "true" : "false");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addBooleanOverride(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.util.Map<java.lang.String, java.lang.Boolean> map = this.mBooleanOverrides.get(str);
        if (map == null) {
            map = new java.util.HashMap<>();
            this.mBooleanOverrides.put(str, map);
        }
        map.put(str2, java.lang.Boolean.valueOf(parseBoolean(str3)));
    }

    private android.flags.SyncableFlag flagToSyncableFlag(android.flags.Flag<?> flag) {
        return new android.flags.SyncableFlag(flag.getNamespace(), flag.getName(), flag.getDefault().toString(), flag instanceof android.flags.DynamicFlag);
    }

    private android.flags.IFeatureFlags bind() {
        if (this.mIFeatureFlags == null) {
            this.mIFeatureFlags = android.flags.IFeatureFlags.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.FEATURE_FLAGS_SERVICE));
            try {
                this.mIFeatureFlags.registerCallback(this.mIFeatureFlagsCallback);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to listen for flag changes!");
            }
        }
        return this.mIFeatureFlags;
    }

    static boolean parseBoolean(java.lang.String str) {
        boolean z = str.equalsIgnoreCase("true") || str.equals("1") || str.equalsIgnoreCase("t") || str.equalsIgnoreCase("on");
        if (!z && !str.equalsIgnoreCase("false") && !str.equals(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS) && !str.equalsIgnoreCase(android.app.backup.FullBackup.FILES_TREE_TOKEN) && !str.equalsIgnoreCase("off")) {
            android.util.Log.e(TAG, "Tried parsing " + str + " as boolean but it doesn't look like one. Value expected to be one of true|false, 1|0, t|f, on|off.");
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean flagEqualsSyncableFlag(android.flags.Flag<?> flag, android.flags.SyncableFlag syncableFlag) {
        return flag.getName().equals(syncableFlag.getName()) && flag.getNamespace().equals(syncableFlag.getNamespace());
    }
}
