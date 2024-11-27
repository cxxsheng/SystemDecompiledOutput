package android.filterpacks.videosrc;

/* loaded from: classes.dex */
public class SurfaceTextureTarget extends android.filterfw.core.Filter {
    private static final java.lang.String TAG = "SurfaceTextureTarget";
    private final int RENDERMODE_CUSTOMIZE;
    private final int RENDERMODE_FILL_CROP;
    private final int RENDERMODE_FIT;
    private final int RENDERMODE_STRETCH;
    private float mAspectRatio;
    private boolean mLogVerbose;
    private android.filterfw.core.ShaderProgram mProgram;
    private int mRenderMode;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "renderMode")
    private java.lang.String mRenderModeString;
    private android.filterfw.core.GLFrame mScreen;

    @android.filterfw.core.GenerateFinalPort(name = "height")
    private int mScreenHeight;

    @android.filterfw.core.GenerateFinalPort(name = "width")
    private int mScreenWidth;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "sourceQuad")
    private android.filterfw.geometry.Quad mSourceQuad;
    private int mSurfaceId;

    @android.filterfw.core.GenerateFinalPort(name = "surfaceTexture")
    private android.graphics.SurfaceTexture mSurfaceTexture;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "targetQuad")
    private android.filterfw.geometry.Quad mTargetQuad;

    public SurfaceTextureTarget(java.lang.String str) {
        super(str);
        this.RENDERMODE_STRETCH = 0;
        this.RENDERMODE_FIT = 1;
        this.RENDERMODE_FILL_CROP = 2;
        this.RENDERMODE_CUSTOMIZE = 3;
        this.mSourceQuad = new android.filterfw.geometry.Quad(new android.filterfw.geometry.Point(0.0f, 1.0f), new android.filterfw.geometry.Point(1.0f, 1.0f), new android.filterfw.geometry.Point(0.0f, 0.0f), new android.filterfw.geometry.Point(1.0f, 0.0f));
        this.mTargetQuad = new android.filterfw.geometry.Quad(new android.filterfw.geometry.Point(0.0f, 0.0f), new android.filterfw.geometry.Point(1.0f, 0.0f), new android.filterfw.geometry.Point(0.0f, 1.0f), new android.filterfw.geometry.Point(1.0f, 1.0f));
        this.mRenderMode = 1;
        this.mAspectRatio = 1.0f;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public synchronized void setupPorts() {
        if (this.mSurfaceTexture == null) {
            throw new java.lang.RuntimeException("Null SurfaceTexture passed to SurfaceTextureTarget");
        }
        addMaskedInputPort("frame", android.filterfw.format.ImageFormat.create(3));
    }

    public void updateRenderMode() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "updateRenderMode. Thread: " + java.lang.Thread.currentThread());
        }
        if (this.mRenderModeString != null) {
            if (this.mRenderModeString.equals("stretch")) {
                this.mRenderMode = 0;
            } else if (this.mRenderModeString.equals("fit")) {
                this.mRenderMode = 1;
            } else if (this.mRenderModeString.equals("fill_crop")) {
                this.mRenderMode = 2;
            } else if (this.mRenderModeString.equals("customize")) {
                this.mRenderMode = 3;
            } else {
                throw new java.lang.RuntimeException("Unknown render mode '" + this.mRenderModeString + "'!");
            }
        }
        updateTargetRect();
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Prepare. Thread: " + java.lang.Thread.currentThread());
        }
        this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        this.mProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        updateRenderMode();
        android.filterfw.core.MutableFrameFormat mutableFrameFormat = new android.filterfw.core.MutableFrameFormat(2, 3);
        mutableFrameFormat.setBytesPerSample(4);
        mutableFrameFormat.setDimensions(this.mScreenWidth, this.mScreenHeight);
        this.mScreen = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 101, 0L);
    }

    @Override // android.filterfw.core.Filter
    public synchronized void open(android.filterfw.core.FilterContext filterContext) {
        if (this.mSurfaceTexture == null) {
            android.util.Log.e(TAG, "SurfaceTexture is null!!");
            throw new java.lang.RuntimeException("Could not register SurfaceTexture: " + this.mSurfaceTexture);
        }
        this.mSurfaceId = filterContext.getGLEnvironment().registerSurfaceTexture(this.mSurfaceTexture, this.mScreenWidth, this.mScreenHeight);
        if (this.mSurfaceId <= 0) {
            throw new java.lang.RuntimeException("Could not register SurfaceTexture: " + this.mSurfaceTexture);
        }
    }

    @Override // android.filterfw.core.Filter
    public synchronized void close(android.filterfw.core.FilterContext filterContext) {
        if (this.mSurfaceId > 0) {
            filterContext.getGLEnvironment().unregisterSurfaceId(this.mSurfaceId);
            this.mSurfaceId = -1;
        }
    }

    public synchronized void disconnect(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, android.media.MediaMetrics.Value.DISCONNECT);
        }
        if (this.mSurfaceTexture == null) {
            android.util.Log.d(TAG, "SurfaceTexture is already null. Nothing to disconnect.");
            return;
        }
        this.mSurfaceTexture = null;
        if (this.mSurfaceId > 0) {
            filterContext.getGLEnvironment().unregisterSurfaceId(this.mSurfaceId);
            this.mSurfaceId = -1;
        }
    }

    @Override // android.filterfw.core.Filter
    public synchronized void process(android.filterfw.core.FilterContext filterContext) {
        boolean z;
        android.filterfw.core.Frame frame;
        if (this.mSurfaceId <= 0) {
            return;
        }
        android.filterfw.core.GLEnvironment gLEnvironment = filterContext.getGLEnvironment();
        android.filterfw.core.Frame pullInput = pullInput("frame");
        float width = pullInput.getFormat().getWidth() / pullInput.getFormat().getHeight();
        if (width != this.mAspectRatio) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Process. New aspect ratio: " + width + ", previously: " + this.mAspectRatio + ". Thread: " + java.lang.Thread.currentThread());
            }
            this.mAspectRatio = width;
            updateTargetRect();
        }
        if (pullInput.getFormat().getTarget() != 3) {
            frame = filterContext.getFrameManager().duplicateFrameToTarget(pullInput, 3);
            z = true;
        } else {
            z = false;
            frame = pullInput;
        }
        gLEnvironment.activateSurfaceWithId(this.mSurfaceId);
        this.mProgram.process(frame, this.mScreen);
        gLEnvironment.setSurfaceTimestamp(pullInput.getTimestamp());
        gLEnvironment.swapBuffers();
        if (z) {
            frame.release();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "FPVU. Thread: " + java.lang.Thread.currentThread());
        }
        updateRenderMode();
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mScreen != null) {
            this.mScreen.release();
        }
    }

    private void updateTargetRect() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "updateTargetRect. Thread: " + java.lang.Thread.currentThread());
        }
        if (this.mScreenWidth > 0 && this.mScreenHeight > 0 && this.mProgram != null) {
            float f = this.mScreenWidth / this.mScreenHeight;
            float f2 = f / this.mAspectRatio;
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "UTR. screen w = " + this.mScreenWidth + " x screen h = " + this.mScreenHeight + " Screen AR: " + f + ", frame AR: " + this.mAspectRatio + ", relative AR: " + f2);
            }
            if (f2 == 1.0f && this.mRenderMode != 3) {
                this.mProgram.setTargetRect(0.0f, 0.0f, 1.0f, 1.0f);
                this.mProgram.setClearsOutput(false);
                return;
            }
            switch (this.mRenderMode) {
                case 0:
                    this.mTargetQuad.p0.set(0.0f, 0.0f);
                    this.mTargetQuad.p1.set(1.0f, 0.0f);
                    this.mTargetQuad.p2.set(0.0f, 1.0f);
                    this.mTargetQuad.p3.set(1.0f, 1.0f);
                    this.mProgram.setClearsOutput(false);
                    break;
                case 1:
                    if (f2 > 1.0f) {
                        float f3 = 0.5f / f2;
                        float f4 = 0.5f - f3;
                        this.mTargetQuad.p0.set(f4, 0.0f);
                        float f5 = f3 + 0.5f;
                        this.mTargetQuad.p1.set(f5, 0.0f);
                        this.mTargetQuad.p2.set(f4, 1.0f);
                        this.mTargetQuad.p3.set(f5, 1.0f);
                    } else {
                        float f6 = f2 * 0.5f;
                        float f7 = 0.5f - f6;
                        this.mTargetQuad.p0.set(0.0f, f7);
                        this.mTargetQuad.p1.set(1.0f, f7);
                        float f8 = f6 + 0.5f;
                        this.mTargetQuad.p2.set(0.0f, f8);
                        this.mTargetQuad.p3.set(1.0f, f8);
                    }
                    this.mProgram.setClearsOutput(true);
                    break;
                case 2:
                    if (f2 > 1.0f) {
                        float f9 = f2 * 0.5f;
                        float f10 = 0.5f - f9;
                        this.mTargetQuad.p0.set(0.0f, f10);
                        this.mTargetQuad.p1.set(1.0f, f10);
                        float f11 = f9 + 0.5f;
                        this.mTargetQuad.p2.set(0.0f, f11);
                        this.mTargetQuad.p3.set(1.0f, f11);
                    } else {
                        float f12 = 0.5f / f2;
                        float f13 = 0.5f - f12;
                        this.mTargetQuad.p0.set(f13, 0.0f);
                        float f14 = f12 + 0.5f;
                        this.mTargetQuad.p1.set(f14, 0.0f);
                        this.mTargetQuad.p2.set(f13, 1.0f);
                        this.mTargetQuad.p3.set(f14, 1.0f);
                    }
                    this.mProgram.setClearsOutput(true);
                    break;
                case 3:
                    this.mProgram.setSourceRegion(this.mSourceQuad);
                    break;
            }
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "UTR. quad: " + this.mTargetQuad);
            }
            this.mProgram.setTargetRegion(this.mTargetQuad);
        }
    }
}
