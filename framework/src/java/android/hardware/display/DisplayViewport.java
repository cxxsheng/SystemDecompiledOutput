package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayViewport {
    public static final int VIEWPORT_EXTERNAL = 2;
    public static final int VIEWPORT_INTERNAL = 1;
    public static final int VIEWPORT_VIRTUAL = 3;
    public int deviceHeight;
    public int deviceWidth;
    public int displayId;
    public boolean isActive;
    public int orientation;
    public java.lang.Integer physicalPort;
    public int type;
    public java.lang.String uniqueId;
    public boolean valid;
    public final android.graphics.Rect logicalFrame = new android.graphics.Rect();
    public final android.graphics.Rect physicalFrame = new android.graphics.Rect();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ViewportType {
    }

    public void copyFrom(android.hardware.display.DisplayViewport displayViewport) {
        this.valid = displayViewport.valid;
        this.isActive = displayViewport.isActive;
        this.displayId = displayViewport.displayId;
        this.orientation = displayViewport.orientation;
        this.logicalFrame.set(displayViewport.logicalFrame);
        this.physicalFrame.set(displayViewport.physicalFrame);
        this.deviceWidth = displayViewport.deviceWidth;
        this.deviceHeight = displayViewport.deviceHeight;
        this.uniqueId = displayViewport.uniqueId;
        this.physicalPort = displayViewport.physicalPort;
        this.type = displayViewport.type;
    }

    public android.hardware.display.DisplayViewport makeCopy() {
        android.hardware.display.DisplayViewport displayViewport = new android.hardware.display.DisplayViewport();
        displayViewport.copyFrom(this);
        return displayViewport;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.display.DisplayViewport)) {
            return false;
        }
        android.hardware.display.DisplayViewport displayViewport = (android.hardware.display.DisplayViewport) obj;
        return this.valid == displayViewport.valid && this.isActive == displayViewport.isActive && this.displayId == displayViewport.displayId && this.orientation == displayViewport.orientation && this.logicalFrame.equals(displayViewport.logicalFrame) && this.physicalFrame.equals(displayViewport.physicalFrame) && this.deviceWidth == displayViewport.deviceWidth && this.deviceHeight == displayViewport.deviceHeight && android.text.TextUtils.equals(this.uniqueId, displayViewport.uniqueId) && java.util.Objects.equals(this.physicalPort, displayViewport.physicalPort) && this.type == displayViewport.type;
    }

    public int hashCode() {
        int i = 31 + (this.valid ? 1 : 0) + 1;
        int i2 = i + (i * 31) + (this.isActive ? 1 : 0);
        int i3 = i2 + (i2 * 31) + this.displayId;
        int i4 = i3 + (i3 * 31) + this.orientation;
        int hashCode = i4 + (i4 * 31) + this.logicalFrame.hashCode();
        int hashCode2 = hashCode + (hashCode * 31) + this.physicalFrame.hashCode();
        int i5 = hashCode2 + (hashCode2 * 31) + this.deviceWidth;
        int i6 = i5 + (i5 * 31) + this.deviceHeight;
        int hashCode3 = i6 + (i6 * 31) + this.uniqueId.hashCode();
        if (this.physicalPort != null) {
            hashCode3 += (hashCode3 * 31) + this.physicalPort.hashCode();
        }
        return hashCode3 + (hashCode3 * 31) + this.type;
    }

    public java.lang.String toString() {
        return "DisplayViewport{type=" + typeToString(this.type) + ", valid=" + this.valid + ", isActive=" + this.isActive + ", displayId=" + this.displayId + ", uniqueId='" + this.uniqueId + "', physicalPort=" + this.physicalPort + ", orientation=" + this.orientation + ", logicalFrame=" + this.logicalFrame + ", physicalFrame=" + this.physicalFrame + ", deviceWidth=" + this.deviceWidth + ", deviceHeight=" + this.deviceHeight + "}";
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case 1:
                return "INTERNAL";
            case 2:
                return "EXTERNAL";
            case 3:
                return "VIRTUAL";
            default:
                return "UNKNOWN (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
