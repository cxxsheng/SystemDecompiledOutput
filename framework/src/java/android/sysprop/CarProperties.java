package android.sysprop;

/* loaded from: classes3.dex */
public final class CarProperties {
    private CarProperties() {
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

    public static java.util.Optional<java.lang.Integer> boot_user_override_id() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("android.car.systemuser.bootuseroverrideid")));
    }

    public static void boot_user_override_id(java.lang.Integer num) {
        android.os.SystemProperties.set("android.car.systemuser.bootuseroverrideid", num == null ? "" : num.toString());
    }

    public static java.util.Optional<java.lang.String> trusted_device_device_name_prefix() {
        return java.util.Optional.ofNullable(tryParseString(android.os.SystemProperties.get("ro.android.car.trusteddevice.device_name_prefix")));
    }

    public static java.util.Optional<java.lang.Integer> number_pre_created_users() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("android.car.number_pre_created_users")));
    }

    public static void number_pre_created_users(java.lang.Integer num) {
        android.os.SystemProperties.set("android.car.number_pre_created_users", num == null ? "" : num.toString());
    }

    public static java.util.Optional<java.lang.Integer> number_pre_created_guests() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("android.car.number_pre_created_guests")));
    }

    public static void number_pre_created_guests(java.lang.Integer num) {
        android.os.SystemProperties.set("android.car.number_pre_created_guests", num == null ? "" : num.toString());
    }

    public static java.util.Optional<java.lang.Boolean> user_hal_enabled() {
        return java.util.Optional.ofNullable(tryParseBoolean(android.os.SystemProperties.get("android.car.user_hal_enabled")));
    }

    public static void user_hal_enabled(java.lang.Boolean bool) {
        android.os.SystemProperties.set("android.car.user_hal_enabled", bool == null ? "" : bool.toString());
    }

    public static java.util.Optional<java.lang.Integer> user_hal_timeout() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("android.car.user_hal_timeout")));
    }

    public static void user_hal_timeout(java.lang.Integer num) {
        android.os.SystemProperties.set("android.car.user_hal_timeout", num == null ? "" : num.toString());
    }

    public static java.util.Optional<java.lang.Integer> device_policy_manager_timeout() {
        return java.util.Optional.ofNullable(tryParseInteger(android.os.SystemProperties.get("android.car.device_policy_manager_timeout")));
    }

    public static void device_policy_manager_timeout(java.lang.Integer num) {
        android.os.SystemProperties.set("android.car.device_policy_manager_timeout", num == null ? "" : num.toString());
    }
}
