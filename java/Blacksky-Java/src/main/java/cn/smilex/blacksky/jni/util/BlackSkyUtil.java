package cn.smilex.blacksky.jni.util;

import cn.smilex.blacksky.jni.json.JsonMut;
import cn.smilex.blacksky.jni.json.type.JsonType;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author smilex
 */
public class BlackSkyUtil {

    public static enum JAVA_TYPE {
        BYTE,
        CHAR,
        SHORT,
        INT,
        LONG,
        DOUBLE,
        BOOLEAN,
        STRING,
        COLLECTION,
        NULL,
    }

    public enum ERROR_MSG {
        ERROR_APPLY_MEMORY_ERROR("ERROR: apply memory error!");

        public String msg;

        ERROR_MSG(String msg) {
            this.msg = msg;
        }
    }

    public static JAVA_TYPE getFieldType(Class<?> type) {

        if (type == byte.class || type == Byte.class) {
            return JAVA_TYPE.BYTE;
        } else if (type == char.class || type == Character.class) {
            return JAVA_TYPE.CHAR;
        } else if (type == short.class || type == Short.class) {
            return JAVA_TYPE.SHORT;
        } else if (type == int.class || type == Integer.class) {
            return JAVA_TYPE.INT;
        } else if (type == long.class || type == Long.class) {
            return JAVA_TYPE.LONG;
        } else if (type == double.class || type == Double.class) {
            return JAVA_TYPE.DOUBLE;
        } else if (type == boolean.class || type == Boolean.class) {
            return JAVA_TYPE.BOOLEAN;
        } else if (type == String.class) {
            return JAVA_TYPE.STRING;
        } else if (type == Collection.class || type == List.class || type == Set.class || type == Map.class) {
            return JAVA_TYPE.COLLECTION;
        } else {
            return JAVA_TYPE.NULL;
        }
    }

    public static <T> void jsonDataInsert(T list, JsonMut.JsonMutArr arr) {

        if (list instanceof List) {
            for (Object o : (List) list) {
                switch (getFieldType(o.getClass())) {
                    case SHORT:
                    case INT: {
                        arr.addArrInt((int) o);
                        break;
                    }

                    case LONG: {
                        arr.addArrLong((long) o);
                        break;
                    }

                    case DOUBLE: {
                        arr.addArrDouble((double) o);
                        break;
                    }

                    case BOOLEAN: {
                        arr.addArrBoolean((boolean) o);
                        break;
                    }

                    case STRING: {
                        arr.addArrStr((String) o);
                        break;
                    }
                }
            }
        } else if (list instanceof Set) {
            for (Object o : (Set) list) {
                switch (getFieldType(o.getClass())) {
                    case SHORT:
                    case INT: {
                        arr.addArrInt((int) o);
                        break;
                    }

                    case LONG: {
                        arr.addArrLong((long) o);
                        break;
                    }

                    case DOUBLE: {
                        arr.addArrDouble((double) o);
                        break;
                    }

                    case BOOLEAN: {
                        arr.addArrBoolean((boolean) o);
                        break;
                    }

                    case STRING: {
                        arr.addArrStr((String) o);
                        break;
                    }
                }
            }
        }
    }

    public static String objToJsonStr(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] objAllField = getObjAllField(objClass);

        if (objAllField.length <= 0) {
            return null;
        }

        JsonMut mut = JsonMut.buildObject();
        var root = (JsonMut.JsonMutObject) mut.getRoot();

        try {
            for (Field field : objAllField) {
                Class<?> type = field.getType();
                String fieldName = field.getName();

                if (type == String.class) {
                    root.addStr(fieldName, (String) field.get(obj));
                } else if (type == short.class || type == Short.class || type == int.class || type == Integer.class) {
                    root.addInt(fieldName, (int) field.get(obj));
                } else if (type == long.class || type == Long.class) {
                    root.addLong(fieldName, (long) field.get(obj));
                } else if (type == double.class || type == Double.class) {
                    root.addDouble(fieldName, (double) field.get(obj));
                } else if (type == boolean.class || type == Boolean.class) {
                    root.addBoolean(fieldName, (boolean) field.get(obj));
                } else if (type == List.class) {
                    var arr = mut.createFreeJsonMutArr(fieldName);
                    root.bind(fieldName, arr);
                    var list = (List) field.get(obj);

                    jsonDataInsert(list, arr);
                } else if (type == Set.class) {
                    var arr = mut.createFreeJsonMutArr(fieldName);
                    root.bind(fieldName, arr);

                    var set = (Set) field.get(obj);

                    jsonDataInsert(set, arr);
                } else if (type == Map.class) {

                }
            }

            return mut.getJsonStr();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mut.close();
        }
    }

    public static Field[] getObjAllField(Class<?> objClass) {
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
        }

        return fields;
    }

    public static String getType(int type) {
        switch (type) {
            default:
            case 1: {
                return "none";
            }

            case 2: {
                return "null";
            }

            case 3: {
                return "boolean";
            }

            case 4: {
                return "number";
            }

            case 5: {
                return "string";
            }

            case 6: {
                return "array";
            }

            case 7: {
                return "object";
            }
        }
    }
}
