package com.android.server.telecom;

/* loaded from: classes2.dex */
public class InternalServiceRepository extends com.android.internal.telecom.IInternalServiceRetriever.Stub {
    private final com.android.server.DeviceIdleInternal mDeviceIdleController;
    private final com.android.internal.telecom.IDeviceIdleControllerAdapter.Stub mDeviceIdleControllerAdapter = new com.android.internal.telecom.IDeviceIdleControllerAdapter.Stub() { // from class: com.android.server.telecom.InternalServiceRepository.1
        public void exemptAppTemporarilyForEvent(java.lang.String str, long j, int i, java.lang.String str2) {
            com.android.server.telecom.InternalServiceRepository.this.mDeviceIdleController.addPowerSaveTempWhitelistApp(android.os.Process.myUid(), str, j, i, true, 0, str2);
        }
    };

    public InternalServiceRepository(com.android.server.DeviceIdleInternal deviceIdleInternal) {
        this.mDeviceIdleController = deviceIdleInternal;
    }

    public com.android.internal.telecom.IDeviceIdleControllerAdapter getDeviceIdleController() {
        ensureSystemProcess();
        return this.mDeviceIdleControllerAdapter;
    }

    private void ensureSystemProcess() {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("SYSTEM ONLY API.");
        }
    }
}
