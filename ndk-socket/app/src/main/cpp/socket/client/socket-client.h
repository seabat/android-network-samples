#ifndef NDK_SOCKET_SOCKET_CLIENT_H
#define NDK_SOCKET_SOCKET_CLIENT_H

#include <jni.h>
#include "i-msg-listener.h"

class SocketClient : public IMsgListener {
private:
    //variables
    static std::shared_ptr<SocketClient> client_;
    jobject j_client_;


public:
    // constructors
    SocketClient(jobject jClient);
      //NOTE: std::make_shared は public のコンストラクタでないと呼び出せない
    //methods
    ~SocketClient();
    static std::shared_ptr<SocketClient> createInstance(jobject jClient);
    void callback(std::string msg) override;
    void send(std::string msg);
};

#endif //NDK_SOCKET_SOCKET_CLIENT_H
