package android.hardware.soundtrigger;

/* loaded from: classes2.dex */
public class ConversionUtil {
    public static android.hardware.soundtrigger.SoundTrigger.ModuleProperties aidl2apiModuleDescriptor(android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor) {
        android.media.soundtrigger.Properties properties = soundTriggerModuleDescriptor.properties;
        return new android.hardware.soundtrigger.SoundTrigger.ModuleProperties(soundTriggerModuleDescriptor.handle, properties.implementor, properties.description, properties.uuid, properties.version, properties.supportedModelArch, properties.maxSoundModels, properties.maxKeyPhrases, properties.maxUsers, aidl2apiRecognitionModes(properties.recognitionModes), properties.captureTransition, properties.maxBufferMs, properties.concurrentCapture, properties.powerConsumptionMw, properties.triggerInEvent, aidl2apiAudioCapabilities(properties.audioCapabilities));
    }

    public static int aidl2apiRecognitionModes(int i) {
        int i2;
        if ((i & 1) == 0) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        if ((i & 8) != 0) {
            return i2 | 8;
        }
        return i2;
    }

    public static int api2aidlRecognitionModes(int i) {
        int i2;
        if ((i & 1) == 0) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        if ((i & 8) != 0) {
            return i2 | 8;
        }
        return i2;
    }

