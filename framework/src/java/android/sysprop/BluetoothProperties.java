package android.sysprop;

/* loaded from: classes3.dex */
public final class BluetoothProperties {
    private BluetoothProperties() {
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

    private static java.lang.Integer tryParseInteger(java.lang.String str) {
        try {
            return java.lang.Integer.valueOf(str);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Integer tryParseUInt(java.lang.String str) {
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

    public enum snoop_default_mode_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FILTERED("filtered"),
        FULL("full");

        private final java.lang.String propValue;

        snoop_default_mode_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.Optional<android.sysprop.BluetoothProperties.snoop_default_mode_values> snoop_default_mode() {
        return java.util.Optional.ofNullable((android.sysprop.BluetoothProperties.snoop_default_mode_values) tryParseEnum(android.sysprop.BluetoothProperties.snoop_default_mode_values.class, android.os.SystemProperties.get("persist.bluetooth.btsnoopdefaultmode")));
    }

    public static void snoop_default_mode(android.sysprop.BluetoothProperties.snoop_default_mode_values snoop_default_mode_valuesVar) {
        android.os.SystemProperties.set("persist.bluetooth.btsnoopdefaultmode", snoop_default_mode_valuesVar == null ? "" : snoop_default_mode_valuesVar.getPropValue());
    }

    public enum snoop_log_mode_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FILTERED("filtered"),
        FULL("full");

        private final java.lang.String propValue;

        snoop_log_mode_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.Optional<android.sysprop.BluetoothProperties.snoop_log_mode_values> snoop_log_mode() {
        return java.util.Optional.ofNullable((android.sysprop.BluetoothProperties.snoop_log_mode_values) tryParseEnum(android.sysprop.BluetoothProperties.snoop_log_mode_values.class, android.os.SystemProperties.get("persist.bluetooth.btsnooplogmode")));
    }

    public static void snoop_log_mode(android.sysprop.BluetoothProperties.snoop_log_mode_values snoop_log_mode_valuesVar) {
        android.os.SystemProperties.set("persist.bluetooth.btsnooplogmode", snoop_log_mode_valuesVar == null ? "" : snoop_log_mode_valuesVar.getPropValue());
    }

    public static java.util.Optional<java.lang.Boolean> snoop_log_filter_snoop_headers_enabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("persist.bluetooth.snooplogfilter.headers.enabled")));
    }

    public static void snoop_log_filter_snoop_headers_enabled(java.lang.Boolean bool) {
        android.os.SystemProperties.set("persist.bluetooth.snooplogfilter.headers.enabled", bool == null ? "" : bool.toString());
    }

    public static java.util.Optional<java.lang.Boolean> snoop_log_filter_profile_a2dp_enabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.a2dp.enabled")));
    }

    public static void snoop_log_filter_profile_a2dp_enabled(java.lang.Boolean bool) {
        android.os.SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.a2dp.enabled", bool == null ? "" : bool.toString());
    }

    public enum snoop_log_filter_profile_map_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FULLFILTER("fullfilter"),
        HEADER(android.provider.Downloads.Impl.RequestHeaders.COLUMN_HEADER),
        MAGIC("magic");

        private final java.lang.String propValue;

        snoop_log_filter_profile_map_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.Optional<android.sysprop.BluetoothProperties.snoop_log_filter_profile_map_values> snoop_log_filter_profile_map() {
        return java.util.Optional.ofNullable((android.sysprop.BluetoothProperties.snoop_log_filter_profile_map_values) tryParseEnum(android.sysprop.BluetoothProperties.snoop_log_filter_profile_map_values.class, android.os.SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.map")));
    }

    public static void snoop_log_filter_profile_map(android.sysprop.BluetoothProperties.snoop_log_filter_profile_map_values snoop_log_filter_profile_map_valuesVar) {
        android.os.SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.map", snoop_log_filter_profile_map_valuesVar == null ? "" : snoop_log_filter_profile_map_valuesVar.getPropValue());
    }

    public enum snoop_log_filter_profile_pbap_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FULLFILTER("fullfilter"),
        HEADER(android.provider.Downloads.Impl.RequestHeaders.COLUMN_HEADER),
        MAGIC("magic");

        private final java.lang.String propValue;

        snoop_log_filter_profile_pbap_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.Optional<android.sysprop.BluetoothProperties.snoop_log_filter_profile_pbap_values> snoop_log_filter_profile_pbap() {
        return java.util.Optional.ofNullable((android.sysprop.BluetoothProperties.snoop_log_filter_profile_pbap_values) tryParseEnum(android.sysprop.BluetoothProperties.snoop_log_filter_profile_pbap_values.class, android.os.SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.pbap")));
    }

    public static void snoop_log_filter_profile_pbap(android.sysprop.BluetoothProperties.snoop_log_filter_profile_pbap_values snoop_log_filter_profile_pbap_valuesVar) {
        android.os.SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.pbap", snoop_log_filter_profile_pbap_valuesVar == null ? "" : snoop_log_filter_profile_pbap_valuesVar.getPropValue());
    }

    public static java.util.Optional<java.lang.Boolean> snoop_log_filter_profile_rfcomm_enabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.rfcomm.enabled")));
    }

    public static void snoop_log_filter_profile_rfcomm_enabled(java.lang.Boolean bool) {
        android.os.SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.rfcomm.enabled", bool == null ? "" : bool.toString());
    }

    public static java.util.Optional<java.lang.Boolean> factory_reset() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("persist.bluetooth.factoryreset")));
    }

