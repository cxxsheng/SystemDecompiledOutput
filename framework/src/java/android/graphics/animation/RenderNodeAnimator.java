package android.graphics.animation;

/* loaded from: classes.dex */
public class RenderNodeAnimator extends android.animation.Animator {
    public static final int ALPHA = 11;
    public static final int LAST_VALUE = 11;
    public static final int PAINT_ALPHA = 1;
    public static final int PAINT_STROKE_WIDTH = 0;
    public static final int ROTATION = 5;
    public static final int ROTATION_X = 6;
    public static final int ROTATION_Y = 7;
    public static final int SCALE_X = 3;
    public static final int SCALE_Y = 4;
    private static final int STATE_DELAYED = 1;
    private static final int STATE_FINISHED = 3;
    private static final int STATE_PREPARE = 0;
    private static final int STATE_RUNNING = 2;
    public static final int TRANSLATION_X = 0;
    public static final int TRANSLATION_Y = 1;
    public static final int TRANSLATION_Z = 2;
    public static final int X = 8;
    public static final int Y = 9;
    public static final int Z = 10;
    private static java.lang.ThreadLocal<android.graphics.animation.RenderNodeAnimator.DelayedAnimationHelper> sAnimationHelper = new java.lang.ThreadLocal<>();
    private float mFinalValue;
    private android.os.Handler mHandler;
    private android.animation.TimeInterpolator mInterpolator;
    private com.android.internal.util.VirtualRefBasePtr mNativePtr;
    private int mRenderProperty;
    private long mStartDelay;
    private long mStartTime;
    private int mState;
    private android.graphics.RenderNode mTarget;
    private final boolean mUiThreadHandlesDelay;
    private long mUnscaledDuration;
    private long mUnscaledStartDelay;
    private android.graphics.animation.RenderNodeAnimator.ViewListener mViewListener;

    public interface ViewListener {
        void invalidateParent(boolean z);

        void onAlphaAnimationStart(float f);
    }

    private static native long nCreateAnimator(int i, float f);

    private static native long nCreateCanvasPropertyFloatAnimator(long j, float f);

    private static native long nCreateCanvasPropertyPaintAnimator(long j, int i, float f);

    private static native long nCreateRevealAnimator(int i, int i2, float f, float f2);

    private static native void nEnd(long j);

    private static native long nGetDuration(long j);

    private static native void nSetAllowRunningAsync(long j, boolean z);

    private static native void nSetDuration(long j, long j2);

    private static native void nSetInterpolator(long j, long j2);

    private static native void nSetListener(long j, android.graphics.animation.RenderNodeAnimator renderNodeAnimator);

    private static native void nSetStartDelay(long j, long j2);

    private static native void nSetStartValue(long j, float f);

    private static native void nStart(long j);

