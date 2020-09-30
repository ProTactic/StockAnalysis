package DAL;
import System.CompanyOverviewRecord;
import org.hibernate.query.Query;

public class CompanyMapper extends GeneralMapper<CompanyOverviewRecord, Long> {

    public CompanyMapper() {
        super(CompanyOverviewRecord.class);
    }

    public CompanyOverviewRecord findBySymbol(String symbol){
        openCurrentSession();
        Query<CompanyOverviewRecord> query = currentSession.createQuery("FROM CompanyOverviewRecord WHERE symbol=:symbol", classType);
        query.setParameter("symbol", symbol);
        CompanyOverviewRecord record = getSingleResultOrNull(query);
        closeCurrentSession();
        return record;
    }
}
