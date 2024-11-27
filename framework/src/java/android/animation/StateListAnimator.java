package android.animation;

/* loaded from: classes.dex */
public class StateListAnimator implements java.lang.Cloneable {
    private android.animation.AnimatorListenerAdapter mAnimatorListener;
    private int mChangingConfigurations;
    private android.animation.StateListAnimator.StateListAnimatorConstantState mConstantState;
    private java.lang.ref.WeakReference<android.view.View> mViewRef;
    private java.util.ArrayList<android.animation.StateListAnimator.Tuple> mTuples = new java.util.ArrayList<>();
    private android.animation.StateListAnimator.Tuple mLastMatch = null;
    private android.animation.Animator mRunningAnimator = null;

    public StateListAnimator() {
        initAnimatorListener();
    }

    private void initAnimatorListener() {
        this.mAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: android.animation.StateListAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                animator.setTarget(null);
                if (android.animation.StateListAnimator.this.mRunningAnimator == animator) {
                    android.animation.StateListAnimator.this.mRunningAnimator = null;
                }
            }
        };
    }

    public void addState(int[] iArr, android.animation.Animator animator) {
        android.animation.StateListAnimator.Tuple tuple = new android.animation.StateListAnimator.Tuple(iArr, animator);
        tuple.mAnimator.addListener(this.mAnimatorListener);
        this.mTuples.add(tuple);
        this.mChangingConfigurations |= animator.getChangingConfigurations();
    }

    public android.animation.Animator getRunningAnimator() {
        return this.mRunningAnimator;
    }

    public android.view.View getTarget() {
        if (this.mViewRef == null) {
            return null;
        }
        return this.mViewRef.get();
    }

    public void setTarget(android.view.View view) {
        android.view.View target = getTarget();
        if (target == view) {
            return;
        }
        if (target != null) {
            clearTarget();
        }
        if (view != null) {
            this.mViewRef = new java.lang.ref.WeakReference<>(view);
        }
    }

    private void clearTarget() {
        int size = this.mTuples.size();
        for (int i = 0; i < size; i++) {
            this.mTuples.get(i).mAnimator.setTarget(null);
        }
        this.mViewRef = null;
        this.mLastMatch = null;
        this.mRunningAnimator = null;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.animation.StateListAnimator m126clone() {
        try {
            android.animation.StateListAnimator stateListAnimator = (android.animation.StateListAnimator) super.clone();
            stateListAnimator.mTuples = new java.util.ArrayList<>(this.mTuples.size());
            stateListAnimator.mLastMatch = null;
            stateListAnimator.mRunningAnimator = null;
            stateListAnimator.mViewRef = null;
            stateListAnimator.mAnimatorListener = null;
            stateListAnimator.initAnimatorListener();
            int size = this.mTuples.size();
            for (int i = 0; i < size; i++) {
                android.animation.StateListAnimator.Tuple tuple = this.mTuples.get(i);
                android.animation.Animator mo77clone = tuple.mAnimator.mo77clone();
                mo77clone.removeListener(this.mAnimatorListener);
                stateListAnimator.addState(tuple.mSpecs, mo77clone);
            }
            stateListAnimator.setChangingConfigurations(getChangingConfigurations());
            return stateListAnimator;
        } catch (java.lang.CloneNotSupportedException e) {
            throw new java.lang.AssertionError("cannot clone state list animator", e);
        }
    }

    public void setState(int[] iArr) {
        android.animation.StateListAnimator.Tuple tuple;
        int size = this.mTuples.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                tuple = null;
                break;
            }
            tuple = this.mTuples.get(i);
            if (android.util.StateSet.stateSetMatches(tuple.mSpecs, iArr)) {
                break;
            } else {
                i++;
            }
        }
        if (tuple == this.mLastMatch) {
            return;
        }
        if (this.mLastMatch != null) {
            cancel();
        }
        this.mLastMatch = tuple;
        if (tuple != null) {
            start(tuple);
        }
    }

    private void start(android.animation.StateListAnimator.Tuple tuple) {
        tuple.mAnimator.setTarget(getTarget());
        this.mRunningAnimator = tuple.mAnimator;
        this.mRunningAnimator.start();
    }

    private void cancel() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.cancel();
            this.mRunningAnimator = null;
        }
    }

    public java.util.ArrayList<android.animation.StateListAnimator.Tuple> getTuples() {
        return this.mTuples;
    }

    public void jumpToCurrentState() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.end();
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

    public android.content.res.ConstantState<android.animation.StateListAnimator> createConstantState() {
        return new android.animation.StateListAnimator.StateListAnimatorConstantState(this);
    }

    public static class Tuple {
        final android.animation.Animator mAnimator;
        final int[] mSpecs;

        private Tuple(int[] iArr, android.animation.Animator animator) {
            this.mSpecs = iArr;
            this.mAnimator = animator;
        }

        public int[] getSpecs() {
            return this.mSpecs;
        }

        public android.animation.Animator getAnimator() {
            return this.mAnimator;
        }
    }

    private static class StateListAnimatorConstantState extends android.content.res.ConstantState<android.animation.StateListAnimator> {
        final android.animation.StateListAnimator mAnimator;
        int mChangingConf;

        public StateListAnimatorConstantState(android.animation.StateListAnimator stateListAnimator) {
            this.mAnimator = stateListAnimator;
            this.mAnimator.mConstantState = this;
            this.mChangingConf = this.mAnimator.getChangingConfigurations();
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public android.animation.StateListAnimator newInstance2() {
            android.animation.StateListAnimator m126clone = this.mAnimator.m126clone();
            m126clone.mConstantState = this;
            return m126clone;
        }
    }
}
