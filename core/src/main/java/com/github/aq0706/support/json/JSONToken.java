package com.github.aq0706.support.json;

public class JSONToken {

    public static final int TYPE_STRING = 0;
    public static final int TYPE_NUMBER = 1;
    public static final int TYPE_BOOL = 2;
    public static final int TYPE_LEFT_BRACE = 3;
    public static final int TYPE_RIGHT_BRACE = 4;
    public static final int TYPE_LEFT_SQUARE = 5;
    public static final int TYPE_RIGHT_SQUARE = 6;
    public static final int TYPE_COMMA = 7;
    public static final int TYPE_COLON = 8;
    public static final int TYPE_EOF = -1;

    int pos;
    int type;
    Object value;

    public JSONToken(int pos, int type, Object value) {
        this.pos = pos;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        if (type == TYPE_STRING || type == TYPE_NUMBER || type == TYPE_BOOL) {
            return value.toString();
        } else if (type == TYPE_LEFT_BRACE) {
            return "{";
        } else if (type == TYPE_RIGHT_BRACE) {
            return "}";
        } else if (type == TYPE_LEFT_SQUARE) {
            return "[";
        } else if (type == TYPE_RIGHT_SQUARE) {
            return "]";
        } else if (type == TYPE_COMMA) {
            return ",";
        } else if (type == TYPE_COLON) {
            return ":";
        } else if (type == TYPE_EOF) {
            return "EOF";
        } else {
            return "Unknown type:" + type;
        }
    }
}
