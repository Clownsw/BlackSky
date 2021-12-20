#include "tools.h"

static jclass _queryJClassByName(JNIEnv* &env, const char* name, const char* className) {
	for (const auto& item : g_jclasss) {
//        std::cout << "name:" << name << " " << "name1:" << item.first << std::endl;
        if (strcmp(item.first.c_str(), name) == 0) {
//            std::cout << "success" << std::endl;
            return item.second;
		}
	}

    jclass tmp = env->FindClass(className);
    g_jclasss.insert(std::make_pair(name, tmp));
	return tmp;
}

jclass queryJClassByName(JNIEnv* &env, const char* name, const char* className) {
    return _queryJClassByName(env, name, className);
}

std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj) {

    std::map<std::string, std::string> m;

    // 获取到HashMap类的class
	jclass hashMapClass = env->FindClass(CLASSNAME_HashMap);
	// 获取到HashMap中的entrySet()方法ID
	jmethodID methodEntrySet = env->GetMethodID(hashMapClass, "entrySet", "()Ljava/util/Set;");

	jobject setObj = env->CallObjectMethod(obj, methodEntrySet);

    jclass classSet = queryJClassByName(env, "SetClass", CLASSNAME_Set);
	jmethodID methodIterator = env->GetMethodID(classSet, "iterator", "()Ljava/util/Iterator;");

	// 通过调用iterator()获取到iterator对象
	jobject objectIterator = env->CallObjectMethod(setObj, methodIterator);

	// 获取Iterator类
    jclass classIterator = queryJClassByName(env, "IteratorClass", CLASSNAME_Iterator);

	// 获取Iterator类中的hasNext()方法ID
	jmethodID methodHasNext = env->GetMethodID(classIterator, "hasNext", "()Z");
	// 获取Iterator类中的next()方法ID
	jmethodID methodNext = env->GetMethodID(classIterator, "next", "()Ljava/lang/Object;");

	// 获取Entry类
	jclass classMapEntry = queryJClassByName(env, "Map$EntryClass", CLASSNAME_Map$Entry);

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

	env->DeleteLocalRef(objectIterator);
	env->DeleteLocalRef(setObj);
    return m;
}

static jobject _createHashMap(JNIEnv* env) {
	jclass classHashMap = queryJClassByName(env, "HashMapClass", CLASSNAME_HashMap);

	jmethodID methodHashMapDefaultCon = env->GetMethodID(classHashMap, "<init>", "()V");

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