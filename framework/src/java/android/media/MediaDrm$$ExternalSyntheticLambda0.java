package android.media;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaDrm$$ExternalSyntheticLambda0 implements java.util.function.Function {
    public final /* synthetic */ android.media.MediaDrm f$0;

    public /* synthetic */ MediaDrm$$ExternalSyntheticLambda0(android.media.MediaDrm mediaDrm) {
        this.f$0 = mediaDrm;
    }

    @Override // java.util.function.Function
    public final java.lang.Object apply(java.lang.Object obj) {
        java.util.function.Consumer createOnExpirationUpdateListener;
        createOnExpirationUpdateListener = this.f$0.createOnExpirationUpdateListener((android.media.MediaDrm.OnExpirationUpdateListener) obj);
        return createOnExpirationUpdateListener;
    }
}
