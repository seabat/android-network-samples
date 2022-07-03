#include "loop-transport-factory.h"
#include "loop-tcp-ipv4.h"
#include "loop-udp-ipv4.h"

ILoopTransport* LoopTransportFactory::create(
        TransportType transportType
        , IMsgListener* listener) {
    ILoopTransport* loop = nullptr;
    if (TransportType::TCP == transportType) {
        loop = new LoopTcpIpv4(listener);
    } else if (TransportType::UDP == transportType) {
        loop = new LoopUdpIpv4(listener);
    }
    return loop;
}