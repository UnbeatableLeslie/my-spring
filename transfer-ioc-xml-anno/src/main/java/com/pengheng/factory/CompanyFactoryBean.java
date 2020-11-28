package com.pengheng.factory;

import com.pengheng.pojo.Company;
import org.springframework.beans.factory.FactoryBean;

public class CompanyFactoryBean implements FactoryBean<Company> {

    private String companyInfo; // 公司名称,地址,规模

    //创建复杂对象 companyBean 一般例如xml和注解实现困难的对象
    @Override
    public Company getObject() throws Exception {
        String[] strings = companyInfo.split(",");
        Company company = new Company();
        company.setName(strings[0]);
        company.setAddress(strings[1]);
        company.setScale(Integer.parseInt(strings[2]));
        return company;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }
}
