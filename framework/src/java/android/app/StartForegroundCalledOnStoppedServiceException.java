package android.app;

/* loaded from: classes.dex */
public final class StartForegroundCalledOnStoppedServiceException extends java.lang.IllegalStateException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.StartForegroundCalledOnStoppedServiceException> CREATOR = new android.os.Parcelable.Creator<android.app.StartForegroundCalledOnStoppedServiceException>() { // from class: android.app.StartForegroundCalledOnStoppedServiceException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.StartForegroundCalledOnStoppedServiceException createFromParcel(android.os.Parcel parcel) {
            return new android.app.StartForegroundCalledOnStoppedServiceException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.StartForegroundCalledOnStoppedServiceException[] newArray(int i) {
            return new android.app.StartForegroundCalledOnStoppedServiceException[i];
        }
    };

    public StartForegroundCalledOnStoppedServiceException(java.lang.String str) {
        super(str);
    }

    StartForegroundCalledOnStoppedServiceException(android.os.Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
