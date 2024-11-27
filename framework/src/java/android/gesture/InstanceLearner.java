package android.gesture;

/* loaded from: classes.dex */
class InstanceLearner extends android.gesture.Learner {
    private static final java.util.Comparator<android.gesture.Prediction> sComparator = new java.util.Comparator<android.gesture.Prediction>() { // from class: android.gesture.InstanceLearner.1
        @Override // java.util.Comparator
        public int compare(android.gesture.Prediction prediction, android.gesture.Prediction prediction2) {
            double d = prediction.score;
            double d2 = prediction2.score;
            if (d > d2) {
                return -1;
            }
            if (d < d2) {
                return 1;
            }
            return 0;
        }
    };

    InstanceLearner() {
    }

    @Override // android.gesture.Learner
    java.util.ArrayList<android.gesture.Prediction> classify(int i, int i2, float[] fArr) {
        double squaredEuclideanDistance;
        double d;
        java.util.ArrayList<android.gesture.Prediction> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList<android.gesture.Instance> instances = getInstances();
        int size = instances.size();
        java.util.TreeMap treeMap = new java.util.TreeMap();
        for (int i3 = 0; i3 < size; i3++) {
            android.gesture.Instance instance = instances.get(i3);
            if (instance.vector.length == fArr.length) {
                if (i == 2) {
                    squaredEuclideanDistance = android.gesture.GestureUtils.minimumCosineDistance(instance.vector, fArr, i2);
                } else {
                    squaredEuclideanDistance = android.gesture.GestureUtils.squaredEuclideanDistance(instance.vector, fArr);
                }
                if (squaredEuclideanDistance == 0.0d) {
                    d = Double.MAX_VALUE;
                } else {
                    d = 1.0d / squaredEuclideanDistance;
                }
                java.lang.Double d2 = (java.lang.Double) treeMap.get(instance.label);
                if (d2 == null || d > d2.doubleValue()) {
                    treeMap.put(instance.label, java.lang.Double.valueOf(d));
                }
            }
        }
        for (java.lang.String str : treeMap.keySet()) {
            arrayList.add(new android.gesture.Prediction(str, ((java.lang.Double) treeMap.get(str)).doubleValue()));
        }
        java.util.Collections.sort(arrayList, sComparator);
        return arrayList;
    }
}
