#ifndef NDK_SOCKET_TRANSPORT_TYPE_H
#define NDK_SOCKET_TRANSPORT_TYPE_H

#include <string>

enum class TransportType {
    TCP
    , UDP
    , UNKNOWN
};

extern "C"
TransportType convertTransportType(std::string strType);

#endif //NDK_SOCKET_TRANSPORT_TYPE_H
