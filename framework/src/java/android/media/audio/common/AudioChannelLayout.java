package android.media.audio.common;

/* loaded from: classes2.dex */
public final class AudioChannelLayout implements android.os.Parcelable {
    public static final int CHANNEL_BACK_CENTER = 256;
    public static final int CHANNEL_BACK_LEFT = 16;
    public static final int CHANNEL_BACK_RIGHT = 32;
    public static final int CHANNEL_BOTTOM_FRONT_CENTER = 2097152;
    public static final int CHANNEL_BOTTOM_FRONT_LEFT = 1048576;
    public static final int CHANNEL_BOTTOM_FRONT_RIGHT = 4194304;
    public static final int CHANNEL_FRONT_CENTER = 4;
    public static final int CHANNEL_FRONT_LEFT = 1;
    public static final int CHANNEL_FRONT_LEFT_OF_CENTER = 64;
    public static final int CHANNEL_FRONT_RIGHT = 2;
    public static final int CHANNEL_FRONT_RIGHT_OF_CENTER = 128;
    public static final int CHANNEL_FRONT_WIDE_LEFT = 16777216;
    public static final int CHANNEL_FRONT_WIDE_RIGHT = 33554432;
    public static final int CHANNEL_HAPTIC_A = 1073741824;
    public static final int CHANNEL_HAPTIC_B = 536870912;
    public static final int CHANNEL_LOW_FREQUENCY = 8;
    public static final int CHANNEL_LOW_FREQUENCY_2 = 8388608;
    public static final int CHANNEL_SIDE_LEFT = 512;
    public static final int CHANNEL_SIDE_RIGHT = 1024;
    public static final int CHANNEL_TOP_BACK_CENTER = 65536;
    public static final int CHANNEL_TOP_BACK_LEFT = 32768;
    public static final int CHANNEL_TOP_BACK_RIGHT = 131072;
    public static final int CHANNEL_TOP_CENTER = 2048;
    public static final int CHANNEL_TOP_FRONT_CENTER = 8192;
    public static final int CHANNEL_TOP_FRONT_LEFT = 4096;
    public static final int CHANNEL_TOP_FRONT_RIGHT = 16384;
    public static final int CHANNEL_TOP_SIDE_LEFT = 262144;
    public static final int CHANNEL_TOP_SIDE_RIGHT = 524288;
    public static final int CHANNEL_VOICE_DNLINK = 32768;
    public static final int CHANNEL_VOICE_UPLINK = 16384;
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioChannelLayout> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioChannelLayout>() { // from class: android.media.audio.common.AudioChannelLayout.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioChannelLayout createFromParcel(android.os.Parcel parcel) {
            return new android.media.audio.common.AudioChannelLayout(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioChannelLayout[] newArray(int i) {
            return new android.media.audio.common.AudioChannelLayout[i];
        }
    };
    public static final int INDEX_MASK_1 = 1;
    public static final int INDEX_MASK_10 = 1023;
    public static final int INDEX_MASK_11 = 2047;
    public static final int INDEX_MASK_12 = 4095;
    public static final int INDEX_MASK_13 = 8191;
    public static final int INDEX_MASK_14 = 16383;
    public static final int INDEX_MASK_15 = 32767;
    public static final int INDEX_MASK_16 = 65535;
    public static final int INDEX_MASK_17 = 131071;
    public static final int INDEX_MASK_18 = 262143;
    public static final int INDEX_MASK_19 = 524287;
    public static final int INDEX_MASK_2 = 3;
    public static final int INDEX_MASK_20 = 1048575;
    public static final int INDEX_MASK_21 = 2097151;
    public static final int INDEX_MASK_22 = 4194303;
    public static final int INDEX_MASK_23 = 8388607;
    public static final int INDEX_MASK_24 = 16777215;
    public static final int INDEX_MASK_3 = 7;
    public static final int INDEX_MASK_4 = 15;
    public static final int INDEX_MASK_5 = 31;
    public static final int INDEX_MASK_6 = 63;
    public static final int INDEX_MASK_7 = 127;
    public static final int INDEX_MASK_8 = 255;
    public static final int INDEX_MASK_9 = 511;
    public static final int INTERLEAVE_LEFT = 0;
    public static final int INTERLEAVE_RIGHT = 1;
    public static final int LAYOUT_13POINT_360RA = 7534087;
    public static final int LAYOUT_22POINT2 = 16777215;
    public static final int LAYOUT_2POINT0POINT2 = 786435;
    public static final int LAYOUT_2POINT1 = 11;
    public static final int LAYOUT_2POINT1POINT2 = 786443;
    public static final int LAYOUT_3POINT0POINT2 = 786439;
    public static final int LAYOUT_3POINT1 = 15;
    public static final int LAYOUT_3POINT1POINT2 = 786447;
    public static final int LAYOUT_5POINT1 = 63;
    public static final int LAYOUT_5POINT1POINT2 = 786495;
    public static final int LAYOUT_5POINT1POINT4 = 184383;
    public static final int LAYOUT_5POINT1_SIDE = 1551;
    public static final int LAYOUT_6POINT1 = 319;
    public static final int LAYOUT_7POINT1 = 1599;
    public static final int LAYOUT_7POINT1POINT2 = 788031;
    public static final int LAYOUT_7POINT1POINT4 = 185919;
    public static final int LAYOUT_9POINT1POINT4 = 50517567;
    public static final int LAYOUT_9POINT1POINT6 = 51303999;
    public static final int LAYOUT_FRONT_BACK = 260;
    public static final int LAYOUT_HAPTIC_AB = 1610612736;
    public static final int LAYOUT_MONO = 1;
    public static final int LAYOUT_MONO_HAPTIC_A = 1073741825;
    public static final int LAYOUT_MONO_HAPTIC_AB = 1610612737;
    public static final int LAYOUT_PENTA = 55;
    public static final int LAYOUT_QUAD = 51;
    public static final int LAYOUT_QUAD_SIDE = 1539;
    public static final int LAYOUT_STEREO = 3;
    public static final int LAYOUT_STEREO_HAPTIC_A = 1073741827;
    public static final int LAYOUT_STEREO_HAPTIC_AB = 1610612739;
    public static final int LAYOUT_SURROUND = 263;
    public static final int LAYOUT_TRI = 7;
    public static final int LAYOUT_TRI_BACK = 259;
    public static final int VOICE_CALL_MONO = 49152;
    public static final int VOICE_DNLINK_MONO = 32768;
    public static final int VOICE_UPLINK_MONO = 16384;
    public static final int indexMask = 2;
    public static final int invalid = 1;
    public static final int layoutMask = 3;
    public static final int none = 0;
    public static final int voiceMask = 4;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int indexMask = 2;
        public static final int invalid = 1;
        public static final int layoutMask = 3;
        public static final int none = 0;
        public static final int voiceMask = 4;
    }

