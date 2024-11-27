package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class ContextHubIntentEvent {
    private final int mClientAuthorizationState;
    private final android.hardware.location.ContextHubInfo mContextHubInfo;
    private final int mEventType;
    private final int mNanoAppAbortCode;
    private final long mNanoAppId;
    private final android.hardware.location.NanoAppMessage mNanoAppMessage;

    private ContextHubIntentEvent(android.hardware.location.ContextHubInfo contextHubInfo, int i, long j, android.hardware.location.NanoAppMessage nanoAppMessage, int i2, int i3) {
        this.mContextHubInfo = contextHubInfo;
        this.mEventType = i;
        this.mNanoAppId = j;
        this.mNanoAppMessage = nanoAppMessage;
        this.mNanoAppAbortCode = i2;
        this.mClientAuthorizationState = i3;
    }

    private ContextHubIntentEvent(android.hardware.location.ContextHubInfo contextHubInfo, int i) {
        this(contextHubInfo, i, -1L, null, -1, 0);
    }

    private ContextHubIntentEvent(android.hardware.location.ContextHubInfo contextHubInfo, int i, long j) {
        this(contextHubInfo, i, j, null, -1, 0);
    }

    private ContextHubIntentEvent(android.hardware.location.ContextHubInfo contextHubInfo, int i, long j, android.hardware.location.NanoAppMessage nanoAppMessage) {
        this(contextHubInfo, i, j, nanoAppMessage, -1, 0);
    }

    private ContextHubIntentEvent(android.hardware.location.ContextHubInfo contextHubInfo, int i, long j, int i2) {
        this(contextHubInfo, i, j, null, i2, 0);
    }

    public static android.hardware.location.ContextHubIntentEvent fromIntent(android.content.Intent intent) {
        java.util.Objects.requireNonNull(intent, "Intent cannot be null");
        hasExtraOrThrow(intent, android.hardware.location.ContextHubManager.EXTRA_CONTEXT_HUB_INFO);
        android.hardware.location.ContextHubInfo contextHubInfo = (android.hardware.location.ContextHubInfo) intent.getParcelableExtra(android.hardware.location.ContextHubManager.EXTRA_CONTEXT_HUB_INFO, android.hardware.location.ContextHubInfo.class);
        if (contextHubInfo == null) {
            throw new java.lang.IllegalArgumentException("ContextHubInfo extra was null");
        }
        int intExtraOrThrow = getIntExtraOrThrow(intent, android.hardware.location.ContextHubManager.EXTRA_EVENT_TYPE);
        switch (intExtraOrThrow) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
                long longExtraOrThrow = getLongExtraOrThrow(intent, android.hardware.location.ContextHubManager.EXTRA_NANOAPP_ID);
                if (intExtraOrThrow == 5) {
                    hasExtraOrThrow(intent, android.hardware.location.ContextHubManager.EXTRA_MESSAGE);
                    android.hardware.location.NanoAppMessage nanoAppMessage = (android.hardware.location.NanoAppMessage) intent.getParcelableExtra(android.hardware.location.ContextHubManager.EXTRA_MESSAGE, android.hardware.location.NanoAppMessage.class);
                    if (nanoAppMessage == null) {
                        throw new java.lang.IllegalArgumentException("NanoAppMessage extra was null");
                    }
                    return new android.hardware.location.ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow, nanoAppMessage);
                }
                if (intExtraOrThrow == 4) {
                    return new android.hardware.location.ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow, getIntExtraOrThrow(intent, android.hardware.location.ContextHubManager.EXTRA_NANOAPP_ABORT_CODE));
                }
                if (intExtraOrThrow == 7) {
                    return new android.hardware.location.ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow, null, -1, getIntExtraOrThrow(intent, android.hardware.location.ContextHubManager.EXTRA_CLIENT_AUTHORIZATION_STATE));
                }
                return new android.hardware.location.ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow);
            case 6:
                return new android.hardware.location.ContextHubIntentEvent(contextHubInfo, intExtraOrThrow);
            default:
                throw new java.lang.IllegalArgumentException("Unknown intent event type " + intExtraOrThrow);
        }
    }

    public int getEventType() {
        return this.mEventType;
    }

    public android.hardware.location.ContextHubInfo getContextHubInfo() {
        return this.mContextHubInfo;
    }

    public long getNanoAppId() {
        if (this.mEventType == 6) {
            throw new java.lang.UnsupportedOperationException("Cannot invoke getNanoAppId() on Context Hub reset event");
        }
        return this.mNanoAppId;
    }

    public int getNanoAppAbortCode() {
        if (this.mEventType != 4) {
            throw new java.lang.UnsupportedOperationException("Cannot invoke getNanoAppAbortCode() on non-abort event: " + this.mEventType);
        }
        return this.mNanoAppAbortCode;
    }

    public android.hardware.location.NanoAppMessage getNanoAppMessage() {
        if (this.mEventType != 5) {
            throw new java.lang.UnsupportedOperationException("Cannot invoke getNanoAppMessage() on non-message event: " + this.mEventType);
        }
        return this.mNanoAppMessage;
    }

    public int getClientAuthorizationState() {
        if (this.mEventType != 7) {
            throw new java.lang.UnsupportedOperationException("Cannot invoke getClientAuthorizationState() on non-authorization event: " + this.mEventType);
        }
        return this.mClientAuthorizationState;
    }

    public java.lang.String toString() {
        java.lang.String str = "ContextHubIntentEvent[eventType = " + this.mEventType + ", contextHubId = " + this.mContextHubInfo.getId();
        if (this.mEventType != 6) {
            str = str + ", nanoAppId = 0x" + java.lang.Long.toHexString(this.mNanoAppId);
        }
        if (this.mEventType == 4) {
            str = str + ", nanoAppAbortCode = " + this.mNanoAppAbortCode;
        }
        if (this.mEventType == 5) {
            str = str + ", nanoAppMessage = " + this.mNanoAppMessage;
        }
        if (this.mEventType == 7) {
            str = str + ", clientAuthState = " + this.mClientAuthorizationState;
        }
        return str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(java.lang.Object obj) {
        boolean z;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.location.ContextHubIntentEvent)) {
            return false;
        }
        android.hardware.location.ContextHubIntentEvent contextHubIntentEvent = (android.hardware.location.ContextHubIntentEvent) obj;
        if (contextHubIntentEvent.getEventType() != this.mEventType || !contextHubIntentEvent.getContextHubInfo().equals(this.mContextHubInfo)) {
            return false;
        }
        try {
            if (this.mEventType != 6) {
                z = (contextHubIntentEvent.getNanoAppId() == this.mNanoAppId) & true;
            } else {
                z = true;
            }
            if (this.mEventType == 4) {
                z &= contextHubIntentEvent.getNanoAppAbortCode() == this.mNanoAppAbortCode;
            }
            if (this.mEventType == 5) {
                z &= contextHubIntentEvent.getNanoAppMessage().equals(this.mNanoAppMessage);
            }
            if (this.mEventType == 7) {
                return z & (contextHubIntentEvent.getClientAuthorizationState() == this.mClientAuthorizationState);
            }
            return z;
        } catch (java.lang.UnsupportedOperationException e) {
            return false;
        }
    }

    private static void hasExtraOrThrow(android.content.Intent intent, java.lang.String str) {
        if (!intent.hasExtra(str)) {
            throw new java.lang.IllegalArgumentException("Intent did not have extra: " + str);
        }
    }

    private static int getIntExtraOrThrow(android.content.Intent intent, java.lang.String str) {
        hasExtraOrThrow(intent, str);
        return intent.getIntExtra(str, -1);
    }

    private static long getLongExtraOrThrow(android.content.Intent intent, java.lang.String str) {
        hasExtraOrThrow(intent, str);
        return intent.getLongExtra(str, -1L);
    }
}
