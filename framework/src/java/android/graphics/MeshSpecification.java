package android.graphics;

/* loaded from: classes.dex */
public class MeshSpecification {
    public static final int ALPHA_TYPE_OPAQUE = 1;
    public static final int ALPHA_TYPE_PREMULTIPLIED = 2;
    public static final int ALPHA_TYPE_UNKNOWN = 0;
    public static final int ALPHA_TYPE_UNPREMULTIPLIED = 3;
    public static final int TYPE_FLOAT = 0;
    public static final int TYPE_FLOAT2 = 1;
    public static final int TYPE_FLOAT3 = 2;
    public static final int TYPE_FLOAT4 = 3;
    public static final int TYPE_UBYTE4 = 4;
    long mNativeMeshSpec;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface AlphaType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface Type {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native long nativeMake(android.graphics.MeshSpecification.Attribute[] attributeArr, int i, android.graphics.MeshSpecification.Varying[] varyingArr, java.lang.String str, java.lang.String str2);

    private static native long nativeMakeWithAlpha(android.graphics.MeshSpecification.Attribute[] attributeArr, int i, android.graphics.MeshSpecification.Varying[] varyingArr, java.lang.String str, java.lang.String str2, long j, int i2);

    private static native long nativeMakeWithCS(android.graphics.MeshSpecification.Attribute[] attributeArr, int i, android.graphics.MeshSpecification.Varying[] varyingArr, java.lang.String str, java.lang.String str2, long j);

    public static class Attribute {
        private final java.lang.String mName;
        private final int mOffset;
        private final int mType;

        public Attribute(int i, int i2, java.lang.String str) {
            this.mType = i;
            this.mOffset = i2;
            this.mName = str;
        }

        public int getType() {
            return this.mType;
        }

        public int getOffset() {
            return this.mOffset;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String toString() {
            return "Attribute{mType=" + this.mType + ", mOffset=" + this.mOffset + ", mName='" + this.mName + android.text.format.DateFormat.QUOTE + '}';
        }
    }

    public static class Varying {
        private final java.lang.String mName;
        private final int mType;

        public Varying(int i, java.lang.String str) {
            this.mType = i;
            this.mName = str;
        }

        public int getType() {
            return this.mType;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String toString() {
            return "Varying{mType=" + this.mType + ", mName='" + this.mName + android.text.format.DateFormat.QUOTE + '}';
        }
    }

    private static class MeshSpecificationHolder {
        public static final libcore.util.NativeAllocationRegistry MESH_SPECIFICATION_REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.MeshSpecification.class.getClassLoader(), android.graphics.MeshSpecification.nativeGetFinalizer());

        private MeshSpecificationHolder() {
        }
    }

    public static android.graphics.MeshSpecification make(android.graphics.MeshSpecification.Attribute[] attributeArr, int i, android.graphics.MeshSpecification.Varying[] varyingArr, java.lang.String str, java.lang.String str2) {
        long nativeMake = nativeMake(attributeArr, i, varyingArr, str, str2);
        if (nativeMake == 0) {
            throw new java.lang.IllegalArgumentException("MeshSpecification construction failed");
        }
        return new android.graphics.MeshSpecification(nativeMake);
    }

    public static android.graphics.MeshSpecification make(android.graphics.MeshSpecification.Attribute[] attributeArr, int i, android.graphics.MeshSpecification.Varying[] varyingArr, java.lang.String str, java.lang.String str2, android.graphics.ColorSpace colorSpace) {
        long nativeMakeWithCS = nativeMakeWithCS(attributeArr, i, varyingArr, str, str2, colorSpace.getNativeInstance());
        if (nativeMakeWithCS == 0) {
            throw new java.lang.IllegalArgumentException("MeshSpecification construction failed");
        }
        return new android.graphics.MeshSpecification(nativeMakeWithCS);
    }

    public static android.graphics.MeshSpecification make(android.graphics.MeshSpecification.Attribute[] attributeArr, int i, android.graphics.MeshSpecification.Varying[] varyingArr, java.lang.String str, java.lang.String str2, android.graphics.ColorSpace colorSpace, int i2) {
        long nativeMakeWithAlpha = nativeMakeWithAlpha(attributeArr, i, varyingArr, str, str2, colorSpace.getNativeInstance(), i2);
        if (nativeMakeWithAlpha == 0) {
            throw new java.lang.IllegalArgumentException("MeshSpecification construction failed");
        }
        return new android.graphics.MeshSpecification(nativeMakeWithAlpha);
    }

    private MeshSpecification(long j) {
        this.mNativeMeshSpec = j;
        android.graphics.MeshSpecification.MeshSpecificationHolder.MESH_SPECIFICATION_REGISTRY.registerNativeAllocation(this, j);
    }
}
