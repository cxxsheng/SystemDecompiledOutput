package android.widget;

/* loaded from: classes4.dex */
public class VideoView extends android.view.SurfaceView implements android.widget.MediaController.MediaPlayerControl, android.media.SubtitleController.Anchor {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static final java.lang.String TAG = "VideoView";
    private android.media.AudioAttributes mAudioAttributes;
    private int mAudioFocusType;
    private android.media.AudioManager mAudioManager;
    private int mAudioSession;
    private android.media.MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private android.media.MediaPlayer.OnCompletionListener mCompletionListener;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private android.media.MediaPlayer.OnErrorListener mErrorListener;
    private java.util.Map<java.lang.String, java.lang.String> mHeaders;
    private android.media.MediaPlayer.OnInfoListener mInfoListener;
    private android.widget.MediaController mMediaController;
    private android.media.MediaPlayer mMediaPlayer;
    private android.media.MediaPlayer.OnCompletionListener mOnCompletionListener;
    private android.media.MediaPlayer.OnErrorListener mOnErrorListener;
    private android.media.MediaPlayer.OnInfoListener mOnInfoListener;
    private android.media.MediaPlayer.OnPreparedListener mOnPreparedListener;
    private final java.util.Vector<android.util.Pair<java.io.InputStream, android.media.MediaFormat>> mPendingSubtitleTracks;
    android.media.MediaPlayer.OnPreparedListener mPreparedListener;
    android.view.SurfaceHolder.Callback mSHCallback;
    private int mSeekWhenPrepared;
    android.media.MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
    private android.media.SubtitleTrack.RenderingWidget mSubtitleWidget;
    private android.media.SubtitleTrack.RenderingWidget.OnChangedListener mSubtitlesChangedListener;
    private int mSurfaceHeight;
    private android.view.SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private android.net.Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;

    public VideoView(android.content.Context context) {
        this(context, null);
    }

