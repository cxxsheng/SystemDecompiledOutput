package android.inputmethodservice;

/* loaded from: classes2.dex */
public class CompactExtractEditLayout extends android.widget.LinearLayout {
    private android.view.View mInputExtractAccessories;
    private android.view.View mInputExtractAction;
    private android.view.View mInputExtractEditText;
    private boolean mPerformLayoutChanges;

    public CompactExtractEditLayout(android.content.Context context) {
        super(context);
    }

    public CompactExtractEditLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CompactExtractEditLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInputExtractEditText = findViewById(16908325);
        this.mInputExtractAccessories = findViewById(16908378);
        this.mInputExtractAction = findViewById(16908377);
        if (this.mInputExtractEditText != null && this.mInputExtractAccessories != null && this.mInputExtractAction != null) {
            this.mPerformLayoutChanges = true;
        }
    }

    private int applyFractionInt(int i, int i2) {
        return java.lang.Math.round(getResources().getFraction(i, i2, i2));
    }

    private static void setLayoutHeight(android.view.View view, int i) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    private static void setLayoutMarginBottom(android.view.View view, int i) {
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.bottomMargin = i;
        view.setLayoutParams(marginLayoutParams);
    }

    private void applyProportionalLayout(int i, int i2) {
        if (getResources().getConfiguration().isScreenRound()) {
            setGravity(80);
        }
        setLayoutHeight(this, applyFractionInt(com.android.internal.R.fraction.input_extract_layout_height, i2));
        setPadding(applyFractionInt(com.android.internal.R.fraction.input_extract_layout_padding_left, i), 0, applyFractionInt(com.android.internal.R.fraction.input_extract_layout_padding_right, i), 0);
        setLayoutMarginBottom(this.mInputExtractEditText, applyFractionInt(com.android.internal.R.fraction.input_extract_text_margin_bottom, i2));
        setLayoutMarginBottom(this.mInputExtractAccessories, applyFractionInt(com.android.internal.R.fraction.input_extract_action_margin_bottom, i2));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mPerformLayoutChanges) {
            android.content.res.Resources resources = getResources();
            android.content.res.Configuration configuration = resources.getConfiguration();
            android.util.DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (configuration.isScreenRound() && i2 < i) {
                i2 = i;
            }
            applyProportionalLayout(i, i2);
        }
    }
}
