package android.service.notification;

/* loaded from: classes3.dex */
public class ScheduleCalendar {
    public static final boolean DEBUG = android.util.Log.isLoggable("ConditionProviders", 3);
    public static final java.lang.String TAG = "ScheduleCalendar";
    private android.service.notification.ZenModeConfig.ScheduleInfo mSchedule;
    private final android.util.ArraySet<java.lang.Integer> mDays = new android.util.ArraySet<>();
    private final java.util.Calendar mCalendar = java.util.Calendar.getInstance();

    public java.lang.String toString() {
        return "ScheduleCalendar[mDays=" + this.mDays + ", mSchedule=" + this.mSchedule + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean exitAtAlarm() {
        return this.mSchedule.exitAtAlarm;
    }

    public void setSchedule(android.service.notification.ZenModeConfig.ScheduleInfo scheduleInfo) {
        if (java.util.Objects.equals(this.mSchedule, scheduleInfo)) {
            return;
        }
        this.mSchedule = scheduleInfo;
        updateDays();
    }

    public void maybeSetNextAlarm(long j, long j2) {
        if (this.mSchedule == null || !this.mSchedule.exitAtAlarm) {
            return;
        }
        if (j2 == 0) {
            this.mSchedule.nextAlarm = 0L;
            return;
        }
        if (j2 > j) {
            this.mSchedule.nextAlarm = j2;
        } else if (this.mSchedule.nextAlarm < j) {
            if (DEBUG) {
                android.util.Log.d(TAG, "All alarms are in the past " + this.mSchedule.nextAlarm);
            }
            this.mSchedule.nextAlarm = 0L;
        }
    }

    public void setTimeZone(java.util.TimeZone timeZone) {
        this.mCalendar.setTimeZone(timeZone);
    }

    public long getNextChangeTime(long j) {
        if (this.mSchedule == null) {
            return 0L;
        }
        return java.lang.Math.min(getNextTime(j, this.mSchedule.startHour, this.mSchedule.startMinute, true), getNextTime(j, this.mSchedule.endHour, this.mSchedule.endMinute, false));
    }

    private long getNextTime(long j, int i, int i2, boolean z) {
        long closestActualTime = z ? getClosestActualTime(j, i, i2) : getTime(j, i, i2);
        if (closestActualTime <= j) {
            long addDays = addDays(closestActualTime, 1);
            return z ? getClosestActualTime(addDays, i, i2) : getTime(addDays, i, i2);
        }
        return closestActualTime;
    }

    private long getTime(long j, int i, int i2) {
        this.mCalendar.setTimeInMillis(j);
        this.mCalendar.set(11, i);
        this.mCalendar.set(12, i2);
        this.mCalendar.set(13, 0);
        this.mCalendar.set(14, 0);
        return this.mCalendar.getTimeInMillis();
    }

    public boolean isInSchedule(long j) {
        long j2;
        if (this.mSchedule == null || this.mDays.size() == 0) {
            return false;
        }
        long closestActualTime = getClosestActualTime(j, this.mSchedule.startHour, this.mSchedule.startMinute);
        long time = getTime(j, this.mSchedule.endHour, this.mSchedule.endMinute);
        if (time > closestActualTime) {
            j2 = time;
        } else {
            j2 = addDays(time, 1);
        }
        return isInSchedule(-1, j, closestActualTime, j2) || isInSchedule(0, j, closestActualTime, j2);
    }

    public boolean isAlarmInSchedule(long j, long j2) {
        long j3;
        if (this.mSchedule == null || this.mDays.size() == 0) {
            return false;
        }
        long closestActualTime = getClosestActualTime(j, this.mSchedule.startHour, this.mSchedule.startMinute);
        long time = getTime(j, this.mSchedule.endHour, this.mSchedule.endMinute);
        if (time > closestActualTime) {
            j3 = time;
        } else {
            j3 = addDays(time, 1);
        }
        return (isInSchedule(-1, j, closestActualTime, j3) && isInSchedule(-1, j2, closestActualTime, j3)) || (isInSchedule(0, j, closestActualTime, j3) && isInSchedule(0, j2, closestActualTime, j3));
    }

    public boolean shouldExitForAlarm(long j) {
        return this.mSchedule != null && this.mSchedule.exitAtAlarm && this.mSchedule.nextAlarm != 0 && j >= this.mSchedule.nextAlarm && isAlarmInSchedule(this.mSchedule.nextAlarm, j);
    }

    private boolean isInSchedule(int i, long j, long j2, long j3) {
        return this.mDays.contains(java.lang.Integer.valueOf(((((getDayOfWeek(j) - 1) + (i % 7)) + 7) % 7) + 1)) && j >= addDays(j2, i) && j < addDays(j3, i);
    }

    private int getDayOfWeek(long j) {
        this.mCalendar.setTimeInMillis(j);
        return this.mCalendar.get(7);
    }

    private void updateDays() {
        this.mDays.clear();
        if (this.mSchedule != null && this.mSchedule.days != null) {
            for (int i = 0; i < this.mSchedule.days.length; i++) {
                this.mDays.add(java.lang.Integer.valueOf(this.mSchedule.days[i]));
            }
        }
    }

    private long addDays(long j, int i) {
        this.mCalendar.setTimeInMillis(j);
        this.mCalendar.add(5, i);
        return this.mCalendar.getTimeInMillis();
    }

    public long getClosestActualTime(long j, int i, int i2) {
        long time = getTime(j, i, i2);
        if (!this.mCalendar.getTimeZone().observesDaylightTime()) {
            return time;
        }
        this.mCalendar.setTimeInMillis(time);
        int i3 = this.mCalendar.get(11);
        int i4 = this.mCalendar.get(12);
        if (i3 == i + 1 && i4 == i2) {
            return getTime(j, i3, 0);
        }
        return time;
    }
}
