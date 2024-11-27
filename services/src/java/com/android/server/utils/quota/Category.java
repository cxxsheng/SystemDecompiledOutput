package com.android.server.utils.quota;

/* loaded from: classes2.dex */
public final class Category {
    public static final com.android.server.utils.quota.Category SINGLE_CATEGORY = new com.android.server.utils.quota.Category("SINGLE");
    private final int mHash;

    @android.annotation.NonNull
    private final java.lang.String mName;

    public Category(@android.annotation.NonNull java.lang.String str) {
        this.mName = str;
        this.mHash = str.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof com.android.server.utils.quota.Category) {
            return this.mName.equals(((com.android.server.utils.quota.Category) obj).mName);
        }
        return false;
    }

    public int hashCode() {
        return this.mHash;
    }

    public java.lang.String toString() {
        return "Category{" + this.mName + "}";
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mName);
        protoOutputStream.end(start);
    }
}
