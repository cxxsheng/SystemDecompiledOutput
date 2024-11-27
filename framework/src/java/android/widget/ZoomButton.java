package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class ZoomButton extends android.widget.ImageButton implements android.view.View.OnLongClickListener {
    private boolean mIsInLongpress;
    private final java.lang.Runnable mRunnable;
    private long mZoomSpeed;

    public ZoomButton(android.content.Context context) {
        this(context, null);
    }

    public ZoomButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ZoomButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRunnable = new java.lang.Runnable() { // from class: android.widget.ZoomButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (android.widget.ZoomButton.this.hasOnClickListeners() && android.widget.ZoomButton.this.mIsInLongpress && android.widget.ZoomButton.this.isEnabled()) {
                    android.widget.ZoomButton.this.callOnClick();
                    android.widget.ZoomButton.this.postDelayed(this, android.widget.ZoomButton.this.mZoomSpeed);
                }
            }
        };
        this.mZoomSpeed = 1000L;
        setOnLongClickListener(this);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.mIsInLongpress = false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setZoomSpeed(long j) {
        this.mZoomSpeed = j;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(android.view.View view) {
        this.mIsInLongpress = true;
        post(this.mRunnable);
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        this.mIsInLongpress = false;
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (!z) {
            setPressed(false);
        }
        super.setEnabled(z);
    }

    @Override // android.view.View
    public boolean dispatchUnhandledMove(android.view.View view, int i) {
        clearFocus();
        return super.dispatchUnhandledMove(view, i);
    }

    @Override // android.widget.ImageButton, android.widget.ImageView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ZoomButton.class.getName();
    }
}
