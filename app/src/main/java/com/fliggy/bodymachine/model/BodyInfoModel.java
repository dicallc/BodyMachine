package com.fliggy.bodymachine.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dicallc on 2018/3/20 0020.
 */

public class BodyInfoModel extends RealmObject {


  @PrimaryKey
  private String id;
  public String user_id;
  public String time;
  public String height;
  public String age;
  public String sex;
  public String weight;
  public String all_impedance;
  public String left_h_impedance;
  public String right_h_impedance;
  public String left_f_impedance;
  public String right_f_impedance;
  public String body_impedance;
  public String fat_weight;
  public String muscle_weight;
  public String total_water_weight;
  public String extracellular_fluid;
  public String intracellular_Fluid;
  public String fat_free;
  public String Standard_muscle;
  public String Bone_salt_content;
  public String skeletal_muscle;
  public String protein;
  public String physique_num;
  public String basal_metabolism;
  public String Body_fat_percentage;
  public String Percentage_of_water;
  public String stander_weight;
  public String visceral_fat;
  public String physical_age;
  public String Body_score;
  public String Edematous_degree;
  public String Fat_degree;
  public String Muscle_control;
  public String Weight_control;
  public String Fat_control;
  public String no_one;
  public String Trunk_fat_rate;
  public String Right_hand_fat_rate;
  public String Left_hand_fat_ratio;
  public String Right_foot_fat_ratio;
  public String LEFT_foot_fat_ratio;
  public String Trunk_muscle_volume;
  public String Right_hand_muscle_volume;
  public String LEFT_hand_muscle_volume;
  public String Right_root_muscle_volume;
  public String LEFT_root_muscle_volume;
  public String Neck_circumference;
  public String waist;
  public String Hipline;
  public String Bust;
  public String r_t_Hipline;
  public String l_t_Hipline;
  public String r_b_Hipline;
  public String l_b_Hipline;

  public String getId() {
    return id;
  }

  public void setId(String mId) {
    id = mId;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String mHeight) {
    height = mHeight;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String mAge) {
    age = mAge;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String mSex) {
    sex = mSex;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String mWeight) {
    weight = mWeight;
  }

  public String getAll_impedance() {
    return all_impedance;
  }

  public void setAll_impedance(String mAll_impedance) {
    all_impedance = mAll_impedance;
  }

  public String getLeft_h_impedance() {
    return left_h_impedance;
  }

  public void setLeft_h_impedance(String mLeft_h_impedance) {
    left_h_impedance = mLeft_h_impedance;
  }

  public String getRight_h_impedance() {
    return right_h_impedance;
  }

  public void setRight_h_impedance(String mRight_h_impedance) {
    right_h_impedance = mRight_h_impedance;
  }

  public String getLeft_f_impedance() {
    return left_f_impedance;
  }

  public void setLeft_f_impedance(String mLeft_f_impedance) {
    left_f_impedance = mLeft_f_impedance;
  }

  public String getRight_f_impedance() {
    return right_f_impedance;
  }

  public void setRight_f_impedance(String mRight_f_impedance) {
    right_f_impedance = mRight_f_impedance;
  }

  public String getBody_impedance() {
    return body_impedance;
  }

  public void setBody_impedance(String mBody_impedance) {
    body_impedance = mBody_impedance;
  }

  public String getFat_weight() {
    return fat_weight;
  }

  public void setFat_weight(String mFat_weight) {
    fat_weight = mFat_weight;
  }

  public String getMuscle_weight() {
    return muscle_weight;
  }

  public void setMuscle_weight(String mMuscle_weight) {
    muscle_weight = mMuscle_weight;
  }

  public String getTotal_water_weight() {
    return total_water_weight;
  }

  public void setTotal_water_weight(String mTotal_water_weight) {
    total_water_weight = mTotal_water_weight;
  }

  public String getExtracellular_fluid() {
    return extracellular_fluid;
  }

  public void setExtracellular_fluid(String mExtracellular_fluid) {
    extracellular_fluid = mExtracellular_fluid;
  }

  public String getIntracellular_Fluid() {
    return intracellular_Fluid;
  }

  public void setIntracellular_Fluid(String mIntracellular_Fluid) {
    intracellular_Fluid = mIntracellular_Fluid;
  }

  public String getFat_free() {
    return fat_free;
  }

  public void setFat_free(String mFat_free) {
    fat_free = mFat_free;
  }

  public String getStandard_muscle() {
    return Standard_muscle;
  }

  public void setStandard_muscle(String mStandard_muscle) {
    Standard_muscle = mStandard_muscle;
  }

  public String getBone_salt_content() {
    return Bone_salt_content;
  }

  public void setBone_salt_content(String mBone_salt_content) {
    Bone_salt_content = mBone_salt_content;
  }

  public String getSkeletal_muscle() {
    return skeletal_muscle;
  }

  public void setSkeletal_muscle(String mSkeletal_muscle) {
    skeletal_muscle = mSkeletal_muscle;
  }

  public String getProtein() {
    return protein;
  }

  public void setProtein(String mProtein) {
    protein = mProtein;
  }

