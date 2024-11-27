package android.transition;

/* loaded from: classes3.dex */
public class Slide extends android.transition.Visibility {
    private static final java.lang.String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final java.lang.String TAG = "Slide";
    private android.transition.Slide.CalculateSlide mSlideCalculator;
    private int mSlideEdge;
    private float mSlideFraction;
    private static final android.animation.TimeInterpolator sDecelerate = new android.view.animation.DecelerateInterpolator();
    private static final android.animation.TimeInterpolator sAccelerate = new android.view.animation.AccelerateInterpolator();
    private static final android.transition.Slide.CalculateSlide sCalculateLeft = new android.transition.Slide.CalculateSlideHorizontal() { // from class: android.transition.Slide.1
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            return view.getTranslationX() - (viewGroup.getWidth() * f);
        }
    };
    private static final android.transition.Slide.CalculateSlide sCalculateStart = new android.transition.Slide.CalculateSlideHorizontal() { // from class: android.transition.Slide.2
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            if (viewGroup.getLayoutDirection() == 1) {
                return view.getTranslationX() + (viewGroup.getWidth() * f);
            }
            return view.getTranslationX() - (viewGroup.getWidth() * f);
        }
    };
    private static final android.transition.Slide.CalculateSlide sCalculateTop = new android.transition.Slide.CalculateSlideVertical() { // from class: android.transition.Slide.3
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneY(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            return view.getTranslationY() - (viewGroup.getHeight() * f);
        }
    };
    private static final android.transition.Slide.CalculateSlide sCalculateRight = new android.transition.Slide.CalculateSlideHorizontal() { // from class: android.transition.Slide.4
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            return view.getTranslationX() + (viewGroup.getWidth() * f);
        }
    };
    private static final android.transition.Slide.CalculateSlide sCalculateEnd = new android.transition.Slide.CalculateSlideHorizontal() { // from class: android.transition.Slide.5
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            if (viewGroup.getLayoutDirection() == 1) {
                return view.getTranslationX() - (viewGroup.getWidth() * f);
            }
            return view.getTranslationX() + (viewGroup.getWidth() * f);
        }
    };
    private static final android.transition.Slide.CalculateSlide sCalculateBottom = new android.transition.Slide.CalculateSlideVertical() { // from class: android.transition.Slide.6
        @Override // android.transition.Slide.CalculateSlide
        public float getGoneY(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            return view.getTranslationY() + (viewGroup.getHeight() * f);
        }
    };

    private interface CalculateSlide {
        float getGoneX(android.view.ViewGroup viewGroup, android.view.View view, float f);

        float getGoneY(android.view.ViewGroup viewGroup, android.view.View view, float f);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    private static abstract class CalculateSlideHorizontal implements android.transition.Slide.CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        @Override // android.transition.Slide.CalculateSlide
        public float getGoneY(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical implements android.transition.Slide.CalculateSlide {
        private CalculateSlideVertical() {
        }

        @Override // android.transition.Slide.CalculateSlide
        public float getGoneX(android.view.ViewGroup viewGroup, android.view.View view, float f) {
            return view.getTranslationX();
        }
    }

    public Slide() {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        this.mSlideFraction = 1.0f;
        setSlideEdge(80);
    }

    public Slide(int i) {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        this.mSlideFraction = 1.0f;
        setSlideEdge(i);
    }

    public Slide(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        this.mSlideFraction = 1.0f;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Slide);
        int i = obtainStyledAttributes.getInt(0, 80);
        obtainStyledAttributes.recycle();
        setSlideEdge(i);
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, iArr);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int i) {
        switch (i) {
            case 3:
                this.mSlideCalculator = sCalculateLeft;
                break;
            case 5:
                this.mSlideCalculator = sCalculateRight;
                break;
            case 48:
                this.mSlideCalculator = sCalculateTop;
                break;
            case 80:
                this.mSlideCalculator = sCalculateBottom;
                break;
            case android.view.Gravity.START /* 8388611 */:
                this.mSlideCalculator = sCalculateStart;
                break;
            case android.view.Gravity.END /* 8388613 */:
                this.mSlideCalculator = sCalculateEnd;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = i;
        android.transition.SidePropagation sidePropagation = new android.transition.SidePropagation();
        sidePropagation.setSide(i);
        setPropagation(sidePropagation);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onAppear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues2.values.get(PROPNAME_SCREEN_POSITION);
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        return android.transition.TranslationAnimationCreator.createAnimation(view, transitionValues2, iArr[0], iArr[1], this.mSlideCalculator.getGoneX(viewGroup, view, this.mSlideFraction), this.mSlideCalculator.getGoneY(viewGroup, view, this.mSlideFraction), translationX, translationY, sDecelerate, this);
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onDisappear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        return android.transition.TranslationAnimationCreator.createAnimation(view, transitionValues, iArr[0], iArr[1], view.getTranslationX(), view.getTranslationY(), this.mSlideCalculator.getGoneX(viewGroup, view, this.mSlideFraction), this.mSlideCalculator.getGoneY(viewGroup, view, this.mSlideFraction), sAccelerate, this);
    }

    public void setSlideFraction(float f) {
        this.mSlideFraction = f;
    }
}
