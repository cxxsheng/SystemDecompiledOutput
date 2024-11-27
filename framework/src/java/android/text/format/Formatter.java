package android.text.format;

/* loaded from: classes3.dex */
public final class Formatter {
    public static final int FLAG_CALCULATE_ROUNDED = 2;
    public static final int FLAG_IEC_UNITS = 8;
    public static final int FLAG_SHORTER = 1;
    public static final int FLAG_SI_UNITS = 4;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final android.icu.text.UnicodeSetSpanner SPACES_AND_CONTROLS = new android.icu.text.UnicodeSetSpanner(new android.icu.text.UnicodeSet("[[:Zs:][:Cf:]]").freeze());

    public static class BytesResult {
        public final long roundedBytes;
        public final java.lang.String units;
        public final java.lang.String value;

        public BytesResult(java.lang.String str, java.lang.String str2, long j) {
            this.value = str;
            this.units = str2;
            this.roundedBytes = j;
        }
    }

    private static java.util.Locale localeFromContext(android.content.Context context) {
        return context.getResources().getConfiguration().getLocales().get(0);
    }

    private static java.lang.String bidiWrap(android.content.Context context, java.lang.String str) {
        if (android.text.TextUtils.getLayoutDirectionFromLocale(localeFromContext(context)) == 1) {
            return android.text.BidiFormatter.getInstance(true).unicodeWrap(str);
        }
        return str;
    }

    public static java.lang.String formatFileSize(android.content.Context context, long j) {
        return formatFileSize(context, j, 4);
    }

    public static java.lang.String formatFileSize(android.content.Context context, long j, int i) {
        if (context == null) {
            return "";
        }
        return bidiWrap(context, formatRoundedBytesResult(context, android.text.format.Formatter.RoundedBytesResult.roundBytes(j, i)));
    }

    public static java.lang.String formatShortFileSize(android.content.Context context, long j) {
        return formatFileSize(context, j, 5);
    }

    private static java.lang.String getByteSuffixOverride(android.content.res.Resources resources) {
        return resources.getString(com.android.internal.R.string.byteShort);
    }

    private static android.icu.text.NumberFormat getNumberFormatter(java.util.Locale locale, int i) {
        android.icu.text.NumberFormat numberFormat = android.icu.text.NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(i);
        numberFormat.setMaximumFractionDigits(i);
        numberFormat.setGroupingUsed(false);
        if (numberFormat instanceof android.icu.text.DecimalFormat) {
            numberFormat.setRoundingMode(4);
        }
        return numberFormat;
    }

