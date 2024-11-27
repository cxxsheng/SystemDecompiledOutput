package android.sysprop;

/* loaded from: classes3.dex */
public final class HdmiProperties {
    private HdmiProperties() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.Boolean tryParseBoolean(java.lang.String str) {
        char c;
        if (str == null) {
            return null;
        }
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.US);
        switch (lowerCase.hashCode()) {
            case 48:
                if (lowerCase.equals(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (lowerCase.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (lowerCase.equals("true")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Integer tryParseInteger(java.lang.String str) {
        try {
            return java.lang.Integer.valueOf(str);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    private static java.lang.Integer tryParseUInt(java.lang.String str) {
        try {
            return java.lang.Integer.valueOf(java.lang.Integer.parseUnsignedInt(str));
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    private static java.lang.Long tryParseLong(java.lang.String str) {
        try {
            return java.lang.Long.valueOf(str);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    private static java.lang.Long tryParseULong(java.lang.String str) {
        try {
            return java.lang.Long.valueOf(java.lang.Long.parseUnsignedLong(str));
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    private static java.lang.Double tryParseDouble(java.lang.String str) {
        try {
            return java.lang.Double.valueOf(str);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    private static java.lang.String tryParseString(java.lang.String str) {
        if ("".equals(str)) {
            return null;
        }
        return str;
    }

    private static <T extends java.lang.Enum<T>> T tryParseEnum(java.lang.Class<T> cls, java.lang.String str) {
        try {
            return (T) java.lang.Enum.valueOf(cls, str.toUpperCase(java.util.Locale.US));
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }

    private static <T> java.util.List<T> tryParseList(java.util.function.Function<java.lang.String, T> function, java.lang.String str) {
        if ("".equals(str)) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = 0;
        while (true) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            while (i < str.length() && str.charAt(i) != ',') {
                if (str.charAt(i) == '\\') {
                    i++;
                }
                if (i == str.length()) {
                    break;
                }
                sb.append(str.charAt(i));
                i++;
            }
            arrayList.add(function.apply(sb.toString()));
            if (i != str.length()) {
                i++;
            } else {
                return arrayList;
            }
        }
    }

    private static <T extends java.lang.Enum<T>> java.util.List<T> tryParseEnumList(java.lang.Class<T> cls, java.lang.String str) {
        if ("".equals(str)) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str2 : str.split(",")) {
            arrayList.add(tryParseEnum(cls, str2));
        }
        return arrayList;
    }

    private static java.lang.String escape(java.lang.String str) {
        return str.replaceAll("([\\\\,])", "\\\\$1");
    }

    private static <T> java.lang.String formatList(java.util.List<T> list) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        java.util.Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T next = it.next();
            stringJoiner.add(next == null ? "" : escape(next.toString()));
        }
        return stringJoiner.toString();
    }

    private static java.lang.String formatUIntList(java.util.List<java.lang.Integer> list) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            java.lang.Integer next = it.next();
            stringJoiner.add(next == null ? "" : escape(java.lang.Integer.toUnsignedString(next.intValue())));
        }
        return stringJoiner.toString();
    }

    private static java.lang.String formatULongList(java.util.List<java.lang.Long> list) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        java.util.Iterator<java.lang.Long> it = list.iterator();
        while (it.hasNext()) {
            java.lang.Long next = it.next();
            stringJoiner.add(next == null ? "" : escape(java.lang.Long.toUnsignedString(next.longValue())));
        }
        return stringJoiner.toString();
    }

    private static <T extends java.lang.Enum<T>> java.lang.String formatEnumList(java.util.List<T> list, java.util.function.Function<T, java.lang.String> function) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        java.util.Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T next = it.next();
            stringJoiner.add(next == null ? "" : function.apply(next));
        }
        return stringJoiner.toString();
    }

    @java.lang.Deprecated
    public static java.util.List<java.lang.Integer> device_type() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.HdmiProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseInteger;
                tryParseInteger = android.sysprop.HdmiProperties.tryParseInteger((java.lang.String) obj);
                return tryParseInteger;
            }
        }, android.os.SystemProperties.get("ro.hdmi.device_type"));
    }

    public enum cec_device_types_values {
        TV("tv"),
        RECORDING_DEVICE("recording_device"),
        RESERVED("reserved"),
        TUNER("tuner"),
        PLAYBACK_DEVICE("playback_device"),
        AUDIO_SYSTEM("audio_system"),
        PURE_CEC_SWITCH("pure_cec_switch"),
        VIDEO_PROCESSOR("video_processor");

        private final java.lang.String propValue;

        cec_device_types_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.List<android.sysprop.HdmiProperties.cec_device_types_values> cec_device_types() {
        return tryParseEnumList(android.sysprop.HdmiProperties.cec_device_types_values.class, android.os.SystemProperties.get("ro.hdmi.cec_device_types"));
    }

    public static java.util.Optional<java.lang.String> arc_port() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get("ro.hdmi.property_sytem_audio_device_arc_port")));
    }

    public static java.util.Optional<java.lang.Boolean> forward_volume_keys_when_system_audio_mode_off() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.hdmi.cec_audio_device_forward_volume_keys_system_audio_mode_off")));
    }

    public static java.util.Optional<java.lang.Boolean> is_switch() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.hdmi.property_is_device_hdmi_cec_switch")));
    }

    public enum playback_device_action_on_routing_control_values {
        NONE("none"),
        WAKE_UP_ONLY("wake_up_only"),
        WAKE_UP_AND_SEND_ACTIVE_SOURCE("wake_up_and_send_active_source");

        private final java.lang.String propValue;

        playback_device_action_on_routing_control_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.Optional<android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values> playback_device_action_on_routing_control() {
        return java.util.Optional.ofNullable((android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values) tryParseEnum(android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values.class, android.os.SystemProperties.get("ro.hdmi.cec.source.playback_device_action_on_routing_control")));
    }
}
