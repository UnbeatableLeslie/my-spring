package com.pengheng.servlet;

import com.pengheng.factory.BeanFactory;
import com.pengheng.pojo.Result;
import com.pengheng.service.TransferService;
import com.pengheng.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pengheng
 */
@WebServlet(name = "transferServlet", urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

//    1. 通过实例化创建对象
//    private TransferService transferService = new TransferServiceImpl();
//    2. 通过工厂创建对象
//    private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");
//    3.通过动态代理获取 添加事务增强的trnasferService对象
//    private TransferService transferService = (TransferService) ProxyFactory.getInstance().getJDKProxy(BeanFactory.getBean("transferService"));

    private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {

            // 2. 调用service层方法
            transferService.transfer(fromCardNo, toCardNo, money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }
}
