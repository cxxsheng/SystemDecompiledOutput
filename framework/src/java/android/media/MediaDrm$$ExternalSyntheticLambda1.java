package android.media;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaDrm$$ExternalSyntheticLambda1 implements java.util.function.Function {
    public final /* synthetic */ android.media.MediaDrm f$0;

    public /* synthetic */ MediaDrm$$ExternalSyntheticLambda1(android.media.MediaDrm mediaDrm) {
        this.f$0 = mediaDrm;
    }

    @Override // java.util.function.Function
    public final java.lang.Object apply(java.lang.Object obj) {
        java.util.function.Consumer createOnKeyStatusChangeListener;
        createOnKeyStatusChangeListener = this.f$0.createOnKeyStatusChangeListener((android.media.MediaDrm.OnKeyStatusChangeListener) obj);
        return createOnKeyStatusChangeListener;
    }
}
