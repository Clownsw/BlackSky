//
// By: smilex
//

#include "Tools.h"

std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj) {

    std::map<std::string, std::string> m;

    // 获取到HashMap类的class
	jclass hashMapClass = env->FindClass(CLASSNAME_HashMap);
	// 获取到HashMap中的entrySet()方法ID
	jmethodID methodEntrySet = env->GetMethodID(hashMapClass, "entrySet", "()Ljava/util/Set;");

	jobject setObj = env->CallObjectMethod(obj, methodEntrySet);

    jclass classSet = env->FindClass(CLASSNAME_Set);
	jmethodID methodIterator = env->GetMethodID(classSet, "iterator", "()Ljava/util/Iterator;");

	// 通过调用iterator()获取到iterator对象
	jobject objectIterator = env->CallObjectMethod(setObj, methodIterator);

	// 获取Iterator类
    jclass classIterator = env->FindClass(CLASSNAME_Iterator);

	// 获取Iterator类中的hasNext()方法ID
	jmethodID methodHasNext = env->GetMethodID(classIterator, "hasNext", "()Z");
	// 获取Iterator类中的next()方法ID
	jmethodID methodNext = env->GetMethodID(classIterator, "next", "()Ljava/lang/Object;");

	// 获取Entry类
	jclass classMapEntry = env->FindClass(CLASSNAME_Map$Entry);

	// 获取Entry类内的getKey方法ID
	jmethodID methodGetKey = env->GetMethodID(classMapEntry, "getKey", "()Ljava/lang/Object;");
	// 获取Entry类内的getValue方法ID
	jmethodID methodGetValue = env->GetMethodID(classMapEntry, "getValue", "()Ljava/lang/Object;");

	while (env->CallBooleanMethod(objectIterator, methodHasNext)) {
		jobject objectEntry = env->CallObjectMethod(objectIterator, methodNext);
		jstring key = (jstring)env->CallObjectMethod(objectEntry, methodGetKey);

		jstring value = (jstring)env->CallObjectMethod(objectEntry, methodGetValue);

        m.insert(std::make_pair<std::string, std::string>(env->GetStringUTFChars(key, JNI_FALSE), 
                env->GetStringUTFChars(value, JNI_FALSE)));

		env->DeleteLocalRef(value);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(objectEntry);
	}

	env->DeleteLocalRef(classMapEntry);
	env->DeleteLocalRef(classIterator);
	env->DeleteLocalRef(classSet);
	env->DeleteLocalRef(objectIterator);
	env->DeleteLocalRef(setObj);
    return m;
}

static jobject _createHashMap(JNIEnv* env) {
	jclass classHashMap = env->FindClass(CLASSNAME_HashMap);

	jmethodID methodHashMapDefaultCon = env->GetMethodID(classHashMap, "<init>", "()V");

    env->DeleteLocalRef(classHashMap);

	return env->NewObject(classHashMap, methodHashMapDefaultCon);
}

jobject createHashMap(JNIEnv* env) { return _createHashMap(env); }

static const char* _getContentTypeName(http_content_type type) {
    switch (type) {
        case TEXT_PLAIN: {
            return "text/plain";
        }
        case TEXT_HTML: {
            return "text/html";
        }
        case TEXT_CSS: {
            return "text/css";
        }
        case IMAGE_JPEG: {
            return "image/jpeg";
        }
        case IMAGE_PNG: {
            return "image/png";
        }
        case IMAGE_GIF: {
            return "image/gif";
        }
        case IMAGE_BMP: {
            return "image/bmp";
        }
        case IMAGE_SVG: {
            return "image/svg";
        }
        case APPLICATION_OCTET_STREAM: {
            return "application/octet-stream";
        }
        case APPLICATION_JAVASCRIPT: {
            return "application/javascript";
        }
        case APPLICATION_XML: {
            return "application/xml";
        }
        case APPLICATION_JSON: {
            return "application/json";
        }
        case APPLICATION_GRPC: {
            return "application/grpc";
        }
        case APPLICATION_URLENCODED: {
            return "application/x-www-form-urlencoded";
        }
        case MULTIPART_FORM_DATA: {
            return "multipart/form-data";
        }
        default: {
            return "null";
        }
    }
}

const char* getContentTypeName(http_content_type type) {
    return _getContentTypeName(type);
}

static http_method _getMethodName(int n) {
    switch (n) {
        case 0: { return HTTP_DELETE; }
        default:
        case 1: { return HTTP_GET; }
        case 2: { return HTTP_HEAD; }
        case 3: { return HTTP_POST; }
        case 4: { return HTTP_PUT; }
        case 5: { return HTTP_CONNECT; }
        case 6: { return HTTP_OPTIONS; }
        case 7: { return HTTP_TRACE; }
        case 8: { return HTTP_COPY; }
        case 9: { return HTTP_LOCK; }
        case 10: { return HTTP_MKCOL; }
        case 11: { return HTTP_MOVE; }
    }
}

http_method getMethodName(int n) {
    return _getMethodName(n);
}