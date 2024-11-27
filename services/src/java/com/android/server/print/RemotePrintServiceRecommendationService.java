package com.android.server.print;

/* loaded from: classes2.dex */
class RemotePrintServiceRecommendationService {
    private static final java.lang.String LOG_TAG = "RemotePrintServiceRecS";

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.print.RemotePrintServiceRecommendationService.Connection mConnection;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsBound;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.printservice.recommendation.IRecommendationService mService;

    public interface RemotePrintServiceRecommendationServiceCallbacks {
        void onPrintServiceRecommendationsUpdated(@android.annotation.Nullable java.util.List<android.printservice.recommendation.RecommendationInfo> list);
    }

    private android.content.Intent getServiceIntent(@android.annotation.NonNull android.os.UserHandle userHandle) throws java.lang.Exception {
        java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.printservice.recommendation.RecommendationService"), 268435588, userHandle.getIdentifier());
        if (queryIntentServicesAsUser.size() != 1) {
            throw new java.lang.Exception(queryIntentServicesAsUser.size() + " instead of exactly one service found");
        }
        android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(0);
        android.content.ComponentName componentName = new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        android.content.pm.ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(resolveInfo.serviceInfo.packageName, 0);
        if (applicationInfo == null) {
            throw new java.lang.Exception("Cannot read appInfo for service");
        }
        if ((applicationInfo.flags & 1) == 0) {
            throw new java.lang.Exception("Service is not part of the system");
        }
        if (!"android.permission.BIND_PRINT_RECOMMENDATION_SERVICE".equals(resolveInfo.serviceInfo.permission)) {
            throw new java.lang.Exception("Service " + componentName.flattenToShortString() + " does not require permission android.permission.BIND_PRINT_RECOMMENDATION_SERVICE");
        }
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(componentName);
        return intent;
    }

    RemotePrintServiceRecommendationService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull com.android.server.print.RemotePrintServiceRecommendationService.RemotePrintServiceRecommendationServiceCallbacks remotePrintServiceRecommendationServiceCallbacks) {
        this.mContext = context;
        this.mConnection = new com.android.server.print.RemotePrintServiceRecommendationService.Connection(remotePrintServiceRecommendationServiceCallbacks);
        try {
            android.content.Intent serviceIntent = getServiceIntent(userHandle);
            synchronized (this.mLock) {
                try {
                    this.mIsBound = this.mContext.bindServiceAsUser(serviceIntent, this.mConnection, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, userHandle);
                    if (!this.mIsBound) {
                        throw new java.lang.Exception("Failed to bind to service " + serviceIntent);
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "Could not connect to print service recommendation service", e);
        }
    }

    void close() {
        synchronized (this.mLock) {
            if (this.mService != null) {
                try {
                    this.mService.registerCallbacks((android.printservice.recommendation.IRecommendationServiceCallbacks) null);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(LOG_TAG, "Could not unregister callbacks", e);
                }
                this.mService = null;
            }
            if (this.mIsBound) {
                this.mContext.unbindService(this.mConnection);
                this.mIsBound = false;
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mIsBound || this.mService != null) {
            android.util.Log.w(LOG_TAG, "Service still connected on finalize()");
            close();
        }
        super.finalize();
    }

    private class Connection implements android.content.ServiceConnection {
        private final com.android.server.print.RemotePrintServiceRecommendationService.RemotePrintServiceRecommendationServiceCallbacks mCallbacks;

        public Connection(@android.annotation.NonNull com.android.server.print.RemotePrintServiceRecommendationService.RemotePrintServiceRecommendationServiceCallbacks remotePrintServiceRecommendationServiceCallbacks) {
            this.mCallbacks = remotePrintServiceRecommendationServiceCallbacks;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.print.RemotePrintServiceRecommendationService.this.mLock) {
                com.android.server.print.RemotePrintServiceRecommendationService.this.mService = android.printservice.recommendation.IRecommendationService.Stub.asInterface(iBinder);
                try {
                    com.android.server.print.RemotePrintServiceRecommendationService.this.mService.registerCallbacks(new android.printservice.recommendation.IRecommendationServiceCallbacks.Stub() { // from class: com.android.server.print.RemotePrintServiceRecommendationService.Connection.1
                        public void onRecommendationsUpdated(java.util.List<android.printservice.recommendation.RecommendationInfo> list) {
                            synchronized (com.android.server.print.RemotePrintServiceRecommendationService.this.mLock) {
                                try {
                                    if (com.android.server.print.RemotePrintServiceRecommendationService.this.mIsBound && com.android.server.print.RemotePrintServiceRecommendationService.this.mService != null) {
                                        if (list != null) {
                                            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "recommendation");
                                        }
                                        com.android.server.print.RemotePrintServiceRecommendationService.Connection.this.mCallbacks.onPrintServiceRecommendationsUpdated(list);
                                    }
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.print.RemotePrintServiceRecommendationService.LOG_TAG, "Could not register callbacks", e);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.util.Log.w(com.android.server.print.RemotePrintServiceRecommendationService.LOG_TAG, "Unexpected termination of connection");
            synchronized (com.android.server.print.RemotePrintServiceRecommendationService.this.mLock) {
                com.android.server.print.RemotePrintServiceRecommendationService.this.mService = null;
            }
        }
    }
}
