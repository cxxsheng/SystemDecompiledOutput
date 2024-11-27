package android.transition;

/* loaded from: classes3.dex */
public abstract class TransitionPropagation {
    public abstract void captureValues(android.transition.TransitionValues transitionValues);

    public abstract java.lang.String[] getPropagationProperties();

    public abstract long getStartDelay(android.view.ViewGroup viewGroup, android.transition.Transition transition, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2);
}
