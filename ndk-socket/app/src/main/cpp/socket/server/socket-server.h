#ifndef NDK_SOCKET_SOCKET_SERVER_H
#define NDK_SOCKET_SOCKET_SERVER_H

#include <jni.h>
#include "i-msg-listener.h"
#include "socket/server/i-loop-transport.h"

class SocketServer : public IMsgListener {
private:
    //variables
    static SocketServer* server_; // instance of this class
    std::string transport_type_;
    jobject j_server_;
    std::shared_ptr<ILoopTransport> loop;

    //constructors
    SocketServer(std::string transportType, jobject jServer);

public:
    //constructors
    ~SocketServer();

    //methods
    static SocketServer* createInstance(std::string transportType, jobject jServer);
    static SocketServer* getInstance();
    void run();
    void callback(std::string msg) override;
    void setLoop(std::shared_ptr<ILoopTransport> loop);
    static void stop();
};

#endif //NDK_SOCKET_SOCKET_SERVER_H
