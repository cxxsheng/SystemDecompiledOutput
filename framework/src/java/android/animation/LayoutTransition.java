package android.animation;

/* loaded from: classes.dex */
public class LayoutTransition {
    public static final int APPEARING = 2;
    public static final int CHANGE_APPEARING = 0;
    public static final int CHANGE_DISAPPEARING = 1;
    public static final int CHANGING = 4;
    public static final int DISAPPEARING = 3;
    private static final int FLAG_APPEARING = 1;
    private static final int FLAG_CHANGE_APPEARING = 4;
    private static final int FLAG_CHANGE_DISAPPEARING = 8;
    private static final int FLAG_CHANGING = 16;
    private static final int FLAG_DISAPPEARING = 2;
    private static android.animation.ObjectAnimator defaultChange;
    private static android.animation.ObjectAnimator defaultChangeIn;
    private static android.animation.ObjectAnimator defaultChangeOut;
    private static android.animation.ObjectAnimator defaultFadeIn;
    private static android.animation.ObjectAnimator defaultFadeOut;
    private android.animation.Animator mAppearingAnim;
    private android.animation.Animator mChangingAnim;
    private android.animation.Animator mChangingAppearingAnim;
    private android.animation.Animator mChangingDisappearingAnim;
    private android.animation.Animator mDisappearingAnim;
    private java.util.ArrayList<android.animation.LayoutTransition.TransitionListener> mListeners;
    private long staggerDelay;
    private static long DEFAULT_DURATION = 300;
    private static android.animation.TimeInterpolator ACCEL_DECEL_INTERPOLATOR = new android.view.animation.AccelerateDecelerateInterpolator();
    private static android.animation.TimeInterpolator DECEL_INTERPOLATOR = new android.view.animation.DecelerateInterpolator();
    private static android.animation.TimeInterpolator sAppearingInterpolator = ACCEL_DECEL_INTERPOLATOR;
    private static android.animation.TimeInterpolator sDisappearingInterpolator = ACCEL_DECEL_INTERPOLATOR;
    private static android.animation.TimeInterpolator sChangingAppearingInterpolator = DECEL_INTERPOLATOR;
    private static android.animation.TimeInterpolator sChangingDisappearingInterpolator = DECEL_INTERPOLATOR;
    private static android.animation.TimeInterpolator sChangingInterpolator = DECEL_INTERPOLATOR;
    private long mChangingAppearingDuration = DEFAULT_DURATION;
    private long mChangingDisappearingDuration = DEFAULT_DURATION;
    private long mChangingDuration = DEFAULT_DURATION;
    private long mAppearingDuration = DEFAULT_DURATION;
    private long mDisappearingDuration = DEFAULT_DURATION;
    private long mAppearingDelay = DEFAULT_DURATION;
    private long mDisappearingDelay = 0;
    private long mChangingAppearingDelay = 0;
    private long mChangingDisappearingDelay = DEFAULT_DURATION;
    private long mChangingDelay = 0;
    private long mChangingAppearingStagger = 0;
    private long mChangingDisappearingStagger = 0;
    private long mChangingStagger = 0;
    private android.animation.TimeInterpolator mAppearingInterpolator = sAppearingInterpolator;
    private android.animation.TimeInterpolator mDisappearingInterpolator = sDisappearingInterpolator;
    private android.animation.TimeInterpolator mChangingAppearingInterpolator = sChangingAppearingInterpolator;
    private android.animation.TimeInterpolator mChangingDisappearingInterpolator = sChangingDisappearingInterpolator;
    private android.animation.TimeInterpolator mChangingInterpolator = sChangingInterpolator;
    private final java.util.HashMap<android.view.View, android.animation.Animator> pendingAnimations = new java.util.HashMap<>();
    private final java.util.LinkedHashMap<android.view.View, android.animation.Animator> currentChangingAnimations = new java.util.LinkedHashMap<>();
    private final java.util.LinkedHashMap<android.view.View, android.animation.Animator> currentAppearingAnimations = new java.util.LinkedHashMap<>();
    private final java.util.LinkedHashMap<android.view.View, android.animation.Animator> currentDisappearingAnimations = new java.util.LinkedHashMap<>();
    private final java.util.HashMap<android.view.View, android.view.View.OnLayoutChangeListener> layoutChangeListenerMap = new java.util.HashMap<>();
    private int mTransitionTypes = 15;
    private boolean mAnimateParentHierarchy = true;