    public VideoView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public VideoView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingSubtitleTracks = new java.util.Vector<>();
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mAudioFocusType = 1;
        this.mSizeChangedListener = new android.media.MediaPlayer.OnVideoSizeChangedListener() { // from class: android.widget.VideoView.1
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(android.media.MediaPlayer mediaPlayer, int i3, int i4) {
                android.widget.VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                android.widget.VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                if (android.widget.VideoView.this.mVideoWidth != 0 && android.widget.VideoView.this.mVideoHeight != 0) {
                    android.widget.VideoView.this.getHolder().setFixedSize(android.widget.VideoView.this.mVideoWidth, android.widget.VideoView.this.mVideoHeight);
                    android.widget.VideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new android.media.MediaPlayer.OnPreparedListener() { // from class: android.widget.VideoView.2
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(android.media.MediaPlayer mediaPlayer) {
                android.widget.VideoView.this.mCurrentState = 2;
                android.media.Metadata metadata = mediaPlayer.getMetadata(false, false);
                if (metadata != null) {
                    android.widget.VideoView.this.mCanPause = !metadata.has(1) || metadata.getBoolean(1);
                    android.widget.VideoView.this.mCanSeekBack = !metadata.has(2) || metadata.getBoolean(2);
                    android.widget.VideoView.this.mCanSeekForward = !metadata.has(3) || metadata.getBoolean(3);
                } else {
                    android.widget.VideoView videoView = android.widget.VideoView.this;
                    android.widget.VideoView videoView2 = android.widget.VideoView.this;
                    android.widget.VideoView.this.mCanSeekForward = true;
                    videoView2.mCanSeekBack = true;
                    videoView.mCanPause = true;
                }
                if (android.widget.VideoView.this.mOnPreparedListener != null) {
                    android.widget.VideoView.this.mOnPreparedListener.onPrepared(android.widget.VideoView.this.mMediaPlayer);
                }
                if (android.widget.VideoView.this.mMediaController != null) {
                    android.widget.VideoView.this.mMediaController.setEnabled(true);
                }
                android.widget.VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                android.widget.VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                int i3 = android.widget.VideoView.this.mSeekWhenPrepared;
                if (i3 != 0) {
                    android.widget.VideoView.this.seekTo(i3);
                }
                if (android.widget.VideoView.this.mVideoWidth == 0 || android.widget.VideoView.this.mVideoHeight == 0) {
                    if (android.widget.VideoView.this.mTargetState == 3) {
                        android.widget.VideoView.this.start();
                        return;
                    }
                    return;
                }
                android.widget.VideoView.this.getHolder().setFixedSize(android.widget.VideoView.this.mVideoWidth, android.widget.VideoView.this.mVideoHeight);
                if (android.widget.VideoView.this.mSurfaceWidth == android.widget.VideoView.this.mVideoWidth && android.widget.VideoView.this.mSurfaceHeight == android.widget.VideoView.this.mVideoHeight) {
                    if (android.widget.VideoView.this.mTargetState == 3) {
                        android.widget.VideoView.this.start();
                        if (android.widget.VideoView.this.mMediaController != null) {
                            android.widget.VideoView.this.mMediaController.show();
                            return;
                        }
                        return;
                    }
                    if (!android.widget.VideoView.this.isPlaying()) {
                        if ((i3 != 0 || android.widget.VideoView.this.getCurrentPosition() > 0) && android.widget.VideoView.this.mMediaController != null) {
                            android.widget.VideoView.this.mMediaController.show(0);
                        }
                    }
                }
            }
        };
        this.mCompletionListener = new android.media.MediaPlayer.OnCompletionListener() { // from class: android.widget.VideoView.3
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                android.widget.VideoView.this.mCurrentState = 5;
                android.widget.VideoView.this.mTargetState = 5;
                if (android.widget.VideoView.this.mMediaController != null) {
                    android.widget.VideoView.this.mMediaController.hide();
                }
                if (android.widget.VideoView.this.mOnCompletionListener != null) {
                    android.widget.VideoView.this.mOnCompletionListener.onCompletion(android.widget.VideoView.this.mMediaPlayer);
                }
                if (android.widget.VideoView.this.mAudioFocusType != 0) {
                    android.widget.VideoView.this.mAudioManager.abandonAudioFocus(null);
                }
            }
        };
        this.mInfoListener = new android.media.MediaPlayer.OnInfoListener() { // from class: android.widget.VideoView.4
            @Override // android.media.MediaPlayer.OnInfoListener
            public boolean onInfo(android.media.MediaPlayer mediaPlayer, int i3, int i4) {
                if (android.widget.VideoView.this.mOnInfoListener != null) {
                    android.widget.VideoView.this.mOnInfoListener.onInfo(mediaPlayer, i3, i4);
                    return true;
                }
                return true;
            }
        };
        this.mErrorListener = new android.media.MediaPlayer.OnErrorListener() { // from class: android.widget.VideoView.5
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(android.media.MediaPlayer mediaPlayer, int i3, int i4) {
                int i5;
                android.util.Log.d(android.widget.VideoView.TAG, "Error: " + i3 + "," + i4);
                android.widget.VideoView.this.mCurrentState = -1;
                android.widget.VideoView.this.mTargetState = -1;
                if (android.widget.VideoView.this.mMediaController != null) {
                    android.widget.VideoView.this.mMediaController.hide();
                }
                if ((android.widget.VideoView.this.mOnErrorListener == null || !android.widget.VideoView.this.mOnErrorListener.onError(android.widget.VideoView.this.mMediaPlayer, i3, i4)) && android.widget.VideoView.this.getWindowToken() != null) {
                    android.widget.VideoView.this.mContext.getResources();
                    if (i3 == 200) {
                        i5 = 17039381;
                    } else {
                        i5 = 17039377;
                    }
                    new android.app.AlertDialog.Builder(android.widget.VideoView.this.mContext).setMessage(i5).setPositiveButton(17039376, new android.content.DialogInterface.OnClickListener() { // from class: android.widget.VideoView.5.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(android.content.DialogInterface dialogInterface, int i6) {
                            if (android.widget.VideoView.this.mOnCompletionListener != null) {
                                android.widget.VideoView.this.mOnCompletionListener.onCompletion(android.widget.VideoView.this.mMediaPlayer);
                            }
                        }
                    }).setCancelable(false).show();
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new android.media.MediaPlayer.OnBufferingUpdateListener() { // from class: android.widget.VideoView.6
            @Override // android.media.MediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(android.media.MediaPlayer mediaPlayer, int i3) {
                android.widget.VideoView.this.mCurrentBufferPercentage = i3;
            }
        };
        this.mSHCallback = new android.view.SurfaceHolder.Callback() { // from class: android.widget.VideoView.7
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i3, int i4, int i5) {
                android.widget.VideoView.this.mSurfaceWidth = i4;
                android.widget.VideoView.this.mSurfaceHeight = i5;
                boolean z = android.widget.VideoView.this.mTargetState == 3;
                boolean z2 = android.widget.VideoView.this.mVideoWidth == i4 && android.widget.VideoView.this.mVideoHeight == i5;
                if (android.widget.VideoView.this.mMediaPlayer != null && z && z2) {
                    if (android.widget.VideoView.this.mSeekWhenPrepared != 0) {
                        android.widget.VideoView.this.seekTo(android.widget.VideoView.this.mSeekWhenPrepared);
                    }
                    android.widget.VideoView.this.start();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
                android.widget.VideoView.this.mSurfaceHolder = surfaceHolder;
                android.widget.VideoView.this.openVideo();
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
                android.widget.VideoView.this.mSurfaceHolder = null;
                if (android.widget.VideoView.this.mMediaController != null) {
                    android.widget.VideoView.this.mMediaController.hide();
                }
                android.widget.VideoView.this.release(true);
            }
        };
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService("audio");
        this.mAudioAttributes = new android.media.AudioAttributes.Builder().setUsage(1).setContentType(3).build();
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x006d, code lost:
    
        if (r1 > r6) goto L26;
     */
    @Override // android.view.SurfaceView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int i3;
        int defaultSize = getDefaultSize(this.mVideoWidth, i);
        int defaultSize2 = getDefaultSize(this.mVideoHeight, i2);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            int mode = android.view.View.MeasureSpec.getMode(i);
            int size = android.view.View.MeasureSpec.getSize(i);
            int mode2 = android.view.View.MeasureSpec.getMode(i2);
            int size2 = android.view.View.MeasureSpec.getSize(i2);
            if (mode != 1073741824 || mode2 != 1073741824) {
                if (mode == 1073741824) {
                    int i4 = (this.mVideoHeight * size) / this.mVideoWidth;
                    if (mode2 != Integer.MIN_VALUE || i4 <= size2) {
                        defaultSize2 = i4;
                        defaultSize = size;
                    }
                    defaultSize = size;
                } else {
                    if (mode2 == 1073741824) {
                        i3 = (this.mVideoWidth * size2) / this.mVideoHeight;
                        if (mode == Integer.MIN_VALUE) {
                        }
                    } else {
                        int i5 = this.mVideoWidth;
                        int i6 = this.mVideoHeight;
                        if (mode2 == Integer.MIN_VALUE && i6 > size2) {
                            i3 = (this.mVideoWidth * size2) / this.mVideoHeight;
                        } else {
                            i3 = i5;
                            size2 = i6;
                        }
                        if (mode == Integer.MIN_VALUE && i3 > size) {
                            defaultSize2 = (this.mVideoHeight * size) / this.mVideoWidth;
                            defaultSize = size;
                        }
                    }
                    defaultSize = i3;
                }
            } else if (this.mVideoWidth * size2 < this.mVideoHeight * size) {
                defaultSize = (this.mVideoWidth * size2) / this.mVideoHeight;
                defaultSize2 = size2;
            } else {
                if (this.mVideoWidth * size2 > this.mVideoHeight * size) {
                    defaultSize2 = (this.mVideoHeight * size) / this.mVideoWidth;
                    defaultSize = size;
                }
                defaultSize = size;
            }
            defaultSize2 = size2;
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    @Override // android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.VideoView.class.getName();
    }

    public int resolveAdjustedSize(int i, int i2) {
        return getDefaultSize(i, i2);
    }

    public void setVideoPath(java.lang.String str) {
        setVideoURI(android.net.Uri.parse(str));
    }

    public void setVideoURI(android.net.Uri uri) {
        setVideoURI(uri, null);
    }

    public void setVideoURI(android.net.Uri uri, java.util.Map<java.lang.String, java.lang.String> map) {
        this.mUri = uri;
        this.mHeaders = map;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public void setAudioFocusRequest(int i) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4) {
            throw new java.lang.IllegalArgumentException("Illegal audio focus type " + i);
        }
        this.mAudioFocusType = i;
    }

    public void setAudioAttributes(android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes");
        }
        this.mAudioAttributes = audioAttributes;
    }

