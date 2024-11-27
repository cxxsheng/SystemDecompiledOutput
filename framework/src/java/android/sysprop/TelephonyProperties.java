package android.sysprop;

/* loaded from: classes3.dex */
public final class TelephonyProperties {
    private TelephonyProperties() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static java.lang.Boolean tryParseBoolean(java.lang.String str) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String tryParseString(java.lang.String str) {
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

    public static java.util.Optional<java.lang.Boolean> airplane_mode_on() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("persist.radio.airplane_mode_on")));
    }

    public static void airplane_mode_on(java.lang.Boolean bool) {
        android.os.SystemProperties.set("persist.radio.airplane_mode_on", bool == null ? "" : bool.booleanValue() ? "1" : android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
    }

    public static java.util.List<java.lang.String> baseband_version() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_BASEBAND_VERSION));
    }

    public static void baseband_version(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_BASEBAND_VERSION, list == null ? "" : formatList(list));
    }

    public static java.util.Optional<java.lang.String> ril_impl() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_RIL_IMPL)));
    }

    public static java.util.List<java.lang.String> operator_alpha() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ALPHA));
    }

    public static void operator_alpha(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ALPHA, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> operator_numeric() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_NUMERIC));
    }

    public static void operator_numeric(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_NUMERIC, list == null ? "" : formatList(list));
    }

    public static java.util.Optional<java.lang.Boolean> operator_is_manual() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISMANUAL)));
    }

    public static java.util.List<java.lang.Boolean> operator_is_roaming() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean tryParseBoolean;
                tryParseBoolean = android.sysprop.TelephonyProperties.tryParseBoolean((java.lang.String) obj);
                return tryParseBoolean;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISROAMING));
    }

    public static void operator_is_roaming(java.util.List<java.lang.Boolean> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISROAMING, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> operator_iso_country() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISO_COUNTRY));
    }

    public static void operator_iso_country(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISO_COUNTRY, list == null ? "" : formatList(list));
    }

    public static java.util.Optional<java.lang.String> lte_on_cdma_product_type() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_LTE_ON_CDMA_PRODUCT_TYPE)));
    }

    public static java.util.Optional<java.lang.Integer> lte_on_cdma_device() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_LTE_ON_CDMA_DEVICE)));
    }

    public static java.util.List<java.lang.Integer> current_active_phone() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseInteger;
                tryParseInteger = android.sysprop.TelephonyProperties.tryParseInteger((java.lang.String) obj);
                return tryParseInteger;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.CURRENT_ACTIVE_PHONE));
    }

    public static void current_active_phone(java.util.List<java.lang.Integer> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.CURRENT_ACTIVE_PHONE, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> sim_state() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_SIM_STATE));
    }

    public static void sim_state(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_SIM_STATE, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> icc_operator_numeric() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC));
    }

    public static void icc_operator_numeric(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> icc_operator_alpha() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ALPHA));
    }

    public static void icc_operator_alpha(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ALPHA, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> icc_operator_iso_country() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ISO_COUNTRY));
    }

    public static void icc_operator_iso_country(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ISO_COUNTRY, list == null ? "" : formatList(list));
    }

    public static java.util.List<java.lang.String> data_network_type() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_DATA_NETWORK_TYPE));
    }

    public static void data_network_type(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_DATA_NETWORK_TYPE, list == null ? "" : formatList(list));
    }

    public static java.util.Optional<java.lang.Boolean> in_ecm_mode() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_INECM_MODE)));
    }

    public static void in_ecm_mode(java.lang.Boolean bool) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_INECM_MODE, bool == null ? "" : bool.toString());
    }

    public static java.util.Optional<java.lang.Long> ecm_exit_timer() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ECM_EXIT_TIMER)));
    }

    public static java.util.Optional<java.lang.String> operator_idp_string() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_IDP_STRING)));
    }

    public static void operator_idp_string(java.lang.String str) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_IDP_STRING, str == null ? "" : str.toString());
    }

    public static java.util.List<java.lang.String> otasp_num_schema() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.TelephonyProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OTASP_NUM_SCHEMA));
    }

    public static java.util.Optional<java.lang.Boolean> disable_call() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_DISABLE_CALL)));
    }

    public static java.util.Optional<java.lang.Boolean> ril_sends_multiple_call_ring() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_RIL_SENDS_MULTIPLE_CALL_RING)));
    }

    public static java.util.Optional<java.lang.Integer> call_ring_delay() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_CALL_RING_DELAY)));
    }

    public static java.util.Optional<java.lang.Integer> cdma_msg_id() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_CDMA_MSG_ID)));
    }

    public static void cdma_msg_id(java.lang.Integer num) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_CDMA_MSG_ID, num == null ? "" : num.toString());
    }

    public static java.util.Optional<java.lang.Integer> wake_lock_timeout() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_WAKE_LOCK_TIMEOUT)));
    }

    public static java.util.Optional<java.lang.Boolean> reset_on_radio_tech_change() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_RESET_ON_RADIO_TECH_CHANGE)));
    }

    public static java.util.List<java.lang.Boolean> sms_receive() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean tryParseBoolean;
                tryParseBoolean = android.sysprop.TelephonyProperties.tryParseBoolean((java.lang.String) obj);
                return tryParseBoolean;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_SMS_RECEIVE));
    }

    public static java.util.List<java.lang.Boolean> sms_send() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean tryParseBoolean;
                tryParseBoolean = android.sysprop.TelephonyProperties.tryParseBoolean((java.lang.String) obj);
                return tryParseBoolean;
            }
        }, android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_SMS_SEND));
    }

    public static java.util.Optional<java.lang.Boolean> test_csim() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_TEST_CSIM)));
    }

    public static java.util.Optional<java.lang.Boolean> ignore_nitz() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_IGNORE_NITZ)));
    }

    public static java.util.Optional<java.lang.String> multi_sim_config() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG)));
    }

    public static void multi_sim_config(java.lang.String str) {
        android.os.SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG, str == null ? "" : str.toString());
    }

    public static java.util.Optional<java.lang.Boolean> reboot_on_modem_change() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_REBOOT_REQUIRED_ON_MODEM_CHANGE)));
    }

    public static java.util.Optional<java.lang.Integer> videocall_audio_output() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("persist.radio.call.audio.output")));
    }

    public static java.util.Optional<java.lang.Boolean> enable_esim_ui_by_default() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("esim.enable_esim_system_ui_by_default")));
    }

    public static java.util.List<java.lang.Integer> default_network() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseInteger;
                tryParseInteger = android.sysprop.TelephonyProperties.tryParseInteger((java.lang.String) obj);
                return tryParseInteger;
            }
        }, android.os.SystemProperties.get("ro.telephony.default_network"));
    }

    public static java.util.Optional<java.lang.Boolean> data_roaming() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.com.android.dataroaming")));
    }

    public static java.util.Optional<java.lang.Boolean> mobile_data() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.com.android.mobiledata")));
    }

    public static java.util.Optional<java.lang.Integer> wps_info() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("wifidirect.wps")));
    }

    public static java.util.Optional<java.lang.Integer> max_active_modems() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_MAX_ACTIVE_MODEMS)));
    }

    public static java.util.Optional<java.lang.Integer> sim_slots_count() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.telephony.sim_slots.count")));
    }
}
