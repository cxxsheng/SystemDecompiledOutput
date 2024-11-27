package android.transition;

/* loaded from: classes3.dex */
public class TransitionValues {
    public android.view.View view;
    public final java.util.Map<java.lang.String, java.lang.Object> values = new android.util.ArrayMap();
    final java.util.ArrayList<android.transition.Transition> targetedTransitions = new java.util.ArrayList<>();

    @java.lang.Deprecated
    public TransitionValues() {
    }

    public TransitionValues(android.view.View view) {
        this.view = view;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.transition.TransitionValues) {
            android.transition.TransitionValues transitionValues = (android.transition.TransitionValues) obj;
            if (this.view == transitionValues.view && this.values.equals(transitionValues.values)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return (this.view.hashCode() * 31) + this.values.hashCode();
    }

    public java.lang.String toString() {
        java.lang.String str = (("TransitionValues@" + java.lang.Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.view + "\n") + "    values:";
        for (java.lang.String str2 : this.values.keySet()) {
            str = str + "    " + str2 + ": " + this.values.get(str2) + "\n";
        }
        return str;
    }
}
