package com.emp.model;

import java.util.*;
import java.sql.*;

public class EmpJDBCDAO implements EmpDAO_interface {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/dreamhouse?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "123456";
    
    

    private static final String INSERT_STMT =
        "INSERT INTO employee (account, password, name, email, status, create_time, updated_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT =
        "SELECT employee_id, account, password, name, email, status, create_time, updated_time FROM employee ORDER BY employee_id";
    private static final String GET_ONE_STMT =
        "SELECT employee_id, account, password, name, email, status, create_time, updated_time FROM employee WHERE employee_id = ?";
    private static final String DELETE =
        "DELETE FROM employee WHERE employee_id = ?";
    private static final String UPDATE =
        "UPDATE employee SET account=?, password=?, name=?, email=?, status=?, create_time=?, updated_time=? WHERE employee_id = ?";

    @Override
    public void insert(EmpVO empVO) {
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(INSERT_STMT)) {

            Class.forName(driver);

            pstmt.setString(1, empVO.getAccount());
            pstmt.setString(2, empVO.getPassword());
            pstmt.setString(3, empVO.getName());
            pstmt.setString(4, empVO.getEmail());
            pstmt.setByte(5, empVO.getStatus());
            pstmt.setTimestamp(6, empVO.getCreateTime());
            pstmt.setTimestamp(7, empVO.getUpdatedTime());

            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
    }

    @Override
    public void update(EmpVO empVO) {
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

            Class.forName(driver);

            pstmt.setString(1, empVO.getAccount());
            pstmt.setString(2, empVO.getPassword());
            pstmt.setString(3, empVO.getName());
            pstmt.setString(4, empVO.getEmail());
            pstmt.setByte(5, empVO.getStatus());
            pstmt.setTimestamp(6, empVO.getCreateTime());
            pstmt.setTimestamp(7, empVO.getUpdatedTime());
            pstmt.setInt(8, empVO.getEmployeeId()); // PK

            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
    }

    @Override
    public void delete(Integer employeeId) {
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(DELETE)) {

            Class.forName(driver);

            pstmt.setInt(1, employeeId);
            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
    }

    @Override
    public EmpVO findByPrimaryKey(Integer employeeId) {
        EmpVO empVO = null;
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

            Class.forName(driver);

            pstmt.setInt(1, employeeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    empVO = new EmpVO();
                    empVO.setEmployeeId(rs.getInt("employee_id"));
                    empVO.setAccount(rs.getString("account"));
                    empVO.setPassword(rs.getString("password"));
                    empVO.setName(rs.getString("name"));
                    empVO.setEmail(rs.getString("email"));
                    empVO.setStatus(rs.getByte("status"));
                    empVO.setCreateTime(rs.getTimestamp("create_time"));
                    empVO.setUpdatedTime(rs.getTimestamp("updated_time"));
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
        return empVO;
    }

    @Override
    public List<EmpVO> getAll() {
        List<EmpVO> list = new ArrayList<>();
        
        try {
            Class.forName(driver);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        try (
        	 Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT);
             ResultSet rs = pstmt.executeQuery()) {


            while (rs.next()) {
                EmpVO empVO = new EmpVO();
                empVO.setEmployeeId(rs.getInt("employee_id"));
                empVO.setAccount(rs.getString("account"));
                empVO.setPassword(rs.getString("password"));
                empVO.setName(rs.getString("name"));
                empVO.setEmail(rs.getString("email"));
                empVO.setStatus(rs.getByte("status"));
                empVO.setCreateTime(rs.getTimestamp("create_time"));
                empVO.setUpdatedTime(rs.getTimestamp("updated_time"));
                list.add(empVO);
            }
        } catch (SQLException se) {
        	se.printStackTrace();
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {
        EmpJDBCDAO dao = new EmpJDBCDAO();

        // 新增
//        EmpVO empVO1 = new EmpVO();
//        empVO1.setAccount("test01");
//        empVO1.setPassword("pw01");
//        empVO1.setName("王小明");
//        empVO1.setEmail("test01@gmail.com");
//        empVO1.setStatus((byte)1);
//        empVO1.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        empVO1.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
//        dao.insert(empVO1);

        // 修改
//        EmpVO empVO2 = new EmpVO();
//        empVO2.setEmployeeId(1);
//        empVO2.setAccount("test02");
//        empVO2.setPassword("pw02");
//        empVO2.setName("李小華");
//        empVO2.setEmail("test02@gmail.com");
//        empVO2.setStatus((byte)0);
//        empVO2.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        empVO2.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
//        dao.update(empVO2);

        // 刪除
//        dao.delete(1);

        // 查詢單筆
//        EmpVO empVO3 = dao.findByPrimaryKey(1);
//        System.out.println(empVO3);

        // 查詢全部
        List<EmpVO> list = dao.getAll();
        for (EmpVO aEmp : list) {
            System.out.println(aEmp);
        }
    }
}