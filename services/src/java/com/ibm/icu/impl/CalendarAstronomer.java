package com.ibm.icu.impl;

/* loaded from: classes3.dex */
public class CalendarAstronomer {
    public static final long DAY_MS = 86400000;
    private static final double DEG_RAD = 0.017453292519943295d;
    static final long EPOCH_2000_MS = 946598400000L;
    public static final int HOUR_MS = 3600000;
    private static final double INVALID = Double.MIN_VALUE;
    static final double JD_EPOCH = 2447891.5d;
    public static final long JULIAN_EPOCH_MS = -210866760000000L;
    public static final int MINUTE_MS = 60000;
    private static final double PI = 3.141592653589793d;
    private static final double PI2 = 6.283185307179586d;
    private static final double RAD_DEG = 57.29577951308232d;
    private static final double RAD_HOUR = 3.819718634205488d;
    public static final int SECOND_MS = 1000;
    public static final double SIDEREAL_DAY = 23.93446960027d;
    public static final double SIDEREAL_MONTH = 27.32166d;
    public static final double SIDEREAL_YEAR = 365.25636d;
    public static final double SOLAR_DAY = 24.065709816d;
    static final double SUN_E = 0.016713d;
    static final double SUN_ETA_G = 4.87650757829735d;
    static final double SUN_OMEGA_G = 4.935239984568769d;
    public static final double SYNODIC_MONTH = 29.530588853d;
    public static final double TROPICAL_YEAR = 365.242191d;
    static final double moonA = 384401.0d;
    static final double moonE = 0.0549d;
    static final double moonI = 0.08980357792017056d;
    static final double moonL0 = 5.556284436750021d;
    static final double moonN0 = 5.559050068029439d;
    static final double moonP0 = 0.6342598060246725d;
    static final double moonPi = 0.016592845198710092d;
    static final double moonT0 = 0.009042550854582622d;
    private transient double eclipObliquity;
    private long fGmtOffset;
    private double fLatitude;
    private double fLongitude;
    private transient double julianCentury;
    private transient double julianDay;
    private transient double meanAnomalySun;
    private transient double moonEclipLong;
    private transient double moonLongitude;
    private transient com.ibm.icu.impl.CalendarAstronomer.Equatorial moonPosition;
    private transient double siderealT0;
    private transient double siderealTime;
    private transient double sunLongitude;
    private long time;
    public static final com.ibm.icu.impl.CalendarAstronomer.SolarLongitude VERNAL_EQUINOX = new com.ibm.icu.impl.CalendarAstronomer.SolarLongitude(0.0d);
    public static final com.ibm.icu.impl.CalendarAstronomer.SolarLongitude SUMMER_SOLSTICE = new com.ibm.icu.impl.CalendarAstronomer.SolarLongitude(1.5707963267948966d);
    public static final com.ibm.icu.impl.CalendarAstronomer.SolarLongitude AUTUMN_EQUINOX = new com.ibm.icu.impl.CalendarAstronomer.SolarLongitude(3.141592653589793d);
    public static final com.ibm.icu.impl.CalendarAstronomer.SolarLongitude WINTER_SOLSTICE = new com.ibm.icu.impl.CalendarAstronomer.SolarLongitude(4.71238898038469d);
    public static final com.ibm.icu.impl.CalendarAstronomer.MoonAge NEW_MOON = new com.ibm.icu.impl.CalendarAstronomer.MoonAge(0.0d);
    public static final com.ibm.icu.impl.CalendarAstronomer.MoonAge FIRST_QUARTER = new com.ibm.icu.impl.CalendarAstronomer.MoonAge(1.5707963267948966d);
    public static final com.ibm.icu.impl.CalendarAstronomer.MoonAge FULL_MOON = new com.ibm.icu.impl.CalendarAstronomer.MoonAge(3.141592653589793d);
    public static final com.ibm.icu.impl.CalendarAstronomer.MoonAge LAST_QUARTER = new com.ibm.icu.impl.CalendarAstronomer.MoonAge(4.71238898038469d);

    private interface AngleFunc {
        double eval();
    }

    private interface CoordFunc {
        com.ibm.icu.impl.CalendarAstronomer.Equatorial eval();
    }

    public CalendarAstronomer() {
        this(java.lang.System.currentTimeMillis());
    }

    public CalendarAstronomer(java.util.Date date) {
        this(date.getTime());
    }

