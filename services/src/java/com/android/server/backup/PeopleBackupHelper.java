package com.android.server.backup;

/* loaded from: classes.dex */
class PeopleBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String KEY_CONVERSATIONS = "people_conversation_infos";
    private static final int STATE_VERSION = 1;
    private static final java.lang.String TAG = com.android.server.backup.PeopleBackupHelper.class.getSimpleName();
    private final int mUserId;

    PeopleBackupHelper(int i) {
        super(1, new java.lang.String[]{KEY_CONVERSATIONS});
        this.mUserId = i;
    }

    protected byte[] getBackupPayload(java.lang.String str) {
        if (!KEY_CONVERSATIONS.equals(str)) {
            android.util.Slog.w(TAG, "Unexpected backup key " + str);
            return new byte[0];
        }
        return ((com.android.server.people.PeopleServiceInternal) com.android.server.LocalServices.getService(com.android.server.people.PeopleServiceInternal.class)).getBackupPayload(this.mUserId);
    }

    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (!KEY_CONVERSATIONS.equals(str)) {
            android.util.Slog.w(TAG, "Unexpected restore key " + str);
            return;
        }
        ((com.android.server.people.PeopleServiceInternal) com.android.server.LocalServices.getService(com.android.server.people.PeopleServiceInternal.class)).restore(this.mUserId, bArr);
    }
}
