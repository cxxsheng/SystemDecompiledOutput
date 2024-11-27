package com.android.server.infra;

/* loaded from: classes2.dex */
public abstract class ServiceNameBaseResolver implements com.android.server.infra.ServiceNameResolver {
    private static final int MSG_RESET_TEMPORARY_SERVICE = 0;
    private static final java.lang.String TAG = com.android.server.infra.ServiceNameBaseResolver.class.getSimpleName();

    @android.annotation.NonNull
    protected final android.content.Context mContext;
    protected final boolean mIsMultiple;

    @android.annotation.Nullable
    private com.android.server.infra.ServiceNameResolver.NameResolverListener mOnSetCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.Handler mTemporaryHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mTemporaryServiceExpiration;

    @android.annotation.NonNull
    protected final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final android.util.SparseArray<java.lang.String[]> mTemporaryServiceNamesList = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final android.util.SparseBooleanArray mDefaultServicesDisabled = new android.util.SparseBooleanArray();

    @android.annotation.Nullable
    public abstract java.lang.String readServiceName(int i);

    @android.annotation.Nullable
    public abstract java.lang.String[] readServiceNameList(int i);

    protected ServiceNameBaseResolver(android.content.Context context, boolean z) {
        this.mContext = context;
        this.mIsMultiple = z;
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void setOnTemporaryServiceNameChangedCallback(@android.annotation.NonNull com.android.server.infra.ServiceNameResolver.NameResolverListener nameResolverListener) {
        synchronized (this.mLock) {
            this.mOnSetCallback = nameResolverListener;
        }
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public java.lang.String getServiceName(int i) {
        java.lang.String[] serviceNameList = getServiceNameList(i);
        if (serviceNameList == null || serviceNameList.length == 0) {
            return null;
        }
        return serviceNameList[0];
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public java.lang.String getDefaultServiceName(int i) {
        java.lang.String[] defaultServiceNameList = getDefaultServiceNameList(i);
        if (defaultServiceNameList == null || defaultServiceNameList.length == 0) {
            return null;
        }
        return defaultServiceNameList[0];
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public java.lang.String[] getServiceNameList(int i) {
        synchronized (this.mLock) {
            try {
                java.lang.String[] strArr = this.mTemporaryServiceNamesList.get(i);
                if (strArr != null) {
                    android.util.Slog.w(TAG, "getServiceName(): using temporary name " + java.util.Arrays.toString(strArr) + " for user " + i);
                    return strArr;
                }
                if (this.mDefaultServicesDisabled.get(i)) {
                    android.util.Slog.w(TAG, "getServiceName(): temporary name not set and default disabled for user " + i);
                    return null;
                }
                return getDefaultServiceNameList(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public java.lang.String[] getDefaultServiceNameList(int i) {
        synchronized (this.mLock) {
            if (this.mIsMultiple) {
                java.lang.String[] readServiceNameList = readServiceNameList(i);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i2 = 0; i2 < readServiceNameList.length; i2++) {
                    try {
                        if (!android.text.TextUtils.isEmpty(readServiceNameList[i2])) {
                            if (android.app.AppGlobals.getPackageManager().getServiceInfo(android.content.ComponentName.unflattenFromString(readServiceNameList[i2]), 786432L, i) != null) {
                                arrayList.add(readServiceNameList[i2]);
                            }
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Could not validate provided services.", e);
                    }
                }
                return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            }
            java.lang.String readServiceName = readServiceName(i);
            return android.text.TextUtils.isEmpty(readServiceName) ? new java.lang.String[0] : new java.lang.String[]{readServiceName};
        }
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public boolean isConfiguredInMultipleMode() {
        return this.mIsMultiple;
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public boolean isTemporary(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mTemporaryServiceNamesList.get(i) != null;
        }
        return z;
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void setTemporaryService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        setTemporaryServices(i, new java.lang.String[]{str}, i2);
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void setTemporaryServices(final int i, @android.annotation.NonNull java.lang.String[] strArr, int i2) {
        synchronized (this.mLock) {
            try {
                this.mTemporaryServiceNamesList.put(i, strArr);
                if (this.mTemporaryHandler == null) {
                    this.mTemporaryHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true) { // from class: com.android.server.infra.ServiceNameBaseResolver.1
                        @Override // android.os.Handler
                        public void handleMessage(android.os.Message message) {
                            if (message.what == 0) {
                                synchronized (com.android.server.infra.ServiceNameBaseResolver.this.mLock) {
                                    com.android.server.infra.ServiceNameBaseResolver.this.resetTemporaryService(i);
                                }
                            } else {
                                android.util.Slog.wtf(com.android.server.infra.ServiceNameBaseResolver.TAG, "invalid handler msg: " + message);
                            }
                        }
                    };
                } else {
                    this.mTemporaryHandler.removeMessages(0);
                }
                long j = i2;
                this.mTemporaryServiceExpiration = android.os.SystemClock.elapsedRealtime() + j;
                this.mTemporaryHandler.sendEmptyMessageDelayed(0, j);
                for (java.lang.String str : strArr) {
                    notifyTemporaryServiceNameChangedLocked(i, str, true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void resetTemporaryService(int i) {
        synchronized (this.mLock) {
            try {
                android.util.Slog.i(TAG, "resetting temporary service for user " + i + " from " + java.util.Arrays.toString(this.mTemporaryServiceNamesList.get(i)));
                this.mTemporaryServiceNamesList.remove(i);
                if (this.mTemporaryHandler != null) {
                    this.mTemporaryHandler.removeMessages(0);
                    this.mTemporaryHandler = null;
                }
                notifyTemporaryServiceNameChangedLocked(i, null, false);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public boolean setDefaultServiceEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (isDefaultServiceEnabledLocked(i) == z) {
                    android.util.Slog.i(TAG, "setDefaultServiceEnabled(" + i + "): already " + z);
                    return false;
                }
                if (z) {
                    android.util.Slog.i(TAG, "disabling default service for user " + i);
                    this.mDefaultServicesDisabled.delete(i);
                } else {
                    android.util.Slog.i(TAG, "enabling default service for user " + i);
                    this.mDefaultServicesDisabled.put(i, true);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public boolean isDefaultServiceEnabled(int i) {
        boolean isDefaultServiceEnabledLocked;
        synchronized (this.mLock) {
            isDefaultServiceEnabledLocked = isDefaultServiceEnabledLocked(i);
        }
        return isDefaultServiceEnabledLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isDefaultServiceEnabledLocked(int i) {
        return !this.mDefaultServicesDisabled.get(i);
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = "FrameworkResourcesServiceNamer[temps=" + this.mTemporaryServiceNamesList + "]";
        }
        return str;
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void dumpShort(@android.annotation.NonNull java.io.PrintWriter printWriter, int i) {
        synchronized (this.mLock) {
            try {
                java.lang.String[] strArr = this.mTemporaryServiceNamesList.get(i);
                if (strArr != null) {
                    printWriter.print("tmpName=");
                    printWriter.print(java.util.Arrays.toString(strArr));
                    long elapsedRealtime = this.mTemporaryServiceExpiration - android.os.SystemClock.elapsedRealtime();
                    printWriter.print(" (expires in ");
                    android.util.TimeUtils.formatDuration(elapsedRealtime, printWriter);
                    printWriter.print("), ");
                }
                printWriter.print("defaultName=");
                printWriter.print(getDefaultServiceName(i));
                printWriter.println(this.mDefaultServicesDisabled.get(i) ? " (disabled)" : " (enabled)");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifyTemporaryServiceNameChangedLocked(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
        if (this.mOnSetCallback != null) {
            this.mOnSetCallback.onNameResolved(i, str, z);
        }
    }
}
