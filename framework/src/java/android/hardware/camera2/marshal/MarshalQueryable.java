package android.hardware.camera2.marshal;

/* loaded from: classes.dex */
public interface MarshalQueryable<T> {
    android.hardware.camera2.marshal.Marshaler<T> createMarshaler(android.hardware.camera2.utils.TypeReference<T> typeReference, int i);

    boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<T> typeReference, int i);
}
