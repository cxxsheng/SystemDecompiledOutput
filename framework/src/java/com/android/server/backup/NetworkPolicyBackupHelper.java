package com.android.server.backup;

/* loaded from: classes5.dex */
public class NetworkPolicyBackupHelper extends android.app.backup.BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final java.lang.String KEY_NETWORK_POLICY = "network_policy";
    private final int mUserId;
    private static final java.lang.String TAG = "NetworkPolicyBackupHelper";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public NetworkPolicyBackupHelper(int i) {
        super(1, KEY_NETWORK_POLICY);
        this.mUserId = i;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        if (!KEY_NETWORK_POLICY.equals(str)) {
            return null;
        }
        try {
            return android.net.INetworkPolicyManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.NETWORK_POLICY_SERVICE)).getBackupPayload(this.mUserId);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Couldn't communicate with network policy manager", e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (DEBUG) {
            android.util.Slog.v(TAG, "Got restore of " + str);
        }
        if (KEY_NETWORK_POLICY.equals(str)) {
            try {
                android.net.INetworkPolicyManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.NETWORK_POLICY_SERVICE)).applyRestore(bArr, this.mUserId);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Couldn't communicate with network policy manager", e);
            }
        }
    }
}
