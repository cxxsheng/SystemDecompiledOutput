package android.view;

/* loaded from: classes4.dex */
public class ViewPropertyAnimator {
    static final int ALPHA = 2048;
    static final int NONE = 0;
    static final int ROTATION = 32;
    static final int ROTATION_X = 64;
    static final int ROTATION_Y = 128;
    static final int SCALE_X = 8;
    static final int SCALE_Y = 16;
    private static final int TRANSFORM_MASK = 2047;
    static final int TRANSLATION_X = 1;
    static final int TRANSLATION_Y = 2;
    static final int TRANSLATION_Z = 4;
    static final int X = 256;
    static final int Y = 512;
    static final int Z = 1024;
    private java.util.HashMap<android.animation.Animator, java.lang.Runnable> mAnimatorCleanupMap;
    private java.util.HashMap<android.animation.Animator, java.lang.Runnable> mAnimatorOnEndMap;
    private java.util.HashMap<android.animation.Animator, java.lang.Runnable> mAnimatorOnStartMap;
    private java.util.HashMap<android.animation.Animator, java.lang.Runnable> mAnimatorSetupMap;
    private long mDuration;
    private android.animation.TimeInterpolator mInterpolator;
    private java.lang.Runnable mPendingCleanupAction;
    private java.lang.Runnable mPendingOnEndAction;
    private java.lang.Runnable mPendingOnStartAction;
    private java.lang.Runnable mPendingSetupAction;
    private android.animation.ValueAnimator mTempValueAnimator;
    final android.view.View mView;
    private boolean mDurationSet = false;
    private long mStartDelay = 0;
    private boolean mStartDelaySet = false;
    private boolean mInterpolatorSet = false;
    private android.animation.Animator.AnimatorListener mListener = null;
    private android.animation.ValueAnimator.AnimatorUpdateListener mUpdateListener = null;
    private android.view.ViewPropertyAnimator.AnimatorEventListener mAnimatorEventListener = new android.view.ViewPropertyAnimator.AnimatorEventListener();
    java.util.ArrayList<android.view.ViewPropertyAnimator.NameValuesHolder> mPendingAnimations = new java.util.ArrayList<>();
    private java.lang.Runnable mAnimationStarter = new java.lang.Runnable() { // from class: android.view.ViewPropertyAnimator.1
        @Override // java.lang.Runnable
        public void run() {
            android.view.ViewPropertyAnimator.this.startAnimation();
        }
    };
    private java.util.HashMap<android.animation.Animator, android.view.ViewPropertyAnimator.PropertyBundle> mAnimatorMap = new java.util.HashMap<>();

    private static class PropertyBundle {
        java.util.ArrayList<android.view.ViewPropertyAnimator.NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int i, java.util.ArrayList<android.view.ViewPropertyAnimator.NameValuesHolder> arrayList) {
            this.mPropertyMask = i;
            this.mNameValuesHolder = arrayList;
        }

        boolean cancel(int i) {
            if ((this.mPropertyMask & i) != 0 && this.mNameValuesHolder != null) {
                int size = this.mNameValuesHolder.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (this.mNameValuesHolder.get(i2).mNameConstant == i) {
                        this.mNameValuesHolder.remove(i2);
                        this.mPropertyMask = (~i) & this.mPropertyMask;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int i, float f, float f2) {
            this.mNameConstant = i;
            this.mFromValue = f;
            this.mDeltaValue = f2;
        }
    }

    ViewPropertyAnimator(android.view.View view) {
        this.mView = view;
        view.ensureTransformationInfo();
    }

    public android.view.ViewPropertyAnimator setDuration(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.mDurationSet = true;
        this.mDuration = j;
        return this;
    }

    public long getDuration() {
        if (this.mDurationSet) {
            return this.mDuration;
        }
        if (this.mTempValueAnimator == null) {
            this.mTempValueAnimator = new android.animation.ValueAnimator();
        }
        return this.mTempValueAnimator.getDuration();
    }

    public long getStartDelay() {
        if (this.mStartDelaySet) {
            return this.mStartDelay;
        }
        return 0L;
    }

    public android.view.ViewPropertyAnimator setStartDelay(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Animators cannot have negative start delay: " + j);
        }
        this.mStartDelaySet = true;
        this.mStartDelay = j;
        return this;
    }

