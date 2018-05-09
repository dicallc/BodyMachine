package com.fliggy.bodymachine.model;

import java.util.List;

/**
 * Created by dicallc on 2018/3/16.
 */

public class MsgModel {

  /**
   * code : 0000
   * msg : insert record successful.
   * response : {"AtUserID":["1"],"BMI":["1234567892"],"BasalMeta":["1234567892"],"BoneSalt":["1234567892"],"FatFreeBodyWt":["1234567892"],"FatWt":["1234567892"],"LhandFatRate":["1234567892"],"LhandMsclVal":["1234567892"],"LlegFatRate":["1234567892"],"LlegMsclVal":["1234567892"],"MuscleWt":["1234567892"],"ObesityDegree":["1234567892"],"PBF":["1234567892"],"PBW":["1234567892"],"PhysicalAge":["1234567892"],"PhysicalScore":["1234567892"],"Protein":["1234567892"],"RecID":["1234567892"],"RecTime":["1234567892"],"RhandFatRate":["1234567892"],"RhandMsclVal":["1234567892"],"RlegFatRate":["1234567892"],"RlegMsclVal":["1234567892"],"ToatalWatWt":["1234567892"],"TrunkFatRate":["1234567892"],"TrunkMsclVal":["1234567892"],"Wt":["1234567892"],"sign":["5e70e2bfff0587a6e870eaeae92ac4c129a2695a"],"time":["1521118780"]}
   */

  public String code;
  public String msg;
  public ResponseBean response;

  public static class ResponseBean {
    public List<String> AtUserID;
    public List<String> BMI;
    public List<String> BasalMeta;
    public List<String> BoneSalt;
    public List<String> FatFreeBodyWt;
    public List<String> FatWt;
    public List<String> LhandFatRate;
    public List<String> LhandMsclVal;
    public List<String> LlegFatRate;
    public List<String> LlegMsclVal;
    public List<String> MuscleWt;
    public List<String> ObesityDegree;
    public List<String> PBF;
    public List<String> PBW;
    public List<String> PhysicalAge;
    public List<String> PhysicalScore;
    public List<String> Protein;
    public List<String> RecID;
    public List<String> RecTime;
    public List<String> RhandFatRate;
    public List<String> RhandMsclVal;
    public List<String> RlegFatRate;
    public List<String> RlegMsclVal;
    public List<String> ToatalWatWt;
    public List<String> TrunkFatRate;
    public List<String> TrunkMsclVal;
    public List<String> Wt;
    public List<String> sign;
    public List<String> time;
  }
}
