package com.android.internal.util;

/* loaded from: classes5.dex */
public interface HeavyHitterSketch<T> {
    void add(T t);

    java.util.List<T> getCandidates(java.util.List<T> list);

    float getRequiredValidationInputRatio();

    java.util.List<T> getTopHeavyHitters(int i, java.util.List<T> list, java.util.List<java.lang.Float> list2);

    void reset();

    void setConfig(int i, int i2);

    static <V> com.android.internal.util.HeavyHitterSketch<V> newDefault() {
        return new com.android.internal.util.HeavyHitterSketch.HeavyHitterSketchImpl();
    }

    public static final class HeavyHitterSketchImpl<T> implements com.android.internal.util.HeavyHitterSketch<T> {
        private int mCapacity;
        private boolean mConfigured;
        private int mNumInputs;
        private int mPassSize;
        private int mTotalSize;
        private final android.util.SparseArray<T> mObjects = new android.util.SparseArray<>();
        private final android.util.SparseIntArray mFrequencies = new android.util.SparseIntArray();

        @Override // com.android.internal.util.HeavyHitterSketch
        public void setConfig(int i, int i2) {
            if (i < i2 || i <= 1) {
                this.mConfigured = false;
                throw new java.lang.IllegalArgumentException();
            }
            reset();
            this.mTotalSize = i;
            this.mPassSize = i >> 1;
            this.mCapacity = i2;
            this.mConfigured = true;
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public void add(T t) {
            if (!this.mConfigured) {
                throw new java.lang.IllegalStateException();
            }
            if (this.mNumInputs < this.mPassSize) {
                addToMGSummary(t);
            } else if (this.mNumInputs < this.mTotalSize) {
                validate(t);
            }
        }

        private void addToMGSummary(T t) {
            int hashCode = t != null ? t.hashCode() : 0;
            int indexOfKey = this.mObjects.indexOfKey(hashCode);
            if (indexOfKey >= 0) {
                this.mFrequencies.setValueAt(indexOfKey, this.mFrequencies.valueAt(indexOfKey) + 1);
            } else if (this.mObjects.size() >= this.mCapacity - 1) {
                for (int size = this.mFrequencies.size() - 1; size >= 0; size--) {
                    int valueAt = this.mFrequencies.valueAt(size) - 1;
                    if (valueAt == 0) {
                        this.mObjects.removeAt(size);
                        this.mFrequencies.removeAt(size);
                    } else {
                        this.mFrequencies.setValueAt(size, valueAt);
                    }
                }
            } else {
                this.mObjects.put(hashCode, t);
                this.mFrequencies.put(hashCode, 1);
            }
            int i = this.mNumInputs + 1;
            this.mNumInputs = i;
            if (i == this.mPassSize) {
                for (int size2 = this.mFrequencies.size() - 1; size2 >= 0; size2--) {
                    this.mFrequencies.setValueAt(size2, 0);
                }
            }
        }

        private void validate(T t) {
            int indexOfKey = this.mObjects.indexOfKey(t != null ? t.hashCode() : 0);
            if (indexOfKey >= 0) {
                this.mFrequencies.setValueAt(indexOfKey, this.mFrequencies.valueAt(indexOfKey) + 1);
            }
            int i = this.mNumInputs + 1;
            this.mNumInputs = i;
            if (i == this.mTotalSize) {
                int i2 = this.mPassSize / this.mCapacity;
                for (int size = this.mFrequencies.size() - 1; size >= 0; size--) {
                    if (this.mFrequencies.valueAt(size) < i2) {
                        this.mFrequencies.removeAt(size);
                        this.mObjects.removeAt(size);
                    }
                }
            }
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public java.util.List<T> getTopHeavyHitters(int i, java.util.List<T> list, java.util.List<java.lang.Float> list2) {
            if (!this.mConfigured) {
                throw new java.lang.IllegalStateException();
            }
            if (i >= this.mCapacity) {
                throw new java.lang.IllegalArgumentException();
            }
            if (this.mNumInputs < this.mTotalSize) {
                throw new java.lang.IllegalStateException();
            }
            java.util.ArrayList arrayList = null;
            for (int size = this.mFrequencies.size() - 1; size >= 0; size--) {
                if (this.mFrequencies.valueAt(size) > 0) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(java.lang.Integer.valueOf(size));
                }
            }
            if (arrayList == null) {
                return null;
            }
            java.util.Collections.sort(arrayList, new java.util.Comparator() { // from class: com.android.internal.util.HeavyHitterSketch$HeavyHitterSketchImpl$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$getTopHeavyHitters$0;
                    lambda$getTopHeavyHitters$0 = com.android.internal.util.HeavyHitterSketch.HeavyHitterSketchImpl.this.lambda$getTopHeavyHitters$0((java.lang.Integer) obj, (java.lang.Integer) obj2);
                    return lambda$getTopHeavyHitters$0;
                }
            });
            if (list == null) {
                list = new java.util.ArrayList();
            }
            if (i == 0) {
                i = this.mCapacity - 1;
            }
            int min = java.lang.Math.min(i, arrayList.size());
            for (int i2 = 0; i2 < min; i2++) {
                T valueAt = this.mObjects.valueAt(((java.lang.Integer) arrayList.get(i2)).intValue());
                if (valueAt != null) {
                    list.add(valueAt);
                    if (list2 != null) {
                        list2.add(java.lang.Float.valueOf(this.mFrequencies.valueAt(r1) / this.mPassSize));
                    }
                }
            }
            return list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getTopHeavyHitters$0(java.lang.Integer num, java.lang.Integer num2) {
            return this.mFrequencies.valueAt(num2.intValue()) - this.mFrequencies.valueAt(num.intValue());
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public java.util.List<T> getCandidates(java.util.List<T> list) {
            if (!this.mConfigured) {
                throw new java.lang.IllegalStateException();
            }
            if (this.mNumInputs < this.mPassSize) {
                return null;
            }
            if (list == null) {
                list = new java.util.ArrayList();
            }
            for (int size = this.mObjects.size() - 1; size >= 0; size--) {
                T valueAt = this.mObjects.valueAt(size);
                if (valueAt != null) {
                    list.add(valueAt);
                }
            }
            return list;
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public void reset() {
            this.mNumInputs = 0;
            this.mObjects.clear();
            this.mFrequencies.clear();
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public float getRequiredValidationInputRatio() {
            return 0.5f;
        }
    }
}
