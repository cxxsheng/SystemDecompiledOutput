package android.filterpacks.videosrc;

/* loaded from: classes.dex */
public class MediaSource extends android.filterfw.core.Filter {
    private static final int NEWFRAME_TIMEOUT = 100;
    private static final int NEWFRAME_TIMEOUT_REPEAT = 10;
    private static final int PREP_TIMEOUT = 100;
    private static final int PREP_TIMEOUT_REPEAT = 100;
    private static final java.lang.String TAG = "MediaSource";
    private boolean mCompleted;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "context")
    private android.content.Context mContext;
    private android.filterfw.core.ShaderProgram mFrameExtractor;
    private final java.lang.String mFrameShader;
    private boolean mGotSize;
    private int mHeight;
    private final boolean mLogVerbose;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "loop")
    private boolean mLooping;
    private android.filterfw.core.GLFrame mMediaFrame;
    private android.media.MediaPlayer mMediaPlayer;
    private boolean mNewFrameAvailable;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "orientation")
    private int mOrientation;
    private boolean mOrientationUpdated;
    private android.filterfw.core.MutableFrameFormat mOutputFormat;
    private boolean mPaused;
    private boolean mPlaying;
    private boolean mPrepared;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "sourceIsUrl")
    private boolean mSelectedIsUrl;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "sourceAsset")
    private android.content.res.AssetFileDescriptor mSourceAsset;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "sourceUrl")
    private java.lang.String mSourceUrl;
    private android.graphics.SurfaceTexture mSurfaceTexture;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME)
    private float mVolume;

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = "waitForNewFrame")
    private boolean mWaitForNewFrame;
    private int mWidth;
    private android.media.MediaPlayer.OnCompletionListener onCompletionListener;
    private android.graphics.SurfaceTexture.OnFrameAvailableListener onMediaFrameAvailableListener;
    private android.media.MediaPlayer.OnPreparedListener onPreparedListener;
    private android.media.MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener;
    private static final float[] mSourceCoords_0 = {1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final float[] mSourceCoords_270 = {0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private static final float[] mSourceCoords_180 = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    private static final float[] mSourceCoords_90 = {1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f};

    public MediaSource(java.lang.String str) {
        super(str);
        this.mSourceUrl = "";
        this.mSourceAsset = null;
        this.mContext = null;
        this.mSelectedIsUrl = false;
        this.mWaitForNewFrame = true;
        this.mLooping = true;
        this.mVolume = 0.0f;
        this.mOrientation = 0;
        this.mFrameShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n";
        this.onVideoSizeChangedListener = new android.media.MediaPlayer.OnVideoSizeChangedListener() { // from class: android.filterpacks.videosrc.MediaSource.1
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(android.media.MediaPlayer mediaPlayer, int i, int i2) {
                if (android.filterpacks.videosrc.MediaSource.this.mLogVerbose) {
                    android.util.Log.v(android.filterpacks.videosrc.MediaSource.TAG, "MediaPlayer sent dimensions: " + i + " x " + i2);
                }
                if (!android.filterpacks.videosrc.MediaSource.this.mGotSize) {
                    if (android.filterpacks.videosrc.MediaSource.this.mOrientation == 0 || android.filterpacks.videosrc.MediaSource.this.mOrientation == 180) {
                        android.filterpacks.videosrc.MediaSource.this.mOutputFormat.setDimensions(i, i2);
                    } else {
                        android.filterpacks.videosrc.MediaSource.this.mOutputFormat.setDimensions(i2, i);
                    }
                    android.filterpacks.videosrc.MediaSource.this.mWidth = i;
                    android.filterpacks.videosrc.MediaSource.this.mHeight = i2;
                } else if (android.filterpacks.videosrc.MediaSource.this.mOutputFormat.getWidth() != i || android.filterpacks.videosrc.MediaSource.this.mOutputFormat.getHeight() != i2) {
                    android.util.Log.e(android.filterpacks.videosrc.MediaSource.TAG, "Multiple video size change events received!");
                }
                synchronized (android.filterpacks.videosrc.MediaSource.this) {
                    android.filterpacks.videosrc.MediaSource.this.mGotSize = true;
                    android.filterpacks.videosrc.MediaSource.this.notify();
                }
            }
        };
        this.onPreparedListener = new android.media.MediaPlayer.OnPreparedListener() { // from class: android.filterpacks.videosrc.MediaSource.2
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(android.media.MediaPlayer mediaPlayer) {
                if (android.filterpacks.videosrc.MediaSource.this.mLogVerbose) {
                    android.util.Log.v(android.filterpacks.videosrc.MediaSource.TAG, "MediaPlayer is prepared");
                }
                synchronized (android.filterpacks.videosrc.MediaSource.this) {
                    android.filterpacks.videosrc.MediaSource.this.mPrepared = true;
                    android.filterpacks.videosrc.MediaSource.this.notify();
                }
            }
        };
        this.onCompletionListener = new android.media.MediaPlayer.OnCompletionListener() { // from class: android.filterpacks.videosrc.MediaSource.3
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                if (android.filterpacks.videosrc.MediaSource.this.mLogVerbose) {
                    android.util.Log.v(android.filterpacks.videosrc.MediaSource.TAG, "MediaPlayer has completed playback");
                }
                synchronized (android.filterpacks.videosrc.MediaSource.this) {
                    android.filterpacks.videosrc.MediaSource.this.mCompleted = true;
                }
            }
        };
        this.onMediaFrameAvailableListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.filterpacks.videosrc.MediaSource.4
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                if (android.filterpacks.videosrc.MediaSource.this.mLogVerbose) {
                    android.util.Log.v(android.filterpacks.videosrc.MediaSource.TAG, "New frame from media player");
                }
                synchronized (android.filterpacks.videosrc.MediaSource.this) {
                    if (android.filterpacks.videosrc.MediaSource.this.mLogVerbose) {
                        android.util.Log.v(android.filterpacks.videosrc.MediaSource.TAG, "New frame: notify");
                    }
                    android.filterpacks.videosrc.MediaSource.this.mNewFrameAvailable = true;
                    android.filterpacks.videosrc.MediaSource.this.notify();
                    if (android.filterpacks.videosrc.MediaSource.this.mLogVerbose) {
                        android.util.Log.v(android.filterpacks.videosrc.MediaSource.TAG, "New frame: notify done");
                    }
                }
            }
        };
        this.mNewFrameAvailable = false;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("video", android.filterfw.format.ImageFormat.create(3, 3));
    }

    private void createFormats() {
        this.mOutputFormat = android.filterfw.format.ImageFormat.create(3, 3);
    }

    @Override // android.filterfw.core.Filter
    protected void prepare(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Preparing MediaSource");
        }
        this.mFrameExtractor = new android.filterfw.core.ShaderProgram(filterContext, "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n");
        this.mFrameExtractor.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        createFormats();
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Opening MediaSource");
            if (this.mSelectedIsUrl) {
                android.util.Log.v(TAG, "Current URL is " + this.mSourceUrl);
            } else {
                android.util.Log.v(TAG, "Current source is Asset!");
            }
        }
        this.mMediaFrame = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newBoundFrame(this.mOutputFormat, 104, 0L);
        this.mSurfaceTexture = new android.graphics.SurfaceTexture(this.mMediaFrame.getTextureId());
        if (!setupMediaPlayer(this.mSelectedIsUrl)) {
            throw new java.lang.RuntimeException("Error setting up MediaPlayer!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Processing new frame");
        }
        if (this.mMediaPlayer == null) {
            throw new java.lang.NullPointerException("Unexpected null media player!");
        }
        if (this.mCompleted) {
            closeOutputPort("video");
            return;
        }
        if (!this.mPlaying) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Waiting for preparation to complete");
            }
            int i = 0;
            do {
                if (!this.mGotSize || !this.mPrepared) {
                    try {
                        wait(100L);
                    } catch (java.lang.InterruptedException e) {
                    }
                    if (this.mCompleted) {
                        closeOutputPort("video");
                        return;
                    }
                    i++;
                } else {
                    if (this.mLogVerbose) {
                        android.util.Log.v(TAG, "Starting playback");
                    }
                    this.mMediaPlayer.start();
                }
            } while (i != 100);
            this.mMediaPlayer.release();
            throw new java.lang.RuntimeException("MediaPlayer timed out while preparing!");
        }
        if (!this.mPaused || !this.mPlaying) {
            if (this.mWaitForNewFrame) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Waiting for new frame");
                }
                int i2 = 0;
                while (!this.mNewFrameAvailable) {
                    if (i2 == 10) {
                        if (this.mCompleted) {
                            closeOutputPort("video");
                            return;
                        }
                        throw new java.lang.RuntimeException("Timeout waiting for new frame!");
                    }
                    try {
                        wait(100L);
                    } catch (java.lang.InterruptedException e2) {
                        if (this.mLogVerbose) {
                            android.util.Log.v(TAG, "interrupted");
                        }
                    }
                    i2++;
                }
                this.mNewFrameAvailable = false;
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Got new frame");
                }
            }
            this.mSurfaceTexture.updateTexImage();
            this.mOrientationUpdated = true;
        }
        if (this.mOrientationUpdated) {
            float[] fArr = new float[16];
            this.mSurfaceTexture.getTransformMatrix(fArr);
            float[] fArr2 = new float[16];
            switch (this.mOrientation) {
                case 90:
                    android.opengl.Matrix.multiplyMM(fArr2, 0, fArr, 0, mSourceCoords_90, 0);
                    break;
                case 180:
                    android.opengl.Matrix.multiplyMM(fArr2, 0, fArr, 0, mSourceCoords_180, 0);
                    break;
                case 270:
                    android.opengl.Matrix.multiplyMM(fArr2, 0, fArr, 0, mSourceCoords_270, 0);
                    break;
                default:
                    android.opengl.Matrix.multiplyMM(fArr2, 0, fArr, 0, mSourceCoords_0, 0);
                    break;
            }
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "OrientationHint = " + this.mOrientation);
                android.util.Log.v(TAG, java.lang.String.format("SetSourceRegion: %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f", java.lang.Float.valueOf(fArr2[4]), java.lang.Float.valueOf(fArr2[5]), java.lang.Float.valueOf(fArr2[0]), java.lang.Float.valueOf(fArr2[1]), java.lang.Float.valueOf(fArr2[12]), java.lang.Float.valueOf(fArr2[13]), java.lang.Float.valueOf(fArr2[8]), java.lang.Float.valueOf(fArr2[9])));
            }
            this.mFrameExtractor.setSourceRegion(fArr2[4], fArr2[5], fArr2[0], fArr2[1], fArr2[12], fArr2[13], fArr2[8], fArr2[9]);
            this.mOrientationUpdated = false;
        }
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        this.mFrameExtractor.process(this.mMediaFrame, newFrame);
        long timestamp = this.mSurfaceTexture.getTimestamp();
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Timestamp: " + (timestamp / 1.0E9d) + " s");
        }
        newFrame.setTimestamp(timestamp);
        pushOutput("video", newFrame);
        newFrame.release();
        this.mPlaying = true;
    }

    @Override // android.filterfw.core.Filter
    public void close(android.filterfw.core.FilterContext filterContext) {
        if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
        }
        this.mPrepared = false;
        this.mGotSize = false;
        this.mPlaying = false;
        this.mPaused = false;
        this.mCompleted = false;
        this.mNewFrameAvailable = false;
        this.mMediaPlayer.release();
        this.mMediaPlayer = null;
        this.mSurfaceTexture.release();
        this.mSurfaceTexture = null;
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "MediaSource closed");
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mMediaFrame != null) {
            this.mMediaFrame.release();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Parameter update");
        }
        if (str.equals("sourceUrl")) {
            if (isOpen()) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Opening new source URL");
                }
                if (this.mSelectedIsUrl) {
                    setupMediaPlayer(this.mSelectedIsUrl);
                    return;
                }
                return;
            }
            return;
        }
        if (str.equals("sourceAsset")) {
            if (isOpen()) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Opening new source FD");
                }
                if (!this.mSelectedIsUrl) {
                    setupMediaPlayer(this.mSelectedIsUrl);
                    return;
                }
                return;
            }
            return;
        }
        if (str.equals("loop")) {
            if (isOpen()) {
                this.mMediaPlayer.setLooping(this.mLooping);
                return;
            }
            return;
        }
        if (str.equals("sourceIsUrl")) {
            if (isOpen()) {
                if (this.mSelectedIsUrl) {
                    if (this.mLogVerbose) {
                        android.util.Log.v(TAG, "Opening new source URL");
                    }
                } else if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Opening new source Asset");
                }
                setupMediaPlayer(this.mSelectedIsUrl);
                return;
            }
            return;
        }
        if (str.equals(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME)) {
            if (isOpen()) {
                this.mMediaPlayer.setVolume(this.mVolume, this.mVolume);
            }
        } else if (str.equals("orientation") && this.mGotSize) {
            if (this.mOrientation == 0 || this.mOrientation == 180) {
                this.mOutputFormat.setDimensions(this.mWidth, this.mHeight);
            } else {
                this.mOutputFormat.setDimensions(this.mHeight, this.mWidth);
            }
            this.mOrientationUpdated = true;
        }
    }

    public synchronized void pauseVideo(boolean z) {
        if (isOpen()) {
            if (z && !this.mPaused) {
                this.mMediaPlayer.pause();
            } else if (!z && this.mPaused) {
                this.mMediaPlayer.start();
            }
        }
        this.mPaused = z;
    }

    private synchronized boolean setupMediaPlayer(boolean z) {
        this.mPrepared = false;
        this.mGotSize = false;
        this.mPlaying = false;
        this.mPaused = false;
        this.mCompleted = false;
        this.mNewFrameAvailable = false;
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Setting up playback.");
        }
        if (this.mMediaPlayer != null) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Resetting existing MediaPlayer.");
            }
            this.mMediaPlayer.reset();
        } else {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Creating new MediaPlayer.");
            }
            this.mMediaPlayer = new android.media.MediaPlayer();
        }
        if (this.mMediaPlayer == null) {
            throw new java.lang.RuntimeException("Unable to create a MediaPlayer!");
        }
        try {
            if (z) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Setting MediaPlayer source to URI " + this.mSourceUrl);
                }
                if (this.mContext == null) {
                    this.mMediaPlayer.setDataSource(this.mSourceUrl);
                } else {
                    this.mMediaPlayer.setDataSource(this.mContext, android.net.Uri.parse(this.mSourceUrl.toString()));
                }
            } else {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Setting MediaPlayer source to asset " + this.mSourceAsset);
                }
                this.mMediaPlayer.setDataSource(this.mSourceAsset.getFileDescriptor(), this.mSourceAsset.getStartOffset(), this.mSourceAsset.getLength());
            }
            this.mMediaPlayer.setLooping(this.mLooping);
            this.mMediaPlayer.setVolume(this.mVolume, this.mVolume);
            android.view.Surface surface = new android.view.Surface(this.mSurfaceTexture);
            this.mMediaPlayer.setSurface(surface);
            surface.release();
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.onVideoSizeChangedListener);
            this.mMediaPlayer.setOnPreparedListener(this.onPreparedListener);
            this.mMediaPlayer.setOnCompletionListener(this.onCompletionListener);
            this.mSurfaceTexture.setOnFrameAvailableListener(this.onMediaFrameAvailableListener);
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Preparing MediaPlayer.");
            }
            this.mMediaPlayer.prepareAsync();
        } catch (java.io.IOException e) {
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            if (z) {
                throw new java.lang.RuntimeException(java.lang.String.format("Unable to set MediaPlayer to URL %s!", this.mSourceUrl), e);
            }
            throw new java.lang.RuntimeException(java.lang.String.format("Unable to set MediaPlayer to asset %s!", this.mSourceAsset), e);
        } catch (java.lang.IllegalArgumentException e2) {
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            if (z) {
                throw new java.lang.RuntimeException(java.lang.String.format("Unable to set MediaPlayer to URL %s!", this.mSourceUrl), e2);
            }
            throw new java.lang.RuntimeException(java.lang.String.format("Unable to set MediaPlayer to asset %s!", this.mSourceAsset), e2);
        }
        return true;
    }
}
