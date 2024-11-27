package com.android.server.wm;

/* loaded from: classes3.dex */
interface TransitionTracer {
    boolean isTracing();

    void logAbortedTransition(com.android.server.wm.Transition transition);

    void logFinishedTransition(com.android.server.wm.Transition transition);

    void logRemovingStartingWindow(@android.annotation.NonNull com.android.server.wm.StartingData startingData);

    void logSentTransition(com.android.server.wm.Transition transition, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList);

    void saveForBugreport(@android.annotation.Nullable java.io.PrintWriter printWriter);

    void startTrace(@android.annotation.Nullable java.io.PrintWriter printWriter);

    void stopTrace(@android.annotation.Nullable java.io.PrintWriter printWriter);
}
