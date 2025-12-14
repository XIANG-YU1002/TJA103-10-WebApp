package com.emp.model;

import java.sql.Timestamp;
import java.util.List;

public class EmpService {

    private EmpDAO_interface dao;

    public EmpService() {
       dao = new EmpJDBCDAO(); // 改用 JDBC 版本
    }

    
    public EmpVO addEmp(String account, String password, String name,
                        String email, Byte status, Timestamp createTime, Timestamp updatedTime) {

        EmpVO empVO = new EmpVO();

        empVO.setAccount(account);
        empVO.setPassword(password);
        empVO.setName(name);
        empVO.setEmail(email);
        empVO.setStatus(status);
        empVO.setCreateTime(createTime);
        empVO.setUpdatedTime(updatedTime);

        dao.insert(empVO);

        return empVO;
    }

    
    public EmpVO updateEmp(Integer employeeId, String account, String password, String name,
                           String email, Byte status, Timestamp createTime, Timestamp updatedTime) {

        EmpVO empVO = new EmpVO();

        empVO.setEmployeeId(employeeId); 
        empVO.setAccount(account);
        empVO.setPassword(password);
        empVO.setName(name);
        empVO.setEmail(email);
        empVO.setStatus(status);
        empVO.setCreateTime(createTime);
        empVO.setUpdatedTime(updatedTime);

        dao.update(empVO);

        return empVO;
    }

   
    public void deleteEmp(Integer employeeId) {
        dao.delete(employeeId);
    }

    
    public EmpVO getOneEmp(Integer employeeId) {
        return dao.findByPrimaryKey(employeeId);
    }

    
    public List<EmpVO> getAll() {
        return dao.getAll();
    }
}