    public void addSubtitleSource(java.io.InputStream inputStream, android.media.MediaFormat mediaFormat) {
        if (this.mMediaPlayer == null) {
            this.mPendingSubtitleTracks.add(android.util.Pair.create(inputStream, mediaFormat));
            return;
        }
        try {
            this.mMediaPlayer.addSubtitleSource(inputStream, mediaFormat);
        } catch (java.lang.IllegalStateException e) {
            this.mInfoListener.onInfo(this.mMediaPlayer, 901, 0);
        }
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
            this.mAudioManager.abandonAudioFocus(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openVideo() {
        if (this.mUri == null || this.mSurfaceHolder == null) {
            return;
        }
        release(false);
        if (this.mAudioFocusType != 0) {
            this.mAudioManager.requestAudioFocus(null, this.mAudioAttributes, this.mAudioFocusType, 0);
        }
        try {
            this.mMediaPlayer = new android.media.MediaPlayer();
            android.content.Context context = getContext();
            android.media.SubtitleController subtitleController = new android.media.SubtitleController(context, this.mMediaPlayer.getMediaTimeProvider(), this.mMediaPlayer);
            subtitleController.registerRenderer(new android.media.WebVttRenderer(context));
            subtitleController.registerRenderer(new android.media.TtmlRenderer(context));
            subtitleController.registerRenderer(new android.media.Cea708CaptionRenderer(context));
            subtitleController.registerRenderer(new android.media.ClosedCaptionRenderer(context));
            this.mMediaPlayer.setSubtitleAnchor(subtitleController, this);
            if (this.mAudioSession != 0) {
                this.mMediaPlayer.setAudioSessionId(this.mAudioSession);
            } else {
                this.mAudioSession = this.mMediaPlayer.getAudioSessionId();
            }
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setDataSource(this.mContext, this.mUri, this.mHeaders);
            this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
            this.mMediaPlayer.setAudioAttributes(this.mAudioAttributes);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            this.mMediaPlayer.prepareAsync();
            java.util.Iterator<android.util.Pair<java.io.InputStream, android.media.MediaFormat>> it = this.mPendingSubtitleTracks.iterator();
            while (it.hasNext()) {
                android.util.Pair<java.io.InputStream, android.media.MediaFormat> next = it.next();
                try {
                    this.mMediaPlayer.addSubtitleSource(next.first, next.second);
                } catch (java.lang.IllegalStateException e) {
                    this.mInfoListener.onInfo(this.mMediaPlayer, 901, 0);
                }
            }
            this.mCurrentState = 1;
            attachMediaController();
        } catch (java.io.IOException e2) {
            android.util.Log.w(TAG, "Unable to open content: " + this.mUri, e2);
            this.mCurrentState = -1;
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        } catch (java.lang.IllegalArgumentException e3) {
            android.util.Log.w(TAG, "Unable to open content: " + this.mUri, e3);
            this.mCurrentState = -1;
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        } finally {
            this.mPendingSubtitleTracks.clear();
        }
    }

    public void setMediaController(android.widget.MediaController mediaController) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = mediaController;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            this.mMediaController.setMediaPlayer(this);
            this.mMediaController.setAnchorView(getParent() instanceof android.view.View ? (android.view.View) getParent() : this);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    public void setOnPreparedListener(android.media.MediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(android.media.MediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(android.media.MediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release(boolean z) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mPendingSubtitleTracks.clear();
            this.mCurrentState = 0;
            if (z) {
                this.mTargetState = 0;
            }
            if (this.mAudioFocusType != 0) {
                this.mAudioManager.abandonAudioFocus(null);
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return super.onTrackballEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        boolean z = (i == 4 || i == 24 || i == 25 || i == 164 || i == 82 || i == 5 || i == 6) ? false : true;
        if (isInPlaybackState() && z && this.mMediaController != null) {
            if (i == 79 || i == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                } else {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            }
            if (i == 126) {
                if (!this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            }
            if (i == 86 || i == 127) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            }
            toggleMediaControlsVisiblity();
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
        }
        this.mTargetState = 4;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openVideo();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(i);
            this.mSeekWhenPrepared = 0;
        } else {
            this.mSeekWhenPrepared = i;
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return this.mCanPause;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        if (this.mAudioSession == 0) {
            android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
            this.mAudioSession = mediaPlayer.getAudioSessionId();
            mediaPlayer.release();
        }
        return this.mAudioSession;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mSubtitleWidget != null) {
            this.mSubtitleWidget.onAttachedToWindow();
        }
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mSubtitleWidget != null) {
            this.mSubtitleWidget.onDetachedFromWindow();
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mSubtitleWidget != null) {
            measureAndLayoutSubtitleWidget();
        }
    }

    @Override // android.view.SurfaceView, android.view.View
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        if (this.mSubtitleWidget != null) {
            int save = canvas.save();
            canvas.translate(getPaddingLeft(), getPaddingTop());
            this.mSubtitleWidget.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    private void measureAndLayoutSubtitleWidget() {
        this.mSubtitleWidget.setSize((getWidth() - getPaddingLeft()) - getPaddingRight(), (getHeight() - getPaddingTop()) - getPaddingBottom());
    }

    @Override // android.media.SubtitleController.Anchor
    public void setSubtitleWidget(android.media.SubtitleTrack.RenderingWidget renderingWidget) {
        if (this.mSubtitleWidget == renderingWidget) {
            return;
        }
        boolean isAttachedToWindow = isAttachedToWindow();
        if (this.mSubtitleWidget != null) {
            if (isAttachedToWindow) {
                this.mSubtitleWidget.onDetachedFromWindow();
            }
            this.mSubtitleWidget.setOnChangedListener(null);
        }
        this.mSubtitleWidget = renderingWidget;
        if (renderingWidget != null) {
            if (this.mSubtitlesChangedListener == null) {
                this.mSubtitlesChangedListener = new android.media.SubtitleTrack.RenderingWidget.OnChangedListener() { // from class: android.widget.VideoView.8
                    @Override // android.media.SubtitleTrack.RenderingWidget.OnChangedListener
                    public void onChanged(android.media.SubtitleTrack.RenderingWidget renderingWidget2) {
                        android.widget.VideoView.this.invalidate();
                    }
                };
            }
            setWillNotDraw(false);
            renderingWidget.setOnChangedListener(this.mSubtitlesChangedListener);
            if (isAttachedToWindow) {
                renderingWidget.onAttachedToWindow();
                requestLayout();
            }
        } else {
            setWillNotDraw(true);
        }
        invalidate();
    }

    @Override // android.media.SubtitleController.Anchor
    public android.os.Looper getSubtitleLooper() {
        return android.os.Looper.getMainLooper();
    }
}
