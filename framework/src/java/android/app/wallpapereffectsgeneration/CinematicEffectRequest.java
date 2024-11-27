package android.app.wallpapereffectsgeneration;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CinematicEffectRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.CinematicEffectRequest> CREATOR = new android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.CinematicEffectRequest>() { // from class: android.app.wallpapereffectsgeneration.CinematicEffectRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.CinematicEffectRequest createFromParcel(android.os.Parcel parcel) {
            return new android.app.wallpapereffectsgeneration.CinematicEffectRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.CinematicEffectRequest[] newArray(int i) {
            return new android.app.wallpapereffectsgeneration.CinematicEffectRequest[i];
        }
    };
    private android.graphics.Bitmap mBitmap;
    private java.lang.String mTaskId;

    private CinematicEffectRequest(android.os.Parcel parcel) {
        this.mTaskId = parcel.readString();
        this.mBitmap = android.graphics.Bitmap.CREATOR.createFromParcel(parcel);
    }

    public CinematicEffectRequest(java.lang.String str, android.graphics.Bitmap bitmap) {
        this.mTaskId = str;
        this.mBitmap = bitmap;
    }

    public java.lang.String getTaskId() {
        return this.mTaskId;
    }

    public android.graphics.Bitmap getBitmap() {
        return this.mBitmap;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mTaskId.equals(((android.app.wallpapereffectsgeneration.CinematicEffectRequest) obj).mTaskId);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mTaskId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mTaskId);
        this.mBitmap.writeToParcel(parcel, i);
    }
}