    public CalendarAstronomer(long j) {
        this.fLongitude = 0.0d;
        this.fLatitude = 0.0d;
        this.fGmtOffset = 0L;
        this.julianDay = INVALID;
        this.julianCentury = INVALID;
        this.sunLongitude = INVALID;
        this.meanAnomalySun = INVALID;
        this.moonLongitude = INVALID;
        this.moonEclipLong = INVALID;
        this.eclipObliquity = INVALID;
        this.siderealT0 = INVALID;
        this.siderealTime = INVALID;
        this.moonPosition = null;
        this.time = j;
    }

    public CalendarAstronomer(double d, double d2) {
        this();
        this.fLongitude = normPI(d * DEG_RAD);
        this.fLatitude = normPI(d2 * DEG_RAD);
        this.fGmtOffset = (long) (((this.fLongitude * 24.0d) * 3600000.0d) / 6.283185307179586d);
    }

    public void setTime(long j) {
        this.time = j;
        clearCache();
    }

    public void setDate(java.util.Date date) {
        setTime(date.getTime());
    }

    public void setJulianDay(double d) {
        this.time = ((long) (8.64E7d * d)) + JULIAN_EPOCH_MS;
        clearCache();
        this.julianDay = d;
    }

    public long getTime() {
        return this.time;
    }

    public java.util.Date getDate() {
        return new java.util.Date(this.time);
    }

    public double getJulianDay() {
        if (this.julianDay == INVALID) {
            this.julianDay = (this.time - JULIAN_EPOCH_MS) / 8.64E7d;
        }
        return this.julianDay;
    }

    public double getJulianCentury() {
        if (this.julianCentury == INVALID) {
            this.julianCentury = (getJulianDay() - 2415020.0d) / 36525.0d;
        }
        return this.julianCentury;
    }

    public double getGreenwichSidereal() {
        if (this.siderealTime == INVALID) {
            this.siderealTime = normalize(getSiderealOffset() + (normalize(this.time / 3600000.0d, 24.0d) * 1.002737909d), 24.0d);
        }
        return this.siderealTime;
    }

    private double getSiderealOffset() {
        if (this.siderealT0 == INVALID) {
            double floor = ((java.lang.Math.floor(getJulianDay() - 0.5d) + 0.5d) - 2451545.0d) / 36525.0d;
            this.siderealT0 = normalize((2400.051336d * floor) + 6.697374558d + (2.5862E-5d * floor * floor), 24.0d);
        }
        return this.siderealT0;
    }

    public double getLocalSidereal() {
        return normalize(getGreenwichSidereal() + (this.fGmtOffset / 3600000.0d), 24.0d);
    }

    private long lstToUT(double d) {
        return ((((this.time + this.fGmtOffset) / 86400000) * 86400000) - this.fGmtOffset) + ((long) (normalize((d - getSiderealOffset()) * 0.9972695663d, 24.0d) * 3600000.0d));
    }

    public final com.ibm.icu.impl.CalendarAstronomer.Equatorial eclipticToEquatorial(com.ibm.icu.impl.CalendarAstronomer.Ecliptic ecliptic) {
        return eclipticToEquatorial(ecliptic.longitude, ecliptic.latitude);
    }

    public final com.ibm.icu.impl.CalendarAstronomer.Equatorial eclipticToEquatorial(double d, double d2) {
        double eclipticObliquity = eclipticObliquity();
        double sin = java.lang.Math.sin(eclipticObliquity);
        double cos = java.lang.Math.cos(eclipticObliquity);
        double sin2 = java.lang.Math.sin(d);
        return new com.ibm.icu.impl.CalendarAstronomer.Equatorial(java.lang.Math.atan2((sin2 * cos) - (java.lang.Math.tan(d2) * sin), java.lang.Math.cos(d)), java.lang.Math.asin((java.lang.Math.sin(d2) * cos) + (java.lang.Math.cos(d2) * sin * sin2)));
    }

    public final com.ibm.icu.impl.CalendarAstronomer.Equatorial eclipticToEquatorial(double d) {
        return eclipticToEquatorial(d, 0.0d);
    }

