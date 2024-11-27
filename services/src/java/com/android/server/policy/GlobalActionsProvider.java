package com.android.server.policy;

/* loaded from: classes2.dex */
public interface GlobalActionsProvider {

    public interface GlobalActionsListener {
        void onGlobalActionsAvailableChanged(boolean z);

        void onGlobalActionsDismissed();

        void onGlobalActionsShown();
    }

    boolean isGlobalActionsDisabled();

    void setGlobalActionsListener(com.android.server.policy.GlobalActionsProvider.GlobalActionsListener globalActionsListener);

    void showGlobalActions();
}
