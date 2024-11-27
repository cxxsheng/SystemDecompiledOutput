package android.service.games;

/* loaded from: classes3.dex */
public final class GameSessionActivityResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.games.GameSessionActivityResult> CREATOR = new android.os.Parcelable.Creator<android.service.games.GameSessionActivityResult>() { // from class: android.service.games.GameSessionActivityResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameSessionActivityResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.games.GameSessionActivityResult(parcel.readInt(), (android.content.Intent) parcel.readParcelable(android.content.Intent.class.getClassLoader(), android.content.Intent.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameSessionActivityResult[] newArray(int i) {
            return new android.service.games.GameSessionActivityResult[i];
        }
    };
    private final android.content.Intent mData;
    private final int mResultCode;

    public GameSessionActivityResult(int i, android.content.Intent intent) {
        this.mResultCode = i;
        this.mData = intent;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public android.content.Intent getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeParcelable(this.mData, i);
    }
}
