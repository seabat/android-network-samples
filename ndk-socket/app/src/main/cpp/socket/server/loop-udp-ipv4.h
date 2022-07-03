#ifndef NDK_SOCKET_LOOP_UDP_IPV4_H
#define NDK_SOCKET_LOOP_UDP_IPV4_H


#include "i-msg-listener.h"
#include "i-loop-transport.h"

class LoopUdpIpv4 : public ILoopTransport {
private:
    IMsgListener* listener_;
    bool stopFlg_;

public:
    LoopUdpIpv4(IMsgListener* listener);
    void run() override;
    void stop() override;
};


#endif //NDK_SOCKET_LOOP_UDP_IPV4_H
