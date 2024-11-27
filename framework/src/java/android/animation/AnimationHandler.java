package android.animation;

/* loaded from: classes.dex */
public class AnimationHandler {
    private static final boolean LOCAL_LOGV = false;
    private static final java.lang.String TAG = "AnimationHandler";
    private android.animation.AnimationHandler.AnimationFrameCallbackProvider mProvider;
    private static boolean sAnimatorPausingEnabled = isPauseBgAnimationsEnabledInSystemProperties();
    private static boolean sOverrideAnimatorPausingSystemProperty = false;
    public static final java.lang.ThreadLocal<android.animation.AnimationHandler> sAnimatorHandler = new java.lang.ThreadLocal<>();
    private static android.animation.AnimationHandler sTestHandler = null;
    private final android.util.ArrayMap<android.animation.AnimationHandler.AnimationFrameCallback, java.lang.Long> mDelayedCallbackStartTime = new android.util.ArrayMap<>();
    private final java.util.ArrayList<android.animation.AnimationHandler.AnimationFrameCallback> mAnimationCallbacks = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.animation.AnimationHandler.AnimationFrameCallback> mCommitCallbacks = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.animation.Animator> mPausedAnimators = new java.util.ArrayList<>();
    private final java.util.ArrayList<java.lang.ref.WeakReference<java.lang.Object>> mAnimatorRequestors = new java.util.ArrayList<>();
    private final android.view.Choreographer.FrameCallback mFrameCallback = new android.view.Choreographer.FrameCallback() { // from class: android.animation.AnimationHandler.1
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            android.animation.AnimationHandler.this.doAnimationFrame(android.animation.AnimationHandler.this.getProvider().getFrameTime());
            if (android.animation.AnimationHandler.this.mAnimationCallbacks.size() > 0) {
                android.animation.AnimationHandler.this.getProvider().postFrameCallback(this);
            }
        }
    };
    private boolean mListDirty = false;
    private android.view.Choreographer.FrameCallback mPauser = new android.view.Choreographer.FrameCallback() { // from class: android.animation.AnimationHandler$$ExternalSyntheticLambda0
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            android.animation.AnimationHandler.this.lambda$new$0(j);
        }
    };

    public interface AnimationFrameCallback {
        void commitAnimationFrame(long j);

        boolean doAnimationFrame(long j);
    }

    public interface AnimationFrameCallbackProvider {
        long getFrameDelay();

        long getFrameTime();

        void postCommitCallback(java.lang.Runnable runnable);

        void postFrameCallback(android.view.Choreographer.FrameCallback frameCallback);

        void setFrameDelay(long j);
    }

    public static android.animation.AnimationHandler getInstance() {
        if (sTestHandler != null) {
            return sTestHandler;
        }
        if (sAnimatorHandler.get() == null) {
            sAnimatorHandler.set(new android.animation.AnimationHandler());
        }
        return sAnimatorHandler.get();
    }

    public static android.animation.AnimationHandler setTestHandler(android.animation.AnimationHandler animationHandler) {
        android.animation.AnimationHandler animationHandler2 = sTestHandler;
        sTestHandler = animationHandler;
        return animationHandler2;
    }

    private static boolean isPauseBgAnimationsEnabledInSystemProperties() {
        return sOverrideAnimatorPausingSystemProperty ? sAnimatorPausingEnabled : android.os.SystemProperties.getBoolean("framework.pause_bg_animations.enabled", true);
    }

    public static void setAnimatorPausingEnabled(boolean z) {
        sAnimatorPausingEnabled = z;
    }

    public static void setOverrideAnimatorPausingSystemProperty(boolean z) {
        sOverrideAnimatorPausingSystemProperty = z;
    }

    public static void removeRequestor(java.lang.Object obj) {
        getInstance().requestAnimatorsEnabledImpl(false, obj);
    }

    public static void requestAnimatorsEnabled(boolean z, java.lang.Object obj) {
        getInstance().requestAnimatorsEnabledImpl(z, obj);
    }

    private void requestAnimatorsEnabledImpl(boolean z, java.lang.Object obj) {
        boolean isEmpty = this.mAnimatorRequestors.isEmpty();
        setAnimatorPausingEnabled(isPauseBgAnimationsEnabledInSystemProperties());
        synchronized (this.mAnimatorRequestors) {
            if (z) {
                java.lang.ref.WeakReference<java.lang.Object> weakReference = null;
                for (int size = this.mAnimatorRequestors.size() - 1; size >= 0; size--) {
                    java.lang.ref.WeakReference<java.lang.Object> weakReference2 = this.mAnimatorRequestors.get(size);
                    java.lang.Object obj2 = weakReference2.get();
                    if (obj2 == obj) {
                        weakReference = weakReference2;
                    } else if (obj2 == null) {
                        this.mAnimatorRequestors.remove(size);
                    }
                }
                if (weakReference == null) {
                    this.mAnimatorRequestors.add(new java.lang.ref.WeakReference<>(obj));
                }
            } else {
                for (int size2 = this.mAnimatorRequestors.size() - 1; size2 >= 0; size2--) {
                    java.lang.Object obj3 = this.mAnimatorRequestors.get(size2).get();
                    if (obj3 == obj || obj3 == null) {
                        this.mAnimatorRequestors.remove(size2);
                    }
                }
            }
        }
        if (!sAnimatorPausingEnabled) {
            resumeAnimators();
            return;
        }
        boolean isEmpty2 = this.mAnimatorRequestors.isEmpty();
        if (isEmpty != isEmpty2) {
            if (!isEmpty2) {
                resumeAnimators();
            } else {
                android.view.Choreographer.getInstance().postFrameCallbackDelayed(this.mPauser, android.animation.Animator.getBackgroundPauseDelay());
            }
        }
    }

    private void resumeAnimators() {
        android.view.Choreographer.getInstance().removeFrameCallback(this.mPauser);
        for (int size = this.mPausedAnimators.size() - 1; size >= 0; size--) {
            this.mPausedAnimators.get(size).resume();
        }
        this.mPausedAnimators.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(long j) {
        if (this.mAnimatorRequestors.size() > 0) {
            return;
        }
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            java.lang.Object obj = (android.animation.AnimationHandler.AnimationFrameCallback) this.mAnimationCallbacks.get(i);
            if (obj instanceof android.animation.Animator) {
                android.animation.Animator animator = (android.animation.Animator) obj;
                if (animator.getTotalDuration() == -1 && !animator.isPaused()) {
                    this.mPausedAnimators.add(animator);
                    animator.pause();
                }
            }
        }
    }

    public void setProvider(android.animation.AnimationHandler.AnimationFrameCallbackProvider animationFrameCallbackProvider) {
        if (animationFrameCallbackProvider == null) {
            this.mProvider = new android.animation.AnimationHandler.MyFrameCallbackProvider();
        } else {
            this.mProvider = animationFrameCallbackProvider;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.animation.AnimationHandler.AnimationFrameCallbackProvider getProvider() {
        if (this.mProvider == null) {
            this.mProvider = new android.animation.AnimationHandler.MyFrameCallbackProvider();
        }
        return this.mProvider;
    }

    public void addAnimationFrameCallback(android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback, long j) {
        if (this.mAnimationCallbacks.size() == 0) {
            getProvider().postFrameCallback(this.mFrameCallback);
        }
        if (!this.mAnimationCallbacks.contains(animationFrameCallback)) {
            this.mAnimationCallbacks.add(animationFrameCallback);
        }
        if (j > 0) {
            this.mDelayedCallbackStartTime.put(animationFrameCallback, java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis() + j));
        }
    }

    public void addOneShotCommitCallback(android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        if (!this.mCommitCallbacks.contains(animationFrameCallback)) {
            this.mCommitCallbacks.add(animationFrameCallback);
        }
    }

    public void removeCallback(android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        this.mCommitCallbacks.remove(animationFrameCallback);
        this.mDelayedCallbackStartTime.remove(animationFrameCallback);
        int indexOf = this.mAnimationCallbacks.indexOf(animationFrameCallback);
        if (indexOf >= 0) {
            this.mAnimationCallbacks.set(indexOf, null);
            this.mListDirty = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAnimationFrame(long j) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        int size = this.mAnimationCallbacks.size();
        for (int i = 0; i < size; i++) {
            final android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(i);
            if (animationFrameCallback != null && isCallbackDue(animationFrameCallback, uptimeMillis)) {
                animationFrameCallback.doAnimationFrame(j);
                if (this.mCommitCallbacks.contains(animationFrameCallback)) {
                    getProvider().postCommitCallback(new java.lang.Runnable() { // from class: android.animation.AnimationHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            android.animation.AnimationHandler.this.commitAnimationFrame(animationFrameCallback, android.animation.AnimationHandler.this.getProvider().getFrameTime());
                        }
                    });
                }
            }
        }
        cleanUpList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commitAnimationFrame(android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback, long j) {
        if (!this.mDelayedCallbackStartTime.containsKey(animationFrameCallback) && this.mCommitCallbacks.contains(animationFrameCallback)) {
            animationFrameCallback.commitAnimationFrame(j);
            this.mCommitCallbacks.remove(animationFrameCallback);
        }
    }

    private boolean isCallbackDue(android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback, long j) {
        java.lang.Long l = this.mDelayedCallbackStartTime.get(animationFrameCallback);
        if (l == null) {
            return true;
        }
        if (l.longValue() < j) {
            this.mDelayedCallbackStartTime.remove(animationFrameCallback);
            return true;
        }
        return false;
    }

    public static int getAnimationCount() {
        android.animation.AnimationHandler animationHandler = sTestHandler;
        if (animationHandler == null) {
            animationHandler = sAnimatorHandler.get();
        }
        if (animationHandler == null) {
            return 0;
        }
        return animationHandler.getCallbackSize();
    }

    public static void setFrameDelay(long j) {
        getInstance().getProvider().setFrameDelay(j);
    }

    public static long getFrameDelay() {
        return getInstance().getProvider().getFrameDelay();
    }

    void autoCancelBasedOn(android.animation.ObjectAnimator objectAnimator) {
        for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
            android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(size);
            if (animationFrameCallback != null && objectAnimator.shouldAutoCancel(animationFrameCallback)) {
                ((android.animation.Animator) this.mAnimationCallbacks.get(size)).cancel();
            }
        }
    }

    private void cleanUpList() {
        if (this.mListDirty) {
            for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
                if (this.mAnimationCallbacks.get(size) == null) {
                    this.mAnimationCallbacks.remove(size);
                }
            }
            this.mListDirty = false;
        }
    }

    private int getCallbackSize() {
        int i = 0;
        for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
            if (this.mAnimationCallbacks.get(size) != null) {
                i++;
            }
        }
        return i;
    }

    private class MyFrameCallbackProvider implements android.animation.AnimationHandler.AnimationFrameCallbackProvider {
        final android.view.Choreographer mChoreographer;

        private MyFrameCallbackProvider() {
            this.mChoreographer = android.view.Choreographer.getInstance();
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postFrameCallback(android.view.Choreographer.FrameCallback frameCallback) {
            this.mChoreographer.postFrameCallback(frameCallback);
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postCommitCallback(java.lang.Runnable runnable) {
            this.mChoreographer.postCallback(4, runnable, null);
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public long getFrameTime() {
            return this.mChoreographer.getFrameTime();
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public long getFrameDelay() {
            return android.view.Choreographer.getFrameDelay();
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void setFrameDelay(long j) {
            android.view.Choreographer.setFrameDelay(j);
        }
    }
}
