package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class Hw2CompatUtil {
    Hw2CompatUtil() {
    }

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel convertSoundModel_2_1_to_2_0(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel) {
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel soundModel2 = soundModel.header;
        soundModel2.data = android.os.HidlMemoryUtil.hidlMemoryToByteList(soundModel.data);
        return soundModel2;
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent convertRecognitionEvent_2_0_to_2_1(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent) {
        android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent();
        recognitionEvent2.header = recognitionEvent;
        recognitionEvent2.data = android.os.HidlMemoryUtil.byteListToHidlMemory(recognitionEvent2.header.data, "SoundTrigger RecognitionEvent");
        recognitionEvent2.header.data = new java.util.ArrayList<>();
        return recognitionEvent2;
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent convertPhraseRecognitionEvent_2_0_to_2_1(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent) {
        android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent();
        phraseRecognitionEvent2.common = convertRecognitionEvent_2_0_to_2_1(phraseRecognitionEvent.common);
        phraseRecognitionEvent2.phraseExtras = phraseRecognitionEvent.phraseExtras;
        return phraseRecognitionEvent2;
    }

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel convertPhraseSoundModel_2_1_to_2_0(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel) {
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel phraseSoundModel2 = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel();
        phraseSoundModel2.common = convertSoundModel_2_1_to_2_0(phraseSoundModel.common);
        phraseSoundModel2.phrases = phraseSoundModel.phrases;
        return phraseSoundModel2;
    }

    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw.RecognitionConfig convertRecognitionConfig_2_3_to_2_1(android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig) {
        return recognitionConfig.base;
    }

    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig convertRecognitionConfig_2_3_to_2_0(android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig) {
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.RecognitionConfig recognitionConfig2 = recognitionConfig.base.header;
        recognitionConfig2.data = android.os.HidlMemoryUtil.hidlMemoryToByteList(recognitionConfig.base.data);
        return recognitionConfig2;
    }

    static android.hardware.soundtrigger.V2_3.Properties convertProperties_2_0_to_2_3(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties) {
        android.hardware.soundtrigger.V2_3.Properties properties2 = new android.hardware.soundtrigger.V2_3.Properties();
        properties2.base = properties;
        return properties2;
    }
}
