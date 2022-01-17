
CC_DEBUG = g++ -Wall -Wformat -s -g -fPIC -c
CC_RELEASE = g++ -Wall -Ofast -s -static -DNDEBUG -flto -fPIC -c
INCLUDE = -Iinclude
LIB = -Llib -lyyjson -lspdlog -lhv -lssl -lcrypto -lws2_32 -lwsock32 -lpthread
DEBUG_LINK = g++ -Wl,--gc-sections -shared -o blacksky.dll
RELEASE_LINK = g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o blacksky.dll

DEBUG_NAME = Tools.o Requests.o Md5.o Base64.o  Sha.o Logger.o Json.o
RELEASE_NAME = ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o ShaRelease.o LoggerRelease.o JsonRelease.o

all: Tools.o Requests.o Md5.o Base64.o Sha.o Logger.o Json.o
	$(DEBUG_LINK) $(DEBUG_NAME) $(LIB)
	
release: ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o ShaRelease.o LoggerRelease.o JsonRelease.o
	$(RELEASE_LINK) $(RELEASE_NAME) $(LIB)


ToolsRelease.o: Tools.h Tools.cpp
	$(CC_RELEASE) -o ToolsRelease.o Tools.cpp $(INCLUDE)

RequestsRelease.o: Interface.h Requests.h Requests.cpp
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



Tools.o: Tools.h Tools.cpp
	$(CC_DEBUG) -o Tools.o Tools.cpp $(INCLUDE)

Requests.o: Interface.h Requests.h Requests.cpp
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
	del Tools.o
	del Md5.o
	del Base64.o
	del Requests.o
	del Sha.o
	del Logger.o
	del Json.o
	del ToolsRelease.o
	del RequestsRelease.o
	del Md5Release.o
	del Base64Release.o
	del ShaRelease.o
	del LoggerRelease.o
	del JsonRelease.o
	del libhv.dll