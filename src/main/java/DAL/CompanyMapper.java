package DAL;
import System.CompanyOverviewRecord;
import System.IncomeStatementRecord;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CompanyMapper extends GeneralMapper<CompanyOverviewRecord, String> {

    public CompanyMapper() {
        super(CompanyOverviewRecord.class);
    }
    
    public List<IncomeStatementRecord> getIncomeStatements(String symbol){
        Query<IncomeStatementRecord> query = currentSession.createQuery("FROM IncomeStatementRecord WHERE symbol= :symbol", IncomeStatementRecord.class)
                .setParameter("symbol", symbol);
        return query.getResultList();
    }

    public void save(List<IncomeStatementRecord> incomeStatementRecords){
        currentSession.beginTransaction();

        for(int i=0; i < incomeStatementRecords.size(); i++){
            if(i % 10 == 0){
                currentSession.flush();
                currentSession.clear();
            }
            currentSession.save(incomeStatementRecords.get(i));
        }

        currentSession.getTransaction().commit();
    }
}
