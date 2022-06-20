#include "i-loop-transport.h"
#include "i-msg-listener.h"

#ifndef NDK_SOCKET_LOOP_TRANSPORT_FACTORY_H
#define NDK_SOCKET_LOOP_TRANSPORT_FACTORY_H

class LoopTransportFactory {
public:
    LoopTransportFactory(){}
    ~LoopTransportFactory(){}
    static ILoopTransport* create(
            std::string transportType
            , IMsgListener* listener);
};


#endif //NDK_SOCKET_LOOP_TRANSPORT_FACTORY_H
