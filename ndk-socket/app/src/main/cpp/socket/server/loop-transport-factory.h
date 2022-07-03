#ifndef NDK_SOCKET_LOOP_TRANSPORT_FACTORY_H
#define NDK_SOCKET_LOOP_TRANSPORT_FACTORY_H

#include <string>
#include "i-loop-transport.h"
#include "i-msg-listener.h"
#include "transport-type.h"

class LoopTransportFactory {
public:
    // constructors
    LoopTransportFactory(){}
    ~LoopTransportFactory(){}

    // methods
    static ILoopTransport* create(TransportType transportType, IMsgListener* listener);
};


#endif //NDK_SOCKET_LOOP_TRANSPORT_FACTORY_H
