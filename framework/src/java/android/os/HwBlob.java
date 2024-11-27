package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class HwBlob {
    private static final java.lang.String TAG = "HwBlob";
    private static final libcore.util.NativeAllocationRegistry sNativeRegistry = new libcore.util.NativeAllocationRegistry(android.os.HwBlob.class.getClassLoader(), native_init(), 128);
    private long mNativeContext;

    private static final native long native_init();

    private final native void native_setup(int i);

    public final native void copyToBoolArray(long j, boolean[] zArr, int i);

    public final native void copyToDoubleArray(long j, double[] dArr, int i);

    public final native void copyToFloatArray(long j, float[] fArr, int i);

    public final native void copyToInt16Array(long j, short[] sArr, int i);

    public final native void copyToInt32Array(long j, int[] iArr, int i);

    public final native void copyToInt64Array(long j, long[] jArr, int i);

    public final native void copyToInt8Array(long j, byte[] bArr, int i);

    public final native boolean getBool(long j);

    public final native double getDouble(long j);

    public final native long getFieldHandle(long j);

    public final native float getFloat(long j);

    public final native short getInt16(long j);

    public final native int getInt32(long j);

    public final native long getInt64(long j);

    public final native byte getInt8(long j);

    public final native java.lang.String getString(long j);

    public final native long handle();

    public final native void putBlob(long j, android.os.HwBlob hwBlob);

    public final native void putBool(long j, boolean z);

    public final native void putBoolArray(long j, boolean[] zArr);

    public final native void putDouble(long j, double d);

    public final native void putDoubleArray(long j, double[] dArr);

    public final native void putFloat(long j, float f);

    public final native void putFloatArray(long j, float[] fArr);

    public final native void putInt16(long j, short s);

    public final native void putInt16Array(long j, short[] sArr);

    public final native void putInt32(long j, int i);

    public final native void putInt32Array(long j, int[] iArr);

    public final native void putInt64(long j, long j2);

    public final native void putInt64Array(long j, long[] jArr);

    public final native void putInt8(long j, byte b);

    public final native void putInt8Array(long j, byte[] bArr);

    public final native void putNativeHandle(long j, android.os.NativeHandle nativeHandle);

    public final native void putString(long j, java.lang.String str);

    public HwBlob(int i) {
        native_setup(i);
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public final void putHidlMemory(long j, android.os.HidlMemory hidlMemory) {
        putNativeHandle(0 + j, hidlMemory.getHandle());
        putInt64(16 + j, hidlMemory.getSize());
        putString(j + 24, hidlMemory.getName());
    }

    public static java.lang.Boolean[] wrapArray(boolean[] zArr) {
        int length = zArr.length;
        java.lang.Boolean[] boolArr = new java.lang.Boolean[length];
        for (int i = 0; i < length; i++) {
            boolArr[i] = java.lang.Boolean.valueOf(zArr[i]);
        }
        return boolArr;
    }

    public static java.lang.Long[] wrapArray(long[] jArr) {
        int length = jArr.length;
        java.lang.Long[] lArr = new java.lang.Long[length];
        for (int i = 0; i < length; i++) {
            lArr[i] = java.lang.Long.valueOf(jArr[i]);
        }
        return lArr;
    }

    public static java.lang.Byte[] wrapArray(byte[] bArr) {
        int length = bArr.length;
        java.lang.Byte[] bArr2 = new java.lang.Byte[length];
        for (int i = 0; i < length; i++) {
            bArr2[i] = java.lang.Byte.valueOf(bArr[i]);
        }
        return bArr2;
    }

    public static java.lang.Short[] wrapArray(short[] sArr) {
        int length = sArr.length;
        java.lang.Short[] shArr = new java.lang.Short[length];
        for (int i = 0; i < length; i++) {
            shArr[i] = java.lang.Short.valueOf(sArr[i]);
        }
        return shArr;
    }

    public static java.lang.Integer[] wrapArray(int[] iArr) {
        int length = iArr.length;
        java.lang.Integer[] numArr = new java.lang.Integer[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = java.lang.Integer.valueOf(iArr[i]);
        }
        return numArr;
    }

    public static java.lang.Float[] wrapArray(float[] fArr) {
        int length = fArr.length;
        java.lang.Float[] fArr2 = new java.lang.Float[length];
        for (int i = 0; i < length; i++) {
            fArr2[i] = java.lang.Float.valueOf(fArr[i]);
        }
        return fArr2;
    }

    public static java.lang.Double[] wrapArray(double[] dArr) {
        int length = dArr.length;
        java.lang.Double[] dArr2 = new java.lang.Double[length];
        for (int i = 0; i < length; i++) {
            dArr2[i] = java.lang.Double.valueOf(dArr[i]);
        }
        return dArr2;
    }
}
