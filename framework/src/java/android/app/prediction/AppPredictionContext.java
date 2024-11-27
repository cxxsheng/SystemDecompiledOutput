package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppPredictionContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.prediction.AppPredictionContext> CREATOR = new android.os.Parcelable.Creator<android.app.prediction.AppPredictionContext>() { // from class: android.app.prediction.AppPredictionContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppPredictionContext createFromParcel(android.os.Parcel parcel) {
            return new android.app.prediction.AppPredictionContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppPredictionContext[] newArray(int i) {
            return new android.app.prediction.AppPredictionContext[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final java.lang.String mPackageName;
    private final int mPredictedTargetCount;
    private final java.lang.String mUiSurface;

    private AppPredictionContext(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) {
        this.mUiSurface = str;
        this.mPredictedTargetCount = i;
        this.mPackageName = str2;
        this.mExtras = bundle;
    }

    private AppPredictionContext(android.os.Parcel parcel) {
        this.mUiSurface = parcel.readString();
        this.mPredictedTargetCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public java.lang.String getUiSurface() {
        return this.mUiSurface;
    }

    public int getPredictedTargetCount() {
        return this.mPredictedTargetCount;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        android.app.prediction.AppPredictionContext appPredictionContext = (android.app.prediction.AppPredictionContext) obj;
        return this.mPredictedTargetCount == appPredictionContext.mPredictedTargetCount && this.mUiSurface.equals(appPredictionContext.mUiSurface) && this.mPackageName.equals(appPredictionContext.mPackageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mUiSurface);
        parcel.writeInt(this.mPredictedTargetCount);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.os.Bundle mExtras;
        private final java.lang.String mPackageName;
        private int mPredictedTargetCount;
        private java.lang.String mUiSurface;

        @android.annotation.SystemApi
        public Builder(android.content.Context context) {
            this.mPackageName = context.getPackageName();
        }

        public android.app.prediction.AppPredictionContext.Builder setPredictedTargetCount(int i) {
            this.mPredictedTargetCount = i;
            return this;
        }

        public android.app.prediction.AppPredictionContext.Builder setUiSurface(java.lang.String str) {
            this.mUiSurface = str;
            return this;
        }

        public android.app.prediction.AppPredictionContext.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.prediction.AppPredictionContext build() {
            return new android.app.prediction.AppPredictionContext(this.mUiSurface, this.mPredictedTargetCount, this.mPackageName, this.mExtras);
        }
    }
}
