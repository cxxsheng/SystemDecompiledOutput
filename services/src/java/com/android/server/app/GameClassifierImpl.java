package com.android.server.app;

/* loaded from: classes.dex */
final class GameClassifierImpl implements com.android.server.app.GameClassifier {
    private final android.content.pm.PackageManager mPackageManager;

    GameClassifierImpl(@android.annotation.NonNull android.content.pm.PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    @Override // com.android.server.app.GameClassifier
    public boolean isGame(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        try {
            return this.mPackageManager.getApplicationInfoAsUser(str, 0, userHandle.getIdentifier()).category == 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
