package com.nvidia;

/* loaded from: classes5.dex */
public class NvAppProfiles {
    static final boolean DEBUG = false;
    static final java.lang.String NV_APP_PROFILES_NAME = "Frameworks_NvAppProfiles";
    private static final java.lang.String TAG = "NvAppProfiles";
    private final android.content.Context mContext;
    private com.nvidia.NvCPLSvc.INvCPLRemoteService mNvCPLSvc = null;

    public NvAppProfiles(android.content.Context context) {
        this.mContext = context;
    }

    public int getApplicationProfile(java.lang.String str, int i) {
        getNvCPLService();
        if (this.mNvCPLSvc != null) {
            try {
                return this.mNvCPLSvc.getAppProfileSettingInt(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to retrieve profile setting. Error=" + e.getMessage());
                return -1;
            }
        }
        return -1;
    }

    public java.lang.String getApplicationProfileString(java.lang.String str, int i) {
        getNvCPLService();
        if (this.mNvCPLSvc != null) {
            try {
                return this.mNvCPLSvc.getAppProfileSettingString(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to retrieve profile setting. Error=" + e.getMessage());
                return null;
            }
        }
        return null;
    }

    public void powerHint(java.lang.String str) {
        getNvCPLService();
        if (this.mNvCPLSvc != null) {
            try {
                this.mNvCPLSvc.powerHint(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to send power hint. Error=" + e.getMessage());
            }
        }
    }

    private void getNvCPLService() {
        if (this.mNvCPLSvc == null) {
            try {
                this.mNvCPLSvc = com.nvidia.NvCPLSvc.INvCPLRemoteService.Stub.asInterface(android.os.ServiceManager.getService("nvcpl"));
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Failed to bind to service. " + e.getMessage());
            }
        }
    }
}