    public android.view.ViewPropertyAnimator setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public android.animation.TimeInterpolator getInterpolator() {
        if (this.mInterpolatorSet) {
            return this.mInterpolator;
        }
        if (this.mTempValueAnimator == null) {
            this.mTempValueAnimator = new android.animation.ValueAnimator();
        }
        return this.mTempValueAnimator.getInterpolator();
    }

    public android.view.ViewPropertyAnimator setListener(android.animation.Animator.AnimatorListener animatorListener) {
        this.mListener = animatorListener;
        return this;
    }

    android.animation.Animator.AnimatorListener getListener() {
        return this.mListener;
    }

    public android.view.ViewPropertyAnimator setUpdateListener(android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.mUpdateListener = animatorUpdateListener;
        return this;
    }

    android.animation.ValueAnimator.AnimatorUpdateListener getUpdateListener() {
        return this.mUpdateListener;
    }

    public void start() {
        this.mView.removeCallbacks(this.mAnimationStarter);
        startAnimation();
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            java.util.Iterator it = ((java.util.HashMap) this.mAnimatorMap.clone()).keySet().iterator();
            while (it.hasNext()) {
                ((android.animation.Animator) it.next()).cancel();
            }
        }
        this.mPendingAnimations.clear();
        this.mPendingSetupAction = null;
        this.mPendingCleanupAction = null;
        this.mPendingOnStartAction = null;
        this.mPendingOnEndAction = null;
        this.mView.removeCallbacks(this.mAnimationStarter);
    }

    public android.view.ViewPropertyAnimator x(float f) {
        animateProperty(256, f);
        return this;
    }

    public android.view.ViewPropertyAnimator xBy(float f) {
        animatePropertyBy(256, f);
        return this;
    }

    public android.view.ViewPropertyAnimator y(float f) {
        animateProperty(512, f);
        return this;
    }

    public android.view.ViewPropertyAnimator yBy(float f) {
        animatePropertyBy(512, f);
        return this;
    }

    public android.view.ViewPropertyAnimator z(float f) {
        animateProperty(1024, f);
        return this;
    }

    public android.view.ViewPropertyAnimator zBy(float f) {
        animatePropertyBy(1024, f);
        return this;
    }

    public android.view.ViewPropertyAnimator rotation(float f) {
        animateProperty(32, f);
        return this;
    }

    public android.view.ViewPropertyAnimator rotationBy(float f) {
        animatePropertyBy(32, f);
        return this;
    }

    public android.view.ViewPropertyAnimator rotationX(float f) {
        animateProperty(64, f);
        return this;
    }

    public android.view.ViewPropertyAnimator rotationXBy(float f) {
        animatePropertyBy(64, f);
        return this;
    }

    public android.view.ViewPropertyAnimator rotationY(float f) {
        animateProperty(128, f);
        return this;
    }

    public android.view.ViewPropertyAnimator rotationYBy(float f) {
        animatePropertyBy(128, f);
        return this;
    }

    public android.view.ViewPropertyAnimator translationX(float f) {
        animateProperty(1, f);
        return this;
    }

    public android.view.ViewPropertyAnimator translationXBy(float f) {
        animatePropertyBy(1, f);
        return this;
    }

    public android.view.ViewPropertyAnimator translationY(float f) {
        animateProperty(2, f);
        return this;
    }

    public android.view.ViewPropertyAnimator translationYBy(float f) {
        animatePropertyBy(2, f);
        return this;
    }

    public android.view.ViewPropertyAnimator translationZ(float f) {
        animateProperty(4, f);
        return this;
    }

    public android.view.ViewPropertyAnimator translationZBy(float f) {
        animatePropertyBy(4, f);
        return this;
    }

    public android.view.ViewPropertyAnimator scaleX(float f) {
        animateProperty(8, f);
        return this;
    }

    public android.view.ViewPropertyAnimator scaleXBy(float f) {
        animatePropertyBy(8, f);
        return this;
    }

    public android.view.ViewPropertyAnimator scaleY(float f) {
        animateProperty(16, f);
        return this;
    }

    public android.view.ViewPropertyAnimator scaleYBy(float f) {
        animatePropertyBy(16, f);
        return this;
    }

    public android.view.ViewPropertyAnimator alpha(float f) {
        animateProperty(2048, f);
        return this;
    }

    public android.view.ViewPropertyAnimator alphaBy(float f) {
        animatePropertyBy(2048, f);
        return this;
    }

    public android.view.ViewPropertyAnimator withLayer() {
        this.mPendingSetupAction = new java.lang.Runnable() { // from class: android.view.ViewPropertyAnimator.2
            @Override // java.lang.Runnable
            public void run() {
                android.view.ViewPropertyAnimator.this.mView.setLayerType(2, null);
                if (android.view.ViewPropertyAnimator.this.mView.isAttachedToWindow()) {
                    android.view.ViewPropertyAnimator.this.mView.buildLayer();
                }
            }
        };
        final int layerType = this.mView.getLayerType();
        this.mPendingCleanupAction = new java.lang.Runnable() { // from class: android.view.ViewPropertyAnimator.3
            @Override // java.lang.Runnable
            public void run() {
                android.view.ViewPropertyAnimator.this.mView.setLayerType(layerType, null);
            }
        };
        if (this.mAnimatorSetupMap == null) {
            this.mAnimatorSetupMap = new java.util.HashMap<>();
        }
        if (this.mAnimatorCleanupMap == null) {
            this.mAnimatorCleanupMap = new java.util.HashMap<>();
        }
        return this;
    }

    public android.view.ViewPropertyAnimator withStartAction(java.lang.Runnable runnable) {
        this.mPendingOnStartAction = runnable;
        if (runnable != null && this.mAnimatorOnStartMap == null) {
            this.mAnimatorOnStartMap = new java.util.HashMap<>();
        }
        return this;
    }

    public android.view.ViewPropertyAnimator withEndAction(java.lang.Runnable runnable) {
        this.mPendingOnEndAction = runnable;
        if (runnable != null && this.mAnimatorOnEndMap == null) {
            this.mAnimatorOnEndMap = new java.util.HashMap<>();
        }
        return this;
    }

    boolean hasActions() {
        return (this.mPendingSetupAction == null && this.mPendingCleanupAction == null && this.mPendingOnStartAction == null && this.mPendingOnEndAction == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation() {
        this.mView.setHasTransientState(true);
        android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(1.0f);
        java.util.ArrayList arrayList = (java.util.ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i |= ((android.view.ViewPropertyAnimator.NameValuesHolder) arrayList.get(i2)).mNameConstant;
        }
        this.mAnimatorMap.put(ofFloat, new android.view.ViewPropertyAnimator.PropertyBundle(i, arrayList));
        if (this.mPendingSetupAction != null) {
            this.mAnimatorSetupMap.put(ofFloat, this.mPendingSetupAction);
            this.mPendingSetupAction = null;
        }
        if (this.mPendingCleanupAction != null) {
            this.mAnimatorCleanupMap.put(ofFloat, this.mPendingCleanupAction);
            this.mPendingCleanupAction = null;
        }
        if (this.mPendingOnStartAction != null) {
            this.mAnimatorOnStartMap.put(ofFloat, this.mPendingOnStartAction);
            this.mPendingOnStartAction = null;
        }
        if (this.mPendingOnEndAction != null) {
            this.mAnimatorOnEndMap.put(ofFloat, this.mPendingOnEndAction);
            this.mPendingOnEndAction = null;
        }
        ofFloat.addUpdateListener(this.mAnimatorEventListener);
        ofFloat.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            ofFloat.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            ofFloat.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            ofFloat.setInterpolator(this.mInterpolator);
        }
        ofFloat.start();
    }

    private void animateProperty(int i, float f) {
        float value = getValue(i);
        animatePropertyBy(i, value, f - value);
    }

    private void animatePropertyBy(int i, float f) {
        animatePropertyBy(i, getValue(i), f);
    }

    private void animatePropertyBy(int i, float f, float f2) {
        android.animation.Animator animator;
        if (this.mAnimatorMap.size() > 0) {
            java.util.Iterator<android.animation.Animator> it = this.mAnimatorMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    animator = null;
                    break;
                }
                animator = it.next();
                android.view.ViewPropertyAnimator.PropertyBundle propertyBundle = this.mAnimatorMap.get(animator);
                if (propertyBundle.cancel(i) && propertyBundle.mPropertyMask == 0) {
                    break;
                }
            }
            if (animator != null) {
                animator.cancel();
            }
        }
        this.mPendingAnimations.add(new android.view.ViewPropertyAnimator.NameValuesHolder(i, f, f2));
        this.mView.removeCallbacks(this.mAnimationStarter);
        this.mView.postOnAnimation(this.mAnimationStarter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setValue(int i, float f) {
        android.graphics.RenderNode renderNode = this.mView.mRenderNode;
        switch (i) {
            case 1:
                renderNode.setTranslationX(f);
                break;
            case 2:
                renderNode.setTranslationY(f);
                break;
            case 4:
                renderNode.setTranslationZ(f);
                break;
            case 8:
                renderNode.setScaleX(f);
                break;
            case 16:
                renderNode.setScaleY(f);
                break;
            case 32:
                renderNode.setRotationZ(f);
                break;
            case 64:
                renderNode.setRotationX(f);
                break;
            case 128:
                renderNode.setRotationY(f);
                break;
            case 256:
                renderNode.setTranslationX(f - this.mView.mLeft);
                break;
            case 512:
                renderNode.setTranslationY(f - this.mView.mTop);
                break;
            case 1024:
                renderNode.setTranslationZ(f - renderNode.getElevation());
                break;
            case 2048:
                this.mView.setAlphaInternal(f);
                renderNode.setAlpha(f);
                break;
        }
    }

    private float getValue(int i) {
        android.graphics.RenderNode renderNode = this.mView.mRenderNode;
        switch (i) {
            case 1:
                return renderNode.getTranslationX();
            case 2:
                return renderNode.getTranslationY();
            case 4:
                return renderNode.getTranslationZ();
            case 8:
                return renderNode.getScaleX();
            case 16:
                return renderNode.getScaleY();
            case 32:
                return renderNode.getRotationZ();
            case 64:
                return renderNode.getRotationX();
            case 128:
                return renderNode.getRotationY();
            case 256:
                return this.mView.mLeft + renderNode.getTranslationX();
            case 512:
                return this.mView.mTop + renderNode.getTranslationY();
            case 1024:
                return renderNode.getElevation() + renderNode.getTranslationZ();
            case 2048:
                return this.mView.getAlpha();
            default:
                return 0.0f;
        }
    }

    private class AnimatorEventListener implements android.animation.Animator.AnimatorListener, android.animation.ValueAnimator.AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            if (android.view.ViewPropertyAnimator.this.mAnimatorSetupMap != null) {
                java.lang.Runnable runnable = (java.lang.Runnable) android.view.ViewPropertyAnimator.this.mAnimatorSetupMap.get(animator);
                if (runnable != null) {
                    runnable.run();
                }
                android.view.ViewPropertyAnimator.this.mAnimatorSetupMap.remove(animator);
            }
            if (android.view.ViewPropertyAnimator.this.mAnimatorOnStartMap != null) {
                java.lang.Runnable runnable2 = (java.lang.Runnable) android.view.ViewPropertyAnimator.this.mAnimatorOnStartMap.get(animator);
                if (runnable2 != null) {
                    runnable2.run();
                }
                android.view.ViewPropertyAnimator.this.mAnimatorOnStartMap.remove(animator);
            }
            if (android.view.ViewPropertyAnimator.this.mListener != null) {
                android.view.ViewPropertyAnimator.this.mListener.onAnimationStart(animator);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            if (android.view.ViewPropertyAnimator.this.mListener != null) {
                android.view.ViewPropertyAnimator.this.mListener.onAnimationCancel(animator);
            }
            if (android.view.ViewPropertyAnimator.this.mAnimatorOnEndMap != null) {
                android.view.ViewPropertyAnimator.this.mAnimatorOnEndMap.remove(animator);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
            if (android.view.ViewPropertyAnimator.this.mListener != null) {
                android.view.ViewPropertyAnimator.this.mListener.onAnimationRepeat(animator);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            android.view.ViewPropertyAnimator.this.mView.setHasTransientState(false);
            if (android.view.ViewPropertyAnimator.this.mAnimatorCleanupMap != null) {
                java.lang.Runnable runnable = (java.lang.Runnable) android.view.ViewPropertyAnimator.this.mAnimatorCleanupMap.get(animator);
                if (runnable != null) {
                    runnable.run();
                }
                android.view.ViewPropertyAnimator.this.mAnimatorCleanupMap.remove(animator);
            }
            if (android.view.ViewPropertyAnimator.this.mListener != null) {
                android.view.ViewPropertyAnimator.this.mListener.onAnimationEnd(animator);
            }
            if (android.view.ViewPropertyAnimator.this.mAnimatorOnEndMap != null) {
                java.lang.Runnable runnable2 = (java.lang.Runnable) android.view.ViewPropertyAnimator.this.mAnimatorOnEndMap.get(animator);
                if (runnable2 != null) {
                    runnable2.run();
                }
                android.view.ViewPropertyAnimator.this.mAnimatorOnEndMap.remove(animator);
            }
            android.view.ViewPropertyAnimator.this.mAnimatorMap.remove(animator);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            boolean z;
            android.view.ViewPropertyAnimator.PropertyBundle propertyBundle = (android.view.ViewPropertyAnimator.PropertyBundle) android.view.ViewPropertyAnimator.this.mAnimatorMap.get(valueAnimator);
            if (propertyBundle == null) {
                return;
            }
            boolean isHardwareAccelerated = android.view.ViewPropertyAnimator.this.mView.isHardwareAccelerated();
            if (!isHardwareAccelerated) {
                android.view.ViewPropertyAnimator.this.mView.invalidateParentCaches();
            }
            float animatedFraction = valueAnimator.getAnimatedFraction();
            int i = propertyBundle.mPropertyMask & 2047;
            if (i != 0) {
                android.view.ViewPropertyAnimator.this.mView.invalidateViewProperty(isHardwareAccelerated, false);
            }
            java.util.ArrayList<android.view.ViewPropertyAnimator.NameValuesHolder> arrayList = propertyBundle.mNameValuesHolder;
            if (arrayList == null) {
                z = false;
            } else {
                int size = arrayList.size();
                z = false;
                for (int i2 = 0; i2 < size; i2++) {
                    android.view.ViewPropertyAnimator.NameValuesHolder nameValuesHolder = arrayList.get(i2);
                    float f = nameValuesHolder.mFromValue + (nameValuesHolder.mDeltaValue * animatedFraction);
                    if (nameValuesHolder.mNameConstant == 2048) {
                        z = android.view.ViewPropertyAnimator.this.mView.setAlphaNoInvalidation(f);
                    } else {
                        android.view.ViewPropertyAnimator.this.setValue(nameValuesHolder.mNameConstant, f);
                    }
                }
            }
            if (i != 0 && !isHardwareAccelerated) {
                android.view.ViewPropertyAnimator.this.mView.mPrivateFlags |= 32;
            }
            if (z) {
                android.view.ViewPropertyAnimator.this.mView.invalidate(true);
            } else {
                android.view.ViewPropertyAnimator.this.mView.invalidateViewProperty(false, false);
            }
            if (android.view.ViewPropertyAnimator.this.mUpdateListener != null) {
                android.view.ViewPropertyAnimator.this.mUpdateListener.onAnimationUpdate(valueAnimator);
            }
        }
    }
}
