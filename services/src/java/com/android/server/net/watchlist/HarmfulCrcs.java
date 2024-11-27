package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class HarmfulCrcs {
    private final java.util.Set<java.lang.Integer> mCrcSet;

    HarmfulCrcs(java.util.List<byte[]> list) {
        java.util.HashSet hashSet = new java.util.HashSet();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            byte[] bArr = list.get(i);
            if (bArr.length <= 4) {
                int i2 = 0;
                for (byte b : bArr) {
                    i2 = (i2 << 8) | (b & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE);
                }
                hashSet.add(java.lang.Integer.valueOf(i2));
            }
        }
        this.mCrcSet = java.util.Collections.unmodifiableSet(hashSet);
    }

    public boolean contains(int i) {
        return this.mCrcSet.contains(java.lang.Integer.valueOf(i));
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.util.Iterator<java.lang.Integer> it = this.mCrcSet.iterator();
        while (it.hasNext()) {
            printWriter.println(com.android.internal.util.HexDump.toHexString(it.next().intValue()));
        }
        printWriter.println("");
    }
}
