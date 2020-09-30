package System;

import DAL.CompanyMapper;
import DAL.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args){
        /*System.RemoteDataHandler alphavantageAPIHandler = new System.AlphavantageAPIHandler();
        System.CompanyOverviewRecord data = alphavantageAPIHandler.companyOverview("IBM");
        System.out.println(data.toString());*/

        /*SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        session.beginTransaction();
        List<CompanyOverviewRecord> records = session.createQuery("FROM CompanyOverviewRecord", CompanyOverviewRecord.class).getResultList();
        for(CompanyOverviewRecord record : records){
            System.out.println(record);
        }*/
        /*session.save(data);
        session.getTransaction().commit();*/

        //session.close();

        //CompanyMapper mapper = new CompanyMapper();
        //CompanyOverviewRecord record = mapper.findById(1L);

        SystemManager systemManager = new SystemManager();
        CompanyOverviewRecord record = systemManager.getCompanyOverview("IBM");
        System.out.println(record);
    }
}