    private static java.lang.String deleteFirstFromString(java.lang.String str, java.lang.String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return str;
        }
        return str.substring(0, indexOf) + str.substring(indexOf + str2.length(), str.length());
    }

    private static java.lang.String formatMeasureShort(java.util.Locale locale, android.icu.text.NumberFormat numberFormat, float f, android.icu.util.MeasureUnit measureUnit) {
        return android.icu.text.MeasureFormat.getInstance(locale, android.icu.text.MeasureFormat.FormatWidth.SHORT, numberFormat).format(new android.icu.util.Measure(java.lang.Float.valueOf(f), measureUnit));
    }

    private static java.lang.String formatRoundedBytesResult(android.content.Context context, android.text.format.Formatter.RoundedBytesResult roundedBytesResult) {
        java.util.Locale localeFromContext = localeFromContext(context);
        android.icu.text.NumberFormat numberFormatter = getNumberFormatter(localeFromContext, roundedBytesResult.fractionDigits);
        if (roundedBytesResult.units == android.icu.util.MeasureUnit.BYTE) {
            return context.getString(com.android.internal.R.string.fileSizeSuffix, numberFormatter.format(roundedBytesResult.value), getByteSuffixOverride(context.getResources()));
        }
        return formatMeasureShort(localeFromContext, numberFormatter, roundedBytesResult.value, roundedBytesResult.units);
    }

    public static class RoundedBytesResult {
        public final int fractionDigits;
        public final long roundedBytes;
        public final android.icu.util.MeasureUnit units;
        public final float value;

        private RoundedBytesResult(float f, android.icu.util.MeasureUnit measureUnit, int i, long j) {
            this.value = f;
            this.units = measureUnit;
            this.fractionDigits = i;
            this.roundedBytes = j;
        }

        public static android.text.format.Formatter.RoundedBytesResult roundBytes(long j, int i) {
            long j2;
            android.icu.util.MeasureUnit measureUnit;
            int i2;
            long round;
            long j3 = j;
            int i3 = (i & 8) != 0 ? 1024 : 1000;
            int i4 = 1;
            boolean z = j3 < 0;
            if (z) {
                j3 = -j3;
            }
            float f = j3;
            android.icu.util.MeasureUnit measureUnit2 = android.icu.util.MeasureUnit.BYTE;
            if (f <= 900.0f) {
                j2 = 1;
            } else {
                measureUnit2 = android.icu.util.MeasureUnit.KILOBYTE;
                j2 = i3;
                f /= i3;
            }
            if (f > 900.0f) {
                measureUnit2 = android.icu.util.MeasureUnit.MEGABYTE;
                j2 *= i3;
                f /= i3;
            }
            if (f > 900.0f) {
                measureUnit2 = android.icu.util.MeasureUnit.GIGABYTE;
                j2 *= i3;
                f /= i3;
            }
            if (f > 900.0f) {
                measureUnit2 = android.icu.util.MeasureUnit.TERABYTE;
                j2 *= i3;
                f /= i3;
            }
            if (f <= 900.0f) {
                measureUnit = measureUnit2;
            } else {
                j2 *= i3;
                f /= i3;
                measureUnit = android.icu.util.MeasureUnit.PETABYTE;
            }
            if (j2 == 1 || f >= 100.0f) {
                i2 = 0;
            } else if (f < 1.0f) {
                i2 = 2;
                i4 = 100;
            } else if (f < 10.0f) {
                if ((i & 1) != 0) {
                    i2 = 1;
                    i4 = 10;
                } else {
                    i2 = 2;
                    i4 = 100;
                }
            } else if ((i & 1) != 0) {
                i2 = 0;
            } else {
                i2 = 2;
                i4 = 100;
            }
            if (z) {
                f = -f;
            }
            if ((i & 2) == 0) {
                round = 0;
            } else {
                round = (java.lang.Math.round(i4 * f) * j2) / i4;
            }
            return new android.text.format.Formatter.RoundedBytesResult(f, measureUnit, i2, round);
        }
    }

    public static android.text.format.Formatter.BytesResult formatBytes(android.content.res.Resources resources, long j, int i) {
        java.lang.String charSequence;
        android.text.format.Formatter.RoundedBytesResult roundBytes = android.text.format.Formatter.RoundedBytesResult.roundBytes(j, i);
        java.util.Locale locale = resources.getConfiguration().getLocales().get(0);
        android.icu.text.NumberFormat numberFormatter = getNumberFormatter(locale, roundBytes.fractionDigits);
        java.lang.String format = numberFormatter.format(roundBytes.value);
        if (roundBytes.units == android.icu.util.MeasureUnit.BYTE) {
            charSequence = getByteSuffixOverride(resources);
        } else {
            charSequence = SPACES_AND_CONTROLS.trim(deleteFirstFromString(formatMeasureShort(locale, numberFormatter, roundBytes.value, roundBytes.units), format)).toString();
        }
        return new android.text.format.Formatter.BytesResult(format, charSequence, roundBytes.roundedBytes);
    }

    @java.lang.Deprecated
    public static java.lang.String formatIpAddress(int i) {
        return com.android.net.module.util.Inet4AddressUtils.intToInet4AddressHTL(i).getHostAddress();
    }

    public static java.lang.String formatShortElapsedTime(android.content.Context context, long j) {
        int i;
        int i2;
        int i3;
        long j2 = j / 1000;
        if (j2 < 86400) {
            i = 0;
        } else {
            i = (int) (j2 / 86400);
            j2 -= SECONDS_PER_DAY * i;
        }
        if (j2 < 3600) {
            i2 = 0;
        } else {
            i2 = (int) (j2 / 3600);
            j2 -= i2 * 3600;
        }
        if (j2 < 60) {
            i3 = 0;
        } else {
            i3 = (int) (j2 / 60);
            j2 -= i3 * 60;
        }
        int i4 = (int) j2;
        android.icu.text.MeasureFormat measureFormat = android.icu.text.MeasureFormat.getInstance(localeFromContext(context), android.icu.text.MeasureFormat.FormatWidth.SHORT);
        if (i >= 2 || (i > 0 && i2 == 0)) {
            return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf(i + ((i2 + 12) / 24)), android.icu.util.MeasureUnit.DAY));
        }
        if (i > 0) {
            return measureFormat.formatMeasures(new android.icu.util.Measure(java.lang.Integer.valueOf(i), android.icu.util.MeasureUnit.DAY), new android.icu.util.Measure(java.lang.Integer.valueOf(i2), android.icu.util.MeasureUnit.HOUR));
        }
        if (i2 >= 2 || (i2 > 0 && i3 == 0)) {
            return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf(i2 + ((i3 + 30) / 60)), android.icu.util.MeasureUnit.HOUR));
        }
        if (i2 > 0) {
            return measureFormat.formatMeasures(new android.icu.util.Measure(java.lang.Integer.valueOf(i2), android.icu.util.MeasureUnit.HOUR), new android.icu.util.Measure(java.lang.Integer.valueOf(i3), android.icu.util.MeasureUnit.MINUTE));
        }
        if (i3 >= 2 || (i3 > 0 && i4 == 0)) {
            return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf(i3 + ((i4 + 30) / 60)), android.icu.util.MeasureUnit.MINUTE));
        }
        if (i3 > 0) {
            return measureFormat.formatMeasures(new android.icu.util.Measure(java.lang.Integer.valueOf(i3), android.icu.util.MeasureUnit.MINUTE), new android.icu.util.Measure(java.lang.Integer.valueOf(i4), android.icu.util.MeasureUnit.SECOND));
        }
        return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf(i4), android.icu.util.MeasureUnit.SECOND));
    }

    public static java.lang.String formatShortElapsedTimeRoundingUpToMinutes(android.content.Context context, long j) {
        long j2 = ((j + 60000) - 1) / 60000;
        if (j2 == 0 || j2 == 1) {
            return android.icu.text.MeasureFormat.getInstance(localeFromContext(context), android.icu.text.MeasureFormat.FormatWidth.SHORT).format(new android.icu.util.Measure(java.lang.Long.valueOf(j2), android.icu.util.MeasureUnit.MINUTE));
        }
        return formatShortElapsedTime(context, j2 * 60000);
    }
}
