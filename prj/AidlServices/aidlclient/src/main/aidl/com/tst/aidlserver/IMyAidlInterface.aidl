// IMyAidlInterface.aidl
package com.tst.aidlserver;

// Declare any non-default types here with import statements
// new a aidl file
interface IMyAidlInterface {
    int plus(int a, int b);
    String toUpperCase(String str);
    String clientExistedOnly(String str); // clinet aidl existed the mtd only;
}