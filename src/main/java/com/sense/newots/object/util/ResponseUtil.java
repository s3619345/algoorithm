 package com.sense.newots.object.util;


 public class ResponseUtil
 {
   private int returnCode;
   private String returnMsg;
   private Object data;
 
   public ResponseUtil()
   {
     this.returnCode = 0;
    this.returnMsg = "执行失败";
   }
 
   public int getReturnCode() {
    return this.returnCode;
   }
 
   public void setReturnCode(int returnCode) {
   this.returnCode = returnCode;
   }
 
   public String getReturnMsg() {
   return this.returnMsg;
   }
 
   public void setReturnMsg(String returnMsg) {
  this.returnMsg = returnMsg;
   }
 
   public Object getData() {
  return this.data;
   }
 
   public void setData(Object data) {
   this.data = data;
   }
 }
