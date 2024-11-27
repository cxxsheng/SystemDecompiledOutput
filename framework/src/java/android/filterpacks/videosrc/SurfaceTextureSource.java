package android.filterpacks.videosrc;

/* loaded from: classes.dex */
public class SurfaceTextureSource extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "closeOnTimeout")
    private boolean mCloseOnTimeout;
    private boolean mFirstFrame;
    private android.filterfw.core.ShaderProgram mFrameExtractor;
    private float[] mFrameTransform;

    @android.filterfw.core.GenerateFieldPort(name = "height")
    private int mHeight;
    private float[] mMappedCoords;
    private android.filterfw.core.GLFrame mMediaFrame;
    private android.os.ConditionVariable mNewFrameAvailable;
    private android.filterfw.core.MutableFrameFormat mOutputFormat;
    private final java.lang.String mRenderShader;

    @android.filterfw.core.GenerateFinalPort(name = "sourceListener")
    private android.filterpacks.videosrc.SurfaceTextureSource.SurfaceTextureSourceListener mSourceListener;
    private android.graphics.SurfaceTexture mSurfaceTexture;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "waitForNewFrame")
    private boolean mWaitForNewFrame;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "waitTimeout")
    private int mWaitTimeout;

    @android.filterfw.core.GenerateFieldPort(name = "width")
    private int mWidth;
    private android.graphics.SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener;
    private static final float[] mSourceCoords = {0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private static final java.lang.String TAG = "SurfaceTextureSource";
    private static final boolean mLogVerbose = android.util.Log.isLoggable(TAG, 2);

    public interface SurfaceTextureSourceListener {
        void onSurfaceTextureSourceReady(android.graphics.SurfaceTexture surfaceTexture);
    }

    public SurfaceTextureSource(java.lang.String str) {
        super(str);
        this.mWaitForNewFrame = true;
        this.mWaitTimeout = 1000;
        this.mCloseOnTimeout = false;
        this.mRenderShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n";
        this.onFrameAvailableListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.filterpacks.videosrc.SurfaceTextureSource.1
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                if (android.filterpacks.videosrc.SurfaceTextureSource.mLogVerbose) {
                    android.util.Log.v(android.filterpacks.videosrc.SurfaceTextureSource.TAG, "New frame from SurfaceTexture");
                }
                android.filterpacks.videosrc.SurfaceTextureSource.this.mNewFrameAvailable.open();
            }
        };
        this.mNewFrameAvailable = new android.os.ConditionVariable();
        this.mFrameTransform = new float[16];
        this.mMappedCoords = new float[16];
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("video", android.filterfw.format.ImageFormat.create(3, 3));
    }

    private void createFormats() {
        this.mOutputFormat = android.filterfw.format.ImageFormat.create(this.mWidth, this.mHeight, 3, 3);
    }

    @Override // android.filterfw.core.Filter
    protected void prepare(android.filterfw.core.FilterContext filterContext) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "Preparing SurfaceTextureSource");
        }
        createFormats();
        this.mMediaFrame = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newBoundFrame(this.mOutputFormat, 104, 0L);
        this.mFrameExtractor = new android.filterfw.core.ShaderProgram(filterContext, "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n");
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "Opening SurfaceTextureSource");
        }
        this.mSurfaceTexture = new android.graphics.SurfaceTexture(this.mMediaFrame.getTextureId());
        this.mSurfaceTexture.setOnFrameAvailableListener(this.onFrameAvailableListener);
        this.mSourceListener.onSurfaceTextureSourceReady(this.mSurfaceTexture);
        this.mFirstFrame = true;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "Processing new frame");
        }
        if (this.mWaitForNewFrame || this.mFirstFrame) {
            if (this.mWaitTimeout != 0) {
                if (!this.mNewFrameAvailable.block(this.mWaitTimeout)) {
                    if (!this.mCloseOnTimeout) {
                        throw new java.lang.RuntimeException("Timeout waiting for new frame");
                    }
                    if (mLogVerbose) {
                        android.util.Log.v(TAG, "Timeout waiting for a new frame. Closing.");
                    }
                    closeOutputPort("video");
                    return;
                }
            } else {
                this.mNewFrameAvailable.block();
            }
            this.mNewFrameAvailable.close();
            this.mFirstFrame = false;
        }
        this.mSurfaceTexture.updateTexImage();
        this.mSurfaceTexture.getTransformMatrix(this.mFrameTransform);
        android.opengl.Matrix.multiplyMM(this.mMappedCoords, 0, this.mFrameTransform, 0, mSourceCoords, 0);
        this.mFrameExtractor.setSourceRegion(this.mMappedCoords[0], this.mMappedCoords[1], this.mMappedCoords[4], this.mMappedCoords[5], this.mMappedCoords[8], this.mMappedCoords[9], this.mMappedCoords[12], this.mMappedCoords[13]);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        this.mFrameExtractor.process(this.mMediaFrame, newFrame);
        newFrame.setTimestamp(this.mSurfaceTexture.getTimestamp());
        pushOutput("video", newFrame);
        newFrame.release();
    }

    @Override // android.filterfw.core.Filter
    public void close(android.filterfw.core.FilterContext filterContext) {
        if (mLogVerbose) {
            android.util.Log.v(TAG, "SurfaceTextureSource closed");
        }
        this.mSourceListener.onSurfaceTextureSourceReady(null);
        this.mSurfaceTexture.release();
        this.mSurfaceTexture = null;
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mMediaFrame != null) {
            this.mMediaFrame.release();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (str.equals("width") || str.equals("height")) {
            this.mOutputFormat.setDimensions(this.mWidth, this.mHeight);
        }
    }
}