    public interface TransitionListener {
        void endTransition(android.animation.LayoutTransition layoutTransition, android.view.ViewGroup viewGroup, android.view.View view, int i);

        void startTransition(android.animation.LayoutTransition layoutTransition, android.view.ViewGroup viewGroup, android.view.View view, int i);
    }

    public LayoutTransition() {
        this.mDisappearingAnim = null;
        this.mAppearingAnim = null;
        this.mChangingAppearingAnim = null;
        this.mChangingDisappearingAnim = null;
        this.mChangingAnim = null;
        if (defaultChangeIn == null) {
            defaultChangeIn = android.animation.ObjectAnimator.ofPropertyValuesHolder(null, android.animation.PropertyValuesHolder.ofInt(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT, 0, 1), android.animation.PropertyValuesHolder.ofInt("top", 0, 1), android.animation.PropertyValuesHolder.ofInt(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT, 0, 1), android.animation.PropertyValuesHolder.ofInt("bottom", 0, 1), android.animation.PropertyValuesHolder.ofInt("scrollX", 0, 1), android.animation.PropertyValuesHolder.ofInt("scrollY", 0, 1));
            defaultChangeIn.setDuration(DEFAULT_DURATION);
            defaultChangeIn.setStartDelay(this.mChangingAppearingDelay);
            defaultChangeIn.setInterpolator(this.mChangingAppearingInterpolator);
            defaultChangeOut = defaultChangeIn.mo77clone();
            defaultChangeOut.setStartDelay(this.mChangingDisappearingDelay);
            defaultChangeOut.setInterpolator(this.mChangingDisappearingInterpolator);
            defaultChange = defaultChangeIn.mo77clone();
            defaultChange.setStartDelay(this.mChangingDelay);
            defaultChange.setInterpolator(this.mChangingInterpolator);
            defaultFadeIn = android.animation.ObjectAnimator.ofFloat((java.lang.Object) null, "alpha", 0.0f, 1.0f);
            defaultFadeIn.setDuration(DEFAULT_DURATION);
            defaultFadeIn.setStartDelay(this.mAppearingDelay);
            defaultFadeIn.setInterpolator(this.mAppearingInterpolator);
            defaultFadeOut = android.animation.ObjectAnimator.ofFloat((java.lang.Object) null, "alpha", 1.0f, 0.0f);
            defaultFadeOut.setDuration(DEFAULT_DURATION);
            defaultFadeOut.setStartDelay(this.mDisappearingDelay);
            defaultFadeOut.setInterpolator(this.mDisappearingInterpolator);
        }
        this.mChangingAppearingAnim = defaultChangeIn;
        this.mChangingDisappearingAnim = defaultChangeOut;
        this.mChangingAnim = defaultChange;
        this.mAppearingAnim = defaultFadeIn;
        this.mDisappearingAnim = defaultFadeOut;
    }

    public void setDuration(long j) {
        this.mChangingAppearingDuration = j;
        this.mChangingDisappearingDuration = j;
        this.mChangingDuration = j;
        this.mAppearingDuration = j;
        this.mDisappearingDuration = j;
    }

    public void enableTransitionType(int i) {
        switch (i) {
            case 0:
                this.mTransitionTypes |= 4;
                break;
            case 1:
                this.mTransitionTypes |= 8;
                break;
            case 2:
                this.mTransitionTypes |= 1;
                break;
            case 3:
                this.mTransitionTypes |= 2;
                break;
            case 4:
                this.mTransitionTypes |= 16;
                break;
        }
    }

