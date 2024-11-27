package android.view.contentprotection;

/* loaded from: classes4.dex */
public final class ContentProtectionUtils {
    public static java.lang.String getEventTextLower(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        java.lang.CharSequence text = contentCaptureEvent.getText();
        if (text == null) {
            return null;
        }
        return text.toString().toLowerCase();
    }

    public static java.lang.String getViewNodeTextLower(android.view.contentcapture.ViewNode viewNode) {
        java.lang.CharSequence text;
        if (viewNode == null || (text = viewNode.getText()) == null) {
            return null;
        }
        return text.toString().toLowerCase();
    }

    public static java.lang.String getHintTextLower(android.view.contentcapture.ViewNode viewNode) {
        java.lang.String hint;
        if (viewNode == null || (hint = viewNode.getHint()) == null) {
            return null;
        }
        return hint.toLowerCase();
    }
}
