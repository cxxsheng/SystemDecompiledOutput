package android.transition;

/* loaded from: classes3.dex */
public abstract class VisibilityPropagation extends android.transition.TransitionPropagation {
    private static final java.lang.String PROPNAME_VISIBILITY = "android:visibilityPropagation:visibility";
    private static final java.lang.String PROPNAME_VIEW_CENTER = "android:visibilityPropagation:center";
    private static final java.lang.String[] VISIBILITY_PROPAGATION_VALUES = {PROPNAME_VISIBILITY, PROPNAME_VIEW_CENTER};

    @Override // android.transition.TransitionPropagation
    public void captureValues(android.transition.TransitionValues transitionValues) {
        android.view.View view = transitionValues.view;
        java.lang.Integer num = (java.lang.Integer) transitionValues.values.get("android:visibility:visibility");
        if (num == null) {
            num = java.lang.Integer.valueOf(view.getVisibility());
        }
        transitionValues.values.put(PROPNAME_VISIBILITY, num);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        iArr[0] = iArr[0] + java.lang.Math.round(view.getTranslationX());
        iArr[0] = iArr[0] + (view.getWidth() / 2);
        iArr[1] = iArr[1] + java.lang.Math.round(view.getTranslationY());
        iArr[1] = iArr[1] + (view.getHeight() / 2);
        transitionValues.values.put(PROPNAME_VIEW_CENTER, iArr);
    }

    @Override // android.transition.TransitionPropagation
    public java.lang.String[] getPropagationProperties() {
        return VISIBILITY_PROPAGATION_VALUES;
    }

    public int getViewVisibility(android.transition.TransitionValues transitionValues) {
        java.lang.Integer num;
        if (transitionValues == null || (num = (java.lang.Integer) transitionValues.values.get(PROPNAME_VISIBILITY)) == null) {
            return 8;
        }
        return num.intValue();
    }

    public int getViewX(android.transition.TransitionValues transitionValues) {
        return getViewCoordinate(transitionValues, 0);
    }

    public int getViewY(android.transition.TransitionValues transitionValues) {
        return getViewCoordinate(transitionValues, 1);
    }

    private static int getViewCoordinate(android.transition.TransitionValues transitionValues, int i) {
        int[] iArr;
        if (transitionValues == null || (iArr = (int[]) transitionValues.values.get(PROPNAME_VIEW_CENTER)) == null) {
            return -1;
        }
        return iArr[i];
    }
}
