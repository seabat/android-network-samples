#include "client-udp-ipv4.h"
#include <stdio.h>
#include <string>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <android/log.h>
#include <errno.h>

ClientUdpIpv4::ClientUdpIpv4(IMsgListener* listener)
        : listener_(listener)
{
}

int ClientUdpIpv4::sendMsg(std::string msg) {
    struct sockaddr_in addr;
    int sock = -1;
    int sendSize = 0;

    sock = socket(AF_INET, SOCK_DGRAM, 0);
    addr.sin_family = AF_INET;
    addr.sin_port = htons(54321);
    inet_pton(AF_INET, "127.0.0.1", &addr.sin_addr);
      //[NOTE] inet_pton(, , &addr.sin_addr.s_addr) ではないの注意

    char buf[2048] = {0};
    memcpy(buf, msg.c_str(), msg.size());
    // サーバへデータを送信
    sendSize = sendto(sock
            , msg.c_str()
            , msg.size() + 1 // NULL 終端の1バイトを足す
            , 0 // flags
            , (struct sockaddr*)&addr
            , sizeof (addr));

    __android_log_print(ANDROID_LOG_INFO
            , "ClientUdpIpv4"
            , "size : %d\n"
            , sendSize);

    this->listener_->callback("");

exit:
    if (sock != -1) {
        close(sock);
    }
    return sendSize;
}
