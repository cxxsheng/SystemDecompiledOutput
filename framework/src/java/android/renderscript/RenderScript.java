package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class RenderScript {
    public static final int CREATE_FLAG_LOW_LATENCY = 2;
    public static final int CREATE_FLAG_LOW_POWER = 4;
    public static final int CREATE_FLAG_NONE = 0;
    public static final int CREATE_FLAG_WAIT_FOR_ATTACH = 8;
    static final boolean DEBUG = false;
    static final boolean LOG_ENABLED = false;
    static final java.lang.String LOG_TAG = "RenderScript_jni";
    static final long TRACE_TAG = 32768;
    private static java.lang.String mCachePath = null;
    private static java.util.ArrayList<android.renderscript.RenderScript> mProcessContextList = new java.util.ArrayList<>();
    static java.lang.reflect.Method registerNativeAllocation = null;
    static java.lang.reflect.Method registerNativeFree = null;
    static boolean sInitialized = false;
    static final long sMinorVersion = 1;
    static int sPointerSize;
    static java.lang.Object sRuntime;
    private android.content.Context mApplicationContext;
    long mContext;
    volatile android.renderscript.Element mElement_ALLOCATION;
    volatile android.renderscript.Element mElement_A_8;
    volatile android.renderscript.Element mElement_BOOLEAN;
    volatile android.renderscript.Element mElement_CHAR_2;
    volatile android.renderscript.Element mElement_CHAR_3;
    volatile android.renderscript.Element mElement_CHAR_4;
    volatile android.renderscript.Element mElement_DOUBLE_2;
    volatile android.renderscript.Element mElement_DOUBLE_3;
    volatile android.renderscript.Element mElement_DOUBLE_4;
    volatile android.renderscript.Element mElement_ELEMENT;
    volatile android.renderscript.Element mElement_F16;
    volatile android.renderscript.Element mElement_F32;
    volatile android.renderscript.Element mElement_F64;
    volatile android.renderscript.Element mElement_FLOAT_2;
    volatile android.renderscript.Element mElement_FLOAT_3;
    volatile android.renderscript.Element mElement_FLOAT_4;
    volatile android.renderscript.Element mElement_FONT;
    volatile android.renderscript.Element mElement_HALF_2;
    volatile android.renderscript.Element mElement_HALF_3;
    volatile android.renderscript.Element mElement_HALF_4;
    volatile android.renderscript.Element mElement_I16;
    volatile android.renderscript.Element mElement_I32;
    volatile android.renderscript.Element mElement_I64;
    volatile android.renderscript.Element mElement_I8;
    volatile android.renderscript.Element mElement_INT_2;
    volatile android.renderscript.Element mElement_INT_3;
    volatile android.renderscript.Element mElement_INT_4;
    volatile android.renderscript.Element mElement_LONG_2;
    volatile android.renderscript.Element mElement_LONG_3;
    volatile android.renderscript.Element mElement_LONG_4;
    volatile android.renderscript.Element mElement_MATRIX_2X2;
    volatile android.renderscript.Element mElement_MATRIX_3X3;
    volatile android.renderscript.Element mElement_MATRIX_4X4;
    volatile android.renderscript.Element mElement_MESH;
    volatile android.renderscript.Element mElement_PROGRAM_FRAGMENT;
    volatile android.renderscript.Element mElement_PROGRAM_RASTER;
    volatile android.renderscript.Element mElement_PROGRAM_STORE;
    volatile android.renderscript.Element mElement_PROGRAM_VERTEX;
    volatile android.renderscript.Element mElement_RGBA_4444;
    volatile android.renderscript.Element mElement_RGBA_5551;
    volatile android.renderscript.Element mElement_RGBA_8888;
    volatile android.renderscript.Element mElement_RGB_565;
    volatile android.renderscript.Element mElement_RGB_888;
    volatile android.renderscript.Element mElement_SAMPLER;
    volatile android.renderscript.Element mElement_SCRIPT;
    volatile android.renderscript.Element mElement_SHORT_2;
    volatile android.renderscript.Element mElement_SHORT_3;
    volatile android.renderscript.Element mElement_SHORT_4;
    volatile android.renderscript.Element mElement_TYPE;
    volatile android.renderscript.Element mElement_U16;
    volatile android.renderscript.Element mElement_U32;
    volatile android.renderscript.Element mElement_U64;
    volatile android.renderscript.Element mElement_U8;
    volatile android.renderscript.Element mElement_UCHAR_2;
    volatile android.renderscript.Element mElement_UCHAR_3;
    volatile android.renderscript.Element mElement_UCHAR_4;
    volatile android.renderscript.Element mElement_UINT_2;
    volatile android.renderscript.Element mElement_UINT_3;
    volatile android.renderscript.Element mElement_UINT_4;
    volatile android.renderscript.Element mElement_ULONG_2;
    volatile android.renderscript.Element mElement_ULONG_3;
    volatile android.renderscript.Element mElement_ULONG_4;
    volatile android.renderscript.Element mElement_USHORT_2;
    volatile android.renderscript.Element mElement_USHORT_3;
    volatile android.renderscript.Element mElement_USHORT_4;
    volatile android.renderscript.Element mElement_YUV;
    android.renderscript.RenderScript.MessageThread mMessageThread;
    android.renderscript.ProgramRaster mProgramRaster_CULL_BACK;
    android.renderscript.ProgramRaster mProgramRaster_CULL_FRONT;
    android.renderscript.ProgramRaster mProgramRaster_CULL_NONE;
    android.renderscript.ProgramStore mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH;
    android.renderscript.ProgramStore mProgramStore_BLEND_ALPHA_DEPTH_TEST;
    android.renderscript.ProgramStore mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH;
    android.renderscript.ProgramStore mProgramStore_BLEND_NONE_DEPTH_TEST;
    java.util.concurrent.locks.ReentrantReadWriteLock mRWLock;
    volatile android.renderscript.Sampler mSampler_CLAMP_LINEAR;
    volatile android.renderscript.Sampler mSampler_CLAMP_LINEAR_MIP_LINEAR;
    volatile android.renderscript.Sampler mSampler_CLAMP_NEAREST;
    volatile android.renderscript.Sampler mSampler_MIRRORED_REPEAT_LINEAR;
    volatile android.renderscript.Sampler mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR;
    volatile android.renderscript.Sampler mSampler_MIRRORED_REPEAT_NEAREST;
    volatile android.renderscript.Sampler mSampler_WRAP_LINEAR;
    volatile android.renderscript.Sampler mSampler_WRAP_LINEAR_MIP_LINEAR;
    volatile android.renderscript.Sampler mSampler_WRAP_NEAREST;
    private boolean mIsProcessContext = false;
    private int mContextFlags = 0;
    private int mContextSdkVersion = 0;
    private boolean mDestroyed = false;
    android.renderscript.RenderScript.RSMessageHandler mMessageCallback = null;
    android.renderscript.RenderScript.RSErrorHandler mErrorCallback = null;
    android.renderscript.RenderScript.ContextType mContextType = android.renderscript.RenderScript.ContextType.NORMAL;

    static native void _nInit();

    static native int rsnSystemGetPointerSize();

    native void nContextDeinitToClient(long j);

    native java.lang.String nContextGetErrorMessage(long j);

    native int nContextGetUserMessage(long j, int[] iArr);

    native void nContextInitToClient(long j);

    native int nContextPeekMessage(long j, int[] iArr);

    native long nDeviceCreate();

    native void nDeviceDestroy(long j);

    native void nDeviceSetConfig(long j, int i, int i2);

    native long rsnAllocationAdapterCreate(long j, long j2, long j3);

    native void rsnAllocationAdapterOffset(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    native void rsnAllocationCopyFromBitmap(long j, long j2, android.graphics.Bitmap bitmap);

    native void rsnAllocationCopyToBitmap(long j, long j2, android.graphics.Bitmap bitmap);

    native long rsnAllocationCreateBitmapBackedAllocation(long j, long j2, int i, android.graphics.Bitmap bitmap, int i2);

    native long rsnAllocationCreateFromBitmap(long j, long j2, int i, android.graphics.Bitmap bitmap, int i2);

    native long rsnAllocationCreateTyped(long j, long j2, int i, int i2, long j3);

    native long rsnAllocationCubeCreateFromBitmap(long j, long j2, int i, android.graphics.Bitmap bitmap, int i2);

    native void rsnAllocationData1D(long j, long j2, int i, int i2, int i3, java.lang.Object obj, int i4, int i5, int i6, boolean z);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, long j3, int i7, int i8, int i9, int i10);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj, int i7, int i8, int i9, boolean z);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, android.graphics.Bitmap bitmap);

    native void rsnAllocationData3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j3, int i8, int i9, int i10, int i11);

    native void rsnAllocationData3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.Object obj, int i8, int i9, int i10, boolean z);

    native void rsnAllocationElementData(long j, long j2, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

    native void rsnAllocationElementRead(long j, long j2, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

    native void rsnAllocationGenerateMipmaps(long j, long j2);

    native java.nio.ByteBuffer rsnAllocationGetByteBuffer(long j, long j2, long[] jArr, int i, int i2, int i3);

    native android.view.Surface rsnAllocationGetSurface(long j, long j2);

    native long rsnAllocationGetType(long j, long j2);

    native long rsnAllocationIoReceive(long j, long j2);

    native void rsnAllocationIoSend(long j, long j2);

    native void rsnAllocationRead(long j, long j2, java.lang.Object obj, int i, int i2, boolean z);

    native void rsnAllocationRead1D(long j, long j2, int i, int i2, int i3, java.lang.Object obj, int i4, int i5, int i6, boolean z);

    native void rsnAllocationRead2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj, int i7, int i8, int i9, boolean z);

    native void rsnAllocationRead3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.Object obj, int i8, int i9, int i10, boolean z);

    native void rsnAllocationResize1D(long j, long j2, int i);

    native void rsnAllocationSetSurface(long j, long j2, android.view.Surface surface);

    native void rsnAllocationSetupBufferQueue(long j, long j2, int i);

    native void rsnAllocationShareBufferQueue(long j, long j2, long j3);

    native void rsnAllocationSyncAll(long j, long j2, int i);

    native void rsnAssignName(long j, long j2, byte[] bArr);

    native long rsnClosureCreate(long j, long j2, long j3, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, long[] jArr4);

    native void rsnClosureSetArg(long j, long j2, int i, long j3, int i2);

    native void rsnClosureSetGlobal(long j, long j2, long j3, long j4, int i);

    native void rsnContextBindProgramFragment(long j, long j2);

    native void rsnContextBindProgramRaster(long j, long j2);

    native void rsnContextBindProgramStore(long j, long j2);

    native void rsnContextBindProgramVertex(long j, long j2);

    native void rsnContextBindRootScript(long j, long j2);

    native void rsnContextBindSampler(long j, int i, int i2);

    native long rsnContextCreate(long j, int i, int i2, int i3);

    native long rsnContextCreateGL(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f, int i13);

    native void rsnContextDestroy(long j);

    native void rsnContextDump(long j, int i);

    native void rsnContextFinish(long j);

    native void rsnContextPause(long j);

    native void rsnContextResume(long j);

    native void rsnContextSendMessage(long j, int i, int[] iArr);

    native void rsnContextSetCacheDir(long j, java.lang.String str);

    native void rsnContextSetPriority(long j, int i);

    native void rsnContextSetSurface(long j, int i, int i2, android.view.Surface surface);

    native void rsnContextSetSurfaceTexture(long j, int i, int i2, android.graphics.SurfaceTexture surfaceTexture);

    native long rsnElementCreate(long j, long j2, int i, boolean z, int i2);

    native long rsnElementCreate2(long j, long[] jArr, java.lang.String[] strArr, int[] iArr);

    native void rsnElementGetNativeData(long j, long j2, int[] iArr);

    native void rsnElementGetSubElements(long j, long j2, long[] jArr, java.lang.String[] strArr, int[] iArr);

    native long rsnFileA3DCreateFromAsset(long j, android.content.res.AssetManager assetManager, java.lang.String str);

    native long rsnFileA3DCreateFromAssetStream(long j, long j2);

    native long rsnFileA3DCreateFromFile(long j, java.lang.String str);

    native long rsnFileA3DGetEntryByIndex(long j, long j2, int i);

    native void rsnFileA3DGetIndexEntries(long j, long j2, int i, int[] iArr, java.lang.String[] strArr);

    native int rsnFileA3DGetNumIndexEntries(long j, long j2);

    native long rsnFontCreateFromAsset(long j, android.content.res.AssetManager assetManager, java.lang.String str, float f, int i);

    native long rsnFontCreateFromAssetStream(long j, java.lang.String str, float f, int i, long j2);

    native long rsnFontCreateFromFile(long j, java.lang.String str, float f, int i);

    native java.lang.String rsnGetName(long j, long j2);

    native long rsnInvokeClosureCreate(long j, long j2, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr);

    native long rsnMeshCreate(long j, long[] jArr, long[] jArr2, int[] iArr);

    native int rsnMeshGetIndexCount(long j, long j2);

    native void rsnMeshGetIndices(long j, long j2, long[] jArr, int[] iArr, int i);

    native int rsnMeshGetVertexBufferCount(long j, long j2);

    native void rsnMeshGetVertices(long j, long j2, long[] jArr, int i);

    native void rsnObjDestroy(long j, long j2);

    native void rsnProgramBindConstants(long j, long j2, int i, long j3);

    native void rsnProgramBindSampler(long j, long j2, int i, long j3);

    native void rsnProgramBindTexture(long j, long j2, int i, long j3);

    native long rsnProgramFragmentCreate(long j, java.lang.String str, java.lang.String[] strArr, long[] jArr);

    native long rsnProgramRasterCreate(long j, boolean z, int i);

    native long rsnProgramStoreCreate(long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, int i2, int i3);

    native long rsnProgramVertexCreate(long j, java.lang.String str, java.lang.String[] strArr, long[] jArr);

    native long rsnSamplerCreate(long j, int i, int i2, int i3, int i4, int i5, float f);

    native void rsnScriptBindAllocation(long j, long j2, long j3, int i);

    native long rsnScriptCCreate(long j, java.lang.String str, java.lang.String str2, byte[] bArr, int i);

    native long rsnScriptFieldIDCreate(long j, long j2, int i);

    native void rsnScriptForEach(long j, long j2, int i, long[] jArr, long j3, byte[] bArr, int[] iArr);

    native double rsnScriptGetVarD(long j, long j2, int i);

    native float rsnScriptGetVarF(long j, long j2, int i);

    native int rsnScriptGetVarI(long j, long j2, int i);

    native long rsnScriptGetVarJ(long j, long j2, int i);

    native void rsnScriptGetVarV(long j, long j2, int i, byte[] bArr);

    native long rsnScriptGroup2Create(long j, java.lang.String str, java.lang.String str2, long[] jArr);

    native void rsnScriptGroup2Execute(long j, long j2);

    native long rsnScriptGroupCreate(long j, long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4, long[] jArr5);

    native void rsnScriptGroupExecute(long j, long j2);

    native void rsnScriptGroupSetInput(long j, long j2, long j3, long j4);

    native void rsnScriptGroupSetOutput(long j, long j2, long j3, long j4);

    native void rsnScriptIntrinsicBLAS_BNNM(long j, long j2, int i, int i2, int i3, long j3, int i4, long j4, int i5, long j5, int i6, int i7);

    native void rsnScriptIntrinsicBLAS_Complex(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, long j3, long j4, float f3, float f4, long j5, int i10, int i11, int i12, int i13);

    native void rsnScriptIntrinsicBLAS_Double(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, long j3, long j4, double d2, long j5, int i10, int i11, int i12, int i13);

    native void rsnScriptIntrinsicBLAS_Single(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, long j3, long j4, float f2, long j5, int i10, int i11, int i12, int i13);

    native void rsnScriptIntrinsicBLAS_Z(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, double d2, long j3, long j4, double d3, double d4, long j5, int i10, int i11, int i12, int i13);

    native long rsnScriptIntrinsicCreate(long j, int i, long j2);

    native void rsnScriptInvoke(long j, long j2, int i);

    native long rsnScriptInvokeIDCreate(long j, long j2, int i);

    native void rsnScriptInvokeV(long j, long j2, int i, byte[] bArr);

    native long rsnScriptKernelIDCreate(long j, long j2, int i, int i2);

    native void rsnScriptReduce(long j, long j2, int i, long[] jArr, long j3, int[] iArr);

    native void rsnScriptSetTimeZone(long j, long j2, byte[] bArr);

    native void rsnScriptSetVarD(long j, long j2, int i, double d);

    native void rsnScriptSetVarF(long j, long j2, int i, float f);

    native void rsnScriptSetVarI(long j, long j2, int i, int i2);

    native void rsnScriptSetVarJ(long j, long j2, int i, long j3);

    native void rsnScriptSetVarObj(long j, long j2, int i, long j3);

    native void rsnScriptSetVarV(long j, long j2, int i, byte[] bArr);

    native void rsnScriptSetVarVE(long j, long j2, int i, byte[] bArr, long j3, int[] iArr);

    native long rsnTypeCreate(long j, long j2, int i, int i2, int i3, boolean z, boolean z2, int i4);

    native void rsnTypeGetNativeData(long j, long j2, long[] jArr);

    static {
        sInitialized = false;
        if (!android.os.SystemProperties.getBoolean("config.disable_renderscript", false)) {
            try {
                java.lang.Class<?> cls = java.lang.Class.forName("dalvik.system.VMRuntime");
                sRuntime = cls.getDeclaredMethod("getRuntime", new java.lang.Class[0]).invoke(null, new java.lang.Object[0]);
                registerNativeAllocation = cls.getDeclaredMethod("registerNativeAllocation", java.lang.Long.TYPE);
                registerNativeFree = cls.getDeclaredMethod("registerNativeFree", java.lang.Long.TYPE);
                try {
                    java.lang.System.loadLibrary("rs_jni");
                    _nInit();
                    sInitialized = true;
                    sPointerSize = rsnSystemGetPointerSize();
                } catch (java.lang.UnsatisfiedLinkError e) {
                    android.util.Log.e(LOG_TAG, "Error loading RS jni library: " + e);
                    throw new android.renderscript.RSRuntimeException("Error loading RS jni library: " + e);
                }
            } catch (java.lang.Exception e2) {
                android.util.Log.e(LOG_TAG, "Error loading GC methods: " + e2);
                throw new android.renderscript.RSRuntimeException("Error loading GC methods: " + e2);
            }
        }
    }

    public static long getMinorID() {
        return 1L;
    }

    public static long getMinorVersion() {
        return 1L;
    }

    public enum ContextType {
        NORMAL(0),
        DEBUG(1),
        PROFILE(2);

        int mID;

        ContextType(int i) {
            this.mID = i;
        }
    }

    synchronized long nContextCreateGL(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f, int i13) {
        return rsnContextCreateGL(j, i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, f, i13);
    }

    synchronized long nContextCreate(long j, int i, int i2, int i3) {
        return rsnContextCreate(j, i, i2, i3);
    }

    synchronized void nContextDestroy() {
        validate();
        java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock writeLock = this.mRWLock.writeLock();
        writeLock.lock();
        long j = this.mContext;
        this.mContext = 0L;
        writeLock.unlock();
        rsnContextDestroy(j);
    }

    synchronized void nContextSetSurface(int i, int i2, android.view.Surface surface) {
        validate();
        rsnContextSetSurface(this.mContext, i, i2, surface);
    }

    synchronized void nContextSetSurfaceTexture(int i, int i2, android.graphics.SurfaceTexture surfaceTexture) {
        validate();
        rsnContextSetSurfaceTexture(this.mContext, i, i2, surfaceTexture);
    }

    synchronized void nContextSetPriority(int i) {
        validate();
        rsnContextSetPriority(this.mContext, i);
    }

    synchronized void nContextSetCacheDir(java.lang.String str) {
        validate();
        rsnContextSetCacheDir(this.mContext, str);
    }

    synchronized void nContextDump(int i) {
        validate();
        rsnContextDump(this.mContext, i);
    }

    synchronized void nContextFinish() {
        validate();
        rsnContextFinish(this.mContext);
    }

    synchronized void nContextSendMessage(int i, int[] iArr) {
        validate();
        rsnContextSendMessage(this.mContext, i, iArr);
    }

    synchronized void nContextBindRootScript(long j) {
        validate();
        rsnContextBindRootScript(this.mContext, j);
    }

    synchronized void nContextBindSampler(int i, int i2) {
        validate();
        rsnContextBindSampler(this.mContext, i, i2);
    }

    synchronized void nContextBindProgramStore(long j) {
        validate();
        rsnContextBindProgramStore(this.mContext, j);
    }

    synchronized void nContextBindProgramFragment(long j) {
        validate();
        rsnContextBindProgramFragment(this.mContext, j);
    }

    synchronized void nContextBindProgramVertex(long j) {
        validate();
        rsnContextBindProgramVertex(this.mContext, j);
    }

    synchronized void nContextBindProgramRaster(long j) {
        validate();
        rsnContextBindProgramRaster(this.mContext, j);
    }

    synchronized void nContextPause() {
        validate();
        rsnContextPause(this.mContext);
    }

    synchronized void nContextResume() {
        validate();
        rsnContextResume(this.mContext);
    }

    synchronized long nClosureCreate(long j, long j2, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, long[] jArr4) {
        long rsnClosureCreate;
        validate();
        rsnClosureCreate = rsnClosureCreate(this.mContext, j, j2, jArr, jArr2, iArr, jArr3, jArr4);
        if (rsnClosureCreate == 0) {
            throw new android.renderscript.RSRuntimeException("Failed creating closure.");
        }
        return rsnClosureCreate;
    }

    synchronized long nInvokeClosureCreate(long j, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr) {
        long rsnInvokeClosureCreate;
        validate();
        rsnInvokeClosureCreate = rsnInvokeClosureCreate(this.mContext, j, bArr, jArr, jArr2, iArr);
        if (rsnInvokeClosureCreate == 0) {
            throw new android.renderscript.RSRuntimeException("Failed creating closure.");
        }
        return rsnInvokeClosureCreate;
    }

    synchronized void nClosureSetArg(long j, int i, long j2, int i2) {
        validate();
        rsnClosureSetArg(this.mContext, j, i, j2, i2);
    }

    synchronized void nClosureSetGlobal(long j, long j2, long j3, int i) {
        validate();
        rsnClosureSetGlobal(this.mContext, j, j2, j3, i);
    }

    synchronized long nScriptGroup2Create(java.lang.String str, java.lang.String str2, long[] jArr) {
        long rsnScriptGroup2Create;
        validate();
        rsnScriptGroup2Create = rsnScriptGroup2Create(this.mContext, str, str2, jArr);
        if (rsnScriptGroup2Create == 0) {
            throw new android.renderscript.RSRuntimeException("Failed creating script group.");
        }
        return rsnScriptGroup2Create;
    }

    synchronized void nScriptGroup2Execute(long j) {
        validate();
        rsnScriptGroup2Execute(this.mContext, j);
    }

    synchronized void nAssignName(long j, byte[] bArr) {
        validate();
        rsnAssignName(this.mContext, j, bArr);
    }

    synchronized java.lang.String nGetName(long j) {
        validate();
        return rsnGetName(this.mContext, j);
    }

    void nObjDestroy(long j) {
        if (this.mContext != 0) {
            rsnObjDestroy(this.mContext, j);
        }
    }

    synchronized long nElementCreate(long j, int i, boolean z, int i2) {
        validate();
        return rsnElementCreate(this.mContext, j, i, z, i2);
    }

    synchronized long nElementCreate2(long[] jArr, java.lang.String[] strArr, int[] iArr) {
        validate();
        return rsnElementCreate2(this.mContext, jArr, strArr, iArr);
    }

    synchronized void nElementGetNativeData(long j, int[] iArr) {
        validate();
        rsnElementGetNativeData(this.mContext, j, iArr);
    }

    synchronized void nElementGetSubElements(long j, long[] jArr, java.lang.String[] strArr, int[] iArr) {
        validate();
        rsnElementGetSubElements(this.mContext, j, jArr, strArr, iArr);
    }

    synchronized long nTypeCreate(long j, int i, int i2, int i3, boolean z, boolean z2, int i4) {
        validate();
        return rsnTypeCreate(this.mContext, j, i, i2, i3, z, z2, i4);
    }

    synchronized void nTypeGetNativeData(long j, long[] jArr) {
        validate();
        rsnTypeGetNativeData(this.mContext, j, jArr);
    }

    synchronized long nAllocationCreateTyped(long j, int i, int i2, long j2) {
        validate();
        return rsnAllocationCreateTyped(this.mContext, j, i, i2, j2);
    }

    synchronized long nAllocationCreateFromBitmap(long j, int i, android.graphics.Bitmap bitmap, int i2) {
        validate();
        return rsnAllocationCreateFromBitmap(this.mContext, j, i, bitmap, i2);
    }

    synchronized long nAllocationCreateBitmapBackedAllocation(long j, int i, android.graphics.Bitmap bitmap, int i2) {
        validate();
        return rsnAllocationCreateBitmapBackedAllocation(this.mContext, j, i, bitmap, i2);
    }

    synchronized long nAllocationCubeCreateFromBitmap(long j, int i, android.graphics.Bitmap bitmap, int i2) {
        validate();
        return rsnAllocationCubeCreateFromBitmap(this.mContext, j, i, bitmap, i2);
    }

    synchronized void nAllocationCopyToBitmap(long j, android.graphics.Bitmap bitmap) {
        validate();
        rsnAllocationCopyToBitmap(this.mContext, j, bitmap);
    }

    synchronized void nAllocationSyncAll(long j, int i) {
        validate();
        rsnAllocationSyncAll(this.mContext, j, i);
    }

    synchronized java.nio.ByteBuffer nAllocationGetByteBuffer(long j, long[] jArr, int i, int i2, int i3) {
        validate();
        return rsnAllocationGetByteBuffer(this.mContext, j, jArr, i, i2, i3);
    }

    synchronized void nAllocationSetupBufferQueue(long j, int i) {
        validate();
        rsnAllocationSetupBufferQueue(this.mContext, j, i);
    }

    synchronized void nAllocationShareBufferQueue(long j, long j2) {
        validate();
        rsnAllocationShareBufferQueue(this.mContext, j, j2);
    }

    synchronized android.view.Surface nAllocationGetSurface(long j) {
        validate();
        return rsnAllocationGetSurface(this.mContext, j);
    }

    synchronized void nAllocationSetSurface(long j, android.view.Surface surface) {
        validate();
        rsnAllocationSetSurface(this.mContext, j, surface);
    }

    synchronized void nAllocationIoSend(long j) {
        validate();
        rsnAllocationIoSend(this.mContext, j);
    }

    synchronized long nAllocationIoReceive(long j) {
        validate();
        return rsnAllocationIoReceive(this.mContext, j);
    }

    synchronized void nAllocationGenerateMipmaps(long j) {
        validate();
        rsnAllocationGenerateMipmaps(this.mContext, j);
    }

    synchronized void nAllocationCopyFromBitmap(long j, android.graphics.Bitmap bitmap) {
        validate();
        rsnAllocationCopyFromBitmap(this.mContext, j, bitmap);
    }

    synchronized void nAllocationData1D(long j, int i, int i2, int i3, java.lang.Object obj, int i4, android.renderscript.Element.DataType dataType, int i5, boolean z) {
        validate();
        rsnAllocationData1D(this.mContext, j, i, i2, i3, obj, i4, dataType.mID, i5, z);
    }

    synchronized void nAllocationElementData(long j, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6) {
        validate();
        rsnAllocationElementData(this.mContext, j, i, i2, i3, i4, i5, bArr, i6);
    }

    synchronized void nAllocationData2D(long j, int i, int i2, int i3, int i4, int i5, int i6, long j2, int i7, int i8, int i9, int i10) {
        validate();
        rsnAllocationData2D(this.mContext, j, i, i2, i3, i4, i5, i6, j2, i7, i8, i9, i10);
    }

    synchronized void nAllocationData2D(long j, int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj, int i7, android.renderscript.Element.DataType dataType, int i8, boolean z) {
        validate();
        rsnAllocationData2D(this.mContext, j, i, i2, i3, i4, i5, i6, obj, i7, dataType.mID, i8, z);
    }

    synchronized void nAllocationData2D(long j, int i, int i2, int i3, int i4, android.graphics.Bitmap bitmap) {
        validate();
        rsnAllocationData2D(this.mContext, j, i, i2, i3, i4, bitmap);
    }

    synchronized void nAllocationData3D(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8, int i9, int i10, int i11) {
        validate();
        rsnAllocationData3D(this.mContext, j, i, i2, i3, i4, i5, i6, i7, j2, i8, i9, i10, i11);
    }

    synchronized void nAllocationData3D(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.Object obj, int i8, android.renderscript.Element.DataType dataType, int i9, boolean z) {
        validate();
        rsnAllocationData3D(this.mContext, j, i, i2, i3, i4, i5, i6, i7, obj, i8, dataType.mID, i9, z);
    }

    synchronized void nAllocationRead(long j, java.lang.Object obj, android.renderscript.Element.DataType dataType, int i, boolean z) {
        validate();
        rsnAllocationRead(this.mContext, j, obj, dataType.mID, i, z);
    }

    synchronized void nAllocationRead1D(long j, int i, int i2, int i3, java.lang.Object obj, int i4, android.renderscript.Element.DataType dataType, int i5, boolean z) {
        validate();
        rsnAllocationRead1D(this.mContext, j, i, i2, i3, obj, i4, dataType.mID, i5, z);
    }

    synchronized void nAllocationElementRead(long j, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6) {
        validate();
        rsnAllocationElementRead(this.mContext, j, i, i2, i3, i4, i5, bArr, i6);
    }

    synchronized void nAllocationRead2D(long j, int i, int i2, int i3, int i4, int i5, int i6, java.lang.Object obj, int i7, android.renderscript.Element.DataType dataType, int i8, boolean z) {
        validate();
        rsnAllocationRead2D(this.mContext, j, i, i2, i3, i4, i5, i6, obj, i7, dataType.mID, i8, z);
    }

    synchronized void nAllocationRead3D(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.Object obj, int i8, android.renderscript.Element.DataType dataType, int i9, boolean z) {
        validate();
        rsnAllocationRead3D(this.mContext, j, i, i2, i3, i4, i5, i6, i7, obj, i8, dataType.mID, i9, z);
    }

    synchronized long nAllocationGetType(long j) {
        validate();
        return rsnAllocationGetType(this.mContext, j);
    }

    synchronized void nAllocationResize1D(long j, int i) {
        validate();
        rsnAllocationResize1D(this.mContext, j, i);
    }

    synchronized long nAllocationAdapterCreate(long j, long j2) {
        validate();
        return rsnAllocationAdapterCreate(this.mContext, j, j2);
    }

    synchronized void nAllocationAdapterOffset(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        validate();
        rsnAllocationAdapterOffset(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9);
    }

    synchronized long nFileA3DCreateFromAssetStream(long j) {
        validate();
        return rsnFileA3DCreateFromAssetStream(this.mContext, j);
    }

    synchronized long nFileA3DCreateFromFile(java.lang.String str) {
        validate();
        return rsnFileA3DCreateFromFile(this.mContext, str);
    }

    synchronized long nFileA3DCreateFromAsset(android.content.res.AssetManager assetManager, java.lang.String str) {
        validate();
        return rsnFileA3DCreateFromAsset(this.mContext, assetManager, str);
    }

    synchronized int nFileA3DGetNumIndexEntries(long j) {
        validate();
        return rsnFileA3DGetNumIndexEntries(this.mContext, j);
    }

    synchronized void nFileA3DGetIndexEntries(long j, int i, int[] iArr, java.lang.String[] strArr) {
        validate();
        rsnFileA3DGetIndexEntries(this.mContext, j, i, iArr, strArr);
    }

    synchronized long nFileA3DGetEntryByIndex(long j, int i) {
        validate();
        return rsnFileA3DGetEntryByIndex(this.mContext, j, i);
    }

    synchronized long nFontCreateFromFile(java.lang.String str, float f, int i) {
        validate();
        return rsnFontCreateFromFile(this.mContext, str, f, i);
    }

    synchronized long nFontCreateFromAssetStream(java.lang.String str, float f, int i, long j) {
        validate();
        return rsnFontCreateFromAssetStream(this.mContext, str, f, i, j);
    }

    synchronized long nFontCreateFromAsset(android.content.res.AssetManager assetManager, java.lang.String str, float f, int i) {
        validate();
        return rsnFontCreateFromAsset(this.mContext, assetManager, str, f, i);
    }

    synchronized void nScriptBindAllocation(long j, long j2, int i) {
        validate();
        rsnScriptBindAllocation(this.mContext, j, j2, i);
    }

    synchronized void nScriptSetTimeZone(long j, byte[] bArr) {
        validate();
        rsnScriptSetTimeZone(this.mContext, j, bArr);
    }

    synchronized void nScriptInvoke(long j, int i) {
        validate();
        rsnScriptInvoke(this.mContext, j, i);
    }

    synchronized void nScriptForEach(long j, int i, long[] jArr, long j2, byte[] bArr, int[] iArr) {
        validate();
        rsnScriptForEach(this.mContext, j, i, jArr, j2, bArr, iArr);
    }

    synchronized void nScriptReduce(long j, int i, long[] jArr, long j2, int[] iArr) {
        validate();
        rsnScriptReduce(this.mContext, j, i, jArr, j2, iArr);
    }

    synchronized void nScriptInvokeV(long j, int i, byte[] bArr) {
        validate();
        rsnScriptInvokeV(this.mContext, j, i, bArr);
    }

    synchronized void nScriptSetVarI(long j, int i, int i2) {
        validate();
        rsnScriptSetVarI(this.mContext, j, i, i2);
    }

    synchronized int nScriptGetVarI(long j, int i) {
        validate();
        return rsnScriptGetVarI(this.mContext, j, i);
    }

    synchronized void nScriptSetVarJ(long j, int i, long j2) {
        validate();
        rsnScriptSetVarJ(this.mContext, j, i, j2);
    }

    synchronized long nScriptGetVarJ(long j, int i) {
        validate();
        return rsnScriptGetVarJ(this.mContext, j, i);
    }

    synchronized void nScriptSetVarF(long j, int i, float f) {
        validate();
        rsnScriptSetVarF(this.mContext, j, i, f);
    }

    synchronized float nScriptGetVarF(long j, int i) {
        validate();
        return rsnScriptGetVarF(this.mContext, j, i);
    }

    synchronized void nScriptSetVarD(long j, int i, double d) {
        validate();
        rsnScriptSetVarD(this.mContext, j, i, d);
    }

    synchronized double nScriptGetVarD(long j, int i) {
        validate();
        return rsnScriptGetVarD(this.mContext, j, i);
    }

    synchronized void nScriptSetVarV(long j, int i, byte[] bArr) {
        validate();
        rsnScriptSetVarV(this.mContext, j, i, bArr);
    }

    synchronized void nScriptGetVarV(long j, int i, byte[] bArr) {
        validate();
        rsnScriptGetVarV(this.mContext, j, i, bArr);
    }

    synchronized void nScriptSetVarVE(long j, int i, byte[] bArr, long j2, int[] iArr) {
        validate();
        rsnScriptSetVarVE(this.mContext, j, i, bArr, j2, iArr);
    }

    synchronized void nScriptSetVarObj(long j, int i, long j2) {
        validate();
        rsnScriptSetVarObj(this.mContext, j, i, j2);
    }

    synchronized long nScriptCCreate(java.lang.String str, java.lang.String str2, byte[] bArr, int i) {
        validate();
        return rsnScriptCCreate(this.mContext, str, str2, bArr, i);
    }

    synchronized long nScriptIntrinsicCreate(int i, long j) {
        validate();
        return rsnScriptIntrinsicCreate(this.mContext, i, j);
    }

    synchronized long nScriptKernelIDCreate(long j, int i, int i2) {
        validate();
        return rsnScriptKernelIDCreate(this.mContext, j, i, i2);
    }

    synchronized long nScriptInvokeIDCreate(long j, int i) {
        validate();
        return rsnScriptInvokeIDCreate(this.mContext, j, i);
    }

    synchronized long nScriptFieldIDCreate(long j, int i) {
        validate();
        return rsnScriptFieldIDCreate(this.mContext, j, i);
    }

    synchronized long nScriptGroupCreate(long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4, long[] jArr5) {
        validate();
        return rsnScriptGroupCreate(this.mContext, jArr, jArr2, jArr3, jArr4, jArr5);
    }

    synchronized void nScriptGroupSetInput(long j, long j2, long j3) {
        validate();
        rsnScriptGroupSetInput(this.mContext, j, j2, j3);
    }

    synchronized void nScriptGroupSetOutput(long j, long j2, long j3) {
        validate();
        rsnScriptGroupSetOutput(this.mContext, j, j2, j3);
    }

    synchronized void nScriptGroupExecute(long j) {
        validate();
        rsnScriptGroupExecute(this.mContext, j);
    }

    synchronized long nSamplerCreate(int i, int i2, int i3, int i4, int i5, float f) {
        validate();
        return rsnSamplerCreate(this.mContext, i, i2, i3, i4, i5, f);
    }

    synchronized long nProgramStoreCreate(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, int i2, int i3) {
        validate();
        return rsnProgramStoreCreate(this.mContext, z, z2, z3, z4, z5, z6, i, i2, i3);
    }

    synchronized long nProgramRasterCreate(boolean z, int i) {
        validate();
        return rsnProgramRasterCreate(this.mContext, z, i);
    }

    synchronized void nProgramBindConstants(long j, int i, long j2) {
        validate();
        rsnProgramBindConstants(this.mContext, j, i, j2);
    }

    synchronized void nProgramBindTexture(long j, int i, long j2) {
        validate();
        rsnProgramBindTexture(this.mContext, j, i, j2);
    }

    synchronized void nProgramBindSampler(long j, int i, long j2) {
        validate();
        rsnProgramBindSampler(this.mContext, j, i, j2);
    }

    synchronized long nProgramFragmentCreate(java.lang.String str, java.lang.String[] strArr, long[] jArr) {
        validate();
        return rsnProgramFragmentCreate(this.mContext, str, strArr, jArr);
    }

    synchronized long nProgramVertexCreate(java.lang.String str, java.lang.String[] strArr, long[] jArr) {
        validate();
        return rsnProgramVertexCreate(this.mContext, str, strArr, jArr);
    }

    synchronized long nMeshCreate(long[] jArr, long[] jArr2, int[] iArr) {
        validate();
        return rsnMeshCreate(this.mContext, jArr, jArr2, iArr);
    }

    synchronized int nMeshGetVertexBufferCount(long j) {
        validate();
        return rsnMeshGetVertexBufferCount(this.mContext, j);
    }

    synchronized int nMeshGetIndexCount(long j) {
        validate();
        return rsnMeshGetIndexCount(this.mContext, j);
    }

    synchronized void nMeshGetVertices(long j, long[] jArr, int i) {
        validate();
        rsnMeshGetVertices(this.mContext, j, jArr, i);
    }

    synchronized void nMeshGetIndices(long j, long[] jArr, int[] iArr, int i) {
        validate();
        rsnMeshGetIndices(this.mContext, j, jArr, iArr, i);
    }

    synchronized void nScriptIntrinsicBLAS_Single(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, long j2, long j3, float f2, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Single(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, f, j2, j3, f2, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_Double(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, long j2, long j3, double d2, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Double(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, d, j2, j3, d2, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_Complex(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, long j2, long j3, float f3, float f4, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Complex(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, f, f2, j2, j3, f3, f4, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_Z(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, double d2, long j2, long j3, double d3, double d4, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Z(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, d, d2, j2, j3, d3, d4, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_BNNM(long j, int i, int i2, int i3, long j2, int i4, long j3, int i5, long j4, int i6, int i7) {
        validate();
        rsnScriptIntrinsicBLAS_BNNM(this.mContext, j, i, i2, i3, j2, i4, j3, i5, j4, i6, i7);
    }

    public static class RSMessageHandler implements java.lang.Runnable {
        protected int[] mData;
        protected int mID;
        protected int mLength;

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    public void setMessageHandler(android.renderscript.RenderScript.RSMessageHandler rSMessageHandler) {
        this.mMessageCallback = rSMessageHandler;
    }

    public android.renderscript.RenderScript.RSMessageHandler getMessageHandler() {
        return this.mMessageCallback;
    }

    public void sendMessage(int i, int[] iArr) {
        nContextSendMessage(i, iArr);
    }

    public static class RSErrorHandler implements java.lang.Runnable {
        protected java.lang.String mErrorMessage;
        protected int mErrorNum;

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    public void setErrorHandler(android.renderscript.RenderScript.RSErrorHandler rSErrorHandler) {
        this.mErrorCallback = rSErrorHandler;
    }

    public android.renderscript.RenderScript.RSErrorHandler getErrorHandler() {
        return this.mErrorCallback;
    }

    public enum Priority {
        LOW(15),
        NORMAL(-8);

        int mID;

        Priority(int i) {
            this.mID = i;
        }
    }

    void validateObject(android.renderscript.BaseObj baseObj) {
        if (baseObj != null && baseObj.mRS != this) {
            throw new android.renderscript.RSIllegalArgumentException("Attempting to use an object across contexts.");
        }
    }

    void validate() {
        if (this.mContext == 0) {
            throw new android.renderscript.RSInvalidStateException("Calling RS with no Context active.");
        }
    }

    public void setPriority(android.renderscript.RenderScript.Priority priority) {
        validate();
        nContextSetPriority(priority.mID);
    }

    static class MessageThread extends java.lang.Thread {
        static final int RS_ERROR_FATAL_DEBUG = 2048;
        static final int RS_ERROR_FATAL_UNKNOWN = 4096;
        static final int RS_MESSAGE_TO_CLIENT_ERROR = 3;
        static final int RS_MESSAGE_TO_CLIENT_EXCEPTION = 1;
        static final int RS_MESSAGE_TO_CLIENT_NEW_BUFFER = 5;
        static final int RS_MESSAGE_TO_CLIENT_NONE = 0;
        static final int RS_MESSAGE_TO_CLIENT_RESIZE = 2;
        static final int RS_MESSAGE_TO_CLIENT_USER = 4;
        int[] mAuxData;
        android.renderscript.RenderScript mRS;
        boolean mRun;

        MessageThread(android.renderscript.RenderScript renderScript) {
            super("RSMessageThread");
            this.mRun = true;
            this.mAuxData = new int[2];
            this.mRS = renderScript;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int[] iArr = new int[16];
            this.mRS.nContextInitToClient(this.mRS.mContext);
            while (this.mRun) {
                iArr[0] = 0;
                int nContextPeekMessage = this.mRS.nContextPeekMessage(this.mRS.mContext, this.mAuxData);
                int i = this.mAuxData[1];
                int i2 = this.mAuxData[0];
                if (nContextPeekMessage == 4) {
                    if ((i >> 2) >= iArr.length) {
                        iArr = new int[(i + 3) >> 2];
                    }
                    if (this.mRS.nContextGetUserMessage(this.mRS.mContext, iArr) != 4) {
                        throw new android.renderscript.RSDriverException("Error processing message from RenderScript.");
                    }
                    if (this.mRS.mMessageCallback != null) {
                        this.mRS.mMessageCallback.mData = iArr;
                        this.mRS.mMessageCallback.mID = i2;
                        this.mRS.mMessageCallback.mLength = i;
                        this.mRS.mMessageCallback.run();
                    } else {
                        throw new android.renderscript.RSInvalidStateException("Received a message from the script with no message handler installed.");
                    }
                } else if (nContextPeekMessage == 3) {
                    java.lang.String nContextGetErrorMessage = this.mRS.nContextGetErrorMessage(this.mRS.mContext);
                    if (i2 >= 4096 || (i2 >= 2048 && (this.mRS.mContextType != android.renderscript.RenderScript.ContextType.DEBUG || this.mRS.mErrorCallback == null))) {
                        throw new android.renderscript.RSRuntimeException("Fatal error " + i2 + ", details: " + nContextGetErrorMessage);
                    }
                    if (this.mRS.mErrorCallback != null) {
                        this.mRS.mErrorCallback.mErrorMessage = nContextGetErrorMessage;
                        this.mRS.mErrorCallback.mErrorNum = i2;
                        this.mRS.mErrorCallback.run();
                    } else {
                        android.util.Log.e(android.renderscript.RenderScript.LOG_TAG, "non fatal RS error, " + nContextGetErrorMessage);
                    }
                } else if (nContextPeekMessage != 5) {
                    try {
                        sleep(1L, 0);
                    } catch (java.lang.InterruptedException e) {
                    }
                } else {
                    if (this.mRS.nContextGetUserMessage(this.mRS.mContext, iArr) != 5) {
                        throw new android.renderscript.RSDriverException("Error processing message from RenderScript.");
                    }
                    android.renderscript.Allocation.sendBufferNotification((iArr[1] << 32) + (iArr[0] & 4294967295L));
                }
            }
        }
    }

    RenderScript(android.content.Context context) {
        if (context != null) {
            this.mApplicationContext = context.getApplicationContext();
        }
        this.mRWLock = new java.util.concurrent.locks.ReentrantReadWriteLock();
    }

    public final android.content.Context getApplicationContext() {
        return this.mApplicationContext;
    }

    static synchronized java.lang.String getCachePath() {
        java.lang.String str;
        synchronized (android.renderscript.RenderScript.class) {
            if (mCachePath == null) {
                if (android.renderscript.RenderScriptCacheDir.mCacheDir == null) {
                    throw new android.renderscript.RSRuntimeException("RenderScript code cache directory uninitialized.");
                }
                java.io.File file = new java.io.File(android.renderscript.RenderScriptCacheDir.mCacheDir, "com.android.renderscript.cache");
                mCachePath = file.getAbsolutePath();
                file.mkdirs();
            }
            str = mCachePath;
        }
        return str;
    }

    private static android.renderscript.RenderScript internalCreate(android.content.Context context, int i, android.renderscript.RenderScript.ContextType contextType, int i2) {
        if (!sInitialized) {
            android.util.Log.e(LOG_TAG, "RenderScript.create() called when disabled; someone is likely to crash");
            return null;
        }
        if ((i2 & (-15)) != 0) {
            throw new android.renderscript.RSIllegalArgumentException("Invalid flags passed.");
        }
        android.renderscript.RenderScript renderScript = new android.renderscript.RenderScript(context);
        renderScript.mContext = renderScript.nContextCreate(renderScript.nDeviceCreate(), i2, i, contextType.mID);
        renderScript.mContextType = contextType;
        renderScript.mContextFlags = i2;
        renderScript.mContextSdkVersion = i;
        if (renderScript.mContext == 0) {
            throw new android.renderscript.RSDriverException("Failed to create RS context.");
        }
        renderScript.nContextSetCacheDir(getCachePath());
        renderScript.mMessageThread = new android.renderscript.RenderScript.MessageThread(renderScript);
        renderScript.mMessageThread.start();
        return renderScript;
    }

    public static android.renderscript.RenderScript create(android.content.Context context) {
        return create(context, android.renderscript.RenderScript.ContextType.NORMAL);
    }

    public static android.renderscript.RenderScript create(android.content.Context context, android.renderscript.RenderScript.ContextType contextType) {
        return create(context, contextType, 0);
    }

    public static android.renderscript.RenderScript create(android.content.Context context, android.renderscript.RenderScript.ContextType contextType, int i) {
        return create(context, context.getApplicationInfo().targetSdkVersion, contextType, i);
    }

    public static android.renderscript.RenderScript create(android.content.Context context, int i) {
        return create(context, i, android.renderscript.RenderScript.ContextType.NORMAL, 0);
    }

    private static android.renderscript.RenderScript create(android.content.Context context, int i, android.renderscript.RenderScript.ContextType contextType, int i2) {
        if (i < 23) {
            return internalCreate(context, i, contextType, i2);
        }
        synchronized (mProcessContextList) {
            java.util.Iterator<android.renderscript.RenderScript> it = mProcessContextList.iterator();
            while (it.hasNext()) {
                android.renderscript.RenderScript next = it.next();
                if (next.mContextType == contextType && next.mContextFlags == i2 && next.mContextSdkVersion == i) {
                    return next;
                }
            }
            android.renderscript.RenderScript internalCreate = internalCreate(context, i, contextType, i2);
            internalCreate.mIsProcessContext = true;
            mProcessContextList.add(internalCreate);
            return internalCreate;
        }
    }

    public static void releaseAllContexts() {
        java.util.ArrayList<android.renderscript.RenderScript> arrayList;
        synchronized (mProcessContextList) {
            arrayList = mProcessContextList;
            mProcessContextList = new java.util.ArrayList<>();
        }
        java.util.Iterator<android.renderscript.RenderScript> it = arrayList.iterator();
        while (it.hasNext()) {
            android.renderscript.RenderScript next = it.next();
            next.mIsProcessContext = false;
            next.destroy();
        }
        arrayList.clear();
    }

    public static android.renderscript.RenderScript createMultiContext(android.content.Context context, android.renderscript.RenderScript.ContextType contextType, int i, int i2) {
        return internalCreate(context, i2, contextType, i);
    }

    public void contextDump() {
        validate();
        nContextDump(0);
    }

    public void finish() {
        nContextFinish();
    }

    private void helpDestroy() {
        boolean z;
        boolean z2;
        synchronized (this) {
            z = false;
            if (this.mDestroyed) {
                z2 = false;
            } else {
                this.mDestroyed = true;
                z2 = true;
            }
        }
        if (z2) {
            nContextFinish();
            nContextDeinitToClient(this.mContext);
            this.mMessageThread.mRun = false;
            this.mMessageThread.interrupt();
            boolean z3 = false;
            while (!z) {
                try {
                    this.mMessageThread.join();
                    z = true;
                } catch (java.lang.InterruptedException e) {
                    z3 = true;
                }
            }
            if (z3) {
                android.util.Log.v(LOG_TAG, "Interrupted during wait for MessageThread to join");
                java.lang.Thread.currentThread().interrupt();
            }
            nContextDestroy();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        helpDestroy();
        super.finalize();
    }

    public void destroy() {
        if (this.mIsProcessContext) {
            return;
        }
        validate();
        helpDestroy();
    }

    boolean isAlive() {
        return this.mContext != 0;
    }

    long safeID(android.renderscript.BaseObj baseObj) {
        if (baseObj != null) {
            return baseObj.getID(this);
        }
        return 0L;
    }
}
