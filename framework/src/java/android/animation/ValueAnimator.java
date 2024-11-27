package android.animation;

/* loaded from: classes.dex */
public class ValueAnimator extends android.animation.Animator implements android.animation.AnimationHandler.AnimationFrameCallback {
    private static final boolean DEBUG = false;
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final java.lang.String TAG = "ValueAnimator";
    private android.animation.AnimationHandler mAnimationHandler;
    private long mPauseTime;
    private boolean mReversing;
    boolean mStartTimeCommitted;
    android.animation.PropertyValuesHolder[] mValues;
    java.util.HashMap<java.lang.String, android.animation.PropertyValuesHolder> mValuesMap;
    private static final boolean TRACE_ANIMATION_FRACTION = android.os.SystemProperties.getBoolean("persist.debug.animator.trace_fraction", false);
    private static float sDurationScale = 1.0f;
    private static final java.util.ArrayList<java.lang.ref.WeakReference<android.animation.ValueAnimator.DurationScaleChangeListener>> sDurationScaleChangeListeners = new java.util.ArrayList<>();
    private static final android.animation.TimeInterpolator sDefaultInterpolator = new android.view.animation.AccelerateDecelerateInterpolator();
    long mStartTime = -1;
    float mSeekFraction = -1.0f;
    private boolean mResumed = false;
    private float mOverallFraction = 0.0f;
    private float mCurrentFraction = 0.0f;
    private long mLastFrameTime = -1;
    private long mFirstFrameTime = -1;
    private boolean mRunning = false;
    private boolean mStarted = false;
    boolean mInitialized = false;
    private boolean mAnimationEndRequested = false;
    private long mDuration = 300;
    private long mStartDelay = 0;
    private int mRepeatCount = 0;
    private int mRepeatMode = 1;
    private boolean mSelfPulse = true;
    private boolean mSuppressSelfPulseRequested = false;
    private android.animation.TimeInterpolator mInterpolator = sDefaultInterpolator;
    java.util.ArrayList<android.animation.ValueAnimator.AnimatorUpdateListener> mUpdateListeners = null;
    private float mDurationScale = -1.0f;

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(android.animation.ValueAnimator valueAnimator);
    }

    public interface DurationScaleChangeListener {
        void onChanged(float f);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    public static void setDurationScale(float f) {
        java.util.ArrayList arrayList;
        sDurationScale = f;
        synchronized (sDurationScaleChangeListeners) {
            arrayList = new java.util.ArrayList(sDurationScaleChangeListeners);
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            android.animation.ValueAnimator.DurationScaleChangeListener durationScaleChangeListener = (android.animation.ValueAnimator.DurationScaleChangeListener) ((java.lang.ref.WeakReference) arrayList.get(i)).get();
            if (durationScaleChangeListener != null) {
                durationScaleChangeListener.onChanged(f);
            }
        }
    }

    public static float getDurationScale() {
        return sDurationScale;
    }

    public static boolean registerDurationScaleChangeListener(android.animation.ValueAnimator.DurationScaleChangeListener durationScaleChangeListener) {
        synchronized (sDurationScaleChangeListeners) {
            int i = -1;
            for (int i2 = 0; i2 < sDurationScaleChangeListeners.size(); i2++) {
                java.lang.ref.WeakReference<android.animation.ValueAnimator.DurationScaleChangeListener> weakReference = sDurationScaleChangeListeners.get(i2);
                if (weakReference.get() == null) {
                    if (i == -1) {
                        i = i2;
                    }
                } else if (weakReference.get() == durationScaleChangeListener) {
                    return false;
                }
            }
            if (i != -1) {
                sDurationScaleChangeListeners.set(i, new java.lang.ref.WeakReference<>(durationScaleChangeListener));
                return true;
            }
            return sDurationScaleChangeListeners.add(new java.lang.ref.WeakReference<>(durationScaleChangeListener));
        }
    }

    public static boolean unregisterDurationScaleChangeListener(android.animation.ValueAnimator.DurationScaleChangeListener durationScaleChangeListener) {
        java.lang.ref.WeakReference<android.animation.ValueAnimator.DurationScaleChangeListener> weakReference;
        boolean remove;
        synchronized (sDurationScaleChangeListeners) {
            java.util.Iterator<java.lang.ref.WeakReference<android.animation.ValueAnimator.DurationScaleChangeListener>> it = sDurationScaleChangeListeners.iterator();
            while (true) {
                if (!it.hasNext()) {
                    weakReference = null;
                    break;
                }
                weakReference = it.next();
                if (weakReference.get() == durationScaleChangeListener) {
                    break;
                }
            }
            remove = sDurationScaleChangeListeners.remove(weakReference);
        }
        return remove;
    }

    public static boolean areAnimatorsEnabled() {
        return sDurationScale != 0.0f;
    }

    public static android.animation.ValueAnimator ofInt(int... iArr) {
        android.animation.ValueAnimator valueAnimator = new android.animation.ValueAnimator();
        valueAnimator.setIntValues(iArr);
        return valueAnimator;
    }

    public static android.animation.ValueAnimator ofArgb(int... iArr) {
        android.animation.ValueAnimator valueAnimator = new android.animation.ValueAnimator();
        valueAnimator.setIntValues(iArr);
        valueAnimator.setEvaluator(android.animation.ArgbEvaluator.getInstance());
        return valueAnimator;
    }

    public static android.animation.ValueAnimator ofFloat(float... fArr) {
        android.animation.ValueAnimator valueAnimator = new android.animation.ValueAnimator();
        valueAnimator.setFloatValues(fArr);
        return valueAnimator;
    }

    public static android.animation.ValueAnimator ofPropertyValuesHolder(android.animation.PropertyValuesHolder... propertyValuesHolderArr) {
        android.animation.ValueAnimator valueAnimator = new android.animation.ValueAnimator();
        valueAnimator.setValues(propertyValuesHolderArr);
        return valueAnimator;
    }

    public static android.animation.ValueAnimator ofObject(android.animation.TypeEvaluator typeEvaluator, java.lang.Object... objArr) {
        android.animation.ValueAnimator valueAnimator = new android.animation.ValueAnimator();
        valueAnimator.setObjectValues(objArr);
        valueAnimator.setEvaluator(typeEvaluator);
        return valueAnimator;
    }

    public void setIntValues(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return;
        }
        if (this.mValues == null || this.mValues.length == 0) {
            setValues(android.animation.PropertyValuesHolder.ofInt("", iArr));
        } else {
            this.mValues[0].setIntValues(iArr);
        }
        this.mInitialized = false;
    }

    public void setFloatValues(float... fArr) {
        if (fArr == null || fArr.length == 0) {
            return;
        }
        if (this.mValues == null || this.mValues.length == 0) {
            setValues(android.animation.PropertyValuesHolder.ofFloat("", fArr));
        } else {
            this.mValues[0].setFloatValues(fArr);
        }
        this.mInitialized = false;
    }

    public void setObjectValues(java.lang.Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return;
        }
        if (this.mValues == null || this.mValues.length == 0) {
            setValues(android.animation.PropertyValuesHolder.ofObject("", (android.animation.TypeEvaluator) null, objArr));
        } else {
            this.mValues[0].setObjectValues(objArr);
        }
        this.mInitialized = false;
    }

    public void setValues(android.animation.PropertyValuesHolder... propertyValuesHolderArr) {
        int length = propertyValuesHolderArr.length;
        this.mValues = propertyValuesHolderArr;
        this.mValuesMap = new java.util.HashMap<>(length);
        for (android.animation.PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
            this.mValuesMap.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
        }
        this.mInitialized = false;
    }

    public android.animation.PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    void initAnimation() {
        if (!this.mInitialized) {
            if (this.mValues != null) {
                int length = this.mValues.length;
                for (int i = 0; i < length; i++) {
                    this.mValues[i].init();
                }
            }
            this.mInitialized = true;
        }
    }

    @Override // android.animation.Animator
    public android.animation.ValueAnimator setDuration(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.mDuration = j;
        return this;
    }

    public void overrideDurationScale(float f) {
        this.mDurationScale = f;
    }

    private float resolveDurationScale() {
        return this.mDurationScale >= 0.0f ? this.mDurationScale : sDurationScale;
    }

    private long getScaledDuration() {
        return (long) (this.mDuration * resolveDurationScale());
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        if (this.mRepeatCount == -1) {
            return -1L;
        }
        return this.mStartDelay + (this.mDuration * (this.mRepeatCount + 1));
    }

    public void setCurrentPlayTime(long j) {
        setCurrentFraction(this.mDuration > 0 ? j / this.mDuration : 1.0f);
    }

    public void setCurrentFraction(float f) {
        initAnimation();
        float clampFraction = clampFraction(f);
        this.mStartTimeCommitted = true;
        if (isPulsingInternal()) {
            this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis() - ((long) (getScaledDuration() * clampFraction));
        } else {
            this.mSeekFraction = clampFraction;
        }
        this.mOverallFraction = clampFraction;
        animateValue(getCurrentIterationFraction(clampFraction, this.mReversing));
    }

    private int getCurrentIteration(float f) {
        float clampFraction = clampFraction(f);
        double d = clampFraction;
        double floor = java.lang.Math.floor(d);
        if (d == floor && clampFraction > 0.0f) {
            floor -= 1.0d;
        }
        return (int) floor;
    }

    private float getCurrentIterationFraction(float f, boolean z) {
        float clampFraction = clampFraction(f);
        int currentIteration = getCurrentIteration(clampFraction);
        float f2 = clampFraction - currentIteration;
        return shouldPlayBackward(currentIteration, z) ? 1.0f - f2 : f2;
    }

    private float clampFraction(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        if (this.mRepeatCount != -1) {
            return java.lang.Math.min(f, this.mRepeatCount + 1);
        }
        return f;
    }

    private boolean shouldPlayBackward(int i, boolean z) {
        if (i <= 0 || this.mRepeatMode != 2 || (i >= this.mRepeatCount + 1 && this.mRepeatCount != -1)) {
            return z;
        }
        return z ? i % 2 == 0 : i % 2 != 0;
    }

    public long getCurrentPlayTime() {
        if (!this.mInitialized) {
            return 0L;
        }
        if (!this.mStarted && this.mSeekFraction < 0.0f) {
            return 0L;
        }
        if (this.mSeekFraction >= 0.0f) {
            return (long) (this.mDuration * this.mSeekFraction);
        }
        float resolveDurationScale = resolveDurationScale();
        if (resolveDurationScale == 0.0f) {
            resolveDurationScale = 1.0f;
        }
        return (long) ((android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / resolveDurationScale);
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // android.animation.Animator
    public void setStartDelay(long j) {
        if (j < 0) {
            android.util.Log.w(TAG, "Start delay should always be non-negative");
            j = 0;
        }
        this.mStartDelay = j;
    }

    public static long getFrameDelay() {
        android.animation.AnimationHandler.getInstance();
        return android.animation.AnimationHandler.getFrameDelay();
    }

    public static void setFrameDelay(long j) {
        android.animation.AnimationHandler.getInstance();
        android.animation.AnimationHandler.setFrameDelay(j);
    }

    public java.lang.Object getAnimatedValue() {
        if (this.mValues != null && this.mValues.length > 0) {
            return this.mValues[0].getAnimatedValue();
        }
        return null;
    }

    public java.lang.Object getAnimatedValue(java.lang.String str) {
        android.animation.PropertyValuesHolder propertyValuesHolder = this.mValuesMap.get(str);
        if (propertyValuesHolder != null) {
            return propertyValuesHolder.getAnimatedValue();
        }
        return null;
    }

    public void setRepeatCount(int i) {
        this.mRepeatCount = i;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public void setRepeatMode(int i) {
        this.mRepeatMode = i;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public void addUpdateListener(android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new java.util.ArrayList<>();
        }
        this.mUpdateListeners.add(animatorUpdateListener);
    }

    public void removeAllUpdateListeners() {
        if (this.mUpdateListeners == null) {
            return;
        }
        this.mUpdateListeners.clear();
        this.mUpdateListeners = null;
    }

    public void removeUpdateListener(android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        if (this.mUpdateListeners == null) {
            return;
        }
        this.mUpdateListeners.remove(animatorUpdateListener);
        if (this.mUpdateListeners.size() == 0) {
            this.mUpdateListeners = null;
        }
    }

    @Override // android.animation.Animator
    public void setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            this.mInterpolator = timeInterpolator;
        } else {
            this.mInterpolator = new android.view.animation.LinearInterpolator();
        }
    }

    @Override // android.animation.Animator
    public android.animation.TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setEvaluator(android.animation.TypeEvaluator typeEvaluator) {
        if (typeEvaluator != null && this.mValues != null && this.mValues.length > 0) {
            this.mValues[0].setEvaluator(typeEvaluator);
        }
    }

    private void start(boolean z) {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mReversing = z;
        this.mSelfPulse = !this.mSuppressSelfPulseRequested;
        if (z && this.mSeekFraction != -1.0f && this.mSeekFraction != 0.0f) {
            if (this.mRepeatCount == -1) {
                this.mSeekFraction = 1.0f - ((float) (this.mSeekFraction - java.lang.Math.floor(this.mSeekFraction)));
            } else {
                this.mSeekFraction = (this.mRepeatCount + 1) - this.mSeekFraction;
            }
        }
        this.mStarted = true;
        this.mPaused = false;
        this.mRunning = false;
        this.mAnimationEndRequested = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        addAnimationCallback(0L);
        if (this.mStartDelay == 0 || this.mSeekFraction >= 0.0f || this.mReversing) {
            startAnimation();
            if (this.mSeekFraction == -1.0f) {
                setCurrentPlayTime(0L);
            } else {
                setCurrentFraction(this.mSeekFraction);
            }
        }
    }

    @Override // android.animation.Animator
    void startWithoutPulsing(boolean z) {
        this.mSuppressSelfPulseRequested = true;
        if (z) {
            reverse();
        } else {
            start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    @Override // android.animation.Animator
    public void start() {
        start(false);
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mAnimationEndRequested) {
            return;
        }
        if ((this.mStarted || this.mRunning || this.mStartListenersCalled) && this.mListeners != null) {
            if (!this.mRunning) {
                notifyStartListeners(this.mReversing);
            }
            notifyListeners(android.animation.Animator.AnimatorCaller.ON_CANCEL, false);
        }
        endAnimation();
    }

    @Override // android.animation.Animator
    public void end() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation();
        }
        animateValue(shouldPlayBackward(this.mRepeatCount, this.mReversing) ? 0.0f : 1.0f);
        endAnimation();
    }

    @Override // android.animation.Animator
    public void resume() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be resumed from the same thread that the animator was started on");
        }
        if (this.mPaused && !this.mResumed) {
            this.mResumed = true;
            if (this.mPauseTime > 0) {
                addAnimationCallback(0L);
            }
        }
        super.resume();
    }

    @Override // android.animation.Animator
    public void pause() {
        boolean z = this.mPaused;
        super.pause();
        if (!z && this.mPaused) {
            this.mPauseTime = -1L;
            this.mResumed = false;
        }
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    @Override // android.animation.Animator
    public void reverse() {
        if (isPulsingInternal()) {
            long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentAnimationTimeMillis - (getScaledDuration() - (currentAnimationTimeMillis - this.mStartTime));
            this.mStartTimeCommitted = true;
            this.mReversing = !this.mReversing;
            return;
        }
        if (this.mStarted) {
            this.mReversing = !this.mReversing;
            end();
        } else {
            start(true);
        }
    }

    @Override // android.animation.Animator
    public boolean canReverse() {
        return true;
    }

    private void endAnimation() {
        if (this.mAnimationEndRequested) {
            return;
        }
        removeAnimationCallback();
        boolean z = true;
        this.mAnimationEndRequested = true;
        this.mPaused = false;
        if ((!this.mStarted && !this.mRunning) || this.mListeners == null) {
            z = false;
        }
        if (z && !this.mRunning) {
            notifyStartListeners(this.mReversing);
        }
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        this.mRunning = false;
        this.mStarted = false;
        notifyEndListeners(this.mReversing);
        this.mReversing = false;
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceEnd(8L, getNameForTrace(), java.lang.System.identityHashCode(this));
        }
    }

    private void startAnimation() {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceBegin(8L, getNameForTrace(), java.lang.System.identityHashCode(this));
        }
        this.mAnimationEndRequested = false;
        initAnimation();
        this.mRunning = true;
        if (this.mSeekFraction >= 0.0f) {
            this.mOverallFraction = this.mSeekFraction;
        } else {
            this.mOverallFraction = 0.0f;
        }
        notifyStartListeners(this.mReversing);
    }

    private boolean isPulsingInternal() {
        return this.mLastFrameTime >= 0;
    }

    java.lang.String getNameForTrace() {
        return "animator";
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public void commitAnimationFrame(long j) {
        if (!this.mStartTimeCommitted) {
            this.mStartTimeCommitted = true;
            long j2 = j - this.mLastFrameTime;
            if (j2 > 0) {
                this.mStartTime += j2;
            }
        }
    }

    boolean animateBasedOnTime(long j) {
        boolean z = false;
        if (this.mRunning) {
            long scaledDuration = getScaledDuration();
            float f = scaledDuration > 0 ? (j - this.mStartTime) / scaledDuration : 1.0f;
            boolean z2 = ((int) f) > ((int) this.mOverallFraction);
            boolean z3 = f >= ((float) (this.mRepeatCount + 1)) && this.mRepeatCount != -1;
            if (scaledDuration == 0) {
                z = true;
            } else if (z2 && !z3) {
                notifyListeners(android.animation.Animator.AnimatorCaller.ON_REPEAT, false);
            } else if (z3) {
                z = true;
            }
            this.mOverallFraction = clampFraction(f);
            animateValue(getCurrentIterationFraction(this.mOverallFraction, this.mReversing));
        }
        return z;
    }

    @Override // android.animation.Animator
    void animateValuesInRange(long j, long j2) {
        if (j < 0 || j2 < -1) {
            throw new java.lang.UnsupportedOperationException("Error: Play time should never be negative.");
        }
        initAnimation();
        long totalDuration = getTotalDuration();
        if (j2 < 0 || (j2 == 0 && j > 0)) {
            notifyStartListeners(false);
        } else if (j2 > totalDuration || (j2 == totalDuration && j < totalDuration)) {
            notifyStartListeners(true);
        }
        if (totalDuration >= 0) {
            j2 = java.lang.Math.min(totalDuration, j2);
        }
        long j3 = j2 - this.mStartDelay;
        long j4 = j - this.mStartDelay;
        if (this.mRepeatCount > 0) {
            if (java.lang.Math.min(java.lang.Math.max(0, (int) (j4 / this.mDuration)), this.mRepeatCount) != java.lang.Math.min(java.lang.Math.max(0, (int) (j3 / this.mDuration)), this.mRepeatCount)) {
                notifyListeners(android.animation.Animator.AnimatorCaller.ON_REPEAT, false);
            }
        }
        if (this.mRepeatCount != -1 && j4 > (this.mRepeatCount + 1) * this.mDuration) {
            throw new java.lang.IllegalStateException("Can't animate a value outside of the duration");
        }
        animateValue(getCurrentIterationFraction(java.lang.Math.max(0L, j4) / this.mDuration, false));
    }

    @Override // android.animation.Animator
    void animateSkipToEnds(long j, long j2) {
        boolean z = false;
        boolean z2 = true;
        boolean z3 = j < j2;
        if (j > 0 || j2 <= 0) {
            long totalDuration = getTotalDuration();
            if (totalDuration >= 0 && j >= totalDuration && j2 < totalDuration) {
                z = true;
            }
            z2 = z;
        }
        if (z2) {
            notifyStartListeners(z3);
            skipToEndValue(z3);
            notifyEndListeners(z3);
        }
    }

    @Override // android.animation.Animator
    void skipToEndValue(boolean z) {
        initAnimation();
        float f = 0.0f;
        float f2 = z ? 0.0f : 1.0f;
        if (this.mRepeatCount % 2 != 1 || this.mRepeatMode != 2) {
            f = f2;
        }
        animateValue(f);
    }

    @Override // android.animation.Animator
    boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public final boolean doAnimationFrame(long j) {
        long resolveDurationScale;
        if (this.mStartTime < 0) {
            if (this.mReversing) {
                resolveDurationScale = j;
            } else {
                resolveDurationScale = ((long) (this.mStartDelay * resolveDurationScale())) + j;
            }
            this.mStartTime = resolveDurationScale;
        }
        if (this.mPaused) {
            this.mPauseTime = j;
            removeAnimationCallback();
            return false;
        }
        if (this.mResumed) {
            this.mResumed = false;
            if (this.mPauseTime > 0) {
                this.mStartTime += j - this.mPauseTime;
            }
        }
        if (!this.mRunning) {
            if (this.mStartTime > j && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            startAnimation();
        }
        if (this.mLastFrameTime < 0) {
            if (this.mSeekFraction >= 0.0f) {
                this.mStartTime = j - ((long) (getScaledDuration() * this.mSeekFraction));
                this.mSeekFraction = -1.0f;
            }
            this.mStartTimeCommitted = false;
        }
        this.mLastFrameTime = j;
        boolean animateBasedOnTime = animateBasedOnTime(java.lang.Math.max(j, this.mStartTime));
        if (animateBasedOnTime) {
            endAnimation();
        }
        return animateBasedOnTime;
    }

    @Override // android.animation.Animator
    boolean pulseAnimationFrame(long j) {
        if (this.mSelfPulse) {
            return false;
        }
        return doAnimationFrame(j);
    }

    private void addOneShotCommitCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        getAnimationHandler().addOneShotCommitCallback(this);
    }

    private void removeAnimationCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        getAnimationHandler().removeCallback(this);
    }

    private void addAnimationCallback(long j) {
        if (!this.mSelfPulse) {
            return;
        }
        getAnimationHandler().addAnimationFrameCallback(this, j);
    }

    public float getAnimatedFraction() {
        return this.mCurrentFraction;
    }

    void animateValue(float f) {
        if (TRACE_ANIMATION_FRACTION) {
            android.os.Trace.traceCounter(8L, getNameForTrace() + hashCode(), (int) (1000.0f * f));
        }
        if (this.mValues == null) {
            return;
        }
        float interpolation = this.mInterpolator.getInterpolation(f);
        this.mCurrentFraction = interpolation;
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].calculateValue(interpolation);
        }
        if (this.mSeekFraction >= 0.0f || this.mStartListenersCalled) {
            callOnList(this.mUpdateListeners, android.animation.Animator.AnimatorCaller.ON_UPDATE, this, false);
        }
    }

    @Override // android.animation.Animator
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.animation.ValueAnimator mo77clone() {
        android.animation.ValueAnimator valueAnimator = (android.animation.ValueAnimator) super.mo77clone();
        if (this.mUpdateListeners != null) {
            valueAnimator.mUpdateListeners = new java.util.ArrayList<>(this.mUpdateListeners);
        }
        valueAnimator.mSeekFraction = -1.0f;
        valueAnimator.mReversing = false;
        valueAnimator.mInitialized = false;
        valueAnimator.mStarted = false;
        valueAnimator.mRunning = false;
        valueAnimator.mPaused = false;
        valueAnimator.mResumed = false;
        valueAnimator.mStartTime = -1L;
        valueAnimator.mStartTimeCommitted = false;
        valueAnimator.mAnimationEndRequested = false;
        valueAnimator.mPauseTime = -1L;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mFirstFrameTime = -1L;
        valueAnimator.mOverallFraction = 0.0f;
        valueAnimator.mCurrentFraction = 0.0f;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        android.animation.PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.mValues = new android.animation.PropertyValuesHolder[length];
            valueAnimator.mValuesMap = new java.util.HashMap<>(length);
            for (int i = 0; i < length; i++) {
                android.animation.PropertyValuesHolder mo122clone = propertyValuesHolderArr[i].mo122clone();
                valueAnimator.mValues[i] = mo122clone;
                valueAnimator.mValuesMap.put(mo122clone.getPropertyName(), mo122clone);
            }
        }
        return valueAnimator;
    }

    public static int getCurrentAnimationsCount() {
        return android.animation.AnimationHandler.getAnimationCount();
    }

    public java.lang.String toString() {
        java.lang.String str = "ValueAnimator@" + java.lang.Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                str = str + "\n    " + this.mValues[i].toString();
            }
        }
        return str;
    }

    @Override // android.animation.Animator
    public void setAllowRunningAsynchronously(boolean z) {
    }

    public android.animation.AnimationHandler getAnimationHandler() {
        return this.mAnimationHandler != null ? this.mAnimationHandler : android.animation.AnimationHandler.getInstance();
    }

    public void setAnimationHandler(android.animation.AnimationHandler animationHandler) {
        this.mAnimationHandler = animationHandler;
    }
}
