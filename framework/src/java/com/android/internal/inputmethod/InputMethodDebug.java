package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodDebug {
    private InputMethodDebug() {
    }

    public static java.lang.String startInputReasonToString(int i) {
        switch (i) {
            case 0:
                return "UNSPECIFIED";
            case 1:
                return "WINDOW_FOCUS_GAIN";
            case 2:
                return "WINDOW_FOCUS_GAIN_REPORT_ONLY";
            case 3:
                return "SCHEDULED_CHECK_FOCUS";
            case 4:
                return "APP_CALLED_RESTART_INPUT_API";
            case 5:
                return "CHECK_FOCUS";
            case 6:
                return "BOUND_TO_IMMS";
            case 7:
                return "UNBOUND_FROM_IMMS";
            case 8:
                return "ACTIVATED_BY_IMMS";
            case 9:
                return "DEACTIVATED_BY_IMMS";
            case 10:
                return "SESSION_CREATED_BY_IME";
            case 11:
            default:
                return "Unknown=" + i;
            case 12:
                return "BOUND_ACCESSIBILITY_SESSION_TO_IMMS";
        }
    }

    public static java.lang.String unbindReasonToString(int i) {
        switch (i) {
            case 0:
                return "UNSPECIFIED";
            case 1:
                return "SWITCH_CLIENT";
            case 2:
                return "SWITCH_IME";
            case 3:
                return "DISCONNECT_IME";
            case 4:
                return "NO_IME";
            case 5:
                return "SWITCH_IME_FAILED";
            case 6:
                return "SWITCH_USER";
            default:
                return "Unknown=" + i;
        }
    }

    public static java.lang.String softInputModeToString(int i) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        int i2 = i & 15;
        int i3 = i & 240;
        boolean z = (i & 256) != 0;
        switch (i2) {
            case 0:
                stringJoiner.add("STATE_UNSPECIFIED");
                break;
            case 1:
                stringJoiner.add("STATE_UNCHANGED");
                break;
            case 2:
                stringJoiner.add("STATE_HIDDEN");
                break;
            case 3:
                stringJoiner.add("STATE_ALWAYS_HIDDEN");
                break;
            case 4:
                stringJoiner.add("STATE_VISIBLE");
                break;
            case 5:
                stringJoiner.add("STATE_ALWAYS_VISIBLE");
                break;
            default:
                stringJoiner.add("STATE_UNKNOWN(" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                break;
        }
        switch (i3) {
            case 0:
                stringJoiner.add("ADJUST_UNSPECIFIED");
                break;
            case 16:
                stringJoiner.add("ADJUST_RESIZE");
                break;
            case 32:
                stringJoiner.add("ADJUST_PAN");
                break;
            case 48:
                stringJoiner.add("ADJUST_NOTHING");
                break;
            default:
                stringJoiner.add("ADJUST_UNKNOWN(" + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                break;
        }
        if (z) {
            stringJoiner.add("IS_FORWARD_NAVIGATION");
        }
        return stringJoiner.setEmptyValue("(none)").toString();
    }

    public static java.lang.String startInputFlagsToString(int i) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if ((i & 1) != 0) {
            stringJoiner.add("VIEW_HAS_FOCUS");
        }
        if ((i & 2) != 0) {
            stringJoiner.add("IS_TEXT_EDITOR");
        }
        if ((i & 4) != 0) {
            stringJoiner.add("INITIAL_CONNECTION");
        }
        return stringJoiner.setEmptyValue("(none)").toString();
    }

    public static java.lang.String softInputDisplayReasonToString(int i) {
        switch (i) {
            case 0:
                return "NOT_SET";
            case 1:
                return "SHOW_SOFT_INPUT";
            case 2:
                return "ATTACH_NEW_INPUT";
            case 3:
                return "SHOW_SOFT_INPUT_FROM_IME";
            case 4:
                return "HIDE_SOFT_INPUT";
            case 5:
                return "HIDE_SOFT_INPUT_FROM_IME";
            case 6:
                return "SHOW_AUTO_EDITOR_FORWARD_NAV";
            case 7:
                return "SHOW_STATE_VISIBLE_FORWARD_NAV";
            case 8:
                return "SHOW_STATE_ALWAYS_VISIBLE";
            case 9:
                return "SHOW_SETTINGS_ON_CHANGE";
            case 10:
                return "HIDE_SWITCH_USER";
            case 11:
                return "HIDE_INVALID_USER";
            case 12:
                return "HIDE_UNSPECIFIED_WINDOW";
            case 13:
                return "HIDE_STATE_HIDDEN_FORWARD_NAV";
            case 14:
                return "HIDE_ALWAYS_HIDDEN_STATE";
            case 15:
                return "HIDE_RESET_SHELL_COMMAND";
            case 16:
                return "HIDE_SETTINGS_ON_CHANGE";
            case 17:
                return "HIDE_POWER_BUTTON_GO_HOME";
            case 18:
                return "HIDE_DOCKED_STACK_ATTACHED";
            case 19:
                return "HIDE_RECENTS_ANIMATION";
            case 20:
                return "HIDE_BUBBLES";
            case 21:
                return "HIDE_SAME_WINDOW_FOCUSED_WITHOUT_EDITOR";
            case 22:
                return "HIDE_REMOVE_CLIENT";
            case 23:
                return "SHOW_RESTORE_IME_VISIBILITY";
            case 24:
                return "SHOW_TOGGLE_SOFT_INPUT";
            case 25:
                return "HIDE_TOGGLE_SOFT_INPUT";
            case 26:
                return "SHOW_SOFT_INPUT_BY_INSETS_API";
            case 27:
                return "HIDE_DISPLAY_IME_POLICY_HIDE";
            case 28:
                return "HIDE_SOFT_INPUT_BY_INSETS_API";
            case 29:
                return "HIDE_SOFT_INPUT_BY_BACK_KEY";
            case 30:
                return "HIDE_SOFT_INPUT_IME_TOGGLE_SOFT_INPUT";
            case 31:
                return "HIDE_SOFT_INPUT_EXTRACT_INPUT_CHANGED";
            case 32:
                return "HIDE_SOFT_INPUT_IMM_DEPRECATION";
            case 33:
                return "HIDE_WINDOW_GAINED_FOCUS_WITHOUT_EDITOR";
            case 34:
                return "SHOW_IME_SCREENSHOT_FROM_IMMS";
            case 35:
                return "REMOVE_IME_SCREENSHOT_FROM_IMMS";
            case 36:
            default:
                return "Unknown=" + i;
            case 37:
                return "HIDE_WHEN_INPUT_TARGET_INVISIBLE";
            case 38:
                return "HIDE_SOFT_INPUT_CLOSE_CURRENT_SESSION";
            case 39:
                return "HIDE_SOFT_INPUT_FROM_VIEW";
            case 40:
                return "SHOW_SOFT_INPUT_LEGACY_DIRECT";
            case 41:
                return "HIDE_SOFT_INPUT_LEGACY_DIRECT";
            case 42:
                return "SHOW_WINDOW_LEGACY_DIRECT";
            case 43:
                return "HIDE_WINDOW_LEGACY_DIRECT";
            case 44:
                return "RESET_NEW_CONFIGURATION";
            case 45:
                return "UPDATE_CANDIDATES_VIEW_VISIBILITY";
            case 46:
                return "CONTROLS_CHANGED";
            case 47:
                return "DISPLAY_CONFIGURATION_CHANGED";
            case 48:
                return "DISPLAY_INSETS_CHANGED";
            case 49:
                return "DISPLAY_CONTROLS_CHANGED";
            case 50:
                return "UNBIND_CURRENT_METHOD";
            case 51:
                return "HIDE_SOFT_INPUT_ON_ANIMATION_STATE_CHANGED";
            case 52:
                return "HIDE_SOFT_INPUT_REQUEST_HIDE_WITH_CONTROL";
            case 53:
                return "SHOW_SOFT_INPUT_IME_TOGGLE_SOFT_INPUT";
            case 54:
                return "SHOW_SOFT_INPUT_IMM_DEPRECATION";
        }
    }

    public static java.lang.String handwritingGestureTypeFlagsToString(int i) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if ((i & 1) != 0) {
            stringJoiner.add("SELECT");
        }
        if ((i & 32) != 0) {
            stringJoiner.add("SELECT_RANGE");
        }
        if ((i & 2) != 0) {
            stringJoiner.add("INSERT");
        }
        if ((i & 4) != 0) {
            stringJoiner.add(android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_DELETE);
        }
        if ((i & 64) != 0) {
            stringJoiner.add("DELETE_RANGE");
        }
        if ((i & 8) != 0) {
            stringJoiner.add("REMOVE_SPACE");
        }
        if ((i & 16) != 0) {
            stringJoiner.add("JOIN_OR_SPLIT");
        }
        return stringJoiner.setEmptyValue("(none)").toString();
    }

    public static java.lang.String dumpViewInfo(android.view.View view) {
        if (view == null) {
            return "null";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(view);
        sb.append(",focus=" + view.hasFocus());
        sb.append(",windowFocus=" + view.hasWindowFocus());
        sb.append(",window=" + view.getWindowToken());
        sb.append(",displayId=" + view.getContext().getDisplayId());
        sb.append(",temporaryDetach=" + view.isTemporarilyDetached());
        sb.append(",hasImeFocus=" + view.hasImeFocus());
        return sb.toString();
    }
}
