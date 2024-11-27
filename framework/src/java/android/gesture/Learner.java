package android.gesture;

/* loaded from: classes.dex */
abstract class Learner {
    private final java.util.ArrayList<android.gesture.Instance> mInstances = new java.util.ArrayList<>();

    abstract java.util.ArrayList<android.gesture.Prediction> classify(int i, int i2, float[] fArr);

    Learner() {
    }

    void addInstance(android.gesture.Instance instance) {
        this.mInstances.add(instance);
    }

    java.util.ArrayList<android.gesture.Instance> getInstances() {
        return this.mInstances;
    }

    void removeInstance(long j) {
        java.util.ArrayList<android.gesture.Instance> arrayList = this.mInstances;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            android.gesture.Instance instance = arrayList.get(i);
            if (j == instance.id) {
                arrayList.remove(instance);
                return;
            }
        }
    }

    void removeInstances(java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList<android.gesture.Instance> arrayList2 = this.mInstances;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            android.gesture.Instance instance = arrayList2.get(i);
            if ((instance.label == null && str == null) || (instance.label != null && instance.label.equals(str))) {
                arrayList.add(instance);
            }
        }
        arrayList2.removeAll(arrayList);
    }
}
