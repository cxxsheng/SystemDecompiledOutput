package com.android.server.musicrecognition;

/* loaded from: classes2.dex */
public class RemoteMusicRecognitionService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.musicrecognition.RemoteMusicRecognitionService, android.media.musicrecognition.IMusicRecognitionService> {
    private static final long TIMEOUT_IDLE_BIND_MILLIS = 40000;
    private final com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.MusicRecognitionServiceCallback mServerCallback;

    interface Callbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.musicrecognition.RemoteMusicRecognitionService> {
    }

    public RemoteMusicRecognitionService(android.content.Context context, android.content.ComponentName componentName, int i, com.android.server.musicrecognition.MusicRecognitionManagerPerUserService musicRecognitionManagerPerUserService, com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.MusicRecognitionServiceCallback musicRecognitionServiceCallback, boolean z, boolean z2) {
        super(context, "android.service.musicrecognition.MUSIC_RECOGNITION", componentName, i, musicRecognitionManagerPerUserService, context.getMainThreadHandler(), (z ? 4194304 : 0) | 4096, z2, 1);
        this.mServerCallback = musicRecognitionServiceCallback;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @android.annotation.NonNull
    /* renamed from: getServiceInterface, reason: merged with bridge method [inline-methods] */
    public android.media.musicrecognition.IMusicRecognitionService m5370getServiceInterface(@android.annotation.NonNull android.os.IBinder iBinder) {
        return android.media.musicrecognition.IMusicRecognitionService.Stub.asInterface(iBinder);
    }

    protected long getTimeoutIdleBindMillis() {
        return TIMEOUT_IDLE_BIND_MILLIS;
    }

    com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.MusicRecognitionServiceCallback getServerCallback() {
        return this.mServerCallback;
    }

    public void onAudioStreamStarted(@android.annotation.NonNull final android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull final android.media.AudioFormat audioFormat) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.musicrecognition.RemoteMusicRecognitionService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                com.android.server.musicrecognition.RemoteMusicRecognitionService.this.lambda$onAudioStreamStarted$0(parcelFileDescriptor, audioFormat, (android.media.musicrecognition.IMusicRecognitionService) iInterface);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAudioStreamStarted$0(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.media.musicrecognition.IMusicRecognitionService iMusicRecognitionService) throws android.os.RemoteException {
        iMusicRecognitionService.onAudioStreamStarted(parcelFileDescriptor, audioFormat, this.mServerCallback);
    }

    public java.util.concurrent.CompletableFuture<java.lang.String> getAttributionTag() {
        final java.util.concurrent.CompletableFuture<java.lang.String> completableFuture = new java.util.concurrent.CompletableFuture<>();
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.musicrecognition.RemoteMusicRecognitionService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                com.android.server.musicrecognition.RemoteMusicRecognitionService.this.lambda$getAttributionTag$1(completableFuture, (android.media.musicrecognition.IMusicRecognitionService) iInterface);
            }
        });
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAttributionTag$1(final java.util.concurrent.CompletableFuture completableFuture, android.media.musicrecognition.IMusicRecognitionService iMusicRecognitionService) throws android.os.RemoteException {
        iMusicRecognitionService.getAttributionTag(new android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.Stub() { // from class: com.android.server.musicrecognition.RemoteMusicRecognitionService.1
            public void onAttributionTag(java.lang.String str) throws android.os.RemoteException {
                completableFuture.complete(str);
            }
        });
    }
}
