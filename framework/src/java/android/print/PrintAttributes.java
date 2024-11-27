package android.print;

/* loaded from: classes3.dex */
public final class PrintAttributes implements android.os.Parcelable {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final android.os.Parcelable.Creator<android.print.PrintAttributes> CREATOR = new android.os.Parcelable.Creator<android.print.PrintAttributes>() { // from class: android.print.PrintAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintAttributes[] newArray(int i) {
            return new android.print.PrintAttributes[i];
        }
    };
    public static final int DUPLEX_MODE_LONG_EDGE = 2;
    public static final int DUPLEX_MODE_NONE = 1;
    public static final int DUPLEX_MODE_SHORT_EDGE = 4;
    private static final int VALID_COLOR_MODES = 3;
    private static final int VALID_DUPLEX_MODES = 7;
    private int mColorMode;
    private int mDuplexMode;
    private android.print.PrintAttributes.MediaSize mMediaSize;
    private android.print.PrintAttributes.Margins mMinMargins;
    private android.print.PrintAttributes.Resolution mResolution;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ColorMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DuplexMode {
    }

    PrintAttributes() {
    }

    private PrintAttributes(android.os.Parcel parcel) {
        this.mMediaSize = parcel.readInt() == 1 ? android.print.PrintAttributes.MediaSize.createFromParcel(parcel) : null;
        this.mResolution = parcel.readInt() == 1 ? android.print.PrintAttributes.Resolution.createFromParcel(parcel) : null;
        this.mMinMargins = parcel.readInt() == 1 ? android.print.PrintAttributes.Margins.createFromParcel(parcel) : null;
        this.mColorMode = parcel.readInt();
        if (this.mColorMode != 0) {
            enforceValidColorMode(this.mColorMode);
        }
        this.mDuplexMode = parcel.readInt();
        if (this.mDuplexMode != 0) {
            enforceValidDuplexMode(this.mDuplexMode);
        }
    }

    public android.print.PrintAttributes.MediaSize getMediaSize() {
        return this.mMediaSize;
    }

    public void setMediaSize(android.print.PrintAttributes.MediaSize mediaSize) {
        this.mMediaSize = mediaSize;
    }

    public android.print.PrintAttributes.Resolution getResolution() {
        return this.mResolution;
    }

    public void setResolution(android.print.PrintAttributes.Resolution resolution) {
        this.mResolution = resolution;
    }

    public android.print.PrintAttributes.Margins getMinMargins() {
        return this.mMinMargins;
    }

    public void setMinMargins(android.print.PrintAttributes.Margins margins) {
        this.mMinMargins = margins;
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public void setColorMode(int i) {
        enforceValidColorMode(i);
        this.mColorMode = i;
    }

    public boolean isPortrait() {
        return this.mMediaSize.isPortrait();
    }

    public int getDuplexMode() {
        return this.mDuplexMode;
    }

    public void setDuplexMode(int i) {
        enforceValidDuplexMode(i);
        this.mDuplexMode = i;
    }

    public android.print.PrintAttributes asPortrait() {
        if (isPortrait()) {
            return this;
        }
        android.print.PrintAttributes printAttributes = new android.print.PrintAttributes();
        printAttributes.setMediaSize(getMediaSize().asPortrait());
        android.print.PrintAttributes.Resolution resolution = getResolution();
        printAttributes.setResolution(new android.print.PrintAttributes.Resolution(resolution.getId(), resolution.getLabel(), resolution.getVerticalDpi(), resolution.getHorizontalDpi()));
        printAttributes.setMinMargins(getMinMargins());
        printAttributes.setColorMode(getColorMode());
        printAttributes.setDuplexMode(getDuplexMode());
        return printAttributes;
    }

    public android.print.PrintAttributes asLandscape() {
        if (!isPortrait()) {
            return this;
        }
        android.print.PrintAttributes printAttributes = new android.print.PrintAttributes();
        printAttributes.setMediaSize(getMediaSize().asLandscape());
        android.print.PrintAttributes.Resolution resolution = getResolution();
        printAttributes.setResolution(new android.print.PrintAttributes.Resolution(resolution.getId(), resolution.getLabel(), resolution.getVerticalDpi(), resolution.getHorizontalDpi()));
        printAttributes.setMinMargins(getMinMargins());
        printAttributes.setColorMode(getColorMode());
        printAttributes.setDuplexMode(getDuplexMode());
        return printAttributes;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mMediaSize != null) {
            parcel.writeInt(1);
            this.mMediaSize.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        if (this.mResolution != null) {
            parcel.writeInt(1);
            this.mResolution.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        if (this.mMinMargins != null) {
            parcel.writeInt(1);
            this.mMinMargins.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mColorMode);
        parcel.writeInt(this.mDuplexMode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return ((((((((this.mColorMode + 31) * 31) + this.mDuplexMode) * 31) + (this.mMinMargins == null ? 0 : this.mMinMargins.hashCode())) * 31) + (this.mMediaSize == null ? 0 : this.mMediaSize.hashCode())) * 31) + (this.mResolution != null ? this.mResolution.hashCode() : 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.print.PrintAttributes printAttributes = (android.print.PrintAttributes) obj;
        if (this.mColorMode != printAttributes.mColorMode || this.mDuplexMode != printAttributes.mDuplexMode) {
            return false;
        }
        if (this.mMinMargins == null) {
            if (printAttributes.mMinMargins != null) {
                return false;
            }
        } else if (!this.mMinMargins.equals(printAttributes.mMinMargins)) {
            return false;
        }
        if (this.mMediaSize == null) {
            if (printAttributes.mMediaSize != null) {
                return false;
            }
        } else if (!this.mMediaSize.equals(printAttributes.mMediaSize)) {
            return false;
        }
        if (this.mResolution == null) {
            if (printAttributes.mResolution != null) {
                return false;
            }
        } else if (!this.mResolution.equals(printAttributes.mResolution)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrintAttributes{");
        sb.append("mediaSize: ").append(this.mMediaSize);
        if (this.mMediaSize != null) {
            sb.append(", orientation: ").append(this.mMediaSize.isPortrait() ? android.hardware.Camera.Parameters.SCENE_MODE_PORTRAIT : android.hardware.Camera.Parameters.SCENE_MODE_LANDSCAPE);
        } else {
            sb.append(", orientation: ").append("null");
        }
        sb.append(", resolution: ").append(this.mResolution);
        sb.append(", minMargins: ").append(this.mMinMargins);
        sb.append(", colorMode: ").append(colorModeToString(this.mColorMode));
        sb.append(", duplexMode: ").append(duplexModeToString(this.mDuplexMode));
        sb.append("}");
        return sb.toString();
    }

    public void clear() {
        this.mMediaSize = null;
        this.mResolution = null;
        this.mMinMargins = null;
        this.mColorMode = 0;
        this.mDuplexMode = 0;
    }

    public void copyFrom(android.print.PrintAttributes printAttributes) {
        this.mMediaSize = printAttributes.mMediaSize;
        this.mResolution = printAttributes.mResolution;
        this.mMinMargins = printAttributes.mMinMargins;
        this.mColorMode = printAttributes.mColorMode;
        this.mDuplexMode = printAttributes.mDuplexMode;
    }

    public static final class MediaSize {
        private static final java.lang.String LOG_TAG = "MediaSize";
        private final int mHeightMils;
        private final java.lang.String mId;
        public final java.lang.String mLabel;
        public final int mLabelResId;
        public final java.lang.String mPackageName;
        private final int mWidthMils;
        private static final java.util.Map<java.lang.String, android.print.PrintAttributes.MediaSize> sIdToMediaSizeMap = new android.util.ArrayMap();
        public static final android.print.PrintAttributes.MediaSize UNKNOWN_PORTRAIT = new android.print.PrintAttributes.MediaSize("UNKNOWN_PORTRAIT", "android", com.android.internal.R.string.mediasize_unknown_portrait, 1, Integer.MAX_VALUE);
        public static final android.print.PrintAttributes.MediaSize UNKNOWN_LANDSCAPE = new android.print.PrintAttributes.MediaSize("UNKNOWN_LANDSCAPE", "android", com.android.internal.R.string.mediasize_unknown_landscape, Integer.MAX_VALUE, 1);
        public static final android.print.PrintAttributes.MediaSize ISO_A0 = new android.print.PrintAttributes.MediaSize("ISO_A0", "android", com.android.internal.R.string.mediasize_iso_a0, 33110, 46810);
        public static final android.print.PrintAttributes.MediaSize ISO_A1 = new android.print.PrintAttributes.MediaSize("ISO_A1", "android", com.android.internal.R.string.mediasize_iso_a1, 23390, 33110);
        public static final android.print.PrintAttributes.MediaSize ISO_A2 = new android.print.PrintAttributes.MediaSize("ISO_A2", "android", com.android.internal.R.string.mediasize_iso_a2, 16540, 23390);
        public static final android.print.PrintAttributes.MediaSize ISO_A3 = new android.print.PrintAttributes.MediaSize("ISO_A3", "android", com.android.internal.R.string.mediasize_iso_a3, 11690, 16540);
        public static final android.print.PrintAttributes.MediaSize ISO_A4 = new android.print.PrintAttributes.MediaSize("ISO_A4", "android", com.android.internal.R.string.mediasize_iso_a4, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_BLE_SET_PRIVACY_MODE, 11690);
        public static final android.print.PrintAttributes.MediaSize ISO_A5 = new android.print.PrintAttributes.MediaSize("ISO_A5", "android", com.android.internal.R.string.mediasize_iso_a5, 5830, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_BLE_SET_PRIVACY_MODE);
        public static final android.print.PrintAttributes.MediaSize ISO_A6 = new android.print.PrintAttributes.MediaSize("ISO_A6", "android", com.android.internal.R.string.mediasize_iso_a6, 4130, 5830);
        public static final android.print.PrintAttributes.MediaSize ISO_A7 = new android.print.PrintAttributes.MediaSize("ISO_A7", "android", com.android.internal.R.string.mediasize_iso_a7, 2910, 4130);
        public static final android.print.PrintAttributes.MediaSize ISO_A8 = new android.print.PrintAttributes.MediaSize("ISO_A8", "android", com.android.internal.R.string.mediasize_iso_a8, 2050, 2910);
        public static final android.print.PrintAttributes.MediaSize ISO_A9 = new android.print.PrintAttributes.MediaSize("ISO_A9", "android", com.android.internal.R.string.mediasize_iso_a9, 1460, 2050);
        public static final android.print.PrintAttributes.MediaSize ISO_A10 = new android.print.PrintAttributes.MediaSize("ISO_A10", "android", com.android.internal.R.string.mediasize_iso_a10, 1020, 1460);
        public static final android.print.PrintAttributes.MediaSize ISO_B0 = new android.print.PrintAttributes.MediaSize("ISO_B0", "android", com.android.internal.R.string.mediasize_iso_b0, 39370, 55670);
        public static final android.print.PrintAttributes.MediaSize ISO_B1 = new android.print.PrintAttributes.MediaSize("ISO_B1", "android", com.android.internal.R.string.mediasize_iso_b1, 27830, 39370);
        public static final android.print.PrintAttributes.MediaSize ISO_B2 = new android.print.PrintAttributes.MediaSize("ISO_B2", "android", com.android.internal.R.string.mediasize_iso_b2, 19690, 27830);
        public static final android.print.PrintAttributes.MediaSize ISO_B3 = new android.print.PrintAttributes.MediaSize("ISO_B3", "android", com.android.internal.R.string.mediasize_iso_b3, 13900, 19690);
        public static final android.print.PrintAttributes.MediaSize ISO_B4 = new android.print.PrintAttributes.MediaSize("ISO_B4", "android", com.android.internal.R.string.mediasize_iso_b4, 9840, 13900);
        public static final android.print.PrintAttributes.MediaSize ISO_B5 = new android.print.PrintAttributes.MediaSize("ISO_B5", "android", com.android.internal.R.string.mediasize_iso_b5, 6930, 9840);
        public static final android.print.PrintAttributes.MediaSize ISO_B6 = new android.print.PrintAttributes.MediaSize("ISO_B6", "android", com.android.internal.R.string.mediasize_iso_b6, 4920, 6930);
        public static final android.print.PrintAttributes.MediaSize ISO_B7 = new android.print.PrintAttributes.MediaSize("ISO_B7", "android", com.android.internal.R.string.mediasize_iso_b7, 3460, 4920);
        public static final android.print.PrintAttributes.MediaSize ISO_B8 = new android.print.PrintAttributes.MediaSize("ISO_B8", "android", com.android.internal.R.string.mediasize_iso_b8, 2440, 3460);
        public static final android.print.PrintAttributes.MediaSize ISO_B9 = new android.print.PrintAttributes.MediaSize("ISO_B9", "android", com.android.internal.R.string.mediasize_iso_b9, 1730, 2440);
        public static final android.print.PrintAttributes.MediaSize ISO_B10 = new android.print.PrintAttributes.MediaSize("ISO_B10", "android", com.android.internal.R.string.mediasize_iso_b10, 1220, 1730);
        public static final android.print.PrintAttributes.MediaSize ISO_C0 = new android.print.PrintAttributes.MediaSize("ISO_C0", "android", com.android.internal.R.string.mediasize_iso_c0, 36100, 51060);
        public static final android.print.PrintAttributes.MediaSize ISO_C1 = new android.print.PrintAttributes.MediaSize("ISO_C1", "android", com.android.internal.R.string.mediasize_iso_c1, 25510, 36100);
        public static final android.print.PrintAttributes.MediaSize ISO_C2 = new android.print.PrintAttributes.MediaSize("ISO_C2", "android", com.android.internal.R.string.mediasize_iso_c2, 18030, 25510);
        public static final android.print.PrintAttributes.MediaSize ISO_C3 = new android.print.PrintAttributes.MediaSize("ISO_C3", "android", com.android.internal.R.string.mediasize_iso_c3, 12760, 18030);
        public static final android.print.PrintAttributes.MediaSize ISO_C4 = new android.print.PrintAttributes.MediaSize("ISO_C4", "android", com.android.internal.R.string.mediasize_iso_c4, 9020, 12760);
        public static final android.print.PrintAttributes.MediaSize ISO_C5 = new android.print.PrintAttributes.MediaSize("ISO_C5", "android", com.android.internal.R.string.mediasize_iso_c5, 6380, 9020);
        public static final android.print.PrintAttributes.MediaSize ISO_C6 = new android.print.PrintAttributes.MediaSize("ISO_C6", "android", com.android.internal.R.string.mediasize_iso_c6, 4490, 6380);
        public static final android.print.PrintAttributes.MediaSize ISO_C7 = new android.print.PrintAttributes.MediaSize("ISO_C7", "android", com.android.internal.R.string.mediasize_iso_c7, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_WRITE_CLB_DATA, 4490);
        public static final android.print.PrintAttributes.MediaSize ISO_C8 = new android.print.PrintAttributes.MediaSize("ISO_C8", "android", com.android.internal.R.string.mediasize_iso_c8, 2240, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_WRITE_CLB_DATA);
        public static final android.print.PrintAttributes.MediaSize ISO_C9 = new android.print.PrintAttributes.MediaSize("ISO_C9", "android", com.android.internal.R.string.mediasize_iso_c9, 1570, 2240);
        public static final android.print.PrintAttributes.MediaSize ISO_C10 = new android.print.PrintAttributes.MediaSize("ISO_C10", "android", com.android.internal.R.string.mediasize_iso_c10, 1100, 1570);
        public static final android.print.PrintAttributes.MediaSize NA_LETTER = new android.print.PrintAttributes.MediaSize("NA_LETTER", "android", com.android.internal.R.string.mediasize_na_letter, 8500, 11000);
        public static final android.print.PrintAttributes.MediaSize NA_GOVT_LETTER = new android.print.PrintAttributes.MediaSize("NA_GOVT_LETTER", "android", com.android.internal.R.string.mediasize_na_gvrnmt_letter, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, android.telephony.DataFailCause.IWLAN_NETWORK_FAILURE);
        public static final android.print.PrintAttributes.MediaSize NA_LEGAL = new android.print.PrintAttributes.MediaSize("NA_LEGAL", "android", com.android.internal.R.string.mediasize_na_legal, 8500, 14000);
        public static final android.print.PrintAttributes.MediaSize NA_JUNIOR_LEGAL = new android.print.PrintAttributes.MediaSize("NA_JUNIOR_LEGAL", "android", com.android.internal.R.string.mediasize_na_junior_legal, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, 5000);
        public static final android.print.PrintAttributes.MediaSize NA_LEDGER = new android.print.PrintAttributes.MediaSize("NA_LEDGER", "android", com.android.internal.R.string.mediasize_na_ledger, 17000, 11000);
        public static final android.print.PrintAttributes.MediaSize NA_TABLOID = new android.print.PrintAttributes.MediaSize("NA_TABLOID", "android", com.android.internal.R.string.mediasize_na_tabloid, 11000, 17000);
        public static final android.print.PrintAttributes.MediaSize NA_INDEX_3X5 = new android.print.PrintAttributes.MediaSize("NA_INDEX_3X5", "android", com.android.internal.R.string.mediasize_na_index_3x5, 3000, 5000);
        public static final android.print.PrintAttributes.MediaSize NA_INDEX_4X6 = new android.print.PrintAttributes.MediaSize("NA_INDEX_4X6", "android", com.android.internal.R.string.mediasize_na_index_4x6, 4000, 6000);
        public static final android.print.PrintAttributes.MediaSize NA_INDEX_5X8 = new android.print.PrintAttributes.MediaSize("NA_INDEX_5X8", "android", com.android.internal.R.string.mediasize_na_index_5x8, 5000, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION);
        public static final android.print.PrintAttributes.MediaSize NA_MONARCH = new android.print.PrintAttributes.MediaSize("NA_MONARCH", "android", com.android.internal.R.string.mediasize_na_monarch, 7250, android.telephony.DataFailCause.IWLAN_NETWORK_FAILURE);
        public static final android.print.PrintAttributes.MediaSize NA_QUARTO = new android.print.PrintAttributes.MediaSize("NA_QUARTO", "android", com.android.internal.R.string.mediasize_na_quarto, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, 10000);
        public static final android.print.PrintAttributes.MediaSize NA_FOOLSCAP = new android.print.PrintAttributes.MediaSize("NA_FOOLSCAP", "android", com.android.internal.R.string.mediasize_na_foolscap, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, 13000);
        public static final android.print.PrintAttributes.MediaSize ANSI_C = new android.print.PrintAttributes.MediaSize("ANSI_C", "android", com.android.internal.R.string.mediasize_na_ansi_c, 17000, 22000);
        public static final android.print.PrintAttributes.MediaSize ANSI_D = new android.print.PrintAttributes.MediaSize("ANSI_D", "android", com.android.internal.R.string.mediasize_na_ansi_d, 22000, 34000);
        public static final android.print.PrintAttributes.MediaSize ANSI_E = new android.print.PrintAttributes.MediaSize("ANSI_E", "android", com.android.internal.R.string.mediasize_na_ansi_e, 34000, 44000);
        public static final android.print.PrintAttributes.MediaSize ANSI_F = new android.print.PrintAttributes.MediaSize("ANSI_F", "android", com.android.internal.R.string.mediasize_na_ansi_f, 28000, 40000);
        public static final android.print.PrintAttributes.MediaSize NA_ARCH_A = new android.print.PrintAttributes.MediaSize("NA_ARCH_A", "android", com.android.internal.R.string.mediasize_na_arch_a, android.telephony.DataFailCause.IWLAN_NON_3GPP_ACCESS_TO_EPC_NOT_ALLOWED, 12000);
        public static final android.print.PrintAttributes.MediaSize NA_ARCH_B = new android.print.PrintAttributes.MediaSize("NA_ARCH_B", "android", com.android.internal.R.string.mediasize_na_arch_b, 12000, 18000);
        public static final android.print.PrintAttributes.MediaSize NA_ARCH_C = new android.print.PrintAttributes.MediaSize("NA_ARCH_C", "android", com.android.internal.R.string.mediasize_na_arch_c, 18000, 24000);
        public static final android.print.PrintAttributes.MediaSize NA_ARCH_D = new android.print.PrintAttributes.MediaSize("NA_ARCH_D", "android", com.android.internal.R.string.mediasize_na_arch_d, 24000, 36000);
        public static final android.print.PrintAttributes.MediaSize NA_ARCH_E = new android.print.PrintAttributes.MediaSize("NA_ARCH_E", "android", com.android.internal.R.string.mediasize_na_arch_e, 36000, 48000);
        public static final android.print.PrintAttributes.MediaSize NA_ARCH_E1 = new android.print.PrintAttributes.MediaSize("NA_ARCH_E1", "android", com.android.internal.R.string.mediasize_na_arch_e1, 30000, 42000);
        public static final android.print.PrintAttributes.MediaSize NA_SUPER_B = new android.print.PrintAttributes.MediaSize("NA_SUPER_B", "android", com.android.internal.R.string.mediasize_na_super_b, 13000, 19000);
        public static final android.print.PrintAttributes.MediaSize ROC_8K = new android.print.PrintAttributes.MediaSize("ROC_8K", "android", com.android.internal.R.string.mediasize_chinese_roc_8k, 10629, 15354);
        public static final android.print.PrintAttributes.MediaSize ROC_16K = new android.print.PrintAttributes.MediaSize("ROC_16K", "android", com.android.internal.R.string.mediasize_chinese_roc_16k, 7677, 10629);
        public static final android.print.PrintAttributes.MediaSize PRC_1 = new android.print.PrintAttributes.MediaSize("PRC_1", "android", com.android.internal.R.string.mediasize_chinese_prc_1, android.adservices.AdservicesProtoEnums.RESERVED_ERROR_CODE_4015, 6496);
        public static final android.print.PrintAttributes.MediaSize PRC_2 = new android.print.PrintAttributes.MediaSize("PRC_2", "android", com.android.internal.R.string.mediasize_chinese_prc_2, android.adservices.AdservicesProtoEnums.RESERVED_ERROR_CODE_4015, 6929);
        public static final android.print.PrintAttributes.MediaSize PRC_3 = new android.print.PrintAttributes.MediaSize("PRC_3", "android", com.android.internal.R.string.mediasize_chinese_prc_3, 4921, 6929);
        public static final android.print.PrintAttributes.MediaSize PRC_4 = new android.print.PrintAttributes.MediaSize("PRC_4", "android", com.android.internal.R.string.mediasize_chinese_prc_4, 4330, 8189);
        public static final android.print.PrintAttributes.MediaSize PRC_5 = new android.print.PrintAttributes.MediaSize("PRC_5", "android", com.android.internal.R.string.mediasize_chinese_prc_5, 4330, 8661);
        public static final android.print.PrintAttributes.MediaSize PRC_6 = new android.print.PrintAttributes.MediaSize("PRC_6", "android", com.android.internal.R.string.mediasize_chinese_prc_6, 4724, 12599);
        public static final android.print.PrintAttributes.MediaSize PRC_7 = new android.print.PrintAttributes.MediaSize("PRC_7", "android", com.android.internal.R.string.mediasize_chinese_prc_7, 6299, 9055);
        public static final android.print.PrintAttributes.MediaSize PRC_8 = new android.print.PrintAttributes.MediaSize("PRC_8", "android", com.android.internal.R.string.mediasize_chinese_prc_8, 4724, 12165);
        public static final android.print.PrintAttributes.MediaSize PRC_9 = new android.print.PrintAttributes.MediaSize("PRC_9", "android", com.android.internal.R.string.mediasize_chinese_prc_9, 9016, 12756);
        public static final android.print.PrintAttributes.MediaSize PRC_10 = new android.print.PrintAttributes.MediaSize("PRC_10", "android", com.android.internal.R.string.mediasize_chinese_prc_10, 12756, 18032);
        public static final android.print.PrintAttributes.MediaSize PRC_16K = new android.print.PrintAttributes.MediaSize("PRC_16K", "android", com.android.internal.R.string.mediasize_chinese_prc_16k, 5749, 8465);
        public static final android.print.PrintAttributes.MediaSize OM_PA_KAI = new android.print.PrintAttributes.MediaSize("OM_PA_KAI", "android", com.android.internal.R.string.mediasize_chinese_om_pa_kai, 10512, 15315);
        public static final android.print.PrintAttributes.MediaSize OM_DAI_PA_KAI = new android.print.PrintAttributes.MediaSize("OM_DAI_PA_KAI", "android", com.android.internal.R.string.mediasize_chinese_om_dai_pa_kai, 10827, 15551);
        public static final android.print.PrintAttributes.MediaSize OM_JUURO_KU_KAI = new android.print.PrintAttributes.MediaSize("OM_JUURO_KU_KAI", "android", com.android.internal.R.string.mediasize_chinese_om_jurro_ku_kai, 7796, 10827);
        public static final android.print.PrintAttributes.MediaSize JIS_B10 = new android.print.PrintAttributes.MediaSize("JIS_B10", "android", com.android.internal.R.string.mediasize_japanese_jis_b10, com.android.internal.logging.nano.MetricsProto.MetricsEvent.NOTIFICATION_ZEN_MODE_TOGGLE_ON_FOREVER, android.app.settings.SettingsEnums.ACTION_BATTERY_DEFENDER_TIP);
        public static final android.print.PrintAttributes.MediaSize JIS_B9 = new android.print.PrintAttributes.MediaSize("JIS_B9", "android", com.android.internal.R.string.mediasize_japanese_jis_b9, android.app.settings.SettingsEnums.ACTION_BATTERY_DEFENDER_TIP, 2520);
        public static final android.print.PrintAttributes.MediaSize JIS_B8 = new android.print.PrintAttributes.MediaSize("JIS_B8", "android", com.android.internal.R.string.mediasize_japanese_jis_b8, 2520, 3583);
        public static final android.print.PrintAttributes.MediaSize JIS_B7 = new android.print.PrintAttributes.MediaSize("JIS_B7", "android", com.android.internal.R.string.mediasize_japanese_jis_b7, 3583, 5049);
        public static final android.print.PrintAttributes.MediaSize JIS_B6 = new android.print.PrintAttributes.MediaSize("JIS_B6", "android", com.android.internal.R.string.mediasize_japanese_jis_b6, 5049, 7165);
        public static final android.print.PrintAttributes.MediaSize JIS_B5 = new android.print.PrintAttributes.MediaSize("JIS_B5", "android", com.android.internal.R.string.mediasize_japanese_jis_b5, 7165, 10118);
        public static final android.print.PrintAttributes.MediaSize JIS_B4 = new android.print.PrintAttributes.MediaSize("JIS_B4", "android", com.android.internal.R.string.mediasize_japanese_jis_b4, 10118, 14331);
        public static final android.print.PrintAttributes.MediaSize JIS_B3 = new android.print.PrintAttributes.MediaSize("JIS_B3", "android", com.android.internal.R.string.mediasize_japanese_jis_b3, 14331, 20276);
        public static final android.print.PrintAttributes.MediaSize JIS_B2 = new android.print.PrintAttributes.MediaSize("JIS_B2", "android", com.android.internal.R.string.mediasize_japanese_jis_b2, 20276, 28661);
        public static final android.print.PrintAttributes.MediaSize JIS_B1 = new android.print.PrintAttributes.MediaSize("JIS_B1", "android", com.android.internal.R.string.mediasize_japanese_jis_b1, 28661, 40551);
        public static final android.print.PrintAttributes.MediaSize JIS_B0 = new android.print.PrintAttributes.MediaSize("JIS_B0", "android", com.android.internal.R.string.mediasize_japanese_jis_b0, 40551, 57323);
        public static final android.print.PrintAttributes.MediaSize JIS_EXEC = new android.print.PrintAttributes.MediaSize("JIS_EXEC", "android", com.android.internal.R.string.mediasize_japanese_jis_exec, 8504, 12992);
        public static final android.print.PrintAttributes.MediaSize JPN_CHOU4 = new android.print.PrintAttributes.MediaSize("JPN_CHOU4", "android", com.android.internal.R.string.mediasize_japanese_chou4, 3543, 8071);
        public static final android.print.PrintAttributes.MediaSize JPN_CHOU3 = new android.print.PrintAttributes.MediaSize("JPN_CHOU3", "android", com.android.internal.R.string.mediasize_japanese_chou3, 4724, 9252);
        public static final android.print.PrintAttributes.MediaSize JPN_CHOU2 = new android.print.PrintAttributes.MediaSize("JPN_CHOU2", "android", com.android.internal.R.string.mediasize_japanese_chou2, com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY, 5748);
        public static final android.print.PrintAttributes.MediaSize JPN_HAGAKI = new android.print.PrintAttributes.MediaSize("JPN_HAGAKI", "android", com.android.internal.R.string.mediasize_japanese_hagaki, 3937, 5827);
        public static final android.print.PrintAttributes.MediaSize JPN_OUFUKU = new android.print.PrintAttributes.MediaSize("JPN_OUFUKU", "android", com.android.internal.R.string.mediasize_japanese_oufuku, 5827, 7874);
        public static final android.print.PrintAttributes.MediaSize JPN_KAHU = new android.print.PrintAttributes.MediaSize("JPN_KAHU", "android", com.android.internal.R.string.mediasize_japanese_kahu, 9449, 12681);
        public static final android.print.PrintAttributes.MediaSize JPN_KAKU2 = new android.print.PrintAttributes.MediaSize("JPN_KAKU2", "android", com.android.internal.R.string.mediasize_japanese_kaku2, 9449, 13071);
        public static final android.print.PrintAttributes.MediaSize JPN_YOU4 = new android.print.PrintAttributes.MediaSize("JPN_YOU4", "android", com.android.internal.R.string.mediasize_japanese_you4, 4134, 9252);
        public static final android.print.PrintAttributes.MediaSize JPN_OE_PHOTO_L = new android.print.PrintAttributes.MediaSize("JPN_OE_PHOTO_L", "android", com.android.internal.R.string.mediasize_japanese_l, 3500, 5000);

        public MediaSize(java.lang.String str, java.lang.String str2, int i, int i2, int i3) {
            this(str, null, str2, i2, i3, i);
            sIdToMediaSizeMap.put(this.mId, this);
        }

        public MediaSize(java.lang.String str, java.lang.String str2, int i, int i2) {
            this(str, str2, null, i, i2, 0);
        }

        public static android.util.ArraySet<android.print.PrintAttributes.MediaSize> getAllPredefinedSizes() {
            android.util.ArraySet<android.print.PrintAttributes.MediaSize> arraySet = new android.util.ArraySet<>(sIdToMediaSizeMap.values());
            arraySet.remove(UNKNOWN_PORTRAIT);
            arraySet.remove(UNKNOWN_LANDSCAPE);
            return arraySet;
        }

        public MediaSize(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) {
            this.mPackageName = str3;
            this.mId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "id cannot be empty.");
            this.mLabelResId = i3;
            this.mWidthMils = com.android.internal.util.Preconditions.checkArgumentPositive(i, "widthMils cannot be less than or equal to zero.");
            this.mHeightMils = com.android.internal.util.Preconditions.checkArgumentPositive(i2, "heightMils cannot be less than or equal to zero.");
            this.mLabel = str2;
            com.android.internal.util.Preconditions.checkArgument((android.text.TextUtils.isEmpty(str2) ^ true) != (!android.text.TextUtils.isEmpty(str3) && i3 != 0), "label cannot be empty.");
        }

        public java.lang.String getId() {
            return this.mId;
        }

        public java.lang.String getLabel(android.content.pm.PackageManager packageManager) {
            if (!android.text.TextUtils.isEmpty(this.mPackageName) && this.mLabelResId > 0) {
                try {
                    return packageManager.getResourcesForApplication(this.mPackageName).getString(this.mLabelResId);
                } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
                    android.util.Log.w(LOG_TAG, "Could not load resouce" + this.mLabelResId + " from package " + this.mPackageName);
                }
            }
            return this.mLabel;
        }

        public int getWidthMils() {
            return this.mWidthMils;
        }

        public int getHeightMils() {
            return this.mHeightMils;
        }

        public boolean isPortrait() {
            return this.mHeightMils >= this.mWidthMils;
        }

        public android.print.PrintAttributes.MediaSize asPortrait() {
            if (isPortrait()) {
                return this;
            }
            return new android.print.PrintAttributes.MediaSize(this.mId, this.mLabel, this.mPackageName, java.lang.Math.min(this.mWidthMils, this.mHeightMils), java.lang.Math.max(this.mWidthMils, this.mHeightMils), this.mLabelResId);
        }

        public android.print.PrintAttributes.MediaSize asLandscape() {
            if (!isPortrait()) {
                return this;
            }
            return new android.print.PrintAttributes.MediaSize(this.mId, this.mLabel, this.mPackageName, java.lang.Math.max(this.mWidthMils, this.mHeightMils), java.lang.Math.min(this.mWidthMils, this.mHeightMils), this.mLabelResId);
        }

        void writeToParcel(android.os.Parcel parcel) {
            parcel.writeString(this.mId);
            parcel.writeString(this.mLabel);
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mWidthMils);
            parcel.writeInt(this.mHeightMils);
            parcel.writeInt(this.mLabelResId);
        }

        static android.print.PrintAttributes.MediaSize createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintAttributes.MediaSize(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public int hashCode() {
            return ((this.mWidthMils + 31) * 31) + this.mHeightMils;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.print.PrintAttributes.MediaSize mediaSize = (android.print.PrintAttributes.MediaSize) obj;
            if (this.mWidthMils == mediaSize.mWidthMils && this.mHeightMils == mediaSize.mHeightMils) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("MediaSize{");
            sb.append("id: ").append(this.mId);
            sb.append(", label: ").append(this.mLabel);
            sb.append(", packageName: ").append(this.mPackageName);
            sb.append(", heightMils: ").append(this.mHeightMils);
            sb.append(", widthMils: ").append(this.mWidthMils);
            sb.append(", labelResId: ").append(this.mLabelResId);
            sb.append("}");
            return sb.toString();
        }

        public static android.print.PrintAttributes.MediaSize getStandardMediaSizeById(java.lang.String str) {
            return sIdToMediaSizeMap.get(str);
        }
    }

    public static final class Resolution {
        private final int mHorizontalDpi;
        private final java.lang.String mId;
        private final java.lang.String mLabel;
        private final int mVerticalDpi;

        public Resolution(java.lang.String str, java.lang.String str2, int i, int i2) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("id cannot be empty.");
            }
            if (android.text.TextUtils.isEmpty(str2)) {
                throw new java.lang.IllegalArgumentException("label cannot be empty.");
            }
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("horizontalDpi cannot be less than or equal to zero.");
            }
            if (i2 <= 0) {
                throw new java.lang.IllegalArgumentException("verticalDpi cannot be less than or equal to zero.");
            }
            this.mId = str;
            this.mLabel = str2;
            this.mHorizontalDpi = i;
            this.mVerticalDpi = i2;
        }

        public java.lang.String getId() {
            return this.mId;
        }

        public java.lang.String getLabel() {
            return this.mLabel;
        }

        public int getHorizontalDpi() {
            return this.mHorizontalDpi;
        }

        public int getVerticalDpi() {
            return this.mVerticalDpi;
        }

        void writeToParcel(android.os.Parcel parcel) {
            parcel.writeString(this.mId);
            parcel.writeString(this.mLabel);
            parcel.writeInt(this.mHorizontalDpi);
            parcel.writeInt(this.mVerticalDpi);
        }

        static android.print.PrintAttributes.Resolution createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintAttributes.Resolution(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        public int hashCode() {
            return ((this.mHorizontalDpi + 31) * 31) + this.mVerticalDpi;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.print.PrintAttributes.Resolution resolution = (android.print.PrintAttributes.Resolution) obj;
            if (this.mHorizontalDpi == resolution.mHorizontalDpi && this.mVerticalDpi == resolution.mVerticalDpi) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Resolution{");
            sb.append("id: ").append(this.mId);
            sb.append(", label: ").append(this.mLabel);
            sb.append(", horizontalDpi: ").append(this.mHorizontalDpi);
            sb.append(", verticalDpi: ").append(this.mVerticalDpi);
            sb.append("}");
            return sb.toString();
        }
    }

    public static final class Margins {
        public static final android.print.PrintAttributes.Margins NO_MARGINS = new android.print.PrintAttributes.Margins(0, 0, 0, 0);
        private final int mBottomMils;
        private final int mLeftMils;
        private final int mRightMils;
        private final int mTopMils;

        public Margins(int i, int i2, int i3, int i4) {
            this.mTopMils = i2;
            this.mLeftMils = i;
            this.mRightMils = i3;
            this.mBottomMils = i4;
        }

        public int getLeftMils() {
            return this.mLeftMils;
        }

        public int getTopMils() {
            return this.mTopMils;
        }

        public int getRightMils() {
            return this.mRightMils;
        }

        public int getBottomMils() {
            return this.mBottomMils;
        }

        void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.mLeftMils);
            parcel.writeInt(this.mTopMils);
            parcel.writeInt(this.mRightMils);
            parcel.writeInt(this.mBottomMils);
        }

        static android.print.PrintAttributes.Margins createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintAttributes.Margins(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public int hashCode() {
            return ((((((this.mBottomMils + 31) * 31) + this.mLeftMils) * 31) + this.mRightMils) * 31) + this.mTopMils;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.print.PrintAttributes.Margins margins = (android.print.PrintAttributes.Margins) obj;
            if (this.mBottomMils == margins.mBottomMils && this.mLeftMils == margins.mLeftMils && this.mRightMils == margins.mRightMils && this.mTopMils == margins.mTopMils) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Margins{");
            sb.append("leftMils: ").append(this.mLeftMils);
            sb.append(", topMils: ").append(this.mTopMils);
            sb.append(", rightMils: ").append(this.mRightMils);
            sb.append(", bottomMils: ").append(this.mBottomMils);
            sb.append("}");
            return sb.toString();
        }
    }

    static java.lang.String colorModeToString(int i) {
        switch (i) {
            case 1:
                return "COLOR_MODE_MONOCHROME";
            case 2:
                return "COLOR_MODE_COLOR";
            default:
                return "COLOR_MODE_UNKNOWN";
        }
    }

    static java.lang.String duplexModeToString(int i) {
        switch (i) {
            case 1:
                return "DUPLEX_MODE_NONE";
            case 2:
                return "DUPLEX_MODE_LONG_EDGE";
            case 3:
            default:
                return "DUPLEX_MODE_UNKNOWN";
            case 4:
                return "DUPLEX_MODE_SHORT_EDGE";
        }
    }

    static void enforceValidColorMode(int i) {
        if ((i & 3) == 0 || java.lang.Integer.bitCount(i) != 1) {
            throw new java.lang.IllegalArgumentException("invalid color mode: " + i);
        }
    }

    static void enforceValidDuplexMode(int i) {
        if ((i & 7) == 0 || java.lang.Integer.bitCount(i) != 1) {
            throw new java.lang.IllegalArgumentException("invalid duplex mode: " + i);
        }
    }

    public static final class Builder {
        private final android.print.PrintAttributes mAttributes = new android.print.PrintAttributes();

        public android.print.PrintAttributes.Builder setMediaSize(android.print.PrintAttributes.MediaSize mediaSize) {
            this.mAttributes.setMediaSize(mediaSize);
            return this;
        }

        public android.print.PrintAttributes.Builder setResolution(android.print.PrintAttributes.Resolution resolution) {
            this.mAttributes.setResolution(resolution);
            return this;
        }

        public android.print.PrintAttributes.Builder setMinMargins(android.print.PrintAttributes.Margins margins) {
            this.mAttributes.setMinMargins(margins);
            return this;
        }

        public android.print.PrintAttributes.Builder setColorMode(int i) {
            this.mAttributes.setColorMode(i);
            return this;
        }

        public android.print.PrintAttributes.Builder setDuplexMode(int i) {
            this.mAttributes.setDuplexMode(i);
            return this;
        }

        public android.print.PrintAttributes build() {
            return this.mAttributes;
        }
    }
}