    public AudioChannelLayout() {
        this._tag = 0;
        this._value = 0;
    }

    private AudioChannelLayout(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioChannelLayout(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.audio.common.AudioChannelLayout none(int i) {
        return new android.media.audio.common.AudioChannelLayout(0, java.lang.Integer.valueOf(i));
    }

    public int getNone() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setNone(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.media.audio.common.AudioChannelLayout invalid(int i) {
        return new android.media.audio.common.AudioChannelLayout(1, java.lang.Integer.valueOf(i));
    }

    public int getInvalid() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setInvalid(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.media.audio.common.AudioChannelLayout indexMask(int i) {
        return new android.media.audio.common.AudioChannelLayout(2, java.lang.Integer.valueOf(i));
    }

    public int getIndexMask() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIndexMask(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.media.audio.common.AudioChannelLayout layoutMask(int i) {
        return new android.media.audio.common.AudioChannelLayout(3, java.lang.Integer.valueOf(i));
    }

    public int getLayoutMask() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setLayoutMask(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.media.audio.common.AudioChannelLayout voiceMask(int i) {
        return new android.media.audio.common.AudioChannelLayout(4, java.lang.Integer.valueOf(i));
    }

    public int getVoiceMask() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setVoiceMask(int i) {
        _set(4, java.lang.Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeInt(getNone());
                break;
            case 1:
                parcel.writeInt(getInvalid());
                break;
            case 2:
                parcel.writeInt(getIndexMask());
                break;
            case 3:
                parcel.writeInt(getLayoutMask());
                break;
            case 4:
                parcel.writeInt(getVoiceMask());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 1:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 4:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "AudioChannelLayout.none(" + getNone() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "AudioChannelLayout.invalid(" + getInvalid() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "AudioChannelLayout.indexMask(" + getIndexMask() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "AudioChannelLayout.layoutMask(" + getLayoutMask() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "AudioChannelLayout.voiceMask(" + getVoiceMask() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioChannelLayout)) {
            return false;
        }
        android.media.audio.common.AudioChannelLayout audioChannelLayout = (android.media.audio.common.AudioChannelLayout) obj;
        if (this._tag == audioChannelLayout._tag && java.util.Objects.deepEquals(this._value, audioChannelLayout._value)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this._tag), this._value).toArray());
    }

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "invalid";
            case 2:
                return "indexMask";
            case 3:
                return "layoutMask";
            case 4:
                return "voiceMask";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
