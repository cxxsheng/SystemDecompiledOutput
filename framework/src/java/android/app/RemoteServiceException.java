package android.app;

/* loaded from: classes.dex */
public class RemoteServiceException extends android.util.AndroidRuntimeException {
    public RemoteServiceException(java.lang.String str) {
        super(str);
    }

    public RemoteServiceException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }

    public static class ForegroundServiceDidNotStartInTimeException extends android.app.RemoteServiceException {
        private static final java.lang.String KEY_SERVICE_CLASS_NAME = "serviceclassname";
        public static final int TYPE_ID = 1;

        public ForegroundServiceDidNotStartInTimeException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }

        public static android.os.Bundle createExtrasForService(android.content.ComponentName componentName) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(KEY_SERVICE_CLASS_NAME, componentName.getClassName());
            return bundle;
        }

        public static java.lang.String getServiceClassNameFromExtras(android.os.Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            return bundle.getString(KEY_SERVICE_CLASS_NAME);
        }
    }

    public static class CannotPostForegroundServiceNotificationException extends android.app.RemoteServiceException {
        public static final int TYPE_ID = 2;

        public CannotPostForegroundServiceNotificationException(java.lang.String str) {
            super(str);
        }
    }

    public static class BadForegroundServiceNotificationException extends android.app.RemoteServiceException {
        public static final int TYPE_ID = 3;

        public BadForegroundServiceNotificationException(java.lang.String str) {
            super(str);
        }
    }

    public static class BadUserInitiatedJobNotificationException extends android.app.RemoteServiceException {
        public static final int TYPE_ID = 6;

        public BadUserInitiatedJobNotificationException(java.lang.String str) {
            super(str);
        }
    }

    public static class MissingRequestPasswordComplexityPermissionException extends android.app.RemoteServiceException {
        public static final int TYPE_ID = 4;

        public MissingRequestPasswordComplexityPermissionException(java.lang.String str) {
            super(str);
        }
    }

    public static class CrashedByAdbException extends android.app.RemoteServiceException {
        public static final int TYPE_ID = 5;

        public CrashedByAdbException(java.lang.String str) {
            super(str);
        }
    }
}
