package com.android.server;

/* loaded from: classes5.dex */
public interface PowerAllowlistInternal {

    public interface TempAllowlistChangeListener {
        void onAppAdded(int i);

        void onAppRemoved(int i);
    }

    void registerTempAllowlistChangeListener(com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener);

    void unregisterTempAllowlistChangeListener(com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener);
}
