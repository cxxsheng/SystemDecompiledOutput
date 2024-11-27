package android.service.rotationresolver;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RotationResolutionRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.rotationresolver.RotationResolutionRequest> CREATOR = new android.os.Parcelable.Creator<android.service.rotationresolver.RotationResolutionRequest>() { // from class: android.service.rotationresolver.RotationResolutionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.rotationresolver.RotationResolutionRequest[] newArray(int i) {
            return new android.service.rotationresolver.RotationResolutionRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.rotationresolver.RotationResolutionRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.rotationresolver.RotationResolutionRequest(parcel);
        }
    };
    private final int mCurrentRotation;
    private final java.lang.String mForegroundPackageName;
    private final int mProposedRotation;
    private final boolean mShouldUseCamera;
    private final long mTimeoutMillis;

    public RotationResolutionRequest(java.lang.String str, int i, int i2, boolean z, long j) {
        this.mForegroundPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mForegroundPackageName);
        this.mCurrentRotation = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.Surface.Rotation.class, (java.lang.annotation.Annotation) null, this.mCurrentRotation);
        this.mProposedRotation = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.Surface.Rotation.class, (java.lang.annotation.Annotation) null, this.mProposedRotation);
        this.mShouldUseCamera = z;
        this.mTimeoutMillis = j;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.DurationMillisLong.class, (java.lang.annotation.Annotation) null, this.mTimeoutMillis);
    }

    public java.lang.String getForegroundPackageName() {
        return this.mForegroundPackageName;
    }

    public int getCurrentRotation() {
        return this.mCurrentRotation;
    }

    public int getProposedRotation() {
        return this.mProposedRotation;
    }

    public boolean shouldUseCamera() {
        return this.mShouldUseCamera;
    }

    public long getTimeoutMillis() {
        return this.mTimeoutMillis;
    }

    public java.lang.String toString() {
        return "RotationResolutionRequest { foregroundPackageName = " + this.mForegroundPackageName + ", currentRotation = " + this.mCurrentRotation + ", proposedRotation = " + this.mProposedRotation + ", shouldUseCamera = " + this.mShouldUseCamera + ", timeoutMillis = " + this.mTimeoutMillis + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mShouldUseCamera ? (byte) 8 : (byte) 0);
        parcel.writeString(this.mForegroundPackageName);
        parcel.writeInt(this.mCurrentRotation);
        parcel.writeInt(this.mProposedRotation);
        parcel.writeLong(this.mTimeoutMillis);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RotationResolutionRequest(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        java.lang.String readString = parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        long readLong = parcel.readLong();
        this.mForegroundPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mForegroundPackageName);
        this.mCurrentRotation = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.Surface.Rotation.class, (java.lang.annotation.Annotation) null, this.mCurrentRotation);
        this.mProposedRotation = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.Surface.Rotation.class, (java.lang.annotation.Annotation) null, this.mProposedRotation);
        this.mShouldUseCamera = z;
        this.mTimeoutMillis = readLong;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.DurationMillisLong.class, (java.lang.annotation.Annotation) null, this.mTimeoutMillis);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
