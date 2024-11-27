package com.android.server.backup.keyvalue;

/* loaded from: classes.dex */
public class BackupRequest {
    public java.lang.String packageName;

    public BackupRequest(java.lang.String str) {
        this.packageName = str;
    }

    public java.lang.String toString() {
        return "BackupRequest{pkg=" + this.packageName + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.backup.keyvalue.BackupRequest)) {
            return false;
        }
        return java.util.Objects.equals(this.packageName, ((com.android.server.backup.keyvalue.BackupRequest) obj).packageName);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.packageName);
    }
}
