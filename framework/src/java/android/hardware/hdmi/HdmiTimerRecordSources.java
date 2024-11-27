package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class HdmiTimerRecordSources {
    private static final int EXTERNAL_SOURCE_SPECIFIER_EXTERNAL_PHYSICAL_ADDRESS = 5;
    private static final int EXTERNAL_SOURCE_SPECIFIER_EXTERNAL_PLUG = 4;
    public static final int RECORDING_SEQUENCE_REPEAT_FRIDAY = 32;
    private static final int RECORDING_SEQUENCE_REPEAT_MASK = 127;
    public static final int RECORDING_SEQUENCE_REPEAT_MONDAY = 2;
    public static final int RECORDING_SEQUENCE_REPEAT_ONCE_ONLY = 0;
    public static final int RECORDING_SEQUENCE_REPEAT_SATUREDAY = 64;
    public static final int RECORDING_SEQUENCE_REPEAT_SUNDAY = 1;
    public static final int RECORDING_SEQUENCE_REPEAT_THURSDAY = 16;
    public static final int RECORDING_SEQUENCE_REPEAT_TUESDAY = 4;
    public static final int RECORDING_SEQUENCE_REPEAT_WEDNESDAY = 8;
    private static final java.lang.String TAG = "HdmiTimerRecordingSources";

    private HdmiTimerRecordSources() {
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofDigitalSource(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource digitalServiceSource) {
        checkTimerRecordSourceInputs(timerInfo, digitalServiceSource);
        return new android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource(timerInfo, digitalServiceSource);
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofAnalogueSource(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.AnalogueServiceSource analogueServiceSource) {
        checkTimerRecordSourceInputs(timerInfo, analogueServiceSource);
        return new android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource(timerInfo, analogueServiceSource);
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofExternalPlug(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.ExternalPlugData externalPlugData) {
        checkTimerRecordSourceInputs(timerInfo, externalPlugData);
        return new android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource(timerInfo, new android.hardware.hdmi.HdmiTimerRecordSources.ExternalSourceDecorator(externalPlugData, 4));
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofExternalPhysicalAddress(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.ExternalPhysicalAddress externalPhysicalAddress) {
        checkTimerRecordSourceInputs(timerInfo, externalPhysicalAddress);
        return new android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource(timerInfo, new android.hardware.hdmi.HdmiTimerRecordSources.ExternalSourceDecorator(externalPhysicalAddress, 5));
    }

    private static void checkTimerRecordSourceInputs(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.RecordSource recordSource) {
        if (timerInfo == null) {
            android.util.Log.w(TAG, "TimerInfo should not be null.");
            throw new java.lang.IllegalArgumentException("TimerInfo should not be null.");
        }
        if (recordSource == null) {
            android.util.Log.w(TAG, "source should not be null.");
            throw new java.lang.IllegalArgumentException("source should not be null.");
        }
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.Time timeOf(int i, int i2) {
        checkTimeValue(i, i2);
        return new android.hardware.hdmi.HdmiTimerRecordSources.Time(i, i2);
    }

    private static void checkTimeValue(int i, int i2) {
        if (i < 0 || i > 23) {
            throw new java.lang.IllegalArgumentException("Hour should be in rage of [0, 23]:" + i);
        }
        if (i2 < 0 || i2 > 59) {
            throw new java.lang.IllegalArgumentException("Minute should be in rage of [0, 59]:" + i2);
        }
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.Duration durationOf(int i, int i2) {
        checkDurationValue(i, i2);
        return new android.hardware.hdmi.HdmiTimerRecordSources.Duration(i, i2);
    }

    private static void checkDurationValue(int i, int i2) {
        if (i < 0 || i > 99) {
            throw new java.lang.IllegalArgumentException("Hour should be in rage of [0, 99]:" + i);
        }
        if (i2 < 0 || i2 > 59) {
            throw new java.lang.IllegalArgumentException("minute should be in rage of [0, 59]:" + i2);
        }
    }

    static class TimeUnit {
        final int mHour;
        final int mMinute;

        TimeUnit(int i, int i2) {
            this.mHour = i;
            this.mMinute = i2;
        }

        int toByteArray(byte[] bArr, int i) {
            bArr[i] = toBcdByte(this.mHour);
            bArr[i + 1] = toBcdByte(this.mMinute);
            return 2;
        }

        static byte toBcdByte(int i) {
            return (byte) ((i % 10) | (((i / 10) % 10) << 4));
        }
    }

    @android.annotation.SystemApi
    public static final class Time extends android.hardware.hdmi.HdmiTimerRecordSources.TimeUnit {
        private Time(int i, int i2) {
            super(i, i2);
        }
    }

    @android.annotation.SystemApi
    public static final class Duration extends android.hardware.hdmi.HdmiTimerRecordSources.TimeUnit {
        private Duration(int i, int i2) {
            super(i, i2);
        }
    }

    public static android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfoOf(int i, int i2, android.hardware.hdmi.HdmiTimerRecordSources.Time time, android.hardware.hdmi.HdmiTimerRecordSources.Duration duration, int i3) {
        if (i < 0 || i > 31) {
            throw new java.lang.IllegalArgumentException("Day of month should be in range of [0, 31]:" + i);
        }
        if (i2 < 1 || i2 > 12) {
            throw new java.lang.IllegalArgumentException("Month of year should be in range of [1, 12]:" + i2);
        }
        checkTimeValue(time.mHour, time.mMinute);
        checkDurationValue(duration.mHour, duration.mMinute);
        if (i3 != 0 && (i3 & (-128)) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid reecording sequence value:" + i3);
        }
        return new android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo(i, i2, time, duration, i3);
    }

    @android.annotation.SystemApi
    public static final class TimerInfo {
        private static final int BASIC_INFO_SIZE = 7;
        private static final int DAY_OF_MONTH_SIZE = 1;
        private static final int DURATION_SIZE = 2;
        private static final int MONTH_OF_YEAR_SIZE = 1;
        private static final int RECORDING_SEQUENCE_SIZE = 1;
        private static final int START_TIME_SIZE = 2;
        private final int mDayOfMonth;
        private final android.hardware.hdmi.HdmiTimerRecordSources.Duration mDuration;
        private final int mMonthOfYear;
        private final int mRecordingSequence;
        private final android.hardware.hdmi.HdmiTimerRecordSources.Time mStartTime;

        private TimerInfo(int i, int i2, android.hardware.hdmi.HdmiTimerRecordSources.Time time, android.hardware.hdmi.HdmiTimerRecordSources.Duration duration, int i3) {
            this.mDayOfMonth = i;
            this.mMonthOfYear = i2;
            this.mStartTime = time;
            this.mDuration = duration;
            this.mRecordingSequence = i3;
        }

        int toByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) this.mDayOfMonth;
            int i2 = i + 1;
            bArr[i2] = (byte) this.mMonthOfYear;
            int i3 = i2 + 1;
            int byteArray = i3 + this.mStartTime.toByteArray(bArr, i3);
            bArr[byteArray + this.mDuration.toByteArray(bArr, byteArray)] = (byte) this.mRecordingSequence;
            return getDataSize();
        }

        int getDataSize() {
            return 7;
        }
    }

    @android.annotation.SystemApi
    public static final class TimerRecordSource {
        private final android.hardware.hdmi.HdmiRecordSources.RecordSource mRecordSource;
        private final android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo mTimerInfo;

        private TimerRecordSource(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.RecordSource recordSource) {
            this.mTimerInfo = timerInfo;
            this.mRecordSource = recordSource;
        }

        int getDataSize() {
            return this.mTimerInfo.getDataSize() + this.mRecordSource.getDataSize(false);
        }

        int toByteArray(byte[] bArr, int i) {
            this.mRecordSource.toByteArray(false, bArr, i + this.mTimerInfo.toByteArray(bArr, i));
            return getDataSize();
        }
    }

    private static class ExternalSourceDecorator extends android.hardware.hdmi.HdmiRecordSources.RecordSource {
        private final int mExternalSourceSpecifier;
        private final android.hardware.hdmi.HdmiRecordSources.RecordSource mRecordSource;

        private ExternalSourceDecorator(android.hardware.hdmi.HdmiRecordSources.RecordSource recordSource, int i) {
            super(recordSource.mSourceType, recordSource.getDataSize(false) + 1);
            this.mRecordSource = recordSource;
            this.mExternalSourceSpecifier = i;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) this.mExternalSourceSpecifier;
            this.mRecordSource.toByteArray(false, bArr, i + 1);
            return getDataSize(false);
        }
    }

    @android.annotation.SystemApi
    public static boolean checkTimerRecordSource(int i, byte[] bArr) {
        int length = bArr.length - 7;
        switch (i) {
            case 1:
                if (7 != length) {
                    break;
                }
                break;
            case 2:
                if (4 != length) {
                    break;
                }
                break;
            case 3:
                byte b = bArr[7];
                if (b == 4) {
                    if (2 != length) {
                        break;
                    }
                } else if (b != 5 || 3 != length) {
                }
                break;
        }
        return false;
    }
}
