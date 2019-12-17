package com.y1ang.service;

import com.y1ang.entity.ProfitAndLoss;

/**
 * @author y1ang
 * @Data 2018.10.24 19:18
 * @Description
 */
public interface ICountService {

    /**
     * 获取盈亏账单
     *
     * @param batchNumber
     * @return
     */
    public ProfitAndLoss getProfitAndLoss(int batchNumber);

    /**
     * 获取已支付的饲料包数
     *
     * @param batchNumber
     * @return
     */
    public int getPaidFeedAmount(int batchNumber);

    /**
     * 获取未支付的饲料包数
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
     * 获取已支付饲料金额
     *
     * @param batchNumber
     * @return
     */
    public double getPaidMoney(int batchNumber);

    /**
     * 获取未支付饲料金额
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
     * 查询购买小羊账单
     *
     * @param batchNumber
     * @return
     */
    public double getPigletMoney(int batchNumber);

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
