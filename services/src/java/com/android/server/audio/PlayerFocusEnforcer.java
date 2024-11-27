package com.android.server.audio;

/* loaded from: classes.dex */
public interface PlayerFocusEnforcer {
    boolean duckPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2, boolean z);

    boolean fadeOutPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2);

    void forgetUid(int i);

    long getFadeInDelayForOffendersMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes);

    long getFadeOutDurationMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes);

    void mutePlayersForCall(int[] iArr);

    void restoreVShapedPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester);

    boolean shouldEnforceFade();

    void unmutePlayersForCall();
}