    public com.ibm.icu.impl.CalendarAstronomer.Horizon eclipticToHorizon(double d) {
        com.ibm.icu.impl.CalendarAstronomer.Equatorial eclipticToEquatorial = eclipticToEquatorial(d);
        double localSidereal = ((getLocalSidereal() * 3.141592653589793d) / 12.0d) - eclipticToEquatorial.ascension;
        double sin = java.lang.Math.sin(localSidereal);
        double cos = java.lang.Math.cos(localSidereal);
        double sin2 = java.lang.Math.sin(eclipticToEquatorial.declination);
        double cos2 = java.lang.Math.cos(eclipticToEquatorial.declination);
        double sin3 = java.lang.Math.sin(this.fLatitude);
        double cos3 = java.lang.Math.cos(this.fLatitude);
        double asin = java.lang.Math.asin((sin2 * sin3) + (cos2 * cos3 * cos));
        return new com.ibm.icu.impl.CalendarAstronomer.Horizon(java.lang.Math.atan2((-cos2) * cos3 * sin, sin2 - (sin3 * java.lang.Math.sin(asin))), asin);
    }

    public double getSunLongitude() {
        if (this.sunLongitude == INVALID) {
            double[] sunLongitude = getSunLongitude(getJulianDay());
            this.sunLongitude = sunLongitude[0];
            this.meanAnomalySun = sunLongitude[1];
        }
        return this.sunLongitude;
    }

    double[] getSunLongitude(double d) {
        double norm2PI = norm2PI((norm2PI((d - JD_EPOCH) * 0.017202791632524146d) + SUN_ETA_G) - SUN_OMEGA_G);
        return new double[]{norm2PI(trueAnomaly(norm2PI, SUN_E) + SUN_OMEGA_G), norm2PI};
    }

    public com.ibm.icu.impl.CalendarAstronomer.Equatorial getSunPosition() {
        return eclipticToEquatorial(getSunLongitude(), 0.0d);
    }

    private static class SolarLongitude {
        double value;

        SolarLongitude(double d) {
            this.value = d;
        }
    }

    public long getSunTime(double d, boolean z) {
        return timeOfAngle(new com.ibm.icu.impl.CalendarAstronomer.AngleFunc() { // from class: com.ibm.icu.impl.CalendarAstronomer.1
            @Override // com.ibm.icu.impl.CalendarAstronomer.AngleFunc
            public double eval() {
                return com.ibm.icu.impl.CalendarAstronomer.this.getSunLongitude();
            }
        }, d, 365.242191d, 60000L, z);
    }

    public long getSunTime(com.ibm.icu.impl.CalendarAstronomer.SolarLongitude solarLongitude, boolean z) {
        return getSunTime(solarLongitude.value, z);
    }

    public long getSunRiseSet(boolean z) {
        long j = this.time;
        setTime(((((this.time + this.fGmtOffset) / 86400000) * 86400000) - this.fGmtOffset) + 43200000 + ((z ? -6L : 6L) * 3600000));
        long riseOrSet = riseOrSet(new com.ibm.icu.impl.CalendarAstronomer.CoordFunc() { // from class: com.ibm.icu.impl.CalendarAstronomer.2
            @Override // com.ibm.icu.impl.CalendarAstronomer.CoordFunc
            public com.ibm.icu.impl.CalendarAstronomer.Equatorial eval() {
                return com.ibm.icu.impl.CalendarAstronomer.this.getSunPosition();
            }
        }, z, 0.009302604913129777d, 0.009890199094634533d, 5000L);
        setTime(j);
        return riseOrSet;
    }

    public com.ibm.icu.impl.CalendarAstronomer.Equatorial getMoonPosition() {
        if (this.moonPosition == null) {
            double sunLongitude = getSunLongitude();
            double julianDay = getJulianDay() - JD_EPOCH;
            double norm2PI = norm2PI((0.22997150421858628d * julianDay) + moonL0);
            double norm2PI2 = norm2PI((norm2PI - (0.001944368345221015d * julianDay)) - moonP0);
            double sin = java.lang.Math.sin(((norm2PI - sunLongitude) * 2.0d) - norm2PI2) * 0.022233749341155764d;
            double sin2 = java.lang.Math.sin(this.meanAnomalySun) * 0.003242821750205464d;
            double sin3 = norm2PI2 + ((sin - sin2) - (java.lang.Math.sin(this.meanAnomalySun) * 0.00645771823237902d));
            this.moonLongitude = (((norm2PI + sin) + (java.lang.Math.sin(sin3) * 0.10975677534091541d)) - sin2) + (java.lang.Math.sin(sin3 * 2.0d) * 0.0037350045992678655d);
            this.moonLongitude += java.lang.Math.sin((this.moonLongitude - sunLongitude) * 2.0d) * 0.011489502465878671d;
            double norm2PI3 = norm2PI(moonN0 - (julianDay * 9.242199067718253E-4d)) - (java.lang.Math.sin(this.meanAnomalySun) * 0.0027925268031909274d);
            double sin4 = java.lang.Math.sin(this.moonLongitude - norm2PI3);
            this.moonEclipLong = java.lang.Math.atan2(java.lang.Math.cos(moonI) * sin4, java.lang.Math.cos(this.moonLongitude - norm2PI3)) + norm2PI3;
            this.moonPosition = eclipticToEquatorial(this.moonEclipLong, java.lang.Math.asin(sin4 * java.lang.Math.sin(moonI)));
        }
        return this.moonPosition;
    }

