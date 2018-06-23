package com.fliggy.bodymachine.model;

/**
 * Created by dicallc on 2018/6/23.
 */
public class MachineModel {

  /**
   * status : ok
   * result : {"data":"第一条"}
   */

  public String status;
  public ResultBean result;

  public static class ResultBean {
    /**
     * data : 第一条
     */

    public String data;
  }
}
