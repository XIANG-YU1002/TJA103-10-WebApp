package com.emp.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmpDAO implements EmpDAO_interface {

    // 共用一個 DataSource
    private static DataSource ds = null;
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    // SQL 語法
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
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_STMT)) {

            pstmt.setString(1, empVO.getAccount());
            pstmt.setString(2, empVO.getPassword());
            pstmt.setString(3, empVO.getName());
            pstmt.setString(4, empVO.getEmail());
            pstmt.setByte(5, empVO.getStatus());
            pstmt.setTimestamp(6, empVO.getCreateTime());
            pstmt.setTimestamp(7, empVO.getUpdatedTime());

            pstmt.executeUpdate();
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
    }

    @Override
    public void update(EmpVO empVO) {
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

            pstmt.setString(1, empVO.getAccount());
            pstmt.setString(2, empVO.getPassword());
            pstmt.setString(3, empVO.getName());
            pstmt.setString(4, empVO.getEmail());
            pstmt.setByte(5, empVO.getStatus());
            pstmt.setTimestamp(6, empVO.getCreateTime());
            pstmt.setTimestamp(7, empVO.getUpdatedTime());
            pstmt.setInt(8, empVO.getEmployeeId()); // PK

            pstmt.executeUpdate();
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
    }

    @Override
    public void delete(Integer employeeId) {
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE)) {

            pstmt.setInt(1, employeeId);
            pstmt.executeUpdate();
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
    }

    @Override
    public EmpVO findByPrimaryKey(Integer employeeId) {
        EmpVO empVO = null;
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

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
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
        return empVO;
    }

    @Override
    public List<EmpVO> getAll() {
        List<EmpVO> list = new ArrayList<>();
        try (Connection con = ds.getConnection();
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
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
        return list;
    }
}