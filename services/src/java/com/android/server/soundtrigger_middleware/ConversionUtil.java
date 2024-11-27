package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class ConversionUtil {
    ConversionUtil() {
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.Properties hidl2aidlProperties(@android.annotation.NonNull android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties) {
        android.media.soundtrigger.Properties properties2 = new android.media.soundtrigger.Properties();
        properties2.implementor = properties.implementor;
        properties2.description = properties.description;
        properties2.version = properties.version;
        properties2.uuid = hidl2aidlUuid(properties.uuid);
        properties2.maxSoundModels = properties.maxSoundModels;
        properties2.maxKeyPhrases = properties.maxKeyPhrases;
        properties2.maxUsers = properties.maxUsers;
        properties2.recognitionModes = hidl2aidlRecognitionModes(properties.recognitionModes);
        properties2.captureTransition = properties.captureTransition;
        properties2.maxBufferMs = properties.maxBufferMs;
        properties2.concurrentCapture = properties.concurrentCapture;
        properties2.triggerInEvent = properties.triggerInEvent;
        properties2.powerConsumptionMw = properties.powerConsumptionMw;
        return properties2;
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.Properties hidl2aidlProperties(@android.annotation.NonNull android.hardware.soundtrigger.V2_3.Properties properties) {
        android.media.soundtrigger.Properties hidl2aidlProperties = hidl2aidlProperties(properties.base);
        hidl2aidlProperties.supportedModelArch = properties.supportedModelArch;
        hidl2aidlProperties.audioCapabilities = hidl2aidlAudioCapabilities(properties.audioCapabilities);
        return hidl2aidlProperties;
    }

    @android.annotation.NonNull
    static java.lang.String hidl2aidlUuid(@android.annotation.NonNull android.hardware.audio.common.V2_0.Uuid uuid) {
        if (uuid.node == null || uuid.node.length != 6) {
            throw new java.lang.IllegalArgumentException("UUID.node must be of length 6.");
        }
        return java.lang.String.format("%08x-%04x-%04x-%04x-%02x%02x%02x%02x%02x%02x", java.lang.Integer.valueOf(uuid.timeLow), java.lang.Short.valueOf(uuid.timeMid), java.lang.Short.valueOf(uuid.versionAndTimeHigh), java.lang.Short.valueOf(uuid.variantAndClockSeqHigh), java.lang.Byte.valueOf(uuid.node[0]), java.lang.Byte.valueOf(uuid.node[1]), java.lang.Byte.valueOf(uuid.node[2]), java.lang.Byte.valueOf(uuid.node[3]), java.lang.Byte.valueOf(uuid.node[4]), java.lang.Byte.valueOf(uuid.node[5]));
    }

    @android.annotation.NonNull
    static android.hardware.audio.common.V2_0.Uuid aidl2hidlUuid(@android.annotation.NonNull java.lang.String str) {
        java.util.regex.Matcher matcher = com.android.server.soundtrigger_middleware.UuidUtil.PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw new java.lang.IllegalArgumentException("Illegal format for UUID: " + str);
        }
        android.hardware.audio.common.V2_0.Uuid uuid = new android.hardware.audio.common.V2_0.Uuid();
        uuid.timeLow = java.lang.Integer.parseUnsignedInt(matcher.group(1), 16);
        uuid.timeMid = (short) java.lang.Integer.parseUnsignedInt(matcher.group(2), 16);
        uuid.versionAndTimeHigh = (short) java.lang.Integer.parseUnsignedInt(matcher.group(3), 16);
        uuid.variantAndClockSeqHigh = (short) java.lang.Integer.parseUnsignedInt(matcher.group(4), 16);
        uuid.node = new byte[]{(byte) java.lang.Integer.parseUnsignedInt(matcher.group(5), 16), (byte) java.lang.Integer.parseUnsignedInt(matcher.group(6), 16), (byte) java.lang.Integer.parseUnsignedInt(matcher.group(7), 16), (byte) java.lang.Integer.parseUnsignedInt(matcher.group(8), 16), (byte) java.lang.Integer.parseUnsignedInt(matcher.group(9), 16), (byte) java.lang.Integer.parseUnsignedInt(matcher.group(10), 16)};
        return uuid;
    }

    static int aidl2hidlSoundModelType(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                throw new java.lang.IllegalArgumentException("Unknown sound model type: " + i);
        }
    }

    static int hidl2aidlSoundModelType(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                throw new java.lang.IllegalArgumentException("Unknown sound model type: " + i);
        }
    }

    @android.annotation.NonNull
    static android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase aidl2hidlPhrase(@android.annotation.NonNull android.media.soundtrigger.Phrase phrase) {
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase phrase2 = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Phrase();
        phrase2.id = phrase.id;
        phrase2.recognitionModes = aidl2hidlRecognitionModes(phrase.recognitionModes);
        for (int i : phrase.users) {
            phrase2.users.add(java.lang.Integer.valueOf(i));
        }
        phrase2.locale = phrase.locale;
        phrase2.text = phrase.text;
        return phrase2;
    }

    static int aidl2hidlRecognitionModes(int i) {
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

    static int hidl2aidlRecognitionModes(int i) {
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

    @android.annotation.NonNull
    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel aidl2hidlSoundModel(@android.annotation.NonNull android.media.soundtrigger.SoundModel soundModel) {
        android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel();
        soundModel2.header.type = aidl2hidlSoundModelType(soundModel.type);
        soundModel2.header.uuid = aidl2hidlUuid(soundModel.uuid);
        soundModel2.header.vendorUuid = aidl2hidlUuid(soundModel.vendorUuid);
        soundModel2.data = parcelFileDescriptorToHidlMemory(soundModel.data, soundModel.dataSize);
        return soundModel2;
    }

    @android.annotation.NonNull
    static android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel aidl2hidlPhraseSoundModel(@android.annotation.NonNull android.media.soundtrigger.PhraseSoundModel phraseSoundModel) {
        android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel2 = new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel();
        phraseSoundModel2.common = aidl2hidlSoundModel(phraseSoundModel.common);
        for (android.media.soundtrigger.Phrase phrase : phraseSoundModel.phrases) {
            phraseSoundModel2.phrases.add(aidl2hidlPhrase(phrase));
        }
        return phraseSoundModel2;
    }

    @android.annotation.NonNull
    static android.hardware.soundtrigger.V2_3.RecognitionConfig aidl2hidlRecognitionConfig(@android.annotation.NonNull android.media.soundtrigger.RecognitionConfig recognitionConfig, int i, int i2) {
        android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig2 = new android.hardware.soundtrigger.V2_3.RecognitionConfig();
        recognitionConfig2.base.header.captureDevice = i;
        recognitionConfig2.base.header.captureHandle = i2;
        recognitionConfig2.base.header.captureRequested = recognitionConfig.captureRequested;
        for (android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra : recognitionConfig.phraseRecognitionExtras) {
            recognitionConfig2.base.header.phrases.add(aidl2hidlPhraseRecognitionExtra(phraseRecognitionExtra));
        }
        recognitionConfig2.base.data = android.os.HidlMemoryUtil.byteArrayToHidlMemory(recognitionConfig.data, "SoundTrigger RecognitionConfig");
        recognitionConfig2.audioCapabilities = recognitionConfig.audioCapabilities;
        return recognitionConfig2;
    }

    @android.annotation.NonNull
    static android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra aidl2hidlPhraseRecognitionExtra(@android.annotation.NonNull android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra) {
        android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra phraseRecognitionExtra2 = new android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra();
        phraseRecognitionExtra2.id = phraseRecognitionExtra.id;
        phraseRecognitionExtra2.recognitionModes = aidl2hidlRecognitionModes(phraseRecognitionExtra.recognitionModes);
        phraseRecognitionExtra2.confidenceLevel = phraseRecognitionExtra.confidenceLevel;
        phraseRecognitionExtra2.levels.ensureCapacity(phraseRecognitionExtra.levels.length);
        for (android.media.soundtrigger.ConfidenceLevel confidenceLevel : phraseRecognitionExtra.levels) {
            phraseRecognitionExtra2.levels.add(aidl2hidlConfidenceLevel(confidenceLevel));
        }
        return phraseRecognitionExtra2;
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.PhraseRecognitionExtra hidl2aidlPhraseRecognitionExtra(@android.annotation.NonNull android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra phraseRecognitionExtra) {
        android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra2 = new android.media.soundtrigger.PhraseRecognitionExtra();
        phraseRecognitionExtra2.id = phraseRecognitionExtra.id;
        phraseRecognitionExtra2.recognitionModes = hidl2aidlRecognitionModes(phraseRecognitionExtra.recognitionModes);
        phraseRecognitionExtra2.confidenceLevel = phraseRecognitionExtra.confidenceLevel;
        phraseRecognitionExtra2.levels = new android.media.soundtrigger.ConfidenceLevel[phraseRecognitionExtra.levels.size()];
        for (int i = 0; i < phraseRecognitionExtra.levels.size(); i++) {
            phraseRecognitionExtra2.levels[i] = hidl2aidlConfidenceLevel(phraseRecognitionExtra.levels.get(i));
        }
        return phraseRecognitionExtra2;
    }

    @android.annotation.NonNull
    static android.hardware.soundtrigger.V2_0.ConfidenceLevel aidl2hidlConfidenceLevel(@android.annotation.NonNull android.media.soundtrigger.ConfidenceLevel confidenceLevel) {
        android.hardware.soundtrigger.V2_0.ConfidenceLevel confidenceLevel2 = new android.hardware.soundtrigger.V2_0.ConfidenceLevel();
        confidenceLevel2.userId = confidenceLevel.userId;
        confidenceLevel2.levelPercent = confidenceLevel.levelPercent;
        return confidenceLevel2;
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.ConfidenceLevel hidl2aidlConfidenceLevel(@android.annotation.NonNull android.hardware.soundtrigger.V2_0.ConfidenceLevel confidenceLevel) {
        android.media.soundtrigger.ConfidenceLevel confidenceLevel2 = new android.media.soundtrigger.ConfidenceLevel();
        confidenceLevel2.userId = confidenceLevel.userId;
        confidenceLevel2.levelPercent = confidenceLevel.levelPercent;
        return confidenceLevel2;
    }

    static int hidl2aidlRecognitionStatus(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Unknown recognition status: " + i);
        }
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.RecognitionEvent hidl2aidlRecognitionEvent(@android.annotation.NonNull android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent) {
        android.media.soundtrigger.RecognitionEvent recognitionEvent2 = new android.media.soundtrigger.RecognitionEvent();
        recognitionEvent2.status = hidl2aidlRecognitionStatus(recognitionEvent.status);
        recognitionEvent2.type = hidl2aidlSoundModelType(recognitionEvent.type);
        recognitionEvent2.captureAvailable = recognitionEvent.captureAvailable;
        recognitionEvent2.captureDelayMs = recognitionEvent.captureDelayMs;
        recognitionEvent2.capturePreambleMs = recognitionEvent.capturePreambleMs;
        recognitionEvent2.triggerInData = recognitionEvent.triggerInData;
        recognitionEvent2.audioConfig = hidl2aidlAudioConfig(recognitionEvent.audioConfig, true);
        recognitionEvent2.data = new byte[recognitionEvent.data.size()];
        for (int i = 0; i < recognitionEvent2.data.length; i++) {
            recognitionEvent2.data[i] = recognitionEvent.data.get(i).byteValue();
        }
        recognitionEvent2.recognitionStillActive = recognitionEvent2.status == 3;
        return recognitionEvent2;
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.RecognitionEvent hidl2aidlRecognitionEvent(@android.annotation.NonNull android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent) {
        android.media.soundtrigger.RecognitionEvent hidl2aidlRecognitionEvent = hidl2aidlRecognitionEvent(recognitionEvent.header);
        hidl2aidlRecognitionEvent.data = android.os.HidlMemoryUtil.hidlMemoryToByteArray(recognitionEvent.data);
        return hidl2aidlRecognitionEvent;
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.PhraseRecognitionEvent hidl2aidlPhraseRecognitionEvent(@android.annotation.NonNull android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent) {
        android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent2 = new android.media.soundtrigger.PhraseRecognitionEvent();
        phraseRecognitionEvent2.common = hidl2aidlRecognitionEvent(phraseRecognitionEvent.common);
        phraseRecognitionEvent2.phraseExtras = new android.media.soundtrigger.PhraseRecognitionExtra[phraseRecognitionEvent.phraseExtras.size()];
        for (int i = 0; i < phraseRecognitionEvent.phraseExtras.size(); i++) {
            phraseRecognitionEvent2.phraseExtras[i] = hidl2aidlPhraseRecognitionExtra(phraseRecognitionEvent.phraseExtras.get(i));
        }
        return phraseRecognitionEvent2;
    }

    @android.annotation.NonNull
    static android.media.soundtrigger.PhraseRecognitionEvent hidl2aidlPhraseRecognitionEvent(@android.annotation.NonNull android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent) {
        android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent2 = new android.media.soundtrigger.PhraseRecognitionEvent();
        phraseRecognitionEvent2.common = hidl2aidlRecognitionEvent(phraseRecognitionEvent.common);
        phraseRecognitionEvent2.phraseExtras = new android.media.soundtrigger.PhraseRecognitionExtra[phraseRecognitionEvent.phraseExtras.size()];
        for (int i = 0; i < phraseRecognitionEvent.phraseExtras.size(); i++) {
            phraseRecognitionEvent2.phraseExtras[i] = hidl2aidlPhraseRecognitionExtra(phraseRecognitionEvent.phraseExtras.get(i));
        }
        return phraseRecognitionEvent2;
    }

    @android.annotation.NonNull
    static android.media.audio.common.AudioConfig hidl2aidlAudioConfig(@android.annotation.NonNull android.hardware.audio.common.V2_0.AudioConfig audioConfig, boolean z) {
        android.media.audio.common.AudioConfig audioConfig2 = new android.media.audio.common.AudioConfig();
        audioConfig2.base = hidl2aidlAudioConfigBase(audioConfig.sampleRateHz, audioConfig.channelMask, audioConfig.format, z);
        audioConfig2.offloadInfo = hidl2aidlOffloadInfo(audioConfig.offloadInfo);
        audioConfig2.frameCount = audioConfig.frameCount;
        return audioConfig2;
    }

    @android.annotation.NonNull
    static android.media.audio.common.AudioOffloadInfo hidl2aidlOffloadInfo(@android.annotation.NonNull android.hardware.audio.common.V2_0.AudioOffloadInfo audioOffloadInfo) {
        android.media.audio.common.AudioOffloadInfo audioOffloadInfo2 = new android.media.audio.common.AudioOffloadInfo();
        audioOffloadInfo2.base = hidl2aidlAudioConfigBase(audioOffloadInfo.sampleRateHz, audioOffloadInfo.channelMask, audioOffloadInfo.format, false);
        audioOffloadInfo2.streamType = android.media.audio.common.AidlConversion.legacy2aidl_audio_stream_type_t_AudioStreamType(audioOffloadInfo.streamType);
        audioOffloadInfo2.bitRatePerSecond = audioOffloadInfo.bitRatePerSecond;
        audioOffloadInfo2.durationUs = audioOffloadInfo.durationMicroseconds;
        audioOffloadInfo2.hasVideo = audioOffloadInfo.hasVideo;
        audioOffloadInfo2.isStreaming = audioOffloadInfo.isStreaming;
        audioOffloadInfo2.bitWidth = audioOffloadInfo.bitWidth;
        audioOffloadInfo2.offloadBufferSize = audioOffloadInfo.bufferSize;
        audioOffloadInfo2.usage = android.media.audio.common.AidlConversion.legacy2aidl_audio_usage_t_AudioUsage(audioOffloadInfo.usage);
        return audioOffloadInfo2;
    }

    @android.annotation.NonNull
    static android.media.audio.common.AudioConfigBase hidl2aidlAudioConfigBase(int i, int i2, int i3, boolean z) {
        android.media.audio.common.AudioConfigBase audioConfigBase = new android.media.audio.common.AudioConfigBase();
        audioConfigBase.sampleRate = i;
        audioConfigBase.channelMask = android.media.audio.common.AidlConversion.legacy2aidl_audio_channel_mask_t_AudioChannelLayout(i2, z);
        audioConfigBase.format = android.media.audio.common.AidlConversion.legacy2aidl_audio_format_t_AudioFormatDescription(i3);
        return audioConfigBase;
    }

    @android.annotation.Nullable
    static android.media.soundtrigger.ModelParameterRange hidl2aidlModelParameterRange(android.hardware.soundtrigger.V2_3.ModelParameterRange modelParameterRange) {
        if (modelParameterRange == null) {
            return null;
        }
        android.media.soundtrigger.ModelParameterRange modelParameterRange2 = new android.media.soundtrigger.ModelParameterRange();
        modelParameterRange2.minInclusive = modelParameterRange.start;
        modelParameterRange2.maxInclusive = modelParameterRange.end;
        return modelParameterRange2;
    }

    static int aidl2hidlModelParameter(int i) {
        switch (i) {
            case 0:
                return 0;
            default:
                return -1;
        }
    }

    static int hidl2aidlAudioCapabilities(int i) {
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

    @android.annotation.NonNull
    private static android.os.HidlMemory parcelFileDescriptorToHidlMemory(@android.annotation.Nullable android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
        if (i > 0) {
            return android.os.HidlMemoryUtil.fileDescriptorToHidlMemory(parcelFileDescriptor.getFileDescriptor(), i);
        }
        return android.os.HidlMemoryUtil.fileDescriptorToHidlMemory((java.io.FileDescriptor) null, 0);
    }
}
