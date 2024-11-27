package android.filterpacks.videosink;

/* loaded from: classes.dex */
public class MediaEncoderFilter extends android.filterfw.core.Filter {
    private static final int NO_AUDIO_SOURCE = -1;
    private static final java.lang.String TAG = "MediaEncoderFilter";

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "audioSource")
    private int mAudioSource;
    private boolean mCaptureTimeLapse;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "errorListener")
    private android.media.MediaRecorder.OnErrorListener mErrorListener;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "outputFileDescriptor")
    private java.io.FileDescriptor mFd;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "framerate")
    private int mFps;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "height")
    private int mHeight;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "infoListener")
    private android.media.MediaRecorder.OnInfoListener mInfoListener;
    private long mLastTimeLapseFrameRealTimestampNs;
    private boolean mLogVerbose;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maxDurationMs")
    private int mMaxDurationMs;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maxFileSize")
    private long mMaxFileSize;
    private android.media.MediaRecorder mMediaRecorder;
    private int mNumFramesEncoded;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "orientationHint")
    private int mOrientationHint;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "outputFile")
    private java.lang.String mOutputFile;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "outputFormat")
    private int mOutputFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "recordingProfile")
    private android.media.CamcorderProfile mProfile;
    private android.filterfw.core.ShaderProgram mProgram;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "recording")
    private boolean mRecording;
    private boolean mRecordingActive;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "recordingDoneListener")
    private android.filterpacks.videosink.MediaEncoderFilter.OnRecordingDoneListener mRecordingDoneListener;
    private android.filterfw.core.GLFrame mScreen;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "inputRegion")
    private android.filterfw.geometry.Quad mSourceRegion;
    private int mSurfaceId;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "timelapseRecordingIntervalUs")
    private long mTimeBetweenTimeLapseFrameCaptureUs;
    private long mTimestampNs;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "videoEncoder")
    private int mVideoEncoder;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "width")
    private int mWidth;

    public interface OnRecordingDoneListener {
        void onRecordingDone();
    }

    public MediaEncoderFilter(java.lang.String str) {
        super(str);
        this.mRecording = true;
        this.mOutputFile = new java.lang.String("/sdcard/MediaEncoderOut.mp4");
        this.mFd = null;
        this.mAudioSource = -1;
        this.mInfoListener = null;
        this.mErrorListener = null;
        this.mRecordingDoneListener = null;
        this.mOrientationHint = 0;
        this.mProfile = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mFps = 30;
        this.mOutputFormat = 2;
        this.mVideoEncoder = 2;
        this.mMaxFileSize = 0L;
        this.mMaxDurationMs = 0;
        this.mTimeBetweenTimeLapseFrameCaptureUs = 0L;
        this.mRecordingActive = false;
        this.mTimestampNs = 0L;
        this.mLastTimeLapseFrameRealTimestampNs = 0L;
        this.mNumFramesEncoded = 0;
        this.mCaptureTimeLapse = false;
        this.mSourceRegion = new android.filterfw.geometry.Quad(new android.filterfw.geometry.Point(0.0f, 0.0f), new android.filterfw.geometry.Point(1.0f, 0.0f), new android.filterfw.geometry.Point(0.0f, 1.0f), new android.filterfw.geometry.Point(1.0f, 1.0f));
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("videoframe", android.filterfw.format.ImageFormat.create(3, 3));
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Port " + str + " has been updated");
        }
        if (str.equals("recording")) {
            return;
        }
        if (str.equals("inputRegion")) {
            if (isOpen()) {
                updateSourceRegion();
            }
        } else if (isOpen() && this.mRecordingActive) {
            throw new java.lang.RuntimeException("Cannot change recording parameters when the filter is recording!");
        }
    }

    private void updateSourceRegion() {
        android.filterfw.geometry.Quad quad = new android.filterfw.geometry.Quad();
        quad.p0 = this.mSourceRegion.p2;
        quad.p1 = this.mSourceRegion.p3;
        quad.p2 = this.mSourceRegion.p0;
        quad.p3 = this.mSourceRegion.p1;
        this.mProgram.setSourceRegion(quad);
    }

    private void updateMediaRecorderParams() {
        this.mCaptureTimeLapse = this.mTimeBetweenTimeLapseFrameCaptureUs > 0;
        this.mMediaRecorder.setVideoSource(2);
        if (!this.mCaptureTimeLapse && this.mAudioSource != -1) {
            this.mMediaRecorder.setAudioSource(this.mAudioSource);
        }
        if (this.mProfile != null) {
            this.mMediaRecorder.setProfile(this.mProfile);
            this.mFps = this.mProfile.videoFrameRate;
            if (this.mWidth > 0 && this.mHeight > 0) {
                this.mMediaRecorder.setVideoSize(this.mWidth, this.mHeight);
            }
        } else {
            this.mMediaRecorder.setOutputFormat(this.mOutputFormat);
            this.mMediaRecorder.setVideoEncoder(this.mVideoEncoder);
            this.mMediaRecorder.setVideoSize(this.mWidth, this.mHeight);
            this.mMediaRecorder.setVideoFrameRate(this.mFps);
        }
        this.mMediaRecorder.setOrientationHint(this.mOrientationHint);
        this.mMediaRecorder.setOnInfoListener(this.mInfoListener);
        this.mMediaRecorder.setOnErrorListener(this.mErrorListener);
        if (this.mFd != null) {
            this.mMediaRecorder.setOutputFile(this.mFd);
        } else {
            this.mMediaRecorder.setOutputFile(this.mOutputFile);
        }
        try {
            this.mMediaRecorder.setMaxFileSize(this.mMaxFileSize);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Setting maxFileSize on MediaRecorder unsuccessful! " + e.getMessage());
        }
        this.mMediaRecorder.setMaxDuration(this.mMaxDurationMs);
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Preparing");
        }
        this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        this.mRecordingActive = false;
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Opening");
        }
        updateSourceRegion();
        if (this.mRecording) {
            startRecording(filterContext);
        }
    }

    private void startRecording(android.filterfw.core.FilterContext filterContext) {
        int i;
        int i2;
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Starting recording");
        }
        android.filterfw.core.MutableFrameFormat mutableFrameFormat = new android.filterfw.core.MutableFrameFormat(2, 3);
        mutableFrameFormat.setBytesPerSample(4);
        boolean z = this.mWidth > 0 && this.mHeight > 0;
        if (this.mProfile != null && !z) {
            i = this.mProfile.videoFrameWidth;
            i2 = this.mProfile.videoFrameHeight;
        } else {
            i = this.mWidth;
            i2 = this.mHeight;
        }
        mutableFrameFormat.setDimensions(i, i2);
        this.mScreen = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 101, 0L);
        this.mMediaRecorder = new android.media.MediaRecorder();
        updateMediaRecorderParams();
        try {
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.start();
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Open: registering surface from Mediarecorder");
            }
            this.mSurfaceId = filterContext.getGLEnvironment().registerSurfaceFromMediaRecorder(this.mMediaRecorder);
            this.mNumFramesEncoded = 0;
            this.mRecordingActive = true;
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("IOException inMediaRecorder.prepare()!", e);
        } catch (java.lang.IllegalStateException e2) {
            throw e2;
        } catch (java.lang.Exception e3) {
            throw new java.lang.RuntimeException("Unknown Exception inMediaRecorder.prepare()!", e3);
        }
    }

    public boolean skipFrameAndModifyTimestamp(long j) {
        if (this.mNumFramesEncoded == 0) {
            this.mLastTimeLapseFrameRealTimestampNs = j;
            this.mTimestampNs = j;
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "timelapse: FIRST frame, last real t= " + this.mLastTimeLapseFrameRealTimestampNs + ", setting t = " + this.mTimestampNs);
            }
            return false;
        }
        if (this.mNumFramesEncoded >= 2 && j < this.mLastTimeLapseFrameRealTimestampNs + (this.mTimeBetweenTimeLapseFrameCaptureUs * 1000)) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "timelapse: skipping intermediate frame");
                return true;
            }
            return true;
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "timelapse: encoding frame, Timestamp t = " + j + ", last real t= " + this.mLastTimeLapseFrameRealTimestampNs + ", interval = " + this.mTimeBetweenTimeLapseFrameCaptureUs);
        }
        this.mLastTimeLapseFrameRealTimestampNs = j;
        this.mTimestampNs += 1000000000 / this.mFps;
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "timelapse: encoding frame, setting t = " + this.mTimestampNs + ", delta t = " + (1000000000 / this.mFps) + ", fps = " + this.mFps);
        }
        return false;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.GLEnvironment gLEnvironment = filterContext.getGLEnvironment();
        android.filterfw.core.Frame pullInput = pullInput("videoframe");
        if (!this.mRecordingActive && this.mRecording) {
            startRecording(filterContext);
        }
        if (this.mRecordingActive && !this.mRecording) {
            stopRecording(filterContext);
        }
        if (this.mRecordingActive) {
            if (this.mCaptureTimeLapse) {
                if (skipFrameAndModifyTimestamp(pullInput.getTimestamp())) {
                    return;
                }
            } else {
                this.mTimestampNs = pullInput.getTimestamp();
            }
            gLEnvironment.activateSurfaceWithId(this.mSurfaceId);
            this.mProgram.process(pullInput, this.mScreen);
            gLEnvironment.setSurfaceTimestamp(this.mTimestampNs);
            gLEnvironment.swapBuffers();
            this.mNumFramesEncoded++;
        }
    }

    private void stopRecording(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Stopping recording");
        }
        this.mRecordingActive = false;
        this.mNumFramesEncoded = 0;
        android.filterfw.core.GLEnvironment gLEnvironment = filterContext.getGLEnvironment();
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, java.lang.String.format("Unregistering surface %d", java.lang.Integer.valueOf(this.mSurfaceId)));
        }
        gLEnvironment.unregisterSurfaceId(this.mSurfaceId);
        try {
            this.mMediaRecorder.stop();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.mScreen.release();
            this.mScreen = null;
            if (this.mRecordingDoneListener != null) {
                this.mRecordingDoneListener.onRecordingDone();
            }
        } catch (java.lang.RuntimeException e) {
            throw new android.filterpacks.videosink.MediaRecorderStopException("MediaRecorder.stop() failed!", e);
        }
    }

    @Override // android.filterfw.core.Filter
    public void close(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Closing");
        }
        if (this.mRecordingActive) {
            stopRecording(filterContext);
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mMediaRecorder != null) {
            this.mMediaRecorder.release();
        }
        if (this.mScreen != null) {
            this.mScreen.release();
        }
    }
}
