package com.android.server.job;

/* loaded from: classes2.dex */
public final class GrantedUriPermissions {
    private final int mGrantFlags;
    private final android.os.IBinder mPermissionOwner;
    private final int mSourceUserId;
    private final java.lang.String mTag;
    private final java.util.ArrayList<android.net.Uri> mUris = new java.util.ArrayList<>();
    private final com.android.server.uri.UriGrantsManagerInternal mUriGrantsManagerInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);

    private GrantedUriPermissions(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        this.mGrantFlags = i;
        this.mSourceUserId = android.os.UserHandle.getUserId(i2);
        this.mTag = str;
        this.mPermissionOwner = this.mUriGrantsManagerInternal.newUriPermissionOwner("job: " + str);
    }

    public void revoke() {
        for (int size = this.mUris.size() - 1; size >= 0; size--) {
            this.mUriGrantsManagerInternal.revokeUriPermissionFromOwner(this.mPermissionOwner, this.mUris.get(size), this.mGrantFlags, this.mSourceUserId);
        }
        this.mUris.clear();
    }

    public static boolean checkGrantFlags(int i) {
        return (i & 3) != 0;
    }

    public static com.android.server.job.GrantedUriPermissions createFromIntent(android.content.Intent intent, int i, java.lang.String str, int i2, java.lang.String str2) {
        int flags = intent.getFlags();
        if (!checkGrantFlags(flags)) {
            return null;
        }
        android.net.Uri data = intent.getData();
        com.android.server.job.GrantedUriPermissions grantedUriPermissions = null;
        if (data != null) {
            grantedUriPermissions = grantUri(data, i, str, i2, flags, str2, null);
        }
        android.content.ClipData clipData = intent.getClipData();
        if (clipData != null) {
            return grantClip(clipData, i, str, i2, flags, str2, grantedUriPermissions);
        }
        return grantedUriPermissions;
    }

    public static com.android.server.job.GrantedUriPermissions createFromClip(android.content.ClipData clipData, int i, java.lang.String str, int i2, int i3, java.lang.String str2) {
        if (!checkGrantFlags(i3) || clipData == null) {
            return null;
        }
        return grantClip(clipData, i, str, i2, i3, str2, null);
    }

    private static com.android.server.job.GrantedUriPermissions grantClip(android.content.ClipData clipData, int i, java.lang.String str, int i2, int i3, java.lang.String str2, com.android.server.job.GrantedUriPermissions grantedUriPermissions) {
        int itemCount = clipData.getItemCount();
        com.android.server.job.GrantedUriPermissions grantedUriPermissions2 = grantedUriPermissions;
        for (int i4 = 0; i4 < itemCount; i4++) {
            grantedUriPermissions2 = grantItem(clipData.getItemAt(i4), i, str, i2, i3, str2, grantedUriPermissions2);
        }
        return grantedUriPermissions2;
    }

    private static com.android.server.job.GrantedUriPermissions grantUri(android.net.Uri uri, int i, java.lang.String str, int i2, int i3, java.lang.String str2, com.android.server.job.GrantedUriPermissions grantedUriPermissions) {
        try {
            int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i));
            android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(uri);
            if (grantedUriPermissions == null) {
                grantedUriPermissions = new com.android.server.job.GrantedUriPermissions(i3, i, str2);
            }
            android.app.UriGrantsManager.getService().grantUriPermissionFromOwner(grantedUriPermissions.mPermissionOwner, i, str, uriWithoutUserId, i3, userIdFromUri, i2);
            grantedUriPermissions.mUris.add(uriWithoutUserId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "AM dead");
        }
        return grantedUriPermissions;
    }

    private static com.android.server.job.GrantedUriPermissions grantItem(android.content.ClipData.Item item, int i, java.lang.String str, int i2, int i3, java.lang.String str2, com.android.server.job.GrantedUriPermissions grantedUriPermissions) {
        com.android.server.job.GrantedUriPermissions grantedUriPermissions2;
        if (item.getUri() == null) {
            grantedUriPermissions2 = grantedUriPermissions;
        } else {
            grantedUriPermissions2 = grantUri(item.getUri(), i, str, i2, i3, str2, grantedUriPermissions);
        }
        android.content.Intent intent = item.getIntent();
        if (intent != null && intent.getData() != null) {
            return grantUri(intent.getData(), i, str, i2, i3, str2, grantedUriPermissions2);
        }
        return grantedUriPermissions2;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print("mGrantFlags=0x");
        printWriter.print(java.lang.Integer.toHexString(this.mGrantFlags));
        printWriter.print(" mSourceUserId=");
        printWriter.println(this.mSourceUserId);
        printWriter.print("mTag=");
        printWriter.println(this.mTag);
        printWriter.print("mPermissionOwner=");
        printWriter.println(this.mPermissionOwner);
        for (int i = 0; i < this.mUris.size(); i++) {
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(this.mUris.get(i));
        }
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mGrantFlags);
        protoOutputStream.write(1120986464258L, this.mSourceUserId);
        protoOutputStream.write(1138166333443L, this.mTag);
        protoOutputStream.write(1138166333444L, this.mPermissionOwner.toString());
        for (int i = 0; i < this.mUris.size(); i++) {
            android.net.Uri uri = this.mUris.get(i);
            if (uri != null) {
                protoOutputStream.write(2237677961221L, uri.toString());
            }
        }
        protoOutputStream.end(start);
    }
}
