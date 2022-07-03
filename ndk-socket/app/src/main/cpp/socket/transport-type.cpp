#include "transport-type.h"

TransportType convertTransportType(std::string strType) {
    if (strType.compare("TCP") == 0) {
        return TransportType::TCP;
    } else if (strType.compare("UDP") == 0) {
        return TransportType::UDP;
    } else {
        return TransportType::UNKNOWN;
    }
}
