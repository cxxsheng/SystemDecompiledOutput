package android.graphics;

/* loaded from: classes.dex */
public class HardwareRenderer {
    private static final java.lang.String CACHE_PATH_SHADERS = "com.android.opengl.shaders_cache";
    private static final java.lang.String CACHE_PATH_SKIASHADERS = "com.android.skia.shaders_cache";
    public static final int CACHE_TRIM_ALL = 0;
    public static final int CACHE_TRIM_FONT = 1;
    public static final int CACHE_TRIM_RESOURCES = 2;
    public static final int FLAG_DUMP_ALL = 1;
    public static final int FLAG_DUMP_FRAMESTATS = 1;
    public static final int FLAG_DUMP_RESET = 2;
    private static final java.lang.String LOG_TAG = "HardwareRenderer";
    public static final int SYNC_CONTEXT_IS_STOPPED = 4;
    public static final int SYNC_FRAME_DROPPED = 8;
    public static final int SYNC_LOST_SURFACE_REWARD_IF_FOUND = 2;
    public static final int SYNC_OK = 0;
    public static final int SYNC_REDRAW_REQUESTED = 1;
    private static int sDensityDpi = 0;
    private final long mNativeProxy;
    protected android.graphics.RenderNode mRootNode;
    private boolean mOpaque = true;
    private int mForceDark = 0;
    private int mColorMode = 0;
    private float mDesiredSdrHdrRatio = 1.0f;
    private android.graphics.HardwareRenderer.FrameRenderRequest mRenderRequest = new android.graphics.HardwareRenderer.FrameRenderRequest();

    public interface ASurfaceTransactionCallback {
        boolean onMergeTransaction(long j, long j2, long j3);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CacheTrimLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DumpFlags {
    }

    public interface FrameCommitCallback {
        void onFrameCommit(boolean z);
    }

    public interface FrameCompleteCallback {
        void onFrameComplete();
    }

    public interface PictureCapturedCallback {
        void onPictureCaptured(android.graphics.Picture picture);
    }

    public interface PrepareSurfaceControlForWebviewCallback {
        void prepare();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SyncAndDrawResult {
    }

    public static native void disableVsync();

    protected static native boolean isWebViewOverlaysEnabled();

    private static native void nAddObserver(long j, long j2);

    private static native void nAddRenderNode(long j, long j2, boolean z);

    private static native void nAllocateBuffers(long j);

    private static native void nBuildLayer(long j, long j2);

    private static native void nCancelLayerUpdate(long j, long j2);

    private static native boolean nCopyLayerInto(long j, long j2, long j3);

    private static native void nCopySurfaceInto(android.view.Surface surface, int i, int i2, int i3, int i4, android.graphics.HardwareRenderer.CopyRequest copyRequest);

    private static native android.graphics.Bitmap nCreateHardwareBitmap(long j, int i, int i2);

    private static native long nCreateProxy(boolean z, long j);

    private static native long nCreateRootRenderNode();

