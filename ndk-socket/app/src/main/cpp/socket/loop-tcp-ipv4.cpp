#include "loop-tcp-ipv4.h"
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

LoopTcpIpv4::LoopTcpIpv4(IMsgListener* listener)
: listener_(listener)
, stopFlg_(false)
{
}

void LoopTcpIpv4::run() {
    int socketFd = -1;
    struct sockaddr_in addr;
    struct sockaddr_in client;
    socklen_t len;

    // ソケットの作成
    socketFd = socket(AF_INET, SOCK_STREAM, 0);
    if (socketFd == -1) {
        __android_log_print(ANDROID_LOG_INFO, "LoopTcpIpv4", "create socket failed[%d]", errno);
        goto exit;
    }

    // ソケットの設定
    addr.sin_family = AF_INET;
    addr.sin_port = htons(54321);
    addr.sin_addr.s_addr = INADDR_ANY;

    // 待受け条件を設定
    if (bind(socketFd, (struct sockaddr *)&addr, sizeof(addr)) != 0) {
        __android_log_print(ANDROID_LOG_INFO, "LoopTcpIpv4", "bind failed[%d]", errno);
        goto exit;
    }

    // TCP クライアントからの待受を開始
    if (listen(socketFd, 5) != 0){
        __android_log_print(ANDROID_LOG_INFO, "LoopTcpIpv4", "listen failed[%d]", errno);
        goto exit;
    }

    for (;;) {
        if (this->stopFlg_) {
            __android_log_print(ANDROID_LOG_INFO, "LoopTcpIpv4", "stop loop[%d]", errno);
            goto exit;
        }

        // TCP クライアントの接続要求を受け付ける
        int acceptedSocketFd;
        len = sizeof(client);
        acceptedSocketFd = accept(socketFd, (struct sockaddr *) &client, &len);

        std::thread sessionThread([this, acceptedSocketFd, client]() {
            // クライアントの IPV4アドレス文字列を生成
            char addressString[INET_ADDRSTRLEN]; // IPアドレスの文字列
            inet_ntop(AF_INET, &client.sin_addr, addressString, sizeof(addressString));
            __android_log_print(ANDROID_LOG_INFO, "LoopTcpIpv4", "connection from %s : %d",
                                addressString, ntohs(client.sin_port));

            // クライアントからメッセージを取得
            int readSize = 0;
            char readBuf[32] = {0};
            readSize = read(acceptedSocketFd, readBuf, sizeof(readBuf));
            __android_log_print(ANDROID_LOG_INFO
                                , "LoopTcpIpv4"
                                , "size : %d\nmessage : %s\n"
                                , readSize
                                , readBuf);

            // 上位に通知
            this->listener_->callback(std::string(readBuf));

            // クライアントに返信
            int writeSize = 0;
            char writeBuf[32] = {0};
            snprintf(writeBuf, sizeof(writeBuf), "message from IPv4 server");
            writeSize = write(acceptedSocketFd, writeBuf, strnlen(writeBuf, sizeof(writeBuf)));

            // fd をクローズ
            close(acceptedSocketFd);
            return;
        });
        sessionThread.detach();
    }

exit:
    // ソケットをクローズ
    if (socketFd != -1) {
        close(socketFd);
    }
}

void LoopTcpIpv4::stop() {
    this->stopFlg_ = true;
}