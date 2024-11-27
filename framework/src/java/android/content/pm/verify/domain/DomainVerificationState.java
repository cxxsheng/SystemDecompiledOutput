package android.content.pm.verify.domain;

/* loaded from: classes.dex */
public interface DomainVerificationState {
    public static final int STATE_APPROVED = 2;
    public static final int STATE_DENIED = 3;
    public static final int STATE_FIRST_VERIFIER_DEFINED = 1024;
    public static final int STATE_LEGACY_FAILURE = 6;
    public static final int STATE_MIGRATED = 4;
    public static final int STATE_NO_RESPONSE = 0;
    public static final int STATE_PRE_VERIFIED = 8;
    public static final int STATE_RESTORED = 5;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_SYS_CONFIG = 7;

    public @interface State {
    }

    static java.lang.String stateToDebugString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "verified";
            case 2:
                return "approved";
            case 3:
                return "denied";
            case 4:
                return "migrated";
            case 5:
                return android.provider.VoicemailContract.Voicemails.RESTORED;
            case 6:
                return "legacy_failure";
            case 7:
                return "system_configured";
            case 8:
                return "pre_verified";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    static boolean isDefault(int i) {
        switch (i) {
            case 0:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    static boolean isVerified(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
                return true;
            case 3:
            case 6:
            default:
                return false;
        }
    }

    static boolean isModifiable(int i) {
        switch (i) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            case 8:
                break;
            case 2:
            case 3:
            case 7:
                break;
            default:
                if (i >= 1024) {
                    break;
                }
                break;
        }
        return false;
    }

    static boolean shouldMigrate(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
                return true;
            case 6:
            case 7:
            default:
                return false;
        }
    }

    static int convertToInfoState(int i) {
        if (i >= 1024) {
            return i;
        }
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (!isModifiable(i)) {
            return 2;
        }
        if (isVerified(i)) {
            return 4;
        }
        return 3;
    }
}
