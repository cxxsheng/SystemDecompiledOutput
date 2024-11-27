package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public final class AidlConversionUtils {
    private static final java.lang.String TAG = "AidlConversionUtils";

    private AidlConversionUtils() {
    }

    public static int toFrameworkError(byte b) {
        switch (b) {
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
                return 8;
            case 8:
                return 16;
            default:
                return 17;
        }
    }

    public static int toFrameworkAcquiredInfo(byte b) {
        switch (b) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            case 9:
                return 8;
            case 10:
                return 9;
            case 11:
                return 10;
            case 12:
                return 11;
            case 13:
                return 12;
            case 14:
                return 13;
            case 15:
                return 14;
            case 16:
                return 15;
            case 17:
                return 16;
            case 18:
                return 17;
            case 19:
                return 18;
            case 20:
                return 19;
            case 21:
                return 20;
            case 22:
                return 21;
            case 23:
                return 22;
            case 24:
                return 24;
            case 25:
                return 25;
            case 26:
                return 26;
            default:
                return 23;
        }
    }

    public static int toFrameworkEnrollmentStage(int i) {
        switch (i) {
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
            default:
                return 0;
        }
    }

    @android.annotation.NonNull
    public static android.hardware.face.FaceAuthenticationFrame toFrameworkAuthenticationFrame(@android.annotation.NonNull android.hardware.biometrics.face.AuthenticationFrame authenticationFrame) {
        return new android.hardware.face.FaceAuthenticationFrame(toFrameworkBaseFrame(authenticationFrame.data));
    }

    @android.annotation.NonNull
    public static android.hardware.face.FaceEnrollFrame toFrameworkEnrollmentFrame(@android.annotation.NonNull android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame) {
        return new android.hardware.face.FaceEnrollFrame(toFrameworkCell(enrollmentFrame.cell), toFrameworkEnrollmentStage(enrollmentFrame.stage), toFrameworkBaseFrame(enrollmentFrame.data));
    }

    @android.annotation.NonNull
    public static android.hardware.face.FaceDataFrame toFrameworkBaseFrame(@android.annotation.NonNull android.hardware.biometrics.face.BaseFrame baseFrame) {
        return new android.hardware.face.FaceDataFrame(toFrameworkAcquiredInfo(baseFrame.acquiredInfo), baseFrame.vendorCode, baseFrame.pan, baseFrame.tilt, baseFrame.distance, baseFrame.isCancellable);
    }

    @android.annotation.Nullable
    public static android.hardware.face.FaceEnrollCell toFrameworkCell(@android.annotation.Nullable android.hardware.biometrics.face.Cell cell) {
        if (cell == null) {
            return null;
        }
        return new android.hardware.face.FaceEnrollCell(cell.x, cell.y, cell.z);
    }

    public static byte convertFrameworkToAidlFeature(int i) throws java.lang.IllegalArgumentException {
        switch (i) {
            case 1:
                return (byte) 0;
            case 2:
                return (byte) 1;
            default:
                android.util.Slog.e(TAG, "Unsupported feature : " + i);
                throw new java.lang.IllegalArgumentException();
        }
    }

    public static int convertAidlToFrameworkFeature(byte b) throws java.lang.IllegalArgumentException {
        switch (b) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                android.util.Slog.e(TAG, "Unsupported feature : " + ((int) b));
                throw new java.lang.IllegalArgumentException();
        }
    }
}
