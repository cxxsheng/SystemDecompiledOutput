package android.view;

/* loaded from: classes4.dex */
public interface InsetsAnimationControlRunner {
    void cancel();

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j);

    android.view.WindowInsetsAnimation getAnimation();

    int getAnimationType();

    int getControllingTypes();

    android.view.inputmethod.ImeTracker.Token getStatsToken();

    int getTypes();

    void notifyControlRevoked(int i);

    void updateSurfacePosition(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray);

    default boolean controlsType(int i) {
        return (i & getTypes()) != 0;
    }
}
