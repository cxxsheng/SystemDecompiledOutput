package com.android.server.display.utils;

/* loaded from: classes.dex */
public abstract class Plog {
    private long mId;

    protected abstract void emit(java.lang.String str);

    public static com.android.server.display.utils.Plog createSystemPlog(java.lang.String str) {
        return new com.android.server.display.utils.Plog.SystemPlog(str);
    }

    public com.android.server.display.utils.Plog start(java.lang.String str) {
        this.mId = java.lang.System.currentTimeMillis();
        write(formatTitle(str));
        return this;
    }

    public com.android.server.display.utils.Plog logPoint(java.lang.String str, float f, float f2) {
        write(formatPoint(str, f, f2));
        return this;
    }

    public com.android.server.display.utils.Plog logCurve(java.lang.String str, float[] fArr, float[] fArr2) {
        write(formatCurve(str, fArr, fArr2));
        return this;
    }

    private java.lang.String formatTitle(java.lang.String str) {
        return "title: " + str;
    }

    private java.lang.String formatPoint(java.lang.String str, float f, float f2) {
        return "point: " + str + ": (" + f + "," + f2 + ")";
    }

    private java.lang.String formatCurve(java.lang.String str, float[] fArr, float[] fArr2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("curve: " + str + ": [");
        int length = fArr.length <= fArr2.length ? fArr.length : fArr2.length;
        for (int i = 0; i < length; i++) {
            sb.append("(" + fArr[i] + "," + fArr2[i] + "),");
        }
        sb.append("]");
        return sb.toString();
    }

    private void write(java.lang.String str) {
        emit("[PLOG " + this.mId + "] " + str);
    }

    public static class SystemPlog extends com.android.server.display.utils.Plog {
        private final java.lang.String mTag;

        public SystemPlog(java.lang.String str) {
            this.mTag = str;
        }

        @Override // com.android.server.display.utils.Plog
        protected void emit(java.lang.String str) {
            android.util.Slog.d(this.mTag, str);
        }
    }
}
