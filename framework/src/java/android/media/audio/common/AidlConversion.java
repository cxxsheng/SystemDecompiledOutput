package android.media.audio.common;

/* loaded from: classes2.dex */
public class AidlConversion {
    private static native int aidl2legacy_AudioChannelLayout_Parcel_audio_channel_mask_t(android.os.Parcel parcel, boolean z);

    public static native int aidl2legacy_AudioEncapsulationMode_audio_encapsulation_mode_t(int i);

    private static native int aidl2legacy_AudioFormatDescription_Parcel_audio_format_t(android.os.Parcel parcel);

    public static native int aidl2legacy_AudioStreamType_audio_stream_type_t(int i);

    public static native int aidl2legacy_AudioUsage_audio_usage_t(int i);

    private static native android.os.Parcel legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel(int i, boolean z);

    public static native int legacy2aidl_audio_encapsulation_mode_t_AudioEncapsulationMode(int i);

    private static native android.os.Parcel legacy2aidl_audio_format_t_AudioFormatDescription_Parcel(int i);

    public static native int legacy2aidl_audio_stream_type_t_AudioStreamType(int i);

    public static native int legacy2aidl_audio_usage_t_AudioUsage(int i);

    public static int aidl2legacy_AudioChannelLayout_audio_channel_mask_t(android.media.audio.common.AudioChannelLayout audioChannelLayout, boolean z) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        audioChannelLayout.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        try {
            return aidl2legacy_AudioChannelLayout_Parcel_audio_channel_mask_t(obtain, z);
        } finally {
            obtain.recycle();
        }
    }

    public static android.media.audio.common.AudioChannelLayout legacy2aidl_audio_channel_mask_t_AudioChannelLayout(int i, boolean z) {
        android.os.Parcel legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel = legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel(i, z);
        if (legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel != null) {
            try {
                return android.media.audio.common.AudioChannelLayout.CREATOR.createFromParcel(legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel);
            } finally {
                legacy2aidl_audio_channel_mask_t_AudioChannelLayout_Parcel.recycle();
            }
        }
        throw new java.lang.IllegalArgumentException("Failed to convert legacy audio " + (z ? "input" : "output") + " audio_channel_mask_t " + i + " value");
    }

    public static int aidl2legacy_AudioFormatDescription_audio_format_t(android.media.audio.common.AudioFormatDescription audioFormatDescription) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        audioFormatDescription.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        try {
            return aidl2legacy_AudioFormatDescription_Parcel_audio_format_t(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public static android.media.audio.common.AudioFormatDescription legacy2aidl_audio_format_t_AudioFormatDescription(int i) {
        android.os.Parcel legacy2aidl_audio_format_t_AudioFormatDescription_Parcel = legacy2aidl_audio_format_t_AudioFormatDescription_Parcel(i);
        if (legacy2aidl_audio_format_t_AudioFormatDescription_Parcel != null) {
            try {
                return android.media.audio.common.AudioFormatDescription.CREATOR.createFromParcel(legacy2aidl_audio_format_t_AudioFormatDescription_Parcel);
            } finally {
                legacy2aidl_audio_format_t_AudioFormatDescription_Parcel.recycle();
            }
        }
        throw new java.lang.IllegalArgumentException("Failed to convert legacy audio_format_t value " + i);
    }

    private static int aidl2api_AudioChannelLayoutBit_AudioFormatChannel(int i, boolean z) {
        if (z) {
            switch (i) {
                case 1:
                    return 4;
                case 2:
                    return 8;
                case 4:
                    return 262144;
                case 8:
                    return 1048576;
                case 16:
                    return 65536;
                case 32:
                    return 131072;
                case 256:
                    return 32;
                case 262144:
                    return 2097152;
                case 524288:
                    return 4194304;
                default:
                    return 0;
            }
        }
        switch (i) {
            case 1:
                return 4;
            case 2:
                return 8;
            case 4:
                return 16;
            case 8:
                return 32;
            case 16:
                return 64;
            case 32:
                return 128;
            case 64:
                return 256;
            case 128:
                return 512;
            case 256:
                return 1024;
            case 512:
                return 2048;
            case 1024:
                return 4096;
            case 2048:
                return 8192;
            case 4096:
                return 16384;
            case 8192:
                return 32768;
            case 16384:
                return 65536;
            case 32768:
                return 131072;
            case 65536:
                return 262144;
            case 131072:
                return 524288;
            case 262144:
                return 1048576;
            case 524288:
                return 2097152;
            case 1048576:
                return 4194304;
            case 2097152:
                return 8388608;
            case 4194304:
                return 16777216;
            case 8388608:
                return 33554432;
            case 16777216:
                return 67108864;
            case 33554432:
                return 134217728;
            case 536870912:
                return 268435456;
            case 1073741824:
                return 536870912;
            default:
                return 0;
        }
    }

    private static int aidl2api_AudioChannelLayoutBitMask_AudioFormatChannelMask(int i, boolean z) {
        int i2 = 0;
        for (int i3 = Integer.MIN_VALUE; i3 != 0; i3 >>>= 1) {
            if ((i & i3) == i3) {
                int aidl2api_AudioChannelLayoutBit_AudioFormatChannel = aidl2api_AudioChannelLayoutBit_AudioFormatChannel(i3, z);
                if (aidl2api_AudioChannelLayoutBit_AudioFormatChannel == 0) {
                    break;
                }
                i2 |= aidl2api_AudioChannelLayoutBit_AudioFormatChannel;
                i &= ~i3;
                if (i == 0) {
                    return i2;
                }
            }
        }
        return 0;
    }

    public static int aidl2api_AudioChannelLayout_AudioFormatChannelMask(android.media.audio.common.AudioChannelLayout audioChannelLayout, boolean z) {
        switch (audioChannelLayout.getTag()) {
            case 3:
                if (!z) {
                    switch (audioChannelLayout.getLayoutMask()) {
                        case 1:
                            break;
                        case 3:
                            break;
                        case 7:
                            break;
                        case 11:
                            break;
                        case 15:
                            break;
                        case 51:
                            break;
                        case 55:
                            break;
                        case 63:
                            break;
                        case 259:
                            break;
                        case 260:
                            break;
                        case 263:
                            break;
                        case 319:
                            break;
                        case 1539:
                            break;
                        case 1551:
                            break;
                        case 1599:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_5POINT1POINT4 /* 184383 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_7POINT1POINT4 /* 185919 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_2POINT0POINT2 /* 786435 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_3POINT0POINT2 /* 786439 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_2POINT1POINT2 /* 786443 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_3POINT1POINT2 /* 786447 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_5POINT1POINT2 /* 786495 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_7POINT1POINT2 /* 788031 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_13POINT_360RA /* 7534087 */:
                            break;
                        case 16777215:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_9POINT1POINT4 /* 50517567 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_9POINT1POINT6 /* 51303999 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_MONO_HAPTIC_A /* 1073741825 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_STEREO_HAPTIC_A /* 1073741827 */:
                            break;
                        case 1610612736:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_MONO_HAPTIC_AB /* 1610612737 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_STEREO_HAPTIC_AB /* 1610612739 */:
                            break;
                    }
                } else {
                    switch (audioChannelLayout.getLayoutMask()) {
                        case 1:
                            break;
                        case 3:
                            break;
                        case 63:
                            break;
                        case 260:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_2POINT0POINT2 /* 786435 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_3POINT0POINT2 /* 786439 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_2POINT1POINT2 /* 786443 */:
                            break;
                        case android.media.audio.common.AudioChannelLayout.LAYOUT_3POINT1POINT2 /* 786447 */:
                            break;
                    }
                }
                break;
            case 4:
                if (z) {
                    switch (audioChannelLayout.getVoiceMask()) {
                        case 16384:
                            break;
                        case 32768:
                            break;
                        case android.media.audio.common.AudioChannelLayout.VOICE_CALL_MONO /* 49152 */:
                            break;
                    }
                }
                break;
        }
        return 0;
    }

    public static android.media.AudioFormat aidl2api_AudioConfig_AudioFormat(android.media.audio.common.AudioConfig audioConfig, boolean z) {
        return aidl2api_AudioConfigBase_AudioFormat(audioConfig.base, z);
    }

    public static android.media.AudioFormat aidl2api_AudioConfigBase_AudioFormat(android.media.audio.common.AudioConfigBase audioConfigBase, boolean z) {
        android.media.AudioFormat.Builder builder = new android.media.AudioFormat.Builder();
        builder.setSampleRate(audioConfigBase.sampleRate);
        if (audioConfigBase.channelMask.getTag() != 2) {
            builder.setChannelMask(aidl2api_AudioChannelLayout_AudioFormatChannelMask(audioConfigBase.channelMask, z));
        } else {
            builder.setChannelIndexMask(aidl2api_AudioChannelLayout_AudioFormatChannelMask(audioConfigBase.channelMask, z));
        }
        builder.setEncoding(aidl2api_AudioFormat_AudioFormatEncoding(audioConfigBase.format));
        return builder.build();
    }

    public static int aidl2api_AudioFormat_AudioFormatEncoding(android.media.audio.common.AudioFormatDescription audioFormatDescription) {
        switch (audioFormatDescription.type) {
            case 0:
                if (audioFormatDescription.encoding != null && !audioFormatDescription.encoding.isEmpty()) {
                    if (!android.media.MediaFormat.MIMETYPE_AUDIO_AC3.equals(audioFormatDescription.encoding)) {
                        if (!android.media.MediaFormat.MIMETYPE_AUDIO_EAC3.equals(audioFormatDescription.encoding)) {
                            if (!android.media.MediaFormat.MIMETYPE_AUDIO_DTS.equals(audioFormatDescription.encoding)) {
                                if (!android.media.MediaFormat.MIMETYPE_AUDIO_DTS_HD.equals(audioFormatDescription.encoding)) {
                                    if (!"audio/mpeg".equals(audioFormatDescription.encoding)) {
                                        if (!android.media.MediaFormat.MIMETYPE_AUDIO_AAC_LC.equals(audioFormatDescription.encoding)) {
                                            if (!android.media.MediaFormat.MIMETYPE_AUDIO_AAC_HE_V1.equals(audioFormatDescription.encoding)) {
                                                if (!android.media.MediaFormat.MIMETYPE_AUDIO_AAC_HE_V2.equals(audioFormatDescription.encoding)) {
                                                    if (!android.media.MediaFormat.MIMETYPE_AUDIO_IEC61937.equals(audioFormatDescription.encoding) || audioFormatDescription.pcm != 1) {
                                                        if (!android.media.MediaFormat.MIMETYPE_AUDIO_DOLBY_TRUEHD.equals(audioFormatDescription.encoding)) {
                                                            if (!android.media.MediaFormat.MIMETYPE_AUDIO_AAC_ELD.equals(audioFormatDescription.encoding)) {
                                                                if (!android.media.MediaFormat.MIMETYPE_AUDIO_AAC_XHE.equals(audioFormatDescription.encoding)) {
                                                                    if (!android.media.MediaFormat.MIMETYPE_AUDIO_AC4.equals(audioFormatDescription.encoding)) {
                                                                        if (!android.media.MediaFormat.MIMETYPE_AUDIO_EAC3_JOC.equals(audioFormatDescription.encoding)) {
                                                                            if (!android.media.MediaFormat.MIMETYPE_AUDIO_DOLBY_MAT.equals(audioFormatDescription.encoding) && !audioFormatDescription.encoding.startsWith("audio/vnd.dolby.mat.")) {
                                                                                if (!android.media.MediaFormat.MIMETYPE_AUDIO_OPUS.equals(audioFormatDescription.encoding)) {
                                                                                    if (!android.media.MediaFormat.MIMETYPE_AUDIO_MPEGH_BL_L3.equals(audioFormatDescription.encoding)) {
                                                                                        if (!android.media.MediaFormat.MIMETYPE_AUDIO_MPEGH_BL_L4.equals(audioFormatDescription.encoding)) {
                                                                                            if (!android.media.MediaFormat.MIMETYPE_AUDIO_MPEGH_LC_L3.equals(audioFormatDescription.encoding)) {
                                                                                                if (!android.media.MediaFormat.MIMETYPE_AUDIO_MPEGH_LC_L4.equals(audioFormatDescription.encoding)) {
                                                                                                    if (!android.media.MediaFormat.MIMETYPE_AUDIO_DTS_UHD.equals(audioFormatDescription.encoding)) {
                                                                                                        if (android.media.MediaFormat.MIMETYPE_AUDIO_DRA.equals(audioFormatDescription.encoding)) {
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 1:
                switch (audioFormatDescription.pcm) {
                }
        }
        return 0;
    }

    public static android.media.audio.common.AudioPort api2aidl_AudioDeviceAttributes_AudioPort(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        android.media.audio.common.AudioPort audioPort = new android.media.audio.common.AudioPort();
        audioPort.name = audioDeviceAttributes.getName();
        audioPort.profiles = new android.media.audio.common.AudioProfile[0];
        audioPort.extraAudioDescriptors = (android.media.audio.common.ExtraAudioDescriptor[]) ((java.util.List) audioDeviceAttributes.getAudioDescriptors().stream().map(new java.util.function.Function() { // from class: android.media.audio.common.AidlConversion$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.media.audio.common.ExtraAudioDescriptor api2aidl_AudioDescriptor_ExtraAudioDescriptor;
                api2aidl_AudioDescriptor_ExtraAudioDescriptor = android.media.audio.common.AidlConversion.api2aidl_AudioDescriptor_ExtraAudioDescriptor((android.media.AudioDescriptor) obj);
                return api2aidl_AudioDescriptor_ExtraAudioDescriptor;
            }
        }).collect(java.util.stream.Collectors.toList())).toArray(new java.util.function.IntFunction() { // from class: android.media.audio.common.AidlConversion$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                return android.media.audio.common.AidlConversion.lambda$api2aidl_AudioDeviceAttributes_AudioPort$1(i);
            }
        });
        audioPort.flags = new android.media.audio.common.AudioIoFlags();
        audioPort.gains = new android.media.audio.common.AudioGain[0];
        android.media.audio.common.AudioPortDeviceExt audioPortDeviceExt = new android.media.audio.common.AudioPortDeviceExt();
        audioPortDeviceExt.device = new android.media.audio.common.AudioDevice();
        audioPortDeviceExt.encodedFormats = new android.media.audio.common.AudioFormatDescription[0];
        audioPortDeviceExt.device.type = api2aidl_NativeType_AudioDeviceDescription(audioDeviceAttributes.getInternalType());
        audioPortDeviceExt.device.address = android.media.audio.common.AudioDeviceAddress.id(audioDeviceAttributes.getAddress());
        audioPort.ext = android.media.audio.common.AudioPortExt.device(audioPortDeviceExt);
        return audioPort;
    }

    static /* synthetic */ android.media.audio.common.ExtraAudioDescriptor[] lambda$api2aidl_AudioDeviceAttributes_AudioPort$1(int i) {
        return new android.media.audio.common.ExtraAudioDescriptor[i];
    }

    public static android.media.audio.common.ExtraAudioDescriptor api2aidl_AudioDescriptor_ExtraAudioDescriptor(android.media.AudioDescriptor audioDescriptor) {
        android.media.audio.common.ExtraAudioDescriptor extraAudioDescriptor = new android.media.audio.common.ExtraAudioDescriptor();
        extraAudioDescriptor.standard = api2aidl_AudioDescriptorStandard_AudioStandard(audioDescriptor.getStandard());
        extraAudioDescriptor.audioDescriptor = audioDescriptor.getDescriptor();
        extraAudioDescriptor.encapsulationType = api2aidl_AudioProfileEncapsulationType_AudioEncapsulationType(audioDescriptor.getEncapsulationType());
        return extraAudioDescriptor;
    }

    public static android.media.AudioDescriptor aidl2api_ExtraAudioDescriptor_AudioDescriptor(android.media.audio.common.ExtraAudioDescriptor extraAudioDescriptor) {
        return new android.media.AudioDescriptor(aidl2api_AudioStandard_AudioDescriptorStandard(extraAudioDescriptor.standard), aidl2api_AudioEncapsulationType_AudioProfileEncapsulationType(extraAudioDescriptor.encapsulationType), extraAudioDescriptor.audioDescriptor);
    }

    public static int api2aidl_AudioDescriptorStandard_AudioStandard(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    public static int aidl2api_AudioStandard_AudioDescriptorStandard(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    public static int api2aidl_AudioProfileEncapsulationType_AudioEncapsulationType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public static int aidl2api_AudioEncapsulationType_AudioProfileEncapsulationType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public static android.media.audio.common.AudioDeviceDescription api2aidl_NativeType_AudioDeviceDescription(int i) {
        android.media.audio.common.AudioDeviceDescription audioDeviceDescription = new android.media.audio.common.AudioDeviceDescription();
        audioDeviceDescription.connection = "";
        switch (i) {
            case -2147483644:
                audioDeviceDescription.type = 9;
                return audioDeviceDescription;
            case -2147483640:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_SCO;
                return audioDeviceDescription;
            case -2147483632:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case -2147483616:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "hdmi";
                return audioDeviceDescription;
            case -2147483584:
                audioDeviceDescription.type = 12;
                return audioDeviceDescription;
            case -2147483520:
                audioDeviceDescription.type = 10;
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_REMOTE_SUBMIX /* -2147483392 */:
                audioDeviceDescription.type = 11;
                return audioDeviceDescription;
            case -2147483136:
                audioDeviceDescription.type = 14;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case -2147482624:
                audioDeviceDescription.type = 14;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case -2147481600:
                audioDeviceDescription.type = 2;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case -2147479552:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case -2147475456:
                audioDeviceDescription.type = 6;
                return audioDeviceDescription;
            case -2147467264:
                audioDeviceDescription.type = 13;
                return audioDeviceDescription;
            case -2147450880:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case -2147418112:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "spdif";
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_BLUETOOTH_A2DP /* -2147352576 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_A2DP;
                return audioDeviceDescription;
            case -2147221504:
                audioDeviceDescription.type = 8;
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_IP /* -2146959360 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_IP_V4;
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_BUS /* -2146435072 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = "bus";
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_PROXY /* -2130706432 */:
                audioDeviceDescription.type = 3;
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_USB_HEADSET /* -2113929216 */:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_BLUETOOTH_BLE /* -2080374784 */:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_LE;
                return audioDeviceDescription;
            case -2013265920:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_HDMI_ARC;
                return audioDeviceDescription;
            case -2013265919:
                audioDeviceDescription.type = 4;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_HDMI_EARC;
                return audioDeviceDescription;
            case -1879048192:
                audioDeviceDescription.type = 5;
                return audioDeviceDescription;
            case -1610612736:
                audioDeviceDescription.type = 7;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_LE;
                return audioDeviceDescription;
            case android.media.AudioSystem.DEVICE_IN_DEFAULT /* -1073741824 */:
                audioDeviceDescription.type = 1;
                return audioDeviceDescription;
            case 1:
                audioDeviceDescription.type = 141;
                return audioDeviceDescription;
            case 2:
                audioDeviceDescription.type = 140;
                return audioDeviceDescription;
            case 4:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case 8:
                audioDeviceDescription.type = 136;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case 16:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_SCO;
                return audioDeviceDescription;
            case 32:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_SCO;
                return audioDeviceDescription;
            case 64:
                audioDeviceDescription.type = 132;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_SCO;
                return audioDeviceDescription;
            case 128:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_A2DP;
                return audioDeviceDescription;
            case 256:
                audioDeviceDescription.type = 136;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_A2DP;
                return audioDeviceDescription;
            case 512:
                audioDeviceDescription.type = 140;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_A2DP;
                return audioDeviceDescription;
            case 1024:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "hdmi";
                return audioDeviceDescription;
            case 2048:
                audioDeviceDescription.type = 145;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case 4096:
                audioDeviceDescription.type = 145;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case 8192:
                audioDeviceDescription.type = 130;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case 16384:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case 32768:
                audioDeviceDescription.type = 143;
                return audioDeviceDescription;
            case 65536:
                audioDeviceDescription.type = 144;
                return audioDeviceDescription;
            case 131072:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
                return audioDeviceDescription;
            case 262144:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_HDMI_ARC;
                return audioDeviceDescription;
            case 262145:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_HDMI_EARC;
                return audioDeviceDescription;
            case 524288:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "spdif";
                return audioDeviceDescription;
            case 1048576:
                audioDeviceDescription.type = 135;
                return audioDeviceDescription;
            case 2097152:
                audioDeviceDescription.type = 139;
                return audioDeviceDescription;
            case 4194304:
                audioDeviceDescription.type = 142;
                return audioDeviceDescription;
            case 8388608:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_IP_V4;
                return audioDeviceDescription;
            case 16777216:
                audioDeviceDescription.type = 133;
                audioDeviceDescription.connection = "bus";
                return audioDeviceDescription;
            case 33554432:
                audioDeviceDescription.type = 131;
                return audioDeviceDescription;
            case 67108864:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = "usb";
                return audioDeviceDescription;
            case 134217728:
                audioDeviceDescription.type = 138;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_WIRELESS;
                return audioDeviceDescription;
            case 268435456:
                audioDeviceDescription.type = 134;
                return audioDeviceDescription;
            case 536870912:
                audioDeviceDescription.type = 137;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_LE;
                return audioDeviceDescription;
            case 536870913:
                audioDeviceDescription.type = 140;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_LE;
                return audioDeviceDescription;
            case 536870914:
                audioDeviceDescription.type = 146;
                audioDeviceDescription.connection = android.media.audio.common.AudioDeviceDescription.CONNECTION_BT_LE;
                return audioDeviceDescription;
            case 1073741824:
                audioDeviceDescription.type = 129;
                return audioDeviceDescription;
            default:
                audioDeviceDescription.type = 0;
                return audioDeviceDescription;
        }
    }
}
