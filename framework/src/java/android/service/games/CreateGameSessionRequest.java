package android.service.games;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CreateGameSessionRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.games.CreateGameSessionRequest> CREATOR = new android.os.Parcelable.Creator<android.service.games.CreateGameSessionRequest>() { // from class: android.service.games.CreateGameSessionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.CreateGameSessionRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.games.CreateGameSessionRequest(parcel.readInt(), parcel.readString8());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.CreateGameSessionRequest[] newArray(int i) {
            return new android.service.games.CreateGameSessionRequest[0];
        }
    };
    private final java.lang.String mGamePackageName;
    private final int mTaskId;

    public CreateGameSessionRequest(int i, java.lang.String str) {
        this.mTaskId = i;
        this.mGamePackageName = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeString8(this.mGamePackageName);
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public java.lang.String getGamePackageName() {
        return this.mGamePackageName;
    }

    public java.lang.String toString() {
        return "GameSessionRequest{mTaskId=" + this.mTaskId + ", mGamePackageName='" + this.mGamePackageName + "'}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.service.games.CreateGameSessionRequest)) {
            return false;
        }
        android.service.games.CreateGameSessionRequest createGameSessionRequest = (android.service.games.CreateGameSessionRequest) obj;
        return this.mTaskId == createGameSessionRequest.mTaskId && java.util.Objects.equals(this.mGamePackageName, createGameSessionRequest.mGamePackageName);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTaskId), this.mGamePackageName);
    }
}
