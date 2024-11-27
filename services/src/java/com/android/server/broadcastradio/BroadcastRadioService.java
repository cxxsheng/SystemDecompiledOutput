package com.android.server.broadcastradio;

/* loaded from: classes.dex */
public class BroadcastRadioService extends com.android.server.SystemService {
    private final android.hardware.radio.IRadioService mServiceImpl;

    public BroadcastRadioService(android.content.Context context) {
        super(context);
        java.util.ArrayList<java.lang.String> servicesNames = com.android.server.broadcastradio.IRadioServiceAidlImpl.getServicesNames();
        this.mServiceImpl = servicesNames.isEmpty() ? new com.android.server.broadcastradio.IRadioServiceHidlImpl(this) : new com.android.server.broadcastradio.IRadioServiceAidlImpl(this, servicesNames);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("broadcastradio", this.mServiceImpl.asBinder());
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    void enforcePolicyAccess() {
        if (getContext().checkCallingPermission("android.permission.ACCESS_BROADCAST_RADIO") != 0) {
            throw new java.lang.SecurityException("ACCESS_BROADCAST_RADIO permission not granted");
        }
    }
}
