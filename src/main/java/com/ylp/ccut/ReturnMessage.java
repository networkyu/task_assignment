package com.ylp.ccut;

public class ReturnMessage {
    public String code;
    public String message;
    public boolean result;
    public Object data;
    public static ReturnMessage getSuccessInstance(){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.code = "001";
        returnMessage.message = "success";
        returnMessage.result = true;
        returnMessage.data = null;
        return returnMessage;
    }
    public static ReturnMessage getFailureInstance(){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.code = "900";
        returnMessage.message = "failure";
        returnMessage.result = false;
        return returnMessage;
    }
}
