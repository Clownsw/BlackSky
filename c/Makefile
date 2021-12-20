all: tools.o main.o
	g++ -static -static-libgcc -static-libstdc++ -DNDEBUG -Wl,--gc-sections -shared -o libhv.dll tools.o main.o -Llib -lhv -lssl -lcrypto -lws2_32 -lwsock32

tools.o: tools.h tools.cpp
	g++ -Wall -Ofast -s -static -DNDEBUG -finline-functions -fno-bounds-check -freg-struct-return -fvisibility=hidden -fno-enforce-eh-specs -fno-rtti -fno-exceptions -c tools.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

main.o: Requests.h main.cpp
	g++ -Wall -Ofast -s -static -DNDEBUG -finline-functions -fno-bounds-check -freg-struct-return -fvisibility=hidden -fno-enforce-eh-specs -fno-rtti -fno-exceptions -c main.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

clean:
	del tools.o
	del main.o
	del libhv.dll