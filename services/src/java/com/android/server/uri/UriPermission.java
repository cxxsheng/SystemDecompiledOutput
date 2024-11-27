package com.android.server.uri;

/* loaded from: classes2.dex */
final class UriPermission {
    static final long INVALID_TIME = Long.MIN_VALUE;
    public static final int STRENGTH_GLOBAL = 2;
    public static final int STRENGTH_NONE = 0;
    public static final int STRENGTH_OWNED = 1;
    public static final int STRENGTH_PERSISTABLE = 3;
    private static final java.lang.String TAG = "UriPermission";
    private android.util.ArraySet<com.android.server.uri.UriPermissionOwner> mReadOwners;
    private android.util.ArraySet<com.android.server.uri.UriPermissionOwner> mWriteOwners;
    final java.lang.String sourcePkg;
    private java.lang.String stringName;
    final java.lang.String targetPkg;
    final int targetUid;
    final int targetUserId;
    final com.android.server.uri.GrantUri uri;
    int modeFlags = 0;
    int ownedModeFlags = 0;
    int globalModeFlags = 0;
    int persistableModeFlags = 0;
    int persistedModeFlags = 0;
    long persistedCreateTime = INVALID_TIME;

    UriPermission(java.lang.String str, java.lang.String str2, int i, com.android.server.uri.GrantUri grantUri) {
        this.targetUserId = android.os.UserHandle.getUserId(i);
        this.sourcePkg = str;
        this.targetPkg = str2;
        this.targetUid = i;
        this.uri = grantUri;
    }

    private void updateModeFlags() {
        int i = this.modeFlags;
        this.modeFlags = this.ownedModeFlags | this.globalModeFlags | this.persistedModeFlags;
        if (android.util.Log.isLoggable(TAG, 2) && this.modeFlags != i) {
            android.util.Slog.d(TAG, "Permission for " + this.targetPkg + " to " + this.uri + " is changing from 0x" + java.lang.Integer.toHexString(i) + " to 0x" + java.lang.Integer.toHexString(this.modeFlags) + " via calling UID " + android.os.Binder.getCallingUid() + " PID " + android.os.Binder.getCallingPid(), new java.lang.Throwable());
        }
    }

    void initPersistedModes(int i, long j) {
        int i2 = i & 3;
        this.persistableModeFlags = i2;
        this.persistedModeFlags = i2;
        this.persistedCreateTime = j;
        updateModeFlags();
    }

    boolean grantModes(int i, @android.annotation.Nullable com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        boolean z = (i & 64) != 0;
        int i2 = i & 3;
        if (z) {
            this.persistableModeFlags |= i2;
        }
        if (uriPermissionOwner == null) {
            this.globalModeFlags = i2 | this.globalModeFlags;
        } else {
            if ((i2 & 1) != 0) {
                addReadOwner(uriPermissionOwner);
            }
            if ((i2 & 2) != 0) {
                addWriteOwner(uriPermissionOwner);
            }
        }
        updateModeFlags();
        return false;
    }

    boolean takePersistableModes(int i) {
        int i2 = i & 3;
        if ((this.persistableModeFlags & i2) != i2) {
            android.util.Slog.w(TAG, "Requested flags 0x" + java.lang.Integer.toHexString(i2) + ", but only 0x" + java.lang.Integer.toHexString(this.persistableModeFlags) + " are allowed");
            return false;
        }
        int i3 = this.persistedModeFlags;
        this.persistedModeFlags = (i2 & this.persistableModeFlags) | this.persistedModeFlags;
        if (this.persistedModeFlags != 0) {
            this.persistedCreateTime = java.lang.System.currentTimeMillis();
        }
        updateModeFlags();
        return this.persistedModeFlags != i3;
    }

    boolean releasePersistableModes(int i) {
        int i2 = this.persistedModeFlags;
        this.persistedModeFlags = (~(i & 3)) & this.persistedModeFlags;
        if (this.persistedModeFlags == 0) {
            this.persistedCreateTime = INVALID_TIME;
        }
        updateModeFlags();
        return this.persistedModeFlags != i2;
    }

    boolean revokeModes(int i, boolean z) {
        boolean z2 = (i & 64) != 0;
        int i2 = i & 3;
        int i3 = this.persistedModeFlags;
        if ((i2 & 1) != 0) {
            if (z2) {
                this.persistableModeFlags &= -2;
                this.persistedModeFlags &= -2;
            }
            this.globalModeFlags &= -2;
            if (this.mReadOwners != null && z) {
                this.ownedModeFlags &= -2;
                java.util.Iterator<com.android.server.uri.UriPermissionOwner> it = this.mReadOwners.iterator();
                while (it.hasNext()) {
                    com.android.server.uri.UriPermissionOwner next = it.next();
                    if (next != null) {
                        next.removeReadPermission(this);
                    }
                }
                this.mReadOwners = null;
            }
        }
        if ((i2 & 2) != 0) {
            if (z2) {
                this.persistableModeFlags &= -3;
                this.persistedModeFlags &= -3;
            }
            this.globalModeFlags &= -3;
            if (this.mWriteOwners != null && z) {
                this.ownedModeFlags &= -3;
                java.util.Iterator<com.android.server.uri.UriPermissionOwner> it2 = this.mWriteOwners.iterator();
                while (it2.hasNext()) {
                    it2.next().removeWritePermission(this);
                }
                this.mWriteOwners = null;
            }
        }
        if (this.persistedModeFlags == 0) {
            this.persistedCreateTime = INVALID_TIME;
        }
        updateModeFlags();
        return this.persistedModeFlags != i3;
    }

