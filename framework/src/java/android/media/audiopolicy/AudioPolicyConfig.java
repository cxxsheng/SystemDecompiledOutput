package android.media.audiopolicy;

/* loaded from: classes2.dex */
public class AudioPolicyConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioPolicyConfig> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioPolicyConfig>() { // from class: android.media.audiopolicy.AudioPolicyConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioPolicyConfig createFromParcel(android.os.Parcel parcel) {
            return new android.media.audiopolicy.AudioPolicyConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioPolicyConfig[] newArray(int i) {
            return new android.media.audiopolicy.AudioPolicyConfig[i];
        }
    };
    private static final java.lang.String TAG = "AudioPolicyConfig";
    protected int mDuckingPolicy;
    private int mMixCounter;
    protected final java.util.ArrayList<android.media.audiopolicy.AudioMix> mMixes;
    private java.lang.String mRegistrationId;

    protected AudioPolicyConfig(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        this.mMixes = audioPolicyConfig.mMixes;
    }

    public AudioPolicyConfig(java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        this.mMixes = arrayList;
    }

    public void addMix(android.media.audiopolicy.AudioMix audioMix) throws java.lang.IllegalArgumentException {
        if (audioMix == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioMix argument");
        }
        this.mMixes.add(audioMix);
    }

    public java.util.ArrayList<android.media.audiopolicy.AudioMix> getMixes() {
        return this.mMixes;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mMixes);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMixes.size());
        java.util.Iterator<android.media.audiopolicy.AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, i);
        }
    }

    private AudioPolicyConfig(android.os.Parcel parcel) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        int readInt = parcel.readInt();
        this.mMixes = new java.util.ArrayList<>(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mMixes.add(android.media.audiopolicy.AudioMix.CREATOR.createFromParcel(parcel));
        }
    }

    public java.lang.String toLogFriendlyString() {
        java.lang.String str;
        java.lang.String str2 = new java.lang.String("android.media.audiopolicy.AudioPolicyConfig:\n") + this.mMixes.size() + " AudioMix, reg:" + this.mRegistrationId + "\n";
        java.util.Iterator<android.media.audiopolicy.AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMix next = it.next();
            str2 = (((((((str2 + "* route flags=0x" + java.lang.Integer.toHexString(next.getRouteFlags()) + "\n") + "  rate=" + next.getFormat().getSampleRate() + "Hz\n") + "  encoding=" + next.getFormat().getEncoding() + "\n") + "  channels=0x") + java.lang.Integer.toHexString(next.getFormat().getChannelMask()).toUpperCase() + "\n") + "  ignore playback capture opt out=" + next.getRule().allowPrivilegedMediaPlaybackCapture() + "\n") + "  allow voice communication capture=" + next.getRule().voiceCommunicationCaptureAllowed() + "\n") + "  specified mix type=" + next.getRule().getTargetMixRole() + "\n";
            java.util.Iterator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> it2 = next.getRule().getCriteria().iterator();
            while (it2.hasNext()) {
                android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion next2 = it2.next();
                switch (next2.mRule) {
                    case 1:
                        str = (str2 + "  match usage ") + next2.mAttr.usageToString();
                        break;
                    case 2:
                        str = (str2 + "  match capture preset ") + next2.mAttr.getCapturePreset();
                        break;
                    case 4:
                        str = (str2 + "  match UID ") + next2.mIntProp;
                        break;
                    case 8:
                        str = (str2 + "  match userId ") + next2.mIntProp;
                        break;
                    case 16:
                        str = (str2 + " match audio session id") + next2.mIntProp;
                        break;
                    case 32769:
                        str = (str2 + "  exclude usage ") + next2.mAttr.usageToString();
                        break;
                    case 32770:
                        str = (str2 + "  exclude capture preset ") + next2.mAttr.getCapturePreset();
                        break;
                    case 32772:
                        str = (str2 + "  exclude UID ") + next2.mIntProp;
                        break;
                    case 32776:
                        str = (str2 + "  exclude userId ") + next2.mIntProp;
                        break;
                    case android.media.audiopolicy.AudioMixingRule.RULE_EXCLUDE_AUDIO_SESSION_ID /* 32784 */:
                        str = (str2 + " exclude audio session id ") + next2.mIntProp;
                        break;
                    default:
                        str = str2 + "invalid rule!";
                        break;
                }
                str2 = str + "\n";
            }
        }
        return str2;
    }

    public java.lang.String toCompactLogString() {
        java.lang.String str = "reg:" + this.mRegistrationId;
        java.util.Iterator<android.media.audiopolicy.AudioMix> it = this.mMixes.iterator();
        int i = 0;
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMix next = it.next();
            str = str + " Mix:" + i + "-Typ:" + mixTypePrefix(next.getMixType()) + "-Rul:" + next.getRule().getCriteria().size();
            i++;
        }
        return str;
    }

    private static java.lang.String mixTypePrefix(int i) {
        switch (i) {
            case 0:
                return "p";
            case 1:
                return "r";
            default:
                return "#";
        }
    }

    protected void reset() {
        this.mMixCounter = 0;
    }

    protected void setRegistration(java.lang.String str) {
        boolean z = this.mRegistrationId == null || this.mRegistrationId.isEmpty();
        boolean z2 = str == null || str.isEmpty();
        if (!z && !z2 && !this.mRegistrationId.equals(str)) {
            android.util.Log.e(TAG, "Invalid registration transition from " + this.mRegistrationId + " to " + str);
            return;
        }
        if (str == null) {
            str = "";
        }
        this.mRegistrationId = str;
        java.util.Iterator<android.media.audiopolicy.AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            setMixRegistration(it.next());
        }
    }

    protected void setMixRegistration(android.media.audiopolicy.AudioMix audioMix) {
        if (!this.mRegistrationId.isEmpty()) {
            if ((audioMix.getRouteFlags() & 2) == 2) {
                java.lang.StringBuilder append = new java.lang.StringBuilder().append(this.mRegistrationId).append("mix").append(mixTypeId(audioMix.getMixType())).append(":");
                int i = this.mMixCounter;
                this.mMixCounter = i + 1;
                audioMix.setRegistration(append.append(i).toString());
                return;
            }
            if ((audioMix.getRouteFlags() & 1) == 1) {
                audioMix.setRegistration(audioMix.mDeviceAddress);
                return;
            }
            return;
        }
        audioMix.setRegistration("");
    }

    protected void add(java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList) {
        java.util.Iterator<android.media.audiopolicy.AudioMix> it = arrayList.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMix next = it.next();
            if (next.getRegistration() == null || next.getRegistration().isEmpty()) {
                setMixRegistration(next);
            }
            this.mMixes.add(next);
        }
    }

    protected void remove(java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList) {
        java.util.Iterator<android.media.audiopolicy.AudioMix> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mMixes.remove(it.next());
        }
    }

    public void updateMixingRules(java.util.List<android.util.Pair<android.media.audiopolicy.AudioMix, android.media.audiopolicy.AudioMixingRule>> list) {
        ((java.util.List) java.util.Objects.requireNonNull(list)).forEach(new java.util.function.Consumer() { // from class: android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.media.audiopolicy.AudioPolicyConfig.this.lambda$updateMixingRules$0((android.util.Pair) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$updateMixingRules$0(android.util.Pair pair) {
        updateMixingRule((android.media.audiopolicy.AudioMix) pair.first, (android.media.audiopolicy.AudioMixingRule) pair.second);
    }

    private void updateMixingRule(final android.media.audiopolicy.AudioMix audioMix, final android.media.audiopolicy.AudioMixingRule audioMixingRule) {
        java.util.stream.Stream stream = this.mMixes.stream();
        java.util.Objects.requireNonNull(audioMix);
        stream.filter(new java.util.function.Predicate() { // from class: android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.media.audiopolicy.AudioMix.this.equals((android.media.audiopolicy.AudioMix) obj);
            }
        }).findAny().ifPresent(new java.util.function.Consumer() { // from class: android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.media.audiopolicy.AudioMix) obj).setAudioMixingRule(android.media.audiopolicy.AudioMixingRule.this);
            }
        });
    }

    private static java.lang.String mixTypeId(int i) {
        return i == 0 ? "p" : i == 1 ? "r" : "i";
    }

    protected java.lang.String getRegistration() {
        return this.mRegistrationId;
    }
}
