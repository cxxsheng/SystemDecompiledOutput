package android.transition;

/* loaded from: classes3.dex */
public class AutoTransition extends android.transition.TransitionSet {
    public AutoTransition() {
        init();
    }

    public AutoTransition(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setOrdering(1);
        addTransition(new android.transition.Fade(2)).addTransition(new android.transition.ChangeBounds()).addTransition(new android.transition.Fade(1));
    }
}
