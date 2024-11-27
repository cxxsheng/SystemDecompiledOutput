package com.android.server.app;

/* loaded from: classes.dex */
final class GameServiceConfiguration {

    @android.annotation.Nullable
    private final com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration mGameServiceComponentConfiguration;
    private final java.lang.String mPackageName;

    GameServiceConfiguration(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration gameServiceComponentConfiguration) {
        java.util.Objects.requireNonNull(str);
        this.mPackageName = str;
        this.mGameServiceComponentConfiguration = gameServiceComponentConfiguration;
    }

    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @android.annotation.Nullable
    public com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration getGameServiceComponentConfiguration() {
        return this.mGameServiceComponentConfiguration;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.app.GameServiceConfiguration)) {
            return false;
        }
        com.android.server.app.GameServiceConfiguration gameServiceConfiguration = (com.android.server.app.GameServiceConfiguration) obj;
        return android.text.TextUtils.equals(this.mPackageName, gameServiceConfiguration.mPackageName) && java.util.Objects.equals(this.mGameServiceComponentConfiguration, gameServiceConfiguration.mGameServiceComponentConfiguration);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackageName, this.mGameServiceComponentConfiguration);
    }

    public java.lang.String toString() {
        return "GameServiceConfiguration{packageName=" + this.mPackageName + ", gameServiceComponentConfiguration=" + this.mGameServiceComponentConfiguration + '}';
    }

    static final class GameServiceComponentConfiguration {
        private final android.content.ComponentName mGameServiceComponentName;
        private final android.content.ComponentName mGameSessionServiceComponentName;
        private final android.os.UserHandle mUserHandle;

        GameServiceComponentConfiguration(@android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.content.ComponentName componentName2) {
            java.util.Objects.requireNonNull(userHandle);
            java.util.Objects.requireNonNull(componentName);
            java.util.Objects.requireNonNull(componentName2);
            this.mUserHandle = userHandle;
            this.mGameServiceComponentName = componentName;
            this.mGameSessionServiceComponentName = componentName2;
        }

        @android.annotation.NonNull
        public android.os.UserHandle getUserHandle() {
            return this.mUserHandle;
        }

        @android.annotation.NonNull
        public android.content.ComponentName getGameServiceComponentName() {
            return this.mGameServiceComponentName;
        }

        @android.annotation.NonNull
        public android.content.ComponentName getGameSessionServiceComponentName() {
            return this.mGameSessionServiceComponentName;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration)) {
                return false;
            }
            com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration gameServiceComponentConfiguration = (com.android.server.app.GameServiceConfiguration.GameServiceComponentConfiguration) obj;
            return this.mUserHandle.equals(gameServiceComponentConfiguration.mUserHandle) && this.mGameServiceComponentName.equals(gameServiceComponentConfiguration.mGameServiceComponentName) && this.mGameSessionServiceComponentName.equals(gameServiceComponentConfiguration.mGameSessionServiceComponentName);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mUserHandle, this.mGameServiceComponentName, this.mGameSessionServiceComponentName);
        }

        public java.lang.String toString() {
            return "GameServiceComponentConfiguration{userHandle=" + this.mUserHandle + ", gameServiceComponentName=" + this.mGameServiceComponentName + ", gameSessionServiceComponentName=" + this.mGameSessionServiceComponentName + "}";
        }
    }
}
