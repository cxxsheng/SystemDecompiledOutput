package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class ExceptionUtils {
    public static android.hardware.camera2.CameraAccessException throwAsPublicException(android.os.ServiceSpecificException serviceSpecificException) throws android.hardware.camera2.CameraAccessException {
        int i;
        switch (serviceSpecificException.errorCode) {
            case 1:
                throw new java.lang.SecurityException(serviceSpecificException.getMessage(), serviceSpecificException);
            case 2:
            case 3:
                throw new java.lang.IllegalArgumentException(serviceSpecificException.getMessage(), serviceSpecificException);
            case 4:
                i = 2;
                break;
            case 5:
            default:
                i = 3;
                break;
            case 6:
                i = 1;
                break;
            case 7:
                i = 4;
                break;
            case 8:
                i = 5;
                break;
            case 9:
                i = 1000;
                break;
        }
        throw new android.hardware.camera2.CameraAccessException(i, serviceSpecificException.getMessage(), serviceSpecificException);
    }

    public static android.hardware.camera2.CameraAccessException throwAsPublicException(android.os.RemoteException remoteException) throws android.hardware.camera2.CameraAccessException {
        if (remoteException instanceof android.os.DeadObjectException) {
            throw new android.hardware.camera2.CameraAccessException(2, "Camera service has died unexpectedly", remoteException);
        }
        throw new java.lang.UnsupportedOperationException("An unknown RemoteException was thrown which should never happen.", remoteException);
    }

    private ExceptionUtils() {
    }
}
