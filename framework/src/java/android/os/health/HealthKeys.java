package android.os.health;

/* loaded from: classes3.dex */
public class HealthKeys {
    public static final int BASE_PACKAGE = 40000;
    public static final int BASE_PID = 20000;
    public static final int BASE_PROCESS = 30000;
    public static final int BASE_SERVICE = 50000;
    public static final int BASE_UID = 10000;
    public static final int TYPE_COUNT = 5;
    public static final int TYPE_MEASUREMENT = 1;
    public static final int TYPE_MEASUREMENTS = 4;
    public static final int TYPE_STATS = 2;
    public static final int TYPE_TIMER = 0;
    public static final int TYPE_TIMERS = 3;
    public static final int UNKNOWN_KEY = 0;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface Constant {
        int type();
    }

    public static class Constants {
        private final java.lang.String mDataType;
        private final int[][] mKeys = new int[5][];

        public Constants(java.lang.Class cls) {
            this.mDataType = cls.getSimpleName();
            java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int length2 = this.mKeys.length;
            android.os.health.HealthKeys.SortedIntArray[] sortedIntArrayArr = new android.os.health.HealthKeys.SortedIntArray[length2];
            for (int i = 0; i < length2; i++) {
                sortedIntArrayArr[i] = new android.os.health.HealthKeys.SortedIntArray(length);
            }
            for (java.lang.reflect.Field field : declaredFields) {
                android.os.health.HealthKeys.Constant constant = (android.os.health.HealthKeys.Constant) field.getAnnotation(android.os.health.HealthKeys.Constant.class);
                if (constant != null) {
                    int type = constant.type();
                    if (type >= length2) {
                        throw new java.lang.RuntimeException("Unknown Constant type " + type + " on " + field);
                    }
                    try {
                        sortedIntArrayArr[type].addValue(field.getInt(null));
                    } catch (java.lang.IllegalAccessException e) {
                        throw new java.lang.RuntimeException("Can't read constant value type=" + type + " field=" + field, e);
                    }
                }
            }
            for (int i2 = 0; i2 < length2; i2++) {
                this.mKeys[i2] = sortedIntArrayArr[i2].getArray();
            }
        }

        public java.lang.String getDataType() {
            return this.mDataType;
        }

        public int getSize(int i) {
            return this.mKeys[i].length;
        }

        public int getIndex(int i, int i2) {
            int binarySearch = java.util.Arrays.binarySearch(this.mKeys[i], i2);
            if (binarySearch >= 0) {
                return binarySearch;
            }
            throw new java.lang.RuntimeException("Unknown Constant " + i2 + " (of type " + i + " )");
        }

        public int[] getKeys(int i) {
            return this.mKeys[i];
        }
    }

    private static class SortedIntArray {
        int[] mArray;
        int mCount;

        SortedIntArray(int i) {
            this.mArray = new int[i];
        }

        void addValue(int i) {
            int[] iArr = this.mArray;
            int i2 = this.mCount;
            this.mCount = i2 + 1;
            iArr[i2] = i;
        }

        int[] getArray() {
            if (this.mCount == this.mArray.length) {
                java.util.Arrays.sort(this.mArray);
                return this.mArray;
            }
            int[] iArr = new int[this.mCount];
            java.lang.System.arraycopy(this.mArray, 0, iArr, 0, this.mCount);
            java.util.Arrays.sort(iArr);
            return iArr;
        }
    }
}
