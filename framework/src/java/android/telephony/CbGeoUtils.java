package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class CbGeoUtils {
    private static final java.lang.String CIRCLE_SYMBOL = "circle";
    public static final int EARTH_RADIUS_METER = 6371000;
    public static final double EPS = 1.0E-7d;
    public static final int GEOMETRY_TYPE_CIRCLE = 3;
    public static final int GEOMETRY_TYPE_POLYGON = 2;
    public static final int GEO_FENCING_MAXIMUM_WAIT_TIME = 1;
    private static final java.lang.String POLYGON_SYMBOL = "polygon";
    private static final java.lang.String TAG = "CbGeoUtils";

    public interface Geometry {
        boolean contains(android.telephony.CbGeoUtils.LatLng latLng);
    }

    private CbGeoUtils() {
    }

    public static class LatLng {
        public final double lat;
        public final double lng;

        public LatLng(double d, double d2) {
            this.lat = d;
            this.lng = d2;
        }

        public android.telephony.CbGeoUtils.LatLng subtract(android.telephony.CbGeoUtils.LatLng latLng) {
            return new android.telephony.CbGeoUtils.LatLng(this.lat - latLng.lat, this.lng - latLng.lng);
        }

        public double distance(android.telephony.CbGeoUtils.LatLng latLng) {
            double sin = java.lang.Math.sin(java.lang.Math.toRadians(this.lat - latLng.lat) * 0.5d);
            double sin2 = java.lang.Math.sin(java.lang.Math.toRadians(this.lng - latLng.lng) * 0.5d);
            double cos = (sin * sin) + (sin2 * sin2 * java.lang.Math.cos(java.lang.Math.toRadians(this.lat)) * java.lang.Math.cos(java.lang.Math.toRadians(latLng.lat)));
            return java.lang.Math.atan2(java.lang.Math.sqrt(cos), java.lang.Math.sqrt(1.0d - cos)) * 2.0d * 6371000.0d;
        }

        public java.lang.String toString() {
            return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.lat + "," + this.lng + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.telephony.CbGeoUtils.LatLng)) {
                return false;
            }
            android.telephony.CbGeoUtils.LatLng latLng = (android.telephony.CbGeoUtils.LatLng) obj;
            return this.lat == latLng.lat && this.lng == latLng.lng;
        }
    }

    public static class Polygon implements android.telephony.CbGeoUtils.Geometry {
        private static final double SCALE = 1000.0d;
        private final android.telephony.CbGeoUtils.LatLng mOrigin;
        private final java.util.List<android.telephony.CbGeoUtils.Polygon.Point> mScaledVertices;
        private final java.util.List<android.telephony.CbGeoUtils.LatLng> mVertices;

        public Polygon(java.util.List<android.telephony.CbGeoUtils.LatLng> list) {
            this.mVertices = list;
            int i = 0;
            for (int i2 = 1; i2 < list.size(); i2++) {
                if (list.get(i2).lng < list.get(i).lng) {
                    i = i2;
                }
            }
            this.mOrigin = list.get(i);
            this.mScaledVertices = (java.util.List) list.stream().map(new java.util.function.Function() { // from class: android.telephony.CbGeoUtils$Polygon$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.telephony.CbGeoUtils.Polygon.Point lambda$new$0;
                    lambda$new$0 = android.telephony.CbGeoUtils.Polygon.this.lambda$new$0((android.telephony.CbGeoUtils.LatLng) obj);
                    return lambda$new$0;
                }
            }).collect(java.util.stream.Collectors.toList());
        }

        public java.util.List<android.telephony.CbGeoUtils.LatLng> getVertices() {
            return this.mVertices;
        }

        @Override // android.telephony.CbGeoUtils.Geometry
        public boolean contains(android.telephony.CbGeoUtils.LatLng latLng) {
            android.telephony.CbGeoUtils.Polygon.Point lambda$new$0 = lambda$new$0(latLng);
            int size = this.mScaledVertices.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                android.telephony.CbGeoUtils.Polygon.Point point = this.mScaledVertices.get(i);
                i++;
                android.telephony.CbGeoUtils.Polygon.Point point2 = this.mScaledVertices.get(i % size);
                int sign = android.telephony.CbGeoUtils.sign(crossProduct(point2.subtract(point), lambda$new$0.subtract(point)));
                if (sign == 0) {
                    if (java.lang.Math.min(point.x, point2.x) <= lambda$new$0.x && lambda$new$0.x <= java.lang.Math.max(point.x, point2.x) && java.lang.Math.min(point.y, point2.y) <= lambda$new$0.y && lambda$new$0.y <= java.lang.Math.max(point.y, point2.y)) {
                        return true;
                    }
                } else if (android.telephony.CbGeoUtils.sign(point.y - lambda$new$0.y) <= 0) {
                    if (sign > 0 && android.telephony.CbGeoUtils.sign(point2.y - lambda$new$0.y) > 0) {
                        i2++;
                    }
                } else if (sign < 0 && android.telephony.CbGeoUtils.sign(point2.y - lambda$new$0.y) <= 0) {
                    i2--;
                }
            }
            return i2 != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: convertAndScaleLatLng, reason: merged with bridge method [inline-methods] */
        public android.telephony.CbGeoUtils.Polygon.Point lambda$new$0(android.telephony.CbGeoUtils.LatLng latLng) {
            double d = latLng.lat - this.mOrigin.lat;
            double d2 = latLng.lng - this.mOrigin.lng;
            if (android.telephony.CbGeoUtils.sign(this.mOrigin.lng) != 0 && android.telephony.CbGeoUtils.sign(this.mOrigin.lng) != android.telephony.CbGeoUtils.sign(latLng.lng)) {
                double abs = java.lang.Math.abs(this.mOrigin.lng) + java.lang.Math.abs(latLng.lng);
                if (android.telephony.CbGeoUtils.sign((2.0d * abs) - 360.0d) > 0) {
                    d2 = android.telephony.CbGeoUtils.sign(this.mOrigin.lng) * (360.0d - abs);
                }
            }
            return new android.telephony.CbGeoUtils.Polygon.Point(d * SCALE, d2 * SCALE);
        }

        private static double crossProduct(android.telephony.CbGeoUtils.Polygon.Point point, android.telephony.CbGeoUtils.Polygon.Point point2) {
            return (point.x * point2.y) - (point.y * point2.x);
        }

        static final class Point {
            public final double x;
            public final double y;

            Point(double d, double d2) {
                this.x = d;
                this.y = d2;
            }

            public android.telephony.CbGeoUtils.Polygon.Point subtract(android.telephony.CbGeoUtils.Polygon.Point point) {
                return new android.telephony.CbGeoUtils.Polygon.Point(this.x - point.x, this.y - point.y);
            }
        }

        public java.lang.String toString() {
            return com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE ? "Polygon: " + this.mVertices : "Polygon: ";
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.telephony.CbGeoUtils.Polygon)) {
                return false;
            }
            android.telephony.CbGeoUtils.Polygon polygon = (android.telephony.CbGeoUtils.Polygon) obj;
            if (this.mVertices.size() != polygon.mVertices.size()) {
                return false;
            }
            for (int i = 0; i < this.mVertices.size(); i++) {
                if (!this.mVertices.get(i).equals(polygon.mVertices.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class Circle implements android.telephony.CbGeoUtils.Geometry {
        private final android.telephony.CbGeoUtils.LatLng mCenter;
        private final double mRadiusMeter;

        public Circle(android.telephony.CbGeoUtils.LatLng latLng, double d) {
            this.mCenter = latLng;
            this.mRadiusMeter = d;
        }

        public android.telephony.CbGeoUtils.LatLng getCenter() {
            return this.mCenter;
        }

        public double getRadius() {
            return this.mRadiusMeter;
        }

        @Override // android.telephony.CbGeoUtils.Geometry
        public boolean contains(android.telephony.CbGeoUtils.LatLng latLng) {
            return this.mCenter.distance(latLng) <= this.mRadiusMeter;
        }

        public java.lang.String toString() {
            return com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE ? "Circle: " + this.mCenter + ", radius = " + this.mRadiusMeter : "Circle: ";
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.telephony.CbGeoUtils.Circle)) {
                return false;
            }
            android.telephony.CbGeoUtils.Circle circle = (android.telephony.CbGeoUtils.Circle) obj;
            return this.mCenter.equals(circle.mCenter) && java.lang.Double.compare(this.mRadiusMeter, circle.mRadiusMeter) == 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static java.util.List<android.telephony.CbGeoUtils.Geometry> parseGeometriesFromString(java.lang.String str) {
        char c;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str2 : str.split("\\s*;\\s*")) {
            java.lang.String[] split = str2.split("\\s*\\|\\s*");
            java.lang.String str3 = split[0];
            switch (str3.hashCode()) {
                case -1360216880:
                    if (str3.equals(CIRCLE_SYMBOL)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -397519558:
                    if (str3.equals(POLYGON_SYMBOL)) {
                        c = 1;
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
                    arrayList.add(new android.telephony.CbGeoUtils.Circle(parseLatLngFromString(split[1]), java.lang.Double.parseDouble(split[2])));
                    break;
                case 1:
                    java.util.ArrayList arrayList2 = new java.util.ArrayList(split.length - 1);
                    for (int i = 1; i < split.length; i++) {
                        arrayList2.add(parseLatLngFromString(split[i]));
                    }
                    arrayList.add(new android.telephony.CbGeoUtils.Polygon(arrayList2));
                    break;
                default:
                    com.android.telephony.Rlog.e(TAG, "Invalid geometry format " + str2);
                    break;
            }
        }
        return arrayList;
    }

    public static java.lang.String encodeGeometriesToString(java.util.List<android.telephony.CbGeoUtils.Geometry> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return (java.lang.String) list.stream().map(new java.util.function.Function() { // from class: android.telephony.CbGeoUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String encodeGeometryToString;
                encodeGeometryToString = android.telephony.CbGeoUtils.encodeGeometryToString((android.telephony.CbGeoUtils.Geometry) obj);
                return encodeGeometryToString;
            }
        }).filter(new java.util.function.Predicate() { // from class: android.telephony.CbGeoUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.telephony.CbGeoUtils.lambda$encodeGeometriesToString$1((java.lang.String) obj);
            }
        }).collect(java.util.stream.Collectors.joining(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR));
    }

    static /* synthetic */ boolean lambda$encodeGeometriesToString$1(java.lang.String str) {
        return !android.text.TextUtils.isEmpty(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String encodeGeometryToString(android.telephony.CbGeoUtils.Geometry geometry) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (geometry instanceof android.telephony.CbGeoUtils.Polygon) {
            sb.append(POLYGON_SYMBOL);
            for (android.telephony.CbGeoUtils.LatLng latLng : ((android.telephony.CbGeoUtils.Polygon) geometry).getVertices()) {
                sb.append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                sb.append(latLng.lat);
                sb.append(",");
                sb.append(latLng.lng);
            }
        } else if (geometry instanceof android.telephony.CbGeoUtils.Circle) {
            sb.append(CIRCLE_SYMBOL);
            android.telephony.CbGeoUtils.Circle circle = (android.telephony.CbGeoUtils.Circle) geometry;
            sb.append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append(circle.getCenter().lat);
            sb.append(",");
            sb.append(circle.getCenter().lng);
            sb.append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append(circle.getRadius());
        } else {
            com.android.telephony.Rlog.e(TAG, "Unsupported geometry object " + geometry);
            return null;
        }
        return sb.toString();
    }

    public static android.telephony.CbGeoUtils.LatLng parseLatLngFromString(java.lang.String str) {
        java.lang.String[] split = str.split("\\s*,\\s*");
        return new android.telephony.CbGeoUtils.LatLng(java.lang.Double.parseDouble(split[0]), java.lang.Double.parseDouble(split[1]));
    }

    public static int sign(double d) {
        if (d > 1.0E-7d) {
            return 1;
        }
        return d < -1.0E-7d ? -1 : 0;
    }
}
