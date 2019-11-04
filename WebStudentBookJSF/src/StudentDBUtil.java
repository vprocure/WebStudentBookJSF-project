import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StudentDBUtil {

	private DataSource dataSource;


	public DataSource getDataSource() {
		return dataSource;
	}


	public StudentDBUtil() {
		try{
			dataSource=setDataSource();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public DataSource setDataSource() throws NamingException{
		String jndi = "jva:comp/env/jdbc/studentdb";
		Context context = new InitialContext();
		DataSource dataSource = (DataSource)
				context.lookup(jndi);
		return dataSource;
	}
	
	
}