  public String getPhysique_num() {
    return physique_num;
  }

  public void setPhysique_num(String mPhysique_num) {
    physique_num = mPhysique_num;
  }

  public String getBasal_metabolism() {
    return basal_metabolism;
  }

  public void setBasal_metabolism(String mBasal_metabolism) {
    basal_metabolism = mBasal_metabolism;
  }

  public String getBody_fat_percentage() {
    return Body_fat_percentage;
  }

  public void setBody_fat_percentage(String mBody_fat_percentage) {
    Body_fat_percentage = mBody_fat_percentage;
  }

  public String getPercentage_of_water() {
    return Percentage_of_water;
  }

  public void setPercentage_of_water(String mPercentage_of_water) {
    Percentage_of_water = mPercentage_of_water;
  }

  public String getStander_weight() {
    return stander_weight;
  }

  public void setStander_weight(String mStander_weight) {
    stander_weight = mStander_weight;
  }

  public String getVisceral_fat() {
    return visceral_fat;
  }

  public void setVisceral_fat(String mVisceral_fat) {
    visceral_fat = mVisceral_fat;
  }

  public String getPhysical_age() {
    return physical_age;
  }

  public void setPhysical_age(String mPhysical_age) {
    physical_age = mPhysical_age;
  }

  public String getBody_score() {
    return Body_score;
  }

  public void setBody_score(String mBody_score) {
    Body_score = mBody_score;
  }

  public String getEdematous_degree() {
    return Edematous_degree;
  }

  public void setEdematous_degree(String mEdematous_degree) {
    Edematous_degree = mEdematous_degree;
  }

  public String getFat_degree() {
    return Fat_degree;
  }

  public void setFat_degree(String mFat_degree) {
    Fat_degree = mFat_degree;
  }

  public String getMuscle_control() {
    return Muscle_control;
  }

  public void setMuscle_control(String mMuscle_control) {
    Muscle_control = mMuscle_control;
  }

  public String getWeight_control() {
    return Weight_control;
  }

  public void setWeight_control(String mWeight_control) {
    Weight_control = mWeight_control;
  }

  public String getFat_control() {
    return Fat_control;
  }

  public void setFat_control(String mFat_control) {
    Fat_control = mFat_control;
  }

  public String getNo_one() {
    return no_one;
  }

  public void setNo_one(String mNo_one) {
    no_one = mNo_one;
  }

  public String getTrunk_fat_rate() {
    return Trunk_fat_rate;
  }

  public void setTrunk_fat_rate(String mTrunk_fat_rate) {
    Trunk_fat_rate = mTrunk_fat_rate;
  }

  public String getRight_hand_fat_rate() {
    return Right_hand_fat_rate;
  }

  public void setRight_hand_fat_rate(String mRight_hand_fat_rate) {
    Right_hand_fat_rate = mRight_hand_fat_rate;
  }

  public String getLeft_hand_fat_ratio() {
    return Left_hand_fat_ratio;
  }

  public void setLeft_hand_fat_ratio(String mLeft_hand_fat_ratio) {
    Left_hand_fat_ratio = mLeft_hand_fat_ratio;
  }

  public String getRight_foot_fat_ratio() {
    return Right_foot_fat_ratio;
  }

  public void setRight_foot_fat_ratio(String mRight_foot_fat_ratio) {
    Right_foot_fat_ratio = mRight_foot_fat_ratio;
  }

  public String getLEFT_foot_fat_ratio() {
    return LEFT_foot_fat_ratio;
  }

  public void setLEFT_foot_fat_ratio(String mLEFT_foot_fat_ratio) {
    LEFT_foot_fat_ratio = mLEFT_foot_fat_ratio;
  }

  public String getTrunk_muscle_volume() {
    return Trunk_muscle_volume;
  }

  public void setTrunk_muscle_volume(String mTrunk_muscle_volume) {
    Trunk_muscle_volume = mTrunk_muscle_volume;
  }

  public String getRight_hand_muscle_volume() {
    return Right_hand_muscle_volume;
  }

  public void setRight_hand_muscle_volume(String mRight_hand_muscle_volume) {
    Right_hand_muscle_volume = mRight_hand_muscle_volume;
  }

  public String getLEFT_hand_muscle_volume() {
    return LEFT_hand_muscle_volume;
  }

  public void setLEFT_hand_muscle_volume(String mLEFT_hand_muscle_volume) {
    LEFT_hand_muscle_volume = mLEFT_hand_muscle_volume;
  }

  public String getRight_root_muscle_volume() {
    return Right_root_muscle_volume;
  }

  public void setRight_root_muscle_volume(String mRight_root_muscle_volume) {
    Right_root_muscle_volume = mRight_root_muscle_volume;
  }

  public String getLEFT_root_muscle_volume() {
    return LEFT_root_muscle_volume;
  }

  public void setLEFT_root_muscle_volume(String mLEFT_root_muscle_volume) {
    LEFT_root_muscle_volume = mLEFT_root_muscle_volume;
  }

  public String getNeck_circumference() {
    return Neck_circumference;
  }

  public void setNeck_circumference(String mNeck_circumference) {
    Neck_circumference = mNeck_circumference;
  }

