package android.app.time;

/* loaded from: classes.dex */
public final class DetectorStatusTypes {
    public static final int DETECTION_ALGORITHM_STATUS_NOT_RUNNING = 2;
    public static final int DETECTION_ALGORITHM_STATUS_NOT_SUPPORTED = 1;
    public static final int DETECTION_ALGORITHM_STATUS_RUNNING = 3;
    public static final int DETECTION_ALGORITHM_STATUS_UNKNOWN = 0;
    public static final int DETECTOR_STATUS_NOT_RUNNING = 2;
    public static final int DETECTOR_STATUS_NOT_SUPPORTED = 1;
    public static final int DETECTOR_STATUS_RUNNING = 3;
    public static final int DETECTOR_STATUS_UNKNOWN = 0;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DetectionAlgorithmStatus {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DetectorStatus {
    }

    private DetectorStatusTypes() {
    }

    public static int requireValidDetectorStatus(int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Invalid detector status: " + i);
        }
        return i;
    }

    public static java.lang.String detectorStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "NOT_SUPPORTED";
            case 2:
                return "NOT_RUNNING";
            case 3:
                return "RUNNING";
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int detectorStatusFromString(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Empty status: " + str);
        }
        switch (str.hashCode()) {
            case -2026200673:
                if (str.equals("RUNNING")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 854821378:
                if (str.equals("NOT_SUPPORTED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2056721427:
                if (str.equals("NOT_RUNNING")) {
                    c = 2;
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
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + str);
        }
    }

    public static int requireValidDetectionAlgorithmStatus(int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Invalid detection algorithm: " + i);
        }
        return i;
    }

    public static java.lang.String detectionAlgorithmStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "NOT_SUPPORTED";
            case 2:
                return "NOT_RUNNING";
            case 3:
                return "RUNNING";
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int detectionAlgorithmStatusFromString(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Empty status: " + str);
        }
        switch (str.hashCode()) {
            case -2026200673:
                if (str.equals("RUNNING")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 854821378:
                if (str.equals("NOT_SUPPORTED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2056721427:
                if (str.equals("NOT_RUNNING")) {
                    c = 2;
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
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + str);
        }
    }
}
