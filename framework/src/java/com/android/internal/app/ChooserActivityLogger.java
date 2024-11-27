package com.android.internal.app;

/* loaded from: classes4.dex */
public interface ChooserActivityLogger {
    com.android.internal.logging.InstanceId getInstanceId();

    void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.internal.logging.InstanceId instanceId);

    void logShareStarted(int i, java.lang.String str, java.lang.String str2, int i2, int i3, boolean z, int i4, java.lang.String str3);

    void logShareTargetSelected(int i, java.lang.String str, int i2, boolean z);

    default void logSharesheetTriggered() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_TRIGGERED, getInstanceId());
    }

    default void logSharesheetAppLoadComplete() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_APP_LOAD_COMPLETE, getInstanceId());
    }

    default void logSharesheetDirectLoadComplete() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_DIRECT_LOAD_COMPLETE, getInstanceId());
    }

    default void logSharesheetDirectLoadTimeout() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_DIRECT_LOAD_TIMEOUT, getInstanceId());
    }

    default void logShareheetProfileChanged() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_PROFILE_CHANGED, getInstanceId());
    }

    default void logSharesheetExpansionChanged(boolean z) {
        log(z ? com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_COLLAPSED : com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_EXPANDED, getInstanceId());
    }

    default void logSharesheetAppShareRankingTimeout() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_APP_SHARE_RANKING_TIMEOUT, getInstanceId());
    }

    default void logSharesheetEmptyDirectShareRow() {
        log(com.android.internal.app.ChooserActivityLogger.SharesheetStandardEvent.SHARESHEET_EMPTY_DIRECT_SHARE_ROW, getInstanceId());
    }

    public enum SharesheetStartedEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        SHARE_STARTED(228);

        private final int mId;

        SharesheetStartedEvent(int i) {
            this.mId = i;
        }

        @Override // com.android.internal.logging.UiEventLogger.UiEventEnum
        public int getId() {
            return this.mId;
        }
    }

    public enum SharesheetTargetSelectedEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        INVALID(0),
        SHARESHEET_SERVICE_TARGET_SELECTED(232),
        SHARESHEET_APP_TARGET_SELECTED(233),
        SHARESHEET_STANDARD_TARGET_SELECTED(234),
        SHARESHEET_COPY_TARGET_SELECTED(235),
        SHARESHEET_NEARBY_TARGET_SELECTED(626),
        SHARESHEET_EDIT_TARGET_SELECTED(669);

        private final int mId;

        SharesheetTargetSelectedEvent(int i) {
            this.mId = i;
        }

        @Override // com.android.internal.logging.UiEventLogger.UiEventEnum
        public int getId() {
            return this.mId;
        }

        public static com.android.internal.app.ChooserActivityLogger.SharesheetTargetSelectedEvent fromTargetType(int i) {
            switch (i) {
                case 1:
                    return SHARESHEET_SERVICE_TARGET_SELECTED;
                case 2:
                    return SHARESHEET_APP_TARGET_SELECTED;
                case 3:
                    return SHARESHEET_STANDARD_TARGET_SELECTED;
                case 4:
                    return SHARESHEET_COPY_TARGET_SELECTED;
                case 5:
                    return SHARESHEET_NEARBY_TARGET_SELECTED;
                case 6:
                    return SHARESHEET_EDIT_TARGET_SELECTED;
                default:
                    return INVALID;
            }
        }
    }

    public enum SharesheetStandardEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        INVALID(0),
        SHARESHEET_TRIGGERED(227),
        SHARESHEET_PROFILE_CHANGED(229),
        SHARESHEET_EXPANDED(230),
        SHARESHEET_COLLAPSED(231),
        SHARESHEET_APP_LOAD_COMPLETE(322),
        SHARESHEET_DIRECT_LOAD_COMPLETE(323),
        SHARESHEET_DIRECT_LOAD_TIMEOUT(324),
        SHARESHEET_APP_SHARE_RANKING_TIMEOUT(com.android.internal.logging.nano.MetricsProto.MetricsEvent.NOTIFICATION_SNOOZED),
        SHARESHEET_EMPTY_DIRECT_SHARE_ROW(com.android.internal.logging.nano.MetricsProto.MetricsEvent.CARRIER_DEMO_MODE_PASSWORD);

        private final int mId;

        SharesheetStandardEvent(int i) {
            this.mId = i;
        }

        @Override // com.android.internal.logging.UiEventLogger.UiEventEnum
        public int getId() {
            return this.mId;
        }
    }

    default int typeFromPreviewInt(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    default int typeFromIntentString(java.lang.String str) {
        char c;
        if (str == null) {
            return 0;
        }
        switch (str.hashCode()) {
            case -1960745709:
                if (str.equals("android.media.action.IMAGE_CAPTURE")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1173683121:
                if (str.equals(android.content.Intent.ACTION_EDIT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1173447682:
                if (str.equals(android.content.Intent.ACTION_MAIN)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1173264947:
                if (str.equals(android.content.Intent.ACTION_SEND)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1173171990:
                if (str.equals("android.intent.action.VIEW")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -58484670:
                if (str.equals(android.content.Intent.ACTION_SEND_MULTIPLE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2068787464:
                if (str.equals(android.content.Intent.ACTION_SENDTO)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return 0;
    }
}
