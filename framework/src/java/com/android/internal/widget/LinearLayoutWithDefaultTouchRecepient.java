package com.android.internal.widget;

/* loaded from: classes5.dex */
public class LinearLayoutWithDefaultTouchRecepient extends android.widget.LinearLayout {
    private android.view.View mDefaultTouchRecepient;
    private final android.graphics.Rect mTempRect;

    public LinearLayoutWithDefaultTouchRecepient(android.content.Context context) {
        super(context);
        this.mTempRect = new android.graphics.Rect();
    }

    public LinearLayoutWithDefaultTouchRecepient(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new android.graphics.Rect();
    }

    public void setDefaultTouchRecepient(android.view.View view) {
        this.mDefaultTouchRecepient = view;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mDefaultTouchRecepient == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (super.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        this.mTempRect.set(0, 0, 0, 0);
        offsetRectIntoDescendantCoords(this.mDefaultTouchRecepient, this.mTempRect);
        motionEvent.setLocation(motionEvent.getX() + this.mTempRect.left, motionEvent.getY() + this.mTempRect.top);
        return this.mDefaultTouchRecepient.dispatchTouchEvent(motionEvent);
    }
}
