package android.view.animation;

/* loaded from: classes4.dex */
public class GridLayoutAnimationController extends android.view.animation.LayoutAnimationController {
    public static final int DIRECTION_BOTTOM_TO_TOP = 2;
    public static final int DIRECTION_HORIZONTAL_MASK = 1;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int DIRECTION_TOP_TO_BOTTOM = 0;
    public static final int DIRECTION_VERTICAL_MASK = 2;
    public static final int PRIORITY_COLUMN = 1;
    public static final int PRIORITY_NONE = 0;
    public static final int PRIORITY_ROW = 2;
    private float mColumnDelay;
    private int mDirection;
    private int mDirectionPriority;
    private float mRowDelay;

    public static class AnimationParameters extends android.view.animation.LayoutAnimationController.AnimationParameters {
        public int column;
        public int columnsCount;
        public int row;
        public int rowsCount;
    }

    public GridLayoutAnimationController(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.GridLayoutAnimation);
        this.mColumnDelay = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context).value;
        this.mRowDelay = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context).value;
        this.mDirection = obtainStyledAttributes.getInt(2, 0);
        this.mDirectionPriority = obtainStyledAttributes.getInt(3, 0);
        obtainStyledAttributes.recycle();
    }

    public GridLayoutAnimationController(android.view.animation.Animation animation) {
        this(animation, 0.5f, 0.5f);
    }

    public GridLayoutAnimationController(android.view.animation.Animation animation, float f, float f2) {
        super(animation);
        this.mColumnDelay = f;
        this.mRowDelay = f2;
    }

    public float getColumnDelay() {
        return this.mColumnDelay;
    }

    public void setColumnDelay(float f) {
        this.mColumnDelay = f;
    }

    public float getRowDelay() {
        return this.mRowDelay;
    }

    public void setRowDelay(float f) {
        this.mRowDelay = f;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public void setDirection(int i) {
        this.mDirection = i;
    }

    public int getDirectionPriority() {
        return this.mDirectionPriority;
    }

    public void setDirectionPriority(int i) {
        this.mDirectionPriority = i;
    }

    @Override // android.view.animation.LayoutAnimationController
    public boolean willOverlap() {
        return this.mColumnDelay < 1.0f || this.mRowDelay < 1.0f;
    }

    @Override // android.view.animation.LayoutAnimationController
    protected long getDelayForView(android.view.View view) {
        long j;
        float f;
        android.view.animation.GridLayoutAnimationController.AnimationParameters animationParameters = (android.view.animation.GridLayoutAnimationController.AnimationParameters) view.getLayoutParams().layoutAnimationParameters;
        if (animationParameters == null) {
            return 0L;
        }
        int transformedColumnIndex = getTransformedColumnIndex(animationParameters);
        int transformedRowIndex = getTransformedRowIndex(animationParameters);
        int i = animationParameters.rowsCount;
        int i2 = animationParameters.columnsCount;
        float duration = this.mAnimation.getDuration();
        float f2 = this.mColumnDelay * duration;
        float f3 = this.mRowDelay * duration;
        if (this.mInterpolator == null) {
            this.mInterpolator = new android.view.animation.LinearInterpolator();
        }
        switch (this.mDirectionPriority) {
            case 1:
                j = (long) ((transformedRowIndex * f3) + (transformedColumnIndex * i * f3));
                f = (i * f3) + (i2 * i * f3);
                break;
            case 2:
                j = (long) ((transformedColumnIndex * f2) + (transformedRowIndex * i2 * f2));
                f = (i2 * f2) + (i * i2 * f2);
                break;
            default:
                j = (long) ((transformedColumnIndex * f2) + (transformedRowIndex * f3));
                f = (i2 * f2) + (i * f3);
                break;
        }
        return (long) (this.mInterpolator.getInterpolation(j / f) * f);
    }

    private int getTransformedColumnIndex(android.view.animation.GridLayoutAnimationController.AnimationParameters animationParameters) {
        int i;
        switch (getOrder()) {
            case 1:
                i = (animationParameters.columnsCount - 1) - animationParameters.column;
                break;
            case 2:
                if (this.mRandomizer == null) {
                    this.mRandomizer = new java.util.Random();
                }
                i = (int) (animationParameters.columnsCount * this.mRandomizer.nextFloat());
                break;
            default:
                i = animationParameters.column;
                break;
        }
        if ((this.mDirection & 1) == 1) {
            return (animationParameters.columnsCount - 1) - i;
        }
        return i;
    }

    private int getTransformedRowIndex(android.view.animation.GridLayoutAnimationController.AnimationParameters animationParameters) {
        int i;
        switch (getOrder()) {
            case 1:
                i = (animationParameters.rowsCount - 1) - animationParameters.row;
                break;
            case 2:
                if (this.mRandomizer == null) {
                    this.mRandomizer = new java.util.Random();
                }
                i = (int) (animationParameters.rowsCount * this.mRandomizer.nextFloat());
                break;
            default:
                i = animationParameters.row;
                break;
        }
        if ((this.mDirection & 2) == 2) {
            return (animationParameters.rowsCount - 1) - i;
        }
        return i;
    }
}
