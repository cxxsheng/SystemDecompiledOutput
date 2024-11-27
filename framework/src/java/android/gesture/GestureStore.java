package android.gesture;

/* loaded from: classes.dex */
public class GestureStore {
    private static final short FILE_FORMAT_VERSION = 1;
    public static final int ORIENTATION_INVARIANT = 1;
    public static final int ORIENTATION_SENSITIVE = 2;
    static final int ORIENTATION_SENSITIVE_4 = 4;
    static final int ORIENTATION_SENSITIVE_8 = 8;
    private static final boolean PROFILE_LOADING_SAVING = false;
    public static final int SEQUENCE_INVARIANT = 1;
    public static final int SEQUENCE_SENSITIVE = 2;
    private int mSequenceType = 2;
    private int mOrientationStyle = 2;
    private final java.util.HashMap<java.lang.String, java.util.ArrayList<android.gesture.Gesture>> mNamedGestures = new java.util.HashMap<>();
    private boolean mChanged = false;
    private android.gesture.Learner mClassifier = new android.gesture.InstanceLearner();

    public void setOrientationStyle(int i) {
        this.mOrientationStyle = i;
    }

    public int getOrientationStyle() {
        return this.mOrientationStyle;
    }

    public void setSequenceType(int i) {
        this.mSequenceType = i;
    }

    public int getSequenceType() {
        return this.mSequenceType;
    }

    public java.util.Set<java.lang.String> getGestureEntries() {
        return this.mNamedGestures.keySet();
    }

    public java.util.ArrayList<android.gesture.Prediction> recognize(android.gesture.Gesture gesture) {
        return this.mClassifier.classify(this.mSequenceType, this.mOrientationStyle, android.gesture.Instance.createInstance(this.mSequenceType, this.mOrientationStyle, gesture, null).vector);
    }

    public void addGesture(java.lang.String str, android.gesture.Gesture gesture) {
        if (str == null || str.length() == 0) {
            return;
        }
        java.util.ArrayList<android.gesture.Gesture> arrayList = this.mNamedGestures.get(str);
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
            this.mNamedGestures.put(str, arrayList);
        }
        arrayList.add(gesture);
        this.mClassifier.addInstance(android.gesture.Instance.createInstance(this.mSequenceType, this.mOrientationStyle, gesture, str));
        this.mChanged = true;
    }

    public void removeGesture(java.lang.String str, android.gesture.Gesture gesture) {
        java.util.ArrayList<android.gesture.Gesture> arrayList = this.mNamedGestures.get(str);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(gesture);
        if (arrayList.isEmpty()) {
            this.mNamedGestures.remove(str);
        }
        this.mClassifier.removeInstance(gesture.getID());
        this.mChanged = true;
    }

    public void removeEntry(java.lang.String str) {
        this.mNamedGestures.remove(str);
        this.mClassifier.removeInstances(str);
        this.mChanged = true;
    }

    public java.util.ArrayList<android.gesture.Gesture> getGestures(java.lang.String str) {
        java.util.ArrayList<android.gesture.Gesture> arrayList = this.mNamedGestures.get(str);
        if (arrayList != null) {
            return new java.util.ArrayList<>(arrayList);
        }
        return null;
    }

    public boolean hasChanged() {
        return this.mChanged;
    }

    public void save(java.io.OutputStream outputStream) throws java.io.IOException {
        save(outputStream, false);
    }

    public void save(java.io.OutputStream outputStream, boolean z) throws java.io.IOException {
        java.util.HashMap<java.lang.String, java.util.ArrayList<android.gesture.Gesture>> hashMap;
        java.io.DataOutputStream dataOutputStream;
        java.io.DataOutputStream dataOutputStream2 = null;
        try {
            hashMap = this.mNamedGestures;
            if (!(outputStream instanceof java.io.BufferedOutputStream)) {
                outputStream = new java.io.BufferedOutputStream(outputStream, 32768);
            }
            dataOutputStream = new java.io.DataOutputStream(outputStream);
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            dataOutputStream.writeShort(1);
            dataOutputStream.writeInt(hashMap.size());
            java.util.Iterator<java.util.Map.Entry<java.lang.String, java.util.ArrayList<android.gesture.Gesture>>> it = hashMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                java.util.Map.Entry<java.lang.String, java.util.ArrayList<android.gesture.Gesture>> next = it.next();
                java.lang.String key = next.getKey();
                java.util.ArrayList<android.gesture.Gesture> value = next.getValue();
                int size = value.size();
                dataOutputStream.writeUTF(key);
                dataOutputStream.writeInt(size);
                for (int i = 0; i < size; i++) {
                    value.get(i).serialize(dataOutputStream);
                }
            }
            dataOutputStream.flush();
            this.mChanged = false;
            if (z) {
                android.gesture.GestureUtils.closeStream(dataOutputStream);
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            dataOutputStream2 = dataOutputStream;
            if (z) {
                android.gesture.GestureUtils.closeStream(dataOutputStream2);
            }
            throw th;
        }
    }

    public void load(java.io.InputStream inputStream) throws java.io.IOException {
        load(inputStream, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void load(java.io.InputStream inputStream, boolean z) throws java.io.IOException {
        java.io.DataInputStream dataInputStream;
        java.io.DataInputStream dataInputStream2 = null;
        try {
            if (!(inputStream instanceof java.io.BufferedInputStream)) {
                inputStream = new java.io.BufferedInputStream(inputStream, 32768);
            }
            dataInputStream = new java.io.DataInputStream(inputStream);
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            switch (dataInputStream.readShort()) {
                case 1:
                    readFormatV1(dataInputStream);
                    if (!z) {
                        android.gesture.GestureUtils.closeStream(dataInputStream);
                        return;
                    }
                    return;
                default:
                    if (!z) {
                    }
                    break;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            dataInputStream2 = dataInputStream;
            if (z) {
                android.gesture.GestureUtils.closeStream(dataInputStream2);
            }
            throw th;
        }
    }

    private void readFormatV1(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        android.gesture.Learner learner = this.mClassifier;
        java.util.HashMap<java.lang.String, java.util.ArrayList<android.gesture.Gesture>> hashMap = this.mNamedGestures;
        hashMap.clear();
        int readInt = dataInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            java.lang.String readUTF = dataInputStream.readUTF();
            int readInt2 = dataInputStream.readInt();
            java.util.ArrayList<android.gesture.Gesture> arrayList = new java.util.ArrayList<>(readInt2);
            for (int i2 = 0; i2 < readInt2; i2++) {
                android.gesture.Gesture deserialize = android.gesture.Gesture.deserialize(dataInputStream);
                arrayList.add(deserialize);
                learner.addInstance(android.gesture.Instance.createInstance(this.mSequenceType, this.mOrientationStyle, deserialize, readUTF));
            }
            hashMap.put(readUTF, arrayList);
        }
    }

    android.gesture.Learner getLearner() {
        return this.mClassifier;
    }
}
