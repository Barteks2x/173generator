package com.github.barteks2x.b173gen.exception;

public class B173GenInitException extends RuntimeException {

    protected final String additionalInfo;

    private static String makeString(String... str) {
        StringBuilder sb = new StringBuilder();
        for(String s: str) {
            sb.append(s);
        }
        return sb.toString();
    }

    public B173GenInitException(Exception ex, String additionalInfo, String... msg) {
        super(makeString(msg), ex);
        this.additionalInfo = additionalInfo;
    }

    public B173GenInitException(String additionalInfo, String... msg) {
        super(makeString(msg));
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
