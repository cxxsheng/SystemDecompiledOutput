package com.android.server.pm;

/* loaded from: classes2.dex */
public class DataLoaderManagerService extends com.android.server.SystemService {
    private static final java.lang.String TAG = "DataLoaderManager";
    private final com.android.server.pm.DataLoaderManagerService.DataLoaderManagerBinderService mBinderService;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.util.SparseArray<com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection> mServiceConnections;

    public DataLoaderManagerService(android.content.Context context) {
        super(context);
        this.mServiceConnections = new android.util.SparseArray<>();
        this.mContext = context;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper());
        this.mBinderService = new com.android.server.pm.DataLoaderManagerService.DataLoaderManagerBinderService();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("dataloader_manager", this.mBinderService);
    }

    final class DataLoaderManagerBinderService extends android.content.pm.IDataLoaderManager.Stub {
        DataLoaderManagerBinderService() {
        }

        public boolean bindToDataLoader(final int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, long j, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) {
            synchronized (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections) {
                try {
                    if (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.get(i) != null) {
                        return true;
                    }
                    android.content.ComponentName componentName = new android.content.ComponentName(dataLoaderParamsParcel.packageName, dataLoaderParamsParcel.className);
                    final android.content.ComponentName resolveDataLoaderComponentName = resolveDataLoaderComponentName(componentName);
                    if (resolveDataLoaderComponentName == null) {
                        android.util.Slog.e(com.android.server.pm.DataLoaderManagerService.TAG, "Invalid component: " + componentName + " for ID=" + i);
                        return false;
                    }
                    final com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection dataLoaderServiceConnection = com.android.server.pm.DataLoaderManagerService.this.new DataLoaderServiceConnection(i, iDataLoaderStatusListener);
                    final android.content.Intent intent = new android.content.Intent();
                    intent.setComponent(resolveDataLoaderComponentName);
                    return com.android.server.pm.DataLoaderManagerService.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.DataLoaderManagerService$DataLoaderManagerBinderService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.DataLoaderManagerService.DataLoaderManagerBinderService.this.lambda$bindToDataLoader$0(intent, dataLoaderServiceConnection, resolveDataLoaderComponentName, i);
                        }
                    }, j);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$bindToDataLoader$0(android.content.Intent intent, com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection dataLoaderServiceConnection, android.content.ComponentName componentName, int i) {
            if (!com.android.server.pm.DataLoaderManagerService.this.mContext.bindServiceAsUser(intent, dataLoaderServiceConnection, 1, com.android.server.pm.DataLoaderManagerService.this.mHandler, android.os.UserHandle.of(android.os.UserHandle.getCallingUserId()))) {
                android.util.Slog.e(com.android.server.pm.DataLoaderManagerService.TAG, "Failed to bind to: " + componentName + " for ID=" + i);
                com.android.server.pm.DataLoaderManagerService.this.mContext.unbindService(dataLoaderServiceConnection);
            }
        }

        @android.annotation.Nullable
        private android.content.ComponentName resolveDataLoaderComponentName(android.content.ComponentName componentName) {
            android.content.pm.PackageManager packageManager = com.android.server.pm.DataLoaderManagerService.this.mContext.getPackageManager();
            if (packageManager == null) {
                android.util.Slog.e(com.android.server.pm.DataLoaderManagerService.TAG, "PackageManager is not available.");
                return null;
            }
            android.content.Intent intent = new android.content.Intent("android.intent.action.LOAD_DATA");
            intent.setComponent(componentName);
            java.util.List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 0, android.os.UserHandle.getCallingUserId());
            if (queryIntentServicesAsUser == null || queryIntentServicesAsUser.isEmpty()) {
                android.util.Slog.e(com.android.server.pm.DataLoaderManagerService.TAG, "Failed to find data loader service provider in " + componentName);
                return null;
            }
            if (queryIntentServicesAsUser.size() > 0) {
                android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(0);
                return new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
            }
            android.util.Slog.e(com.android.server.pm.DataLoaderManagerService.TAG, "Didn't find any matching data loader service provider.");
            return null;
        }

        @android.annotation.Nullable
        public android.content.pm.IDataLoader getDataLoader(int i) {
            synchronized (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections) {
                try {
                    com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection dataLoaderServiceConnection = (com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection) com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.get(i, null);
                    if (dataLoaderServiceConnection == null) {
                        return null;
                    }
                    return dataLoaderServiceConnection.getDataLoader();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void unbindFromDataLoader(int i) {
            synchronized (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections) {
                try {
                    com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection dataLoaderServiceConnection = (com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection) com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.get(i, null);
                    if (dataLoaderServiceConnection == null) {
                        return;
                    }
                    dataLoaderServiceConnection.destroy();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DataLoaderServiceConnection implements android.content.ServiceConnection, android.os.IBinder.DeathRecipient {
        android.content.pm.IDataLoader mDataLoader = null;
        final int mId;
        final android.content.pm.IDataLoaderStatusListener mListener;

        DataLoaderServiceConnection(int i, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) {
            this.mId = i;
            this.mListener = iDataLoaderStatusListener;
            callListener(1);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            this.mDataLoader = android.content.pm.IDataLoader.Stub.asInterface(iBinder);
            if (this.mDataLoader == null) {
                onNullBinding(componentName);
                return;
            }
            if (!append()) {
                com.android.server.pm.DataLoaderManagerService.this.mContext.unbindService(this);
                return;
            }
            try {
                iBinder.linkToDeath(this, 0);
                callListener(2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.DataLoaderManagerService.TAG, "Failed to link to DataLoader's death: " + this.mId, e);
                onBindingDied(componentName);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.util.Slog.i(com.android.server.pm.DataLoaderManagerService.TAG, "DataLoader " + this.mId + " disconnected, but will try to recover");
            unbindAndReportDestroyed();
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            android.util.Slog.i(com.android.server.pm.DataLoaderManagerService.TAG, "DataLoader " + this.mId + " died");
            unbindAndReportDestroyed();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(android.content.ComponentName componentName) {
            android.util.Slog.i(com.android.server.pm.DataLoaderManagerService.TAG, "DataLoader " + this.mId + " failed to start");
            unbindAndReportDestroyed();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.i(com.android.server.pm.DataLoaderManagerService.TAG, "DataLoader " + this.mId + " died");
            unbindAndReportDestroyed();
        }

        android.content.pm.IDataLoader getDataLoader() {
            return this.mDataLoader;
        }

        private void unbindAndReportDestroyed() {
            if (unbind()) {
                callListener(0);
            }
        }

        void destroy() {
            if (this.mDataLoader != null) {
                try {
                    this.mDataLoader.destroy(this.mId);
                } catch (android.os.RemoteException e) {
                }
                this.mDataLoader = null;
            }
            unbind();
        }

        boolean unbind() {
            try {
                com.android.server.pm.DataLoaderManagerService.this.mContext.unbindService(this);
            } catch (java.lang.Exception e) {
            }
            return remove();
        }

        private boolean append() {
            synchronized (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections) {
                try {
                    com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection dataLoaderServiceConnection = (com.android.server.pm.DataLoaderManagerService.DataLoaderServiceConnection) com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.get(this.mId);
                    if (dataLoaderServiceConnection == this) {
                        return true;
                    }
                    if (dataLoaderServiceConnection != null) {
                        return false;
                    }
                    com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.append(this.mId, this);
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private boolean remove() {
            synchronized (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections) {
                try {
                    if (com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.get(this.mId) == this) {
                        com.android.server.pm.DataLoaderManagerService.this.mServiceConnections.remove(this.mId);
                        return true;
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void callListener(int i) {
            if (this.mListener != null) {
                try {
                    this.mListener.onStatusChanged(this.mId, i);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }
}
