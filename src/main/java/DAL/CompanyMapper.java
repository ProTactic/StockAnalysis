package DAL;
import System.CompanyOverviewRecord;
import org.hibernate.query.Query;

public class CompanyMapper extends GeneralMapper<CompanyOverviewRecord, String> {

    public CompanyMapper() {
        super(CompanyOverviewRecord.class);
    }

}
