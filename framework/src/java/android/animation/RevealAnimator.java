package android.animation;

/* loaded from: classes.dex */
public class RevealAnimator extends android.view.RenderNodeAnimator {
    private android.view.View mClipView;

    public RevealAnimator(android.view.View view, int i, int i2, float f, float f2) {
        super(i, i2, f, f2);
        this.mClipView = view;
        setTarget(this.mClipView);
    }

    @Override // android.graphics.animation.RenderNodeAnimator
    protected void onFinished() {
        this.mClipView.setRevealClip(false, 0.0f, 0.0f, 0.0f);
        super.onFinished();
    }
}
