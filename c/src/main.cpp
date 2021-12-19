#include "Requests.h"
#include <hv/requests.h>

/*
 * 以Get方式请求网站并返回结果
 * Class:     cn_smilex_libhv_jni_Requests
 * Method:    get
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 * Author:	  Smilex
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_Requests_get
(JNIEnv* env, jobject, jstring url) {
	auto resp = requests::get(env->GetStringUTFChars(url, false));
	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}

/*
 * 以Post方式请求指定网站并返回结果
 * Class:     cn_smilex_libhv_jni_Requests
 * Method:    post
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 * Author:	  Smilex
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_Requests_post
(JNIEnv* env, jobject, jstring url) {
	auto resp = requests::post(env->GetStringUTFChars(url, false));
	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}

/*
 * 通过HttpRequest类请求指定网站并返回结果
 * Class:     cn_smilex_libhv_jni_Requests
 * Method:    request
 * Signature: (Lcn/smilex/libhv/jni/HttpRequest;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_Requests_request 
(JNIEnv* env, jobject, jobject request) {

	jclass httpRequestClass = env->GetObjectClass(request);
	jfieldID field_Method = env->GetFieldID(httpRequestClass, "method", "I");
	int method = env->GetIntField(request, field_Method);

	jfieldID fieldUrl = env->GetFieldID(httpRequestClass, "url", "Ljava/lang/String;");

	if (fieldUrl == NULL) {
		return env->NewStringUTF("error: found url");;
	}

	jstring stringUrl = (jstring) env->GetObjectField(request, fieldUrl);

	requests::Request req(new HttpRequest);

	req->SetMethod(method == 1 ? "GET" : "POST");
	req->SetUrl(env->GetStringUTFChars(stringUrl, false));

	/**
	 * 获取到Map<String, String> headers的jfieldID
	 */

	jfieldID fieldHeaders = env->GetFieldID(httpRequestClass, "headers", "Ljava/util/HashMap;");
	jobject objectHeaders = env->GetObjectField(request, fieldHeaders);

	// if (fieldHeaders != NULL) {
	// 	std::cout << fieldHeaders << std::endl;
	// }

	// 获取到HashMap类的class
	jclass hashMapClass = env->FindClass("Ljava/util/HashMap;");
	// 获取到HashMap中的entrySet()方法ID
	jmethodID methodEntrySet = env->GetMethodID(hashMapClass, "entrySet", "()Ljava/util/Set;");

	jobject setObj = env->CallObjectMethod(objectHeaders, methodEntrySet);

	jmethodID methodSize = env->GetMethodID(hashMapClass, "size", "()I");

	jint intSize = env->CallIntMethod(objectHeaders, methodSize);

	// std::cout << intSize << std::endl;

	// 获取到set方法中的iterator() 方法ID
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

		req->headers[env->GetStringUTFChars(key, false)] = env->GetStringUTFChars(value, false);

		env->DeleteLocalRef(value);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(objectEntry);
	}

	auto resp = requests::request(req);

	env->DeleteLocalRef(classMapEntry);
	env->DeleteLocalRef(classIterator);
	env->DeleteLocalRef(objectIterator);
	env->DeleteLocalRef(classSet);
	env->DeleteLocalRef(setObj);
	env->DeleteLocalRef(hashMapClass);
	env->DeleteLocalRef(objectHeaders);
	env->DeleteLocalRef(stringUrl);
	env->DeleteLocalRef(httpRequestClass);

	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}