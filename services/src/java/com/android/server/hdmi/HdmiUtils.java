package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiUtils {
    private static final java.util.Map<java.lang.Integer, java.util.List<java.lang.Integer>> ADDRESS_TO_TYPE = java.util.Map.ofEntries(java.util.Map.entry(0, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{0})), java.util.Map.entry(1, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{1})), java.util.Map.entry(2, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{1})), java.util.Map.entry(3, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{3})), java.util.Map.entry(4, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{4})), java.util.Map.entry(5, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{5})), java.util.Map.entry(6, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{3})), java.util.Map.entry(7, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{3})), java.util.Map.entry(8, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{4})), java.util.Map.entry(9, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{1})), java.util.Map.entry(10, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{3})), java.util.Map.entry(11, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{4})), java.util.Map.entry(12, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{4, 1, 3, 7})), java.util.Map.entry(13, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{4, 1, 3, 7})), java.util.Map.entry(14, com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{0})), java.util.Map.entry(15, java.util.Collections.emptyList()));
    private static final java.lang.String[] DEFAULT_NAMES = {"TV", "Recorder_1", "Recorder_2", "Tuner_1", "Playback_1", "AudioSystem", "Tuner_2", "Tuner_3", "Playback_2", "Recorder_3", "Tuner_4", "Playback_3", "Backup_1", "Backup_2", "Secondary_TV"};
    private static final java.lang.String TAG = "HdmiUtils";
    static final int TARGET_NOT_UNDER_LOCAL_DEVICE = -1;
    static final int TARGET_SAME_PHYSICAL_ADDRESS = 0;

    private HdmiUtils() {
    }

    static boolean isValidAddress(int i) {
        return i >= 0 && i <= 14;
    }

    static boolean isEligibleAddressForDevice(int i, int i2) {
        return isValidAddress(i2) && ADDRESS_TO_TYPE.get(java.lang.Integer.valueOf(i2)).contains(java.lang.Integer.valueOf(i));
    }

    static boolean isEligibleAddressForCecVersion(int i, int i2) {
        if (isValidAddress(i2)) {
            return !(i2 == 12 || i2 == 13) || i >= 6;
        }
        return false;
    }

    static java.util.List<java.lang.Integer> getTypeFromAddress(int i) {
        if (isValidAddress(i)) {
            return ADDRESS_TO_TYPE.get(java.lang.Integer.valueOf(i));
        }
        return com.google.android.collect.Lists.newArrayList(new java.lang.Integer[]{-1});
    }

    static java.lang.String getDefaultDeviceName(int i) {
        if (isValidAddress(i)) {
            return DEFAULT_NAMES[i];
        }
        return "";
    }

    static void verifyAddressType(int i, int i2) {
        java.util.List<java.lang.Integer> typeFromAddress = getTypeFromAddress(i);
        if (!typeFromAddress.contains(java.lang.Integer.valueOf(i2))) {
            throw new java.lang.IllegalArgumentException("Device type missmatch:[Expected:" + i2 + ", Actual:" + typeFromAddress);
        }
    }

    static boolean checkCommandSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i, java.lang.String str) {
        int source = hdmiCecMessage.getSource();
        if (source != i) {
            android.util.Slog.w(str, "Invalid source [Expected:" + i + ", Actual:" + source + "]");
            return false;
        }
        return true;
    }

    static boolean parseCommandParamSystemAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return hdmiCecMessage.getParams()[0] == 1;
    }

    static boolean isAudioStatusMute(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return (hdmiCecMessage.getParams()[0] & 128) == 128;
    }

    static int getAudioStatusVolume(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.getParams()[0] & Byte.MAX_VALUE;
        if (i < 0 || 100 < i) {
            return -1;
        }
        return i;
    }

    static java.util.List<java.lang.Integer> asImmutableList(int[] iArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(java.lang.Integer.valueOf(i));
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    static int twoBytesToInt(byte[] bArr) {
        return (bArr[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) | ((bArr[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 8);
    }

    static int twoBytesToInt(byte[] bArr, int i) {
        return (bArr[i + 1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) | ((bArr[i] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 8);
    }

    static int threeBytesToInt(byte[] bArr) {
        return (bArr[2] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) | ((bArr[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 16) | ((bArr[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 8);
    }

    static <T> java.util.List<T> sparseArrayToList(android.util.SparseArray<T> sparseArray) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add(sparseArray.valueAt(i));
        }
        return arrayList;
    }

    static <T> java.util.List<T> mergeToUnmodifiableList(java.util.List<T> list, java.util.List<T> list2) {
        if (list.isEmpty() && list2.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        if (list.isEmpty()) {
            return java.util.Collections.unmodifiableList(list2);
        }
        if (list2.isEmpty()) {
            return java.util.Collections.unmodifiableList(list);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(list);
        arrayList.addAll(list2);
        return java.util.Collections.unmodifiableList(arrayList);
    }

    static boolean isAffectingActiveRoutingPath(int i, int i2) {
        int i3 = 0;
        while (true) {
            if (i3 > 12) {
                break;
            }
            if (((i2 >> i3) & 15) == 0) {
                i3 += 4;
            } else {
                i2 &= 65520 << i3;
                break;
            }
        }
        if (i2 == 0) {
            return true;
        }
        return isInActiveRoutingPath(i, i2);
    }

    static boolean isInActiveRoutingPath(int i, int i2) {
        int pathRelationship = pathRelationship(i2, i);
        return pathRelationship == 2 || pathRelationship == 3 || pathRelationship == 5;
    }

    @com.android.server.hdmi.Constants.PathRelationship
    static int pathRelationship(int i, int i2) {
        if (i == 65535 || i2 == 65535) {
            return 0;
        }
        for (int i3 = 0; i3 <= 3; i3++) {
            int i4 = 12 - (i3 * 4);
            int i5 = (i >> i4) & 15;
            int i6 = (i2 >> i4) & 15;
            if (i5 != i6) {
                int i7 = i4 - 4;
                int i8 = (i >> i7) & 15;
                int i9 = (i2 >> i7) & 15;
                if (i5 == 0) {
                    return 2;
                }
                if (i6 == 0) {
                    return 3;
                }
                if (i3 == 3 || (i8 == 0 && i9 == 0)) {
                    return 4;
                }
                return 1;
            }
        }
        return 5;
    }

    static <T> void dumpSparseArray(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, android.util.SparseArray<T> sparseArray) {
        printWithTrailingColon(indentingPrintWriter, str);
        indentingPrintWriter.increaseIndent();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            indentingPrintWriter.printPair(java.lang.Integer.toString(keyAt), sparseArray.get(keyAt));
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
    }

    private static void printWithTrailingColon(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str) {
        if (!str.endsWith(":")) {
            str = str.concat(":");
        }
        indentingPrintWriter.println(str);
    }

    static <K, V> void dumpMap(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.util.Map<K, V> map) {
        printWithTrailingColon(indentingPrintWriter, str);
        indentingPrintWriter.increaseIndent();
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            indentingPrintWriter.printPair(entry.getKey().toString(), entry.getValue());
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
    }

    static <T> void dumpIterable(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Iterable<T> iterable) {
        printWithTrailingColon(indentingPrintWriter, str);
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            indentingPrintWriter.println(it.next());
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static int getLocalPortFromPhysicalAddress(int i, int i2) {
        if (i2 == i) {
            return 0;
        }
        int i3 = 61440;
        int i4 = i2;
        int i5 = 61440;
        while (i4 != 0) {
            i4 = i2 & i5;
            i3 |= i5;
            i5 >>= 4;
        }
        int i6 = i & i3;
        if (((i3 << 4) & i6) != i2) {
            return -1;
        }
        int i7 = i6 & (i5 << 4);
        while (true) {
            int i8 = i7 >> 4;
            if (i8 != 0) {
                i7 = i8;
            } else {
                return i7;
            }
        }
    }

    static int getAbortFeatureOpcode(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
    }

    static int getAbortReason(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return hdmiCecMessage.getParams()[1];
    }

    public static com.android.server.hdmi.HdmiCecMessage buildMessage(java.lang.String str) {
        java.lang.String[] split = str.split(":");
        if (split.length < 2) {
            throw new java.lang.IllegalArgumentException("Message is too short");
        }
        for (java.lang.String str2 : split) {
            if (str2.length() != 2) {
                throw new java.lang.IllegalArgumentException("Malformatted CEC message: " + str);
            }
        }
        int parseInt = java.lang.Integer.parseInt(split[0].substring(0, 1), 16);
        int parseInt2 = java.lang.Integer.parseInt(split[0].substring(1, 2), 16);
        int parseInt3 = java.lang.Integer.parseInt(split[1], 16);
        int length = split.length - 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) java.lang.Integer.parseInt(split[i + 2], 16);
        }
        return com.android.server.hdmi.HdmiCecMessage.build(parseInt, parseInt2, parseInt3, bArr);
    }

    public static int getEndOfSequence(byte[] bArr, int i) {
        if (i < 0) {
            return -1;
        }
        while (i < bArr.length && ((bArr[i] >> 7) & 1) == 1) {
            i++;
        }
        if (i >= bArr.length) {
            return -1;
        }
        return i;
    }

    public static class ShortAudioDescriptorXmlParser {
        private static final java.lang.String NS = null;

        public static java.util.List<com.android.server.hdmi.HdmiUtils.DeviceConfig> parse(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
            resolvePullParser.nextTag();
            return readDevices(resolvePullParser);
        }

        private static void skip(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            if (typedXmlPullParser.getEventType() != 2) {
                throw new java.lang.IllegalStateException();
            }
            int i = 1;
            while (i != 0) {
                switch (typedXmlPullParser.next()) {
                    case 2:
                        i++;
                        break;
                    case 3:
                        i--;
                        break;
                }
            }
        }

        private static java.util.List<com.android.server.hdmi.HdmiUtils.DeviceConfig> readDevices(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            typedXmlPullParser.require(2, NS, "config");
            while (typedXmlPullParser.next() != 3) {
                if (typedXmlPullParser.getEventType() == 2) {
                    if (typedXmlPullParser.getName().equals("device")) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
                        com.android.server.hdmi.HdmiUtils.DeviceConfig readDeviceConfig = attributeValue != null ? readDeviceConfig(typedXmlPullParser, attributeValue) : null;
                        if (readDeviceConfig != null) {
                            arrayList.add(readDeviceConfig);
                        }
                    } else {
                        skip(typedXmlPullParser);
                    }
                }
            }
            return arrayList;
        }

        @android.annotation.Nullable
        private static com.android.server.hdmi.HdmiUtils.DeviceConfig readDeviceConfig(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            typedXmlPullParser.require(2, NS, "device");
            while (typedXmlPullParser.next() != 3) {
                if (typedXmlPullParser.getEventType() == 2) {
                    if (typedXmlPullParser.getName().equals("supportedFormat")) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "format");
                        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "descriptor");
                        int formatNameToNum = attributeValue == null ? 0 : formatNameToNum(attributeValue);
                        byte[] readSad = readSad(attributeValue2);
                        if (formatNameToNum != 0 && readSad != null) {
                            arrayList.add(new com.android.server.hdmi.HdmiUtils.CodecSad(formatNameToNum, readSad));
                        }
                        typedXmlPullParser.nextTag();
                        typedXmlPullParser.require(3, NS, "supportedFormat");
                    } else {
                        skip(typedXmlPullParser);
                    }
                }
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return new com.android.server.hdmi.HdmiUtils.DeviceConfig(str, arrayList);
        }

        @android.annotation.Nullable
        private static byte[] readSad(java.lang.String str) {
            if (str == null || str.length() == 0) {
                return null;
            }
            byte[] hexStringToByteArray = com.android.internal.util.HexDump.hexStringToByteArray(str);
            if (hexStringToByteArray.length != 3) {
                android.util.Slog.w(com.android.server.hdmi.HdmiUtils.TAG, "SAD byte array length is not 3. Length = " + hexStringToByteArray.length);
                return null;
            }
            return hexStringToByteArray;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static int formatNameToNum(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -2131742975:
                    if (str.equals("AUDIO_FORMAT_WMAPRO")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1197237630:
                    if (str.equals("AUDIO_FORMAT_ATRAC")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1194465888:
                    if (str.equals("AUDIO_FORMAT_DTSHD")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1186286867:
                    if (str.equals("AUDIO_FORMAT_MPEG1")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1186286866:
                    if (str.equals("AUDIO_FORMAT_MPEG2")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -358943216:
                    if (str.equals("AUDIO_FORMAT_ONEBITAUDIO")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -282810364:
                    if (str.equals("AUDIO_FORMAT_AAC")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -282807375:
                    if (str.equals("AUDIO_FORMAT_DDP")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -282806906:
                    if (str.equals("AUDIO_FORMAT_DST")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -282806876:
                    if (str.equals("AUDIO_FORMAT_DTS")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -282798811:
                    if (str.equals("AUDIO_FORMAT_MAX")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -282798383:
                    if (str.equals("AUDIO_FORMAT_MP3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -176844499:
                    if (str.equals("AUDIO_FORMAT_LPCM")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -176785545:
                    if (str.equals("AUDIO_FORMAT_NONE")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 129424511:
                    if (str.equals("AUDIO_FORMAT_DD")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2082539401:
                    if (str.equals("AUDIO_FORMAT_TRUEHD")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                    return 6;
                case 7:
                    return 7;
                case '\b':
                    return 8;
                case '\t':
                    return 9;
                case '\n':
                    return 10;
                case 11:
                    return 11;
                case '\f':
                    return 12;
                case '\r':
                    return 13;
                case 14:
                    return 14;
                case 15:
                    return 15;
                default:
                    return 0;
            }
        }
    }

    public static class DeviceConfig {
        public final java.lang.String name;
        public final java.util.List<com.android.server.hdmi.HdmiUtils.CodecSad> supportedCodecs;

        public DeviceConfig(java.lang.String str, java.util.List<com.android.server.hdmi.HdmiUtils.CodecSad> list) {
            this.name = str;
            this.supportedCodecs = list;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.hdmi.HdmiUtils.DeviceConfig)) {
                return false;
            }
            com.android.server.hdmi.HdmiUtils.DeviceConfig deviceConfig = (com.android.server.hdmi.HdmiUtils.DeviceConfig) obj;
            return deviceConfig.name.equals(this.name) && deviceConfig.supportedCodecs.equals(this.supportedCodecs);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.name, java.lang.Integer.valueOf(this.supportedCodecs.hashCode()));
        }
    }

    public static class CodecSad {
        public final int audioCodec;
        public final byte[] sad;

        public CodecSad(int i, byte[] bArr) {
            this.audioCodec = i;
            this.sad = bArr;
        }

        public CodecSad(int i, java.lang.String str) {
            this.audioCodec = i;
            this.sad = com.android.internal.util.HexDump.hexStringToByteArray(str);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.hdmi.HdmiUtils.CodecSad)) {
                return false;
            }
            com.android.server.hdmi.HdmiUtils.CodecSad codecSad = (com.android.server.hdmi.HdmiUtils.CodecSad) obj;
            return codecSad.audioCodec == this.audioCodec && java.util.Arrays.equals(codecSad.sad, this.sad);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.audioCodec), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.sad)));
        }
    }
}
