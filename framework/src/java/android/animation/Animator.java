package android.animation;

/* loaded from: classes.dex */
public abstract class Animator implements java.lang.Cloneable {
    public static final long DURATION_INFINITE = -1;
    private static long sBackgroundPauseDelay = 1000;
    private android.animation.Animator.AnimatorConstantState mConstantState;
    java.util.ArrayList<android.animation.Animator.AnimatorListener> mListeners = null;
    java.util.ArrayList<android.animation.Animator.AnimatorPauseListener> mPauseListeners = null;
    boolean mPaused = false;
    int mChangingConfigurations = 0;
    private java.util.concurrent.atomic.AtomicReference<java.lang.Object[]> mCachedList = new java.util.concurrent.atomic.AtomicReference<>();
    boolean mStartListenersCalled = false;

    public interface AnimatorPauseListener {
        void onAnimationPause(android.animation.Animator animator);

        void onAnimationResume(android.animation.Animator animator);
    }

    public abstract long getDuration();

    public abstract long getStartDelay();

    public abstract boolean isRunning();

    public abstract android.animation.Animator setDuration(long j);

    public abstract void setInterpolator(android.animation.TimeInterpolator timeInterpolator);

    public abstract void setStartDelay(long j);

    public static void setBackgroundPauseDelay(long j) {
        sBackgroundPauseDelay = j;
    }

    public static long getBackgroundPauseDelay() {
        return sBackgroundPauseDelay;
    }

    public static void setAnimatorPausingEnabled(boolean z) {
        android.animation.AnimationHandler.setAnimatorPausingEnabled(z);
        android.animation.AnimationHandler.setOverrideAnimatorPausingSystemProperty(!z);
    }

    public void start() {
    }

    public void cancel() {
    }

    public void end() {
    }

    public void pause() {
        if ((isStarted() || this.mStartListenersCalled) && !this.mPaused) {
            this.mPaused = true;
            notifyPauseListeners(android.animation.Animator.AnimatorCaller.ON_PAUSE);
        }
    }

