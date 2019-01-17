package com.smartdatachain.api.integration.impl.ibatis;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.smartdatachain.api.integration.DappDataService;
import com.smartdatachain.api.integration.bo.DappBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailsBO;
import com.smartdatachain.api.web.po.DappDataPO;
import com.smartdatachain.api.web.po.DataBasePO;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author hoalen
 * @version 1.0
 */
@Scope("prototype")
@Repository("dappDataService")
public class DappDAOIbatis extends BaseDaoiBATIS implements DappDataService {


    @Override
    public Integer createDapp(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.insert("dappMapper.createDapp", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<DappBO> getDappByMacAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectList("dappMapper.getDappByMacAddress",param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<DataBaseDetailBO> getDataBaseDetailByDappId(Integer dappId) throws BizException {
        try{
            return sqlSessionTemplate.selectList("dappMapper.getDataBaseDetailByDappId", dappId);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getDataBaseIdByName(String name) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("dappMapper.getDataBaseIdByName", name);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<DappDataPO> getDataToDappByParam(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectList("dappMapper.getDataToDappByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getDataToDappCountByParam(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("dappMapper.getDataToDappCountByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getDataBaseIdByParam(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("dappMapper.getDataBaseIdByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<DataBaseDetailsBO> getDataBaseDetailByDappIdAndDBId(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectList("dappMapper.getDataBaseDetailByDappIdAndDBId", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer dappRelevantDB(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.insert("dappMapper.dappRelevantDB", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateRelevantDBStatus(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.update("dappMapper.updateRelevantDBStatus", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<DataBasePO> getDataBaseListByWalletAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectList("dappMapper.getDataBaseListByWalletAddress", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer deleteDappById(Integer dappId) throws BizException {
        try{
            return sqlSessionTemplate.delete("dappMapper.deleteDappById", dappId);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer deleteDappRelevantByParam(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.delete("dappMapper.deleteDappRelevantByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }
}