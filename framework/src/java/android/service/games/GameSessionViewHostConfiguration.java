package android.service.games;

/* loaded from: classes3.dex */
public final class GameSessionViewHostConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.games.GameSessionViewHostConfiguration> CREATOR = new android.os.Parcelable.Creator<android.service.games.GameSessionViewHostConfiguration>() { // from class: android.service.games.GameSessionViewHostConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameSessionViewHostConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.service.games.GameSessionViewHostConfiguration(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameSessionViewHostConfiguration[] newArray(int i) {
            return new android.service.games.GameSessionViewHostConfiguration[0];
        }
    };
    final int mDisplayId;
    final int mHeightPx;
    final int mWidthPx;

    public GameSessionViewHostConfiguration(int i, int i2, int i3) {
        this.mDisplayId = i;
        this.mWidthPx = i2;
        this.mHeightPx = i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mWidthPx);
        parcel.writeInt(this.mHeightPx);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.service.games.GameSessionViewHostConfiguration)) {
            return false;
        }
        android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration = (android.service.games.GameSessionViewHostConfiguration) obj;
        return this.mDisplayId == gameSessionViewHostConfiguration.mDisplayId && this.mWidthPx == gameSessionViewHostConfiguration.mWidthPx && this.mHeightPx == gameSessionViewHostConfiguration.mHeightPx;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDisplayId), java.lang.Integer.valueOf(this.mWidthPx), java.lang.Integer.valueOf(this.mHeightPx));
    }

    public java.lang.String toString() {
        return "GameSessionViewHostConfiguration{mDisplayId=" + this.mDisplayId + ", mWidthPx=" + this.mWidthPx + ", mHeightPx=" + this.mHeightPx + '}';
    }
}
