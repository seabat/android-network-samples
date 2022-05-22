#include "loop-tcp-ipv4.h"

LoopTcpIpv4::LoopTcpIpv4(IMsgListener* listener)
: listener_(listener)
{
}

void LoopTcpIpv4::run() {
//    for(;;) {
//
//    }
    this->listener_->callback("Hello");
}