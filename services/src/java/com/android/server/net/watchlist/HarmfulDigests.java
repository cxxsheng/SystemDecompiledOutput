package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class HarmfulDigests {
    private final java.util.Set<java.lang.String> mDigestSet;

    HarmfulDigests(java.util.List<byte[]> list) {
        java.util.HashSet hashSet = new java.util.HashSet();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            hashSet.add(com.android.internal.util.HexDump.toHexString(list.get(i)));
        }
        this.mDigestSet = java.util.Collections.unmodifiableSet(hashSet);
    }

    public boolean contains(byte[] bArr) {
        return this.mDigestSet.contains(com.android.internal.util.HexDump.toHexString(bArr));
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.util.Iterator<java.lang.String> it = this.mDigestSet.iterator();
        while (it.hasNext()) {
            printWriter.println(it.next());
        }
        printWriter.println("");
    }
}
