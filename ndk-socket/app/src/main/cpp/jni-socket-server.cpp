#include <jni.h>
#include <string>
#include "socket/server/socket-server.h"
#include "jni-ref.h"

extern "C"
JNIEXPORT jint JNICALL
Java_dev_seabat_socket_SocketServer_runJni(JNIEnv *env, jobject thiz, jstring transportType, jobject jSocketServer) {
    const char *transportTypeChar = env->GetStringUTFChars(transportType, nullptr);
    SocketServer* server = SocketServer::createInstance(std::string(transportTypeChar), jSocketServer);
    server->run();
    env->ReleaseStringUTFChars(transportType, transportTypeChar);
    return 0;
}
extern "C"
JNIEXPORT void JNICALL
Java_dev_seabat_socket_SocketServer_closeJni(JNIEnv *env, jobject thiz) {
    SocketServer::stop();
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JniRef::createInstance(vm);
    return JNI_VERSION_1_6;
}
