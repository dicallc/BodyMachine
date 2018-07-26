package com.fliggy.bodymachine.utils;

import android.os.Looper;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.socks.library.KLog;
import io.realm.Realm;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by dicallc on 2018/3/16.
 */

public class Utils {
  public static String sha1(String data) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA1");
    md.update(data.getBytes());
    StringBuffer buf = new StringBuffer();
    byte[] bits = md.digest();
    for (int i = 0; i < bits.length; i++) {
      int a = bits[i];
      if (a < 0) a += 256;
      if (a < 16) buf.append("0");
      buf.append(Integer.toHexString(a));
    }
    return buf.toString();
  }
  public static String getSign(BodyInfoModel mBodyInfoModel, String mMache_id){
    String authKey="zp0adsxvtp1oeiw1k7isd3tz9pft";
    LinkedHashMap<String,String> mMap=new LinkedHashMap<>();
    mMap.put("AtUserID",mBodyInfoModel.getId());
    mMap.put("BMI",mBodyInfoModel.getPhysique_num());
    mMap.put("BasalMeta",mBodyInfoModel.getBasal_metabolism());
    mMap.put("BoneSalt",mBodyInfoModel.getBone_salt_content());
    mMap.put("FatFreeBodyWt",mBodyInfoModel.getFat_free());
    mMap.put("FatWt",mBodyInfoModel.getFat_weight());
    mMap.put("LhandFatRate",mBodyInfoModel.getLeft_hand_fat_ratio());
    mMap.put("LhandMsclVal",mBodyInfoModel.getLEFT_hand_muscle_volume());
    mMap.put("LlegFatRate",mBodyInfoModel.getLEFT_foot_fat_ratio());
    mMap.put("LlegMsclVal",mBodyInfoModel.getLEFT_root_muscle_volume());
    mMap.put("MuscleWt",mBodyInfoModel.getMuscle_weight());
    mMap.put("ObesityDegree",mBodyInfoModel.getFat_degree());
    mMap.put("PBF",mBodyInfoModel.getBody_fat_percentage());
    mMap.put("PBW",mBodyInfoModel.getPercentage_of_water());
    mMap.put("PhysicalAge",mBodyInfoModel.getPhysical_age());
    mMap.put("PhysicalScore",mBodyInfoModel.getBody_score());

    mMap.put("Protein",mBodyInfoModel.getProtein());
    mMap.put("RecID",mBodyInfoModel.getId()+"@"+mMache_id);
    mMap.put("RecTime",mBodyInfoModel.getTime());
    mMap.put("RhandFatRate",mBodyInfoModel.getRight_hand_fat_rate());
    mMap.put("RhandMsclVal",mBodyInfoModel.getRight_hand_muscle_volume());
    mMap.put("RlegFatRate",mBodyInfoModel.getRight_foot_fat_ratio());
    mMap.put("RlegMsclVal",mBodyInfoModel.getRight_root_muscle_volume());

    mMap.put("SktMuscleWt",mBodyInfoModel.getSkeletal_muscle());
    mMap.put("ToatalWatWt",mBodyInfoModel.getTotal_water_weight());
    mMap.put("TrunkFatRate",mBodyInfoModel.getTrunk_fat_rate());
    mMap.put("TrunkMsclVal",mBodyInfoModel.getTrunk_muscle_volume());

    mMap.put("ViscAdiGrd",mBodyInfoModel.getVisceral_fat());
    mMap.put("Wt",mBodyInfoModel.getWeight());
    mMap.put("time",System.currentTimeMillis()+"");
    StringBuffer mBuffer=new StringBuffer();
    for (String key : mMap.keySet()) {
      mBuffer.append(key+"="+mMap.get(key)+"&");
    }
    String str=mBuffer.toString().substring(0,mBuffer.length()-1);
    String finalStr=authKey+str+authKey;
    String mS = null;
    try {
      mS =sha1(finalStr);
    } catch (NoSuchAlgorithmException mE) {
      mE.printStackTrace();
    }
    String params = str + "&sign=" + mS;
    return params;
  }
  //public static String getTestSign(){
  //  //AtUserID=1&BMI=1234567892&BasalMeta=1234567892&BoneSalt=1234567892&FatFreeBodyWt=1234567892&FatWt=1234567892&LhandFatRate=1234567892&LhandMsclVal=1234567892&
  //   String authKey="zp0adsxvtp1oeiw1k7isd3tz9pft";
  //  LinkedHashMap<String,String> mMap=new LinkedHashMap<>();
  //  mMap.put("AtUserID","111");
  //  mMap.put("BMI","1234567892");
  //  mMap.put("BasalMeta","1234567892");
  //  mMap.put("BoneSalt","1234567892");
  //  mMap.put("FatFreeBodyWt","1234567892");
  //  mMap.put("FatWt","1234567892");
  //  mMap.put("LhandFatRate","1234567892");
  //  mMap.put("LhandMsclVal","1234567892");
  //  mMap.put("LlegFatRate","1234567892");
  //  mMap.put("LlegMsclVal","1234567892");
  //  mMap.put("MuscleWt","1234567892");
  //  mMap.put("ObesityDegree","1234567892");
  //  mMap.put("PBF","1234567892");
  //  mMap.put("PBW","1234567892");
  //  mMap.put("PhysicalAge","1234567892");
  //  mMap.put("PhysicalScore","1234567892");
  //
  //  mMap.put("Protein","1234567892");
  //  mMap.put("RecID","111");
  //  mMap.put("RecTime","1234567892");
  //  mMap.put("RhandFatRate","1234567892");
  //  mMap.put("RhandMsclVal","1234567892");
  //  mMap.put("RlegFatRate","1234567892");
  //  mMap.put("RlegMsclVal","1234567892");
  //  //SktMuscleWt
  //  mMap.put("SktMuscleWt","1234567892");
  //  mMap.put("ToatalWatWt","1234567892");
  //  mMap.put("TrunkFatRate","1234567892");
  //  mMap.put("TrunkMsclVal","1234567892");
  //  //skeletal_muscle
  //  mMap.put("ViscAdiGrd","1234567892");
  //  mMap.put("Wt","1234567892");
  //  mMap.put("time",System.currentTimeMillis()+"");
  //
  //
  //
  //
  //
  //  StringBuffer mBuffer=new StringBuffer();
  //  for (String key : mMap.keySet()) {
  //    mBuffer.append(key+"="+mMap.get(key)+"&");
  //  }
  //  String str=mBuffer.toString().substring(0,mBuffer.length()-1);
  //  String finalStr=authKey+str+authKey;
  //  String mS = null;
  //  try {
  //    mS =sha1(finalStr);
  //  } catch (NoSuchAlgorithmException mE) {
  //    mE.printStackTrace();
  //  }
  //  String params = str + "&sign=" + mS;
  //  return params;
  //}

  //public static BodyInfoModel toShowFinalResultModel(String mS) {
  //  if (Looper.myLooper() == Looper.getMainLooper()) {
  //    KLog.e("UI");
  //  }
  //  Realm realm = Realm.getDefaultInstance();
  //  realm.beginTransaction();
  //  BodyInfoModel mModel = realm.createObject(BodyInfoModel.class);
  //  //BodyInfoModel mModel = new BodyInfoModel();
  //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
  //  Date curDate = new Date(System.currentTimeMillis());//获取当前时间
  //  String time = formatter.format(curDate);
  //  mModel.setTime(time);
  //  mModel.weight = android.serialport.utils.Utils.toResultHasPoint(mS, 6, 10);
  //  //总阻抗
  //  mModel.all_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 10, 14);
  //  mModel.left_h_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 14, 18);
  //  mModel.right_h_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 18, 22);
  //  mModel.left_f_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 22, 26);
  //  mModel.right_f_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 26, 30);
  //  mModel.body_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 30, 34);
  //  //脂肪重
  //  mModel.fat_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 34, 38);
  //  //肌肉重
  //  mModel.muscle_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 38, 42);
  //  //总水重
  //  mModel.total_water_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 42, 46);
  //  //细胞外液
  //  mModel.extracellular_fluid = android.serialport.utils.Utils.toResultHasPoint(mS, 46, 50);
  //  //细胞内液
  //  mModel.intracellular_Fluid = android.serialport.utils.Utils.toResultHasPoint(mS, 50, 54);
  //  //去脂体重
  //  mModel.fat_free = android.serialport.utils.Utils.toResultHasPoint(mS, 54, 58);
  //  //标准肌肉
  //  mModel.Standard_muscle = android.serialport.utils.Utils.toResultHasPoint(mS, 58, 62);
  //  //骨盐量
  //  mModel.Bone_salt_content = android.serialport.utils.Utils.toResultHasPoint(mS, 62, 66);
  //  //骨骼肌重
  //  mModel.skeletal_muscle = android.serialport.utils.Utils.toResultHasPoint(mS, 66, 70);
  //  //蛋白质
  //  mModel.protein = android.serialport.utils.Utils.toResultHasPoint(mS, 70, 74);
  //  //体质指数
  //  mModel.physique_num = android.serialport.utils.Utils.toResultHasPoint(mS, 74, 78);
  //  //基础代谢
  //  mModel.basal_metabolism = android.serialport.utils.Utils.toResultHasPoint(mS, 78, 82);
  //  //体脂百分比
  //  mModel.Body_fat_percentage = android.serialport.utils.Utils.toResultHasPoint(mS, 82, 86);
  //  //含水百分比
  //  mModel.Percentage_of_water = android.serialport.utils.Utils.toResultHasPoint(mS, 86, 90);
  //  //标准体重
  //  mModel.stander_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 90, 94);
  //  //内脏脂肪等级
  //  mModel.visceral_fat = android.serialport.utils.Utils.toResultHasPoint(mS, 94, 98);
  //  //身体年龄
  //  mModel.physical_age = android.serialport.utils.Utils.toResultHasPoint(mS, 98, 100);
  //  //身体评分
  //  mModel.Body_score = android.serialport.utils.Utils.toResultHasPoint(mS, 100, 102);
  //  //水肿度
  //  mModel.Edematous_degree = android.serialport.utils.Utils.toResultHasPoint(mS, 102, 106);
  //  //肥胖度
  //  mModel.Fat_degree = android.serialport.utils.Utils.toResultHasPoint(mS, 106, 110);
  //  //肌肉控制
  //  mModel.Muscle_control = android.serialport.utils.Utils.toResultHasPoint(mS, 110, 114);
  //  //体重控制
  //  mModel.Weight_control = android.serialport.utils.Utils.toResultHasPoint(mS, 114, 118);
  //  //脂肪控制
  //  mModel.Fat_control = android.serialport.utils.Utils.toResultHasPoint(mS, 118, 122);
  //  //no
  //  mModel.no_one = android.serialport.utils.Utils.toResultHasPoint(mS, 122, 126);
  //  //躯干脂肪率
  //  mModel.Trunk_fat_rate = android.serialport.utils.Utils.toResultHasPoint(mS, 126, 130);
  //  //右手脂肪率
  //  mModel.Right_hand_fat_rate = android.serialport.utils.Utils.toResultHasPoint(mS, 130, 134);
  //  //左手脂肪率
  //  mModel.Left_hand_fat_ratio = android.serialport.utils.Utils.toResultHasPoint(mS, 134, 138);
  //  //右脚脂肪率
  //  mModel.Right_foot_fat_ratio = android.serialport.utils.Utils.toResultHasPoint(mS, 138, 142);
  //  //左脚脂肪率
  //  mModel.LEFT_foot_fat_ratio = android.serialport.utils.Utils.toResultHasPoint(mS, 142, 146);
  //  //躯干肌肉量
  //  mModel.Trunk_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 146, 150);
  //  //右手肌肉量
  //  mModel.Right_hand_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 150, 154);
  //  //左手肌肉
  //  mModel.LEFT_hand_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 154, 158);
  //  //右脚肌肉
  //  mModel.Right_root_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 158, 162);
  //  //左脚肌肉
  //  mModel.LEFT_root_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 162, 166);
  //  //颈围
  //  mModel.Neck_circumference = android.serialport.utils.Utils.toResultHasPoint(mS, 166, 170);
  //  //腰围
  //  mModel.waist = android.serialport.utils.Utils.toResultHasPoint(mS, 170, 174);
  //  //臀围
  //  mModel.Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 174, 178);
  //  //胸围
  //  mModel.Bust = android.serialport.utils.Utils.toResultHasPoint(mS, 178, 182);
  //  //右上臂围
  //  mModel.r_t_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 182, 186);
  //  //左上臂围
  //  mModel.l_t_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 186, 190);
  //  //右大腿围
  //  mModel.r_b_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 190, 194);
  //  //左大腿围
  //  mModel.l_b_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 194, 198);
  //  //保存数据
  //  realm.commitTransaction();
  //  return mModel;
  //}

  public static BodyInfoModel toShowFinalResultModel(String mHeight, String mAge, String mSex,
      String mS) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      KLog.e("UI");
    }
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    BodyInfoModel mModel = realm.createObject(BodyInfoModel.class, UUID.randomUUID().toString());
    mModel.getId();
    mModel.setTime(System.currentTimeMillis()+"");
    mModel.setAge(mAge);
    mModel.setHeight(mHeight);
    mModel.setSex(mSex);
    mModel.weight = android.serialport.utils.Utils.toResultHasPoint(mS, 6, 10);
    //总阻抗
    mModel.all_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 10, 14);
    mModel.left_h_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 14, 18);
    mModel.right_h_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 18, 22);
    mModel.left_f_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 22, 26);
    mModel.right_f_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 26, 30);
    mModel.body_impedance = android.serialport.utils.Utils.toResultHasPoint(mS, 30, 34);

    //肌肉重
    mModel.muscle_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 38, 42);
    //总水重
    mModel.total_water_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 42, 46);
    //细胞外液
    mModel.extracellular_fluid = android.serialport.utils.Utils.toResultHasPoint(mS, 46, 50);
    //细胞内液
    mModel.intracellular_Fluid = android.serialport.utils.Utils.toResultHasPoint(mS, 50, 54);
    //去脂体重
    mModel.fat_free = android.serialport.utils.Utils.toResultHasPoint(mS, 54, 58);
    //标准肌肉
    mModel.Standard_muscle = android.serialport.utils.Utils.toResultHasPoint(mS, 58, 62);
    //骨盐量
    mModel.Bone_salt_content = android.serialport.utils.Utils.toResultHasPoint(mS, 62, 66);
    //骨骼肌重
    mModel.skeletal_muscle = android.serialport.utils.Utils.toResultHasPoint(mS, 66, 70);
    //蛋白质
    mModel.protein = android.serialport.utils.Utils.toResultHasPoint(mS, 70, 74);
    //体质指数
    mModel.physique_num = "19.5";
    //基础代谢
    mModel.basal_metabolism = android.serialport.utils.Utils.toResultHasPoint(mS, 78, 82);
    //体脂百分比
    mModel.Body_fat_percentage = android.serialport.utils.Utils.toResultHasPoint(mS, 82, 86);
    //脂肪重
    mModel.fat_weight = Arith.div(mModel.weight+"",mModel.Body_fat_percentage+"")+"";
    //含水百分比
    mModel.Percentage_of_water = android.serialport.utils.Utils.toResultHasPoint(mS, 86, 90);
    //标准体重
    mModel.stander_weight = android.serialport.utils.Utils.toResultHasPoint(mS, 90, 94);
    //内脏脂肪等级
    mModel.visceral_fat = android.serialport.utils.Utils.toResultHasPoint(mS, 94, 98);
    //身体年龄
    mModel.physical_age = android.serialport.utils.Utils.toResultHasPoint(mS, 98, 100);
    //身体评分
    mModel.Body_score = android.serialport.utils.Utils.toResultHasPoint(mS, 100, 102);
    //水肿度
    mModel.Edematous_degree = android.serialport.utils.Utils.toResultHasPoint(mS, 102, 106);
    //肥胖度
    mModel.Fat_degree = android.serialport.utils.Utils.toResultHasPoint(mS, 106, 110);
    //肌肉控制
    mModel.Muscle_control = android.serialport.utils.Utils.toResultHasPoint(mS, 110, 114);
    //体重控制
    mModel.Weight_control = android.serialport.utils.Utils.toResultHasPoint(mS, 114, 118);
    //脂肪控制
    mModel.Fat_control = android.serialport.utils.Utils.toResultHasPoint(mS, 118, 122);
    //no
    mModel.no_one = android.serialport.utils.Utils.toResultHasPoint(mS, 122, 126);
    //躯干脂肪率
    mModel.Trunk_fat_rate = android.serialport.utils.Utils.toResultHasPoint(mS, 126, 130);
    //右手脂肪率
    mModel.Right_hand_fat_rate = android.serialport.utils.Utils.toResultHasPoint(mS, 130, 134);
    //左手脂肪率
    mModel.Left_hand_fat_ratio = android.serialport.utils.Utils.toResultHasPoint(mS, 134, 138);
    //右脚脂肪率
    mModel.Right_foot_fat_ratio = android.serialport.utils.Utils.toResultHasPoint(mS, 138, 142);
    //左脚脂肪率
    mModel.LEFT_foot_fat_ratio = android.serialport.utils.Utils.toResultHasPoint(mS, 142, 146);
    //躯干肌肉量
    mModel.Trunk_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 146, 150);
    //右手肌肉量
    mModel.Right_hand_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 150, 154);
    //左手肌肉
    mModel.LEFT_hand_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 154, 158);
    //右脚肌肉
    mModel.Right_root_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 158, 162);
    //左脚肌肉
    mModel.LEFT_root_muscle_volume = android.serialport.utils.Utils.toResultHasPoint(mS, 162, 166);
    //颈围
    mModel.Neck_circumference = android.serialport.utils.Utils.toResultHasPoint(mS, 166, 170);
    //腰围
    mModel.waist = android.serialport.utils.Utils.toResultHasPoint(mS, 170, 174);
    //臀围
    mModel.Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 174, 178);
    //胸围
    mModel.Bust = android.serialport.utils.Utils.toResultHasPoint(mS, 178, 182);
    //右上臂围
    mModel.r_t_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 182, 186);
    //左上臂围
    mModel.l_t_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 186, 190);
    //右大腿围
    mModel.r_b_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 190, 194);
    //左大腿围
    mModel.l_b_Hipline = android.serialport.utils.Utils.toResultHasPoint(mS, 194, 198);
    //保存数据
    realm.commitTransaction();
    mModel.getTime();
    mModel.getAge();
    mModel.getHeight();
    mModel.getWeight();
    return mModel;
  }
}
