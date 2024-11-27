package android.media.voice;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class KeyphraseModelManager {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "KeyphraseModelManager";
    private final com.android.internal.app.IVoiceInteractionManagerService mVoiceInteractionManagerService;

    public KeyphraseModelManager(com.android.internal.app.IVoiceInteractionManagerService iVoiceInteractionManagerService) {
        this.mVoiceInteractionManagerService = iVoiceInteractionManagerService;
    }

    public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, java.util.Locale locale) {
        java.util.Objects.requireNonNull(locale);
        try {
            return this.mVoiceInteractionManagerService.getKeyphraseSoundModel(i, locale.toLanguageTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        java.util.Objects.requireNonNull(keyphraseSoundModel);
        try {
            int updateKeyphraseSoundModel = this.mVoiceInteractionManagerService.updateKeyphraseSoundModel(keyphraseSoundModel);
            if (updateKeyphraseSoundModel != 0) {
                throw new android.os.ServiceSpecificException(updateKeyphraseSoundModel);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteKeyphraseSoundModel(int i, java.util.Locale locale) {
        java.util.Objects.requireNonNull(locale);
        try {
            int deleteKeyphraseSoundModel = this.mVoiceInteractionManagerService.deleteKeyphraseSoundModel(i, locale.toLanguageTag());
            if (deleteKeyphraseSoundModel != 0) {
                throw new android.os.ServiceSpecificException(deleteKeyphraseSoundModel);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setModelDatabaseForTestEnabled(boolean z) {
        try {
            this.mVoiceInteractionManagerService.setModelDatabaseForTestEnabled(z, new android.os.Binder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
