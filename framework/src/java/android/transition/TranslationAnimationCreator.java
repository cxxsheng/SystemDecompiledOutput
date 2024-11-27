package android.transition;

/* loaded from: classes3.dex */
class TranslationAnimationCreator {
    TranslationAnimationCreator() {
    }

    static android.animation.Animator createAnimation(android.view.View view, android.transition.TransitionValues transitionValues, int i, int i2, float f, float f2, float f3, float f4, android.animation.TimeInterpolator timeInterpolator, android.transition.Transition transition) {
        float f5;
        float f6;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        if (((int[]) transitionValues.view.getTag(com.android.internal.R.id.transitionPosition)) == null) {
            f5 = f;
            f6 = f2;
        } else {
            f5 = (r4[0] - i) + translationX;
            f6 = (r4[1] - i2) + translationY;
        }
        int round = java.lang.Math.round(f5 - translationX) + i;
        int round2 = java.lang.Math.round(f6 - translationY) + i2;
        view.setTranslationX(f5);
        view.setTranslationY(f6);
        if (f5 == f3 && f6 == f4) {
            return null;
        }
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(f5, f6);
        path.lineTo(f3, f4);
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, android.view.View.TRANSLATION_X, android.view.View.TRANSLATION_Y, path);
        android.transition.TranslationAnimationCreator.TransitionPositionListener transitionPositionListener = new android.transition.TranslationAnimationCreator.TransitionPositionListener(view, transitionValues.view, round, round2, translationX, translationY);
        transition.addListener(transitionPositionListener);
        ofFloat.addListener(transitionPositionListener);
        ofFloat.addPauseListener(transitionPositionListener);
        ofFloat.setInterpolator(timeInterpolator);
        return ofFloat;
    }

    private static class TransitionPositionListener extends android.animation.AnimatorListenerAdapter implements android.transition.Transition.TransitionListener {
        private final android.view.View mMovingView;
        private float mPausedX;
        private float mPausedY;
        private final int mStartX;
        private final int mStartY;
        private final float mTerminalX;
        private final float mTerminalY;
        private int[] mTransitionPosition;
        private final android.view.View mViewInHierarchy;

        private TransitionPositionListener(android.view.View view, android.view.View view2, int i, int i2, float f, float f2) {
            this.mMovingView = view;
            this.mViewInHierarchy = view2;
            this.mStartX = i - java.lang.Math.round(this.mMovingView.getTranslationX());
            this.mStartY = i2 - java.lang.Math.round(this.mMovingView.getTranslationY());
            this.mTerminalX = f;
            this.mTerminalY = f2;
            this.mTransitionPosition = (int[]) this.mViewInHierarchy.getTag(com.android.internal.R.id.transitionPosition);
            if (this.mTransitionPosition != null) {
                this.mViewInHierarchy.setTagInternal(com.android.internal.R.id.transitionPosition, null);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            if (this.mTransitionPosition == null) {
                this.mTransitionPosition = new int[2];
            }
            this.mTransitionPosition[0] = java.lang.Math.round(this.mStartX + this.mMovingView.getTranslationX());
            this.mTransitionPosition[1] = java.lang.Math.round(this.mStartY + this.mMovingView.getTranslationY());
            this.mViewInHierarchy.setTagInternal(com.android.internal.R.id.transitionPosition, this.mTransitionPosition);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public void onAnimationPause(android.animation.Animator animator) {
            this.mPausedX = this.mMovingView.getTranslationX();
            this.mPausedY = this.mMovingView.getTranslationY();
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public void onAnimationResume(android.animation.Animator animator) {
            this.mMovingView.setTranslationX(this.mPausedX);
            this.mMovingView.setTranslationY(this.mPausedY);
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionStart(android.transition.Transition transition) {
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionEnd(android.transition.Transition transition) {
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
            transition.removeListener(this);
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionCancel(android.transition.Transition transition) {
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionPause(android.transition.Transition transition) {
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionResume(android.transition.Transition transition) {
        }
    }
}
