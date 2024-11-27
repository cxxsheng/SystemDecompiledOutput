package android.widget;

/* loaded from: classes4.dex */
public class MediaController extends android.widget.FrameLayout {
    private static final int sDefaultTimeout = 3000;
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private android.view.View mAnchor;
    private final android.view.View.OnAttachStateChangeListener mAttachStateListener;
    private final android.window.OnBackInvokedCallback mBackCallback;
    private boolean mBackCallbackRegistered;
    private final android.content.Context mContext;
    private android.widget.TextView mCurrentTime;
    private android.view.View mDecor;
    private android.view.WindowManager.LayoutParams mDecorLayoutParams;
    private boolean mDragging;
    private android.widget.TextView mEndTime;
    private final java.lang.Runnable mFadeOut;
    private android.widget.ImageButton mFfwdButton;
    private final android.view.View.OnClickListener mFfwdListener;
    java.lang.StringBuilder mFormatBuilder;
    java.util.Formatter mFormatter;
    private boolean mFromXml;
    private final android.view.View.OnLayoutChangeListener mLayoutChangeListener;
    private boolean mListenersSet;
    private android.widget.ImageButton mNextButton;
    private android.view.View.OnClickListener mNextListener;
    private android.widget.ImageButton mPauseButton;
    private java.lang.CharSequence mPauseDescription;
    private final android.view.View.OnClickListener mPauseListener;
    private java.lang.CharSequence mPlayDescription;
    private android.widget.MediaController.MediaPlayerControl mPlayer;
    private android.widget.ImageButton mPrevButton;
    private android.view.View.OnClickListener mPrevListener;
    private android.widget.ProgressBar mProgress;
    private android.widget.ImageButton mRewButton;
    private final android.view.View.OnClickListener mRewListener;
    private android.view.View mRoot;
    private final android.widget.SeekBar.OnSeekBarChangeListener mSeekListener;
    private final java.lang.Runnable mShowProgress;
    private boolean mShowing;
    private final android.view.View.OnTouchListener mTouchListener;
    private final boolean mUseFastForward;
    private android.view.Window mWindow;
    private android.view.WindowManager mWindowManager;

    public interface MediaPlayerControl {
        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        int getAudioSessionId();

        int getBufferPercentage();

        int getCurrentPosition();

        int getDuration();

        boolean isPlaying();

        void pause();

        void seekTo(int i);

        void start();
    }

