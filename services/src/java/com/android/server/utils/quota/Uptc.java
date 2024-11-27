package com.android.server.utils.quota;

/* loaded from: classes2.dex */
final class Uptc {
    private final int mHash;

    @android.annotation.NonNull
    public final java.lang.String packageName;

    @android.annotation.Nullable
    public final java.lang.String tag;
    public final int userId;

    Uptc(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        this.userId = i;
        this.packageName = str;
        this.tag = str2;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append((i * 31) + (str.hashCode() * 31));
        sb.append(str2);
        this.mHash = sb.toString() == null ? 0 : str2.hashCode() * 31;
    }

    public java.lang.String toString() {
        return string(this.userId, this.packageName, this.tag);
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.userId);
        protoOutputStream.write(1138166333442L, this.packageName);
        protoOutputStream.write(1138166333443L, this.tag);
        protoOutputStream.end(start);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.utils.quota.Uptc)) {
            return false;
        }
        com.android.server.utils.quota.Uptc uptc = (com.android.server.utils.quota.Uptc) obj;
        return this.userId == uptc.userId && java.util.Objects.equals(this.packageName, uptc.packageName) && java.util.Objects.equals(this.tag, uptc.tag);
    }

    public int hashCode() {
        return this.mHash;
    }

    static java.lang.String string(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        java.lang.String str3;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("<");
        sb.append(i);
        sb.append(">");
        sb.append(str);
        if (str2 == null) {
            str3 = "";
        } else {
            str3 = "::" + str2;
        }
        sb.append(str3);
        return sb.toString();
    }
}
