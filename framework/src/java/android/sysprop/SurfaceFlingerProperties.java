package android.sysprop;

/* loaded from: classes3.dex */
public final class SurfaceFlingerProperties {
    private SurfaceFlingerProperties() {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Double tryParseDouble(java.lang.String str) {
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

    public static java.util.Optional<java.lang.Long> vsync_event_phase_offset_ns() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.vsync_event_phase_offset_ns")));
    }

    public static java.util.Optional<java.lang.Long> vsync_sf_event_phase_offset_ns() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.vsync_sf_event_phase_offset_ns")));
    }

    public static java.util.Optional<java.lang.Boolean> use_context_priority() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.use_context_priority")));
    }

    public static java.util.Optional<java.lang.Long> max_frame_buffer_acquired_buffers() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.max_frame_buffer_acquired_buffers")));
    }

    public static java.util.Optional<java.lang.Integer> max_graphics_width() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.max_graphics_width")));
    }

    public static java.util.Optional<java.lang.Integer> max_graphics_height() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.max_graphics_height")));
    }

    public static java.util.Optional<java.lang.Boolean> has_wide_color_display() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.has_wide_color_display")));
    }

    public static java.util.Optional<java.lang.Boolean> running_without_sync_framework() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.running_without_sync_framework")));
    }

    public static java.util.Optional<java.lang.Boolean> has_HDR_display() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.has_HDR_display")));
    }

    public static java.util.Optional<java.lang.Long> present_time_offset_from_vsync_ns() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.present_time_offset_from_vsync_ns")));
    }

    public static java.util.Optional<java.lang.Boolean> force_hwc_copy_for_virtual_displays() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.force_hwc_copy_for_virtual_displays")));
    }

    public static java.util.Optional<java.lang.Long> max_virtual_display_dimension() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.max_virtual_display_dimension")));
    }

    public static java.util.Optional<java.lang.Boolean> use_vr_flinger() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.use_vr_flinger")));
    }

    public static java.util.Optional<java.lang.Boolean> start_graphics_allocator_service() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.start_graphics_allocator_service")));
    }

    public enum primary_display_orientation_values {
        ORIENTATION_0("ORIENTATION_0"),
        ORIENTATION_90("ORIENTATION_90"),
        ORIENTATION_180("ORIENTATION_180"),
        ORIENTATION_270("ORIENTATION_270");

        private final java.lang.String propValue;

        primary_display_orientation_values(java.lang.String str) {
            this.propValue = str;
        }

        public java.lang.String getPropValue() {
            return this.propValue;
        }
    }

    public static java.util.Optional<android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values> primary_display_orientation() {
        return java.util.Optional.ofNullable((android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values) tryParseEnum(android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values.class, android.os.SystemProperties.get("ro.surface_flinger.primary_display_orientation")));
    }

    public static java.util.Optional<java.lang.Boolean> use_color_management() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.use_color_management")));
    }

    public static java.util.Optional<java.lang.Long> default_composition_dataspace() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.default_composition_dataspace")));
    }

    public static java.util.Optional<java.lang.Integer> default_composition_pixel_format() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.default_composition_pixel_format")));
    }

    public static java.util.Optional<java.lang.Long> wcg_composition_dataspace() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.wcg_composition_dataspace")));
    }

    public static java.util.Optional<java.lang.Integer> wcg_composition_pixel_format() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.wcg_composition_pixel_format")));
    }

    public static java.util.Optional<java.lang.Long> color_space_agnostic_dataspace() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.color_space_agnostic_dataspace")));
    }

    public static java.util.List<java.lang.Double> display_primary_red() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.SurfaceFlingerProperties$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Double tryParseDouble;
                tryParseDouble = android.sysprop.SurfaceFlingerProperties.tryParseDouble((java.lang.String) obj);
                return tryParseDouble;
            }
        }, android.os.SystemProperties.get("ro.surface_flinger.display_primary_red"));
    }

    public static java.util.List<java.lang.Double> display_primary_green() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.SurfaceFlingerProperties$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Double tryParseDouble;
                tryParseDouble = android.sysprop.SurfaceFlingerProperties.tryParseDouble((java.lang.String) obj);
                return tryParseDouble;
            }
        }, android.os.SystemProperties.get("ro.surface_flinger.display_primary_green"));
    }

    public static java.util.List<java.lang.Double> display_primary_blue() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.SurfaceFlingerProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Double tryParseDouble;
                tryParseDouble = android.sysprop.SurfaceFlingerProperties.tryParseDouble((java.lang.String) obj);
                return tryParseDouble;
            }
        }, android.os.SystemProperties.get("ro.surface_flinger.display_primary_blue"));
    }

    public static java.util.List<java.lang.Double> display_primary_white() {
        return tryParseList(new java.util.function.Function() { // from class: android.sysprop.SurfaceFlingerProperties$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Double tryParseDouble;
                tryParseDouble = android.sysprop.SurfaceFlingerProperties.tryParseDouble((java.lang.String) obj);
                return tryParseDouble;
            }
        }, android.os.SystemProperties.get("ro.surface_flinger.display_primary_white"));
    }

    @java.lang.Deprecated
    public static java.util.Optional<java.lang.Boolean> refresh_rate_switching() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.refresh_rate_switching")));
    }

    public static java.util.Optional<java.lang.Integer> set_idle_timer_ms() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.set_idle_timer_ms")));
    }

    public static java.util.Optional<java.lang.Integer> set_touch_timer_ms() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.set_touch_timer_ms")));
    }

    public static java.util.Optional<java.lang.Integer> set_display_power_timer_ms() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.set_display_power_timer_ms")));
    }

    public static java.util.Optional<java.lang.Boolean> use_content_detection_for_refresh_rate() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.use_content_detection_for_refresh_rate")));
    }

    @java.lang.Deprecated
    public static java.util.Optional<java.lang.Boolean> use_smart_90_for_video() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.use_smart_90_for_video")));
    }

    public static java.util.Optional<java.lang.Boolean> enable_protected_contents() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.protected_contents")));
    }

    public static java.util.Optional<java.lang.Boolean> support_kernel_idle_timer() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.support_kernel_idle_timer")));
    }

    public static java.util.Optional<java.lang.Boolean> supports_background_blur() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.supports_background_blur")));
    }

    public static java.util.Optional<java.lang.Integer> display_update_imminent_timeout_ms() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.display_update_imminent_timeout_ms")));
    }

    public static java.util.Optional<java.lang.Boolean> update_device_product_info_on_hotplug_reconnect() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.update_device_product_info_on_hotplug_reconnect")));
    }

    public static java.util.Optional<java.lang.Boolean> enable_frame_rate_override() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.enable_frame_rate_override")));
    }

    public static java.util.Optional<java.lang.Boolean> enable_layer_caching() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.enable_layer_caching")));
    }

    public static java.util.Optional<java.lang.Boolean> enable_sdr_dimming() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.enable_sdr_dimming")));
    }

    public static java.util.Optional<java.lang.Boolean> ignore_hdr_camera_layers() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.ignore_hdr_camera_layers")));
    }

    public static java.util.Optional<java.lang.Long> min_acquired_buffers() {
        return java.util.Optional.ofNullable(tryParseLong(android.os.SystemProperties.get("ro.surface_flinger.min_acquired_buffers")));
    }

    public static java.util.Optional<java.lang.Boolean> clear_slots_with_set_layer_buffer() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("ro.surface_flinger.clear_slots_with_set_layer_buffer")));
    }

    public static java.util.Optional<java.lang.Integer> game_default_frame_rate_override() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("ro.surface_flinger.game_default_frame_rate_override")));
    }
}
