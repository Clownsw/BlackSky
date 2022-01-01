
CC_DEBUG = g++ -Wall -s -static -g -fno-exceptions -c
CC_RELEASE = g++ -Wall -Ofast -s -static -DNDEBUG -flto -fno-exceptions -c
INCLUDE = -ID:/zulu-jdk11/include -ID:/zulu-jdk11/include/win32 -Iinclude
LIB = -Llib -lfmt -lhv -lssl -lcrypto -lws2_32 -lwsock32 -lpthread
LINK = g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o libhv.dll

DEBUG_NAME = Tools.o Requests.o Md5.o Base64.o
RELEASE_NAME = ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o

all: Tools.o Requests.o Md5.o Base64.o
	$(LINK) $(DEBUG_NAME) $(LIB)
	
release: ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o
	$(LINK) $(RELEASE_NAME) $(LIB)


ToolsRelease.o: Tools.h Tools.cpp
	$(CC_RELEASE) -o ToolsRelease.o Tools.cpp $(INCLUDE)

RequestsRelease.o: Interface.h Requests.h Requests.cpp
	$(CC_RELEASE) -o RequestsRelease.o Requests.cpp $(INCLUDE)

Md5Release.o: Interface.h Md5.cpp
	$(CC_RELEASE) -o Md5Release.o Md5.cpp $(INCLUDE)

Base64Release.o: Interface.h Base64.cpp
	$(CC_RELEASE) -o Base64Release.o Base64.cpp $(INCLUDE)



Tools.o: Tools.h Tools.cpp
	$(CC_DEBUG) -o Tools.o Tools.cpp $(INCLUDE)

Requests.o: Interface.h Requests.h Requests.cpp
	$(CC_DEBUG) -o Requests.o Requests.cpp $(INCLUDE)

Md5.o: Interface.h Md5.cpp
	$(CC_DEBUG) -o Md5.o Md5.cpp $(INCLUDE)

Base64.o: Interface.h Base64.cpp
	$(CC_DEBUG) -o Base64.o Base64.cpp $(INCLUDE)

clean:
	del Tools.o
	del Md5.o
	del Base64.o
	del Requests.o
	del ToolsRelease.o
	del RequestsRelease.o
	del Md5Release.o
	del Base64Release.o
	del libhv.dll