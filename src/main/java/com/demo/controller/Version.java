package com.demo.controller;

public class Version implements Comparable<Version>{
    public boolean isSnapshot(){
        return false;
    }
    public Version(String v){}
    @Override
    public int compareTo(Version o) {
        return 0;
    }
}
