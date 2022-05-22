#ifndef NDK_SOCKET_SOCKET_SERVER_H
#define NDK_SOCKET_SOCKET_SERVER_H

#include <jni.h>
#include "msg-listener.h"

class SocketServer : public IMsgListener {
private:
    //variables
    static SocketServer* server_;
    jobject j_server_;

    //constructors
    SocketServer(jobject jServer);

public:
    //constructors
    ~SocketServer();

    //methods
    static SocketServer* createInstance(jobject jServer);
    static SocketServer* getInstance();
    void run();
    void callback(std::string msg) override;
    static void close();
};

#endif //NDK_SOCKET_SOCKET_SERVER_H
