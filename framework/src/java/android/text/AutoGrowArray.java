package android.text;

/* loaded from: classes3.dex */
public final class AutoGrowArray {
    private static final int MAX_CAPACITY_TO_BE_KEPT = 10000;
    private static final int MIN_CAPACITY_INCREMENT = 12;

    /* JADX INFO: Access modifiers changed from: private */
    public static int computeNewCapacity(int i, int i2) {
        int i3 = i + (i < 6 ? 12 : i >> 1);
        return i3 > i2 ? i3 : i2;
    }

    public static class ByteArray {
        private int mSize;
        private byte[] mValues;

        public ByteArray() {
            this(10);
        }

        public ByteArray(int i) {
            if (i == 0) {
                this.mValues = libcore.util.EmptyArray.BYTE;
            } else {
                this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedByteArray(i);
            }
            this.mSize = 0;
        }

        public void resize(int i) {
            if (i > this.mValues.length) {
                ensureCapacity(i - this.mSize);
            }
            this.mSize = i;
        }

        public void append(byte b) {
            ensureCapacity(1);
            byte[] bArr = this.mValues;
            int i = this.mSize;
            this.mSize = i + 1;
            bArr[i] = b;
        }

        private void ensureCapacity(int i) {
            int i2 = this.mSize + i;
            if (i2 >= this.mValues.length) {
                byte[] newUnpaddedByteArray = com.android.internal.util.ArrayUtils.newUnpaddedByteArray(android.text.AutoGrowArray.computeNewCapacity(this.mSize, i2));
                java.lang.System.arraycopy(this.mValues, 0, newUnpaddedByteArray, 0, this.mSize);
                this.mValues = newUnpaddedByteArray;
            }
        }

        public void clear() {
            this.mSize = 0;
        }

        public void clearWithReleasingLargeArray() {
            clear();
            if (this.mValues.length > 10000) {
                this.mValues = libcore.util.EmptyArray.BYTE;
            }
        }

        public byte get(int i) {
            return this.mValues[i];
        }

        public void set(int i, byte b) {
            this.mValues[i] = b;
        }

        public int size() {
            return this.mSize;
        }

        public byte[] getRawArray() {
            return this.mValues;
        }
    }

    public static class IntArray {
        private int mSize;
        private int[] mValues;

        public IntArray() {
            this(10);
        }

        public IntArray(int i) {
            if (i == 0) {
                this.mValues = libcore.util.EmptyArray.INT;
            } else {
                this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(i);
            }
            this.mSize = 0;
        }

        public void resize(int i) {
            if (i > this.mValues.length) {
                ensureCapacity(i - this.mSize);
            }
            this.mSize = i;
        }

        public void append(int i) {
            ensureCapacity(1);
            int[] iArr = this.mValues;
            int i2 = this.mSize;
            this.mSize = i2 + 1;
            iArr[i2] = i;
        }

        private void ensureCapacity(int i) {
            int i2 = this.mSize + i;
            if (i2 >= this.mValues.length) {
                int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(android.text.AutoGrowArray.computeNewCapacity(this.mSize, i2));
                java.lang.System.arraycopy(this.mValues, 0, newUnpaddedIntArray, 0, this.mSize);
                this.mValues = newUnpaddedIntArray;
            }
        }

        public void clear() {
            this.mSize = 0;
        }

        public void clearWithReleasingLargeArray() {
            clear();
            if (this.mValues.length > 10000) {
                this.mValues = libcore.util.EmptyArray.INT;
            }
        }

        public int get(int i) {
            return this.mValues[i];
        }

        public void set(int i, int i2) {
            this.mValues[i] = i2;
        }

        public int size() {
            return this.mSize;
        }

        public int[] getRawArray() {
            return this.mValues;
        }
    }

    public static class FloatArray {
        private int mSize;
        private float[] mValues;

        public FloatArray() {
            this(10);
        }

        public FloatArray(int i) {
            if (i == 0) {
                this.mValues = libcore.util.EmptyArray.FLOAT;
            } else {
                this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedFloatArray(i);
            }
            this.mSize = 0;
        }

        public void resize(int i) {
            if (i > this.mValues.length) {
                ensureCapacity(i - this.mSize);
            }
            this.mSize = i;
        }

        public void append(float f) {
            ensureCapacity(1);
            float[] fArr = this.mValues;
            int i = this.mSize;
            this.mSize = i + 1;
            fArr[i] = f;
        }

        private void ensureCapacity(int i) {
            int i2 = this.mSize + i;
            if (i2 >= this.mValues.length) {
                float[] newUnpaddedFloatArray = com.android.internal.util.ArrayUtils.newUnpaddedFloatArray(android.text.AutoGrowArray.computeNewCapacity(this.mSize, i2));
                java.lang.System.arraycopy(this.mValues, 0, newUnpaddedFloatArray, 0, this.mSize);
                this.mValues = newUnpaddedFloatArray;
            }
        }

        public void clear() {
            this.mSize = 0;
        }

        public void clearWithReleasingLargeArray() {
            clear();
            if (this.mValues.length > 10000) {
                this.mValues = libcore.util.EmptyArray.FLOAT;
            }
        }

        public float get(int i) {
            return this.mValues[i];
        }

        public void set(int i, float f) {
            this.mValues[i] = f;
        }

        public int size() {
            return this.mSize;
        }

        public float[] getRawArray() {
            return this.mValues;
        }
    }
}
