package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
class CompanionDeviceServiceConnector extends com.android.internal.infra.ServiceConnector.Impl<android.companion.ICompanionDeviceService> {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CDM_CompanionServiceConnector";
    private static final long UNBIND_POST_DELAY_MS = 5000;

    @android.annotation.Nullable
    private static volatile com.android.server.ServiceThread sServiceThread;

    @android.annotation.NonNull
    private final android.content.ComponentName mComponentName;
    private boolean mIsPrimary;

    @android.annotation.Nullable
    private com.android.server.companion.CompanionDeviceServiceConnector.Listener mListener;
    private final int mUserId;

    interface Listener {
        void onBindingDied(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.companion.CompanionDeviceServiceConnector companionDeviceServiceConnector);
    }

    static com.android.server.companion.CompanionDeviceServiceConnector newInstance(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.content.ComponentName componentName, boolean z, boolean z2) {
        return new com.android.server.companion.CompanionDeviceServiceConnector(context, i, componentName, z ? 268435456 : 65536, z2);
    }

    private CompanionDeviceServiceConnector(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2, boolean z) {
        super(context, buildIntent(componentName), i2, i, (java.util.function.Function) null);
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mIsPrimary = z;
    }

    void setListener(@android.annotation.Nullable com.android.server.companion.CompanionDeviceServiceConnector.Listener listener) {
        this.mListener = listener;
    }

    void postOnDeviceAppeared(@android.annotation.NonNull final android.companion.AssociationInfo associationInfo) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.companion.CompanionDeviceServiceConnector$$ExternalSyntheticLambda3
            public final void runNoResult(java.lang.Object obj) {
                ((android.companion.ICompanionDeviceService) obj).onDeviceAppeared(associationInfo);
            }
        });
    }

    void postOnDeviceDisappeared(@android.annotation.NonNull final android.companion.AssociationInfo associationInfo) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.companion.CompanionDeviceServiceConnector$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                ((android.companion.ICompanionDeviceService) obj).onDeviceDisappeared(associationInfo);
            }
        });
    }

    void postOnDevicePresenceEvent(@android.annotation.NonNull final android.companion.DevicePresenceEvent devicePresenceEvent) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.companion.CompanionDeviceServiceConnector$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                ((android.companion.ICompanionDeviceService) obj).onDevicePresenceEvent(devicePresenceEvent);
            }
        });
    }

    void postUnbind() {
        getJobHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.companion.CompanionDeviceServiceConnector$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.CompanionDeviceServiceConnector.this.unbind();
            }
        }, UNBIND_POST_DELAY_MS);
    }

    boolean isPrimary() {
        return this.mIsPrimary;
    }

    android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(@android.annotation.NonNull android.companion.ICompanionDeviceService iCompanionDeviceService, boolean z) {
    }

    public void binderDied() {
        super.binderDied();
        if (this.mListener != null) {
            this.mListener.onBindingDied(this.mUserId, this.mComponentName.getPackageName(), this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: binderAsInterface, reason: merged with bridge method [inline-methods] */
    public android.companion.ICompanionDeviceService m2735binderAsInterface(@android.annotation.NonNull android.os.IBinder iBinder) {
        return android.companion.ICompanionDeviceService.Stub.asInterface(iBinder);
    }

    @android.annotation.NonNull
    protected android.os.Handler getJobHandler() {
        return getServiceThread().getThreadHandler();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    @android.annotation.NonNull
    private static android.content.Intent buildIntent(@android.annotation.NonNull android.content.ComponentName componentName) {
        return new android.content.Intent("android.companion.CompanionDeviceService").setComponent(componentName);
    }

    @android.annotation.NonNull
    private static com.android.server.ServiceThread getServiceThread() {
        if (sServiceThread == null) {
            synchronized (com.android.server.companion.CompanionDeviceManagerService.class) {
                try {
                    if (sServiceThread == null) {
                        sServiceThread = new com.android.server.ServiceThread("companion-device-service-connector", 0, false);
                        sServiceThread.start();
                    }
                } finally {
                }
            }
        }
        return sServiceThread;
    }
}
