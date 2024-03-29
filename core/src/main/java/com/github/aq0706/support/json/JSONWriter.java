package com.github.aq0706.support.json;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class JSONWriter {

    public static String toJSONString(Object object) {
        StringWriter writer = new StringWriter();

        try {
            if (object instanceof Collection) {
                writeArray((Collection) object, writer);
            } else if (object instanceof String) {
                writer.write(object.toString());
            } else if (object instanceof Number) {
                writer.write(object.toString());
            } else if (object instanceof Map) {
                writeMap((Map)object, writer);
            } else {
                writeObject(object, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ToJSONString failed.");
        }

        return writer.toString();
    }

    private static void writeObject(Object object, StringWriter out) throws IllegalAccessException {
        if (object == null) {
            out.write("{}");
        } else {
            out.write("{");

            boolean isFirst = true;
            Field[] fields = object.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                String key = field.getName();
                Object value = field.get(object);

                if (value != null) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        out.write(",");
                    }
                    out.write("\"" + key + "\"");
                    out.write(":");
                    writeValue(value, out);
                }
            }

            out.write("}");
        }
    }

    private static void writeArray(Collection collection, StringWriter out) throws IllegalAccessException {
        if (collection == null) {
            out.write("[]");
        } else {
            out.write("[");

            boolean isFirst = true;
            for (Object value : collection) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    out.write(",");
                }

                writeValue(value, out);
            }

            out.write("]");
        }
    }

    private static void writeMap(Map map, StringWriter out) throws IllegalAccessException {
        if (map == null) {
            out.write("{}");
        } else {
            out.write("{");

            boolean isFirst = true;
            for (Object entry : map.entrySet()) {
                Object key = ((Map.Entry) entry).getKey();
                Object value = ((Map.Entry) entry).getValue();

                if (value != null) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        out.write(",");
                    }
                    out.write("\"" + key.toString() + "\"");
                    out.write(":");
                    writeValue(value, out);
                }
            }

            out.write("}");
        }
    }

    private static void writeValue(Object value, StringWriter out) throws IllegalAccessException {
        if (value != null) {
            if (value instanceof String) {
                out.write("\"" + value + "\"");
            } else if (value instanceof Double) {
                out.write(value.toString());
            } else if (value instanceof Float) {
                out.write(value.toString());
            } else if (value instanceof Number) {
                out.write(value.toString());
            } else if (value instanceof Boolean) {
                out.write(value.toString());
            } else if (value instanceof Map) {
                writeMap((Map)value, out);
            } else if (value instanceof Collection) {
                writeArray((Collection)value, out);
            } else if (value instanceof Date) {
                out.write(((Date) value).getTime() + "");
            } else {
                writeObject(value, out);
            }
        }
    }
}
