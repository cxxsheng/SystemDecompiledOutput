package com.android.server.app;

/* loaded from: classes.dex */
interface GameServiceProviderSelector {
    @android.annotation.Nullable
    com.android.server.app.GameServiceConfiguration get(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.Nullable java.lang.String str);
}
