package com.emp.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.emp.model.*;

public class EmpServlet extends HttpServlet {

    // GET 請求統一交給 doPost 處理
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        // ================= 單筆查詢 =================
        if ("getOne_For_Display".equals(action)) {
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            // 1. 取得員工編號並檢查格式
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
                return;
            }

            // 2. 查詢資料
            EmpService empSvc = new EmpService();
            EmpVO empVO = empSvc.getOneEmp(employeeId);
            if (empVO == null) {
                errorMsgs.add("查無資料");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            // 3. 成功 → forward 到顯示單筆資料頁面
            req.setAttribute("empVO", empVO);
            RequestDispatcher successView = req.getRequestDispatcher("/emp/listOneEmp.jsp");
            successView.forward(req, res);
        }

        // ================= 查詢並準備修改 =================
        if ("getOne_For_Update".equals(action)) {
            Integer employeeId = Integer.valueOf(req.getParameter("employee_id"));

            EmpService empSvc = new EmpService();
            EmpVO empVO = empSvc.getOneEmp(employeeId);

            req.setAttribute("empVO", empVO);
            RequestDispatcher successView = req.getRequestDispatcher("/emp/update_emp_input.jsp");
            successView.forward(req, res);
        }

        // ================= 修改 =================
        if ("update".equals(action)) {
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer employeeId = Integer.valueOf(req.getParameter("employee_id").trim());

            // 1. 驗證輸入
            String account = req.getParameter("account").trim();
            if (account == null || account.length() == 0) {
                errorMsgs.add("帳號請勿空白");
            }

            String password = req.getParameter("password").trim();
            if (password == null || password.length() < 8) {
                errorMsgs.add("密碼請勿小於8個字元");
            }

            String name = req.getParameter("name").trim();
            if (name == null || name.length() == 0) {
                errorMsgs.add("姓名請勿空白");
            }

            String email = req.getParameter("email").trim();
            if (email == null || email.length() == 0) {
                errorMsgs.add("電子信箱請勿空白");
            } else {
                // 使用正規表達式檢查 email 格式
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                if (!email.matches(emailRegex)) {
                    errorMsgs.add("電子信箱格式不正確");
                }
            }

            Byte status = null;
            try {
                status = Byte.valueOf(req.getParameter("status").trim());
            } catch (Exception e) {
                errorMsgs.add("帳號狀態格式錯誤");
            }

            // 2. 保留原本的 createTime，不要覆蓋
            EmpService empSvc = new EmpService();
            Timestamp createTime = empSvc.getOneEmp(employeeId).getCreateTime();

            // 3. 更新時間每次更新都重新設定
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

            // 4. 更新資料
            empVO = empSvc.updateEmp(employeeId, account, password, name, email, status, createTime, updatedTime);

            // 5. 成功 → forward 到顯示單筆資料頁面
            req.setAttribute("empVO", empVO);
            RequestDispatcher successView = req.getRequestDispatcher("/emp/listOneEmp.jsp");
            successView.forward(req, res);
        }

        // ================= 新增 =================
        if ("insert".equals(action)) {
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            String account = req.getParameter("account").trim();
            if (account == null || account.length() == 0) {
                errorMsgs.add("帳號請勿空白");
            }

            String password = req.getParameter("password").trim();
            if (password == null || password.length() < 8) {
                errorMsgs.add("密碼請勿小於8個字元");
            }

            String name = req.getParameter("name").trim();
            if (name == null || name.length() == 0) {
                errorMsgs.add("姓名請勿空白");
            }

            String email = req.getParameter("email").trim();
            if (email == null || email.length() == 0) {
                errorMsgs.add("電子信箱請勿空白");
            } else {
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                if (!email.matches(emailRegex)) {
                    errorMsgs.add("電子信箱格式不正確");
                }
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

            EmpService empSvc = new EmpService();
            empVO = empSvc.addEmp(account, password, name, email, status, createTime, updatedTime);

            RequestDispatcher successView = req.getRequestDispatcher("/emp/listAllEmp.jsp");
            successView.forward(req, res);
        }

        // ================= 刪除 =================
        if ("delete".equals(action)) {
            Integer employeeId = Integer.valueOf(req.getParameter("employee_id"));

            EmpService empSvc = new EmpService();
            empSvc.deleteEmp(employeeId);

            RequestDispatcher successView = req.getRequestDispatcher("/emp/listAllEmp.jsp");
            successView.forward(req, res);
        }
    }
}