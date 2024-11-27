package android.service.voice;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class VoiceInteractionService$$ExternalSyntheticLambda0 implements java.util.function.Consumer {
    public final /* synthetic */ android.service.voice.VoiceInteractionService f$0;

    public /* synthetic */ VoiceInteractionService$$ExternalSyntheticLambda0(android.service.voice.VoiceInteractionService voiceInteractionService) {
        this.f$0 = voiceInteractionService;
    }

    @Override // java.util.function.Consumer
    public final void accept(java.lang.Object obj) {
        this.f$0.onHotwordDetectorDestroyed((android.service.voice.AbstractDetector) obj);
    }
}
