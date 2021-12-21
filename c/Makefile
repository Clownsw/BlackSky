all: Tools.o Requests.o
	g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o libhv.dll Tools.o Requests.o -Llib -lhv -lssl -lcrypto -lws2_32 -lwsock32
	
release: ToolsRelease.o RequestsRelease.o
	g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o libhv.dll ToolsRelease.o RequestsRelease.o -Llib -lhv -lssl -lcrypto -lws2_32 -lwsock32

ToolsRelease.o: Tools.h Tools.cpp
	g++ -Wall -Ofast -s -static -DNDEBUG -fno-exceptions -o ToolsRelease.o -c Tools.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

RequestsRelease.o: Requests.h Requests.cpp
	g++ -Wall -Ofast -s -static -DNDEBUG -fno-exceptions -o RequestsRelease.o -c Requests.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

Tools.o: Tools.h Tools.cpp
	g++ -Wall -Ofast -s -static -g -fno-exceptions -c Tools.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

Requests.o: Requests.h Requests.cpp
	g++ -Wall -Ofast -s -static -g -fno-exceptions -c Requests.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

clean:
	del Tools.o
	del Requests.o
	del ToolsRelease.o
	del RequestsRelease.o
	del libhv.dll