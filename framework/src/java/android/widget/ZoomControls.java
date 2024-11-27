package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class ZoomControls extends android.widget.LinearLayout {
    private final android.widget.ZoomButton mZoomIn;
    private final android.widget.ZoomButton mZoomOut;

    public ZoomControls(android.content.Context context) {
        this(context, null);
    }

    public ZoomControls(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(false);
        ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(com.android.internal.R.layout.zoom_controls, (android.view.ViewGroup) this, true);
        this.mZoomIn = (android.widget.ZoomButton) findViewById(com.android.internal.R.id.zoomIn);
        this.mZoomOut = (android.widget.ZoomButton) findViewById(com.android.internal.R.id.zoomOut);
    }

    public void setOnZoomInClickListener(android.view.View.OnClickListener onClickListener) {
        this.mZoomIn.setOnClickListener(onClickListener);
    }

    public void setOnZoomOutClickListener(android.view.View.OnClickListener onClickListener) {
        this.mZoomOut.setOnClickListener(onClickListener);
    }

    public void setZoomSpeed(long j) {
        this.mZoomIn.setZoomSpeed(j);
        this.mZoomOut.setZoomSpeed(j);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        return true;
    }

    public void show() {
        fade(0, 0.0f, 1.0f);
    }

    public void hide() {
        fade(8, 1.0f, 0.0f);
    }

    private void fade(int i, float f, float f2) {
        android.view.animation.AlphaAnimation alphaAnimation = new android.view.animation.AlphaAnimation(f, f2);
        alphaAnimation.setDuration(500L);
        startAnimation(alphaAnimation);
        setVisibility(i);
    }

    public void setIsZoomInEnabled(boolean z) {
        this.mZoomIn.setEnabled(z);
    }

    public void setIsZoomOutEnabled(boolean z) {
        this.mZoomOut.setEnabled(z);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.mZoomIn.hasFocus() || this.mZoomOut.hasFocus();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ZoomControls.class.getName();
    }
}
