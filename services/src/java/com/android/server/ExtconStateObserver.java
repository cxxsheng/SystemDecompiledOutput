package com.android.server;

/* loaded from: classes.dex */
public abstract class ExtconStateObserver<S> extends com.android.server.ExtconUEventObserver {
    private static final boolean LOG = false;
    private static final java.lang.String TAG = "ExtconStateObserver";

    @android.annotation.Nullable
    public abstract S parseState(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, java.lang.String str);

    public abstract void updateState(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, java.lang.String str, @android.annotation.NonNull S s);

    @android.annotation.Nullable
    public S parseStateFromFile(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo) throws java.io.IOException {
        return parseState(extconInfo, android.os.FileUtils.readTextFile(new java.io.File(extconInfo.getStatePath()), 0, null).trim());
    }

    @Override // com.android.server.ExtconUEventObserver
    public void onUEvent(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, android.os.UEventObserver.UEvent uEvent) {
        java.lang.String str = uEvent.get("NAME");
        S parseState = parseState(extconInfo, uEvent.get("STATE"));
        if (parseState != null) {
            updateState(extconInfo, str, parseState);
        }
    }
}
