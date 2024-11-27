package android.hidl.base.V1_0;

/* loaded from: classes.dex */
public final class DebugInfo {
    public int pid = 0;
    public long ptr = 0;
    public int arch = 0;

    public static final class Architecture {
        public static final int IS_32BIT = 2;
        public static final int IS_64BIT = 1;
        public static final int UNKNOWN = 0;

        public static final java.lang.String toString(int i) {
            if (i == 0) {
                return "UNKNOWN";
            }
            if (i == 1) {
                return "IS_64BIT";
            }
            if (i == 2) {
                return "IS_32BIT";
            }
            return "0x" + java.lang.Integer.toHexString(i);
        }

        public static final java.lang.String dumpBitfield(int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("UNKNOWN");
            int i2 = 1;
            if ((i & 1) != 1) {
                i2 = 0;
            } else {
                arrayList.add("IS_64BIT");
            }
            if ((i & 2) == 2) {
                arrayList.add("IS_32BIT");
                i2 |= 2;
            }
            if (i != i2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hidl.base.V1_0.DebugInfo.class) {
            return false;
        }
        android.hidl.base.V1_0.DebugInfo debugInfo = (android.hidl.base.V1_0.DebugInfo) obj;
        if (this.pid == debugInfo.pid && this.ptr == debugInfo.ptr && this.arch == debugInfo.arch) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.ptr))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.arch))));
    }

    public final java.lang.String toString() {
        return "{.pid = " + this.pid + ", .ptr = " + this.ptr + ", .arch = " + android.hidl.base.V1_0.DebugInfo.Architecture.toString(this.arch) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hidl.base.V1_0.DebugInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hidl.base.V1_0.DebugInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
            debugInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(debugInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.pid = hwBlob.getInt32(0 + j);
        this.ptr = hwBlob.getInt64(8 + j);
        this.arch = hwBlob.getInt32(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hidl.base.V1_0.DebugInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.pid);
        hwBlob.putInt64(8 + j, this.ptr);
        hwBlob.putInt32(j + 16, this.arch);
    }
}
