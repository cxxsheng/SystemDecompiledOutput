package com.android.server.wm;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class SurfaceAnimationRunner$$ExternalSyntheticLambda1 implements android.view.Choreographer.FrameCallback {
    public final /* synthetic */ com.android.server.wm.SurfaceAnimationRunner f$0;

    public /* synthetic */ SurfaceAnimationRunner$$ExternalSyntheticLambda1(com.android.server.wm.SurfaceAnimationRunner surfaceAnimationRunner) {
        this.f$0 = surfaceAnimationRunner;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        this.f$0.startAnimations(j);
    }
}
