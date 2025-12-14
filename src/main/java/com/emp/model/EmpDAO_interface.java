package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
    public void insert(EmpVO empVO);
    public void update(EmpVO empVO);
    public void delete(Integer employeeId);              
    public EmpVO findByPrimaryKey(Integer employeeId);   
    public List<EmpVO> getAll();
    
    // 萬用複合查詢 (傳入參數型態 Map)(回傳 List)
    // public List<EmpVO> getAll(Map<String, String[]> map);
}