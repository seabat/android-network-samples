#include <jni.h>
#include <string>
#include "socket/client/socket-client.h"

extern "C"
JNIEXPORT jint JNICALL
Java_dev_seabat_socket_SocketClient_sendJni(
        JNIEnv *env
        , jobject thiz
        , jstring transportType
        , jobject jSocketClient
        , jstring msg) {
    const char *transportTypeChar = env->GetStringUTFChars(transportType, nullptr);
    std::shared_ptr<SocketClient> client = SocketClient::createInstance(jSocketClient);
    const char *msgChar = env->GetStringUTFChars(msg, nullptr);
    client->sendMsg(std::string(transportTypeChar), std::string(msgChar));
    env->ReleaseStringUTFChars(msg, msgChar);
    env->ReleaseStringUTFChars(transportType, transportTypeChar);
    return 0;
}