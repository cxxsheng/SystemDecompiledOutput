package android.widget;

/* loaded from: classes4.dex */
class DoubleDigitManager {
    private java.lang.Integer intermediateDigit;
    private final android.widget.DoubleDigitManager.CallBack mCallBack;
    private final long timeoutInMillis;

    interface CallBack {
        void singleDigitFinal(int i);

        boolean singleDigitIntermediate(int i);

        boolean twoDigitsFinal(int i, int i2);
    }

    public DoubleDigitManager(long j, android.widget.DoubleDigitManager.CallBack callBack) {
        this.timeoutInMillis = j;
        this.mCallBack = callBack;
    }

    public void reportDigit(int i) {
        if (this.intermediateDigit == null) {
            this.intermediateDigit = java.lang.Integer.valueOf(i);
            new android.os.Handler().postDelayed(new java.lang.Runnable() { // from class: android.widget.DoubleDigitManager.1
                @Override // java.lang.Runnable
                public void run() {
                    if (android.widget.DoubleDigitManager.this.intermediateDigit != null) {
                        android.widget.DoubleDigitManager.this.mCallBack.singleDigitFinal(android.widget.DoubleDigitManager.this.intermediateDigit.intValue());
                        android.widget.DoubleDigitManager.this.intermediateDigit = null;
                    }
                }
            }, this.timeoutInMillis);
            if (!this.mCallBack.singleDigitIntermediate(i)) {
                this.intermediateDigit = null;
                this.mCallBack.singleDigitFinal(i);
                return;
            }
            return;
        }
        if (this.mCallBack.twoDigitsFinal(this.intermediateDigit.intValue(), i)) {
            this.intermediateDigit = null;
        }
    }
}
