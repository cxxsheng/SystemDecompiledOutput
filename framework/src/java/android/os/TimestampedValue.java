package android.os;

/* loaded from: classes3.dex */
public final class TimestampedValue<T> implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.TimestampedValue<?>> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.os.TimestampedValue<?>>() { // from class: android.os.TimestampedValue.1
        @Override // android.os.Parcelable.Creator
        public android.os.TimestampedValue<?> createFromParcel(android.os.Parcel parcel) {
            return createFromParcel(parcel, (java.lang.ClassLoader) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public android.os.TimestampedValue<?> createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            return new android.os.TimestampedValue<>(parcel.readLong(), parcel.readValue(classLoader));
        }

        @Override // android.os.Parcelable.Creator
        public android.os.TimestampedValue[] newArray(int i) {
            return new android.os.TimestampedValue[i];
        }
    };
    private final long mReferenceTimeMillis;
    private final T mValue;

    public TimestampedValue(long j, T t) {
        this.mReferenceTimeMillis = j;
        this.mValue = t;
    }

    public long getReferenceTimeMillis() {
        return this.mReferenceTimeMillis;
    }

    public T getValue() {
        return this.mValue;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.os.TimestampedValue timestampedValue = (android.os.TimestampedValue) obj;
        if (this.mReferenceTimeMillis == timestampedValue.mReferenceTimeMillis && java.util.Objects.equals(this.mValue, timestampedValue.mValue)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mReferenceTimeMillis), this.mValue);
    }

    public java.lang.String toString() {
        return "TimestampedValue{mReferenceTimeMillis=" + this.mReferenceTimeMillis + ", mValue=" + this.mValue + '}';
    }

    public static long referenceTimeDifference(android.os.TimestampedValue<?> timestampedValue, android.os.TimestampedValue<?> timestampedValue2) {
        return ((android.os.TimestampedValue) timestampedValue).mReferenceTimeMillis - ((android.os.TimestampedValue) timestampedValue2).mReferenceTimeMillis;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mReferenceTimeMillis);
        parcel.writeValue(this.mValue);
    }
}
