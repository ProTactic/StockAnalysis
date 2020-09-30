package DAL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.NonUniqueResultException;
import java.io.Serializable;
import java.util.List;

public abstract class GeneralMapper<T, Id extends Serializable> {

    private static SessionFactory sessionFactory;

    protected Session currentSession;
    protected Class<T> classType;

    GeneralMapper(Class<T> classType){
        sessionFactory = HibernateUtil.getSessionFactory();
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
        openCurrentSession();
        T data = currentSession.get(classType, id);
        closeCurrentSession();
        return data;
    }

    public void save(T entity){
        openCurrentSession();
        currentSession.save(entity);
        closeCurrentSession();
    }

    /*abstract public void update(T entity);

    abstract public void delete(T entity);

    abstract public List<T> findAll();

    abstract public void deleteAll();*/

    public T getSingleResultOrNull(Query<T> query){
        List<T> results = query.getResultList();
        if (results.isEmpty()) { return null; }
        else if (results.size() == 1) { return results.get(0); }
        throw new NonUniqueResultException();
    }

}