    public int getStrength(int i) {
        int i2 = i & 3;
        if ((this.persistableModeFlags & i2) == i2) {
            return 3;
        }
        if ((this.globalModeFlags & i2) == i2) {
            return 2;
        }
        if ((this.ownedModeFlags & i2) == i2) {
            return 1;
        }
        return 0;
    }

    private void addReadOwner(com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        if (this.mReadOwners == null) {
            this.mReadOwners = com.google.android.collect.Sets.newArraySet();
            this.ownedModeFlags |= 1;
            updateModeFlags();
        }
        if (this.mReadOwners.add(uriPermissionOwner)) {
            uriPermissionOwner.addReadPermission(this);
        }
    }

    void removeReadOwner(com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        if (this.mReadOwners == null || !this.mReadOwners.remove(uriPermissionOwner)) {
            android.util.Slog.wtf(TAG, "Unknown read owner " + uriPermissionOwner + " in " + this);
            return;
        }
        if (this.mReadOwners.size() == 0) {
            this.mReadOwners = null;
            this.ownedModeFlags &= -2;
            updateModeFlags();
        }
    }

    private void addWriteOwner(com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        if (this.mWriteOwners == null) {
            this.mWriteOwners = com.google.android.collect.Sets.newArraySet();
            this.ownedModeFlags |= 2;
            updateModeFlags();
        }
        if (this.mWriteOwners.add(uriPermissionOwner)) {
            uriPermissionOwner.addWritePermission(this);
        }
    }

    void removeWriteOwner(com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        if (this.mWriteOwners == null || !this.mWriteOwners.remove(uriPermissionOwner)) {
            android.util.Slog.wtf(TAG, "Unknown write owner " + uriPermissionOwner + " in " + this);
            return;
        }
        if (this.mWriteOwners.size() == 0) {
            this.mWriteOwners = null;
            this.ownedModeFlags &= -3;
            updateModeFlags();
        }
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("UriPermission{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.uri);
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("targetUserId=" + this.targetUserId);
        printWriter.print(" sourcePkg=" + this.sourcePkg);
        printWriter.println(" targetPkg=" + this.targetPkg);
        printWriter.print(str);
        printWriter.print("mode=0x" + java.lang.Integer.toHexString(this.modeFlags));
        printWriter.print(" owned=0x" + java.lang.Integer.toHexString(this.ownedModeFlags));
        printWriter.print(" global=0x" + java.lang.Integer.toHexString(this.globalModeFlags));
        printWriter.print(" persistable=0x" + java.lang.Integer.toHexString(this.persistableModeFlags));
        printWriter.print(" persisted=0x" + java.lang.Integer.toHexString(this.persistedModeFlags));
        if (this.persistedCreateTime != INVALID_TIME) {
            printWriter.print(" persistedCreate=" + this.persistedCreateTime);
        }
        printWriter.println();
        if (this.mReadOwners != null) {
            printWriter.print(str);
            printWriter.println("readOwners:");
            java.util.Iterator<com.android.server.uri.UriPermissionOwner> it = this.mReadOwners.iterator();
            while (it.hasNext()) {
                com.android.server.uri.UriPermissionOwner next = it.next();
                printWriter.print(str);
                printWriter.println("  * " + next);
            }
        }
        if (this.mWriteOwners != null) {
            printWriter.print(str);
            printWriter.println("writeOwners:");
            java.util.Iterator<com.android.server.uri.UriPermissionOwner> it2 = this.mReadOwners.iterator();
            while (it2.hasNext()) {
                com.android.server.uri.UriPermissionOwner next2 = it2.next();
                printWriter.print(str);
                printWriter.println("  * " + next2);
            }
        }
    }

    public static class PersistedTimeComparator implements java.util.Comparator<com.android.server.uri.UriPermission> {
        @Override // java.util.Comparator
        public int compare(com.android.server.uri.UriPermission uriPermission, com.android.server.uri.UriPermission uriPermission2) {
            return java.lang.Long.compare(uriPermission.persistedCreateTime, uriPermission2.persistedCreateTime);
        }
    }

    public static class Snapshot {
        final long persistedCreateTime;
        final int persistedModeFlags;
        final java.lang.String sourcePkg;
        final java.lang.String targetPkg;
        final int targetUserId;
        final com.android.server.uri.GrantUri uri;

        private Snapshot(com.android.server.uri.UriPermission uriPermission) {
            this.targetUserId = uriPermission.targetUserId;
            this.sourcePkg = uriPermission.sourcePkg;
            this.targetPkg = uriPermission.targetPkg;
            this.uri = uriPermission.uri;
            this.persistedModeFlags = uriPermission.persistedModeFlags;
            this.persistedCreateTime = uriPermission.persistedCreateTime;
        }
    }

    public com.android.server.uri.UriPermission.Snapshot snapshot() {
        return new com.android.server.uri.UriPermission.Snapshot();
    }

    public android.content.UriPermission buildPersistedPublicApiObject() {
        return new android.content.UriPermission(this.uri.uri, this.persistedModeFlags, this.persistedCreateTime);
    }

    public android.app.GrantedUriPermission buildGrantedUriPermission() {
        return new android.app.GrantedUriPermission(this.uri.uri, this.targetPkg);
    }
}