    public void disableTransitionType(int i) {
        switch (i) {
            case 0:
                this.mTransitionTypes &= -5;
                break;
            case 1:
                this.mTransitionTypes &= -9;
                break;
            case 2:
                this.mTransitionTypes &= -2;
                break;
            case 3:
                this.mTransitionTypes &= -3;
                break;
            case 4:
                this.mTransitionTypes &= -17;
                break;
        }
    }

    public boolean isTransitionTypeEnabled(int i) {
        switch (i) {
            case 0:
                return (this.mTransitionTypes & 4) == 4;
            case 1:
                return (this.mTransitionTypes & 8) == 8;
            case 2:
                return (this.mTransitionTypes & 1) == 1;
            case 3:
                return (this.mTransitionTypes & 2) == 2;
            case 4:
                return (this.mTransitionTypes & 16) == 16;
            default:
                return false;
        }
    }

    public void setStartDelay(int i, long j) {
        switch (i) {
            case 0:
                this.mChangingAppearingDelay = j;
                break;
            case 1:
                this.mChangingDisappearingDelay = j;
                break;
            case 2:
                this.mAppearingDelay = j;
                break;
            case 3:
                this.mDisappearingDelay = j;
                break;
            case 4:
                this.mChangingDelay = j;
                break;
        }
    }

    public long getStartDelay(int i) {
        switch (i) {
            case 0:
                return this.mChangingAppearingDelay;
            case 1:
                return this.mChangingDisappearingDelay;
            case 2:
                return this.mAppearingDelay;
            case 3:
                return this.mDisappearingDelay;
            case 4:
                return this.mChangingDelay;
            default:
                return 0L;
        }
    }

    public void setDuration(int i, long j) {
        switch (i) {
            case 0:
                this.mChangingAppearingDuration = j;
                break;
            case 1:
                this.mChangingDisappearingDuration = j;
                break;
            case 2:
                this.mAppearingDuration = j;
                break;
            case 3:
                this.mDisappearingDuration = j;
                break;
            case 4:
                this.mChangingDuration = j;
                break;
        }
    }

    public long getDuration(int i) {
        switch (i) {
            case 0:
                return this.mChangingAppearingDuration;
            case 1:
                return this.mChangingDisappearingDuration;
            case 2:
                return this.mAppearingDuration;
            case 3:
                return this.mDisappearingDuration;
            case 4:
                return this.mChangingDuration;
            default:
                return 0L;
        }
    }

    public void setStagger(int i, long j) {
        switch (i) {
            case 0:
                this.mChangingAppearingStagger = j;
                break;
            case 1:
                this.mChangingDisappearingStagger = j;
                break;
            case 4:
                this.mChangingStagger = j;
                break;
        }
    }

    public long getStagger(int i) {
        switch (i) {
            case 0:
                return this.mChangingAppearingStagger;
            case 1:
                return this.mChangingDisappearingStagger;
            case 2:
            case 3:
            default:
                return 0L;
            case 4:
                return this.mChangingStagger;
        }
    }

    public void setInterpolator(int i, android.animation.TimeInterpolator timeInterpolator) {
        switch (i) {
            case 0:
                this.mChangingAppearingInterpolator = timeInterpolator;
                break;
            case 1:
                this.mChangingDisappearingInterpolator = timeInterpolator;
                break;
            case 2:
                this.mAppearingInterpolator = timeInterpolator;
                break;
            case 3:
                this.mDisappearingInterpolator = timeInterpolator;
                break;
            case 4:
                this.mChangingInterpolator = timeInterpolator;
                break;
        }
    }

    public android.animation.TimeInterpolator getInterpolator(int i) {
        switch (i) {
            case 0:
                return this.mChangingAppearingInterpolator;
            case 1:
                return this.mChangingDisappearingInterpolator;
            case 2:
                return this.mAppearingInterpolator;
            case 3:
                return this.mDisappearingInterpolator;
            case 4:
                return this.mChangingInterpolator;
            default:
                return null;
        }
    }

    public void setAnimator(int i, android.animation.Animator animator) {
        switch (i) {
            case 0:
                this.mChangingAppearingAnim = animator;
                break;
            case 1:
                this.mChangingDisappearingAnim = animator;
                break;
            case 2:
                this.mAppearingAnim = animator;
                break;
            case 3:
                this.mDisappearingAnim = animator;
                break;
            case 4:
                this.mChangingAnim = animator;
                break;
        }
    }