    private static native long nCreateTextureLayer(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nDeleteProxy(long j);

    private static native void nDestroy(long j, long j2);

    private static native void nDestroyHardwareResources(long j);

    private static native void nDetachSurfaceTexture(long j, long j2);

    private static native void nDrawRenderNode(long j, long j2);

    private static native void nDumpGlobalProfileInfo(java.io.FileDescriptor fileDescriptor, int i);

    private static native void nDumpProfileInfo(long j, java.io.FileDescriptor fileDescriptor, int i);

    private static native void nFence(long j);

    private static native void nForceDrawNextFrame(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nGetRenderThreadTid(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nInitDisplayInfo(int i, int i2, float f, int i3, long j, long j2, boolean z, boolean z2);

    private static native boolean nIsDrawingEnabled();

    private static native boolean nLoadSystemProperties(long j);

    private static native void nNotifyCallbackPending(long j);

    private static native void nNotifyExpensiveFrame(long j);

    private static native void nNotifyFramePending(long j);

    private static native void nOverrideProperty(java.lang.String str, java.lang.String str2);

    private static native boolean nPause(long j);

    private static native void nPushLayerUpdate(long j, long j2);

    private static native void nRegisterAnimatingRenderNode(long j, long j2);

    private static native void nRegisterVectorDrawableAnimator(long j, long j2);

    private static native void nRemoveObserver(long j, long j2);

    private static native void nRemoveRenderNode(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nRotateProcessStatsBuffer();

    private static native void nSetASurfaceTransactionCallback(long j, android.graphics.HardwareRenderer.ASurfaceTransactionCallback aSurfaceTransactionCallback);

    private static native float nSetColorMode(long j, int i);

    private static native void nSetContentDrawBounds(long j, int i, int i2, int i3, int i4);

    private static native void nSetContextPriority(int i);

    private static native void nSetDebuggingEnabled(boolean z);

    private static native void nSetDisplayDensityDpi(int i);

    private static native void nSetDrawingEnabled(boolean z);

    private static native void nSetForceDark(long j, int i);

    private static native void nSetFrameCallback(long j, android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetFrameCommitCallback(long j, android.graphics.HardwareRenderer.FrameCommitCallback frameCommitCallback);

    private static native void nSetFrameCompleteCallback(long j, android.graphics.HardwareRenderer.FrameCompleteCallback frameCompleteCallback);

    private static native void nSetHighContrastText(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetIsHighEndGfx(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetIsLowRam(boolean z);

    private static native void nSetIsSystemOrPersistent(boolean z);

    private static native void nSetIsolatedProcess(boolean z);

    private static native void nSetLightAlpha(long j, float f, float f2);

    private static native void nSetLightGeometry(long j, float f, float f2, float f3, float f4);

    private static native void nSetName(long j, java.lang.String str);

    private static native void nSetOpaque(long j, boolean z);

    private static native void nSetPictureCaptureCallback(long j, android.graphics.HardwareRenderer.PictureCapturedCallback pictureCapturedCallback);

    private static native void nSetPrepareSurfaceControlForWebviewCallback(long j, android.graphics.HardwareRenderer.PrepareSurfaceControlForWebviewCallback prepareSurfaceControlForWebviewCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetProcessStatsBuffer(int i);

    private static native void nSetRtAnimationsEnabled(boolean z);

    private static native void nSetSdrWhitePoint(long j, float f);

    private static native void nSetStopped(long j, boolean z);

    private static native void nSetSurface(long j, android.view.Surface surface, boolean z);

    private static native void nSetSurfaceControl(long j, long j2);

    private static native void nSetTargetSdrHdrRatio(long j, float f);

    private static native void nStopDrawing(long j);

    private static native int nSyncAndDrawFrame(long j, long[] jArr, int i);

    private static native void nTrimCaches(int i);

    private static native void nTrimMemory(int i);

    public static native void preload();

    protected static native void setupShadersDiskCache(java.lang.String str, java.lang.String str2);

    public HardwareRenderer() {
        android.graphics.HardwareRenderer.ProcessInitializer.sInstance.initUsingContext();
        this.mRootNode = android.graphics.RenderNode.adopt(nCreateRootRenderNode());
        this.mRootNode.setClipToBounds(false);
        this.mNativeProxy = nCreateProxy(true ^ this.mOpaque, this.mRootNode.mNativeRenderNode);
        if (this.mNativeProxy == 0) {
            throw new java.lang.OutOfMemoryError("Unable to create hardware renderer");
        }
        sun.misc.Cleaner.create(this, new android.graphics.HardwareRenderer.DestroyContextRunnable(this.mNativeProxy));
        android.graphics.HardwareRenderer.ProcessInitializer.sInstance.init(this.mNativeProxy);
    }

    public void destroy() {
        nDestroy(this.mNativeProxy, this.mRootNode.mNativeRenderNode);
    }

    public void setName(java.lang.String str) {
        nSetName(this.mNativeProxy, str);
    }

    public void setLightSourceGeometry(float f, float f2, float f3, float f4) {
        validateFinite(f, "lightX");
        validateFinite(f2, "lightY");
        validatePositive(f3, "lightZ");
        validatePositive(f4, "lightRadius");
        nSetLightGeometry(this.mNativeProxy, f, f2, f3, f4);
    }

    public void setLightSourceAlpha(float f, float f2) {
        validateAlpha(f, "ambientShadowAlpha");
        validateAlpha(f2, "spotShadowAlpha");
        nSetLightAlpha(this.mNativeProxy, f, f2);
    }

    public void setContentRoot(android.graphics.RenderNode renderNode) {
        android.graphics.RecordingCanvas beginRecording = this.mRootNode.beginRecording();
        if (renderNode != null) {
            beginRecording.drawRenderNode(renderNode);
        }
        this.mRootNode.endRecording();
    }

    public void setSurface(android.view.Surface surface) {
        setSurface(surface, false);
    }

    public void setSurface(android.view.Surface surface, boolean z) {
        if (surface != null && !surface.isValid()) {
            throw new java.lang.IllegalArgumentException("Surface is invalid. surface.isValid() == false.");
        }
        nSetSurface(this.mNativeProxy, surface, z);
    }

    public void setSurfaceControl(android.view.SurfaceControl surfaceControl, android.graphics.BLASTBufferQueue bLASTBufferQueue) {
        nSetSurfaceControl(this.mNativeProxy, surfaceControl != null ? surfaceControl.mNativeObject : 0L);
    }

    public final class FrameRenderRequest {
        private android.graphics.FrameInfo mFrameInfo;
        private boolean mWaitForPresent;

        private FrameRenderRequest() {
            this.mFrameInfo = new android.graphics.FrameInfo();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mWaitForPresent = false;
            android.graphics.HardwareRenderer.this.mRenderRequest.setVsyncTime(android.view.animation.AnimationUtils.currentAnimationTimeMillis() * 1000000);
        }

        public void setFrameInfo(android.graphics.FrameInfo frameInfo) {
            java.lang.System.arraycopy(frameInfo.frameInfo, 0, this.mFrameInfo.frameInfo, 0, frameInfo.frameInfo.length);
        }

        public android.graphics.HardwareRenderer.FrameRenderRequest setVsyncTime(long j) {
            this.mFrameInfo.setVsync(j, j, -1L, Long.MAX_VALUE, j, -1L);
            this.mFrameInfo.addFlags(4L);
            return this;
        }

        public android.graphics.HardwareRenderer.FrameRenderRequest setFrameCommitCallback(final java.util.concurrent.Executor executor, final java.lang.Runnable runnable) {
            android.graphics.HardwareRenderer.nSetFrameCommitCallback(android.graphics.HardwareRenderer.this.mNativeProxy, new android.graphics.HardwareRenderer.FrameCommitCallback() { // from class: android.graphics.HardwareRenderer$FrameRenderRequest$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    executor.execute(runnable);
                }
            });
            return this;
        }

        public android.graphics.HardwareRenderer.FrameRenderRequest setWaitForPresent(boolean z) {
            this.mWaitForPresent = z;
            return this;
        }

        public int syncAndDraw() {
            int syncAndDrawFrame = android.graphics.HardwareRenderer.this.syncAndDrawFrame(this.mFrameInfo);
            if (this.mWaitForPresent && (syncAndDrawFrame & 8) == 0) {
                android.graphics.HardwareRenderer.this.fence();
            }
            return syncAndDrawFrame;
        }
    }

    public android.graphics.HardwareRenderer.FrameRenderRequest createRenderRequest() {
        this.mRenderRequest.reset();
        return this.mRenderRequest;
    }

    public int syncAndDrawFrame(android.graphics.FrameInfo frameInfo) {
        return nSyncAndDrawFrame(this.mNativeProxy, frameInfo.frameInfo, frameInfo.frameInfo.length);
    }

    public boolean pause() {
        return nPause(this.mNativeProxy);
    }

    public void setStopped(boolean z) {
        nSetStopped(this.mNativeProxy, z);
    }

    public void stop() {
        nSetStopped(this.mNativeProxy, true);
    }

    public void start() {
        nSetStopped(this.mNativeProxy, false);
    }

    public void clearContent() {
        nDestroyHardwareResources(this.mNativeProxy);
    }

    public boolean setForceDark(int i) {
        if (this.mForceDark != i) {
            this.mForceDark = i;
            nSetForceDark(this.mNativeProxy, i);
            return true;
        }
        return false;
    }

    public void allocateBuffers() {
        nAllocateBuffers(this.mNativeProxy);
    }

    public void notifyFramePending() {
        nNotifyFramePending(this.mNativeProxy);
    }

    public void setOpaque(boolean z) {
        if (this.mOpaque != z) {
            this.mOpaque = z;
            nSetOpaque(this.mNativeProxy, this.mOpaque);
        }
    }

    public boolean isOpaque() {
        return this.mOpaque;
    }

    public void setFrameCommitCallback(android.graphics.HardwareRenderer.FrameCommitCallback frameCommitCallback) {
        nSetFrameCommitCallback(this.mNativeProxy, frameCommitCallback);
    }

    public void setFrameCompleteCallback(android.graphics.HardwareRenderer.FrameCompleteCallback frameCompleteCallback) {
        nSetFrameCompleteCallback(this.mNativeProxy, frameCompleteCallback);
    }

    public void addObserver(android.graphics.HardwareRendererObserver hardwareRendererObserver) {
        nAddObserver(this.mNativeProxy, hardwareRendererObserver.getNativeInstance());
    }

    public void removeObserver(android.graphics.HardwareRendererObserver hardwareRendererObserver) {
        nRemoveObserver(this.mNativeProxy, hardwareRendererObserver.getNativeInstance());
    }

    public float setColorMode(int i) {
        if (this.mColorMode != i) {
            this.mColorMode = i;
            this.mDesiredSdrHdrRatio = nSetColorMode(this.mNativeProxy, i);
        }
        return this.mDesiredSdrHdrRatio;
    }

    public void setColorMode(int i, float f) {
        nSetSdrWhitePoint(this.mNativeProxy, f);
        this.mColorMode = i;
        nSetColorMode(this.mNativeProxy, i);
    }

    public void setTargetHdrSdrRatio(float f) {
        if (f < 1.0f || !java.lang.Float.isFinite(f)) {
            f = 1.0f;
        }
        nSetTargetSdrHdrRatio(this.mNativeProxy, f);
    }

    public void fence() {
        nFence(this.mNativeProxy);
    }

    public void registerAnimatingRenderNode(android.graphics.RenderNode renderNode) {
        nRegisterAnimatingRenderNode(this.mRootNode.mNativeRenderNode, renderNode.mNativeRenderNode);
    }

    public void registerVectorDrawableAnimator(android.view.NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        nRegisterVectorDrawableAnimator(this.mRootNode.mNativeRenderNode, nativeVectorDrawableAnimator.getAnimatorNativePtr());
    }

    public void stopDrawing() {
        nStopDrawing(this.mNativeProxy);
    }

    public android.graphics.TextureLayer createTextureLayer() {
        return android.graphics.TextureLayer.adoptTextureLayer(this, nCreateTextureLayer(this.mNativeProxy));
    }

    public void detachSurfaceTexture(long j) {
        nDetachSurfaceTexture(this.mNativeProxy, j);
    }

    public void buildLayer(android.graphics.RenderNode renderNode) {
        if (renderNode.hasDisplayList()) {
            nBuildLayer(this.mNativeProxy, renderNode.mNativeRenderNode);
        }
    }

    public boolean copyLayerInto(android.graphics.TextureLayer textureLayer, android.graphics.Bitmap bitmap) {
        return nCopyLayerInto(this.mNativeProxy, textureLayer.getDeferredLayerUpdater(), bitmap.getNativeInstance());
    }

    public void pushLayerUpdate(android.graphics.TextureLayer textureLayer) {
        nPushLayerUpdate(this.mNativeProxy, textureLayer.getDeferredLayerUpdater());
    }

    public void onLayerDestroyed(android.graphics.TextureLayer textureLayer) {
        nCancelLayerUpdate(this.mNativeProxy, textureLayer.getDeferredLayerUpdater());
    }

    protected void setASurfaceTransactionCallback(android.graphics.HardwareRenderer.ASurfaceTransactionCallback aSurfaceTransactionCallback) {
        nSetASurfaceTransactionCallback(this.mNativeProxy, aSurfaceTransactionCallback);
    }

    protected void setPrepareSurfaceControlForWebviewCallback(android.graphics.HardwareRenderer.PrepareSurfaceControlForWebviewCallback prepareSurfaceControlForWebviewCallback) {
        nSetPrepareSurfaceControlForWebviewCallback(this.mNativeProxy, prepareSurfaceControlForWebviewCallback);
    }

    public void setFrameCallback(android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        nSetFrameCallback(this.mNativeProxy, frameDrawingCallback);
    }

    public void addRenderNode(android.graphics.RenderNode renderNode, boolean z) {
        nAddRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode, z);
    }

    public void removeRenderNode(android.graphics.RenderNode renderNode) {
        nRemoveRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode);
    }

    public void drawRenderNode(android.graphics.RenderNode renderNode) {
        nDrawRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode);
    }

    public boolean loadSystemProperties() {
        return nLoadSystemProperties(this.mNativeProxy);
    }

    public static void dumpGlobalProfileInfo(java.io.FileDescriptor fileDescriptor, int i) {
        nDumpGlobalProfileInfo(fileDescriptor, i);
    }

    public void dumpProfileInfo(java.io.FileDescriptor fileDescriptor, int i) {
        nDumpProfileInfo(this.mNativeProxy, fileDescriptor, i);
    }

    public void setContentDrawBounds(int i, int i2, int i3, int i4) {
        nSetContentDrawBounds(this.mNativeProxy, i, i2, i3, i4);
    }

    public void forceDrawNextFrame() {
        nForceDrawNextFrame(this.mNativeProxy);
    }

    public void setPictureCaptureCallback(android.graphics.HardwareRenderer.PictureCapturedCallback pictureCapturedCallback) {
        nSetPictureCaptureCallback(this.mNativeProxy, pictureCapturedCallback);
    }

    static void invokePictureCapturedCallback(long j, android.graphics.HardwareRenderer.PictureCapturedCallback pictureCapturedCallback) {
        pictureCapturedCallback.onPictureCaptured(new android.graphics.Picture(j));
    }

    public interface FrameDrawingCallback {
        void onFrameDraw(long j);

        default android.graphics.HardwareRenderer.FrameCommitCallback onFrameDraw(int i, long j) {
            onFrameDraw(j);
            return null;
        }
    }

    private static void validateAlpha(float f, java.lang.String str) {
        if (f < 0.0f || f > 1.0f) {
            throw new java.lang.IllegalArgumentException(str + " must be a valid alpha, " + f + " is not in the range of 0.0f to 1.0f");
        }
    }

    private static void validatePositive(float f, java.lang.String str) {
        if (!java.lang.Float.isFinite(f) || f < 0.0f) {
            throw new java.lang.IllegalArgumentException(str + " must be a finite positive, given=" + f);
        }
    }

    private static void validateFinite(float f, java.lang.String str) {
        if (!java.lang.Float.isFinite(f)) {
            throw new java.lang.IllegalArgumentException(str + " must be finite, given=" + f);
        }
    }

    public void notifyCallbackPending() {
        nNotifyCallbackPending(this.mNativeProxy);
    }

    public void notifyExpensiveFrame() {
        nNotifyExpensiveFrame(this.mNativeProxy);
    }

    public static void setFPSDivisor(int i) {
        nSetRtAnimationsEnabled(i <= 1);
    }

    public static void setContextPriority(int i) {
        nSetContextPriority(i);
    }

    public static void setHighContrastText(boolean z) {
        nSetHighContrastText(z);
    }

    public static void setIsolatedProcess(boolean z) {
        nSetIsolatedProcess(z);
        android.graphics.HardwareRenderer.ProcessInitializer.sInstance.setIsolated(z);
    }

    public static void sendDeviceConfigurationForDebugging(android.content.res.Configuration configuration) {
        if (configuration.densityDpi != 0 && configuration.densityDpi != sDensityDpi) {
            sDensityDpi = configuration.densityDpi;
            nSetDisplayDensityDpi(configuration.densityDpi);
        }
    }

    public static void setDebuggingEnabled(boolean z) {
        nSetDebuggingEnabled(z);
    }

    public static abstract class CopyRequest {
        protected android.graphics.Bitmap mDestinationBitmap;
        final android.graphics.Rect mSrcRect;

        public abstract void onCopyFinished(int i);

        protected CopyRequest(android.graphics.Rect rect, android.graphics.Bitmap bitmap) {
            this.mDestinationBitmap = bitmap;
            if (rect != null) {
                this.mSrcRect = rect;
            } else {
                this.mSrcRect = new android.graphics.Rect();
            }
        }

        public long getDestinationBitmap(int i, int i2) {
            if (this.mDestinationBitmap == null) {
                this.mDestinationBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
            }
            return this.mDestinationBitmap.getNativeInstance();
        }
    }

    public static void copySurfaceInto(android.view.Surface surface, android.graphics.HardwareRenderer.CopyRequest copyRequest) {
        android.graphics.Rect rect = copyRequest.mSrcRect;
        nCopySurfaceInto(surface, rect.left, rect.top, rect.right, rect.bottom, copyRequest);
    }

    public static android.graphics.Bitmap createHardwareBitmap(android.graphics.RenderNode renderNode, int i, int i2) {
        return nCreateHardwareBitmap(renderNode.mNativeRenderNode, i, i2);
    }

    public static void trimMemory(int i) {
        nTrimMemory(i);
    }

    public static void trimCaches(int i) {
        nTrimCaches(i);
    }

    public static void overrideProperty(java.lang.String str, java.lang.String str2) {
        if (str == null || str2 == null) {
            throw new java.lang.IllegalArgumentException("name and value must be non-null");
        }
        nOverrideProperty(str, str2);
    }

    public static void setupDiskCache(java.io.File file) {
        setupShadersDiskCache(new java.io.File(file, CACHE_PATH_SHADERS).getAbsolutePath(), new java.io.File(file, CACHE_PATH_SKIASHADERS).getAbsolutePath());
    }

    public static void setPackageName(java.lang.String str) {
        android.graphics.HardwareRenderer.ProcessInitializer.sInstance.setPackageName(str);
    }

    public static void setContextForInit(android.content.Context context) {
        android.graphics.HardwareRenderer.ProcessInitializer.sInstance.setContext(context);
    }

    public static void setIsSystemOrPersistent() {
        nSetIsSystemOrPersistent(true);
    }

    public static boolean isDrawingEnabled() {
        return nIsDrawingEnabled();
    }

    public static void setDrawingEnabled(boolean z) {
        nSetDrawingEnabled(z);
    }

    public static void setRtAnimationsEnabled(boolean z) {
        nSetRtAnimationsEnabled(z);
    }

    private static final class DestroyContextRunnable implements java.lang.Runnable {
        private final long mNativeInstance;

        DestroyContextRunnable(long j) {
            this.mNativeInstance = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.graphics.HardwareRenderer.nDeleteProxy(this.mNativeInstance);
        }
    }

    private static class ProcessInitializer {
        static android.graphics.HardwareRenderer.ProcessInitializer sInstance = new android.graphics.HardwareRenderer.ProcessInitializer();
        private android.content.Context mContext;
        private android.view.IGraphicsStats mGraphicsStatsService;
        private java.lang.String mPackageName;
        private boolean mInitialized = false;
        private boolean mDisplayInitialized = false;
        private boolean mIsolated = false;
        private android.view.IGraphicsStatsCallback mGraphicsStatsCallback = new android.view.IGraphicsStatsCallback.Stub() { // from class: android.graphics.HardwareRenderer.ProcessInitializer.1
            @Override // android.view.IGraphicsStatsCallback
            public void onRotateGraphicsStatsBuffer() throws android.os.RemoteException {
                android.graphics.HardwareRenderer.ProcessInitializer.this.rotateBuffer();
            }
        };

        private ProcessInitializer() {
        }

        synchronized void setPackageName(java.lang.String str) {
            if (this.mInitialized) {
                return;
            }
            this.mPackageName = str;
        }

        synchronized void setIsolated(boolean z) {
            if (this.mInitialized) {
                return;
            }
            this.mIsolated = z;
        }

        synchronized void setContext(android.content.Context context) {
            if (this.mInitialized) {
                return;
            }
            this.mContext = context;
        }

        synchronized void init(long j) {
            if (this.mInitialized) {
                return;
            }
            this.mInitialized = true;
            initSched(j);
            initGraphicsStats();
        }

        private void initSched(long j) {
            try {
                android.app.ActivityManager.getService().setRenderThread(android.graphics.HardwareRenderer.nGetRenderThreadTid(j));
            } catch (java.lang.Throwable th) {
                android.util.Log.w(android.graphics.HardwareRenderer.LOG_TAG, "Failed to set scheduler for RenderThread", th);
            }
        }

        private void initGraphicsStats() {
            if (this.mPackageName == null) {
                return;
            }
            try {
                android.os.IBinder service = android.os.ServiceManager.getService(android.graphics.GraphicsStatsService.GRAPHICS_STATS_SERVICE);
                if (service == null) {
                    return;
                }
                this.mGraphicsStatsService = android.view.IGraphicsStats.Stub.asInterface(service);
                requestBuffer();
            } catch (java.lang.Throwable th) {
                android.util.Log.w(android.graphics.HardwareRenderer.LOG_TAG, "Could not acquire gfx stats buffer", th);
            }
        }

        synchronized void initUsingContext() {
            if (this.mContext == null) {
                return;
            }
            initDisplayInfo();
            android.graphics.HardwareRenderer.nSetIsHighEndGfx(android.app.ActivityManager.isHighEndGfx());
            android.graphics.HardwareRenderer.nSetIsLowRam(android.app.ActivityManager.isLowRamDeviceStatic());
            this.mContext = null;
        }

        private void initDisplayInfo() {
            int i;
            android.graphics.ColorSpace preferredWideGamutColorSpace;
            if (this.mDisplayInitialized) {
                return;
            }
            if (this.mIsolated) {
                this.mDisplayInitialized = true;
                return;
            }
            android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.content.Context.DISPLAY_SERVICE);
            if (displayManager == null) {
                android.util.Log.d(android.graphics.HardwareRenderer.LOG_TAG, "Failed to find DisplayManager for display-based configuration");
                return;
            }
            android.view.Display display = displayManager.getDisplay(0);
            if (display == null) {
                android.util.Log.d(android.graphics.HardwareRenderer.LOG_TAG, "Failed to find default display for display-based configuration");
                return;
            }
            android.view.Display[] displays = displayManager.getDisplays();
            if (displays.length == 0) {
                android.util.Log.d(android.graphics.HardwareRenderer.LOG_TAG, "Failed to query displays");
                return;
            }
            android.view.Display.Mode mode = display.getMode();
            android.graphics.ColorSpace preferredWideGamutColorSpace2 = display.getPreferredWideGamutColorSpace();
            if (preferredWideGamutColorSpace2 == null) {
                i = 0;
            } else {
                i = preferredWideGamutColorSpace2.getDataSpace();
            }
            int physicalWidth = mode.getPhysicalWidth();
            int physicalHeight = mode.getPhysicalHeight();
            android.hardware.OverlayProperties overlaySupport = display.getOverlaySupport();
            int i2 = physicalHeight;
            int i3 = i;
            int i4 = physicalWidth;
            for (android.view.Display display2 : displays) {
                if (i3 == 0 && (preferredWideGamutColorSpace = display2.getPreferredWideGamutColorSpace()) != null) {
                    i3 = preferredWideGamutColorSpace.getDataSpace();
                }
                for (android.view.Display.Mode mode2 : display2.getSupportedModes()) {
                    int physicalWidth2 = mode2.getPhysicalWidth();
                    int physicalHeight2 = mode2.getPhysicalHeight();
                    if (physicalWidth2 * physicalHeight2 > i4 * i2) {
                        i2 = physicalHeight2;
                        i4 = physicalWidth2;
                    }
                }
            }
            android.graphics.HardwareRenderer.nInitDisplayInfo(i4, i2, display.getRefreshRate(), i3, display.getAppVsyncOffsetNanos(), display.getPresentationDeadlineNanos(), overlaySupport.isFp16SupportedForHdr(), overlaySupport.isMixedColorSpacesSupported());
            this.mDisplayInitialized = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void rotateBuffer() {
            android.graphics.HardwareRenderer.nRotateProcessStatsBuffer();
            requestBuffer();
        }

        private void requestBuffer() {
            try {
                android.os.ParcelFileDescriptor requestBufferForProcess = this.mGraphicsStatsService.requestBufferForProcess(this.mPackageName, this.mGraphicsStatsCallback);
                android.graphics.HardwareRenderer.nSetProcessStatsBuffer(requestBufferForProcess.getFd());
                requestBufferForProcess.close();
            } catch (java.lang.Throwable th) {
                android.util.Log.w(android.graphics.HardwareRenderer.LOG_TAG, "Could not acquire gfx stats buffer", th);
            }
        }
    }
}
