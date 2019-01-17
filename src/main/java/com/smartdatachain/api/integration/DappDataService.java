package com.smartdatachain.api.integration;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.integration.DataService;
import com.smartdatachain.api.integration.bo.DappBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailsBO;
import com.smartdatachain.api.web.po.DappDataPO;
import com.smartdatachain.api.web.po.DataBasePO;

import java.util.List;
import java.util.Map;

/**
 * 钱包访问接口
 *
 * @author haolen
 * @version 1.0
 * @see
 */
public interface DappDataService extends DataService {

    /**
     * 创建dapp
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer createDapp(Map<String, Object> param) throws BizException;

    /**
     * 获得dapp列表
     *
     * @return
     * @throws BizException
     */
    List<DappBO> getDappByMacAddress(Map<String, Object> param) throws BizException;

    /**
     * 根据dapp连接查询获得dapp关联下的数据库信息列表
     *
     * @return
     * @throws BizException
     */
    List<DataBaseDetailBO> getDataBaseDetailByDappId(Integer dappId) throws BizException;

    /**
     *  根据数据库名字获取数据库id
     *
     * @param name
     * @return
     * @throws BizException
     */
    Integer getDataBaseIdByName(String name) throws BizException;

    /**
     *  根据条件获得dapp数据
     *
     * @param param
     * @return
     * @throws BizException
     */
    List<DappDataPO> getDataToDappByParam(Map<String, Object> param) throws BizException;

    /**
     *  根据条件获得dapp数据条数
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer getDataToDappCountByParam(Map<String, Object> param) throws BizException;

    /**
     * 根据dappId和数据库id获得数据库信息
     *
     * @return param
     * @throws BizException
     */
    List<DataBaseDetailsBO> getDataBaseDetailByDappIdAndDBId(Map<String, Object> param) throws BizException;

    /**
     * 根据条件查询时候有这条数据
     *
     * @return
     * @throws BizException
     */
    Integer getDataBaseIdByParam(Map<String, Object> param) throws BizException;

    /**
     * dapp与数据库关联
     *
     * @return
     * @throws BizException
     */
    Integer dappRelevantDB(Map<String, Object> param) throws BizException;

    /**
     * 根据条件修改数据库表的关联appId
     *
     * @return
     * @throws BizException
     */
    Integer updateRelevantDBStatus(Map<String, Object> param) throws BizException;

    /**
     * 根据钱包地址获得数据库列表
     *
     * @return
     * @throws BizException
     */
    List<DataBasePO> getDataBaseListByWalletAddress(Map<String, Object> param) throws BizException;

    /**
     * 根据dappId删除dapp应用
     *
     * @return
     * @throws BizException
     */
    Integer deleteDappById(Integer dappId) throws BizException;

    /**
     * 根据dappId删除dapp关联的字段为空
     *
     * @return
     * @throws BizException
     */
    Integer deleteDappRelevantByParam(Map<String, Object> param) throws BizException;

}