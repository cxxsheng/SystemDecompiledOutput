package android.media;

/* loaded from: classes2.dex */
public class MediaCasException extends java.lang.Exception {
    private MediaCasException(java.lang.String str) {
        super(str);
    }

    static void throwExceptionIfNeeded(int i) throws android.media.MediaCasException {
        if (i == 0) {
            return;
        }
        if (i == 7) {
            throw new android.media.MediaCasException.NotProvisionedException(null);
        }
        if (i == 8) {
            throw new android.media.MediaCasException.ResourceBusyException(null);
        }
        if (i == 11) {
            throw new android.media.MediaCasException.DeniedByServerException(null);
        }
        android.media.MediaCasStateException.throwExceptionIfNeeded(i);
    }

    public static final class UnsupportedCasException extends android.media.MediaCasException {
        public UnsupportedCasException(java.lang.String str) {
            super(str);
        }
    }

    public static final class NotProvisionedException extends android.media.MediaCasException {
        public NotProvisionedException(java.lang.String str) {
            super(str);
        }
    }

    public static final class DeniedByServerException extends android.media.MediaCasException {
        public DeniedByServerException(java.lang.String str) {
            super(str);
        }
    }

    public static final class ResourceBusyException extends android.media.MediaCasException {
        public ResourceBusyException(java.lang.String str) {
            super(str);
        }
    }

    public static final class InsufficientResourceException extends android.media.MediaCasException {
        public InsufficientResourceException(java.lang.String str) {
            super(str);
        }
    }
}
