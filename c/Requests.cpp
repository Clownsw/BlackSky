//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"

/*
 * 通过HttpRequest类请求指定网站并返回HttpResponse
 * Class:     cn_smilex_libhv_jni_http_Requests
 * Method:    request
 * Signature: (Lcn/smilex/libhv/jni/http/HttpRequest;)Ljava/lang/String;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_http_Requests_request
(JNIEnv* env, jobject obj, jobject request) {

    requests::Request req(new HttpRequest);

    JavaHttpRequest* javaHttpRequest = jobjectToRequest(env, request);

    // 如果未传入url则抛出异常
    if (javaHttpRequest == nullptr) {
        jclass classNullPointerException = env->FindClass(CLASSNAME_NullPointerException);

        env->ThrowNew(classNullPointerException, "*** url is null ***");
        env->DeleteLocalRef(classNullPointerException);
        return nullptr;
    }

    // 设置请求URL
    req->SetUrl(javaHttpRequest->url);

    // 设置请求方式
    req->method = javaHttpRequest->method;

    // 设置请求cookie
    if (javaHttpRequest->cookie != nullptr) {
        req->headers["Cookie"] = javaHttpRequest->cookie;
    }

    // 设置请求头
	for (auto& item : javaHttpRequest->headers) {
		req->headers[std::string(item.first).c_str()] = item.second;
	}

    // 设置请求参数
	for (auto& item : javaHttpRequest->params) {
//		req->query_params.insert(std::make_pair(item.first.c_str(), item.second.c_str()));
        req->SetUrlEncoded(std::string(item.first).c_str(), item.second);
    }

    // 设置body
    if (javaHttpRequest->body != nullptr) {
        req->body = javaHttpRequest->body;
    }

    // 发出请求
    auto resp = requests::request(req);

    jobject objectHttpResponse = nullptr;

    if (resp != nullptr) {
        objectHttpResponse = ResponseToHttpResponse(env, resp);
    }

    delete javaHttpRequest;

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(request);

    return objectHttpResponse;
}

/*
 * Class:     cn_smilex_libhv_jni_http_Requests
 * Method:    asyncRequest
 * Signature: (Lcn/smilex/libhv/jni/http/HttpRequest;)Lcn/smilex/libhv/jni/http/HttpResponse;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_http_Requests_asyncRequest
    (JNIEnv* env, jobject obj, jobject request) {

    requests::Request req(new HttpRequest);

    JavaHttpRequest* javaHttpRequest = jobjectToRequest(env, request);

    // 如果未传入url则抛出异常
    if (javaHttpRequest == nullptr) {
        throwException(env, CLASSNAME_NullPointerException, "*** url is null ***");
        return nullptr;
    }

    // 设置请求URL
    req->SetUrl(javaHttpRequest->url);

    // 设置请求方式
    req->method = javaHttpRequest->method;

    // 设置请求cookie
    if (javaHttpRequest->cookie != nullptr) {
        req->headers["Cookie"] = javaHttpRequest->cookie;
    }

    // 设置请求头
    for (auto& item : javaHttpRequest->headers) {
        req->headers[std::string(item.first).c_str()] = item.second;
    }

    // 设置请求参数
    for (auto& item : javaHttpRequest->params) {
//		req->query_params.insert(std::make_pair(item.first.c_str(), item.second.c_str()));
        req->SetUrlEncoded(std::string(item.first).c_str(), item.second);
    }

    // 设置body
    if (javaHttpRequest->body != nullptr) {
        req->body = javaHttpRequest->body;
    }

    int finished = 0;
    requests::Response response = nullptr;
    jobject objectHttpResponse = nullptr;

    requests::async(req, [&](const HttpResponsePtr& resp) {
        if (resp != nullptr) {
            response = resp;
        }
        finished = 1;
    });

    while (!finished) hv_sleep(1);

    if (response != nullptr) {
        objectHttpResponse = ResponseToHttpResponse(env, response);
    }

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(request);

    return objectHttpResponse;
}