    public void resume() {
        if (this.mPaused) {
            this.mPaused = false;
            notifyPauseListeners(android.animation.Animator.AnimatorCaller.ON_RESUME);
        }
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    public long getTotalDuration() {
        long duration = getDuration();
        if (duration == -1) {
            return -1L;
        }
        return getStartDelay() + duration;
    }

    public android.animation.TimeInterpolator getInterpolator() {
        return null;
    }

    public boolean isStarted() {
        return isRunning();
    }

    public void addListener(android.animation.Animator.AnimatorListener animatorListener) {
        if (this.mListeners == null) {
            this.mListeners = new java.util.ArrayList<>();
        }
        this.mListeners.add(animatorListener);
    }

    public void removeListener(android.animation.Animator.AnimatorListener animatorListener) {
        if (this.mListeners == null) {
            return;
        }
        this.mListeners.remove(animatorListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    public java.util.ArrayList<android.animation.Animator.AnimatorListener> getListeners() {
        return this.mListeners;
    }

    public void addPauseListener(android.animation.Animator.AnimatorPauseListener animatorPauseListener) {
        if (this.mPauseListeners == null) {
            this.mPauseListeners = new java.util.ArrayList<>();
        }
        this.mPauseListeners.add(animatorPauseListener);
    }

    public void removePauseListener(android.animation.Animator.AnimatorPauseListener animatorPauseListener) {
        if (this.mPauseListeners == null) {
            return;
        }
        this.mPauseListeners.remove(animatorPauseListener);
        if (this.mPauseListeners.size() == 0) {
            this.mPauseListeners = null;
        }
    }

    public void removeAllListeners() {
        if (this.mListeners != null) {
            this.mListeners.clear();
            this.mListeners = null;
        }
        if (this.mPauseListeners != null) {
            this.mPauseListeners.clear();
            this.mPauseListeners = null;
        }
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void setChangingConfigurations(int i) {
        this.mChangingConfigurations = i;
    }

    public void appendChangingConfigurations(int i) {
        this.mChangingConfigurations = i | this.mChangingConfigurations;
    }

    public android.content.res.ConstantState<android.animation.Animator> createConstantState() {
        return new android.animation.Animator.AnimatorConstantState(this);
    }

    @Override // 
    /* renamed from: clone */
    public android.animation.Animator mo77clone() {
        try {
            android.animation.Animator animator = (android.animation.Animator) super.clone();
            if (this.mListeners != null) {
                animator.mListeners = new java.util.ArrayList<>(this.mListeners);
            }
            if (this.mPauseListeners != null) {
                animator.mPauseListeners = new java.util.ArrayList<>(this.mPauseListeners);
            }
            animator.mCachedList.set(null);
            animator.mStartListenersCalled = false;
            return animator;
        } catch (java.lang.CloneNotSupportedException e) {
            throw new java.lang.AssertionError();
        }
    }

    public void setupStartValues() {
    }

    public void setupEndValues() {
    }

    public void setTarget(java.lang.Object obj) {
    }

    public boolean canReverse() {
        return false;
    }

    public void reverse() {
        throw new java.lang.IllegalStateException("Reverse is not supported");
    }

    boolean pulseAnimationFrame(long j) {
        return false;
    }

    void startWithoutPulsing(boolean z) {
        if (z) {
            reverse();
        } else {
            start();
        }
    }

    void skipToEndValue(boolean z) {
    }

    boolean isInitialized() {
        return true;
    }

    void animateValuesInRange(long j, long j2) {
    }

    void animateSkipToEnds(long j, long j2) {
    }

    void getStartAndEndTimes(android.util.LongArray longArray, long j) {
        long startDelay = getStartDelay() + j;
        if (longArray.indexOf(startDelay) < 0) {
            longArray.add(startDelay);
        }
        long totalDuration = getTotalDuration();
        if (totalDuration != -1) {
            long j2 = totalDuration + j;
            if (longArray.indexOf(j2) < 0) {
                longArray.add(j2);
            }
        }
    }

    void notifyListeners(android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorListener, android.animation.Animator> animatorCaller, boolean z) {
        callOnList(this.mListeners, animatorCaller, this, z);
    }

    void notifyPauseListeners(android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorPauseListener, android.animation.Animator> animatorCaller) {
        callOnList(this.mPauseListeners, animatorCaller, this, false);
    }

    void notifyStartListeners(boolean z) {
        boolean z2 = this.mStartListenersCalled;
        this.mStartListenersCalled = true;
        if (this.mListeners != null && !z2) {
            notifyListeners(android.animation.Animator.AnimatorCaller.ON_START, z);
        }
    }

    void notifyEndListeners(boolean z) {
        boolean z2 = this.mStartListenersCalled;
        this.mStartListenersCalled = false;
        if (this.mListeners != null && z2) {
            notifyListeners(android.animation.Animator.AnimatorCaller.ON_END, z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T, A> void callOnList(java.util.ArrayList<T> arrayList, android.animation.Animator.AnimatorCaller<T, A> animatorCaller, A a, boolean z) {
        int size = arrayList == null ? 0 : arrayList.size();
        if (size > 0) {
            java.lang.Object[] andSet = this.mCachedList.getAndSet(null);
            if (andSet == null || andSet.length < size) {
                andSet = new java.lang.Object[size];
            }
            arrayList.toArray(andSet);
            for (int i = 0; i < size; i++) {
                animatorCaller.call(andSet[i], a, z);
                andSet[i] = null;
            }
            this.mCachedList.compareAndSet(null, andSet);
        }
    }

    public interface AnimatorListener {
        void onAnimationCancel(android.animation.Animator animator);

        void onAnimationEnd(android.animation.Animator animator);

        void onAnimationRepeat(android.animation.Animator animator);

        void onAnimationStart(android.animation.Animator animator);

        default void onAnimationStart(android.animation.Animator animator, boolean z) {
            onAnimationStart(animator);
        }

        default void onAnimationEnd(android.animation.Animator animator, boolean z) {
            onAnimationEnd(animator);
        }
    }

    public void setAllowRunningAsynchronously(boolean z) {
    }

    private static class AnimatorConstantState extends android.content.res.ConstantState<android.animation.Animator> {
        final android.animation.Animator mAnimator;
        int mChangingConf;

        public AnimatorConstantState(android.animation.Animator animator) {
            this.mAnimator = animator;
            this.mAnimator.mConstantState = this;
            this.mChangingConf = this.mAnimator.getChangingConfigurations();
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.res.ConstantState
        public android.animation.Animator newInstance() {
            android.animation.Animator mo77clone = this.mAnimator.mo77clone();
            mo77clone.mConstantState = this;
            return mo77clone;
        }
    }

    interface AnimatorCaller<T, A> {
        public static final android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorListener, android.animation.Animator> ON_START = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda0
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.Animator.AnimatorListener) obj).onAnimationStart((android.animation.Animator) obj2, z);
            }
        };
        public static final android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorListener, android.animation.Animator> ON_END = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda1
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.Animator.AnimatorListener) obj).onAnimationEnd((android.animation.Animator) obj2, z);
            }
        };
        public static final android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorListener, android.animation.Animator> ON_CANCEL = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda2
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.Animator.AnimatorListener) obj).onAnimationCancel((android.animation.Animator) obj2);
            }
        };
        public static final android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorListener, android.animation.Animator> ON_REPEAT = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda3
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.Animator.AnimatorListener) obj).onAnimationRepeat((android.animation.Animator) obj2);
            }
        };
        public static final android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorPauseListener, android.animation.Animator> ON_PAUSE = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda4
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.Animator.AnimatorPauseListener) obj).onAnimationPause((android.animation.Animator) obj2);
            }
        };
        public static final android.animation.Animator.AnimatorCaller<android.animation.Animator.AnimatorPauseListener, android.animation.Animator> ON_RESUME = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda5
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.Animator.AnimatorPauseListener) obj).onAnimationResume((android.animation.Animator) obj2);
            }
        };
        public static final android.animation.Animator.AnimatorCaller<android.animation.ValueAnimator.AnimatorUpdateListener, android.animation.ValueAnimator> ON_UPDATE = new android.animation.Animator.AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda6
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(java.lang.Object obj, java.lang.Object obj2, boolean z) {
                ((android.animation.ValueAnimator.AnimatorUpdateListener) obj).onAnimationUpdate((android.animation.ValueAnimator) obj2);
            }
        };

        void call(T t, A a, boolean z);
    }
}