    public double getMoonAge() {
        getMoonPosition();
        return norm2PI(this.moonEclipLong - this.sunLongitude);
    }

    public double getMoonPhase() {
        return (1.0d - java.lang.Math.cos(getMoonAge())) * 0.5d;
    }

    private static class MoonAge {
        double value;

        MoonAge(double d) {
            this.value = d;
        }
    }

    public long getMoonTime(double d, boolean z) {
        return timeOfAngle(new com.ibm.icu.impl.CalendarAstronomer.AngleFunc() { // from class: com.ibm.icu.impl.CalendarAstronomer.3
            @Override // com.ibm.icu.impl.CalendarAstronomer.AngleFunc
            public double eval() {
                return com.ibm.icu.impl.CalendarAstronomer.this.getMoonAge();
            }
        }, d, 29.530588853d, 60000L, z);
    }

    public long getMoonTime(com.ibm.icu.impl.CalendarAstronomer.MoonAge moonAge, boolean z) {
        return getMoonTime(moonAge.value, z);
    }

    public long getMoonRiseSet(boolean z) {
        return riseOrSet(new com.ibm.icu.impl.CalendarAstronomer.CoordFunc() { // from class: com.ibm.icu.impl.CalendarAstronomer.4
            @Override // com.ibm.icu.impl.CalendarAstronomer.CoordFunc
            public com.ibm.icu.impl.CalendarAstronomer.Equatorial eval() {
                return com.ibm.icu.impl.CalendarAstronomer.this.getMoonPosition();
            }
        }, z, 0.009302604913129777d, 0.009890199094634533d, 60000L);
    }

    private long timeOfAngle(com.ibm.icu.impl.CalendarAstronomer.AngleFunc angleFunc, double d, double d2, long j, boolean z) {
        double eval = angleFunc.eval();
        double d3 = 8.64E7d * d2;
        double norm2PI = ((norm2PI(d - eval) + (z ? 0.0d : -6.283185307179586d)) * d3) / 6.283185307179586d;
        long j2 = this.time;
        setTime(this.time + ((long) norm2PI));
        while (true) {
            double eval2 = angleFunc.eval();
            double abs = java.lang.Math.abs(norm2PI / normPI(eval2 - eval)) * normPI(d - eval2);
            if (java.lang.Math.abs(abs) > java.lang.Math.abs(norm2PI)) {
                long j3 = (long) (d3 / 8.0d);
                if (!z) {
                    j3 = -j3;
                }
                setTime(j2 + j3);
                return timeOfAngle(angleFunc, d, d2, j, z);
            }
            setTime(this.time + ((long) abs));
            if (java.lang.Math.abs(abs) > j) {
                norm2PI = abs;
                eval = eval2;
            } else {
                return this.time;
            }
        }
    }

    private long riseOrSet(com.ibm.icu.impl.CalendarAstronomer.CoordFunc coordFunc, boolean z, double d, double d2, long j) {
        com.ibm.icu.impl.CalendarAstronomer.Equatorial eval;
        long j2;
        double tan = java.lang.Math.tan(this.fLatitude);
        int i = 0;
        do {
            eval = coordFunc.eval();
            double acos = java.lang.Math.acos((-tan) * java.lang.Math.tan(eval.declination));
            if (z) {
                acos = 6.283185307179586d - acos;
            }
            long lstToUT = lstToUT(((acos + eval.ascension) * 24.0d) / 6.283185307179586d);
            j2 = lstToUT - this.time;
            setTime(lstToUT);
            i++;
            if (i >= 5) {
                break;
            }
        } while (java.lang.Math.abs(j2) > j);
        double cos = java.lang.Math.cos(eval.declination);
        long asin = (long) ((((java.lang.Math.asin(java.lang.Math.sin((d / 2.0d) + d2) / java.lang.Math.sin(java.lang.Math.acos(java.lang.Math.sin(this.fLatitude) / cos))) * 240.0d) * RAD_DEG) / cos) * 1000.0d);
        long j3 = this.time;
        if (z) {
            asin = -asin;
        }
        return j3 + asin;
    }

