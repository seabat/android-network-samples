#ifndef NDK_SOCKET_JNI_REF_H
#define NDK_SOCKET_JNI_REF_H

#include <jni.h>

class JniRef {
private:
    // variables
    static JniRef* ref_;
    JavaVM* vm_;
    JNIEnv* env_;

    // constructors
    JniRef(JavaVM* vm);

public:
    // methods
    static JniRef* createInstance(JavaVM* vm);
    static JniRef* getInstance();
    JavaVM* getJavaVm();
    JNIEnv* getJNIEnv();
    void setJavaVm(JavaVM* vm);
    void setJNIEnv(JNIEnv* env);
};

#endif //NDK_SOCKET_JNI_REF_H
