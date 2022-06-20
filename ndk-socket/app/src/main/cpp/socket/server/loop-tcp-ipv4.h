#ifndef NDK_SOCKET_LOOP_TCP_IPV4_H
#define NDK_SOCKET_LOOP_TCP_IPV4_H


#include "i-msg-listener.h"
#include "i-loop-transport.h"

class LoopTcpIpv4 : public ILoopTransport {
private:
    IMsgListener* listener_;
    bool stopFlg_;

public:
    LoopTcpIpv4(IMsgListener* listener);
    void run();
    void stop();
};


#endif //NDK_SOCKET_LOOP_TCP_IPV4_H
