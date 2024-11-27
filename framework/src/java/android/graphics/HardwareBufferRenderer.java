package android.graphics;

/* loaded from: classes.dex */
public class HardwareBufferRenderer implements java.lang.AutoCloseable {
    private static final android.graphics.ColorSpace DEFAULT_COLORSPACE = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
    private final java.lang.Runnable mCleaner;
    private final android.hardware.HardwareBuffer mHardwareBuffer;
    private long mProxy;
    private final android.graphics.HardwareBufferRenderer.RenderRequest mRenderRequest;
    private final android.graphics.RenderNode mRootNode;

    private static native long nCreateHardwareBufferRenderer(android.hardware.HardwareBuffer hardwareBuffer, long j);

    private static native long nCreateRootRenderNode();

    private static native void nDestroyRootRenderNode(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFinalizer();

    static native int nRender(long j, int i, int i2, int i3, long j2, java.util.function.Consumer<android.graphics.HardwareBufferRenderer.RenderResult> consumer);

    private static native void nSetLightAlpha(long j, float f, float f2);

    private static native void nSetLightGeometry(long j, float f, float f2, float f3, float f4);

    private static class HardwareBufferRendererHolder {
        public static final libcore.util.NativeAllocationRegistry REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.HardwareBufferRenderer.class.getClassLoader(), android.graphics.HardwareBufferRenderer.nGetFinalizer());

        private HardwareBufferRendererHolder() {
        }
    }

    public HardwareBufferRenderer(android.hardware.HardwareBuffer hardwareBuffer) {
        android.graphics.RenderNode adopt = android.graphics.RenderNode.adopt(nCreateRootRenderNode());
        adopt.setClipToBounds(false);
        this.mProxy = nCreateHardwareBufferRenderer(hardwareBuffer, adopt.mNativeRenderNode);
        this.mCleaner = android.graphics.HardwareBufferRenderer.HardwareBufferRendererHolder.REGISTRY.registerNativeAllocation(this, this.mProxy);
        this.mRenderRequest = new android.graphics.HardwareBufferRenderer.RenderRequest();
        this.mRootNode = adopt;
        this.mHardwareBuffer = hardwareBuffer;
    }

    public void setContentRoot(android.graphics.RenderNode renderNode) {
        android.graphics.RecordingCanvas beginRecording = this.mRootNode.beginRecording();
        if (renderNode != null) {
            beginRecording.drawRenderNode(renderNode);
        }
        this.mRootNode.endRecording();
    }

    public android.graphics.HardwareBufferRenderer.RenderRequest obtainRenderRequest() {
        this.mRenderRequest.reset();
        return this.mRenderRequest;
    }

    public boolean isClosed() {
        return this.mProxy == 0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        nDestroyRootRenderNode(this.mRootNode.mNativeRenderNode);
        if (this.mProxy != 0) {
            this.mCleaner.run();
            this.mProxy = 0L;
        }
    }

    public void setLightSourceGeometry(float f, float f2, float f3, float f4) {
        validateFinite(f, "lightX");
        validateFinite(f2, "lightY");
        validatePositive(f3, "lightZ");
        validatePositive(f4, "lightRadius");
        nSetLightGeometry(this.mProxy, f, f2, f3, f4);
    }

    public void setLightSourceAlpha(float f, float f2) {
        validateAlpha(f, "ambientShadowAlpha");
        validateAlpha(f2, "spotShadowAlpha");
        nSetLightAlpha(this.mProxy, f, f2);
    }

    public static final class RenderResult {
        public static final int ERROR_UNKNOWN = 1;
        public static final int SUCCESS = 0;
        private final android.hardware.SyncFence mFence;
        private final int mResultStatus;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RenderResultStatus {
        }

        private RenderResult(android.hardware.SyncFence syncFence, int i) {
            this.mFence = syncFence;
            this.mResultStatus = i;
        }

        public android.hardware.SyncFence getFence() {
            return this.mFence;
        }

        public int getStatus() {
            return this.mResultStatus;
        }
    }

    public final class RenderRequest {
        private android.graphics.ColorSpace mColorSpace;
        private int mTransform;

        private RenderRequest() {
            this.mColorSpace = android.graphics.HardwareBufferRenderer.DEFAULT_COLORSPACE;
            this.mTransform = 0;
        }

        public void draw(final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.graphics.HardwareBufferRenderer.RenderResult> consumer) {
            int height;
            int width;
            java.util.function.Consumer consumer2 = new java.util.function.Consumer() { // from class: android.graphics.HardwareBufferRenderer$RenderRequest$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    executor.execute(new java.lang.Runnable() { // from class: android.graphics.HardwareBufferRenderer$RenderRequest$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(r2);
                        }
                    });
                }
            };
            if (!android.graphics.HardwareBufferRenderer.this.isClosed()) {
                if (this.mTransform == 4 || this.mTransform == 7) {
                    height = android.graphics.HardwareBufferRenderer.this.mHardwareBuffer.getHeight();
                    width = android.graphics.HardwareBufferRenderer.this.mHardwareBuffer.getWidth();
                } else {
                    height = android.graphics.HardwareBufferRenderer.this.mHardwareBuffer.getWidth();
                    width = android.graphics.HardwareBufferRenderer.this.mHardwareBuffer.getHeight();
                }
                android.graphics.HardwareBufferRenderer.nRender(android.graphics.HardwareBufferRenderer.this.mProxy, this.mTransform, height, width, this.mColorSpace.getNativeInstance(), consumer2);
                return;
            }
            throw new java.lang.IllegalStateException("Attempt to draw with a HardwareBufferRenderer instance that has already been closed");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mColorSpace = android.graphics.HardwareBufferRenderer.DEFAULT_COLORSPACE;
            this.mTransform = 0;
        }

        public android.graphics.HardwareBufferRenderer.RenderRequest setColorSpace(android.graphics.ColorSpace colorSpace) {
            if (colorSpace == null) {
                this.mColorSpace = android.graphics.HardwareBufferRenderer.DEFAULT_COLORSPACE;
            } else {
                this.mColorSpace = colorSpace;
            }
            return this;
        }

        public android.graphics.HardwareBufferRenderer.RenderRequest setBufferTransform(int i) {
            if (i == 0 || i == 4 || i == 3 || i == 7) {
                this.mTransform = i;
                return this;
            }
            throw new java.lang.IllegalArgumentException("Invalid transform provided, must be one ofthe SurfaceControl.BufferTransform values");
        }
    }

    private static void invokeRenderCallback(java.util.function.Consumer<android.graphics.HardwareBufferRenderer.RenderResult> consumer, int i, int i2) {
        consumer.accept(new android.graphics.HardwareBufferRenderer.RenderResult(android.hardware.SyncFence.adopt(i), i2));
    }

    private static void validateAlpha(float f, java.lang.String str) {
        if (f < 0.0f || f > 1.0f) {
            throw new java.lang.IllegalArgumentException(str + " must be a valid alpha, " + f + " is not in the range of 0.0f to 1.0f");
        }
    }

    private static void validateFinite(float f, java.lang.String str) {
        if (!java.lang.Float.isFinite(f)) {
            throw new java.lang.IllegalArgumentException(str + " must be finite, given=" + f);
        }
    }

    private static void validatePositive(float f, java.lang.String str) {
        if (!java.lang.Float.isFinite(f) || f < 0.0f) {
            throw new java.lang.IllegalArgumentException(str + " must be a finite positive, given=" + f);
        }
    }
}
