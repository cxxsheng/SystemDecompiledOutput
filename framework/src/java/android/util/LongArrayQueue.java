package android.util;

/* loaded from: classes3.dex */
public class LongArrayQueue {
    private int mHead;
    private int mSize;
    private int mTail;
    private long[] mValues;

    public LongArrayQueue(int i) {
        if (i == 0) {
            this.mValues = android.util.EmptyArray.LONG;
        } else {
            this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(i);
        }
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public LongArrayQueue() {
        this(16);
    }

    private void grow() {
        if (this.mSize < this.mValues.length) {
            throw new java.lang.IllegalStateException("Queue not full yet!");
        }
        long[] newUnpaddedLongArray = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(com.android.internal.util.GrowingArrayUtils.growSize(this.mSize));
        int length = this.mValues.length - this.mHead;
        java.lang.System.arraycopy(this.mValues, this.mHead, newUnpaddedLongArray, 0, length);
        java.lang.System.arraycopy(this.mValues, 0, newUnpaddedLongArray, length, this.mHead);
        this.mValues = newUnpaddedLongArray;
        this.mHead = 0;
        this.mTail = this.mSize;
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public void addLast(long j) {
        if (this.mSize == this.mValues.length) {
            grow();
        }
        this.mValues[this.mTail] = j;
        this.mTail = (this.mTail + 1) % this.mValues.length;
        this.mSize++;
    }

    public long removeFirst() {
        if (this.mSize == 0) {
            throw new java.util.NoSuchElementException("Queue is empty!");
        }
        long j = this.mValues[this.mHead];
        this.mHead = (this.mHead + 1) % this.mValues.length;
        this.mSize--;
        return j;
    }

    public long get(int i) {
        if (i < 0 || i >= this.mSize) {
            throw new java.lang.IndexOutOfBoundsException("Index " + i + " not valid for a queue of size " + this.mSize);
        }
        return this.mValues[(this.mHead + i) % this.mValues.length];
    }

    public long peekFirst() {
        if (this.mSize == 0) {
            throw new java.util.NoSuchElementException("Queue is empty!");
        }
        return this.mValues[this.mHead];
    }

    public long peekLast() {
        if (this.mSize == 0) {
            throw new java.util.NoSuchElementException("Queue is empty!");
        }
        return this.mValues[(this.mTail == 0 ? this.mValues.length : this.mTail) - 1];
    }

    public java.lang.String toString() {
        if (this.mSize <= 0) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mSize * 64);
        sb.append('{');
        sb.append(get(0));
        for (int i = 1; i < this.mSize; i++) {
            sb.append(", ");
            sb.append(get(i));
        }
        sb.append('}');
        return sb.toString();
    }
}
