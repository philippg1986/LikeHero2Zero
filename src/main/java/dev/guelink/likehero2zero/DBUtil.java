package guelink.dev.likeherotozero;

import dev.guelink.likehero2zero.Emission;
import dev.guelink.likehero2zero.Scientist;
import org.hibernate.type.internal.UserTypeSqlTypeAdapter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DBUtil {

    private static guelink.dev.likeherotozero.DBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:/likeHero2Zero";
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static guelink.dev.likeherotozero.DBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new guelink.dev.likeherotozero.DBUtil();
        }
        return instance;
    }

    public DBUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource ds = (DataSource) context.lookup(jndiName);
        return ds;
    }

    public List<Scientist> getScientists() throws Exception {
        List<Scientist> scientists = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            String sql = "select * from scientists";
            logger.info("Exceuting query: " + sql);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String mail = resultSet.getString("mail");
                String password = resultSet.getString("password");

                Scientist scientist = new Scientist(id, firstName, lastName, mail, password);
                scientists.add(scientist);
            }

            logger.info("scientists list size is now: " + scientists.size());

            return scientists;
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
    }

    public List<Emission> getEmissions() throws Exception {

        List<Emission> emissions = new ArrayList<Emission>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM emissions";
            logger.info("Executing query: " + sql);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String country = resultSet.getString("country");
                int year = resultSet.getInt("year");
                double co2 = resultSet.getDouble("co2");
                int scientistId = resultSet.getInt("scientist");

                Emission emission = new Emission(id,country,co2,year,scientistId,null);

                emissions.add(emission);

                logger.info(id+" "+country+" "+year+" "+" "+co2);
                logger.info(emission.toString());
            }

            logger.info("emissions list size is now: " + emissions.size());

            return emissions;

        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
    }

    public void addEmission(Emission emission) throws Exception {

        System.out.println("DBUtil addEmission method " + emission.toString());



        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = dataSource.getConnection();
            String sql = "INSERT INTO emissions (country,co2,year) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, emission.getCountry());
            preparedStatement.setDouble(2, emission.getCo2());
            preparedStatement.setInt(3, emission.getYear());

            preparedStatement.execute();

        } finally {
            connection.close();
            preparedStatement.close();
        }
    }

}
