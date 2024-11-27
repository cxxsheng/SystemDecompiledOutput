package com.android.server.uri;

/* loaded from: classes2.dex */
public class GrantUri {
    public final boolean prefix;
    public final int sourceUserId;
    public final android.net.Uri uri;

    public GrantUri(int i, android.net.Uri uri, int i2) {
        this.sourceUserId = i;
        this.uri = uri;
        this.prefix = (i2 & 128) != 0;
    }

    public int hashCode() {
        return ((((this.sourceUserId + 31) * 31) + this.uri.hashCode()) * 31) + (this.prefix ? 1231 : 1237);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.server.uri.GrantUri)) {
            return false;
        }
        com.android.server.uri.GrantUri grantUri = (com.android.server.uri.GrantUri) obj;
        return this.uri.equals(grantUri.uri) && this.sourceUserId == grantUri.sourceUserId && this.prefix == grantUri.prefix;
    }

    public java.lang.String toString() {
        java.lang.String str = this.uri.toString() + " [user " + this.sourceUserId + "]";
        if (!this.prefix) {
            return str;
        }
        return str + " [prefix]";
    }

    public java.lang.String toSafeString() {
        java.lang.String str = this.uri.toSafeString() + " [user " + this.sourceUserId + "]";
        if (!this.prefix) {
            return str;
        }
        return str + " [prefix]";
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333442L, this.uri.toString());
        protoOutputStream.write(1120986464257L, this.sourceUserId);
        protoOutputStream.end(start);
    }

    public static com.android.server.uri.GrantUri resolve(int i, android.net.Uri uri, int i2) {
        if (com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return new com.android.server.uri.GrantUri(android.content.ContentProvider.getUserIdFromUri(uri, i), android.content.ContentProvider.getUriWithoutUserId(uri), i2);
        }
        return new com.android.server.uri.GrantUri(i, uri, i2);
    }
}