    private static final double normalize(double d, double d2) {
        return d - (d2 * java.lang.Math.floor(d / d2));
    }

    private static final double norm2PI(double d) {
        return normalize(d, 6.283185307179586d);
    }

    private static final double normPI(double d) {
        return normalize(d + 3.141592653589793d, 6.283185307179586d) - 3.141592653589793d;
    }

    private double trueAnomaly(double d, double d2) {
        double sin;
        double d3 = d;
        do {
            sin = (d3 - (java.lang.Math.sin(d3) * d2)) - d;
            d3 -= sin / (1.0d - (java.lang.Math.cos(d3) * d2));
        } while (java.lang.Math.abs(sin) > 1.0E-5d);
        return java.lang.Math.atan(java.lang.Math.tan(d3 / 2.0d) * java.lang.Math.sqrt((d2 + 1.0d) / (1.0d - d2))) * 2.0d;
    }

    private double eclipticObliquity() {
        if (this.eclipObliquity == INVALID) {
            double julianDay = (getJulianDay() - 2451545.0d) / 36525.0d;
            this.eclipObliquity = ((23.439292d - (0.013004166666666666d * julianDay)) - ((1.6666666666666665E-7d * julianDay) * julianDay)) + (5.027777777777778E-7d * julianDay * julianDay * julianDay);
            this.eclipObliquity *= DEG_RAD;
        }
        return this.eclipObliquity;
    }

    private void clearCache() {
        this.julianDay = INVALID;
        this.julianCentury = INVALID;
        this.sunLongitude = INVALID;
        this.meanAnomalySun = INVALID;
        this.moonLongitude = INVALID;
        this.moonEclipLong = INVALID;
        this.eclipObliquity = INVALID;
        this.siderealTime = INVALID;
        this.siderealT0 = INVALID;
        this.moonPosition = null;
    }

    public java.lang.String local(long j) {
        return new java.util.Date(j - java.util.TimeZone.getDefault().getRawOffset()).toString();
    }

    public static final class Ecliptic {
        public final double latitude;
        public final double longitude;

        public Ecliptic(double d, double d2) {
            this.latitude = d;
            this.longitude = d2;
        }

        public java.lang.String toString() {
            return java.lang.Double.toString(this.longitude * com.ibm.icu.impl.CalendarAstronomer.RAD_DEG) + "," + (this.latitude * com.ibm.icu.impl.CalendarAstronomer.RAD_DEG);
        }
    }

    public static final class Equatorial {
        public final double ascension;
        public final double declination;

        public Equatorial(double d, double d2) {
            this.ascension = d;
            this.declination = d2;
        }

        public java.lang.String toString() {
            return java.lang.Double.toString(this.ascension * com.ibm.icu.impl.CalendarAstronomer.RAD_DEG) + "," + (this.declination * com.ibm.icu.impl.CalendarAstronomer.RAD_DEG);
        }

        public java.lang.String toHmsString() {
            return com.ibm.icu.impl.CalendarAstronomer.radToHms(this.ascension) + "," + com.ibm.icu.impl.CalendarAstronomer.radToDms(this.declination);
        }
    }

    public static final class Horizon {
        public final double altitude;
        public final double azimuth;

        public Horizon(double d, double d2) {
            this.altitude = d;
            this.azimuth = d2;
        }

        public java.lang.String toString() {
            return java.lang.Double.toString(this.altitude * com.ibm.icu.impl.CalendarAstronomer.RAD_DEG) + "," + (this.azimuth * com.ibm.icu.impl.CalendarAstronomer.RAD_DEG);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String radToHms(double d) {
        double d2 = d * RAD_HOUR;
        int i = (int) d2;
        double d3 = d2 - i;
        int i2 = (int) (d3 * 60.0d);
        return java.lang.Integer.toString(i) + "h" + i2 + "m" + ((int) ((d3 - (i2 / 60.0d)) * 3600.0d)) + "s";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String radToDms(double d) {
        double d2 = d * RAD_DEG;
        int i = (int) d2;
        double d3 = d2 - i;
        int i2 = (int) (d3 * 60.0d);
        return java.lang.Integer.toString(i) + "°" + i2 + "'" + ((int) ((d3 - (i2 / 60.0d)) * 3600.0d)) + "\"";
    }
}
