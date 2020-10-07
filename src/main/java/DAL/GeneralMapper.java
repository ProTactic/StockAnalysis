package DAL;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.NonUniqueResultException;
import java.io.Serializable;
import java.util.List;

public abstract class GeneralMapper<T, Id extends Serializable> {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    protected Session currentSession;
    protected Class<T> classType;

    GeneralMapper(Class<T> classType){
        openCurrentSession();
        this.classType = classType;

    }

    protected Session openCurrentSession(){
        closeCurrentSession();
        currentSession = sessionFactory.openSession();
        return currentSession;
    }

    protected void closeCurrentSession(){
        if(currentSession != null && currentSession.isOpen()){
            currentSession.close();
        }
    }

    public T findById(Id id){
        return currentSession.get(classType, id);
    }

    public void save(T entity){
        currentSession.beginTransaction();
        currentSession.save(entity);
        currentSession.getTransaction().commit();
    }

    public void update(T entity){
        currentSession.beginTransaction();
        currentSession.merge(entity);
        currentSession.getTransaction().commit();

    }

    public void delete(T entity){
        currentSession.beginTransaction();
        currentSession.delete(entity);
        currentSession.getTransaction().commit();

    }

    public List<T> findAll(){
        return currentSession.createQuery("FROM CompanyOverviewRecord", classType).list();
    }

    /*abstract public void deleteAll();*/

    public T getSingleResultOrNull(Query<T> query){
        List<T> results = query.getResultList();
        if (results.isEmpty()) { return null; }
        else if (results.size() == 1) { return results.get(0); }
        throw new NonUniqueResultException();
    }

}
