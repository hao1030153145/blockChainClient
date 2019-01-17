package com.smartdatachain.api.integration.impl.ibatis;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.integration.DataServiceException;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.jeeframework.util.validate.Validate;
import com.smartdatachain.api.integration.WorkGroupDataService;
import com.smartdatachain.api.integration.bo.WorkGroupBO;
import com.smartdatachain.api.integration.bo.WorkGroupDetailBO;
import com.smartdatachain.api.web.filter.WorkGroupDetailFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hoalen
 * @version 1.0
 */
@Scope("prototype")
@Repository("workGroupDataService")
public class WorkGroupDAOIbatis extends BaseDaoiBATIS implements WorkGroupDataService {


    @Override
    public List<WorkGroupBO> getWorkGroupList(Map<String, Object> param) throws DataServiceException {
        try{
            return sqlSessionTemplate.selectList("workGroupMapper.getWorkGroupListByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<Integer>  getWordGroupIdByName(String string) throws BizException {
        try{
            return sqlSessionTemplate.selectList("workGroupMapper.getWordGroupIdByName", string);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer addWorkGroup(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.insert("workGroupMapper.addWorkGroup", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer deleteWorkGroupById(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.delete("workGroupMapper.deleteWorkGroupById", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateWorkGroupById(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.update("workGroupMapper.updateWorkGroupById", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer deleteWorkGroupDetailById(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.delete("workGroupMapper.deleteWorkGroupDetailById", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<WorkGroupDetailBO> getWorkGroupDetailByParam(String name, Integer collectionId, String timeCreate, String endTime, Integer size, Integer page) throws BizException {

        Map<String,Object> param = new HashMap<>();

        if (!Validate.isEmpty(name)){
            param.put("name",name);
        }
        if (!Validate.isEmpty(timeCreate)){
            param.put("timeCreate",timeCreate);
        }
        if (!Validate.isEmpty(endTime)){
            param.put("endTime",endTime);
        }
        if (null != size){
            param.put("size",size);
        }
        if (null != page){
            param.put("page",page);
        }
        param.put("collectionId",collectionId);
        try{
            return sqlSessionTemplate.selectList("workGroupMapper.getWorkGroupDetailByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getWorkGroupDetailCountByParam(String name,Integer collectionId, String timeCreate, String endTime) throws BizException {
        Map<String,Object> param = new HashMap<>();

        if (!Validate.isEmpty(name)){
            param.put("name",name);
        }
        if (!Validate.isEmpty(timeCreate)){
            param.put("timeCreate",timeCreate);
        }
        if (!Validate.isEmpty(endTime)){
            param.put("endTime",endTime);
        }
        param.put("collectionId",collectionId);

        try{
            return sqlSessionTemplate.selectOne("workGroupMapper.getWorkGroupDetailCountByParam", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateWorkGroupDetailById(WorkGroupDetailFilter workGroupDetailFilter) throws BizException {
        try{
            return sqlSessionTemplate.update("workGroupMapper.updateWorkGroupDetailById", workGroupDetailFilter);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public WorkGroupDetailBO getWorkGroupDetailById(String appId) throws BizException {
        if (!Validate.isEmpty(appId)){
            try{
                return sqlSessionTemplate.selectOne("workGroupMapper.getWorkGroupDetailById", Integer.parseInt(appId));
            }catch (DataAccessException e){
                throw new DAOException(e);
            }
        }
        return null;
    }

    @Override
    public Integer saveWorkGroupDetail(WorkGroupDetailFilter workGroupDetailFilter) throws BizException {
        try{
            return sqlSessionTemplate.insert("workGroupMapper.saveWorkGroupDetail", workGroupDetailFilter);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

}