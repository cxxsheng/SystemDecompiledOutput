package com.android.server.alarm;

/* loaded from: classes.dex */
final class TareBill {
    static final com.android.server.tare.EconomyManagerInternal.ActionBill ALARM_CLOCK = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_CLOCK, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill NONWAKEUP_INEXACT_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_INEXACT, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill NONWAKEUP_EXACT_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_EXACT, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill NONWAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill WAKEUP_INEXACT_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_INEXACT, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill WAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill WAKEUP_EXACT_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT, 1, 0)));
    static final com.android.server.tare.EconomyManagerInternal.ActionBill WAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE, 1, 0)));

    @android.annotation.NonNull
    static com.android.server.tare.EconomyManagerInternal.ActionBill getAppropriateBill(@android.annotation.NonNull com.android.server.alarm.Alarm alarm) {
        if (alarm.alarmClock != null) {
            return ALARM_CLOCK;
        }
        boolean z = (alarm.flags & 12) != 0;
        boolean z2 = alarm.windowLength == 0;
        if (alarm.wakeup) {
            if (z2) {
                if (z) {
                    return WAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM;
                }
                return WAKEUP_EXACT_ALARM;
            }
            if (z) {
                return WAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM;
            }
            return WAKEUP_INEXACT_ALARM;
        }
        if (z2) {
            if (z) {
                return NONWAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM;
            }
            return NONWAKEUP_EXACT_ALARM;
        }
        if (z) {
            return NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM;
        }
        return NONWAKEUP_INEXACT_ALARM;
    }

    @android.annotation.NonNull
    static java.lang.String getName(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        if (actionBill.equals(ALARM_CLOCK)) {
            return "ALARM_CLOCK_BILL";
        }
        if (actionBill.equals(NONWAKEUP_INEXACT_ALARM)) {
            return "NONWAKEUP_INEXACT_ALARM_BILL";
        }
        if (actionBill.equals(NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM)) {
            return "NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM_BILL";
        }
        if (actionBill.equals(NONWAKEUP_EXACT_ALARM)) {
            return "NONWAKEUP_EXACT_ALARM_BILL";
        }
        if (actionBill.equals(NONWAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM)) {
            return "NONWAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM_BILL";
        }
        if (actionBill.equals(WAKEUP_INEXACT_ALARM)) {
            return "WAKEUP_INEXACT_ALARM_BILL";
        }
        if (actionBill.equals(WAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM)) {
            return "WAKEUP_INEXACT_ALLOW_WHILE_IDLE_ALARM_BILL";
        }
        if (actionBill.equals(WAKEUP_EXACT_ALARM)) {
            return "WAKEUP_EXACT_ALARM_BILL";
        }
        if (actionBill.equals(WAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM)) {
            return "WAKEUP_EXACT_ALLOW_WHILE_IDLE_ALARM_BILL";
        }
        return "UNKNOWN_BILL (" + actionBill.toString() + ")";
    }

    private TareBill() {
    }
}
