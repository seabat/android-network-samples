#include "loop-transport-factory.h"
#include "loop-tcp-ipv4.h"

ILoopTransport* LoopTransportFactory::create(
        std::string transportType
        , IMsgListener* listener) {
    LoopTcpIpv4* loop = nullptr;
    if (transportType.compare("TCP") == 0) {
        loop = new LoopTcpIpv4(listener);
    }
    return loop;
}