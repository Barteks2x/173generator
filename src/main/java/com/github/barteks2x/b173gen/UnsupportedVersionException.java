package com.github.barteks2x.b173gen;

public class UnsupportedVersionException extends RuntimeException {

    public UnsupportedVersionException(String version) {
        super("UnsupportedVersion: " + version);
    }

}
