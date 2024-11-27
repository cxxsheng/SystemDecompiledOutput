package android.service.notification;

/* loaded from: classes3.dex */
public final class ZenPolicy implements android.os.Parcelable {
    private static final int CHANNEL_POLICY_NONE = 2;
    private static final int CHANNEL_POLICY_PRIORITY = 1;
    private static final int CHANNEL_POLICY_UNSET = 0;
    public static final int CONVERSATION_SENDERS_ANYONE = 1;
    public static final int CONVERSATION_SENDERS_IMPORTANT = 2;
    public static final int CONVERSATION_SENDERS_NONE = 3;
    public static final int CONVERSATION_SENDERS_UNSET = 0;
    public static final android.os.Parcelable.Creator<android.service.notification.ZenPolicy> CREATOR = new android.os.Parcelable.Creator<android.service.notification.ZenPolicy>() { // from class: android.service.notification.ZenPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ZenPolicy createFromParcel(android.os.Parcel parcel) {
            if (android.app.Flags.modesApi()) {
                return new android.service.notification.ZenPolicy(android.service.notification.ZenPolicy.trimList(parcel.readArrayList(java.lang.Integer.class.getClassLoader(), java.lang.Integer.class), 9), android.service.notification.ZenPolicy.trimList(parcel.readArrayList(java.lang.Integer.class.getClassLoader(), java.lang.Integer.class), 7), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            }
            android.service.notification.ZenPolicy zenPolicy = new android.service.notification.ZenPolicy();
            zenPolicy.mPriorityCategories = android.service.notification.ZenPolicy.trimList(parcel.readArrayList(java.lang.Integer.class.getClassLoader(), java.lang.Integer.class), 9);
            zenPolicy.mVisualEffects = android.service.notification.ZenPolicy.trimList(parcel.readArrayList(java.lang.Integer.class.getClassLoader(), java.lang.Integer.class), 7);
            zenPolicy.mPriorityMessages = parcel.readInt();
            zenPolicy.mPriorityCalls = parcel.readInt();
            zenPolicy.mConversationSenders = parcel.readInt();
            return zenPolicy;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ZenPolicy[] newArray(int i) {
            return new android.service.notification.ZenPolicy[i];
        }
    };
    public static final int FIELD_ALLOW_CHANNELS = 8;
    public static final int FIELD_CALLS = 2;
    public static final int FIELD_CONVERSATIONS = 4;
    public static final int FIELD_MESSAGES = 1;
    public static final int FIELD_PRIORITY_CATEGORY_ALARMS = 128;
    public static final int FIELD_PRIORITY_CATEGORY_EVENTS = 32;
    public static final int FIELD_PRIORITY_CATEGORY_MEDIA = 256;
    public static final int FIELD_PRIORITY_CATEGORY_REMINDERS = 16;
    public static final int FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS = 64;
    public static final int FIELD_PRIORITY_CATEGORY_SYSTEM = 512;
    public static final int FIELD_VISUAL_EFFECT_AMBIENT = 32768;
    public static final int FIELD_VISUAL_EFFECT_BADGE = 16384;
    public static final int FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT = 1024;
    public static final int FIELD_VISUAL_EFFECT_LIGHTS = 2048;
    public static final int FIELD_VISUAL_EFFECT_NOTIFICATION_LIST = 65536;
    public static final int FIELD_VISUAL_EFFECT_PEEK = 4096;
    public static final int FIELD_VISUAL_EFFECT_STATUS_BAR = 8192;
    public static final int NUM_PRIORITY_CATEGORIES = 9;
    public static final int NUM_VISUAL_EFFECTS = 7;
    public static final int PEOPLE_TYPE_ANYONE = 1;
    public static final int PEOPLE_TYPE_CONTACTS = 2;
    public static final int PEOPLE_TYPE_NONE = 4;
    public static final int PEOPLE_TYPE_STARRED = 3;
    public static final int PEOPLE_TYPE_UNSET = 0;
    public static final int PRIORITY_CATEGORY_ALARMS = 5;
    public static final int PRIORITY_CATEGORY_CALLS = 3;
    public static final int PRIORITY_CATEGORY_CONVERSATIONS = 8;
    public static final int PRIORITY_CATEGORY_EVENTS = 1;
    public static final int PRIORITY_CATEGORY_MEDIA = 6;
    public static final int PRIORITY_CATEGORY_MESSAGES = 2;
    public static final int PRIORITY_CATEGORY_REMINDERS = 0;
    public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 4;
    public static final int PRIORITY_CATEGORY_SYSTEM = 7;
    public static final int STATE_ALLOW = 1;
    public static final int STATE_DISALLOW = 2;
    public static final int STATE_UNSET = 0;
    public static final int VISUAL_EFFECT_AMBIENT = 5;
    public static final int VISUAL_EFFECT_BADGE = 4;
    public static final int VISUAL_EFFECT_FULL_SCREEN_INTENT = 0;
    public static final int VISUAL_EFFECT_LIGHTS = 1;
    public static final int VISUAL_EFFECT_NOTIFICATION_LIST = 6;
    public static final int VISUAL_EFFECT_PEEK = 2;
    public static final int VISUAL_EFFECT_STATUS_BAR = 3;
    private int mAllowChannels;
    private int mConversationSenders;
    private int mPriorityCalls;
    private java.util.List<java.lang.Integer> mPriorityCategories;
    private int mPriorityMessages;
    private java.util.List<java.lang.Integer> mVisualEffects;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ChannelType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConversationSenders {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PeopleType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PriorityCategory {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VisualEffect {
    }

    public ZenPolicy() {
        this.mPriorityMessages = 0;
        this.mPriorityCalls = 0;
        this.mConversationSenders = 0;
        this.mAllowChannels = 0;
        this.mPriorityCategories = new java.util.ArrayList(java.util.Collections.nCopies(9, 0));
        this.mVisualEffects = new java.util.ArrayList(java.util.Collections.nCopies(7, 0));
    }

    public ZenPolicy(java.util.List<java.lang.Integer> list, java.util.List<java.lang.Integer> list2, int i, int i2, int i3, int i4) {
        this.mPriorityMessages = 0;
        this.mPriorityCalls = 0;
        this.mConversationSenders = 0;
        this.mAllowChannels = 0;
        this.mPriorityCategories = list;
        this.mVisualEffects = list2;
        this.mPriorityMessages = i;
        this.mPriorityCalls = i2;
        this.mConversationSenders = i3;
        this.mAllowChannels = i4;
    }

    public int getPriorityConversationSenders() {
        return this.mConversationSenders;
    }

    public int getPriorityMessageSenders() {
        return this.mPriorityMessages;
    }

    public int getPriorityCallSenders() {
        return this.mPriorityCalls;
    }

    public int getPriorityCategoryConversations() {
        return this.mPriorityCategories.get(8).intValue();
    }

    public int getPriorityCategoryReminders() {
        return this.mPriorityCategories.get(0).intValue();
    }

    public int getPriorityCategoryEvents() {
        return this.mPriorityCategories.get(1).intValue();
    }

    public int getPriorityCategoryMessages() {
        return this.mPriorityCategories.get(2).intValue();
    }

    public int getPriorityCategoryCalls() {
        return this.mPriorityCategories.get(3).intValue();
    }

    public int getPriorityCategoryRepeatCallers() {
        return this.mPriorityCategories.get(4).intValue();
    }

    public int getPriorityCategoryAlarms() {
        return this.mPriorityCategories.get(5).intValue();
    }

    public int getPriorityCategoryMedia() {
        return this.mPriorityCategories.get(6).intValue();
    }

    public int getPriorityCategorySystem() {
        return this.mPriorityCategories.get(7).intValue();
    }

    public int getVisualEffectFullScreenIntent() {
        return this.mVisualEffects.get(0).intValue();
    }

    public int getVisualEffectLights() {
        return this.mVisualEffects.get(1).intValue();
    }

    public int getVisualEffectPeek() {
        return this.mVisualEffects.get(2).intValue();
    }

    public int getVisualEffectStatusBar() {
        return this.mVisualEffects.get(3).intValue();
    }

    public int getVisualEffectBadge() {
        return this.mVisualEffects.get(4).intValue();
    }

    public int getVisualEffectAmbient() {
        return this.mVisualEffects.get(5).intValue();
    }

    public int getVisualEffectNotificationList() {
        return this.mVisualEffects.get(6).intValue();
    }

    public int getPriorityChannelsAllowed() {
        switch (this.mAllowChannels) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public boolean shouldHideAllVisualEffects() {
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldShowAllVisualEffects() {
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 1) {
                return false;
            }
        }
        return true;
    }

    public static final class Builder {
        private android.service.notification.ZenPolicy mZenPolicy;

        public Builder() {
            this.mZenPolicy = new android.service.notification.ZenPolicy();
        }

        public Builder(android.service.notification.ZenPolicy zenPolicy) {
            if (zenPolicy != null) {
                this.mZenPolicy = zenPolicy.copy();
            } else {
                this.mZenPolicy = new android.service.notification.ZenPolicy();
            }
        }

        public android.service.notification.ZenPolicy build() {
            if (android.app.Flags.modesApi()) {
                return new android.service.notification.ZenPolicy(new java.util.ArrayList(this.mZenPolicy.mPriorityCategories), new java.util.ArrayList(this.mZenPolicy.mVisualEffects), this.mZenPolicy.mPriorityMessages, this.mZenPolicy.mPriorityCalls, this.mZenPolicy.mConversationSenders, this.mZenPolicy.mAllowChannels);
            }
            return this.mZenPolicy.copy();
        }

        public android.service.notification.ZenPolicy.Builder allowAllSounds() {
            for (int i = 0; i < this.mZenPolicy.mPriorityCategories.size(); i++) {
                this.mZenPolicy.mPriorityCategories.set(i, 1);
            }
            this.mZenPolicy.mPriorityMessages = 1;
            this.mZenPolicy.mPriorityCalls = 1;
            this.mZenPolicy.mConversationSenders = 1;
            return this;
        }

        public android.service.notification.ZenPolicy.Builder disallowAllSounds() {
            for (int i = 0; i < this.mZenPolicy.mPriorityCategories.size(); i++) {
                this.mZenPolicy.mPriorityCategories.set(i, 2);
            }
            this.mZenPolicy.mPriorityMessages = 4;
            this.mZenPolicy.mPriorityCalls = 4;
            this.mZenPolicy.mConversationSenders = 3;
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showAllVisualEffects() {
            for (int i = 0; i < this.mZenPolicy.mVisualEffects.size(); i++) {
                this.mZenPolicy.mVisualEffects.set(i, 1);
            }
            return this;
        }

        public android.service.notification.ZenPolicy.Builder hideAllVisualEffects() {
            for (int i = 0; i < this.mZenPolicy.mVisualEffects.size(); i++) {
                this.mZenPolicy.mVisualEffects.set(i, 2);
            }
            return this;
        }

        public android.service.notification.ZenPolicy.Builder unsetPriorityCategory(int i) {
            this.mZenPolicy.mPriorityCategories.set(i, 0);
            if (i == 2) {
                this.mZenPolicy.mPriorityMessages = 0;
            } else if (i == 3) {
                this.mZenPolicy.mPriorityCalls = 0;
            } else if (i == 8) {
                this.mZenPolicy.mConversationSenders = 0;
            }
            return this;
        }

        public android.service.notification.ZenPolicy.Builder unsetVisualEffect(int i) {
            this.mZenPolicy.mVisualEffects.set(i, 0);
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowReminders(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(0, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowEvents(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(1, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowConversations(int i) {
            if (i == 0) {
                return unsetPriorityCategory(8);
            }
            if (i == 3) {
                this.mZenPolicy.mPriorityCategories.set(8, 2);
            } else if (i == 1 || i == 2) {
                this.mZenPolicy.mPriorityCategories.set(8, 1);
            } else {
                return this;
            }
            this.mZenPolicy.mConversationSenders = i;
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowMessages(int i) {
            if (i == 0) {
                return unsetPriorityCategory(2);
            }
            if (i == 4) {
                this.mZenPolicy.mPriorityCategories.set(2, 2);
            } else if (i == 1 || i == 2 || i == 3) {
                this.mZenPolicy.mPriorityCategories.set(2, 1);
            } else {
                return this;
            }
            this.mZenPolicy.mPriorityMessages = i;
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowCalls(int i) {
            if (i == 0) {
                return unsetPriorityCategory(3);
            }
            if (i == 4) {
                this.mZenPolicy.mPriorityCategories.set(3, 2);
            } else if (i == 1 || i == 2 || i == 3) {
                this.mZenPolicy.mPriorityCategories.set(3, 1);
            } else {
                return this;
            }
            this.mZenPolicy.mPriorityCalls = i;
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowRepeatCallers(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(4, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowAlarms(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(5, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowMedia(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(6, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder allowSystem(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(7, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x001c, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.service.notification.ZenPolicy.Builder allowCategory(int i, boolean z) {
            switch (i) {
                case 0:
                    allowReminders(z);
                    break;
                case 1:
                    allowEvents(z);
                    break;
                case 4:
                    allowRepeatCallers(z);
                    break;
                case 5:
                    allowAlarms(z);
                    break;
                case 6:
                    allowMedia(z);
                    break;
                case 7:
                    allowSystem(z);
                    break;
            }
        }

        public android.service.notification.ZenPolicy.Builder showFullScreenIntent(boolean z) {
            this.mZenPolicy.mVisualEffects.set(0, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showLights(boolean z) {
            this.mZenPolicy.mVisualEffects.set(1, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showPeeking(boolean z) {
            this.mZenPolicy.mVisualEffects.set(2, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showStatusBarIcons(boolean z) {
            this.mZenPolicy.mVisualEffects.set(3, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showBadges(boolean z) {
            this.mZenPolicy.mVisualEffects.set(4, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showInAmbientDisplay(boolean z) {
            this.mZenPolicy.mVisualEffects.set(5, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public android.service.notification.ZenPolicy.Builder showInNotificationList(boolean z) {
            this.mZenPolicy.mVisualEffects.set(6, java.lang.Integer.valueOf(z ? 1 : 2));
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0020, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.service.notification.ZenPolicy.Builder showVisualEffect(int i, boolean z) {
            switch (i) {
                case 0:
                    showFullScreenIntent(z);
                    break;
                case 1:
                    showLights(z);
                    break;
                case 2:
                    showPeeking(z);
                    break;
                case 3:
                    showStatusBarIcons(z);
                    break;
                case 4:
                    showBadges(z);
                    break;
                case 5:
                    showInAmbientDisplay(z);
                    break;
                case 6:
                    showInNotificationList(z);
                    break;
            }
        }

        public android.service.notification.ZenPolicy.Builder allowPriorityChannels(boolean z) {
            this.mZenPolicy.mAllowChannels = z ? 1 : 2;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeList(this.mPriorityCategories);
        parcel.writeList(this.mVisualEffects);
        parcel.writeInt(this.mPriorityMessages);
        parcel.writeInt(this.mPriorityCalls);
        parcel.writeInt(this.mConversationSenders);
        if (android.app.Flags.modesApi()) {
            parcel.writeInt(this.mAllowChannels);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder(android.service.notification.ZenPolicy.class.getSimpleName()).append('{').append("priorityCategories=[").append(priorityCategoriesToString()).append("], visualEffects=[").append(visualEffectsToString()).append("], priorityCallsSenders=").append(peopleTypeToString(this.mPriorityCalls)).append(", priorityMessagesSenders=").append(peopleTypeToString(this.mPriorityMessages)).append(", priorityConversationSenders=").append(conversationTypeToString(this.mConversationSenders));
        if (android.app.Flags.modesApi()) {
            append.append(", allowChannels=").append(channelTypeToString(this.mAllowChannels));
        }
        return append.append('}').toString();
    }

    public static java.lang.String fieldsToString(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("FIELD_MESSAGES");
        }
        if ((i & 2) != 0) {
            arrayList.add("FIELD_CALLS");
        }
        if ((i & 4) != 0) {
            arrayList.add("FIELD_CONVERSATIONS");
        }
        if ((i & 8) != 0) {
            arrayList.add("FIELD_ALLOW_CHANNELS");
        }
        if ((i & 16) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_REMINDERS");
        }
        if ((i & 32) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_EVENTS");
        }
        if ((i & 64) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS");
        }
        if ((i & 128) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_ALARMS");
        }
        if ((i & 256) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_MEDIA");
        }
        if ((i & 512) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_SYSTEM");
        }
        if ((i & 1024) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT");
        }
        if ((i & 2048) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_LIGHTS");
        }
        if ((i & 4096) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_PEEK");
        }
        if ((i & 8192) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_STATUS_BAR");
        }
        if ((i & 16384) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_BADGE");
        }
        if ((32768 & i) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_AMBIENT");
        }
        if ((i & 65536) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_NOTIFICATION_LIST");
        }
        return "{" + java.lang.String.join(",", arrayList) + "}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.ArrayList<java.lang.Integer> trimList(java.util.ArrayList<java.lang.Integer> arrayList, int i) {
        if (arrayList == null || arrayList.size() <= i) {
            return arrayList;
        }
        return new java.util.ArrayList<>(arrayList.subList(0, i));
    }

    private java.lang.String priorityCategoriesToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.mPriorityCategories.size(); i++) {
            if (this.mPriorityCategories.get(i).intValue() != 0) {
                sb.append(indexToCategory(i)).append("=").append(stateToString(this.mPriorityCategories.get(i).intValue())).append(" ");
            }
        }
        return sb.toString();
    }

    private java.lang.String visualEffectsToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 0) {
                sb.append(indexToVisualEffect(i)).append("=").append(stateToString(this.mVisualEffects.get(i).intValue())).append(" ");
            }
        }
        return sb.toString();
    }

    private java.lang.String indexToVisualEffect(int i) {
        switch (i) {
            case 0:
                return "fullScreenIntent";
            case 1:
                return android.content.Context.LIGHTS_SERVICE;
            case 2:
                return "peek";
            case 3:
                return "statusBar";
            case 4:
                return "badge";
            case 5:
                return android.media.AudioSystem.DEVICE_IN_AMBIENT_NAME;
            case 6:
                return "notificationList";
            default:
                return null;
        }
    }

    private java.lang.String indexToCategory(int i) {
        switch (i) {
            case 0:
                return "reminders";
            case 1:
                return "events";
            case 2:
                return "messages";
            case 3:
                return "calls";
            case 4:
                return "repeatCallers";
            case 5:
                return "alarms";
            case 6:
                return "media";
            case 7:
                return "system";
            case 8:
                return "convs";
            default:
                return null;
        }
    }

    private java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "unset";
            case 1:
                return "allow";
            case 2:
                return "disallow";
            default:
                return "invalidState{" + i + "}";
        }
    }

    private java.lang.String peopleTypeToString(int i) {
        switch (i) {
            case 0:
                return "unset";
            case 1:
                return "anyone";
            case 2:
                return android.provider.Contacts.AUTHORITY;
            case 3:
                return "starred_contacts";
            case 4:
                return "none";
            default:
                return "invalidPeopleType{" + i + "}";
        }
    }

    public static java.lang.String conversationTypeToString(int i) {
        switch (i) {
            case 0:
                return "unset";
            case 1:
                return "anyone";
            case 2:
                return "important";
            case 3:
                return "none";
            default:
                return "invalidConversationType{" + i + "}";
        }
    }

    public static java.lang.String channelTypeToString(int i) {
        switch (i) {
            case 0:
                return "unset";
            case 1:
                return "priority";
            case 2:
                return "none";
            default:
                return "invalidChannelType{" + i + "}";
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.service.notification.ZenPolicy)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.service.notification.ZenPolicy zenPolicy = (android.service.notification.ZenPolicy) obj;
        boolean z = java.util.Objects.equals(zenPolicy.mPriorityCategories, this.mPriorityCategories) && java.util.Objects.equals(zenPolicy.mVisualEffects, this.mVisualEffects) && zenPolicy.mPriorityCalls == this.mPriorityCalls && zenPolicy.mPriorityMessages == this.mPriorityMessages && zenPolicy.mConversationSenders == this.mConversationSenders;
        if (android.app.Flags.modesApi()) {
            return z && zenPolicy.mAllowChannels == this.mAllowChannels;
        }
        return z;
    }

    public int hashCode() {
        if (android.app.Flags.modesApi()) {
            return java.util.Objects.hash(this.mPriorityCategories, this.mVisualEffects, java.lang.Integer.valueOf(this.mPriorityCalls), java.lang.Integer.valueOf(this.mPriorityMessages), java.lang.Integer.valueOf(this.mConversationSenders), java.lang.Integer.valueOf(this.mAllowChannels));
        }
        return java.util.Objects.hash(this.mPriorityCategories, this.mVisualEffects, java.lang.Integer.valueOf(this.mPriorityCalls), java.lang.Integer.valueOf(this.mPriorityMessages), java.lang.Integer.valueOf(this.mConversationSenders));
    }

    private int getZenPolicyPriorityCategoryState(int i) {
        switch (i) {
            case 0:
                return getPriorityCategoryReminders();
            case 1:
                return getPriorityCategoryEvents();
            case 2:
                return getPriorityCategoryMessages();
            case 3:
                return getPriorityCategoryCalls();
            case 4:
                return getPriorityCategoryRepeatCallers();
            case 5:
                return getPriorityCategoryAlarms();
            case 6:
                return getPriorityCategoryMedia();
            case 7:
                return getPriorityCategorySystem();
            case 8:
                return getPriorityCategoryConversations();
            default:
                return -1;
        }
    }

    private int getZenPolicyVisualEffectState(int i) {
        switch (i) {
            case 0:
                return getVisualEffectFullScreenIntent();
            case 1:
                return getVisualEffectLights();
            case 2:
                return getVisualEffectPeek();
            case 3:
                return getVisualEffectStatusBar();
            case 4:
                return getVisualEffectBadge();
            case 5:
                return getVisualEffectAmbient();
            case 6:
                return getVisualEffectNotificationList();
            default:
                return -1;
        }
    }

    public static boolean stateToBoolean(int i, boolean z) {
        switch (i) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                return z;
        }
    }

    public boolean isCategoryAllowed(int i, boolean z) {
        return stateToBoolean(getZenPolicyPriorityCategoryState(i), z);
    }

    public boolean isVisualEffectAllowed(int i, boolean z) {
        return stateToBoolean(getZenPolicyVisualEffectState(i), z);
    }

    public void apply(android.service.notification.ZenPolicy zenPolicy) {
        int intValue;
        if (zenPolicy == null) {
            return;
        }
        for (int i = 0; i < this.mPriorityCategories.size(); i++) {
            if (this.mPriorityCategories.get(i).intValue() != 2 && (intValue = zenPolicy.mPriorityCategories.get(i).intValue()) != 0) {
                this.mPriorityCategories.set(i, java.lang.Integer.valueOf(intValue));
                if (i == 2 && this.mPriorityMessages < zenPolicy.mPriorityMessages) {
                    this.mPriorityMessages = zenPolicy.mPriorityMessages;
                } else if (i == 3 && this.mPriorityCalls < zenPolicy.mPriorityCalls) {
                    this.mPriorityCalls = zenPolicy.mPriorityCalls;
                } else if (i == 8 && this.mConversationSenders < zenPolicy.mConversationSenders) {
                    this.mConversationSenders = zenPolicy.mConversationSenders;
                }
            }
        }
        for (int i2 = 0; i2 < this.mVisualEffects.size(); i2++) {
            if (this.mVisualEffects.get(i2).intValue() != 2 && zenPolicy.mVisualEffects.get(i2).intValue() != 0) {
                this.mVisualEffects.set(i2, zenPolicy.mVisualEffects.get(i2));
            }
        }
        if (android.app.Flags.modesApi() && this.mAllowChannels != 2 && zenPolicy.mAllowChannels != 0) {
            this.mAllowChannels = zenPolicy.mAllowChannels;
        }
    }

    public android.service.notification.ZenPolicy overwrittenWith(android.service.notification.ZenPolicy zenPolicy) {
        android.service.notification.ZenPolicy copy = copy();
        if (zenPolicy == null) {
            return copy;
        }
        for (int i = 0; i < this.mPriorityCategories.size(); i++) {
            int intValue = zenPolicy.mPriorityCategories.get(i).intValue();
            if (intValue != 0) {
                copy.mPriorityCategories.set(i, java.lang.Integer.valueOf(intValue));
                if (i == 2) {
                    copy.mPriorityMessages = zenPolicy.mPriorityMessages;
                } else if (i == 3) {
                    copy.mPriorityCalls = zenPolicy.mPriorityCalls;
                } else if (i == 8) {
                    copy.mConversationSenders = zenPolicy.mConversationSenders;
                }
            }
        }
        for (int i2 = 0; i2 < this.mVisualEffects.size(); i2++) {
            if (zenPolicy.mVisualEffects.get(i2).intValue() != 0) {
                copy.mVisualEffects.set(i2, zenPolicy.mVisualEffects.get(i2));
            }
        }
        if (zenPolicy.mAllowChannels != 0) {
            copy.mAllowChannels = zenPolicy.mAllowChannels;
        }
        return copy;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, getPriorityCategoryReminders());
        protoOutputStream.write(1159641169922L, getPriorityCategoryEvents());
        protoOutputStream.write(1159641169923L, getPriorityCategoryMessages());
        protoOutputStream.write(1159641169924L, getPriorityCategoryCalls());
        protoOutputStream.write(1159641169925L, getPriorityCategoryRepeatCallers());
        protoOutputStream.write(1159641169926L, getPriorityCategoryAlarms());
        protoOutputStream.write(1159641169927L, getPriorityCategoryMedia());
        protoOutputStream.write(1159641169928L, getPriorityCategorySystem());
        protoOutputStream.write(1159641169929L, getVisualEffectFullScreenIntent());
        protoOutputStream.write(1159641169930L, getVisualEffectLights());
        protoOutputStream.write(1159641169931L, getVisualEffectPeek());
        protoOutputStream.write(1159641169932L, getVisualEffectStatusBar());
        protoOutputStream.write(1159641169933L, getVisualEffectBadge());
        protoOutputStream.write(1159641169934L, getVisualEffectAmbient());
        protoOutputStream.write(1159641169935L, getVisualEffectNotificationList());
        protoOutputStream.write(1159641169937L, getPriorityMessageSenders());
        protoOutputStream.write(1159641169936L, getPriorityCallSenders());
        protoOutputStream.end(start);
    }

    public byte[] toProto() {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(byteArrayOutputStream);
        protoOutputStream.write(1159641169921L, getPriorityCategoryCalls());
        protoOutputStream.write(1159641169922L, getPriorityCategoryRepeatCallers());
        protoOutputStream.write(1159641169923L, getPriorityCategoryMessages());
        protoOutputStream.write(1159641169924L, getPriorityCategoryConversations());
        protoOutputStream.write(1159641169925L, getPriorityCategoryReminders());
        protoOutputStream.write(1159641169926L, getPriorityCategoryEvents());
        protoOutputStream.write(1159641169927L, getPriorityCategoryAlarms());
        protoOutputStream.write(1159641169928L, getPriorityCategoryMedia());
        protoOutputStream.write(1159641169929L, getPriorityCategorySystem());
        protoOutputStream.write(1159641169930L, getVisualEffectFullScreenIntent());
        protoOutputStream.write(1159641169931L, getVisualEffectLights());
        protoOutputStream.write(1159641169932L, getVisualEffectPeek());
        protoOutputStream.write(1159641169933L, getVisualEffectStatusBar());
        protoOutputStream.write(1159641169934L, getVisualEffectBadge());
        protoOutputStream.write(1159641169935L, getVisualEffectAmbient());
        protoOutputStream.write(1159641169936L, getVisualEffectNotificationList());
        protoOutputStream.write(1159641169937L, getPriorityCallSenders());
        protoOutputStream.write(1159641169938L, getPriorityMessageSenders());
        protoOutputStream.write(1159641169939L, getPriorityConversationSenders());
        if (android.app.Flags.modesApi()) {
            protoOutputStream.write(1159641169940L, getPriorityChannelsAllowed());
        }
        protoOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public android.service.notification.ZenPolicy copy() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return CREATOR.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }
}
