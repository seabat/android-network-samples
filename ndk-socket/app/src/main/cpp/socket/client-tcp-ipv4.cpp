#include "client-tcp-ipv4.h"
#include <stdio.h>
#include <string>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <android/log.h>
#include <errno.h>

ClientTcpIpv4::ClientTcpIpv4(IMsgListener* listener)
        : listener_(listener)
{
}

int ClientTcpIpv4::send() {
    struct sockaddr_in dest;
    int sock = -1;

    sock = socket(AF_INET, SOCK_STREAM, 0);
    dest.sin_family = AF_INET;
    dest.sin_port = htons(54321);
    inet_pton(AF_INET, "127.0.0.1", &dest.sin_addr.s_addr);
    char writeBuf[32] = {0};
    int writeSize = 0;
    char readBuf[32] = {0};
    int readSize = 0;

    if (connect(sock, (struct sockaddr*)&dest, sizeof(dest)) != 0) {
        __android_log_print(ANDROID_LOG_INFO, "ClientTcpIpv4", "connect failed[%d]", errno);
        goto exit;
    }

    // サーバへデータを送信
    snprintf(writeBuf, sizeof(writeBuf), "message from IPv4 client");
    writeSize = write(sock, writeBuf, strnlen(writeBuf, sizeof(writeBuf)));

    //サーバからデータを受信
    readSize = read(sock, readBuf, sizeof(readBuf));
    __android_log_print(ANDROID_LOG_INFO
            , "ClientTcpIpv4"
            , "size : %d\nmessage : %s\n"
            , readSize
            , readBuf);
    this->listener_->callback( std::string(readBuf));

exit:
    if (sock != -1) {
        close(sock);
    }
    return readSize;
}
