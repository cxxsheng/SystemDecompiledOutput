package com.android.server.uri;

/* loaded from: classes2.dex */
public class UriPermissionOwner {
    private android.os.Binder externalToken;
    private final java.lang.Object mOwner;
    private android.util.ArraySet<com.android.server.uri.UriPermission> mReadPerms;
    private final com.android.server.uri.UriGrantsManagerInternal mService;
    private android.util.ArraySet<com.android.server.uri.UriPermission> mWritePerms;

    class ExternalToken extends android.os.Binder {
        ExternalToken() {
        }

        com.android.server.uri.UriPermissionOwner getOwner() {
            return com.android.server.uri.UriPermissionOwner.this;
        }
    }

    public UriPermissionOwner(com.android.server.uri.UriGrantsManagerInternal uriGrantsManagerInternal, java.lang.Object obj) {
        this.mService = uriGrantsManagerInternal;
        this.mOwner = obj;
    }

    public android.os.Binder getExternalToken() {
        if (this.externalToken == null) {
            this.externalToken = new com.android.server.uri.UriPermissionOwner.ExternalToken();
        }
        return this.externalToken;
    }

    static com.android.server.uri.UriPermissionOwner fromExternalToken(android.os.IBinder iBinder) {
        if (iBinder instanceof com.android.server.uri.UriPermissionOwner.ExternalToken) {
            return ((com.android.server.uri.UriPermissionOwner.ExternalToken) iBinder).getOwner();
        }
        return null;
    }

    public void removeUriPermissions() {
        removeUriPermissions(3);
    }

    void removeUriPermissions(int i) {
        removeUriPermission(null, i);
    }

    void removeUriPermission(com.android.server.uri.GrantUri grantUri, int i) {
        removeUriPermission(grantUri, i, null, -1);
    }

    void removeUriPermission(com.android.server.uri.GrantUri grantUri, int i, java.lang.String str, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this) {
            if ((i & 1) != 0) {
                try {
                    if (this.mReadPerms != null) {
                        java.util.Iterator<com.android.server.uri.UriPermission> it = this.mReadPerms.iterator();
                        while (it.hasNext()) {
                            com.android.server.uri.UriPermission next = it.next();
                            if (grantUri == null || grantUri.equals(next.uri)) {
                                if (str == null || str.equals(next.targetPkg)) {
                                    if (i2 == -1 || i2 == next.targetUserId) {
                                        arrayList.add(next);
                                        next.removeReadOwner(this);
                                        it.remove();
                                    }
                                }
                            }
                        }
                        if (this.mReadPerms.isEmpty()) {
                            this.mReadPerms = null;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if ((i & 2) != 0 && this.mWritePerms != null) {
                java.util.Iterator<com.android.server.uri.UriPermission> it2 = this.mWritePerms.iterator();
                while (it2.hasNext()) {
                    com.android.server.uri.UriPermission next2 = it2.next();
                    if (grantUri == null || grantUri.equals(next2.uri)) {
                        if (str == null || str.equals(next2.targetPkg)) {
                            if (i2 == -1 || i2 == next2.targetUserId) {
                                arrayList.add(next2);
                                next2.removeWriteOwner(this);
                                it2.remove();
                            }
                        }
                    }
                }
                if (this.mWritePerms.isEmpty()) {
                    this.mWritePerms = null;
                }
            }
        }
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mService.removeUriPermissionIfNeeded((com.android.server.uri.UriPermission) arrayList.get(i3));
        }
    }

    public void addReadPermission(com.android.server.uri.UriPermission uriPermission) {
        synchronized (this) {
            try {
                if (this.mReadPerms == null) {
                    this.mReadPerms = com.google.android.collect.Sets.newArraySet();
                }
                this.mReadPerms.add(uriPermission);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addWritePermission(com.android.server.uri.UriPermission uriPermission) {
        synchronized (this) {
            try {
                if (this.mWritePerms == null) {
                    this.mWritePerms = com.google.android.collect.Sets.newArraySet();
                }
                this.mWritePerms.add(uriPermission);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeReadPermission(com.android.server.uri.UriPermission uriPermission) {
        synchronized (this) {
            try {
                if (this.mReadPerms != null) {
                    this.mReadPerms.remove(uriPermission);
                    if (this.mReadPerms.isEmpty()) {
                        this.mReadPerms = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeWritePermission(com.android.server.uri.UriPermission uriPermission) {
        synchronized (this) {
            try {
                if (this.mWritePerms != null) {
                    this.mWritePerms.remove(uriPermission);
                    if (this.mWritePerms.isEmpty()) {
                        this.mWritePerms = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this) {
            try {
                if (this.mReadPerms != null) {
                    printWriter.print(str);
                    printWriter.print("readUriPermissions=");
                    printWriter.println(this.mReadPerms);
                }
                if (this.mWritePerms != null) {
                    printWriter.print(str);
                    printWriter.print("writeUriPermissions=");
                    printWriter.println(this.mWritePerms);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mOwner.toString());
        synchronized (this) {
            try {
                if (this.mReadPerms != null) {
                    java.util.Iterator<com.android.server.uri.UriPermission> it = this.mReadPerms.iterator();
                    while (it.hasNext()) {
                        it.next().uri.dumpDebug(protoOutputStream, 2246267895810L);
                    }
                }
                if (this.mWritePerms != null) {
                    java.util.Iterator<com.android.server.uri.UriPermission> it2 = this.mWritePerms.iterator();
                    while (it2.hasNext()) {
                        it2.next().uri.dumpDebug(protoOutputStream, 2246267895811L);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        protoOutputStream.end(start);
    }

    public java.lang.String toString() {
        return this.mOwner.toString();
    }
}
