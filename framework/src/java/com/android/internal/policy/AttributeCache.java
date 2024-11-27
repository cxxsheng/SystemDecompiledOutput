package com.android.internal.policy;

/* loaded from: classes5.dex */
public final class AttributeCache {
    private static final int CACHE_SIZE = 4;
    private static com.android.internal.policy.AttributeCache sInstance = null;
    private final android.content.Context mContext;
    private com.android.internal.policy.AttributeCache.PackageMonitor mPackageMonitor;
    private final android.util.LruCache<java.lang.String, com.android.internal.policy.AttributeCache.Package> mPackages = new android.util.LruCache<>(4);
    private final android.content.res.Configuration mConfiguration = new android.content.res.Configuration();

    public static final class Package {
        public final android.content.Context context;
        private final android.util.SparseArray<android.util.ArrayMap<int[], com.android.internal.policy.AttributeCache.Entry>> mMap = new android.util.SparseArray<>();

        public Package(android.content.Context context) {
            this.context = context;
        }
    }

    public static final class Entry {
        public final android.content.res.TypedArray array;
        public final android.content.Context context;

        public Entry(android.content.Context context, android.content.res.TypedArray typedArray) {
            this.context = context;
            this.array = typedArray;
        }

        void recycle() {
            if (this.array != null) {
                this.array.recycle();
            }
        }
    }

    public static void init(android.content.Context context) {
        if (sInstance == null) {
            sInstance = new com.android.internal.policy.AttributeCache(context);
        }
    }

    void monitorPackageRemove(android.os.Handler handler) {
        if (this.mPackageMonitor == null) {
            this.mPackageMonitor = new com.android.internal.policy.AttributeCache.PackageMonitor(this.mContext, handler);
        }
    }

    static class PackageMonitor extends android.content.BroadcastReceiver {
        PackageMonitor(android.content.Context context, android.os.Handler handler) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter(android.content.Intent.ACTION_PACKAGE_REMOVED);
            intentFilter.addDataScheme("package");
            context.registerReceiverAsUser(this, android.os.UserHandle.ALL, intentFilter, null, handler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                com.android.internal.policy.AttributeCache.instance().removePackage(data.getEncodedSchemeSpecificPart());
            }
        }
    }

    public static com.android.internal.policy.AttributeCache instance() {
        return sInstance;
    }

    public AttributeCache(android.content.Context context) {
        this.mContext = context;
    }

    public void removePackage(java.lang.String str) {
        synchronized (this) {
            com.android.internal.policy.AttributeCache.Package remove = this.mPackages.remove(str);
            if (remove != null) {
                for (int i = 0; i < remove.mMap.size(); i++) {
                    android.util.ArrayMap arrayMap = (android.util.ArrayMap) remove.mMap.valueAt(i);
                    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                        ((com.android.internal.policy.AttributeCache.Entry) arrayMap.valueAt(i2)).recycle();
                    }
                }
                remove.context.getResources().flushLayoutCache();
            }
        }
    }

    public void updateConfiguration(android.content.res.Configuration configuration) {
        synchronized (this) {
            if ((this.mConfiguration.updateFrom(configuration) & (-1073741985)) != 0) {
                this.mPackages.evictAll();
            }
        }
    }

    public com.android.internal.policy.AttributeCache.Entry get(java.lang.String str, int i, int[] iArr) {
        return get(str, i, iArr, -2);
    }

    public com.android.internal.policy.AttributeCache.Entry get(java.lang.String str, int i, int[] iArr, int i2) {
        android.util.ArrayMap arrayMap;
        com.android.internal.policy.AttributeCache.Entry entry;
        synchronized (this) {
            com.android.internal.policy.AttributeCache.Package r0 = this.mPackages.get(str);
            if (r0 != null) {
                arrayMap = (android.util.ArrayMap) r0.mMap.get(i);
                if (arrayMap != null && (entry = (com.android.internal.policy.AttributeCache.Entry) arrayMap.get(iArr)) != null) {
                    return entry;
                }
            } else {
                try {
                    android.content.Context createPackageContextAsUser = this.mContext.createPackageContextAsUser(str, 0, new android.os.UserHandle(i2));
                    if (createPackageContextAsUser == null) {
                        return null;
                    }
                    r0 = new com.android.internal.policy.AttributeCache.Package(createPackageContextAsUser);
                    this.mPackages.put(str, r0);
                    arrayMap = null;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    return null;
                }
            }
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap();
                r0.mMap.put(i, arrayMap);
            }
            try {
                com.android.internal.policy.AttributeCache.Entry entry2 = new com.android.internal.policy.AttributeCache.Entry(r0.context, r0.context.obtainStyledAttributes(i, iArr));
                arrayMap.put(iArr, entry2);
                return entry2;
            } catch (android.content.res.Resources.NotFoundException e2) {
                return null;
            }
        }
    }
}
