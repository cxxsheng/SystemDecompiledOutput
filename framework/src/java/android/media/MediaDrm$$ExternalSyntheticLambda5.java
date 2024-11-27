package android.media;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaDrm$$ExternalSyntheticLambda5 implements java.util.function.Function {
    public final /* synthetic */ android.media.MediaDrm f$0;

    public /* synthetic */ MediaDrm$$ExternalSyntheticLambda5(android.media.MediaDrm mediaDrm) {
        this.f$0 = mediaDrm;
    }

    @Override // java.util.function.Function
    public final java.lang.Object apply(java.lang.Object obj) {
        java.util.function.Consumer createOnSessionLostStateListener;
        createOnSessionLostStateListener = this.f$0.createOnSessionLostStateListener((android.media.MediaDrm.OnSessionLostStateListener) obj);
        return createOnSessionLostStateListener;
    }
}
