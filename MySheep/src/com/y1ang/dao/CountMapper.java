package com.y1ang.dao;

import com.y1ang.entity.ProfitAndLoss;

/**
 * @author y1ang
 * @Data 2018.10.19 18:00
 * @Description 统计结果接口类
 */
public interface CountMapper {

    /**
     * 获取盈亏账单
     *
     * @param batchNumber
     * @return
     */
    public ProfitAndLoss getProfitAndLoss(int batchNumber);

    /**
     * 获取已付的饲料包数
     *
     * @param batchNumber
     * @return
     */
    public int getPaidFeedAmount(int batchNumber);

    /**
     * 获取未付的饲料包数
     *
     * @param batchNumber
     * @return
     */
    public int getUnPaidFeedAmount(int batchNumber);

    /**
     * 获取合计饲料数目
     *
     * @param batchNumber
     * @return
     */
    public int getAllFeedAmount(int batchNumber);

    /**
     * 获取已付饲料金额
     *
     * @param batchNumber
     * @return
     */
    public double getPaidMoney(int batchNumber);

    /**
     * 获取未付饲料金额
     *
     * @param batchNumber
     * @return
     */
    public double getUnPaidMoney(int batchNumber);

    /**
     * 查询饲料总开支
     *
     * @param batchNumber
     * @return
     */
    public double getFeedMoney(int batchNumber);

    /**
     * 查询饲料总欠款
     *
     * @param batchNumber
     * @return
     */
    public double getFeedArrears(int batchNumber);

    /**
     * 查询购买小羊账单
     *
     * @param batchNumber
     * @return
     */
    public double getPigletMoney(int batchNumber);

    /**
     * 查询购买小羊欠款
     *
     * @param batchNumber
     * @return
     */
    public double getPigletArrears(int batchNumber);

    /**
     * 查询购买小羊账单
     *
     * @param batchNumber
     * @return
     */
    public int getPigletCount(int batchNumber);

    /**
     * 查询卖羊账单统计
     *
     * @param batchNumber
     * @return
     */
    public double getSaleMoney(int batchNumber);

    /**
     * 查询卖羊账单统计
     *
     * @param batchNumber
     * @return
     */
    public double getSaleArrears(int batchNumber);

    /**
     * 查询卖羊账单统计
     *
     * @param batchNumber
     * @return
     */
    public int getSaleCount(int batchNumber);

    /**
     * 查询疫苗统计账单合计
     *
     * @param batchNumber
     * @return
     */
    public double getVaccineMoney(int batchNumber);

    /**
     * 查询其他账单合计
     *
     * @param batchNumber
     * @return
     */
    public double getOtherMoney(int batchNumber);

}
