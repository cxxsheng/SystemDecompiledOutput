package android.filterpacks.ui;

/* loaded from: classes.dex */
public class SurfaceTargetFilter extends android.filterfw.core.Filter {
    private static final java.lang.String TAG = "SurfaceRenderFilter";
    private final int RENDERMODE_FILL_CROP;
    private final int RENDERMODE_FIT;
    private final int RENDERMODE_STRETCH;
    private float mAspectRatio;
    private android.filterfw.core.GLEnvironment mGlEnv;
    private boolean mLogVerbose;
    private android.filterfw.core.ShaderProgram mProgram;
    private int mRenderMode;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "renderMode")
    private java.lang.String mRenderModeString;
    private android.filterfw.core.GLFrame mScreen;

    @android.filterfw.core.GenerateFieldPort(name = "oheight")
    private int mScreenHeight;

    @android.filterfw.core.GenerateFieldPort(name = "owidth")
    private int mScreenWidth;

    @android.filterfw.core.GenerateFinalPort(name = "surface")
    private android.view.Surface mSurface;
    private int mSurfaceId;

    public SurfaceTargetFilter(java.lang.String str) {
        super(str);
        this.RENDERMODE_STRETCH = 0;
        this.RENDERMODE_FIT = 1;
        this.RENDERMODE_FILL_CROP = 2;
        this.mRenderMode = 1;
        this.mAspectRatio = 1.0f;
        this.mSurfaceId = -1;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        if (this.mSurface == null) {
            throw new java.lang.RuntimeException("NULL Surface passed to SurfaceTargetFilter");
        }
        addMaskedInputPort("frame", android.filterfw.format.ImageFormat.create(3));
    }

    public void updateRenderMode() {
        if (this.mRenderModeString != null) {
            if (this.mRenderModeString.equals("stretch")) {
                this.mRenderMode = 0;
            } else if (this.mRenderModeString.equals("fit")) {
                this.mRenderMode = 1;
            } else if (this.mRenderModeString.equals("fill_crop")) {
                this.mRenderMode = 2;
            } else {
                throw new java.lang.RuntimeException("Unknown render mode '" + this.mRenderModeString + "'!");
            }
        }
        updateTargetRect();
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        this.mGlEnv = filterContext.getGLEnvironment();
        this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        this.mProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearsOutput(true);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        this.mScreen = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newBoundFrame(android.filterfw.format.ImageFormat.create(this.mScreenWidth, this.mScreenHeight, 3, 3), 101, 0L);
        updateRenderMode();
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        registerSurface();
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        boolean z;
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Starting frame processing");
        }
        android.filterfw.core.Frame pullInput = pullInput("frame");
        float width = pullInput.getFormat().getWidth() / pullInput.getFormat().getHeight();
        if (width != this.mAspectRatio) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "New aspect ratio: " + width + ", previously: " + this.mAspectRatio);
            }
            this.mAspectRatio = width;
            updateTargetRect();
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Got input format: " + pullInput.getFormat());
        }
        if (pullInput.getFormat().getTarget() != 3) {
            pullInput = filterContext.getFrameManager().duplicateFrameToTarget(pullInput, 3);
            z = true;
        } else {
            z = false;
        }
        this.mGlEnv.activateSurfaceWithId(this.mSurfaceId);
        this.mProgram.process(pullInput, this.mScreen);
        this.mGlEnv.swapBuffers();
        if (z) {
            pullInput.release();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        this.mScreen.setViewport(0, 0, this.mScreenWidth, this.mScreenHeight);
        updateTargetRect();
    }

    @Override // android.filterfw.core.Filter
    public void close(android.filterfw.core.FilterContext filterContext) {
        unregisterSurface();
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mScreen != null) {
            this.mScreen.release();
        }
    }

    private void updateTargetRect() {
        if (this.mScreenWidth > 0 && this.mScreenHeight > 0 && this.mProgram != null) {
            float f = (this.mScreenWidth / this.mScreenHeight) / this.mAspectRatio;
            switch (this.mRenderMode) {
                case 0:
                    this.mProgram.setTargetRect(0.0f, 0.0f, 1.0f, 1.0f);
                    break;
                case 1:
                    if (f > 1.0f) {
                        this.mProgram.setTargetRect(0.5f - (0.5f / f), 0.0f, 1.0f / f, 1.0f);
                        break;
                    } else {
                        this.mProgram.setTargetRect(0.0f, 0.5f - (f * 0.5f), 1.0f, f);
                        break;
                    }
                case 2:
                    if (f > 1.0f) {
                        this.mProgram.setTargetRect(0.0f, 0.5f - (f * 0.5f), 1.0f, f);
                        break;
                    } else {
                        this.mProgram.setTargetRect(0.5f - (0.5f / f), 0.0f, 1.0f / f, 1.0f);
                        break;
                    }
            }
        }
    }

    private void registerSurface() {
        this.mSurfaceId = this.mGlEnv.registerSurface(this.mSurface);
        if (this.mSurfaceId < 0) {
            throw new java.lang.RuntimeException("Could not register Surface: " + this.mSurface);
        }
    }

    private void unregisterSurface() {
        if (this.mSurfaceId > 0) {
            this.mGlEnv.unregisterSurfaceId(this.mSurfaceId);
        }
    }
}
