package com.android.server.autofill.ui;

/* loaded from: classes.dex */
public class BottomSheetButtonBarLayout extends com.android.internal.widget.ButtonBarLayout {
    public BottomSheetButtonBarLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        android.view.View findViewById = findViewById(android.R.id.autofill_button_bar_spacer);
        if (findViewById == null) {
            return;
        }
        if (isStacked()) {
            findViewById.getLayoutParams().width = 0;
            findViewById.getLayoutParams().height = getResources().getDimensionPixelSize(android.R.dimen.aerr_padding_list_bottom);
            setGravity(8388629);
            return;
        }
        findViewById.getLayoutParams().width = getResources().getDimensionPixelSize(android.R.dimen.aerr_padding_list_top);
        findViewById.getLayoutParams().height = 0;
        setGravity(16);
    }

    private boolean isStacked() {
        return getOrientation() == 1;
    }
}
