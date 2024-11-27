package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
public class NavigationHandle extends android.view.View implements android.inputmethodservice.navigationbar.ButtonInterface {
    public NavigationHandle(android.content.Context context) {
        this(context, null);
    }

    public NavigationHandle(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(false);
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setImageDrawable(android.graphics.drawable.Drawable drawable) {
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDarkIntensity(float f) {
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDelayTouchFeedback(boolean z) {
    }
}
