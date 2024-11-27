package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
public final class NavigationBarFrame extends android.widget.FrameLayout {
    private android.inputmethodservice.navigationbar.DeadZone mDeadZone;

    public NavigationBarFrame(android.content.Context context) {
        super(context);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDeadZone = null;
    }

    public void setDeadZone(android.inputmethodservice.navigationbar.DeadZone deadZone) {
        this.mDeadZone = deadZone;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 4 && this.mDeadZone != null) {
            return this.mDeadZone.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
