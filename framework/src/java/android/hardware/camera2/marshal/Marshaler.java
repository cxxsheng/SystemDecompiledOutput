package android.hardware.camera2.marshal;

/* loaded from: classes.dex */
public abstract class Marshaler<T> {
    public static int NATIVE_SIZE_DYNAMIC = -1;
    protected final int mNativeType;
    protected final android.hardware.camera2.utils.TypeReference<T> mTypeReference;

    public abstract int getNativeSize();

    public abstract void marshal(T t, java.nio.ByteBuffer byteBuffer);

    public abstract T unmarshal(java.nio.ByteBuffer byteBuffer);

    protected Marshaler(android.hardware.camera2.marshal.MarshalQueryable<T> marshalQueryable, android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        this.mTypeReference = (android.hardware.camera2.utils.TypeReference) com.android.internal.util.Preconditions.checkNotNull(typeReference, "typeReference must not be null");
        this.mNativeType = android.hardware.camera2.marshal.MarshalHelpers.checkNativeType(i);
        if (!marshalQueryable.isTypeMappingSupported(typeReference, i)) {
            throw new java.lang.UnsupportedOperationException("Unsupported type marshaling for managed type " + typeReference + " and native type " + android.hardware.camera2.marshal.MarshalHelpers.toStringNativeType(i));
        }
    }

    public int calculateMarshalSize(T t) {
        int nativeSize = getNativeSize();
        if (nativeSize == NATIVE_SIZE_DYNAMIC) {
            throw new java.lang.AssertionError("Override this function for dynamically-sized objects");
        }
        return nativeSize;
    }

    public android.hardware.camera2.utils.TypeReference<T> getTypeReference() {
        return this.mTypeReference;
    }

    public int getNativeType() {
        return this.mNativeType;
    }
}
