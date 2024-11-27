package com.android.server.app;

/* loaded from: classes.dex */
final class GameSessionRecord {

    @android.annotation.Nullable
    private final android.service.games.IGameSession mIGameSession;
    private final android.content.ComponentName mRootComponentName;
    private final com.android.server.app.GameSessionRecord.State mState;

    @android.annotation.Nullable
    private final android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    private final int mTaskId;

    private enum State {
        NO_GAME_SESSION_REQUESTED,
        GAME_SESSION_REQUESTED,
        GAME_SESSION_ATTACHED,
        GAME_SESSION_ENDED_PROCESS_DEATH
    }

    static com.android.server.app.GameSessionRecord awaitingGameSessionRequest(int i, android.content.ComponentName componentName) {
        return new com.android.server.app.GameSessionRecord(i, com.android.server.app.GameSessionRecord.State.NO_GAME_SESSION_REQUESTED, componentName, null, null);
    }

    private GameSessionRecord(int i, @android.annotation.NonNull com.android.server.app.GameSessionRecord.State state, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable android.service.games.IGameSession iGameSession, @android.annotation.Nullable android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        this.mTaskId = i;
        this.mState = state;
        this.mRootComponentName = componentName;
        this.mIGameSession = iGameSession;
        this.mSurfacePackage = surfacePackage;
    }

    public boolean isAwaitingGameSessionRequest() {
        return this.mState == com.android.server.app.GameSessionRecord.State.NO_GAME_SESSION_REQUESTED;
    }

    @android.annotation.NonNull
    public com.android.server.app.GameSessionRecord withGameSessionRequested() {
        return new com.android.server.app.GameSessionRecord(this.mTaskId, com.android.server.app.GameSessionRecord.State.GAME_SESSION_REQUESTED, this.mRootComponentName, null, null);
    }

    public boolean isGameSessionRequested() {
        return this.mState == com.android.server.app.GameSessionRecord.State.GAME_SESSION_REQUESTED;
    }

    @android.annotation.NonNull
    public com.android.server.app.GameSessionRecord withGameSession(@android.annotation.NonNull android.service.games.IGameSession iGameSession, @android.annotation.NonNull android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        java.util.Objects.requireNonNull(iGameSession);
        return new com.android.server.app.GameSessionRecord(this.mTaskId, com.android.server.app.GameSessionRecord.State.GAME_SESSION_ATTACHED, this.mRootComponentName, iGameSession, surfacePackage);
    }

    @android.annotation.NonNull
    public com.android.server.app.GameSessionRecord withGameSessionEndedOnProcessDeath() {
        return new com.android.server.app.GameSessionRecord(this.mTaskId, com.android.server.app.GameSessionRecord.State.GAME_SESSION_ENDED_PROCESS_DEATH, this.mRootComponentName, null, null);
    }

    public boolean isGameSessionEndedForProcessDeath() {
        return this.mState == com.android.server.app.GameSessionRecord.State.GAME_SESSION_ENDED_PROCESS_DEATH;
    }

    @android.annotation.NonNull
    public int getTaskId() {
        return this.mTaskId;
    }

    @android.annotation.NonNull
    public android.content.ComponentName getComponentName() {
        return this.mRootComponentName;
    }

    @android.annotation.Nullable
    public android.service.games.IGameSession getGameSession() {
        return this.mIGameSession;
    }

    @android.annotation.Nullable
    public android.view.SurfaceControlViewHost.SurfacePackage getSurfacePackage() {
        return this.mSurfacePackage;
    }

    public java.lang.String toString() {
        return "GameSessionRecord{mTaskId=" + this.mTaskId + ", mState=" + this.mState + ", mRootComponentName=" + this.mRootComponentName + ", mIGameSession=" + this.mIGameSession + ", mSurfacePackage=" + this.mSurfacePackage + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.app.GameSessionRecord)) {
            return false;
        }
        com.android.server.app.GameSessionRecord gameSessionRecord = (com.android.server.app.GameSessionRecord) obj;
        return this.mTaskId == gameSessionRecord.mTaskId && this.mState == gameSessionRecord.mState && this.mRootComponentName.equals(gameSessionRecord.mRootComponentName) && java.util.Objects.equals(this.mIGameSession, gameSessionRecord.mIGameSession) && java.util.Objects.equals(this.mSurfacePackage, gameSessionRecord.mSurfacePackage);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTaskId), this.mState, this.mRootComponentName, this.mIGameSession, this.mState, this.mSurfacePackage);
    }
}
