package android.hardware.camera2.marshal;

/* loaded from: classes.dex */
public class MarshalRegistry {
    private static final java.lang.Object sMarshalLock = new java.lang.Object();
    private static final java.util.List<android.hardware.camera2.marshal.MarshalQueryable<?>> sRegisteredMarshalQueryables = new java.util.ArrayList();
    private static final java.util.HashMap<android.hardware.camera2.marshal.MarshalRegistry.MarshalToken<?>, android.hardware.camera2.marshal.Marshaler<?>> sMarshalerMap = new java.util.HashMap<>();

    public static <T> void registerMarshalQueryable(android.hardware.camera2.marshal.MarshalQueryable<T> marshalQueryable) {
        synchronized (sMarshalLock) {
            sRegisteredMarshalQueryables.add(marshalQueryable);
        }
    }

    public static <T> android.hardware.camera2.marshal.Marshaler<T> getMarshaler(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        android.hardware.camera2.marshal.Marshaler<T> marshaler;
        synchronized (sMarshalLock) {
            android.hardware.camera2.marshal.MarshalRegistry.MarshalToken<?> marshalToken = new android.hardware.camera2.marshal.MarshalRegistry.MarshalToken<>(typeReference, i);
            marshaler = (android.hardware.camera2.marshal.Marshaler) sMarshalerMap.get(marshalToken);
            if (marshaler == null) {
                if (sRegisteredMarshalQueryables.size() == 0) {
                    throw new java.lang.AssertionError("No available query marshalers registered");
                }
                java.util.Iterator<android.hardware.camera2.marshal.MarshalQueryable<?>> it = sRegisteredMarshalQueryables.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    android.hardware.camera2.marshal.MarshalQueryable<?> next = it.next();
                    if (next.isTypeMappingSupported(typeReference, i)) {
                        marshaler = next.createMarshaler(typeReference, i);
                        break;
                    }
                }
                if (marshaler == null) {
                    throw new java.lang.UnsupportedOperationException("Could not find marshaler that matches the requested combination of type reference " + typeReference + " and native type " + android.hardware.camera2.marshal.MarshalHelpers.toStringNativeType(i));
                }
                sMarshalerMap.put(marshalToken, marshaler);
            }
        }
        return marshaler;
    }

    private static class MarshalToken<T> {
        private final int hash;
        final int nativeType;
        final android.hardware.camera2.utils.TypeReference<T> typeReference;

        public MarshalToken(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
            this.typeReference = typeReference;
            this.nativeType = i;
            this.hash = typeReference.hashCode() ^ i;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.hardware.camera2.marshal.MarshalRegistry.MarshalToken)) {
                return false;
            }
            android.hardware.camera2.marshal.MarshalRegistry.MarshalToken marshalToken = (android.hardware.camera2.marshal.MarshalRegistry.MarshalToken) obj;
            return this.typeReference.equals(marshalToken.typeReference) && this.nativeType == marshalToken.nativeType;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private MarshalRegistry() {
        throw new java.lang.AssertionError();
    }
}
