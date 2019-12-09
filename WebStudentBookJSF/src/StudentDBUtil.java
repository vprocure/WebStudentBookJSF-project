import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@ManagedBean(name="studentDBUtil")
@ApplicationScoped
public class StudentDBUtil {

	private DataSource dataSource;

	public StudentDBUtil() {
		try{
			dataSource=getDataSource();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public StudentDBUtil (DataSource theDataSource) {
		dataSource=theDataSource;
	}


	public DataSource getDataSource() throws NamingException{
		String jndi = "java:comp/env/jdbc/studentdb";
		Context context = new InitialContext();
		DataSource dataSource = (DataSource)
				context.lookup(jndi);
		return dataSource;
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<Student>();
		
		Connection myConn=null;
		Statement myStmt = null;
		ResultSet myRs=null;
		
		try {
				myConn= dataSource.getConnection();
				myStmt = myConn.createStatement();
				String sql = "select * from student order by id";
				myRs = myStmt.executeQuery(sql);
				
				while(myRs.next())
				{
					int id = myRs.getInt("id");
					String firstName=myRs.getString("firstname");
					String lastName=myRs.getString("lastname");
					String email = myRs.getString("email");
					
					Student tempStudent = new Student(id,firstName,lastName,email);
					students.add(tempStudent);
				}
				
				return students;
				
		} finally { close(myConn,myStmt,myRs);}
	}
	
	public void addStudent(Student student) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		if(student.getId()==0)
		{
			try {
				myConn = dataSource.getConnection();
				String sql = "insert into student (firstname,lastname,email) values (?,?,?)";
				myStmt = myConn.prepareStatement(sql);
				String fs = student.getFirst_Name();
				String ls = student.getLast_Name();
				String em = student.getEmail();
				myStmt.setString(1, fs);
				myStmt.setString(2, ls);
				myStmt.setString(3, em);
				myStmt.execute();
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			
			finally {
				close(myConn,myStmt,myRs);
			}
		}
		else {
			try {
				myConn = dataSource.getConnection();
				String sql = "insert into student (id,firstname,lastname,email) values (?,?,?,?)";
				myStmt = myConn.prepareStatement(sql);
				String fs = student.getFirst_Name();
				String ls = student.getLast_Name();
				String em = student.getEmail();
				int id = student.getId();
				myStmt.setInt(1, id);
				myStmt.setString(2, fs);
				myStmt.setString(3, ls);
				myStmt.setString(4, em);
				myStmt.execute();
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			
			finally {
				close(myConn,myStmt,myRs);
			}
		}
		
	}
	
	public Student fetchStudent(int id)  throws Exception{		
		Connection myConn=null;
		Statement myStmt = null;
		ResultSet myRs=null;
		
		try {
				myConn= dataSource.getConnection();
				myStmt = myConn.createStatement();
				String sql = "select * from student where id="+id;
				myRs = myStmt.executeQuery(sql);
				
				String firstName=myRs.getString("firstname");
				String lastName=myRs.getString("lastname");
				String email = myRs.getString("email");
								
				return (new Student(firstName,lastName,email));
				
		} finally { close(myConn,myStmt,myRs);}
	}

	public void editStudent(int id, String firstName, String lastName, String email) throws Exception{
		Connection myConn=null;
		PreparedStatement myStmt = null;
		ResultSet myRs=null;
				
		try {
				myConn= dataSource.getConnection();
				String sql = "update student set firstname='"+firstName+"', lastname='"+lastName+"', email='"+email+"' where id="+id;
				myStmt = myConn.prepareStatement(sql);
				myStmt.execute();		
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally { close(myConn,myStmt,myRs);}
	}
	
	public void deleteStudent(int id) throws Exception{
		Connection myConn=null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
				myConn = dataSource.getConnection();
				String sql = "delete from student where id="+id;
				myStmt = myConn.prepareStatement(sql);
				myStmt.execute();
		}finally {close(myConn,myStmt,myRs);}
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs)
	{
		try {
				if(myStmt!=null)
					myStmt.close();
				if(myRs!=null)
					myRs.close();
				if(myConn!=null)
					myConn.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
