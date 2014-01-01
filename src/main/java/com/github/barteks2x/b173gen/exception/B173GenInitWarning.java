package com.github.barteks2x.b173gen.exception;

public class B173GenInitWarning {
    private final String additionalInfo, msg;

    public B173GenInitWarning(String additionalInfo, String... msg) {
        this.additionalInfo = additionalInfo;
        if(msg == null || msg.length == 0) {
            this.msg = "";
            return;
        }
        StringBuilder sb = new StringBuilder(msg[0]);
        for(int i = 1; i < msg.length; ++i) {
            sb.append(msg[i]);
        }
        this.msg = sb.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[").append(additionalInfo).append("]");
        sb.append(" ").append(msg);
        return sb.toString();
    }
}