    public android.animation.Animator getAnimator(int i) {
        switch (i) {
            case 0:
                return this.mChangingAppearingAnim;
            case 1:
                return this.mChangingDisappearingAnim;
            case 2:
                return this.mAppearingAnim;
            case 3:
                return this.mDisappearingAnim;
            case 4:
                return this.mChangingAnim;
            default:
                return null;
        }
    }

    private void runChangeTransition(android.view.ViewGroup viewGroup, android.view.View view, int i) {
        android.animation.Animator animator;
        long j;
        android.animation.ObjectAnimator objectAnimator;
        int i2;
        switch (i) {
            case 2:
                animator = this.mChangingAppearingAnim;
                j = this.mChangingAppearingDuration;
                objectAnimator = defaultChangeIn;
                break;
            case 3:
                animator = this.mChangingDisappearingAnim;
                j = this.mChangingDisappearingDuration;
                objectAnimator = defaultChangeOut;
                break;
            case 4:
                animator = this.mChangingAnim;
                j = this.mChangingDuration;
                objectAnimator = defaultChange;
                break;
            default:
                j = 0;
                animator = null;
                objectAnimator = null;
                break;
        }
        if (animator == null) {
            return;
        }
        this.staggerDelay = 0L;
        android.view.ViewTreeObserver viewTreeObserver = viewGroup.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        int i3 = 0;
        while (i3 < childCount) {
            android.view.View childAt = viewGroup.getChildAt(i3);
            if (childAt == view) {
                i2 = i3;
            } else {
                i2 = i3;
                setupChangeAnimation(viewGroup, i, animator, j, childAt);
            }
            i3 = i2 + 1;
        }
        if (this.mAnimateParentHierarchy) {
            android.view.ViewGroup viewGroup2 = viewGroup;
            while (viewGroup2 != null) {
                android.view.ViewParent parent = viewGroup2.getParent();
                if (parent instanceof android.view.ViewGroup) {
                    android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) parent;
                    setupChangeAnimation(viewGroup3, i, objectAnimator, j, viewGroup2);
                    viewGroup2 = viewGroup3;
                } else {
                    viewGroup2 = null;
                }
            }
        }
        android.animation.LayoutTransition.CleanupCallback cleanupCallback = new android.animation.LayoutTransition.CleanupCallback(this.layoutChangeListenerMap, viewGroup);
        viewTreeObserver.addOnPreDrawListener(cleanupCallback);
        viewGroup.addOnAttachStateChangeListener(cleanupCallback);
    }

    public void setAnimateParentHierarchy(boolean z) {
        this.mAnimateParentHierarchy = z;
    }

    private void setupChangeAnimation(final android.view.ViewGroup viewGroup, final int i, android.animation.Animator animator, final long j, final android.view.View view) {
        if (this.layoutChangeListenerMap.get(view) != null) {
            return;
        }
        if (view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        final android.animation.Animator mo77clone = animator.mo77clone();
        mo77clone.setTarget(view);
        mo77clone.setupStartValues();
        android.animation.Animator animator2 = this.pendingAnimations.get(view);
        if (animator2 != null) {
            animator2.cancel();
            this.pendingAnimations.remove(view);
        }
        this.pendingAnimations.put(view, mo77clone);
        android.animation.ValueAnimator duration = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(100 + j);
        duration.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator3) {
                android.animation.LayoutTransition.this.pendingAnimations.remove(view);
            }
        });
        duration.start();
        final android.view.View.OnLayoutChangeListener onLayoutChangeListener = new android.view.View.OnLayoutChangeListener() { // from class: android.animation.LayoutTransition.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(android.view.View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                long j2;
                mo77clone.setupEndValues();
                if (mo77clone instanceof android.animation.ValueAnimator) {
                    boolean z = false;
                    for (android.animation.PropertyValuesHolder propertyValuesHolder : ((android.animation.ValueAnimator) mo77clone).getValues()) {
                        if (propertyValuesHolder.mKeyframes instanceof android.animation.KeyframeSet) {
                            android.animation.KeyframeSet keyframeSet = (android.animation.KeyframeSet) propertyValuesHolder.mKeyframes;
                            if (keyframeSet.mFirstKeyframe == null || keyframeSet.mLastKeyframe == null || !keyframeSet.mFirstKeyframe.getValue().equals(keyframeSet.mLastKeyframe.getValue())) {
                                z = true;
                            }
                        } else if (!propertyValuesHolder.mKeyframes.getValue(0.0f).equals(propertyValuesHolder.mKeyframes.getValue(1.0f))) {
                            z = true;
                        }
                    }
                    if (!z) {
                        return;
                    }
                }
                switch (i) {
                    case 2:
                        j2 = android.animation.LayoutTransition.this.mChangingAppearingDelay + android.animation.LayoutTransition.this.staggerDelay;
                        android.animation.LayoutTransition.this.staggerDelay += android.animation.LayoutTransition.this.mChangingAppearingStagger;
                        if (android.animation.LayoutTransition.this.mChangingAppearingInterpolator != android.animation.LayoutTransition.sChangingAppearingInterpolator) {
                            mo77clone.setInterpolator(android.animation.LayoutTransition.this.mChangingAppearingInterpolator);
                            break;
                        }
                        break;
                    case 3:
                        j2 = android.animation.LayoutTransition.this.mChangingDisappearingDelay + android.animation.LayoutTransition.this.staggerDelay;
                        android.animation.LayoutTransition.this.staggerDelay += android.animation.LayoutTransition.this.mChangingDisappearingStagger;
                        if (android.animation.LayoutTransition.this.mChangingDisappearingInterpolator != android.animation.LayoutTransition.sChangingDisappearingInterpolator) {
                            mo77clone.setInterpolator(android.animation.LayoutTransition.this.mChangingDisappearingInterpolator);
                            break;
                        }
                        break;
                    case 4:
                        j2 = android.animation.LayoutTransition.this.mChangingDelay + android.animation.LayoutTransition.this.staggerDelay;
                        android.animation.LayoutTransition.this.staggerDelay += android.animation.LayoutTransition.this.mChangingStagger;
                        if (android.animation.LayoutTransition.this.mChangingInterpolator != android.animation.LayoutTransition.sChangingInterpolator) {
                            mo77clone.setInterpolator(android.animation.LayoutTransition.this.mChangingInterpolator);
                            break;
                        }
                        break;
                    default:
                        j2 = 0;
                        break;
                }
                mo77clone.setStartDelay(j2);
                mo77clone.setDuration(j);
                android.animation.Animator animator3 = (android.animation.Animator) android.animation.LayoutTransition.this.currentChangingAnimations.get(view);
                if (animator3 != null) {
                    animator3.cancel();
                }
                if (((android.animation.Animator) android.animation.LayoutTransition.this.pendingAnimations.get(view)) != null) {
                    android.animation.LayoutTransition.this.pendingAnimations.remove(view);
                }
                android.animation.LayoutTransition.this.currentChangingAnimations.put(view, mo77clone);
                viewGroup.requestTransitionStart(android.animation.LayoutTransition.this);
                view.removeOnLayoutChangeListener(this);
                android.animation.LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }
        };
        mo77clone.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(android.animation.Animator animator3) {
                int i2;
                if (android.animation.LayoutTransition.this.hasListeners()) {
                    java.util.Iterator it = ((java.util.ArrayList) android.animation.LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        android.animation.LayoutTransition.TransitionListener transitionListener = (android.animation.LayoutTransition.TransitionListener) it.next();
                        android.animation.LayoutTransition layoutTransition = android.animation.LayoutTransition.this;
                        android.view.ViewGroup viewGroup2 = viewGroup;
                        android.view.View view2 = view;
                        if (i == 2) {
                            i2 = 0;
                        } else {
                            i2 = i == 3 ? 1 : 4;
                        }
                        transitionListener.startTransition(layoutTransition, viewGroup2, view2, i2);
                    }
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator3) {
                view.removeOnLayoutChangeListener(onLayoutChangeListener);
                android.animation.LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator3) {
                int i2;
                android.animation.LayoutTransition.this.currentChangingAnimations.remove(view);
                if (android.animation.LayoutTransition.this.hasListeners()) {
                    java.util.Iterator it = ((java.util.ArrayList) android.animation.LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        android.animation.LayoutTransition.TransitionListener transitionListener = (android.animation.LayoutTransition.TransitionListener) it.next();
                        android.animation.LayoutTransition layoutTransition = android.animation.LayoutTransition.this;
                        android.view.ViewGroup viewGroup2 = viewGroup;
                        android.view.View view2 = view;
                        if (i == 2) {
                            i2 = 0;
                        } else {
                            i2 = i == 3 ? 1 : 4;
                        }
                        transitionListener.endTransition(layoutTransition, viewGroup2, view2, i2);
                    }
                }
            }
        });
        view.addOnLayoutChangeListener(onLayoutChangeListener);
        this.layoutChangeListenerMap.put(view, onLayoutChangeListener);
    }

    public void startChangingAnimations() {
        for (android.animation.Animator animator : ((java.util.LinkedHashMap) this.currentChangingAnimations.clone()).values()) {
            if (animator instanceof android.animation.ObjectAnimator) {
                ((android.animation.ObjectAnimator) animator).setCurrentPlayTime(0L);
            }
            animator.start();
        }
    }

    public void endChangingAnimations() {
        for (android.animation.Animator animator : ((java.util.LinkedHashMap) this.currentChangingAnimations.clone()).values()) {
            animator.start();
            animator.end();
        }
        this.currentChangingAnimations.clear();
    }

    public boolean isChangingLayout() {
        return this.currentChangingAnimations.size() > 0;
    }

    public boolean isRunning() {
        return this.currentChangingAnimations.size() > 0 || this.currentAppearingAnimations.size() > 0 || this.currentDisappearingAnimations.size() > 0;
    }

    public void cancel() {
        if (this.currentChangingAnimations.size() > 0) {
            java.util.Iterator it = ((java.util.LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
            while (it.hasNext()) {
                ((android.animation.Animator) it.next()).cancel();
            }
            this.currentChangingAnimations.clear();
        }
        if (this.currentAppearingAnimations.size() > 0) {
            java.util.Iterator it2 = ((java.util.LinkedHashMap) this.currentAppearingAnimations.clone()).values().iterator();
            while (it2.hasNext()) {
                ((android.animation.Animator) it2.next()).end();
            }
            this.currentAppearingAnimations.clear();
        }
        if (this.currentDisappearingAnimations.size() > 0) {
            java.util.Iterator it3 = ((java.util.LinkedHashMap) this.currentDisappearingAnimations.clone()).values().iterator();
            while (it3.hasNext()) {
                ((android.animation.Animator) it3.next()).end();
            }
            this.currentDisappearingAnimations.clear();
        }
    }

    public void cancel(int i) {
        switch (i) {
            case 0:
            case 1:
            case 4:
                if (this.currentChangingAnimations.size() > 0) {
                    java.util.Iterator it = ((java.util.LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
                    while (it.hasNext()) {
                        ((android.animation.Animator) it.next()).cancel();
                    }
                    this.currentChangingAnimations.clear();
                    break;
                }
                break;
            case 2:
                if (this.currentAppearingAnimations.size() > 0) {
                    java.util.Iterator it2 = ((java.util.LinkedHashMap) this.currentAppearingAnimations.clone()).values().iterator();
                    while (it2.hasNext()) {
                        ((android.animation.Animator) it2.next()).end();
                    }
                    this.currentAppearingAnimations.clear();
                    break;
                }
                break;
            case 3:
                if (this.currentDisappearingAnimations.size() > 0) {
                    java.util.Iterator it3 = ((java.util.LinkedHashMap) this.currentDisappearingAnimations.clone()).values().iterator();
                    while (it3.hasNext()) {
                        ((android.animation.Animator) it3.next()).end();
                    }
                    this.currentDisappearingAnimations.clear();
                    break;
                }
                break;
        }
    }

    private void runAppearingTransition(final android.view.ViewGroup viewGroup, final android.view.View view) {
        android.animation.Animator animator = this.currentDisappearingAnimations.get(view);
        if (animator != null) {
            animator.cancel();
        }
        if (this.mAppearingAnim == null) {
            if (hasListeners()) {
                java.util.Iterator it = ((java.util.ArrayList) this.mListeners.clone()).iterator();
                while (it.hasNext()) {
                    ((android.animation.LayoutTransition.TransitionListener) it.next()).endTransition(this, viewGroup, view, 2);
                }
                return;
            }
            return;
        }
        android.animation.Animator mo77clone = this.mAppearingAnim.mo77clone();
        mo77clone.setTarget(view);
        mo77clone.setStartDelay(this.mAppearingDelay);
        mo77clone.setDuration(this.mAppearingDuration);
        if (this.mAppearingInterpolator != sAppearingInterpolator) {
            mo77clone.setInterpolator(this.mAppearingInterpolator);
        }
        if (mo77clone instanceof android.animation.ObjectAnimator) {
            ((android.animation.ObjectAnimator) mo77clone).setCurrentPlayTime(0L);
        }
        mo77clone.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator2) {
                android.animation.LayoutTransition.this.currentAppearingAnimations.remove(view);
                if (android.animation.LayoutTransition.this.hasListeners()) {
                    java.util.Iterator it2 = ((java.util.ArrayList) android.animation.LayoutTransition.this.mListeners.clone()).iterator();
                    while (it2.hasNext()) {
                        ((android.animation.LayoutTransition.TransitionListener) it2.next()).endTransition(android.animation.LayoutTransition.this, viewGroup, view, 2);
                    }
                }
            }
        });
        this.currentAppearingAnimations.put(view, mo77clone);
        mo77clone.start();
    }

    private void runDisappearingTransition(final android.view.ViewGroup viewGroup, final android.view.View view) {
        android.animation.Animator animator = this.currentAppearingAnimations.get(view);
        if (animator != null) {
            animator.cancel();
        }
        if (this.mDisappearingAnim == null) {
            if (hasListeners()) {
                java.util.Iterator it = ((java.util.ArrayList) this.mListeners.clone()).iterator();
                while (it.hasNext()) {
                    ((android.animation.LayoutTransition.TransitionListener) it.next()).endTransition(this, viewGroup, view, 3);
                }
                return;
            }
            return;
        }
        android.animation.Animator mo77clone = this.mDisappearingAnim.mo77clone();
        mo77clone.setStartDelay(this.mDisappearingDelay);
        mo77clone.setDuration(this.mDisappearingDuration);
        if (this.mDisappearingInterpolator != sDisappearingInterpolator) {
            mo77clone.setInterpolator(this.mDisappearingInterpolator);
        }
        mo77clone.setTarget(view);
        final float alpha = view.getAlpha();
        mo77clone.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator2) {
                android.animation.LayoutTransition.this.currentDisappearingAnimations.remove(view);
                view.setAlpha(alpha);
                if (android.animation.LayoutTransition.this.hasListeners()) {
                    java.util.Iterator it2 = ((java.util.ArrayList) android.animation.LayoutTransition.this.mListeners.clone()).iterator();
                    while (it2.hasNext()) {
                        ((android.animation.LayoutTransition.TransitionListener) it2.next()).endTransition(android.animation.LayoutTransition.this, viewGroup, view, 3);
                    }
                }
            }
        });
        if (mo77clone instanceof android.animation.ObjectAnimator) {
            ((android.animation.ObjectAnimator) mo77clone).setCurrentPlayTime(0L);
        }
        this.currentDisappearingAnimations.put(view, mo77clone);
        mo77clone.start();
    }

    private void addChild(android.view.ViewGroup viewGroup, android.view.View view, boolean z) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 1) == 1) {
            cancel(3);
        }
        if (z && (this.mTransitionTypes & 4) == 4) {
            cancel(0);
            cancel(4);
        }
        if (hasListeners() && (this.mTransitionTypes & 1) == 1) {
            java.util.Iterator it = ((java.util.ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((android.animation.LayoutTransition.TransitionListener) it.next()).startTransition(this, viewGroup, view, 2);
            }
        }
        if (z && (this.mTransitionTypes & 4) == 4) {
            runChangeTransition(viewGroup, view, 2);
        }
        if ((this.mTransitionTypes & 1) == 1) {
            runAppearingTransition(viewGroup, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasListeners() {
        return this.mListeners != null && this.mListeners.size() > 0;
    }

    public void layoutChange(android.view.ViewGroup viewGroup) {
        if (viewGroup.getWindowVisibility() == 0 && (this.mTransitionTypes & 16) == 16 && !isRunning()) {
            runChangeTransition(viewGroup, null, 4);
        }
    }

    public void addChild(android.view.ViewGroup viewGroup, android.view.View view) {
        addChild(viewGroup, view, true);
    }

    @java.lang.Deprecated
    public void showChild(android.view.ViewGroup viewGroup, android.view.View view) {
        addChild(viewGroup, view, true);
    }

    public void showChild(android.view.ViewGroup viewGroup, android.view.View view, int i) {
        addChild(viewGroup, view, i == 8);
    }

    private void removeChild(android.view.ViewGroup viewGroup, android.view.View view, boolean z) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 2) == 2) {
            cancel(2);
        }
        if (z && (this.mTransitionTypes & 8) == 8) {
            cancel(1);
            cancel(4);
        }
        if (hasListeners() && (this.mTransitionTypes & 2) == 2) {
            java.util.Iterator it = ((java.util.ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((android.animation.LayoutTransition.TransitionListener) it.next()).startTransition(this, viewGroup, view, 3);
            }
        }
        if (z && (this.mTransitionTypes & 8) == 8) {
            runChangeTransition(viewGroup, view, 3);
        }
        if ((this.mTransitionTypes & 2) == 2) {
            runDisappearingTransition(viewGroup, view);
        }
    }

    public void removeChild(android.view.ViewGroup viewGroup, android.view.View view) {
        removeChild(viewGroup, view, true);
    }

    @java.lang.Deprecated
    public void hideChild(android.view.ViewGroup viewGroup, android.view.View view) {
        removeChild(viewGroup, view, true);
    }

    public void hideChild(android.view.ViewGroup viewGroup, android.view.View view, int i) {
        removeChild(viewGroup, view, i == 8);
    }

    public void addTransitionListener(android.animation.LayoutTransition.TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new java.util.ArrayList<>();
        }
        this.mListeners.add(transitionListener);
    }

    public void removeTransitionListener(android.animation.LayoutTransition.TransitionListener transitionListener) {
        if (this.mListeners == null) {
            return;
        }
        this.mListeners.remove(transitionListener);
    }

    public java.util.List<android.animation.LayoutTransition.TransitionListener> getTransitionListeners() {
        return this.mListeners;
    }

    private static final class CleanupCallback implements android.view.ViewTreeObserver.OnPreDrawListener, android.view.View.OnAttachStateChangeListener {
        final java.util.Map<android.view.View, android.view.View.OnLayoutChangeListener> layoutChangeListenerMap;
        final android.view.ViewGroup parent;

        CleanupCallback(java.util.Map<android.view.View, android.view.View.OnLayoutChangeListener> map, android.view.ViewGroup viewGroup) {
            this.layoutChangeListenerMap = map;
            this.parent = viewGroup;
        }

        private void cleanup() {
            this.parent.getViewTreeObserver().removeOnPreDrawListener(this);
            this.parent.removeOnAttachStateChangeListener(this);
            if (this.layoutChangeListenerMap.size() > 0) {
                for (android.view.View view : this.layoutChangeListenerMap.keySet()) {
                    view.removeOnLayoutChangeListener(this.layoutChangeListenerMap.get(view));
                }
                this.layoutChangeListenerMap.clear();
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            cleanup();
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            cleanup();
            return true;
        }
    }
}
