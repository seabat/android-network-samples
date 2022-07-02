#include "loop-transport-factory.h"
#include "loop-tcp-ipv4.h"
#include "loop-udp-ipv4.h"

ILoopTransport* LoopTransportFactory::create(
        std::string transportType
        , IMsgListener* listener) {
    ILoopTransport* loop = nullptr;
    if (transportType.compare("TCP") == 0) {
        loop = new LoopTcpIpv4(listener);
    } else if (transportType.compare("UDP") == 0) {
        loop = new LoopUdpIpv4(listener);
    }
    return loop;
}