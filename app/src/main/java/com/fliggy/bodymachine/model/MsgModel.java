package com.fliggy.bodymachine.model;

import java.util.List;

/**
 * Created by dicallc on 2018/3/16.
 */

public class MsgModel {


  public String code;
  public String msg;
  public ResponseBean response;

  public static class ResponseBean {
    public List<RecordsBean> records;

    public static class RecordsBean {
      /**
       * AtUserID : 1
       * BMI : 1234570000
       * BasalMeta : 1234570000
       * BoneSalt : 1234570000
       * FatFreeBodyWt : 1234570000
       * FatWt : 1234570000
       * LhandFatRate : 1234570000
       * LhandMsclVal : 1234570000
       * LlegFatRate : 1234570000
       * LlegMsclVal : 1234570000
       * MuscleWt : 1234570000
       * ObesityDegree : 1234570000
       * PBF : 1234570000
       * PBW : 1234570000
       * PhysicalAge : 1234570000
       * PhysicalScore : 1234570000
       * Protein : 1234570000
       * RecID : 1234567890
       * RecTime : 1234567890
       * RhandFatRate : 1234570000
       * RhandMsclVal : 1234570000
       * RlegFatRate : 1234570000
       * RlegMsclVal : 1234570000
       * ToatalWatWt : 1234570000
       * TrunkFatRateWt : 1234570000
       * TrunkMsclVal : 1234570000
       * Wt : 1234570000
       */

      public int AtUserID;
      public int BMI;
      public int BasalMeta;
      public int BoneSalt;
      public int FatFreeBodyWt;
      public int FatWt;
      public int LhandFatRate;
      public int LhandMsclVal;
      public int LlegFatRate;
      public int LlegMsclVal;
      public int MuscleWt;
      public int ObesityDegree;
      public int PBF;
      public int PBW;
      public int PhysicalAge;
      public int PhysicalScore;
      public int Protein;
      public String RecID;
      public int RecTime;
      public int RhandFatRate;
      public int RhandMsclVal;
      public int RlegFatRate;
      public int RlegMsclVal;
      public int ToatalWatWt;
      public int TrunkFatRateWt;
      public int TrunkMsclVal;
      public int Wt;
    }
  }
}
