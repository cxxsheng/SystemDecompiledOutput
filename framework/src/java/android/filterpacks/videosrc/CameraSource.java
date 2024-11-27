package android.filterpacks.videosrc;

/* loaded from: classes.dex */
public class CameraSource extends android.filterfw.core.Filter {
    private static final int NEWFRAME_TIMEOUT = 100;
    private static final int NEWFRAME_TIMEOUT_REPEAT = 10;
    private static final java.lang.String TAG = "CameraSource";
    private static final java.lang.String mFrameShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n";
    private static final float[] mSourceCoords = {0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private android.hardware.Camera mCamera;
    private android.filterfw.core.GLFrame mCameraFrame;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "id")
    private int mCameraId;
    private android.hardware.Camera.Parameters mCameraParameters;
    private float[] mCameraTransform;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "framerate")
    private int mFps;
    private android.filterfw.core.ShaderProgram mFrameExtractor;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "height")
    private int mHeight;
    private final boolean mLogVerbose;
    private float[] mMappedCoords;
    private boolean mNewFrameAvailable;
    private android.filterfw.core.MutableFrameFormat mOutputFormat;
    private android.graphics.SurfaceTexture mSurfaceTexture;

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = "waitForNewFrame")
    private boolean mWaitForNewFrame;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "width")
    private int mWidth;
    private android.graphics.SurfaceTexture.OnFrameAvailableListener onCameraFrameAvailableListener;

    public CameraSource(java.lang.String str) {
        super(str);
        this.mCameraId = 0;
        this.mWidth = 320;
        this.mHeight = 240;
        this.mFps = 30;
        this.mWaitForNewFrame = true;
        this.onCameraFrameAvailableListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.filterpacks.videosrc.CameraSource.1
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                if (android.filterpacks.videosrc.CameraSource.this.mLogVerbose) {
                    android.util.Log.v(android.filterpacks.videosrc.CameraSource.TAG, "New frame from camera");
                }
                synchronized (android.filterpacks.videosrc.CameraSource.this) {
                    android.filterpacks.videosrc.CameraSource.this.mNewFrameAvailable = true;
                    android.filterpacks.videosrc.CameraSource.this.notify();
                }
            }
        };
        this.mCameraTransform = new float[16];
        this.mMappedCoords = new float[16];
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("video", android.filterfw.format.ImageFormat.create(3, 3));
    }

    private void createFormats() {
        this.mOutputFormat = android.filterfw.format.ImageFormat.create(this.mWidth, this.mHeight, 3, 3);
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Preparing");
        }
        this.mFrameExtractor = new android.filterfw.core.ShaderProgram(filterContext, mFrameShader);
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Opening");
        }
        this.mCamera = android.hardware.Camera.open(this.mCameraId);
        getCameraParameters();
        this.mCamera.setParameters(this.mCameraParameters);
        createFormats();
        this.mCameraFrame = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newBoundFrame(this.mOutputFormat, 104, 0L);
        this.mSurfaceTexture = new android.graphics.SurfaceTexture(this.mCameraFrame.getTextureId());
        try {
            this.mCamera.setPreviewTexture(this.mSurfaceTexture);
            this.mSurfaceTexture.setOnFrameAvailableListener(this.onCameraFrameAvailableListener);
            this.mNewFrameAvailable = false;
            this.mCamera.startPreview();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Could not bind camera surface texture: " + e.getMessage() + "!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Processing new frame");
        }
        if (this.mWaitForNewFrame) {
            while (!this.mNewFrameAvailable) {
                try {
                    wait(100L);
                } catch (java.lang.InterruptedException e) {
                    if (this.mLogVerbose) {
                        android.util.Log.v(TAG, "Interrupted while waiting for new frame");
                    }
                }
            }
            this.mNewFrameAvailable = false;
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Got new frame");
            }
        }
        this.mSurfaceTexture.updateTexImage();
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Using frame extractor in thread: " + java.lang.Thread.currentThread());
        }
        this.mSurfaceTexture.getTransformMatrix(this.mCameraTransform);
        android.opengl.Matrix.multiplyMM(this.mMappedCoords, 0, this.mCameraTransform, 0, mSourceCoords, 0);
        this.mFrameExtractor.setSourceRegion(this.mMappedCoords[0], this.mMappedCoords[1], this.mMappedCoords[4], this.mMappedCoords[5], this.mMappedCoords[8], this.mMappedCoords[9], this.mMappedCoords[12], this.mMappedCoords[13]);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        this.mFrameExtractor.process(this.mCameraFrame, newFrame);
        long timestamp = this.mSurfaceTexture.getTimestamp();
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Timestamp: " + (timestamp / 1.0E9d) + " s");
        }
        newFrame.setTimestamp(timestamp);
        pushOutput("video", newFrame);
        newFrame.release();
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Done processing new frame");
        }
    }

    @Override // android.filterfw.core.Filter
    public void close(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Closing");
        }
        this.mCamera.release();
        this.mCamera = null;
        this.mSurfaceTexture.release();
        this.mSurfaceTexture = null;
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mCameraFrame != null) {
            this.mCameraFrame.release();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (str.equals("framerate")) {
            getCameraParameters();
            int[] findClosestFpsRange = findClosestFpsRange(this.mFps, this.mCameraParameters);
            this.mCameraParameters.setPreviewFpsRange(findClosestFpsRange[0], findClosestFpsRange[1]);
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    public synchronized android.hardware.Camera.Parameters getCameraParameters() {
        boolean z;
        if (this.mCameraParameters == null) {
            if (this.mCamera != null) {
                z = false;
            } else {
                this.mCamera = android.hardware.Camera.open(this.mCameraId);
                z = true;
            }
            this.mCameraParameters = this.mCamera.getParameters();
            if (z) {
                this.mCamera.release();
                this.mCamera = null;
            }
        }
        int[] findClosestSize = findClosestSize(this.mWidth, this.mHeight, this.mCameraParameters);
        this.mWidth = findClosestSize[0];
        this.mHeight = findClosestSize[1];
        this.mCameraParameters.setPreviewSize(this.mWidth, this.mHeight);
        int[] findClosestFpsRange = findClosestFpsRange(this.mFps, this.mCameraParameters);
        this.mCameraParameters.setPreviewFpsRange(findClosestFpsRange[0], findClosestFpsRange[1]);
        return this.mCameraParameters;
    }

    public synchronized void setCameraParameters(android.hardware.Camera.Parameters parameters) {
        parameters.setPreviewSize(this.mWidth, this.mHeight);
        this.mCameraParameters = parameters;
        if (isOpen()) {
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    private int[] findClosestSize(int i, int i2, android.hardware.Camera.Parameters parameters) {
        java.util.List<android.hardware.Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        int i3 = supportedPreviewSizes.get(0).width;
        int i4 = supportedPreviewSizes.get(0).height;
        int i5 = -1;
        int i6 = -1;
        for (android.hardware.Camera.Size size : supportedPreviewSizes) {
            if (size.width <= i && size.height <= i2 && size.width >= i5 && size.height >= i6) {
                i5 = size.width;
                i6 = size.height;
            }
            if (size.width < i3 && size.height < i4) {
                i3 = size.width;
                i4 = size.height;
            }
        }
        if (i5 != -1) {
            i3 = i5;
            i4 = i6;
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Requested resolution: (" + i + ", " + i2 + "). Closest match: (" + i3 + ", " + i4 + ").");
        }
        return new int[]{i3, i4};
    }

    private int[] findClosestFpsRange(int i, android.hardware.Camera.Parameters parameters) {
        java.util.List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        int[] iArr = supportedPreviewFpsRange.get(0);
        for (int[] iArr2 : supportedPreviewFpsRange) {
            int i2 = i * 1000;
            if (iArr2[0] < i2 && iArr2[1] > i2 && iArr2[0] > iArr[0] && iArr2[1] < iArr[1]) {
                iArr = iArr2;
            }
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Requested fps: " + i + ".Closest frame rate range: [" + (iArr[0] / 1000.0d) + "," + (iArr[1] / 1000.0d) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return iArr;
    }
}
