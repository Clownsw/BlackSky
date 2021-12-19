#pragma once

#ifndef TOOLS_H
#define TOOLS_H

#include <jni.h>
#include <map>
#include <string>

/**
 * @brief 
 * 
 * @param env 
 * @param obj 
 * @return std::map<std::string, std::string> 
 */
std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj);

#endif