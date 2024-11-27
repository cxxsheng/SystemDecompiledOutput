package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public final class Metadata implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.Metadata> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.Metadata>() { // from class: android.hardware.broadcastradio.Metadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.Metadata createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.broadcastradio.Metadata(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.Metadata[] newArray(int i) {
            return new android.hardware.broadcastradio.Metadata[i];
        }
    };
    public static final int albumArt = 8;
    public static final int commentActualText = 18;
    public static final int commentShortDescription = 17;
    public static final int commercial = 19;
    public static final int dabComponentName = 14;
    public static final int dabComponentNameShort = 15;
    public static final int dabEnsembleName = 10;
    public static final int dabEnsembleNameShort = 11;
    public static final int dabServiceName = 12;
    public static final int dabServiceNameShort = 13;
    public static final int genre = 16;
    public static final int hdStationNameLong = 22;
    public static final int hdStationNameShort = 21;
    public static final int hdSubChannelsAvailable = 23;
    public static final int programName = 9;
    public static final int rbdsPty = 2;
    public static final int rdsPs = 0;
    public static final int rdsPty = 1;
    public static final int rdsRt = 3;
    public static final int songAlbum = 6;
    public static final int songArtist = 5;
    public static final int songTitle = 4;
    public static final int stationIcon = 7;
    public static final int ufids = 20;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int albumArt = 8;
        public static final int commentActualText = 18;
        public static final int commentShortDescription = 17;
        public static final int commercial = 19;
        public static final int dabComponentName = 14;
        public static final int dabComponentNameShort = 15;
        public static final int dabEnsembleName = 10;
        public static final int dabEnsembleNameShort = 11;
        public static final int dabServiceName = 12;
        public static final int dabServiceNameShort = 13;
        public static final int genre = 16;
        public static final int hdStationNameLong = 22;
        public static final int hdStationNameShort = 21;
        public static final int hdSubChannelsAvailable = 23;
        public static final int programName = 9;
        public static final int rbdsPty = 2;
        public static final int rdsPs = 0;
        public static final int rdsPty = 1;
        public static final int rdsRt = 3;
        public static final int songAlbum = 6;
        public static final int songArtist = 5;
        public static final int songTitle = 4;
        public static final int stationIcon = 7;
        public static final int ufids = 20;
    }

    public Metadata() {
        this._tag = 0;
        this._value = null;
    }

    private Metadata(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private Metadata(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.broadcastradio.Metadata rdsPs(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(0, str);
    }

    public java.lang.String getRdsPs() {
        _assertTag(0);
        return (java.lang.String) this._value;
    }

    public void setRdsPs(java.lang.String str) {
        _set(0, str);
    }

    public static android.hardware.broadcastradio.Metadata rdsPty(int i) {
        return new android.hardware.broadcastradio.Metadata(1, java.lang.Integer.valueOf(i));
    }

    public int getRdsPty() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setRdsPty(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.broadcastradio.Metadata rbdsPty(int i) {
        return new android.hardware.broadcastradio.Metadata(2, java.lang.Integer.valueOf(i));
    }

    public int getRbdsPty() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setRbdsPty(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.broadcastradio.Metadata rdsRt(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(3, str);
    }

    public java.lang.String getRdsRt() {
        _assertTag(3);
        return (java.lang.String) this._value;
    }

    public void setRdsRt(java.lang.String str) {
        _set(3, str);
    }

    public static android.hardware.broadcastradio.Metadata songTitle(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(4, str);
    }

    public java.lang.String getSongTitle() {
        _assertTag(4);
        return (java.lang.String) this._value;
    }

    public void setSongTitle(java.lang.String str) {
        _set(4, str);
    }

    public static android.hardware.broadcastradio.Metadata songArtist(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(5, str);
    }

    public java.lang.String getSongArtist() {
        _assertTag(5);
        return (java.lang.String) this._value;
    }

    public void setSongArtist(java.lang.String str) {
        _set(5, str);
    }

    public static android.hardware.broadcastradio.Metadata songAlbum(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(6, str);
    }

    public java.lang.String getSongAlbum() {
        _assertTag(6);
        return (java.lang.String) this._value;
    }

    public void setSongAlbum(java.lang.String str) {
        _set(6, str);
    }

    public static android.hardware.broadcastradio.Metadata stationIcon(int i) {
        return new android.hardware.broadcastradio.Metadata(7, java.lang.Integer.valueOf(i));
    }

    public int getStationIcon() {
        _assertTag(7);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setStationIcon(int i) {
        _set(7, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.broadcastradio.Metadata albumArt(int i) {
        return new android.hardware.broadcastradio.Metadata(8, java.lang.Integer.valueOf(i));
    }

    public int getAlbumArt() {
        _assertTag(8);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAlbumArt(int i) {
        _set(8, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.broadcastradio.Metadata programName(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(9, str);
    }

    public java.lang.String getProgramName() {
        _assertTag(9);
        return (java.lang.String) this._value;
    }

    public void setProgramName(java.lang.String str) {
        _set(9, str);
    }

    public static android.hardware.broadcastradio.Metadata dabEnsembleName(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(10, str);
    }

    public java.lang.String getDabEnsembleName() {
        _assertTag(10);
        return (java.lang.String) this._value;
    }

    public void setDabEnsembleName(java.lang.String str) {
        _set(10, str);
    }

    public static android.hardware.broadcastradio.Metadata dabEnsembleNameShort(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(11, str);
    }

    public java.lang.String getDabEnsembleNameShort() {
        _assertTag(11);
        return (java.lang.String) this._value;
    }

    public void setDabEnsembleNameShort(java.lang.String str) {
        _set(11, str);
    }

    public static android.hardware.broadcastradio.Metadata dabServiceName(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(12, str);
    }

    public java.lang.String getDabServiceName() {
        _assertTag(12);
        return (java.lang.String) this._value;
    }

    public void setDabServiceName(java.lang.String str) {
        _set(12, str);
    }

    public static android.hardware.broadcastradio.Metadata dabServiceNameShort(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(13, str);
    }

    public java.lang.String getDabServiceNameShort() {
        _assertTag(13);
        return (java.lang.String) this._value;
    }

    public void setDabServiceNameShort(java.lang.String str) {
        _set(13, str);
    }

    public static android.hardware.broadcastradio.Metadata dabComponentName(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(14, str);
    }

    public java.lang.String getDabComponentName() {
        _assertTag(14);
        return (java.lang.String) this._value;
    }

    public void setDabComponentName(java.lang.String str) {
        _set(14, str);
    }

    public static android.hardware.broadcastradio.Metadata dabComponentNameShort(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(15, str);
    }

    public java.lang.String getDabComponentNameShort() {
        _assertTag(15);
        return (java.lang.String) this._value;
    }

    public void setDabComponentNameShort(java.lang.String str) {
        _set(15, str);
    }

    public static android.hardware.broadcastradio.Metadata genre(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(16, str);
    }

    public java.lang.String getGenre() {
        _assertTag(16);
        return (java.lang.String) this._value;
    }

    public void setGenre(java.lang.String str) {
        _set(16, str);
    }

    public static android.hardware.broadcastradio.Metadata commentShortDescription(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(17, str);
    }

    public java.lang.String getCommentShortDescription() {
        _assertTag(17);
        return (java.lang.String) this._value;
    }

    public void setCommentShortDescription(java.lang.String str) {
        _set(17, str);
    }

    public static android.hardware.broadcastradio.Metadata commentActualText(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(18, str);
    }

    public java.lang.String getCommentActualText() {
        _assertTag(18);
        return (java.lang.String) this._value;
    }

    public void setCommentActualText(java.lang.String str) {
        _set(18, str);
    }

    public static android.hardware.broadcastradio.Metadata commercial(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(19, str);
    }

    public java.lang.String getCommercial() {
        _assertTag(19);
        return (java.lang.String) this._value;
    }

    public void setCommercial(java.lang.String str) {
        _set(19, str);
    }

    public static android.hardware.broadcastradio.Metadata ufids(java.lang.String[] strArr) {
        return new android.hardware.broadcastradio.Metadata(20, strArr);
    }

    public java.lang.String[] getUfids() {
        _assertTag(20);
        return (java.lang.String[]) this._value;
    }

    public void setUfids(java.lang.String[] strArr) {
        _set(20, strArr);
    }

    public static android.hardware.broadcastradio.Metadata hdStationNameShort(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(21, str);
    }

    public java.lang.String getHdStationNameShort() {
        _assertTag(21);
        return (java.lang.String) this._value;
    }

    public void setHdStationNameShort(java.lang.String str) {
        _set(21, str);
    }

    public static android.hardware.broadcastradio.Metadata hdStationNameLong(java.lang.String str) {
        return new android.hardware.broadcastradio.Metadata(22, str);
    }

    public java.lang.String getHdStationNameLong() {
        _assertTag(22);
        return (java.lang.String) this._value;
    }

    public void setHdStationNameLong(java.lang.String str) {
        _set(22, str);
    }

    public static android.hardware.broadcastradio.Metadata hdSubChannelsAvailable(int i) {
        return new android.hardware.broadcastradio.Metadata(23, java.lang.Integer.valueOf(i));
    }

    public int getHdSubChannelsAvailable() {
        _assertTag(23);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setHdSubChannelsAvailable(int i) {
        _set(23, java.lang.Integer.valueOf(i));
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeString(getRdsPs());
                break;
            case 1:
                parcel.writeInt(getRdsPty());
                break;
            case 2:
                parcel.writeInt(getRbdsPty());
                break;
            case 3:
                parcel.writeString(getRdsRt());
                break;
            case 4:
                parcel.writeString(getSongTitle());
                break;
            case 5:
                parcel.writeString(getSongArtist());
                break;
            case 6:
                parcel.writeString(getSongAlbum());
                break;
            case 7:
                parcel.writeInt(getStationIcon());
                break;
            case 8:
                parcel.writeInt(getAlbumArt());
                break;
            case 9:
                parcel.writeString(getProgramName());
                break;
            case 10:
                parcel.writeString(getDabEnsembleName());
                break;
            case 11:
                parcel.writeString(getDabEnsembleNameShort());
                break;
            case 12:
                parcel.writeString(getDabServiceName());
                break;
            case 13:
                parcel.writeString(getDabServiceNameShort());
                break;
            case 14:
                parcel.writeString(getDabComponentName());
                break;
            case 15:
                parcel.writeString(getDabComponentNameShort());
                break;
            case 16:
                parcel.writeString(getGenre());
                break;
            case 17:
                parcel.writeString(getCommentShortDescription());
                break;
            case 18:
                parcel.writeString(getCommentActualText());
                break;
            case 19:
                parcel.writeString(getCommercial());
                break;
            case 20:
                parcel.writeStringArray(getUfids());
                break;
            case 21:
                parcel.writeString(getHdStationNameShort());
                break;
            case 22:
                parcel.writeString(getHdStationNameLong());
                break;
            case 23:
                parcel.writeInt(getHdSubChannelsAvailable());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, parcel.readString());
                return;
            case 1:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, parcel.readString());
                return;
            case 4:
                _set(readInt, parcel.readString());
                return;
            case 5:
                _set(readInt, parcel.readString());
                return;
            case 6:
                _set(readInt, parcel.readString());
                return;
            case 7:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 8:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 9:
                _set(readInt, parcel.readString());
                return;
            case 10:
                _set(readInt, parcel.readString());
                return;
            case 11:
                _set(readInt, parcel.readString());
                return;
            case 12:
                _set(readInt, parcel.readString());
                return;
            case 13:
                _set(readInt, parcel.readString());
                return;
            case 14:
                _set(readInt, parcel.readString());
                return;
            case 15:
                _set(readInt, parcel.readString());
                return;
            case 16:
                _set(readInt, parcel.readString());
                return;
            case 17:
                _set(readInt, parcel.readString());
                return;
            case 18:
                _set(readInt, parcel.readString());
                return;
            case 19:
                _set(readInt, parcel.readString());
                return;
            case 20:
                _set(readInt, parcel.createStringArray());
                return;
            case 21:
                _set(readInt, parcel.readString());
                return;
            case 22:
                _set(readInt, parcel.readString());
                return;
            case 23:
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
                return "Metadata.rdsPs(" + java.util.Objects.toString(getRdsPs()) + ")";
            case 1:
                return "Metadata.rdsPty(" + getRdsPty() + ")";
            case 2:
                return "Metadata.rbdsPty(" + getRbdsPty() + ")";
            case 3:
                return "Metadata.rdsRt(" + java.util.Objects.toString(getRdsRt()) + ")";
            case 4:
                return "Metadata.songTitle(" + java.util.Objects.toString(getSongTitle()) + ")";
            case 5:
                return "Metadata.songArtist(" + java.util.Objects.toString(getSongArtist()) + ")";
            case 6:
                return "Metadata.songAlbum(" + java.util.Objects.toString(getSongAlbum()) + ")";
            case 7:
                return "Metadata.stationIcon(" + getStationIcon() + ")";
            case 8:
                return "Metadata.albumArt(" + getAlbumArt() + ")";
            case 9:
                return "Metadata.programName(" + java.util.Objects.toString(getProgramName()) + ")";
            case 10:
                return "Metadata.dabEnsembleName(" + java.util.Objects.toString(getDabEnsembleName()) + ")";
            case 11:
                return "Metadata.dabEnsembleNameShort(" + java.util.Objects.toString(getDabEnsembleNameShort()) + ")";
            case 12:
                return "Metadata.dabServiceName(" + java.util.Objects.toString(getDabServiceName()) + ")";
            case 13:
                return "Metadata.dabServiceNameShort(" + java.util.Objects.toString(getDabServiceNameShort()) + ")";
            case 14:
                return "Metadata.dabComponentName(" + java.util.Objects.toString(getDabComponentName()) + ")";
            case 15:
                return "Metadata.dabComponentNameShort(" + java.util.Objects.toString(getDabComponentNameShort()) + ")";
            case 16:
                return "Metadata.genre(" + java.util.Objects.toString(getGenre()) + ")";
            case 17:
                return "Metadata.commentShortDescription(" + java.util.Objects.toString(getCommentShortDescription()) + ")";
            case 18:
                return "Metadata.commentActualText(" + java.util.Objects.toString(getCommentActualText()) + ")";
            case 19:
                return "Metadata.commercial(" + java.util.Objects.toString(getCommercial()) + ")";
            case 20:
                return "Metadata.ufids(" + java.util.Arrays.toString(getUfids()) + ")";
            case 21:
                return "Metadata.hdStationNameShort(" + java.util.Objects.toString(getHdStationNameShort()) + ")";
            case 22:
                return "Metadata.hdStationNameLong(" + java.util.Objects.toString(getHdStationNameLong()) + ")";
            case 23:
                return "Metadata.hdSubChannelsAvailable(" + getHdSubChannelsAvailable() + ")";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.Metadata)) {
            return false;
        }
        android.hardware.broadcastradio.Metadata metadata = (android.hardware.broadcastradio.Metadata) obj;
        if (this._tag == metadata._tag && java.util.Objects.deepEquals(this._value, metadata._value)) {
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
                return "rdsPs";
            case 1:
                return "rdsPty";
            case 2:
                return "rbdsPty";
            case 3:
                return "rdsRt";
            case 4:
                return "songTitle";
            case 5:
                return "songArtist";
            case 6:
                return "songAlbum";
            case 7:
                return "stationIcon";
            case 8:
                return "albumArt";
            case 9:
                return "programName";
            case 10:
                return "dabEnsembleName";
            case 11:
                return "dabEnsembleNameShort";
            case 12:
                return "dabServiceName";
            case 13:
                return "dabServiceNameShort";
            case 14:
                return "dabComponentName";
            case 15:
                return "dabComponentNameShort";
            case 16:
                return "genre";
            case 17:
                return "commentShortDescription";
            case 18:
                return "commentActualText";
            case 19:
                return "commercial";
            case 20:
                return "ufids";
            case 21:
                return "hdStationNameShort";
            case 22:
                return "hdStationNameLong";
            case 23:
                return "hdSubChannelsAvailable";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
