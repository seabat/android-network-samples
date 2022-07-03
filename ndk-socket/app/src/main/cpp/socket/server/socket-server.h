#ifndef NDK_SOCKET_SOCKET_SERVER_H
#define NDK_SOCKET_SOCKET_SERVER_H

#include <jni.h>
#include "i-msg-listener.h"
#include "socket/server/i-loop-transport.h"

class SocketServer : public IMsgListener {
private:
    //variables
    static SocketServer* server_; // instance of this class
    jobject j_server_;
    std::shared_ptr<ILoopTransport> loop_;
    bool tcp_enabled_; // TCP のループが有効か
    bool udp_enabled_; // UDP のループが有効か

    //constructors
    SocketServer(jobject jServer);

public:
    //constructors
    ~SocketServer();

    //methods
    static SocketServer* createInstance(jobject jServer);
    static SocketServer* getInstance();
    void run(std::string transportType);
    void callback(std::string msg) override;
    void setLoop(std::shared_ptr<ILoopTransport> loop);
    static void stop();
};

#endif //NDK_SOCKET_SOCKET_SERVER_H