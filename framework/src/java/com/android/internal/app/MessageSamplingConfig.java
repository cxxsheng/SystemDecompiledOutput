package com.android.internal.app;

/* loaded from: classes4.dex */
public final class MessageSamplingConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.app.MessageSamplingConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.app.MessageSamplingConfig>() { // from class: com.android.internal.app.MessageSamplingConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.app.MessageSamplingConfig[] newArray(int i) {
            return new com.android.internal.app.MessageSamplingConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.app.MessageSamplingConfig createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.app.MessageSamplingConfig(parcel);
        }
    };
    private final int mAcceptableLeftDistance;
    private final long mExpirationTimeSinceBootMillis;
    private final int mSampledOpCode;

    public MessageSamplingConfig(int i, int i2, long j) {
        this.mSampledOpCode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mSampledOpCode, "from", -1L, "to", 147L);
        this.mAcceptableLeftDistance = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mAcceptableLeftDistance, "from", 0L, "to", 147L);
        this.mExpirationTimeSinceBootMillis = j;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mExpirationTimeSinceBootMillis, "from", 0L);
    }

    public int getSampledOpCode() {
        return this.mSampledOpCode;
    }

    public int getAcceptableLeftDistance() {
        return this.mAcceptableLeftDistance;
    }

    public long getExpirationTimeSinceBootMillis() {
        return this.mExpirationTimeSinceBootMillis;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSampledOpCode);
        parcel.writeInt(this.mAcceptableLeftDistance);
        parcel.writeLong(this.mExpirationTimeSinceBootMillis);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MessageSamplingConfig(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        long readLong = parcel.readLong();
        this.mSampledOpCode = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mSampledOpCode, "from", -1L, "to", 147L);
        this.mAcceptableLeftDistance = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mAcceptableLeftDistance, "from", 0L, "to", 147L);
        this.mExpirationTimeSinceBootMillis = readLong;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mExpirationTimeSinceBootMillis, "from", 0L);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
