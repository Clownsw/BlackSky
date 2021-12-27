//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"
#include <hv/requests.h>

/*
 * 通过HttpRequest类请求指定网站并返回HttpResponse
 * Class:     cn_smilex_libhv_jni_http_Requests
 * Method:    request
 * Signature: (Lcn/smilex/libhv/jni/http/HttpRequest;)Ljava/lang/String;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_http_Requests_request
(JNIEnv* env, jobject, jobject request) {

    requests::Request req(new HttpRequest);

    jclass httpRequestClass = env->FindClass(CLASSNAME_HttpRequest);

    jfieldID field_Method = env->GetFieldID(httpRequestClass, "method", "I");

    int method = env->GetIntField(request, field_Method);

    jfieldID fieldUrl = env->GetFieldID(httpRequestClass, "url", CLASSNAME_String);
	jstring stringUrl = (jstring) env->GetObjectField(request, fieldUrl);

    /**
     * 如果未传入url则抛出异常
     */
    if (stringUrl == nullptr) {
        jclass classNullPointerException = env->FindClass(CLASSNAME_NullPointerException);

        env->ThrowNew(classNullPointerException, "*** url is null ***");
        env->DeleteLocalRef(classNullPointerException);
        return nullptr;
    }

    jfieldID fieldHeaders = env->GetFieldID(httpRequestClass, "headers", CLASSNAME_HashMap);
	jfieldID fieldParams = env->GetFieldID(httpRequestClass, "params", CLASSNAME_HashMap);
	jfieldID fieldCookie = env->GetFieldID(httpRequestClass, "cookie", CLASSNAME_String);
//	jfieldID fieldBody = env->GetFieldID(httpRequestClass, "body", CLASSNAME_String);

	jobject objectHeaders = env->GetObjectField(request, fieldHeaders);
	jobject objectParams = env->GetObjectField(request, fieldParams);
	jobject objectCookie = env->GetObjectField(request, fieldCookie);
//	jobject objectBody = env->GetObjectField(request, fieldBody);

    // 设置请求方法
    req->method = getMethodName(method);

    // 设置请求URL
	req->SetUrl(env->GetStringUTFChars(stringUrl, JNI_FALSE));

//    // 设置请求body
//    if (objectBody != nullptr) {
//        req->SetBody(env->GetStringUTFChars((jstring)objectBody, JNI_FALSE));
//    }

    std::map<std::string, std::string> m_headers = parseMap(env, objectHeaders);
	for (auto& item : m_headers) {
		req->headers[item.first] = item.second;
	}

    std::map<std::string, std::string> m_params = parseMap(env, objectParams);

    // 设置请求参数
	for (auto& item : m_params) {
//		req->query_params.insert(std::make_pair(item.first.c_str(), item.second.c_str()));
        req->SetUrlEncoded(item.first.c_str(), item.second);
    }

    //设置请求cookie
    if (objectCookie != nullptr) {
		req->headers["Cookie"] = env->GetStringUTFChars((jstring)objectCookie, JNI_FALSE);
	}

    auto resp = requests::request(req);

	jclass classHttpResponse = env->FindClass(CLASSNAME_HttpResponse);
    jclass classHashMap = env->FindClass(CLASSNAME_HashMap);

    jmethodID methodHttpResponseDefaultCon = env->GetMethodID(classHttpResponse, "<init>", "()V");
    jmethodID methodPut = env->GetMethodID(classHashMap,
                                           "put",
                                           "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

    jfieldID fieldResponseBody = env->GetFieldID(classHttpResponse, "body", CLASSNAME_String);
	jfieldID fieldStatusCode = env->GetFieldID(classHttpResponse, "statusCode", "I");
    jfieldID fieldResponseCookies = env->GetFieldID(classHttpResponse, "cookies", CLASSNAME_HashMap);
    jfieldID fieldResponseHeaders = env->GetFieldID(classHttpResponse, "headers", CLASSNAME_HashMap);
    jfieldID fieldContentType = env->GetFieldID(classHttpResponse, "contentType", CLASSNAME_String);

    jobject objectHttpResponse = env->NewObject(classHttpResponse, methodHttpResponseDefaultCon);
    jobject tmpCookies = env->GetObjectField(objectHttpResponse, fieldResponseCookies);
    jobject tmpHeaders = env->GetObjectField(objectHttpResponse, fieldResponseHeaders);

    if (resp != nullptr) {
        // 设置响应内容
        env->SetObjectField(objectHttpResponse, fieldResponseBody, env->NewStringUTF(resp->body.c_str()));

        // 设置响应状态
        env->SetIntField(objectHttpResponse, fieldStatusCode, resp->status_code);

        // 设置所有响应cookie
        for (const auto &item : resp->cookies) {
            env->CallObjectMethod(tmpCookies, methodPut,
                                  env->NewStringUTF(item.name.c_str()),
                                  env->NewStringUTF(item.value.c_str()));
        }

        // 设置所有响应头
        for (const auto &item : resp->headers) {
            env->CallObjectMethod(tmpHeaders, methodPut,
                                  env->NewStringUTF(item.first.c_str()),
                                  env->NewStringUTF(item.second.c_str()));
        }

        // 设置响应contentType
        env->SetObjectField(objectHttpResponse,
                            fieldContentType,
                            env->NewStringUTF(getContentTypeName(resp->content_type)));
    }

	env->DeleteLocalRef(tmpHeaders);
	env->DeleteLocalRef(tmpHeaders);
	env->DeleteLocalRef(tmpCookies);
	env->DeleteLocalRef(classHashMap);
	env->DeleteLocalRef(classHttpResponse);
//	env->DeleteLocalRef(objectBody);
	env->DeleteLocalRef(objectCookie);
	env->DeleteLocalRef(objectParams);
	env->DeleteLocalRef(objectHeaders);
	env->DeleteLocalRef(stringUrl);
	env->DeleteLocalRef(httpRequestClass);
    env->DeleteLocalRef(request);

    return objectHttpResponse;
}
