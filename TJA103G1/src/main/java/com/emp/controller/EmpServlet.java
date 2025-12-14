package com.emp.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.emp.model.*;

public class EmpServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("getOne_For_Display".equals(action)) { 
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
            String str = req.getParameter("employee_id");
            if (str == null || str.trim().length() == 0) {
                errorMsgs.add("請輸入員工編號");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            Integer employeeId = null;
            try {
                employeeId = Integer.valueOf(str);
            } catch (Exception e) {
                errorMsgs.add("員工編號格式不正確");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
                failureView.forward(req, res);
                return;//程式中斷
            }

            /***************************2.開始查詢資料*****************************************/
            EmpService empSvc = new EmpService();
            EmpVO empVO = empSvc.getOneEmp(employeeId);
            if (empVO == null) {
                errorMsgs.add("查無資料");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
                failureView.forward(req, res);
                return;//程式中斷
            }

            /***************************3.查詢完成,準備轉交(Send the Success view)*************/
            req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
            String url = "/emp/listOneEmp.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("getOne_For_Update".equals(action)) { 
        	/***************************1.接收請求參數****************************************/
            Integer employeeId = Integer.valueOf(req.getParameter("employee_id"));

            /***************************2.開始查詢資料****************************************/
            EmpService empSvc = new EmpService();
            EmpVO empVO = empSvc.getOneEmp(employeeId);

            /***************************3.查詢完成,準備轉交(Send the Success view)************/
            req.setAttribute("empVO", empVO);
            String url = "/emp/update_emp_input.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("update".equals(action)) { 
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer employeeId = Integer.valueOf(req.getParameter("employee_id").trim());

            /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
            String account = req.getParameter("account").trim();
            if (account == null || account.length() == 0) {
                errorMsgs.add("帳號請勿空白");
            }

            String password = req.getParameter("password").trim();
            if (password == null || password.length() == 0) {
                errorMsgs.add("密碼請勿空白");
            }

            String name = req.getParameter("name").trim();
            if (name == null || name.length() == 0) {
                errorMsgs.add("姓名請勿空白");
            }

            String email = req.getParameter("email").trim();
            if (email == null || email.length() == 0) {
                errorMsgs.add("電子信箱請勿空白");
            }

            Byte status = null;
            try {
                status = Byte.valueOf(req.getParameter("status").trim());
            } catch (Exception e) {
                errorMsgs.add("帳號狀態格式錯誤");
            }

            Timestamp createTime = null;
            try {
                createTime = Timestamp.valueOf(req.getParameter("create_time").trim());
            } catch (Exception e) {
                createTime = new Timestamp(System.currentTimeMillis());
            }

            Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

            EmpVO empVO = new EmpVO();
            empVO.setEmployeeId(employeeId);
            empVO.setAccount(account);
            empVO.setPassword(password);
            empVO.setName(name);
            empVO.setEmail(email);
            empVO.setStatus(status);
            empVO.setCreateTime(createTime);
            empVO.setUpdatedTime(updatedTime);

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("empVO", empVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/update_emp_input.jsp");
                failureView.forward(req, res);
                return;
            }

            /***************************2.開始修改資料*****************************************/
            EmpService empSvc = new EmpService();
            empVO = empSvc.updateEmp(employeeId, account, password, name, email, status, createTime, updatedTime);

            /***************************3.修改完成,準備轉交(Send the Success view)*************/
            req.setAttribute("empVO", empVO);
            String url = "/emp/listOneEmp.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("insert".equals(action)) { 
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            String account = req.getParameter("account").trim();
            if (account == null || account.length() == 0) {
                errorMsgs.add("帳號請勿空白");
            }

            String password = req.getParameter("password").trim();
            if (password == null || password.length() == 0) {
                errorMsgs.add("密碼請勿空白");
            }

            String name = req.getParameter("name").trim();
            if (name == null || name.length() == 0) {
                errorMsgs.add("姓名請勿空白");
            }

            String email = req.getParameter("email").trim();
            if (email == null || email.length() == 0) {
                errorMsgs.add("電子信箱請勿空白");
            }

            Byte status = null;
            try {
                status = Byte.valueOf(req.getParameter("status").trim());
            } catch (Exception e) {
                errorMsgs.add("帳號狀態格式錯誤");
            }

            Timestamp createTime = new Timestamp(System.currentTimeMillis());
            Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

            EmpVO empVO = new EmpVO();
            empVO.setAccount(account);
            empVO.setPassword(password);
            empVO.setName(name);
            empVO.setEmail(email);
            empVO.setStatus(status);
            empVO.setCreateTime(createTime);
            empVO.setUpdatedTime(updatedTime);

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("empVO", empVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/addEmp.jsp");
                failureView.forward(req, res);
                return;
            }

            /***************************2.開始新增資料***************************************/
            EmpService empSvc = new EmpService();
            empVO = empSvc.addEmp(account, password, name, email, status, createTime, updatedTime);

            /***************************3.新增完成,準備轉交(Send the Success view)***********/
            String url = "/emp/listAllEmp.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("delete".equals(action)) { 
        	/***************************1.接收請求參數***************************************/
            Integer employeeId = Integer.valueOf(req.getParameter("employee_id"));

            /***************************2.開始刪除資料***************************************/
            EmpService empSvc = new EmpService();
            empSvc.deleteEmp(employeeId);

			/***************************3.刪除完成,準備轉交(Send the Success view)***********/			
            String url = "/emp/listAllEmp.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }
    }
}