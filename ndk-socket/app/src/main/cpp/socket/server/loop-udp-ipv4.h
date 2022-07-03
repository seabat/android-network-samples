#ifndef NDK_SOCKET_LOOP_UDP_IPV4_H
#define NDK_SOCKET_LOOP_UDP_IPV4_H

#include "i-msg-listener.h"
#include "i-loop-transport.h"

class LoopUdpIpv4 : public ILoopTransport {
private:
    // variables
    IMsgListener* listener_;
    bool stopFlg_;

public:
    // constructors
    LoopUdpIpv4(IMsgListener* listener);

    // methods
    void waitMsg() override;
    void stopWait() override;
};


#endif //NDK_SOCKET_LOOP_UDP_IPV4_H
