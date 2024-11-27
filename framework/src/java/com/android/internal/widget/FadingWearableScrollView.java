package com.android.internal.widget;

/* loaded from: classes5.dex */
public class FadingWearableScrollView extends android.widget.ScrollView {
    private com.android.internal.widget.ViewGroupFader mFader;

    public FadingWearableScrollView(android.content.Context context) {
        this(context, null);
    }

    public FadingWearableScrollView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842880);
    }

    public FadingWearableScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FadingWearableScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        this.mFader = createFader(this);
    }

    private com.android.internal.widget.ViewGroupFader createFader(android.view.ViewGroup viewGroup) {
        return new com.android.internal.widget.ViewGroupFader(viewGroup, new com.android.internal.widget.ViewGroupFader.AnimationCallback() { // from class: com.android.internal.widget.FadingWearableScrollView.1
            @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
            public boolean shouldFadeFromTop(android.view.View view) {
                return true;
            }

            @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
            public boolean shouldFadeFromBottom(android.view.View view) {
                return true;
            }

            @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
            public void viewHasBecomeFullSize(android.view.View view) {
            }
        }, new com.android.internal.widget.ViewGroupFader.GlobalVisibleViewBoundsProvider());
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mFader.updateFade();
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mFader.updateFade();
    }
}
