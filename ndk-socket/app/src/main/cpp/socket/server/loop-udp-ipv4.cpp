#include "loop-udp-ipv4.h"
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <android/log.h>
#include <arpa/inet.h> // inet_ntop
#include <thread>
#include <errno.h>

LoopUdpIpv4::LoopUdpIpv4(IMsgListener* listener)
: listener_(listener)
, stopFlg_(false)
{
}

void LoopUdpIpv4::waitMsg() {
    int socketFd = -1;
    struct sockaddr_in addr;
    struct sockaddr_in senderAddr; // 送信側の socket 情報
    socklen_t addrSize;
    char buf[2048];
    int rcvSize;

    // ソケットの作成
    socketFd = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    if (socketFd == -1) {
        __android_log_print(ANDROID_LOG_INFO, "LoopUdpIpv4", "create socket failed[%d]", errno);
        goto exit;
    }

    // ソケットの設定
    addr.sin_family = AF_INET;
    addr.sin_port = htons(54321);
    addr.sin_addr.s_addr = INADDR_ANY;

    // 待受け条件を設定
    if (bind(socketFd, (struct sockaddr *)&addr, sizeof(addr)) != 0) {
        __android_log_print(ANDROID_LOG_INFO, "LoopUdpIpv4", "bind failed[%d]", errno);
        goto exit;
    }

    for (;;) {
        if (this->stopFlg_) {
            __android_log_print(ANDROID_LOG_INFO, "LoopUdpIpv4", "stopWait loop[%d]", errno);
            break;
        }

        memset(buf, 0, sizeof(buf));
        addrSize = sizeof(senderAddr);

        // UDP クライアントの接続要求を受け付ける
        rcvSize = recvfrom(socketFd
                , buf
                , sizeof(buf) - 1
                , 0
                , (struct sockaddr *) &senderAddr
                , &addrSize);

        std::thread sessionThread([this, senderAddr, buf]() {
            // クライアントの IPV4アドレス文字列を生成
            char ipaddrStr[INET_ADDRSTRLEN]; // 送信側のIPアドレスの文字列
            inet_ntop(AF_INET
                      , &senderAddr.sin_addr
                      , ipaddrStr
                      , sizeof(ipaddrStr));
            __android_log_print(ANDROID_LOG_INFO
                      , "LoopUdpIpv4", "connection from %s : %d"
                      , ipaddrStr
                      , ntohs(senderAddr.sin_port));

            // 上位に通知
            this->listener_->callback(std::string(buf));

            return;
        });
        sessionThread.detach();

    } // for

exit:
    // ソケットをクローズ
    if (socketFd != -1) {
        close(socketFd);
    }

    return;
}

void LoopUdpIpv4::stopWait() {
    this->stopFlg_ = true;
}