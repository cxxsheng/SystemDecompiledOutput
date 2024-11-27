package android.webkit;

/* loaded from: classes4.dex */
public class ConsoleMessage {
    private android.webkit.ConsoleMessage.MessageLevel mLevel;
    private int mLineNumber;
    private java.lang.String mMessage;
    private java.lang.String mSourceId;

    public enum MessageLevel {
        TIP,
        LOG,
        WARNING,
        ERROR,
        DEBUG
    }

    public ConsoleMessage(java.lang.String str, java.lang.String str2, int i, android.webkit.ConsoleMessage.MessageLevel messageLevel) {
        this.mMessage = str;
        this.mSourceId = str2;
        this.mLineNumber = i;
        this.mLevel = messageLevel;
    }

    public android.webkit.ConsoleMessage.MessageLevel messageLevel() {
        return this.mLevel;
    }

    public java.lang.String message() {
        return this.mMessage;
    }

    public java.lang.String sourceId() {
        return this.mSourceId;
    }

    public int lineNumber() {
        return this.mLineNumber;
    }
}
