package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class InstantAppResolverService extends android.app.Service {
    private static final boolean DEBUG_INSTANT = android.os.Build.IS_DEBUGGABLE;
    public static final java.lang.String EXTRA_RESOLVE_INFO = "android.app.extra.RESOLVE_INFO";
    public static final java.lang.String EXTRA_SEQUENCE = "android.app.extra.SEQUENCE";
    private static final java.lang.String TAG = "PackageManager";
    android.os.Handler mHandler;

    @java.lang.Deprecated
    public void onGetInstantAppResolveInfo(int[] iArr, java.lang.String str, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        throw new java.lang.IllegalStateException("Must define onGetInstantAppResolveInfo");
    }

    @java.lang.Deprecated
    public void onGetInstantAppIntentFilter(int[] iArr, java.lang.String str, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        throw new java.lang.IllegalStateException("Must define onGetInstantAppIntentFilter");
    }

    @java.lang.Deprecated
    public void onGetInstantAppResolveInfo(android.content.Intent intent, int[] iArr, java.lang.String str, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        if (intent.isWebIntent()) {
            onGetInstantAppResolveInfo(iArr, str, instantAppResolutionCallback);
        } else {
            instantAppResolutionCallback.onInstantAppResolveInfo(java.util.Collections.emptyList());
        }
    }

    @java.lang.Deprecated
    public void onGetInstantAppIntentFilter(android.content.Intent intent, int[] iArr, java.lang.String str, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        android.util.Log.e(TAG, "New onGetInstantAppIntentFilter is not overridden");
        if (intent.isWebIntent()) {
            onGetInstantAppIntentFilter(iArr, str, instantAppResolutionCallback);
        } else {
            instantAppResolutionCallback.onInstantAppResolveInfo(java.util.Collections.emptyList());
        }
    }

    @java.lang.Deprecated
    public void onGetInstantAppResolveInfo(android.content.Intent intent, int[] iArr, android.os.UserHandle userHandle, java.lang.String str, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppResolveInfo(intent, iArr, str, instantAppResolutionCallback);
    }

    @java.lang.Deprecated
    public void onGetInstantAppIntentFilter(android.content.Intent intent, int[] iArr, android.os.UserHandle userHandle, java.lang.String str, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppIntentFilter(intent, iArr, str, instantAppResolutionCallback);
    }

    public void onGetInstantAppResolveInfo(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppResolveInfo(instantAppRequestInfo.getIntent(), instantAppRequestInfo.getHostDigestPrefix(), instantAppRequestInfo.getUserHandle(), instantAppRequestInfo.getToken(), instantAppResolutionCallback);
    }

    public void onGetInstantAppIntentFilter(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, android.app.InstantAppResolverService.InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppIntentFilter(instantAppRequestInfo.getIntent(), instantAppRequestInfo.getHostDigestPrefix(), instantAppRequestInfo.getUserHandle(), instantAppRequestInfo.getToken(), instantAppResolutionCallback);
    }

    android.os.Looper getLooper() {
        return getBaseContext().getMainLooper();
    }

    @Override // android.app.Service, android.content.ContextWrapper
    public final void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.app.InstantAppResolverService.ServiceHandler(getLooper());
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.app.IInstantAppResolver.Stub() { // from class: android.app.InstantAppResolverService.1
            @Override // android.app.IInstantAppResolver
            public void getInstantAppResolveInfoList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, int i, android.os.IRemoteCallback iRemoteCallback) {
                if (android.app.InstantAppResolverService.DEBUG_INSTANT) {
                    android.util.Slog.v(android.app.InstantAppResolverService.TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo.getToken() + "] Phase1 called; posting");
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = instantAppRequestInfo;
                obtain.arg2 = iRemoteCallback;
                android.app.InstantAppResolverService.this.mHandler.obtainMessage(1, i, 0, obtain).sendToTarget();
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppIntentFilterList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, android.os.IRemoteCallback iRemoteCallback) {
                if (android.app.InstantAppResolverService.DEBUG_INSTANT) {
                    android.util.Slog.v(android.app.InstantAppResolverService.TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo.getToken() + "] Phase2 called; posting");
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = instantAppRequestInfo;
                obtain.arg2 = iRemoteCallback;
                android.app.InstantAppResolverService.this.mHandler.obtainMessage(2, obtain).sendToTarget();
            }
        };
    }

    public static final class InstantAppResolutionCallback {
        private final android.os.IRemoteCallback mCallback;
        private final int mSequence;

        public InstantAppResolutionCallback(int i, android.os.IRemoteCallback iRemoteCallback) {
            this.mCallback = iRemoteCallback;
            this.mSequence = i;
        }

        public void onInstantAppResolveInfo(java.util.List<android.content.pm.InstantAppResolveInfo> list) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelableList(android.app.InstantAppResolverService.EXTRA_RESOLVE_INFO, list);
            bundle.putInt(android.app.InstantAppResolverService.EXTRA_SEQUENCE, this.mSequence);
            try {
                this.mCallback.sendResult(bundle);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private final class ServiceHandler extends android.os.Handler {
        public static final int MSG_GET_INSTANT_APP_INTENT_FILTER = 2;
        public static final int MSG_GET_INSTANT_APP_RESOLVE_INFO = 1;

        public ServiceHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.content.pm.InstantAppRequestInfo instantAppRequestInfo = (android.content.pm.InstantAppRequestInfo) someArgs.arg1;
                    android.os.IRemoteCallback iRemoteCallback = (android.os.IRemoteCallback) someArgs.arg2;
                    someArgs.recycle();
                    int i2 = message.arg1;
                    if (android.app.InstantAppResolverService.DEBUG_INSTANT) {
                        android.util.Slog.d(android.app.InstantAppResolverService.TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo.getToken() + "] Phase1 request; prefix: " + java.util.Arrays.toString(instantAppRequestInfo.getHostDigestPrefix()) + ", userId: " + instantAppRequestInfo.getUserHandle().getIdentifier());
                    }
                    android.app.InstantAppResolverService.this.onGetInstantAppResolveInfo(instantAppRequestInfo, new android.app.InstantAppResolverService.InstantAppResolutionCallback(i2, iRemoteCallback));
                    return;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.content.pm.InstantAppRequestInfo instantAppRequestInfo2 = (android.content.pm.InstantAppRequestInfo) someArgs2.arg1;
                    android.os.IRemoteCallback iRemoteCallback2 = (android.os.IRemoteCallback) someArgs2.arg2;
                    someArgs2.recycle();
                    if (android.app.InstantAppResolverService.DEBUG_INSTANT) {
                        android.util.Slog.d(android.app.InstantAppResolverService.TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo2.getToken() + "] Phase2 request; prefix: " + java.util.Arrays.toString(instantAppRequestInfo2.getHostDigestPrefix()) + ", userId: " + instantAppRequestInfo2.getUserHandle().getIdentifier());
                    }
                    android.app.InstantAppResolverService.this.onGetInstantAppIntentFilter(instantAppRequestInfo2, new android.app.InstantAppResolverService.InstantAppResolutionCallback(-1, iRemoteCallback2));
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown message: " + i);
            }
        }
    }
}
