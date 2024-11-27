package android.gesture;

/* loaded from: classes.dex */
public abstract class GestureLibrary {
    protected final android.gesture.GestureStore mStore = new android.gesture.GestureStore();

    public abstract boolean load();

    public abstract boolean save();

    protected GestureLibrary() {
    }

    public boolean isReadOnly() {
        return false;
    }

    public android.gesture.Learner getLearner() {
        return this.mStore.getLearner();
    }

    public void setOrientationStyle(int i) {
        this.mStore.setOrientationStyle(i);
    }

    public int getOrientationStyle() {
        return this.mStore.getOrientationStyle();
    }

    public void setSequenceType(int i) {
        this.mStore.setSequenceType(i);
    }

    public int getSequenceType() {
        return this.mStore.getSequenceType();
    }

    public java.util.Set<java.lang.String> getGestureEntries() {
        return this.mStore.getGestureEntries();
    }

    public java.util.ArrayList<android.gesture.Prediction> recognize(android.gesture.Gesture gesture) {
        return this.mStore.recognize(gesture);
    }

    public void addGesture(java.lang.String str, android.gesture.Gesture gesture) {
        this.mStore.addGesture(str, gesture);
    }

    public void removeGesture(java.lang.String str, android.gesture.Gesture gesture) {
        this.mStore.removeGesture(str, gesture);
    }

    public void removeEntry(java.lang.String str) {
        this.mStore.removeEntry(str);
    }

    public java.util.ArrayList<android.gesture.Gesture> getGestures(java.lang.String str) {
        return this.mStore.getGestures(str);
    }
}
