package cn.smilex.blacksky.jni.util;

/**
 * @author smilex
 */
public class BlackSkyUtil {
    public enum ERROR_MSG {
        ERROR_APPLY_MEMORY_ERROR("ERROR: apply memory error!");

        public String msg;

        ERROR_MSG(String msg) {
            this.msg = msg;
        }
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
