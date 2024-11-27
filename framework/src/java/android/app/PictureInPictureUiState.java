package android.app;

/* loaded from: classes.dex */
public final class PictureInPictureUiState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.PictureInPictureUiState> CREATOR = new android.os.Parcelable.Creator<android.app.PictureInPictureUiState>() { // from class: android.app.PictureInPictureUiState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.PictureInPictureUiState createFromParcel(android.os.Parcel parcel) {
            return new android.app.PictureInPictureUiState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.PictureInPictureUiState[] newArray(int i) {
            return new android.app.PictureInPictureUiState[i];
        }
    };
    private final boolean mIsStashed;
    private final boolean mIsTransitioningToPip;

    PictureInPictureUiState(android.os.Parcel parcel) {
        this.mIsStashed = parcel.readBoolean();
        this.mIsTransitioningToPip = parcel.readBoolean();
    }

    public PictureInPictureUiState(boolean z) {
        this(z, false);
    }

    private PictureInPictureUiState(boolean z, boolean z2) {
        this.mIsStashed = z;
        this.mIsTransitioningToPip = z2;
    }

    public boolean isStashed() {
        return this.mIsStashed;
    }

    public boolean isTransitioningToPip() {
        return this.mIsTransitioningToPip;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.PictureInPictureUiState)) {
            return false;
        }
        android.app.PictureInPictureUiState pictureInPictureUiState = (android.app.PictureInPictureUiState) obj;
        return this.mIsStashed == pictureInPictureUiState.mIsStashed && this.mIsTransitioningToPip == pictureInPictureUiState.mIsTransitioningToPip;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsStashed), java.lang.Boolean.valueOf(this.mIsTransitioningToPip));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsStashed);
        parcel.writeBoolean(this.mIsTransitioningToPip);
    }

    public static final class Builder {
        private boolean mIsStashed;
        private boolean mIsTransitioningToPip;

        public android.app.PictureInPictureUiState.Builder setStashed(boolean z) {
            this.mIsStashed = z;
            return this;
        }

        public android.app.PictureInPictureUiState.Builder setTransitioningToPip(boolean z) {
            this.mIsTransitioningToPip = z;
            return this;
        }

        public android.app.PictureInPictureUiState build() {
            return new android.app.PictureInPictureUiState(this.mIsStashed, this.mIsTransitioningToPip);
        }
    }
}