    public RenderNodeAnimator(int i, float f) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        this.mRenderProperty = i;
        this.mFinalValue = f;
        this.mUiThreadHandlesDelay = true;
        init(nCreateAnimator(i, f));
    }

    public RenderNodeAnimator(android.graphics.CanvasProperty<java.lang.Float> canvasProperty, float f) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateCanvasPropertyFloatAnimator(canvasProperty.getNativeContainer(), f));
        this.mUiThreadHandlesDelay = false;
    }

    public RenderNodeAnimator(android.graphics.CanvasProperty<android.graphics.Paint> canvasProperty, int i, float f) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateCanvasPropertyPaintAnimator(canvasProperty.getNativeContainer(), i, f));
        this.mUiThreadHandlesDelay = false;
    }

    public RenderNodeAnimator(int i, int i2, float f, float f2) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateRevealAnimator(i, i2, f, f2));
        this.mUiThreadHandlesDelay = true;
    }

    private void init(long j) {
        this.mNativePtr = new com.android.internal.util.VirtualRefBasePtr(j);
    }

    private void checkMutable() {
        if (this.mState != 0) {
            throw new java.lang.IllegalStateException("Animator has already started, cannot change it now!");
        }
        if (this.mNativePtr == null) {
            throw new java.lang.IllegalStateException("Animator's target has been destroyed (trying to modify an animation after activity destroy?)");
        }
    }

    static boolean isNativeInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        return timeInterpolator.getClass().isAnnotationPresent(android.graphics.animation.HasNativeInterpolator.class);
    }

    private void applyInterpolator() {
        long createNativeInterpolator;
        if (this.mInterpolator == null || this.mNativePtr == null) {
            return;
        }
        if (isNativeInterpolator(this.mInterpolator)) {
            createNativeInterpolator = ((android.graphics.animation.NativeInterpolator) this.mInterpolator).createNativeInterpolator();
        } else {
            createNativeInterpolator = android.graphics.animation.FallbackLUTInterpolator.createNativeInterpolator(this.mInterpolator, nGetDuration(this.mNativePtr.get()));
        }
        nSetInterpolator(this.mNativePtr.get(), createNativeInterpolator);
    }

    @Override // android.animation.Animator
    public void start() {
        if (this.mTarget == null) {
            throw new java.lang.IllegalStateException("Missing target!");
        }
        if (this.mState != 0) {
            throw new java.lang.IllegalStateException("Already started!");
        }
        this.mState = 1;
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler(true);
        }
        applyInterpolator();
        if (this.mNativePtr == null) {
            cancel();
        } else if (this.mStartDelay <= 0 || !this.mUiThreadHandlesDelay) {
            nSetStartDelay(this.mNativePtr.get(), this.mStartDelay);
            doStart();
        } else {
            getHelper().addDelayedAnimation(this);
        }
    }

    private void doStart() {
        if (this.mRenderProperty == 11 && this.mViewListener != null) {
            this.mViewListener.onAlphaAnimationStart(this.mFinalValue);
        }
        moveToRunningState();
        if (this.mViewListener != null) {
            this.mViewListener.invalidateParent(false);
        }
    }

    private void moveToRunningState() {
        this.mState = 2;
        if (this.mNativePtr != null) {
            nStart(this.mNativePtr.get());
        }
        notifyStartListeners();
    }

    private void notifyStartListeners() {
        java.util.ArrayList<android.animation.Animator.AnimatorListener> cloneListeners = cloneListeners();
        int size = cloneListeners == null ? 0 : cloneListeners.size();
        for (int i = 0; i < size; i++) {
            cloneListeners.get(i).onAnimationStart(this);
        }
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (this.mState != 0 && this.mState != 3) {
            if (this.mState == 1) {
                getHelper().removeDelayedAnimation(this);
                moveToRunningState();
            }
            java.util.ArrayList<android.animation.Animator.AnimatorListener> cloneListeners = cloneListeners();
            int size = cloneListeners == null ? 0 : cloneListeners.size();
            for (int i = 0; i < size; i++) {
                cloneListeners.get(i).onAnimationCancel(this);
            }
            end();
        }
    }

    @Override // android.animation.Animator
    public void end() {
        if (this.mState != 3) {
            if (this.mState < 2) {
                getHelper().removeDelayedAnimation(this);
                doStart();
            }
            if (this.mNativePtr != null) {
                nEnd(this.mNativePtr.get());
                if (this.mViewListener != null) {
                    this.mViewListener.invalidateParent(false);
                    return;
                }
                return;
            }
            onFinished();
        }
    }

    @Override // android.animation.Animator
    public void pause() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // android.animation.Animator
    public void resume() {
        throw new java.lang.UnsupportedOperationException();
    }

    public void setViewListener(android.graphics.animation.RenderNodeAnimator.ViewListener viewListener) {
        this.mViewListener = viewListener;
    }

    public final void setTarget(android.graphics.RecordingCanvas recordingCanvas) {
        setTarget(recordingCanvas.mNode);
    }

    protected void setTarget(android.graphics.RenderNode renderNode) {
        checkMutable();
        if (this.mTarget != null) {
            throw new java.lang.IllegalStateException("Target already set!");
        }
        nSetListener(this.mNativePtr.get(), this);
        this.mTarget = renderNode;
        this.mTarget.addAnimator(this);
    }

    public void setStartValue(float f) {
        checkMutable();
        nSetStartValue(this.mNativePtr.get(), f);
    }

    @Override // android.animation.Animator
    public void setStartDelay(long j) {
        checkMutable();
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("startDelay must be positive; " + j);
        }
        this.mUnscaledStartDelay = j;
        this.mStartDelay = (long) (android.animation.ValueAnimator.getDurationScale() * j);
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mUnscaledStartDelay;
    }

    @Override // android.animation.Animator
    public android.graphics.animation.RenderNodeAnimator setDuration(long j) {
        checkMutable();
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("duration must be positive; " + j);
        }
        this.mUnscaledDuration = j;
        nSetDuration(this.mNativePtr.get(), (long) (j * android.animation.ValueAnimator.getDurationScale()));
        return this;
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mUnscaledDuration;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        return this.mUnscaledDuration + this.mUnscaledStartDelay;
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        return this.mState == 1 || this.mState == 2;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mState != 0;
    }

    @Override // android.animation.Animator
    public void setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        checkMutable();
        this.mInterpolator = timeInterpolator;
    }

    @Override // android.animation.Animator
    public android.animation.TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    protected void onFinished() {
        if (this.mState == 0) {
            releaseNativePtr();
            return;
        }
        if (this.mState == 1) {
            getHelper().removeDelayedAnimation(this);
            notifyStartListeners();
        }
        this.mState = 3;
        java.util.ArrayList<android.animation.Animator.AnimatorListener> cloneListeners = cloneListeners();
        int size = cloneListeners == null ? 0 : cloneListeners.size();
        for (int i = 0; i < size; i++) {
            cloneListeners.get(i).onAnimationEnd(this);
        }
        releaseNativePtr();
    }

    private void releaseNativePtr() {
        if (this.mNativePtr != null) {
            this.mNativePtr.release();
            this.mNativePtr = null;
        }
    }

    private java.util.ArrayList<android.animation.Animator.AnimatorListener> cloneListeners() {
        java.util.ArrayList<android.animation.Animator.AnimatorListener> listeners = getListeners();
        if (listeners != null) {
            return (java.util.ArrayList) listeners.clone();
        }
        return listeners;
    }

    public long getNativeAnimator() {
        return this.mNativePtr.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processDelayed(long j) {
        if (this.mStartTime == 0) {
            this.mStartTime = j;
            return false;
        }
        if (j - this.mStartTime >= this.mStartDelay) {
            doStart();
            return true;
        }
        return false;
    }

    private static android.graphics.animation.RenderNodeAnimator.DelayedAnimationHelper getHelper() {
        android.graphics.animation.RenderNodeAnimator.DelayedAnimationHelper delayedAnimationHelper = sAnimationHelper.get();
        if (delayedAnimationHelper == null) {
            android.graphics.animation.RenderNodeAnimator.DelayedAnimationHelper delayedAnimationHelper2 = new android.graphics.animation.RenderNodeAnimator.DelayedAnimationHelper();
            sAnimationHelper.set(delayedAnimationHelper2);
            return delayedAnimationHelper2;
        }
        return delayedAnimationHelper;
    }

    private static class DelayedAnimationHelper implements java.lang.Runnable {
        private boolean mCallbackScheduled;
        private java.util.ArrayList<android.graphics.animation.RenderNodeAnimator> mDelayedAnims = new java.util.ArrayList<>();
        private final android.view.Choreographer mChoreographer = android.view.Choreographer.getInstance();

        DelayedAnimationHelper() {
        }

        public void addDelayedAnimation(android.graphics.animation.RenderNodeAnimator renderNodeAnimator) {
            this.mDelayedAnims.add(renderNodeAnimator);
            scheduleCallback();
        }

        public void removeDelayedAnimation(android.graphics.animation.RenderNodeAnimator renderNodeAnimator) {
            this.mDelayedAnims.remove(renderNodeAnimator);
        }

        private void scheduleCallback() {
            if (!this.mCallbackScheduled) {
                this.mCallbackScheduled = true;
                this.mChoreographer.postCallback(1, this, null);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            long frameTime = this.mChoreographer.getFrameTime();
            this.mCallbackScheduled = false;
            int i = 0;
            for (int i2 = 0; i2 < this.mDelayedAnims.size(); i2++) {
                android.graphics.animation.RenderNodeAnimator renderNodeAnimator = this.mDelayedAnims.get(i2);
                if (!renderNodeAnimator.processDelayed(frameTime)) {
                    if (i != i2) {
                        this.mDelayedAnims.set(i, renderNodeAnimator);
                    }
                    i++;
                }
            }
            while (this.mDelayedAnims.size() > i) {
                this.mDelayedAnims.remove(this.mDelayedAnims.size() - 1);
            }
            if (this.mDelayedAnims.size() > 0) {
                scheduleCallback();
            }
        }
    }

    private static void callOnFinished(final android.graphics.animation.RenderNodeAnimator renderNodeAnimator) {
        if (renderNodeAnimator.mHandler != null) {
            android.os.Handler handler = renderNodeAnimator.mHandler;
            java.util.Objects.requireNonNull(renderNodeAnimator);
            handler.post(new java.lang.Runnable() { // from class: android.graphics.animation.RenderNodeAnimator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.graphics.animation.RenderNodeAnimator.this.onFinished();
                }
            });
        } else {
            android.os.Handler handler2 = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
            java.util.Objects.requireNonNull(renderNodeAnimator);
            handler2.post(new java.lang.Runnable() { // from class: android.graphics.animation.RenderNodeAnimator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.graphics.animation.RenderNodeAnimator.this.onFinished();
                }
            });
        }
    }

    @Override // android.animation.Animator
    /* renamed from: clone */
    public android.animation.Animator mo77clone() {
        throw new java.lang.IllegalStateException("Cannot clone this animator");
    }

    @Override // android.animation.Animator
    public void setAllowRunningAsynchronously(boolean z) {
        checkMutable();
        nSetAllowRunningAsync(this.mNativePtr.get(), z);
    }
}
