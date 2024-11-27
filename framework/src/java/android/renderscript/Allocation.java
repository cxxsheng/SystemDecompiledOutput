package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Allocation extends android.renderscript.BaseObj {
    private static final int MAX_NUMBER_IO_INPUT_ALLOC = 16;
    public static final int USAGE_GRAPHICS_CONSTANTS = 8;
    public static final int USAGE_GRAPHICS_RENDER_TARGET = 16;
    public static final int USAGE_GRAPHICS_TEXTURE = 2;
    public static final int USAGE_GRAPHICS_VERTEX = 4;
    public static final int USAGE_IO_INPUT = 32;
    public static final int USAGE_IO_OUTPUT = 64;
    public static final int USAGE_SCRIPT = 1;
    public static final int USAGE_SHARED = 128;
    static java.util.HashMap<java.lang.Long, android.renderscript.Allocation> mAllocationMap = new java.util.HashMap<>();
    static android.graphics.BitmapFactory.Options mBitmapOptions = new android.graphics.BitmapFactory.Options();
    android.renderscript.Allocation mAdaptedAllocation;
    boolean mAutoPadding;
    android.graphics.Bitmap mBitmap;
    android.renderscript.Allocation.OnBufferAvailableListener mBufferNotifier;
    private java.nio.ByteBuffer mByteBuffer;
    private long mByteBufferStride;
    int mCurrentCount;
    int mCurrentDimX;
    int mCurrentDimY;
    int mCurrentDimZ;
    private android.view.Surface mGetSurfaceSurface;
    android.renderscript.Allocation.MipmapControl mMipmapControl;
    boolean mOwningType;
    boolean mReadAllowed;
    int[] mSelectedArray;
    android.renderscript.Type.CubemapFace mSelectedFace;
    int mSelectedLOD;
    int mSelectedX;
    int mSelectedY;
    int mSelectedZ;
    int mSize;
    long mTimeStamp;
    android.renderscript.Type mType;
    int mUsage;
    boolean mWriteAllowed;

    public interface OnBufferAvailableListener {
        void onBufferAvailable(android.renderscript.Allocation allocation);
    }

    static {
        mBitmapOptions.inScaled = false;
    }

    private android.renderscript.Element.DataType validateObjectIsPrimitiveArray(java.lang.Object obj, boolean z) {
        java.lang.Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            throw new android.renderscript.RSIllegalArgumentException("Object passed is not an array of primitives.");
        }
        java.lang.Class<?> componentType = cls.getComponentType();
        if (!componentType.isPrimitive()) {
            throw new android.renderscript.RSIllegalArgumentException("Object passed is not an Array of primitives.");
        }
        if (componentType == java.lang.Long.TYPE) {
            if (z) {
                validateIsInt64();
                return this.mType.mElement.mType;
            }
            return android.renderscript.Element.DataType.SIGNED_64;
        }
        if (componentType == java.lang.Integer.TYPE) {
            if (z) {
                validateIsInt32();
                return this.mType.mElement.mType;
            }
            return android.renderscript.Element.DataType.SIGNED_32;
        }
        if (componentType == java.lang.Short.TYPE) {
            if (z) {
                validateIsInt16OrFloat16();
                return this.mType.mElement.mType;
            }
            return android.renderscript.Element.DataType.SIGNED_16;
        }
        if (componentType == java.lang.Byte.TYPE) {
            if (z) {
                validateIsInt8();
                return this.mType.mElement.mType;
            }
            return android.renderscript.Element.DataType.SIGNED_8;
        }
        if (componentType == java.lang.Float.TYPE) {
            if (z) {
                validateIsFloat32();
            }
            return android.renderscript.Element.DataType.FLOAT_32;
        }
        if (componentType == java.lang.Double.TYPE) {
            if (z) {
                validateIsFloat64();
            }
            return android.renderscript.Element.DataType.FLOAT_64;
        }
        throw new android.renderscript.RSIllegalArgumentException("Parameter of type " + componentType.getSimpleName() + "[] is not compatible with data type " + this.mType.mElement.mType.name() + " of allocation");
    }

    public enum MipmapControl {
        MIPMAP_NONE(0),
        MIPMAP_FULL(1),
        MIPMAP_ON_SYNC_TO_TEXTURE(2);

        int mID;

        MipmapControl(int i) {
            this.mID = i;
        }
    }

    private long getIDSafe() {
        if (this.mAdaptedAllocation != null) {
            return this.mAdaptedAllocation.getID(this.mRS);
        }
        return getID(this.mRS);
    }

    public android.renderscript.Element getElement() {
        return this.mType.getElement();
    }

    public int getUsage() {
        return this.mUsage;
    }

    public android.renderscript.Allocation.MipmapControl getMipmap() {
        return this.mMipmapControl;
    }

    public void setAutoPadding(boolean z) {
        this.mAutoPadding = z;
    }

    public int getBytesSize() {
        if (this.mType.mDimYuv != 0) {
            return (int) java.lang.Math.ceil(this.mType.getCount() * this.mType.getElement().getBytesSize() * 1.5d);
        }
        return this.mType.getCount() * this.mType.getElement().getBytesSize();
    }

    private void updateCacheInfo(android.renderscript.Type type) {
        this.mCurrentDimX = type.getX();
        this.mCurrentDimY = type.getY();
        this.mCurrentDimZ = type.getZ();
        this.mCurrentCount = this.mCurrentDimX;
        if (this.mCurrentDimY > 1) {
            this.mCurrentCount *= this.mCurrentDimY;
        }
        if (this.mCurrentDimZ > 1) {
            this.mCurrentCount *= this.mCurrentDimZ;
        }
    }

    private void setBitmap(android.graphics.Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    Allocation(long j, android.renderscript.RenderScript renderScript, android.renderscript.Type type, int i) {
        super(j, renderScript);
        this.mOwningType = false;
        this.mTimeStamp = -1L;
        this.mReadAllowed = true;
        this.mWriteAllowed = true;
        this.mAutoPadding = false;
        this.mSelectedFace = android.renderscript.Type.CubemapFace.POSITIVE_X;
        this.mGetSurfaceSurface = null;
        this.mByteBuffer = null;
        this.mByteBufferStride = -1L;
        if ((i & (-256)) != 0) {
            throw new android.renderscript.RSIllegalArgumentException("Unknown usage specified.");
        }
        if ((i & 32) != 0) {
            this.mWriteAllowed = false;
            if ((i & (-36)) != 0) {
                throw new android.renderscript.RSIllegalArgumentException("Invalid usage combination.");
            }
        }
        this.mType = type;
        this.mUsage = i;
        if (type != null) {
            this.mSize = this.mType.getCount() * this.mType.getElement().getBytesSize();
            updateCacheInfo(type);
        }
        try {
            android.renderscript.RenderScript.registerNativeAllocation.invoke(android.renderscript.RenderScript.sRuntime, java.lang.Integer.valueOf(this.mSize));
            this.guard.open("destroy");
        } catch (java.lang.Exception e) {
            android.util.Log.e("RenderScript_jni", "Couldn't invoke registerNativeAllocation:" + e);
            throw new android.renderscript.RSRuntimeException("Couldn't invoke registerNativeAllocation:" + e);
        }
    }

    Allocation(long j, android.renderscript.RenderScript renderScript, android.renderscript.Type type, boolean z, int i, android.renderscript.Allocation.MipmapControl mipmapControl) {
        this(j, renderScript, type, i);
        this.mOwningType = z;
        this.mMipmapControl = mipmapControl;
    }

    @Override // android.renderscript.BaseObj
    protected void finalize() throws java.lang.Throwable {
        android.renderscript.RenderScript.registerNativeFree.invoke(android.renderscript.RenderScript.sRuntime, java.lang.Integer.valueOf(this.mSize));
        super.finalize();
    }

    private void validateIsInt64() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.SIGNED_64 || this.mType.mElement.mType == android.renderscript.Element.DataType.UNSIGNED_64) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("64 bit integer source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    private void validateIsInt32() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.SIGNED_32 || this.mType.mElement.mType == android.renderscript.Element.DataType.UNSIGNED_32) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("32 bit integer source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    private void validateIsInt16OrFloat16() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.SIGNED_16 || this.mType.mElement.mType == android.renderscript.Element.DataType.UNSIGNED_16 || this.mType.mElement.mType == android.renderscript.Element.DataType.FLOAT_16) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("16 bit integer source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    private void validateIsInt8() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.SIGNED_8 || this.mType.mElement.mType == android.renderscript.Element.DataType.UNSIGNED_8) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("8 bit integer source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    private void validateIsFloat32() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.FLOAT_32) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("32 bit float source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    private void validateIsFloat64() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.FLOAT_64) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("64 bit float source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    private void validateIsObject() {
        if (this.mType.mElement.mType == android.renderscript.Element.DataType.RS_ELEMENT || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_TYPE || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_ALLOCATION || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_SAMPLER || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_SCRIPT || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_MESH || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_PROGRAM_FRAGMENT || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_PROGRAM_VERTEX || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_PROGRAM_RASTER || this.mType.mElement.mType == android.renderscript.Element.DataType.RS_PROGRAM_STORE) {
        } else {
            throw new android.renderscript.RSIllegalArgumentException("Object source does not match allocation type " + this.mType.mElement.mType);
        }
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        super.updateFromNative();
        long nAllocationGetType = this.mRS.nAllocationGetType(getID(this.mRS));
        if (nAllocationGetType != 0) {
            this.mType = new android.renderscript.Type(nAllocationGetType, this.mRS);
            this.mType.updateFromNative();
            updateCacheInfo(this.mType);
        }
    }

    public android.renderscript.Type getType() {
        return this.mType;
    }

    public void syncAll(int i) {
        try {
            android.os.Trace.traceBegin(32768L, "syncAll");
            switch (i) {
                case 1:
                case 2:
                    if ((this.mUsage & 128) != 0) {
                        copyFrom(this.mBitmap);
                        break;
                    }
                    break;
                case 4:
                case 8:
                    break;
                case 128:
                    if ((this.mUsage & 128) != 0) {
                        copyTo(this.mBitmap);
                        break;
                    }
                    break;
                default:
                    throw new android.renderscript.RSIllegalArgumentException("Source must be exactly one usage type.");
            }
            this.mRS.validate();
            this.mRS.nAllocationSyncAll(getIDSafe(), i);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void ioSend() {
        try {
            android.os.Trace.traceBegin(32768L, "ioSend");
            if ((this.mUsage & 64) == 0) {
                throw new android.renderscript.RSIllegalArgumentException("Can only send buffer if IO_OUTPUT usage specified.");
            }
            this.mRS.validate();
            this.mRS.nAllocationIoSend(getID(this.mRS));
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void ioReceive() {
        try {
            android.os.Trace.traceBegin(32768L, "ioReceive");
            if ((this.mUsage & 32) == 0) {
                throw new android.renderscript.RSIllegalArgumentException("Can only receive if IO_INPUT usage specified.");
            }
            this.mRS.validate();
            this.mTimeStamp = this.mRS.nAllocationIoReceive(getID(this.mRS));
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(android.renderscript.BaseObj[] baseObjArr) {
        try {
            android.os.Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            validateIsObject();
            if (baseObjArr.length != this.mCurrentCount) {
                throw new android.renderscript.RSIllegalArgumentException("Array size mismatch, allocation sizeX = " + this.mCurrentCount + ", array length = " + baseObjArr.length);
            }
            if (android.renderscript.RenderScript.sPointerSize == 8) {
                long[] jArr = new long[baseObjArr.length * 4];
                for (int i = 0; i < baseObjArr.length; i++) {
                    jArr[i * 4] = baseObjArr[i].getID(this.mRS);
                }
                copy1DRangeFromUnchecked(0, this.mCurrentCount, (java.lang.Object) jArr);
            } else {
                int[] iArr = new int[baseObjArr.length];
                for (int i2 = 0; i2 < baseObjArr.length; i2++) {
                    iArr[i2] = (int) baseObjArr[i2].getID(this.mRS);
                }
                copy1DRangeFromUnchecked(0, this.mCurrentCount, iArr);
            }
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    private void validateBitmapFormat(android.graphics.Bitmap bitmap) {
        android.graphics.Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            throw new android.renderscript.RSIllegalArgumentException("Bitmap has an unsupported format for this operation");
        }
        switch (config) {
            case ALPHA_8:
                if (this.mType.getElement().mKind != android.renderscript.Element.DataKind.PIXEL_A) {
                    throw new android.renderscript.RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                }
                return;
            case ARGB_8888:
                if (this.mType.getElement().mKind != android.renderscript.Element.DataKind.PIXEL_RGBA || this.mType.getElement().getBytesSize() != 4) {
                    throw new android.renderscript.RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                }
                return;
            case RGB_565:
                if (this.mType.getElement().mKind != android.renderscript.Element.DataKind.PIXEL_RGB || this.mType.getElement().getBytesSize() != 2) {
                    throw new android.renderscript.RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                }
                return;
            case ARGB_4444:
                if (this.mType.getElement().mKind != android.renderscript.Element.DataKind.PIXEL_RGBA || this.mType.getElement().getBytesSize() != 2) {
                    throw new android.renderscript.RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                }
                return;
            default:
                return;
        }
    }

    private void validateBitmapSize(android.graphics.Bitmap bitmap) {
        if (this.mCurrentDimX != bitmap.getWidth() || this.mCurrentDimY != bitmap.getHeight()) {
            throw new android.renderscript.RSIllegalArgumentException("Cannot update allocation from bitmap, sizes mismatch");
        }
    }

    private void copyFromUnchecked(java.lang.Object obj, android.renderscript.Element.DataType dataType, int i) {
        try {
            android.os.Trace.traceBegin(32768L, "copyFromUnchecked");
            this.mRS.validate();
            if (this.mCurrentDimZ > 0) {
                copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, obj, dataType, i);
            } else if (this.mCurrentDimY > 0) {
                copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, this.mCurrentDimY, obj, dataType, i);
            } else {
                copy1DRangeFromUnchecked(0, this.mCurrentCount, obj, dataType, i);
            }
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copyFromUnchecked(java.lang.Object obj) {
        try {
            android.os.Trace.traceBegin(32768L, "copyFromUnchecked");
            copyFromUnchecked(obj, validateObjectIsPrimitiveArray(obj, false), java.lang.reflect.Array.getLength(obj));
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copyFromUnchecked(int[] iArr) {
        copyFromUnchecked(iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyFromUnchecked(short[] sArr) {
        copyFromUnchecked(sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyFromUnchecked(byte[] bArr) {
        copyFromUnchecked(bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyFromUnchecked(float[] fArr) {
        copyFromUnchecked(fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyFrom(java.lang.Object obj) {
        try {
            android.os.Trace.traceBegin(32768L, "copyFrom");
            copyFromUnchecked(obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(int[] iArr) {
        validateIsInt32();
        copyFromUnchecked(iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyFrom(short[] sArr) {
        validateIsInt16OrFloat16();
        copyFromUnchecked(sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyFrom(byte[] bArr) {
        validateIsInt8();
        copyFromUnchecked(bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyFrom(float[] fArr) {
        validateIsFloat32();
        copyFromUnchecked(fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyFrom(android.graphics.Bitmap bitmap) {
        try {
            android.os.Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            if (bitmap.getConfig() == null) {
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
                new android.graphics.Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (android.graphics.Paint) null);
                copyFrom(createBitmap);
            } else {
                validateBitmapSize(bitmap);
                validateBitmapFormat(bitmap);
                this.mRS.nAllocationCopyFromBitmap(getID(this.mRS), bitmap);
            }
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(android.renderscript.Allocation allocation) {
        try {
            android.os.Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            if (!this.mType.equals(allocation.getType())) {
                throw new android.renderscript.RSIllegalArgumentException("Types of allocations must match.");
            }
            copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, allocation, 0, 0);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void setFromFieldPacker(int i, android.renderscript.FieldPacker fieldPacker) {
        this.mRS.validate();
        int bytesSize = this.mType.mElement.getBytesSize();
        byte[] data = fieldPacker.getData();
        int pos = fieldPacker.getPos();
        int i2 = pos / bytesSize;
        if (bytesSize * i2 != pos) {
            throw new android.renderscript.RSIllegalArgumentException("Field packer length " + pos + " not divisible by element size " + bytesSize + android.media.MediaMetrics.SEPARATOR);
        }
        copy1DRangeFromUnchecked(i, i2, data);
    }

    public void setFromFieldPacker(int i, int i2, android.renderscript.FieldPacker fieldPacker) {
        setFromFieldPacker(i, 0, 0, i2, fieldPacker);
    }

    public void setFromFieldPacker(int i, int i2, int i3, int i4, android.renderscript.FieldPacker fieldPacker) {
        this.mRS.validate();
        if (i4 >= this.mType.mElement.mElements.length) {
            throw new android.renderscript.RSIllegalArgumentException("Component_number " + i4 + " out of range.");
        }
        if (i < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset x must be >= 0.");
        }
        if (i2 < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset y must be >= 0.");
        }
        if (i3 < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset z must be >= 0.");
        }
        byte[] data = fieldPacker.getData();
        int pos = fieldPacker.getPos();
        int bytesSize = this.mType.mElement.mElements[i4].getBytesSize() * this.mType.mElement.mArraySizes[i4];
        if (pos != bytesSize) {
            throw new android.renderscript.RSIllegalArgumentException("Field packer sizelength " + pos + " does not match component size " + bytesSize + android.media.MediaMetrics.SEPARATOR);
        }
        this.mRS.nAllocationElementData(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, data, pos);
    }

    private void data1DChecks(int i, int i2, int i3, int i4, boolean z) {
        this.mRS.validate();
        if (i < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset must be >= 0.");
        }
        if (i2 < 1) {
            throw new android.renderscript.RSIllegalArgumentException("Count must be >= 1.");
        }
        if (i + i2 > this.mCurrentCount) {
            throw new android.renderscript.RSIllegalArgumentException("Overflow, Available count " + this.mCurrentCount + ", got " + i2 + " at offset " + i + android.media.MediaMetrics.SEPARATOR);
        }
        if (z) {
            if (i3 < (i4 / 4) * 3) {
                throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
            }
        } else if (i3 < i4) {
            throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
        }
    }

    public void generateMipmaps() {
        this.mRS.nAllocationGenerateMipmaps(getID(this.mRS));
    }

    private void copy1DRangeFromUnchecked(int i, int i2, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i3) {
        boolean z;
        try {
            android.os.Trace.traceBegin(32768L, "copy1DRangeFromUnchecked");
            int bytesSize = this.mType.mElement.getBytesSize() * i2;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                z = true;
            } else {
                z = false;
            }
            data1DChecks(i, i2, i3 * dataType.mSize, bytesSize, z);
            this.mRS.nAllocationData1D(getIDSafe(), i, this.mSelectedLOD, i2, obj, bytesSize, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy1DRangeFromUnchecked(int i, int i2, java.lang.Object obj) {
        copy1DRangeFromUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, false), java.lang.reflect.Array.getLength(obj));
    }

    public void copy1DRangeFromUnchecked(int i, int i2, int[] iArr) {
        copy1DRangeFromUnchecked(i, i2, iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, short[] sArr) {
        copy1DRangeFromUnchecked(i, i2, sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, byte[] bArr) {
        copy1DRangeFromUnchecked(i, i2, bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, float[] fArr) {
        copy1DRangeFromUnchecked(i, i2, fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, java.lang.Object obj) {
        copy1DRangeFromUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
    }

    public void copy1DRangeFrom(int i, int i2, int[] iArr) {
        validateIsInt32();
        copy1DRangeFromUnchecked(i, i2, iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, short[] sArr) {
        validateIsInt16OrFloat16();
        copy1DRangeFromUnchecked(i, i2, sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, byte[] bArr) {
        validateIsInt8();
        copy1DRangeFromUnchecked(i, i2, bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, float[] fArr) {
        validateIsFloat32();
        copy1DRangeFromUnchecked(i, i2, fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, android.renderscript.Allocation allocation, int i3) {
        android.os.Trace.traceBegin(32768L, "copy1DRangeFrom");
        this.mRS.nAllocationData2D(getIDSafe(), i, 0, this.mSelectedLOD, this.mSelectedFace.mID, i2, 1, allocation.getID(this.mRS), i3, 0, allocation.mSelectedLOD, allocation.mSelectedFace.mID);
        android.os.Trace.traceEnd(32768L);
    }

    private void validate2DRange(int i, int i2, int i3, int i4) {
        if (this.mAdaptedAllocation == null) {
            if (i < 0 || i2 < 0) {
                throw new android.renderscript.RSIllegalArgumentException("Offset cannot be negative.");
            }
            if (i4 < 0 || i3 < 0) {
                throw new android.renderscript.RSIllegalArgumentException("Height or width cannot be negative.");
            }
            if (i + i3 > this.mCurrentDimX || i2 + i4 > this.mCurrentDimY) {
                throw new android.renderscript.RSIllegalArgumentException("Updated region larger than allocation.");
            }
        }
    }

    void copy2DRangeFromUnchecked(int i, int i2, int i3, int i4, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i5) {
        boolean z;
        int i6;
        try {
            android.os.Trace.traceBegin(32768L, "copy2DRangeFromUnchecked");
            this.mRS.validate();
            validate2DRange(i, i2, i3, i4);
            int bytesSize = this.mType.mElement.getBytesSize() * i3 * i4;
            int i7 = dataType.mSize * i5;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i7) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                i6 = bytesSize;
                z = true;
            } else {
                if (bytesSize > i7) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                i6 = i7;
            }
            this.mRS.nAllocationData2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, i3, i4, obj, i6, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, java.lang.Object obj) {
        try {
            android.os.Trace.traceBegin(32768L, "copy2DRangeFrom");
            copy2DRangeFromUnchecked(i, i2, i3, i4, obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, byte[] bArr) {
        validateIsInt8();
        copy2DRangeFromUnchecked(i, i2, i3, i4, bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, short[] sArr) {
        validateIsInt16OrFloat16();
        copy2DRangeFromUnchecked(i, i2, i3, i4, sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, int[] iArr) {
        validateIsInt32();
        copy2DRangeFromUnchecked(i, i2, i3, i4, iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, float[] fArr) {
        validateIsFloat32();
        copy2DRangeFromUnchecked(i, i2, i3, i4, fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, android.renderscript.Allocation allocation, int i5, int i6) {
        try {
            android.os.Trace.traceBegin(32768L, "copy2DRangeFrom");
            this.mRS.validate();
            validate2DRange(i, i2, i3, i4);
            this.mRS.nAllocationData2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, i3, i4, allocation.getID(this.mRS), i5, i6, allocation.mSelectedLOD, allocation.mSelectedFace.mID);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeFrom(int i, int i2, android.graphics.Bitmap bitmap) {
        try {
            android.os.Trace.traceBegin(32768L, "copy2DRangeFrom");
            this.mRS.validate();
            if (bitmap.getConfig() == null) {
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
                new android.graphics.Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (android.graphics.Paint) null);
                copy2DRangeFrom(i, i2, createBitmap);
            } else {
                validateBitmapFormat(bitmap);
                validate2DRange(i, i2, bitmap.getWidth(), bitmap.getHeight());
                this.mRS.nAllocationData2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, bitmap);
            }
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    private void validate3DRange(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mAdaptedAllocation == null) {
            if (i < 0 || i2 < 0 || i3 < 0) {
                throw new android.renderscript.RSIllegalArgumentException("Offset cannot be negative.");
            }
            if (i5 < 0 || i4 < 0 || i6 < 0) {
                throw new android.renderscript.RSIllegalArgumentException("Height or width cannot be negative.");
            }
            if (i + i4 > this.mCurrentDimX || i2 + i5 > this.mCurrentDimY || i3 + i6 > this.mCurrentDimZ) {
                throw new android.renderscript.RSIllegalArgumentException("Updated region larger than allocation.");
            }
        }
    }

    private void copy3DRangeFromUnchecked(int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i7) {
        boolean z;
        try {
            android.os.Trace.traceBegin(32768L, "copy3DRangeFromUnchecked");
            this.mRS.validate();
            validate3DRange(i, i2, i3, i4, i5, i6);
            int bytesSize = this.mType.mElement.getBytesSize() * i4 * i5 * i6;
            int i8 = dataType.mSize * i7;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i8) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = true;
            } else {
                if (bytesSize > i8) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                bytesSize = i8;
            }
            this.mRS.nAllocationData3D(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, i5, i6, obj, bytesSize, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeFrom(int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj) {
        try {
            android.os.Trace.traceBegin(32768L, "copy3DRangeFrom");
            copy3DRangeFromUnchecked(i, i2, i3, i4, i5, i6, obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeFrom(int i, int i2, int i3, int i4, int i5, int i6, android.renderscript.Allocation allocation, int i7, int i8, int i9) {
        this.mRS.validate();
        validate3DRange(i, i2, i3, i4, i5, i6);
        this.mRS.nAllocationData3D(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, i5, i6, allocation.getID(this.mRS), i7, i8, i9, allocation.mSelectedLOD);
    }

    public void copyTo(android.graphics.Bitmap bitmap) {
        try {
            android.os.Trace.traceBegin(32768L, "copyTo");
            this.mRS.validate();
            validateBitmapFormat(bitmap);
            validateBitmapSize(bitmap);
            this.mRS.nAllocationCopyToBitmap(getID(this.mRS), bitmap);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    private void copyTo(java.lang.Object obj, android.renderscript.Element.DataType dataType, int i) {
        boolean z;
        try {
            android.os.Trace.traceBegin(32768L, "copyTo");
            this.mRS.validate();
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                if (dataType.mSize * i < (this.mSize / 4) * 3) {
                    throw new android.renderscript.RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
                }
            } else if (dataType.mSize * i < this.mSize) {
                throw new android.renderscript.RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
            }
            this.mRS.nAllocationRead(getID(this.mRS), obj, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copyTo(java.lang.Object obj) {
        copyTo(obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
    }

    public void copyTo(byte[] bArr) {
        validateIsInt8();
        copyTo(bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyTo(short[] sArr) {
        validateIsInt16OrFloat16();
        copyTo(sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyTo(int[] iArr) {
        validateIsInt32();
        copyTo(iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyTo(float[] fArr) {
        validateIsFloat32();
        copyTo(fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyToFieldPacker(int i, int i2, int i3, int i4, android.renderscript.FieldPacker fieldPacker) {
        this.mRS.validate();
        if (i4 >= this.mType.mElement.mElements.length) {
            throw new android.renderscript.RSIllegalArgumentException("Component_number " + i4 + " out of range.");
        }
        if (i < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset x must be >= 0.");
        }
        if (i2 < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset y must be >= 0.");
        }
        if (i3 < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Offset z must be >= 0.");
        }
        byte[] data = fieldPacker.getData();
        int length = data.length;
        int bytesSize = this.mType.mElement.mElements[i4].getBytesSize() * this.mType.mElement.mArraySizes[i4];
        if (length != bytesSize) {
            throw new android.renderscript.RSIllegalArgumentException("Field packer sizelength " + length + " does not match component size " + bytesSize + android.media.MediaMetrics.SEPARATOR);
        }
        this.mRS.nAllocationElementRead(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, data, length);
    }

    public synchronized void resize(int i) {
        if (this.mRS.getApplicationContext().getApplicationInfo().targetSdkVersion >= 21) {
            throw new android.renderscript.RSRuntimeException("Resize is not allowed in API 21+.");
        }
        if (this.mType.getY() > 0 || this.mType.getZ() > 0 || this.mType.hasFaces() || this.mType.hasMipmaps()) {
            throw new android.renderscript.RSInvalidStateException("Resize only support for 1D allocations at this time.");
        }
        this.mRS.nAllocationResize1D(getID(this.mRS), i);
        this.mRS.finish();
        long nAllocationGetType = this.mRS.nAllocationGetType(getID(this.mRS));
        this.mType.setID(0L);
        this.mType = new android.renderscript.Type(nAllocationGetType, this.mRS);
        this.mType.updateFromNative();
        updateCacheInfo(this.mType);
    }

    private void copy1DRangeToUnchecked(int i, int i2, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i3) {
        boolean z;
        try {
            android.os.Trace.traceBegin(32768L, "copy1DRangeToUnchecked");
            int bytesSize = this.mType.mElement.getBytesSize() * i2;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                z = true;
            } else {
                z = false;
            }
            data1DChecks(i, i2, i3 * dataType.mSize, bytesSize, z);
            this.mRS.nAllocationRead1D(getIDSafe(), i, this.mSelectedLOD, i2, obj, bytesSize, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy1DRangeToUnchecked(int i, int i2, java.lang.Object obj) {
        copy1DRangeToUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, false), java.lang.reflect.Array.getLength(obj));
    }

    public void copy1DRangeToUnchecked(int i, int i2, int[] iArr) {
        copy1DRangeToUnchecked(i, i2, iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, short[] sArr) {
        copy1DRangeToUnchecked(i, i2, sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, byte[] bArr) {
        copy1DRangeToUnchecked(i, i2, bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, float[] fArr) {
        copy1DRangeToUnchecked(i, i2, fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeTo(int i, int i2, java.lang.Object obj) {
        copy1DRangeToUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
    }

    public void copy1DRangeTo(int i, int i2, int[] iArr) {
        validateIsInt32();
        copy1DRangeToUnchecked(i, i2, iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeTo(int i, int i2, short[] sArr) {
        validateIsInt16OrFloat16();
        copy1DRangeToUnchecked(i, i2, sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeTo(int i, int i2, byte[] bArr) {
        validateIsInt8();
        copy1DRangeToUnchecked(i, i2, bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeTo(int i, int i2, float[] fArr) {
        validateIsFloat32();
        copy1DRangeToUnchecked(i, i2, fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    void copy2DRangeToUnchecked(int i, int i2, int i3, int i4, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i5) {
        boolean z;
        int i6;
        try {
            android.os.Trace.traceBegin(32768L, "copy2DRangeToUnchecked");
            this.mRS.validate();
            validate2DRange(i, i2, i3, i4);
            int bytesSize = this.mType.mElement.getBytesSize() * i3 * i4;
            int i7 = dataType.mSize * i5;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i7) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                i6 = bytesSize;
                z = true;
            } else {
                if (bytesSize > i7) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                i6 = i7;
            }
            this.mRS.nAllocationRead2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, i3, i4, obj, i6, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, java.lang.Object obj) {
        copy2DRangeToUnchecked(i, i2, i3, i4, obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, byte[] bArr) {
        validateIsInt8();
        copy2DRangeToUnchecked(i, i2, i3, i4, bArr, android.renderscript.Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, short[] sArr) {
        validateIsInt16OrFloat16();
        copy2DRangeToUnchecked(i, i2, i3, i4, sArr, android.renderscript.Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, int[] iArr) {
        validateIsInt32();
        copy2DRangeToUnchecked(i, i2, i3, i4, iArr, android.renderscript.Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, float[] fArr) {
        validateIsFloat32();
        copy2DRangeToUnchecked(i, i2, i3, i4, fArr, android.renderscript.Element.DataType.FLOAT_32, fArr.length);
    }

    private void copy3DRangeToUnchecked(int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i7) {
        boolean z;
        try {
            android.os.Trace.traceBegin(32768L, "copy3DRangeToUnchecked");
            this.mRS.validate();
            validate3DRange(i, i2, i3, i4, i5, i6);
            int bytesSize = this.mType.mElement.getBytesSize() * i4 * i5 * i6;
            int i8 = dataType.mSize * i7;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i8) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = true;
            } else {
                if (bytesSize > i8) {
                    throw new android.renderscript.RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                bytesSize = i8;
            }
            this.mRS.nAllocationRead3D(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, i5, i6, obj, bytesSize, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeTo(int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj) {
        copy3DRangeToUnchecked(i, i2, i3, i4, i5, i6, obj, validateObjectIsPrimitiveArray(obj, true), java.lang.reflect.Array.getLength(obj));
    }

    public static android.renderscript.Allocation createTyped(android.renderscript.RenderScript renderScript, android.renderscript.Type type, android.renderscript.Allocation.MipmapControl mipmapControl, int i) {
        try {
            android.os.Trace.traceBegin(32768L, "createTyped");
            renderScript.validate();
            if (type.getID(renderScript) == 0) {
                throw new android.renderscript.RSInvalidStateException("Bad Type");
            }
            long nAllocationCreateTyped = renderScript.nAllocationCreateTyped(type.getID(renderScript), mipmapControl.mID, i, 0L);
            if (nAllocationCreateTyped == 0) {
                throw new android.renderscript.RSRuntimeException("Allocation creation failed.");
            }
            return new android.renderscript.Allocation(nAllocationCreateTyped, renderScript, type, false, i, mipmapControl);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public static android.renderscript.Allocation createTyped(android.renderscript.RenderScript renderScript, android.renderscript.Type type, int i) {
        return createTyped(renderScript, type, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, i);
    }

    public static android.renderscript.Allocation createTyped(android.renderscript.RenderScript renderScript, android.renderscript.Type type) {
        return createTyped(renderScript, type, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 1);
    }

    public static android.renderscript.Allocation createSized(android.renderscript.RenderScript renderScript, android.renderscript.Element element, int i, int i2) {
        try {
            android.os.Trace.traceBegin(32768L, "createSized");
            renderScript.validate();
            android.renderscript.Type.Builder builder = new android.renderscript.Type.Builder(renderScript, element);
            builder.setX(i);
            android.renderscript.Type create = builder.create();
            long nAllocationCreateTyped = renderScript.nAllocationCreateTyped(create.getID(renderScript), android.renderscript.Allocation.MipmapControl.MIPMAP_NONE.mID, i2, 0L);
            if (nAllocationCreateTyped == 0) {
                throw new android.renderscript.RSRuntimeException("Allocation creation failed.");
            }
            return new android.renderscript.Allocation(nAllocationCreateTyped, renderScript, create, true, i2, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE);
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public static android.renderscript.Allocation createSized(android.renderscript.RenderScript renderScript, android.renderscript.Element element, int i) {
        return createSized(renderScript, element, i, 1);
    }

    static android.renderscript.Element elementFromBitmap(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap) {
        android.graphics.Bitmap.Config config = bitmap.getConfig();
        if (config == android.graphics.Bitmap.Config.ALPHA_8) {
            return android.renderscript.Element.A_8(renderScript);
        }
        if (config == android.graphics.Bitmap.Config.ARGB_4444) {
            return android.renderscript.Element.RGBA_4444(renderScript);
        }
        if (config == android.graphics.Bitmap.Config.ARGB_8888) {
            return android.renderscript.Element.RGBA_8888(renderScript);
        }
        if (config == android.graphics.Bitmap.Config.RGB_565) {
            return android.renderscript.Element.RGB_565(renderScript);
        }
        throw new android.renderscript.RSInvalidStateException("Bad bitmap type: " + config);
    }

    static android.renderscript.Type typeFromBitmap(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap, android.renderscript.Allocation.MipmapControl mipmapControl) {
        android.renderscript.Type.Builder builder = new android.renderscript.Type.Builder(renderScript, elementFromBitmap(renderScript, bitmap));
        builder.setX(bitmap.getWidth());
        builder.setY(bitmap.getHeight());
        builder.setMipmaps(mipmapControl == android.renderscript.Allocation.MipmapControl.MIPMAP_FULL);
        return builder.create();
    }

    public static android.renderscript.Allocation createFromBitmap(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap, android.renderscript.Allocation.MipmapControl mipmapControl, int i) {
        try {
            android.os.Trace.traceBegin(32768L, "createFromBitmap");
            renderScript.validate();
            if (bitmap.getConfig() == null) {
                if ((i & 128) != 0) {
                    throw new android.renderscript.RSIllegalArgumentException("USAGE_SHARED cannot be used with a Bitmap that has a null config.");
                }
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
                new android.graphics.Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (android.graphics.Paint) null);
                return createFromBitmap(renderScript, createBitmap, mipmapControl, i);
            }
            android.renderscript.Type typeFromBitmap = typeFromBitmap(renderScript, bitmap, mipmapControl);
            if (mipmapControl != android.renderscript.Allocation.MipmapControl.MIPMAP_NONE || !typeFromBitmap.getElement().isCompatible(android.renderscript.Element.RGBA_8888(renderScript)) || i != 131) {
                long nAllocationCreateFromBitmap = renderScript.nAllocationCreateFromBitmap(typeFromBitmap.getID(renderScript), mipmapControl.mID, bitmap, i);
                if (nAllocationCreateFromBitmap != 0) {
                    return new android.renderscript.Allocation(nAllocationCreateFromBitmap, renderScript, typeFromBitmap, true, i, mipmapControl);
                }
                throw new android.renderscript.RSRuntimeException("Load failed.");
            }
            long nAllocationCreateBitmapBackedAllocation = renderScript.nAllocationCreateBitmapBackedAllocation(typeFromBitmap.getID(renderScript), mipmapControl.mID, bitmap, i);
            if (nAllocationCreateBitmapBackedAllocation == 0) {
                throw new android.renderscript.RSRuntimeException("Load failed.");
            }
            android.renderscript.Allocation allocation = new android.renderscript.Allocation(nAllocationCreateBitmapBackedAllocation, renderScript, typeFromBitmap, true, i, mipmapControl);
            allocation.setBitmap(bitmap);
            return allocation;
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    public java.nio.ByteBuffer getByteBuffer() {
        if (this.mType.hasFaces()) {
            throw new android.renderscript.RSInvalidStateException("Cubemap is not supported for getByteBuffer().");
        }
        if (this.mType.getYuv() == 17 || this.mType.getYuv() == 842094169 || this.mType.getYuv() == 35) {
            throw new android.renderscript.RSInvalidStateException("YUV format is not supported for getByteBuffer().");
        }
        if (this.mByteBuffer == null || (this.mUsage & 32) != 0) {
            long[] jArr = new long[1];
            this.mByteBuffer = this.mRS.nAllocationGetByteBuffer(getID(this.mRS), jArr, this.mType.getX() * this.mType.getElement().getBytesSize(), this.mType.getY(), this.mType.getZ());
            this.mByteBufferStride = jArr[0];
        }
        if ((this.mUsage & 32) != 0) {
            return this.mByteBuffer.asReadOnlyBuffer();
        }
        return this.mByteBuffer;
    }

    public static android.renderscript.Allocation[] createAllocations(android.renderscript.RenderScript renderScript, android.renderscript.Type type, int i, int i2) {
        try {
            android.os.Trace.traceBegin(32768L, "createAllocations");
            renderScript.validate();
            if (type.getID(renderScript) == 0) {
                throw new android.renderscript.RSInvalidStateException("Bad Type");
            }
            android.renderscript.Allocation[] allocationArr = new android.renderscript.Allocation[i2];
            allocationArr[0] = createTyped(renderScript, type, i);
            if ((i & 32) != 0) {
                if (i2 > 16) {
                    allocationArr[0].destroy();
                    throw new android.renderscript.RSIllegalArgumentException("Exceeds the max number of Allocations allowed: 16");
                }
                allocationArr[0].setupBufferQueue(i2);
            }
            for (int i3 = 1; i3 < i2; i3++) {
                allocationArr[i3] = createFromAllocation(renderScript, allocationArr[0]);
            }
            return allocationArr;
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    static android.renderscript.Allocation createFromAllocation(android.renderscript.RenderScript renderScript, android.renderscript.Allocation allocation) {
        try {
            android.os.Trace.traceBegin(32768L, "createFromAllcation");
            renderScript.validate();
            if (allocation.getID(renderScript) == 0) {
                throw new android.renderscript.RSInvalidStateException("Bad input Allocation");
            }
            android.renderscript.Type type = allocation.getType();
            int usage = allocation.getUsage();
            android.renderscript.Allocation.MipmapControl mipmap = allocation.getMipmap();
            long nAllocationCreateTyped = renderScript.nAllocationCreateTyped(type.getID(renderScript), mipmap.mID, usage, 0L);
            if (nAllocationCreateTyped == 0) {
                throw new android.renderscript.RSRuntimeException("Allocation creation failed.");
            }
            android.renderscript.Allocation allocation2 = new android.renderscript.Allocation(nAllocationCreateTyped, renderScript, type, false, usage, mipmap);
            if ((usage & 32) != 0) {
                allocation2.shareBufferQueue(allocation);
            }
            return allocation2;
        } finally {
            android.os.Trace.traceEnd(32768L);
        }
    }

    void setupBufferQueue(int i) {
        this.mRS.validate();
        if ((this.mUsage & 32) == 0) {
            throw new android.renderscript.RSInvalidStateException("Allocation is not USAGE_IO_INPUT.");
        }
        this.mRS.nAllocationSetupBufferQueue(getID(this.mRS), i);
    }

    void shareBufferQueue(android.renderscript.Allocation allocation) {
        this.mRS.validate();
        if ((this.mUsage & 32) == 0) {
            throw new android.renderscript.RSInvalidStateException("Allocation is not USAGE_IO_INPUT.");
        }
        this.mGetSurfaceSurface = allocation.getSurface();
        this.mRS.nAllocationShareBufferQueue(getID(this.mRS), allocation.getID(this.mRS));
    }

    public long getStride() {
        if (this.mByteBufferStride == -1) {
            getByteBuffer();
        }
        return this.mByteBufferStride;
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public android.view.Surface getSurface() {
        if ((this.mUsage & 32) == 0) {
            throw new android.renderscript.RSInvalidStateException("Allocation is not a surface texture.");
        }
        if (this.mGetSurfaceSurface == null) {
            this.mGetSurfaceSurface = this.mRS.nAllocationGetSurface(getID(this.mRS));
        }
        return this.mGetSurfaceSurface;
    }

    public void setSurface(android.view.Surface surface) {
        this.mRS.validate();
        if ((this.mUsage & 64) == 0) {
            throw new android.renderscript.RSInvalidStateException("Allocation is not USAGE_IO_OUTPUT.");
        }
        this.mRS.nAllocationSetSurface(getID(this.mRS), surface);
    }

    public static android.renderscript.Allocation createFromBitmap(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap) {
        if (renderScript.getApplicationContext().getApplicationInfo().targetSdkVersion >= 18) {
            return createFromBitmap(renderScript, bitmap, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 131);
        }
        return createFromBitmap(renderScript, bitmap, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 2);
    }

    public static android.renderscript.Allocation createCubemapFromBitmap(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap, android.renderscript.Allocation.MipmapControl mipmapControl, int i) {
        renderScript.validate();
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (width % 6 != 0) {
            throw new android.renderscript.RSIllegalArgumentException("Cubemap height must be multiple of 6");
        }
        if (width / 6 != height) {
            throw new android.renderscript.RSIllegalArgumentException("Only square cube map faces supported");
        }
        if (!(((height + (-1)) & height) == 0)) {
            throw new android.renderscript.RSIllegalArgumentException("Only power of 2 cube faces supported");
        }
        android.renderscript.Element elementFromBitmap = elementFromBitmap(renderScript, bitmap);
        android.renderscript.Type.Builder builder = new android.renderscript.Type.Builder(renderScript, elementFromBitmap);
        builder.setX(height);
        builder.setY(height);
        builder.setFaces(true);
        builder.setMipmaps(mipmapControl == android.renderscript.Allocation.MipmapControl.MIPMAP_FULL);
        android.renderscript.Type create = builder.create();
        long nAllocationCubeCreateFromBitmap = renderScript.nAllocationCubeCreateFromBitmap(create.getID(renderScript), mipmapControl.mID, bitmap, i);
        if (nAllocationCubeCreateFromBitmap == 0) {
            throw new android.renderscript.RSRuntimeException("Load failed for bitmap " + bitmap + " element " + elementFromBitmap);
        }
        return new android.renderscript.Allocation(nAllocationCubeCreateFromBitmap, renderScript, create, true, i, mipmapControl);
    }

    public static android.renderscript.Allocation createCubemapFromBitmap(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap) {
        return createCubemapFromBitmap(renderScript, bitmap, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 2);
    }

    public static android.renderscript.Allocation createCubemapFromCubeFaces(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap, android.graphics.Bitmap bitmap2, android.graphics.Bitmap bitmap3, android.graphics.Bitmap bitmap4, android.graphics.Bitmap bitmap5, android.graphics.Bitmap bitmap6, android.renderscript.Allocation.MipmapControl mipmapControl, int i) {
        int height = bitmap.getHeight();
        if (bitmap.getWidth() != height || bitmap2.getWidth() != height || bitmap2.getHeight() != height || bitmap3.getWidth() != height || bitmap3.getHeight() != height || bitmap4.getWidth() != height || bitmap4.getHeight() != height || bitmap5.getWidth() != height || bitmap5.getHeight() != height || bitmap6.getWidth() != height || bitmap6.getHeight() != height) {
            throw new android.renderscript.RSIllegalArgumentException("Only square cube map faces supported");
        }
        if (!(((height + (-1)) & height) == 0)) {
            throw new android.renderscript.RSIllegalArgumentException("Only power of 2 cube faces supported");
        }
        android.renderscript.Type.Builder builder = new android.renderscript.Type.Builder(renderScript, elementFromBitmap(renderScript, bitmap));
        builder.setX(height);
        builder.setY(height);
        builder.setFaces(true);
        builder.setMipmaps(mipmapControl == android.renderscript.Allocation.MipmapControl.MIPMAP_FULL);
        android.renderscript.Allocation createTyped = createTyped(renderScript, builder.create(), mipmapControl, i);
        android.renderscript.AllocationAdapter create2D = android.renderscript.AllocationAdapter.create2D(renderScript, createTyped);
        create2D.setFace(android.renderscript.Type.CubemapFace.POSITIVE_X);
        create2D.copyFrom(bitmap);
        create2D.setFace(android.renderscript.Type.CubemapFace.NEGATIVE_X);
        create2D.copyFrom(bitmap2);
        create2D.setFace(android.renderscript.Type.CubemapFace.POSITIVE_Y);
        create2D.copyFrom(bitmap3);
        create2D.setFace(android.renderscript.Type.CubemapFace.NEGATIVE_Y);
        create2D.copyFrom(bitmap4);
        create2D.setFace(android.renderscript.Type.CubemapFace.POSITIVE_Z);
        create2D.copyFrom(bitmap5);
        create2D.setFace(android.renderscript.Type.CubemapFace.NEGATIVE_Z);
        create2D.copyFrom(bitmap6);
        return createTyped;
    }

    public static android.renderscript.Allocation createCubemapFromCubeFaces(android.renderscript.RenderScript renderScript, android.graphics.Bitmap bitmap, android.graphics.Bitmap bitmap2, android.graphics.Bitmap bitmap3, android.graphics.Bitmap bitmap4, android.graphics.Bitmap bitmap5, android.graphics.Bitmap bitmap6) {
        return createCubemapFromCubeFaces(renderScript, bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 2);
    }

    public static android.renderscript.Allocation createFromBitmapResource(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, int i, android.renderscript.Allocation.MipmapControl mipmapControl, int i2) {
        renderScript.validate();
        if ((i2 & 224) != 0) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported usage specified.");
        }
        android.graphics.Bitmap decodeResource = android.graphics.BitmapFactory.decodeResource(resources, i);
        android.renderscript.Allocation createFromBitmap = createFromBitmap(renderScript, decodeResource, mipmapControl, i2);
        decodeResource.recycle();
        return createFromBitmap;
    }

    public static android.renderscript.Allocation createFromBitmapResource(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, int i) {
        if (renderScript.getApplicationContext().getApplicationInfo().targetSdkVersion >= 18) {
            return createFromBitmapResource(renderScript, resources, i, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 3);
        }
        return createFromBitmapResource(renderScript, resources, i, android.renderscript.Allocation.MipmapControl.MIPMAP_NONE, 2);
    }

    public static android.renderscript.Allocation createFromString(android.renderscript.RenderScript renderScript, java.lang.String str, int i) {
        renderScript.validate();
        try {
            byte[] bytes = str.getBytes(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
            android.renderscript.Allocation createSized = createSized(renderScript, android.renderscript.Element.U8(renderScript), bytes.length, i);
            createSized.copyFrom(bytes);
            return createSized;
        } catch (java.lang.Exception e) {
            throw new android.renderscript.RSRuntimeException("Could not convert string to utf-8.");
        }
    }

    public void setOnBufferAvailableListener(android.renderscript.Allocation.OnBufferAvailableListener onBufferAvailableListener) {
        synchronized (mAllocationMap) {
            mAllocationMap.put(new java.lang.Long(getID(this.mRS)), this);
            this.mBufferNotifier = onBufferAvailableListener;
        }
    }

    static void sendBufferNotification(long j) {
        synchronized (mAllocationMap) {
            android.renderscript.Allocation allocation = mAllocationMap.get(new java.lang.Long(j));
            if (allocation != null && allocation.mBufferNotifier != null) {
                allocation.mBufferNotifier.onBufferAvailable(allocation);
            }
        }
    }

    @Override // android.renderscript.BaseObj
    public void destroy() {
        if ((this.mUsage & 64) != 0) {
            setSurface(null);
        }
        if (this.mType != null && this.mOwningType) {
            this.mType.destroy();
        }
        super.destroy();
    }
}
