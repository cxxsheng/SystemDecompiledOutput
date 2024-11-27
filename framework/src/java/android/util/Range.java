package android.util;

/* loaded from: classes3.dex */
public final class Range<T extends java.lang.Comparable<? super T>> {
    private final T mLower;
    private final T mUpper;

    public Range(T t, T t2) {
        this.mLower = (T) com.android.internal.util.Preconditions.checkNotNull(t, "lower must not be null");
        this.mUpper = (T) com.android.internal.util.Preconditions.checkNotNull(t2, "upper must not be null");
        if (t.compareTo(t2) > 0) {
            throw new java.lang.IllegalArgumentException("lower must be less than or equal to upper");
        }
    }

    public static <T extends java.lang.Comparable<? super T>> android.util.Range<T> create(T t, T t2) {
        return new android.util.Range<>(t, t2);
    }

    public T getLower() {
        return this.mLower;
    }

    public T getUpper() {
        return this.mUpper;
    }

    public boolean contains(T t) {
        com.android.internal.util.Preconditions.checkNotNull(t, "value must not be null");
        return (t.compareTo(this.mLower) >= 0) && (t.compareTo(this.mUpper) <= 0);
    }

    public boolean contains(android.util.Range<T> range) {
        com.android.internal.util.Preconditions.checkNotNull(range, "value must not be null");
        return (range.mLower.compareTo(this.mLower) >= 0) && (range.mUpper.compareTo(this.mUpper) <= 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.util.Range)) {
            return false;
        }
        android.util.Range range = (android.util.Range) obj;
        if (!this.mLower.equals(range.mLower) || !this.mUpper.equals(range.mUpper)) {
            return false;
        }
        return true;
    }

    public T clamp(T t) {
        com.android.internal.util.Preconditions.checkNotNull(t, "value must not be null");
        if (t.compareTo(this.mLower) < 0) {
            return this.mLower;
        }
        if (t.compareTo(this.mUpper) > 0) {
            return this.mUpper;
        }
        return t;
    }

    public android.util.Range<T> intersect(android.util.Range<T> range) {
        com.android.internal.util.Preconditions.checkNotNull(range, "range must not be null");
        int compareTo = range.mLower.compareTo(this.mLower);
        int compareTo2 = range.mUpper.compareTo(this.mUpper);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return this;
        }
        if (compareTo >= 0 && compareTo2 <= 0) {
            return range;
        }
        return create(compareTo <= 0 ? this.mLower : range.mLower, compareTo2 >= 0 ? this.mUpper : range.mUpper);
    }

    public android.util.Range<T> intersect(T t, T t2) {
        com.android.internal.util.Preconditions.checkNotNull(t, "lower must not be null");
        com.android.internal.util.Preconditions.checkNotNull(t2, "upper must not be null");
        int compareTo = t.compareTo(this.mLower);
        int compareTo2 = t2.compareTo(this.mUpper);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return this;
        }
        if (compareTo <= 0) {
            t = this.mLower;
        }
        if (compareTo2 >= 0) {
            t2 = this.mUpper;
        }
        return create(t, t2);
    }

    public android.util.Range<T> extend(android.util.Range<T> range) {
        com.android.internal.util.Preconditions.checkNotNull(range, "range must not be null");
        int compareTo = range.mLower.compareTo(this.mLower);
        int compareTo2 = range.mUpper.compareTo(this.mUpper);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return range;
        }
        if (compareTo >= 0 && compareTo2 <= 0) {
            return this;
        }
        return create(compareTo >= 0 ? this.mLower : range.mLower, compareTo2 <= 0 ? this.mUpper : range.mUpper);
    }

    public android.util.Range<T> extend(T t, T t2) {
        com.android.internal.util.Preconditions.checkNotNull(t, "lower must not be null");
        com.android.internal.util.Preconditions.checkNotNull(t2, "upper must not be null");
        int compareTo = t.compareTo(this.mLower);
        int compareTo2 = t2.compareTo(this.mUpper);
        if (compareTo >= 0 && compareTo2 <= 0) {
            return this;
        }
        if (compareTo >= 0) {
            t = this.mLower;
        }
        if (compareTo2 <= 0) {
            t2 = this.mUpper;
        }
        return create(t, t2);
    }

    public android.util.Range<T> extend(T t) {
        com.android.internal.util.Preconditions.checkNotNull(t, "value must not be null");
        return extend(t, t);
    }

    public java.lang.String toString() {
        return java.lang.String.format("[%s, %s]", this.mLower, this.mUpper);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mLower, this.mUpper);
    }
}
