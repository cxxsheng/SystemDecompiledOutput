package android.speech.tts;

/* loaded from: classes3.dex */
class EventLogger extends android.speech.tts.AbstractEventLogger {
    private final android.speech.tts.SynthesisRequest mRequest;

    EventLogger(android.speech.tts.SynthesisRequest synthesisRequest, int i, int i2, java.lang.String str) {
        super(i, i2, str);
        this.mRequest = synthesisRequest;
    }

    @Override // android.speech.tts.AbstractEventLogger
    protected void logFailure(int i) {
        if (i != -2) {
            android.speech.tts.EventLogTags.writeTtsSpeakFailure(this.mServiceApp, this.mCallerUid, this.mCallerPid, getUtteranceLength(), getLocaleString(), this.mRequest.getSpeechRate(), this.mRequest.getPitch());
        }
    }

    @Override // android.speech.tts.AbstractEventLogger
    protected void logSuccess(long j, long j2, long j3) {
        android.speech.tts.EventLogTags.writeTtsSpeakSuccess(this.mServiceApp, this.mCallerUid, this.mCallerPid, getUtteranceLength(), getLocaleString(), this.mRequest.getSpeechRate(), this.mRequest.getPitch(), j2, j3, j);
    }

    private int getUtteranceLength() {
        java.lang.String text = this.mRequest.getText();
        if (text == null) {
            return 0;
        }
        return text.length();
    }

    private java.lang.String getLocaleString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mRequest.getLanguage());
        if (!android.text.TextUtils.isEmpty(this.mRequest.getCountry())) {
            sb.append('-');
            sb.append(this.mRequest.getCountry());
            if (!android.text.TextUtils.isEmpty(this.mRequest.getVariant())) {
                sb.append('-');
                sb.append(this.mRequest.getVariant());
            }
        }
        return sb.toString();
    }
}
