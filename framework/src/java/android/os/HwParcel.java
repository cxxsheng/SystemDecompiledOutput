package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class HwParcel {
    public static final int STATUS_SUCCESS = 0;
    private static final java.lang.String TAG = "HwParcel";
    private static final libcore.util.NativeAllocationRegistry sNativeRegistry = new libcore.util.NativeAllocationRegistry(android.os.HwParcel.class.getClassLoader(), native_init(), 128);
    private long mNativeContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private static final native long native_init();

    @dalvik.annotation.optimization.FastNative
    private final native void native_setup(boolean z);

    @dalvik.annotation.optimization.FastNative
    private final native boolean[] readBoolVectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native double[] readDoubleVectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native float[] readFloatVectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native short[] readInt16VectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native int[] readInt32VectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native long[] readInt64VectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native byte[] readInt8VectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native android.os.NativeHandle[] readNativeHandleAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native java.lang.String[] readStringVectorAsArray();

    @dalvik.annotation.optimization.FastNative
    private final native void writeBoolVector(boolean[] zArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeDoubleVector(double[] dArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeFloatVector(float[] fArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeInt16Vector(short[] sArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeInt32Vector(int[] iArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeInt64Vector(long[] jArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeInt8Vector(byte[] bArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeNativeHandleVector(android.os.NativeHandle[] nativeHandleArr);

    @dalvik.annotation.optimization.FastNative
    private final native void writeStringVector(java.lang.String[] strArr);

    public final native void enforceInterface(java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    public final native boolean readBool();

    @dalvik.annotation.optimization.FastNative
    public final native android.os.HwBlob readBuffer(long j);

    @dalvik.annotation.optimization.FastNative
    public final native double readDouble();

    @dalvik.annotation.optimization.FastNative
    public final native android.os.HwBlob readEmbeddedBuffer(long j, long j2, long j3, boolean z);

    @dalvik.annotation.optimization.FastNative
    public final native android.os.HidlMemory readEmbeddedHidlMemory(long j, long j2, long j3);

    @dalvik.annotation.optimization.FastNative
    public final native android.os.NativeHandle readEmbeddedNativeHandle(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    public final native float readFloat();

    @dalvik.annotation.optimization.FastNative
    public final native android.os.HidlMemory readHidlMemory();

    @dalvik.annotation.optimization.FastNative
    public final native short readInt16();

    @dalvik.annotation.optimization.FastNative
    public final native int readInt32();

    @dalvik.annotation.optimization.FastNative
    public final native long readInt64();

    @dalvik.annotation.optimization.FastNative
    public final native byte readInt8();

    @dalvik.annotation.optimization.FastNative
    public final native android.os.NativeHandle readNativeHandle();

    @dalvik.annotation.optimization.FastNative
    public final native java.lang.String readString();

    @dalvik.annotation.optimization.FastNative
    public final native android.os.IHwBinder readStrongBinder();

    @dalvik.annotation.optimization.FastNative
    public final native void release();

    @dalvik.annotation.optimization.FastNative
    public final native void releaseTemporaryStorage();

    public final native void send();

    @dalvik.annotation.optimization.FastNative
    public final native void verifySuccess();

    @dalvik.annotation.optimization.FastNative
    public final native void writeBool(boolean z);

    @dalvik.annotation.optimization.FastNative
    public final native void writeBuffer(android.os.HwBlob hwBlob);

    @dalvik.annotation.optimization.FastNative
    public final native void writeDouble(double d);

    @dalvik.annotation.optimization.FastNative
    public final native void writeFloat(float f);

    @dalvik.annotation.optimization.FastNative
    public final native void writeHidlMemory(android.os.HidlMemory hidlMemory);

    @dalvik.annotation.optimization.FastNative
    public final native void writeInt16(short s);

    @dalvik.annotation.optimization.FastNative
    public final native void writeInt32(int i);

    @dalvik.annotation.optimization.FastNative
    public final native void writeInt64(long j);

    @dalvik.annotation.optimization.FastNative
    public final native void writeInt8(byte b);

    @dalvik.annotation.optimization.FastNative
    public final native void writeInterfaceToken(java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    public final native void writeNativeHandle(android.os.NativeHandle nativeHandle);

    @dalvik.annotation.optimization.FastNative
    public final native void writeStatus(int i);

    @dalvik.annotation.optimization.FastNative
    public final native void writeString(java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    public final native void writeStrongBinder(android.os.IHwBinder iHwBinder);

    private HwParcel(boolean z) {
        native_setup(z);
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public HwParcel() {
        native_setup(true);
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public final void writeBoolVector(java.util.ArrayList<java.lang.Boolean> arrayList) {
        int size = arrayList.size();
        boolean[] zArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            zArr[i] = arrayList.get(i).booleanValue();
        }
        writeBoolVector(zArr);
    }

    public final void writeInt8Vector(java.util.ArrayList<java.lang.Byte> arrayList) {
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        writeInt8Vector(bArr);
    }

    public final void writeInt16Vector(java.util.ArrayList<java.lang.Short> arrayList) {
        int size = arrayList.size();
        short[] sArr = new short[size];
        for (int i = 0; i < size; i++) {
            sArr[i] = arrayList.get(i).shortValue();
        }
        writeInt16Vector(sArr);
    }

    public final void writeInt32Vector(java.util.ArrayList<java.lang.Integer> arrayList) {
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).intValue();
        }
        writeInt32Vector(iArr);
    }

    public final void writeInt64Vector(java.util.ArrayList<java.lang.Long> arrayList) {
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = arrayList.get(i).longValue();
        }
        writeInt64Vector(jArr);
    }

    public final void writeFloatVector(java.util.ArrayList<java.lang.Float> arrayList) {
        int size = arrayList.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = arrayList.get(i).floatValue();
        }
        writeFloatVector(fArr);
    }

    public final void writeDoubleVector(java.util.ArrayList<java.lang.Double> arrayList) {
        int size = arrayList.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = arrayList.get(i).doubleValue();
        }
        writeDoubleVector(dArr);
    }

    public final void writeStringVector(java.util.ArrayList<java.lang.String> arrayList) {
        writeStringVector((java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]));
    }

    public final void writeNativeHandleVector(java.util.ArrayList<android.os.NativeHandle> arrayList) {
        writeNativeHandleVector((android.os.NativeHandle[]) arrayList.toArray(new android.os.NativeHandle[arrayList.size()]));
    }

    public final java.util.ArrayList<java.lang.Boolean> readBoolVector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readBoolVectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.Byte> readInt8Vector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readInt8VectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.Short> readInt16Vector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readInt16VectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.Integer> readInt32Vector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readInt32VectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.Long> readInt64Vector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readInt64VectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.Float> readFloatVector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readFloatVectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.Double> readDoubleVector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.os.HwBlob.wrapArray(readDoubleVectorAsArray())));
    }

    public final java.util.ArrayList<java.lang.String> readStringVector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(readStringVectorAsArray()));
    }

    public final java.util.ArrayList<android.os.NativeHandle> readNativeHandleVector() {
        return new java.util.ArrayList<>(java.util.Arrays.asList(readNativeHandleAsArray()));
    }
}
