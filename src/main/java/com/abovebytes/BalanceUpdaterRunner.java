package com.abovebytes;

import com.abovebytes.entities.*;
import com.abovebytes.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.joda.time.DateTime;
import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.Logger;

public class BalanceUpdaterRunner {
    private static final Logger logger = LogManager.getLogger(BalanceUpdaterRunner.class);

    public static void main(String[] args) {
        Query query = null;
        String username = "";
        String password = "";

        if (args == null || args.length !=2 ) {
            logger.error("Args null");
            System.out.println("Args null");
            System.exit(-1);
        } else {
            username = args[0];
            password = args[1];
            logger.info("Setting connection for username {} and password {} ", username, password);
        }

        Configuration configuration = datasource(username, password);
        configuration.addAnnotatedClass(Expense.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Solde.class);
        configuration.addAnnotatedClass(SaleHistory.class);
        configuration.addAnnotatedClass(TotalSum.class);

        // Create Session Factory
        SessionFactory sessionFactory  = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterDay = cal.getTime();

        //get yesterday's date from midnight
        Date yest = Utils.getTodayDateFromMidnight(yesterDay);

        logger.info("Yesterday's Date {}  ", Utils.formatDate(yest));

        DateTime dateTime = new DateTime(yest);

        DateTime from = dateTime.withTimeAtStartOfDay();
        DateTime to = dateTime.withTime(23, 59, 59, 999);

        logger.info("From {}  to {}  ", Utils.formatDate(from.toDate()), Utils.formatDate(to.toDate()));

        query = session.createQuery("SELECT IFNULL(SUM(cost), 0) from Expense WHERE expenseCreatedDate BETWEEN :from AND :to");
        Query soldeQuery = session.createQuery("SELECT soldeValue from Solde");

        query.setParameter("from", from.toDate());
        query.setParameter("to", to.toDate());

        Double yesterDayExpenses = (Double) query.getSingleResult();

        query = session.createQuery("SELECT IFNULL(SUM(paid), 0) from SaleHistory WHERE saleHistoryCreatedDate BETWEEN :fromDate AND :toDate");

        query.setParameter("fromDate", from.toDate());
        query.setParameter("toDate", to.toDate());

        Double yesterDayInputs = (Double) query.getSingleResult();
        // get sales today and remaining amount

        if (yesterDayExpenses == 0 && yesterDayInputs == 0) {
            logger.info("Yesterday expenses and entries is 0 exiting ");
            System.exit(-1);
        }
        Double soldeValue = (Double) soldeQuery.getSingleResult();

        logger.info("YesterDayExpenses {}  SoldeValue {}  ", yesterDayExpenses, soldeValue);

        int update = updateSolde(session, soldeValue + yesterDayInputs, yesterDayExpenses);

        logger.info("Update result : {} ", update);

        if (update > 0) {
            createHistory(session, soldeValue,  yesterDayInputs, yesterDayExpenses);
            logger.info("History created ");
            System.out.println("Update Query result " + update);
        }
    }

    public static int updateSolde(Session session, Double soldeValue, Double yesterDayExpenses) {
        int result = -1;

        boolean openTransaction = !session.getTransaction().isActive();
        if (openTransaction)
            session.getTransaction().begin();
        try {
            if (openTransaction) {
                Solde solde = session.get(Solde.class, 1);
                solde.setSoldeModifyDate(new Date());
                Double diff = soldeValue - yesterDayExpenses;
                solde.setSoldeValue(diff);
                session.update(solde);
                logger.info("New Solde : {} ", diff);
                result = 1;
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        return result;
    }

    public static int createHistory(Session session, Double soldeFromValue, Double yesterdayEntries, Double yesterDayExpenses) {
        int result = -1;

        boolean openTransaction = !session.getTransaction().isActive();
        if(openTransaction)
            session.getTransaction().begin();
        try {
            if (openTransaction) {
                SoldeHistory soldeHistory = new SoldeHistory();

                soldeHistory.setHistoryCreatedDate(new Date());
                soldeHistory.setHistoryModifyDate(new Date());
                soldeHistory.setFromCost(soldeFromValue);
                soldeHistory.setExpensesCost(yesterDayExpenses);
                soldeHistory.setDifferenceCost((soldeFromValue + yesterdayEntries) - yesterDayExpenses);
                soldeHistory.setUserCreateId(1);
                soldeHistory.setUserModifyId(1);

                session.persist(soldeHistory);
                result = 1;
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        return result;
    }

    public static Configuration datasource(String username, String password) {
        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/gesapp");
        config.setProperty("hibernate.connection.username", username);
        config.setProperty("hibernate.connection.password", password);
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        return config;
    }
}