    public static android.media.soundtrigger.SoundModel api2aidlGenericSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) {
        return api2aidlSoundModel(genericSoundModel);
    }

    public static android.media.soundtrigger.SoundModel api2aidlSoundModel(android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel) {
        android.media.soundtrigger.SoundModel soundModel2 = new android.media.soundtrigger.SoundModel();
        soundModel2.type = soundModel.getType();
        soundModel2.uuid = api2aidlUuid(soundModel.getUuid());
        soundModel2.vendorUuid = api2aidlUuid(soundModel.getVendorUuid());
        byte[] data = soundModel.getData();
        soundModel2.data = byteArrayToSharedMemory(data, "SoundTrigger SoundModel");
        soundModel2.dataSize = data.length;
        return soundModel2;
    }

    public static java.lang.String api2aidlUuid(java.util.UUID uuid) {
        return uuid.toString();
    }

    public static android.media.soundtrigger.PhraseSoundModel api2aidlPhraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        android.media.soundtrigger.PhraseSoundModel phraseSoundModel = new android.media.soundtrigger.PhraseSoundModel();
        phraseSoundModel.common = api2aidlSoundModel(keyphraseSoundModel);
        phraseSoundModel.phrases = new android.media.soundtrigger.Phrase[keyphraseSoundModel.getKeyphrases().length];
        for (int i = 0; i < keyphraseSoundModel.getKeyphrases().length; i++) {
            phraseSoundModel.phrases[i] = api2aidlPhrase(keyphraseSoundModel.getKeyphrases()[i]);
        }
        return phraseSoundModel;
    }

    public static android.media.soundtrigger.Phrase api2aidlPhrase(android.hardware.soundtrigger.SoundTrigger.Keyphrase keyphrase) {
        android.media.soundtrigger.Phrase phrase = new android.media.soundtrigger.Phrase();
        phrase.id = keyphrase.getId();
        phrase.recognitionModes = api2aidlRecognitionModes(keyphrase.getRecognitionModes());
        phrase.users = java.util.Arrays.copyOf(keyphrase.getUsers(), keyphrase.getUsers().length);
        phrase.locale = keyphrase.getLocale().toLanguageTag();
        phrase.text = keyphrase.getText();
        return phrase;
    }

    public static android.hardware.soundtrigger.SoundTrigger.Keyphrase aidl2apiPhrase(android.media.soundtrigger.Phrase phrase) {
        return new android.hardware.soundtrigger.SoundTrigger.Keyphrase(phrase.id, aidl2apiRecognitionModes(phrase.recognitionModes), new java.util.Locale.Builder().setLanguageTag(phrase.locale).build(), phrase.text, java.util.Arrays.copyOf(phrase.users, phrase.users.length));
    }

    public static android.media.soundtrigger.RecognitionConfig api2aidlRecognitionConfig(android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) {
        android.media.soundtrigger.RecognitionConfig recognitionConfig2 = new android.media.soundtrigger.RecognitionConfig();
        recognitionConfig2.captureRequested = recognitionConfig.captureRequested;
        recognitionConfig2.phraseRecognitionExtras = new android.media.soundtrigger.PhraseRecognitionExtra[recognitionConfig.keyphrases.length];
        for (int i = 0; i < recognitionConfig.keyphrases.length; i++) {
            recognitionConfig2.phraseRecognitionExtras[i] = api2aidlPhraseRecognitionExtra(recognitionConfig.keyphrases[i]);
        }
        recognitionConfig2.data = java.util.Arrays.copyOf(recognitionConfig.data, recognitionConfig.data.length);
        recognitionConfig2.audioCapabilities = api2aidlAudioCapabilities(recognitionConfig.audioCapabilities);
        return recognitionConfig2;
    }

    public static android.hardware.soundtrigger.SoundTrigger.RecognitionConfig aidl2apiRecognitionConfig(android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr = new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[recognitionConfig.phraseRecognitionExtras.length];
        android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr = recognitionConfig.phraseRecognitionExtras;
        int length = phraseRecognitionExtraArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            keyphraseRecognitionExtraArr[i2] = aidl2apiPhraseRecognitionExtra(phraseRecognitionExtraArr[i]);
            i++;
            i2++;
        }
        return new android.hardware.soundtrigger.SoundTrigger.RecognitionConfig(recognitionConfig.captureRequested, false, keyphraseRecognitionExtraArr, java.util.Arrays.copyOf(recognitionConfig.data, recognitionConfig.data.length), aidl2apiAudioCapabilities(recognitionConfig.audioCapabilities));
    }

    public static android.media.soundtrigger.PhraseRecognitionExtra api2aidlPhraseRecognitionExtra(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra keyphraseRecognitionExtra) {
        android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra = new android.media.soundtrigger.PhraseRecognitionExtra();
        phraseRecognitionExtra.id = keyphraseRecognitionExtra.id;
        phraseRecognitionExtra.recognitionModes = api2aidlRecognitionModes(keyphraseRecognitionExtra.recognitionModes);
        phraseRecognitionExtra.confidenceLevel = keyphraseRecognitionExtra.coarseConfidenceLevel;
        phraseRecognitionExtra.levels = new android.media.soundtrigger.ConfidenceLevel[keyphraseRecognitionExtra.confidenceLevels.length];
        for (int i = 0; i < keyphraseRecognitionExtra.confidenceLevels.length; i++) {
            phraseRecognitionExtra.levels[i] = api2aidlConfidenceLevel(keyphraseRecognitionExtra.confidenceLevels[i]);
        }
        return phraseRecognitionExtra;
    }

    public static android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra aidl2apiPhraseRecognitionExtra(android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra) {
        android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[] confidenceLevelArr = new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[phraseRecognitionExtra.levels.length];
        for (int i = 0; i < phraseRecognitionExtra.levels.length; i++) {
            confidenceLevelArr[i] = aidl2apiConfidenceLevel(phraseRecognitionExtra.levels[i]);
        }
        return new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra(phraseRecognitionExtra.id, aidl2apiRecognitionModes(phraseRecognitionExtra.recognitionModes), phraseRecognitionExtra.confidenceLevel, confidenceLevelArr);
    }

    public static android.media.soundtrigger.ConfidenceLevel api2aidlConfidenceLevel(android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel confidenceLevel) {
        android.media.soundtrigger.ConfidenceLevel confidenceLevel2 = new android.media.soundtrigger.ConfidenceLevel();
        confidenceLevel2.levelPercent = confidenceLevel.confidenceLevel;
        confidenceLevel2.userId = confidenceLevel.userId;
        return confidenceLevel2;
    }

    public static android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel aidl2apiConfidenceLevel(android.media.soundtrigger.ConfidenceLevel confidenceLevel) {
        return new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel(confidenceLevel.userId, confidenceLevel.levelPercent);
    }

    public static android.hardware.soundtrigger.SoundTrigger.RecognitionEvent aidl2apiRecognitionEvent(int i, int i2, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys) {
        android.media.soundtrigger.RecognitionEvent recognitionEvent = recognitionEventSys.recognitionEvent;
        return new android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent(recognitionEvent.status, i, recognitionEvent.captureAvailable, i2, recognitionEvent.captureDelayMs, recognitionEvent.capturePreambleMs, recognitionEvent.triggerInData, aidl2apiAudioFormatWithDefault(recognitionEvent.audioConfig, true), recognitionEvent.data, recognitionEvent.recognitionStillActive, recognitionEventSys.halEventReceivedMillis, recognitionEventSys.token);
    }

    public static android.hardware.soundtrigger.SoundTrigger.RecognitionEvent aidl2apiPhraseRecognitionEvent(int i, int i2, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys) {
        android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent = phraseRecognitionEventSys.phraseRecognitionEvent;
        android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr = new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[phraseRecognitionEvent.phraseExtras.length];
        for (int i3 = 0; i3 < phraseRecognitionEvent.phraseExtras.length; i3++) {
            keyphraseRecognitionExtraArr[i3] = aidl2apiPhraseRecognitionExtra(phraseRecognitionEvent.phraseExtras[i3]);
        }
        return new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent(phraseRecognitionEvent.common.status, i, phraseRecognitionEvent.common.captureAvailable, i2, phraseRecognitionEvent.common.captureDelayMs, phraseRecognitionEvent.common.capturePreambleMs, phraseRecognitionEvent.common.triggerInData, aidl2apiAudioFormatWithDefault(phraseRecognitionEvent.common.audioConfig, true), phraseRecognitionEvent.common.data, keyphraseRecognitionExtraArr, phraseRecognitionEventSys.halEventReceivedMillis, phraseRecognitionEventSys.token);
    }

    public static android.media.AudioFormat aidl2apiAudioFormatWithDefault(android.media.audio.common.AudioConfig audioConfig, boolean z) {
        if (audioConfig != null) {
            return android.media.audio.common.AidlConversion.aidl2api_AudioConfig_AudioFormat(audioConfig, z);
        }
        return new android.media.AudioFormat.Builder().setSampleRate(48000).setEncoding(2).setChannelMask(16).build();
    }

    public static int api2aidlModelParameter(int i) {
        switch (i) {
            case 0:
                return 0;
            default:
                return -1;
        }
    }

    public static android.hardware.soundtrigger.SoundTrigger.ModelParamRange aidl2apiModelParameterRange(android.media.soundtrigger.ModelParameterRange modelParameterRange) {
        if (modelParameterRange == null) {
            return null;
        }
        return new android.hardware.soundtrigger.SoundTrigger.ModelParamRange(modelParameterRange.minInclusive, modelParameterRange.maxInclusive);
    }

    public static int aidl2apiAudioCapabilities(int i) {
        int i2;
        if ((i & 1) == 0) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        if ((i & 2) != 0) {
            return i2 | 2;
        }
        return i2;
    }

    public static int api2aidlAudioCapabilities(int i) {
        int i2;
        if ((i & 1) == 0) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        if ((i & 2) != 0) {
            return i2 | 2;
        }
        return i2;
    }

    public static android.os.ParcelFileDescriptor byteArrayToSharedMemory(byte[] bArr, java.lang.String str) {
        if (bArr.length == 0) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        try {
            android.os.SharedMemory create = android.os.SharedMemory.create(str, bArr.length);
            java.nio.ByteBuffer mapReadWrite = create.mapReadWrite();
            mapReadWrite.put(bArr);
            android.os.SharedMemory.unmap(mapReadWrite);
            android.os.ParcelFileDescriptor fdDup = create.getFdDup();
            create.close();
            return fdDup;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static byte[] sharedMemoryToByteArray(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
        if (parcelFileDescriptor == null || i == 0) {
            return new byte[0];
        }
        try {
            android.os.SharedMemory fromFileDescriptor = android.os.SharedMemory.fromFileDescriptor(parcelFileDescriptor);
            try {
                java.nio.ByteBuffer mapReadOnly = fromFileDescriptor.mapReadOnly();
                if (i > fromFileDescriptor.getSize()) {
                    i = fromFileDescriptor.getSize();
                }
                byte[] bArr = new byte[i];
                mapReadOnly.get(bArr);
                android.os.SharedMemory.unmap(mapReadOnly);
                if (fromFileDescriptor != null) {
                    fromFileDescriptor.close();
                }
                return bArr;
            } finally {
            }
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
