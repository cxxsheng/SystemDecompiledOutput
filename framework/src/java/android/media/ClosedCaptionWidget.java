package android.media;

/* compiled from: ClosedCaptionRenderer.java */
/* loaded from: classes2.dex */
abstract class ClosedCaptionWidget extends android.view.ViewGroup implements android.media.SubtitleTrack.RenderingWidget {
    private static final android.view.accessibility.CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = android.view.accessibility.CaptioningManager.CaptionStyle.DEFAULT;
    protected android.view.accessibility.CaptioningManager.CaptionStyle mCaptionStyle;
    private final android.view.accessibility.CaptioningManager.CaptioningChangeListener mCaptioningListener;
    protected android.media.ClosedCaptionWidget.ClosedCaptionLayout mClosedCaptionLayout;
    private boolean mHasChangeListener;
    protected android.media.SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final android.view.accessibility.CaptioningManager mManager;

    /* compiled from: ClosedCaptionRenderer.java */
    interface ClosedCaptionLayout {
        void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle);

        void setFontScale(float f);
    }

    public abstract android.media.ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(android.content.Context context);

    public ClosedCaptionWidget(android.content.Context context) {
        this(context, null);
    }

    public ClosedCaptionWidget(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClosedCaptionWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ClosedCaptionWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCaptioningListener = new android.view.accessibility.CaptioningManager.CaptioningChangeListener() { // from class: android.media.ClosedCaptionWidget.1
            @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
            public void onUserStyleChanged(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
                android.media.ClosedCaptionWidget.this.mCaptionStyle = android.media.ClosedCaptionWidget.DEFAULT_CAPTION_STYLE.applyStyle(captionStyle);
                android.media.ClosedCaptionWidget.this.mClosedCaptionLayout.setCaptionStyle(android.media.ClosedCaptionWidget.this.mCaptionStyle);
            }

            @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
            public void onFontScaleChanged(float f) {
                android.media.ClosedCaptionWidget.this.mClosedCaptionLayout.setFontScale(f);
            }
        };
        setLayerType(1, null);
        this.mManager = (android.view.accessibility.CaptioningManager) context.getSystemService(android.content.Context.CAPTIONING_SERVICE);
        this.mCaptionStyle = DEFAULT_CAPTION_STYLE.applyStyle(this.mManager.getUserStyle());
        this.mClosedCaptionLayout = createCaptionLayout(context);
        this.mClosedCaptionLayout.setCaptionStyle(this.mCaptionStyle);
        this.mClosedCaptionLayout.setFontScale(this.mManager.getFontScale());
        addView((android.view.ViewGroup) this.mClosedCaptionLayout, -1, -1);
        requestLayout();
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setOnChangedListener(android.media.SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener) {
        this.mListener = onChangedListener;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setSize(int i, int i2) {
        measure(android.view.View.MeasureSpec.makeMeasureSpec(i, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        layout(0, 0, i, i2);
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setVisible(boolean z) {
        if (z) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        manageChangeListener();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        manageChangeListener();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        manageChangeListener();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ((android.view.ViewGroup) this.mClosedCaptionLayout).measure(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ((android.view.ViewGroup) this.mClosedCaptionLayout).layout(i, i2, i3, i4);
    }

    private void manageChangeListener() {
        boolean z = isAttachedToWindow() && getVisibility() == 0;
        if (this.mHasChangeListener != z) {
            this.mHasChangeListener = z;
            if (z) {
                this.mManager.addCaptioningChangeListener(this.mCaptioningListener);
            } else {
                this.mManager.removeCaptioningChangeListener(this.mCaptioningListener);
            }
        }
    }
}
