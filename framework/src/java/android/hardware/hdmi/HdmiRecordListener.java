package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class HdmiRecordListener {
    public abstract android.hardware.hdmi.HdmiRecordSources.RecordSource onOneTouchRecordSourceRequested(int i);

    public void onOneTouchRecordResult(int i, int i2) {
    }

    public void onTimerRecordingResult(int i, android.hardware.hdmi.HdmiRecordListener.TimerStatusData timerStatusData) {
    }

    @android.annotation.SystemApi
    public static class TimerStatusData {
        private int mDurationHour;
        private int mDurationMinute;
        private int mExtraError;
        private int mMediaInfo;
        private int mNotProgrammedError;
        private boolean mOverlapped;
        private boolean mProgrammed;
        private int mProgrammedInfo;

        static android.hardware.hdmi.HdmiRecordListener.TimerStatusData parseFrom(int i) {
            android.hardware.hdmi.HdmiRecordListener.TimerStatusData timerStatusData = new android.hardware.hdmi.HdmiRecordListener.TimerStatusData();
            timerStatusData.mOverlapped = ((i >> 31) & 1) != 0;
            timerStatusData.mMediaInfo = (i >> 29) & 3;
            timerStatusData.mProgrammed = ((i >> 28) & 1) != 0;
            if (timerStatusData.mProgrammed) {
                timerStatusData.mProgrammedInfo = (i >> 24) & 15;
                timerStatusData.mDurationHour = bcdByteToInt((byte) ((i >> 16) & 255));
                timerStatusData.mDurationMinute = bcdByteToInt((byte) ((i >> 8) & 255));
            } else {
                timerStatusData.mNotProgrammedError = (i >> 24) & 15;
                timerStatusData.mDurationHour = bcdByteToInt((byte) ((i >> 16) & 255));
                timerStatusData.mDurationMinute = bcdByteToInt((byte) ((i >> 8) & 255));
            }
            timerStatusData.mExtraError = i & 255;
            return timerStatusData;
        }

        private static int bcdByteToInt(byte b) {
            return ((((b >> 4) & 15) * 10) + b) & 15;
        }

        private TimerStatusData() {
        }

        public boolean isOverlapped() {
            return this.mOverlapped;
        }

        public int getMediaInfo() {
            return this.mMediaInfo;
        }

        public boolean isProgrammed() {
            return this.mProgrammed;
        }

        public int getProgrammedInfo() {
            if (!isProgrammed()) {
                throw new java.lang.IllegalStateException("No programmed info. Call getNotProgammedError() instead.");
            }
            return this.mProgrammedInfo;
        }

        public int getNotProgammedError() {
            if (isProgrammed()) {
                throw new java.lang.IllegalStateException("Has no not-programmed error. Call getProgrammedInfo() instead.");
            }
            return this.mNotProgrammedError;
        }

        public int getDurationHour() {
            return this.mDurationHour;
        }

        public int getDurationMinute() {
            return this.mDurationMinute;
        }

        public int getExtraError() {
            return this.mExtraError;
        }
    }

    public void onClearTimerRecordingResult(int i, int i2) {
    }
}
