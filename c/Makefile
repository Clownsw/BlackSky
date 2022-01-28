
CC_DEBUG = g++ -Wall -std=c++17 -Wformat -s -g -fPIC -c
CC_RELEASE = g++ -Wall -std=c++17 -O2 -s -static -DNDEBUG -flto -fPIC -c
INCLUDE = -Iinclude
LIB = -Llib -lyyjson -lspdlog -lhv -lssl -lcrypto -lpthread
DEBUG_LINK = g++ -Wl,--gc-sections -shared -o blacksky.dll
RELEASE_LINK = g++ -static -static-libgcc -static-libstdc++ -O2 -flto -s -Wl,--gc-sections -shared -o libblacksky.so

DEBUG_NAME = Tools.o Requests.o Md5.o Base64.o  Sha.o Logger.o Json.o
RELEASE_NAME = ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o ShaRelease.o LoggerRelease.o JsonRelease.o

all: Tools.o Requests.o Md5.o Base64.o Sha.o Logger.o Json.o
	$(DEBUG_LINK) $(DEBUG_NAME) $(LIB)
	
release: ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o ShaRelease.o LoggerRelease.o JsonRelease.o
	$(RELEASE_LINK) $(RELEASE_NAME) $(LIB)


ToolsRelease.o: Tools.h Tools.cpp Error.hpp
	$(CC_RELEASE) -o ToolsRelease.o Tools.cpp $(INCLUDE)

RequestsRelease.o: Interface.h Requests.h Requests.cpp Error.hpp
	$(CC_RELEASE) -o RequestsRelease.o Requests.cpp $(INCLUDE)

Md5Release.o: Interface.h Md5.cpp
	$(CC_RELEASE) -o Md5Release.o Md5.cpp $(INCLUDE)

Base64Release.o: Interface.h Base64.cpp
	$(CC_RELEASE) -o Base64Release.o Base64.cpp $(INCLUDE)

ShaRelease.o: Interface.h Sha.cpp
	$(CC_RELEASE) -o ShaRelease.o Sha.cpp $(INCLUDE)

LoggerRelease.o: Interface.h Logger.cpp
	$(CC_RELEASE) -o LoggerRelease.o Logger.cpp $(INCLUDE)

JsonRelease.o: Interface.h Json.cpp
	$(CC_RELEASE) -o JsonRelease.o Json.cpp $(INCLUDE)



Tools.o: Tools.h Tools.cpp Error.hpp
	$(CC_DEBUG) -o Tools.o Tools.cpp $(INCLUDE)

Requests.o: Interface.h Requests.h Requests.cpp Error.hpp
	$(CC_DEBUG) -o Requests.o Requests.cpp $(INCLUDE)

Md5.o: Interface.h Md5.cpp
	$(CC_DEBUG) -o Md5.o Md5.cpp $(INCLUDE)

Base64.o: Interface.h Base64.cpp
	$(CC_DEBUG) -o Base64.o Base64.cpp $(INCLUDE)

Sha.o: Interface.h Sha.cpp
	$(CC_DEBUG) -o Sha.o Sha.cpp $(INCLUDE)

Logger.o: Interface.h Logger.cpp
	$(CC_DEBUG) -o Logger.o Logger.cpp $(INCLUDE)

Json.o: Interface.h Json.cpp
	$(CC_DEBUG) -o Json.o Json.cpp $(INCLUDE)

clean:
	rm -rf Tools.o
	rm -rf Md5.o
	rm -rf Base64.o
	rm -rf Requests.o
	rm -rf Sha.o
	rm -rf Logger.o
	rm -rf Json.o
	rm -rf ToolsRelease.o
	rm -rf RequestsRelease.o
	rm -rf Md5Release.o
	rm -rf Base64Release.o
	rm -rf ShaRelease.o
	rm -rf LoggerRelease.o
	rm -rf JsonRelease.o
	rm -rf libblacksky.so
