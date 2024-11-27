package android.service.games;

/* loaded from: classes3.dex */
public final class CreateGameSessionResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.games.CreateGameSessionResult> CREATOR = new android.os.Parcelable.Creator<android.service.games.CreateGameSessionResult>() { // from class: android.service.games.CreateGameSessionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.CreateGameSessionResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.games.CreateGameSessionResult(android.service.games.IGameSession.Stub.asInterface(parcel.readStrongBinder()), (android.view.SurfaceControlViewHost.SurfacePackage) parcel.readParcelable(android.view.SurfaceControlViewHost.SurfacePackage.class.getClassLoader(), android.view.SurfaceControlViewHost.SurfacePackage.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.CreateGameSessionResult[] newArray(int i) {
            return new android.service.games.CreateGameSessionResult[0];
        }
    };
    private final android.service.games.IGameSession mGameSession;
    private final android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;

    public CreateGameSessionResult(android.service.games.IGameSession iGameSession, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        this.mGameSession = iGameSession;
        this.mSurfacePackage = surfacePackage;
    }

    public android.service.games.IGameSession getGameSession() {
        return this.mGameSession;
    }

    public android.view.SurfaceControlViewHost.SurfacePackage getSurfacePackage() {
        return this.mSurfacePackage;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mGameSession.asBinder());
        parcel.writeParcelable(this.mSurfacePackage, i);
    }
}
