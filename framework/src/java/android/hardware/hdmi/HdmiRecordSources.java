package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HdmiRecordSources {
    public static final int ANALOGUE_BROADCAST_TYPE_CABLE = 0;
    public static final int ANALOGUE_BROADCAST_TYPE_SATELLITE = 1;
    public static final int ANALOGUE_BROADCAST_TYPE_TERRESTRIAL = 2;
    public static final int BROADCAST_SYSTEM_NTSC_M = 3;
    public static final int BROADCAST_SYSTEM_PAL_BG = 0;
    public static final int BROADCAST_SYSTEM_PAL_DK = 8;
    public static final int BROADCAST_SYSTEM_PAL_I = 4;
    public static final int BROADCAST_SYSTEM_PAL_M = 2;
    public static final int BROADCAST_SYSTEM_PAL_OTHER_SYSTEM = 31;
    public static final int BROADCAST_SYSTEM_SECAM_BG = 6;
    public static final int BROADCAST_SYSTEM_SECAM_DK = 5;
    public static final int BROADCAST_SYSTEM_SECAM_L = 7;
    public static final int BROADCAST_SYSTEM_SECAM_LP = 1;
    private static final int CHANNEL_NUMBER_FORMAT_1_PART = 1;
    private static final int CHANNEL_NUMBER_FORMAT_2_PART = 2;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB = 0;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB_BS = 8;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB_CS = 9;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB_T = 10;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC = 1;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC_CABLE = 16;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC_SATELLITE = 17;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC_TERRESTRIAL = 18;
    public static final int DIGITAL_BROADCAST_TYPE_DVB = 2;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_C = 24;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_S = 25;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_S2 = 26;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_T = 27;
    private static final int RECORD_SOURCE_TYPE_ANALOGUE_SERVICE = 3;
    private static final int RECORD_SOURCE_TYPE_DIGITAL_SERVICE = 2;
    private static final int RECORD_SOURCE_TYPE_EXTERNAL_PHYSICAL_ADDRESS = 5;
    private static final int RECORD_SOURCE_TYPE_EXTERNAL_PLUG = 4;
    private static final int RECORD_SOURCE_TYPE_OWN_SOURCE = 1;
    private static final java.lang.String TAG = "HdmiRecordSources";

    private interface DigitalServiceIdentification {
        int toByteArray(byte[] bArr, int i);
    }

    private HdmiRecordSources() {
    }

    @android.annotation.SystemApi
    public static abstract class RecordSource {
        final int mExtraDataSize;
        final int mSourceType;

        abstract int extraParamToByteArray(byte[] bArr, int i);

        RecordSource(int i, int i2) {
            this.mSourceType = i;
            this.mExtraDataSize = i2;
        }

        final int getDataSize(boolean z) {
            return z ? this.mExtraDataSize + 1 : this.mExtraDataSize;
        }

        final int toByteArray(boolean z, byte[] bArr, int i) {
            if (z) {
                bArr[i] = (byte) this.mSourceType;
                i++;
            }
            extraParamToByteArray(bArr, i);
            return getDataSize(z);
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.OwnSource ofOwnSource() {
        return new android.hardware.hdmi.HdmiRecordSources.OwnSource();
    }

    @android.annotation.SystemApi
    public static final class OwnSource extends android.hardware.hdmi.HdmiRecordSources.RecordSource {
        private static final int EXTRA_DATA_SIZE = 0;

        private OwnSource() {
            super(1, 0);
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            return 0;
        }
    }

    public static final class AribData implements android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification {
        private final int mOriginalNetworkId;
        private final int mServiceId;
        private final int mTransportStreamId;

        public AribData(int i, int i2, int i3) {
            this.mTransportStreamId = i;
            this.mServiceId = i2;
            this.mOriginalNetworkId = i3;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification
        public int toByteArray(byte[] bArr, int i) {
            return android.hardware.hdmi.HdmiRecordSources.threeFieldsToSixBytes(this.mTransportStreamId, this.mServiceId, this.mOriginalNetworkId, bArr, i);
        }
    }

    public static final class AtscData implements android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification {
        private final int mProgramNumber;
        private final int mTransportStreamId;

        public AtscData(int i, int i2) {
            this.mTransportStreamId = i;
            this.mProgramNumber = i2;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification
        public int toByteArray(byte[] bArr, int i) {
            return android.hardware.hdmi.HdmiRecordSources.threeFieldsToSixBytes(this.mTransportStreamId, this.mProgramNumber, 0, bArr, i);
        }
    }

    public static final class DvbData implements android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification {
        private final int mOriginalNetworkId;
        private final int mServiceId;
        private final int mTransportStreamId;

        public DvbData(int i, int i2, int i3) {
            this.mTransportStreamId = i;
            this.mServiceId = i2;
            this.mOriginalNetworkId = i3;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification
        public int toByteArray(byte[] bArr, int i) {
            return android.hardware.hdmi.HdmiRecordSources.threeFieldsToSixBytes(this.mTransportStreamId, this.mServiceId, this.mOriginalNetworkId, bArr, i);
        }
    }

    private static final class ChannelIdentifier {
        private final int mChannelNumberFormat;
        private final int mMajorChannelNumber;
        private final int mMinorChannelNumber;

        private ChannelIdentifier(int i, int i2, int i3) {
            this.mChannelNumberFormat = i;
            this.mMajorChannelNumber = i2;
            this.mMinorChannelNumber = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int toByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) ((this.mChannelNumberFormat << 2) | ((this.mMajorChannelNumber >>> 8) & 3));
            bArr[i + 1] = (byte) (this.mMajorChannelNumber & 255);
            android.hardware.hdmi.HdmiRecordSources.shortToByteArray((short) this.mMinorChannelNumber, bArr, i + 2);
            return 4;
        }
    }

    public static final class DigitalChannelData implements android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification {
        private final android.hardware.hdmi.HdmiRecordSources.ChannelIdentifier mChannelIdentifier;

        public static android.hardware.hdmi.HdmiRecordSources.DigitalChannelData ofTwoNumbers(int i, int i2) {
            return new android.hardware.hdmi.HdmiRecordSources.DigitalChannelData(new android.hardware.hdmi.HdmiRecordSources.ChannelIdentifier(2, i, i2));
        }

        public static android.hardware.hdmi.HdmiRecordSources.DigitalChannelData ofOneNumber(int i) {
            return new android.hardware.hdmi.HdmiRecordSources.DigitalChannelData(new android.hardware.hdmi.HdmiRecordSources.ChannelIdentifier(1, 0, i));
        }

        private DigitalChannelData(android.hardware.hdmi.HdmiRecordSources.ChannelIdentifier channelIdentifier) {
            this.mChannelIdentifier = channelIdentifier;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification
        public int toByteArray(byte[] bArr, int i) {
            this.mChannelIdentifier.toByteArray(bArr, i);
            bArr[i + 4] = 0;
            bArr[i + 5] = 0;
            return 6;
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource ofDigitalChannelId(int i, android.hardware.hdmi.HdmiRecordSources.DigitalChannelData digitalChannelData) {
        if (digitalChannelData == null) {
            throw new java.lang.IllegalArgumentException("data should not be null.");
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
            case 16:
            case 17:
            case 18:
            case 24:
            case 25:
            case 26:
            case 27:
                return new android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource(1, i, digitalChannelData);
            default:
                android.util.Log.w(TAG, "Invalid broadcast type:" + i);
                throw new java.lang.IllegalArgumentException("Invalid broadcast system value:" + i);
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource ofArib(int i, android.hardware.hdmi.HdmiRecordSources.AribData aribData) {
        if (aribData == null) {
            throw new java.lang.IllegalArgumentException("data should not be null.");
        }
        switch (i) {
            case 0:
            case 8:
            case 9:
            case 10:
                return new android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource(0, i, aribData);
            default:
                android.util.Log.w(TAG, "Invalid ARIB type:" + i);
                throw new java.lang.IllegalArgumentException("type should not be null.");
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource ofAtsc(int i, android.hardware.hdmi.HdmiRecordSources.AtscData atscData) {
        if (atscData == null) {
            throw new java.lang.IllegalArgumentException("data should not be null.");
        }
        switch (i) {
            case 1:
            case 16:
            case 17:
            case 18:
                return new android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource(0, i, atscData);
            default:
                android.util.Log.w(TAG, "Invalid ATSC type:" + i);
                throw new java.lang.IllegalArgumentException("Invalid ATSC type:" + i);
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource ofDvb(int i, android.hardware.hdmi.HdmiRecordSources.DvbData dvbData) {
        if (dvbData == null) {
            throw new java.lang.IllegalArgumentException("data should not be null.");
        }
        switch (i) {
            case 2:
            case 24:
            case 25:
            case 26:
            case 27:
                return new android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource(0, i, dvbData);
            default:
                android.util.Log.w(TAG, "Invalid DVB type:" + i);
                throw new java.lang.IllegalArgumentException("Invalid DVB type:" + i);
        }
    }

    @android.annotation.SystemApi
    public static final class DigitalServiceSource extends android.hardware.hdmi.HdmiRecordSources.RecordSource {
        private static final int DIGITAL_SERVICE_IDENTIFIED_BY_CHANNEL = 1;
        private static final int DIGITAL_SERVICE_IDENTIFIED_BY_DIGITAL_ID = 0;
        static final int EXTRA_DATA_SIZE = 7;
        private final int mBroadcastSystem;
        private final android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification mIdentification;
        private final int mIdentificationMethod;

        private DigitalServiceSource(int i, int i2, android.hardware.hdmi.HdmiRecordSources.DigitalServiceIdentification digitalServiceIdentification) {
            super(2, 7);
            this.mIdentificationMethod = i;
            this.mBroadcastSystem = i2;
            this.mIdentification = digitalServiceIdentification;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) ((this.mIdentificationMethod << 7) | (this.mBroadcastSystem & 127));
            this.mIdentification.toByteArray(bArr, i + 1);
            return 7;
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.AnalogueServiceSource ofAnalogue(int i, int i2, int i3) {
        if (i < 0 || i > 2) {
            android.util.Log.w(TAG, "Invalid Broadcast type:" + i);
            throw new java.lang.IllegalArgumentException("Invalid Broadcast type:" + i);
        }
        if (i2 < 0 || i2 > 65535) {
            android.util.Log.w(TAG, "Invalid frequency value[0x0000-0xFFFF]:" + i2);
            throw new java.lang.IllegalArgumentException("Invalid frequency value[0x0000-0xFFFF]:" + i2);
        }
        if (i3 < 0 || i3 > 31) {
            android.util.Log.w(TAG, "Invalid Broadcast system:" + i3);
            throw new java.lang.IllegalArgumentException("Invalid Broadcast system:" + i3);
        }
        return new android.hardware.hdmi.HdmiRecordSources.AnalogueServiceSource(i, i2, i3);
    }

    @android.annotation.SystemApi
    public static final class AnalogueServiceSource extends android.hardware.hdmi.HdmiRecordSources.RecordSource {
        static final int EXTRA_DATA_SIZE = 4;
        private final int mBroadcastSystem;
        private final int mBroadcastType;
        private final int mFrequency;

        private AnalogueServiceSource(int i, int i2, int i3) {
            super(3, 4);
            this.mBroadcastType = i;
            this.mFrequency = i2;
            this.mBroadcastSystem = i3;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) this.mBroadcastType;
            android.hardware.hdmi.HdmiRecordSources.shortToByteArray((short) this.mFrequency, bArr, i + 1);
            bArr[i + 3] = (byte) this.mBroadcastSystem;
            return 4;
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.ExternalPlugData ofExternalPlug(int i) {
        if (i < 1 || i > 255) {
            android.util.Log.w(TAG, "Invalid plug number[1-255]" + i);
            throw new java.lang.IllegalArgumentException("Invalid plug number[1-255]" + i);
        }
        return new android.hardware.hdmi.HdmiRecordSources.ExternalPlugData(i);
    }

    @android.annotation.SystemApi
    public static final class ExternalPlugData extends android.hardware.hdmi.HdmiRecordSources.RecordSource {
        static final int EXTRA_DATA_SIZE = 1;
        private final int mPlugNumber;

        private ExternalPlugData(int i) {
            super(4, 1);
            this.mPlugNumber = i;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) this.mPlugNumber;
            return 1;
        }
    }

    public static android.hardware.hdmi.HdmiRecordSources.ExternalPhysicalAddress ofExternalPhysicalAddress(int i) {
        if (((-65536) & i) != 0) {
            android.util.Log.w(TAG, "Invalid physical address:" + i);
            throw new java.lang.IllegalArgumentException("Invalid physical address:" + i);
        }
        return new android.hardware.hdmi.HdmiRecordSources.ExternalPhysicalAddress(i);
    }

    @android.annotation.SystemApi
    public static final class ExternalPhysicalAddress extends android.hardware.hdmi.HdmiRecordSources.RecordSource {
        static final int EXTRA_DATA_SIZE = 2;
        private final int mPhysicalAddress;

        private ExternalPhysicalAddress(int i) {
            super(5, 2);
            this.mPhysicalAddress = i;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            android.hardware.hdmi.HdmiRecordSources.shortToByteArray((short) this.mPhysicalAddress, bArr, i);
            return 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int threeFieldsToSixBytes(int i, int i2, int i3, byte[] bArr, int i4) {
        shortToByteArray((short) i, bArr, i4);
        shortToByteArray((short) i2, bArr, i4 + 2);
        shortToByteArray((short) i3, bArr, i4 + 4);
        return 6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int shortToByteArray(short s, byte[] bArr, int i) {
        bArr[i] = (byte) ((s >>> 8) & 255);
        bArr[i + 1] = (byte) (s & 255);
        return 2;
    }

    @android.annotation.SystemApi
    public static boolean checkRecordSource(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return false;
        }
        byte b = bArr[0];
        int length = bArr.length - 1;
        switch (b) {
            case 1:
                return length == 0;
            case 2:
                return length == 7;
            case 3:
                return length == 4;
            case 4:
                return length == 1;
            case 5:
                return length == 2;
            default:
                return false;
        }
    }
}
