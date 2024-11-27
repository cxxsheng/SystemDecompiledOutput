package com.android.ims.internal.uce.uceservice;

/* loaded from: classes4.dex */
public class ImsUceManager {
    public static final java.lang.String ACTION_UCE_SERVICE_DOWN = "com.android.ims.internal.uce.UCE_SERVICE_DOWN";
    public static final java.lang.String ACTION_UCE_SERVICE_UP = "com.android.ims.internal.uce.UCE_SERVICE_UP";
    private static final java.lang.String LOG_TAG = "ImsUceManager";
    private static final java.lang.String UCE_SERVICE = "uce";
    public static final int UCE_SERVICE_STATUS_CLOSED = 2;
    public static final int UCE_SERVICE_STATUS_FAILURE = 0;
    public static final int UCE_SERVICE_STATUS_ON = 1;
    public static final int UCE_SERVICE_STATUS_READY = 3;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static com.android.ims.internal.uce.uceservice.ImsUceManager sUceManager;
    private android.content.Context mContext;
    private com.android.ims.internal.uce.uceservice.IUceService mUceService = null;
    private com.android.ims.internal.uce.uceservice.ImsUceManager.UceServiceDeathRecipient mDeathReceipient = new com.android.ims.internal.uce.uceservice.ImsUceManager.UceServiceDeathRecipient();

    public static com.android.ims.internal.uce.uceservice.ImsUceManager getInstance(android.content.Context context) {
        com.android.ims.internal.uce.uceservice.ImsUceManager imsUceManager;
        synchronized (sLock) {
            if (sUceManager == null && context != null) {
                sUceManager = new com.android.ims.internal.uce.uceservice.ImsUceManager(context);
            }
            imsUceManager = sUceManager;
        }
        return imsUceManager;
    }

    private ImsUceManager(android.content.Context context) {
        this.mContext = context;
        createUceService(true);
    }

    public com.android.ims.internal.uce.uceservice.IUceService getUceServiceInstance() {
        return this.mUceService;
    }

    private java.lang.String getUceServiceName() {
        return UCE_SERVICE;
    }

    public void createUceService(boolean z) {
        if (z && android.os.ServiceManager.checkService(getUceServiceName()) == null) {
            return;
        }
        android.os.IBinder service = android.os.ServiceManager.getService(getUceServiceName());
        if (service != null) {
            try {
                service.linkToDeath(this.mDeathReceipient, 0);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mUceService = com.android.ims.internal.uce.uceservice.IUceService.Stub.asInterface(service);
    }

    private class UceServiceDeathRecipient implements android.os.IBinder.DeathRecipient {
        private UceServiceDeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.ims.internal.uce.uceservice.ImsUceManager.this.mUceService = null;
            if (com.android.ims.internal.uce.uceservice.ImsUceManager.this.mContext != null) {
                com.android.ims.internal.uce.uceservice.ImsUceManager.this.mContext.sendBroadcast(new android.content.Intent(new android.content.Intent(com.android.ims.internal.uce.uceservice.ImsUceManager.ACTION_UCE_SERVICE_DOWN)));
            }
        }
    }
}
