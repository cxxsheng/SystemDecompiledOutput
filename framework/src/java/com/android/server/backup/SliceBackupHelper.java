package com.android.server.backup;

/* loaded from: classes5.dex */
public class SliceBackupHelper extends android.app.backup.BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final java.lang.String KEY_SLICES = "slices";
    static final java.lang.String TAG = "SliceBackupHelper";
    static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public SliceBackupHelper(android.content.Context context) {
        super(1, KEY_SLICES);
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        if (!KEY_SLICES.equals(str)) {
            return null;
        }
        try {
            return android.app.slice.ISliceManager.Stub.asInterface(android.os.ServiceManager.getService("slice")).getBackupPayload(0);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Couldn't communicate with slice manager", e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (DEBUG) {
            android.util.Slog.v(TAG, "Got restore of " + str);
        }
        if (KEY_SLICES.equals(str)) {
            try {
                android.app.slice.ISliceManager.Stub.asInterface(android.os.ServiceManager.getService("slice")).applyRestore(bArr, 0);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Couldn't communicate with slice manager", e);
            }
        }
    }
}