  public String getWaist() {
    return waist;
  }

  public void setWaist(String mWaist) {
    waist = mWaist;
  }

  public String getHipline() {
    return Hipline;
  }

  public void setHipline(String mHipline) {
    Hipline = mHipline;
  }

  public String getBust() {
    return Bust;
  }

  public void setBust(String mBust) {
    Bust = mBust;
  }

  public String getR_t_Hipline() {
    return r_t_Hipline;
  }

  public void setR_t_Hipline(String mR_t_Hipline) {
    r_t_Hipline = mR_t_Hipline;
  }

  public String getL_t_Hipline() {
    return l_t_Hipline;
  }

  public void setL_t_Hipline(String mL_t_Hipline) {
    l_t_Hipline = mL_t_Hipline;
  }

  public String getR_b_Hipline() {
    return r_b_Hipline;
  }

  public void setR_b_Hipline(String mR_b_Hipline) {
    r_b_Hipline = mR_b_Hipline;
  }

  public String getL_b_Hipline() {
    return l_b_Hipline;
  }

  public void setL_b_Hipline(String mL_b_Hipline) {
    l_b_Hipline = mL_b_Hipline;
  }
  public String getTime() {
    return time;
  }

  public void setTime(String mTime) {
    time = mTime;
  }
  @Override public String toString() {
    return "BodyInfoModel{"
        + "weight='"
        + weight
        + '\''
        + ", all_impedance='"
        + all_impedance
        + '\''
        + ", left_h_impedance='"
        + left_h_impedance
        + '\''
        + ", right_h_impedance='"
        + right_h_impedance
        + '\''
        + ", left_f_impedance='"
        + left_f_impedance
        + '\''
        + ", right_f_impedance='"
        + right_f_impedance
        + '\''
        + ", body_impedance='"
        + body_impedance
        + '\''
        + ", fat_weight='"
        + fat_weight
        + '\''
        + ", muscle_weight='"
        + muscle_weight
        + '\''
        + ", total_water_weight='"
        + total_water_weight
        + '\''
        + ", extracellular_fluid='"
        + extracellular_fluid
        + '\''
        + ", intracellular_Fluid='"
        + intracellular_Fluid
        + '\''
        + ", fat_free='"
        + fat_free
        + '\''
        + ", Standard_muscle='"
        + Standard_muscle
        + '\''
        + ", Bone_salt_content='"
        + Bone_salt_content
        + '\''
        + ", skeletal_muscle='"
        + skeletal_muscle
        + '\''
        + ", protein='"
        + protein
        + '\''
        + ", physique_num='"
        + physique_num
        + '\''
        + ", basal_metabolism='"
        + basal_metabolism
        + '\''
        + ", Body_fat_percentage='"
        + Body_fat_percentage
        + '\''
        + ", Percentage_of_water='"
        + Percentage_of_water
        + '\''
        + ", stander_weight='"
        + stander_weight
        + '\''
        + ", visceral_fat='"
        + visceral_fat
        + '\''
        + ", physical_age='"
        + physical_age
        + '\''
        + ", Body_score='"
        + Body_score
        + '\''
        + ", Edematous_degree='"
        + Edematous_degree
        + '\''
        + ", Fat_degree='"
        + Fat_degree
        + '\''
        + ", Muscle_control='"
        + Muscle_control
        + '\''
        + ", Weight_control='"
        + Weight_control
        + '\''
        + ", Fat_control='"
        + Fat_control
        + '\''
        + ", no_one='"
        + no_one
        + '\''
        + ", Trunk_fat_rate='"
        + Trunk_fat_rate
        + '\''
        + ", Right_hand_fat_rate='"
        + Right_hand_fat_rate
        + '\''
        + ", Left_hand_fat_ratio='"
        + Left_hand_fat_ratio
        + '\''
        + ", Right_foot_fat_ratio='"
        + Right_foot_fat_ratio
        + '\''
        + ", LEFT_foot_fat_ratio='"
        + LEFT_foot_fat_ratio
        + '\''
        + ", Trunk_muscle_volume='"
        + Trunk_muscle_volume
        + '\''
        + ", Right_hand_muscle_volume='"
        + Right_hand_muscle_volume
        + '\''
        + ", LEFT_hand_muscle_volume='"
        + LEFT_hand_muscle_volume
        + '\''
        + ", Right_root_muscle_volume='"
        + Right_root_muscle_volume
        + '\''
        + ", LEFT_root_muscle_volume='"
        + LEFT_root_muscle_volume
        + '\''
        + ", Neck_circumference='"
        + Neck_circumference
        + '\''
        + ", waist='"
        + waist
        + '\''
        + ", Hipline='"
        + Hipline
        + '\''
        + ", Bust='"
        + Bust
        + '\''
        + ", r_t_Hipline='"
        + r_t_Hipline
        + '\''
        + ", l_t_Hipline='"
        + l_t_Hipline
        + '\''
        + ", r_b_Hipline='"
        + r_b_Hipline
        + '\''
        + ", l_b_Hipline='"
        + l_b_Hipline
        + '\''
        + '}';
  }
}
