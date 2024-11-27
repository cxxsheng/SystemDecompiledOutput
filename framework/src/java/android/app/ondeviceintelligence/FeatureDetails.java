package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class FeatureDetails implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ondeviceintelligence.FeatureDetails> CREATOR = new android.os.Parcelable.Creator<android.app.ondeviceintelligence.FeatureDetails>() { // from class: android.app.ondeviceintelligence.FeatureDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.FeatureDetails[] newArray(int i) {
            return new android.app.ondeviceintelligence.FeatureDetails[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.FeatureDetails createFromParcel(android.os.Parcel parcel) {
            return new android.app.ondeviceintelligence.FeatureDetails(parcel);
        }
    };
    public static final int FEATURE_STATUS_AVAILABLE = 3;
    public static final int FEATURE_STATUS_DOWNLOADABLE = 1;
    public static final int FEATURE_STATUS_DOWNLOADING = 2;
    public static final int FEATURE_STATUS_SERVICE_UNAVAILABLE = 4;
    public static final int FEATURE_STATUS_UNAVAILABLE = 0;
    private final android.os.PersistableBundle mFeatureDetailParams;
    private final int mStatus;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public FeatureDetails(int i, android.os.PersistableBundle persistableBundle) {
        this.mStatus = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ondeviceintelligence.FeatureDetails.Status.class, (java.lang.annotation.Annotation) null, this.mStatus);
        this.mFeatureDetailParams = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFeatureDetailParams);
    }

    public FeatureDetails(int i) {
        this.mStatus = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ondeviceintelligence.FeatureDetails.Status.class, (java.lang.annotation.Annotation) null, this.mStatus);
        this.mFeatureDetailParams = new android.os.PersistableBundle();
    }

    public int getStatus() {
        return this.mStatus;
    }

    public android.os.PersistableBundle getFeatureDetailParams() {
        return this.mFeatureDetailParams;
    }

    public java.lang.String toString() {
        return java.text.MessageFormat.format("FeatureDetails '{' status = {0}, persistableBundle = {1} '}'", java.lang.Integer.valueOf(this.mStatus), this.mFeatureDetailParams);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.ondeviceintelligence.FeatureDetails featureDetails = (android.app.ondeviceintelligence.FeatureDetails) obj;
        if (this.mStatus == featureDetails.mStatus && java.util.Objects.equals(this.mFeatureDetailParams, featureDetails.mFeatureDetailParams)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mStatus + 31) * 31) + java.util.Objects.hashCode(this.mFeatureDetailParams);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeTypedObject(this.mFeatureDetailParams, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FeatureDetails(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
        this.mStatus = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ondeviceintelligence.FeatureDetails.Status.class, (java.lang.annotation.Annotation) null, this.mStatus);
        this.mFeatureDetailParams = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFeatureDetailParams);
    }
}