    public static void factory_reset(java.lang.Boolean bool) {
        android.os.SystemProperties.set("persist.bluetooth.factoryreset", bool == null ? "" : bool.toString());
    }

    public static java.util.List<java.lang.String> le_audio_allow_list() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.BluetoothProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get("persist.bluetooth.leaudio.allow_list"));
    }

    public static void le_audio_allow_list(java.util.List<java.lang.String> list) {
        android.os.SystemProperties.set("persist.bluetooth.leaudio.allow_list", list == null ? "" : formatList(list));
    }

    public static java.util.Optional<java.lang.Boolean> isGapLePrivacyEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.core.gap.le.privacy.enabled")));
    }

    public static java.util.Optional<java.lang.Integer> getGapLeConnMinLimit() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.core.gap.le.conn.min.limit")));
    }

    public static java.util.Optional<java.lang.Boolean> isGapLeConnOnlyInit1mPhyEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.core.gap.le.conn.only_init_1m_phy.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isLeAudioInbandRingtoneSupported() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.core.le_audio.inband_ringtone.supported")));
    }

    public static java.util.Optional<java.lang.String> getDefaultDeviceName() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get("bluetooth.device.default_name")));
    }

    public static java.util.List<java.lang.Integer> getClassOfDevice() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseUInt;
                tryParseUInt = android.sysprop.BluetoothProperties.tryParseUInt((java.lang.String) obj);
                return tryParseUInt;
            }
        }, android.os.SystemProperties.get("bluetooth.device.class_of_device"));
    }

    public static java.util.Optional<java.lang.Integer> getDefaultOutputOnlyAudioProfile() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.device.default_output_only_audio_profile")));
    }

    public static java.util.Optional<java.lang.Integer> getDefaultDuplexAudioProfile() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.device.default_duplex_audio_profile")));
    }

    public static java.util.Optional<java.lang.Integer> getHardwareOperatingVoltageMv() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.hardware.power.operating_voltage_mv")));
    }

    public static java.util.Optional<java.lang.Integer> getHardwareIdleCurrentMa() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.hardware.power.idle_cur_ma")));
    }

    public static java.util.Optional<java.lang.Integer> getHardwareTxCurrentMa() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.hardware.power.tx_cur_ma")));
    }

    public static java.util.Optional<java.lang.Integer> getHardwareRxCurrentMa() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("bluetooth.hardware.power.rx_cur_ma")));
    }

    public static java.util.Optional<java.lang.Boolean> isSupportPersistedStateEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.framework.support_persisted_state")));
    }

    public static java.util.Optional<java.lang.Boolean> isAdapterAddressValidationEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.framework.adapter_address_validation")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileA2dpSinkEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.a2dp.sink.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileA2dpSourceEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.a2dp.source.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileAshaCentralEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.asha.central.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileAvrcpControllerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.avrcp.controller.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileAvrcpTargetEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.avrcp.target.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileBapBroadcastAssistEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.bap.broadcast.assist.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileBapBroadcastSourceEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.bap.broadcast.source.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileBapUnicastClientEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.bap.unicast.client.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileBasClientEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.bas.client.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileBassClientEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.bass.client.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileCsipSetCoordinatorEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.csip.set_coordinator.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileGattEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.gatt.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileHapClientEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.hap.client.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileHfpAgEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.hfp.ag.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileHfpHfEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.hfp.hf.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileHidDeviceEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.hid.device.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileHidHostEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.hid.host.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileMapClientEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.map.client.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileMapServerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.map.server.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileMcpServerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.mcp.server.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileOppEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.opp.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfilePanNapEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.pan.nap.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfilePanPanuEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.pan.panu.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfilePbapClientEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.pbap.client.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfilePbapServerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.pbap.server.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfilePbapSimEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.pbap.sim.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileSapServerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.sap.server.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileCcpServerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.ccp.server.enabled")));
    }

    public static java.util.Optional<java.lang.Boolean> isProfileVcpControllerEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.profile.vcp.controller.enabled")));
    }

    public static java.util.Optional<java.lang.Integer> getLinkSupervisionTimeout() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.acl.link_supervision_timeout")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicPageScanType() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.page_scan_type")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicPageScanInterval() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.page_scan_interval")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicPageScanWindow() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.page_scan_window")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicInquiryScanType() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.inq_scan_type")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicInquiryScanInterval() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.inq_scan_interval")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicInquiryScanWindow() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.inq_scan_window")));
    }

    public static java.util.Optional<java.lang.Integer> getClassicPageTimeout() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.classic.page_timeout")));
    }

    public static java.util.List<java.lang.Integer> getClassicSniffMaxIntervals() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseUInt;
                tryParseUInt = android.sysprop.BluetoothProperties.tryParseUInt((java.lang.String) obj);
                return tryParseUInt;
            }
        }, android.os.SystemProperties.get("bluetooth.core.classic.sniff_max_intervals"));
    }

    public static java.util.List<java.lang.Integer> getClassicSniffMinIntervals() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseUInt;
                tryParseUInt = android.sysprop.BluetoothProperties.tryParseUInt((java.lang.String) obj);
                return tryParseUInt;
            }
        }, android.os.SystemProperties.get("bluetooth.core.classic.sniff_min_intervals"));
    }

    public static java.util.List<java.lang.Integer> getClassicSniffAttempts() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseUInt;
                tryParseUInt = android.sysprop.BluetoothProperties.tryParseUInt((java.lang.String) obj);
                return tryParseUInt;
            }
        }, android.os.SystemProperties.get("bluetooth.core.classic.sniff_attempts"));
    }

    public static java.util.List<java.lang.Integer> getClassicSniffTimeouts() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer tryParseUInt;
                tryParseUInt = android.sysprop.BluetoothProperties.tryParseUInt((java.lang.String) obj);
                return tryParseUInt;
            }
        }, android.os.SystemProperties.get("bluetooth.core.classic.sniff_timeouts"));
    }

    public static java.util.Optional<java.lang.Integer> getLeMinConnectionInterval() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.min_connection_interval")));
    }

    public static java.util.Optional<java.lang.Integer> getLeMaxConnectionInterval() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.max_connection_interval")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionLatency() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_latency")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionSupervisionTimeout() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_supervision_timeout")));
    }

    public static java.util.Optional<java.lang.Integer> getLeDirectConnectionTimeout() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.direct_connection_timeout")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionScanIntervalFast() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_scan_interval_fast")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionScanWindowFast() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_scan_window_fast")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionScanWindow2mFast() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_scan_window_2m_fast")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionScanWindowCodedFast() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_scan_window_coded_fast")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionScanIntervalSlow() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_scan_interval_slow")));
    }

    public static java.util.Optional<java.lang.Integer> getLeConnectionScanWindowSlow() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.connection_scan_window_slow")));
    }

    public static java.util.Optional<java.lang.Integer> getLeInquiryScanInterval() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.inquiry_scan_interval")));
    }

    public static java.util.Optional<java.lang.Integer> getLeInquiryScanWindow() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.inquiry_scan_window")));
    }

    public static java.util.Optional<java.lang.Boolean> getLeVendorCapabilitiesEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.core.le.vendor_capabilities.enabled")));
    }

    public static java.util.Optional<java.lang.Integer> getLeMaxNumberOfConcurrentConnections() {
        return java.util.Optional.ofNullable(tryParseUInt(android.os.SystemProperties.get("bluetooth.core.le.max_number_of_concurrent_connections")));
    }

    public static java.util.List<java.lang.String> dsa_transport_preference() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String tryParseString;
                tryParseString = android.sysprop.BluetoothProperties.tryParseString((java.lang.String) obj);
                return tryParseString;
            }
        }, android.os.SystemProperties.get("bluetooth.core.le.dsa_transport_preference"));
    }

    public static java.util.Optional<java.lang.Boolean> getDisableEnchancedConnection() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.sco.disable_enhanced_connection")));
    }

    public static java.util.Optional<java.lang.Boolean> isScoManagedByAudioEnabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("bluetooth.sco.managed_by_audio")));
    }
}
