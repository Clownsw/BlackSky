#include "tools.h"

static jclass _queryJclassByName(const char* name) {
	for (auto& item : g_jclasss) {
		if (item.first.compare(name)) {
			return item.second;
		}
	}
	return nullptr;
}

jclass queryJclassByName(const char* name) { return _queryJclassByName(name); }

std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj) {

    std::map<std::string, std::string> m;

    // 获取到HashMap类的class
	jclass hashMapClass = env->FindClass("Ljava/util/HashMap;");
	// 获取到HashMap中的entrySet()方法ID
	jmethodID methodEntrySet = env->GetMethodID(hashMapClass, "entrySet", "()Ljava/util/Set;");

	jobject setObj = env->CallObjectMethod(obj, methodEntrySet);

    jclass classSet = env->FindClass("Ljava/util/Set;");
	jmethodID methodIterator = env->GetMethodID(classSet, "iterator", "()Ljava/util/Iterator;");

	// 通过调用iterator()获取到iterator对象
	jobject objectIterator = env->CallObjectMethod(setObj, methodIterator);

	// 获取Iterator类
	jclass classIterator = env->FindClass("Ljava/util/Iterator;");
	// 获取Iterator类中的hasNext()方法ID
	jmethodID methodHasNext = env->GetMethodID(classIterator, "hasNext", "()Z");
	// 获取Iterator类中的next()方法ID
	jmethodID methodNext = env->GetMethodID(classIterator, "next", "()Ljava/lang/Object;");

	// 获取Entry类
	jclass classMapEntry = env->FindClass("Ljava/util/Map$Entry;");
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
	env->DeleteLocalRef(objectIterator);
	env->DeleteLocalRef(classSet);
	env->DeleteLocalRef(setObj);
	env->DeleteLocalRef(hashMapClass);
    return m;
}

static jobject _createHashMap(JNIEnv* env) {
	jclass classHashMap = queryJclassByName("HashMapClass");
	if (classHashMap == nullptr) {
		classHashMap = env->FindClass("Ljava/util/HashMap;");
		g_jclasss.insert(std::make_pair("HashMapClass", classHashMap));
	}

	std::cout << classHashMap << std::endl;

	jmethodID methodHashMapDefaultCon = env->GetMethodID(classHashMap, "<init>", "()V");
	std::cout << methodHashMapDefaultCon << std::endl;

	return env->NewObject(classHashMap, methodHashMapDefaultCon);
}

jobject createHashMap(JNIEnv* env) { return _createHashMap(env); }