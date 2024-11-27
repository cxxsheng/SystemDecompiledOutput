package com.android.server.accessibility.magnification;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationThumbnail$$ExternalSyntheticLambda0 implements java.lang.Runnable {
    public final /* synthetic */ com.android.server.accessibility.magnification.MagnificationThumbnail f$0;

    public /* synthetic */ MagnificationThumbnail$$ExternalSyntheticLambda0(com.android.server.accessibility.magnification.MagnificationThumbnail magnificationThumbnail) {
        this.f$0 = magnificationThumbnail;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.hideThumbnailMainThread();
    }
}
