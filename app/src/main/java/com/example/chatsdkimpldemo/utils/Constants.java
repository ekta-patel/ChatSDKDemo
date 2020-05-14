package com.example.chatsdkimpldemo.utils;

public final class Constants {

    public static class BundleKeys {
        public static final String IS_GROUP = "is_group";
        public static final String CHATROOM_ID = "chatroom_id";
        public static final String CHATROOM_NAME = "chatroom_name";
        public static final String OTHER_USER_ID = "other_user_id";
        public static final String OTHER_USER_NAME = "other_user_name";
        public static final String SELECTED_USERS = "selected_users";
        public static final String PHOTO_FILE = "photo_file";
    }

    public static class RequestCodes {
        public static final int OPEN_DOCUMENT = 1111;
        public static final int OPEN_CAMERA = 1112;
    }

    public static class PermissionCodes {
        public static final int RECORD_AUDIO = 1000;
        public static final int WRITE_EXTERNAL_STORAGE = 1001;
    }

    public enum MediaIdentifier {

        IMAGE("image"),
        VIDEO("video"),
        AUDIO("audio");
        private String value;

        MediaIdentifier(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ViewHolderIdentifier {
        SENDER_TYPE1(1),
        SENDER_TYPE2(2),
        RECEIVER_TYPE1(3),
        RECEIVER_TYPE2(4),
        SENDER_MEDIA_TYPE1(5),
        SENDER_MEDIA_TYPE2(6),
        RECEIVER_MEDIA_TYPE1(7),
        RECEIVER_MEDIA_TYPE2(8),
        SENDER_MEDIA_DOCTYPE1(9),
        SENDER_MEDIA_DOCTYPE2(10),
        RECEIVER_MEDIA_DOCTYPE1(11),
        RECEIVER_MEDIA_DOCTYPE2(12);

        private int value;

        ViewHolderIdentifier(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
