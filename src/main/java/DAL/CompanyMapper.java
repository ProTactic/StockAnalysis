package DAL;
import System.CompanyOverviewRecord;
import System.IncomeStatementRecord;
import System.BalanceSheetRecord;
import System.CompanyFinancialRecord;
import org.hibernate.query.Query;

import java.util.List;

public class CompanyMapper extends GeneralMapper<CompanyOverviewRecord, String> {

    public CompanyMapper() {
        super(CompanyOverviewRecord.class);
    }

    public <E extends CompanyFinancialRecord> List<E> getFinancialStatement(String symbol, Class<E> classType) {
        String hql = "FROM " + classType.getSimpleName() + " WHERE symbol=:symbol";
        Query<E> query = currentSession.createQuery(hql ,classType)
                .setParameter("symbol", symbol);
        return query.getResultList();
    }

    public <E> void save(List<E> record){
        currentSession.beginTransaction();

        for(int i=0; i < record.size(); i++){
            if(i % 10 == 0){
                currentSession.flush();
                currentSession.clear();
            }
            currentSession.save(record.get(i));
        }
        currentSession.getTransaction().commit();
    }
}
