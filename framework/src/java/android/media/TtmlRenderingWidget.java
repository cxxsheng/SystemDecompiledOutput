package android.media;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlRenderingWidget extends android.widget.LinearLayout implements android.media.SubtitleTrack.RenderingWidget {
    private android.media.SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final android.widget.TextView mTextView;

    public TtmlRenderingWidget(android.content.Context context) {
        this(context, null);
    }

    public TtmlRenderingWidget(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TtmlRenderingWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TtmlRenderingWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayerType(1, null);
        android.view.accessibility.CaptioningManager captioningManager = (android.view.accessibility.CaptioningManager) context.getSystemService(android.content.Context.CAPTIONING_SERVICE);
        this.mTextView = new android.widget.TextView(context);
        this.mTextView.setTextColor(captioningManager.getUserStyle().foregroundColor);
        addView(this.mTextView, -1, -1);
        this.mTextView.setGravity(81);
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
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setActiveCues(java.util.Vector<android.media.SubtitleTrack.Cue> vector) {
        int size = vector.size();
        java.lang.String str = "";
        for (int i = 0; i < size; i++) {
            str = str + ((android.media.TtmlCue) vector.get(i)).mText + "\n";
        }
        this.mTextView.lambda$setTextAsync$0(str);
        if (this.mListener != null) {
            this.mListener.onChanged(this);
        }
    }
}
