package android.animation;

/* loaded from: classes.dex */
public final class AnimatorSet extends android.animation.Animator implements android.animation.AnimationHandler.AnimationFrameCallback {
    private static final java.lang.String TAG = "AnimatorSet";
    private long[] mChildStartAndStopTimes;
    private final boolean mEndCanBeCalled;
    private final boolean mShouldIgnoreEndWithoutStart;
    private final boolean mShouldResetValuesAtStart;
    private java.util.ArrayList<android.animation.AnimatorSet.Node> mPlayingSet = new java.util.ArrayList<>();
    private android.util.ArrayMap<android.animation.Animator, android.animation.AnimatorSet.Node> mNodeMap = new android.util.ArrayMap<>();
    private java.util.ArrayList<android.animation.AnimatorSet.AnimationEvent> mEvents = new java.util.ArrayList<>();
    private java.util.ArrayList<android.animation.AnimatorSet.Node> mNodes = new java.util.ArrayList<>();
    private boolean mDependencyDirty = false;
    private boolean mStarted = false;
    private long mStartDelay = 0;
    private android.animation.ValueAnimator mDelayAnim = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(0L);
    private android.animation.AnimatorSet.Node mRootNode = new android.animation.AnimatorSet.Node(this.mDelayAnim);
    private long mDuration = -1;
    private android.animation.TimeInterpolator mInterpolator = null;
    private long mTotalDuration = 0;
    private long mLastFrameTime = -1;
    private long mFirstFrame = -1;
    private int mLastEventId = -1;
    private boolean mReversing = false;
    private boolean mSelfPulse = true;
    private android.animation.AnimatorSet.SeekState mSeekState = new android.animation.AnimatorSet.SeekState();
    private boolean mChildrenInitialized = false;
    private long mPauseTime = -1;
    private android.animation.AnimatorListenerAdapter mAnimationEndListener = new android.animation.AnimatorListenerAdapter() { // from class: android.animation.AnimatorSet.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (android.animation.AnimatorSet.this.mNodeMap.get(animator) == null) {
                throw new android.util.AndroidRuntimeException("Error: animation ended is not in the node map");
            }
            ((android.animation.AnimatorSet.Node) android.animation.AnimatorSet.this.mNodeMap.get(animator)).mEnded = true;
        }
    };

    public AnimatorSet() {
        boolean z = false;
        this.mNodeMap.put(this.mDelayAnim, this.mRootNode);
        this.mNodes.add(this.mRootNode);
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null || currentApplication.getApplicationInfo() == null) {
            this.mShouldIgnoreEndWithoutStart = true;
            z = true;
        } else {
            if (currentApplication.getApplicationInfo().targetSdkVersion < 24) {
                this.mShouldIgnoreEndWithoutStart = true;
            } else {
                this.mShouldIgnoreEndWithoutStart = false;
            }
            if (currentApplication.getApplicationInfo().targetSdkVersion < 26) {
                z = true;
            }
        }
        this.mShouldResetValuesAtStart = !z;
        this.mEndCanBeCalled = !z;
    }

    public void playTogether(android.animation.Animator... animatorArr) {
        if (animatorArr != null) {
            android.animation.AnimatorSet.Builder play = play(animatorArr[0]);
            for (int i = 1; i < animatorArr.length; i++) {
                play.with(animatorArr[i]);
            }
        }
    }

    public void playTogether(java.util.Collection<android.animation.Animator> collection) {
        if (collection != null && collection.size() > 0) {
            android.animation.AnimatorSet.Builder builder = null;
            for (android.animation.Animator animator : collection) {
                if (builder == null) {
                    builder = play(animator);
                } else {
                    builder.with(animator);
                }
            }
        }
    }

    public void playSequentially(android.animation.Animator... animatorArr) {
        if (animatorArr != null) {
            int i = 0;
            if (animatorArr.length == 1) {
                play(animatorArr[0]);
                return;
            }
            while (i < animatorArr.length - 1) {
                android.animation.AnimatorSet.Builder play = play(animatorArr[i]);
                i++;
                play.before(animatorArr[i]);
            }
        }
    }

    public void playSequentially(java.util.List<android.animation.Animator> list) {
        if (list != null && list.size() > 0) {
            int i = 0;
            if (list.size() == 1) {
                play(list.get(0));
                return;
            }
            while (i < list.size() - 1) {
                android.animation.AnimatorSet.Builder play = play(list.get(i));
                i++;
                play.before(list.get(i));
            }
        }
    }

    public java.util.ArrayList<android.animation.Animator> getChildAnimations() {
        java.util.ArrayList<android.animation.Animator> arrayList = new java.util.ArrayList<>();
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i);
            if (node != this.mRootNode) {
                arrayList.add(node.mAnimation);
            }
        }
        return arrayList;
    }

    @Override // android.animation.Animator
    public void setTarget(java.lang.Object obj) {
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            android.animation.Animator animator = this.mNodes.get(i).mAnimation;
            if (animator instanceof android.animation.AnimatorSet) {
                ((android.animation.AnimatorSet) animator).setTarget(obj);
            } else if (animator instanceof android.animation.ObjectAnimator) {
                ((android.animation.ObjectAnimator) animator).setTarget(obj);
            }
        }
    }

    @Override // android.animation.Animator
    public int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            changingConfigurations |= this.mNodes.get(i).mAnimation.getChangingConfigurations();
        }
        return changingConfigurations;
    }

    @Override // android.animation.Animator
    public void setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    @Override // android.animation.Animator
    public android.animation.TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public android.animation.AnimatorSet.Builder play(android.animation.Animator animator) {
        if (animator != null) {
            return new android.animation.AnimatorSet.Builder(animator);
        }
        return null;
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (isStarted() || this.mStartListenersCalled) {
            notifyListeners(android.animation.Animator.AnimatorCaller.ON_CANCEL, false);
            callOnPlayingSet(new java.util.function.Consumer() { // from class: android.animation.AnimatorSet$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.animation.Animator) obj).cancel();
                }
            });
            this.mPlayingSet.clear();
            endAnimation();
        }
    }

    private void callOnPlayingSet(java.util.function.Consumer<android.animation.Animator> consumer) {
        java.util.ArrayList<android.animation.AnimatorSet.Node> arrayList = this.mPlayingSet;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(arrayList.get(i).mAnimation);
        }
    }

    private void forceToEnd() {
        if (this.mEndCanBeCalled) {
            end();
            return;
        }
        if (this.mReversing) {
            handleAnimationEvents(this.mLastEventId, 0, getTotalDuration());
        } else {
            long totalDuration = getTotalDuration();
            if (totalDuration == -1) {
                totalDuration = 2147483647L;
            }
            handleAnimationEvents(this.mLastEventId, this.mEvents.size() - 1, totalDuration);
        }
        this.mPlayingSet.clear();
        endAnimation();
    }

    @Override // android.animation.Animator
    public void end() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mShouldIgnoreEndWithoutStart && !isStarted()) {
            return;
        }
        if (isStarted()) {
            this.mStarted = false;
            if (this.mReversing) {
                this.mLastEventId = this.mLastEventId == -1 ? this.mEvents.size() : this.mLastEventId;
                for (int i = this.mLastEventId - 1; i >= 0; i--) {
                    android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i);
                    android.animation.Animator animator = animationEvent.mNode.mAnimation;
                    if (!this.mNodeMap.get(animator).mEnded) {
                        if (animationEvent.mEvent == 2) {
                            animator.reverse();
                        } else if (animationEvent.mEvent == 1 && animator.isStarted()) {
                            animator.end();
                        }
                    }
                }
            } else {
                for (int i2 = this.mLastEventId + 1; i2 < this.mEvents.size(); i2++) {
                    android.animation.AnimatorSet.AnimationEvent animationEvent2 = this.mEvents.get(i2);
                    android.animation.Animator animator2 = animationEvent2.mNode.mAnimation;
                    if (!this.mNodeMap.get(animator2).mEnded) {
                        if (animationEvent2.mEvent == 0) {
                            animator2.start();
                        } else if (animationEvent2.mEvent == 2 && animator2.isStarted()) {
                            animator2.end();
                        }
                    }
                }
            }
        }
        endAnimation();
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        if (this.mStartDelay == 0) {
            return this.mStarted;
        }
        return this.mLastFrameTime > 0;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
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
        long j2 = j - this.mStartDelay;
        if (j2 == 0) {
            return;
        }
        this.mStartDelay = j;
        if (!this.mDependencyDirty) {
            int size = this.mNodes.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                android.animation.AnimatorSet.Node node = this.mNodes.get(i);
                if (node == this.mRootNode) {
                    node.mEndTime = this.mStartDelay;
                } else {
                    node.mStartTime = node.mStartTime == -1 ? -1L : node.mStartTime + j2;
                    node.mEndTime = node.mEndTime != -1 ? node.mEndTime + j2 : -1L;
                }
                i++;
            }
            if (this.mTotalDuration != -1) {
                this.mTotalDuration += j2;
            }
        }
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.animation.Animator
    public android.animation.AnimatorSet setDuration(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("duration must be a value of zero or greater");
        }
        this.mDependencyDirty = true;
        this.mDuration = j;
        return this;
    }

    @Override // android.animation.Animator
    public void setupStartValues() {
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i);
            if (node != this.mRootNode) {
                node.mAnimation.setupStartValues();
            }
        }
    }

    @Override // android.animation.Animator
    public void setupEndValues() {
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i);
            if (node != this.mRootNode) {
                node.mAnimation.setupEndValues();
            }
        }
    }

    @Override // android.animation.Animator
    public void pause() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        boolean z = this.mPaused;
        super.pause();
        if (!z && this.mPaused) {
            this.mPauseTime = -1L;
            callOnPlayingSet(new java.util.function.Consumer() { // from class: android.animation.AnimatorSet$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.animation.Animator) obj).pause();
                }
            });
        }
    }

    @Override // android.animation.Animator
    public void resume() {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        boolean z = this.mPaused;
        super.resume();
        if (z && !this.mPaused) {
            if (this.mPauseTime >= 0) {
                addAnimationCallback(0L);
            }
            callOnPlayingSet(new java.util.function.Consumer() { // from class: android.animation.AnimatorSet$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.animation.Animator) obj).resume();
                }
            });
        }
    }

    @Override // android.animation.Animator
    public void start() {
        start(false, true);
    }

    @Override // android.animation.Animator
    void startWithoutPulsing(boolean z) {
        start(z, false);
    }

    private void initAnimation() {
        if (this.mInterpolator != null) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                this.mNodes.get(i).mAnimation.setInterpolator(this.mInterpolator);
            }
        }
        updateAnimatorsDuration();
        createDependencyGraph();
    }

    private void start(boolean z, boolean z2) {
        if (android.os.Looper.myLooper() == null) {
            throw new android.util.AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (z == this.mReversing && z2 == this.mSelfPulse && this.mStarted) {
            return;
        }
        this.mStarted = true;
        this.mSelfPulse = z2;
        this.mPaused = false;
        this.mPauseTime = -1L;
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i);
            node.mEnded = false;
            node.mAnimation.setAllowRunningAsynchronously(false);
        }
        initAnimation();
        if (z && !canReverse()) {
            throw new java.lang.UnsupportedOperationException("Cannot reverse infinite AnimatorSet");
        }
        this.mReversing = z;
        boolean isEmptySet = isEmptySet(this);
        if (!isEmptySet) {
            startAnimation();
        }
        notifyStartListeners(z);
        if (isEmptySet) {
            end();
        }
    }

    private static boolean isEmptySet(android.animation.AnimatorSet animatorSet) {
        if (animatorSet.getStartDelay() > 0) {
            return false;
        }
        for (int i = 0; i < animatorSet.getChildAnimations().size(); i++) {
            android.animation.Animator animator = animatorSet.getChildAnimations().get(i);
            if (!(animator instanceof android.animation.AnimatorSet) || !isEmptySet((android.animation.AnimatorSet) animator)) {
                return false;
            }
        }
        return true;
    }

    private void updateAnimatorsDuration() {
        if (this.mDuration >= 0) {
            int size = this.mNodes.size();
            for (int i = 0; i < size; i++) {
                this.mNodes.get(i).mAnimation.setDuration(this.mDuration);
            }
        }
        this.mDelayAnim.setDuration(this.mStartDelay);
    }

    @Override // android.animation.Animator
    void skipToEndValue(boolean z) {
        initAnimation();
        initChildren();
        if (z) {
            for (int size = this.mEvents.size() - 1; size >= 0; size--) {
                android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(size);
                if (animationEvent.mEvent == 1) {
                    animationEvent.mNode.mAnimation.skipToEndValue(true);
                }
            }
            return;
        }
        for (int i = 0; i < this.mEvents.size(); i++) {
            android.animation.AnimatorSet.AnimationEvent animationEvent2 = this.mEvents.get(i);
            if (animationEvent2.mEvent == 2) {
                animationEvent2.mNode.mAnimation.skipToEndValue(false);
            }
        }
    }

    private void animateBasedOnPlayTime(long j, long j2, boolean z) {
        if (j < 0 || j2 < -1) {
            throw new java.lang.UnsupportedOperationException("Error: Play time should never be negative.");
        }
        if (z) {
            long totalDuration = getTotalDuration();
            if (totalDuration == -1) {
                throw new java.lang.UnsupportedOperationException("Cannot reverse AnimatorSet with infinite duration");
            }
            j = totalDuration - java.lang.Math.min(j, totalDuration);
            j2 = totalDuration - j2;
        }
        long[] ensureChildStartAndEndTimes = ensureChildStartAndEndTimes();
        int findNextIndex = findNextIndex(j2, ensureChildStartAndEndTimes);
        int findNextIndex2 = findNextIndex(j, ensureChildStartAndEndTimes);
        if (j >= j2) {
            while (findNextIndex < findNextIndex2) {
                long j3 = ensureChildStartAndEndTimes[findNextIndex];
                if (j2 != j3) {
                    animateSkipToEnds(j3, j2);
                    animateValuesInRange(j3, j2);
                    j2 = j3;
                }
                findNextIndex++;
            }
        } else {
            while (findNextIndex > findNextIndex2) {
                findNextIndex--;
                long j4 = ensureChildStartAndEndTimes[findNextIndex];
                if (j2 != j4) {
                    animateSkipToEnds(j4, j2);
                    animateValuesInRange(j4, j2);
                    j2 = j4;
                }
            }
        }
        if (j != j2) {
            animateSkipToEnds(j, j2);
            animateValuesInRange(j, j2);
        }
    }

    private int findNextIndex(long j, long[] jArr) {
        int binarySearch = java.util.Arrays.binarySearch(jArr, j);
        if (binarySearch < 0) {
            return (-binarySearch) - 1;
        }
        return binarySearch + 1;
    }

    @Override // android.animation.Animator
    void animateSkipToEnds(long j, long j2) {
        initAnimation();
        if (j2 <= j) {
            notifyStartListeners(false);
            int size = this.mEvents.size();
            for (int i = 0; i < size; i++) {
                android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i);
                android.animation.AnimatorSet.Node node = animationEvent.mNode;
                if (animationEvent.mEvent == 1 && node.mStartTime != -1) {
                    android.animation.Animator animator = node.mAnimation;
                    long j3 = node.mStartTime;
                    long j4 = node.mTotalDuration == -1 ? Long.MAX_VALUE : node.mEndTime;
                    if (j2 < j4 && j4 <= j) {
                        animator.animateSkipToEnds(j4 - node.mStartTime, j2 - node.mStartTime);
                        this.mPlayingSet.remove(node);
                    } else if (j3 <= j && j <= j4) {
                        animator.animateSkipToEnds(j - node.mStartTime, j2 - node.mStartTime);
                        if (!this.mPlayingSet.contains(node)) {
                            this.mPlayingSet.add(node);
                        }
                    }
                }
            }
            if (j >= getTotalDuration()) {
                notifyEndListeners(false);
                return;
            }
            return;
        }
        notifyStartListeners(true);
        for (int size2 = this.mEvents.size() - 1; size2 >= 0; size2--) {
            android.animation.AnimatorSet.AnimationEvent animationEvent2 = this.mEvents.get(size2);
            android.animation.AnimatorSet.Node node2 = animationEvent2.mNode;
            if (animationEvent2.mEvent == 2 && node2.mStartTime != -1) {
                android.animation.Animator animator2 = node2.mAnimation;
                long j5 = node2.mStartTime;
                long j6 = node2.mTotalDuration == -1 ? Long.MAX_VALUE : node2.mEndTime;
                if (j <= j5 && j5 < j2) {
                    animator2.animateSkipToEnds(0L, j2 - node2.mStartTime);
                    this.mPlayingSet.remove(node2);
                } else if (j5 <= j && j <= j6) {
                    animator2.animateSkipToEnds(j - node2.mStartTime, j2 - node2.mStartTime);
                    if (!this.mPlayingSet.contains(node2)) {
                        this.mPlayingSet.add(node2);
                    }
                }
            }
        }
        if (j <= 0) {
            notifyEndListeners(true);
        }
    }

    @Override // android.animation.Animator
    void animateValuesInRange(long j, long j2) {
        initAnimation();
        if (j2 < 0 || (j2 == 0 && j > 0)) {
            notifyStartListeners(false);
        } else {
            long totalDuration = getTotalDuration();
            if (totalDuration >= 0 && (j2 > totalDuration || (j2 == totalDuration && j < totalDuration))) {
                notifyStartListeners(true);
            }
        }
        int size = this.mEvents.size();
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i);
            android.animation.AnimatorSet.Node node = animationEvent.mNode;
            if (animationEvent.mEvent == 1 && node.mStartTime != -1) {
                android.animation.Animator animator = node.mAnimation;
                long j3 = node.mStartTime;
                long j4 = node.mTotalDuration == -1 ? Long.MAX_VALUE : node.mEndTime;
                if ((j3 < j && j < j4) || ((j3 == j && j2 < j3) || (j4 == j && j2 > j4))) {
                    animator.animateValuesInRange(j - node.mStartTime, java.lang.Math.max(-1L, j2 - node.mStartTime));
                }
            }
        }
    }

    private long[] ensureChildStartAndEndTimes() {
        if (this.mChildStartAndStopTimes == null) {
            android.util.LongArray longArray = new android.util.LongArray();
            getStartAndEndTimes(longArray, 0L);
            long[] array = longArray.toArray();
            java.util.Arrays.sort(array);
            this.mChildStartAndStopTimes = array;
        }
        return this.mChildStartAndStopTimes;
    }

    @Override // android.animation.Animator
    void getStartAndEndTimes(android.util.LongArray longArray, long j) {
        int size = this.mEvents.size();
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i);
            if (animationEvent.mEvent == 1 && animationEvent.mNode.mStartTime != -1) {
                animationEvent.mNode.mAnimation.getStartAndEndTimes(longArray, animationEvent.mNode.mStartTime + j);
            }
        }
    }

    @Override // android.animation.Animator
    boolean isInitialized() {
        boolean z = true;
        if (this.mChildrenInitialized) {
            return true;
        }
        int i = 0;
        while (true) {
            if (i >= this.mNodes.size()) {
                break;
            }
            if (this.mNodes.get(i).mAnimation.isInitialized()) {
                i++;
            } else {
                z = false;
                break;
            }
        }
        this.mChildrenInitialized = z;
        return this.mChildrenInitialized;
    }

    public void setCurrentPlayTime(long j) {
        if (this.mReversing && getTotalDuration() == -1) {
            throw new java.lang.UnsupportedOperationException("Error: Cannot seek in reverse in an infinite AnimatorSet");
        }
        if ((getTotalDuration() != -1 && j > getTotalDuration() - this.mStartDelay) || j < 0) {
            throw new java.lang.UnsupportedOperationException("Error: Play time should always be in between 0 and duration.");
        }
        initAnimation();
        long playTime = this.mSeekState.getPlayTime();
        if (!isStarted() || isPaused()) {
            if (this.mReversing && !isStarted()) {
                throw new java.lang.UnsupportedOperationException("Error: Something went wrong. mReversing should not be set when AnimatorSet is not started.");
            }
            if (!this.mSeekState.isActive()) {
                findLatestEventIdForTime(0L);
                initChildren();
                skipToEndValue(!this.mReversing);
                this.mSeekState.setPlayTime(0L, this.mReversing);
            }
        }
        this.mSeekState.setPlayTime(j, this.mReversing);
        animateBasedOnPlayTime(j, playTime, this.mReversing);
    }

    public long getCurrentPlayTime() {
        if (this.mSeekState.isActive()) {
            return this.mSeekState.getPlayTime();
        }
        if (this.mLastFrameTime == -1) {
            return 0L;
        }
        float durationScale = android.animation.ValueAnimator.getDurationScale();
        if (durationScale == 0.0f) {
            durationScale = 1.0f;
        }
        if (this.mReversing) {
            return (long) ((this.mLastFrameTime - this.mFirstFrame) / durationScale);
        }
        return (long) (((this.mLastFrameTime - this.mFirstFrame) - this.mStartDelay) / durationScale);
    }

    private void initChildren() {
        if (!isInitialized()) {
            this.mChildrenInitialized = true;
            skipToEndValue(false);
        }
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        boolean z;
        float durationScale = android.animation.ValueAnimator.getDurationScale();
        if (durationScale == 0.0f) {
            forceToEnd();
            return true;
        }
        if (this.mFirstFrame < 0) {
            this.mFirstFrame = j;
        }
        if (this.mPaused) {
            this.mPauseTime = j;
            removeAnimationCallback();
            return false;
        }
        if (this.mPauseTime > 0) {
            this.mFirstFrame += j - this.mPauseTime;
            this.mPauseTime = -1L;
        }
        if (this.mSeekState.isActive()) {
            this.mSeekState.updateSeekDirection(this.mReversing);
            if (this.mReversing) {
                this.mFirstFrame = (long) (j - (this.mSeekState.getPlayTime() * durationScale));
            } else {
                this.mFirstFrame = (long) (j - ((this.mSeekState.getPlayTime() + this.mStartDelay) * durationScale));
            }
            this.mSeekState.reset();
        }
        if (!this.mReversing && j < this.mFirstFrame + (this.mStartDelay * durationScale)) {
            return false;
        }
        long j2 = (long) ((j - this.mFirstFrame) / durationScale);
        this.mLastFrameTime = j;
        int findLatestEventIdForTime = findLatestEventIdForTime(j2);
        handleAnimationEvents(this.mLastEventId, findLatestEventIdForTime, j2);
        this.mLastEventId = findLatestEventIdForTime;
        for (int i = 0; i < this.mPlayingSet.size(); i++) {
            android.animation.AnimatorSet.Node node = this.mPlayingSet.get(i);
            if (!node.mEnded) {
                pulseFrame(node, getPlayTimeForNodeIncludingDelay(j2, node));
            }
        }
        for (int size = this.mPlayingSet.size() - 1; size >= 0; size--) {
            if (this.mPlayingSet.get(size).mEnded) {
                this.mPlayingSet.remove(size);
            }
        }
        if (this.mReversing) {
            if (this.mPlayingSet.size() == 1 && this.mPlayingSet.get(0) == this.mRootNode) {
                z = true;
            } else if (this.mPlayingSet.isEmpty() && this.mLastEventId < 3) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = this.mPlayingSet.isEmpty() && this.mLastEventId == this.mEvents.size() - 1;
        }
        if (!z) {
            return false;
        }
        endAnimation();
        return true;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public void commitAnimationFrame(long j) {
    }

    @Override // android.animation.Animator
    boolean pulseAnimationFrame(long j) {
        return doAnimationFrame(j);
    }

    private void handleAnimationEvents(int i, int i2, long j) {
        if (this.mReversing) {
            if (i == -1) {
                i = this.mEvents.size();
            }
            for (int i3 = i - 1; i3 >= i2; i3--) {
                android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i3);
                android.animation.AnimatorSet.Node node = animationEvent.mNode;
                if (animationEvent.mEvent == 2) {
                    if (node.mAnimation.isStarted()) {
                        node.mAnimation.cancel();
                    }
                    node.mEnded = false;
                    this.mPlayingSet.add(animationEvent.mNode);
                    node.mAnimation.startWithoutPulsing(true);
                    pulseFrame(node, 0L);
                } else if (animationEvent.mEvent == 1 && !node.mEnded) {
                    pulseFrame(node, getPlayTimeForNodeIncludingDelay(j, node));
                }
            }
            return;
        }
        for (int i4 = i + 1; i4 <= i2; i4++) {
            android.animation.AnimatorSet.AnimationEvent animationEvent2 = this.mEvents.get(i4);
            android.animation.AnimatorSet.Node node2 = animationEvent2.mNode;
            if (animationEvent2.mEvent == 0) {
                this.mPlayingSet.add(animationEvent2.mNode);
                if (node2.mAnimation.isStarted()) {
                    node2.mAnimation.cancel();
                }
                node2.mEnded = false;
                node2.mAnimation.startWithoutPulsing(false);
                pulseFrame(node2, 0L);
            } else if (animationEvent2.mEvent == 2 && !node2.mEnded) {
                pulseFrame(node2, getPlayTimeForNodeIncludingDelay(j, node2));
            }
        }
    }

    private void pulseFrame(android.animation.AnimatorSet.Node node, long j) {
        if (!node.mEnded) {
            float durationScale = android.animation.ValueAnimator.getDurationScale();
            if (durationScale == 0.0f) {
                durationScale = 1.0f;
            }
            if (node.mAnimation.pulseAnimationFrame((long) (j * durationScale))) {
                node.mEnded = true;
            }
        }
    }

    private long getPlayTimeForNodeIncludingDelay(long j, android.animation.AnimatorSet.Node node) {
        return getPlayTimeForNodeIncludingDelay(j, node, this.mReversing);
    }

    private long getPlayTimeForNodeIncludingDelay(long j, android.animation.AnimatorSet.Node node, boolean z) {
        if (z) {
            return node.mEndTime - (getTotalDuration() - j);
        }
        return j - node.mStartTime;
    }

    private void startAnimation() {
        addAnimationEndListener();
        long j = 0;
        addAnimationCallback(0L);
        if (this.mSeekState.getPlayTimeNormalized() == 0 && this.mReversing) {
            this.mSeekState.reset();
        }
        if (this.mShouldResetValuesAtStart) {
            if (isInitialized()) {
                skipToEndValue(!this.mReversing);
            } else if (this.mReversing) {
                initChildren();
                skipToEndValue(!this.mReversing);
            } else {
                for (int size = this.mEvents.size() - 1; size >= 0; size--) {
                    if (this.mEvents.get(size).mEvent == 1) {
                        android.animation.Animator animator = this.mEvents.get(size).mNode.mAnimation;
                        if (animator.isInitialized()) {
                            animator.skipToEndValue(true);
                        }
                    }
                }
            }
        }
        if (this.mReversing || this.mStartDelay == 0 || this.mSeekState.isActive()) {
            if (this.mSeekState.isActive()) {
                this.mSeekState.updateSeekDirection(this.mReversing);
                j = this.mSeekState.getPlayTime();
            }
            int findLatestEventIdForTime = findLatestEventIdForTime(j);
            handleAnimationEvents(-1, findLatestEventIdForTime, j);
            for (int size2 = this.mPlayingSet.size() - 1; size2 >= 0; size2--) {
                if (this.mPlayingSet.get(size2).mEnded) {
                    this.mPlayingSet.remove(size2);
                }
            }
            this.mLastEventId = findLatestEventIdForTime;
        }
    }

    private void addAnimationEndListener() {
        for (int i = 1; i < this.mNodes.size(); i++) {
            this.mNodes.get(i).mAnimation.addListener(this.mAnimationEndListener);
        }
    }

    private void removeAnimationEndListener() {
        for (int i = 1; i < this.mNodes.size(); i++) {
            this.mNodes.get(i).mAnimation.removeListener(this.mAnimationEndListener);
        }
    }

    private int findLatestEventIdForTime(long j) {
        int size = this.mEvents.size();
        int i = this.mLastEventId;
        if (this.mReversing) {
            long totalDuration = getTotalDuration() - j;
            if (this.mLastEventId != -1) {
                size = this.mLastEventId;
            }
            this.mLastEventId = size;
            for (int i2 = this.mLastEventId - 1; i2 >= 0; i2--) {
                if (this.mEvents.get(i2).getTime() >= totalDuration) {
                    i = i2;
                }
            }
        } else {
            int i3 = this.mLastEventId;
            while (true) {
                i3++;
                if (i3 >= size) {
                    break;
                }
                android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i3);
                if (animationEvent.getTime() != -1 && animationEvent.getTime() <= j) {
                    i = i3;
                }
            }
        }
        return i;
    }

    private void endAnimation() {
        this.mStarted = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mPaused = false;
        this.mPauseTime = -1L;
        this.mSeekState.reset();
        this.mPlayingSet.clear();
        removeAnimationCallback();
        notifyEndListeners(this.mReversing);
        removeAnimationEndListener();
        this.mSelfPulse = true;
        this.mReversing = false;
    }

    private void removeAnimationCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        android.animation.AnimationHandler.getInstance().removeCallback(this);
    }

    private void addAnimationCallback(long j) {
        if (!this.mSelfPulse) {
            return;
        }
        android.animation.AnimationHandler.getInstance().addAnimationFrameCallback(this, j);
    }

    @Override // android.animation.Animator
    /* renamed from: clone */
    public android.animation.AnimatorSet mo77clone() {
        final android.animation.AnimatorSet animatorSet = (android.animation.AnimatorSet) super.mo77clone();
        int size = this.mNodes.size();
        animatorSet.mStarted = false;
        animatorSet.mLastFrameTime = -1L;
        animatorSet.mFirstFrame = -1L;
        animatorSet.mLastEventId = -1;
        animatorSet.mPaused = false;
        animatorSet.mPauseTime = -1L;
        animatorSet.mSeekState = new android.animation.AnimatorSet.SeekState();
        animatorSet.mSelfPulse = true;
        animatorSet.mStartListenersCalled = false;
        animatorSet.mPlayingSet = new java.util.ArrayList<>();
        animatorSet.mNodeMap = new android.util.ArrayMap<>();
        animatorSet.mNodes = new java.util.ArrayList<>(size);
        animatorSet.mEvents = new java.util.ArrayList<>();
        animatorSet.mAnimationEndListener = new android.animation.AnimatorListenerAdapter() { // from class: android.animation.AnimatorSet.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                if (animatorSet.mNodeMap.get(animator) == null) {
                    throw new android.util.AndroidRuntimeException("Error: animation ended is not in the node map");
                }
                ((android.animation.AnimatorSet.Node) animatorSet.mNodeMap.get(animator)).mEnded = true;
            }
        };
        animatorSet.mReversing = false;
        animatorSet.mDependencyDirty = true;
        java.util.HashMap hashMap = new java.util.HashMap(size);
        for (int i = 0; i < size; i++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i);
            android.animation.AnimatorSet.Node m83clone = node.m83clone();
            m83clone.mAnimation.removeListener(this.mAnimationEndListener);
            hashMap.put(node, m83clone);
            animatorSet.mNodes.add(m83clone);
            animatorSet.mNodeMap.put(m83clone.mAnimation, m83clone);
        }
        animatorSet.mRootNode = (android.animation.AnimatorSet.Node) hashMap.get(this.mRootNode);
        animatorSet.mDelayAnim = (android.animation.ValueAnimator) animatorSet.mRootNode.mAnimation;
        for (int i2 = 0; i2 < size; i2++) {
            android.animation.AnimatorSet.Node node2 = this.mNodes.get(i2);
            android.animation.AnimatorSet.Node node3 = (android.animation.AnimatorSet.Node) hashMap.get(node2);
            node3.mLatestParent = node2.mLatestParent == null ? null : (android.animation.AnimatorSet.Node) hashMap.get(node2.mLatestParent);
            int size2 = node2.mChildNodes == null ? 0 : node2.mChildNodes.size();
            for (int i3 = 0; i3 < size2; i3++) {
                node3.mChildNodes.set(i3, (android.animation.AnimatorSet.Node) hashMap.get(node2.mChildNodes.get(i3)));
            }
            int size3 = node2.mSiblings == null ? 0 : node2.mSiblings.size();
            for (int i4 = 0; i4 < size3; i4++) {
                node3.mSiblings.set(i4, (android.animation.AnimatorSet.Node) hashMap.get(node2.mSiblings.get(i4)));
            }
            int size4 = node2.mParents == null ? 0 : node2.mParents.size();
            for (int i5 = 0; i5 < size4; i5++) {
                node3.mParents.set(i5, (android.animation.AnimatorSet.Node) hashMap.get(node2.mParents.get(i5)));
            }
        }
        return animatorSet;
    }

    @Override // android.animation.Animator
    public boolean canReverse() {
        return getTotalDuration() != -1;
    }

    @Override // android.animation.Animator
    public void reverse() {
        start(true, true);
    }

    public java.lang.String toString() {
        java.lang.String str = "AnimatorSet@" + java.lang.Integer.toHexString(hashCode()) + "{";
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            str = str + "\n    " + this.mNodes.get(i).mAnimation.toString();
        }
        return str + "\n}";
    }

    private void printChildCount() {
        int i;
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mNodes.size());
        arrayList.add(this.mRootNode);
        android.util.Log.d(TAG, "Current tree: ");
        int i2 = 0;
        while (i2 < arrayList.size()) {
            int size = arrayList.size();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            while (i2 < size) {
                android.animation.AnimatorSet.Node node = (android.animation.AnimatorSet.Node) arrayList.get(i2);
                if (node.mChildNodes == null) {
                    i = 0;
                } else {
                    i = 0;
                    for (int i3 = 0; i3 < node.mChildNodes.size(); i3++) {
                        android.animation.AnimatorSet.Node node2 = node.mChildNodes.get(i3);
                        if (node2.mLatestParent == node) {
                            i++;
                            arrayList.add(node2);
                        }
                    }
                }
                sb.append(" ");
                sb.append(i);
                i2++;
            }
            android.util.Log.d(TAG, sb.toString());
        }
    }

    private void createDependencyGraph() {
        boolean z;
        if (!this.mDependencyDirty) {
            int i = 0;
            while (true) {
                if (i >= this.mNodes.size()) {
                    z = false;
                    break;
                }
                if (this.mNodes.get(i).mTotalDuration == this.mNodes.get(i).mAnimation.getTotalDuration()) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return;
            }
        }
        this.mDependencyDirty = false;
        int size = this.mNodes.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mNodes.get(i2).mParentsAdded = false;
        }
        for (int i3 = 0; i3 < size; i3++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i3);
            if (!node.mParentsAdded) {
                node.mParentsAdded = true;
                if (node.mSiblings != null) {
                    findSiblings(node, node.mSiblings);
                    node.mSiblings.remove(node);
                    int size2 = node.mSiblings.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        node.addParents(node.mSiblings.get(i4).mParents);
                    }
                    for (int i5 = 0; i5 < size2; i5++) {
                        android.animation.AnimatorSet.Node node2 = node.mSiblings.get(i5);
                        node2.addParents(node.mParents);
                        node2.mParentsAdded = true;
                    }
                }
            }
        }
        for (int i6 = 0; i6 < size; i6++) {
            android.animation.AnimatorSet.Node node3 = this.mNodes.get(i6);
            if (node3 != this.mRootNode && node3.mParents == null) {
                node3.addParent(this.mRootNode);
            }
        }
        java.util.ArrayList<android.animation.AnimatorSet.Node> arrayList = new java.util.ArrayList<>(this.mNodes.size());
        this.mRootNode.mStartTime = 0L;
        this.mRootNode.mEndTime = this.mDelayAnim.getDuration();
        updatePlayTime(this.mRootNode, arrayList);
        sortAnimationEvents();
        this.mTotalDuration = this.mEvents.get(this.mEvents.size() - 1).getTime();
    }

    private void sortAnimationEvents() {
        boolean z;
        this.mEvents.clear();
        for (int i = 1; i < this.mNodes.size(); i++) {
            android.animation.AnimatorSet.Node node = this.mNodes.get(i);
            this.mEvents.add(new android.animation.AnimatorSet.AnimationEvent(node, 0));
            this.mEvents.add(new android.animation.AnimatorSet.AnimationEvent(node, 1));
            this.mEvents.add(new android.animation.AnimatorSet.AnimationEvent(node, 2));
        }
        this.mEvents.sort(new java.util.Comparator<android.animation.AnimatorSet.AnimationEvent>() { // from class: android.animation.AnimatorSet.3
            @Override // java.util.Comparator
            public int compare(android.animation.AnimatorSet.AnimationEvent animationEvent, android.animation.AnimatorSet.AnimationEvent animationEvent2) {
                long time = animationEvent.getTime();
                long time2 = animationEvent2.getTime();
                if (time == time2) {
                    if (animationEvent2.mEvent + animationEvent.mEvent == 1) {
                        return animationEvent.mEvent - animationEvent2.mEvent;
                    }
                    return animationEvent2.mEvent - animationEvent.mEvent;
                }
                if (time2 == -1) {
                    return -1;
                }
                return (time != -1 && time - time2 <= 0) ? -1 : 1;
            }
        });
        int size = this.mEvents.size();
        int i2 = 0;
        while (i2 < size) {
            android.animation.AnimatorSet.AnimationEvent animationEvent = this.mEvents.get(i2);
            if (animationEvent.mEvent == 2) {
                if (animationEvent.mNode.mStartTime == animationEvent.mNode.mEndTime) {
                    z = true;
                } else if (animationEvent.mNode.mEndTime == animationEvent.mNode.mStartTime + animationEvent.mNode.mAnimation.getStartDelay()) {
                    z = false;
                } else {
                    i2++;
                }
                int i3 = i2 + 1;
                int i4 = size;
                int i5 = i4;
                for (int i6 = i3; i6 < size && (i4 >= size || i5 >= size); i6++) {
                    if (this.mEvents.get(i6).mNode == animationEvent.mNode) {
                        if (this.mEvents.get(i6).mEvent != 0) {
                            if (this.mEvents.get(i6).mEvent == 1) {
                                i5 = i6;
                            }
                        } else {
                            i4 = i6;
                        }
                    }
                }
                if (z && i4 == this.mEvents.size()) {
                    throw new java.lang.UnsupportedOperationException("Something went wrong, no start isfound after stop for an animation that has the same start and endtime.");
                }
                if (i5 == this.mEvents.size()) {
                    throw new java.lang.UnsupportedOperationException("Something went wrong, no startdelay end is found after stop for an animation");
                }
                if (z) {
                    this.mEvents.add(i2, this.mEvents.remove(i4));
                    i2 = i3;
                }
                this.mEvents.add(i2, this.mEvents.remove(i5));
                i2 += 2;
            } else {
                i2++;
            }
        }
        if (!this.mEvents.isEmpty() && this.mEvents.get(0).mEvent != 0) {
            throw new java.lang.UnsupportedOperationException("Sorting went bad, the start event should always be at index 0");
        }
        this.mEvents.add(0, new android.animation.AnimatorSet.AnimationEvent(this.mRootNode, 0));
        this.mEvents.add(1, new android.animation.AnimatorSet.AnimationEvent(this.mRootNode, 1));
        this.mEvents.add(2, new android.animation.AnimatorSet.AnimationEvent(this.mRootNode, 2));
        if (this.mEvents.get(this.mEvents.size() - 1).mEvent == 0 || this.mEvents.get(this.mEvents.size() - 1).mEvent == 1) {
            throw new java.lang.UnsupportedOperationException("Something went wrong, the last event is not an end event");
        }
    }

    private void updatePlayTime(android.animation.AnimatorSet.Node node, java.util.ArrayList<android.animation.AnimatorSet.Node> arrayList) {
        int i = 0;
        if (node.mChildNodes == null) {
            if (node == this.mRootNode) {
                while (i < this.mNodes.size()) {
                    android.animation.AnimatorSet.Node node2 = this.mNodes.get(i);
                    if (node2 != this.mRootNode) {
                        node2.mStartTime = -1L;
                        node2.mEndTime = -1L;
                    }
                    i++;
                }
                return;
            }
            return;
        }
        arrayList.add(node);
        int size = node.mChildNodes.size();
        while (i < size) {
            android.animation.AnimatorSet.Node node3 = node.mChildNodes.get(i);
            node3.mTotalDuration = node3.mAnimation.getTotalDuration();
            int indexOf = arrayList.indexOf(node3);
            if (indexOf >= 0) {
                while (indexOf < arrayList.size()) {
                    arrayList.get(indexOf).mLatestParent = null;
                    arrayList.get(indexOf).mStartTime = -1L;
                    arrayList.get(indexOf).mEndTime = -1L;
                    indexOf++;
                }
                node3.mStartTime = -1L;
                node3.mEndTime = -1L;
                node3.mLatestParent = null;
                android.util.Log.w(TAG, "Cycle found in AnimatorSet: " + this);
            } else {
                if (node3.mStartTime != -1) {
                    if (node.mEndTime == -1) {
                        node3.mLatestParent = node;
                        node3.mStartTime = -1L;
                        node3.mEndTime = -1L;
                    } else {
                        if (node.mEndTime >= node3.mStartTime) {
                            node3.mLatestParent = node;
                            node3.mStartTime = node.mEndTime;
                        }
                        node3.mEndTime = node3.mTotalDuration == -1 ? -1L : node3.mStartTime + node3.mTotalDuration;
                    }
                }
                updatePlayTime(node3, arrayList);
            }
            i++;
        }
        arrayList.remove(node);
    }

    private void findSiblings(android.animation.AnimatorSet.Node node, java.util.ArrayList<android.animation.AnimatorSet.Node> arrayList) {
        if (!arrayList.contains(node)) {
            arrayList.add(node);
            if (node.mSiblings == null) {
                return;
            }
            for (int i = 0; i < node.mSiblings.size(); i++) {
                findSiblings(node.mSiblings.get(i), arrayList);
            }
        }
    }

    public boolean shouldPlayTogether() {
        updateAnimatorsDuration();
        createDependencyGraph();
        return this.mRootNode.mChildNodes == null || this.mRootNode.mChildNodes.size() == this.mNodes.size() - 1;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        updateAnimatorsDuration();
        createDependencyGraph();
        return this.mTotalDuration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.animation.AnimatorSet.Node getNodeForAnimation(android.animation.Animator animator) {
        android.animation.AnimatorSet.Node node = this.mNodeMap.get(animator);
        if (node == null) {
            android.animation.AnimatorSet.Node node2 = new android.animation.AnimatorSet.Node(animator);
            this.mNodeMap.put(animator, node2);
            this.mNodes.add(node2);
            return node2;
        }
        return node;
    }

    private static class Node implements java.lang.Cloneable {
        android.animation.Animator mAnimation;
        java.util.ArrayList<android.animation.AnimatorSet.Node> mParents;
        java.util.ArrayList<android.animation.AnimatorSet.Node> mSiblings;
        java.util.ArrayList<android.animation.AnimatorSet.Node> mChildNodes = null;
        boolean mEnded = false;
        android.animation.AnimatorSet.Node mLatestParent = null;
        boolean mParentsAdded = false;
        long mStartTime = 0;
        long mEndTime = 0;
        long mTotalDuration = 0;

        public Node(android.animation.Animator animator) {
            this.mAnimation = animator;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.animation.AnimatorSet.Node m83clone() {
            try {
                android.animation.AnimatorSet.Node node = (android.animation.AnimatorSet.Node) super.clone();
                node.mAnimation = this.mAnimation.mo77clone();
                if (this.mChildNodes != null) {
                    node.mChildNodes = new java.util.ArrayList<>(this.mChildNodes);
                }
                if (this.mSiblings != null) {
                    node.mSiblings = new java.util.ArrayList<>(this.mSiblings);
                }
                if (this.mParents != null) {
                    node.mParents = new java.util.ArrayList<>(this.mParents);
                }
                node.mEnded = false;
                return node;
            } catch (java.lang.CloneNotSupportedException e) {
                throw new java.lang.AssertionError();
            }
        }

        void addChild(android.animation.AnimatorSet.Node node) {
            if (this.mChildNodes == null) {
                this.mChildNodes = new java.util.ArrayList<>();
            }
            if (!this.mChildNodes.contains(node)) {
                this.mChildNodes.add(node);
                node.addParent(this);
            }
        }

        public void addSibling(android.animation.AnimatorSet.Node node) {
            if (this.mSiblings == null) {
                this.mSiblings = new java.util.ArrayList<>();
            }
            if (!this.mSiblings.contains(node)) {
                this.mSiblings.add(node);
                node.addSibling(this);
            }
        }

        public void addParent(android.animation.AnimatorSet.Node node) {
            if (this.mParents == null) {
                this.mParents = new java.util.ArrayList<>();
            }
            if (!this.mParents.contains(node)) {
                this.mParents.add(node);
                node.addChild(this);
            }
        }

        public void addParents(java.util.ArrayList<android.animation.AnimatorSet.Node> arrayList) {
            if (arrayList == null) {
                return;
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                addParent(arrayList.get(i));
            }
        }
    }

    private static class AnimationEvent {
        static final int ANIMATION_DELAY_ENDED = 1;
        static final int ANIMATION_END = 2;
        static final int ANIMATION_START = 0;
        final int mEvent;
        final android.animation.AnimatorSet.Node mNode;

        AnimationEvent(android.animation.AnimatorSet.Node node, int i) {
            this.mNode = node;
            this.mEvent = i;
        }

        long getTime() {
            if (this.mEvent == 0) {
                return this.mNode.mStartTime;
            }
            if (this.mEvent != 1) {
                return this.mNode.mEndTime;
            }
            if (this.mNode.mStartTime == -1) {
                return -1L;
            }
            return this.mNode.mAnimation.getStartDelay() + this.mNode.mStartTime;
        }

        public java.lang.String toString() {
            java.lang.String str;
            if (this.mEvent == 0) {
                str = "start";
            } else {
                str = this.mEvent == 1 ? "delay ended" : "end";
            }
            return str + " " + this.mNode.mAnimation.toString();
        }
    }

    private class SeekState {
        private long mPlayTime;
        private boolean mSeekingInReverse;

        private SeekState() {
            this.mPlayTime = -1L;
            this.mSeekingInReverse = false;
        }

        void reset() {
            this.mPlayTime = -1L;
            this.mSeekingInReverse = false;
        }

        void setPlayTime(long j, boolean z) {
            if (android.animation.AnimatorSet.this.getTotalDuration() != -1) {
                this.mPlayTime = java.lang.Math.min(j, android.animation.AnimatorSet.this.getTotalDuration() - android.animation.AnimatorSet.this.mStartDelay);
            } else {
                this.mPlayTime = j;
            }
            this.mPlayTime = java.lang.Math.max(0L, this.mPlayTime);
            this.mSeekingInReverse = z;
        }

        void updateSeekDirection(boolean z) {
            if (z && android.animation.AnimatorSet.this.getTotalDuration() == -1) {
                throw new java.lang.UnsupportedOperationException("Error: Cannot reverse infinite animator set");
            }
            if (this.mPlayTime >= 0 && z != this.mSeekingInReverse) {
                this.mPlayTime = (android.animation.AnimatorSet.this.getTotalDuration() - android.animation.AnimatorSet.this.mStartDelay) - this.mPlayTime;
                this.mSeekingInReverse = z;
            }
        }

        long getPlayTime() {
            return this.mPlayTime;
        }

        long getPlayTimeNormalized() {
            if (android.animation.AnimatorSet.this.mReversing) {
                return (android.animation.AnimatorSet.this.getTotalDuration() - android.animation.AnimatorSet.this.mStartDelay) - this.mPlayTime;
            }
            return this.mPlayTime;
        }

        boolean isActive() {
            return this.mPlayTime != -1;
        }
    }

    public class Builder {
        private android.animation.AnimatorSet.Node mCurrentNode;

        Builder(android.animation.Animator animator) {
            android.animation.AnimatorSet.this.mDependencyDirty = true;
            this.mCurrentNode = android.animation.AnimatorSet.this.getNodeForAnimation(animator);
        }

        public android.animation.AnimatorSet.Builder with(android.animation.Animator animator) {
            this.mCurrentNode.addSibling(android.animation.AnimatorSet.this.getNodeForAnimation(animator));
            return this;
        }

        public android.animation.AnimatorSet.Builder before(android.animation.Animator animator) {
            this.mCurrentNode.addChild(android.animation.AnimatorSet.this.getNodeForAnimation(animator));
            return this;
        }

        public android.animation.AnimatorSet.Builder after(android.animation.Animator animator) {
            this.mCurrentNode.addParent(android.animation.AnimatorSet.this.getNodeForAnimation(animator));
            return this;
        }

        public android.animation.AnimatorSet.Builder after(long j) {
            android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(j);
            after(ofFloat);
            return this;
        }
    }
}
