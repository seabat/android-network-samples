#ifndef NDK_SOCKET_SOCKET_SERVER_H
#define NDK_SOCKET_SOCKET_SERVER_H

#include <jni.h>
#include "msg-listener.h"
#include "loop-tcp-ipv4.h"

class SocketServer : public IMsgListener {
private:
    //variables
    static SocketServer* server_;
    jobject j_server_;
    std::shared_ptr<LoopTcpIpv4> loop;

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
    void setLoop(std::shared_ptr<LoopTcpIpv4> loop);
    static void stop();
};

#endif //NDK_SOCKET_SOCKET_SERVER_H
