// IMyAidlInterface.aidl
package com.tst.aidlserver;

// Declare any non-default types here with import statements

/*
    aidl: package.interface (as a .h file, declare mtds(in/out))
*/
interface IMyAidlInterface {
    int plus(int a, int b);
    String toUpperCase(String str);
}