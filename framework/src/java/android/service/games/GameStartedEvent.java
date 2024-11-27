package android.service.games;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class GameStartedEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.games.GameStartedEvent> CREATOR = new android.os.Parcelable.Creator<android.service.games.GameStartedEvent>() { // from class: android.service.games.GameStartedEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameStartedEvent createFromParcel(android.os.Parcel parcel) {
            return new android.service.games.GameStartedEvent(parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameStartedEvent[] newArray(int i) {
            return new android.service.games.GameStartedEvent[0];
        }
    };
    private final java.lang.String mPackageName;
    private final int mTaskId;

    public GameStartedEvent(int i, java.lang.String str) {
        this.mTaskId = i;
        this.mPackageName = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeString(this.mPackageName);
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String toString() {
        return "GameStartedEvent{mTaskId=" + this.mTaskId + ", mPackageName='" + this.mPackageName + "'}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.service.games.GameStartedEvent)) {
            return false;
        }
        android.service.games.GameStartedEvent gameStartedEvent = (android.service.games.GameStartedEvent) obj;
        return this.mTaskId == gameStartedEvent.mTaskId && java.util.Objects.equals(this.mPackageName, gameStartedEvent.mPackageName);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTaskId), this.mPackageName);
    }
}
