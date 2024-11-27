package android.speech.tts;

/* loaded from: classes3.dex */
public final class SynthesisRequest {
    private int mCallerUid;
    private java.lang.String mCountry;
    private java.lang.String mLanguage;
    private final android.os.Bundle mParams;
    private int mPitch;
    private int mSpeechRate;
    private final java.lang.CharSequence mText;
    private java.lang.String mVariant;
    private java.lang.String mVoiceName;

    public SynthesisRequest(java.lang.String str, android.os.Bundle bundle) {
        this.mText = str;
        this.mParams = new android.os.Bundle(bundle);
    }

    public SynthesisRequest(java.lang.CharSequence charSequence, android.os.Bundle bundle) {
        this.mText = charSequence;
        this.mParams = new android.os.Bundle(bundle);
    }

    @java.lang.Deprecated
    public java.lang.String getText() {
        return this.mText.toString();
    }

    public java.lang.CharSequence getCharSequenceText() {
        return this.mText;
    }

    public java.lang.String getVoiceName() {
        return this.mVoiceName;
    }

    public java.lang.String getLanguage() {
        return this.mLanguage;
    }

    public java.lang.String getCountry() {
        return this.mCountry;
    }

    public java.lang.String getVariant() {
        return this.mVariant;
    }

    public int getSpeechRate() {
        return this.mSpeechRate;
    }

    public int getPitch() {
        return this.mPitch;
    }

    public android.os.Bundle getParams() {
        return this.mParams;
    }

    public int getCallerUid() {
        return this.mCallerUid;
    }

    void setLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mLanguage = str;
        this.mCountry = str2;
        this.mVariant = str3;
    }

    void setVoiceName(java.lang.String str) {
        this.mVoiceName = str;
    }

    void setSpeechRate(int i) {
        this.mSpeechRate = i;
    }

    void setPitch(int i) {
        this.mPitch = i;
    }

    void setCallerUid(int i) {
        this.mCallerUid = i;
    }
}