    public MediaController(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.widget.MediaController$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                android.widget.MediaController.this.hide();
            }
        };
        this.mAttachStateListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.MediaController.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view) {
                android.widget.MediaController.this.registerOnBackInvokedCallback();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view) {
                android.widget.MediaController.this.unregisterOnBackInvokedCallback();
            }
        };
        this.mLayoutChangeListener = new android.view.View.OnLayoutChangeListener() { // from class: android.widget.MediaController.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                android.widget.MediaController.this.updateFloatingWindowLayout();
                if (android.widget.MediaController.this.mShowing) {
                    android.widget.MediaController.this.mWindowManager.updateViewLayout(android.widget.MediaController.this.mDecor, android.widget.MediaController.this.mDecorLayoutParams);
                }
            }
        };
        this.mTouchListener = new android.view.View.OnTouchListener() { // from class: android.widget.MediaController.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0 && android.widget.MediaController.this.mShowing) {
                    android.widget.MediaController.this.hide();
                    return false;
                }
                return false;
            }
        };
        this.mFadeOut = new java.lang.Runnable() { // from class: android.widget.MediaController.4
            @Override // java.lang.Runnable
            public void run() {
                android.widget.MediaController.this.hide();
            }
        };
        this.mShowProgress = new java.lang.Runnable() { // from class: android.widget.MediaController.5
            @Override // java.lang.Runnable
            public void run() {
                int progress = android.widget.MediaController.this.setProgress();
                if (!android.widget.MediaController.this.mDragging && android.widget.MediaController.this.mShowing && android.widget.MediaController.this.mPlayer.isPlaying()) {
                    android.widget.MediaController.this.postDelayed(android.widget.MediaController.this.mShowProgress, 1000 - (progress % 1000));
                }
            }
        };
        this.mPauseListener = new android.view.View.OnClickListener() { // from class: android.widget.MediaController.6
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.MediaController.this.doPauseResume();
                android.widget.MediaController.this.show(3000);
            }
        };
        this.mSeekListener = new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: android.widget.MediaController.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
                android.widget.MediaController.this.show(3600000);
                android.widget.MediaController.this.mDragging = true;
                android.widget.MediaController.this.removeCallbacks(android.widget.MediaController.this.mShowProgress);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
                if (!z) {
                    return;
                }
                int duration = (int) ((android.widget.MediaController.this.mPlayer.getDuration() * i) / 1000);
                android.widget.MediaController.this.mPlayer.seekTo(duration);
                if (android.widget.MediaController.this.mCurrentTime != null) {
                    android.widget.MediaController.this.mCurrentTime.lambda$setTextAsync$0(android.widget.MediaController.this.stringForTime(duration));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                android.widget.MediaController.this.mDragging = false;
                android.widget.MediaController.this.setProgress();
                android.widget.MediaController.this.updatePausePlay();
                android.widget.MediaController.this.show(3000);
                android.widget.MediaController.this.post(android.widget.MediaController.this.mShowProgress);
            }
        };
        this.mRewListener = new android.view.View.OnClickListener() { // from class: android.widget.MediaController.8
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.MediaController.this.mPlayer.seekTo(android.widget.MediaController.this.mPlayer.getCurrentPosition() - 5000);
                android.widget.MediaController.this.setProgress();
                android.widget.MediaController.this.show(3000);
            }
        };
        this.mFfwdListener = new android.view.View.OnClickListener() { // from class: android.widget.MediaController.9
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.MediaController.this.mPlayer.seekTo(android.widget.MediaController.this.mPlayer.getCurrentPosition() + 15000);
                android.widget.MediaController.this.setProgress();
                android.widget.MediaController.this.show(3000);
            }
        };
        this.mRoot = this;
        this.mContext = context;
        this.mUseFastForward = true;
        this.mFromXml = true;
        this.mAccessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(context);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        if (this.mRoot != null) {
            initControllerView(this.mRoot);
        }
    }

    public MediaController(android.content.Context context, boolean z) {
        super(context);
        this.mBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.widget.MediaController$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                android.widget.MediaController.this.hide();
            }
        };
        this.mAttachStateListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.MediaController.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view) {
                android.widget.MediaController.this.registerOnBackInvokedCallback();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view) {
                android.widget.MediaController.this.unregisterOnBackInvokedCallback();
            }
        };
        this.mLayoutChangeListener = new android.view.View.OnLayoutChangeListener() { // from class: android.widget.MediaController.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                android.widget.MediaController.this.updateFloatingWindowLayout();
                if (android.widget.MediaController.this.mShowing) {
                    android.widget.MediaController.this.mWindowManager.updateViewLayout(android.widget.MediaController.this.mDecor, android.widget.MediaController.this.mDecorLayoutParams);
                }
            }
        };
        this.mTouchListener = new android.view.View.OnTouchListener() { // from class: android.widget.MediaController.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0 && android.widget.MediaController.this.mShowing) {
                    android.widget.MediaController.this.hide();
                    return false;
                }
                return false;
            }
        };
        this.mFadeOut = new java.lang.Runnable() { // from class: android.widget.MediaController.4
            @Override // java.lang.Runnable
            public void run() {
                android.widget.MediaController.this.hide();
            }
        };
        this.mShowProgress = new java.lang.Runnable() { // from class: android.widget.MediaController.5
            @Override // java.lang.Runnable
            public void run() {
                int progress = android.widget.MediaController.this.setProgress();
                if (!android.widget.MediaController.this.mDragging && android.widget.MediaController.this.mShowing && android.widget.MediaController.this.mPlayer.isPlaying()) {
                    android.widget.MediaController.this.postDelayed(android.widget.MediaController.this.mShowProgress, 1000 - (progress % 1000));
                }
            }
        };
        this.mPauseListener = new android.view.View.OnClickListener() { // from class: android.widget.MediaController.6
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.MediaController.this.doPauseResume();
                android.widget.MediaController.this.show(3000);
            }
        };
        this.mSeekListener = new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: android.widget.MediaController.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
                android.widget.MediaController.this.show(3600000);
                android.widget.MediaController.this.mDragging = true;
                android.widget.MediaController.this.removeCallbacks(android.widget.MediaController.this.mShowProgress);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z2) {
                if (!z2) {
                    return;
                }
                int duration = (int) ((android.widget.MediaController.this.mPlayer.getDuration() * i) / 1000);
                android.widget.MediaController.this.mPlayer.seekTo(duration);
                if (android.widget.MediaController.this.mCurrentTime != null) {
                    android.widget.MediaController.this.mCurrentTime.lambda$setTextAsync$0(android.widget.MediaController.this.stringForTime(duration));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                android.widget.MediaController.this.mDragging = false;
                android.widget.MediaController.this.setProgress();
                android.widget.MediaController.this.updatePausePlay();
                android.widget.MediaController.this.show(3000);
                android.widget.MediaController.this.post(android.widget.MediaController.this.mShowProgress);
            }
        };
        this.mRewListener = new android.view.View.OnClickListener() { // from class: android.widget.MediaController.8
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.MediaController.this.mPlayer.seekTo(android.widget.MediaController.this.mPlayer.getCurrentPosition() - 5000);
                android.widget.MediaController.this.setProgress();
                android.widget.MediaController.this.show(3000);
            }
        };
        this.mFfwdListener = new android.view.View.OnClickListener() { // from class: android.widget.MediaController.9
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.MediaController.this.mPlayer.seekTo(android.widget.MediaController.this.mPlayer.getCurrentPosition() + 15000);
                android.widget.MediaController.this.setProgress();
                android.widget.MediaController.this.show(3000);
            }
        };
        this.mContext = context;
        this.mUseFastForward = z;
        initFloatingWindowLayout();
        initFloatingWindow();
        this.mAccessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(context);
    }

    public MediaController(android.content.Context context) {
        this(context, true);
    }

    private void initFloatingWindow() {
        this.mWindowManager = (android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE);
        this.mWindow = new com.android.internal.policy.PhoneWindow(this.mContext);
        this.mWindow.setWindowManager(this.mWindowManager, null, null);
        this.mWindow.requestFeature(1);
        this.mDecor = this.mWindow.getDecorView();
        this.mDecor.setOnTouchListener(this.mTouchListener);
        this.mDecor.addOnAttachStateChangeListener(this.mAttachStateListener);
        this.mWindow.setContentView(this);
        this.mWindow.setBackgroundDrawableResource(17170445);
        this.mWindow.setVolumeControlStream(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(262144);
        requestFocus();
    }

    private void initFloatingWindowLayout() {
        this.mDecorLayoutParams = new android.view.WindowManager.LayoutParams();
        android.view.WindowManager.LayoutParams layoutParams = this.mDecorLayoutParams;
        layoutParams.gravity = 51;
        layoutParams.height = -2;
        layoutParams.x = 0;
        layoutParams.format = -3;
        layoutParams.type = 1000;
        layoutParams.flags |= 8519712;
        layoutParams.token = null;
        layoutParams.windowAnimations = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingWindowLayout() {
        int[] iArr = new int[2];
        this.mAnchor.getLocationOnScreen(iArr);
        this.mDecor.measure(android.view.View.MeasureSpec.makeMeasureSpec(this.mAnchor.getWidth(), Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(this.mAnchor.getHeight(), Integer.MIN_VALUE));
        android.view.WindowManager.LayoutParams layoutParams = this.mDecorLayoutParams;
        layoutParams.width = this.mAnchor.getWidth();
        layoutParams.x = iArr[0] + ((this.mAnchor.getWidth() - layoutParams.width) / 2);
        layoutParams.y = (iArr[1] + this.mAnchor.getHeight()) - this.mDecor.getMeasuredHeight();
        layoutParams.token = this.mAnchor.getWindowToken();
    }

    public void setMediaPlayer(android.widget.MediaController.MediaPlayerControl mediaPlayerControl) {
        this.mPlayer = mediaPlayerControl;
        updatePausePlay();
    }

    public void setAnchorView(android.view.View view) {
        if (this.mAnchor != null) {
            this.mAnchor.removeOnLayoutChangeListener(this.mLayoutChangeListener);
        }
        this.mAnchor = view;
        if (this.mAnchor != null) {
            this.mAnchor.addOnLayoutChangeListener(this.mLayoutChangeListener);
        }
        android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(-1, -1);
        removeAllViews();
        addView(makeControllerView(), layoutParams);
    }

    protected android.view.View makeControllerView() {
        this.mRoot = ((android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(com.android.internal.R.layout.media_controller, (android.view.ViewGroup) null);
        initControllerView(this.mRoot);
        return this.mRoot;
    }

    private void initControllerView(android.view.View view) {
        android.content.res.Resources resources = this.mContext.getResources();
        this.mPlayDescription = resources.getText(com.android.internal.R.string.lockscreen_transport_play_description);
        this.mPauseDescription = resources.getText(com.android.internal.R.string.lockscreen_transport_pause_description);
        this.mPauseButton = (android.widget.ImageButton) view.findViewById(com.android.internal.R.id.pause);
        if (this.mPauseButton != null) {
            this.mPauseButton.requestFocus();
            this.mPauseButton.setOnClickListener(this.mPauseListener);
        }
        this.mFfwdButton = (android.widget.ImageButton) view.findViewById(com.android.internal.R.id.ffwd);
        if (this.mFfwdButton != null) {
            this.mFfwdButton.setOnClickListener(this.mFfwdListener);
            if (!this.mFromXml) {
                this.mFfwdButton.setVisibility(this.mUseFastForward ? 0 : 8);
            }
        }
        this.mRewButton = (android.widget.ImageButton) view.findViewById(com.android.internal.R.id.rew);
        if (this.mRewButton != null) {
            this.mRewButton.setOnClickListener(this.mRewListener);
            if (!this.mFromXml) {
                this.mRewButton.setVisibility(this.mUseFastForward ? 0 : 8);
            }
        }
        this.mNextButton = (android.widget.ImageButton) view.findViewById(com.android.internal.R.id.next);
        if (this.mNextButton != null && !this.mFromXml && !this.mListenersSet) {
            this.mNextButton.setVisibility(8);
        }
        this.mPrevButton = (android.widget.ImageButton) view.findViewById(com.android.internal.R.id.prev);
        if (this.mPrevButton != null && !this.mFromXml && !this.mListenersSet) {
            this.mPrevButton.setVisibility(8);
        }
        this.mProgress = (android.widget.ProgressBar) view.findViewById(com.android.internal.R.id.mediacontroller_progress);
        if (this.mProgress != null) {
            if (this.mProgress instanceof android.widget.SeekBar) {
                ((android.widget.SeekBar) this.mProgress).setOnSeekBarChangeListener(this.mSeekListener);
            }
            this.mProgress.setMax(1000);
        }
        this.mEndTime = (android.widget.TextView) view.findViewById(com.android.internal.R.id.time);
        this.mCurrentTime = (android.widget.TextView) view.findViewById(com.android.internal.R.id.time_current);
        this.mFormatBuilder = new java.lang.StringBuilder();
        this.mFormatter = new java.util.Formatter(this.mFormatBuilder, java.util.Locale.getDefault());
        installPrevNextListeners();
    }

    public void show() {
        show(3000);
    }

    private void disableUnsupportedButtons() {
        try {
            if (this.mPauseButton != null && !this.mPlayer.canPause()) {
                this.mPauseButton.setEnabled(false);
            }
            if (this.mRewButton != null && !this.mPlayer.canSeekBackward()) {
                this.mRewButton.setEnabled(false);
            }
            if (this.mFfwdButton != null && !this.mPlayer.canSeekForward()) {
                this.mFfwdButton.setEnabled(false);
            }
            if (this.mProgress != null && !this.mPlayer.canSeekBackward() && !this.mPlayer.canSeekForward()) {
                this.mProgress.setEnabled(false);
            }
        } catch (java.lang.IncompatibleClassChangeError e) {
        }
    }

    public void show(int i) {
        if (!this.mShowing && this.mAnchor != null) {
            setProgress();
            if (this.mPauseButton != null) {
                this.mPauseButton.requestFocus();
            }
            disableUnsupportedButtons();
            updateFloatingWindowLayout();
            this.mWindowManager.addView(this.mDecor, this.mDecorLayoutParams);
            this.mShowing = true;
        }
        updatePausePlay();
        post(this.mShowProgress);
        if (i != 0 && !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            removeCallbacks(this.mFadeOut);
            postDelayed(this.mFadeOut, i);
        }
        registerOnBackInvokedCallback();
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public void hide() {
        if (this.mAnchor != null && this.mShowing) {
            try {
                removeCallbacks(this.mShowProgress);
                this.mWindowManager.removeView(this.mDecor);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.w("MediaController", "already removed");
            }
            this.mShowing = false;
            unregisterOnBackInvokedCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String stringForTime(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        this.mFormatBuilder.setLength(0);
        if (i5 > 0) {
            return this.mFormatter.format("%d:%02d:%02d", java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i3)).toString();
        }
        return this.mFormatter.format("%02d:%02d", java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i3)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setProgress() {
        if (this.mPlayer == null || this.mDragging) {
            return 0;
        }
        int currentPosition = this.mPlayer.getCurrentPosition();
        int duration = this.mPlayer.getDuration();
        if (this.mProgress != null) {
            if (duration > 0) {
                this.mProgress.setProgress((int) ((currentPosition * 1000) / duration));
            }
            this.mProgress.setSecondaryProgress(this.mPlayer.getBufferPercentage() * 10);
        }
        if (this.mEndTime != null) {
            this.mEndTime.lambda$setTextAsync$0(stringForTime(duration));
        }
        if (this.mCurrentTime != null) {
            this.mCurrentTime.lambda$setTextAsync$0(stringForTime(currentPosition));
        }
        return currentPosition;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                show(0);
                break;
            case 1:
                show(3000);
                break;
            case 3:
                hide();
                break;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        show(3000);
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getRepeatCount() == 0 && keyEvent.getAction() == 0;
        if (keyCode == 79 || keyCode == 85 || keyCode == 62) {
            if (z) {
                doPauseResume();
                show(3000);
                if (this.mPauseButton != null) {
                    this.mPauseButton.requestFocus();
                }
            }
            return true;
        }
        if (keyCode == 126) {
            if (z && !this.mPlayer.isPlaying()) {
                this.mPlayer.start();
                updatePausePlay();
                show(3000);
            }
            return true;
        }
        if (keyCode == 86 || keyCode == 127) {
            if (z && this.mPlayer.isPlaying()) {
                this.mPlayer.pause();
                updatePausePlay();
                show(3000);
            }
            return true;
        }
        if (keyCode == 25 || keyCode == 24 || keyCode == 164 || keyCode == 27) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyCode == 4 || keyCode == 82) {
            if (z) {
                hide();
            }
            return true;
        }
        show(3000);
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePausePlay() {
        if (this.mRoot == null || this.mPauseButton == null) {
            return;
        }
        if (this.mPlayer.isPlaying()) {
            this.mPauseButton.setImageResource(17301539);
            this.mPauseButton.setContentDescription(this.mPauseDescription);
        } else {
            this.mPauseButton.setImageResource(17301540);
            this.mPauseButton.setContentDescription(this.mPlayDescription);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPauseResume() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.pause();
        } else {
            this.mPlayer.start();
        }
        updatePausePlay();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (this.mPauseButton != null) {
            this.mPauseButton.setEnabled(z);
        }
        if (this.mFfwdButton != null) {
            this.mFfwdButton.setEnabled(z);
        }
        if (this.mRewButton != null) {
            this.mRewButton.setEnabled(z);
        }
        if (this.mNextButton != null) {
            this.mNextButton.setEnabled(z && this.mNextListener != null);
        }
        if (this.mPrevButton != null) {
            this.mPrevButton.setEnabled(z && this.mPrevListener != null);
        }
        if (this.mProgress != null) {
            this.mProgress.setEnabled(z);
        }
        disableUnsupportedButtons();
        super.setEnabled(z);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.MediaController.class.getName();
    }

    private void installPrevNextListeners() {
        if (this.mNextButton != null) {
            this.mNextButton.setOnClickListener(this.mNextListener);
            this.mNextButton.setEnabled(this.mNextListener != null);
        }
        if (this.mPrevButton != null) {
            this.mPrevButton.setOnClickListener(this.mPrevListener);
            this.mPrevButton.setEnabled(this.mPrevListener != null);
        }
    }

    public void setPrevNextListeners(android.view.View.OnClickListener onClickListener, android.view.View.OnClickListener onClickListener2) {
        this.mNextListener = onClickListener;
        this.mPrevListener = onClickListener2;
        this.mListenersSet = true;
        if (this.mRoot != null) {
            installPrevNextListeners();
            if (this.mNextButton != null && !this.mFromXml) {
                this.mNextButton.setVisibility(0);
            }
            if (this.mPrevButton != null && !this.mFromXml) {
                this.mPrevButton.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterOnBackInvokedCallback() {
        if (!this.mBackCallbackRegistered) {
            return;
        }
        android.view.ViewRootImpl viewRootImpl = this.mDecor.getViewRootImpl();
        if (viewRootImpl != null && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mBackCallback);
        }
        this.mBackCallbackRegistered = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerOnBackInvokedCallback() {
        android.view.ViewRootImpl viewRootImpl;
        if (!this.mBackCallbackRegistered && (viewRootImpl = this.mDecor.getViewRootImpl()) != null && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
            this.mBackCallbackRegistered = true;
        }
    }
}
