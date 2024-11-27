package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class ValidationUtil {
    static void validateUuid(@android.annotation.Nullable java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (!com.android.server.soundtrigger_middleware.UuidUtil.PATTERN.matcher(str).matches()) {
            throw new java.lang.IllegalArgumentException("Illegal format for UUID: " + str);
        }
    }

    static void validateGenericModel(@android.annotation.Nullable android.media.soundtrigger.SoundModel soundModel) {
        validateModel(soundModel, 1);
    }

    static void validateModel(@android.annotation.Nullable android.media.soundtrigger.SoundModel soundModel, int i) {
        java.util.Objects.requireNonNull(soundModel);
        if (soundModel.type != i) {
            throw new java.lang.IllegalArgumentException("Invalid type");
        }
        validateUuid(soundModel.uuid);
        validateUuid(soundModel.vendorUuid);
        if (soundModel.dataSize > 0) {
            java.util.Objects.requireNonNull(soundModel.data);
        }
    }

    static void validatePhraseModel(@android.annotation.Nullable android.media.soundtrigger.PhraseSoundModel phraseSoundModel) {
        java.util.Objects.requireNonNull(phraseSoundModel);
        validateModel(phraseSoundModel.common, 0);
        java.util.Objects.requireNonNull(phraseSoundModel.phrases);
        for (android.media.soundtrigger.Phrase phrase : phraseSoundModel.phrases) {
            java.util.Objects.requireNonNull(phrase);
            if ((phrase.recognitionModes & (-16)) != 0) {
                throw new java.lang.IllegalArgumentException("Invalid recognitionModes");
            }
            java.util.Objects.requireNonNull(phrase.users);
            java.util.Objects.requireNonNull(phrase.locale);
            java.util.Objects.requireNonNull(phrase.text);
        }
    }

    static void validateRecognitionConfig(@android.annotation.Nullable android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        java.util.Objects.requireNonNull(recognitionConfig);
        java.util.Objects.requireNonNull(recognitionConfig.phraseRecognitionExtras);
        for (android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra : recognitionConfig.phraseRecognitionExtras) {
            java.util.Objects.requireNonNull(phraseRecognitionExtra);
            if ((phraseRecognitionExtra.recognitionModes & (-16)) != 0) {
                throw new java.lang.IllegalArgumentException("Invalid recognitionModes");
            }
            if (phraseRecognitionExtra.confidenceLevel < 0 || phraseRecognitionExtra.confidenceLevel > 100) {
                throw new java.lang.IllegalArgumentException("Invalid confidenceLevel");
            }
            java.util.Objects.requireNonNull(phraseRecognitionExtra.levels);
            for (android.media.soundtrigger.ConfidenceLevel confidenceLevel : phraseRecognitionExtra.levels) {
                java.util.Objects.requireNonNull(confidenceLevel);
                if (confidenceLevel.levelPercent < 0 || confidenceLevel.levelPercent > 100) {
                    throw new java.lang.IllegalArgumentException("Invalid confidenceLevel");
                }
            }
        }
        java.util.Objects.requireNonNull(recognitionConfig.data);
    }

    static void validateModelParameter(int i) {
        switch (i) {
            case 0:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Invalid model parameter");
        }
    }
}
