//
// By: smilex
//

#pragma once
#ifndef REQUESTS_H
#define REQUESTS_H

#include <hv/HttpMessage.h>

typedef struct JavaHttpRequest {
    const char * url = nullptr;
    http_method method;
    const char * body = nullptr;
    std::map<std::string, std::string> headers;
    std::map<std::string, std::string> params;
    const char * cookie = nullptr;
} JavaHttpRequest;

typedef struct JavaHttpResponse {
    const char * body;
    int statusCode;
    http_cookies cookies;
    http_headers headers;
    http_content_type contentType;
} JavaHttpResponse;


#endif //REQUESTS_H
