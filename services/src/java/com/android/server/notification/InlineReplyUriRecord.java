package com.android.server.notification;

/* loaded from: classes2.dex */
public final class InlineReplyUriRecord {
    private final java.lang.String mKey;
    private final java.lang.String mPackageName;
    private final android.os.IBinder mPermissionOwner;
    private final android.util.ArraySet<android.net.Uri> mUris = new android.util.ArraySet<>();
    private final android.os.UserHandle mUser;

    public InlineReplyUriRecord(android.os.IBinder iBinder, android.os.UserHandle userHandle, java.lang.String str, java.lang.String str2) {
        this.mPermissionOwner = iBinder;
        this.mUser = userHandle;
        this.mPackageName = str;
        this.mKey = str2;
    }

    public android.os.IBinder getPermissionOwner() {
        return this.mPermissionOwner;
    }

    public android.util.ArraySet<android.net.Uri> getUris() {
        return this.mUris;
    }

    public void addUri(android.net.Uri uri) {
        this.mUris.add(uri);
    }

    public int getUserId() {
        int identifier = this.mUser.getIdentifier();
        if (android.os.UserManager.isHeadlessSystemUserMode() && identifier == -1) {
            return android.app.ActivityManager.getCurrentUser();
        }
        if (identifier == -1) {
            return 0;
        }
        return identifier;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getKey() {
        return this.mKey;
    }
}
